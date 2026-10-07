package mx.gob.atdt.interprete.runnable;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.HomeDAO;
import mx.gob.atdt.interprete.dto.AgregaDescripcionesDTO;
import mx.gob.atdt.interprete.dto.DetApoyoOtorgadoDTO;
import mx.gob.atdt.interprete.dto.DetEspecificacionRequisitoDTO;
import mx.gob.atdt.interprete.dto.DetEstadoSistemaDTO;
import mx.gob.atdt.interprete.dto.DetExcepcionTramiteDTO;
import mx.gob.atdt.interprete.dto.DetHomeDTO;
import mx.gob.atdt.interprete.dto.DetLegalesDTO;
import mx.gob.atdt.interprete.dto.DetObjetivosProgramaDTO;
import mx.gob.atdt.interprete.dto.DetPoblacionObjetivoDTO;
import mx.gob.atdt.interprete.dto.DetProgramaSocialDTO;
import mx.gob.atdt.interprete.dto.DetRequisitoDTO;
import mx.gob.atdt.interprete.dto.DetTramiteServicioDTO;
import mx.gob.atdt.interprete.dto.HomeProgramaSocialDTO;
import mx.gob.atdt.interprete.dto.HomeTramiteDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.util.WebResources;
import mx.gob.atdt.widget.application.IndexBean;

public class IndexRunnable implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndexRunnable.class);

	private final IndexBean indexBean;
 
	private ProyectoDTO proyectoDTO;

	// Home Tramite o Servicio
	private HomeTramiteDTO homeTramiteDTO;
	private List<AgregaDescripcionesDTO> lstRequisitos;
	private List<AgregaDescripcionesDTO> lstEspecificaciones;
	private List<AgregaDescripcionesDTO> lstExepciones;

	// Home Programa Social
	private HomeProgramaSocialDTO homeProgramaDTO;
	private List<AgregaDescripcionesDTO> lstObjetivos;
	private List<AgregaDescripcionesDTO> lstPoblacion;
	private List<AgregaDescripcionesDTO> lstApoyos;

	public IndexRunnable(final IndexBean indexBean) {
		this.indexBean = indexBean;
	}

	@Override
	public void run() {
		try {
			/** Antes de realizar consultas a las tablas, primero se valida si existe el esquema de BD
			 *  Si no existe quiere decir que es la primera vez que se ejecuta ear en el cliente
			 **/
			HomeDAO homeDAO = new HomeDAO();
			
			if (homeDAO.existeEsquemaInterprete()) {				
				LOGGER.debug("------------------> INDEX: Intentando actualizar la información para los home ... ");
				proyectoDTO = new ProyectoDTO();			

				/**
				 * Se agrega condición para que únicamente sea ejecutada la actualización del
				 * home cuando El estatus del Sistema se encuentre en "EN LINEA", "SINCRONIZADO"
				 * O "PAUSADO".
				 */
	
				DetEstadoSistemaDTO estadoSistema = null;
				if (homeDAO.existeTablaEstadoSistema()) {
					estadoSistema = homeDAO.consultarEstadoSistema();
					indexBean.setEstadoSistemaDTO(estadoSistema);	
				}
				
				
				if (BeanUtils.isNotNull(estadoSistema) && (estadoSistema.getCatEstadosSistemaDTO()
						.getIdEstadoSistema() == Constantes.ID_ESTADO_SISTEMA_LINEA
						|| estadoSistema.getCatEstadosSistemaDTO()
								.getIdEstadoSistema() == Constantes.ID_ESTADO_SISTEMA_PAUSADO)) {
					
					proyectoDTO = homeDAO.consultaProyecto();
					
					if (BeanUtils.isNotNull(proyectoDTO)) {
	
						indexBean.setProyectoDTO(proyectoDTO);
							
						DetLegalesDTO legalesDTO = new DetLegalesDTO();
						legalesDTO = homeDAO.buscarDetalleLegalesPorIdProyecto(proyectoDTO.getIdProyecto());
	
						// Se valida el tipo de proyecto
						if (proyectoDTO.getCatTipoProyectoDTO().getIdTipoProyecto() == Constantes.PROYECTO_TIPO_TRAMITE) {
							homeTramiteDTO = new HomeTramiteDTO();
	
							if (BeanUtils.isNotNull(legalesDTO)) {
								homeTramiteDTO.setLegalesDTO(legalesDTO);
							}
	
							DetHomeDTO existeDetalleHome = new DetHomeDTO();
	
							existeDetalleHome = homeDAO.buscarPorIdProyecto(proyectoDTO.getIdProyecto());
							if (BeanUtils.isNotNull(existeDetalleHome)) {
								try {
									// Seteo de datos de la tabla detalle_home
									homeTramiteDTO.setDetalleHomeDTO(existeDetalleHome);
									homeTramiteDTO.setNotificacion(existeDetalleHome.isHabilitaNotificacion());
									homeTramiteDTO.setRutaImagen(existeDetalleHome.getRutaArchivoLogotipo());
									homeTramiteDTO.setNotificacionEmpezar(existeDetalleHome.getDescripcionNotificacion());
									homeTramiteDTO.setRutaImagen(existeDetalleHome.getRutaArchivoLogotipo());
									homeTramiteDTO.setFechaCreacion(existeDetalleHome.getFechaCreacion());
									homeTramiteDTO.setSeccionSincronizada(existeDetalleHome.isSeccionSincronizada());
									homeTramiteDTO.getDetalleHomeDTO().setPersonalizaPausa(existeDetalleHome.isPersonalizaPausa());
									homeTramiteDTO.getDetalleHomeDTO().setTituloPausa(existeDetalleHome.getTituloPausa());
									homeTramiteDTO.getDetalleHomeDTO().setDescripcionPausa(existeDetalleHome.getDescripcionPausa());
	
									// Se setea la ruta para obtener la imagen desde el motor
									homeTramiteDTO.setRutaImagen(obtenerPathArchivos(homeTramiteDTO.getRutaImagen()));
	//								LOGGER.info("Imagen home trámite: " + homeTramiteDTO.getRutaImagen());
	
									// Se llena DTO de Tramite Servicio
									DetTramiteServicioDTO existetramite = new DetTramiteServicioDTO();
									existetramite = homeDAO.buscarPorIdDetalleHome(existeDetalleHome.getIdDetalleHome());
	
									// Seteo de datos de la tabla detalle_tramite_servicio
									homeTramiteDTO.setIdHomeTramite(existetramite.getIdDetalleTramite());
									homeTramiteDTO.setCosto(existetramite.isHabilitaCostoTramite());
									homeTramiteDTO.setCostoTramite(existetramite.getDescripcionCostoTramite());
									homeTramiteDTO.setExepcion(existetramite.isHabilitaExcepcionTramite());
	
									List<DetRequisitoDTO> lstDetalleRequisito = new ArrayList<DetRequisitoDTO>();
									lstRequisitos = new ArrayList<AgregaDescripcionesDTO>();
									// Buscamos los requisitos por el id detalle home
	
									lstDetalleRequisito = homeDAO
											.buscarRequisitosPorIdDetalleHome(existeDetalleHome.getIdDetalleHome());
									if (BeanUtils.isNotEmpty(lstDetalleRequisito)) {
										lstDetalleRequisito.forEach(detReq -> {
											// Llenamos la lista de requisitos
											AgregaDescripcionesDTO existeReq = new AgregaDescripcionesDTO();
											existeReq.setId(detReq.getIdRequisito());
											existeReq.setOrden(detReq.getOrden());
											existeReq.setActivo(detReq.isActivo());
											existeReq.setDescripcion(detReq.getDescripcionRequisito());
	
											// Buscamos las especificaciones de cada requisito
											List<DetEspecificacionRequisitoDTO> lstDetalleEspecificacion = new ArrayList<DetEspecificacionRequisitoDTO>();
											lstEspecificaciones = new ArrayList<AgregaDescripcionesDTO>();
	
											try {
												lstDetalleEspecificacion = homeDAO
														.buscarEspecificacionesPorIdRequisito(detReq.getIdRequisito());
											} catch (Exception e) {
												LOGGER.error("Error al obtener el detalle de especificaciones ", e);
											}
	
											if (BeanUtils.isNotEmpty(lstDetalleEspecificacion)
													&& BeanUtils.isNotNull(lstDetalleEspecificacion)) {
												lstDetalleEspecificacion.forEach(detEsp -> {
													AgregaDescripcionesDTO existeEsp = new AgregaDescripcionesDTO();
													existeEsp.setId(detEsp.getIdEspecificacion());
													existeEsp.setOrden(detEsp.getOrden());
													existeEsp.setDescripcion(detEsp.getDescripcionEspecificacion());
													existeEsp.setActivo(detEsp.isActivo());
													lstEspecificaciones.add(existeEsp);
												});
												existeReq.setSubLst(lstEspecificaciones);
											}
											lstRequisitos.add(existeReq);
										});
									}
	
									// Validamos si el check excepciones es true
									if (homeTramiteDTO.isExepcion()) {
										List<DetExcepcionTramiteDTO> lstDetalleExcepciones = new ArrayList<DetExcepcionTramiteDTO>();
										lstExepciones = new ArrayList<AgregaDescripcionesDTO>();
	
										lstDetalleExcepciones = homeDAO
												.buscarExcepcionesPorIdDetHome(existeDetalleHome.getIdDetalleHome());
	
										if (BeanUtils.isNotEmpty(lstDetalleExcepciones)) {
											lstDetalleExcepciones.forEach(detExcepciones -> {
												// Llenamos la lista de excepciones
												AgregaDescripcionesDTO existeExcepcion = new AgregaDescripcionesDTO();
												existeExcepcion.setId(detExcepciones.getIdExcepcion());
												existeExcepcion.setOrden(detExcepciones.getOrden());
												existeExcepcion.setDescripcion(detExcepciones.getDescripcionExcepcion());
												existeExcepcion.setActivo(detExcepciones.isActivo());
												lstExepciones.add(existeExcepcion);
											});
										}
									}
	
								} catch (FileNotFoundException e) {
									LOGGER.error("Error al obtener archivos de home trámite  "
											+ homeTramiteDTO.getDetalleHomeDTO().getIdDetalleHome(), e);
									WebResources.addSuccessMessage("msj_error_archivos_detalle_tramite", true);
								}
							}
	
							homeTramiteDTO.setLstRequisitos(lstRequisitos);
							homeTramiteDTO.setLstExepciones(lstExepciones);
							indexBean.setHomeTramiteDTO(homeTramiteDTO);
							LOGGER.debug(
									"------------------> TRAMITE O SERVICIO: Se actualizó correctamente la información para el home de un tramite o servicio");
							LOGGER.debug(homeTramiteDTO.toString());
						} else if (proyectoDTO.getCatTipoProyectoDTO()
								.getIdTipoProyecto() == Constantes.PROYECTO_TIPO_PROGRAMA) {
							homeProgramaDTO = new HomeProgramaSocialDTO();
							if (BeanUtils.isNotNull(legalesDTO)) {
								homeProgramaDTO.setLegalesDTO(legalesDTO);
							}
	
							DetHomeDTO existeDetalleHome = new DetHomeDTO();
							existeDetalleHome = homeDAO.buscarPorIdProyecto(proyectoDTO.getIdProyecto());
	
							if (BeanUtils.isNotNull(existeDetalleHome)) {
								try {
									// seteo de datos tabla detalle_home
									homeProgramaDTO.setDetalleHomeDTO(existeDetalleHome);
									homeProgramaDTO.setNotificacion(existeDetalleHome.isHabilitaNotificacion());
									homeProgramaDTO.setNotificacionEmpezar(existeDetalleHome.getDescripcionNotificacion());
									homeProgramaDTO.setRutaImagen(existeDetalleHome.getRutaArchivoLogotipo());
									homeProgramaDTO.setFechaCreacion(existeDetalleHome.getFechaCreacion());
									homeProgramaDTO.getDetalleHomeDTO().setPersonalizaPausa(existeDetalleHome.isPersonalizaPausa());
									homeProgramaDTO.getDetalleHomeDTO().setTituloPausa(existeDetalleHome.getTituloPausa());
									homeProgramaDTO.getDetalleHomeDTO().setDescripcionPausa(existeDetalleHome.getDescripcionPausa());
									homeProgramaDTO
											.setFechaUltimaActualizacion(existeDetalleHome.getFechaUltimaActualizacion());
	
									// Se setea la ruta para obtener la imagen desde el motor
									homeProgramaDTO.setRutaImagen(obtenerPathArchivos(homeProgramaDTO.getRutaImagen()));
	//								LOGGER.info("Imagen home programa: " + homeProgramaDTO.getRutaImagen());
	
									// se llena DTO de home programa social
									DetProgramaSocialDTO existePrograma = new DetProgramaSocialDTO();
	
									existePrograma = homeDAO
											.buscarProgramaSocialPorIdDetalleHome(existeDetalleHome.getIdDetalleHome());
	
									if (BeanUtils.isNotNull(existePrograma)) {
										homeProgramaDTO.setIdHome(existePrograma.getIdDetallePrograma());
										homeProgramaDTO.setDetalleHomeDTO(existeDetalleHome);
										homeProgramaDTO.setCicloPrograma(existePrograma.isHabilitaCicloPrograma());
										homeProgramaDTO.setCiclo(existePrograma.getDescripcionCicloPrograma());
										homeProgramaDTO.setTipoApoyo(existePrograma.getDescripcionTipoApoyo());
										homeProgramaDTO.setDuracionApoyo(existePrograma.getDescripcionDuracionApoyo());
										homeProgramaDTO.setAccesoPrograma(existePrograma.isHabilitaProgramaSimultaneo());
	
										List<DetObjetivosProgramaDTO> lstDetalleObjetivos = new ArrayList<DetObjetivosProgramaDTO>();
										lstObjetivos = new ArrayList<AgregaDescripcionesDTO>();
										// Buscamos los objetivos por el id detalle home
										lstDetalleObjetivos = homeDAO
												.buscarObjetivosPorIdDetalleHome(existeDetalleHome.getIdDetalleHome());
	
										if (BeanUtils.isNotEmpty(lstDetalleObjetivos)) {
											lstDetalleObjetivos.forEach(detObj -> {
												AgregaDescripcionesDTO existeObj = new AgregaDescripcionesDTO();
												existeObj.setId(detObj.getIdObjetivo());
												existeObj.setDescripcion(detObj.getDescripcionObjetivo());
												existeObj.setOrden(detObj.getOrden());
												existeObj.setActivo(detObj.isActivo());
												lstObjetivos.add(existeObj);
											});
										}
	
										List<DetPoblacionObjetivoDTO> lstDetallePoblacion = new ArrayList<DetPoblacionObjetivoDTO>();
										lstPoblacion = new ArrayList<AgregaDescripcionesDTO>();
										// Buscamos poblacion objetivo por el id detalle home
										lstDetallePoblacion = homeDAO
												.buscarListaPoblacionPorIdDetalleHome(existeDetalleHome.getIdDetalleHome());
	
										if (BeanUtils.isNotEmpty(lstDetallePoblacion)) {
											lstDetallePoblacion.forEach(detPob -> {
												AgregaDescripcionesDTO existePob = new AgregaDescripcionesDTO();
												existePob.setId(detPob.getIdPoblacionObjetivo());
												existePob.setDescripcion(detPob.getDescripcionPoblacionObjetivo());
												existePob.setOrden(detPob.getOrden());
												existePob.setActivo(detPob.isActivo());
												lstPoblacion.add(existePob);
											});
										}
	
										List<DetRequisitoDTO> lstDetalleRequisito = new ArrayList<DetRequisitoDTO>();
										lstRequisitos = new ArrayList<AgregaDescripcionesDTO>();
										// Buscamos los requisitos por el id detalle home
										lstDetalleRequisito = homeDAO
												.buscarRequisitosPorIdDetalleHome(existeDetalleHome.getIdDetalleHome());
	
										if (BeanUtils.isNotEmpty(lstDetalleRequisito)) {
											lstDetalleRequisito.forEach(detReq -> {
												// Llenamos la lista de requisitos
												AgregaDescripcionesDTO existeReq = new AgregaDescripcionesDTO();
												existeReq.setId(detReq.getIdRequisito());
												existeReq.setOrden(detReq.getOrden());
												existeReq.setActivo(detReq.isActivo());
												existeReq.setDescripcion(detReq.getDescripcionRequisito());
	
												// Buscamos las especificaciones de cada requisito
												List<DetEspecificacionRequisitoDTO> lstDetalleEspecificacion = new ArrayList<DetEspecificacionRequisitoDTO>();
												lstEspecificaciones = new ArrayList<AgregaDescripcionesDTO>();
												try {
													lstDetalleEspecificacion = homeDAO
															.buscarEspecificacionesPorIdRequisito(detReq.getIdRequisito());
												} catch (Exception e) {
													LOGGER.error(
															"Error al obtener detalle de especificaciones de requisitos  ",
															e);
												}
	
												if (BeanUtils.isNotEmpty(lstDetalleEspecificacion)) {
													lstDetalleEspecificacion.forEach(detEsp -> {
														AgregaDescripcionesDTO existeEsp = new AgregaDescripcionesDTO();
														existeEsp.setId(detEsp.getIdEspecificacion());
														existeEsp.setOrden(detEsp.getOrden());
														existeEsp.setDescripcion(detEsp.getDescripcionEspecificacion());
														existeEsp.setActivo(detEsp.isActivo());
														lstEspecificaciones.add(existeEsp);
													});
													existeReq.setSubLst(lstEspecificaciones);
												}
												lstRequisitos.add(existeReq);
											});
										}
	
										List<DetApoyoOtorgadoDTO> lstDetalleApoyo = new ArrayList<DetApoyoOtorgadoDTO>();
										lstApoyos = new ArrayList<AgregaDescripcionesDTO>();
										// Buscamos apoyos objetivo por el id detalle home
										lstDetalleApoyo = homeDAO
												.buscarApoyosPorIdDetalleHome(existeDetalleHome.getIdDetalleHome());
	
										if (BeanUtils.isNotEmpty(lstDetalleApoyo)) {
											lstDetalleApoyo.forEach(detApoyo -> {
												AgregaDescripcionesDTO existeApoyo = new AgregaDescripcionesDTO();
												existeApoyo.setId(detApoyo.getIdApoyo());
												existeApoyo.setDescripcion(detApoyo.getDescripcionApoyoOtorgado());
												existeApoyo.setOrden(detApoyo.getOrden());
												existeApoyo.setActivo(detApoyo.isActivo());
												lstApoyos.add(existeApoyo);
											});
										}
									}
	
								} catch (FileNotFoundException e) {
									LOGGER.error("Error al obtener archivos de home trámite  "
											+ homeProgramaDTO.getDetalleHomeDTO().getIdDetalleHome(), e);
								}
							}
							homeProgramaDTO.setLstObjetivos(lstObjetivos);
							homeProgramaDTO.setLstPoblacion(lstPoblacion);
							homeProgramaDTO.setLstRequisitos(lstRequisitos);
							homeProgramaDTO.setLstApoyos(lstApoyos);
							indexBean.setHomeProgramaDTO(homeProgramaDTO);
							LOGGER.debug(
									"------------------> PROGRAMA SOCIAL: Se actualizó correctamente la información para el home de un programa social");
							LOGGER.debug(homeProgramaDTO.toString());
						}
					} else {
						indexBean.setProyectoDTO(null);
						LOGGER.debug("----------> No existe ningún proyecto");
					}
	
			
				} else {
					indexBean.setProyectoDTO(null);
				}
			
			} else {
				indexBean.setProyectoDTO(null);
				LOGGER.info("El estatus actual del sistema es inconsistente, no se actualiza detalle de home y se regresa modal a en Mantenimiento.");
			}

		} catch (Exception e) {
			LOGGER.error("Ocurrió un error para obtener la información para los home: ", e);
		}
	}

	/**
	 * Método auxiliar que arma la URL del documento a obtener desde el file-server
	 * del motor-admin
	 * 
	 * @return
	 */
	private String obtenerPathArchivos(String rutaArchivo) {
		String pathArchivoMotor = Constantes.EMPTY_STRING;
		if (!BeanUtils.isEmpty(rutaArchivo)) {
			if (rutaArchivo.contains(Environment.getPathFileServerMotor())) {
				pathArchivoMotor = rutaArchivo.replace(Environment.getPathFileServerMotor(),
						Environment.getUrlFileServerMotor());
			}
		}
		return pathArchivoMotor;
	}

	public ProyectoDTO getProyectoDTO() {
		return proyectoDTO;
	}

	public void setProyectoDTO(ProyectoDTO proyectoDTO) {
		this.proyectoDTO = proyectoDTO;
	}

	public HomeTramiteDTO getHomeTramiteDTO() {
		return homeTramiteDTO;
	}

	public void setHomeTramiteDTO(HomeTramiteDTO homeTramiteDTO) {
		this.homeTramiteDTO = homeTramiteDTO;
	}

	public HomeProgramaSocialDTO getHomeProgramaDTO() {
		return homeProgramaDTO;
	}

	public void setHomeProgramaDTO(HomeProgramaSocialDTO homeProgramaDTO) {
		this.homeProgramaDTO = homeProgramaDTO;
	}

}

package mx.gob.atdt.interprete.formulario.facade;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.ejb.*;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.*;
import mx.gob.atdt.interprete.dto.*;
import mx.gob.atdt.webhook.NotificacionTramiteWebhookRESTClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.estructura.formulario.dao.DetalleFormularioDAO;
import mx.gob.atdt.interprete.formulario.dao.FormularioDAO;
import mx.gob.atdt.interprete.notificaciones.EnvioCorreo;
import mx.gob.atdt.interprete.notificaciones.EnvioCorreoEstatusTramite;

@Stateless
@LocalBean
public class FormularioFacade {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FormularioFacade.class);
	
	private static final String COMENTARIO_OBSERVACIONES = ". Observaciones: ";
	
	private static final String SE_ACTUALIZA_A = "Se actualiza estatus a : ";
	
	@Inject
	FormularioDAO formularioDAO;
	
	@Inject
	DetalleFormularioDAO detalleFormularioDAO;
	
	@Inject
	DetSecurityDomainDAO detSecurityDomainDAO;
	
	@Inject
	DetGestionUsuariosDAO detGestionUsuariosDAO;
	
	@Inject
	BitMovimientosTramiteDAO bitMovimientosTramiteDAO;
	
	@Inject
	ProyectoDAO proyectoDAO;
	
	@Inject
	CatEstatusTramiteDAO catEstatusTramiteDAO;

	@Inject
	private NotificacionMovimientoTramiteDAO notificacionMovimientoTramiteDAO;
	
	@Inject
	private DynamicTableDAO dynamicTableDAO;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long registrarTramite(TramiteDTO tramite) {

		//1. Se consulta consecutivo para trámite
		tramite.setIdTramite(formularioDAO.consultarConsecutivoTramite());
		
		//2. Se registra el trámite.
		formularioDAO.registrarTramiteUsuario(tramite);
		
		//3. Se registra movimiento en bitácora.
		BitMovimientosTramiteDTO bitacora = new BitMovimientosTramiteDTO();
		bitacora.setCatTiposMovimientoDTO(new CatTiposMovimientoDTO(Constantes.ID_REGISTRO_TRAMITE));
		bitacora.setUsuarioDTO(tramite.getUsuario());
		bitacora.setIdTramite(tramite.getIdTramite());
		bitacora.setComentarios("Registro de trámite.");
		bitacora.setFechaMovimiento(new Date());
		
		bitMovimientosTramiteDAO.actualizar(bitacora);
		
		return tramite.getIdTramite();
	}	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrarObservacionesSeccion(TramiteDTO tramite, SeccionesFormularioDTO seccion, String nombreTabla) {

		//1. Se registran observaciones de la sección		
		formularioDAO.actualizaObservacionesSeccion(tramite, seccion, nombreTabla);
				
		//2. Se registra movimiento en bitácora.
		BitMovimientosTramiteDTO bitacora = new BitMovimientosTramiteDTO();
		bitacora.setCatTiposMovimientoDTO(new CatTiposMovimientoDTO(Constantes.ID_REGISTRO_OBSERVACIONES));
		bitacora.setUsuarioDTO(tramite.getUsuarioRevisor());
		bitacora.setIdTramite(tramite.getIdTramite());
		bitacora.setComentarios("Registro de observaciones de la sección : " + seccion.getIdSeccionFormulario() + 
				(seccion.isContieneObservaciones() ? " Observaciones: " + seccion.getObservaciones() : Constantes.EMPTY_STRING));
		bitacora.setFechaMovimiento(new Date());
		
		bitMovimientosTramiteDAO.actualizar(bitacora);		
	}	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void notificarRegistroTramite(TramiteDTO tramite) {
		//1. Se registra realiza la actualización de estatus de la solicitud.
		formularioDAO.generarActualizacionTramite(tramite);
		
		//2. Se envía notificación de registro de Solicitud (Solo se intenta si el usuario cuenta con un correo)
		DetGestionUsuarioDTO detGestionUsuarioDTO = detGestionUsuariosDAO.buscarPorIdProyecto(tramite.getProyectoDTO().getIdProyecto());
				
		if(BeanUtils.isNotNull(tramite.getUsuario()) && BeanUtils.isNotNull(tramite.getUsuario().getCorreo())
				&& BeanUtils.isNotNull(detGestionUsuarioDTO)) {		
			
				try {
					EnvioCorreo correo = new EnvioCorreo();
					DetSecurityDomainDTO detSecurityDomainDTO = detSecurityDomainDAO.buscarPorIdProyecto(tramite.getProyectoDTO().getIdProyecto(), Constantes.ID_TIPO_SECURITY_DOMAIN_CLIENTE);
					ProyectoDTO proyectoDTO = proyectoDAO.buscarPorId(tramite.getProyectoDTO().getIdProyecto());
					//Si el proyecto está marcado como aviso, el trámite pasa como aprobado directamente y se envia el correo asociado a un trámite aprobado
					
					if(proyectoDTO.isAviso()) {
						correo.enviarCorreoNotificacion(tramite.getUsuario().getCorreo(), 
								detGestionUsuarioDTO.getCorreoRegistrado(), 
								Constantes.TITULO_EXPEDIDO, 
								tramite.getProyectoDTO().getNombreProyecto(), 
								tramite, 
								detSecurityDomainDTO.getUrlSistema(),
								detGestionUsuarioDTO, proyectoDTO);
					} else {
						correo.enviarCorreoNotificacion(tramite.getUsuario().getCorreo(), detGestionUsuarioDTO.getCorreoRegistrado(), Constantes.TITULO_REGISTRO_TRAMITE, tramite.getProyectoDTO().getNombreProyecto(), tramite, detSecurityDomainDTO.getUrlSistema(),
								detGestionUsuarioDTO, proyectoDTO);
					}
				} catch (Exception e) {
					LOGGER.error("Ocurrió un error al notificar por correo el trámite con FOLIO:: " + tramite.getFolioSeguimiento() + " ", e);
				}
		}
		
		//3. Se registra movimiento en bitácora
		BitMovimientosTramiteDTO bitacora = new BitMovimientosTramiteDTO();
		bitacora.setCatTiposMovimientoDTO(new CatTiposMovimientoDTO(Constantes.ID_ACTUALIZACION_ESTATUS));
		bitacora.setUsuarioDTO(tramite.getUsuario());
		bitacora.setIdTramite(tramite.getIdTramite());
		bitacora.setComentarios(SE_ACTUALIZA_A + tramite.getCatEstatusTramiteDTO().getIdEstatusTramite());
		bitacora.setFechaMovimiento(new Date());
		
		bitMovimientosTramiteDAO.actualizar(bitacora);		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarEstatusTramite(TramiteDTO tramite) {

		//1. Se actualiza estatus al trámite		
		formularioDAO.actualizarEstatusTramite(tramite);
				
		//2. Se registra movimiento en bitácora.
		BitMovimientosTramiteDTO bitacora = new BitMovimientosTramiteDTO();
		bitacora.setCatTiposMovimientoDTO(new CatTiposMovimientoDTO(Constantes.ID_ACTUALIZACION_ESTATUS));
		bitacora.setUsuarioDTO(tramite.getUsuario());
		bitacora.setIdTramite(tramite.getIdTramite());
		bitacora.setComentarios(SE_ACTUALIZA_A + tramite.getCatEstatusTramiteDTO().getIdEstatusTramite());
		bitacora.setFechaMovimiento(new Date());
		
		bitMovimientosTramiteDAO.actualizar(bitacora);		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void moverArchivoRutaFinal(File fileArchivoTemp, String rutaArchivoFinal, String nombreArchivoOrigen) {
		try(FileInputStream in = new FileInputStream(fileArchivoTemp);
				FileOutputStream out = new FileOutputStream(new File(rutaArchivoFinal.concat(nombreArchivoOrigen)))){						 

		        int read = Constantes.INT_VALOR_CERO;
		    	byte[] bytes = new byte[Constantes.TAMAÑO_BUFFER];
		    	while ((read = in.read(bytes)) != -1) {
		    		out.write(bytes, Constantes.INT_VALOR_CERO, read);
		    	}	
		    	
			} catch (IOException e) {
				LOGGER.error("No se pudo guardar el archivo:: " + fileArchivoTemp.getName() + " en el filesystem ", e);
				/*
				 * Recordar que cualquier unchecked exception que ocurra dentro de una transacción provoca que 
				 * se haga un rollback a toda la transacción, por lo tanto, de esta manera evitamos que existan 
				 * archivos en el FileSystem final sin asociar a un registro en BD.
				 */
				throw new IllegalStateException(
						"No se pudo mover el archivo de la carpeta temporal. Se realiza rollback en el registro de la sección actual del formulario.");
			}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrarInformacionSeccion(TramiteDTO tramite, Long idSeccion, String nombreTabla, List<String> lstColumnas, 
			Map<String, Object> mapRespuestas, Map<String, ControlComponentesDTO> mapControlComponentes) {
		
		/** 1. Se revisa el si las respuestas corresponden a componentes de carga de archivos, si es el caso, se copiará el archivo
		 * 	   de la ruta temporal en donde fue cargado en la captura del formulario a la ruta final en la que quedará en el server
		 *     también se actualiza el dato del Map de respuestas para que al generar el insert lleve la ruta final, no la temporal. **/		
		for(String columnaActual: lstColumnas) {
			if(BeanUtils.isNotNull(mapControlComponentes.get(columnaActual))) {
				
				/**Se revisa si la respuesta actual es de un componente de Carga de archivos**/
				if(mapControlComponentes.get(columnaActual).getIdTipoComponente() == Constantes.ID_COMPONENTE_CARGA_DOCUMENTOS) {
					if(BeanUtils.isNotNull(mapRespuestas.get(columnaActual)) 
							&& BeanUtils.isNotEmpty(mapRespuestas.get(columnaActual).toString())) {
						
						List<DatosDocumentoJsonDTO> lstNuevoPathArchivos = new ArrayList<>();
						
						/** Se obtiene la respuesta que registró el componente en el MAP de respuestas, se tiene que convertir de Json a 
						 *  listado de ComponenteCargaDocumentosDTO, para obtener por separado las rutas que pudo cargar en el componente
						 *  recordar que es configurable el componente para permitir adjuntar más de un archivo por componente**/
						java.lang.reflect.Type tipoLista = new TypeToken<ArrayList<DatosDocumentoJsonDTO>>() {}.getType();
						List<DatosDocumentoJsonDTO> lstPathsArchivos = new Gson().fromJson(mapRespuestas.get(columnaActual).toString(), tipoLista);
						
						/** Inicia iteración para copiar los archivos de la ruta temporal, a la ruta definitiva que será registrada en BD**/
						for(DatosDocumentoJsonDTO datosDocumento : lstPathsArchivos) {	
							String rutaArchivoFinal = null;
							try {
								File fileArchivoTemp = new File(datosDocumento.getRuta());						
								String nombreArchivoOrigen = fileArchivoTemp.getName();
	
								rutaArchivoFinal = Environment.getPathClienteDocumentos().concat(tramite.getProyectoDTO().getIdProyecto() + Constantes.SEPARADOR_RUTA);
	
								File carpetaDocumentos = new File(rutaArchivoFinal);													
								if (!carpetaDocumentos.exists()) {
									carpetaDocumentos.mkdirs();
								}
								datosDocumento.setRuta(rutaArchivoFinal.concat(nombreArchivoOrigen));
								lstNuevoPathArchivos.add(datosDocumento);
								
								rutaArchivoFinal = carpetaDocumentos.getPath() + Constantes.SEPARADOR_RUTA;
								
								this.moverArchivoRutaFinal(fileArchivoTemp, rutaArchivoFinal, nombreArchivoOrigen);							
								//Se elimina el archivo de la carpeta temporal					    	
								Files.deleteIfExists(fileArchivoTemp.toPath());
							} catch (IOException e) {
								LOGGER.error("Ocurrió un error al consultar eliminar documento temporal: ", e);
							}
						}							
						/** Se actualiza el map de respuestas con las nuevas rutas de los archivos copiados para su inserción en BD**/					
						mapRespuestas.put(columnaActual, new Gson().toJson(lstNuevoPathArchivos));	
						
					}									
				}
				
				/**Se revisa si la respuesta actual es de un componente inputText, se aplica UPPERCASE a respuesta del componente**/
				if(mapControlComponentes.get(columnaActual).getIdTipoComponente() == Constantes.ID_COMPONENTE_CAMPO_TEXTO) {
					if(BeanUtils.isNotNull(mapRespuestas.get(columnaActual)) 
							&& BeanUtils.isNotEmpty(mapRespuestas.get(columnaActual).toString())) {						
						
						//Se valida si el componente pertenece a un campo de texto con validador de URL no se realiza UPPERCASE
						try {
							ComponenteCampoTextoDTO campoTextoTmp = detalleFormularioDAO.consultarDetalleCampoTextoIdComponente(mapControlComponentes.get(columnaActual).getIdComponente());
							
							//Si no tiene validador, se realiza UPPERCASE
							if(BeanUtils.isNull(campoTextoTmp.getCatValidadoresDTO())) {
								Object respuestaObjUpper = (mapRespuestas.get(columnaActual).toString()).toUpperCase();
								mapRespuestas.put(columnaActual, respuestaObjUpper);
							} else {
								//Si el validador es distinto al de CORREO y URL, se realiza UPPERCASE
								if(campoTextoTmp.getCatValidadoresDTO().getIdValidador() != Constantes.ID_VALIDADOR_CORREO &&
										campoTextoTmp.getCatValidadoresDTO().getIdValidador() != Constantes.ID_VALIDADOR_URL) {
									Object respuestaObjUpper = (mapRespuestas.get(columnaActual).toString()).toUpperCase();
									mapRespuestas.put(columnaActual, respuestaObjUpper);
								}									
							}							
						} catch (Exception e) {
							LOGGER.error("Ocurrió un error al consultar el detalle del componente campo de texto: ", e);
						}						
					} 					
				}	
				
				/**Se revisa si la respuesta actual es de un componente Datos de domicilio, se aplica UPPERCASE a respuestas del componente
				 * en los campos de Calle, Número exterior y Número interior.**/
				if(mapControlComponentes.get(columnaActual).getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_DOMICILIO) {
					if((Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_1")).equals(columnaActual)
							|| (Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_2")).equals(columnaActual)
							|| (Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_3")).equals(columnaActual)) {						
						if(BeanUtils.isNotNull(mapRespuestas.get(columnaActual)) 
								&& BeanUtils.isNotEmpty(mapRespuestas.get(columnaActual).toString())) {						
							Object respuestaObjUpper = (mapRespuestas.get(columnaActual).toString()).toUpperCase();
							mapRespuestas.put(columnaActual, respuestaObjUpper);						
						} 	
					}										
				}	
				
				/**Se revisa si la respuesta actual es de un componente Datos personales sin llave, se aplica UPPERCASE a respuestas del componente
				 * en los campos Curp, Nombre(s), Primer apellido, Segundo apellido**/
				if(mapControlComponentes.get(columnaActual).getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONALES_SIN_LLAVE) {
					if((Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_1")).equals(columnaActual)
							|| (Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_2")).equals(columnaActual)
							|| (Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_3")).equals(columnaActual)
							|| (Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_4")).equals(columnaActual)) {						
						if(BeanUtils.isNotNull(mapRespuestas.get(columnaActual)) 
								&& BeanUtils.isNotEmpty(mapRespuestas.get(columnaActual).toString())) {						
							Object respuestaObjUpper = (mapRespuestas.get(columnaActual).toString()).toUpperCase();
							mapRespuestas.put(columnaActual, respuestaObjUpper);						
						} 	
					}										
				}	
				
			} 
		}	

		/** 2. Se realiza la inserción de información de la sección en la BD **/
		formularioDAO.generarSentenciaRegistro(tramite, idSeccion, nombreTabla, lstColumnas, mapRespuestas);
				
		/** 3. Se registra el movimiento en la bitácora **/
		BitMovimientosTramiteDTO bitacora = new BitMovimientosTramiteDTO();
		bitacora.setCatTiposMovimientoDTO(new CatTiposMovimientoDTO(Constantes.ID_REGISTRO_DATOS_SECCION));
		bitacora.setUsuarioDTO(tramite.getUsuario());
		bitacora.setIdTramite(tramite.getIdTramite());
		bitacora.setComentarios("Se registra información de la sección : " + idSeccion);
		bitacora.setFechaMovimiento(new Date());
		
		bitMovimientosTramiteDAO.actualizar(bitacora);		
	}	
	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarInformacionSeccion(TramiteDTO tramite, Long idSeccion, String nombreTabla, List<String> lstColumnas, 
			Map<String, Object> mapRespuestas, Map<String, ControlComponentesDTO> mapControlComponentes) {
		
		/** 1. Se revisa el si las respuestas corresponden a componentes de carga de archivos, si es el caso, se copiará el archivo
		 * 	   de la ruta temporal en donde fue cargado en la captura del formulario a la ruta final en la que quedará en el server
		 *     también se actualiza el dato del Map de respuestas para que al generar el insert lleve la ruta final, no la temporal. **/		
		for(String columnaActual: lstColumnas) {
			if(BeanUtils.isNotNull(mapControlComponentes.get(columnaActual))) {

				/**Se revisa si la respuesta actual es de un componente de Carga de archivos**/
				if(mapControlComponentes.get(columnaActual).getIdTipoComponente() == Constantes.ID_COMPONENTE_CARGA_DOCUMENTOS) {

					if(BeanUtils.isNotNull(mapRespuestas.get(columnaActual)) 
							&& BeanUtils.isNotEmpty(mapRespuestas.get(columnaActual).toString())) {

						List<DatosDocumentoJsonDTO> lstNuevoPathArchivos = new ArrayList<>();
						/** Se obtiene la respuesta que registró el componente en el MAP de respuestas, se tiene que convertir de Json a 
						 *  listado de ComponenteCargaDocumentosDTO, para obtener por separado las rutas que pudo cargar en el componente
						 *  recordar que es configurable el componente para permitir adjuntar más de un archivo por componente**/
						java.lang.reflect.Type tipoLista = new TypeToken<ArrayList<DatosDocumentoJsonDTO>>() {}.getType();
						List<DatosDocumentoJsonDTO> lstPathsArchivos = new Gson().fromJson(mapRespuestas.get(columnaActual).toString(), tipoLista);

						/** Inicia iteración para copiar los archivos de la ruta temporal, a la ruta definitiva que será registrada en BD**/
						for(DatosDocumentoJsonDTO datosDocumento : lstPathsArchivos) {
							String rutaArchivoFinal = null;

							/** Si la ruta actual del archivo no contiene el path de temporal, quiere decir que está realizando
							 *  una actualización de su registro, pero no actualizó el documento, por lo tanto no debe realizarse
							 *  el copiado de la carpeta temporal **/
							if(!datosDocumento.getRuta().contains(Environment.getPathClienteDocumentos())) {

								try {
									File fileArchivoTemp = new File(datosDocumento.getRuta());						
									String nombreArchivoOrigen = fileArchivoTemp.getName();

									rutaArchivoFinal = Environment.getPathClienteDocumentos().concat(tramite.getProyectoDTO().getIdProyecto() + Constantes.SEPARADOR_RUTA);

									File carpetaDocumentos = new File(rutaArchivoFinal);													
									if (!carpetaDocumentos.exists()) {
										carpetaDocumentos.mkdirs();
									}
									datosDocumento.setRuta(rutaArchivoFinal.concat(nombreArchivoOrigen));									
									rutaArchivoFinal = carpetaDocumentos.getPath() + Constantes.SEPARADOR_RUTA;

									this.moverArchivoRutaFinal(fileArchivoTemp, rutaArchivoFinal, nombreArchivoOrigen);	

									//Se elimina el archivo de la carpeta temporal					    	
									Files.deleteIfExists(fileArchivoTemp.toPath());
								} catch (IOException e) {
									LOGGER.error("Ocurrió un error al consultar eliminar documento temporal: ", e);
								}
							}
							lstNuevoPathArchivos.add(datosDocumento);
							/** Se actualiza el map de respuestas con las nuevas rutas de los archivos copiados para su inserción en BD**/					
							mapRespuestas.put(columnaActual, new Gson().toJson(lstNuevoPathArchivos));
						}
					}

					/**Se revisa si la respuesta actual es de un componente inputText, se aplica UPPERCASE a respuesta del componente**/
					if(mapControlComponentes.get(columnaActual).getIdTipoComponente() == Constantes.ID_COMPONENTE_CAMPO_TEXTO) {
						if(BeanUtils.isNotNull(mapRespuestas.get(columnaActual)) 
								&& BeanUtils.isNotEmpty(mapRespuestas.get(columnaActual).toString())) {		

							//Se valida si el componente pertenece a un campo de texto con validador de URL no se realiza UPPERCASE
							try {
								ComponenteCampoTextoDTO campoTextoTmp = detalleFormularioDAO.consultarDetalleCampoTextoIdComponente(mapControlComponentes.get(columnaActual).getIdComponente());

								//Si no tiene validador, se realiza UPPERCASE
								if(BeanUtils.isNull(campoTextoTmp.getCatValidadoresDTO())) {
									Object respuestaObjUpper = (mapRespuestas.get(columnaActual).toString()).toUpperCase();
									mapRespuestas.put(columnaActual, respuestaObjUpper);
								} else {
									//Si el validador es distinto al de CORREO y URL, se realiza UPPERCASE
									if(campoTextoTmp.getCatValidadoresDTO().getIdValidador() != Constantes.ID_VALIDADOR_CORREO && 
											campoTextoTmp.getCatValidadoresDTO().getIdValidador() != Constantes.ID_VALIDADOR_URL) {
										Object respuestaObjUpper = (mapRespuestas.get(columnaActual).toString()).toUpperCase();
										mapRespuestas.put(columnaActual, respuestaObjUpper);
									}									
								}			
							} catch (Exception e) {
								LOGGER.error("Ocurrió un error al consultar el detalle del componente campo de texto: ", e);
							}						
						} 					
					}	

					/**Se revisa si la respuesta actual es de un componente Datos de domicilio, se aplica UPPERCASE a respuestas del componente
					 * en los campos de Calle, Número exterior y Número interior.**/
					if(mapControlComponentes.get(columnaActual).getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_DOMICILIO) {
						if((Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_1")).equals(columnaActual)
								|| (Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_2")).equals(columnaActual)
								|| (Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_3")).equals(columnaActual)) {						
							if(BeanUtils.isNotNull(mapRespuestas.get(columnaActual)) 
									&& BeanUtils.isNotEmpty(mapRespuestas.get(columnaActual).toString())) {						
								Object respuestaObjUpper = (mapRespuestas.get(columnaActual).toString()).toUpperCase();
								mapRespuestas.put(columnaActual, respuestaObjUpper);						
							} 	
						}										
					}	

					/**Se revisa si la respuesta actual es de un componente Datos personales sin llave, se aplica UPPERCASE a respuestas del componente
					 * en los campos Curp, Nombre(s), Primer apellido, Segundo apellido**/
					if(mapControlComponentes.get(columnaActual).getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONALES_SIN_LLAVE) {
						if((Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_1")).equals(columnaActual)
								|| (Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_2")).equals(columnaActual)
								|| (Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_3")).equals(columnaActual)
								|| (Constantes.NOMBRE_BASE_COLUMNAS.concat(mapControlComponentes.get(columnaActual).getIdComponente()+"_4")).equals(columnaActual)) {						
							if(BeanUtils.isNotNull(mapRespuestas.get(columnaActual)) 
									&& BeanUtils.isNotEmpty(mapRespuestas.get(columnaActual).toString())) {						
								Object respuestaObjUpper = (mapRespuestas.get(columnaActual).toString()).toUpperCase();
								mapRespuestas.put(columnaActual, respuestaObjUpper);						
							} 	
						}										
					}	

				} 
			}
		}
	
		/** 2. Se realiza la actualización de información de la sección en la BD **/
		formularioDAO.generarSentenciaActualizacion(tramite, nombreTabla, lstColumnas, mapRespuestas);
				
		/** 3. Se registra el movimiento en la bitácora **/
		BitMovimientosTramiteDTO bitacora = new BitMovimientosTramiteDTO();
		bitacora.setCatTiposMovimientoDTO(new CatTiposMovimientoDTO(Constantes.ID_ACTUALIZACION_DATOS_SECCION));
		bitacora.setUsuarioDTO(tramite.getUsuario());
		bitacora.setIdTramite(tramite.getIdTramite());
		bitacora.setComentarios("Se actualiza información de la sección : " + idSeccion);
		bitacora.setFechaMovimiento(new Date());
		
		bitMovimientosTramiteDAO.actualizar(bitacora);		
	}	
					
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void notificarPrevencionVencidaTramite(TramiteDTO tramite, String nombreRemitente) {

		//1. Se realiza la actualización de estatus de la solicitud debido al termino del tiempo de prevención
		formularioDAO.actualizarRevisionTramite(tramite);
		
		//Se registra en Bitacora el vencimiento de la Revisión
		BitMovimientosTramiteDTO bitacora = new BitMovimientosTramiteDTO();
		bitacora.setCatTiposMovimientoDTO(new CatTiposMovimientoDTO(Constantes.ID_ACTUALIZACION_ESTATUS));
		bitacora.setUsuarioDTO(new UsuarioDTO(Constantes.ID_USUARIO_SCHEDULE_PREVENCION));
		bitacora.setIdTramite(tramite.getIdTramite());
		bitacora.setComentarios("Se actualiza estatus a : " + tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() + 
					(tramite.getRespuestaFolioConclusion() != null ? ". Observaciones: " + tramite.getRespuestaFolioConclusion() : Constantes.EMPTY_STRING));	
		bitacora.setFechaMovimiento(new Date());
				
		bitMovimientosTramiteDAO.actualizar(bitacora);		

		//2. Se envía notificación de vencimiento del tiempo de prevención (Solo si cuenta con un correo)
		DetGestionUsuarioDTO detGestionUsuarioDTO = detGestionUsuariosDAO.buscarPorIdProyecto(tramite.getProyectoDTO().getIdProyecto());
		if(tramite.getUsuario() != null && tramite.getUsuario().getCorreo() != null
				&& BeanUtils.isNotNull(detGestionUsuarioDTO)) {
				try {					
					EnvioCorreo correo = new EnvioCorreo();
					DetSecurityDomainDTO detSecurityDomainDTO = detSecurityDomainDAO.buscarPorIdProyecto(tramite.getProyectoDTO().getIdProyecto(), Constantes.ID_TIPO_SECURITY_DOMAIN_CLIENTE);
					ProyectoDTO proyectoDTO = proyectoDAO.buscarPorId(tramite.getProyectoDTO().getIdProyecto());
					correo.enviarCorreoNotificacion(
							tramite.getUsuario().getCorreo(), 
							detGestionUsuarioDTO.getCorreoSubsanarPrevencion(), 
							Constantes.TITULO_NO_SUBSANAR_TRAMITE, 
							nombreRemitente, 
							tramite,
							detSecurityDomainDTO.getUrlSistema(), detGestionUsuarioDTO, proyectoDTO);
				} catch (Exception e) {
					LOGGER.error("Ocurrió un error al notificar por correo el trámite:: " + tramite.getFolioSeguimiento() + " ", e);
				}
			}		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void notificarRevisionTramite(TramiteDTO tramite, String nombreRemitente, SeccionesFormularioDTO seccionActual, 
			String nombreTabla, boolean isSeccionInformativa, ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) {
		Predicate<CatEstatusTramiteDTO> prEstatusApro = p -> p.getIdEstatusTramite()==Constantes.ID_ESTATUS_APROBADO;
		Predicate<CatEstatusTramiteDTO> prEstatusRecha= p -> p.getIdEstatusTramite()==Constantes.ID_ESTATUS_RECHAZADO;
		//1. Se realiza la actualización de estatus de la solicitud debido al termino del tiempo de prevención
		formularioDAO.actualizarRevisionTramite(tramite);
		
		//2. Se registran si existen o no observaciones en la última sección únicamente si no es sección de componentes informativos.
		if(isSeccionInformativa == false) {
			formularioDAO.generarSentenciaRevisionSecciones(tramite, seccionActual, Constantes.NOMBRE_BASE_TABLAS.concat(seccionActual.getIdSeccionFormulario().toString()));	
		}
		
		//3.-. Se envía notificación de vencimiento del tiempo de prevención.
		DetGestionUsuarioDTO detGestionUsuarioDTO = detGestionUsuariosDAO.buscarPorIdProyecto(tramite.getProyectoDTO().getIdProyecto());
		if(tramite.getUsuario() != null && tramite.getUsuario().getCorreo() != null 
				&& BeanUtils.isNotNull(detGestionUsuarioDTO)) {			
				try {					
					EnvioCorreo correo = new EnvioCorreo();
					DetSecurityDomainDTO detSecurityDomainDTO = detSecurityDomainDAO.buscarPorIdProyecto(tramite.getProyectoDTO().getIdProyecto(), Constantes.ID_TIPO_SECURITY_DOMAIN_CLIENTE);
					ProyectoDTO proyectoDTO = proyectoDAO.buscarPorId(tramite.getProyectoDTO().getIdProyecto());
					correo.enviarCorreoNotificacion(
							tramite.getUsuario().getCorreo(), 
							obtenerCuerpoCorreo(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite(), detGestionUsuarioDTO),
							obtenerAsuntoCorreo(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite()), 
							nombreRemitente, 
							tramite,
							detSecurityDomainDTO.getUrlSistema(), detGestionUsuarioDTO, proyectoDTO);
				} catch (Exception e) {
					LOGGER.error("Ocurrió un error al notificar por correo el trámite:: " + tramite.getFolioSeguimiento() + " ", e);
				}
		} 
		
		//4. Se registra movimiento en bitácora.
		BitMovimientosTramiteDTO bitacora = new BitMovimientosTramiteDTO();
		bitacora.setCatTiposMovimientoDTO(new CatTiposMovimientoDTO(Constantes.ID_ACTUALIZACION_ESTATUS));
		bitacora.setUsuarioDTO(tramite.getUsuarioRevisor());
		bitacora.setIdTramite(tramite.getIdTramite());
		if(prEstatusApro.or(prEstatusRecha).test(tramite.getCatEstatusTramiteDTO())) {
			bitacora.setComentarios(SE_ACTUALIZA_A + tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() + 
					(tramite.getRespuestaFolioConclusion() != null ? COMENTARIO_OBSERVACIONES + tramite.getRespuestaFolioConclusion() : Constantes.EMPTY_STRING));	
		} else {
			bitacora.setComentarios(SE_ACTUALIZA_A + tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() + 
					(tramite.getRespuestaFolioPrevencion() != null ? COMENTARIO_OBSERVACIONES + tramite.getRespuestaFolioPrevencion() : Constantes.EMPTY_STRING));	
		}
		bitacora.setFechaMovimiento(new Date());
				
		bitMovimientosTramiteDAO.actualizar(bitacora);		
		
		//5.- Se revisa si el trámite fue firmado para el registro de información de firma.
		if(BeanUtils.isNotNull(respuestaConsultaFirma) && 
				(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO 
				|| tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO)) {
			TramiteFirmaElectronicaDTO tramiteFirma = new TramiteFirmaElectronicaDTO();
			tramiteFirma.setIdTramiteFirma(formularioDAO.consultarConsecutivoTramitesFirma());
			tramiteFirma.setCadenaOriginal(respuestaConsultaFirma.getLstCadenaDigitales().get(0).getCadena());
			tramiteFirma.setCadenaFirmada(respuestaConsultaFirma.getLstCadenaDigitales().get(0).getSelloDigitalFirma());
			tramiteFirma.setNombreFirmante(respuestaConsultaFirma.getNombreFirmante());
			tramiteFirma.setFechaCreacion(new Date());
			tramiteFirma.setRespuestaServicio(respuestaConsultaFirma.getRespuestaServicio());
			tramiteFirma.setTramiteDTO(tramite);
			
			formularioDAO.registrarFirmaTramite(tramiteFirma);
		}		
	}
		
	/**
	 * Método que realiza el registro de una resolución del trámites.
	 * @param tramite
	 * @param nombreRemitente
	 * @param seccionActual
	 * @param nombreTabla
	 * @param isSeccionInformativa
	 * @param respuestaConsultaFirma
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void notificarResolucionTramite(TramiteDTO tramite, String nombreRemitente, SeccionesFormularioDTO seccionActual, 
			String nombreTabla, boolean isSeccionInformativa, ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) {

		//1. Se realiza la actualización de la resolución
		formularioDAO.actualizaResolucionTramite(tramite);
		
		//2.-. Se envía notificación de la resolución del trámite.
		DetGestionUsuarioDTO detGestionUsuarioDTO = detGestionUsuariosDAO.buscarPorIdProyecto(tramite.getProyectoDTO().getIdProyecto());
		if(tramite.getUsuario() != null && tramite.getUsuario().getCorreo() != null 
				&& BeanUtils.isNotNull(detGestionUsuarioDTO)) {			
				try {					
					EnvioCorreo correo = new EnvioCorreo();
					DetSecurityDomainDTO detSecurityDomainDTO = detSecurityDomainDAO.buscarPorIdProyecto(tramite.getProyectoDTO().getIdProyecto(), Constantes.ID_TIPO_SECURITY_DOMAIN_CLIENTE);
					ProyectoDTO proyectoDTO = proyectoDAO.buscarPorId(tramite.getProyectoDTO().getIdProyecto());
					correo.enviarCorreoNotificacion(
							tramite.getUsuario().getCorreo(), 
							obtenerCuerpoCorreo(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite(), detGestionUsuarioDTO),
							obtenerAsuntoCorreo(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite()), 
							nombreRemitente, 
							tramite,
							detSecurityDomainDTO.getUrlSistema(), detGestionUsuarioDTO, proyectoDTO);
				} catch (Exception e) {
					LOGGER.error("Ocurrió un error al notificar por correo el trámite para notificación resolución :: " + tramite.getFolioSeguimiento() + " ", e);
				}
		} 
		
		//3. Se registra movimiento en bitácora.
		BitMovimientosTramiteDTO bitacora = new BitMovimientosTramiteDTO();
		bitacora.setCatTiposMovimientoDTO(new CatTiposMovimientoDTO(Constantes.ID_ACTUALIZACION_ESTATUS));
		bitacora.setUsuarioDTO(tramite.getUsuarioRevisor());
		bitacora.setIdTramite(tramite.getIdTramite());
		bitacora.setComentarios(SE_ACTUALIZA_A + tramite.getCatEstatusTramiteDTO().getIdEstatusTramite());	
		bitacora.setFechaMovimiento(new Date());
				
		bitMovimientosTramiteDAO.actualizar(bitacora);
	}
	
	/**
	 * Método privado auxiliar para obtener el asunto que se enviará en el correo
	 * correspondiente al estatus del trámite
	 * @param idEstatus
	 * @return
	 */
	private String obtenerAsuntoCorreo(Integer idEstatus) {
		String asuntoCorreo = null;
		if(idEstatus != null && idEstatus != 0) {
			if(idEstatus == Constantes.ID_ESTATUS_APROBADO) {
				asuntoCorreo = Constantes.TITULO_CONCLUSION_TRAMITE;
			} else if(idEstatus == Constantes.ID_ESTATUS_CORRECIONES) {
				asuntoCorreo = Constantes.TITULO_PREVENCION_TRAMITE;
			} else if(idEstatus == Constantes.ID_ESTATUS_RECHAZADO) {
				asuntoCorreo = Constantes.TITULO_RECHAZO_TRAMITE;
			}else if(idEstatus == Constantes.ID_ESTATUS_CONCLUSION_POSITIVA) {
				asuntoCorreo = Constantes.TITULO_CONCLUSION_POSITIVA;
			}else if(idEstatus == Constantes.ID_ESTATUS_CONCLUSION_NEGATIVA) {
				asuntoCorreo = Constantes.TITULO_CONCLUSION_NEGATIVA;
			}
		}
		return asuntoCorreo;
	}
	
	/**
	 * Método auxiliar que obtiene el texto configurado en la sección de Gestión del usuario en la que se personaliza el texto a enviar por correo dependiendo
	 * del estatus del trámite.
	 * 
	 * @param idEstatus
	 * @param detGestionUsuarioDTO
	 * @return
	 */
	private String obtenerCuerpoCorreo(Integer idEstatus, DetGestionUsuarioDTO detGestionUsuarioDTO) {
		String cuerpoCorroe = null;
		if(idEstatus != null && idEstatus != 0) {
			switch (idEstatus) {
			case Constantes.ID_ESTATUS_APROBADO:
				cuerpoCorroe = detGestionUsuarioDTO.getCorreoConclusion();
				break;
			case Constantes.ID_ESTATUS_CORRECIONES:
				cuerpoCorroe = detGestionUsuarioDTO.getCorreoPrevencion();
				break;
			case Constantes.ID_ESTATUS_RECHAZADO:
				cuerpoCorroe = detGestionUsuarioDTO.getCorreoRechazado();
				break;
			case Constantes.ID_ESTATUS_CONCLUSION_POSITIVA:
				cuerpoCorroe = detGestionUsuarioDTO.getCorreoResolucionPositiva();
				break;
			case Constantes.ID_ESTATUS_CONCLUSION_NEGATIVA:
				cuerpoCorroe = detGestionUsuarioDTO.getCorreoResolucionNegativa();
				break;
			default:
				cuerpoCorroe = detGestionUsuarioDTO.getCorreoRegistrado();
				break;
			}
		}
		return cuerpoCorroe;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizacionTramiteRevisadoOperador(TramiteDTO tramite, SeccionesFormularioDTO seccionActual, boolean isSeccionInformativa) {

		//1. Se realiza la actualización de estatus de la solicitud debido al termino del tiempo de prevención
		formularioDAO.actualizarRevisionTramite(tramite);
		
		//2. Se registran si existen o no observaciones en la última sección únicamente si no es sección de componentes informativos.
		if(isSeccionInformativa == false) {
			formularioDAO.generarSentenciaRevisionSecciones(tramite, seccionActual, Constantes.NOMBRE_BASE_TABLAS.concat(seccionActual.getIdSeccionFormulario().toString()));	
		}
				
		//3. Se registra movimiento en bitácora.
		BitMovimientosTramiteDTO bitacora = new BitMovimientosTramiteDTO();
		bitacora.setCatTiposMovimientoDTO(new CatTiposMovimientoDTO(Constantes.ID_ACTUALIZACION_ESTATUS));
		bitacora.setUsuarioDTO(tramite.getUsuarioRevisor());
		bitacora.setIdTramite(tramite.getIdTramite());
		if(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO) {
			bitacora.setComentarios(SE_ACTUALIZA_A + tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() + 
				(tramite.getRespuestaFolioConclusion() != null ? COMENTARIO_OBSERVACIONES + tramite.getRespuestaFolioConclusion() : Constantes.EMPTY_STRING));	
		} else {
			bitacora.setComentarios(SE_ACTUALIZA_A + tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() + 
				(tramite.getRespuestaFolioPrevencion() != null ? COMENTARIO_OBSERVACIONES + tramite.getRespuestaFolioPrevencion() : Constantes.EMPTY_STRING));	
		}
		bitacora.setFechaMovimiento(new Date());
				
		bitMovimientosTramiteDAO.actualizar(bitacora);		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrarFirmaTramite(TramiteDTO tramite, ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) {
		//1.- Se obtiene el primer del listado de Cadenas digitales para obtener a información de la firma
		TramiteFirmaElectronicaDTO tramiteFirma = new TramiteFirmaElectronicaDTO();
		tramiteFirma.setIdTramiteFirma(formularioDAO.consultarConsecutivoTramitesFirma());
		tramiteFirma.setCadenaOriginal(respuestaConsultaFirma.getLstCadenaDigitales().get(0).getCadena());
		tramiteFirma.setCadenaFirmada(respuestaConsultaFirma.getLstCadenaDigitales().get(0).getSelloDigitalFirma());
		tramiteFirma.setNombreFirmante(respuestaConsultaFirma.getNombreFirmante());
		tramiteFirma.setFechaCreacion(new Date());
		tramiteFirma.setRespuestaServicio(respuestaConsultaFirma.getRespuestaServicio());
		tramiteFirma.setFirmaCiudadano(respuestaConsultaFirma.isFirmaCiudadano());
		tramiteFirma.setTramiteDTO(tramite);
		
		formularioDAO.registrarFirmaTramite(tramiteFirma);		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrarFirmadoTramites(ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) {
		//Se itera la respuesta de cadenas dígitales para obtener la firma
		for(CadenasDigitalesDTO cadenaDigitalActual :respuestaConsultaFirma.getLstCadenaDigitales()) {
			TramiteFirmaElectronicaDTO tramiteFirma = new TramiteFirmaElectronicaDTO();
			tramiteFirma.setIdTramiteFirma(formularioDAO.consultarConsecutivoTramitesFirma());
			tramiteFirma.setCadenaOriginal(cadenaDigitalActual.getCadena());
			tramiteFirma.setCadenaFirmada(cadenaDigitalActual.getSelloDigitalFirma());		
			tramiteFirma.setNombreFirmante(respuestaConsultaFirma.getNombreFirmante());
			tramiteFirma.setFechaCreacion(new Date());
			tramiteFirma.setTramiteDTO(new TramiteDTO(Long.parseLong(obtenerIdTramite(cadenaDigitalActual.getCadena()))));
			tramiteFirma.setRespuestaServicio(respuestaConsultaFirma.getRespuestaServicio());
			
			formularioDAO.registrarFirmaTramite(tramiteFirma);	
		}
	}
	
	/**
	 * Método auxiliar que obtiene el Id del trámite de la cadena 
	 * @return
	 */
	private String obtenerIdTramite(String cadenaFirmada) {
		String[] elementosCadenaFirma = cadenaFirmada.split("\\|"); 
		
		return elementosCadenaFirma[Constantes.INT_POSICION_ID_TRAMITE_FIRMADO];
	}
	
	public TramiteFirmaElectronicaDTO consultarFirmaTramiteCiudadano(TramiteDTO tramite) {
		
		try {
	        return formularioDAO.consultarFirmaTramiteCiudadano(tramite);
	    } 
	    catch (Exception e) {
	        LOGGER.error("Error al consultar firma para el trámite ID: ", tramite.getIdTramite(), e);
	        return null;
	    }
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void notificarConteoEstatusTramite(TramiteDTO tramite, String nombreRemitente) {

		//1. Se realiza la actualización de estatus de la solicitud debido al termino del tiempo de prevención
		formularioDAO.actualizarRevisionTramite(tramite);
		
		//2. Se envía notificación de vencimiento del tiempo de prevención (Solo si cuenta con un correo)
		DetGestionUsuarioDTO detGestionUsuarioDTO = detGestionUsuariosDAO.buscarPorIdProyecto(tramite.getProyectoDTO().getIdProyecto());
		if(tramite.getUsuario() != null && tramite.getUsuario().getCorreo() != null
				&& BeanUtils.isNotNull(detGestionUsuarioDTO)) {
				try {					
					EnvioCorreo correo = new EnvioCorreo();
					DetSecurityDomainDTO detSecurityDomainDTO = detSecurityDomainDAO.buscarPorIdProyecto(tramite.getProyectoDTO().getIdProyecto(), Constantes.ID_TIPO_SECURITY_DOMAIN_CLIENTE);
					ProyectoDTO proyectoDTO = proyectoDAO.buscarPorId(tramite.getProyectoDTO().getIdProyecto());
					correo.enviarCorreoNotificacion(
							tramite.getUsuario().getCorreo(), 
							detGestionUsuarioDTO.getCorreoSubsanarPrevencion(), 
							Constantes.TITULO_NO_SUBSANAR_TRAMITE, 
							nombreRemitente, 
							tramite,
							detSecurityDomainDTO.getUrlSistema(), detGestionUsuarioDTO, proyectoDTO);
				} catch (Exception e) {
					LOGGER.error("Ocurrió un error al notificar por correo el trámite:: " + tramite.getFolioSeguimiento() + " ", e);
				}
			}		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void	enviarNotificacionFuncionario(NotificacionesDTO notificacion, String nombreRemitente) {
		try {
			 List<TramiteDTO> tramites = formularioDAO.consultarTramites(); 

			DetGestionUsuarioDTO detGestionUsuarioDTO = detGestionUsuariosDAO.buscarPorIdProyecto(notificacion.getProyectoDTO().getIdProyecto());

			DetSecurityDomainDTO detSecurityDomainDTO = detSecurityDomainDAO.buscarPorIdProyecto(
					notificacion.getProyectoDTO().getIdProyecto(), Constantes.ID_TIPO_SECURITY_DOMAIN_CLIENTE);
			ProyectoDTO proyectoDTO = proyectoDAO.buscarPorId(notificacion.getProyectoDTO().getIdProyecto());

			List<CatEstatusTramiteDTO> listEstatus = catEstatusTramiteDAO.buscarTodos();
				
				EnvioCorreoEstatusTramite correoEstatus = new EnvioCorreoEstatusTramite();
				correoEstatus.enviarCorreoEstatus(
						notificacion.getCorreosNotificacion(), 
						Constantes.TITULO_NOTIFICACION_ESTATUS, 
						nombreRemitente, 
						tramites,
						detSecurityDomainDTO.getUrlSistema(),
						detGestionUsuarioDTO, 
						proyectoDTO,
						listEstatus);
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al notificar por correo el trámite:: "
					+ notificacion.getProyectoDTO().getNombreProyecto() + " ", e);
		}
	}

	/**
	 * @deprecated 26/06/2026
	 * Metodo en deshuso se usar ahora NotificacionWebHookFacade 
	 * @param idProyecto
	 * @param tramiteDTO
	 * @param configuracionWebhookDTO
	 * @param idTipoNotificacion
	 */
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void enviarNotificacionWebhook(Long idProyecto, TramiteDTO  tramiteDTO, ConfiguracionWebhookDTO configuracionWebhookDTO, int idTipoNotificacion) {

		boolean notificacionEnviada = false;

		NotificacionTramiteWebhookRESTClient notificacionTramiteWebhookRESTClient = new NotificacionTramiteWebhookRESTClient();
		NotificacionMovimientoTramiteDTO dto =  new NotificacionMovimientoTramiteDTO();

		CatTipoNotificacionDTO  catTipoNotificacionDTO = new CatTipoNotificacionDTO();
		catTipoNotificacionDTO.setIdTipoNotificacion(idTipoNotificacion);
		dto.setCatTipoNotificacionDTO(catTipoNotificacionDTO);
		dto.setTramiteDTO(tramiteDTO);

		try {
			notificacionEnviada = notificacionTramiteWebhookRESTClient.enviarNotificacion(idProyecto, tramiteDTO, configuracionWebhookDTO);

			if(notificacionEnviada) {
				dto.setFechaNotificacion(new Date());
				dto.setEnvioConfirmado(true);
			}
		}catch (Exception e) {
			LOGGER.error("Ocurrió un error al en enviar la notificación por Webhook :: " + tramiteDTO.getFolioSeguimiento() + " ", e);
		}
		notificacionMovimientoTramiteDAO.guardar(dto);
	}
	
	
    /**
     * Obtiene la configuración general de una tabla dinámica
     * 
     * @param idComponente ID del componente
     * @return Mapa con la configuración (permite_agregar_filas, tamanio_pagina, minimo_filas, maximo_filas)
     */
    public Map<String, Object> obtenerConfiguracionTabla(Long idComponente) {
        try {
            return dynamicTableDAO.obtenerConfiguracionTabla(idComponente);
        } catch (Exception e) {
            LOGGER.error("Error al obtener configuración de tabla dinámica para componente: {}", idComponente, e);
            return new HashMap<>();
        }
    }

    /**
     * Obtiene las columnas configuradas para una tabla dinámica
     * 
     * @param idComponente ID del componente
     * @return Lista de columnas configuradas
     */
    public List<DynamicColumnConfigDTO> obtenerColumnasTabla(Long idComponente) {
        try {
            return dynamicTableDAO.obtenerColumnasTabla(idComponente);
        } catch (Exception e) {
            LOGGER.error("Error al obtener columnas de tabla dinámica para componente: {}", idComponente, e);
            return new ArrayList<>();
        }
    }

    /**
     * Obtiene los datos guardados de una tabla dinámica para un trámite específico
     * 
     * @param idTramite ID del trámite
     * @param idComponente ID del componente
     * @return Lista de filas guardadas
     */
    public List<DynamicTableRowDTO> obtenerDatosTabla(Long idTramite, Long idComponente) {
        try {
            return dynamicTableDAO.obtenerDatosTabla(idTramite, idComponente);
        } catch (Exception e) {
            LOGGER.error("Error al obtener datos de tabla dinámica para trámite: {} componente: {}", idTramite, idComponente, e);
            return new ArrayList<>();
        }
    }

    /**
     * Guarda una nueva fila en la tabla dinámica
     * 
     * @param idTramite ID del trámite
     * @param idComponente ID del componente
     * @param fila Datos de la fila a guardar
     */
    public void guardarFilaTabla(Long idTramite, Long idComponente, DynamicTableRowDTO fila) {
        try {
            dynamicTableDAO.guardarFila(idTramite, idComponente, fila);
        } catch (Exception e) {
            LOGGER.error("Error al guardar fila en tabla dinámica", e);
        }
    }

    /**
     * Actualiza una fila existente en la tabla dinámica
     * 
     * @param idTramite ID del trámite
     * @param idComponente ID del componente
     * @param fila Datos de la fila a actualizar
     */
    public void actualizarFilaTabla(Long idTramite, Long idComponente, DynamicTableRowDTO fila) {
        try {
            dynamicTableDAO.actualizarFila(idTramite, idComponente, fila);
        } catch (Exception e) {
            LOGGER.error("Error al actualizar fila en tabla dinámica", e);
        }
    }

    /**
     * Actualiza una celda específica de la tabla dinámica
     * 
     * @param idTramite ID del trámite
     * @param idComponente ID del componente
     * @param numeroFila Número de fila
     * @param columnaNombre Nombre de la columna
     * @param valor Nuevo valor
     */
    public void actualizarCeldaTabla(Long idTramite, Long idComponente, int numeroFila, 
                                      String columnaNombre, Object valor) {
        try {
            dynamicTableDAO.actualizarCelda(idTramite, idComponente, numeroFila, columnaNombre, valor);
        } catch (Exception e) {
            LOGGER.error("Error al actualizar celda en tabla dinámica", e);
        }
    }

    /**
     * Elimina una fila de la tabla dinámica
     * 
     * @param idTramite ID del trámite
     * @param idComponente ID del componente
     * @param numeroFila Número de fila a eliminar
     */
    public void eliminarFilaTabla(Long idTramite, Long idComponente, int numeroFila) {
        try {
            dynamicTableDAO.eliminarFila(idTramite, idComponente, numeroFila);
        } catch (Exception e) {
            LOGGER.error("Error al eliminar fila en tabla dinámica", e);
        }
    }

    /**
     * Guarda todas las filas de una tabla dinámica (útil para guardado masivo)
     * 
     * @param idTramite ID del trámite
     * @param idComponente ID del componente
     * @param filas Lista de filas a guardar
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void guardarDatosTablaCompleta(Long idTramite, Long idComponente, List<DynamicTableRowDTO> filas) {
        if (BeanUtils.isEmpty(filas)) {
            return;
        }
        
        for (DynamicTableRowDTO fila : filas) {
            if (fila.getId() == null) {
                guardarFilaTabla(idTramite, idComponente, fila);
            } else {
                actualizarFilaTabla(idTramite, idComponente, fila);
            }
        }
    }
	
}

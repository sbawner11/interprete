package mx.gob.atdt.interprete.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.cdi.Eager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.common.util.JerseyUtil;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.AccesoLlaveDAO;
import mx.gob.atdt.interprete.dao.AnalyticsDAO;
import mx.gob.atdt.interprete.dao.ArchivoRespuestaDAO;
import mx.gob.atdt.interprete.dao.CaptchaDAO;
import mx.gob.atdt.interprete.dao.ConfiguracionWebhookDAO;
import mx.gob.atdt.interprete.dao.DetDistribucionDAO;
import mx.gob.atdt.interprete.dao.DetSecurityDomainLineasCapturaDAO;
import mx.gob.atdt.interprete.dao.DominioSeguridadDAO;
import mx.gob.atdt.interprete.dao.FirmaDigitalDAO;
import mx.gob.atdt.interprete.dao.PagosDAO;
import mx.gob.atdt.interprete.dao.ParametrosSistemaDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dao.UsuarioGestionDAO;
import mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO;
import mx.gob.atdt.interprete.dto.ConfiguracionWebhookDTO;
import mx.gob.atdt.interprete.dto.DetAccesoLLaveDTO;
import mx.gob.atdt.interprete.dto.DetAnalyticsDTO;
import mx.gob.atdt.interprete.dto.DetCaptchaDTO;
import mx.gob.atdt.interprete.dto.DetDistribucionDTO;
import mx.gob.atdt.interprete.dto.DetElementosTokenDTO;
import mx.gob.atdt.interprete.dto.DetFirmaDigitalDTO;
import mx.gob.atdt.interprete.dto.DetGestionUsuarioDTO;
import mx.gob.atdt.interprete.dto.DetPagoDTO;
import mx.gob.atdt.interprete.dto.DetSecurityDomainDTO;
import mx.gob.atdt.interprete.dto.DetSecurityDomainLineasCapturaDTO;
import mx.gob.atdt.interprete.dto.ParametrosDetallePagoDTO;
import mx.gob.atdt.interprete.dto.ParametrosSistemaDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;

@Eager // Que se construya al iniciar el Wildfly
@Named
@ApplicationScoped
public class SeccionesProyectoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8500617277130367934L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SeccionesProyectoBean.class);

	@Inject
	private ProyectoDAO proyectoDAO;

	@Inject
	private CaptchaDAO captchaDAO;

	@Inject
	private AccesoLlaveDAO llaveDAO;

	@Inject
	private PagosDAO pagosDAO;

	@Inject
	private UsuarioGestionDAO usuarioGestionDAO;

	@Inject
	private FirmaDigitalDAO firmaDAO;

	@Inject
	private ArchivoRespuestaDAO respuestaDAO;

	@Inject
	private DominioSeguridadDAO dominioSeguridadDAO;

	@Inject
	private AnalyticsDAO analyticsDAO;
	
	@Inject
	private DetDistribucionDAO detDistribucionDAO;
	
	@Inject
	private ParametrosSistemaDAO parametrosSistemaDAO;	
	
	@Inject
	private DetSecurityDomainLineasCapturaDAO securityDomainLineasCapturaDAO;
	
	@Inject
	private SeccionAccesoLlaveApplication seccionAccesoLlaveApplication;
	
	@Inject
	private SeccionSecurityCurpApplication seccionSecurityCurpApplication;
	
	@Inject
	private SeccionFirmaDigitalApplication seccionFirmaDigitalApplication;
	
	@Inject
	private DetSecurityDomainLineasCapturaApplication securityDomainLineasCapturaApplication;
	
	@Inject
	private ConfiguracionWebhookDAO configuracionWebhookDAO;
	
	@Inject
	private ConfiguracionWebhookApplication configuracionWebhookApplication;
			
	private List<ParametrosSistemaDTO> lstParametrosSistema;

	private ProyectoDTO proyectoDTO;

	private DetCaptchaDTO captchaDTO;

	private DetAccesoLLaveDTO accesoLlaveDTO;

	private DetPagoDTO pagoDTO;

	private List<ParametrosDetallePagoDTO> lstParametrosDTO;

	private DetGestionUsuarioDTO gestionUsuarioDTO;

	private DetFirmaDigitalDTO firmaDTO;

	private List<ArchivosRespuestaTokenDTO> lstRespuesta;

	private DetAnalyticsDTO analyticsDTO;

	private DetSecurityDomainDTO securityDomainDTO;
	
	private DetSecurityDomainDTO securityDomainCurpDTO;
	
	private DetDistribucionDTO detDistribucionDTO;
		
	private ArchivosRespuestaTokenDTO archivosRespuestaTokenRegistroDTO;
	private ArchivosRespuestaTokenDTO archivosRespuestaTokenAceptacionDTO;
	private ArchivosRespuestaTokenDTO archivosRespuestaTokenRechazoDTO;
	private ArchivosRespuestaTokenDTO archivosRespuestaTokenConclusionDTO;
	
	private DetSecurityDomainLineasCapturaDTO securityDomainLineasCapturaDTO;
	
	private ConfiguracionWebhookDTO configuracionWebhookDTO;
	
	private String privateCaptchaKey;
	private String publicCaptchaKey;
	
	private boolean habilitarFirmadoTramites;
	private boolean authenticacionCiudadano;
	private boolean habilitaDistribucion;
	private boolean mostrarColumnaDescarga;
	
	/**
	 * Método auxiliar que permite obtener la llave privada para captcha.
	 * @return
	 */
	public String getPrivateCaptchaKey() {
		if(captchaDTO != null) {
			privateCaptchaKey = captchaDTO.getLlavePrivada();
		} else {
			privateCaptchaKey = "no_definida";
		}
		return privateCaptchaKey;
	}

	/**
	 * Método auxiliar que permite obtener la llave pública para captcha.
	 * @return
	 */
	public String getPublicCaptchaKey() {
		if(captchaDTO != null) {
			publicCaptchaKey = captchaDTO.getLlavePublica();
		} else {
			publicCaptchaKey = "no_definida";
		}
		return publicCaptchaKey;
	}
	
	@PostConstruct
	public void init() {
		LOGGER.info("------------------>> INICIA CONSULTAR SECCIONES PROYECTO <<----------------");
		proyectoDTO = new ProyectoDTO();
		captchaDTO = new DetCaptchaDTO();
		accesoLlaveDTO = new DetAccesoLLaveDTO();
		pagoDTO = new DetPagoDTO();
		lstParametrosDTO = new ArrayList<ParametrosDetallePagoDTO>();
		gestionUsuarioDTO = new DetGestionUsuarioDTO();
		firmaDTO = new DetFirmaDigitalDTO();
		lstRespuesta = new ArrayList<ArchivosRespuestaTokenDTO>();
		analyticsDTO = new DetAnalyticsDTO();
		securityDomainDTO = new DetSecurityDomainDTO();
		securityDomainCurpDTO = new DetSecurityDomainDTO();
		detDistribucionDTO = new DetDistribucionDTO();
		securityDomainLineasCapturaDTO = new DetSecurityDomainLineasCapturaDTO();
		configuracionWebhookDTO = new ConfiguracionWebhookDTO();
		habilitarFirmadoTramites = false;
		authenticacionCiudadano = false;
		habilitaDistribucion = false;
		lstParametrosSistema = new ArrayList<ParametrosSistemaDTO>();
		try {
			proyectoDTO = proyectoDAO.consultaProyecto();
			consultaDetalleSeccionesProyecto(proyectoDTO);
			mostrarColumnaDescarga = habiitarColumnaDescarga();
		} catch (Exception e) {
			LOGGER.error("Error al consultar proyecto SeccionesProyectoBean: ", e);
		}
	}

	/*
	 * Método que consulta y carga la información de las secciones del proyecto
	 */
	private void consultaDetalleSeccionesProyecto(ProyectoDTO proyectoDTO) throws Exception {
		if (BeanUtils.isNotNull(proyectoDTO)) {
				
			captchaDTO = captchaDAO.consultaCaptcha(proyectoDTO.getIdProyecto());

			accesoLlaveDTO = llaveDAO.consultaAccesoLlave(proyectoDTO.getIdProyecto());
			/** Se revisa si el proyecto tiene configurado el acceso mediante llave para el ciudadano, para mostrar la columna CURP
			 *  en la tabla de trámites de la bandeja del funcionario. */
			if(BeanUtils.isNotNull(accesoLlaveDTO) && accesoLlaveDTO.isAutenticacionCiudadano()) {
				authenticacionCiudadano = true;	
			}	
			
			if(accesoLlaveDTO != null) {
				/** Se inicializa valores en clase application del proyecto EJB con datos de acceso de llave 
				 * y se inicializa valores del filter **/			
				seccionAccesoLlaveApplication.setAccesoLlaveDTO(accesoLlaveDTO);
				JerseyUtil.getInstance().getClientSDKCdmxWithAuth().removeAllFilters();
				JerseyUtil.getInstance().getClientSDKCdmxWithAuth().addFilter(new HTTPBasicAuthFilter(accesoLlaveDTO.getUsuarioDominoSeg(), accesoLlaveDTO.getContrasenaDominioSeg()));	
			}			

			pagoDTO = pagosDAO.consultaDetallePagos(proyectoDTO.getIdProyecto());
			if (BeanUtils.isNotNull(pagoDTO)) {
				lstParametrosDTO = pagosDAO.consultaParametrosPago(pagoDTO.getIdDetallePago());				
			} 

			gestionUsuarioDTO = usuarioGestionDAO.consultaUsuarioGestion(proyectoDTO.getIdProyecto());

			firmaDTO = firmaDAO.consultaFirmaDigital(proyectoDTO.getIdProyecto());
			if (BeanUtils.isNotNull(firmaDTO)) {
				seccionFirmaDigitalApplication.setFirmaDigitalDTO(firmaDTO);
				JerseyUtil.getInstance().getClientFirmaWithAuth().removeAllFilters();
				JerseyUtil.getInstance().getClientFirmaWithAuth().addFilter(new HTTPBasicAuthFilter(firmaDTO.getUsuarioDominioSeg(), firmaDTO.getContrasenaDominioSeg()));
			}

			lstRespuesta = respuestaDAO.consultaArchivoRespuesta(proyectoDTO.getIdProyecto());
			if (BeanUtils.isNotNull(lstRespuesta)) {
				/**Se precargan los token de cada archivo de respuesta **/
				for (ArchivosRespuestaTokenDTO respuesta : lstRespuesta) {
					List<DetElementosTokenDTO> lstToken = new ArrayList<DetElementosTokenDTO>();
					lstToken = respuestaDAO.consultaElementosToken(respuesta.getIdArchivoRespuesta());
					if (BeanUtils.isNotNull(lstToken)) {
						respuesta.setLstToken(lstToken);
					} 
				}
				
				archivosRespuestaTokenRegistroDTO = null;
				archivosRespuestaTokenAceptacionDTO = null;
				archivosRespuestaTokenRechazoDTO = null;
				archivosRespuestaTokenConclusionDTO = null;
				
				/** Se revisa si los formatos que se tienen registrados requieren del firmado del trámite**/
				habilitarFirmadoTramites = BeanUtils.habilitaFirmadoTramites(lstRespuesta);
				/*for (ArchivosRespuestaTokenDTO respuestaFirmaTemp : lstRespuesta) {
					//archivosRespuestaTokenRegistroDTO = respuestaFirmaTemp;
					if((respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_FIRMADO_ACEPTADO ||
							respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_REGISTRO_CONCLUIDO ||
							respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_FIRMADO_RECHAZO) &&
							respuestaFirmaTemp.isHabilitaFirma()) {
						habilitarFirmadoTramites = true;
						break;
					}
				}*/
//				/** Si se habilita el firmado de trámites, se buscarán los archivos para aceptación y rechazo **/
//				if(habilitarFirmadoTramites) {
//					/** Si la configuración tiene habilitado el firmado, se deberá obtener archivo para rechazo y para aceptación **/
//					for (ArchivosRespuestaTokenDTO respuestaFirmaTemp : lstRespuesta) {						
//						if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_FIRMADO_ACEPTADO) {
//							archivosRespuestaTokenAceptacionDTO = respuestaFirmaTemp;
//						}						
//						if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_FIRMADO_RECHAZO) {
//							archivosRespuestaTokenRechazoDTO = respuestaFirmaTemp;
//						}
//					}					
//				}
				
				/** Se revisan los archivos de respuesta, y dependiendo del tipo se plantilla se carga el DTO para el tipo de plantilla Aceptado, Rechazado, y Registro**/
				for (ArchivosRespuestaTokenDTO respuestaFirmaTemp : lstRespuesta) {
					if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_REGISTRO_CONCLUIDO) {
						archivosRespuestaTokenConclusionDTO = respuestaFirmaTemp;
					}						
					if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_FIRMADO_ACEPTADO) {
						archivosRespuestaTokenAceptacionDTO = respuestaFirmaTemp;
					}						
					if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_FIRMADO_RECHAZO) {
						archivosRespuestaTokenRechazoDTO = respuestaFirmaTemp;
					}					
					if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_COMPROBANTE_REGISTRO) {
						archivosRespuestaTokenRegistroDTO = respuestaFirmaTemp;
					}					
				}

//				if (BeanUtils.isNotNull(lstRespuesta.get(0))) {
//					archivosRespuestaTokenDTO = lstRespuesta.get(0);
//					/**Se verifica el primer archivo de respuesta configurado, se valida si la configuración está habilitada para 
//					 * el firmado de archivos, si está habilitada para firmado de archivos se obtiene plantilla para Aceptación y 
//					 * Rechazo de trámite, si no contiene firmado se toma la única plantilla para trámites rechazados y aceptados **/
//					if(archivosRespuestaTokenDTO.isHabilitaFirma()) {
//						habilitarFirmadoTramites = true;
//						/** Si la configuración tiene habilitado el firmado, se deberá obtener archivo para rechazo y para aceptación **/
//						for (ArchivosRespuestaTokenDTO respuestaFirmaTemp : lstRespuesta) {						
//							if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_FIRMADO_ACEPTADO) {
//								archivosRespuestaTokenAceptacionDTO = respuestaFirmaTemp;
//							}
//							
//							if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_FIRMADO_RECHAZO) {
//								archivosRespuestaTokenRechazoDTO = respuestaFirmaTemp;
//							}
//						}
//					} else {
//						archivosRespuestaTokenAceptacionDTO = null;
//						archivosRespuestaTokenRechazoDTO = null;
//					}
//				} else {
//					archivosRespuestaTokenDTO = null;
//					archivosRespuestaTokenAceptacionDTO = null;
//					archivosRespuestaTokenRechazoDTO = null;
//				}			
				
			} else {
				archivosRespuestaTokenRegistroDTO = null;
				archivosRespuestaTokenAceptacionDTO = null;
				archivosRespuestaTokenRechazoDTO = null;
				archivosRespuestaTokenConclusionDTO = null;
			}

			analyticsDTO = analyticsDAO.consultaAnalytics(proyectoDTO.getIdProyecto());
			if (BeanUtils.isNotNull(analyticsDTO)) {
				//Se setea la ruta para obtener la imagen desde el motor
				analyticsDTO.setRutaImagenGrap(obtenerPathArchivos(analyticsDTO.getRutaImagenGrap()));
			} 

			securityDomainDTO = dominioSeguridadDAO.consultaDominioSeguridad(proyectoDTO.getIdProyecto());
			
			securityDomainCurpDTO = dominioSeguridadDAO.consultaDominioSeguridadCurp(proyectoDTO.getIdProyecto());
			if (BeanUtils.isNotNull(securityDomainCurpDTO)) {
				seccionSecurityCurpApplication.setDetSecurityDomainCurpDTO(securityDomainCurpDTO);
				if(BeanUtils.isNotNull(securityDomainCurpDTO.getUrlSistema()) && 
						BeanUtils.isNotNull(securityDomainCurpDTO.getUsuario()) && 
								BeanUtils.isNotNull(securityDomainCurpDTO.getContrasenia())) {
					/** Se inicializa valores en clase application del proyecto EJB con datos de Seguridad de dominio para CURP 
					 * y se inicializa valores del filter **/
					JerseyUtil.getInstance().getClientCURPWithAuth().removeAllFilters();
					JerseyUtil.getInstance().getClientCURPWithAuth().addFilter(new HTTPBasicAuthFilter(securityDomainCurpDTO.getUsuario(), BeanUtils.desencriptarPassword(securityDomainCurpDTO.getContrasenia())));
				}
			}
			
			//Se obtiene el detalle de la sección Distribución de las solicitudes.
			detDistribucionDTO = detDistribucionDAO.buscarPorIdProyecto(proyectoDTO.getIdProyecto());
			if(BeanUtils.isNotNull(detDistribucionDTO) && BeanUtils.isNotNull(detDistribucionDTO.isActivo())
					&& detDistribucionDTO.isActivo()) {
				habilitaDistribucion = true;
			}
							
			//Se consultan parametros de sistema
			lstParametrosSistema = parametrosSistemaDAO.consultaParametrosActivosSistema(Constantes.ID_ACTIVO);
			
			if(proyectoDTO.isProyectoLineaCaptura()) {
				securityDomainLineasCapturaDTO = securityDomainLineasCapturaDAO.buscarPorIdProyecto(proyectoDTO.getIdProyecto());
				securityDomainLineasCapturaApplication.setSecurityDomainLineasCapturaDTO(securityDomainLineasCapturaDTO);
				JerseyUtil.getInstance().getClientLineaCapturaAuth().removeAllFilters();
				JerseyUtil.getInstance().getClientLineaCapturaAuth().addFilter(new HTTPBasicAuthFilter(securityDomainLineasCapturaDTO.getUsuario(), BeanUtils.desencriptarPassword(securityDomainLineasCapturaDTO.getContrasenia())));
			}
			//Se obtiene la configuración para las notificaciones por Webhook
			configuracionWebhookDTO = configuracionWebhookDAO.buscarPorIdProyecto(proyectoDTO.getIdProyecto());
			//Solo si se tiene una configuración y se encuentra activa la bandera para notificar cambios de trámite se inicializa el cliente.
			if(configuracionWebhookDTO != null && configuracionWebhookDTO.isHabilitaEnvioNotificaciones()) {
				configuracionWebhookApplication.setConfiguracionWebhookDTO(configuracionWebhookDTO);
				JerseyUtil.getInstance().getClientWebhook().removeAllFilters();
				JerseyUtil.getInstance().getClientWebhook().addFilter(new HTTPBasicAuthFilter(configuracionWebhookApplication.getUsuario(), configuracionWebhookApplication.getContrasenia()));
			}
		}
	}
	
	/**
	 * Método auxiliar que arma la URL del documento a obtener desde el file-server del motor-admin
	 * @return
	 */
	private String obtenerPathArchivos(String rutaArchivo) {
		String pathArchivoMotor = Constantes.EMPTY_STRING;
		if(!BeanUtils.isEmpty(rutaArchivo)) {
			if(rutaArchivo.contains(Environment.getPathFileServerMotor())) {
				pathArchivoMotor = rutaArchivo.replace(Environment.getPathFileServerMotor(), Environment.getUrlFileServerMotor());
			}
		}
		return pathArchivoMotor;
	}
	
	/**
	 * Método auxiiar que verifica de acuerdo a las reglas actuales, si la columna de "Descarga" del listado de trámites se muestra o no.
	 * De acuerdo a las siguientes reglas::
	 * 
	 * 1.- Si el proyecto tiene la marca de "Aviso", se mostrará en la bandeja del Ciudadano la columna "Descargar", y el tipo de plantilla
	 * que podrá descargar desde esta opción será "3 - Plantilla para registro concluido", pero si el usuario no configuró esta plantilla en 
	 * la sección de Adminsitración de formatos, entonces no habría ningún documento por descargar, por lo tanto no se muestra columna.
	 * 
	 * 2.- Si el proyecto no es un "Aviso" y si se cuenta con la bandera para habilitarFirmadoTramites y se cuenta con datos de firma, si 
	 * se podrá habilitar la columna "Descargar", porque la bandera "habilitarFirmadoTramites" indica que se cuenta con las 2 plantillas
	 * para realizar el flujo de firmado de trámites, por lo tanto si se muestra la columna de Descarga.
	 * 
	 * @return
	 */
	private boolean habiitarColumnaDescarga() {
		boolean habilitaColumna = false;
		
		if (BeanUtils.isNotNull(proyectoDTO)) {			
			if(proyectoDTO.isAviso()) {			
				if(BeanUtils.isNotNull(archivosRespuestaTokenConclusionDTO)) {
					habilitaColumna = true;
				}
			} else {
				if(BeanUtils.isNotNull(lstRespuesta) ||
						(habilitarFirmadoTramites && BeanUtils.isNotNull(firmaDTO) && BeanUtils.isNotNull(firmaDTO.getIdDetalleFirma()))) {
					habilitaColumna = true;
				}
			}
		}
		
		return habilitaColumna;
	}
	
	
	/**GETTER´s y SETTER´s**/
	
	public ProyectoDTO getProyectoDTO() {
		return proyectoDTO;
	}

	public void setProyectoDTO(ProyectoDTO proyectoDTO) {
		this.proyectoDTO = proyectoDTO;
	}
	
	public DetCaptchaDTO getCaptchaDTO() {
		return captchaDTO;
	}

	public void setCaptchaDTO(DetCaptchaDTO captchaDTO) {
		this.captchaDTO = captchaDTO;
	}

	public DetAccesoLLaveDTO getAccesoLlaveDTO() {
		return accesoLlaveDTO;
	}

	public void setAccesoLlaveDTO(DetAccesoLLaveDTO accesoLlaveDTO) {
		this.accesoLlaveDTO = accesoLlaveDTO;
	}

	public DetPagoDTO getPagoDTO() {
		return pagoDTO;
	}

	public void setPagoDTO(DetPagoDTO pagoDTO) {
		this.pagoDTO = pagoDTO;
	}

	public List<ParametrosDetallePagoDTO> getLstParametrosDTO() {
		return lstParametrosDTO;
	}

	public void setLstParametrosDTO(List<ParametrosDetallePagoDTO> lstParametrosDTO) {
		this.lstParametrosDTO = lstParametrosDTO;
	}

	public DetGestionUsuarioDTO getGestionUsuarioDTO() {
		return gestionUsuarioDTO;
	}

	public void setGestionUsuarioDTO(DetGestionUsuarioDTO gestionUsuarioDTO) {
		this.gestionUsuarioDTO = gestionUsuarioDTO;
	}

	public DetFirmaDigitalDTO getFirmaDTO() {
		return firmaDTO;
	}

	public void setFirmaDTO(DetFirmaDigitalDTO firmaDTO) {
		this.firmaDTO = firmaDTO;
	}

	public List<ArchivosRespuestaTokenDTO> getLstRespuesta() {
		return lstRespuesta;
	}

	public void setLstRespuesta(List<ArchivosRespuestaTokenDTO> lstRespuesta) {
		this.lstRespuesta = lstRespuesta;
	}

	public DetAnalyticsDTO getAnalyticsDTO() {
		return analyticsDTO;
	}

	public void setAnalyticsDTO(DetAnalyticsDTO analyticsDTO) {
		this.analyticsDTO = analyticsDTO;
	}

	public DetSecurityDomainDTO getSecurityDomainDTO() {
		return securityDomainDTO;
	}

	public void setSecurityDomainDTO(DetSecurityDomainDTO securityDomainDTO) {
		this.securityDomainDTO = securityDomainDTO;
	}

	public DetSecurityDomainDTO getSecurityDomainCurpDTO() {
		return securityDomainCurpDTO;
	}

	public void setSecurityDomainCurpDTO(DetSecurityDomainDTO securityDomainCurpDTO) {
		this.securityDomainCurpDTO = securityDomainCurpDTO;
	}

	public boolean isHabilitarFirmadoTramites() {
		return habilitarFirmadoTramites;
	}
	
	public void setHabilitarFirmadoTramites(boolean habilitarFirmadoTramites) {
		this.habilitarFirmadoTramites = habilitarFirmadoTramites;
	}

	public ArchivosRespuestaTokenDTO getArchivosRespuestaTokenRegistroDTO() {
		return archivosRespuestaTokenRegistroDTO;
	}

	public void setArchivosRespuestaTokenRegistroDTO(ArchivosRespuestaTokenDTO archivosRespuestaTokenRegistroDTO) {
		this.archivosRespuestaTokenRegistroDTO = archivosRespuestaTokenRegistroDTO;
	}

	/**
	 * @return the archivosRespuestaTokenAceptacionDTO
	 */
	public ArchivosRespuestaTokenDTO getArchivosRespuestaTokenAceptacionDTO() {
		return archivosRespuestaTokenAceptacionDTO;
	}

	/**
	 * @param archivosRespuestaTokenAceptacionDTO the archivosRespuestaTokenAceptacionDTO to set
	 */
	public void setArchivosRespuestaTokenAceptacionDTO(ArchivosRespuestaTokenDTO archivosRespuestaTokenAceptacionDTO) {
		this.archivosRespuestaTokenAceptacionDTO = archivosRespuestaTokenAceptacionDTO;
	}

	/**
	 * @return the archivosRespuestaTokenRechazoDTO
	 */
	public ArchivosRespuestaTokenDTO getArchivosRespuestaTokenRechazoDTO() {
		return archivosRespuestaTokenRechazoDTO;
	}

	/**
	 * @param archivosRespuestaTokenRechazoDTO the archivosRespuestaTokenRechazoDTO to set
	 */
	public void setArchivosRespuestaTokenRechazoDTO(ArchivosRespuestaTokenDTO archivosRespuestaTokenRechazoDTO) {
		this.archivosRespuestaTokenRechazoDTO = archivosRespuestaTokenRechazoDTO;
	}

	public ArchivosRespuestaTokenDTO getArchivosRespuestaTokenConclusionDTO() {
		return archivosRespuestaTokenConclusionDTO;
	}

	public void setArchivosRespuestaTokenConclusionDTO(ArchivosRespuestaTokenDTO archivosRespuestaTokenConclusionDTO) {
		this.archivosRespuestaTokenConclusionDTO = archivosRespuestaTokenConclusionDTO;
	}

	/**
	 * @return the authenticacionCiudadano
	 */
	public boolean isAuthenticacionCiudadano() {
		return authenticacionCiudadano;
	}

	/**
	 * @param authenticacionCiudadano the authenticacionCiudadano to set
	 */
	public void setAuthenticacionCiudadano(boolean authenticacionCiudadano) {
		this.authenticacionCiudadano = authenticacionCiudadano;
	}

	public DetDistribucionDTO getDetDistribucionDTO() {
		return detDistribucionDTO;
	}

	public void setDetDistribucionDTO(DetDistribucionDTO detDistribucionDTO) {
		this.detDistribucionDTO = detDistribucionDTO;
	}

	public boolean isHabilitaDistribucion() {
		return habilitaDistribucion;
	}

	public void setHabilitaDistribucion(boolean habilitaDistribucion) {
		this.habilitaDistribucion = habilitaDistribucion;
	}	

	/**
	 * @return the lstParametrosSistema
	 */
	public List<ParametrosSistemaDTO> getLstParametrosSistema() {
		return lstParametrosSistema;
	}

	/**
	 * @param lstParametrosSistema the lstParametrosSistema to set
	 */
	public void setLstParametrosSistema(List<ParametrosSistemaDTO> lstParametrosSistema) {
		this.lstParametrosSistema = lstParametrosSistema;
	}

	/**
	 * @return the mostrarColumnaDescarga
	 */
	public boolean isMostrarColumnaDescarga() {
		return mostrarColumnaDescarga;
	}

	/**
	 * @param mostrarColumnaDescarga the mostrarColumnaDescarga to set
	 */
	public void setMostrarColumnaDescarga(boolean mostrarColumnaDescarga) {
		this.mostrarColumnaDescarga = mostrarColumnaDescarga;
	}
	
	
	public DetSecurityDomainLineasCapturaDTO getSecurityDomainLineasCapturaDTO() {
		return securityDomainLineasCapturaDTO;
	}

	public void setSecurityDomainLineasCapturaDTO(DetSecurityDomainLineasCapturaDTO securityDomainLineasCapturaDTO) {
		this.securityDomainLineasCapturaDTO = securityDomainLineasCapturaDTO;
	}

	/**
	 * @return the configuracionWebhookDTO
	 */
	public ConfiguracionWebhookDTO getConfiguracionWebhookDTO() {
		return configuracionWebhookDTO;
	}

	/**
	 * @param configuracionWebhookDTO the configuracionWebhookDTO to set
	 */
	public void setConfiguracionWebhookDTO(ConfiguracionWebhookDTO configuracionWebhookDTO) {
		this.configuracionWebhookDTO = configuracionWebhookDTO;
	}	
	
}

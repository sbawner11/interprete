package mx.gob.atdt.interprete.tramites.bean;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.codehaus.jettison.json.JSONException;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import mx.gob.atdt.firma.client.FirmaRESTClient;
import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.backoffice.facade.BackofficeFacade;
import mx.gob.atdt.interprete.client.SituacionRolClient;
import mx.gob.atdt.interprete.common.formatos.FormatoRespuestaPDF;
import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.commons.utils.Utils;
import mx.gob.atdt.interprete.dao.BitMovimientosTramiteDAO;
import mx.gob.atdt.interprete.dao.CatEstatusLineaCapturaDAO;
import mx.gob.atdt.interprete.dao.CatEstatusTramiteDAO;
import mx.gob.atdt.interprete.dao.DetAsignacionDistribucionDAO;
import mx.gob.atdt.interprete.dao.DetElementosTokenDAO;
import mx.gob.atdt.interprete.dao.DetGestionUsuariosDAO;
import mx.gob.atdt.interprete.dao.DetLineaCapturaDAO;
import mx.gob.atdt.interprete.dao.UsuarioDAO;
import mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO;
import mx.gob.atdt.interprete.dto.BitAsignacionRevisorTramiteDTO;
import mx.gob.atdt.interprete.dto.BitMovimientosTramiteDTO;
import mx.gob.atdt.interprete.dto.BitRevertirEstatusDTO;
import mx.gob.atdt.interprete.dto.CatEstatusLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.CatEstatusTramiteDTO;
import mx.gob.atdt.interprete.dto.CatTiposMovimientoDTO;
import mx.gob.atdt.interprete.dto.ConsultaTramiteDTO;
import mx.gob.atdt.interprete.dto.ControlComponentesDTO;
import mx.gob.atdt.interprete.dto.DetAsignacionDistribucionDTO;
import mx.gob.atdt.interprete.dto.DetElementosTokenDTO;
import mx.gob.atdt.interprete.dto.DetGestionUsuarioDTO;
import mx.gob.atdt.interprete.dto.DetLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.FirmaTramiteDTO;
import mx.gob.atdt.interprete.dto.LineaCapturaDTO;
import mx.gob.atdt.interprete.dto.ParametrosSistemaDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.ResponseServiceFirmaDTO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.TramiteFirmaElectronicaDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;
import mx.gob.atdt.interprete.exception.InterpreteException;
import mx.gob.atdt.interprete.facade.LineaCapturaFacade;
import mx.gob.atdt.interprete.formulario.dao.FormularioDAO;
import mx.gob.atdt.interprete.formularios.application.GenerarFormularioApplication;
import mx.gob.atdt.interprete.formularios.bean.RegistrarFormularioBean;
import mx.gob.atdt.interprete.linea.captura.client.EstatusLineaCapturaClient;
import mx.gob.atdt.interprete.linea.captura.dto.RequestConsultaLCDTO;
import mx.gob.atdt.interprete.linea.captura.dto.ResponseEstatusLCDTO;
import mx.gob.atdt.interprete.notificaciones.EnvioCorreo;
import mx.gob.atdt.interprete.tramites.facade.TramitesFacade;
import mx.gob.atdt.interprete.util.WebResources;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.Map.Entry;

@Named
@SessionScoped
public class BandejaValidacionTramitesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6576631731244437967L;

	private static final Logger LOGGER = LoggerFactory.getLogger(BandejaValidacionTramitesBean.class);
	
	@Inject
	private FacesContext facesContext;

	@Inject
	private GenerarFormularioApplication formularioApplication;
	
	@Inject
	private SeccionesProyectoBean seccionesProyectoBean;
	
	@Inject
	private GenerarFormularioApplication generarFormularioApplication;

	@Inject
	private	FormularioDAO formularioDAO;

	@Inject
	private EstructuraFormularioDAO estructuraFormularioDAO;
	
	@Inject
	private AuthenticatorBean authBean;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private BackofficeFacade backofficeFacade;
		
	@Inject
	private TramitesFacade tramitesFacade;
	
	@Inject
	private RegistrarFormularioBean registrarFormularioBean;
	
	@Inject
	private DetElementosTokenDAO detElementosTokenDAO;
	
	@Inject
	private DetAsignacionDistribucionDAO detAsignacionDistribucionDAO;
	
	@Inject
	private DetGestionUsuariosDAO detGestionUsuariosDAO;
	
	@Inject
	private CatEstatusLineaCapturaDAO catEstatusLineaCapturaDAO;
	
	@Inject 
	private DetLineaCapturaDAO detLineaCapturaDAO;
	
	@Inject 
	private LineaCapturaFacade lineaFacturaFacade;
	@Inject
	private BitMovimientosTramiteDAO bitMovimientosTramiteDAO;
	@Inject
	private CatEstatusTramiteDAO catEstatusTramiteDAO;

	private List<TramiteDTO> lstTramites;

	private List<CatEstatusTramiteDTO> lstEstatusTramite;
	
	private List<UsuarioDTO> lstUsuariosOperadoresActivos;
	
	private List<TramiteDTO> lstTramitesSeleccionados;
	
	private List<TramiteDTO> lstTramitesFirma;
	
	private List<TramiteDTO> lstTramitesSelFirma;
	
	private List<Long> lstElementosDistribucion;

	private TramiteDTO tramiteBusqueda;
	
	private TramiteDTO tramiteSeleccionado;
	
	private UsuarioDTO usuarioOperadorDTO;
	
	private ArchivosRespuestaTokenDTO archivosRespuestaTokenDTO;	
	
	private ResponseServiceFirmaDTO respuestaFirmaTramite;	
	
	private DetAsignacionDistribucionDTO detAsignacionDistribucionDTO;
	
	private DefaultStreamedContent reporteTramites;
	
	private Date fechaActual;
	private Date fechaMinima;
	private Date fechaMaxima;
	private int diasRangoTramites;
	private List<BitMovimientosTramiteDTO> lstHistorico;
	
	private String msgErrorFirma;
	
	 public void cargaMsgErrorFirmante() {
		 
	      if(msgErrorFirma != null ) {
	    	  WebResources.validationMessage(msgErrorFirma, true);
	      }
	      
	      msgErrorFirma = null;
	 }
	
	
	/**
	 * Método que inicializa la bandeja de validación de trámites para los Usuarios
	 * Supervisor y Operador
	 * 
	 * @return
	 */
	public String inicializar() {
		
		/**El proyecto si cuenta con distribución habiltiada, se revisa si el rol actual ya está configurado**/
		if(!validarTieneDistribucionAsignada()) {
			return Constantes.URL_SIN_DISTRIBUCION + Constantes.JSF_REDIRECT;
		}	

		/**Se verifica si desde BD en la tabla de parámetros_sistema, se encuentra la configuración de 
		 * los días máximos para consulta de trámites, si no se tiene en BD se configura valor default**/
		diasRangoTramites = Constantes.INT_MAX_DIAS_CONSULTA_TRAMITES_DEFAULT;		
		
		if(BeanUtils.isNotNull(seccionesProyectoBean.getLstParametrosSistema())) {
			for(ParametrosSistemaDTO parametro : seccionesProyectoBean.getLstParametrosSistema()) {
				if(parametro.getIdParametro().intValue() == Constantes.ID_PARAMETRO_MAX_DIAS_CONSULTA_TRAMITES){					
					diasRangoTramites = parametro.getValor().intValue(); 
				}
			}			
		}
		
		fechaMinima = new Date();
		fechaMaxima = new Date();
		fechaActual = new Date();
		tramiteBusqueda = new TramiteDTO();
		lstTramites = new ArrayList<TramiteDTO>();
		lstTramitesFirma = new ArrayList<TramiteDTO>();		
		lstEstatusTramite = new ArrayList<CatEstatusTramiteDTO>();
		lstElementosDistribucion = new ArrayList<Long>();
		lstTramitesSeleccionados = null;
		lstTramitesSelFirma = null;
		usuarioOperadorDTO = null;
		lstUsuariosOperadoresActivos = null;
		detAsignacionDistribucionDTO = new DetAsignacionDistribucionDTO();
		
		/**Si el usuario es un Administrador, se revisa si existe la tabla de bítacora para revertir estatus, 
		 * si no existe dicha tabla, se valida si existe la tabla de trámites, la tabla de bítacora solo será 
		 * creada si ya existe la tabla de trámite
		**/
		if((authBean.isRolAdministrador() || authBean.isRolAdministradorDatosTecnicos()) && estructuraFormularioDAO.existeTablaBitacoraCambiosEstatus() == false) {
			if(estructuraFormularioDAO.existeTablaTramites()) {
				try {
					estructuraFormularioDAO.creaTablaBitacoraCambioEstatus();
					estructuraFormularioDAO.creaSecuenciaTablaBitacoraCambioEstatus();
				} catch (Exception e) {
					LOGGER.error("Ocurrió un error al crear la tabla de bítacora para cambios de estatus de trámites :: ", e);
				}	
			}			
		}
		
		if(authBean.isRolSupervisor()) {
			usuarioOperadorDTO = new UsuarioDTO();
			lstUsuariosOperadoresActivos = new ArrayList<UsuarioDTO>();
			lstTramitesSeleccionados = new ArrayList<TramiteDTO>();
			SituacionRolClient situacionRolClient = new SituacionRolClient();
			try {
				lstUsuariosOperadoresActivos = situacionRolClient.obtenerUsuariosRol(
						Long.parseLong(seccionesProyectoBean.getAccesoLlaveDTO().getClaveSistema()),
						Constantes.DESC_ROL_OPERADOR, 
						true);
				if (BeanUtils.isEmpty(lstUsuariosOperadoresActivos)) {
					WebResources.addValidationMessage("msj_sin_usuarios_rol_llave",
							new Object[] { Constantes.DESC_ROL_OPERADOR }, false);
				}
			} catch (NumberFormatException | JSONException e) {
				LOGGER.error("Error al consultar los operadores activos en Llave MX: ", e);
				WebResources.addValidationMessage("msj_sin_usuarios_rol_llave",
						new Object[] { Constantes.DESC_ROL_OPERADOR }, false);
			}
		}
		
		try {
			if(authBean.isRolOperador()) {
				tramiteBusqueda.setUsuarioOperador(authBean.getUsuarioLogueado());
			}			
			//Se valida si existe tabla trámites
			if(estructuraFormularioDAO.existeTablaTramites()) {
				//Se obtiene informacion necesaria para filtrar la consulta de solicitudes por la distribucion
				obtenerInformacionDistribucionParaFiltrado();
				ConsultaTramiteDTO consulta = new ConsultaTramiteDTO(authBean.isRolOperador(), authBean.isRolSupervisor(), 
						authBean.isRolConsulta(), seccionesProyectoBean.isHabilitaDistribucion(), lstElementosDistribucion, 
						detAsignacionDistribucionDTO.getComponenteDistribucionDTO().getIdComponente(), 
						detAsignacionDistribucionDTO.getComponenteDistribucionDTO().getCatTipoComponenteDTO().getIdTipoComponente(),
						obtenerNombreTablaComponenteDistribucion());
				lstTramites = formularioDAO.consultarTamites(tramiteBusqueda, consulta);	
			}
			lstEstatusTramite = cargarListaEstatusParaFiltro();
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error en la consulta de trámites:: ", e);
			WebResources.errorMessage("msj_error_busqueda", true);
		}
		if (BeanUtils.isNull(lstTramites) || lstTramites.isEmpty()) {
			if(seccionesProyectoBean.isHabilitaDistribucion() == false) {
				WebResources.validationMessage("msj_busqueda_sin_resultados", true);
			} else {
				WebResources.validationMessage("msj_busqueda_sin_resultados_distribucion", true);				
			}
			
		} else {
			asignarEstiloTramite();
			//Si el proyecto permite firmado, se revisa si los trámites cuentan con firma
			if(seccionesProyectoBean.isHabilitarFirmadoTramites()) {
				consultarFirmaTramite();	
			}			
		}
		
		return Constantes.RETURN_BANDEJA_VALIDACION_TRAMITES_PAGE + Constantes.JSF_REDIRECT;		
	}
	
	/**
	 * Método auxiliar privado cuya función es obtener el detalle de la
	 * asignación de la distribución y preparar los objetos necesarios 
	 * para poder realizar la búsqueda de las solicitudes 
	 * referente a la distribución asiganada.
	 */
	private void obtenerInformacionDistribucionParaFiltrado() {
		
		if (authBean.isRolConsulta()) {
	        lstElementosDistribucion = null;
	        detAsignacionDistribucionDTO = new DetAsignacionDistribucionDTO();
	        return;
	    }
		
		if((authBean.isRolSupervisor() || authBean.isRolOperador() || authBean.isRolConsulta()) && seccionesProyectoBean.isHabilitaDistribucion()) {
			List<DetAsignacionDistribucionDTO> lstDistribucionesAsignadas = detAsignacionDistribucionDAO.buscarPorIdUsuarioAsignadoAndComponente(
					authBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx(),
					seccionesProyectoBean.getDetDistribucionDTO().getComponenteDTO().getIdComponente());
			
			if(BeanUtils.isNotNull(lstDistribucionesAsignadas)) {
				LOGGER.info("No es nula la lista de asignaciones para el usuario: " + authBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
				detAsignacionDistribucionDTO = new DetAsignacionDistribucionDTO();
				detAsignacionDistribucionDTO.setComponenteDistribucionDTO(lstDistribucionesAsignadas.get(0).getComponenteDistribucionDTO());
				if(lstDistribucionesAsignadas.size() == 1) {
					lstElementosDistribucion = new ArrayList<Long>();
					lstElementosDistribucion.add(lstDistribucionesAsignadas.get(0).getIdElementoAsignado());
				} else {
					lstElementosDistribucion = new ArrayList<Long>();
					for (DetAsignacionDistribucionDTO detAsignacionDistribucionDTO : lstDistribucionesAsignadas) {
						lstElementosDistribucion.add(detAsignacionDistribucionDTO.getIdElementoAsignado());
					}
				}
			}
		}
	}
	
	
	/**
	 * Método privado que se utiliza para cargar la lista de los estatus, 
	 * la consulta regresa todos los estatus y en este método se eliminan
	 * los estatus que no se quieren mostrar
	 * @return
	 * @throws Exception
	 */
	private List<CatEstatusTramiteDTO> cargarListaEstatusParaFiltro() throws Exception{
		
	    List<CatEstatusTramiteDTO> lstEstatusTmp = formularioDAO.consultarEstatusTramite();
	    if(lstEstatusTmp != null) {
	        for (int i = 0; i < lstEstatusTmp.size(); i++) {
	            if(lstEstatusTmp.get(i).getIdEstatusTramite() == Constantes.ID_ESTATUS_EN_CAPTURA) {
	                lstEstatusTmp.remove(i);
	                break;
	            }
	        }
	        
	        //lógica de descripción personalizada para la lista de filtros
	        lstEstatusTmp.forEach(estatus -> {
	            if (estatus.getDescripcionPersonalizada() != null && !estatus.getDescripcionPersonalizada().trim().isEmpty()) {
	                estatus.setDescripcion(estatus.getDescripcionPersonalizada());
	            }
	        });
	    } else {
	        lstEstatusTmp = new ArrayList<CatEstatusTramiteDTO>();
	    }
	    return lstEstatusTmp;
	}

	/**
	 * Método auxiliar que asigna los estilos para cada estatus de trámite.
	 */
	private void asignarEstiloTramite() {
	    lstTramites.forEach(tramite -> {
	        CatEstatusTramiteDTO estatus = tramite.getCatEstatusTramiteDTO();
	        
	        // descripción personalizada si está disponible
	        if (estatus.getDescripcionPersonalizada() != null && !estatus.getDescripcionPersonalizada().trim().isEmpty()) {
	            estatus.setDescripcion(estatus.getDescripcionPersonalizada());
	            estatus.setDescripcionAviso(estatus.getDescripcionPersonalizada());
	        }
	        
	        // asignar estilos 
	        if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_EN_CAPTURA) {
	            tramite.setEstiloEstatus("estatus-en-captura");
	            tramite.setEstiloTxtEstatus("estatus-en-captura-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_PENDIENTE_PAGO) {
	            tramite.setEstiloEstatus("estatus-pendiente-pago");
	            tramite.setEstiloTxtEstatus("estatus-pendiente-pago-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO) {
	            tramite.setEstiloEstatus("estatus-enviado");
	            tramite.setEstiloTxtEstatus("estatus-enviado-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_CORRECIONES) {
	            tramite.setEstiloEstatus("estatus-en-correcciones");
	            tramite.setEstiloTxtEstatus("estatus-en-correcciones-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_CORREGIDO) {
	            tramite.setEstiloEstatus("estatus-corregido");
	            tramite.setEstiloTxtEstatus("estatus-corregido-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO) {
	            tramite.setEstiloEstatus("estatus-rechazado");
	            tramite.setEstiloTxtEstatus("estatus-rechazado-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO) {
	            tramite.setEstiloEstatus("estatus-aprobado");
	            tramite.setEstiloTxtEstatus("estatus-aprobado-txt");
	        }
	        asignarEstiloEstatusLCTramite(tramite);
	        
	        tramite.setProyectoDTO(formularioApplication.getProyectoDTO());
	    });
	}
	
	/**
	 * Método auxiliar que verifica si el trámite ya cuenta con su firma generada, solo se consulta firma
	 * de trámites con estatus Aceptado o rechazado
	 * 
	 * 24/05/2025
	 * Se integra nuevo flujo en el que puede firmar un ciudadano como parte del registro de trámites, entonces esta validación 
	 * ahora preguntará si el trámite ya cuenta con una firma que en la bandera firma_ciudadano sea false (Revisa si hay firma de usuario back)
	 */
	private void consultarFirmaTramite() {
		lstTramites.forEach(tramite -> {
			if (tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO 
					|| tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO) {
				try {
					tramite.setTramiteFirmado(BeanUtils.isNull(formularioDAO.consultarFirmaTramite(tramite)) ? false : true);
				} catch (Exception e) {
					LOGGER.error("Ocurrió un error al consultar firma de trámites : ", e);
					WebResources.errorMessage("msj_error_consulta_firma_tramite", true);
				}
			}		
		});
	}

	/**
	 * Método que realiza la búsqueda de trámites con los filtros ingresados.
	 */
	public void filtrarTramites() {
		//Se valida si existe tabla trámites
		if(estructuraFormularioDAO.existeTablaTramites()) {
			boolean isFiltrosCorrectos = true;
			
			if (BeanUtils.isNotNull(tramiteBusqueda.getFechaDesde())
					&& BeanUtils.isNotNull(tramiteBusqueda.getFechaHasta())) {
				if (tramiteBusqueda.getFechaDesde().after(tramiteBusqueda.getFechaHasta())) {
					isFiltrosCorrectos = false;
					WebResources.validationMessage("msj_fechas_incorrectas", false);
				}
			}
			if (isFiltrosCorrectos) {
				try {
					if(authBean.isRolOperador()) {
						tramiteBusqueda.setUsuarioRevisor(authBean.getUsuarioLogueado());
					}
					
					//Se obtiene informacion necesaria para filtrar la consulta de solicitudes por la distribucion
					obtenerInformacionDistribucionParaFiltrado();
					
					ConsultaTramiteDTO consulta = new ConsultaTramiteDTO(authBean.isRolOperador(), authBean.isRolSupervisor(), 
							authBean.isRolConsulta(), seccionesProyectoBean.isHabilitaDistribucion(), lstElementosDistribucion, 
							detAsignacionDistribucionDTO.getComponenteDistribucionDTO().getIdComponente(), 
							detAsignacionDistribucionDTO.getComponenteDistribucionDTO().getCatTipoComponenteDTO().getIdTipoComponente(),
							obtenerNombreTablaComponenteDistribucion());
					lstTramites = formularioDAO.consultarTamites(tramiteBusqueda, consulta);
					if (BeanUtils.isNull(lstTramites) || lstTramites.isEmpty()) {
						WebResources.validationMessage("msj_busqueda_sin_resultados", true);
					} else {
						asignarEstiloTramite();
						//Si el proyecto permite firmado, se revisa si los trámites cuentan con firma
						if(seccionesProyectoBean.isHabilitarFirmadoTramites()) {
							consultarFirmaTramite();	
						}	
					}
				} catch (Exception e) {
					LOGGER.error("Ocurrió un error al filtrar los trámites:: ", e);
					WebResources.errorMessage("msj_error_busqueda", true);
				}
			}		
		} else {
			WebResources.validationMessage("msj_no_tramites_registrados", true);
		}		
	}
	
	/**
	 * Método auxiliar para obtener el nombre de la tabla a la que 
	 * pertenece el componente utilizado para la distribución de 
	 * solicitudes.
	 * @return
	 */
	private String obtenerNombreTablaComponenteDistribucion() {
		
		if (detAsignacionDistribucionDTO == null || detAsignacionDistribucionDTO.getComponenteDistribucionDTO() == null) {
			return Constantes.EMPTY_STRING;
		}
		
		String nombreTabla = Constantes.EMPTY_STRING;
		//Se valida si el proyecto actual cuenta con distribución configurada
		if(BeanUtils.isNotNull(seccionesProyectoBean.getDetDistribucionDTO()) && BeanUtils.isNotNull(seccionesProyectoBean.getDetDistribucionDTO().isActivo())
				&& seccionesProyectoBean.getDetDistribucionDTO().isActivo()) {
			List<DetAsignacionDistribucionDTO> lstAsignacionesTmp = detAsignacionDistribucionDAO.buscarPorIdUsuarioAsignadoAndComponente(
					authBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx(),
					seccionesProyectoBean.getDetDistribucionDTO().getComponenteDTO().getIdComponente());
			
			if((authBean.isRolSupervisor() && BeanUtils.isNotEmpty(lstAsignacionesTmp)) || (authBean.isRolOperador() && BeanUtils.isNotEmpty(lstAsignacionesTmp)) || (authBean.isRolConsulta() && BeanUtils.isNotEmpty(lstAsignacionesTmp))) {
				List<ControlComponentesDTO> lstControlComponentes = formularioApplication.getLstControlComponentesDTO();
				for (ControlComponentesDTO controlComponentesDTO : lstControlComponentes) {
					if(controlComponentesDTO.getIdComponente().longValue() == detAsignacionDistribucionDTO.getComponenteDistribucionDTO().getIdComponente()) {
						nombreTabla = controlComponentesDTO.getNombreTabla();
						break;
					} 
				}
			}
		}
		return nombreTabla;
	}
	
	/**
	 * Método auxiliar que inicializa los valores de los filtrós de búsqueda de la bandeja de validación
	 * de trámites.
	 */
	public void limpiarFiltros() {
		inicializarValoresBusqueda();
		filtrarTramites();
	}
	
	/**
	 * Método que inicializa los valores que son utilizados en la búsqueda de Trámites
	 */
	private void inicializarValoresBusqueda() {
		tramiteBusqueda.setFechaDesde(null);
		tramiteBusqueda.setFechaHasta(null);
		tramiteBusqueda.setCatEstatusTramiteDTO(new CatEstatusTramiteDTO());
		tramiteBusqueda.setFolioSeguimiento(null);
		tramiteBusqueda.getUsuario().setCurp(null);
		fechaMinima = new Date();
		fechaMaxima = new Date();
		fechaActual = new Date();
	}

	/**
	 * Método axiliar que se ejecuta al seleccionar un operado y setea la información completa del operador.
	 */
	public void listenerUsuarioOperador() {
		for(int i = 0; i < lstUsuariosOperadoresActivos.size(); i++) {        	
        	if(lstUsuariosOperadoresActivos.get(i).getIdUsuarioLlaveCdmx() == usuarioOperadorDTO.getIdUsuarioLlaveCdmx()) {
        		usuarioOperadorDTO.setCorreo(lstUsuariosOperadoresActivos.get(i).getCorreo());
        		usuarioOperadorDTO.setCurp(lstUsuariosOperadoresActivos.get(i).getCurp());
        		usuarioOperadorDTO.setNombre(lstUsuariosOperadoresActivos.get(i).getNombre());
        		usuarioOperadorDTO.setPrimerApellido(lstUsuariosOperadoresActivos.get(i).getPrimerApellido());
        		usuarioOperadorDTO.setSegundoApellido(lstUsuariosOperadoresActivos.get(i).getSegundoApellido());
        		usuarioOperadorDTO.setTelefono(lstUsuariosOperadoresActivos.get(i).getTelefono());
        		break;
        	}
        }
    }
	
	/**
	 * Método para actualizar el usuario representante de los centros de trabajo
	 **/
    public String asignarOperadorTramites() {
    	PrimeFaces current = PrimeFaces.current();
    	String redirect = Constantes.RETURN_SAME_PAGE;
    	BitAsignacionRevisorTramiteDTO bitAsignacionRevisorTramiteDTO = null;
    	try {
	    	if (usuarioOperadorDTO != null && usuarioOperadorDTO.getIdUsuarioLlaveCdmx() != 0) {
	    		backofficeFacade.guardarActualizarUsuarioOperador(usuarioOperadorDTO); 
	    		
	    		if (lstTramitesSeleccionados != null && !lstTramitesSeleccionados.isEmpty()) {
	    			
					for (TramiteDTO tramiteSeleccionado : lstTramitesSeleccionados) {
						// Se setea el usuario Operador al trámite
						tramiteSeleccionado.setUsuarioOperador(usuarioOperadorDTO);
						
						//Se setea al trámite el usuario revisor asignado
						tramiteSeleccionado.setUsuarioRevisor(usuarioOperadorDTO);
						
						//Se inicializa el objeto bitAsignacionRevisorTramiteDTO con la infromación necesaria para guardar en la BD el movimeinto
						bitAsignacionRevisorTramiteDTO = new BitAsignacionRevisorTramiteDTO();
						bitAsignacionRevisorTramiteDTO.setUsuarioAsignaDTO(authBean.getUsuarioLogueado());
						bitAsignacionRevisorTramiteDTO.setUsuarioRevisorDTO(usuarioOperadorDTO);
						bitAsignacionRevisorTramiteDTO.setTamiteDTO(tramiteSeleccionado);
						bitAsignacionRevisorTramiteDTO.setFechaAsignacion(new Date());
						
						backofficeFacade.registrarAsignacionUsuarioRevisorDeTramite(tramiteSeleccionado, bitAsignacionRevisorTramiteDTO);
					}
					inicializar();
					current.executeScript("PF('dtbTramites').unselectAllRows();");
					this.usuarioOperadorDTO = new UsuarioDTO();
					WebResources.addSuccessMessage("asignacion_exitosa", true);
					return redirect;
				} else {
					WebResources.addValidationMessage("no_selecciono_tramite", true);
					return redirect;
				}
			} else {
	    		WebResources.addValidationMessage("no_selecciono_usuario", true);
	    		return redirect;
			}
    	} catch (Exception e) {
			LOGGER.error("Error desconocido al registrar la asignación del usuario revisor: ", e);
			WebResources.addErrorMessage("error_al_asignar_revisor", true);
			return redirect;
		}
	}   
    
	/**
	 * Método auxiar que filtra los trámites de la bandeja que cumplen con la regla para poderse firmar.
	 * 1.- Los únicos estatus de trámtes que es posible realizar su firma son 6.- Rechazado y 7.- Aprobado.
	 * 2.- Para poder firmarse, el trámite no debe contar con registro de firmado.  
	 **/
    public void iniciarModalFirmadoTramites() {
    	if(BeanUtils.isNotNull(lstTramites) && BeanUtils.isNotEmpty(lstTramites)) {
    		lstTramitesFirma = new ArrayList<TramiteDTO>();		
    		lstTramitesSeleccionados = null;
    		
    		for(TramiteDTO tramiteTemp : lstTramites) {
    			if((tramiteTemp.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO ||
    					tramiteTemp.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO) &&
    					tramiteTemp.isTramiteFirmado() == false) {
    				lstTramitesFirma.add(tramiteTemp);
    			}
    		}				
    		if(BeanUtils.isNotEmpty(lstTramitesFirma)) {
    			PrimeFaces current = PrimeFaces.current();
    			current.executeScript("PF('mdlFirmaTramites').show();");	
    		} else {
    			WebResources.addValidationMessage("msj_sin_tramites_firmar", true);
    		}    		
    	} else {
    		WebResources.addValidationMessage("msj_sin_tramites_seleccionados_firma", true);
    	}		
	}    
    
	/**
	 * Método auxiliar que evalua si la configuración del proyecto permite realizar firma de trámites, además
	 * evalua si el rol de Supervisor u Operador se encuentra habilitado para realizar firmado de trámites.
	 * 
	 * @return
	 */
	public boolean validaPermisosFirmado() {
		boolean permiteFirmado = false;
		if(BeanUtils.isNotNull(seccionesProyectoBean.getArchivosRespuestaTokenAceptacionDTO()) || 
				BeanUtils.isNotNull(seccionesProyectoBean.getArchivosRespuestaTokenRechazoDTO())) {
			if(seccionesProyectoBean.isHabilitarFirmadoTramites()) {
				if(authBean.isRolSupervisor() &&
						(seccionesProyectoBean.getArchivosRespuestaTokenAceptacionDTO().isFirmaSupervisor() || 
								seccionesProyectoBean.getArchivosRespuestaTokenRechazoDTO().isFirmaSupervisor())) {
					permiteFirmado = true;
				}
				
				if(authBean.isRolOperador() && 
						(seccionesProyectoBean.getArchivosRespuestaTokenAceptacionDTO().isFirmaOperador() || 
								seccionesProyectoBean.getArchivosRespuestaTokenRechazoDTO().isFirmaOperador())) {					
					permiteFirmado = true;
				}
			}				
		}		
		return permiteFirmado;
	}
	
	/**
	 * Método auxiliar que evalua si el rol (Administrador, Supervisor, Operador o Consulta) se encuentra habilitado para realizar 
	 * la descarga del archivo final.
	 * 
	 * @return
	 */
	public boolean validaPermisosDescargaArchivo() {
		boolean permiteDescarga = false;
		if(BeanUtils.isNotNull(seccionesProyectoBean.getArchivosRespuestaTokenAceptacionDTO()) || 
				BeanUtils.isNotNull(seccionesProyectoBean.getArchivosRespuestaTokenRechazoDTO())) {
			if(authBean.isRolAdministrador() || authBean.isRolAdministradorDatosTecnicos() || authBean.isRolSupervisor() || authBean.isRolOperador() || authBean.isRolConsulta()) {
				permiteDescarga = true;
			}				
		}		
		return permiteDescarga;
	}
	
	/**
	 * Método auxiliar que inicia el proceso de firmado masivo de los trámites seleccionados.
	 * @return
	 */
	public String iniciarFirmadoTramites() {
		String redirect = Constantes.RETURN_SAME_PAGE;
		if(BeanUtils.isNotNull(lstTramitesSelFirma) && BeanUtils.isNotEmpty(lstTramitesSelFirma)) {
			if(lstTramitesSelFirma.size() != Constantes.INT_MINIMO_TRAMITE_FIRMADO) {
				if(lstTramitesSelFirma.size() <= Constantes.INT_MAX_TRAMITES_FIRMADO) {
					/**Paso 1, se realiza el registro del trámite para firmado.**/
					if(registrarFirmaTramites()) {
						/**Paso 2, se construye el redireccionamiento para iniciar pasarela de firmado.
						 * 
						 * 26/06/2026 WEBHOOK
						 * Se anexa setear el listado de tramites a firmar para poder usar
						 **/
						registrarFormularioBean.setRespuestaFirmaTramite(respuestaFirmaTramite);
						registrarFormularioBean.setLstTramitesSelFirma(lstTramitesSelFirma);
						//se apaga la banderas activas
						registrarFormularioBean.setFirmaTramiteCiudadano(false);
						registrarFormularioBean.setFirmaBandeja(false);
						registrarFormularioBean.setFirmaTramiteCiudadano(false);
						redirectUrlFirmaCDMX();	
					} else {
						PrimeFaces current = PrimeFaces.current();
						current.executeScript("PF('mdlFirmaMx').show();");
					}				
				} else {
					ocultaModalFirmado("msj_maximo_tramites_firmar");
				}
			}else {
				ocultaModalFirmado("msj_minimo_tramites_firmar");
			}
		} else {
			ocultaModalFirmado("msj_sin_tramites_seleccionados_firma");		
		}	
		
		return redirect; 
	}
	
	/**
	 * Metodo auxiliar para ocultar el modal de firmado
	 * 
	 * @param mensaje
	 */
	private void ocultaModalFirmado(String mensaje) {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('mdlFirmaTramites').hide();");
		WebResources.validationMessage(mensaje, false);
	}
	
	/**
	 * Método que envía a Firma la información de trámites que será firmada.
	 * @return
	 */
	public boolean registrarFirmaTramites() {
		boolean registroFirmaCorrecto = false;
		FirmaRESTClient firmaClient = new FirmaRESTClient();
		respuestaFirmaTramite = null;
		List<FirmaTramiteDTO> lstTramitesFirma = new ArrayList<FirmaTramiteDTO>();
		
		ProyectoDTO proyectoDTO = generarFormularioApplication.getProyectoDTO();
		UsuarioDTO usuarioFirmanteDTO = usuarioDAO.buscarPorId(authBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx());
		
		for(TramiteDTO tramiteActual: lstTramitesSelFirma) {
			FirmaTramiteDTO firmaTramite = new FirmaTramiteDTO();		
			firmaTramite.setTramite(tramiteActual);
			firmaTramite.getTramite().setProyectoDTO(proyectoDTO);
			if (BeanUtils.isNotNull(tramiteActual.getUsuario().getIdUsuarioLlaveCdmx())) {				
				firmaTramite.setUsuarioTramite(usuarioDAO.buscarPorId(tramiteActual.getUsuario().getIdUsuarioLlaveCdmx()));
			} else {
				firmaTramite.setUsuarioTramite(null);
			}
			firmaTramite.setUsuarioFirmante(usuarioFirmanteDTO);
			firmaTramite.setFechaFirmado(new Date());
			
			lstTramitesFirma.add(firmaTramite);
		}
		
		try {
			respuestaFirmaTramite = firmaClient.firmarRespuesta(lstTramitesFirma, seccionesProyectoBean.getFirmaDTO());
			registroFirmaCorrecto = true;			
		} catch (URISyntaxException | JSONException | InterpreteException e) {
			LOGGER.error("Ocurrió un error al registrar listado de trámites para firmado : ", e);
		}
		
		return registroFirmaCorrecto;
	}
	
	/**
	 * Método auxiliar que construye la ULR con la que se solicitará el registro para el firmado de trámites		
	 * @return
	 */
	public void redirectUrlFirmaCDMX() {
		StringBuilder urlFirmaCDMX = new StringBuilder();
		respuestaFirmaTramite.setState(Utils.randomCharsStateFirma().toString());
		urlFirmaCDMX.append(seccionesProyectoBean.getFirmaDTO().getUrlFirmado()).append("?")
			.append(Constantes.PARAM_FIRMA_CLIENT_ID).append("=")
			.append(seccionesProyectoBean.getFirmaDTO().getClaveSistema()).append("&")
			.append(Constantes.PARAM_FIRMA_TRAMITE_ID).append("=")
			.append(respuestaFirmaTramite.getIdSolicitud()).append("&")
			.append(Constantes.PARAM_FIRMA_REDIRECT).append("=")
			.append(seccionesProyectoBean.getFirmaDTO().getUrlRedirecciona()).append("&")
			.append(Constantes.PARAM_STATE).append("=").append(respuestaFirmaTramite.getState())
			.toString();
		try {
			facesContext.getExternalContext().redirect(urlFirmaCDMX.toString());
		} catch (IOException e) {
			WebResources.addErrorMessage("msj_error_redirect_firma_masiva_tramites", false);
			LOGGER.error("Ocurrio un error al generar el redirect para firma MX masiva:", e);
		}
	}	
	
	/**
	 * Método que realizará la generación de un archivo después de haber sido validado o firmado, este método genera el archivo
	 * final que el ciudadano descarga desde su bandeja de trámites.
	 * 
	 * Los archivos que pueden generarse son 3:
	 * 
	 * 1.- Si la configuración de archivos de respuesta se encuentra habilitada para utilizar el firmado, se tendrán 2 posibles 
	 * 		archivos de respuesta con su respectiva plantilla, uno será para el trámites "Aceptados" y la segunda para trámites 
	 * 		"Rechazados", desde el motor se valida que cuando es utilizado el firmado, por fuerza sean sincronizadas las 2 plantillas.
	 * 
	 * 2.- Si la configuración de archivos de respuesta no tiene habilitado el firmado, solo se tendrá 1 posible archivo de respuesta
	 * 		o plantilla para la generación del archivo final no importando si el estatus del trámite es "Aprobado" o "Rechazado".
	 * 
	 * 26/06/2026 WEBHOOK
	 * Este metodo sirvio de base para generar el metodo NotificacionWebHookFacade.generarComprobanteFinalizado
	 * cualquier cambio que sufra respecto a la generacion de comprobantes replicar la logica en dicho metodo
	 * @return
	 * @throws IOException
	 */
	public void generarArchivo(TramiteDTO tramiteActual) {
		FormatoRespuestaPDF formatoPDF = new FormatoRespuestaPDF();
		try {
			/**Dependiendo del estatus del trámite, se tomará el archivo respuesta para la descarga **/			
			ArchivosRespuestaTokenDTO archivoRespuesta = null;
			if(seccionesProyectoBean.isHabilitarFirmadoTramites()) {
				if(tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO) {
					archivoRespuesta = seccionesProyectoBean.getArchivosRespuestaTokenAceptacionDTO();
				}			
				if(tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO) {
					archivoRespuesta = seccionesProyectoBean.getArchivosRespuestaTokenRechazoDTO();
				}				
			} else {
				archivoRespuesta = seccionesProyectoBean.getArchivosRespuestaTokenRegistroDTO(); 
			}
			
			List<DetElementosTokenDTO> lstDetElementosTokenDTO = detElementosTokenDAO.buscarPorIdArchivoRespuesta(archivoRespuesta.getIdArchivoRespuesta());
			if(archivoRespuesta != null && lstDetElementosTokenDTO != null) {
				Map<String, ControlComponentesDTO> mapControlComponentes = formularioApplication.getMapControlComponentes();
				List<SeccionesFormularioDTO> lstSeccionesDTO = formularioApplication.generarListaSecciones();
				Map<String, Object> mapRespuestas = new HashMap<>();
				mapRespuestas = cargarDatosRespuesta(tramiteActual, mapControlComponentes, lstSeccionesDTO, lstDetElementosTokenDTO);	
				
//				if(!mapRespuestas.isEmpty()) {
					List<TramiteFirmaElectronicaDTO> lstFirma = null;
					if (seccionesProyectoBean.isHabilitarFirmadoTramites()) {
						lstFirma = formularioDAO.consultarFirmaTramite(tramiteActual);
						formatoPDF.generarDocumento(seccionesProyectoBean.getProyectoDTO().getIdProyecto(), archivoRespuesta.getRutaArchivoRespuesta(), mapRespuestas, tramiteActual, lstDetElementosTokenDTO, seccionesProyectoBean.isHabilitarFirmadoTramites(), 
								archivoRespuesta, seccionesProyectoBean.getSecurityDomainDTO().getUrlSistema(), lstFirma.get(0), mapControlComponentes, lstSeccionesDTO);
					} else {
						formatoPDF.generarDocumento(seccionesProyectoBean.getProyectoDTO().getIdProyecto(), archivoRespuesta.getRutaArchivoRespuesta(), mapRespuestas, tramiteActual, lstDetElementosTokenDTO, seccionesProyectoBean.isHabilitarFirmadoTramites(), 
								archivoRespuesta, null, null, mapControlComponentes, lstSeccionesDTO);
					}
					String directorioPDF = Environment.getPathPlantillasClientePdf()+ seccionesProyectoBean.getProyectoDTO().getIdProyecto()+ "/" + "formatosRespuesta/";
					String extensionPDF = ".pdf";
					File pdf = new File(directorioPDF + tramiteActual.getFolioSeguimiento() + extensionPDF);
					if (pdf.exists()) {
						FacesContext facesContext = FacesContext.getCurrentInstance();
						HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
						response.reset();
						response.setHeader("Content-Type", "application/pdf");
						response.setHeader("Content-Disposition", "attachment;filename=" + pdf.getName());

						OutputStream responseOutputStream = response.getOutputStream();
						InputStream fileInputStream = new FileInputStream(pdf);

						byte[] bytesBuffer = new byte[2048];
						int bytesRead;
						try {
							while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
								responseOutputStream.write(bytesBuffer, 0, bytesRead);
							}
							responseOutputStream.flush();
							fileInputStream.close();
							responseOutputStream.close();
							facesContext.responseComplete();
							WebResources.successMessage("msj_comprobante_generado", false);
						} catch (Exception e) {
							WebResources.addValidationMessage("msj_error_comprobante", false);	
							LOGGER.error("Ocurrió un error al descargar el documento: ", e);
						} finally {
							if(fileInputStream != null) {
								try {
									fileInputStream.close();
								} catch (Exception e2) {
									LOGGER.error("Ocurrió un error al cerrar el recurso fileInputStream ", e2);
								}
							}
						}
					} else {
						WebResources.addValidationMessage("msj_no_comprobante", false);	
					}					
//				} else {					
//					WebResources.addValidationMessage("msj_sin_datos_comprobante", false);
//				}
			} else {
				WebResources.addValidationMessage("msj_error_conf_comprobante", false);
			}
		} catch (Exception e) {
			LOGGER.error("No se encontró el PDF para la descarga del tramite desde bandeja del funcionario " + tramiteActual.getFolioSeguimiento(), e);
			WebResources.addErrorMessage("msj_error_comprobante", false);
		}		
	}
	
	public void generarResolucion(TramiteDTO tramiteActual) {
		File pdfR = new File(tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == 9 ? 
				tramiteActual.getRutaDocumentoResolucionPositiva() : tramiteActual.getRutaDocumentoResolucionNegativa());
		try {
			
			if (pdfR.exists()) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				HttpServletResponse responseR = (HttpServletResponse) facesContext.getExternalContext().getResponse();
				responseR.reset();
				responseR.setHeader("Content-Type", "application/pdf");
				responseR.setHeader("Content-Disposition", "attachment;filename=" + pdfR.getName());
				
				OutputStream responseOutputStream = responseR.getOutputStream();
				InputStream fileInputStream = new FileInputStream(pdfR);
				byte[] bytesBuffer = new byte[2048];
				int bytesRead;
				
				try {
					while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
						responseOutputStream.write(bytesBuffer, 0, bytesRead);
					}
					responseOutputStream.flush();
					fileInputStream.close();
					responseOutputStream.close();
					facesContext.responseComplete();
					WebResources.successMessage("msj_resolucion_generada", false);
					
				} catch (Exception e) {
					WebResources.addValidationMessage("msj_error_resolucion", false);	
					LOGGER.error("Ocurrió un error al descargar la resolución: ", e);
				} finally {
					if(fileInputStream != null) {
						try {
							fileInputStream.close();
						} catch (Exception e2) {
							LOGGER.error("Ocurrió un error al cerrar el recurso fileInputStream ", e2);
						}
					}
				}
			} else {
				WebResources.addValidationMessage("msj_error_resolucion", false);	
			}
		} catch (Exception e) {
			LOGGER.error("No se encontró el PDF de la Resolución para la descarga desde bandeja del funcionario " + tramiteActual.getFolioSeguimiento(), e);
			WebResources.addErrorMessage("msj_error_resolucion", false);
		}		

	}
	
	/**
	 * Metodo que se utiliza para recuperar las respuestas de un formulario
	 **/
	private Map<String, Object> cargarDatosRespuesta(TramiteDTO tramiteActual, Map<String, 
			ControlComponentesDTO> mapControlComponentes, List<SeccionesFormularioDTO> lstSeccionesDTO,
			List<DetElementosTokenDTO> lstDetElementosTokenDTO) throws Exception {
		
		Map<String, Object> mapRespuestas = new HashMap<>();
		
		/**
		 * Se consulta las respuestas del formulario sección por sección
		 **/
		for (SeccionesFormularioDTO seccionTemp : lstSeccionesDTO) {
			
			List<String> lstColumnas = new ArrayList<String>();
			for (Iterator<Map.Entry<String, ControlComponentesDTO>> elementos = mapControlComponentes.entrySet()
					.iterator(); elementos.hasNext();) {
				Map.Entry<String, ControlComponentesDTO> elementoTmp = elementos.next();
				if (elementoTmp.getValue().getNombreTabla().equals(
						Constantes.NOMBRE_BASE_TABLAS.concat(seccionTemp.getIdSeccionFormulario().toString()))) {
					lstColumnas.add(elementoTmp.getValue().getNombreColumna());
				}
			}
			
			/**
			 * Se verifica que la sección no contenga únicamente Componentes informativos,
			 * dichos componentes no insertan datos en la BD
			 **/
			if (lstColumnas.size() > 0) {
				
				//Se iteran las columnas y solo se consultan aquellas que coinciden con un token a localizar
				for(int c = 0; c < lstColumnas.size(); c ++) {
					for(int t = 0; t < lstDetElementosTokenDTO.size(); t ++) {
							
							if(BeanUtils.isNotNull(lstDetElementosTokenDTO.get(t).getIdComponente())
									&& ( lstColumnas.get(c).equals("componente_".concat(lstDetElementosTokenDTO.get(t).getIdComponente().toString()))
											|| lstColumnas.get(c).equals("componente_".concat(lstDetElementosTokenDTO.get(t).getIdComponente().toString()).concat("_1"))
													|| lstColumnas.get(c).equals("componente_".concat(lstDetElementosTokenDTO.get(t).getIdComponente().toString()).concat("_2"))
															|| lstColumnas.get(c).equals("componente_".concat(lstDetElementosTokenDTO.get(t).getIdComponente().toString()).concat("_3")))) {	
							
							
							/**
							 * Se consulta si existe información de la sección para el trámite actual, esto
							 * se puede dar por trámites existentes a los que se les habilita una nueva
							 * sección que anteriormente no fue registrada
							 **/
							if (formularioDAO.existeRegistroSeccionPorTramite(tramiteActual, 
									Constantes.ESQUEMA_INTERPRETE.concat(".").concat(Constantes.NOMBRE_BASE_TABLAS
											.concat(seccionTemp.getIdSeccionFormulario().toString())))) {

								/**
								 * Se consultar la información registrada en Base de datos de la Sección actual
								 **/
								Map<String, Object> respuestasFormulario = formularioDAO.consultarRespuestasSeccion(tramiteActual,
										Constantes.ESQUEMA_INTERPRETE.concat(".")
												.concat(Constantes.NOMBRE_BASE_TABLAS
														.concat(seccionTemp.getIdSeccionFormulario().toString())),
										mapControlComponentes, lstColumnas);

								/**
								 * Se coloca la información recuperada en el map de respuestas
								 **/
								for (Entry<String, Object> respuestaTemp : respuestasFormulario.entrySet()) {
									mapRespuestas.put(respuestaTemp.getKey(), respuestaTemp.getValue());
								}
							}							
						}						
					}
				}				
			} 
		}
		return mapRespuestas;
	}
	
	/**
	 * Método auxiliar que evalua si en la bandeja del usuario back existe algún trámite con estatus 4 - En corrección,
	 * 6 - Rechazado o 7 - Aceptado.
	 * 
	 * @return
	 */
	public boolean mostrarColumnaAcciones() {
		boolean mostrarColumna = false;
		if(BeanUtils.isNotNull(lstTramites) && BeanUtils.isNotEmpty(lstTramites)) {
			for(TramiteDTO tmp : lstTramites) {
				if (tmp.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_CORRECIONES || 
						tmp.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO ||
								tmp.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO) {				
					mostrarColumna = true;
					break;
				}
			}			
		}
		return mostrarColumna;
	}
	
	/**
	 * Método auxiliar que evalua si en la bandeja del usuario back se habilita la opción para revertir estatus de trámites.
	 * 
	 * @return
	 */
	public boolean mostrarOpcionRevertir() {
		boolean mostrarOpcion = false;
		if(authBean.isRolAdministrador() || authBean.isRolAdministradorDatosTecnicos()) {
			mostrarOpcion = true;
		}		
		return mostrarOpcion;
	}	
	
	/**
	 * Método auxiliar que evalua si en la bandeja del usuario back se habilita la opción para revertir estatus de trámites.
	 * 
	 * @return
	 */
	public boolean mostrarOpcionRevocarAviso() {
		boolean mostrarOpcion = false;
		if(authBean.isRolAdministrador() || authBean.isRolAdministradorDatosTecnicos() || authBean.isRolSupervisor()) {
			mostrarOpcion = true;
		}		
		return mostrarOpcion;
	}
	
	/**
	 * Método que muestra la modal para revertir estatus al trámite 
	 */
	public void mostrarModalRevertirEstatus(TramiteDTO tramite) {
		tramiteSeleccionado = new TramiteDTO();
		tramiteSeleccionado = tramite;
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalRevertirEstatus').show();");
	}
	
	/**
	 * Método que cierra la modal para revertir estatus al trámite 
	 */
	public void cerrarModalRevertirEstatus() {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalRevertirEstatus').hide();");
	}
	
	/**
	 * Método que muestra la modal para revocar el aviso 
	 */
	public void mostrarModalRevocarAviso(TramiteDTO tramite) {
		tramiteSeleccionado = new TramiteDTO();
		tramiteSeleccionado = tramite;
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalRevocarAviso').show();");
	}
	
	/**
	 * Método que muestra la modal para revocar el aviso 
	 */
	public void mostrarModalVerRevocacion(TramiteDTO tramite) {
		tramiteSeleccionado = new TramiteDTO();
		tramiteSeleccionado = tramite;
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalVerRevocacion').show();");
	}
	
	/**
	 * Método que cierra la modal para revocar el aviso 
	 */
	public void cerrarModalRevocarAviso() {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalRevocarAviso').hide();");
	}
	
	/**
	 * Método que cierra la modal para revocar el aviso 
	 */
	public void cerrarModalVerRevocacion() {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalVerRevocacion').hide();");
	}
	
	/**
	 * Método auxiliar para mostrar en la modal el nombre del documento cargado
	 * 
	 * @param rutaArchivo
	 * @return
	 */
	public String obtenerFileName(String rutaArchivo) {
		if (rutaArchivo != null && !rutaArchivo.isEmpty()) {
			return FilenameUtils.getName(rutaArchivo);
		} else {
			return Constantes.EMPTY_STRING;
		}
	}
	
	/**
	 * Método auxiliar que eliminá un archivo cargado como prevención
	 * 
	 * @param archivo
	 */
	public void eliminarArchivoCargado(String rutaArchivo) {		
		File documento = new File(rutaArchivo);
		try {
			if (!documento.delete()) {
				LOGGER.warn("El documento no se pudo eliminar");
			}
			tramiteSeleccionado.setRutaDocumentoRevocado(null);
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al querer eliminar el documento: ", e);
		}
	}
	
	/**
	 * Método auxiliar para la carga del documento utilizado como prevención
	 * 
	 * @param event
	 */
	public void subirArchivo(FileUploadEvent event) {
		try {
			if (event.getFile() != null) {
				// Se copia el documento cargado en el filesystem
				tramiteSeleccionado.setRutaDocumentoRevocado(copiarDocumento(FilenameUtils.getExtension(event.getFile().getFileName()),
						event.getFile().getInputStream(), Environment.getPathClienteDocumentos()));
			}
		} catch (IOException e) {
			LOGGER.error("Ocurrió un error al intentar copiar el docuento en el filesystem: ", e);
		}
	}
	
	/**
	 * Método private auxiliar para copiar un documento cargado en el filesystem
	 * 
	 * @param extension
	 * @param documento
	 * @param destinoDoc
	 * @return
	 */
	private String copiarDocumento(String extension, InputStream documento, String destinoDoc) {
		File folder = new File(destinoDoc);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		OutputStream out = null;
		try {
			destinoDoc += UUID.randomUUID().toString() + "." + extension;
			out = new FileOutputStream(new File(destinoDoc));
			int read = Constantes.INT_VALOR_CERO;
			byte[] bytes = new byte[Constantes.TAMAÑO_BUFFER];
			while ((read = documento.read(bytes)) != -1) {
				out.write(bytes, Constantes.INT_VALOR_CERO, read);
			}
			documento.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			LOGGER.error("Problemas al copiar el archivo: ", e);
		}  finally {
			if (out != null) {
				try {
					out.close();
				} catch(Exception e) {
					LOGGER.warn("No se pudo cerrar de manera correcta el outputstream de copiarDocumento - RegistrarFormularioBean");
				}
			}
		}
		return destinoDoc;
	}
	
	/**
	 * Método para revertir el estatus de un trámite con estatus 4 - En corrección, 6 - Rechazado, o 7 - Aceptado  a estatus 3 - Enviado.
	 * 
	 * 15/07/2026
	 * Se agrega la notificacion via webhook
	 **/
    public String revertirEstatusTramite() {
    	String redirect = Constantes.RETURN_SAME_PAGE;    	
    	
    	if(permiteRevertirEstatus()) {
    		//1. 
    		CatEstatusTramiteDTO estatusTramite = tramiteSeleccionado.getCatEstatusTramiteDTO();
    		//1. guardar registro en bitacora de reversion de estatus
    		BitRevertirEstatusDTO bitacoraRevertir = new BitRevertirEstatusDTO();
        	bitacoraRevertir.setTramite(tramiteSeleccionado);
        	bitacoraRevertir.setUsuarioDTO(authBean.getUsuarioLogueado());
        	bitacoraRevertir.setEstatusTramiteDTO(estatusTramite);
        	if(estatusTramite.getIdEstatusTramite() == Constantes.ID_ESTATUS_CORRECIONES) {
        		bitacoraRevertir.setRespuestaPrevencionConclusion(tramiteSeleccionado.getRespuestaFolioPrevencion());
        	}
        	if(estatusTramite.getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO ||
        			estatusTramite.getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO) {
        		bitacoraRevertir.setRespuestaPrevencionConclusion(tramiteSeleccionado.getRespuestaFolioConclusion());
        	}    
        	bitacoraRevertir.setFechaReversion(new Date());
        	CatEstatusTramiteDTO catEstatusTramiteDTO = catEstatusTramiteDAO.buscarPorIdEstatus(Constantes.ID_ESTATUS_ENVIADO);
			tramiteSeleccionado.setCatEstatusTramiteDTO(catEstatusTramiteDTO);
        	tramitesFacade.revertirEstatusTramite(tramiteSeleccionado, bitacoraRevertir);
        	
        	//2. Se registra movimiento en bitácora del tramite.
    		BitMovimientosTramiteDTO bitacora = new BitMovimientosTramiteDTO();
    		bitacora.setCatTiposMovimientoDTO(new CatTiposMovimientoDTO(Constantes.ID_ACTUALIZACION_ESTATUS));
    		bitacora.setUsuarioDTO(authBean.getUsuarioLogueado());
    		bitacora.setIdTramite(tramiteSeleccionado.getIdTramite());
    		StringBuilder strComentario = new StringBuilder("Se actualiza estatus a : ");
    			strComentario.append(tramiteSeleccionado.getCatEstatusTramiteDTO().getIdEstatusTramite())
    						.append(". Observaciones: Revocación de estatus ")
    						.append(BeanUtils.isNotNull(estatusTramite.getDescripcionPersonalizada())?estatusTramite.getDescripcionPersonalizada():estatusTramite.getDescripcion());
    		bitacora.setComentarios(strComentario.toString());
    		bitacora.setFechaMovimiento(new Date());
    		bitMovimientosTramiteDAO.actualizar(bitacora);
    		
    		
        	cerrarModalRevertirEstatus();    	
        	filtrarTramites();
        	
	    	registrarFormularioBean.setTramiteActual(tramiteSeleccionado);
	    	registrarFormularioBean.iniciaProcesoNotificacionWebHook();
	    	
        	WebResources.addValidationMessage("msj_revertir_correcto", true);
    	} else {
    		cerrarModalRevertirEstatus();
    		WebResources.addValidationMessage("msj_no_revertir_estatus", true);
    	}
    	
    	return redirect;
    }	
    
    /**
	 * Método para revocar el aviso que ya se encuentra como expedido
	 * 
	 * 15/07/2026
	 * Se agrega la notificacion via webhook
	 * 
	 **/
    public String revocarAviso() {
    	String redirect = Constantes.RETURN_SAME_PAGE;   
    	try {
	    	if(BeanUtils.isEmpty(tramiteSeleccionado.getRutaDocumentoRevocado())) {
	    		enviarMensajeVista("msj_campo_requerido", "frmRevocarAviso:cdoc_revocacion", FacesMessage.SEVERITY_ERROR);
	    	} else {
	    		if(seccionesProyectoBean.getProyectoDTO().isAviso()) {
	    			
	    			CatEstatusTramiteDTO catEstatusTramiteDTO = catEstatusTramiteDAO.buscarPorIdEstatus(Constantes.ID_ESTATUS_RECHAZADO);
	    			tramiteSeleccionado.setCatEstatusTramiteDTO(catEstatusTramiteDTO);
	        		tramiteSeleccionado.setUsuarioRevisor(authBean.getUsuarioLogueado());
	        		tramiteSeleccionado.setFechaRevision(new Date());
	    			BitRevertirEstatusDTO bitacoraRevertir = new BitRevertirEstatusDTO();
	    	    	bitacoraRevertir.setTramite(tramiteSeleccionado);
	    	    	bitacoraRevertir.setUsuarioDTO(tramiteSeleccionado.getUsuarioRevisor());
	    	    	bitacoraRevertir.setEstatusTramiteDTO(tramiteSeleccionado.getCatEstatusTramiteDTO());
	    	    	bitacoraRevertir.setRespuestaPrevencionConclusion(tramiteSeleccionado.getMotivoRechazo());
	    	    	bitacoraRevertir.setFechaReversion(tramiteSeleccionado.getFechaRevision());
	    	    	
	    	    	tramitesFacade.revocarAviso(tramiteSeleccionado, bitacoraRevertir);
	    	    	DetGestionUsuarioDTO detGestionUsuarioDTO = detGestionUsuariosDAO.buscarPorIdProyecto(seccionesProyectoBean.getProyectoDTO().getIdProyecto());
	    	    	if(seccionesProyectoBean.isAuthenticacionCiudadano()
	    	    			&& BeanUtils.isNotNull(detGestionUsuarioDTO)) {
	    	    		UsuarioDTO usuarioTmp = usuarioDAO.buscarPorId(tramiteSeleccionado.getUsuario().getIdUsuarioLlaveCdmx());
	    	    		//Solo si el usuario cuenta con un correo, entonces se realiza el intento de notificación por correo
	    	    		if(BeanUtils.isNotNull(usuarioTmp) && BeanUtils.isNotNull(usuarioTmp.getCorreo())) {
	    	    			EnvioCorreo correo = new EnvioCorreo();
		        			correo.enviarCorreoNotificacion(
		        					usuarioTmp.getCorreo(), 
		        					detGestionUsuarioDTO.getCorreoRechazado(), 
		        					Constantes.TITULO_REVOCADO, 
		        					seccionesProyectoBean.getProyectoDTO().getNombreProyecto(), 
		        					tramiteSeleccionado, 
		        					seccionesProyectoBean.getSecurityDomainDTO().getUrlSistema(), 
		        					detGestionUsuarioDTO, 
		        					seccionesProyectoBean.getProyectoDTO());	
	    	    		}        	    	
	    	    	}
	    	    	
	    	    	cerrarModalRevocarAviso();   
	    	    	filtrarTramites();
	    	    	
	    	    	registrarFormularioBean.setTramiteActual(tramiteSeleccionado);
	    	    	registrarFormularioBean.iniciaProcesoNotificacionWebHook();
	    	    	
	    	    	WebResources.addSuccessMessage("msj_revertir_correcto", true);
	        	}
	    	}
    	} catch (Exception e) {
			LOGGER.error("Ocurrió un error al realizar la revocación del aviso: ", e);
			WebResources.addValidationMessage("msj_error_revocar", true);
		}
    	return redirect;
    }
    
    /**
	 * Método auxiliar que valida si el usuario actual tiene trámites generados con estatus diferente a Rechazados
	 * @return
	 */
	public boolean permiteRevertirEstatus() {
		boolean permiteRevertir = true;		
		
		if(seccionesProyectoBean.getAccesoLlaveDTO().isAutenticacionCiudadano() && seccionesProyectoBean.getAccesoLlaveDTO().isLimitarUnicoTramite()) {
			/**Se consulta trámites del usuario del trámite**/
			TramiteDTO tramiteBusqueda = new TramiteDTO();
			tramiteBusqueda.setUsuario(new UsuarioDTO(tramiteSeleccionado.getUsuario().getIdUsuarioLlaveCdmx()));
			try {
				List<TramiteDTO> lstTramites = formularioDAO.consultarTamitesUsuario(tramiteBusqueda);
				if(BeanUtils.isNotNull(lstTramites) && 
						lstTramites.size() > Constantes.MAXIMO_TRAMITES_USUARIO) {
					int tramitesNoRechazados = Constantes.INT_VALOR_CERO;
					for (TramiteDTO tramite : lstTramites) {
						if(tramiteSeleccionado.getIdTramite().longValue() != tramite.getIdTramite().longValue()) {
							if(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() != Constantes.ID_ESTATUS_RECHAZADO) {
								tramitesNoRechazados ++;
								if(tramitesNoRechazados >= Constantes.MAXIMO_TRAMITES_USUARIO) {
									permiteRevertir = false;
									break;
								} 										
							}	
						}						 									
					}				
				} 
			} catch (Exception e) {
				permiteRevertir = false;
				LOGGER.error("Ocurrió un error al consultar trámites del usuario actual: ", e);
				WebResources.errorMessage("msj_error_busqueda", true);
			}			
		}		
		return permiteRevertir;
	}
	
	/**
	 * Método auxiliar que verifica si el proyecto maneja distribución, si está habilitada la distribución se verifica
	 * que el usuario actual tenga configurada la distribución para que pueda ingresar a su bandeja.
	 * @return
	 */
	private boolean validarTieneDistribucionAsignada() {
		
		if (authBean.isRolConsulta()) {
			return true;
		}
		
		List<DetAsignacionDistribucionDTO> lstAsignaciones = null;
		boolean tieneDistribucion = true;
		if(seccionesProyectoBean.isHabilitaDistribucion() && (authBean.isRolSupervisor() || authBean.isRolOperador() || authBean.isRolConsulta())) {
			lstAsignaciones = 
					detAsignacionDistribucionDAO.buscarPorIdUsuarioAsignadoAndComponente(
							authBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx(), 
							seccionesProyectoBean.getDetDistribucionDTO().getComponenteDTO().getIdComponente());
			if(BeanUtils.isEmpty(lstAsignaciones)) {
				tieneDistribucion = false;
			}
		}
		
		return tieneDistribucion;
	}
	
	/**
	 * Método privado utilizado para enviar un mensaje a la vista
	 * 
	 * @param mensaje
	 * @param idComponente
	 * @param severity
	 */
	private void enviarMensajeVista(String mensaje, String idComponente, Severity severity) {
		PrimeFaces.current().scrollTo(idComponente);
		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(FacesContext.getCurrentInstance().getViewRoot().findComponent(idComponente).getClientId(),
				new FacesMessage(severity, null, WebResources.getBundleMsg(mensaje)));
	}
	
	public void generarArchivoMotivoRevocacion() {
		try {
			File documento = new File(tramiteSeleccionado.getRutaDocumentoRevocado());
			if (documento.exists()) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Disposition", "attachment;filename=" + documento.getName());

				OutputStream responseOutputStream = response.getOutputStream();
				InputStream fileInputStream = new FileInputStream(documento);

				byte[] bytesBuffer = new byte[2048];
				int bytesRead;
				try {
					while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
						responseOutputStream.write(bytesBuffer, 0, bytesRead);
					}
					responseOutputStream.flush();
					fileInputStream.close();
					responseOutputStream.close();
					facesContext.responseComplete();
					WebResources.successMessage("msj_documento_revocado_generado", false);
				} catch (Exception e) {
					WebResources.addValidationMessage("msj_error_documento_revocado", false);	
					LOGGER.error("Ocurrió un error al descargar el documento: ", e);
				} finally {
					if(fileInputStream != null) {
						try {
							fileInputStream.close();
						} catch (Exception e2) {
							LOGGER.error("Ocurrió un error al cerrar el recurso fileInputStream ", e2);
						}
					}
				}
			} else {
				WebResources.addValidationMessage("msj_no_documento_revocado", false);	
			}					
		} catch (Exception e) {
			LOGGER.error("No se encontró el PDF que complementa el motivo de rechazo para la descarga " + tramiteSeleccionado.getFolioSeguimiento(), e);
			WebResources.addErrorMessage("msj_error_documento_revocado", false);
		}		
	}
	
	/**
	 * Método auxiliar que genera el reporte de trámites en excel para el funcionario.
	 */
	public void listenergenerarExcel(List<TramiteDTO> lstTramites) {	
	    if (BeanUtils.isNull(lstTramites) || BeanUtils.isEmpty(lstTramites)) {
	        WebResources.addValidationMessage("msj_sin_tramites", false);
	    } else {	
	        // Límite máximo para HSSF (formato .xls) - 65,535 filas por hoja (0 a 65535)
	        final int MAX_HSSF_ROWS_PER_SHEET = 65535;
	        
	        // Validar si excede el límite permitido antes de generar el Excel
	        if (lstTramites.size() > MAX_HSSF_ROWS_PER_SHEET) {
	            WebResources.addValidationMessage("msj_excede_limite_descarga", false);
	            return; // Salir del método sin generar el Excel
	        }
	        
	        HSSFWorkbook workbook = null;
	        FileOutputStream fileExcel = null;
	        int numeroPagina = 0;
	        
	        //Se valida si existe la ruta de archivos temporales y si no existe, se crea
	        File pathTemporales = new File(Environment.getPathArchivosTemporales()); 
	        if(!pathTemporales.exists()) {
	            pathTemporales.mkdirs();
	        }
	        
	        String pathReporte = Environment.getPathArchivosTemporales() + Constantes.SEPARADOR_RUTA + Constantes.REPORTE_TRAMITES + Constantes.EXTENSION_XLS;
	        
	        File fileReporte = new File(pathReporte);
	        try {
	            workbook = new HSSFWorkbook();
	            HSSFSheet sheet = workbook.createSheet();
	            workbook.setSheetName(numeroPagina, Constantes.NOMBRE_REPORTE);
	            CellStyle headerStyle = workbook.createCellStyle();
	            Font font = workbook.createFont();
	            font.setBold(true);
	            headerStyle.setFont(font);
	            
	            //Columnas para reporte excel
	            Map<Integer, String> mapColumnasExcel = new HashMap<>();
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_FOLIO, Constantes.DESC_COLUMNA_FOLIO);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_NOMBRE, Constantes.DESC_COLUMNA_NOMBRE);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_APE_PATERNO, Constantes.DESC_COLUMNA_APE_PATERNO);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_APE_MATERNO, Constantes.DESC_COLUMNA_APE_MATERNO);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_CURP, Constantes.DESC_COLUMNA_CURP);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_FECHA_SOLICITUD, Constantes.DESC_COLUMNA_FECHA_SOLICITUD);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_FECHA_CAMBIO, Constantes.DESC_COLUMNA_FECHA_CAMBIO);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_OPERADOR, Constantes.DESC_COLUMNA_OPERADOR);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_ESTATUS, Constantes.DESC_COLUMNA_ESTATUS);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_ESTATUS_ENVIADO, Constantes.DESC_COLUMNA_ESTATUS_ENVIADO);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_ESTATUS_EN_CORRECION, Constantes.DESC_COLUMNA_ESTATUS_EN_CORRECCION);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_ESTATUS_CORREGIDO, Constantes.DESC_COLUMNA_ESTATUS_CORREGIDO);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_ESTATUS_RECHAZADO, Constantes.DESC_COLUMNA_ESTATUS_RECHAZADO);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_ESTATUS_ACEPTADO, Constantes.DESC_COLUMNA_ESTATUS_ACEPTADO);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_ESTATUS_REVISADO, Constantes.DESC_COLUMNA_ESTATUS_REVISADO);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_ESTATUS_RESOLUCION_POSITIVA, Constantes.DESC_COLUMNA_ESTATUS_RESOLUCION_POSITIVA);
	            mapColumnasExcel.put(Constantes.ID_COLUMNA_ESTATUS_RESOLUCION_NEGATIVA, Constantes.DESC_COLUMNA_ESTATUS_RESOLUCION_NEGATIVA);
	            
	            //Agregar nombres de encabezados al reporte
	            HSSFRow headerRow = sheet.createRow(0);
	            for (int i = 0; i < mapColumnasExcel.size(); ++i) {
	                String header = mapColumnasExcel.get(i);
	                HSSFCell cell = headerRow.createCell(i);
	                cell.setCellStyle(headerStyle);
	                cell.setCellValue(header);
	            }
	            
	            //Colocar datos del trámite en la columna que le corresponde.
	            BitMovimientosTramiteDTO movimientoDTO = new BitMovimientosTramiteDTO();
	            for (int filaActual = 0; filaActual < lstTramites.size(); filaActual++) {
	                HSSFRow valuesRow = sheet.createRow(filaActual+1);
	                for (int columnaActual = 0; columnaActual < mapColumnasExcel.size(); ++columnaActual) {				
	                    HSSFCell cellValue = valuesRow.createCell(columnaActual);
	                    String valorCelda;
	                    switch (columnaActual) {						
	                    case Constantes.ID_COLUMNA_FOLIO:
	                        valorCelda = BeanUtils.isNotNull(lstTramites.get(filaActual).getFolioSeguimiento()) 
	                        ? lstTramites.get(filaActual).getFolioSeguimiento() : Constantes.EMPTY_STRING;
	                        break;								
	                    case Constantes.ID_COLUMNA_NOMBRE:
	                        valorCelda = (BeanUtils.isNotNull(lstTramites.get(filaActual).getUsuario()) && BeanUtils.isNotNull(lstTramites.get(filaActual).getUsuario().getNombre())) 
	                        ? lstTramites.get(filaActual).getUsuario().getNombre() : Constantes.EMPTY_STRING;
	                        break;							
	                    case Constantes.ID_COLUMNA_APE_PATERNO:
	                        valorCelda = (BeanUtils.isNotNull(lstTramites.get(filaActual).getUsuario()) && BeanUtils.isNotNull(lstTramites.get(filaActual).getUsuario().getPrimerApellido()))
	                        ? lstTramites.get(filaActual).getUsuario().getPrimerApellido() : Constantes.EMPTY_STRING;
	                        break;
	                    case Constantes.ID_COLUMNA_APE_MATERNO:
	                        valorCelda = (BeanUtils.isNotNull(lstTramites.get(filaActual).getUsuario()) && BeanUtils.isNotNull(lstTramites.get(filaActual).getUsuario().getSegundoApellido()))
	                        ? lstTramites.get(filaActual).getUsuario().getSegundoApellido() : Constantes.EMPTY_STRING;
	                        break;
	                    case Constantes.ID_COLUMNA_CURP:
	                        valorCelda = (BeanUtils.isNotNull(lstTramites.get(filaActual).getUsuario()) && BeanUtils.isNotNull(lstTramites.get(filaActual).getUsuario().getCurp()))
	                        ? lstTramites.get(filaActual).getUsuario().getCurp() : Constantes.EMPTY_STRING;
	                        break;		
	                    case Constantes.ID_COLUMNA_FECHA_SOLICITUD:
	                        valorCelda = BeanUtils.isNotNull(lstTramites.get(filaActual).getFechaCreacion()) 
	                        ? BeanUtils.convertirDateStringAnioMesDia(lstTramites.get(filaActual).getFechaCreacion()) : Constantes.EMPTY_STRING;
	                        break;
	                    case Constantes.ID_COLUMNA_FECHA_CAMBIO:
	                        valorCelda = BeanUtils.isNotNull(lstTramites.get(filaActual).getFechaRevision()) 
	                        ? BeanUtils.convertirDateStringAnioMesDia(lstTramites.get(filaActual).getFechaRevision()) : Constantes.EMPTY_STRING;
	                        break;
	                    case Constantes.ID_COLUMNA_OPERADOR:
	                        valorCelda = (BeanUtils.isNotNull(lstTramites.get(filaActual).getUsuarioOperador()) && BeanUtils.isNotNull(lstTramites.get(filaActual).getUsuarioOperador().getNombreCompleto())) 
	                        ? lstTramites.get(filaActual).getUsuarioOperador().getNombreCompleto() : Constantes.EMPTY_STRING;
	                        break;
	                    case Constantes.ID_COLUMNA_ESTATUS:
	                        valorCelda = BeanUtils.isNotNull(lstTramites.get(filaActual).getCatEstatusTramiteDTO().getDescripcion()) 
	                        ? lstTramites.get(filaActual).getCatEstatusTramiteDTO().getDescripcion() : Constantes.EMPTY_STRING;
	                        break;							
	                    case Constantes.ID_COLUMNA_ESTATUS_ENVIADO:
	                    	movimientoDTO = bitMovimientosTramiteDAO.buscarPorIdtramiteYEstatus(lstTramites.get(filaActual).getIdTramite(), 
	                    			Constantes.ID_ESTATUS_ENVIADO);
	                    	valorCelda = (movimientoDTO != null && BeanUtils.isNotNull(movimientoDTO.getFechaMovimiento())) 
	                        		? BeanUtils.convertirDateStringDiaMesAnioHora(movimientoDTO.getFechaMovimiento()) : Constantes.EMPTY_STRING;
	                        break;							
	                    case Constantes.ID_COLUMNA_ESTATUS_EN_CORRECION:
	                    	movimientoDTO = bitMovimientosTramiteDAO.buscarPorIdtramiteYEstatus(lstTramites.get(filaActual).getIdTramite(), 
	                    			Constantes.ID_ESTATUS_CORRECIONES);
	                    	valorCelda = (movimientoDTO != null && BeanUtils.isNotNull(movimientoDTO.getFechaMovimiento())) 
	                    			? BeanUtils.convertirDateStringDiaMesAnioHora(movimientoDTO.getFechaMovimiento()) : Constantes.EMPTY_STRING;
	                        break;							
	                    case Constantes.ID_COLUMNA_ESTATUS_CORREGIDO:
	                    	movimientoDTO = bitMovimientosTramiteDAO.buscarPorIdtramiteYEstatus(lstTramites.get(filaActual).getIdTramite(), 
	                    			Constantes.ID_ESTATUS_CORREGIDO);
	                    	valorCelda = (movimientoDTO != null && BeanUtils.isNotNull(movimientoDTO.getFechaMovimiento())) 
	                    			? BeanUtils.convertirDateStringDiaMesAnioHora(movimientoDTO.getFechaMovimiento()) : Constantes.EMPTY_STRING;
	                        break;							
	                    case Constantes.ID_COLUMNA_ESTATUS_RECHAZADO:
	                    	movimientoDTO = bitMovimientosTramiteDAO.buscarPorIdtramiteYEstatus(lstTramites.get(filaActual).getIdTramite(), 
	                    			Constantes.ID_ESTATUS_RECHAZADO);
	                        if (BeanUtils.isNotNull(movimientoDTO) && BeanUtils.isNotNull(movimientoDTO.getFechaMovimiento())) {
	                            valorCelda = BeanUtils.convertirDateStringDiaMesAnioHora(movimientoDTO.getFechaMovimiento());
	                        } else if (BeanUtils.isNotNull(lstTramites.get(filaActual).getFechaRevision())
	                        		&& lstTramites.get(filaActual).getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO ) {
	                            valorCelda = BeanUtils.convertirDateStringDiaMesAnioHora(lstTramites.get(filaActual).getFechaRevision());
	                        } else {
	                            valorCelda = Constantes.EMPTY_STRING;
	                        }
	                        break;							
	                    case Constantes.ID_COLUMNA_ESTATUS_ACEPTADO:
	                    	movimientoDTO = bitMovimientosTramiteDAO.buscarPorIdtramiteYEstatus(lstTramites.get(filaActual).getIdTramite(), 
	                    			Constantes.ID_ESTATUS_APROBADO);
	                    	valorCelda = (movimientoDTO != null && BeanUtils.isNotNull(movimientoDTO.getFechaMovimiento())) 
	                    			? BeanUtils.convertirDateStringDiaMesAnioHora(movimientoDTO.getFechaMovimiento()) : Constantes.EMPTY_STRING;
	                        break;							
	                    case Constantes.ID_COLUMNA_ESTATUS_REVISADO:
	                    	movimientoDTO = bitMovimientosTramiteDAO.buscarPorIdtramiteYEstatus(lstTramites.get(filaActual).getIdTramite(), 
	                    			Constantes.ID_ESTATUS_REVISADO);
	                    	valorCelda = (movimientoDTO != null && BeanUtils.isNotNull(movimientoDTO.getFechaMovimiento())) 
	                    			? BeanUtils.convertirDateStringDiaMesAnioHora(movimientoDTO.getFechaMovimiento()) : Constantes.EMPTY_STRING;
	                        break;							
	                    case Constantes.ID_COLUMNA_ESTATUS_RESOLUCION_POSITIVA:
	                    	movimientoDTO = bitMovimientosTramiteDAO.buscarPorIdtramiteYEstatus(lstTramites.get(filaActual).getIdTramite(), 
	                    			Constantes.ID_ESTATUS_CONCLUSION_POSITIVA);
	                    	valorCelda = (movimientoDTO != null && BeanUtils.isNotNull(movimientoDTO.getFechaMovimiento())) 
	                    			? BeanUtils.convertirDateStringDiaMesAnioHora(movimientoDTO.getFechaMovimiento()) : Constantes.EMPTY_STRING;
	                        break;							
	                    case Constantes.ID_COLUMNA_ESTATUS_RESOLUCION_NEGATIVA:
	                    	movimientoDTO = bitMovimientosTramiteDAO.buscarPorIdtramiteYEstatus(lstTramites.get(filaActual).getIdTramite(), 
	                    			Constantes.ID_ESTATUS_CONCLUSION_NEGATIVA);
	                    	valorCelda = (movimientoDTO != null && BeanUtils.isNotNull(movimientoDTO.getFechaMovimiento())) 
	                    			? BeanUtils.convertirDateStringDiaMesAnioHora(movimientoDTO.getFechaMovimiento()) : Constantes.EMPTY_STRING;
	                        break;							
	                    default:
	                        valorCelda = Constantes.EMPTY_STRING;
	                        break;
	                    }						
	                    cellValue.setCellValue(valorCelda);
	                }			
	            }		
	            for (int columnIndex = 0; columnIndex < mapColumnasExcel.size(); columnIndex++) {
	                sheet.setColumnWidth(columnIndex, 6000);
	            }				
	            
	            fileExcel = new FileOutputStream(pathReporte);
	            workbook.write(fileExcel);

	            byte[] buffer = FileUtils.readFileToByteArray(fileReporte);
	            setReporteTramites(DefaultStreamedContent.builder().contentType("application/xls")
	                    .name(fileReporte.getName()).stream(() -> new ByteArrayInputStream(buffer)).build());

	            PrimeFaces prCurrent = PrimeFaces.current();
	            prCurrent.executeScript("document.getElementById('frmTramites:descargaReporte').click();");
	        } catch (FileNotFoundException e) {
	            LOGGER.error("error al obtener archivo del reporte: ", e);
	            WebResources.validationMessage("msg_error_descarga_reporte", true);
	        } catch (IOException e) {
	            LOGGER.error("error al obtener archivo del reporte: ", e);
	            WebResources.validationMessage("msg_error_descarga_reporte", true);
	        } catch (Exception e) {
	            LOGGER.error(e.getMessage(), e);
	            WebResources.validationMessage("msg_error_descarga_reporte", true);
	        } finally {
	            if (fileReporte != null) {
	                try {
	                    fileReporte.delete();
	                } catch (SecurityException e) {
	                    LOGGER.warn("No se pudo borrar el objeto file del reporte de excel ", e);
	                }
	            }

	            if (workbook != null) {
	                try {
	                    workbook.close();
	                } catch (IOException e) {
	                    LOGGER.warn("No se pudo cerrar el workbook del Excel ", e);
	                }
	            }
	            if (fileExcel != null) {
	                try {
	                    fileExcel.flush();
	                    fileExcel.close();
	                } catch (IOException e) {
	                    LOGGER.warn("No se pudo cerrar el FileOutputStream del Excel: ", e);
	                }
	            }
	        }
	    }
	}

	public String buscarFechaEnvio(long idTramite) {
		BitMovimientosTramiteDTO  movDTO = bitMovimientosTramiteDAO.buscarPorIdtramiteYEstatus(idTramite, Constantes.ID_ESTATUS_ENVIADO);
		if (BeanUtils.isNotNull(movDTO) && BeanUtils.isNotNull(movDTO.getFechaMovimiento())) {
			return BeanUtils.convertirDateStringDiaMesAnio(movDTO.getFechaMovimiento()); 
		}
		return Constantes.EMPTY_STRING;
	}
	
	public void cargarHistoricoEstatus(long idTramite) {
		lstHistorico = new ArrayList<>();
		lstHistorico = bitMovimientosTramiteDAO.buscarPorIdTramite(idTramite);
	}
	
	public void cerrarDlgHistoricoEstatus() {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dlgHistoricoEstatus').hide();");
	}

	public String descripcionEstatus(Integer idEstatus) {
		CatEstatusTramiteDTO estatusTramite = catEstatusTramiteDAO.buscarPorIdEstatus(idEstatus);
		if (BeanUtils.isNotNull(estatusTramite)) {
			ProyectoDTO proyectoDTO = generarFormularioApplication.getProyectoDTO();
			return proyectoDTO.isAviso() ? estatusTramite.getDescripcionAviso() : estatusTramite.getDescripcion() ;
		}
		return Constantes.EMPTY_STRING;
	}
	
	public String nombreCompletoUsuario(long idUsuario) {
		UsuarioDTO usuario = usuarioDAO.buscarPorId(idUsuario);
		if (BeanUtils.isNotNull(usuario)) {
			return usuario.getIdUsuarioLlaveCdmx() == Constantes.ID_USUARIO_SCHEDULE_PREVENCION ? usuario.getNombre() : usuario.getNombreCompleto() ;
		}
		return Constantes.EMPTY_STRING;
	}

	/**
	 * Método listener del campo de fecha "Desde", que limitará a un periodo determinado. 
	 * Se valida si se tiene algún valor en el identificador 1 de la tabla de parametros_sistema, 
	 * Si no se encuentra un valor configurado desde BD, se tomará un valor máximo de 30 días, para 
	 * poder seleccionar un rango de trámites.
	 */
	public void listenerFechaInicial() {
		if(BeanUtils.isNotNull(tramiteBusqueda.getFechaDesde())){
			fechaMinima = tramiteBusqueda.getFechaDesde();						
			fechaMaxima = BeanUtils.sumarDiasFecha(fechaMinima, diasRangoTramites);
			if(fechaMaxima.before(fechaActual)) {
				tramiteBusqueda.setFechaHasta(fechaMaxima);	
			} else {
				tramiteBusqueda.setFechaHasta(fechaActual);
				fechaMaxima = fechaActual;
				fechaMinima = tramiteBusqueda.getFechaDesde();
			}			
		}
	}
	
	/**
	 * Método listener que verifica que forzosamente sea seleccionada una fecha en el campo "Desde"
	 */
	public void listenerFechaFinal() {
		if(BeanUtils.isNull(tramiteBusqueda.getFechaDesde())) {
			tramiteBusqueda.setFechaHasta(null);
			WebResources.validationMessage("msj_seleccionar_fecha_inicial", true);				
		}
	}
	
	/**
	 * Metodo auxiliar para asignar estilos de estatus linea captura 
	 * @param tramite
	 */
	public void asignarEstiloEstatusLCTramite(TramiteDTO tramite) {
		if(seccionesProyectoBean.getProyectoDTO().isProyectoLineaCaptura()
				&& BeanUtils.isNotNull(tramite.getLineaCapturaDTO())) {
			CatEstatusLineaCapturaDTO estatus = tramite.getLineaCapturaDTO().getCatEstatusLineaCaptura();  
			// asignar estilos
			if (estatus.getIdEstatusLineaCaptura() == Constantes.ID_LC_ESTATUS_PENDIENTE) {
				tramite.setEstiloLCEstatus("estatus-pendiente-pago");
				tramite.setEstiloLCTxtEstatus("estatus-pendiente-pago-txt");
			} else if (estatus.getIdEstatusLineaCaptura() == Constantes.ID_LC_ESTATUS_PAGADO) {
				tramite.setEstiloLCEstatus("estatus-aprobado");
				tramite.setEstiloLCTxtEstatus("estatus-aprobado-txt");
			} else if (estatus.getIdEstatusLineaCaptura() == Constantes.ID_LC_ESTATUS_VENCIDA) {
				tramite.setEstiloLCEstatus("estatus-rechazado");
				tramite.setEstiloLCTxtEstatus("estatus-rechazado-txt");
			}
        }
	}
	
	/**
	 * Metodo auxiliar para el consumo del webservice estatus LC
	 *
	 * @param tramiteSeleccionado tramite seleccionado
	 * @return DTO linea de captura
	 */
	public LineaCapturaDTO procesaConsultaEstatusLineaCaptura(TramiteDTO tramiteSeleccionado) {
		Gson gson = new Gson();
		ResponseEstatusLCDTO estatusLCDTO = null;
		LineaCapturaDTO lineaCapturaDTO=  null;
		EstatusLineaCapturaClient consultaLCClient = new EstatusLineaCapturaClient();
		Predicate<ResponseEstatusLCDTO> prResponseEstatus = p -> p.getCodigo().equals(0);

		try {
			DetLineaCapturaDTO detLineaCapturaDTO = detLineaCapturaDAO.buscarPorIdProyecto(seccionesProyectoBean.getProyectoDTO().getIdProyecto());
	    	RequestConsultaLCDTO requestConsulta = new RequestConsultaLCDTO();
		    	requestConsulta.setIdDependencia(detLineaCapturaDTO.getDependenciaPagoDTO().getIdDependenciaPago());
		    	requestConsulta.setIdSolicitud(tramiteSeleccionado.getLineaCapturaDTO().getSolicitudLineaCaptura());
				requestConsulta.setLineaCaptura(tramiteSeleccionado.getLineaCapturaDTO().getLineaCaptura());
				LOGGER.info("requestConsulta estatus LC tramite: {} ", requestConsulta);
				estatusLCDTO = consultaLCClient.consultaEstatusLC(seccionesProyectoBean.getSecurityDomainLineasCapturaDTO().getUrlSistema(), requestConsulta);
				LOGGER.info("termina consulta estatus LC tramite: {} ", estatusLCDTO);
			if(prResponseEstatus.test(estatusLCDTO)) {
				lineaCapturaDTO = tramiteSeleccionado.getLineaCapturaDTO();				
				TramiteDTO tramiteModificado = lineaFacturaFacade.procesarRespuestaEstatusLC(estatusLCDTO, tramiteSeleccionado, gson);
				lineaCapturaDTO = tramiteModificado.getLineaCapturaDTO();	
				lineaCapturaDTO.setCatEstatusLineaCaptura(catEstatusLineaCapturaDAO.buscarPorId(lineaCapturaDTO.getCatEstatusLineaCaptura().getIdEstatusLineaCaptura()));
			}else {
				LOGGER.info("error al consultar Linea de captura: {} , mensajeError: {}", tramiteSeleccionado.getLineaCapturaDTO().getLineaCaptura(), estatusLCDTO.getMensajeError());
			}
		} catch (URISyntaxException e) {
			enviarMensajeVista("msj_ce_lc_error_url", Constantes.ID_FORM_MODAL_ESTATUS_LC, FacesMessage.SEVERITY_ERROR);
			LOGGER.error("Ocurrio un error sintaxis URL consulta Linea de Captura:: ", e);
		}catch (ConnectException e) {
			enviarMensajeVista("msj_ce_lc_error_conexion", Constantes.ID_FORM_MODAL_ESTATUS_LC, FacesMessage.SEVERITY_ERROR);
			LOGGER.error("Ocurrio un error de conexion al consulta Linea de Captura:: ", e);
		}catch (InterpreteException e) {
			LOGGER.error("Ocurrio un error de controlado interprete al consulta Linea de Captura:: ", e);
			manejoInterpreteException(e);
		}catch (Exception e) {
			LOGGER.error("Ocurrio general actualizar la Linea de Captura:: ", e);
			enviarMensajeVista("msj_error_linea_captura", Constantes.ID_FORM_MODAL_ESTATUS_LC, FacesMessage.SEVERITY_ERROR);
		}
		return lineaCapturaDTO;
	}

	/**
	 * Manejo especial para errores
	 *
	 * @param e
	 */
	public void manejoInterpreteException(InterpreteException e) {
		if(e.getMessage().contains("Mensaje")) {
			enviarMensajeVista("msj_ce_lc_error_general", Constantes.ID_FORM_MODAL_ESTATUS_LC, FacesMessage.SEVERITY_WARN);
		}else if(e.getMessage().contains("Acceso")) {
			enviarMensajeVista("msj_ce_lc_error_acceso_denegado", Constantes.ID_FORM_MODAL_ESTATUS_LC, FacesMessage.SEVERITY_ERROR);
		}else {
			enviarMensajeVista("msj_ce_lc_error_general", Constantes.ID_FORM_MODAL_ESTATUS_LC, FacesMessage.SEVERITY_WARN);
		}
	}
	
	/**
	 * Metodo para abrir el modal de Consulta Estatus Linea Captura
	 */
	public void mostrarModalEstatusLC(TramiteDTO tramite) {
		this.tramiteSeleccionado = new TramiteDTO();
		this.tramiteSeleccionado = tramite;
		LOGGER.info("mostrarModalEstatusLC descarga del asi llega tramite: {}", tramite);
		Predicate<LineaCapturaDTO> prLineaCaptura = BeanUtils::isNotNull;
		Predicate<CatEstatusLineaCapturaDTO> prEstLineaCaptura = p -> p.getIdEstatusLineaCaptura().equals(Constantes.ID_LC_ESTATUS_PENDIENTE);
		if(prEstLineaCaptura.test(this.tramiteSeleccionado.getLineaCapturaDTO().getCatEstatusLineaCaptura())) {
			LineaCapturaDTO lineaCapturaDTO = procesaConsultaEstatusLineaCaptura(this.tramiteSeleccionado);
			if(prLineaCaptura.test(lineaCapturaDTO)) {
				this.tramiteSeleccionado.setLineaCapturaDTO(lineaCapturaDTO);
				this.inicializar();
			}
		}
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalEstatusLC').show();");
	}

	/**
	 * Metodo para cerrar el modal de Consulta Estatus Linea Captura
	 */
	public void cerrarModalEstatusLC() {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalEstatusLC').hide();");
	}
    
    /**GETTER´s y SETTER´s**/
    
	public List<TramiteDTO> getLstTramites() {
		return lstTramites;
	}

	public void setLstTramites(List<TramiteDTO> lstTramites) {
		this.lstTramites = lstTramites;
	}

	public List<CatEstatusTramiteDTO> getLstEstatusTramite() {
		return lstEstatusTramite;
	}

	public void setLstEstatusTramite(List<CatEstatusTramiteDTO> lstEstatusTramite) {
		this.lstEstatusTramite = lstEstatusTramite;
	}

	public TramiteDTO getTramiteBusqueda() {
		return tramiteBusqueda;
	}

	public void setTramiteBusqueda(TramiteDTO tramiteBusqueda) {
		this.tramiteBusqueda = tramiteBusqueda;
	}

	public List<UsuarioDTO> getLstUsuariosOperadoresActivos() {
		return lstUsuariosOperadoresActivos;
	}

	public void setLstUsuariosOperadoresActivos(List<UsuarioDTO> lstUsuariosOperadoresActivos) {
		this.lstUsuariosOperadoresActivos = lstUsuariosOperadoresActivos;
	}

	public UsuarioDTO getUsuarioOperadorDTO() {
		return usuarioOperadorDTO;
	}

	public void setUsuarioOperadorDTO(UsuarioDTO usuarioOperadorDTO) {
		this.usuarioOperadorDTO = usuarioOperadorDTO;
	}

	public List<TramiteDTO> getLstTramitesSeleccionados() {
		return lstTramitesSeleccionados;
	}

	public void setLstTramitesSeleccionados(List<TramiteDTO> lstTramitesSeleccionados) {
		this.lstTramitesSeleccionados = lstTramitesSeleccionados;
	}

	/**
	 * @return the lstTramitesFirma
	 */
	public List<TramiteDTO> getLstTramitesFirma() {
		return lstTramitesFirma;
	}

	/**
	 * @param lstTramitesFirma the lstTramitesFirma to set
	 */
	public void setLstTramitesFirma(List<TramiteDTO> lstTramitesFirma) {
		this.lstTramitesFirma = lstTramitesFirma;
	}

	/**
	 * @return the lstTramitesSelFirma
	 */
	public List<TramiteDTO> getLstTramitesSelFirma() {
		return lstTramitesSelFirma;
	}

	/**
	 * @param lstTramitesSelFirma the lstTramitesSelFirma to set
	 */
	public void setLstTramitesSelFirma(List<TramiteDTO> lstTramitesSelFirma) {
		this.lstTramitesSelFirma = lstTramitesSelFirma;
	}

	/**
	 * @return the archivosRespuestaTokenDTO
	 */
	public ArchivosRespuestaTokenDTO getArchivosRespuestaTokenDTO() {
		return archivosRespuestaTokenDTO;
	}

	/**
	 * @param archivosRespuestaTokenDTO the archivosRespuestaTokenDTO to set
	 */
	public void setArchivosRespuestaTokenDTO(ArchivosRespuestaTokenDTO archivosRespuestaTokenDTO) {
		this.archivosRespuestaTokenDTO = archivosRespuestaTokenDTO;
	}

	/**
	 * @return the respuestaFirmaTramite
	 */
	public ResponseServiceFirmaDTO getRespuestaFirmaTramite() {
		return respuestaFirmaTramite;
	}

	/**
	 * @param respuestaFirmaTramite the respuestaFirmaTramite to set
	 */
	public void setRespuestaFirmaTramite(ResponseServiceFirmaDTO respuestaFirmaTramite) {
		this.respuestaFirmaTramite = respuestaFirmaTramite;
	}

	/**
	 * @return the tramiteSeleccionado
	 */
	public TramiteDTO getTramiteSeleccionado() {
		return tramiteSeleccionado;
	}

	/**
	 * @param tramiteSeleccionado the tramiteSeleccionado to set
	 */
	public void setTramiteSeleccionado(TramiteDTO tramiteSeleccionado) {
		this.tramiteSeleccionado = tramiteSeleccionado;
	}

	public DetAsignacionDistribucionDTO getDetAsignacionDistribucionDTO() {
		return detAsignacionDistribucionDTO;
	}

	public void setDetAsignacionDistribucionDTO(DetAsignacionDistribucionDTO detAsignacionDistribucionDTO) {
		this.detAsignacionDistribucionDTO = detAsignacionDistribucionDTO;
	}

	/**
	 * @return the reporteTramites
	 */
	public DefaultStreamedContent getReporteTramites() {
		return reporteTramites;
	}

	/**
	 * @param reporteTramites the reporteTramites to set
	 */
	public void setReporteTramites(DefaultStreamedContent reporteTramites) {
		this.reporteTramites = reporteTramites;
	}

	/**
	 * @return the fechaMinima
	 */
	public Date getFechaMinima() {
		return fechaMinima;
	}

	/**
	 * @param fechaMinima the fechaMinima to set
	 */
	public void setFechaMinima(Date fechaMinima) {
		this.fechaMinima = fechaMinima;
	}
	
	/**
	 * @return the fechaMaxima
	 */
	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	/**
	 * @param fechaMaxima the fechaMaxima to set
	 */
	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}

	/**
	 * @return the diasRangoTramites
	 */
	public int getDiasRangoTramites() {
		return diasRangoTramites;
	}

	/**
	 * @param diasRangoTramites the diasRangoTramites to set
	 */
	public void setDiasRangoTramites(int diasRangoTramites) {
		this.diasRangoTramites = diasRangoTramites;
	}

	/**
	 * @return the fechaActual
	 */
	public Date getFechaActual() {
		return fechaActual;
	}

	/**
	 * @param fechaActual the fechaActual to set
	 */
	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getMsgErrorFirma() {
		return msgErrorFirma;
	}

	public void setMsgErrorFirma(String msgErrorFirma) {
		this.msgErrorFirma = msgErrorFirma;
	}

	public List<BitMovimientosTramiteDTO> getLstHistorico() {
		return lstHistorico;
	}

	public void setLstHistorico(List<BitMovimientosTramiteDTO> lstHistorico) {
		this.lstHistorico = lstHistorico;
	}

}

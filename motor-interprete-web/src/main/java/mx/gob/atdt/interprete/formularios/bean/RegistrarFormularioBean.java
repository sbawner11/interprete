package mx.gob.atdt.interprete.formularios.bean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.IntPredicate;
import java.util.function.LongPredicate;
import java.util.function.Predicate;
import java.util.function.Supplier;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.codehaus.jettison.json.JSONException;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.atdt.firma.client.FirmaRESTClient;
import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.common.formatos.FormatoTramiteFinalizadoPDF;
import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.commons.dto.CatAsentamientosDTO;
import mx.gob.atdt.interprete.commons.dto.CatEstadosDTO;
import mx.gob.atdt.interprete.commons.dto.CatMunicipiosDTO;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.commons.utils.Utils;
import mx.gob.atdt.interprete.componentes.bean.CargaDocumentoBean;
import mx.gob.atdt.interprete.componentes.bean.CheckBoxBean;
import mx.gob.atdt.interprete.componentes.bean.DynamicTableBean;
import mx.gob.atdt.interprete.dao.CatAsentamientosDAO;
import mx.gob.atdt.interprete.dao.CatEstadosDAO;
import mx.gob.atdt.interprete.dao.CatEstatusTramiteDAO;
import mx.gob.atdt.interprete.dao.CatMunicipiosDAO;
import mx.gob.atdt.interprete.dao.ConfiguracionCondicionesDAO;
import mx.gob.atdt.interprete.dao.DetElementosTokenDAO;
import mx.gob.atdt.interprete.dao.DetFirmaDigitalDAO;
import mx.gob.atdt.interprete.dao.UsuarioDAO;
import mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO;
import mx.gob.atdt.interprete.dto.CatEstatusTramiteDTO;
import mx.gob.atdt.interprete.dto.ComponenteCargaDocumentosDTO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxDTO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonaMoralDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesLlaveDTO;
import mx.gob.atdt.interprete.dto.ConfiguracionCondicionValorDTO;
import mx.gob.atdt.interprete.dto.ConfiguracionCondicionesDTO;
import mx.gob.atdt.interprete.dto.ConfiguracionWebhookDTO;
import mx.gob.atdt.interprete.dto.ControlComponentesDTO;
import mx.gob.atdt.interprete.dto.DetElementosCheckboxDTO;
import mx.gob.atdt.interprete.dto.DetElementosTokenDTO;
import mx.gob.atdt.interprete.dto.DetGestionUsuarioDTO;
import mx.gob.atdt.interprete.dto.FirmaTramiteDTO;
import mx.gob.atdt.interprete.dto.GeneraComprobanteDTO;
import mx.gob.atdt.interprete.dto.PersonaMoralDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.ResponseServiceConsultaFirmaDTO;
import mx.gob.atdt.interprete.dto.ResponseServiceFirmaDTO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.TramiteFirmaElectronicaDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.exception.InterpreteException;
import mx.gob.atdt.interprete.exception.ServiciosException;
import mx.gob.atdt.interprete.facade.NotificacionWebHookFacade;
import mx.gob.atdt.interprete.formulario.dao.FormularioDAO;
import mx.gob.atdt.interprete.formulario.facade.FormularioFacade;
import mx.gob.atdt.interprete.formularios.application.GenerarFormularioApplication;
import mx.gob.atdt.interprete.lineacaptura.bean.GeneraLineaCapturaBean;
import mx.gob.atdt.interprete.tramites.bean.BandejaTramitesBean;
import mx.gob.atdt.interprete.tramites.bean.BandejaValidacionTramitesBean;
import mx.gob.atdt.interprete.util.BeanUtils;
import mx.gob.atdt.interprete.util.WebResources;

@Named("registrarFormularioBean")
@SessionScoped
public class RegistrarFormularioBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5805612886702948921L;

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrarFormularioBean.class);

	@Inject
	private FacesContext facesContext;

	@Inject
	AuthenticatorBean authenticatorBean;

	@Inject
	private GenerarFormularioApplication generarFormularioApplication;

	@Inject
	private SeccionesProyectoBean seccionesProyectoBean;

	@Inject
	private BandejaTramitesBean bandejaTramitesBean;

	@Inject
	private BandejaValidacionTramitesBean bandejaValidacionTramitesBean;

	@Inject
	private CheckBoxBean checkBoxBean;

	@Inject
	private CargaDocumentoBean cargaDocumentoBean;

	@Inject
	private FormularioDAO formularioDAO;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private FormularioFacade formularioFacade;

	@Inject
	private DetFirmaDigitalDAO detFirmaDigitalDAO;

	@Inject
	private DetElementosTokenDAO detElementosTokenDAO;

	@Inject
	private ConfiguracionCondicionesDAO configCodicionDAO;

	@Inject
	private GeneraLineaCapturaBean generaLineaCapturaBean;
	
	@Inject
	private DynamicTableBean dynamicTableBean;
	
	@Inject
	private CatEstadosDAO estadoDAO;
	@Inject
	private CatMunicipiosDAO municipioDAO;
	@Inject
	private CatAsentamientosDAO asentamientoDAO;
	
	@Inject CatEstatusTramiteDAO catEstatusTramiteDAO;
	
	@Inject
	private NotificacionWebHookFacade notificacionWebHookFacade;


	private Map<String, ControlComponentesDTO> mapControlComponentes;
	private Map<String, Object> mapRespuestas;

	private List<SeccionesFormularioDTO> lstSeccionesDTO;
	
	//lo usaremos para persistir la informacion de BandejaValidacionTramitesBean del listado de tramites maximo
	private List<TramiteDTO> lstTramitesSelFirma;

	private SeccionesFormularioDTO seccionActualDTO;
	private ProyectoDTO proyectoDTO;
	private TramiteDTO tramiteActual;
	private TramiteDTO tramiteRevision;
	private UsuarioDTO usuarioTramite;
	private ArchivosRespuestaTokenDTO archivosRespuestaTokenDTO;
	private ResponseServiceFirmaDTO respuestaFirmaTramite;

	private boolean isUsuarioBack;
	private boolean isSeccionInformativa;
	private boolean isEdicionTramite;
	private boolean isUltimaSeccion;
	private boolean isObservacionesTramite;
	private boolean isNuevoTramite;
	private boolean rolPermiteConcluirTramite;
	private boolean rolPermitePrevenirTramite;
	private boolean rolPermiteFinalizarTramite;
	private boolean isFirmaBandeja;
	private boolean isFirmaTramiteCiudadano;
	private boolean isValidacionTramite;
	private int idEstatusValidacionTramite;

	private boolean mostrarModalTipoPersona = false;
	private String msgTipoPersona;
	private boolean firmaCiudadano;
	private TramiteFirmaElectronicaDTO datosFirmaCiudadanoDTO;
	private String rfcPersonaMoral;

	private boolean tipoResolucionTramite;
	private String leyendaTipoDeResolucionTramite;
	private boolean archivoResolucionTramiteCargado = false;
	
	private boolean mostrarPnlComentario;
	
	private boolean renderBtnConclusion;
	
	private boolean renderBtnFinalizarRevision;

	/**
	 * Método que inicializa el bean para registro de formulario.
	 *
	 * @return
	 */
	public String inicializarCapturaFormulario(ProyectoDTO proyectoDTO, List<SeccionesFormularioDTO> lstSecciones,
											   Map<String, ControlComponentesDTO> mapControlComponentesDTO, Map<String, Object> mapRespuestasDTO) {

		if (dynamicTableBean != null) {
	        dynamicTableBean.limpiar();
		}
		
		List<PersonaMoralDTO> listaPersonasMorales = authenticatorBean.getListaPersonasMorales();
		if(listaPersonasMorales != null) {
			mostrarModalTipoPersona = true;
			String razonSocialSeleccionada = "";
			for (PersonaMoralDTO personaMoral : listaPersonasMorales) {
				if(personaMoral.getIdPersonaMoral().equals(authenticatorBean.getPersonaMoralSeleccionada())) {
					razonSocialSeleccionada = personaMoral.getRazonSocial();
					rfcPersonaMoral = personaMoral.getRfc();
				}
			}

			if(authenticatorBean.getPersonaMoralSeleccionada() != null) {
				msgTipoPersona = "Persona Moral " + razonSocialSeleccionada;
			}else if(authenticatorBean.isCiudadanoSeleccionado()) {
				msgTipoPersona = "Ciudadano";
			}

		}

		firmaCiudadano = detFirmaDigitalDAO.getFirmaCiudadanoPorIdProyecto(proyectoDTO.getIdProyecto());

		datosFirmaCiudadanoDTO = new TramiteFirmaElectronicaDTO();

//		LOGGER.info("Se inicializa bean de sesión :::   " + this.toString());
		this.proyectoDTO = proyectoDTO;
		this.lstSeccionesDTO = lstSecciones;
		this.isEdicionTramite = false;
		this.isSeccionInformativa = false;
		this.isObservacionesTramite = false;
		this.isNuevoTramite = true;
		this.rolPermiteConcluirTramite = false;
		this.rolPermitePrevenirTramite = false;
		this.rolPermiteFinalizarTramite = false;
		this.isValidacionTramite = false;
		this.usuarioTramite = new UsuarioDTO();
		this.setMostrarPnlComentario(false);
		this.setRenderBtnConclusion(false);
		this.setRenderBtnFinalizarRevision(false);

		if (BeanUtils.isNotNull(authenticatorBean.getUsuarioLogueado())
				&& (authenticatorBean.isRolSupervisor() || authenticatorBean.isRolOperador() || authenticatorBean.isRolConsulta())) {
			this.isUsuarioBack = true;
			validarPermisosRevisor();
		}

		/** Se preselecciona la primer sección del formulario **/
		if (BeanUtils.isNotNull(lstSeccionesDTO.size()) && lstSeccionesDTO.size() > Constantes.INT_VALOR_CERO) {
			seccionActualDTO = lstSeccionesDTO.get(Constantes.INT_VALOR_CERO);

			mapControlComponentes = mapControlComponentesDTO;

			/** Se inicializan los valores del Map de respuestas **/
			this.mapRespuestas = new HashMap<>();
			for (Entry<String, Object> elemento : mapRespuestasDTO.entrySet()) {
				this.mapRespuestas.put(elemento.getKey(), null);
			}

			verificaUltimaSeccionFormulario();
			validaConsultaFirmaCiudadano();
		}
		
		// Flujo según presencia de tabla dinámica en primera sección
	    boolean tablaEnPrimeraSeccion = primeraSeccionContieneTablaDinamica();

	    if (tablaEnPrimeraSeccion) {
	    	generarNuevoTramiteComponenteTabla();              
	        inicializarTablasDinamicasSeccionActual();
	    } else {
	        generarNuevoTramite();     
	        dynamicTableBean.setTramiteActualDTO(tramiteActual);
	    }

		/**
		 * Se pregarga información en los componentes que son de tipo Datos Personales
		 * Con Llave, con la información del Usuario Actual.
		 **/
		try {
			complementaComponenteDatosPersonalesLlave();
		} catch (ParseException e) {
			WebResources.errorMessage("msj_error_datos_llave", false);
			LOGGER.error("Ocurrió un error al precargar información del componentes de Datos Personales:: ", e);
		}

		/**
		 * Se pregarga información en los componentes que son de tipo
		 * Datos Persona Moral, con la información del Usuario Actual.
		 **/
		try {
			complementaComponenteDatosPersonaMoral();
		} catch (ParseException e) {
			WebResources.errorMessage("msj_error_datos_persona_moral", false);
			LOGGER.error("Ocurrió un error al precargar información del componentes de Datos Persona Moral: ", e);
		}
	
		return Constantes.RETURN_FORMULARIO_PAGE + Constantes.JSF_REDIRECT;
	}
	
	/**
	 * Verifica si la primera sección del formulario contiene al menos un componente de tabla dinámica.
	 */
	private boolean primeraSeccionContieneTablaDinamica() {
	    if (lstSeccionesDTO == null || lstSeccionesDTO.isEmpty()) {
	        return false;
	    }
	    SeccionesFormularioDTO primeraSeccion = lstSeccionesDTO.get(0);
	    if (primeraSeccion.getLstSubsecciones() != null) {
	        for (SubSeccionesFormularioDTO subseccion : primeraSeccion.getLstSubsecciones()) {
	            if (subseccion.getLstComponentes() != null) {
	                for (ComponenteDTO componente : subseccion.getLstComponentes()) {
	                    if (componente != null &&
	                        componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_TABLA) {
	                        return true;
	                    }
	                }
	            }
	        }
	    }
	    return false;
	}
	
	/**
	 * Inicializa todas las tablas dinámicas de la sección actual
	 */
	public void inicializarTablasDinamicasSeccionActual() {
	    
	    if (seccionActualDTO == null || 
	        seccionActualDTO.getLstSubsecciones() == null || 
	        seccionActualDTO.getLstSubsecciones().isEmpty()) {       
	        return;
	    }
	    
	    // Verificar que el trámite tenga ID
	    if (tramiteActual == null || tramiteActual.getIdTramite() == null) {       
	        return;
	    }
	    
	    for (SubSeccionesFormularioDTO subseccionTemp : seccionActualDTO.getLstSubsecciones()) {
	        if (subseccionTemp.getLstComponentes() == null) continue;
	        
	        for (ComponenteDTO componenteTemp : subseccionTemp.getLstComponentes()) {
	            if (componenteTemp != null && 
	                componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_TABLA) {	                
	                dynamicTableBean.inicializarComponente(componenteTemp, tramiteActual);
	            }
	        }
	    }
	}
	
	/**
	 * Limpia el estado de las tablas dinámicas de la sección actual
	 *
	 */
	private void limpiarEstadoTablasSeccionActual() {
	    if (seccionActualDTO == null || seccionActualDTO.getLstSubsecciones() == null) {
	        return;
	    }
	    
	    List<ComponenteDTO> componentesTabla = new ArrayList<>();
	    for (SubSeccionesFormularioDTO subseccion : seccionActualDTO.getLstSubsecciones()) {
	        if (subseccion.getLstComponentes() == null) continue;
	        for (ComponenteDTO componente : subseccion.getLstComponentes()) {
	            if (componente.getCatTipoComponenteDTO() != null &&
	                componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_TABLA) {
	                componentesTabla.add(componente);
	            }
	        }
	    }
	    
	    if (!componentesTabla.isEmpty() && dynamicTableBean != null) {
	        dynamicTableBean.limpiarEstadoSeccion(componentesTabla);
	    }
	}

	/**
	 * Método que inicializa la edición del formulario para el trámite seleccionado.
	 *
	 * @param tramiteSeleccionado
	 * @param isValidacionTramite
	 * @return
	 */
	public String iniciarEdicionFormulario(TramiteDTO tramiteSeleccionado, boolean isValidacionTramite) {
		this.isValidacionTramite = isValidacionTramite;
		this.isEdicionTramite = true;
		this.isObservacionesTramite = false;
		this.isNuevoTramite = false;
		this.rolPermiteConcluirTramite = false;
		this.rolPermitePrevenirTramite = false;
		this.rolPermiteFinalizarTramite = false;
		this.tramiteActual = tramiteSeleccionado;
		this.proyectoDTO = generarFormularioApplication.getProyectoDTO();
		this.lstSeccionesDTO = generarFormularioApplication.generarListaSecciones();

		firmaCiudadano = detFirmaDigitalDAO.getFirmaCiudadanoPorIdProyecto(proyectoDTO.getIdProyecto());

		if (BeanUtils.isNotNull(authenticatorBean.getUsuarioLogueado())
				&& (authenticatorBean.isRolAdministrador() || authenticatorBean.isRolAdministradorDatosTecnicos() || authenticatorBean.isRolSupervisor() || authenticatorBean.isRolOperador() || authenticatorBean.isRolConsulta())) {
			this.isUsuarioBack = true;
			validarPermisosRevisor();
		}

		/** Se preselecciona la primer sección del formulario **/
		if (BeanUtils.isNotNull(lstSeccionesDTO.size()) && lstSeccionesDTO.size() > Constantes.INT_VALOR_CERO) {
			seccionActualDTO = lstSeccionesDTO.get(Constantes.INT_VALOR_CERO);
			
			inicializarTablasDinamicasSeccionActual();

			mapControlComponentes = generarFormularioApplication.getMapControlComponentes();

			/** Se inicializan los valores del Map de respuestas **/
			this.mapRespuestas = new HashMap<>();
			for (Entry<String, Object> elemento : generarFormularioApplication.generarMapRespuestas().entrySet()) {
				this.mapRespuestas.put(elemento.getKey(), null);
			}

			/**
			 * Si el estatus es ENVIADO y el ROL es OPERADOR o SUPERVISOR se verifica si la
			 * sección contiene únicamente componente Informativos
			 */
			if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO) {
				if (this.isUsuarioBack) {
					/** Se valida si la sección solo contiene Componentes informativos. **/
					validaComponentesInformativosSeccion();
				}
			}
			
			mostrarPanelComentarios();
			renderedBtnConclusion();
			renderedBtnFinalizarRevision();
		}

		/**
		 * Se pregarga información en los componentes que son de tipo Datos Personales
		 * Con Llave, con la información del Usuario Actual.
		 **/
		try {
			complementaComponenteDatosPersonalesLlave();
		} catch (ParseException e) {
			WebResources.errorMessage("msj_error_datos_llave", false);
			LOGGER.error("Ocurrió un error al precargar información del componentes de Datos Personales:: ", e);
		}

		/**
		 * Se pregarga información en los componentes que son de tipo
		 * Datos Persona Moral, con la información del Usuario Actual.
		 **/
		try {
			complementaComponenteDatosPersonaMoral();
		} catch (ParseException e) {
			WebResources.errorMessage("msj_error_datos_persona_moral", false);
			LOGGER.error("Ocurrió un error al precargar información del componentes de Datos Persona Moral: ", e);
		}

		/**
		 * Se debe recuperar la información de la sección actual para precargarla en el
		 * MAP de respuestas
		 **/
		try {
			consultarDatosSeccionActual();
		} catch (Exception e) {
			WebResources.errorMessage("msj_error_datos_seccion", false);
			LOGGER.error("Ocurrió un error al precargar información de la sección actual:: ", e);
		}
		return Constantes.RETURN_FORMULARIO_PAGE + Constantes.JSF_REDIRECT;
	}

	/**
	 * Método auxiliar que revisa las opciones configuradas en el Motor para permir realizar validaciones al trámite
	 * para concluirlo (Rechazarlo o Aprobarlo) y para poder realizar su prevención (Marcar el trámite para correcciones).
	 */
	private void validarPermisosRevisor() {
		if (BeanUtils.isNotNull(seccionesProyectoBean.getGestionUsuarioDTO())) {

			if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorPrevencion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorPrevencion() == false &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == false) {
				//Escenario 1
				if (authenticatorBean.isRolSupervisor()) {
					this.rolPermitePrevenirTramite = true;
					this.rolPermiteConcluirTramite = true;
					this.rolPermiteFinalizarTramite = false;
				} else if (authenticatorBean.isRolOperador()) {
					this.rolPermitePrevenirTramite = false;
					this.rolPermiteConcluirTramite = false;
					this.rolPermiteFinalizarTramite = true;
				}
			} else 	if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorPrevencion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorPrevencion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == false) {
				//Escenario 2
				if (authenticatorBean.isRolSupervisor()) {
					this.rolPermitePrevenirTramite = true;
					this.rolPermiteConcluirTramite = true;
					this.rolPermiteFinalizarTramite = false;
				} else if (authenticatorBean.isRolOperador()) {
					this.rolPermitePrevenirTramite = true;
					this.rolPermiteConcluirTramite = false;
					this.rolPermiteFinalizarTramite = true;
				}
			} else 	if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorPrevencion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorPrevencion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == true) {
				//Escenario 3
				if (authenticatorBean.isRolSupervisor()) {
					this.rolPermitePrevenirTramite = true;
					this.rolPermiteConcluirTramite = true;
					this.rolPermiteFinalizarTramite = false;
				} else if (authenticatorBean.isRolOperador()) {
					this.rolPermitePrevenirTramite = true;
					this.rolPermiteConcluirTramite = true;
					this.rolPermiteFinalizarTramite = false;
				}
			} else 	if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorPrevencion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorPrevencion() == false &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == true) {
				//Escenario 4
				if (authenticatorBean.isRolSupervisor()) {
					this.rolPermitePrevenirTramite = true;
					this.rolPermiteConcluirTramite = true;
					this.rolPermiteFinalizarTramite = false;
				} else if (authenticatorBean.isRolOperador()) {
					this.rolPermitePrevenirTramite = false;
					this.rolPermiteConcluirTramite = true;
					this.rolPermiteFinalizarTramite = false;
				}
			} else 	if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == false &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == false) {
				//Escenario 5
				if (authenticatorBean.isRolSupervisor()) {
					this.rolPermitePrevenirTramite = false;
					this.rolPermiteConcluirTramite = true;
					this.rolPermiteFinalizarTramite = false;
				} else if (authenticatorBean.isRolOperador()) {
					this.rolPermitePrevenirTramite = false;
					this.rolPermiteConcluirTramite = false;
					this.rolPermiteFinalizarTramite = true;
				}
			} else 	if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == false &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
					seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == true) {
				//Escenario 6
				if (authenticatorBean.isRolSupervisor()) {
					this.rolPermitePrevenirTramite = false;
					this.rolPermiteConcluirTramite = true;
					this.rolPermiteFinalizarTramite = false;
				} else if (authenticatorBean.isRolOperador()) {
					this.rolPermitePrevenirTramite = false;
					this.rolPermiteConcluirTramite = true;
					this.rolPermiteFinalizarTramite = false;
				}
			} else {
				LOGGER.info("-->>>Escenario para revisión de trámite no definido<<--");
				LOGGER.info("isHabilitaPrevencion()				:" + seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion());
				LOGGER.info("getPerfilSupervisorPrevencion()	:" + seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorPrevencion());
				LOGGER.info("getPerfilOperadorPrevencion()		:" + seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorPrevencion());
				LOGGER.info("getPerfilSupervisorConclusion()	:" + seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion());
				LOGGER.info("getPerfilOperadorConclusion()		:" + seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion());
				this.rolPermitePrevenirTramite = false;
				this.rolPermiteConcluirTramite = false;
				this.rolPermiteFinalizarTramite = false;
				WebResources.addValidationMessage("msj_escenario_no_identificado", true);
			}
		}


		if (validaPermisosFirmado()) {
			if (authenticatorBean.isRolOperador()) {
				this.rolPermiteConcluirTramite = true;
				this.rolPermitePrevenirTramite = false;
				this.rolPermiteFinalizarTramite = false;
			}
		}
	}

	/**
	 * Método auxiliar que revisa la información del apartado de Archivos de respuesta.
	 */
	public void validaFirmadoTramites() {
		if(BeanUtils.isNotNull(seccionesProyectoBean.getLstRespuesta())){
			setArchivosRespuestaTokenDTO(seccionesProyectoBean.getLstRespuesta().get(0));
		} else {
			setArchivosRespuestaTokenDTO(null);
		}
	}

	/**
	 * Método auxiliar que revisa las opciones configuradas en el Motor para habilitar o no la opción de "Finalizar trámite"
	 * para el rol Operador.
	 * @return
	 */
	public boolean habilitarOpcionFinalizarTramite() {
		boolean deshabilitaOpcion = true;
		if (BeanUtils.isNotNull(seccionesProyectoBean.getGestionUsuarioDTO())) {
			if (authenticatorBean.isRolOperador()) {
				if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorPrevencion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorPrevencion() == false &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == false) {
					//Escenario 1
					deshabilitaOpcion = false;
				} else 	if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorPrevencion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorPrevencion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == false) {
					//Escenario 2
					if(existenSeccionesConObservaciones()) {
						deshabilitaOpcion = true;
					} else {
						deshabilitaOpcion = false;
					}
				} else 	if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorPrevencion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorPrevencion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == true) {
					//Escenario 3
					deshabilitaOpcion = true;
				} else 	if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorPrevencion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorPrevencion() == false &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == true) {
					//Escenario 4
					if(existenSeccionesConObservaciones()) {
						deshabilitaOpcion = true;
					} else {
						deshabilitaOpcion = false;
					}
				} else 	if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == false &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == false) {
					//Escenario 5
					deshabilitaOpcion = false;
				} else 	if(seccionesProyectoBean.getGestionUsuarioDTO().isHabilitaPrevencion() == false &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilSupervisorConclusion() == true &&
						seccionesProyectoBean.getGestionUsuarioDTO().getPerfilOperadorConclusion() == true) {
					//Escenario 6
					deshabilitaOpcion = true;
				}
			}
		}

		return deshabilitaOpcion;
	}

	/**
	 * Método que coloca los datos de authenticación del usuario actual en el map de
	 * respuestas para los componentes que son de tipo Datos Personales con Llave,
	 * en el mismo ciclo se agrega validación para guardar en Map de Componentes
	 * aquellos que registran en BD su información en formato JSON
	 *
	 * @throws ParseException
	 */
	private void complementaComponenteDatosPersonalesLlave() throws ParseException {
		for (SeccionesFormularioDTO seccionTemp : lstSeccionesDTO) {
			if (BeanUtils.isNotNull(seccionTemp.getLstSubsecciones())) {
				for (SubSeccionesFormularioDTO subseccionTemp : seccionTemp.getLstSubsecciones()) {
					for (ComponenteDTO componenteTemp : subseccionTemp.getLstComponentes()) {
						if (BeanUtils.isNotNull(componenteTemp)) {
							if (componenteTemp.getCatTipoComponenteDTO()
									.getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONALES_CON_LLAVE) {
								ComponenteDatosPersonalesLlaveDTO componenteDatosPersonalesConLlave = (ComponenteDatosPersonalesLlaveDTO) componenteTemp;
								mapRespuestas.put(
										Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_1"),
										componenteDatosPersonalesConLlave.isHabilitaCurp()
												? authenticatorBean.getUsuarioLogueado().getCurp()
												: null);
								mapRespuestas.put(
										Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_2"),
										componenteDatosPersonalesConLlave.isHabilitaNombre()
												? authenticatorBean.getUsuarioLogueado().getNombre()
												: null);
								mapRespuestas.put(
										Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_3"),
										componenteDatosPersonalesConLlave.isHabilitaPrimerApellido()
												? authenticatorBean.getUsuarioLogueado().getPrimerApellido()
												: null);
								if (authenticatorBean.getUsuarioLogueado().getSegundoApellido() != null
										&& !authenticatorBean.getUsuarioLogueado().getSegundoApellido()
										.equals("null")) {
									mapRespuestas.put(
											Constantes.NOMBRE_BASE_COLUMNAS
													.concat(componenteTemp.getIdComponente() + "_4"),
											componenteDatosPersonalesConLlave.isHabilitaSegundoApellido()
													? authenticatorBean.getUsuarioLogueado().getSegundoApellido()
													: null);
								} else {
									mapRespuestas.put(Constantes.NOMBRE_BASE_COLUMNAS
											.concat(componenteTemp.getIdComponente() + "_4"), null);
								}
								if (authenticatorBean.getUsuarioLogueado().getTelefono() != null
										&& !authenticatorBean.getUsuarioLogueado().getTelefono().equals("null")) {
									mapRespuestas.put(
											Constantes.NOMBRE_BASE_COLUMNAS
													.concat(componenteTemp.getIdComponente() + "_5"),
											componenteDatosPersonalesConLlave.isHabilitaTelefono()
													? authenticatorBean.getUsuarioLogueado().getTelefono()
													: null);
								} else {
									mapRespuestas.put(Constantes.NOMBRE_BASE_COLUMNAS
											.concat(componenteTemp.getIdComponente() + "_5"), null);
								}
								if (authenticatorBean.getUsuarioLogueado().getCorreo() != null
										&& !authenticatorBean.getUsuarioLogueado().getCorreo().equals("null")) {
									mapRespuestas.put(
											Constantes.NOMBRE_BASE_COLUMNAS
													.concat(componenteTemp.getIdComponente() + "_6"),
											componenteDatosPersonalesConLlave.isHabilitaCorreoElectronico()
													? authenticatorBean.getUsuarioLogueado().getCorreo()
													: null);
								} else {
									mapRespuestas.put(Constantes.NOMBRE_BASE_COLUMNAS
											.concat(componenteTemp.getIdComponente() + "_6"), null);
								}
								if (authenticatorBean.getUsuarioLogueado().getFechaNacimiento() != null
										&& !authenticatorBean.getUsuarioLogueado().getFechaNacimiento().toString()
										.equals("null")) {
									mapRespuestas.put(
											Constantes.NOMBRE_BASE_COLUMNAS
													.concat(componenteTemp.getIdComponente() + "_7"),
											componenteDatosPersonalesConLlave.isHabilitaFechaNacimiento()
													? BeanUtils.convertirDateString(
													authenticatorBean.getUsuarioLogueado().getFechaNacimiento())
													: null);
								} else {
									mapRespuestas.put(Constantes.NOMBRE_BASE_COLUMNAS
											.concat(componenteTemp.getIdComponente() + "_7"), null);
								}

								if (authenticatorBean.getUsuarioLogueado().getSexo() != null
										&& !authenticatorBean.getUsuarioLogueado().getSexo().toString()
										.equals("null")) {
									mapRespuestas.put(
											Constantes.NOMBRE_BASE_COLUMNAS
													.concat(componenteTemp.getIdComponente() + "_8"),
											componenteDatosPersonalesConLlave.isHabilitaSexo()
													? authenticatorBean.getUsuarioLogueado().getSexo()
													: null);
								} else {
									mapRespuestas.put(Constantes.NOMBRE_BASE_COLUMNAS
											.concat(componenteTemp.getIdComponente() + "_8"), null);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Método que coloca los datos de la persona moral del usuario actual en el map de
	 * respuestas para los componentes que son de tipo Datos Persona Moral,
	 * en el mismo ciclo se agrega validación para guardar en Map de Componentes
	 * aquellos que registran en BD su información en formato JSON
	 * Los datos son colocados siempre que se haya ingresado como Persona Moral.
	 *
	 * @throws ParseException
	 */
	private void complementaComponenteDatosPersonaMoral() throws ParseException {
		for (SeccionesFormularioDTO seccionTemp : lstSeccionesDTO) {
			if (BeanUtils.isNotNull(seccionTemp.getLstSubsecciones())) {
				for (SubSeccionesFormularioDTO subseccionTemp : seccionTemp.getLstSubsecciones()) {
					for (ComponenteDTO componenteTemp : subseccionTemp.getLstComponentes()) {
						if (BeanUtils.isNotNull(componenteTemp)) {
							if (componenteTemp.getCatTipoComponenteDTO()
									.getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONA_MORAL) {
								if (BeanUtils.isNotNull(authenticatorBean.getUsuarioLogueado()) && authenticatorBean.getUsuarioLogueado().isEsPersonaMoral()) {
									ComponenteDatosPersonaMoralDTO componenteDatosPersonaMoral = (ComponenteDatosPersonaMoralDTO) componenteTemp;
									PersonaMoralDTO personaMoral = authenticatorBean.getListaPersonasMorales().stream()
											.filter(p -> p.getIdPersonaMoral().equals(authenticatorBean.getUsuarioLogueado().getIdPersonaMoral()))
											.findFirst().orElse(null);
									if(personaMoral != null) {
										mapRespuestas.put(
												Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_1"),
												componenteDatosPersonaMoral.isHabilitaRfc() ? personaMoral.getRfc() : null);
										mapRespuestas.put(
												Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_2"),
												componenteDatosPersonaMoral.isHabilitaPersonaMoral() ? personaMoral.getRazonSocial() : null);
										mapRespuestas.put(
												Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_3"),
												componenteDatosPersonaMoral.isHabilitaFechaVigencia() ? personaMoral.getVigenciaCertificado().toString() : null);
									} else {
										mapRespuestas.put(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_1"), null);
										mapRespuestas.put(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_2"), null);
										mapRespuestas.put(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_3"), null);
									}
								}
								else {
									mapRespuestas.put(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_1"), null);
									mapRespuestas.put(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_2"), null);
									mapRespuestas.put(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_3"), null);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Método que realiza el registro de un nuevo trámite
	 */
	private void generarNuevoTramite() {
	    tramiteActual = new TramiteDTO();
	    if (BeanUtils.isNotNull(this.proyectoDTO.getAcronimo()) && this.proyectoDTO.getAcronimo().length() <= Constantes.LONGITUD_ACRONIMO) {
	        tramiteActual.setFolioSeguimiento(this.proyectoDTO.getAcronimo().concat("-").concat(BeanUtils.convertirDateStringAnioMesDia2Dig(new Date())).concat("-"));
	    } else {
	        tramiteActual.setFolioSeguimiento(BeanUtils.convertirDateStringAnioMesDia2Dig(new Date()).concat("-"));
	    }
	    if (BeanUtils.isNotNull(authenticatorBean.getUsuarioLogueado())) {
	        tramiteActual.setUsuario(new UsuarioDTO(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx()));
	        tramiteActual.getUsuario().setNombre(authenticatorBean.getUsuarioLogueado().getNombreCompleto());
	    } else {
	        tramiteActual.setUsuario(null);
	    }
	    tramiteActual.setTipoPersona(authenticatorBean.getPersonaMoralSeleccionada() != null ? "Sí" : "No");
	    tramiteActual.setCatEstatusTramiteDTO(new CatEstatusTramiteDTO(Constantes.ID_ESTATUS_EN_CAPTURA));
	    tramiteActual.setUsuarioRevisor(null);
	    tramiteActual.setRespuestaFolioPrevencion(null);
	    tramiteActual.setRespuestaFolioConclusion(null);
	    tramiteActual.setUuid(UUID.randomUUID().toString());
	}
	
	
	/**
	 * Genera y persiste el trámite de inmediato (solo cuando hay tabla dinámica en la primera sección).
	 */
	private void generarNuevoTramiteComponenteTabla() {
	    tramiteActual = new TramiteDTO();
	    // Asignación de folio con acrónimo
	    if (BeanUtils.isNotNull(this.proyectoDTO.getAcronimo()) && this.proyectoDTO.getAcronimo().length() <= Constantes.LONGITUD_ACRONIMO) {
	        tramiteActual.setFolioSeguimiento(this.proyectoDTO.getAcronimo().concat("-").concat(BeanUtils.convertirDateStringAnioMesDia2Dig(new Date())).concat("-"));
	    } else {
	        tramiteActual.setFolioSeguimiento(BeanUtils.convertirDateStringAnioMesDia2Dig(new Date()).concat("-"));
	    }
	    // Usuario logueado
	    if (BeanUtils.isNotNull(authenticatorBean.getUsuarioLogueado())) {
	        tramiteActual.setUsuario(new UsuarioDTO(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx()));
	        tramiteActual.getUsuario().setNombre(authenticatorBean.getUsuarioLogueado().getNombreCompleto());
	    } else {
	        tramiteActual.setUsuario(null);
	    }
	    tramiteActual.setTipoPersona(authenticatorBean.getPersonaMoralSeleccionada() != null ? "Sí" : "No");
	    tramiteActual.setFechaCreacion(new Date());

	    if (BeanUtils.isNotNull(tramiteActual.getUsuario()) && BeanUtils.isNotNull(authenticatorBean.getUsuarioLogueado())) {
	        UsuarioDTO usuarioLogueado = authenticatorBean.getUsuarioLogueado();
	        tramiteActual.getUsuario().setIdPersonaMoral(usuarioLogueado.getIdPersonaMoral());
	        tramiteActual.getUsuario().setEsPersonaMoral(usuarioLogueado.isEsPersonaMoral());
	    }

	    tramiteActual.setCatEstatusTramiteDTO(new CatEstatusTramiteDTO(Constantes.ID_ESTATUS_EN_CAPTURA));
	    tramiteActual.setUsuarioRevisor(null);
	    tramiteActual.setRespuestaFolioPrevencion(null);
	    tramiteActual.setRespuestaFolioConclusion(null);
	    tramiteActual.setUuid(UUID.randomUUID().toString());

	    // Persistencia inmediata
	    tramiteActual.setIdTramite(formularioFacade.registrarTramite(tramiteActual));
	}

	/**
	 * Método privado auxiliar para validar si existen componentes de carga de
	 * documentos y si esos componentes son requeridos.
	 *
	 * @return
	 */
	private boolean documentosRequeridosCargados() {
		boolean isRequeridoDocumento = false;
		// Solo entra a esta validación si se encuentra en estatus de captura o
		// correcciones
		if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_EN_CAPTURA
				|| tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_CORRECIONES) {
			// Se itera las subsecciones
			for (SubSeccionesFormularioDTO subSeccionesDTO : seccionActualDTO.getLstSubsecciones()) {
				// Se valida si existen componentes en esta sección
				if (subSeccionesDTO.getLstComponentes() != null && !subSeccionesDTO.getLstComponentes().isEmpty()) {
					// Se itera los componentes de la sección para busar si existen componentes de
					// carga de documentos
					for (ComponenteDTO componenteDTO : subSeccionesDTO.getLstComponentes()) {
						// Se valida si existe el componente de carga de documetnos y si este componente
						// es requerido
						if (componenteDTO.getCatTipoComponenteDTO()
								.getIdTipoComponente() == Constantes.ID_COMPONENTE_CARGA_DOCUMENTOS
								&& componenteDTO.isRequerido()) {
							// Si el componente es requerido, se valida si aún no se carga el valor en el
							// mapRespuestas
							if (mapRespuestas.get("componente_" + componenteDTO.getIdComponente()) == null) {
								isRequeridoDocumento = true;
								enviarMensajeVista("msj_campo_requerido", "frmFormulario:componente_"
												+ componenteDTO.getIdComponente() + ":cdoc_" + componenteDTO.getIdComponente(),
										FacesMessage.SEVERITY_ERROR);
							}
						}
					}
					if (isRequeridoDocumento) {
						return isRequeridoDocumento;
					}
				}
			}
		}
		return isRequeridoDocumento;
	}

	/**
	 * Método auxiliar que valida si el usuario actual puede realizar la captura de un nuevo trámite.
	 * @return
	 */
	public boolean permiteCapturaTramite() {
		boolean permiteContinuar = true;
		/**
		 * Se valida si es la primer sección que se encuentra registrando
		 */
		SeccionesFormularioDTO seccionTmp = lstSeccionesDTO.get(0);
		if (seccionTmp.getIdSeccionFormulario() == seccionActualDTO.getIdSeccionFormulario()) {
			/**Se agrega validación para que no permita generar más de un trámite cuando desde el motor marcó la opción para
			 * limitar a un trámite por cuenta llave**/
			if(seccionesProyectoBean.getAccesoLlaveDTO().isAutenticacionCiudadano() && seccionesProyectoBean.getAccesoLlaveDTO().isLimitarUnicoTramite()) {
				/**Se consulta trámites del usuario actual**/
				TramiteDTO tramiteBusqueda = new TramiteDTO();
				tramiteBusqueda.setUsuario(new UsuarioDTO(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx()));
				try {
					List<TramiteDTO> lstTramites = formularioDAO.consultarTamitesUsuario(tramiteBusqueda);
					if(BeanUtils.isNotNull(lstTramites)) {
						int tramitesNoRechazados = Constantes.INT_VALOR_CERO;
						for (TramiteDTO tramite : lstTramites) {
							if(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() != Constantes.ID_ESTATUS_RECHAZADO) {
								tramitesNoRechazados ++;
								if(tramitesNoRechazados >= Constantes.MAXIMO_TRAMITES_USUARIO) {
									/**Solo se revisa trámites cuando se encuentra registrando un nuevo trámite, para la edición no se debe validar en número de trámites que
									 * tiene registrados**/
									if(isNuevoTramite == true) {
										if(tramitesNoRechazados == Constantes.MAXIMO_TRAMITES_USUARIO) {
											permiteContinuar = false;
											break;
										}
									}
								}
							}
						}
					}
				} catch (Exception e) {
					permiteContinuar = false;
					LOGGER.error("Ocurrió un error al consultar trámites del usuario actual: ", e);
					WebResources.errorMessage("msj_error_busqueda", true);
				}
			}
		}
		return permiteContinuar;
	}

	private void guardarNuevoTramite() {
		
	    if (isNuevoTramite && tramiteActual.getIdTramite() == null) {
	        tramiteActual.setFechaCreacion(new Date());
	        if (BeanUtils.isNotNull(tramiteActual.getUsuario()) && BeanUtils.isNotNull(authenticatorBean.getUsuarioLogueado())) {
	            tramiteActual.getUsuario().setIdPersonaMoral(authenticatorBean.getUsuarioLogueado().getIdPersonaMoral());
	            tramiteActual.getUsuario().setEsPersonaMoral(authenticatorBean.getUsuarioLogueado().isEsPersonaMoral());
	        }
	        tramiteActual.setIdTramite(formularioFacade.registrarTramite(tramiteActual));
	        isNuevoTramite = false;
	        isEdicionTramite = true;
	    }
	}
	
	/**
	 * Método que actualiza la información de la sección actual y realiza la
	 * consulta de respuestas de la siguiente sección.
	 *
	 */
	public String siguienteSeccion() {
		if(!permiteFinalizarTramiteCuandoExisteLC()) {
			return null;
		}
		//String redirect = Constantes.RETURN_SAME_PAGE;
		String redirect = Constantes.RETURN_FORMULARIO_PAGE + Constantes.JSF_REDIRECT;
		/**Se agrega validación para no permitir el guardado de un trámite, cuando en la configuración del motor se encuentra
		 * la restricción para 1 trámite (con estatus distinto a 6 Rechazado)**/
		if(permiteCapturaTramite() == false) {
			WebResources.addValidationMessage("msj_no_registro_formulario", true);
			return redirect;
		}

		
		/**
		 * Si es un nuevo trámite se valida que sea la primer sección para realizar el
		 * registro del trámite.
		 */
		this.guardarNuevoTramite();

		/** Se llama a la validación de carga de documentos, si un componente de carga de documentos es requerido y aún no se carga su
		 *  value, se queda en la misma pagina y se muestran los mensajes.
		 **/
		if (documentosRequeridosCargados()) {
			return redirect;
		}

		try {
			
			if (!validarTablasDinamicas()) {
	            return redirect;
	        }
			
			/**
			 * Solo se registra movimiento en BD cuando el estatus del trámite es En captura y no es un Usuario del back o cuando el estatus
			 * es en Correcciones no es un usuario del back y la sección actual tiene observaciones, y se notifica actualización o registro
			 * de información en todas las secciones, menos en la última.
			 **/
			if ((tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_EN_CAPTURA && ((this.isUsuarioBack == true && isValidacionTramite == false) || isUsuarioBack == false))
					|| (tramiteActual.getCatEstatusTramiteDTO() .getIdEstatusTramite() == Constantes.ID_ESTATUS_CORRECIONES && ((this.isUsuarioBack == true && isValidacionTramite == false) || isUsuarioBack == false)
					&& seccionActualDTO.isContieneObservaciones())) {
				guardarActualizarDatosSeccionActual();
				if ((lstSeccionesDTO.get(lstSeccionesDTO.size() - 1).getIdSeccionFormulario() != seccionActualDTO.getIdSeccionFormulario())) {
					if (isEdicionTramite) {
						/** Se notifica actualización cuando se realizo movimiento en BD **/
						WebResources.successMessage("msj_seccion_actualizada", true);
					} else {
						/** Se notifica registro cuando se realizo movimiento en BD **/
						WebResources.successMessage("msj_seccion_registrada", true);
					}
				}
			}

			/**
			 * Si el rol actual es un Operador o Supervisor y el trámite está en estatus
			 * ENVIADO, se registra en cada sección si contiene o no Observaciones.
			 */
			if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO) {
				/** Se revisa que la sección no contenga únicamente componentes informativos **/
				if (isSeccionInformativa == false) {
					// OOC : SE COMENTA LA LÍNEA DEL IF Y SE CAMBIA VALIDACIÓN PARA QUE SUPERVISOR Y OPERADOR PUEDAN HACER OBSERVACIONES
//					if (this.isUsuarioBack && (this.rolPermiteConcluirTramite || this.rolPermitePrevenirTramite)) {
					if ((this.isUsuarioBack && isValidacionTramite == true) && (authenticatorBean.isRolSupervisor() || authenticatorBean.isRolOperador())) {
						tramiteActual.setUsuarioRevisor(new UsuarioDTO(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx()));
						/** Realizar insert a la sección actual **/
						formularioFacade.registrarObservacionesSeccion(tramiteActual, seccionActualDTO, Constantes.NOMBRE_BASE_TABLAS
								.concat(seccionActualDTO.getIdSeccionFormulario().toString()));

						/** Se actualizan valores de revisión en listado de secciones **/
						for (int i = 0; i < this.lstSeccionesDTO.size() - 1; i++) {
							if (this.lstSeccionesDTO.get(i).getIdSeccionFormulario() == seccionActualDTO.getIdSeccionFormulario()) {
								this.lstSeccionesDTO.get(i).setContieneObservaciones(seccionActualDTO.isContieneObservaciones());
								this.lstSeccionesDTO.get(i).setObservaciones(seccionActualDTO.getObservaciones());
								break;
							}
						}
						WebResources.successMessage("msj_seccion_revisada", true);
					}
				}
			}

			if (lstSeccionesDTO != null) {
				int indiceActual = Constantes.INT_VALOR_CERO;
				for (int i = 0; i < lstSeccionesDTO.size(); i++) {
					if (seccionActualDTO.getIdSeccionFormulario() == lstSeccionesDTO.get(i).getIdSeccionFormulario()) {
						indiceActual = i;
						break;
					}
				}

				int siguienteIndice = encontrarSiguienteSeccionVisible(indiceActual);

				if (siguienteIndice != -1) {
					
					limpiarEstadoTablasSeccionActual();
					
					if (dynamicTableBean != null) {
						dynamicTableBean.limpiar();
					}
					
					seccionActualDTO = lstSeccionesDTO.get(siguienteIndice);
					
					inicializarTablasDinamicasSeccionActual();
					
					if (isEdicionTramite) {
						try {
							consultarDatosSeccionActual();
						} catch (Exception e) {
							WebResources.errorMessage("msj_error_datos_seccion", true);
							LOGGER.error("Ocurrió un error al precargar información de la sección siguiente:: ", e);
						}
					}
					verificaUltimaSeccionFormulario();
					validaConsultaFirmaCiudadano();
				} else {
					/**
					 * En la última sección, solo se actualizará el estatus correspondiente si se
					 * editó información del trámite y se actualiza trámite a su estatus
					 * correspondiente
					 * 
					 *26/06/2026
					 *Cuando termina la captura se agrega recuperar de la base los datos del catalogo debido que  
					 * se puede configurar para personaliza y no es necesariamente la descripcion que deja en duro
					 * se marca con //1 //2 //3 //4 donde se ajusta la busqueda
					 */
					CatEstatusTramiteDTO  catEstatusTramiteDTO;
					
					/**Se valida primero si trámite contiene un usuario asignado, esto debido a que se tienen trámites que no 
					 * requieren acceso con llave
					 * 30/07/2026 
					 * Consultamos datos del usuario, ya que en la bandeja del ciudadano no se cargan los datos del mismo al tramite**/
					if(BeanUtils.isNotNull(tramiteActual.getUsuario())) {
						tramiteActual.setUsuario(usuarioDAO.buscarPorId(tramiteActual.getUsuario().getIdUsuarioLlaveCdmx()));
					} else {
						tramiteActual.setUsuario(null);
					}
					
					switch (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite()) {

						case Constantes.ID_ESTATUS_EN_CAPTURA:

							if(firmaCiudadano) {
								return iniciarFirmaTramiteCiudadano();
							}

							if(seccionesProyectoBean.getProyectoDTO().isAviso()) {
								//1
								catEstatusTramiteDTO = catEstatusTramiteDAO.buscarPorIdEstatus(Constantes.ID_ESTATUS_APROBADO);
								tramiteActual.setCatEstatusTramiteDTO(catEstatusTramiteDTO);
							} else if (this.generaLineaCapturaBean.permitirGeneracionLineaDeCaptura(tramiteActual, proyectoDTO, true)) {
								/**
								 * Al finalizar el registro de todas las secciones, se valida si el trámite se encuentra habilitado para pago,
								 * si requiere pago, se actualiza trámite como PENDIENTE DE PAGO, de lo contrario se actualiza como ENVIADO y
								 * se actualiza su fecha de creación
								 **/
								//2
								catEstatusTramiteDTO = catEstatusTramiteDAO.buscarPorIdEstatus(Constantes.ID_ESTATUS_PENDIENTE_PAGO);
								tramiteActual.setCatEstatusTramiteDTO(catEstatusTramiteDTO);
							} else {
								//3
								catEstatusTramiteDTO = catEstatusTramiteDAO.buscarPorIdEstatus(Constantes.ID_ESTATUS_ENVIADO);
								tramiteActual.setCatEstatusTramiteDTO(catEstatusTramiteDTO);
							}
							tramiteActual.setFechaCreacion(new Date());

							/**
							 * Se consultan datos del trámite para mostrarlos al usuario al final del registro.
							 **/
							TramiteDTO tramiteTemp = formularioDAO.consultarFolioTramite(tramiteActual);
							tramiteActual.setFolioSeguimiento(tramiteTemp.getFolioSeguimiento());
							tramiteActual.setUuid(tramiteTemp.getUuid());
							
							tramiteActual.setProyectoDTO(proyectoDTO);
							try {
								formularioFacade.notificarRegistroTramite(tramiteActual);
								
								//Envio de notificacion de webHook
								iniciaProcesoNotificacionWebHook();
								
								WebResources.successMessage("msj_tramite_registrado", true);
								FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tramiteCompleto", true);
								redirect = Constantes.RETURN_FIN_FORMULARIO_PAGE + Constantes.JSF_REDIRECT;
							} catch (Exception e) {
								LOGGER.error("Ocurrió un error al finalizar el registro del trámite "
										+ tramiteActual.getFolioSeguimiento(), e);
								WebResources.addErrorMessage("msj_error_registro_tramite", true);
							}
							break;

						case Constantes.ID_ESTATUS_CORRECIONES:
							if ((this.isUsuarioBack && isValidacionTramite) ||
									((authenticatorBean.isRolAdministrador() || authenticatorBean.isRolAdministradorDatosTecnicos()) && isValidacionTramite == true)) {
								redirect = bandejaValidacionTramitesBean.inicializar();
								break;
							} else {
								/** Al realizar las correcciones, se actualiza trámite como CORREGIDO **/
								//4
								catEstatusTramiteDTO = catEstatusTramiteDAO.buscarPorIdEstatus(Constantes.ID_ESTATUS_CORREGIDO);
								tramiteActual.setCatEstatusTramiteDTO(catEstatusTramiteDTO);
								formularioFacade.actualizarEstatusTramite(tramiteActual);
								
								//Envio de notificacion de webHook
								iniciaProcesoNotificacionWebHook();

								WebResources.successMessage("msj_tramite_corregido", true);
								redirect = bandejaTramitesBean.inicializar();
								break;
							}
						default:
							if ((this.isUsuarioBack && isValidacionTramite == true) || authenticatorBean.isRolAdministrador() || authenticatorBean.isRolAdministradorDatosTecnicos()) {
								redirect = bandejaValidacionTramitesBean.inicializar();
								break;
							} else {
								/**
								 * Si el estatus es diferente al que puede actualizar, solo se redirecciona a la
								 * bandeja en su última sección
								 **/
								redirect = bandejaTramitesBean.inicializar();
								break;
							}
					}
				}
				
				renderedBtnConclusion();
				renderedBtnFinalizarRevision();
			}
			PrimeFaces.current().scrollTo("messages");

			/**
			 * Si el estatus es ENVIADO y el ROL es OPERADOR o SUPERVISOR se verifica si la
			 * sección contiene únicamente componente Informativos
			 */
			if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO && (this.isUsuarioBack && isValidacionTramite == true)) {
				/** Se valida si la sección solo contiene Componentes informativos. **/
				validaComponentesInformativosSeccion();
			}
		} catch (Exception e) {
			WebResources.errorMessage("msj_error_registro_seccion", true);
			LOGGER.error("Ocurrió un error al guardar/actualizar la información de la sección en BD:: ", e);
		}

		return redirect;
	}

	/**
	 * Valida que las tablas dinámicas cumplan con el mínimo de filas
	 */
	private boolean validarTablasDinamicas() {
		
	    List<Integer> listEstatusVal = Arrays.asList(Constantes.ID_ESTATUS_CORRECIONES, Constantes.ID_ESTATUS_EN_CAPTURA);
	    Predicate<CatEstatusTramiteDTO> prEstatusVal = p -> listEstatusVal.contains(p.getIdEstatusTramite());
	    Predicate<SeccionesFormularioDTO> prSeccionVal = SeccionesFormularioDTO::isContieneObservaciones;
	    Predicate<CatEstatusTramiteDTO> prEstatusCorrec = p -> p.getIdEstatusTramite() == Constantes.ID_ESTATUS_CORRECIONES;
	    
	    if ((prEstatusCorrec.test(tramiteActual.getCatEstatusTramiteDTO()) && prSeccionVal.negate().test(seccionActualDTO))
	            || prEstatusVal.negate().test(tramiteActual.getCatEstatusTramiteDTO())) {
	        return true;
	    }
	    
	    if (dynamicTableBean == null) {
	        return true;
	    }

	    boolean todasValidas = true;
	    boolean hayTablaRequerida = false;
	    
	    for (SubSeccionesFormularioDTO subseccionTemp : seccionActualDTO.getLstSubsecciones()) {
	        for (ComponenteDTO componenteTemp : subseccionTemp.getLstComponentes()) {
	            if (componenteTemp.getCatTipoComponenteDTO() != null &&
	                componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_TABLA) {
	                
	                Long idComponente = componenteTemp.getIdComponente();
	                
	                if (dynamicTableBean.isTablaRequerida(idComponente)) {
	                    hayTablaRequerida = true;
	                    int filasLlenas = dynamicTableBean.getTotalFilasLlenas(idComponente);
	                    int minRows = dynamicTableBean.getMinRows(idComponente);
	                    
	                    if (filasLlenas < minRows) { 	                       
	                        WebResources.addValidationMessage("msg_tabla_obligatoria", true);	                       
	                        todasValidas = false;	                      
	                    }
	                }
	            }
	        }
	    }
	    
	    if (hayTablaRequerida && !todasValidas) {
	        return false;
	    }
	    
	    return true;
	}
	
	private boolean permiteFinalizarTramiteCuandoExisteLC() {
		boolean permiteFinalizar = true;
		if (this.generaLineaCapturaBean.permitirGeneracionLineaDeCaptura(tramiteActual, proyectoDTO, esUltimaSeccion())
				&& this.generaLineaCapturaBean.isDeshabilitarBotonFinalizarTramite()) {
			WebResources.addValidationMessage("msg_es_necesario_generar_linea_de_captura", false);
			return false;
		}
		return permiteFinalizar;
	}

	public boolean isFinalizarTramite() {
		return lstSeccionesDTO.get(lstSeccionesDTO.size()-1).getIdSeccionFormulario() == seccionActualDTO.getIdSeccionFormulario();
	}

	/**
	 * Método auxiliar que valida si en la sección actual únicamente existen
	 * componentes informativos.
	 *
	 * @return
	 */
	public void validaComponentesInformativosSeccion() {
		isSeccionInformativa = true;

		/**
		 * Se verifica que la sección no contenga únicamente Componentes informativos,
		 * dichos componentes no insertan datos en la BD por lo tanto no debería tener
		 * observaciones esta sección.
		 **/
		List<String> lstColumnas = new ArrayList<String>();
		for (Iterator<Map.Entry<String, ControlComponentesDTO>> elementos = mapControlComponentes.entrySet()
				.iterator(); elementos.hasNext();) {
			Map.Entry<String, ControlComponentesDTO> elementoTmp = elementos.next();
			if (elementoTmp.getValue().getNombreTabla().equals(
					Constantes.NOMBRE_BASE_TABLAS.concat(seccionActualDTO.getIdSeccionFormulario().toString()))) {
				lstColumnas.add(elementoTmp.getValue().getNombreColumna());
			}
		}

		if (lstColumnas.size() > Constantes.INT_VALOR_CERO) {
			isSeccionInformativa = false;
		}
	}

	/**
	 * Método que verifica si el usuario logueado tiene correo para las
	 * notificaciones.
	 *
	 * @return
	 */
	public UsuarioDTO consultarUsuarioPorCorreo() {
		UsuarioDTO correoUsuario = null;
		/**
		 * Si es un usuario logueado con llave, se toma el correo que se recupera en el
		 * inicio de sesión
		 **/
		if (BeanUtils.isNotNull(authenticatorBean.getUsuarioLogueado())) {
			if (BeanUtils.isNotNull(authenticatorBean.getUsuarioLogueado().getCorreo())
					&& !(authenticatorBean.getUsuarioLogueado().getCorreo().equalsIgnoreCase("null"))) {
				correoUsuario = authenticatorBean.getUsuarioLogueado();
			}
		}
		return correoUsuario;
	}

	/**
	 * Método que busca la sección anterior del listado de secciones y le precarga los datos de Respuesta.
	 */
	public void anteriorSeccion() {
		if (lstSeccionesDTO != null) {
			int indiceActual = Constantes.INT_VALOR_CERO;
			for (int i = 0; i < lstSeccionesDTO.size(); i++) {
				if (seccionActualDTO.getIdSeccionFormulario() == lstSeccionesDTO.get(i).getIdSeccionFormulario()) {
					indiceActual = i;
					break;
				}
			}

			int anteriorIndice = encontrarAnteriorSeccionVisible(indiceActual);

			if (anteriorIndice != -1) {
				
				limpiarEstadoTablasSeccionActual();
				
				if (dynamicTableBean != null) {
					dynamicTableBean.limpiar();
				}
				
				seccionActualDTO = lstSeccionesDTO.get(anteriorIndice);
				
				inicializarTablasDinamicasSeccionActual();
				
				/**
				 * Se debe recuperar la información de la sección actual para precargarla en el MAP de respuestas
				 **/
				try {
					consultarDatosSeccionActual();
				} catch (Exception e) {
					WebResources.errorMessage("msj_error_datos_seccion", false);
					LOGGER.error("Ocurrió un error al precargar información de la sección anterior:: ", e);
				}
				PrimeFaces.current().scrollTo("messages");

				/**
				 * Si el estatus es ENVIADO y el ROL es OPERADOR o SUPERVISOR se verifica si la
				 * sección contiene únicamente componente Informativos
				 */
				if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO && (this.isUsuarioBack &&  isValidacionTramite == true)) {
					/** Se valida si la sección solo contiene Componentes informativos. **/
					validaComponentesInformativosSeccion();
				}
				renderedBtnConclusion();
				renderedBtnFinalizarRevision();
			} else {
				WebResources.successMessage("msj_primera_seccion", false);
			}
		}
	}

	/**
	 * Método que prepara la información para guardado o actualizado de la sección
	 * actual.
	 *
	 * @throws Exception
	 *
	 */
	private void guardarActualizarDatosSeccionActual() throws Exception {
	    
		 // Guardar datos de todas las tablas dinámicas de la sección actual
	     if (dynamicTableBean != null && seccionActualDTO != null) {
	        for (SubSeccionesFormularioDTO subseccionTemp : seccionActualDTO.getLstSubsecciones()) {
	            if (subseccionTemp.getLstComponentes() == null) continue;
	            for (ComponenteDTO componenteTemp : subseccionTemp.getLstComponentes()) {
	                if (componenteTemp.getCatTipoComponenteDTO() != null &&
	                    componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_TABLA) {
	                    
	                    Long idComponente = componenteTemp.getIdComponente();	                    
	                    dynamicTableBean.guardarDatosTabla(idComponente, tramiteActual.getIdTramite());
	                }
	            }
	        }
	     }
		 
		 List<String> lstColumnas = new ArrayList<String>();
			for (Iterator<Map.Entry<String, ControlComponentesDTO>> elementos = mapControlComponentes.entrySet()
					.iterator(); elementos.hasNext();) {
				Map.Entry<String, ControlComponentesDTO> elementoTmp = elementos.next();
				if (elementoTmp.getValue().getNombreTabla().equals(
						Constantes.NOMBRE_BASE_TABLAS.concat(seccionActualDTO.getIdSeccionFormulario().toString()))) {
					lstColumnas.add(elementoTmp.getValue().getNombreColumna());
				}
			}
			/**
			 * Se verifica que la sección no contenga únicamente Componentes informativos, dichos componentes no insertan datos en la BD
			 **/
			if (lstColumnas.size() > Constantes.INT_VALOR_CERO) {
				tramiteActual.setProyectoDTO(proyectoDTO);
				/** Se consulta si existe información de la sección para el trámite actual **/
				if (formularioDAO.existeRegistroSeccionPorTramite(tramiteActual, Constantes.ESQUEMA_INTERPRETE.concat(".").concat(Constantes.NOMBRE_BASE_TABLAS
						.concat(seccionActualDTO.getIdSeccionFormulario().toString())))) {
					/** Realizar update a la sección actual **/
					formularioFacade.actualizarInformacionSeccion(tramiteActual, seccionActualDTO.getIdSeccionFormulario(),
							Constantes.NOMBRE_BASE_TABLAS.concat(seccionActualDTO.getIdSeccionFormulario().toString()), lstColumnas, mapRespuestas, mapControlComponentes);
				} else {
					/** Realizar insert a la sección actual **/
					formularioFacade.registrarInformacionSeccion(tramiteActual, seccionActualDTO.getIdSeccionFormulario(),
							Constantes.NOMBRE_BASE_TABLAS.concat(seccionActualDTO.getIdSeccionFormulario().toString()), lstColumnas, mapRespuestas, mapControlComponentes);
				}
			} else {
				//LOGGER.info("La sección solo contiene componentes informativos, no registran información en la Base de Datos.");
			}
		    
	    
	  
	}

	/**
	 * Método auxiliar que realiza la consulta de datos registrados de la sección actual y la precarga en el Map de respuestas, para presentarla en el
	 * formulario.
	 *
	 * @throws Exception
	 */
	private void consultarDatosSeccionActual() throws Exception {
		/**
		 * Se obtienen las columnas que serán consultadas en Base de Datos, se filtran
		 * columnas de la sección actual
		 **/
		List<String> lstColumnas = new ArrayList<String>();
		for (Iterator<Map.Entry<String, ControlComponentesDTO>> elementos = mapControlComponentes.entrySet()
				.iterator(); elementos.hasNext();) {
			Map.Entry<String, ControlComponentesDTO> elementoTmp = elementos.next();
			if (elementoTmp.getValue().getNombreTabla().equals(
					Constantes.NOMBRE_BASE_TABLAS.concat(seccionActualDTO.getIdSeccionFormulario().toString()))) {
				lstColumnas.add(elementoTmp.getValue().getNombreColumna());
			}
		}
		
		
	    //Inicializar componentes de tabla dinámica antes de cargar respuestas
		for (SubSeccionesFormularioDTO subseccionTemp : seccionActualDTO.getLstSubsecciones()) {
		    for (ComponenteDTO componenteTemp : subseccionTemp.getLstComponentes()) {
		        if (componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_TABLA) {
		            dynamicTableBean.inicializarComponente(componenteTemp, tramiteActual);
		        }
		    }
		}
		
		/**
		 * Se verifica que la sección no contenga únicamente Componentes informativos,
		 * dichos componentes no insertan datos en la BD
		 **/
		if (lstColumnas.size() > 0) {

			/**
			 * Se consulta si existe información de la sección para el trámite actual, esto
			 * se puede dar por trámites existentes a los que se les habilita una nueva
			 * sección que anteriormente no fue registrada
			 **/
			if (formularioDAO.existeRegistroSeccionPorTramite(tramiteActual,
					Constantes.ESQUEMA_INTERPRETE.concat(".").concat(Constantes.NOMBRE_BASE_TABLAS
							.concat(seccionActualDTO.getIdSeccionFormulario().toString())))) {

				/**
				 * Se consultar la información registrada en Base de datos de la Sección actual
				 **/
				Map<String, Object> respuestasFormulario = formularioDAO.consultarRespuestasSeccion(tramiteActual,
						Constantes.ESQUEMA_INTERPRETE.concat(".")
								.concat(Constantes.NOMBRE_BASE_TABLAS
										.concat(seccionActualDTO.getIdSeccionFormulario().toString())),
						mapControlComponentes, lstColumnas);

				/**
				 * Se consulta si existen observaciones en la sección actual.
				 */
				if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_CORRECIONES
						|| tramiteActual.getCatEstatusTramiteDTO()
						.getIdEstatusTramite() == Constantes.ID_ESTATUS_CORREGIDO
						|| tramiteActual.getCatEstatusTramiteDTO()
						.getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO
						|| tramiteActual.getCatEstatusTramiteDTO()
						.getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO
						// OOC : SE AGREGA CONDICIÓN PARA MOSTRAR COMENTARIOS TANTO AL OPERADOR Y SUPERVISOR
						|| tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO
						|| tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_REVISADO) {
					SeccionesFormularioDTO seccionTemp = formularioDAO.consultarObservacionesSeccion(tramiteActual,
							Constantes.ESQUEMA_INTERPRETE.concat(".").concat(Constantes.NOMBRE_BASE_TABLAS
									.concat(seccionActualDTO.getIdSeccionFormulario().toString())));
					if (BeanUtils.isNotNull(seccionTemp)) {
						seccionActualDTO.setContieneObservaciones(seccionTemp.isContieneObservaciones());
						seccionActualDTO.setObservaciones(seccionTemp.getObservaciones());
					}
				}

				/**
				 * Se coloca la información recuperada en el map de respuestas y en en el DTO de
				 * componente si se requiere por ejemplo para el componente Check multiple
				 **/
				for (Entry<String, Object> respuestaTemp : respuestasFormulario.entrySet()) {
					this.mapRespuestas.put(respuestaTemp.getKey(), respuestaTemp.getValue());

					/**
					 * Para algunos componentes se requiere convertir el tipo de dato recuperado
					 * como Json, para poderlo setear al componente se agrega ejemplo para el caso
					 * del Check box, este componente registra información en Json en la BD, cuando
					 * se consulta se tiene que mandar a su bean para hacer la conversión de Json al
					 * listado que maneja este componente para selección múltiple
					 **/
					if (mapControlComponentes.get(respuestaTemp.getKey())
							.getIdTipoComponente() == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO) {

						if (BeanUtils.isNotNull(respuestaTemp.getValue())) {
							/** Se invoca método de apoyo para conversión del bean del CheckBox **/
							List<DetElementosCheckboxDTO> lstOpcionesCheckRespuesta = checkBoxBean
									.obtenerOpcionesCheck(respuestaTemp.getValue().toString());

							/**
							 * Se itera en los componente de la sección Actual, o la que será mostrada en el
							 * formulario
							 **/
							if (BeanUtils.isNotNull(seccionActualDTO.getLstSubsecciones())) {
								for (SubSeccionesFormularioDTO subseccionTemp : seccionActualDTO.getLstSubsecciones()) {
									for (ComponenteDTO componenteTemp : subseccionTemp.getLstComponentes()) {
										if (BeanUtils.isNotNull(componenteTemp)) {
											/**
											 * Se evalua si la respuesta consultada en BD es la que le corresponde al
											 * componente de la iteración actual
											 **/
											if (componenteTemp.getIdComponente().longValue() == mapControlComponentes
													.get(respuestaTemp.getKey()).getIdComponente().longValue()) {
												/**
												 * Se obtiene listado de elementos posibles del check del componente
												 * actual
												 **/
												List<DetElementosCheckboxDTO> lstElementosCheck = ((ComponenteCheckboxDTO) componenteTemp)
														.getDetElementosCheckboxDTO();

												List<DetElementosCheckboxDTO> lstOpcionesSeleccionadas = new ArrayList<DetElementosCheckboxDTO>();
												for (DetElementosCheckboxDTO opcionRespuestaTemp : lstOpcionesCheckRespuesta) {
													for (DetElementosCheckboxDTO elementoTemp : lstElementosCheck) {
														if (elementoTemp.getIdElementoCheckbox().longValue() == opcionRespuestaTemp
																.getIdElementoCheckbox().longValue()) {
															lstOpcionesSeleccionadas.add(elementoTemp);
														}
													}
												}
												((ComponenteCheckboxDTO) componenteTemp)
														.setLstOpcionesSeleccionadas(lstOpcionesSeleccionadas);
											}
										}
									}
								}
							}
						}
					} // Fin ID_COMPONENTE_CHECKBOX_GRUPO

					if (mapControlComponentes.get(respuestaTemp.getKey())
							.getIdTipoComponente() == Constantes.ID_COMPONENTE_CARGA_DOCUMENTOS) {
						if (BeanUtils.isNotNull(respuestaTemp.getValue())) {
							/**
							 * Se invoca método de apoyo para conversión del JSON a la lista de documentos
							 * cargados
							 **/
							List<ComponenteCargaDocumentosDTO> lstDocumentos = cargaDocumentoBean
									.convertirPahtsDocumentos(respuestaTemp.getValue().toString());
							/**
							 * Se itera en los componente de la sección Actual, o la que será mostrada en el
							 * formulario
							 **/
							if (BeanUtils.isNotNull(seccionActualDTO.getLstSubsecciones())) {
								for (SubSeccionesFormularioDTO subseccionTemp : seccionActualDTO.getLstSubsecciones()) {
									for (ComponenteDTO componenteTemp : subseccionTemp.getLstComponentes()) {
										if (BeanUtils.isNotNull(componenteTemp)) {
											if (Long.parseLong(componenteTemp.getIdComponente()+"") == Long.parseLong(mapControlComponentes
													.get(respuestaTemp.getKey()).getIdComponente()+"")) {
												/**
												 * Se itera la lista de documentos cargados, para poder setear el id
												 * componente al que pertenecen
												 **/
												for (ComponenteCargaDocumentosDTO documento : lstDocumentos) {
													documento.setIdComponente(componenteTemp.getIdComponente());
													documento.setIdComponenteCarga(
															((ComponenteCargaDocumentosDTO) componenteTemp)
																	.getIdComponenteCarga());
												}
												/**
												 * Se setea la lista de documentos cargados para que sean visibles en el
												 * detalle del trámite
												 **/
												((ComponenteCargaDocumentosDTO) componenteTemp)
														.setArchivos(lstDocumentos);
											}
										}
									}
								}
							}
						}
					}
				}
			} else {
				/**
				 * Se notifica al usuario que no existe registro previo de datos de esta sección
				 **/
				WebResources.successMessage("msj_sin_datos_seccion", true);
			}
		} else {
			//LOGGER.info("No se tienen columnas generadas para la sección actual. (Solo contiene componentes informativos).");
		}
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

	/**
	 * Método que realizará la generación de un comprobante.
	 *
	 * 26/06/2026 WEBHOOK
	 * Este metodo sirvio de base para generar el metodo NotificacionWebHookFacade.generarComprobanteTramiteEnviado
	 * cualquier cambio que sufra respecto a la generacion de comprobantes replicar la logica en dicho metodo
	 * @return
	 * @throws IOException
	 */
	public void generarComprobante() {
		FormatoTramiteFinalizadoPDF formatoPDF = new FormatoTramiteFinalizadoPDF();
		try {
			//Si el ciudadano accede con llave, entonces es posible recuperar los datos del usuario para plantilla
			if (seccionesProyectoBean.getAccesoLlaveDTO().isAutenticacionCiudadano()) {
				usuarioTramite = usuarioDAO.buscarPorId(tramiteActual.getUsuario().getIdUsuarioLlaveCdmx());
			}
			//Si el proyecto tiene configuración de firma y además puede firmar el ciudadano, entonces se recupera los datos
			//de firma del trámite, y se debe utilizar la plantilla personalizada de tipo "4 - Plantilla comprobante registro".
			TramiteFirmaElectronicaDTO datosFirmaTramite = null;
			if (BeanUtils.isNotNull(seccionesProyectoBean.getFirmaDTO()) && seccionesProyectoBean.getFirmaDTO().isFirmaCiudadano()) {
				datosFirmaTramite = formularioDAO.consultarFirmaTramiteCiudadano(tramiteActual);
			}

			//Inicio- Se agrega funcionalidad para recuperar las respuestas del formulario
			List<SeccionesFormularioDTO> lstSeccionesTramiteDTO = generarFormularioApplication.generarListaSecciones();
			Map<String, Object> mapRespuestasTramite = new HashMap<>();
			Map<String, ControlComponentesDTO> mapControlComponentesRespuestas = new HashMap<String, ControlComponentesDTO>();
			List<DetElementosTokenDTO> lstDetElementosTokenDTO = new ArrayList<DetElementosTokenDTO>();
			if(BeanUtils.isNotNull(seccionesProyectoBean.getArchivosRespuestaTokenRegistroDTO())) {
				lstDetElementosTokenDTO = detElementosTokenDAO.buscarPorIdArchivoRespuesta(seccionesProyectoBean.getArchivosRespuestaTokenRegistroDTO().getIdArchivoRespuesta());
				if(lstDetElementosTokenDTO != null) {
					mapControlComponentesRespuestas = generarFormularioApplication.getMapControlComponentes();
					mapRespuestasTramite = cargarDatosRespuesta(tramiteActual, mapControlComponentesRespuestas, lstSeccionesTramiteDTO, lstDetElementosTokenDTO);
				}
			}
			//Fin- se agrega funcionalidad para recuperar las respuestas del formulario

			//se revisan los tipode componente para recuperar descripcion en lugar de id
			for(Map.Entry<String, ControlComponentesDTO> entry : mapControlComponentesRespuestas.entrySet()) {
			    ControlComponentesDTO control = entry.getValue();
			    String key = entry.getKey();
			    
			    if (control.getIdTipoComponente() == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO
			    		|| control.getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_DOMICILIO) {
			    	Object respuesta = mapRespuestasTramite.get(key);
			    					    
				    if(respuesta != null) {
				    	String nuevoValor = cambioRespuesta(control, key, respuesta);
				    	 mapRespuestasTramite.put(key, nuevoValor);
				    }
			    }
			}

			String comprobanteGenerado = formatoPDF.generarDocumento(tramiteActual, seccionesProyectoBean.getSecurityDomainDTO().getUrlSistema(), usuarioTramite, seccionesProyectoBean.getAccesoLlaveDTO().isAutenticacionCiudadano(),
					seccionesProyectoBean.getArchivosRespuestaTokenRegistroDTO(), datosFirmaTramite, mapRespuestasTramite, lstDetElementosTokenDTO, mapControlComponentesRespuestas, lstSeccionesTramiteDTO);

			File pdf = new File(comprobanteGenerado);
			if (pdf.exists()) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Disposition", "attachment;filename=" + pdf.getName());

				OutputStream responseOutputStream = null;
				InputStream fileInputStream = null;
				try {
					responseOutputStream = response.getOutputStream();
					fileInputStream = new FileInputStream(pdf);
					byte[] bytesBuffer = new byte[2048];
					int bytesRead;
					while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
						responseOutputStream.write(bytesBuffer, 0, bytesRead);
					}
					responseOutputStream.flush();
					fileInputStream.close();
					responseOutputStream.close();
					facesContext.responseComplete();
					WebResources.successMessage("msj_comprobante_generado", false);
				} catch (Exception e) {
					LOGGER.error("Problemas al generar el comprobante: ", e);
				} finally {
					if (responseOutputStream != null) {
						try {
							responseOutputStream.close();
						} catch(Exception e) {
							LOGGER.warn("No se pudo cerrar de manera correcta el outputstream de generarComprobante", e);
						}
					}
					if (fileInputStream != null) {
						try {
							fileInputStream.close();
						} catch(Exception e) {
							LOGGER.warn("No se pudo cerrar de manera correcta el inputStream de generarComprobante", e);
						}
					}
				}
			} else {
				WebResources.addValidationMessage("msj_no_comprobante", false);
			}
		}catch (Exception e) {
			LOGGER.error("No se encontró el PDF para la descarga del tramite " + tramiteActual.getFolioSeguimiento(), e);
			WebResources.addValidationMessage("msj_no_comprobante", false);
		}

	}
	
	private String cambioRespuesta(ControlComponentesDTO control, String key, Object respuesta) {
		String texto = respuesta.toString().trim();
		switch (control.getIdTipoComponente()) {
		
		case Constantes.ID_COMPONENTE_CHECKBOX_GRUPO:
			JsonArray array = JsonParser.parseString(texto).getAsJsonArray();
			StringBuilder sb = new StringBuilder();

			for (JsonElement element : array) {
				JsonObject obj = element.getAsJsonObject();
				if (obj.has("descripcionElemento") && !obj.get("descripcionElemento").isJsonNull()) {
					String descripcion = obj.get("descripcionElemento").getAsString();
					if (descripcion != null && !descripcion.trim().isEmpty()) {
						if (sb.length() > 0) {
							sb.append(", ");
						}
						sb.append(descripcion);
					}
				}
			}
			texto = sb.toString();
			break;

		case Constantes.ID_COMPONENTE_DATOS_DOMICILIO:
			String cadenac = "componente_".concat(control.getIdComponente().toString());
			// colonia
			if (key.equals("componente_".concat(control.getIdComponente().toString().concat("_5")))) {
				Integer id = Integer.parseInt(texto);
				CatAsentamientosDTO asentamientoDTO = asentamientoDAO.buscarPorId(id);
				if (BeanUtils.isNotNull(asentamientoDTO) && BeanUtils.isNotNull(asentamientoDTO.getDescripcion())) {
					texto = asentamientoDTO.getDescripcion();
				}
			}
			// municipio
			if (key.equals("componente_".concat(control.getIdComponente().toString().concat("_6")))) {
				Integer id = Integer.parseInt(texto);
				CatMunicipiosDTO municipioDTO = municipioDAO.buscarPorId(id);
				if (BeanUtils.isNotNull(municipioDTO) && BeanUtils.isNotNull(municipioDTO.getDescripcion())) {
					texto = municipioDTO.getDescripcion();
				}
			}
			// estado
			if (key.equals("componente_".concat(control.getIdComponente().toString().concat("_7")))) {
				Integer id = Integer.parseInt(texto);
				CatEstadosDTO estadoDTO = estadoDAO.buscarPorId(id);
				if (BeanUtils.isNotNull(estadoDTO) && BeanUtils.isNotNull(estadoDTO.getDescripcion())) {
					texto = estadoDTO.getDescripcion();
				}
			}
			break;

		default:
			break;
		}

		return texto;
	}

	/**
	 * Metodo que se utiliza para recuperar las respuestas de un formulario, se
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
	 * Método que inicializa finaliza el registro de un trámite
	 *
	 * @return
	 */
	public String finalizarRegistro() {
		String redirect = Constantes.RETURN_SAME_PAGE;
		/**
		 * Si es un usuario logueado con llave, se redirecciona a su bandeja de
		 * ciudadano, de lo contrario a vista de registro de trámite
		 **/
		if (BeanUtils.isNotNull(authenticatorBean.getUsuarioLogueado())) {
			redirect = bandejaTramitesBean.inicializar();
		} else {
			redirect = Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT;
		}


		return redirect;
	}

	/**
	 * Método que inicializa el firmado del trámite al concluir su validación.
	 * @return
	 */
	public boolean registrarFirmaTramite() {
		boolean registroFirmaCorrecto = false;
		FirmaRESTClient firmaClient = new FirmaRESTClient();
		respuestaFirmaTramite = null;
		List<FirmaTramiteDTO> lstTramitesFirma = new ArrayList<FirmaTramiteDTO>();
		FirmaTramiteDTO firmaTramite = new FirmaTramiteDTO();
		firmaTramite.setTramite(tramiteActual);
		firmaTramite.getTramite().setProyectoDTO(proyectoDTO);
		if (BeanUtils.isNotNull(tramiteActual.getUsuario()) &&
				BeanUtils.isNotNull(tramiteActual.getUsuario().getIdUsuarioLlaveCdmx())) {
			firmaTramite.setUsuarioTramite(usuarioDAO.buscarPorId(tramiteActual.getUsuario().getIdUsuarioLlaveCdmx()));
		} else {
			firmaTramite.setUsuarioTramite(null);
		}
		firmaTramite.setUsuarioFirmante(usuarioDAO.buscarPorId(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx()));
		firmaTramite.setFechaFirmado(new Date());
		try {
			lstTramitesFirma.add(firmaTramite);
			respuestaFirmaTramite = firmaClient.firmarRespuesta(lstTramitesFirma, seccionesProyectoBean.getFirmaDTO());
			registroFirmaCorrecto = true;
		} catch (URISyntaxException | JSONException | InterpreteException e) {
			LOGGER.error("Ocurrió un error al registrar el trámite para firmado : ", e);
		}

		return registroFirmaCorrecto;
	}

	/**
	 * Método inicial que construye la ULR con la que se solicitará el registro para el firmado del trámite
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
			WebResources.addErrorMessage("msj_error_redirect_firma_tramite", false);
			LOGGER.error("Ocurrio un error al generar el redirect para firma MX:", e);
		}
	}

	/**
	 * Método que revisa el redireccionamiento de Firma CDMX al concluir con el firmado de una solicitud.
	 */
	public void revisaRespuestaFirmaCdmx() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if (!FacesContext.getCurrentInstance().isPostback() && params != null && !params.isEmpty()) {
			if ((params.get(Constantes.PARAM_FIRMA_TRAMITE_ID) == null
					|| params.get(Constantes.PARAM_FIRMA_TRAMITE_ID).compareTo(Constantes.EMPTY_STRING) == 0)
					|| (params.get(Constantes.PARAM_STATE) == null
					|| params.get(Constantes.PARAM_STATE).compareTo(Constantes.EMPTY_STRING) == 0)) {
				WebResources.addErrorMessage("msj_error_redirect_firmar_tramite", true);
				try {
					facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath()
							+ Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT);
				} catch (IOException e) {
					LOGGER.error("Error al redireccionar al index.", e);
				}
			} else {
//				try {
//					/**Se verifica que los valores de id y state sean los mismos que fueron enviados a firma**/
//					if((params.get(Constantes.PARAM_FIRMA_TRAMITE_ID).compareTo(respuestaFirmaTramite.getIdSolicitud()) == 0)
//							&&(params.get(Constantes.PARAM_STATE).compareTo(respuestaFirmaTramite.getState()) == 0)){
				/**Paso 4, Se consulta el resultado del trámite firmado.**/
				consultaRespuestafirmaTramite();
//					} else {
//						WebResources.addErrorMessage("msj_error_parametros_firma_tramite", true);
//						try {
//							facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath()
//									+ Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT);
//						} catch (IOException e) {
//							LOGGER.error("Error redirect parámetros enviados al firmado : ", e);
//						}
//					}
//				} catch (Exception e) {
//					WebResources.addErrorMessage("msj_error_general_firmacdmx", false);
//					LOGGER.error("Ocurrio un error al firmar el trámite : ", e);
//				}
			}
		}
	}

	/**
	 * Método que consulta mediante un servicio, la respuesta del firmado de un trámite.
	 */
	public void consultaRespuestafirmaTramite() {

		FirmaRESTClient firmaClient = new FirmaRESTClient();
		ResponseServiceConsultaFirmaDTO respuestaConsultaFirma = null;

		try {
			respuestaConsultaFirma = firmaClient.consultaFirmado(respuestaFirmaTramite, seccionesProyectoBean.getFirmaDTO());

			if (BeanUtils.isNull(respuestaConsultaFirma)) {
				mostrarErrorYRedireccionar("msj_error_consulta_firma_tramite", Constantes.RETURN_INDEX_PAGE);
				return;
			}

			/**Se valida si la respuesta contiene más de 1 cadena firmada, el firmado se realizó desde la bandeja de validación
			 * mediante la selección masiva de trámites, entonces se redirecciona a  **/
			if (respuestaConsultaFirma.getLstCadenaDigitales().size() > Constantes.INT_MINIMO_TRAMITE_FIRMADO) {
				redireccionar(guardarFirmaMasivaTramite(respuestaConsultaFirma));
				return;
			}


			if (!esFirmaValida(respuestaConsultaFirma)) {
				return;
			}

			/**Paso 5, Si el firmado se realizó desde la bandeja de validación de trámites, solo se debe registrar
			 * el firmado del trámite, puesto que ya se validó en otro momento.**/
			if (isFirmaBandeja) {
				redireccionar(guardarFirmaTramite(respuestaConsultaFirma));
			} else if (isFirmaTramiteCiudadano) {
				procesarFirmaCiudadano(respuestaConsultaFirma);
			} else {
				/**Si el firmado se realiza en el momento que se revisa el trámite, el firmado se registrará como parte
				 * de la validación de trámite.**/
				redireccionar(guardarRevisionTramite(respuestaConsultaFirma));
			}

		} catch (URISyntaxException | JSONException | InterpreteException e) {
			LOGGER.error("Ocurrió un error al consultar el trámite firmado : ", e);
			mostrarErrorYRedireccionar("msj_error_consulta_firma_tramite", Constantes.RETURN_INDEX_PAGE);
		} catch (IOException e) {
			LOGGER.error("Error en redirección al procesar firma de trámite: ", e);
		}
	}


	/**
	 * Método que valida si la firma del Ciudadano o BackOffice coincide con el nombre del usuario logueado
	 * y en el caso de tener asociado información de Persona Moral se compara con el RFC del certificado.
	 */
	private boolean esFirmaValida(ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) throws IOException {

		if (authenticatorBean.getPersonaMoralSeleccionada() == null) {
			if (!nombreFirmanteCoincide(respuestaConsultaFirma)) {
				if (isFirmaTramiteCiudadano) {
					manejarErrorFirma();
				} else {
					manejarErrorFirmaBackOffice();
				}
				return false;
			}
		} else {
			if (!rfcPersonaMoral.trim().equalsIgnoreCase(respuestaConsultaFirma.getIdentificadorCer().trim())) {
				if (isFirmaTramiteCiudadano) {
					manejarErrorFirma();
				} else {
					manejarErrorFirmaBackOffice();
				}
				return false;
			}
		}

		return true;
	}

	private void redireccionar(String path) throws IOException {
		facesContext.getExternalContext().redirect(
				facesContext.getExternalContext().getRequestContextPath() + path
		);
	}

	private void mostrarErrorYRedireccionar(String mensajeClave, String destino) {
		WebResources.addErrorMessage(mensajeClave, true);
		try {
			redireccionar(destino + Constantes.JSF_REDIRECT);
		} catch (IOException e) {
			LOGGER.error("Error en redirect al consultar resultado de firma de trámite : ", e);
		}
	}


	private boolean nombreFirmanteCoincide(ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) {
		UsuarioDTO usuarioSesion = authenticatorBean.getUsuarioLogueado();
		String nombreCompleto = String.format("%s %s %s",
				usuarioSesion.getNombre(),
				usuarioSesion.getPrimerApellido(),
				Optional.ofNullable(usuarioSesion.getSegundoApellido()).orElse("")
		).trim();

		return nombreCompleto.equalsIgnoreCase(respuestaConsultaFirma.getNombreFirmante().trim());
	}

	private void manejarErrorFirma() {
		tramiteActual.setCatEstatusTramiteDTO(new CatEstatusTramiteDTO(Constantes.ID_ESTATUS_EN_CAPTURA));
		formularioFacade.actualizarEstatusTramite(tramiteActual);
		if(authenticatorBean.isCiudadanoSeleccionado() ||
				authenticatorBean.getPersonaMoralSeleccionada() == null) {
			bandejaTramitesBean.setMsgErrorFirma("msg_valida_nombre_firmante_no_coincide");
		} else {
			bandejaTramitesBean.setMsgErrorFirma("msg_valida_rfc_firmante_no_coincide");
		}

		redirectToBandejaTramites();
	}

	private void manejarErrorFirmaBackOffice() {

		if(authenticatorBean.isCiudadanoSeleccionado() ||
				authenticatorBean.getPersonaMoralSeleccionada() == null) {
			bandejaValidacionTramitesBean.setMsgErrorFirma("msg_valida_nombre_firmante_no_coincide");
		} else {
			bandejaValidacionTramitesBean.setMsgErrorFirma("msg_valida_rfc_firmante_no_coincide");
		}

		try {
			facesContext.getExternalContext()
					.redirect(facesContext.getExternalContext().getRequestContextPath()
							+ bandejaValidacionTramitesBean.inicializar());
		} catch (IOException e) {
			LOGGER.error("Ocurrio un error al redireccionar pagina", e);
		}

	}

	private void procesarFirmaCiudadano(ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) throws IOException {
		respuestaConsultaFirma.setFirmaCiudadano(true);
		String urlRedireccion = facesContext.getExternalContext().getRequestContextPath() +
				guardarFirmaTramiteCiudadano(respuestaConsultaFirma);
		facesContext.getExternalContext().redirect(urlRedireccion);
	}

	/**
	 * Método que revisa si el proyecto está configurado para permitir realizar el firmado de revisiones
	 * de trámite, si el proyecto tiene habilitada la funcionalidad de Firmado, se pregunta al ciudadano
	 * si necesita realizar el firmado del archivo de respuesta configurado en el proyecto.
	 *
	 * @return
	 */
	public String validarFirmadoTramite() {
		String redirect = Constantes.RETURN_SAME_PAGE;
		/** Se evalua si la confuración del proyecto y usuario actual puede realizar el firmado **/
		if (validaPermisosFirmado()) {
			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('mdlFirmaTramite').show();");
		} else {
			redirect = guardarRevisionTramite(null);
		}

		return redirect;
	}

	/**
	 * Método auxiliar que evalua si la configuración del proyecto permite realizar firma de trámites, además
	 * evalua si el rol de Supervisor u Operador se encuentra habilitado para realizar firmado de trámites.
	 *
	 * @return
	 */
	private boolean validaPermisosFirmado() {
		boolean permiteFirmado = false;
		if(idEstatusValidacionTramite != Constantes.ID_ESTATUS_CORRECIONES) {
			if(BeanUtils.isNotNull(seccionesProyectoBean.getArchivosRespuestaTokenAceptacionDTO()) ||
					BeanUtils.isNotNull(seccionesProyectoBean.getArchivosRespuestaTokenRechazoDTO())) {
				if(seccionesProyectoBean.isHabilitarFirmadoTramites()) {
					if(authenticatorBean.isRolSupervisor() &&
							((BeanUtils.isNotNull(seccionesProyectoBean.getArchivosRespuestaTokenAceptacionDTO()) && seccionesProyectoBean.getArchivosRespuestaTokenAceptacionDTO().isFirmaSupervisor()) ||
									((BeanUtils.isNotNull(seccionesProyectoBean.getArchivosRespuestaTokenRechazoDTO()) && seccionesProyectoBean.getArchivosRespuestaTokenRechazoDTO().isFirmaSupervisor())))) {
						permiteFirmado = true;
					}

					if(authenticatorBean.isRolOperador() &&
							((BeanUtils.isNotNull(seccionesProyectoBean.getArchivosRespuestaTokenAceptacionDTO()) && seccionesProyectoBean.getArchivosRespuestaTokenAceptacionDTO().isFirmaOperador()) ||
									((BeanUtils.isNotNull(seccionesProyectoBean.getArchivosRespuestaTokenRechazoDTO()) && seccionesProyectoBean.getArchivosRespuestaTokenRechazoDTO().isFirmaOperador())))) {
						permiteFirmado = true;
					}
				}
			}
		}
		return permiteFirmado;
	}

	/**
	 * Método que inicia el proceso de firmado del trámite actual.
	 * @return
	 */
	public String iniciarFirmaTramite() {
		return iniciarFirmaTramiteCommon(false, false, false);
	}

	/**
	 * Método auxiliar que inicia el proceso de firmado del trámite actual.
	 * @return
	 */
	public String iniciarFirmaTramiteBandeja() {
		return iniciarFirmaTramiteCommon(true, false, true);
	}

	/**
	 * Método que realiza la consulta los datos de la firma que realiza el ciudadano como parte de su captura
	 */
	public void consultaDatosFirmaCiudadano() {

		if (tramiteActual == null) {
			datosFirmaCiudadanoDTO = null;
			return;
		}

		try {
			datosFirmaCiudadanoDTO = formularioFacade.consultarFirmaTramiteCiudadano(tramiteActual);
		} catch (Exception e) {
			LOGGER.error("Error consultando firma ciudadano para trámite: " + tramiteActual.getIdTramite(), e);
			datosFirmaCiudadanoDTO = null;
		}
	}

	public String iniciarFirmaTramiteCiudadano() {
		return iniciarFirmaTramiteCommon(false, true, false);
	}

	private String iniciarFirmaTramiteCommon(boolean isBandeja, boolean isCiudadano, boolean necesitaProyectoDTO) {
		String redirect = Constantes.RETURN_SAME_PAGE;
		isFirmaBandeja = isBandeja;
		isFirmaTramiteCiudadano = isCiudadano;

		if (necesitaProyectoDTO) {
			this.proyectoDTO = generarFormularioApplication.getProyectoDTO();
		}

		if (registrarFirmaTramite()) {
			redirectUrlFirmaCDMX();
		} else {
			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('mdlFirmaMx').show();");
		}

		return redirect;
	}

	/**
	 * Método que registra la revisión del trámite por un funcionario.
	 * 26/06/2026 WEBHOOK
	 * Se anexa logica para comucion hacia webhook
	 * @param respuestaConsultaFirma
	 * @return
	 */
	public String guardarRevisionTramite(ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) {
		Predicate<ResponseServiceConsultaFirmaDTO> prReponseIsNotNull = BeanUtils::isNotNull;
		Predicate<SeccionesProyectoBean> prHabilitaFirmado = SeccionesProyectoBean::isHabilitarFirmadoTramites;

		String redirect = Constantes.RETURN_SAME_PAGE;
		boolean revisionExitosa = false;
		try {
			/**
			 * Se revisa si la última sección solo contiene componentes informativos, si es
			 * el caso no se inserta registro en BD.
			 **/
			validaComponentesInformativosSeccion();

			tramiteActual.setUsuarioRevisor(new UsuarioDTO(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx()));
			revisionExitosa = guardarRevisionTramiteVal(tramiteActual, respuestaConsultaFirma);

			//Envio de notificacion de webHook
			//Si el DTO de firmado no es nulo ya paso por firmado o el firmado de tramites no esta habilitado se realiza la notificacion
			if((prHabilitaFirmado.test(seccionesProyectoBean) && prReponseIsNotNull.test(respuestaConsultaFirma))
					|| (prHabilitaFirmado.negate().test(seccionesProyectoBean) && prReponseIsNotNull.negate().test(respuestaConsultaFirma))){
				iniciaProcesoNotificacionWebHook();
			}

			if (revisionExitosa)
				redirect = bandejaValidacionTramitesBean.inicializar();

		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al realizar la revisión del trámite: ", e);
		}

		return redirect;
	}
	
	/**
	 * Metodo auxiliar de validacion para guardar revision
	 * @param tramiteActual
	 * @param respuestaConsultaFirma
	 * @return
	 */
	public boolean guardarRevisionTramiteVal(TramiteDTO tramiteActual, ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) {
		boolean revisionExitosa = false;
		IntPredicate prEstatus = p->p==idEstatusValidacionTramite;
		Predicate<String> prRespuestaFolio = p-> BeanUtils.isNotNull(p) && p.length() > 500;
		if (prEstatus.test(Constantes.ID_ESTATUS_APROBADO)) {

			tramiteActual.setRutaDocumentoPrevencion(null);
			tramiteActual.setFechaRevision(new Date());
			tramiteActual.getCatEstatusTramiteDTO().setIdEstatusTramite(idEstatusValidacionTramite);
			if(prRespuestaFolio.test(tramiteRevision.getRespuestaFolioConclusion())) {
				WebResources.addErrorMessage("msj_modal_longitud_conclusion", false);
				return false;
			}
			tramiteActual.setRespuestaFolioConclusion(tramiteRevision.getRespuestaFolioConclusion());
			try {
				formularioFacade.notificarRevisionTramite(tramiteActual, proyectoDTO.getNombreProyecto(), seccionActualDTO,
						Constantes.NOMBRE_BASE_TABLAS.concat(seccionActualDTO.getIdSeccionFormulario().toString()), isSeccionInformativa, respuestaConsultaFirma);
				revisionExitosa = true;
			} catch (Exception e) {
				LOGGER.error("Ocurrió un error al aprobar el trámite " + tramiteActual.getFolioSeguimiento(), e);
				WebResources.addErrorMessage("msj_error_validacion_tramite", false);
			}
		} else if (prEstatus.test(Constantes.ID_ESTATUS_CORRECIONES)) {
			tramiteActual.setRespuestaFolioPrevencion(tramiteRevision.getRespuestaFolioPrevencion());
			tramiteActual.setRutaDocumentoPrevencion(tramiteRevision.getRutaDocumentoPrevencion());
			tramiteActual.setFechaRevision(new Date());
			tramiteActual.getCatEstatusTramiteDTO().setIdEstatusTramite(idEstatusValidacionTramite);
			try {
				formularioFacade.notificarRevisionTramite(tramiteActual, proyectoDTO.getNombreProyecto(), seccionActualDTO,
						Constantes.NOMBRE_BASE_TABLAS.concat(seccionActualDTO.getIdSeccionFormulario().toString()), isSeccionInformativa, null);
				PrimeFaces.current().executeScript("PF('mdlPrevencion').hide();");
				revisionExitosa = true;
			} catch (Exception e) {
				LOGGER.error("Ocurrió un error al actualizar a correcciones el trámite " + tramiteActual.getFolioSeguimiento(), e);
				WebResources.addErrorMessage("msj_error_validacion_tramite", false);
			}
		} else if (prEstatus.test(Constantes.ID_ESTATUS_RECHAZADO)) {

			tramiteActual.setRespuestaFolioConclusion(tramiteRevision.getRespuestaFolioConclusion());
			tramiteActual.setRutaDocumentoPrevencion(null);
			tramiteActual.setFechaRevision(new Date());
			tramiteActual.getCatEstatusTramiteDTO().setIdEstatusTramite(idEstatusValidacionTramite);
			try {
				formularioFacade.notificarRevisionTramite(tramiteActual, proyectoDTO.getNombreProyecto(), seccionActualDTO,
						Constantes.NOMBRE_BASE_TABLAS.concat(seccionActualDTO.getIdSeccionFormulario().toString()), isSeccionInformativa, respuestaConsultaFirma);
				PrimeFaces.current().executeScript("PF('mdlPrevencion').hide();");
				revisionExitosa = true;
			} catch (Exception e) {
				LOGGER.error("Ocurrió un error al rechazar el trámite " + tramiteActual.getFolioSeguimiento(), e);
				WebResources.addErrorMessage("msj_error_validacion_tramite", false);
			}
		} else if (prEstatus.test(Constantes.ID_ESTATUS_REVISADO)) {
			tramiteActual.setRutaDocumentoPrevencion(null);
			tramiteActual.setFechaRevision(new Date());
			tramiteActual.getCatEstatusTramiteDTO().setIdEstatusTramite(idEstatusValidacionTramite);
			try {
				formularioFacade.actualizacionTramiteRevisadoOperador(tramiteActual, seccionActualDTO, isSeccionInformativa);
				revisionExitosa = true;
			} catch (Exception e) {
				LOGGER.error("Ocurrió un error con la revisión del operador " + tramiteActual.getFolioSeguimiento(), e);
				WebResources.addErrorMessage("msj_error_validacion_tramite", false);
			}
		}
		return revisionExitosa;
	}


	public String guardarResolucionTramite() {

		// Validación para trámites rechazados
		if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO) {
			tipoResolucionTramite = false;
		}

		if (!validaCargaDocumentoResolucionObligatoria()) {
			return null;
		}

		if (idEstatusValidacionTramite == Constantes.ID_ESTATUS_APROBADO ||
				idEstatusValidacionTramite == Constantes.ID_ESTATUS_RECHAZADO) {

			actualizaEstatusResolucionTramite();

			String nombreTabla = Constantes.NOMBRE_BASE_TABLAS.concat(seccionActualDTO.getIdSeccionFormulario().toString());
			ResponseServiceConsultaFirmaDTO respuestaConsultaFirma = null;

			formularioFacade.notificarResolucionTramite(
					tramiteActual,
					proyectoDTO.getNombreProyecto(),
					seccionActualDTO,
					nombreTabla,
					isSeccionInformativa,
					respuestaConsultaFirma
			);
			
			try {				
				//Envio de notificacion de webHook en la resolucion queda comentado webhook
				iniciaProcesoNotificacionWebHook();
			} catch (Exception e) {
				LOGGER.error("Ocurrió un error al notificar cambio del trámite mediante webhook "
						+ tramiteActual.getFolioSeguimiento(), e);
				WebResources.addErrorMessage("msj_error_notificacion_webhook", true);
			}

			archivoResolucionTramiteCargado = false;
			return bandejaValidacionTramitesBean.inicializar();
		}

		return Constantes.RETURN_SAME_PAGE;
	}


	public void actualizaEstatusResolucionTramite() {

		String rutaResolucion;

		if (tipoResolucionTramite) {
			idEstatusValidacionTramite = Constantes.ID_ESTATUS_CONCLUSION_POSITIVA;
			rutaResolucion = tramiteRevision.getRutaDocumentoResolucionPositiva();
			tramiteActual.setRutaDocumentoResolucionPositiva(rutaResolucion);
		} else {
			idEstatusValidacionTramite = Constantes.ID_ESTATUS_CONCLUSION_NEGATIVA;
			rutaResolucion = tramiteRevision.getRutaDocumentoResolucionNegativa();
			tramiteActual.setRutaDocumentoResolucionNegativa(rutaResolucion);
		}

		tramiteActual.setFechaRevision(new Date());
		tramiteActual.getCatEstatusTramiteDTO().setIdEstatusTramite(idEstatusValidacionTramite);
		tramiteActual.setUsuarioRevisor(new UsuarioDTO(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx()));

	}


	public boolean validaCargaDocumentoResolucionObligatoria() {

		boolean esResolucionPositiva = tipoResolucionTramite;
		boolean archivoNoCargado = !archivoResolucionTramiteCargado;

		boolean positivaObligatoria = seccionesProyectoBean.getGestionUsuarioDTO().isResolucionPositivaObligatoria();
		boolean negativaObligatoria = seccionesProyectoBean.getGestionUsuarioDTO().isResolucionNegativaObligatoria();

		if (archivoNoCargado) {

			if (esResolucionPositiva && positivaObligatoria) {
				enviarMensajeVista("msg_resolucion_positiva_obligatoria", "frmModalResolucionTramite", FacesMessage.SEVERITY_WARN);
				return false;
			}

			if (!esResolucionPositiva && negativaObligatoria) {
				enviarMensajeVista("msg_resolucion_negativa_obligatoria", "frmModalResolucionTramite", FacesMessage.SEVERITY_WARN);
				return false;
			}
		}

		return true;
	}


	/**
	 * Método que registra la firma de un trámite desde la bandeja de trámites del funcionario.
	 *
	 * @param respuestaConsultaFirma
	 * @return
	 */
	private String guardarFirmaTramite(ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) {
		//Envio de notificacion de webHook
		iniciaProcesoNotificacionWebHook();
		return guardarFirmaCommon(
				respuestaConsultaFirma,
				resp -> formularioFacade.registrarFirmaTramite(tramiteActual, resp),
				bandejaValidacionTramitesBean::inicializar
		);
	}
	/**
	 * 26/06/2026
	 * Cuando firma el ciudadano procedemos a notificar
	 * cuando regresa tras la firma del ciudadano
	 */
	private String guardarFirmaTramiteCiudadano(ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) {
		//para aquello de los estatus personalizados
		CatEstatusTramiteDTO catEstatusTramite = catEstatusTramiteDAO.buscarPorIdEstatus(Constantes.ID_ESTATUS_ENVIADO);
		
		if(this.proyectoDTO.isAviso()) {
			catEstatusTramite = catEstatusTramiteDAO.buscarPorIdEstatus(Constantes.ID_ESTATUS_APROBADO);
		}
		tramiteActual.setCatEstatusTramiteDTO(catEstatusTramite);
		tramiteActual.setFechaCreacion(new Date());

		try {

			TramiteDTO	tramiteTemp = formularioDAO.consultarFolioTramite(tramiteActual);
			tramiteActual.setFolioSeguimiento(tramiteTemp.getFolioSeguimiento());
			tramiteActual.setUuid(tramiteTemp.getUuid());
			tramiteActual.setProyectoDTO(proyectoDTO);
			/**Se tiene que validar si el trámite tiene asignado un usuario, recordar que existen trámites sin acceso mediante llave**/
			if(BeanUtils.isNotNull(tramiteActual.getUsuario())) {
				tramiteActual.setUsuario(usuarioDAO.buscarPorId(tramiteActual.getUsuario().getIdUsuarioLlaveCdmx()));
			} else {
				tramiteActual.setUsuario(null);
			}
			formularioFacade.notificarRegistroTramite(tramiteActual);
			
			//Envio de notificacion de webHook
			iniciaProcesoNotificacionWebHook();

		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al notificar el registro del trámite firmado por el ciudadano "
					+ (tramiteActual != null ? tramiteActual.getFolioSeguimiento() : ""), e);
		}


		formularioFacade.registrarFirmaTramite(tramiteActual, respuestaConsultaFirma);

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tramiteCompleto", true);

		return  Constantes.RETURN_FIN_FORMULARIO_PAGE + Constantes.JSF_REDIRECT;

	}

	/**
	 * Método que registra la respuesta del firmado masivo de trámites
	 *
	 * @param respuestaConsultaFirma
	 * @return
	 */
	private String guardarFirmaMasivaTramite(ResponseServiceConsultaFirmaDTO respuestaConsultaFirma) {
		//Envio de notificacion de webHook
		iniciaProcesoNotificacionWebHook();
		return guardarFirmaCommon(
				respuestaConsultaFirma,
				formularioFacade::registrarFirmadoTramites,
				bandejaValidacionTramitesBean::inicializar
		);
	}

	private String guardarFirmaCommon(ResponseServiceConsultaFirmaDTO respuestaConsultaFirma,
									  Consumer<ResponseServiceConsultaFirmaDTO> accionFacade,
									  Supplier<String> inicializadorBean) {
		String redirect = Constantes.RETURN_SAME_PAGE;
		accionFacade.accept(respuestaConsultaFirma);
		redirect = inicializadorBean.get();
		return redirect;
	}

	/**
	 * Método auxiliar para setear el estatus del trámite al momento de realizar la
	 * revisión del trámite por un funcionario.
	 *
	 * @param idEstatusTramite
	 */
	public void listenerAsignarEstatusTramite(int idEstatusTramite) {
		idEstatusValidacionTramite = idEstatusTramite;
		if (idEstatusTramite == Constantes.ID_ESTATUS_CORRECIONES
				|| idEstatusTramite == Constantes.ID_ESTATUS_RECHAZADO) {
			tramiteRevision = new TramiteDTO();
			PrimeFaces current = PrimeFaces.current();
			current.executeScript("PF('mdlPrevencion').show();");
		}
	}


	public void listenerAsignarEstatusResolucionTramite(int idEstatusTramite) {

		idEstatusValidacionTramite = idEstatusTramite;

		// Si el trámite está rechazado, se forza la resolución negativa
		if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO) {
			tipoResolucionTramite = false; // Negativa
		} else {
			tipoResolucionTramite = true; // Positiva por defecto
		}

		actualizarLeyendaResolucion();
		tramiteRevision = new TramiteDTO();
		PrimeFaces.current().executeScript("PF('mdlResolucionTramite').show();");
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
				tramiteRevision.setRutaDocumentoPrevencion(copiarDocumento(FilenameUtils.getExtension(event.getFile().getFileName()),
						event.getFile().getInputStream(), Environment.getPathClienteDocumentos()));
			}
		} catch (IOException e) {
			LOGGER.error("Ocurrió un error al intentar copiar el docuento en el filesystem: ", e);
		}
	}

	/**
	 * Método auxiliar que eliminá un archivo cargado como prevención
	 *
	 * @param rutaArchivo
	 */
	public void eliminarArchivoCargado(String rutaArchivo) {
		File documento = new File(rutaArchivo);
		try {
			if (!documento.delete()) {
				LOGGER.warn("El documento no se pudo eliminar");
			}
			tramiteRevision.setRutaDocumentoPrevencion(null);
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al querer eliminar el documento: ", e);
		}
	}

	public void subirArchivoResolucionTramite(FileUploadEvent event) {


		if (archivoResolucionTramiteCargado) {
			enviarMensajeVista("msj_carga_un_archivo_resolucion_tramite", "frmModalResolucionTramite", FacesMessage.SEVERITY_WARN);
			return;
		}

		try {

			if (event.getFile() != null) {

				String rutaBase = Environment.getPathClienteDocumentos();
				String rutaResoluciones = rutaBase  + "resoluciones" + "/";

				// Crear el directorio si no existe
				File directorioResoluciones = new File(rutaResoluciones);
				if (!directorioResoluciones.exists()) {
					boolean directorioCreado = directorioResoluciones.mkdirs();
					if (!directorioCreado) {
						LOGGER.error("No se pudo crear el directorio para resoluciones: " + rutaResoluciones);
						enviarMensajeVista("msj_error_crear_directorio", "frmModalResolucionTramite", FacesMessage.SEVERITY_ERROR);
						return;
					}
					LOGGER.info("Directorio de resoluciones creado: " + rutaResoluciones);
				}

				if(tipoResolucionTramite) {
					tramiteRevision.setRutaDocumentoResolucionPositiva(copiarDocumento(
							FilenameUtils.getExtension(event.getFile().getFileName()),
							event.getFile().getInputStream(),
							rutaResoluciones
					));

				} else {
					tramiteRevision.setRutaDocumentoResolucionNegativa(copiarDocumento(
							FilenameUtils.getExtension(event.getFile().getFileName()),
							event.getFile().getInputStream(),
							rutaResoluciones
					));
				}

				archivoResolucionTramiteCargado = true;


			}

		} catch (IOException e) {
			LOGGER.error("Ocurrió un error al intentar copiar el documento de resolución del trámite: ", e);
			enviarMensajeVista("msj_error_carga_archivo", "frmModalResolucionTramite", FacesMessage.SEVERITY_ERROR);
		}
	}


	public void eliminarArchivoResolucionTramite() {

		String rutaResolucionPositiva = tramiteRevision.getRutaDocumentoResolucionPositiva();
		String rutaResolucionNegativa = tramiteRevision.getRutaDocumentoResolucionNegativa();

		if (rutaResolucionPositiva != null) {
			eliminarArchivoCargado(rutaResolucionPositiva);
			tramiteRevision.setRutaDocumentoResolucionPositiva(null);
		} else if (rutaResolucionNegativa != null) {
			eliminarArchivoCargado(rutaResolucionNegativa);
			tramiteRevision.setRutaDocumentoResolucionNegativa(null);
		}

		archivoResolucionTramiteCargado = false;
	}


	public void actualizarLeyendaResolucion() {
		leyendaTipoDeResolucionTramite =
				tipoResolucionTramite ?
						"Resolución Positiva" :
						"Resolución Negativa";
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
					LOGGER.warn("No se pudo cerrar de manera correcta el outputstream de copiarDocumento - RegistrarFormularioBean", e);
				}
			}
		}
		return destinoDoc;
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
	 * Método auxiliar para limpiar los valores ingresados en la modal de prevención/rechazo
	 */
	public void cancelarModal() {
		if (tramiteActual.getRutaDocumentoPrevencion() != null
				&& !tramiteActual.getRutaDocumentoPrevencion().isEmpty()) {
			eliminarArchivoCargado(tramiteActual.getRutaDocumentoPrevencion());
		}
		if (tramiteActual.getRespuestaFolioPrevencion() != null && !tramiteActual.getRespuestaFolioPrevencion().isEmpty()) {
			tramiteActual.setRespuestaFolioPrevencion(Constantes.EMPTY_STRING);
		}
		if (tramiteActual.getRespuestaFolioConclusion() != null && !tramiteActual.getRespuestaFolioConclusion().isEmpty()) {
			tramiteActual.setRespuestaFolioConclusion(Constantes.EMPTY_STRING);
		}
	}

	/**
	 * Método auxiliar que habilita la sección de observaciones para poder agregar observaciones a la sección Actual.
	 *
	 * @param habilitarObservaciones
	 */
	public void listenerHabilitaObservaciones(Boolean habilitarObservaciones) {
		seccionActualDTO.setContieneObservaciones(habilitarObservaciones);

		// Se limpia campo de observaciones si se marca la sección que no tiene
		// observaciones
		if (!habilitarObservaciones) {
			seccionActualDTO.setObservaciones(null);
		}

		/** Se actualizan bandera de Observaciones en listado de secciones **/
		for (int i = 0; i < this.lstSeccionesDTO.size() - 1; i++) {
			if (this.lstSeccionesDTO.get(i).getIdSeccionFormulario() == seccionActualDTO.getIdSeccionFormulario()) {
				this.lstSeccionesDTO.get(i).setContieneObservaciones(seccionActualDTO.isContieneObservaciones());
				this.lstSeccionesDTO.get(i).setObservaciones(seccionActualDTO.getObservaciones());
				break;
			}
		}

		// Se valida si el trámite es enviado
		if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO) {
			// Se valida si el rol actual está revisando el trámite
			if (this.isUsuarioBack) {
				// Se verifica si es la última sección, para activar o inactivar los botones de
				// Rechazar o Concluir solo para la última sección
				this.isObservacionesTramite = false;
				// Se validan todas las secciones guardadas
				for (SeccionesFormularioDTO seccionTmp : this.lstSeccionesDTO) {
					if (BeanUtils.isNotNull(seccionTmp.isContieneObservaciones())
							&& seccionTmp.isContieneObservaciones()) {
						this.isObservacionesTramite = true;
						break;
					}
				}
			}
		}
	}

	/**
	 * Método auxilar que verifica si la sección actual del formulario es la última
	 * sección del listado.
	 */
	private void verificaUltimaSeccionFormulario() {
		if (BeanUtils.isNotNull(tramiteActual)
				&& tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO) {
			isUltimaSeccion = false;
			if (BeanUtils.isNotNull(lstSeccionesDTO) && lstSeccionesDTO.size() > Constantes.INT_VALOR_CERO) {
				/**
				 * Se obiente última sección del listado para comparlo con la seccción actual,
				 * para identificar si es la última sección del formulario.
				 */
				SeccionesFormularioDTO seccionTmp = lstSeccionesDTO.get(lstSeccionesDTO.size() - 1);
				if (seccionTmp.getIdSeccionFormulario() == seccionActualDTO.getIdSeccionFormulario()) {
					isUltimaSeccion = true;
				}
			}

			/**
			 * Si el rol actual es un Operador o Supervisor y el trámite está en estatus
			 * ENVIADO, se revisa si existen observaciones en las secciones del trámite
			 */
			if (isUltimaSeccion) {
				if (this.isUsuarioBack) {
					for (SeccionesFormularioDTO seccionTmp : this.lstSeccionesDTO) {
						if (BeanUtils.isNotNull(seccionTmp.isContieneObservaciones())&& seccionTmp.isContieneObservaciones()) {
							this.isObservacionesTramite = true;
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Método auxiliar que revisa si es un rolñ de tipo back y si es la última sección para consultar información de firma
	 * del trámite como parte del su registro (Firma del ciudadano)
	 */
	private void validaConsultaFirmaCiudadano() {
		if(BeanUtils.isNotNull(seccionesProyectoBean.getFirmaDTO()) &&
				BeanUtils.isNotNull(seccionesProyectoBean.getFirmaDTO().isFirmaCiudadano())) {
			if (rolPuedeVerFirmaCiudadano() && isUltimaSeccionFormulario()) {
				//Inicializamos objeto hasta cumplir la condicion de consulta
				datosFirmaCiudadanoDTO = new TramiteFirmaElectronicaDTO();
				consultaDatosFirmaCiudadano();
			}
		}
	}

	/**
	 * Método auxiliar que verifica si el rol actual puede ver los datos de la firma realizada como parte de la captura de un ciudadano.
	 * @return
	 */
	private boolean rolPuedeVerFirmaCiudadano() {
		return (BeanUtils.isNotNull(tramiteActual)
				&& (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO ||
				tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_CORREGIDO ||
				tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO ||
				tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO  ||
				tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_REVISADO));
	}

	/**
	 * Método auxiliar que revisa si la sección que se mostrará en el formulario es la última sección.
	 * @return
	 */
	private boolean isUltimaSeccionFormulario() {
		isUltimaSeccion = false;
		if (BeanUtils.isNotNull(lstSeccionesDTO) && lstSeccionesDTO.size() > Constantes.INT_VALOR_CERO) {
			/**
			 * Se obiente última sección del listado para comparlo con la seccción actual,
			 * para identificar si es la última sección del formulario.
			 */
			SeccionesFormularioDTO seccionTmp = lstSeccionesDTO.get(lstSeccionesDTO.size() - 1);
			if (seccionTmp.getIdSeccionFormulario().intValue() == seccionActualDTO.getIdSeccionFormulario().intValue()) {
				isUltimaSeccion = true;
			}
		}
		return isUltimaSeccion;
	}

	/**
	 * Método auxiliar que se utiliza por la opción Ver documento que se registra cuando el trámite se envía a correcciones.
	 *
	 * @param rutaArchivo
	 */
	public void verDocumento(String rutaArchivo) {
		InputStream is = null;
		ServletOutputStream soutput = null;
		File documento = new File(rutaArchivo);
		if (documento != null) {
			try {
				byte[] bytes = new byte[Constantes.TAMAÑO_BUFFER];
				int read = Constantes.INT_VALOR_CERO;
				is = new FileInputStream(documento);
				FacesContext fctx = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) fctx.getExternalContext().getResponse();

				response.setContentType(asignarContentTypeDocumento(documento.getName()));
				response.setHeader("Content-Disposition", "attachment;filename=\"" + documento.getName() + "\"");
				soutput = response.getOutputStream();

				while ((read = is.read(bytes)) != -1) {
					soutput.write(bytes, Constantes.INT_VALOR_CERO, read);
				}

				soutput.flush();
				soutput.close();
				fctx.responseComplete();
			} catch (IOException e) {
				LOGGER.error("Ocurrió un error al realizar la función de Ver documento: ", e);
			} finally {
				if (soutput != null) {
					try {
						soutput.close();
					} catch (Exception e) {
						LOGGER.error("Ocurrió un problema al cerrar el recurso soutput", e);
					}
				}
				if (is != null) {
					try {
						is.close();
					} catch (Exception e) {
						LOGGER.error("Ocurrió un problema al cerrar el recurso is", e);
					}
				}
			}
		}
	}

	/**
	 * Método privado auxiliar para poder asignar el content type del documento
	 * cargado
	 *
	 * @param fileName
	 * @return
	 */
	private String asignarContentTypeDocumento(String fileName) {
		String contentType = Constantes.EMPTY_STRING;
		if (fileName.contains(Constantes.EXTENSION_JPG)) {
			contentType = Constantes.CONTENTTYPE_JPG;
		} else if (fileName.contains(Constantes.EXTENSION_JPEG)) {
			contentType = Constantes.CONTENTTYPE_JPEG;
		} else if (fileName.contains(Constantes.EXTENSION_PNG)) {
			contentType = Constantes.CONTENTTYPE_PNG;
		} else if (fileName.contains(Constantes.EXTENSION_PDF)) {
			contentType = Constantes.CONTENTTYPE_PDF;
		}
		return contentType;
	}

	/**
	 * Método auxiliar que permite verificar si alguna sección tiene 
	 * observaciones marcadas. Con el fin de controlar le habilitar/deshabilitar
	 * del botón Realizar prevención
	 * @return
	 */
	public boolean existenSeccionesConObservaciones() {
		boolean existeSeccionConObservaciones = false;
		if(authenticatorBean.isRolSupervisor() || authenticatorBean.isRolOperador()) {
			for (SeccionesFormularioDTO seccionesFormularioDTO : lstSeccionesDTO) {
				if (seccionesFormularioDTO.isContieneObservaciones()) {
					existeSeccionConObservaciones = true;
					break;
				}
			}
		}
		return existeSeccionConObservaciones;
	}


	/**
	 * Método que se utiliza para saber si se puede ver el captcha 
	 * @return
	 */
	public boolean visualizarCaptha() {
		return Environment.getAppProfile().compareTo("local") == Constantes.INT_VALOR_CERO;
	}

	/**
	 * Método auxiliar que dependiendo la bandeja ingresada, valida la bandeja que se redirecciona al
	 * presionar el botón Cancelar
	 *
	 * @return
	 */
	public String cancelar() {
		String redirect = Constantes.RETURN_SAME_PAGE;
		
		limpiarEstadoTablasSeccionActual();
		
		if(isValidacionTramite) {
			redirect = bandejaValidacionTramitesBean.inicializar();
		} else {
			redirect = bandejaTramitesBean.inicializar();
		}
		
		return redirect;
	}

	public void cerrarModalTipoPersona() {
		mostrarModalTipoPersona = false;
	}

	public void redirectToBandejaTramites() {
		try {
			facesContext.getExternalContext()
					.redirect(facesContext.getExternalContext().getRequestContextPath()
							+ bandejaTramitesBean.inicializar());
		} catch (IOException e) {
			LOGGER.error("Ocurrio un error al redireccionar pagina", e);
		}
	}

	public String getBotonTramiteCiudadano() {
		int idEstatus = tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite();
		String texto ="";

		if (esUltimaSeccion()) {
			if ((idEstatus == Constantes.ID_ESTATUS_EN_CAPTURA ||
					idEstatus == Constantes.ID_ESTATUS_PENDIENTE_PAGO
					|| idEstatus == Constantes.ID_ESTATUS_CORRECIONES)) {
				texto = "Finalizar";
			}
			if (firmaCiudadano && ( isEdicionTramite() || isNuevoTramite() )) {
				texto = "Firmar";
			}
		} else {
			texto="Siguiente";
		}

		return texto;
	}

	public boolean esUltimaSeccion() {
	    if (BeanUtils.isNull(lstSeccionesDTO)
	            || lstSeccionesDTO.isEmpty()
	            || BeanUtils.isNull(seccionActualDTO)) {
	        return false;
	    }
	    SeccionesFormularioDTO ultimaSeccion = lstSeccionesDTO.get(lstSeccionesDTO.size() - 1);
	    if (BeanUtils.isNull(ultimaSeccion)) {
	        return false;
	    }

	    Long idUltimaSeccion = ultimaSeccion.getIdSeccionFormulario();
	    Long idSeccionActual = seccionActualDTO.getIdSeccionFormulario();

	    return idSeccionActual != null
	            && idUltimaSeccion != null
	            && Objects.equals(idUltimaSeccion, idSeccionActual);
	}

	public boolean activarBotonLineaCaptura() {
		this.generaLineaCapturaBean.inicializarDatosParaLineaDeCaptura(this.tramiteActual, 
				this.proyectoDTO, esUltimaSeccion());
		return permitirGeneracionDeLineaDeCaptura()
				&& this.generaLineaCapturaBean.isMostrarBotonGeneraLineaCaptura();
	}
	
	public boolean mostrarBotonSiguienteOFinalizar() {
		if(this.permitirGeneracionDeLineaDeCaptura()) {
			return false;
		}
		int idEstatus = tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite();
		return !(esUltimaSeccion() && idEstatus != Constantes.ID_ESTATUS_EN_CAPTURA
				&& idEstatus != Constantes.ID_ESTATUS_CORRECIONES);
	}

	public boolean permitirGeneracionDeLineaDeCaptura() {
		if(BeanUtils.isNull(authenticatorBean.getUsuarioLogueado())) {
			return false;
		}
		long idUsuarioLogeuado = authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx();
		long idUsuarioAsignadoTramite = Objects.nonNull(tramiteActual.getUsuario()) ? tramiteActual.getUsuario().getIdUsuarioLlaveCdmx() : -1;
		boolean esMismoUsuario = idUsuarioLogeuado == idUsuarioAsignadoTramite;
		return esMismoUsuario && this.generaLineaCapturaBean
				.permitirGeneracionLineaDeCaptura(tramiteActual, proyectoDTO, esUltimaSeccion());
	}

	public boolean isRenderizarResolucionTramite() {

		DetGestionUsuarioDTO gestionUsuarioDTO = seccionesProyectoBean.getGestionUsuarioDTO();

		if (gestionUsuarioDTO == null) {
			return false;
		}

		if (!gestionUsuarioDTO.isHabilitaResolucion()) {
			return false;
		}

		if (!isValidacionTramite) {
			return false;
		}

		boolean noTienePermisosResolucion =
				!seccionesProyectoBean.getGestionUsuarioDTO().isPerfilSupervisorResolucion() &&
						!seccionesProyectoBean.getGestionUsuarioDTO().isPerfilOperadorResolucion();

		if (noTienePermisosResolucion) {
			return false;
		}

		if(authenticatorBean.isRolConsulta() || authenticatorBean.isRolAdministrador() || authenticatorBean.isRolAdministradorDatosTecnicos()) {
			return false;
		}

		//Si el trámite requiere firma, verificar que ya esté firmado
		if (seccionesProyectoBean.getFirmaDTO() != null &&
				seccionesProyectoBean.isHabilitarFirmadoTramites()) {

			try {
				// Consultar si el trámite ya tiene firma registrada
				List<TramiteFirmaElectronicaDTO> lstFirma = formularioDAO.consultarFirmaTramite(tramiteActual);
				TramiteFirmaElectronicaDTO firma = null;

				if (lstFirma != null && !lstFirma.isEmpty()) {
					firma = lstFirma.get(0);
				}

				if (firma == null || firma.getCadenaFirmada() == null || firma.getCadenaFirmada().isEmpty()) {
					return false; // No mostrar resolución si requiere firma y no está firmado
				}
			} catch (Exception e) {
				LOGGER.error("Error al consultar firma del trámite", e);
				return false;
			}
		}


		int estatus = tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite();
		boolean estatusPermitido = estatus == Constantes.ID_ESTATUS_RECHAZADO ||
				estatus == Constantes.ID_ESTATUS_APROBADO;

		boolean esUltimaSeccion = !lstSeccionesDTO.isEmpty() &&
				lstSeccionesDTO.get(lstSeccionesDTO.size() - 1).getIdSeccionFormulario()
						.equals(seccionActualDTO.getIdSeccionFormulario());

		return (estatusPermitido && esUltimaSeccion);
	}


	public void limpiarSesionTramiteCompleto() {
		FacesContext.getCurrentInstance()
				.getExternalContext()
				.getSessionMap()
				.remove("tramiteCompleto");
	}

	/**
	 * Método que verifica si una sección tiene condiciones configuradas para ocultarse
	 * @param idSeccion ID de la sección a verificar
	 * @return true si la sección tiene condiciones de ocultamiento, false en caso contrario
	 */
	private boolean tieneCondicionesOcultamiento(Long idSeccion) {
		try {
			List<ConfiguracionCondicionesDTO> condiciones = configCodicionDAO.consultarCondicionesPorSeccion(idSeccion);
			return condiciones != null && !condiciones.isEmpty();
		} catch (Exception e) {
			LOGGER.error("Error al consultar condiciones para la sección " + idSeccion, e);
			return false;
		}
	}

	/**
	 * Método que evalúa si se cumplen las condiciones para mostrar una sección
	 * @param idSeccion ID de la sección a evaluar
	 * @return true si se debe mostrarse la sección, false en caso contrario
	 */
	private boolean evaluarCondicionesOcultamiento(Long idSeccion) {

		try {

			List<ConfiguracionCondicionesDTO> condiciones = configCodicionDAO.consultarCondicionesPorSeccion(idSeccion);

			if (condiciones == null || condiciones.isEmpty()) {
				return false;
			}

			String COMPONENTE = "componente_";
			String valorActual = "";
			// Para cada condición, verificar si se cumple
			for (ConfiguracionCondicionesDTO condicion : condiciones) {

				String idComponente = condicion.getComponenteDTO().getIdComponente().toString();

				int tipoComponente = condicion.getComponenteDTO().getCatTipoComponenteDTO().getIdTipoComponente();

				if (tipoComponente == Constantes.ID_COMPONENTE_RADIO_BOTON) {

					if(mapRespuestas.get(COMPONENTE.concat(idComponente).concat("_3")) != null) {
						valorActual = String.valueOf(mapRespuestas.get(COMPONENTE.concat(idComponente).concat("_3")));
					} else if(mapRespuestas.get(COMPONENTE.concat(idComponente).concat("_1")) != null) {
						valorActual = String.valueOf(mapRespuestas.get(COMPONENTE + condicion.getComponenteDTO().getIdComponente() + "_1"));
					}

				} else if(tipoComponente == Constantes.ID_COMPONENTE_CHECKBOX_UNICO) {

					boolean checkSeleccionado = (boolean) mapRespuestas.get(COMPONENTE.concat(idComponente));

					if(checkSeleccionado) {
						valorActual = "1";
					}  else {
						valorActual = "0";
					}

				} else if(tipoComponente == Constantes.ID_COMPONENTE_MENU_DESPLEGABLE) {
					valorActual = String.valueOf(mapRespuestas.get(COMPONENTE.concat(idComponente)));
				} else if(tipoComponente == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO) {
					valorActual = String.valueOf(mapRespuestas.get(COMPONENTE.concat(idComponente)));
				}

				if (valorActual == null || valorActual.trim().isEmpty()) {
					// Si no hay valor, no se cumple la condición
					return false;
				}

				List<ConfiguracionCondicionValorDTO> valores = condicion.getValoresCondicion();
				if (valores == null || valores.isEmpty()) {
					return false;
				}

				// Comparar según el operador
				switch (condicion.getCatOperadorDTO().getIdOperador()) {
					case Constantes.OPERADOR_IGUAL_QUE: // IGUAL =

						if (tipoComponente == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO) {

							ObjectMapper mapper = new ObjectMapper();
							List<Map<String, Object>> listaSeleccionados = mapper.readValue(valorActual, new TypeReference<List<Map<String, Object>>>() {});

							if (listaSeleccionados == null || listaSeleccionados.isEmpty()) {
								return false;
							}

							Set<Integer> idsSeleccionados = new HashSet<>();
							for (Map<String, Object> elemento : listaSeleccionados) {
								idsSeleccionados.add((Integer) elemento.get("idElementoCheckbox"));
							}

							// Verificar si algun valor de condición está en los seleccionados
							for (ConfiguracionCondicionValorDTO valorCondicion : valores) {
								if (idsSeleccionados.contains(valorCondicion.getValor())) {
									return true; // Coincide al menos uno
								}
							}

							return false;
						} else {

							for (ConfiguracionCondicionValorDTO valor : valores) {
								if (valorActual.equals(String.valueOf(valor.getValor()))) {
									return true;
								}
							}
							return false;
						}

					case Constantes.OPERADOR_DISTINTO_QUE: // DIFERENTE !=

						if (tipoComponente == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO) {

							ObjectMapper mapper = new ObjectMapper();
							List<Map<String, Object>> listaSeleccionados = mapper.readValue(valorActual, new TypeReference<List<Map<String, Object>>>() {});

							if (listaSeleccionados == null || listaSeleccionados.isEmpty()) {
								return true;
							}

							Set<Integer> idsSeleccionados = new HashSet<>();
							for (Map<String, Object> elemento : listaSeleccionados) {
								idsSeleccionados.add((Integer) elemento.get("idElementoCheckbox"));
							}

							// Verificar que ningun valor de condición esté en los seleccionados
							for (ConfiguracionCondicionValorDTO valorCondicion : valores) {
								if (idsSeleccionados.contains(valorCondicion.getValor())) {
									return false;
								}
							}

							return true;
						} else {

							for (ConfiguracionCondicionValorDTO valor : valores) {
								if (valorActual.equals(String.valueOf(valor.getValor()))) {
									return false;
								}
							}
							return true;
						}

					case Constantes.OPERADOR_MAYOR_QUE: // MAYOR QUE >

						if (tipoComponente == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO) {

							ObjectMapper mapper = new ObjectMapper();
							List<Map<String, Object>> listaSeleccionados = mapper.readValue(valorActual, new TypeReference<List<Map<String, Object>>>() {});

							if (listaSeleccionados == null || listaSeleccionados.isEmpty()) {
								return false;
							}


							Set<Integer> idsSeleccionados = new HashSet<>();
							for (Map<String, Object> elemento : listaSeleccionados) {
								idsSeleccionados.add((Integer) elemento.get("idElementoCheckbox"));
							}


							// al menos una opción seleccionada debe ser mayor que al menos un valor de condición
							for (Integer idSeleccionado : idsSeleccionados) {
								for (ConfiguracionCondicionValorDTO valorCondicion : valores) {
									if (idSeleccionado > valorCondicion.getValor()) {
										return true; // encontró al menos una opción mayor
									}
								}
							}

							return false;
						} else {

							int valorActualInt = Integer.parseInt(valorActual);
							for (ConfiguracionCondicionValorDTO valor : valores) {
								if (valorActualInt > valor.getValor()) {
									return true;
								}
							}
							return false;
						}

					case Constantes.OPERADOR_MENOR_QUE: // MENOR QUE <

						if (tipoComponente == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO) {

							ObjectMapper mapper = new ObjectMapper();
							List<Map<String, Object>> listaSeleccionados = mapper.readValue(valorActual, new TypeReference<List<Map<String, Object>>>() {});

							if (listaSeleccionados == null || listaSeleccionados.isEmpty()) {
								return false;
							}


							Set<Integer> idsSeleccionados = new HashSet<>();
							for (Map<String, Object> elemento : listaSeleccionados) {
								idsSeleccionados.add((Integer) elemento.get("idElementoCheckbox"));
							}

							// al menos una opción seleccionada debe ser menor que al menos un valor de condición
							for (Integer idSeleccionado : idsSeleccionados) {
								for (ConfiguracionCondicionValorDTO valorCondicion : valores) {
									if (idSeleccionado < valorCondicion.getValor()) {
										return true; // encontró al menos una opción menor
									}
								}
							}

							return false;
						} else {

							int valorActualInt = Integer.parseInt(valorActual);
							for (ConfiguracionCondicionValorDTO valor : valores) {
								if (valorActualInt < valor.getValor()) {
									return true;
								}
							}
							return false;
						}

					case Constantes.OPERADOR_MAYOR_O_IGUAL_QUE: // MAYOR O IGUAL >=

						if (tipoComponente == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO) {

							ObjectMapper mapper = new ObjectMapper();
							List<Map<String, Object>> listaSeleccionados = mapper.readValue(valorActual, new TypeReference<List<Map<String, Object>>>() {});

							if (listaSeleccionados == null || listaSeleccionados.isEmpty()) {
								return false;
							}

							Set<Integer> idsSeleccionados = new HashSet<>();
							for (Map<String, Object> elemento : listaSeleccionados) {
								idsSeleccionados.add((Integer) elemento.get("idElementoCheckbox"));
							}


							// al menos una opción seleccionada debe ser mayor o igual que al menos un valor de condición
							for (Integer idSeleccionado : idsSeleccionados) {
								for (ConfiguracionCondicionValorDTO valorCondicion : valores) {
									if (idSeleccionado >= valorCondicion.getValor()) {
										return true; // encontró al menos una opción mayor o igual
									}
								}
							}

							return false;
						} else {

							int valorActualInt = Integer.parseInt(valorActual);
							for (ConfiguracionCondicionValorDTO valor : valores) {
								if (valorActualInt >= valor.getValor()) {
									return true;
								}
							}
							return false;
						}

					case Constantes.OPERADOR_MENOR_O_IGUAL_QUE: // MENOR O IGUAL <=

						if (tipoComponente == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO) {

							ObjectMapper mapper = new ObjectMapper();
							List<Map<String, Object>> listaSeleccionados = mapper.readValue(valorActual, new TypeReference<List<Map<String, Object>>>() {});

							if (listaSeleccionados == null || listaSeleccionados.isEmpty()) {
								return false;
							}

							Set<Integer> idsSeleccionados = new HashSet<>();
							for (Map<String, Object> elemento : listaSeleccionados) {
								idsSeleccionados.add((Integer) elemento.get("idElementoCheckbox"));
							}

							// al menos una opción seleccionada debe ser menor o igual que al menos un valor de condición
							for (Integer idSeleccionado : idsSeleccionados) {
								for (ConfiguracionCondicionValorDTO valorCondicion : valores) {
									if (idSeleccionado <= valorCondicion.getValor()) {
										return true; // encontró al menos una opción menor o igual
									}
								}
							}

							return false;
						} else {

							int valorActualInt = Integer.parseInt(valorActual);
							for (ConfiguracionCondicionValorDTO valor : valores) {
								if (valorActualInt <= valor.getValor()) {
									return true;
								}
							}
							return false;
						}

					case Constantes.OPERADOR_IN:

						if (valores == null || valores.isEmpty()) {
							return false;
						}

						if(tipoComponente == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO) {

							ObjectMapper mapper = new ObjectMapper();
							List<Map<String,Object>> lista = mapper.readValue(valorActual, new TypeReference<List<Map<String,Object>>>() {});
							if (lista == null) {
								return false;
							}

							Set<Integer> idSeleccionados = new HashSet<>();
							for (Map<String,Object> e : lista) {
								idSeleccionados.add((Integer) e.get("idElementoCheckbox"));
							}

							// Verificar que al menos un valor de condición está en los seleccionados
							for(ConfiguracionCondicionValorDTO valor : valores) {
								Integer valorCondicion = Integer.valueOf(valor.getValor());
								if(idSeleccionados.contains(valorCondicion)) {
									return true; // Encontró coincidencia
								}
							}

							return false;

						} else if(	tipoComponente == Constantes.ID_COMPONENTE_RADIO_BOTON ||
								tipoComponente == Constantes.ID_COMPONENTE_MENU_DESPLEGABLE ) {

							for (ConfiguracionCondicionValorDTO valor : condicion.getValoresCondicion()) {
								if (valorActual.equals(String.valueOf(valor.getValor()))) {
									return true; // Se cumple si encuentra coincidencia
								}
							}

							return false;
						}
						break;
					case Constantes.OPERADOR_NOT_IN:

						if (valores == null || valores.isEmpty()) {
							return false;
						}

						if(tipoComponente == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO) {

							ObjectMapper mapper = new ObjectMapper();
							List<Map<String,Object>> lista = mapper.readValue(valorActual, new TypeReference<List<Map<String,Object>>>() {});
							if (lista == null) {
								return false;
							}

							Set<Integer> idSeleccionados = new HashSet<>();
							for (Map<String,Object> e : lista) {
								idSeleccionados.add((Integer) e.get("idElementoCheckbox"));
							}

							Set<Integer> valoresCondicionSet = new HashSet<>();
							for(ConfiguracionCondicionValorDTO valor : valores) {
								valoresCondicionSet.add(Integer.valueOf(valor.getValor()));
							}

							// Verificar que NO hay intersección
							for(Integer seleccionado : idSeleccionados) {
								if(valoresCondicionSet.contains(seleccionado)) {
									return false; // Si encuentra al menos una coincidencia
								}
							}

							return true;

						} else if(	tipoComponente == Constantes.ID_COMPONENTE_RADIO_BOTON ||
								tipoComponente == Constantes.ID_COMPONENTE_MENU_DESPLEGABLE ) {

							for (ConfiguracionCondicionValorDTO valor : valores) {
								if (valorActual.equals(String.valueOf(valor.getValor()))) {
									return false;
								}
							}

							return true;
						}
						break;
					default:
						return false;
				}
			}

			return true;

		} catch (Exception e) {
			LOGGER.error("Error al evaluar condiciones para la sección " + idSeccion, e);
			return false;
		}
	}

	/**
	 * Método que encuentra la siguiente sección visible, considerando condiciones de ocultamiento
	 * @param indiceActual Índice de la sección actual
	 * @return Índice de la siguiente sección visible, o -1 si no hay más secciones
	 */
	private int encontrarSiguienteSeccionVisible(int indiceActual) {


		if (indiceActual >= lstSeccionesDTO.size() - 1) {
			return -1; // Ya es la última sección
		}

		for (int i = indiceActual + 1; i < lstSeccionesDTO.size(); i++) {
			SeccionesFormularioDTO seccion = lstSeccionesDTO.get(i);

			// Si la sección no tiene condiciones o no se cumplen, es visible
			if (!tieneCondicionesOcultamiento(seccion.getIdSeccionFormulario())) {
				return i;
			}

			if (tieneCondicionesOcultamiento(seccion.getIdSeccionFormulario())) {
				if (evaluarCondicionesOcultamiento(seccion.getIdSeccionFormulario())) {
					return i;
				}
			}
		}

		return -1; // No hay más secciones visibles
	}

	/**
	 * Método que encuentra la sección visible anterior, considerando condiciones de ocultamiento
	 * @param indiceActual Índice de la sección actual
	 * @return Índice de la sección anterior visible, o -1 si no hay más secciones
	 */
	private int encontrarAnteriorSeccionVisible(int indiceActual) {
		if (indiceActual <= 0) {
			return -1; // Ya es la primera sección
		}

		for (int i = indiceActual - 1; i >= 0; i--) {
			SeccionesFormularioDTO seccion = lstSeccionesDTO.get(i);

			// Si la sección no tiene condiciones o no se cumplen, es visible
			if (!tieneCondicionesOcultamiento(seccion.getIdSeccionFormulario())) {
				return i;
			}

			if (tieneCondicionesOcultamiento(seccion.getIdSeccionFormulario())) {
				if (evaluarCondicionesOcultamiento(seccion.getIdSeccionFormulario())) {
					return i;
				}
			}
		}

		return -1; // No hay más secciones visibles anteriores
	}

	public boolean isDeshabilitaEliminarArchivo() {
		if (isUsuarioBack && tramiteActual != null && tramiteActual.getCatEstatusTramiteDTO() != null) {
			int idEstatus = tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite();

			if (idEstatus != 1 && idEstatus != 4) {
				return true;
			}

			if (idEstatus == 4) {
				if (seccionActualDTO != null && !seccionActualDTO.isContieneObservaciones()) {
					return true;
				}

				if (isUsuarioBack && tramiteActual.getUsuario() != null && authenticatorBean != null
						&& authenticatorBean.getUsuarioLogueado() != null) {
					return tramiteActual.getUsuario().getIdUsuarioLlaveCdmx() !=
							authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx();
				}
			}
		}
		return false;
	}
	
	public String generarLineaCapturaDesdeFormularioTramite(TramiteDTO tramiteSeleccionado, 
			final ProyectoDTO proyecto) {
		if(Objects.isNull(tramiteSeleccionado.getIdTramite())) {
			this.guardarNuevoTramite();
			tramiteSeleccionado = this.tramiteActual;
		}
		
		try {
			if(documentosRequeridosCargados()) {
				return Constantes.RETURN_FORMULARIO_PAGE + Constantes.JSF_REDIRECT;
			}
			
			return this.generaLineaCapturaBean.generarLineaCapturaDesdeFormularioTramite(tramiteSeleccionado, proyecto);
		} catch (ServiciosException e) {
			return bandejaTramitesBean.inicializar();
		}

	}
	
	/*
	 * Metodo auxiliar para validar mostrar panel de comentarios
	 */
	public void mostrarPanelComentarios() {
		List<Integer> estatusValidos = Arrays.asList(Constantes.ID_ESTATUS_CORRECIONES, Constantes.ID_ESTATUS_CORREGIDO,
				Constantes.ID_ESTATUS_RECHAZADO, Constantes.ID_ESTATUS_APROBADO, Constantes.ID_ESTATUS_REVISADO,
				Constantes.ID_ESTATUS_CONCLUSION_POSITIVA, Constantes.ID_ESTATUS_CONCLUSION_NEGATIVA);
		Predicate<CatEstatusTramiteDTO> prEstatusValido = p -> estatusValidos.contains(p.getIdEstatusTramite());	
		Predicate<TramiteDTO> prFolioPreven   = p-> BeanUtils.isNotNull(p.getRespuestaFolioPrevencion());
		Predicate<TramiteDTO> prFolioConclu   = p-> BeanUtils.isNotNull(p.getRespuestaFolioConclusion());
		Predicate<TramiteDTO> prRutaConcluPos = p-> BeanUtils.isNotNull(p.getRutaDocumentoResolucionPositiva());
		Predicate<TramiteDTO> prRutaConcluNeg = p-> BeanUtils.isNotNull(p.getRutaDocumentoResolucionNegativa());
		this.setMostrarPnlComentario(prEstatusValido.test(this.tramiteActual.getCatEstatusTramiteDTO())
						 && prFolioPreven.or(prFolioConclu).or(prRutaConcluPos).or(prRutaConcluNeg)
						 				 .test(this.tramiteActual));
	}
	
	/**
	 * metodo auxiliar para el validar render del boton conclusion
	 * @param tramiteActual tramiteDTO
	 */
	public void renderedBtnConclusion(){
		long idUltimaSeccion = lstSeccionesDTO.get(lstSeccionesDTO.size() - 1).getIdSeccionFormulario();
		List<Integer> estatusValidos = Arrays.asList(Constantes.ID_ESTATUS_ENVIADO, Constantes.ID_ESTATUS_CORREGIDO,
				 									Constantes.ID_ESTATUS_REVISADO);
		Predicate<CatEstatusTramiteDTO> prEstatusValido = p -> estatusValidos.contains(p.getIdEstatusTramite());	
		Predicate<RegistrarFormularioBean> prRolConCTramite  = RegistrarFormularioBean::isRolPermiteConcluirTramite;
		Predicate<RegistrarFormularioBean> prValTramite  = RegistrarFormularioBean::isValidacionTramite;
		LongPredicate prIdUltimaSeccion = p -> p == idUltimaSeccion;
		this.setRenderBtnConclusion(prRolConCTramite.and(prValTramite).test(this) 
									&& prIdUltimaSeccion.test(this.seccionActualDTO.getIdSeccionFormulario())
											&& prEstatusValido.test(this.tramiteActual.getCatEstatusTramiteDTO()));
	}
	
	/**
	 * Metodo auxiliar para validar el render del boton finalizar revision
	 * @param tramiteActual tramiteDTO
	 */
	public void renderedBtnFinalizarRevision() {
		long idUltimaSeccion = lstSeccionesDTO.get(lstSeccionesDTO.size() - 1).getIdSeccionFormulario();
		Predicate<RegistrarFormularioBean> prRolFinalTramite  = RegistrarFormularioBean::isRolPermiteFinalizarTramite;
		Predicate<RegistrarFormularioBean> prValTramite  = RegistrarFormularioBean::isValidacionTramite;
		LongPredicate prIdUltimaSeccion = p -> p == idUltimaSeccion;
		Predicate<CatEstatusTramiteDTO> prEstatus3  = p ->p.getIdEstatusTramite()==3;
		this.setRenderBtnFinalizarRevision(prRolFinalTramite.and(prValTramite).test(this)
															&& prIdUltimaSeccion.test(this.seccionActualDTO.getIdSeccionFormulario())
															&& prEstatus3.test(this.tramiteActual.getCatEstatusTramiteDTO()));
	}
	
	/**
	 * Metodo auxiliar para mostrar el modal de concluir
	 */
	public void mostrarModalConcluir() {
		tramiteRevision = new TramiteDTO();
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('mdlConclusion').show();");
	}
	
	/**
	 * Metodo que permite redireccionar del modal de firma fallida a las bandejas
	 * @param isBandejaValidacion bandera el modal se abrio desde bandeja validacion
	 * @return true or false
	 */
	public String inicializarBandejaPostFirma(boolean isBandejaValidacion) {
		long usuarioDesconocidoLogueado = 0;
		long usuarioDesconocidoTramite = -1;
		long idUsuarioLogeuado = Objects.nonNull(authenticatorBean.getUsuarioLogueado()) ?
				authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx(): usuarioDesconocidoLogueado;
		long idUsuarioAsignadoTramite = Objects.nonNull(tramiteActual.getUsuario()) ?
				tramiteActual.getUsuario().getIdUsuarioLlaveCdmx() : usuarioDesconocidoTramite;
		boolean esMismoUsuario = idUsuarioLogeuado == idUsuarioAsignadoTramite;
		
	    if (isBandejaValidacion && !esMismoUsuario) {
	        return bandejaValidacionTramitesBean.inicializar();
	    } else {
	        return bandejaTramitesBean.inicializar();
	    }
	}
	

	
	/**
	 * 26/06/2026
	 * Metodo encargado de concentrar la logica para notificacion a webhook
	 * @author Ramiro Luna Torres
	 */
	public void iniciaProcesoNotificacionWebHook() {		
		GeneraComprobanteDTO inGeneraComprobanteDTO = new GeneraComprobanteDTO();
		Predicate<ConfiguracionWebhookDTO> prConfiguraWebhook = BeanUtils::isNotNull;
		Predicate<ConfiguracionWebhookDTO> prHabiNotiWebhook =ConfiguracionWebhookDTO::isHabilitaEnvioNotificaciones;
		Predicate<PersonaMoralDTO> prPersonaId = p->p.getIdPersonaMoral().equals(authenticatorBean.getPersonaMoralSeleccionada());
		Predicate<List<PersonaMoralDTO>> prLstMoralNotNull = BeanUtils::isNotNull;
		Predicate<List<TramiteDTO>> prLstTramitesNull = BeanUtils::isNull;
		
		List<TramiteDTO> lstTramites = new ArrayList<>();
		
		if(prConfiguraWebhook.and(prHabiNotiWebhook).test(seccionesProyectoBean.getConfiguracionWebhookDTO())){
			List<PersonaMoralDTO> listaPersonasMorales = authenticatorBean.getListaPersonasMorales();
			if(prLstMoralNotNull.test(listaPersonasMorales)) {
				for (PersonaMoralDTO personaMoral : listaPersonasMorales) {
					if(prPersonaId.test(personaMoral)) {
						this.tramiteActual.getUsuario().setIdPersonaMoral(personaMoral.getIdPersonaMoral());
						break;
					}
				}
			}
			if(prLstTramitesNull.test(lstTramitesSelFirma)) {
				lstTramites.add(this.tramiteActual);
			}else {
				lstTramites.addAll(lstTramitesSelFirma);
			}
			
			inGeneraComprobanteDTO.setLstSeccionesTramiteDTO(generarFormularioApplication.generarListaSecciones());
			inGeneraComprobanteDTO.setMapControlComponentesRespuestas(generarFormularioApplication.getMapControlComponentes());
			inGeneraComprobanteDTO.setFirmaDTO(seccionesProyectoBean.getFirmaDTO());
			inGeneraComprobanteDTO.setAccesoLlaveDTO(seccionesProyectoBean.getAccesoLlaveDTO());
			inGeneraComprobanteDTO.setSecurityDomainDTO(seccionesProyectoBean.getSecurityDomainDTO());
			inGeneraComprobanteDTO.setHabilitaFirmadoTramites(seccionesProyectoBean.isHabilitarFirmadoTramites());
			notificacionWebHookFacade.notificarWebhook(lstTramites , seccionesProyectoBean.getProyectoDTO(), seccionesProyectoBean.getConfiguracionWebhookDTO(), inGeneraComprobanteDTO);
		}	
	}
	
	/** GETTER´s y SETTER´s **/

	/**
	 * @return the lstSeccionesDTO
	 */
	public List<SeccionesFormularioDTO> getLstSeccionesDTO() {
		return lstSeccionesDTO;
	}

	/**
	 * @param lstSeccionesDTO the lstSeccionesDTO to set
	 */
	public void setLstSeccionesDTO(List<SeccionesFormularioDTO> lstSeccionesDTO) {
		this.lstSeccionesDTO = lstSeccionesDTO;
	}

	/**
	 * @return the seccionActualDTO
	 */
	public SeccionesFormularioDTO getSeccionActualDTO() {
		return seccionActualDTO;
	}

	/**
	 * @param seccionActualDTO the seccionActualDTO to set
	 */
	public void setSeccionActualDTO(SeccionesFormularioDTO seccionActualDTO) {
		this.seccionActualDTO = seccionActualDTO;
	}

	/**
	 * @return the mapControlComponentes
	 */
	public Map<String, ControlComponentesDTO> getMapControlComponentes() {
		return mapControlComponentes;
	}

	/**
	 * @param mapControlComponentes the mapControlComponentes to set
	 */
	public void setMapControlComponentes(Map<String, ControlComponentesDTO> mapControlComponentes) {
		this.mapControlComponentes = mapControlComponentes;
	}

	/**
	 * @return the mapRespuestas
	 */
	public Map<String, Object> getMapRespuestas() {
		return mapRespuestas;
	}

	/**
	 * @param mapRespuestas the mapRespuestas to set
	 */
	public void setMapRespuestas(Map<String, Object> mapRespuestas) {
		this.mapRespuestas = mapRespuestas;
	}

	/**
	 * @return the proyectoDTO
	 */
	public ProyectoDTO getProyectoDTO() {
		return proyectoDTO;
	}

	/**
	 * @param proyectoDTO the proyectoDTO to set
	 */
	public void setProyectoDTO(ProyectoDTO proyectoDTO) {
		this.proyectoDTO = proyectoDTO;
	}

	/**
	 * @return the tramiteActual
	 */
	public TramiteDTO getTramiteActual() {
		return tramiteActual;
	}

	/**
	 * @param tramiteActual the tramiteActual to set
	 */
	public void setTramiteActual(TramiteDTO tramiteActual) {
		this.tramiteActual = tramiteActual;
	}

	/**
	 * @return the isEdicionTramite
	 */
	public boolean isEdicionTramite() {
		return isEdicionTramite;
	}

	/**
	 * @param isEdicionTramite the isEdicionTramite to set
	 */
	public void setEdicionTramite(boolean isEdicionTramite) {
		this.isEdicionTramite = isEdicionTramite;
	}

	public int getIdEstatusValidacionTramite() {
		return idEstatusValidacionTramite;
	}

	public void setIdEstatusValidacionTramite(int idEstatusValidacionTramite) {
		this.idEstatusValidacionTramite = idEstatusValidacionTramite;
	}

	public boolean isMostrarModalTipoPersona() {
		return mostrarModalTipoPersona;
	}

	public void setMostrarModalTipoPersona(boolean mostrarModalTipoPersona) {
		this.mostrarModalTipoPersona = mostrarModalTipoPersona;
	}

	public String getMsgTipoPersona() {
		return msgTipoPersona;
	}

	public void setMsgTipoPersona(String msgTipoPersona) {
		this.msgTipoPersona = msgTipoPersona;
	}

	/**
	 * @return the isSeccionInformativa
	 */
	public boolean isSeccionInformativa() {
		return isSeccionInformativa;
	}

	/**
	 * @param isSeccionInformativa the isSeccionInformativa to set
	 */
	public void setSeccionInformativa(boolean isSeccionInformativa) {
		this.isSeccionInformativa = isSeccionInformativa;
	}

	/**
	 * @return the isUltimaSeccion
	 */
	public boolean isUltimaSeccion() {
		return isUltimaSeccion;
	}

	/**
	 * @param isUltimaSeccion the isUltimaSeccion to set
	 */
	public void setUltimaSeccion(boolean isUltimaSeccion) {
		this.isUltimaSeccion = isUltimaSeccion;
	}

	/**
	 * @return the isObservacionesTramite
	 */
	public boolean isObservacionesTramite() {
		return isObservacionesTramite;
	}

	/**
	 * @param isObservacionesTramite the isObservacionesTramite to set
	 */
	public void setObservacionesTramite(boolean isObservacionesTramite) {
		this.isObservacionesTramite = isObservacionesTramite;
	}

	/**
	 * @return the isUsuarioBack
	 */
	public boolean isUsuarioBack() {
		return isUsuarioBack;
	}

	/**
	 * @param isUsuarioBack the isUsuarioBack to set
	 */
	public void setUsuarioBack(boolean isUsuarioBack) {
		this.isUsuarioBack = isUsuarioBack;
	}

	/**
	 * @return the isNuevoTramite
	 */
	public boolean isNuevoTramite() {
		return isNuevoTramite;
	}

	/**
	 * @param isNuevoTramite the isNuevoTramite to set
	 */
	public void setNuevoTramite(boolean isNuevoTramite) {
		this.isNuevoTramite = isNuevoTramite;
	}

	public UsuarioDTO getUsuarioTramite() {
		return usuarioTramite;
	}

	public void setUsuarioTramite(UsuarioDTO usuarioTramite) {
		this.usuarioTramite = usuarioTramite;
	}

	/**
	 * @return the rolPermiteConcluirTramite
	 */
	public boolean isRolPermiteConcluirTramite() {
		return rolPermiteConcluirTramite;
	}

	/**
	 * @param rolPermiteConcluirTramite the rolPermiteConcluirTramite to set
	 */
	public void setRolPermiteConcluirTramite(boolean rolPermiteConcluirTramite) {
		this.rolPermiteConcluirTramite = rolPermiteConcluirTramite;
	}

	/**
	 * @return the rolPermitePrevenirTramite
	 */
	public boolean isRolPermitePrevenirTramite() {
		return rolPermitePrevenirTramite;
	}

	/**
	 * @param rolPermitePrevenirTramite the rolPermitePrevenirTramite to set
	 */
	public void setRolPermitePrevenirTramite(boolean rolPermitePrevenirTramite) {
		this.rolPermitePrevenirTramite = rolPermitePrevenirTramite;
	}

	/**
	 * @return the rolPermiteFinalizarTramite
	 */
	public boolean isRolPermiteFinalizarTramite() {
		return rolPermiteFinalizarTramite;
	}

	/**
	 * @param rolPermiteFinalizarTramite the rolPermiteFinalizarTramite to set
	 */
	public void setRolPermiteFinalizarTramite(boolean rolPermiteFinalizarTramite) {
		this.rolPermiteFinalizarTramite = rolPermiteFinalizarTramite;
	}

	/**
	 * @return the tramiteRevision
	 */
	public TramiteDTO getTramiteRevision() {
		return tramiteRevision;
	}

	/**
	 * @param tramiteRevision the tramiteRevision to set
	 */
	public void setTramiteRevision(TramiteDTO tramiteRevision) {
		this.tramiteRevision = tramiteRevision;
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
	 * @return the isFirmaBandeja
	 */
	public boolean isFirmaBandeja() {
		return isFirmaBandeja;
	}

	/**
	 * @param isFirmaBandeja the isFirmaBandeja to set
	 */
	public void setFirmaBandeja(boolean isFirmaBandeja) {
		this.isFirmaBandeja = isFirmaBandeja;
	}

	public boolean isFirmaTramiteCiudadano() {
		return isFirmaTramiteCiudadano;
	}

	public void setFirmaTramiteCiudadano(boolean isFirmaTramiteCiudadano) {
		this.isFirmaTramiteCiudadano = isFirmaTramiteCiudadano;
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
	 * @return the isValidacionTramite
	 */
	public boolean isValidacionTramite() {
		return isValidacionTramite;
	}

	/**
	 * @param isValidacionTramite the isValidacionTramite to set
	 */
	public void setValidacionTramite(boolean isValidacionTramite) {
		this.isValidacionTramite = isValidacionTramite;
	}

	public boolean isFirmaCiudadano() {
		return firmaCiudadano;
	}

	public void setFirmaCiudadano(boolean firmaCiudadano) {
		this.firmaCiudadano = firmaCiudadano;
	}

	public TramiteFirmaElectronicaDTO getDatosFirmaCiudadanoDTO() {
		return datosFirmaCiudadanoDTO;
	}

	public void setDatosFirmaCiudadanoDTO(TramiteFirmaElectronicaDTO datosFirmaCiudadanoDTO) {
		this.datosFirmaCiudadanoDTO = datosFirmaCiudadanoDTO;
	}

	public boolean isTipoResolucionTramite() {
		return tipoResolucionTramite;
	}

	public void setTipoResolucionTramite(boolean tipoResolucionTramite) {
		this.tipoResolucionTramite = tipoResolucionTramite;
	}

	public String getLeyendaTipoDeResolucionTramite() {
		return leyendaTipoDeResolucionTramite;
	}

	public void setLeyendaTipoDeResolucionTramite(String leyendaTipoDeResolucionTramite) {
		this.leyendaTipoDeResolucionTramite = leyendaTipoDeResolucionTramite;
	}

	public boolean isMostrarPnlComentario() {
		return mostrarPnlComentario;
	}

	public void setMostrarPnlComentario(boolean mostrarPnlComentario) {
		this.mostrarPnlComentario = mostrarPnlComentario;
	}

	public boolean isRenderBtnConclusion() {
		return renderBtnConclusion;
	}

	public void setRenderBtnConclusion(boolean renderBtnConclusion) {
		this.renderBtnConclusion = renderBtnConclusion;
	}

	public boolean isRenderBtnFinalizarRevision() {
		return renderBtnFinalizarRevision;
	}

	public void setRenderBtnFinalizarRevision(boolean renderBtnFinalizarRevision) {
		this.renderBtnFinalizarRevision = renderBtnFinalizarRevision;
	}

	public List<TramiteDTO> getLstTramitesSelFirma() {
		return lstTramitesSelFirma;
	}

	public void setLstTramitesSelFirma(List<TramiteDTO> lstTramitesSelFirma) {
		this.lstTramitesSelFirma = lstTramitesSelFirma;
	}
}
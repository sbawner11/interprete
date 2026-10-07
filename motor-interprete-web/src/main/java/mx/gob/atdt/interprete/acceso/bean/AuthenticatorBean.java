package mx.gob.atdt.interprete.acceso.bean;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.primefaces.component.captcha.Captcha;
import org.primefaces.event.CloseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.client.OAuth2CdmxClient;
import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.commons.utils.Utils;
import mx.gob.atdt.interprete.dao.PersonaMoralDAO;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.UsuarioDAO;
import mx.gob.atdt.interprete.dto.PersonaMoralDTO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.formularios.application.GenerarFormularioApplication;
import mx.gob.atdt.interprete.formularios.bean.RegistrarFormularioBean;
import mx.gob.atdt.interprete.oauth.dto.RequestRolesDTO;
import mx.gob.atdt.interprete.oauth.dto.RequestTokenDTO;
import mx.gob.atdt.interprete.oauth.dto.RolesUsuarioDTO;
import mx.gob.atdt.interprete.tramites.bean.BandejaTramitesBean;
import mx.gob.atdt.interprete.tramites.bean.BandejaValidacionTramitesBean;
import mx.gob.atdt.interprete.tramites.bean.ConsultaTramiteExpedienteBean;
import mx.gob.atdt.interprete.util.WebResources;
import mx.gob.atdt.widget.application.IndexBean;

/**
 * @autor Raúl Soto
 */
@Named
@SessionScoped
public class AuthenticatorBean implements Serializable {

	private static final long serialVersionUID = 6048100734819950909L;
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticatorBean.class);
	private static final String PARAM_LISTLLAVE = "listllave";

	@Inject
	private FacesContext facesContext;

	@Inject
	private HttpServletRequest request;

	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private PersonaMoralDAO personaMoralDAO;

	@Inject
	private ComponenteDAO componenteDAO;
	
	@Inject
	private BandejaTramitesBean bandejaTramitesBean;

	@Inject
	private BandejaValidacionTramitesBean bandejaValidacionTramitesBean;
	
	@Inject
	private RegistrarFormularioBean registrarFormularioBean;

	@Inject
	private SeccionesProyectoBean seccionesProyectoBean;
	
	@Inject
	private GenerarFormularioApplication generarFormularioApplication;
	
	@Inject
	private ConsultaTramiteExpedienteBean consultaTramiteExpedienteBean;
	
	@Inject
	private IndexBean indexBean;

	private String codeOauth;
	private String tokenOauth;
	private UsuarioDTO usuarioLogueado;
	private List<PersonaMoralDTO> personasMorales;

	private boolean ingresaDesdeLlave;

	private boolean mostrarNotificacion = true;

	private boolean rolSupervisor;
	private boolean rolOperador;
	private boolean rolConsulta;
	private boolean rolAdministrador;
	private boolean rolAdministradorDatosTecnicos;

	private boolean isAceptaManifiesto;
	
	private boolean ingresaBackOffice;
	
	private Origen origen;
		
	private boolean habilitarMenuPersonaMoral = false;
	private boolean ciudadanoSeleccionado;
	private Long personaMoralSeleccionada;
	private List<PersonaMoralDTO> listaPersonasMorales;
	
	private String redirectPage;
	
	private boolean haSeleccionadoPreviamente = false;
	private boolean esPrimeraVezEnVista = true;
	private boolean recuperaCiudadanoSeleccionado;
	private Long recuperaPersonaMoralSeleccionada;
	
	enum Origen{
		HOME, EXPEDIENTE, NUEVO_TRAMITE
	}

	public void inicializar() {
		usuarioLogueado = new UsuarioDTO();
		personasMorales = new ArrayList<>();
		ingresaDesdeLlave = false;
		isAceptaManifiesto = false;
		ingresaBackOffice = false;
		haSeleccionadoPreviamente = false;
		esPrimeraVezEnVista = true;
		recuperaCiudadanoSeleccionado = false;
	    recuperaPersonaMoralSeleccionada = null;
		
	}
	
	public String inicializarPersonasMorales() {

		listaPersonasMorales = personaMoralDAO
				.buscarPorId(usuarioLogueado.getIdUsuarioLlaveCdmx());
		if (listaPersonasMorales == null) {
			habilitarMenuPersonaMoral = false;
		} else {

			habilitarMenuPersonaMoral = true;
			usuarioLogueado.setEsPersonaMoral(true);
		}
		
		return Constantes.RETURN_PERSONAS_MORALES_PAGE + Constantes.JSF_REDIRECT;
	}
	
	public void seleccionaCiudadano() {

		if (ciudadanoSeleccionado) {
			personaMoralSeleccionada = null;
			usuarioLogueado.setEsPersonaMoral(false);
			usuarioLogueado.setIdPersonaMoral(null);
		}
	}

	public void procesarSeleccionPersonaMoral() {
		usuarioLogueado.setEsPersonaMoral(true);
		usuarioLogueado.setIdPersonaMoral(personaMoralSeleccionada);
	}
	
	
	private void guardarSeleccionTipoPersona() {
		
	    recuperaCiudadanoSeleccionado = ciudadanoSeleccionado;
	    recuperaPersonaMoralSeleccionada = personaMoralSeleccionada;
	}
	
	private void restaurarSeleccion() {
		
	    ciudadanoSeleccionado = recuperaCiudadanoSeleccionado;
	    personaMoralSeleccionada = recuperaPersonaMoralSeleccionada;
	    
	    if (ciudadanoSeleccionado) {
	        usuarioLogueado.setEsPersonaMoral(false);
	        usuarioLogueado.setIdPersonaMoral(null);
	    } else if (personaMoralSeleccionada != null) {
	        usuarioLogueado.setEsPersonaMoral(true);
	        usuarioLogueado.setIdPersonaMoral(personaMoralSeleccionada);
	    }
	}
	
	public void ejecutaAlCargarPagina() {
		
		 if (esPrimeraVezEnVista) {
			 guardarSeleccionTipoPersona();
		     haSeleccionadoPreviamente = (ciudadanoSeleccionado || personaMoralSeleccionada != null);
		 }
		 
		 esPrimeraVezEnVista = false;
	}
	
	public void btnCancelarSeleccionTipoPersona() {
		
		restaurarSeleccion();
		redirectToPage();
	}
	
	public void btnContinuarTipoPersona() {
		
		guardarSeleccionTipoPersona();
		haSeleccionadoPreviamente = true;
		redirectToPage();
	}
	
	public void redirectToPage() {
		
		try {
								
			if(getRedirectPage().equals(Constantes.FORMULARIO_PAGE_GENERAR_FORMULARIO)) {
				
				facesContext.getExternalContext()
				.redirect(facesContext.getExternalContext().getRequestContextPath()
						+ generarFormularioApplication.iniciarCapturaFormulario());
				
			} else if(getRedirectPage().equals(Constantes.FORMULARIO_PAGE_REGISTRAR_FORMULARIO)) {

				facesContext.getExternalContext()
				.redirect(facesContext.getExternalContext().getRequestContextPath()
						+ registrarFormularioBean.iniciarEdicionFormulario(consultaTramiteExpedienteBean.getTramiteConsulta(), false));
				
			} else if(getRedirectPage().equals(Constantes.RETURN_BANDEJA_VALIDACION_TRAMITES_PAGE)) {
				
				facesContext.getExternalContext()
				.redirect(facesContext.getExternalContext().getRequestContextPath()
						+ bandejaValidacionTramitesBean.inicializar());
				
			} else if(getRedirectPage().equals(Constantes.RETURN_BANDEJA_TRAMITES_PAGE)) {
				
				facesContext.getExternalContext()
				.redirect(facesContext.getExternalContext().getRequestContextPath()
						+ bandejaTramitesBean.inicializar());
				
			} 
					
						
		} catch (IOException e) {			
			e.printStackTrace();
			LOGGER.error("Ocurrio un error al redireccionar pagina", e);
		}
	}

	
	public void actualizaContextCaptcha() {	
		FacesContext context = FacesContext.getCurrentInstance();
		String privateKey = context.getApplication().evaluateExpressionGet(context,
                context.getExternalContext().getInitParameter(Captcha.PRIVATE_KEY), String.class);
		String publicKey = context.getApplication().evaluateExpressionGet(context,
                context.getExternalContext().getInitParameter(Captcha.PUBLIC_KEY), String.class);
	}

	/**
	 * Método inicial que construye la ULR con la que se solicitará a Llave el
	 * acceso
	 */
	public void redirectUrlLoginCDMX(int origen, boolean ingresaDesdeBackOffice) {
		if(origen == Constantes.ID_ORIGEN_HOME) {
			this.origen = Origen.HOME;		
		} else if (origen == Constantes.ID_ORIGEN_EXPEDIENTE) {
			this.origen = Origen.EXPEDIENTE;			
		} else if (origen == Constantes.ID_NUEVO_TRAMITE) {
			this.origen = Origen.NUEVO_TRAMITE;
		} else {
			throw new IllegalArgumentException("No se puede realizar el redirect porque se obtuvo un origen no implementado aún");
		}		
		StringBuilder urlLoginLlaveCDMx = new StringBuilder();
		ingresaBackOffice = ingresaDesdeBackOffice;
		try {
			urlLoginLlaveCDMx.append(Environment.getUrlLoginCdmx()).append("?").append(Constantes.PARAM_CLIENT_ID)
					.append("=").append(seccionesProyectoBean.getAccesoLlaveDTO().getClaveSistema()).append("&");
			if(this.origen == Origen.HOME) {
				urlLoginLlaveCDMx.append(Constantes.PARAM_REDIRECT).append("=")
				.append(URLEncoder.encode(seccionesProyectoBean.getAccesoLlaveDTO().getUrlRedireccionar(), "UTF-8")).append("&");	
			} else if(this.origen == Origen.EXPEDIENTE || this.origen == Origen.NUEVO_TRAMITE) {
				/**Para conformar la URL para validación de login desde expediente, se toma la url base del detalle de dominio de seguridad del proyecto**/
				urlLoginLlaveCDMx.append(Constantes.PARAM_REDIRECT).append("=")
				.append(URLEncoder.encode((seccionesProyectoBean.getSecurityDomainDTO().getUrlSistema().concat(Constantes.RETURN_LOGIN_EXPEDIENTE_PAGE).toString()), "UTF-8")).append("&");
			}
			urlLoginLlaveCDMx.append(Constantes.PARAM_STATE).append("=").append(Utils.randomChars().toString()).toString();
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Ocurrio un error al realizar el encode de :", e);
		}
		isAceptaManifiesto = false;
		try {
			facesContext.getExternalContext().redirect(urlLoginLlaveCDMx.toString());
		} catch (IOException e) {
			LOGGER.error("Ocurrio un error al generar el redirect al Login MX:", e);
		}
	}

	public void revisaRespuestaLoginCdmx() {

		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if (!FacesContext.getCurrentInstance().isPostback() && params != null && !params.isEmpty()) {

			if (params.get(Constantes.PARAM_CODE) == null
					|| params.get(Constantes.PARAM_CODE).compareTo(Constantes.EMPTY_STRING) == 0) {
				// usuarioLogueado = new PersonaDTO();
				WebResources.addValidationMessage("login.code_incorrecto", true);
				LOGGER.debug("Code incorrecto:" + params.get(Constantes.PARAM_CODE));
				try {
					facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath()
							+ Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT + "&error=codeIncorrecto");
				} catch (IOException e) {
					LOGGER.error("Error al redireccionar al index.", e);
				}
			} else {
				try {
					codeOauth = params.get(Constantes.PARAM_CODE);
					tokenOauth = cambiaCodePorToken(codeOauth);
					LOGGER.debug("Token recibido de Llave MX:" + tokenOauth);
					if (tokenOauth != null && !codeOauth.isEmpty()) {
						usuarioLogueado = cambiarTokenPorDatosUsuario(tokenOauth);						

						List<ComponenteDTO> componentes= componenteDAO.buscarPorTipo(Constantes.ID_COMPONENTE_DATOS_PERSONA_MORAL);
						if (componentes != null && !componentes.isEmpty() ) {
							personasMorales = getDatosPersonaMoral(tokenOauth);
						}

						if (usuarioLogueado != null) {
							obtenerRolesUsuario(usuarioLogueado.getIdUsuarioLlaveCdmx(),
									Long.parseLong(seccionesProyectoBean.getAccesoLlaveDTO().getClaveSistema()),
									tokenOauth);
							/**Si proviene desde URL para iniciar nuevo trámite, se tiene que redireccionar al inicio del formulario **/
							if(this.origen == Origen.NUEVO_TRAMITE) {
								/**Se valida si el estatus del sistema es "En línea" para poder ingresar al formulario, de lo contrario no se puede ingresar**/
								if (indexBean.getEstadoSistemaDTO().getCatEstadosSistemaDTO().getIdEstadoSistema() == Constantes.ID_ESTADO_SISTEMA_LINEA) {
									ingresaDesdeLlave = false;
									ingresaBackOffice = false;
									guardarActualizarUsuario();
									crearSesionUsuario(tokenOauth);
									if(personasMorales != null) {
										
										redirectPage = Constantes.FORMULARIO_PAGE_GENERAR_FORMULARIO;
										guardarActualizarPersonaMoral();												
										facesContext.getExternalContext()
											.redirect(facesContext.getExternalContext().getRequestContextPath()
											+ inicializarPersonasMorales());	
										
									} else {
																			
										facesContext.getExternalContext()
										.redirect(facesContext.getExternalContext().getRequestContextPath()
												+ generarFormularioApplication.iniciarCapturaFormulario());
									}
									

								} else {
									/**Si el estatus del proyecto es distinto a "En línea", no se permite el acceso**/
									usuarioLogueado = null;
									facesContext.getExternalContext()
										.redirect(facesContext.getExternalContext().getRequestContextPath()
											+ Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT+ "&error=nuevoTramite");
									WebResources.addValidationMessage("acceso.no_disponible", true);
								}
							
							} else if (this.origen == Origen.EXPEDIENTE) {
								
								/**Si proviene desde el expediente, se tiene que redireccionar al inicio del formulario **/
								
								if (indexBean.getEstadoSistemaDTO().getCatEstadosSistemaDTO().getIdEstadoSistema() == Constantes.ID_ESTADO_SISTEMA_LINEA) {
									/**Se valida que el id usuario que se loguea sea el mismo que registró el trámite**/
									if(BeanUtils.isNull(consultaTramiteExpedienteBean.getTramiteConsulta().getUsuario()) ||
											consultaTramiteExpedienteBean.getTramiteConsulta().getUsuario().getIdUsuarioLlaveCdmx() 
											!= usuarioLogueado.getIdUsuarioLlaveCdmx()) {
										WebResources.addValidationMessage("msj_cuenta_no_duenia_tramite", true);
									} else {
										ingresaDesdeLlave = false;
										ingresaBackOffice = false;
										guardarActualizarUsuario();
										crearSesionUsuario(tokenOauth);
										if(personasMorales != null) {
											
											redirectPage = Constantes.FORMULARIO_PAGE_REGISTRAR_FORMULARIO;
											guardarActualizarPersonaMoral();												
											facesContext.getExternalContext()
												.redirect(facesContext.getExternalContext().getRequestContextPath()
												+ inicializarPersonasMorales());
										} else {
											
											facesContext.getExternalContext()
											.redirect(facesContext.getExternalContext().getRequestContextPath()
													+ registrarFormularioBean.iniciarEdicionFormulario(consultaTramiteExpedienteBean.getTramiteConsulta(), false));
										}
										
										
									}											
								} else {
									// Si el estatus del proyecto es distinto a "En línea", no se permite el acceso
									usuarioLogueado = null;
									facesContext.getExternalContext()
										.redirect(facesContext.getExternalContext().getRequestContextPath()
											+ Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT);
									WebResources.addValidationMessage("acceso.no_disponible", true);
								}																					
							} else {
								// Se verifica que el usuario contenga Roles para ingresar al aplicativo
//								LOGGER.info("ingresaBackOffice: " + ingresaBackOffice);
								if (ingresaBackOffice) {				
									if(BeanUtils.isNotNull(usuarioLogueado.getLstRoles())) {									
										// Se revisa los roles que tiene el usuario actual
										validarRolUsuarioActual();
										if (rolSupervisor || rolOperador || rolAdministrador || rolAdministradorDatosTecnicos) {
											
											ingresaDesdeLlave = false;
											guardarActualizarUsuario();
											crearSesionUsuario(tokenOauth);
											
											if(personasMorales != null) {											
												redirectPage = Constantes.RETURN_BANDEJA_VALIDACION_TRAMITES_PAGE;
												guardarActualizarPersonaMoral();																																		
											} 												
											
											facesContext.getExternalContext()
												.redirect(facesContext.getExternalContext().getRequestContextPath()
														+ bandejaValidacionTramitesBean.inicializar());
																																									
										} else {												
											usuarioLogueado = null;
											facesContext.getExternalContext()
												.redirect(facesContext.getExternalContext().getRequestContextPath()
													+ "rolInvalido.xhtml");
										}									
									} else {
										usuarioLogueado = null;
										facesContext.getExternalContext()
											.redirect(facesContext.getExternalContext().getRequestContextPath()
												+ "rolInvalido.xhtml");
									}
								} else {								
									if (indexBean.getEstadoSistemaDTO().getCatEstadosSistemaDTO().getIdEstadoSistema() == Constantes.ID_ESTADO_SISTEMA_LINEA) {									
										if(BeanUtils.isNotNull(usuarioLogueado.getLstRoles())) {									
											// Se revisa los roles que tiene el usuario actual
											validarRolUsuarioActual();
											if (rolSupervisor || rolOperador || rolAdministrador || rolAdministradorDatosTecnicos) {
												ingresaDesdeLlave = false;
												ingresaBackOffice = false;
												guardarActualizarUsuario();
												crearSesionUsuario(tokenOauth);
												if(personasMorales != null) {
													
													redirectPage = Constantes.RETURN_BANDEJA_VALIDACION_TRAMITES_PAGE;
													guardarActualizarPersonaMoral();												
													facesContext.getExternalContext()
														.redirect(facesContext.getExternalContext().getRequestContextPath()
														+ inicializarPersonasMorales());
													
												} else {													
													facesContext.getExternalContext()
													.redirect(facesContext.getExternalContext().getRequestContextPath()
															+ bandejaValidacionTramitesBean.inicializar());
												}												
											} else {
												// No tiene rol de supervisor u Operador
												if (validarRolesPermitidos()) {
													ingresaDesdeLlave = false;
													ingresaBackOffice = false;
													guardarActualizarUsuario();
													crearSesionUsuario(tokenOauth);
												
													if(personasMorales != null) {
													
														redirectPage = Constantes.RETURN_BANDEJA_TRAMITES_PAGE;
														guardarActualizarPersonaMoral();												
														facesContext.getExternalContext()
															.redirect(facesContext.getExternalContext().getRequestContextPath()
																+ inicializarPersonasMorales());
													} else {													
														facesContext.getExternalContext()
														.redirect(facesContext.getExternalContext().getRequestContextPath()
																+ bandejaTramitesBean.inicializar());
													}
												} else {
													cerrarSesionUsuario();									
													usuarioLogueado = null;
													facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + Constantes.URL_USUARIO_NO_AUTORIZADO + Constantes.JSF_REDIRECT);
												}
											}											
										} else {
											// No tiene roles, es usuario ciudadano, pero se debe re revisar si tiene habilitada la 
											// restricción de ingresar por roles
											if (validarRolesPermitidos()) {
												
												ingresaDesdeLlave = false;
												ingresaBackOffice = false;
												guardarActualizarUsuario();
												crearSesionUsuario(tokenOauth);
												if(personasMorales != null) {
													
													redirectPage = Constantes.RETURN_BANDEJA_TRAMITES_PAGE;
													guardarActualizarPersonaMoral();												
													facesContext.getExternalContext()
														.redirect(facesContext.getExternalContext().getRequestContextPath()
															+ inicializarPersonasMorales());
												} else {												
													facesContext.getExternalContext()
													.redirect(facesContext.getExternalContext().getRequestContextPath()
															+ bandejaTramitesBean.inicializar());
												}		
												
											} else {
												
												cerrarSesionUsuario();									
												usuarioLogueado = null;
												facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + Constantes.URL_USUARIO_NO_AUTORIZADO + Constantes.JSF_REDIRECT);
												
											}																		
										}
									} else {
										// Si el estatus del proyecto es distinto a "En línea", no se permite el acceso
										usuarioLogueado = null;
										facesContext.getExternalContext()
											.redirect(facesContext.getExternalContext().getRequestContextPath()
												+ Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT + "&error=ProyectoNoLinea");
										WebResources.addValidationMessage("login.no_disponible", true);
									}
								}	
							}
						}
					} else {
						facesContext.getExternalContext()
								.redirect(facesContext.getExternalContext().getRequestContextPath()
										+ Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT + "&error=cambiarCodePorToken");
						WebResources.addValidationMessage("login.token_incorrecto", true);
					}
				} catch (Exception e) {
					WebResources.addValidationMessage("msj_error_general_llavecdmx", false);
					LOGGER.error("Ocurrio un error al iniciar sesión: ", e);
				}
			}
		}
	}
	
	/**
	 * Método auxiliar que valida si el proyecto tiene la configuración para restringir acceso por roles el usuario actual debe contar con 
	 * un rol que si permita el acceso, de lo contrario no se permite el ingreso.
	 * @return
	 */
	private boolean validarRolesPermitidos() {
		boolean permitirContinuar = false;
		
		if(rolConsulta) {
		     permitirContinuar = true;
		   } else {
		
	    if (seccionesProyectoBean.getAccesoLlaveDTO().isValidaRol()) {
	    	String [] permisosRoles = seccionesProyectoBean.getAccesoLlaveDTO().getRolesPermitidos().split(",");
	    	
	    	for(String rolMotor : permisosRoles) {
	    		
	    		if(BeanUtils.isNotNull(usuarioLogueado.getLstRoles()) && BeanUtils.isNotEmpty(usuarioLogueado.getLstRoles())) {
	    			for (RolesUsuarioDTO rolLlave : usuarioLogueado.getLstRoles()) {
	    				if (convertirTextoMayusculasSinEspacios(rolMotor).equalsIgnoreCase(convertirTextoMayusculasSinEspacios(rolLlave.getRol()))) {
	    					permitirContinuar = true;
	    					break;
	    				} 
	    			}
	    				break;  
	    			} 
	    		}
	    	} else {
	    		permitirContinuar = true;
	    	}
		 }
	    
	    return permitirContinuar;
	  }
	
	
	/**
	 * Método auxiliar para quitar espacios y convertir a mayúsculas un texto
	 * @param cadena
	 * @return
	 */
	private String convertirTextoMayusculasSinEspacios(String cadena) {
		return cadena.replaceAll("\\s+", "").toUpperCase();
	}

	/**
	 * Método que verifica el rol del usuario actual
	 */
	private void validarRolUsuarioActual() {
		rolSupervisor = false;
		rolOperador = false;
		rolConsulta = false;
		rolAdministrador = false;
		rolAdministradorDatosTecnicos = false;

		for (RolesUsuarioDTO rolTemp : usuarioLogueado.getLstRoles()) {
			if (rolTemp.getRol().equalsIgnoreCase(Constantes.DESC_ROL_ADMINISTRADOR)
					|| rolTemp.getRol().equalsIgnoreCase(Constantes.DESC_ROL_ADMINISTRADOR_GENERAL)) {
				rolAdministrador = true;
				break;
			} else if (rolTemp.getRol().equalsIgnoreCase(Constantes.DESC_ROL_ADMINISTRADOR_DATOS_TECNICOS)) {
				rolAdministradorDatosTecnicos = true;
				break;
			} else if (rolTemp.getRol().equalsIgnoreCase(Constantes.DESC_ROL_SUPERVISOR)) {
				rolSupervisor = true;
				break;
			} else if (rolTemp.getRol().equalsIgnoreCase(Constantes.DESC_ROL_OPERADOR)) {
				rolOperador = true;
				break;
			}else if (rolTemp.getRol().equalsIgnoreCase(Constantes.DESC_ROL_CONSULTA)) {
				rolConsulta = true;
				break;				
			}
		}
	}

	/**
	 * Método que guarda o actualiza la información del usario que se loguea.
	 */
	private void guardarActualizarUsuario() {
		UsuarioDTO tmpUsuario = usuarioDAO.buscarPorId(usuarioLogueado.getIdUsuarioLlaveCdmx());
		if (tmpUsuario != null) {
			if (!tmpUsuario.equals(usuarioLogueado)) {
				usuarioDAO.actualizar(usuarioLogueado);
			}
		} else {
			usuarioDAO.guardar(usuarioLogueado);
		}
	}

	/**
	 * Método que se utiliza para validar si la URL de inicio cuenta con el
	 * parámetro listllave, lo que identifica que viene redireccionado desde llave.
	 */
	public void revisaRedirectUrlInicio() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if (!FacesContext.getCurrentInstance().isPostback() && params != null && !params.isEmpty()) {
			setIngresaDesdeLlave(false);
			if (usuarioLogueado == null) {
				usuarioLogueado = new UsuarioDTO();
			}
			if (params.get(PARAM_LISTLLAVE) == null || params.get(PARAM_LISTLLAVE).trim().compareTo(Constantes.EMPTY_STRING) == 0) {
				setIngresaDesdeLlave(false);
			} else {
				if (params.get(PARAM_LISTLLAVE).compareToIgnoreCase("true") == 0 || params.get(PARAM_LISTLLAVE).compareToIgnoreCase("false") == 0) {
					setIngresaDesdeLlave(Boolean.parseBoolean(params.get(PARAM_LISTLLAVE)));
				}
			}
		}
	}
	

	/**
	 * Método que obtiene un token mediante el code generado
	 * 
	 * @param code
	 * @return
	 */
	private String cambiaCodePorToken(final String code) {
		RequestTokenDTO requestToken = new RequestTokenDTO();
		requestToken.setClientId(seccionesProyectoBean.getAccesoLlaveDTO().getClaveSistema());
		requestToken.setClientSecret(seccionesProyectoBean.getAccesoLlaveDTO().getCodigoSecreto());
		requestToken.setCode(code);
		requestToken.setGrantType(Constantes.GRANT_TYPE_AUTHORIZATION_CODE);
		requestToken.setRedirectUri(seccionesProyectoBean.getAccesoLlaveDTO().getUrlRedireccionar());

		OAuth2CdmxClient oautCdmxClient = new OAuth2CdmxClient();
		String token = oautCdmxClient.obtenerToken(requestToken);
		return token;
	}

	/**
	 * Método que obtiene la información del usuario logueado para el inicio de la
	 * sesión
	 * 
	 * @param token
	 * @return
	 */
	private UsuarioDTO cambiarTokenPorDatosUsuario(final String token) {
		String strToken = "";
		if (token != null) {
			try {
				JSONObject tokenObj = new JSONObject(token);
				strToken = tokenObj.get("accessToken").toString();
			} catch (JSONException e) {
				LOGGER.error("Error al crear el json del token", e);
			}
		}
		OAuth2CdmxClient oauthCdmxClient = new OAuth2CdmxClient();

		return oauthCdmxClient.obtenerDatosUsuarioPorToken(strToken);
	}
	
	
	private List<PersonaMoralDTO> getDatosPersonaMoral(String token) {
		String strToken = "";
		if(token != null) {
			try {
				JSONObject tokenObj = new JSONObject(token);
				strToken = tokenObj.get("accessToken").toString();
			} catch (JSONException e) {
				LOGGER.error("Error al crear el json del token");
			}
		}
		
		OAuth2CdmxClient oauthCdmxClient = new OAuth2CdmxClient();
		
		return oauthCdmxClient.obtenerPersonasMorales(strToken);
	}
	
	private void guardarActualizarPersonaMoral() {	
		
		boolean existenDatosPersona = personaMoralDAO.existeRegistro(usuarioLogueado.getIdUsuarioLlaveCdmx());
		if(!existenDatosPersona) {	
			personaMoralDAO.guardar(personasMorales);
		} else {
			personaMoralDAO.actualizar(personasMorales);
		}
	}
	

	private void obtenerRolesUsuario(long idPersona, long idSistema, String token) {
		String strToken = "";
		if (token != null) {
			try {
				JSONObject tokenObj = new JSONObject(token);
				strToken = tokenObj.get("accessToken").toString();
			} catch (JSONException e) {
				LOGGER.error("Error al crear el json del token", e);
			}
		}
		RequestRolesDTO requestRolesDTO = new RequestRolesDTO(idPersona, idSistema);
		OAuth2CdmxClient oauthCdmxClient = new OAuth2CdmxClient();
		List<RolesUsuarioDTO> lstRolesUsuario = oauthCdmxClient.obtenerRolesUsuario(requestRolesDTO, strToken);
		if (BeanUtils.isNotNull(lstRolesUsuario)) {
			usuarioLogueado.setLstRoles(lstRolesUsuario);
//			LOGGER.debug("Roles: " + usuarioLogueado.getLstRoles().get(0).getRol());
		}
	}
	
	/**
	 * Método que crea la sesión del usuario con la información recuperada de Llave
	 * CDMX
	 * 
	 * @param token
	 */
	private void crearSesionUsuario(final String token) {
		// Invalidar cualquier sesión que tenga el usuario.
//		HttpSession oldSession = request.getSession(false);
//		if (oldSession != null) {
//			LOGGER.debug("Invalidar sesión en caso de que exista...");
//			oldSession.invalidate();
//		}
		// Generar nueva sesión de usuario
		HttpSession newSession = request.getSession(true);
		newSession.setAttribute(Constantes.TOKEN_SESSION, token);
		// Guardar token en cookie para Single SignOn
		Cookie message = new Cookie("idCDMX", token);
		((javax.servlet.http.HttpServletResponse) facesContext.getExternalContext().getResponse()).addCookie(message);
	}

	/**
	 * Método que realiza el cierre del aplicativo.
	 * 
	 * @return
	 */
	public String cerrarSesionUsuario() {
		OAuth2CdmxClient clienteOAuth2Cdmx = new OAuth2CdmxClient();
		try {
			if (tokenOauth != null) {
				JSONObject tokenObj = new JSONObject(tokenOauth);
				String strToken = tokenObj.get("accessToken").toString();
				clienteOAuth2Cdmx.cerrarSesionConLlaveCDMX(strToken);
			}
		} catch (URISyntaxException | NoSuchAlgorithmException e) {
			LOGGER.warn("Problemas el servicio logout de Llave MX: ", e);
		} catch (JSONException e) {
			LOGGER.error("Error al crear el json del token", e);
		}
		String respuesta = "";
		HttpSession cerrarSesion = request.getSession(false);
		cerrarSesion.invalidate();
		isAceptaManifiesto = false;
		ingresaDesdeLlave = true;
		respuesta = Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT;
		return respuesta;
	}
	
	/**
	 * Método que realiza el cierre del aplicativo.
	 * 
	 * @return
	 */
	public String cerrarSesionUsuarioFuncionario() {
		OAuth2CdmxClient clienteOAuth2Cdmx = new OAuth2CdmxClient();
		try {
			if (tokenOauth != null) {
				JSONObject tokenObj = new JSONObject(tokenOauth);
				String strToken = tokenObj.get("accessToken").toString();
				clienteOAuth2Cdmx.cerrarSesionConLlaveCDMX(strToken);
			}
		} catch (URISyntaxException | NoSuchAlgorithmException e) {
			LOGGER.warn("Problemas el servicio logout de Llave MX: ", e);
		} catch (JSONException e) {
			LOGGER.error("Error al crear el json del token", e);
		}
		String respuesta = "";
		HttpSession cerrarSesion = request.getSession(false);
		cerrarSesion.invalidate();
		isAceptaManifiesto = false;
		ingresaDesdeLlave = true;
		ingresaBackOffice = false;
		respuesta = Constantes.RETURN_INGRESAR_BACKOFFICE_PAGE + Constantes.JSF_REDIRECT;
		return respuesta;
	}
	
	/**
	 * Método que realiza el cierre del aplicativo cuando el proyecto cuenta con configuración de roles permitidos
	 * 
	 * @return
	 */
	public String cerrarSesionRolInvalido() {
		OAuth2CdmxClient clienteOAuth2Cdmx = new OAuth2CdmxClient();
		try {
			if (tokenOauth != null) {
				JSONObject tokenObj = new JSONObject(tokenOauth);
				String strToken = tokenObj.get("accessToken").toString();
				clienteOAuth2Cdmx.cerrarSesionConLlaveCDMX(strToken);
			}
		} catch (URISyntaxException | NoSuchAlgorithmException e) {
			LOGGER.warn("Problemas el servicio logout de Llave MX: ", e);
		} catch (JSONException e) {
			LOGGER.error("Error al crear el json del token", e);
		}
		String respuesta = "";
		HttpSession cerrarSesion = request.getSession(false);
		cerrarSesion.invalidate();
		isAceptaManifiesto = false;
		ingresaDesdeLlave = true;
		ingresaBackOffice = false;
		respuesta = Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT;
		return respuesta;
	}


	/**
	 * Método que inicializa la vista del Home.
	 * 
	 * @return
	 */
	public String incializarHome() {
		return Constantes.RETURN_HOME_PAGE + Constantes.JSF_REDIRECT;
	}

	/**
	 * Método que redirecciona a la vista de Index
	 * 
	 * @return
	 */
	public String redireccionarIndex() {
		return Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT;
	}

	public String redireccionaBandejaTramites() throws IOException {
//		return "/BandejaTramites.xhtml" + Constantes.JSF_REDIRECT;
		return Constantes.RETURN_BANDEJA_TRAMITES_PAGE + Constantes.JSF_REDIRECT;
	}

	/**
	 * Método que se utiliza para que se pinte el meta de noindex.
	 * 
	 * @return
	 */
	public String parametroMetaRobots() {
		return Environment.getAppProfile().compareTo("dev") == 0 ? "noindex" : "all";
	}

	/**
	 * Método que cierra las notificación del cintillo de vacunación
	 * 
	 * @param event
	 */
	public void onCloseNotificacionHeader(CloseEvent event) {
		mostrarNotificacion = false;
	}
	
	public String listenerClicImagenHeader() {
		return Constantes.RETURN_INDEX_PAGE + Constantes.JSF_REDIRECT;
	}

	/**
	 * @return the usuarioLogueado
	 */
	public UsuarioDTO getUsuarioLogueado() {
		return usuarioLogueado;
	}

	/**
	 * @return the mostrarNotificacion
	 */
	public boolean isMostrarNotificacion() {
		return mostrarNotificacion;
	}

	/**
	 * @param mostrarNotificacion the mostrarNotificacion to set
	 */
	public void setMostrarNotificacion(boolean mostrarNotificacion) {
		this.mostrarNotificacion = mostrarNotificacion;
	}

	/**
	 * @return the ingresaDesdeLlave
	 */
	public boolean isIngresaDesdeLlave() {
		return ingresaDesdeLlave;
	}

	/**
	 * @param ingresaDesdeLlave the ingresaDesdeLlave to set
	 */
	public void setIngresaDesdeLlave(boolean ingresaDesdeLlave) {
		this.ingresaDesdeLlave = ingresaDesdeLlave;
	}

	public boolean isRolSupervisor() {
		return rolSupervisor;
	}

	public void setRolSupervisor(boolean rolSupervisor) {
		this.rolSupervisor = rolSupervisor;
	}

	public boolean isRolOperador() {
		return rolOperador;
	}

	public void setRolOperador(boolean rolOperador) {
		this.rolOperador = rolOperador;
	}

	public boolean isAceptaManifiesto() {
		return isAceptaManifiesto;
	}

	public void setAceptaManifiesto(boolean isAceptaManifiesto) {
		this.isAceptaManifiesto = isAceptaManifiesto;
	}

	public boolean isIngresaBackOffice() {
		return ingresaBackOffice;
	}

	public void setIngresaBackOffice(boolean ingresaBackOffice) {
		this.ingresaBackOffice = ingresaBackOffice;
	}

	public boolean isRolAdministrador() {
		return rolAdministrador;
	}

	public void setRolAdministrador(boolean rolAdministrador) {
		this.rolAdministrador = rolAdministrador;
	}

	public boolean isRolAdministradorDatosTecnicos() {
		return rolAdministradorDatosTecnicos;
	}

	public void setRolAdministradorDatosTecnicos(boolean rolAdministradorDatosTecnicos) {
		this.rolAdministradorDatosTecnicos = rolAdministradorDatosTecnicos;
	}
	
	/**
	 * @return the rolConsulta
	 */
	public boolean isRolConsulta() {
		return rolConsulta;
	}

	/**
	 * @param rolConsulta the rolConsulta to set
	 */
	public void setRolConsulta(boolean rolConsulta) {
		this.rolConsulta = rolConsulta;
	}
	
	public boolean isHabilitarMenuPersonaMoral() {
		return habilitarMenuPersonaMoral;
	}

	public String getRedirectPage() {
		return redirectPage;
	}

	public void setRedirectPage(String redirectPage) {
		this.redirectPage = redirectPage;
	}
	
	public boolean isCiudadanoSeleccionado() {
		return ciudadanoSeleccionado;
	}

	public void setCiudadanoSeleccionado(boolean ciudadanoSeleccionado) {
		this.ciudadanoSeleccionado = ciudadanoSeleccionado;
	}

	public Long getPersonaMoralSeleccionada() {
		return personaMoralSeleccionada;
	}

	public void setPersonaMoralSeleccionada(Long personaMoralSeleccionada) {
		this.personaMoralSeleccionada = personaMoralSeleccionada;
	}

	public List<PersonaMoralDTO> getListaPersonasMorales() {
		return listaPersonasMorales;
	}

	public void setListaPersonasMorales(List<PersonaMoralDTO> listaPersonasMorales) {
		this.listaPersonasMorales = listaPersonasMorales;
	}
	
	public boolean isHaSeleccionadoPreviamente() {
	    return haSeleccionadoPreviamente;
	}

	public boolean isEsPrimeraVezEnVista() {
	    return esPrimeraVezEnVista;
	}

}

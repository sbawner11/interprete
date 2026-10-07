package mx.gob.atdt.interprete.formularios.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.Eager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.ComponenteDatosPersonalesLlaveDAO;
import mx.gob.atdt.interprete.dto.ComponenteAreaTextoDTO;
import mx.gob.atdt.interprete.dto.ComponenteDynamicTableDTO;
import mx.gob.atdt.interprete.dto.ComponenteCampoTextoDTO;
import mx.gob.atdt.interprete.dto.ComponenteCargaDocumentosDTO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxDTO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxUnicoDTO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosDomicilioDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesLlaveDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonaMoralDTO;
import mx.gob.atdt.interprete.dto.ComponenteFechaDTO;
import mx.gob.atdt.interprete.dto.ComponenteInformativoDTO;
import mx.gob.atdt.interprete.dto.ComponenteMenuDesplegableDTO;
import mx.gob.atdt.interprete.dto.ComponenteRadiobotonDTO;
import mx.gob.atdt.interprete.dto.ControlComponentesDTO;
import mx.gob.atdt.interprete.dto.DetElementosMenuDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.DetalleFormularioDAO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;
import mx.gob.atdt.interprete.facade.ComponenteDatosPersonalesLlaveFacade;
import mx.gob.atdt.interprete.facade.DatosDomicilioFacade;
import mx.gob.atdt.interprete.facade.SeccionesFormularioFacade;
import mx.gob.atdt.interprete.formulario.dao.FormularioDAO;
import mx.gob.atdt.interprete.formularios.bean.RegistrarFormularioBean;
import mx.gob.atdt.interprete.util.WebResources;

@Eager // Que se construya al iniciar el Wildfly
@Named
@ApplicationScoped
public class GenerarFormularioApplication implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1127178407593369411L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerarFormularioApplication.class);
	
	@Inject
	private EstructuraFormularioDAO estructuraFormularioDAO;
	
	@Inject
	private AuthenticatorBean authenticatorBean;
	
	@Inject
	private DetalleFormularioDAO detalleFormularioDAO;
	
	@Inject
	private RegistrarFormularioBean registrarFormularioBean;
	
	@Inject
	private FormularioDAO formularioDAO;
	
	@Inject
	private SeccionesFormularioFacade seccionesFormularioFacade;
	
	@Inject
	private DatosDomicilioFacade datosDomicilioFacade;
	
	@Inject
	private ComponenteDatosPersonalesLlaveFacade datosPersonalesLlaveFacade;
	
	@Inject
	private SeccionesProyectoBean seccionesProyectoBean;
		
	private Map<String, ControlComponentesDTO> mapControlComponentes;
	private Map<String, Object> mapRespuestas;
	
	private ArrayList<SeccionesFormularioDTO> lstSeccionesDTO;
	private List<ControlComponentesDTO> lstControlComponentesDTO;
	
	private ProyectoDTO proyectoDTO;		
		
	@PostConstruct
	public void inicializarComponente() {		
		LOGGER.info("------------------>> Se inicializa el formulario <<----------------");
		lstSeccionesDTO = new ArrayList<SeccionesFormularioDTO>();
		try {
			proyectoDTO = detalleFormularioDAO.consultaProyecto();
			if(proyectoDTO != null) {
				//Se valida si existen los campos de relacionados a Avisos, solo si el proyecto es configurado como aviso
				validarExistenciaCamposAvisos();
				
				consultarInformacionSecciones(proyectoDTO);
				
				/**Se realiza validación en campos de nuevos componentes**/
				revisarNuevosCamposComponentes();
				
				/**Precargar map de Control de componentes y map para respuestas de formulario**/
				if(estructuraFormularioDAO.existeTablaControl()) {
					consultaMapComponentesRespuestas();	
				}	
			} else {
				LOGGER.info("No ha sido sincronizado un proyecto.");
			}
					  
		} catch (Exception e) {
			LOGGER.error("Error al generar formulario:: ", e);
		}
	}
	
	/**
	 * 	Método que revisa si se agregan nuevos campos a componentes.
	 * 
	 * 	22/05/2025 Se agrega campo "Estado" al componente datos domicilio.
	 * 
	 * 
	 *  05/06/2025 Se agrega campo "Sexo" al componente DatosPersonalesLlave.
	 *
	 */
	private void revisarNuevosCamposComponentes() {
		if(BeanUtils.isNotNull(lstSeccionesDTO) && lstSeccionesDTO.size() > Constantes.INT_VALOR_CERO) {			
			for(SeccionesFormularioDTO seccionTemp : lstSeccionesDTO) {
				if (BeanUtils.isNotNull(seccionTemp.getLstSubsecciones())) {
					for(SubSeccionesFormularioDTO subseccionTemp : seccionTemp.getLstSubsecciones()) {				
						if (BeanUtils.isNotNull(subseccionTemp.getLstComponentes())) {
							for(ComponenteDTO componenteTemp : subseccionTemp.getLstComponentes()) {
								/**
								 * 22/05/2025, Se integra al componente datos de domicilio nuevo campo para registrar el "estado", por lo que se tiene que 
								 * generar este campo en en la tabla "control_componentes" para que pueda ser reservada una nueva columna para el nuevo 
								 * campo "Estado", además de agregar la nueva columna en la tabla dinamica de sección "seccion_formulario" para que pueda
								 * guardarse el nuevo dato "Estado".
								 */
								if(componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente().intValue() == Constantes.ID_COMPONENTE_DATOS_DOMICILIO) {
									try {
										ComponenteDatosDomicilioDTO datosDomicilio = detalleFormularioDAO.consultarDetalleDomicilio(componenteTemp);
										datosDomicilioFacade.actualizarColumnasComponente(datosDomicilio);
									} catch (Exception e) {
										LOGGER.error("OCurrió un problema al consultar el componente municipio para agregar columna Estado: ", e);
									}
								}
								/**
								 * 05/06/2025, Se integra al componente datos personales llave nuevo campo para registrar el "sexo", por lo que se tiene que 
								 * generar este campo en en la tabla "control_componentes" para que pueda ser reservada una nueva columna para el nuevo 
								 * campo "Sexo", además de agregar la nueva columna en la tabla dinamica de sección "seccion_formulario" para que pueda
								 * guardarse el nuevo dato "Sexo".
								 */
								if(componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente().intValue() == Constantes.ID_COMPONENTE_DATOS_PERSONALES_CON_LLAVE) {
									try {
										ComponenteDatosPersonalesLlaveDTO datosPersonalesLlave = detalleFormularioDAO.consultarDetalleDatosPersonalesConLlave(componenteTemp);
										datosPersonalesLlaveFacade.actualizarColumnasComponente(datosPersonalesLlave);
									} catch (Exception e) {
										LOGGER.error("OCurrió un problema al consultar el componente datos personales con llave para agregar columna sexo: ", e);
									}
								}
							}
						}						
					}
				}
			}						
		}
	}
	
	/**
	 * Método que inicializa la vista para iniciar captura de información en el formulario.
	 * @return
	 */
	public String iniciarCapturaFormulario() {		
		String redirect = Constantes.RETURN_SAME_PAGE;
		
		if(permiteCapturaTramite()) {
			/**Se inicializa bean de tipo Sesion para la captura de información del formulario**/
			if(BeanUtils.isNotNull(lstSeccionesDTO) && lstSeccionesDTO.size() > Constantes.INT_VALOR_CERO) {
				redirect = registrarFormularioBean.inicializarCapturaFormulario(proyectoDTO, generarListaSecciones(), mapControlComponentes, generarMapRespuestas());
			} else {
				WebResources.addValidationMessage("msj_no_formulario", false);
			}		
		} else {
			WebResources.addValidationMessage("msj_no_registro_formulario", false);
		}
			
		return redirect;
	}
	
	/**
	 * Método auxiliar que valida si el usuario actual puede realizar la captura de un nuevo trámite.
	 * @return
	 */
	public boolean permiteCapturaTramite() {
		boolean permiteContinuar = true;
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
								permiteContinuar = false;
								break;	
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
		return permiteContinuar;
	}
	
	/**
	 * Método auxiliar que genera una nueva lista con las Secciones, Subsecciones y componentes del Formulario, para su captura
	 * en el formulario.
	 * 
	 * @return
	 */
	public List<SeccionesFormularioDTO> generarListaSecciones() {
		ArrayList<SeccionesFormularioDTO> lstSecciones =  new ArrayList<SeccionesFormularioDTO>();
		
		for(SeccionesFormularioDTO seccionTemp : lstSeccionesDTO) {
			if (BeanUtils.isNotNull(seccionTemp.getLstSubsecciones())) {
				
				//Se genera nueva sección				
				SeccionesFormularioDTO newSeccionFormulario = new SeccionesFormularioDTO(seccionTemp.getIdSeccionFormulario(),
						new ProyectoDTO(seccionTemp.getProyectoDTO().getIdProyecto()), seccionTemp.getNombreSeccion(), seccionTemp.getOrden());
				
				for(SubSeccionesFormularioDTO subseccionTemp : seccionTemp.getLstSubsecciones()) {
					
					//Se genera nueva subsección
					SubSeccionesFormularioDTO newSubseccionFormulario = new SubSeccionesFormularioDTO(subseccionTemp.getIdSubseccionFormulario(), 
							subseccionTemp.getSeccionesFormularioDTO().getIdSeccionFormulario(), subseccionTemp.getNombreSubseccion(), 
							subseccionTemp.getOrden(), subseccionTemp.isActivo(), subseccionTemp.isSeccionSincronizada());
					
					for(ComponenteDTO componenteTemp : subseccionTemp.getLstComponentes()) {						
						if (BeanUtils.isNotNull(componenteTemp)) {
							//Se verifica el tipo de componente
							switch (componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente()) {
									
							case Constantes.ID_COMPONENTE_CAMPO_TEXTO:
								ComponenteCampoTextoDTO tempCt = (ComponenteCampoTextoDTO) componenteTemp;
								
								//Se genera nuevo componente de Campo de Texto
								ComponenteCampoTextoDTO newCampoTexto = 
										new ComponenteCampoTextoDTO(tempCt.getIdComponenteCampoTexto(), 
												tempCt.getIdComponente(), tempCt.getCatTipoComponenteDTO().getIdTipoComponente(), 
												tempCt.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(), 
												tempCt.getOrden(), tempCt.isRequerido(), tempCt.isTooltip(), tempCt.getDescripcionTooltip(), 
												tempCt.getTituloCampo(), tempCt.isActivo(), tempCt.getFechaCreacion(), 
												tempCt.getFechaUltimaActualizacion(), tempCt.isSeccionSincronizada(), 
												tempCt.isAlfanumerico(), tempCt.isNumerico(), tempCt.isHabilitaTextoInterior(),
												tempCt.getTextoInterior(), tempCt.getCatOrigenLlenadoDTO() != null ? tempCt.getCatOrigenLlenadoDTO().getIdOrigenLlenado() : null,
												tempCt.isValidadores(), tempCt.getCatValidadoresDTO() != null ? tempCt.getCatValidadoresDTO().getIdValidador() : null, 
												tempCt.getValorMinimo(), tempCt.getValorMaximo(),
												tempCt.isPermiteDecimales() );

								//Se setea el campo para respuesta
								newCampoTexto.setRespuestaComponente(tempCt.getRespuestaComponente());
								newSubseccionFormulario.getLstComponentes().add(newCampoTexto);		
								break;

							case Constantes.ID_COMPONENTE_FECHA:
								ComponenteFechaDTO tempCF = (ComponenteFechaDTO) componenteTemp;
								
								//Se genera nuevo componente Fecha
								ComponenteFechaDTO newFechaDTO =
										new ComponenteFechaDTO(tempCF.getIdComponenteFecha(),
												tempCF.getIdComponente(), tempCF.getCatTipoComponenteDTO().getIdTipoComponente(),
												tempCF.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(),
												tempCF.getOrden(), tempCF.isRequerido(), tempCF.isTooltip(), tempCF.getDescripcionTooltip(),
												tempCF.getTituloCampo(), tempCF.isActivo(), tempCF.getFechaCreacion(), tempCF.getFechaUltimaActualizacion(),
												tempCF.isSeccionSincronizada(), tempCF.isDiasInhabiles(), tempCF.isFechaMenorHoy(), tempCF.isFechaMayorHoy(),
												tempCF.getFechaInicio(), tempCF.getFechaLimite());
								//Se setea el campo para respuesta
								newFechaDTO.setRespuestaComponente(tempCF.getRespuestaComponente());
								
								newSubseccionFormulario.getLstComponentes().add(newFechaDTO);					
								break;
								
							case Constantes.ID_COMPONENTE_CHECKBOX_UNICO:
								ComponenteCheckboxUnicoDTO tempCU = (ComponenteCheckboxUnicoDTO) componenteTemp;
								
								//Se genera nuevo componente CheckUnico
								ComponenteCheckboxUnicoDTO newCheckUnicoDTO = 
										new ComponenteCheckboxUnicoDTO(tempCU.getIdComponenteCheckboxUnico(), tempCU.getTexto(),
												tempCU.getIdComponente(), tempCU.getCatTipoComponenteDTO().getIdTipoComponente(),
												tempCU.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(), tempCU.getOrden(),
												tempCU.isRequerido(), tempCU.isTooltip(), tempCU.getDescripcionTooltip(), tempCU.getTituloCampo(),
												tempCU.isActivo(), tempCU.getFechaCreacion(), tempCU.getFechaUltimaActualizacion(),
												tempCU.isSeccionSincronizada());
								//Se setea el campo para respuesta
								newCheckUnicoDTO.setRespuestaComponente(tempCU.getRespuestaComponente());
								
								newSubseccionFormulario.getLstComponentes().add(newCheckUnicoDTO);						
								break;		
								
							case Constantes.ID_COMPONENTE_CHECKBOX_GRUPO:
								ComponenteCheckboxDTO tempCG = (ComponenteCheckboxDTO) componenteTemp;
								
								//Se genera nuevo componente CheckBox
								ComponenteCheckboxDTO newCheckBox = 
										new ComponenteCheckboxDTO(tempCG.getIdComponenteCheckbox(),
												tempCG.isHabilitaTodosNinguno(),
												tempCG.getIdComponente(), tempCG.getCatTipoComponenteDTO().getIdTipoComponente(),
												tempCG.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(), tempCG.getOrden(),
												tempCG.isRequerido(), tempCG.isTooltip(), tempCG.getDescripcionTooltip(),
												tempCG.getTituloCampo(), tempCG.isActivo(), tempCG.getFechaCreacion(),
												tempCG.getFechaUltimaActualizacion(), tempCG.isSeccionSincronizada());
								newCheckBox.setDetElementosCheckboxDTO(tempCG.getDetElementosCheckboxDTO());
								//Se setea el campo para respuesta
								newCheckBox.setRespuestaComponente(tempCG.getRespuestaComponente());
								
								newSubseccionFormulario.getLstComponentes().add(newCheckBox);	
								break;		
								
							case Constantes.ID_COMPONENTE_RADIO_BOTON:
								ComponenteRadiobotonDTO tempRB = (ComponenteRadiobotonDTO) componenteTemp;
								
								//Se genera nuevo componente Radioboton
								ComponenteRadiobotonDTO newComponenteRadioBoton = new ComponenteRadiobotonDTO(tempRB.getIdComponenteRadioboton(), 
										tempRB.isHabilitaOpcionOtro(), tempRB.getTextoInteriorOtro(), tempRB.getIdComponente(), tempRB.getCatTipoComponenteDTO().getIdTipoComponente(),
										tempRB.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(), tempRB.getOrden(), tempRB.isRequerido(), tempRB.isTooltip(),
										tempRB.getDescripcionTooltip(), tempRB.getTituloCampo(), tempRB.isActivo(), tempRB.getFechaCreacion(),
										tempRB.getFechaUltimaActualizacion(), tempRB.isSeccionSincronizada());
								newComponenteRadioBoton.setDetElementosRadiobotonsDTO(tempRB.getDetElementosRadiobotonsDTO());
								//Se setean campos para respuesta
								newComponenteRadioBoton.setOpcionRespuesta(tempRB.getOpcionRespuesta());
								newComponenteRadioBoton.setIsOtro(tempRB.getIsOtro());
								newComponenteRadioBoton.setEspecifiqueOtro(tempRB.getEspecifiqueOtro());
								
								newSubseccionFormulario.getLstComponentes().add(newComponenteRadioBoton);					
								break;		
								
							case Constantes.ID_COMPONENTE_DATOS_DOMICILIO:
								ComponenteDatosDomicilioDTO tempDD = (ComponenteDatosDomicilioDTO) componenteTemp;
								
								//Se genera nuevo componente Datos Domicilio
								ComponenteDatosDomicilioDTO newDatosDomicilio = 
										new ComponenteDatosDomicilioDTO(tempDD.getIdComponenteDatosDomicilio(),
												tempDD.getIdComponente(), tempDD.getCatTipoComponenteDTO().getIdTipoComponente(),
												tempDD.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(), tempDD.getOrden(),
												tempDD.isRequerido(), tempDD.isTooltip(), tempDD.getDescripcionTooltip(),
												tempDD.getTituloCampo(), tempDD.isActivo(), tempDD.getFechaCreacion(),
												tempDD.getFechaUltimaActualizacion(), tempDD.isSeccionSincronizada(),
												tempDD.isHabilitaCalle(), tempDD.isCalleObligatorio(),tempDD.getTextoInteriorCalle(),
												tempDD.isHabilitaNumeroExterior(), tempDD.isNumeroExteriorObligatorio(), tempDD.getTextoInteriorNumeroExterior(),
												tempDD.isHabilitaNumeroInterior(), tempDD.isNumeroInteriorObligatorio(), tempDD.getTextoInteriorNumeroInterior(),
												tempDD.isHabilitaCodigoPostal(), tempDD.isCodigoPostalObligatorio(), tempDD.getTextoInteriorCodigoPostal(),
												tempDD.isHabilitaColonia(), tempDD.isColoniaObligatorio(), tempDD.getTextoInteriorColonia(),
												tempDD.isHabilitaAlcaldia(), tempDD.isAlcaldiaObligatorio(), tempDD.getTextoInteriorAlcaldia(),
												tempDD.isHabilitaEstado(), tempDD.isEstadoObligatorio(), tempDD.getTextoInteriorEstado());								
								//Se setean campos para respuesta
								newDatosDomicilio.setCalle(tempDD.getCalle());
								newDatosDomicilio.setNumeroExterior(tempDD.getNumeroExterior());
								newDatosDomicilio.setNumeroInterior(tempDD.getNumeroInterior());
								newDatosDomicilio.setCodigoPostal(tempDD.getCodigoPostal());
								newDatosDomicilio.setColonia(tempDD.getColonia());
								newDatosDomicilio.setAlcaldia(tempDD.getAlcaldia());
								newDatosDomicilio.setEstado(tempDD.getEstado());
								
								newSubseccionFormulario.getLstComponentes().add(newDatosDomicilio);					
								break;	
							
							case Constantes.ID_COMPONENTE_CARGA_DOCUMENTOS:
								ComponenteCargaDocumentosDTO tempCD = (ComponenteCargaDocumentosDTO) componenteTemp;
								
								//Se genera nuevo componente Carga de documentos
								ComponenteCargaDocumentosDTO newComponenteCargaDocumentos = new ComponenteCargaDocumentosDTO(tempCD.getIdComponenteCarga(),
										tempCD.isDocumentoUnico(), tempCD.getCatTamanioArchivosDTO().getIdTamanioArchivo(), tempCD.getIdComponente(), tempCD.getCatTipoComponenteDTO().getIdTipoComponente(),
										tempCD.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(), tempCD.getOrden(), tempCD.isRequerido(), tempCD.isTooltip(), 
										tempCD.getDescripcionTooltip(), tempCD.getTituloCampo(), tempCD.isActivo(), tempCD.getFechaCreacion(), tempCD.getFechaUltimaActualizacion(), tempCD.isSeccionSincronizada());
								newComponenteCargaDocumentos.setCrcCargaDocumentosTipoArchivoDTO(tempCD.getCrcCargaDocumentosTipoArchivoDTO());
								//Se setean campos para respuesta
								newComponenteCargaDocumentos.setRespuestaComponente(tempCD.getRespuestaComponente());
								
								newSubseccionFormulario.getLstComponentes().add(newComponenteCargaDocumentos);				
								break;	
								
							case Constantes.ID_COMPONENTE_INFORMATIVO:
								ComponenteInformativoDTO tempCI = (ComponenteInformativoDTO) componenteTemp;
								
								//Se genera nuevo componente Informativo
								ComponenteInformativoDTO newComponenteInformativo = new ComponenteInformativoDTO(tempCI.getIdComponenteInformativo(), 
										tempCI.getIdComponente(), tempCI.getCatTipoComponenteDTO().getIdTipoComponente(), tempCI.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(), 
										tempCI.getOrden(), tempCI.isRequerido(), tempCI.isTooltip(), tempCI.getDescripcionTooltip(), tempCI.getTituloCampo(), tempCI.isActivo(), 
										tempCI.getFechaCreacion(), tempCI.getFechaUltimaActualizacion(), tempCI.isSeccionSincronizada(), tempCI.getTextoInformativo());
								
								newSubseccionFormulario.getLstComponentes().add(newComponenteInformativo);					
								break;	
								
							case Constantes.ID_COMPONENTE_DATOS_PERSONALES_SIN_LLAVE:
								ComponenteDatosPersonalesDTO tempSL = (ComponenteDatosPersonalesDTO) componenteTemp;
								
								//Se genera nuevo componente de Datos Personales
								ComponenteDatosPersonalesDTO newComponenteDatosPersonalesSinLlave = new ComponenteDatosPersonalesDTO(tempSL.getIdComponenteDatosPersonales(), 
										tempSL.getIdComponente(), tempSL.getCatTipoComponenteDTO().getIdTipoComponente(), tempSL.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(),
										tempSL.getOrden(), tempSL.isRequerido(), tempSL.isTooltip(), tempSL.getDescripcionTooltip(), tempSL.getTituloCampo(), tempSL.isActivo(), 
										tempSL.getFechaCreacion(), tempSL.getFechaUltimaActualizacion(), tempSL.isSeccionSincronizada(), tempSL.isHabilitaCurp(), tempSL.isCurpObligatorio(), 
										tempSL.getTextoInteriorCurp(), tempSL.isHabilitaNombre(), tempSL.isNombreObligatorio(), tempSL.getTextoInteriorNombre(), 
										tempSL.isHabilitaPrimerApellido(), tempSL.isPrimerApellidoObligatorio(), tempSL.getTextoInteriorPrimerApellido(), 
										tempSL.isHabilitaSegundoApellido(), tempSL.isSegundoApellidoObligatorio(), tempSL.getTextoInteriorSegundoApellido(), 
										tempSL.isHabilitaTelefono(), tempSL.isTelefonoObligatorio(), tempSL.getTextoInteriorTelefono(), 
										tempSL.isHabilitaCorreoElectronico(), tempSL.isCorreoElectronicoObligatorio(), tempSL.getTextoInteriorCorreoElectronico(), tempSL.isHabilitaRenapo());
								//Se setean campos para respuesta
								newComponenteDatosPersonalesSinLlave.setCurp(tempSL.getCurp());
								newComponenteDatosPersonalesSinLlave.setNombre(tempSL.getNombre());
								newComponenteDatosPersonalesSinLlave.setpApellido(tempSL.getpApellido());
								newComponenteDatosPersonalesSinLlave.setsApellido(tempSL.getsApellido());
								newComponenteDatosPersonalesSinLlave.setTelefono(tempSL.getTelefono());
								newComponenteDatosPersonalesSinLlave.setEmail(tempSL.getEmail());
								
								newSubseccionFormulario.getLstComponentes().add(newComponenteDatosPersonalesSinLlave);				
								break;	
								
							case Constantes.ID_COMPONENTE_DATOS_PERSONALES_CON_LLAVE:
								ComponenteDatosPersonalesLlaveDTO tempCCL = (ComponenteDatosPersonalesLlaveDTO) componenteTemp;
								
								//Se genera nuevo componente de Datos Personales con llave
								ComponenteDatosPersonalesLlaveDTO newComponenteDatosPersonalesLlave = new ComponenteDatosPersonalesLlaveDTO(tempCCL.getIdComponenteDatosPersonales(),
										tempCCL.getIdComponente(), tempCCL.getCatTipoComponenteDTO().getIdTipoComponente(),
										tempCCL.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(), tempCCL.getOrden(),
										tempCCL.isRequerido(), tempCCL.isTooltip(), tempCCL.getDescripcionTooltip(), tempCCL.getTituloCampo(), tempCCL.isActivo(),
										tempCCL.getFechaCreacion(), tempCCL.getFechaUltimaActualizacion(), tempCCL.isSeccionSincronizada(), tempCCL.isHabilitaCurp(), 
										tempCCL.isHabilitaNombre(), tempCCL.isHabilitaPrimerApellido(), tempCCL.isHabilitaSegundoApellido(), tempCCL.isHabilitaTelefono(),
										tempCCL.isHabilitaCorreoElectronico(), tempCCL.isHabilitaFechaNacimiento(), tempCCL.isHabilitaSexo());
								//Se setean campos para respuesta										
								newComponenteDatosPersonalesLlave.setCurp(tempCCL.getCurp());
								newComponenteDatosPersonalesLlave.setNombre(tempCCL.getNombre());
								newComponenteDatosPersonalesLlave.setPrimerApellido(tempCCL.getPrimerApellido());
								newComponenteDatosPersonalesLlave.setSegundoApellido(tempCCL.getSegundoApellido());
								newComponenteDatosPersonalesLlave.setTelefono(tempCCL.getTelefono());
								newComponenteDatosPersonalesLlave.setCorreoElectronico(tempCCL.getCorreoElectronico());
								newComponenteDatosPersonalesLlave.setFechaNacimiento(tempCCL.getFechaNacimiento());	
								newComponenteDatosPersonalesLlave.setSexo(tempCCL.getSexo());
								
								newSubseccionFormulario.getLstComponentes().add(newComponenteDatosPersonalesLlave);				
								break;	

							case Constantes.ID_COMPONENTE_DATOS_PERSONA_MORAL:
								ComponenteDatosPersonaMoralDTO tempDPM = (ComponenteDatosPersonaMoralDTO) componenteTemp;

								//Se genera nuevo componente de Datos Personales con llave
								ComponenteDatosPersonaMoralDTO newComponenteDatosPersonaMoral = new ComponenteDatosPersonaMoralDTO(tempDPM.getIdComponenteDatosPersonaMoral(),
										tempDPM.getIdComponente(), tempDPM.getCatTipoComponenteDTO().getIdTipoComponente(),
										tempDPM.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(), tempDPM.getOrden(),
										tempDPM.isRequerido(), tempDPM.isTooltip(), tempDPM.getDescripcionTooltip(), tempDPM.getTituloCampo(), tempDPM.isActivo(),
										tempDPM.getFechaCreacion(), tempDPM.getFechaUltimaActualizacion(), tempDPM.isSeccionSincronizada(), tempDPM.isHabilitaRfc(), 
										tempDPM.isHabilitaPersonaMoral(), tempDPM.isHabilitaFechaVigencia());
								//Se setean campos para respuesta										
								newComponenteDatosPersonaMoral.setRfc(tempDPM.getRfc());
								newComponenteDatosPersonaMoral.setRazonSocial(tempDPM.getRazonSocial());
								newComponenteDatosPersonaMoral.setVigenciaCertificado(tempDPM.getVigenciaCertificado());	
								
								newSubseccionFormulario.getLstComponentes().add(newComponenteDatosPersonaMoral);				
								break;	
								
							case Constantes.ID_COMPONENTE_MENU_DESPLEGABLE:
								ComponenteMenuDesplegableDTO tempMD = (ComponenteMenuDesplegableDTO) componenteTemp;		
								
								Long idTipoOrdenamiento = 
										tempMD.getCatTipoOrdenamientoDTO() != null ? 
												tempMD.getCatTipoOrdenamientoDTO().getIdTipoOrdenamiento() : null;
								
								//Se genera nuevo componente MenuDesplegable
								ComponenteMenuDesplegableDTO newMenuDTO = 
										new ComponenteMenuDesplegableDTO(tempMD.getIdComponenteMenuDesplegable(),
												tempMD.getCatOrigenLlenadoDTO().getIdOrigenLlenado(), tempMD.isHabilitaTextoInteriorOtro(),
												tempMD.getTextoInteriorOtro(), tempMD.getIdComponente(), tempMD.getCatTipoComponenteDTO().getIdTipoComponente(),
												tempMD.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(), tempMD.getOrden(), tempMD.isRequerido(),
												tempMD.isTooltip(), tempMD.getDescripcionTooltip(), tempMD.getTituloCampo(), tempMD.isActivo(),
												tempMD.getFechaCreacion(), tempMD.getFechaUltimaActualizacion(), tempMD.isSeccionSincronizada(),
												idTipoOrdenamiento);
								// Se verifica que el componente contenga elementos configurados
								if(BeanUtils.isNotNull(tempMD.getDetElementosMenuDTO()) && 
										BeanUtils.isNotEmpty(tempMD.getDetElementosMenuDTO())) {
									
									// Se valida que sea solamente elementos activos
									List<DetElementosMenuDTO> elementos = tempMD.getDetElementosMenuDTO().stream().filter(e -> e.isActivo()).map(e -> {
						 				DetElementosMenuDTO detDTO = new DetElementosMenuDTO();
										detDTO.setIdElementoMenu(e.getIdElementoMenu());
										detDTO.setComponenteMenuDesplegableDTO(newMenuDTO);
										detDTO.setDescripcionElemento(e.getDescripcionElemento());
										detDTO.setActivo(e.isActivo());
										return detDTO;
									}).collect(Collectors.toList());
									
									//elementos.sort(Comparator.comparing(DetElementosMenuDTO::getIdElementoMenu));

									newMenuDTO.setDetElementosMenuDTO(elementos);									
								}

								//Se setea el campo para respuesta
								newMenuDTO.setRespuestaComponente(tempMD.getRespuestaComponente());
								
								newSubseccionFormulario.getLstComponentes().add(newMenuDTO);					
								break;	
								
							case Constantes.ID_COMPONENTE_AREA_TEXTO:
								ComponenteAreaTextoDTO tempAt = (ComponenteAreaTextoDTO) componenteTemp;
								
								//Se genera nuevo componente de Area de Texto
								ComponenteAreaTextoDTO newAreaTexto = 
										new ComponenteAreaTextoDTO(tempAt.getIdComponenteAreaTexto(), 
												tempAt.getIdComponente(), tempAt.getCatTipoComponenteDTO().getIdTipoComponente(), 
												tempAt.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(), 
												tempAt.getOrden(), tempAt.isRequerido(), tempAt.isTooltip(), tempAt.getDescripcionTooltip(), 
												tempAt.getTituloCampo(), tempAt.isActivo(), tempAt.getFechaCreacion(), 
												tempAt.getFechaUltimaActualizacion(), tempAt.isSeccionSincronizada(), 
												tempAt.isHabilitaTextoInterior(),
												tempAt.getTextoInterior(), 
												tempAt.getCatOrigenLlenadoDTO() != null ? tempAt.getCatOrigenLlenadoDTO().getIdOrigenLlenado() : null,
												tempAt.getLineasAltura());
								//Se setea el campo para respuesta
								newAreaTexto.setRespuestaComponente(tempAt.getRespuestaComponente());
								
								newSubseccionFormulario.getLstComponentes().add(newAreaTexto);		
								break;
								
							case Constantes.ID_COMPONENTE_TABLA:
							    ComponenteDynamicTableDTO tempTD = (ComponenteDynamicTableDTO) componenteTemp;
							    
							    ComponenteDynamicTableDTO newTablaDinamica = new ComponenteDynamicTableDTO(
							        tempTD.getIdComponente(),
							        tempTD.getCatTipoComponenteDTO().getIdTipoComponente(),
							        tempTD.getSubSeccionesFormularioDTO().getIdSubseccionFormulario(),
							        tempTD.getOrden(),
							        tempTD.isRequerido(),
							        tempTD.isTooltip(),
							        tempTD.getDescripcionTooltip(),
							        tempTD.getTituloCampo(),
							        tempTD.isActivo(),
							        tempTD.getFechaCreacion(),
							        tempTD.getFechaUltimaActualizacion(),
							        tempTD.isSeccionSincronizada()
							    );
							    // propiedades específicas de tabla dinámica
							    newTablaDinamica.setTituloTabla(tempTD.getTituloTabla());
							    newTablaDinamica.setPermiteAgregarFilas(tempTD.isPermiteAgregarFilas());
							    newTablaDinamica.setMinimoFilas(tempTD.getMinimoFilas());
							    newTablaDinamica.setMaximoFilas(tempTD.getMaximoFilas());
							    newTablaDinamica.setTamanioPagina(tempTD.getTamanioPagina());
							    newTablaDinamica.setColumnas(tempTD.getColumnas());
							    newTablaDinamica.setRespuestaComponente(tempTD.getRespuestaComponente());
							    
							    newSubseccionFormulario.getLstComponentes().add(newTablaDinamica);
							    break;

							default:
								LOGGER.warn("Tipo de componente no configurado :: " + componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente());
								break;
							}
						}
					}						
					//Se agrega subsección a lista de las secciones
					newSeccionFormulario.getLstSubsecciones().add(newSubseccionFormulario);
				}
				//Se agrega sección a nueva lista 
				lstSecciones.add(newSeccionFormulario);
			} 
		}			
		
		return lstSecciones;
	}	
	
	/**
	 * Método auxiliar que genera Map para el guardado de respuestas del formulario
	 * @return
	 */
	public Map<String, Object> generarMapRespuestas() {
		Map<String, Object> mapRespuestasTmp = new HashMap<>();
		for(ControlComponentesDTO controlComponente: lstControlComponentesDTO) {			
			/**Se genera Map de respuestas para inciar el formulario**/
			mapRespuestasTmp.put(controlComponente.getNombreColumna(), Constantes.EMPTY_STRING);
		}		
		return mapRespuestasTmp;
	}
	
	/**
	 * Método auxiliar que obtiene la información de Secciones, con sus subsecciones y componentes.
	 * @param proyectoDTO
	 * @throws Exception 
	 */
	private void consultarInformacionSecciones(ProyectoDTO proyectoDTO) throws Exception {		
		if(BeanUtils.isNotNull(proyectoDTO)) {
			lstSeccionesDTO = detalleFormularioDAO.consultarSeccionesPorProyecto(proyectoDTO);	
			
			if(BeanUtils.isNotNull(lstSeccionesDTO)) {
				for(SeccionesFormularioDTO seccionTemp : lstSeccionesDTO) {
					List<SubSeccionesFormularioDTO> lstSubsecciones = new ArrayList<SubSeccionesFormularioDTO>();
					lstSubsecciones = detalleFormularioDAO.consultarSubseccionesPorSeccion(seccionTemp);
					if (BeanUtils.isNotNull(lstSubsecciones)) {
						seccionTemp.setLstSubsecciones(lstSubsecciones);
						
						for(SubSeccionesFormularioDTO subseccionTemp : seccionTemp.getLstSubsecciones()) {
							List<ComponenteDTO> lstComponente = new ArrayList<ComponenteDTO>();
							lstComponente = detalleFormularioDAO.consultarComponentesPorSubseccion(subseccionTemp);
					
							if (BeanUtils.isNotNull(lstComponente)) {
								subseccionTemp.setLstComponentes(consultarDetalleComponente(lstComponente));
							} else {
								subseccionTemp.setLstComponentes(new ArrayList<ComponenteDTO>());
							}						
						}
					} else {
						seccionTemp.setLstSubsecciones(new ArrayList<SubSeccionesFormularioDTO>());
					}
				}				
			}			
		}
	}
	
	/**
	 * Método que consulta el detalle de cada componente.
	 * @param lstComponente
	 * @return
	 * @throws Exception
	 */
	private List<ComponenteDTO> consultarDetalleComponente(List<ComponenteDTO> lstComponente) throws Exception {
		List<ComponenteDTO> lstDetalleComponente = new ArrayList<ComponenteDTO>();
		
		for(ComponenteDTO componenteTemp : lstComponente) {			
			
			//Se verifica el tipo de componente
			switch (componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente()) {
					
			case Constantes.ID_COMPONENTE_CAMPO_TEXTO:
				lstDetalleComponente.add(detalleFormularioDAO.consultarDetalleCampoTexto(componenteTemp));		
				break;

			case Constantes.ID_COMPONENTE_FECHA:
				lstDetalleComponente.add(detalleFormularioDAO.consultarDetalleFecha(componenteTemp));				
				break;
				
			case Constantes.ID_COMPONENTE_CHECKBOX_UNICO:
				lstDetalleComponente.add(detalleFormularioDAO.consultarDetalleCheckUnico(componenteTemp));				
				break;		
				
			case Constantes.ID_COMPONENTE_CHECKBOX_GRUPO:
				ComponenteCheckboxDTO componenteCheckGrupoTmp = detalleFormularioDAO.consultarDetalleCheckGrupo(componenteTemp);
				componenteCheckGrupoTmp.setDetElementosCheckboxDTO(detalleFormularioDAO.consultarElementosCheckGrupo(componenteCheckGrupoTmp));
				lstDetalleComponente.add(componenteCheckGrupoTmp);		
				break;		
				
			case Constantes.ID_COMPONENTE_RADIO_BOTON:
				ComponenteRadiobotonDTO componenteRadioBotonTmp = detalleFormularioDAO.consultarDetalleRadioBoton(componenteTemp);
				componenteRadioBotonTmp.setDetElementosRadiobotonsDTO(detalleFormularioDAO.consultarElementosRadioBoton(componenteRadioBotonTmp));
				lstDetalleComponente.add(componenteRadioBotonTmp);						
				break;		
				
			case Constantes.ID_COMPONENTE_DATOS_DOMICILIO:
				lstDetalleComponente.add(detalleFormularioDAO.consultarDetalleDomicilio(componenteTemp));				
				break;	
			
			case Constantes.ID_COMPONENTE_CARGA_DOCUMENTOS:
				ComponenteCargaDocumentosDTO componenteCargaDocumentos = detalleFormularioDAO.consultarDetalleCargaDocumentos(componenteTemp);
				componenteCargaDocumentos.setCrcCargaDocumentosTipoArchivoDTO(detalleFormularioDAO.consultarTiposArchivoComponenteCargaDocumentos(componenteCargaDocumentos));
				lstDetalleComponente.add(componenteCargaDocumentos);				
				break;	
				
			case Constantes.ID_COMPONENTE_INFORMATIVO:
				lstDetalleComponente.add(detalleFormularioDAO.consultarDetalleInformativo(componenteTemp));				
				break;	
				
			case Constantes.ID_COMPONENTE_DATOS_PERSONALES_SIN_LLAVE:
				lstDetalleComponente.add(detalleFormularioDAO.consultarDetalleDatosPersonales(componenteTemp));				
				break;	
				
			case Constantes.ID_COMPONENTE_DATOS_PERSONALES_CON_LLAVE:
				lstDetalleComponente.add(detalleFormularioDAO.consultarDetalleDatosPersonalesConLlave(componenteTemp));				
				break;	

			case Constantes.ID_COMPONENTE_DATOS_PERSONA_MORAL:
				lstDetalleComponente.add(detalleFormularioDAO.consultarDetalleDatosPersonaMoral(componenteTemp));				
				break;	

			case Constantes.ID_COMPONENTE_MENU_DESPLEGABLE:
				ComponenteMenuDesplegableDTO menuDesplegable = detalleFormularioDAO.consultarDetalleMenuDesplegable(componenteTemp);
				menuDesplegable.setDetElementosMenuDTO(detalleFormularioDAO.consultarElementosElementosMenuDesplegable(menuDesplegable));
				lstDetalleComponente.add(menuDesplegable);					
				break;	
				
			case Constantes.ID_COMPONENTE_AREA_TEXTO:
				lstDetalleComponente.add(detalleFormularioDAO.consultarDetalleAreaTexto(componenteTemp));		
				break;
				
			case Constantes.ID_COMPONENTE_TABLA:
			    lstDetalleComponente.add(detalleFormularioDAO.consultarDetalleTablaDinamica(componenteTemp));
			    break;
				
			default:
				LOGGER.warn("Tipo de componente no configurado :: " + componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente());
				break;
			}
		}
		
		return lstDetalleComponente;
	}	
		
	/**
	 * Método que realiza la carga en hashMap de la información de Tabla y Campo en la que deberá registrarse la información
	 * de un componente del formulario de captura, en el mismo ciclo se precarga el Map de respuestas para el formulario.
	 * 
	 * @throws Exception 
	 */
	private void consultaMapComponentesRespuestas() throws Exception {
		lstControlComponentesDTO = detalleFormularioDAO.consultarControlComponentes();
		
		/**Se agregan elementos de la tabla control a hashMap**/
		Map<String, ControlComponentesDTO> mapControlComponentesTmp = new HashMap<>();
		mapRespuestas = new HashMap<>();
		for(ControlComponentesDTO controlComponente: lstControlComponentesDTO) {			
			mapControlComponentesTmp.put(controlComponente.getNombreColumna(), controlComponente);
			/**Se agregan nombres de columna para el guardado de datos**/
			mapRespuestas.put(controlComponente.getNombreColumna(), Constantes.EMPTY_STRING);
		}
		
		/**Se aplica ordenamiento al hashMap por "id" que nos ayudará a saber el orden en el que fue creada la tabla**/
		mapControlComponentes = mapControlComponentesTmp.entrySet().stream()
				.sorted(Entry.comparingByValue())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		/**Se agrega a cada componente el nombre de la columna (key del map mapRespuestas) en la que deberá colocar su respuesta**/
		/**En los componentes necesario se inicializan valores**/
		for(SeccionesFormularioDTO seccionTemp : lstSeccionesDTO) {
			if (BeanUtils.isNotNull(seccionTemp.getLstSubsecciones())) {
				for(SubSeccionesFormularioDTO subseccionTemp : seccionTemp.getLstSubsecciones()) {
					for(ComponenteDTO componenteTemp : subseccionTemp.getLstComponentes()) {
						if (BeanUtils.isNotNull(componenteTemp)) {
							
							//Se verifica el tipo de componente
							switch (componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente()) {
							
							case Constantes.ID_COMPONENTE_CAMPO_TEXTO:
								componenteTemp.setRespuestaComponente(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente().toString())).getNombreColumna());
								break;								
							case Constantes.ID_COMPONENTE_FECHA:
								componenteTemp.setRespuestaComponente(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente().toString())).getNombreColumna());
								break;
								
							case Constantes.ID_COMPONENTE_MENU_DESPLEGABLE:
								componenteTemp.setRespuestaComponente(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente().toString())).getNombreColumna());
								break;
								
							case Constantes.ID_COMPONENTE_CHECKBOX_UNICO:
								componenteTemp.setRespuestaComponente(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente().toString())).getNombreColumna());
								break;
								
							case Constantes.ID_COMPONENTE_CHECKBOX_GRUPO:
								componenteTemp.setRespuestaComponente(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente().toString())).getNombreColumna());
								break;
															
							case Constantes.ID_COMPONENTE_DATOS_DOMICILIO:
								ComponenteDatosDomicilioDTO componenteDomicilio = (ComponenteDatosDomicilioDTO) componenteTemp;								
								componenteDomicilio.setCalle(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_1")).getNombreColumna());
								componenteDomicilio.setNumeroExterior(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_2")).getNombreColumna());
								componenteDomicilio.setNumeroInterior(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_3")).getNombreColumna());
								componenteDomicilio.setCodigoPostal(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_4")).getNombreColumna());
								componenteDomicilio.setColonia(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_5")).getNombreColumna());
								componenteDomicilio.setAlcaldia(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_6")).getNombreColumna());
								componenteDomicilio.setEstado(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_7")).getNombreColumna());
								componenteTemp = componenteDomicilio;
								break;
								
							case Constantes.ID_COMPONENTE_DATOS_PERSONALES_CON_LLAVE:
								ComponenteDatosPersonalesLlaveDTO componenteDatosPersonalesConLlave = (ComponenteDatosPersonalesLlaveDTO) componenteTemp;								
								componenteDatosPersonalesConLlave.setCurp(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_1")).getNombreColumna());
								componenteDatosPersonalesConLlave.setNombre(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_2")).getNombreColumna());
								componenteDatosPersonalesConLlave.setPrimerApellido(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_3")).getNombreColumna());
								componenteDatosPersonalesConLlave.setSegundoApellido(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_4")).getNombreColumna());
								componenteDatosPersonalesConLlave.setTelefono(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_5")).getNombreColumna());
								componenteDatosPersonalesConLlave.setCorreoElectronico(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_6")).getNombreColumna());
								componenteDatosPersonalesConLlave.setFechaNacimiento(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_7")).getNombreColumna());
								
								String claveSexo = Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente() + "_8");
								ControlComponentesDTO componenteSexo = mapControlComponentes.get(claveSexo);

								if (componenteSexo != null) {
								    componenteDatosPersonalesConLlave.setSexo(componenteSexo.getNombreColumna());
								} else {
								    LOGGER.warn("No se encontró componente para clave '{}'. No se puede asignar sexo.", claveSexo);								
								}
								componenteTemp = componenteDatosPersonalesConLlave;
								break;					
								
							case Constantes.ID_COMPONENTE_DATOS_PERSONALES_SIN_LLAVE:
								ComponenteDatosPersonalesDTO componenteDatosPersonalesSinLlave = (ComponenteDatosPersonalesDTO) componenteTemp;
								componenteDatosPersonalesSinLlave.setCurp(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_1")).getNombreColumna());
								componenteDatosPersonalesSinLlave.setNombre(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_2")).getNombreColumna());
								componenteDatosPersonalesSinLlave.setpApellido(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_3")).getNombreColumna());
								componenteDatosPersonalesSinLlave.setsApellido(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_4")).getNombreColumna());
								componenteDatosPersonalesSinLlave.setTelefono(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_5")).getNombreColumna());
								componenteDatosPersonalesSinLlave.setEmail(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_6")).getNombreColumna());
								componenteTemp = componenteDatosPersonalesSinLlave;
								break;

							case Constantes.ID_COMPONENTE_DATOS_PERSONA_MORAL:
								ComponenteDatosPersonaMoralDTO componenteDatosPersonaMoral = (ComponenteDatosPersonaMoralDTO) componenteTemp;								
								componenteDatosPersonaMoral.setRfc(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_1")).getNombreColumna());
								componenteDatosPersonaMoral.setRazonSocial(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_2")).getNombreColumna());
								componenteDatosPersonaMoral.setVigenciaCertificado(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_3")).getNombreColumna());
								componenteTemp = componenteDatosPersonaMoral;
								break;
								
							case Constantes.ID_COMPONENTE_RADIO_BOTON:
								ComponenteRadiobotonDTO componenteRadio = (ComponenteRadiobotonDTO) componenteTemp;
								componenteRadio.setOpcionRespuesta(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_1")).getNombreColumna());
								componenteRadio.setIsOtro(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_2")).getNombreColumna());
								componenteRadio.setEspecifiqueOtro(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente()+"_3")).getNombreColumna());
								componenteTemp = componenteRadio;
								break;
								
							case Constantes.ID_COMPONENTE_CARGA_DOCUMENTOS:
								componenteTemp.setRespuestaComponente(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente().toString())).getNombreColumna());		
								break;
							
							/**El componente de tipo informativo, no registra información a nivel de BD**/	
							case Constantes.ID_COMPONENTE_INFORMATIVO:
								//componenteTemp.setRespuestaComponente(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente().toString())).getNombreColumna());		
							break;

							case Constantes.ID_COMPONENTE_AREA_TEXTO:
								componenteTemp.setRespuestaComponente(mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente().toString())).getNombreColumna());
								break;		
								
							case Constantes.ID_COMPONENTE_TABLA:
							    componenteTemp.setRespuestaComponente(
							        mapControlComponentes.get(Constantes.NOMBRE_BASE_COLUMNAS.concat(componenteTemp.getIdComponente().toString())).getNombreColumna()
							    );
							    break;
								
							default:
								LOGGER.warn("Tipo de componente no configurado :: " + componenteTemp.getCatTipoComponenteDTO().getIdTipoComponente());
								break;
							}
						}
					}							
				}
			} 
		}			
	}
	
	private void validarExistenciaCamposAvisos() {
		if(proyectoDTO.isAviso()) {
			seccionesFormularioFacade.generarCamposParaAvisos();
		}
	}
		
	/**GETTER´s y SETTER´s**/
	
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
	 * @return the lstSeccionesDTO
	 */
	public ArrayList<SeccionesFormularioDTO> getLstSeccionesDTO() {
		return lstSeccionesDTO;
	}

	/**
	 * @param lstSeccionesDTO the lstSeccionesDTO to set
	 */
	public void setLstSeccionesDTO(ArrayList<SeccionesFormularioDTO> lstSeccionesDTO) {
		this.lstSeccionesDTO = lstSeccionesDTO;
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
	 * @return the lstControlComponentesDTO
	 */
	public List<ControlComponentesDTO> getLstControlComponentesDTO() {
		return lstControlComponentesDTO;
	}

	/**
	 * @param lstControlComponentesDTO the lstControlComponentesDTO to set
	 */
	public void setLstControlComponentesDTO(List<ControlComponentesDTO> lstControlComponentesDTO) {
		this.lstControlComponentesDTO = lstControlComponentesDTO;
	}
		
}

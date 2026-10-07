package mx.gob.atdt.interprete.facade;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import mx.gob.atdt.interprete.common.formatos.FormatoRespuestaPDF;
import mx.gob.atdt.interprete.common.formatos.FormatoTramiteFinalizadoPDF;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dto.CatAsentamientosDTO;
import mx.gob.atdt.interprete.commons.dto.CatEstadosDTO;
import mx.gob.atdt.interprete.commons.dto.CatMunicipiosDTO;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.AccesoLlaveDAO;
import mx.gob.atdt.interprete.dao.ArchivoRespuestaDAO;
import mx.gob.atdt.interprete.dao.CatAsentamientosDAO;
import mx.gob.atdt.interprete.dao.CatEstadosDAO;
import mx.gob.atdt.interprete.dao.CatMunicipiosDAO;
import mx.gob.atdt.interprete.dao.DetElementosTokenDAO;
import mx.gob.atdt.interprete.dao.DominioSeguridadDAO;
import mx.gob.atdt.interprete.dao.FirmaDigitalDAO;
import mx.gob.atdt.interprete.dao.NotificacionMovimientoTramiteDAO;
import mx.gob.atdt.interprete.dao.PersonaMoralDAO;
import mx.gob.atdt.interprete.dao.UsuarioDAO;
import mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO;
import mx.gob.atdt.interprete.dto.CatTipoNotificacionDTO;
import mx.gob.atdt.interprete.dto.ComponenteAreaTextoDTO;
import mx.gob.atdt.interprete.dto.ComponenteCampoTextoDTO;
import mx.gob.atdt.interprete.dto.ComponenteCargaDocumentosDTO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxDTO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxUnicoDTO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosDomicilioDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonaMoralDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesLlaveDTO;
import mx.gob.atdt.interprete.dto.ComponenteDynamicTableDTO;
import mx.gob.atdt.interprete.dto.ComponenteFechaDTO;
import mx.gob.atdt.interprete.dto.ComponenteInformativoDTO;
import mx.gob.atdt.interprete.dto.ComponenteMenuDesplegableDTO;
import mx.gob.atdt.interprete.dto.ComponenteRadiobotonDTO;
import mx.gob.atdt.interprete.dto.ConfiguracionWebhookDTO;
import mx.gob.atdt.interprete.dto.ControlComponentesDTO;
import mx.gob.atdt.interprete.dto.DetAccesoLLaveDTO;
import mx.gob.atdt.interprete.dto.DetElementosMenuDTO;
import mx.gob.atdt.interprete.dto.DetElementosTokenDTO;
import mx.gob.atdt.interprete.dto.DetFirmaDigitalDTO;
import mx.gob.atdt.interprete.dto.DetSecurityDomainDTO;
import mx.gob.atdt.interprete.dto.GeneraComprobanteDTO;
import mx.gob.atdt.interprete.dto.NotificacionMovimientoTramiteDTO;
import mx.gob.atdt.interprete.dto.PersonaMoralDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.TramiteFirmaElectronicaDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.dto.webhook.RequestWebhookDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.DetalleFormularioDAO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;
import mx.gob.atdt.interprete.exception.InterpreteException;
import mx.gob.atdt.interprete.formulario.dao.FormularioDAO;
import mx.gob.atdt.webhook.NotificacionTramiteWebhookRESTClient;

/**
 * Esta clase concentra la logica para la generacion del request
 * para notificacion a webhook
 * Contiene adaptaciones de metodos para el back de las clases
 * 
 * GenerarFormularioApplication, SeccionesProyectoBean
 * BandejaValidacionTramitesBean, BandejaTramitesBean, RegistroFormularioBean
 * 
 * @author Ramiro Luna Torres
 * @version 1.0
 * 
 */
@Stateless
@LocalBean
public class NotificacionWebHookFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionWebHookFacade.class);

	private static final String DESCRIPCION_ELEMENTO = "descripcionElemento";

	private static final String COMPONENTE =  "componente_";

	@Inject
	private NotificacionMovimientoTramiteDAO notificacionMovimientoTramiteDAO;

	@Inject
	private DetElementosTokenDAO detElementosTokenDAO;

	@Inject
	private FormularioDAO formularioDAO;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private CatEstadosDAO estadoDAO;

	@Inject
	private CatMunicipiosDAO municipioDAO;

	@Inject
	private CatAsentamientosDAO asentamientoDAO;

	@Inject
	private PersonaMoralDAO personaMoralDAO;
	
	@Inject
	private DominioSeguridadDAO dominioSeguridadDAO;
	
	@Inject
	private FirmaDigitalDAO firmaDAO;
	
	@Inject
	private AccesoLlaveDAO llaveDAO;
	
	@Inject
	private DetalleFormularioDAO detalleFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO estructuraFormularioDAO;
	
	@Inject
	private ArchivoRespuestaDAO respuestaDAO;
	

	/**
	 * Metodo asincrono para ejecutar el envio de notificaciones a webhook
	 * @param lstTramites
	 * @param proyectoDTO
	 * @param configuracionWebhookDTO
	 * @param iniciaScheduler
	 * @param inGeneraComprobanteDTO
	 */
	@Asynchronous
	public void notificarWebhook(List<TramiteDTO> lstTramites, ProyectoDTO proyectoDTO, ConfiguracionWebhookDTO configuracionWebhookDTO,
			 GeneraComprobanteDTO inGeneraComprobanteDTO) {
		RequestWebhookDTO requestWebHook;
		long inicio;
		long fin;
		inicio = System.nanoTime();
		try {
			//antes de iniciar con la carga de informacion para documentos validamos si hay documentos configuirados
			asignarArchivoRespuesta(inGeneraComprobanteDTO, proyectoDTO);
			GeneraComprobanteDTO generaComprobanteDTO = inGeneraComprobanteDTO;
			
			for(TramiteDTO tramite:lstTramites) {
				requestWebHook = new RequestWebhookDTO(proyectoDTO.getIdProyecto(), tramite.getIdTramite(), tramite.getFolioSeguimiento(), convertirFechaYHoraDate(tramite.getFechaCreacion()), tramite.getCatEstatusTramiteDTO().getIdEstatusTramite());
				
				if(BeanUtils.isNull(tramite.getProyectoDTO())) {
					tramite.setProyectoDTO(proyectoDTO);
				}
				requestWebHook.setFechaRevision(convertirFechaYHoraDate(tramite.getFechaRevision()));
				
				asignaDatosUsuarioTramite(tramite, requestWebHook);
				
				enviarNotificacionWebhook(null, tramite, proyectoDTO, generaComprobanteDTO, requestWebHook, configuracionWebhookDTO);
			}
			fin = System.nanoTime();
			LOGGER.info(">>> NotificacionesWebhook - Duración: {{}} seg.", TimeUnit.NANOSECONDS.toSeconds((fin-inicio)));
		}catch (Exception e) {
			LOGGER.error("Error en el prellenado de solicitud webHook:: "+e.getMessage());
		}
		
	}
	
	/**
	 * Metodo destinado a la notificaciones del Scheduler
	 * 
	 * @param lstNotificaTramites
	 * @param proyectoDTO
	 * @param configuracionWebhookDTO
	 * @param inGeneraComprobanteDTO
	 */
	public void notificarWebhookScheduler(List<NotificacionMovimientoTramiteDTO> lstNotificaTramites, ProyectoDTO proyectoDTO, ConfiguracionWebhookDTO configuracionWebhookDTO,
			GeneraComprobanteDTO inGeneraComprobanteDTO) {
		RequestWebhookDTO requestWebHook;
		try {
			//antes de iniciar con la carga de informacion para documentos validamos si hay documentos configuirados
			asignarArchivoRespuesta(inGeneraComprobanteDTO, proyectoDTO);
			
			//el proceso ha iniciado por el scheduler procedemos a cargar la informacion de generacion de documentos por el back
			GeneraComprobanteDTO generaComprobanteDTO = inGeneraComprobanteDTO;
			generaComprobanteDTOScheduler(proyectoDTO, generaComprobanteDTO);
			
			for(NotificacionMovimientoTramiteDTO notiTramite:lstNotificaTramites) {
				TramiteDTO tramite = notiTramite.getTramiteDTO();
				requestWebHook = new RequestWebhookDTO(proyectoDTO.getIdProyecto(), tramite.getIdTramite(), tramite.getFolioSeguimiento(), convertirFechaYHoraDate(tramite.getFechaCreacion()), tramite.getCatEstatusTramiteDTO().getIdEstatusTramite());
				
				if(BeanUtils.isNull(tramite.getProyectoDTO())) {
					tramite.setProyectoDTO(proyectoDTO);
				}
				requestWebHook.setFechaRevision(convertirFechaYHoraDate(tramite.getFechaRevision()));
				
				asignaDatosUsuarioTramite(tramite, requestWebHook);
				
				enviarNotificacionWebhook(notiTramite, tramite, proyectoDTO, generaComprobanteDTO, requestWebHook, configuracionWebhookDTO);
			}
		}catch (Exception e) {
			LOGGER.error("Error en el prellenado de solicitud webHook:: "+e.getMessage());
		}
		
	}
	
	/**
	 * Asignar datos usuario
	 * 
	 * Para estos casos en los que el tramite lo registran como persona moral, 
	 * aqui seguimos manteniendo una cuenta llave (idUsuarioLlaveMx y su CURP), 
	 * pero lo adicional es que si sabemos el RFC al que quedo asociado el tramite, 
	 * por eso quiza sea conveniente pegarle el CURP, para que solo tengamos el flujo 
	 * alterno de tramites asociados a persona moral, y esos seran los que si llevan dato de RFC.
	 * 
	 * @param tramite
	 * @param requestWebHook
	 */
	private void asignaDatosUsuarioTramite(TramiteDTO tramite, RequestWebhookDTO requestWebHook){
		//si campo persona moral no viene vacio, entonces el tramite pertence a un persona moral
		//buscamos los datos del rfc y su idLlave y se los seteamos al objeto como duenio del tramite
		if(BeanUtils.isNotNull(tramite.getUsuario())) {
			requestWebHook.setCurp(tramite.getUsuario().getCurp());
			requestWebHook.setIdUsuarioLlaveMX(tramite.getUsuario().getIdUsuarioLlaveCdmx());
			if(BeanUtils.isNotNull(tramite.getUsuario().getIdPersonaMoral())) {
				PersonaMoralDTO datosPersonaMoral = personaMoralDAO.buscarPorPersonaMoral(tramite.getUsuario().getIdPersonaMoral());
				requestWebHook.setRfc(datosPersonaMoral.getRfc());
			}
		}else {
			LOGGER.warn("No hay usuario para el tramite: {}", tramite.getFolioSeguimiento());
		}
	}
	
	/**
	 * Metodo encargado de abrir la conexion con el cliente de webhook
	 * y de guardar la respuesta del mismo en la BD
	 * 
	 * @param tramiteActual
	 * @param prEstatusEnviado
	 * @param proyectoDTO
	 * @param generaComprobanteDTO
	 * @param requestWebHook
	 * @param configuracionWebhookDTO
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private boolean enviarNotificacionWebhook(NotificacionMovimientoTramiteDTO notificaDTO, TramiteDTO tramiteActual, ProyectoDTO proyectoDTO, 
			GeneraComprobanteDTO generaComprobanteDTO, RequestWebhookDTO requestWebHook, ConfiguracionWebhookDTO configuracionWebhookDTO) {
		NotificacionTramiteWebhookRESTClient notificacionTramiteWebhookRESTClient = new NotificacionTramiteWebhookRESTClient();
		boolean notificacionEnviada = false;
		int idTipoNotificacion;
		NotificacionMovimientoTramiteDTO dto =  new NotificacionMovimientoTramiteDTO();
		CatTipoNotificacionDTO  catTipoNotificacionDTO = new CatTipoNotificacionDTO();
		
		String pathDocumento = null;
		dto.setTramiteDTO(new TramiteDTO(requestWebHook.getIdTramite()));
		if(BeanUtils.isNotNull(notificaDTO)) {
			dto.setIdNotificacionMovimiento(notificaDTO.getIdNotificacionMovimiento());
		}
		idTipoNotificacion = asignaTipoDeNotificacion(notificaDTO, proyectoDTO, tramiteActual);
		catTipoNotificacionDTO.setIdTipoNotificacion(idTipoNotificacion);
		dto.setCatTipoNotificacionDTO(catTipoNotificacionDTO);
		requestWebHook.setIdTipoNotificacion(idTipoNotificacion);	
		try {
			
			pathDocumento = asignaPathDocumentoEnviar(proyectoDTO, tramiteActual, generaComprobanteDTO);
			requestWebHook.setDocumentoGenerado(generarDocumentoBase64(pathDocumento));
			
			notificacionEnviada = notificacionTramiteWebhookRESTClient.enviarNotificacion(configuracionWebhookDTO, requestWebHook);

			if(notificacionEnviada) {
				dto.setFechaNotificacion(new Date());
				dto.setEnvioConfirmado(true);
			}
		}catch (Exception e) {
			LOGGER.error("Ocurrió un error al en enviar la notificación por Webhook :: " + requestWebHook.getFolioSeguimiento() + " ", e);
		}
		actualizaNotificacionWebHookTramite(dto);
		return notificacionEnviada;
	}
	
	/**
	 * Asigna la ruta del documento adjunto segun aplica en el momento que se esta notificando
	 *  Enviado - Plantilla default/configurado/ninguna
	 *  Aprobado/Rechazado - Plantillas configuradas/ninguna
	 *  En Correciones - Documento adjunto en el proceso
	 *  Conclusion Positiva/Negativa - Documento adjunto en el proceso
	 *  
	 * @param proyectoDTO
	 * @param tramiteActual
	 * @param generaComprobanteDTO
	 * @return
	 * @throws InterpreteException
	 */
	private String asignaPathDocumentoEnviar(ProyectoDTO proyectoDTO, TramiteDTO tramiteActual, 
			GeneraComprobanteDTO generaComprobanteDTO) throws InterpreteException {
		String pathDocumento = null;
		switch (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite()) {
		case Constantes.ID_ESTATUS_ENVIADO:
			pathDocumento = generarComprobanteTramiteEnviado(tramiteActual, generaComprobanteDTO);
			break;
		case Constantes.ID_ESTATUS_CORRECIONES:
			if(BeanUtils.isNotNull(tramiteActual.getRutaDocumentoPrevencion())) {
				pathDocumento = tramiteActual.getRutaDocumentoPrevencion();
			}
			break;
		case Constantes.ID_ESTATUS_APROBADO:
		case Constantes.ID_ESTATUS_RECHAZADO:
			if(proyectoDTO.isAviso() && BeanUtils.isNotNull(tramiteActual.getRutaDocumentoRevocado())) {
				pathDocumento = tramiteActual.getRutaDocumentoRevocado();
			}else {
				pathDocumento = generarComprobanteFinalizado(tramiteActual, proyectoDTO,  generaComprobanteDTO);
			}
			break;
		case Constantes.ID_ESTATUS_CONCLUSION_NEGATIVA:
		case Constantes.ID_ESTATUS_CONCLUSION_POSITIVA:
			if(BeanUtils.isNotNull(tramiteActual.getRutaDocumentoResolucionNegativa())) {
				pathDocumento = tramiteActual.getRutaDocumentoResolucionNegativa();
			}else if(BeanUtils.isNotNull(tramiteActual.getRutaDocumentoResolucionPositiva())) {
				pathDocumento = tramiteActual.getRutaDocumentoResolucionPositiva();
			}
			break;
		default:
			break;
		}
		return pathDocumento;
	}
	
	/**
	 * Asignar el notificacion a enviar
	 * 
	 * @param notificaDTO
	 * @param proyectoDTO
	 * @param tramiteActual
	 * @return
	 */
	private int asignaTipoDeNotificacion(NotificacionMovimientoTramiteDTO notificaDTO, ProyectoDTO proyectoDTO, TramiteDTO tramiteActual){
		int idTipoNotificacion;
		if(BeanUtils.isNotNull(notificaDTO)) {
			idTipoNotificacion = notificaDTO.getCatTipoNotificacionDTO().getIdTipoNotificacion();
		}else{
			if(proyectoDTO.isAviso()) {
				idTipoNotificacion = tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO?Constantes.ID_TIPO_NOTIFICACION_REGISTRO_TRAMITE:Constantes.ID_TIPO_NOTIFICACION_ACTUALIZACION_ESTATUS_TRAMITE;				
			}else {
				idTipoNotificacion = tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO?Constantes.ID_TIPO_NOTIFICACION_REGISTRO_TRAMITE:Constantes.ID_TIPO_NOTIFICACION_ACTUALIZACION_ESTATUS_TRAMITE;
			}
		}
		return idTipoNotificacion;
	}
	/**
	 * Metodo para la actualizacion o insercion a la BD
	 * @param dto
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	private void actualizaNotificacionWebHookTramite(NotificacionMovimientoTramiteDTO dto) {
		if(BeanUtils.isDiferent(dto.getIdNotificacionMovimiento(), 0)) {
			notificacionMovimientoTramiteDAO.actualizar(dto);
		}else {
			notificacionMovimientoTramiteDAO.guardar(dto);
		}
	}
	
	/**¨
	 * Este metodo hace invocacion a metodos replica de codigo que existen en GenerarFormularioApplication y SeccionesProyectoBean
	 *     La carga de ArchivosRespuesta de  SeccionesProyectoBean
	 *     la carga mapa de control de componentes generarFormularioApplication.getMapControlComponentes() 
	 * 		    y lista de seccciones de GenerarFormularioApplication generarFormularioApplication.generarListaSecciones
	 * 
	 * cualquier modificacion a esos metodos favor de replicar su aplicacion en esta clase facade utilizada para notificacion webhook
	 * 
	 * @param proyecto
	 * @param lstSeccionesTramiteDTO
	 * @param mapControlComponentesRespuestas
	 * @return
	 * @throws InterpreteException 
	 */
	private void generaComprobanteDTOScheduler(ProyectoDTO proyecto, GeneraComprobanteDTO generaComprobanteDTO) throws InterpreteException {
		DetSecurityDomainDTO securityDomainDTO;
		DetAccesoLLaveDTO accesoLlaveDTO;
		DetFirmaDigitalDTO firmaDTO;
		try {
			securityDomainDTO = dominioSeguridadDAO.consultaDominioSeguridad(proyecto.getIdProyecto());
			accesoLlaveDTO = llaveDAO.consultaAccesoLlave(proyecto.getIdProyecto());
			firmaDTO = firmaDAO.consultaFirmaDigital(proyecto.getIdProyecto());
			generaComprobanteDTO.setSecurityDomainDTO(securityDomainDTO);
			generaComprobanteDTO.setFirmaDTO(firmaDTO);
			generaComprobanteDTO.setAccesoLlaveDTO(accesoLlaveDTO);			
			
			List<SeccionesFormularioDTO> lstSeccionesTramiteDTO = consultarInformacionSecciones(proyecto);
			generaComprobanteDTO.setLstSeccionesTramiteDTO(generarListaSecciones(lstSeccionesTramiteDTO));
			
			/**Precargar map de Control de componentes y map para respuestas de formulario**/
			if(estructuraFormularioDAO.existeTablaControl()) {
				consultaMapComponentesRespuestas(generaComprobanteDTO);	
			}
		} catch (Exception e) {
			throw new InterpreteException("Fallo al consultar informacion del proyecto "+e.getMessage());
		}
	}
	
	/**
	 * Metodo para convetir Date a LocalDateTime
	 * @param fecha
	 * @return
	 */
	private LocalDateTime convertirFechaYHoraDate(Date fecha) {
		return BeanUtils.isNotNull(fecha)?fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime():null;
	}

	/**
	 * Genera archivo base64 
	 * 
	 * @param rutaArchivo ubicacion fisica del archivo
	 * @return base64 del archivo
	 */
	private String generarDocumentoBase64(String rutaArchivo) {
		String documentoActaBase64=null;
		if(BeanUtils.isNotNull(rutaArchivo)) {
			try {
				Path documento =Paths.get(rutaArchivo) ;
				if(Files.exists(documento)) {
					byte[] documetoByte = Files.readAllBytes(documento);
					documentoActaBase64 = Base64.getEncoder().encodeToString(documetoByte);
					//despues de procesar el documento a base 64, lo eliminamos de la carpeta
					Files.delete(documento);
				}else {
					LOGGER.info("No se encontro en ruta, favor de revisar el proceso");
				}
			} catch (IOException e) {
				LOGGER.error("Error, no fue posible leer el archivo de acta en el path enviado: ", e);
			}
		}
		return documentoActaBase64;
	}

	/**
	 * Metodo auxiliar que sigue las reglas de negocio de los metodos
	 * RegistrarFormularioBean.generarComprobante
	 * 
	 * Cualquier cambios en los metodos en cuanto nuevos formatos agregados replicar
	 * en este metodo para envio a webhook
	 * 
	 * @param tramiteActual
	 * @param generaComprobanteDTO
	 * @return
	 * @throws InterpreteException 
	 */
	private String generarComprobanteTramiteEnviado(TramiteDTO tramiteActual, GeneraComprobanteDTO generaComprobanteDTO) throws InterpreteException {
		String pathComprobante = null;
		UsuarioDTO usuarioTramite = null;
		//Inicio- Se agrega funcionalidad para recuperar las respuestas del formulario
		Map<String, Object> mapRespuestasTramite = new HashMap<>();
		List<DetElementosTokenDTO> lstDetElementosTokenDTO = new ArrayList<>();
		FormatoTramiteFinalizadoPDF formatoPDF = new FormatoTramiteFinalizadoPDF();
		try {
			//Si el ciudadano accede con llave, entonces es posible recuperar los datos del usuario para plantilla
			if (generaComprobanteDTO.getAccesoLlaveDTO().isAutenticacionCiudadano()) {
				usuarioTramite = usuarioDAO.buscarPorId(tramiteActual.getUsuario().getIdUsuarioLlaveCdmx());
			}
			//Si el proyecto tiene configuración de firma y además puede firmar el ciudadano, entonces se recupera los datos
			//de firma del trámite, y se debe utilizar la plantilla personalizada de tipo "4 - Plantilla comprobante registro".
			TramiteFirmaElectronicaDTO datosFirmaTramite = null;
			if (BeanUtils.isNotNull(generaComprobanteDTO.getFirmaDTO()) && generaComprobanteDTO.getFirmaDTO().isFirmaCiudadano()) {
				datosFirmaTramite = formularioDAO.consultarFirmaTramiteCiudadano(tramiteActual);
			}
			
			List<SeccionesFormularioDTO> lstSeccionesTramiteDTO = generaComprobanteDTO.getLstSeccionesTramiteDTO();
			Map<String, ControlComponentesDTO> mapControlComponentesRespuestas = generaComprobanteDTO.getMapControlComponentesRespuestas();
			if(BeanUtils.isNotNull(generaComprobanteDTO.getArchivosRespuestaTokenRegistroDTO())) {
				lstDetElementosTokenDTO = detElementosTokenDAO.buscarPorIdArchivoRespuesta(generaComprobanteDTO.getArchivosRespuestaTokenRegistroDTO().getIdArchivoRespuesta());
				if(lstDetElementosTokenDTO != null) {
					mapRespuestasTramite = cargarDatosRespuesta(tramiteActual, mapControlComponentesRespuestas , lstSeccionesTramiteDTO, lstDetElementosTokenDTO, false);
				}
			}
			//Fin- se agrega funcionalidad para recuperar las respuestas del formulario

			//se revisan los tipo de componente para recuperar descripcion en lugar de id
			for(Map.Entry<String, ControlComponentesDTO> entry : generaComprobanteDTO.getMapControlComponentesRespuestas().entrySet()) {
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
			pathComprobante = formatoPDF.generarDocumento(tramiteActual, generaComprobanteDTO.getSecurityDomainDTO().getUrlSistema(), usuarioTramite, generaComprobanteDTO.getAccesoLlaveDTO().isAutenticacionCiudadano(),
					generaComprobanteDTO.getArchivosRespuestaTokenRegistroDTO(), datosFirmaTramite, mapRespuestasTramite, lstDetElementosTokenDTO, mapControlComponentesRespuestas, lstSeccionesTramiteDTO);
		} catch (Exception e) {
			throw new InterpreteException("No se encontró el PDF para la descarga del tramite desde bandeja del funcionario "+ tramiteActual.getFolioSeguimiento()+"\n"+e.getCause());
		}	

		return pathComprobante;
	}
	

	/**
	 * Metodo auxiliar que sigue las reglas de negocio de los metodos
	 * BandejaTramitesBean.generarArchivo
	 * BandejaValidacionTramitesBean.generarArchivo
	 * 
	 * Cualquier cambios en los metodos en cuanto nuevos formatos agregados replicar
	 * en este metodo para envio a webhook
	 * 
	 * 
	 * @param tramiteActual
	 * @param generaComprobanteDTO
	 * @return
	 * @throws InterpreteException 
	 */
	private String generarComprobanteFinalizado(TramiteDTO tramiteActual, ProyectoDTO proyectoDTO, GeneraComprobanteDTO generaComprobanteDTO) throws InterpreteException {
		String pathComprobante = null;
		FormatoRespuestaPDF formatoPDF = new FormatoRespuestaPDF();
		List<DetElementosTokenDTO> lstDetElementosTokenDTO = new ArrayList<>();
		String mensajeWarn="El proyecto actual no cuenta con una configuraci\\u00F3n para emitir comprobantes, por favor verifique con el Administrador.";
		try {
			/**Dependiendo del estatus del trámite, se tomará el archivo respuesta para la descarga **/	
			if(generaComprobanteDTO.isExistenFormatosConfigurados()) {
				ArchivosRespuestaTokenDTO archivoRespuesta = obtenerArchivoRespuesta(tramiteActual, generaComprobanteDTO.getArchivosRespuestaTokenRegistroDTO(), 
						generaComprobanteDTO.getArchivosRespuestaTokenAceptacionDTO(), generaComprobanteDTO.getArchivosRespuestaTokenRechazoDTO(), 
						generaComprobanteDTO.getArchivosRespuestaTokenConclusionDTO(), generaComprobanteDTO.isHabilitaFirmadoTramites());
				if(BeanUtils.isNotNull(archivoRespuesta)){
					List<SeccionesFormularioDTO> lstSeccionesTramiteDTO = generaComprobanteDTO.getLstSeccionesTramiteDTO();
					Map<String, ControlComponentesDTO> mapControlComponentesRespuestas = generaComprobanteDTO.getMapControlComponentesRespuestas();
					lstDetElementosTokenDTO = detElementosTokenDAO.buscarPorIdArchivoRespuesta(archivoRespuesta.getIdArchivoRespuesta());

					if(BeanUtils.isNotNull(lstDetElementosTokenDTO)) {
						Map<String, Object> mapRespuestas =cargarDatosRespuesta(tramiteActual, mapControlComponentesRespuestas, lstSeccionesTramiteDTO, lstDetElementosTokenDTO, true);
						List<TramiteFirmaElectronicaDTO> lstFirma = null;
						if (generaComprobanteDTO.isHabilitaFirmadoTramites()) {
							lstFirma = formularioDAO.consultarFirmaTramite(tramiteActual);
							pathComprobante = formatoPDF.generarDocumento(proyectoDTO.getIdProyecto(), archivoRespuesta.getRutaArchivoRespuesta(), mapRespuestas,
									tramiteActual, lstDetElementosTokenDTO, generaComprobanteDTO.isHabilitaFirmadoTramites(), archivoRespuesta, 
									generaComprobanteDTO.getSecurityDomainDTO().getUrlSistema(), lstFirma.get(0), mapControlComponentesRespuestas, lstSeccionesTramiteDTO);
						} else {
							pathComprobante = formatoPDF.generarDocumento(proyectoDTO.getIdProyecto(), archivoRespuesta.getRutaArchivoRespuesta(), mapRespuestas, 
									tramiteActual, lstDetElementosTokenDTO, generaComprobanteDTO.isHabilitaFirmadoTramites(), archivoRespuesta, 
									null, null, mapControlComponentesRespuestas, lstSeccionesTramiteDTO);
						}
					}else {
						LOGGER.warn(mensajeWarn);
					}
				}else {
					LOGGER.warn(mensajeWarn);
				}
			}else {
				LOGGER.warn(mensajeWarn);
			}

		} catch (Exception e) {
			throw new InterpreteException("No se encontró el PDF para la descarga del tramite desde bandeja del funcionario "+ tramiteActual.getFolioSeguimiento()+"\n"+e.getCause());
		}
		return pathComprobante;
	}
	
	/**
	 * Metodo para obtener el archivo respuesta configuradfo en formatos
	 * 
	 * @param tramiteActual
	 * @param archivosRespuestaTokenRegistroDTO
	 * @param archivosRespuestaTokenAceptacionDTO
	 * @param archivosRespuestaTokenRechazoDTO
	 * @param archivosRespuestaTokenConclusionDTO
	 * @param habilitaFirmadoTramites
	 * @return
	 */
	private ArchivosRespuestaTokenDTO obtenerArchivoRespuesta(TramiteDTO tramiteActual, ArchivosRespuestaTokenDTO archivosRespuestaTokenRegistroDTO,
			ArchivosRespuestaTokenDTO archivosRespuestaTokenAceptacionDTO, ArchivosRespuestaTokenDTO archivosRespuestaTokenRechazoDTO,
			ArchivosRespuestaTokenDTO archivosRespuestaTokenConclusionDTO, boolean habilitaFirmadoTramites) {
		ArchivosRespuestaTokenDTO archivoRespuesta= null;
		Predicate<ArchivosRespuestaTokenDTO> prArchivoRespuesta = BeanUtils::isNotNull;
		IntPredicate prEstatusVal = p->tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite()==p;
		if(habilitaFirmadoTramites) {
			if(prArchivoRespuesta.test(archivosRespuestaTokenAceptacionDTO) && prEstatusVal.test(Constantes.ID_ESTATUS_APROBADO)) {
				archivoRespuesta = archivosRespuestaTokenAceptacionDTO;
			}else if(prArchivoRespuesta.test(archivosRespuestaTokenRechazoDTO) && prEstatusVal.test(Constantes.ID_ESTATUS_RECHAZADO)) {
				archivoRespuesta = archivosRespuestaTokenRechazoDTO;
			}
		}else {
			if(prArchivoRespuesta.test(archivosRespuestaTokenConclusionDTO) && prEstatusVal.test(Constantes.ID_ESTATUS_APROBADO)) {
				archivoRespuesta = archivosRespuestaTokenConclusionDTO;			
			}else if(prArchivoRespuesta.test(archivosRespuestaTokenRegistroDTO)) {
				archivoRespuesta = archivosRespuestaTokenRegistroDTO;
			}
		}
		return archivoRespuesta;
	} 
	
	/**
	 * Metodo para cambiar respuesta de id por descripciones
	 * 
	 * @param control
	 * @param key
	 * @param respuesta
	 * @return
	 */
	private String cambioRespuesta(ControlComponentesDTO control, String key, Object respuesta) {
		String texto = respuesta.toString().trim();
		switch (control.getIdTipoComponente()) {

		case Constantes.ID_COMPONENTE_CHECKBOX_GRUPO:
			JsonArray array = JsonParser.parseString(texto).getAsJsonArray();
			texto = datosCheckBox(array).toString();
			break;

		case Constantes.ID_COMPONENTE_DATOS_DOMICILIO:
			texto = descripcionDomicilio(control, key, texto);
			break;

		default:
			break;
		}

		return texto;
	}
	
	/**
	 * Devuelve datos de un checkbox seleccionado
	 * @param array
	 * @return
	 */
	private StringBuilder datosCheckBox(JsonArray array) {
		StringBuilder sb = new StringBuilder();

		for (JsonElement element : array) {
			JsonObject obj = element.getAsJsonObject();
			if (obj.has(DESCRIPCION_ELEMENTO) && !obj.get(DESCRIPCION_ELEMENTO).isJsonNull()) {
				String descripcion = obj.get(DESCRIPCION_ELEMENTO).getAsString();
				if (descripcion != null && !descripcion.trim().isEmpty()) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					sb.append(descripcion);
				}
			}
		}
		return sb;
	}
	
	/**
	 * Obtener descripcion de los ID de domicilio 
	 * para Colonia, Municipio, Estado
	 * @param control
	 * @param key
	 * @param textoOri
	 * @return
	 */
	private String descripcionDomicilio(ControlComponentesDTO control, String key, String textoOri) {
		String texto=textoOri;
		// colonia
		if (key.equals(COMPONENTE.concat(control.getIdComponente().toString().concat("_5")))) {
			Integer id = Integer.parseInt(texto);
			CatAsentamientosDTO asentamientoDTO = asentamientoDAO.buscarPorId(id);
			if (BeanUtils.isNotNull(asentamientoDTO) && BeanUtils.isNotNull(asentamientoDTO.getDescripcion())) {
				texto = asentamientoDTO.getDescripcion();
			}
		}
		// municipio
		if (key.equals(COMPONENTE.concat(control.getIdComponente().toString().concat("_6")))) {
			Integer id = Integer.parseInt(texto);
			CatMunicipiosDTO municipioDTO = municipioDAO.buscarPorId(id);
			if (BeanUtils.isNotNull(municipioDTO) && BeanUtils.isNotNull(municipioDTO.getDescripcion())) {
				texto = municipioDTO.getDescripcion();
			}
		}
		// estado
		if (key.equals(COMPONENTE.concat(control.getIdComponente().toString().concat("_7")))) {
			Integer id = Integer.parseInt(texto);
			CatEstadosDTO estadoDTO = estadoDAO.buscarPorId(id);
			if (BeanUtils.isNotNull(estadoDTO) && BeanUtils.isNotNull(estadoDTO.getDescripcion())) {
				texto = estadoDTO.getDescripcion();
			}
		}
		return texto;
	}

	
	/**
	 * Metodo que se utiliza para recuperar las respuestas del formulario
	 * 
	 * @param tramiteActual
	 * @param mapControlComponentes
	 * @param lstSeccionesDTO
	 * @param lstDetElementosTokenDTO
	 * @param isBandeja
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> cargarDatosRespuesta(TramiteDTO tramiteActual, Map<String, ControlComponentesDTO> mapControlComponentes,
			List<SeccionesFormularioDTO> lstSeccionesDTO, List<DetElementosTokenDTO> lstDetElementosTokenDTO, boolean isBandeja) throws Exception {

		Map<String, Object> mapRespuestas = new HashMap<>();

		/**
		 * Se consulta las respuestas del formulario sección por sección
		 **/
		for (SeccionesFormularioDTO seccionTemp : lstSeccionesDTO) {
			
			List<String> lstColumnas = this.lstColumnas(mapControlComponentes, seccionTemp );

			/**
			 * Se verifica que la sección no contenga únicamente Componentes informativos,
			 * dichos componentes no insertan datos en la BD
			 **/
			if (!lstColumnas.isEmpty()) {

				//Se iteran las columnas y solo se consultan aquellas que coinciden con un token a localizar
				if(isBandeja) {
					cargaMapDatosRespuetaBandeja(mapRespuestas, seccionTemp, lstColumnas, tramiteActual, mapControlComponentes, lstDetElementosTokenDTO);
				}else {
					cargaMapDatosRespuesta(mapRespuestas, seccionTemp, lstColumnas, tramiteActual, mapControlComponentes, lstDetElementosTokenDTO);
				}
			} 
		}
		return mapRespuestas;
	}
	/**
	 * Carga el mapa de respuesta basandose en la logica de RegistrarFormularioBean
	 * 
	 * @param mapRespuestas
	 * @param seccionTemp
	 * @param lstColumnas
	 * @param tramiteActual
	 * @param mapControlComponentes
	 * @param lstDetElementosTokenDTO
	 * @throws Exception
	 */
	private void cargaMapDatosRespuesta(Map<String, Object> mapRespuestas, SeccionesFormularioDTO seccionTemp, List<String> lstColumnas, TramiteDTO tramiteActual, 
			Map<String, ControlComponentesDTO> mapControlComponentes, List<DetElementosTokenDTO> lstDetElementosTokenDTO) throws Exception{
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
					cargaMapaRespuestas(mapRespuestas, seccionTemp, lstColumnas, tramiteActual, mapControlComponentes);
				}
			}
		}
	}
	
	/**
	 * Carga el mapa de respuestas basandose en la logica de BandejaTramitesBean
	 * 
	 * @param mapRespuestas
	 * @param seccionTemp
	 * @param lstColumnas
	 * @param tramiteActual
	 * @param mapControlComponentes
	 * @param lstDetElementosTokenDTO
	 * @throws Exception
	 */
	private void cargaMapDatosRespuetaBandeja(Map<String, Object> mapRespuestas, SeccionesFormularioDTO seccionTemp, List<String> lstColumnas, 
			TramiteDTO tramiteActual, 
			Map<String, ControlComponentesDTO> mapControlComponentes, List<DetElementosTokenDTO> lstDetElementosTokenDTO) throws Exception{
		for(int c = 0; c < lstColumnas.size(); c ++) {
			for(int t = 0; t < lstDetElementosTokenDTO.size(); t ++) {
				if(BeanUtils.isNotNull(lstDetElementosTokenDTO.get(t).getIdComponente())) { 	
					/**
					 * Se consulta si existe información de la sección para el trámite actual, esto
					 * se puede dar por trámites existentes a los que se les habilita una nueva
					 * sección que anteriormente no fue registrada
					 **/
					cargaMapaRespuestas(mapRespuestas, seccionTemp, lstColumnas, tramiteActual, mapControlComponentes);						
				}						
			}
		}
	}
	
	/**
	 * Procesa la carga de respuesta Map
	 * 
	 * @param mapRespuestas
	 * @param seccionTemp
	 * @param lstColumnas
	 * @param tramiteActual
	 * @param mapControlComponentes
	 * @throws Exception
	 */
	private void cargaMapaRespuestas(Map<String, Object> mapRespuestas, SeccionesFormularioDTO seccionTemp, List<String> lstColumnas, TramiteDTO tramiteActual, 
			Map<String, ControlComponentesDTO> mapControlComponentes) throws Exception {
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
	
	/**
	 * Procesa de la lista de columnas
	 * 
	 * @param mapControlComponentes
	 * @param seccionTemp
	 * @return
	 */
	private List<String> lstColumnas(Map<String, ControlComponentesDTO> mapControlComponentes, SeccionesFormularioDTO seccionTemp){
		List<String> lstColumnas = new ArrayList<>();
		for (Iterator<Map.Entry<String, ControlComponentesDTO>> elementos = mapControlComponentes.entrySet()
				.iterator(); elementos.hasNext();) {
			Map.Entry<String, ControlComponentesDTO> elementoTmp = elementos.next();
			if (elementoTmp.getValue().getNombreTabla().equals(
					Constantes.NOMBRE_BASE_TABLAS.concat(seccionTemp.getIdSeccionFormulario().toString()))) {
				lstColumnas.add(elementoTmp.getValue().getNombreColumna());
			}
		}
		return lstColumnas;
	}
	
	/**
	 * Método auxiliar que obtiene la información de Secciones, con sus subsecciones y componentes.
	 * @param proyectoDTO
	 * @throws Exception 
	 */
	private List<SeccionesFormularioDTO> consultarInformacionSecciones(ProyectoDTO proyectoDTO) throws Exception {	
		List<SeccionesFormularioDTO> lstSeccionesDTO = new ArrayList<>();
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
		return lstSeccionesDTO;
	}
	
	/**
	 * Método que consulta el detalle de cada componente.
	 * @param lstComponente
	 * @return
	 * @throws Exception
	 */
	private List<ComponenteDTO> consultarDetalleComponente(List<ComponenteDTO> lstComponente) throws Exception {
		List<ComponenteDTO> lstDetalleComponente = new ArrayList<>();
		
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
	 * Método auxiliar que genera una nueva lista con las Secciones, Subsecciones y componentes del Formulario, para su captura
	 * en el formulario.
	 * 
	 * @return
	 */
	private List<SeccionesFormularioDTO> generarListaSecciones(List<SeccionesFormularioDTO> lstSeccionesDTO) {
		ArrayList<SeccionesFormularioDTO> lstSecciones =  new ArrayList<>();
		
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
	 * Método que realiza la carga en hashMap de la información de Tabla y Campo en la que deberá registrarse la información
	 * de un componente del formulario de captura, en el mismo ciclo se precarga el Map de respuestas para el formulario.
	 * 
	 * @throws Exception 
	 */
	private void consultaMapComponentesRespuestas(GeneraComprobanteDTO generaComprobanteDTO) throws Exception {
		List<ControlComponentesDTO> lstControlComponentesDTO = detalleFormularioDAO.consultarControlComponentes();
		
		/**Se agregan elementos de la tabla control a hashMap**/
		Map<String, ControlComponentesDTO> mapControlComponentesTmp = new HashMap<>();
		Map<String, Object> mapRespuestas = new HashMap<>();
		for(ControlComponentesDTO controlComponente: lstControlComponentesDTO) {			
			mapControlComponentesTmp.put(controlComponente.getNombreColumna(), controlComponente);
			/**Se agregan nombres de columna para el guardado de datos**/
			mapRespuestas.put(controlComponente.getNombreColumna(), Constantes.EMPTY_STRING);
		}
		
		/**Se aplica ordenamiento al hashMap por "id" que nos ayudará a saber el orden en el que fue creada la tabla**/
		Map<String, ControlComponentesDTO> mapControlComponentes = mapControlComponentesTmp.entrySet().stream()
				.sorted(Entry.comparingByValue())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		
		/**Se agrega a cada componente el nombre de la columna (key del map mapRespuestas) en la que deberá colocar su respuesta**/
		/**En los componentes necesario se inicializan valores**/
		for(SeccionesFormularioDTO seccionTemp : generaComprobanteDTO.getLstSeccionesTramiteDTO()) {
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
		generaComprobanteDTO.setMapControlComponentesRespuestas(mapControlComponentes);
	}
	
	/**
	 * Metodo para asignar archivos respuesta formatos configurados
	 * 
	 * @param generaComprobanteDTO
	 * @param proyectoDTO
	 * @throws InterpreteException
	 */
	private void asignarArchivoRespuesta(GeneraComprobanteDTO generaComprobanteDTO, ProyectoDTO proyectoDTO) throws InterpreteException {
		try {
			List<ArchivosRespuestaTokenDTO> lstRespuesta = respuestaDAO.consultaArchivoRespuesta(proyectoDTO.getIdProyecto());
			if (BeanUtils.isNotNull(lstRespuesta)) {
				generaComprobanteDTO.setExistenFormatosConfigurados(true);
				/**Se precargan los token de cada archivo de respuesta **/
				for (ArchivosRespuestaTokenDTO respuesta : lstRespuesta) {
					List<DetElementosTokenDTO> lstToken = respuestaDAO.consultaElementosToken(respuesta.getIdArchivoRespuesta());
					if (BeanUtils.isNotNull(lstToken)) {
						respuesta.setLstToken(lstToken);
					} 
				}

				/** Se revisa si los formatos que se tienen registrados requieren del firmado del trámite**/
				generaComprobanteDTO.setHabilitaFirmadoTramites(BeanUtils.habilitaFirmadoTramites(lstRespuesta));

				/** Se revisan los archivos de respuesta, y dependiendo del tipo se plantilla se carga el DTO para el tipo de plantilla Aceptado, Rechazado, y Registro**/
				for (ArchivosRespuestaTokenDTO respuestaFirmaTemp : lstRespuesta) {
					if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_REGISTRO_CONCLUIDO) {
						generaComprobanteDTO.setArchivosRespuestaTokenConclusionDTO(respuestaFirmaTemp);
					}						
					if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_FIRMADO_ACEPTADO) {
						generaComprobanteDTO.setArchivosRespuestaTokenAceptacionDTO(respuestaFirmaTemp);
					}						
					if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_FIRMADO_RECHAZO) {
						generaComprobanteDTO.setArchivosRespuestaTokenRechazoDTO(respuestaFirmaTemp);
					}					
					if(respuestaFirmaTemp.getCatTipoPlantillaDTO().getIdTipoPlantilla().intValue() == Constantes.INT_PLANTILLA_COMPROBANTE_REGISTRO) {
						generaComprobanteDTO.setArchivosRespuestaTokenRegistroDTO(respuestaFirmaTemp);
					}					
				}

			}
		} catch (Exception e) {
			throw new InterpreteException("Fallo al consultar formatos configurados: "+e.getMessage());
		}
	}
}

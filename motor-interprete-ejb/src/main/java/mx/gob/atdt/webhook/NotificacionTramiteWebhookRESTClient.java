package mx.gob.atdt.webhook;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.common.util.JerseyUtil;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.ConfiguracionWebhookDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.webhook.RequestWebhookDTO;
import mx.gob.atdt.interprete.dto.webhook.ResponseWebhookDTO;
import mx.gob.atdt.interprete.exception.InterpreteException;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.function.IntPredicate;


public class NotificacionTramiteWebhookRESTClient implements Serializable {
	
	private ObjectMapper objectMapper;
	
    private static final long serialVersionUID =  1L;

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionTramiteWebhookRESTClient.class);
	
	public NotificacionTramiteWebhookRESTClient() {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	/**
	 * @deprecated
	 * Metodo en deshuso
	 * @param idProyecto
	 * @param tramiteDTO
	 * @param webhookDTO
	 * @return
	 * @throws URISyntaxException
	 * @throws ConnectException
	 * @throws JSONException
	 * @throws InterpreteException
	 */
	public boolean  enviarNotificacion(Long idProyecto, TramiteDTO tramiteDTO,  ConfiguracionWebhookDTO webhookDTO) throws URISyntaxException, ConnectException, JSONException, InterpreteException {
		WebResource webResource = null;
		URI uri = null;
		String respuesta = null;
		boolean notificacionEnviada = false;
		try {
			
			LOGGER.info("webhookDTO.getUrlAplicacionNotificaciones()::  {}" , webhookDTO.getUrlAplicacionNotificaciones());

			uri = new URI(webhookDTO.getUrlAplicacionNotificaciones());
		} catch (URISyntaxException e) {
			LOGGER.error("Error en la sintaxis de la url de la configuración de Webhook: ", e);
			throw new URISyntaxException("Error", "La sintaxis de la url para la notificación de Webhook no es correcta: " + e);
		}
		try {
			webResource = JerseyUtil.getInstance().getClientWebhook().resource(uri.toString());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio de bitácora de proyectos "
					+ idProyecto + " :", e);
			throw new InterpreteException("No se pudo establecer la conexión al servicio configurado de Webhook: " + e);
		}

		JSONObject body = new JSONObject();
		body.put("idProyecto", idProyecto);
		body.put("folioSeguimiento", tramiteDTO.getFolioSeguimiento());
		body.put("fechaCreacion", tramiteDTO.getFechaCreacion().toString());
		body.put("fechaRevision", BeanUtils.isNotNull(tramiteDTO.getFechaRevision())? tramiteDTO.getFechaRevision().toString() : "" );
		body.put("idEstatusTramite", tramiteDTO.getCatEstatusTramiteDTO().getIdEstatusTramite());
		body.put("idEstatusPlataforma", Constantes.ID_TIPO_NOTIFICACION_PLATAFORMA );
		body.put("rutaDocumento", "");

		
		try {
			ClientResponse response = webResource
					.accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", "application/json;charset=utf-8")
					.post(ClientResponse.class, body.toString());

			respuesta = response.getEntity(String.class);

			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				notificacionEnviada = true;
			} else {
				throw new InterpreteException(respuesta);
			}
		} catch (com.sun.jersey.api.client.ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio de Notificación de Webhook: ", ex);
			throw new ConnectException("No fue posible realizar la conexión con el servicio de Notificación de Webhook:" + ex);
		} catch (Exception e) {
			throw new InterpreteException(e.getMessage());
		}

		return notificacionEnviada;
	}
	
	/**
	 * 26/06/2026
	 * Metodo nuevo para la notificacion hacia webhook
	 * @author Ramiro Luna Torres
	 * 
	 * @param webhookDTO
	 * @param requestWebHook
	 * @return
	 * @throws URISyntaxException
	 * @throws ConnectException
	 * @throws InterpreteException
	 */
	public boolean  enviarNotificacion(ConfiguracionWebhookDTO webhookDTO, RequestWebhookDTO requestWebHook) throws URISyntaxException, ConnectException, InterpreteException {
		IntPredicate okResponse      = pr -> pr==Response.Status.OK.getStatusCode();
		IntPredicate createdResponse = pr -> pr==Response.Status.CREATED.getStatusCode();
		WebResource webResource = null;
		URI uri = null;
		String respuesta = null;
		boolean notificacionEnviada = false;
		try {
			
			uri = new URI(webhookDTO.getUrlAplicacionNotificaciones());
		} catch (URISyntaxException e) {
			LOGGER.error("Error en la sintaxis de la url de la configuración de Webhook: ", e);
			throw new URISyntaxException("", "La sintaxis de la url para la notificación de Webhook no es correcta: " + e);
		}
		try {
			webResource = JerseyUtil.getInstance().getClientWebhook().resource(uri.toString());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio de bitácora de proyectos "
					+ requestWebHook.getIdProyecto() + " :", e);
			throw new InterpreteException("No se pudo establecer la conexión al servicio configurado de Webhook: " + e);
		}		
		try {
			ClientResponse clientResponse = webResource
					.accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", "application/json;charset=utf-8")
					.post(ClientResponse.class, objectMapper.writeValueAsString(requestWebHook));
			respuesta = clientResponse.getEntity(String.class);
			JsonNode node = objectMapper.readTree(respuesta);
			LOGGER.info("respuesta: {}, node: {}", respuesta, node);
			ResponseWebhookDTO webHookResponse = objectMapper.treeToValue(node, ResponseWebhookDTO.class);
			if (okResponse.or(createdResponse).test(clientResponse.getStatus()) 
					&& okResponse.or(createdResponse).test(webHookResponse.getCodigo())) {
				notificacionEnviada = true;
			} else {
				throw new Exception(respuesta);
			}
		} catch (com.sun.jersey.api.client.ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio de Notificación de Webhook: ", ex);
			throw new ConnectException("No fue posible realizar la conexión con el servicio de Notificación de Webhook:" + ex);
		} catch (Exception e) {
			throw new InterpreteException(e.getMessage());
		}

		return notificacionEnviada;
	}
}

package mx.gob.atdt.interprete.lineacaptura.client;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.JerseyUtil;
import mx.gob.atdt.interprete.dto.sat.dto.RequestGenerarLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.sat.dto.RespuestaGeneracionLCDTO;
import mx.gob.atdt.interprete.dto.sat.dto.RespuestaLineaCapturaDTO;
import mx.gob.atdt.interprete.exception.ServiciosException;


public class GeneraLineaCapturaClient {

	private ObjectMapper objectMapper;
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneraLineaCapturaClient.class);
	
	

	public GeneraLineaCapturaClient() {
		super();
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}



	public RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> generaLineaCaptura(final String uriServicio, 
			final RequestGenerarLineaCapturaDTO requestNuevaLineaCaptura) 
			throws URISyntaxException, ConnectException, JSONException, ServiciosException {
		RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> respuestaLineaCaptura = new RespuestaLineaCapturaDTO<>();
		URI uri = null;
		String respuestaEnString = null;
		WebResource webResource = null;
		

		try {
			uri = new URI(uriServicio.concat(Environment.getServiceGeneraLc()));
		} catch (URISyntaxException e) {
			LOGGER.error("Error en la sintaxis de la url de generacion de linea de captura: ", e);
			throw new URISyntaxException("", "La sintaxis de la url de envío de datos de generacion de linea de captura no es correcta: " + e);
		}

		try {
			webResource = JerseyUtil.getInstance().getClientLineaCapturaAuth().resource(uri.toString());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio de generacion de linea de captura : {}", e);
			throw new ServiciosException("No se pudo establecer la conexión al servicio de generacion de linea de captura: " + e);
		}

		try {
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", "application/json;charset=utf-8")
					.post(ClientResponse.class, 
							objectMapper.writeValueAsString(requestNuevaLineaCaptura));
			respuestaEnString = response.getEntity(String.class);

			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				JsonNode node = objectMapper.readTree(respuestaEnString);
				RespuestaGeneracionLCDTO data = objectMapper.treeToValue(node, RespuestaGeneracionLCDTO.class);
				respuestaLineaCaptura.setRespuestaDTO(data); 
				respuestaLineaCaptura.setRespuestaString(respuestaEnString);
			} else {
				throw new Exception(respuestaEnString);
			}
		} catch (com.sun.jersey.api.client.ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio para"
					+ " generacion de linea de captura: ", ex);
			throw new ConnectException("No fue posible realizar la conexión con el servicio de Linea de captura: " + ex);
		} catch (Exception e) {
			LOGGER.error("Error inesperado al generar linea de captura: ", e);
			throw new ServiciosException(e.getMessage());
		}

		return respuestaLineaCaptura;
	}
}

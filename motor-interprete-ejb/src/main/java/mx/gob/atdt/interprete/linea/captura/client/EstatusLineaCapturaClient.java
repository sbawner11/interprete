package mx.gob.atdt.interprete.linea.captura.client;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.WebResource;
import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.JerseyUtil;
import mx.gob.atdt.interprete.exception.InterpreteException;
import mx.gob.atdt.interprete.linea.captura.dto.RequestConsultaLCDTO;
import mx.gob.atdt.interprete.linea.captura.dto.ResponseEstatusLCDTO;

public class EstatusLineaCapturaClient {
	
private static final Logger LOGGER = LoggerFactory.getLogger(EstatusLineaCapturaClient.class);
	
	/**
	 * Metodo auxiliar en el consumo del cliente de puente
	 * @param uriPuente urlSistema
	 * @param request datos de consulta
	 * @return objecto EstatusLCDTO
	 * @throws URISyntaxException
	 * @throws ConnectException
	 * @throws JSONException
	 * @throws InterpreteException
	 */
	public ResponseEstatusLCDTO consultaEstatusLC(String uriPuente, RequestConsultaLCDTO request) 
			throws URISyntaxException, ConnectException, InterpreteException {
		Gson gson = new Gson();
		ResponseEstatusLCDTO response = null;
		URI uri = null;
		String respuesta = null;
		WebResource webResource = null;
		
		try {
			uri = new URI(uriPuente.concat(Environment.getServiceConsultaEstatusLc()));
			
		} catch (URISyntaxException e) {
			throw new URISyntaxException(uriPuente, "Error en la sintaxis de la url del proyecto puente " + e);
		}
			
		try {
			webResource = JerseyUtil.getInstance().getClientLineaCapturaAuth().resource(uri.toString());
		} catch (NoSuchAlgorithmException e) {
			throw new ConnectException("No se pudo establecer la conexión al servicio puente dpa: " + e);
		}
		
		try {
			ClientResponse clientResponse = webResource.accept(MediaType.APPLICATION_JSON)
									.header("Content-Type", "application/json;charset=utf-8")
									.post(ClientResponse.class, request);
			respuesta = clientResponse.getEntity(String.class);
			if (clientResponse.getStatus() == Response.Status.OK.getStatusCode()) {
				response = gson.fromJson(respuesta, ResponseEstatusLCDTO.class);
			}else {
				this.mapeoRespuestaClienteError(clientResponse, respuesta);
			}
		} catch (com.sun.jersey.api.client.ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión:: ", ex);
			throw new ConnectException("Error al realizar la conexión con el servicio consulta estatus LC: " + ex);
		}catch (Exception ex) {
			LOGGER.error("Error con el cliente :: ", ex);
			throw new InterpreteException(ex.getMessage());
		} 
		return response;
	}
	
	public void mapeoRespuestaClienteError(ClientResponse clientResponse, String respuesta) throws InterpreteException{
		
		if (clientResponse.getStatus() == Response.Status.BAD_REQUEST.getStatusCode()) {
			throw new InterpreteException(respuesta);
		}
		if (clientResponse.getStatus() == Response.Status.UNAUTHORIZED.getStatusCode()) {
			throw new InterpreteException("Acceso denegado, favor de validar credenciales configuradas");
		}else {
			throw new InterpreteException("Consulte a soporte: "+respuesta);
		}
	}
}

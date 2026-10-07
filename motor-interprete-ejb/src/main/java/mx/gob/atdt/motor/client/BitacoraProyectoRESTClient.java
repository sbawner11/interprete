package mx.gob.atdt.motor.client;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.JerseyUtil;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.exception.InterpreteException;


public class BitacoraProyectoRESTClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(BitacoraProyectoRESTClient.class);

	public boolean actualizarBitacoraProyecto(ProyectoDTO proyectoDTO) throws URISyntaxException, ConnectException, JSONException, InterpreteException {
		WebResource webResource = null;
		URI uri = null;
		String respuesta = null;
		boolean bitacoraActualizada = false;
		try {
			uri = new URI(Environment.getUrlServiceBitacora());			
		} catch (URISyntaxException e) {
			LOGGER.error("Error en la sintaxis de la url de reinicio de bitácora de Proyectos: ", e);
			throw new URISyntaxException("", "La sintaxis de la url para el reinicio de bitácora del proyecto no es correcta: " + e);
		}
		try {
			webResource = JerseyUtil.getInstance().getClientMotorWithAuth().resource(uri.toString());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio de bitácora de proyectos "
					+ proyectoDTO.getIdProyecto() + " :", e);
			throw new InterpreteException("No se pudo establecer la conexión al servicio de bitácora de proyectos: " + e);
		}
		
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("idProyecto", proyectoDTO.getIdProyecto().toString());
		
		try {
			ClientResponse response = webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", "application/json;charset=utf-8").post(ClientResponse.class);
			respuesta = response.getEntity(String.class);

			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				bitacoraActualizada = true;
			} else {
				throw new Exception(respuesta);
			}
		} catch (com.sun.jersey.api.client.ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio de bitácora de proyectos: ", ex);
			throw new ConnectException("No fue posible realizar la conexión con el servicio de bitácora de proyectos:" + ex);
		} catch (Exception e) {
			throw new InterpreteException(e.getMessage());
		}

		return bitacoraActualizada;
	}
}

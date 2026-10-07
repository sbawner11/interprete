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
import mx.gob.atdt.interprete.dto.ReporteContadoresProyectosDTO;
import mx.gob.atdt.interprete.exception.InterpreteException;


public class ContadoresProyectoRESTClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContadoresProyectoRESTClient.class);

	public boolean registrarContadoresProyecto(ReporteContadoresProyectosDTO reporteContadores) throws URISyntaxException, ConnectException, JSONException, InterpreteException {
		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("idProyecto", reporteContadores.getProyectoDTO().getIdProyecto().toString());
		queryParams.add("subtotalCaptura", String.valueOf(reporteContadores.getSubtotalCaptura()));		
		queryParams.add("subtotalEnviado", String.valueOf(reporteContadores.getSubtotalEnviado()));		
		queryParams.add("subtotalCorreccion", String.valueOf(reporteContadores.getSubtotalCorreccion()));
		queryParams.add("subtotalCorregido", String.valueOf(reporteContadores.getSubtotalCorregido()));				
		queryParams.add("subtotalRevisado", String.valueOf(reporteContadores.getSubtotalRevisado()));		
		queryParams.add("subtotalRechazado", String.valueOf(reporteContadores.getSubtotalRechazado()));
		queryParams.add("subtotalAceptado", String.valueOf(reporteContadores.getSubtotalAceptado()));
		queryParams.add("subtotalFirmado", String.valueOf(reporteContadores.getSubtotalFirmado()));
		queryParams.add("totalRegistros", String.valueOf(reporteContadores.getTotalRegistros()));
		queryParams.add("versionBaseDatos", String.valueOf(reporteContadores.getVersionBaseDatos()));
		queryParams.add("versionEar", String.valueOf(reporteContadores.getVersionEar()));
		
		WebResource webResource = null;
		URI uri = null;
		String respuesta = null;
		boolean contadoresActualizados = false;
		try {
			uri = new URI(Environment.getUrlServiceDashboard());			
		} catch (URISyntaxException e) {
			LOGGER.error("Error en la sintaxis de la url de registro de contadores de Proyectos: ", e);
			throw new URISyntaxException("", "La sintaxis de la url para el registro de contadores del proyecto no es correcta: " + e);
		}		
		try {
			webResource = JerseyUtil.getInstance().getClientContadoresWithAuth().resource(uri.toString());
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio de registro de contadores de proyectos "
					+ reporteContadores.getProyectoDTO().getIdProyecto() + " :", e);
			throw new InterpreteException("No se pudo establecer la conexión al servicio de registro de contadores de proyectos: " + e);
		}		
		try {
			ClientResponse response = webResource.queryParams(queryParams).accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", "application/json;charset=utf-8").post(ClientResponse.class);
			respuesta = response.getEntity(String.class);
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				contadoresActualizados = true;
			} else {
				throw new Exception(respuesta);
			}
		} catch (com.sun.jersey.api.client.ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio de registro de contadores de proyectos: ", ex);
			throw new ConnectException("No fue posible realizar la conexión con el servicio de registro de contadores de proyectos:" + ex);
		} catch (Exception e) {
			throw new InterpreteException(e.getMessage());
		}
		return contadoresActualizados;
	}
}

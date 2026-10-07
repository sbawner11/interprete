package mx.gob.atdt.motor.client;

import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MultivaluedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.JerseyUtil;
import mx.gob.atdt.interprete.exception.InterpreteException;

public class SincronizacionProyectoRESTClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(SincronizacionProyectoRESTClient.class);
	
	public String obtenerEstatusProyecto(Long idProyecto) 
			throws URISyntaxException, ConnectException, InterpreteException {
		WebResource webResource;
		URI uri;

		try {
			uri = new URI(Environment.getUrlServiceGetEstatusProyecto() + "/" + idProyecto);
		} catch (URISyntaxException e) {
			LOGGER.error("URL inválida para Estatus del Proyecto: ", e);
			throw new URISyntaxException("", "URL mal formada: " + e.getMessage());
		}

		try {
			webResource = JerseyUtil.getInstance().getClientMotorWithAuth().resource(uri.toString());
			
			ClientResponse response = webResource
					.accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", "application/json;charset=utf-8")
					.post(ClientResponse.class);

			String respuesta = response.getEntity(String.class);
			LOGGER.debug("obtenerEstatusProyecto, respuesta: {}", respuesta);

			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				return respuesta;
			} else {
				throw new Exception("Error del servidor: " + respuesta);
			}
		} catch (com.sun.jersey.api.client.ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio de Estatus del Proyecto: ", ex);
			throw new ConnectException("No fue posible realizar la conexión con el servicio de Estatus del Proyecto: " + ex.getMessage());
		} catch (Exception e) {
			throw new InterpreteException("Error al obtener el Estatus del Proyecto: " + e.getMessage());
		}
	}
	
	public String validarSincronizacion(Long idProyecto, String domainCliente) 
			throws URISyntaxException, ConnectException, InterpreteException {
		WebResource webResource;
		URI uri;

		try {		
			uri = new URI(Environment.getUrlServiceValidaSincronizacion() + "/" + idProyecto);
		} catch (URISyntaxException e) {
			LOGGER.error("URL inválida para Sincronizar el Proyecto: ", e);
			throw new URISyntaxException("", "URL mal formada: " + e.getMessage());
		}

		try {
			MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		        queryParams.add("ipCliente", domainCliente);
		        
			webResource = JerseyUtil.getInstance().getClientMotorWithAuth().resource(uri.toString());

			ClientResponse response = webResource
					.queryParams(queryParams)
					.accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", "application/json;charset=utf-8")
					.post(ClientResponse.class);

			String respuesta = response.getEntity(String.class);
			LOGGER.info("Sincronizar Proyecto >>> Respuesta al validar proyecto: {}", respuesta);

			return respuesta;
		} catch (com.sun.jersey.api.client.ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el Servidor para Sincronización del Proyecto: ", ex);
			throw new ConnectException("No se pudo establecer la conexión al servicio de Sincronización del Proyecto: " + ex.getMessage());
		} catch (Exception e) {
			throw new InterpreteException("Error al Sincronizar el Proyecto: " + e.getMessage());
		}
	}

	public String iniciarSincronizacion(Long idProyecto) 
			throws URISyntaxException, ConnectException, InterpreteException {
		WebResource webResource;
		URI uri;

		try {		
			uri = new URI(Environment.getUrlServiceSincronizarProyecto() + "/" + idProyecto);
		} catch (URISyntaxException e) {
			LOGGER.error("URL inválida para Sincronizar el Proyecto: ", e);
			throw new URISyntaxException("", "URL mal formada: " + e.getMessage());
		}
		
		try {			
			webResource = JerseyUtil.getInstance().getClientMotorWithAuth().resource(uri.toString());

			ClientResponse response = webResource					
					.accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", "application/json;charset=utf-8")
					.post(ClientResponse.class);	

			int statusCode = response.getStatus();
			String respuesta = response.getEntity(String.class);	
			LOGGER.info("Sincronizar Proyecto >>> Respuesta al iniciar sincronizacion:  {}", respuesta);

			if (statusCode != Response.Status.OK.getStatusCode()) {
	            LOGGER.warn("Sincronización fallida. Código HTTP: {}", statusCode);
	            throw new InterpreteException("Error al sincronizar el proyecto. Código HTTP: " + statusCode);
	        }
			
			return respuesta;
		} catch (com.sun.jersey.api.client.ClientHandlerException ex) {
			LOGGER.error("Error al realizar la conexión con el servicio de Sincronización del Proyecto: ", ex);
			throw new ConnectException("No se pudo establecer la conexión al servicio de Sincronización del Proyecto: " + ex.getMessage());
		} catch (Exception e) {
			LOGGER.error("Error inesperado durante sincronización ({}): {}", e.getClass().getSimpleName(), e.getMessage());
	        throw new InterpreteException("Error al sincronizar el proyecto: " + e.getMessage());
		}
	}
}
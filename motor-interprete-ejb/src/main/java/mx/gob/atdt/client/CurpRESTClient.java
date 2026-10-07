package mx.gob.atdt.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.atdt.interprete.common.util.JerseyUtil;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesDTO;
import mx.gob.atdt.interprete.dto.DetSecurityDomainDTO;
import mx.gob.atdt.interprete.exception.InterpreteException;

public class CurpRESTClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(CurpRESTClient.class);
		
	/**
	 * Método que obtiene la información de Nombres y Apellidos mediante la consulta al servicio de CURP.
	 * 
	 * @param curp
	 * @param detSecurityDomainCurpDTO
	 * @return
	 * @throws InterpreteException
	 */
	public ComponenteDatosPersonalesDTO obtenerDatosCurp(String curp, DetSecurityDomainDTO detSecurityDomainCurpDTO) throws InterpreteException {
		ComponenteDatosPersonalesDTO datosPersonalesDTO = null;	
		StringBuilder urlQuery = new StringBuilder();
		urlQuery.append(detSecurityDomainCurpDTO.getUrlSistema().trim());
		urlQuery.append(curp.trim());
		
		URI uri = null;
		String respuesta = null;
		try {
			uri = new URI(urlQuery.toString());
			WebResource webResource = JerseyUtil.getInstance().getClientCURPWithAuth().resource(uri.toString());
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
			respuesta = response.getEntity(String.class);
			LOGGER.debug("Respuesta del servicio curp : " + respuesta + " "  + response.getStatus());
			JSONObject jsonRespuesta = new JSONObject(respuesta);
			
			if (jsonRespuesta.get("statusOper").toString().compareTo("EXITOSO") == 0) {
				JSONObject jsonCurp = new JSONObject(respuesta);
				datosPersonalesDTO = new ComponenteDatosPersonalesDTO();
				datosPersonalesDTO.setCurp(jsonCurp.get("curp").toString());
				datosPersonalesDTO.setNombre(jsonCurp.get("nombres").toString());
				datosPersonalesDTO.setpApellido(jsonCurp.get("apellido1").toString());
				datosPersonalesDTO.setsApellido(jsonCurp.get("apellido2").toString());
			} /*else {
				//Significa que la CURP capturada no existe, por lo tanto se regresa null en el DTO	
			}*/
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al consultar la CURP en el webservice de RENAPO: ", e);
			throw new InterpreteException("msj_error_consulta_curp");
		}
		return datosPersonalesDTO;
	}
}

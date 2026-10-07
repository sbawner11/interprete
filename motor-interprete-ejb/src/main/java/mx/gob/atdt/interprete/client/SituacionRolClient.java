package mx.gob.atdt.interprete.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.JerseyUtil;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.UsuarioDTO;

public class SituacionRolClient {
	private static final Logger LOGGER = LoggerFactory.getLogger(SituacionRolClient.class);
	
	private List<UsuarioDTO> lstUsuariosOperadorDTO;
	private UsuarioDTO usuarioOperadorDTO;

	/**
	 * Método para obtener la lista de usuarios de llave que están asociados a un rol 
	 * (true) y que están y que estuvierón asociados a ese rol (false)
	 * @param rol
	 * @param esActivo
	 * @return lstUsuarioRevisorDTO
	 */
	public List<UsuarioDTO> obtenerUsuariosRol(Long clientId, String rol, boolean esActivo) throws JSONException {
		usuarioOperadorDTO  = new UsuarioDTO();
		lstUsuariosOperadorDTO = new ArrayList<UsuarioDTO>();
		StringBuilder strbRequest = new StringBuilder();
		strbRequest.append("{");
		strbRequest.append("\"clientId\":").append(clientId).append(",");
		strbRequest.append("\"nombreRol\":\"").append(rol).append("\",");
		strbRequest.append("\"esActivo\":").append(esActivo);
		strbRequest.append("}");
		
		String respuesta = null;
		try {
			LOGGER.debug("Environment.getUrlServiceSituacionRol()" + Environment.getUrlServiceSituacionRol());
			URI uri = new URI(Environment.getUrlServiceSituacionRol());
			WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).header("Content-Type", Constantes.CONTENT_TYPE).post(ClientResponse.class, strbRequest.toString());
			respuesta = response.getEntity(String.class);
			if (esRespuestaErrorLlave(respuesta, response.getStatus())) {
				return lstUsuariosOperadorDTO;
			}

			JSONArray lstjSONArrayUsuarios = new JSONArray(respuesta);
		    JSONArray sortedJsonArray = new JSONArray();
		    List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		    for (int i = 0; i < lstjSONArrayUsuarios.length(); i++) {
		    	//Se agrega atributo nombreCompleto al objeto JSON. Se compone de los atributos
		    	//existentes: nombre, primerApellido y opcionalmente segundoApellido.
		    	lstjSONArrayUsuarios.getJSONObject(i).put(
		    			"nombreCompleto", 
		    			lstjSONArrayUsuarios.getJSONObject(i).get("nombre") + " " 
		    			+ lstjSONArrayUsuarios.getJSONObject(i).get("primerApellido") 
		    			+ (lstjSONArrayUsuarios.getJSONObject(i).getString("segundoApellido").compareTo("null") != 0 ? 
		    					" " + lstjSONArrayUsuarios.getJSONObject(i).getString("segundoApellido"):""));
		    	
		    	jsonValues.add(lstjSONArrayUsuarios.getJSONObject(i));
		    }
		    Collections.sort( jsonValues, new Comparator<JSONObject>() {
		        //Se ordena lista por el campo "emailVigente"
//			        private static final String KEY_NAME = "emailVigente";
		    	private static final String KEY_NAME = "nombreCompleto";

		        @Override
		        public int compare(JSONObject a, JSONObject b) {
		            String valA = new String();
		            String valB = new String();
		            try {
		                valA = (String) a.get(KEY_NAME);
		                valB = (String) b.get(KEY_NAME);
		            } 
		            catch (JSONException e) {
		            	LOGGER.error("Ocurrió un error al leer el json con los roles de Llave:", e);
		            }
		            return valA.compareTo(valB);
		        }
		    });

		    for (int i = 0; i < lstjSONArrayUsuarios.length(); i++) {
		        sortedJsonArray.put(jsonValues.get(i));
		    }
			
			for (int i = 0; i < sortedJsonArray.length(); i++) {
				JSONObject myObject = new JSONObject();
				myObject = (JSONObject) sortedJsonArray.get(i);
				usuarioOperadorDTO  = new UsuarioDTO();
				usuarioOperadorDTO.setIdUsuarioLlaveCdmx(myObject.getLong("idUsuario"));
				usuarioOperadorDTO.setCurp(myObject.getString("curp"));
				usuarioOperadorDTO.setNombre(myObject.get("nombre").toString());
				usuarioOperadorDTO.setPrimerApellido(myObject.get("primerApellido").toString());
				usuarioOperadorDTO.setSegundoApellido(myObject.get("segundoApellido").toString().compareTo("null") != 0 ? myObject.get("segundoApellido").toString() : null);
				usuarioOperadorDTO.setTelefono(myObject.get("telVigente").toString());
				usuarioOperadorDTO.setCorreo(myObject.get("emailVigente").toString());
				usuarioOperadorDTO.setNombreCompleto(myObject.getString("nombreCompleto"));
				lstUsuariosOperadorDTO.add(i, usuarioOperadorDTO);
			}
		} catch (URISyntaxException e) {
			LOGGER.error("Error al conectarse al servicio situacion rol, URI incorrecta: ", e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No es posible conectarse al servicio situacion rol, posible error en la configuración del Cliente para que permita Certificados no Seguroros: ", e);
		}
		return lstUsuariosOperadorDTO;
	}

	private boolean esRespuestaErrorLlave(String respuesta, int httpStatus) throws JSONException {
		if (httpStatus != 200) {
			return true;
		}
		if (respuesta == null || respuesta.trim().isEmpty() || respuesta.trim().charAt(0) != '{') {
			return false;
		}
		JSONObject responseJson = new JSONObject(respuesta);
		if (responseJson.has(Constantes.PARAM_ROLES_USUARIO_MENSAJE) 
				|| responseJson.has(Constantes.PARAM_ROLES_USUARIO_MENSAJE)) {
			return true;
		}
		return false;
	}
	
}

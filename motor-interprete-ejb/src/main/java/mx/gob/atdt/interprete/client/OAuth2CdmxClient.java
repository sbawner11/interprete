package mx.gob.atdt.interprete.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.JerseyUtil;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.PersonaMoralDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.oauth.dto.RequestRolesDTO;
import mx.gob.atdt.interprete.oauth.dto.RequestTokenDTO;
import mx.gob.atdt.interprete.oauth.dto.RolesUsuarioDTO;

public class OAuth2CdmxClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2CdmxClient.class);

	/**
	 * Método que obtiene un Token de Llave mediante un objeto RequestTokenDTO
	 * @param requestToken
	 * @return
	 */
	public String obtenerToken(RequestTokenDTO requestToken) {
		String token = null;
		try {
			URI uri = new URI(Environment.getUrlServiceGetToken());
			WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", Constantes.CONTENT_TYPE)
					.post(ClientResponse.class, requestToken);
			
			token = response.getEntity(String.class);
			if(token.contains("error")) {
				LOGGER.warn("Token con error:"+token);
				token = null;
			}
		} catch (URISyntaxException e) {
			LOGGER.error("No fue posible conectarse al servicio para cambiar el Code por un Token porque la URI no es correcta:",e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No fue posible conectarse al servicio para cambiar el Code por un Token posiblemente porque la configuración del Cliente para que permita Certificados no Seguros no funcionó: ",e);
		} 
		return token;
	}
	
	/**
	 * Método que obtiene los datos del usuario de Llave, enviando un token de acceso
	 * @param token
	 * @return
	 */
	public UsuarioDTO obtenerDatosUsuarioPorToken(String token) {
		String respuesta = null;
		UsuarioDTO datosUsuario = new UsuarioDTO();
		try {
			URI uri = new URI(Environment.getUrlServiceGetDatosUsuario());
			WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.header("accessToken", token)
					.get(ClientResponse.class);
			
			respuesta = response.getEntity(String.class);
			
			if(respuesta.contains("error")) {
				datosUsuario = null;
			} else {
				JSONObject jsonObj =  new JSONObject(respuesta);
				datosUsuario.setIdUsuarioLlaveCdmx(Long.parseLong(jsonObj.get("idUsuario").toString()));				
				datosUsuario.setNombre(jsonObj.get("nombre").toString());
				datosUsuario.setPrimerApellido(jsonObj.get("primerApellido").toString());
				datosUsuario.setSegundoApellido(jsonObj.get("segundoApellido").toString());
				datosUsuario.setTelefono(jsonObj.get("telVigente").toString());
				datosUsuario.setCurp(jsonObj.get("curp").toString());
				datosUsuario.setCorreo(jsonObj.get("correo").toString().equals("null") ? null : jsonObj.get("correo").toString());
				datosUsuario.setSexo(jsonObj.get("sexo").toString());
				String fechastr = jsonObj.get("fechaNacimiento").toString();  
			    Date fechaNacimiento;
				try {
					fechaNacimiento = new SimpleDateFormat("dd/MM/yyyy").parse(fechastr);
					datosUsuario.setFechaNacimiento(fechaNacimiento);
				} catch (ParseException e) {
					LOGGER.error("No se pudo trasnformar correctamente la fecha de nacimiento recibida de Llave. Fecha en String∫ "+fechastr, e);
				}  
			}
		} catch(URISyntaxException e) {
			LOGGER.error("No fue posible conectarse al servicio para obtener los datos del usuario por el Token porque la URI no es correcta:",e);
		} catch (JSONException e) {
			LOGGER.error("No fue posible obtener los datos del usuario del Json de respuesta", e);
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No fue posible conectarse al servicio para cambiar el Code por un Token posiblemente porque la configuración del Cliente para que permita Certificados no Seguros no funcionó: ",e);
		} 
		return datosUsuario;
	}
	
	/**
	 * Método que obtiene los roles de un usuario logueado con llave
	 * @param roles
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<RolesUsuarioDTO> obtenerRolesUsuario(RequestRolesDTO roles, String token) {
		String respuesta = null;
		List<RolesUsuarioDTO> lstRoles = new ArrayList<RolesUsuarioDTO>();
		try {
//			URI uri = new URI("https://llave-dev.cdmx.gob.mx/rest/oauth/roles");
//			URI uri = new URI("https://llave.cdmx.gob.mx/rest/oauth/roles");
//			URI uri = new URI("http://10.19.104.18:8081/rest/oauth/roles");
			URI uri = new URI(Environment.getUrlServiceGetRolesUsuario());
			WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.header("accessToken", token)
					.header("Content-Type", Constantes.CONTENT_TYPE).post(ClientResponse.class, roles);
					
			respuesta = response.getEntity(String.class);
			//LOGGER.info("Respuesta:::   " + respuesta);
//			LOGGER.info("respuesta obtener roles usuario: " + respuesta);
//			if (respuesta.contains("Mensaje")) {
//				lstRoles = null;
//			} else {
//				JSONArray arrayJson = new JSONArray(respuesta);
//				for (int i = Constantes.INT_VALOR_CERO; i < arrayJson.length(); i++) {
//					JSONObject jsonObj = arrayJson.getJSONObject(i);
//					RolesUsuarioDTO rolesDTO = new RolesUsuarioDTO();
//					rolesDTO.setIdRol(jsonObj.getInt("idRol"));
//					rolesDTO.setRol(jsonObj.get("rol").toString());
//					lstRoles.add(rolesDTO);
//				}
////				for (RolesUsuarioDTO rolesUsuarioDTO : lstRoles) {
////					LOGGER.info("List tamanio: " + lstRoles.size());
////				}
//			}
			if (response.getStatus() != Status.OK.getStatusCode()) {
				lstRoles = null;
				LOGGER.debug(respuesta);
			} else {
				JSONObject responseJSON = new JSONObject(respuesta);
				if(!responseJSON.isNull(Constantes.PARAM_ROLES_USUARIO_CODE) 
						&& responseJSON.getInt(Constantes.PARAM_ROLES_USUARIO_CODE) == Constantes.CODE_RESPUESTA_ROLES_USUARIO_SUCCESS){
					JSONArray arrayJson = new JSONArray(responseJSON.get(Constantes.PARAM_ROLES_USUARIO_ROLES).toString());
					for (int i = Constantes.INT_VALOR_CERO; i < arrayJson.length(); i++) {
						JSONObject jsonObj = arrayJson.getJSONObject(i);
						RolesUsuarioDTO rolesDTO = new RolesUsuarioDTO();
						rolesDTO.setIdRol(jsonObj.getInt("idRol"));
						rolesDTO.setRol(jsonObj.get("rol").toString());
						lstRoles.add(rolesDTO);
					}
				} else {
					lstRoles = null;
				}
			}
		} catch (URISyntaxException e) {
			LOGGER.error("No fue posible conectarse al servicio para obtener los rolesdel usuario porque la URI no es correcta:", e);
		} catch (JSONException e) {
			LOGGER.error("No fue posible obtener los datos de los roles desde el Json de respuesta", e);
		}  catch (NoSuchAlgorithmException e) {
			LOGGER.error("No fue posible conectarse al servicio para cambiar el Code por un Token posiblemente porque la configuración del Cliente para que permita Certificados no Seguros no funcionó: ",e);
		} 
		return lstRoles;
	}
	
	
	  /**
	 * Consulta las personas morales vinculadas a un token de Llave MX
	 * @param token de acceso 
	 * @return Objeto con la respuesta del servicio
	 */
    public List<PersonaMoralDTO> obtenerPersonasMorales(String token) {
    	
    	List<PersonaMoralDTO> personasMorales = new ArrayList<PersonaMoralDTO>();
        
        try {
        	
            URI uri = new URI(Environment.getUrlServiceGetInformacionPersonaMoral());
            WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.header("accessToken", token)
					.get(ClientResponse.class);
            
            if (response.getStatus() == Status.OK.getStatusCode()) {
                String respuesta = response.getEntity(String.class);
                JSONObject responseJSON = new JSONObject(respuesta);
                         
                if (responseJSON.has("personasMorales") && !responseJSON.isNull("personasMorales")) {
                    personasMorales = parseaPersonasMorales(responseJSON.getJSONArray("personasMorales"), responseJSON.getLong("idUsuario"));
                } else {
                	personasMorales = null;
                }
                
            } else {
            	personasMorales = null;
                LOGGER.error("Error al consultar personas morales. Código de respuesta: {}", response.getStatus());
            }
            
        } catch (URISyntaxException e) {
            LOGGER.error("URI incorrecta para el servicio de personas morales", e);           
        } catch (JSONException e) {
            LOGGER.error("Error al procesar la respuesta JSON del servicio de personas morales", e);      
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("No fue posible conectarse al servicio para realizar la consulta de personas morales", e);           
        }
        
        return personasMorales;
    }
    

    private List<PersonaMoralDTO> parseaPersonasMorales(JSONArray jsonArray, Long idUsuario) throws JSONException {
        List<PersonaMoralDTO> personas = new ArrayList<>();
        
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonPersona = jsonArray.getJSONObject(i);
            PersonaMoralDTO persona = new PersonaMoralDTO();
            persona.setIdUsuarioLlaveCdmx(idUsuario);              
            persona.setRfc(jsonPersona.getString("rfc").trim());                     
            persona.setRazonSocial(jsonPersona.getString("razonSocial"));
            persona.setVigenciaCertificado(LocalDate.parse(jsonPersona.getString("vigenciaCertificado")));
            persona.setCertificadoVigente(true);

            personas.add(persona);
        }
        
        return personas;
    }
    
	public boolean cerrarSesionConLlaveCDMX(String token) throws URISyntaxException, NoSuchAlgorithmException {
		boolean logoutSucces = true;
		try {			
			/**
			 * Es necesario enviar en el body algo para que Jersey agregue en los headers el atributo Content-Type con un valor, 
			 * ya que de lo contrario podría causar conflicto con algunos web servers o load balancers como el caso del balanceador 
			 * de Google que marcaba un 411 Length Required.
			 * **/
			StringBuilder strbRequest = new StringBuilder();
			strbRequest.append("{}");
			
			URI uri = new URI(Environment.getUrlServiceLogout());
			WebResource webResource = JerseyUtil.getInstance().getClientSDKCdmxWithAuth().resource(uri.toString());
			ClientResponse response = webResource
					.header("accessToken", token)
					.header("Content-Type", Constantes.CONTENT_TYPE)
					.post(ClientResponse.class, strbRequest.toString());
			
			if(response.getStatus() != Status.OK.getStatusCode()) {
				logoutSucces = false;
			}
		} catch (URISyntaxException e) {
			LOGGER.error("No fue posible conectarse al servicio para realizar el logouit del usuario porque la URI no es correcta: ", e);
			logoutSucces = false;
			throw new URISyntaxException("Error", "No fue posible conectarse al servicio para realizar el logouit del usuario porque la URI no es correcta" );
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No fue posible conectarse al servicio para realizar el logouit del usuario, posiblemente porque la configuración del Cliente para que permita Certificados no Seguros no funcionó: ",e);
			logoutSucces = false;
			throw new NoSuchAlgorithmException("No fue posible conectarse al servicio para realizar el logouit del usuario, posiblemente porque la configuración del Cliente para que permita Certificados no Seguros no funcionó");
		}
		return logoutSucces;
	}
}

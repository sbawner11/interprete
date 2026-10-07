package mx.gob.atdt.interprete.validator;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.atdt.interprete.common.util.JerseyUtil;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.DetSecurityDomainDTO;

@FacesValidator("curpValidator")
public class CurpValidator implements Validator<Object> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CurpValidator.class);
		
	@Override	
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		final DetSecurityDomainDTO  detSecurityDomainCurpDTO = (DetSecurityDomainDTO) component.getAttributes().get("detSecurityDomainCurpDTO"); 
				
		if(value != null) {
			
			if (value.toString().length() != Constantes.LONGITUD_CURP) {
				FacesMessage message = new FacesMessage(
						"CURP inválida",
						"La CURP ingresada no cumple con el número de caracteres necesarios.");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);

				throw new ValidatorException(message);
			} else {				
				StringBuilder urlQuery = new StringBuilder();
				urlQuery.append(detSecurityDomainCurpDTO.getUrlSistema().trim());
				urlQuery.append(value.toString().trim());
				URI uri = null;
				String respuesta = null;
				try {
					uri = new URI(urlQuery.toString());
					WebResource webResource = null;
					webResource = JerseyUtil.getInstance().getClientCURPWithAuth().resource(uri.toString());
					ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
					respuesta = response.getEntity(String.class);
					//LOGGER.info("Respuesta del servico curp : " + respuesta + " " + response.getStatus());
					JSONObject jsonRespuesta = new JSONObject(respuesta);

					if (jsonRespuesta.get("statusOper").toString().compareTo("EXITOSO") != 0) {
						FacesMessage message = new FacesMessage("CURP no válida.",
								"La CURP ingresada no es válida.");
						message.setSeverity(FacesMessage.SEVERITY_ERROR);

						throw new ValidatorException(message);
					}
				} catch (Exception e) {
					LOGGER.error("Error en validator de CURP:: ", e);
					FacesMessage message = new FacesMessage("Error en validación de la CURP",
							"No fue posible realizar la validación de la CURP, por favor intente más tarde.");
					message.setSeverity(FacesMessage.SEVERITY_ERROR);

					throw new ValidatorException(message);
				}
			}		
		} 	
	}	
}

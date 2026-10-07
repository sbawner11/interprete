package mx.gob.atdt.interprete.application;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.dto.ConfiguracionWebhookDTO;


@Named
@ApplicationScoped
public class ConfiguracionWebhookApplication  implements Serializable{

	
	private static final long serialVersionUID = -5786206109640157155L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracionWebhookApplication.class);

	private ConfiguracionWebhookDTO configuracionWebhookDTO;
	
	/**GETTER´s y SETTER´s**/
	
	/**
	 * @return the configuracionWebhookDTO
	 */
	public ConfiguracionWebhookDTO getConfiguracionWebhookDTO() {
		return configuracionWebhookDTO;
	}

	/**
	 * @param configuracionWebhookDTO the configuracionWebhookDTO to set
	 */
	public void setConfiguracionWebhookDTO(ConfiguracionWebhookDTO configuracionWebhookDTO) {
		this.configuracionWebhookDTO = configuracionWebhookDTO;
	}
	
	/**
	 * 
	 * @return usuario
	 */
	public String getUsuario() {
		String usuario = "no_definido";
		if(configuracionWebhookDTO != null) {
			usuario = configuracionWebhookDTO.getUsuario();
		}
		return usuario;
	}
	
	/**
	 * 
	 * @return contrasenia
	 */
	public String getContrasenia() {
		String contrasenia = "no_definido";
		if(configuracionWebhookDTO != null) {
			contrasenia = configuracionWebhookDTO.getContrasenia();
		}
		return contrasenia;
	}	
}

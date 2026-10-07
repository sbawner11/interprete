package mx.gob.atdt.interprete.application;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.dto.DetSecurityDomainLineasCapturaDTO;

@Named
@ApplicationScoped
public class DetSecurityDomainLineasCapturaApplication  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DetSecurityDomainLineasCapturaApplication.class);

	private DetSecurityDomainLineasCapturaDTO securityDomainLineasCapturaDTO;
	
	
	
	/**GETTER´s y SETTER´s**/
	
	/**
	 * 
	 * @return security Lineas Captura
	 */
	public DetSecurityDomainLineasCapturaDTO getSecurityDomainLineasCapturaDTO() {
		return securityDomainLineasCapturaDTO;
	}
	
	/**
	 * 
	 * @param securityDomainLineasCapturaDTO
	 */
	public void setSecurityDomainLineasCapturaDTO(DetSecurityDomainLineasCapturaDTO securityDomainLineasCapturaDTO) {
		this.securityDomainLineasCapturaDTO = securityDomainLineasCapturaDTO;
	}

	/**
	 * 
	 * @return usuario
	 */
	public String getUsuario() {
		String usuario = "no_definido";
		if(securityDomainLineasCapturaDTO != null) {
			usuario = securityDomainLineasCapturaDTO.getUsuario();
		}
		return usuario;
	}
	
	/**
	 * 
	 * @return contrasenia
	 */
	public String getContrasenia() {
		String contrasenia = "no_definido";
		if(securityDomainLineasCapturaDTO != null) {
			contrasenia = securityDomainLineasCapturaDTO.getContrasenia();
		}
		return contrasenia;
	}
}

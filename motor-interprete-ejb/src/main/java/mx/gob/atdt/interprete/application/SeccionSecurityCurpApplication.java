package mx.gob.atdt.interprete.application;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.dto.DetSecurityDomainDTO;

@Named
@ApplicationScoped
public class SeccionSecurityCurpApplication implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8825862951087664826L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SeccionSecurityCurpApplication.class);

	private DetSecurityDomainDTO  detSecurityDomainCurpDTO;	
	private String usuarioDominoSeg;
	private String contrasenaDominioSeg;
	
	/**GETTER´s y SETTER´s**/
	
	/**
	 * @return the detSecurityDomainCurpDTO
	 */
	public DetSecurityDomainDTO getDetSecurityDomainCurpDTO() {
		return detSecurityDomainCurpDTO;
	}

	/**
	 * @param detSecurityDomainCurpDTO the detSecurityDomainCurpDTO to set
	 */
	public void setDetSecurityDomainCurpDTO(DetSecurityDomainDTO detSecurityDomainCurpDTO) {
		this.detSecurityDomainCurpDTO = detSecurityDomainCurpDTO;
	}	

		
	/**
	 * @return the usuarioDominoSeg
	 */
	public String getUsuarioDominoSeg() {
		if(detSecurityDomainCurpDTO != null) {
			usuarioDominoSeg = detSecurityDomainCurpDTO.getUsuario();
		} else {
			usuarioDominoSeg = "no_definido";
		}
		return usuarioDominoSeg;
	}

	/**
	 * @return the contrasenaDominioSeg
	 */
	public String getContrasenaDominioSeg() {
		if(detSecurityDomainCurpDTO != null) {
			contrasenaDominioSeg = detSecurityDomainCurpDTO.getContrasenia();
		} else {
			contrasenaDominioSeg = "no_definido";
		}
		return contrasenaDominioSeg;
	}
}

package mx.gob.atdt.interprete.application;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.dto.DetAccesoLLaveDTO;

@Named
@ApplicationScoped
public class SeccionAccesoLlaveApplication implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2432646518122838301L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SeccionAccesoLlaveApplication.class);

	private DetAccesoLLaveDTO accesoLlaveDTO;
	private String usuarioDominoSeg;
	private String contrasenaDominioSeg;
	
	/**GETTER´s y SETTER´s**/

	/**
	 * @return the accesoLlaveDTO
	 */
	public DetAccesoLLaveDTO getAccesoLlaveDTO() {
		return accesoLlaveDTO;
	}

	/**
	 * @param accesoLlaveDTO the accesoLlaveDTO to set
	 */
	public void setAccesoLlaveDTO(DetAccesoLLaveDTO accesoLlaveDTO) {
		this.accesoLlaveDTO = accesoLlaveDTO;
	}
	
	/**
	 * @return the usuarioDominoSeg
	 */
	public String getUsuarioDominoSeg() {
		if(accesoLlaveDTO != null) {
			usuarioDominoSeg = accesoLlaveDTO.getUsuarioDominoSeg();
		} else {
			usuarioDominoSeg = "no_definido";
		}
		return usuarioDominoSeg;
	}

	/**
	 * @return the contrasenaDominioSeg
	 */
	public String getContrasenaDominioSeg() {
		if(accesoLlaveDTO != null) {
			contrasenaDominioSeg = accesoLlaveDTO.getContrasenaDominioSeg();
		} else {
			contrasenaDominioSeg = "no_definido";
		}
		return contrasenaDominioSeg;
	}	

}

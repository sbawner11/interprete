package mx.gob.atdt.interprete.application;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.dto.DetFirmaDigitalDTO;

@Named
@ApplicationScoped
public class SeccionFirmaDigitalApplication implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8789450152328914765L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SeccionFirmaDigitalApplication.class);
	private DetFirmaDigitalDTO firmaDigitalDTO;
	private String usuarioDominioFirma;
	private String contraseniaDominioFirma;

	/**
	 * @return the firmaDigitalDTO
	 */
	public DetFirmaDigitalDTO getFirmaDigitalDTO() {
		return firmaDigitalDTO;
	}

	/**
	 * @param firmaDigitalDTO the firmaDigitalDTO to set
	 */
	public void setFirmaDigitalDTO(DetFirmaDigitalDTO firmaDigitalDTO) {
		this.firmaDigitalDTO = firmaDigitalDTO;
	}

	/**
	 * @return the usuarioDominioFirma
	 */
	public String getUsuarioDominioFirma() {
		if (firmaDigitalDTO != null) {
			usuarioDominioFirma = firmaDigitalDTO.getUsuarioDominioSeg();
		} else {
			usuarioDominioFirma = "no_definido";
		}
		return usuarioDominioFirma;
	}

	/**
	 * @return the contraseniaDominioFirma
	 */
	public String getContraseniaDominioFirma() {
		if (firmaDigitalDTO != null) {
			contraseniaDominioFirma = firmaDigitalDTO.getContrasenaDominioSeg();
		} else {
			contraseniaDominioFirma = "no_definido";
		}
		return contraseniaDominioFirma;
	}

}

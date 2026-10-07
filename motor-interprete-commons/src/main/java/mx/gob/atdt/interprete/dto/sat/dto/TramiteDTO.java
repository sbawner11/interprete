package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TramiteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6230095975613480641L;
	
	@JsonProperty("Tramite")
	private List<DetalleTramiteDTO> tramite;

	/**
	 * 
	 */
	public TramiteDTO() {
		tramite = new ArrayList<>();
	}

	/**
	 * @return the tramite
	 */
	public List<DetalleTramiteDTO> getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
	 */
	public void setTramite(List<DetalleTramiteDTO> tramite) {
		this.tramite = tramite;
	}	
}

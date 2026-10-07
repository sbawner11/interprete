package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AgrupadorDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7987093732487851021L;

	@JsonProperty("IdAgrupador")
	private Integer idAgrupador;
	
	@JsonProperty("TipoAgrupador")
	private String tipoAgrupador;
	
	/**
	 * 
	 */
	public AgrupadorDTO() {
		// Constructo vacío para inicialización por default
	}

	/**
	 * @return the idAgrupador
	 */
	public Integer getIdAgrupador() {
		return idAgrupador;
	}

	/**
	 * @param idAgrupador the idAgrupador to set
	 */
	public void setIdAgrupador(Integer idAgrupador) {
		this.idAgrupador = idAgrupador;
	}

	/**
	 * @return the tipoAgrupador
	 */
	public String getTipoAgrupador() {
		return tipoAgrupador;
	}

	/**
	 * @param tipoAgrupador the tipoAgrupador to set
	 */
	public void setTipoAgrupador(String tipoAgrupador) {
		this.tipoAgrupador = tipoAgrupador;
	}	
}

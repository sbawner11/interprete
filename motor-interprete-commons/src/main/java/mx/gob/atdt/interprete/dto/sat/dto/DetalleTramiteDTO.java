package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetalleTramiteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 617907586903499170L;
	
	@JsonProperty("NumeroTramite")
	private Integer numeroTramite;
	
	@JsonProperty("Homoclave")
	private String homoClave;
	
	@JsonProperty("Variante")
	private String variante;
	
	@JsonProperty("NumeroConceptos")
	private Integer numeroConceptos;
	
	@JsonProperty("TotalTramite")
	private Integer totalTramite;
	
	@JsonProperty("Conceptos")
	private ConceptoDTO conceptos;
		
	/**
	 * 
	 */
	public DetalleTramiteDTO() {
		this.conceptos = new ConceptoDTO();
	}

	/**
	 * @return the numeroTramite
	 */
	public Integer getNumeroTramite() {
		return numeroTramite;
	}

	/**
	 * @param numeroTramite the numeroTramite to set
	 */
	public void setNumeroTramite(Integer numeroTramite) {
		this.numeroTramite = numeroTramite;
	}

	/**
	 * @return the homoClave
	 */
	public String getHomoClave() {
		return homoClave;
	}

	/**
	 * @param homoClave the homoClave to set
	 */
	public void setHomoClave(String homoClave) {
		this.homoClave = homoClave;
	}

	/**
	 * @return the variante
	 */
	public String getVariante() {
		return variante;
	}

	/**
	 * @param variante the variante to set
	 */
	public void setVariante(String variante) {
		this.variante = variante;
	}

	/**
	 * @return the numeroConceptos
	 */
	public Integer getNumeroConceptos() {
		return numeroConceptos;
	}

	/**
	 * @param numeroConceptos the numeroConceptos to set
	 */
	public void setNumeroConceptos(Integer numeroConceptos) {
		this.numeroConceptos = numeroConceptos;
	}

	/**
	 * @return the totalTramite
	 */
	public Integer getTotalTramite() {
		return totalTramite;
	}

	/**
	 * @param totalTramite the totalTramite to set
	 */
	public void setTotalTramite(Integer totalTramite) {
		this.totalTramite = totalTramite;
	}

	/**
	 * @return the conceptos
	 */
	public ConceptoDTO getConceptos() {
		return conceptos;
	}

	/**
	 * @param conceptos the conceptos to set
	 */
	public void setConceptos(ConceptoDTO conceptos) {
		this.conceptos = conceptos;
	}	
}

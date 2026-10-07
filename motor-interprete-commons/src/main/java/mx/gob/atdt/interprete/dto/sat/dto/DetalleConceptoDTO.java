package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetalleConceptoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6869145921864213188L;

	@JsonProperty("NumeroSecuencia")
	private Integer numeroSecuencia;
	
	@JsonProperty("ClaveConcepto")
	private String claveConcepto;
	
	@JsonProperty("TotalContribuciones")
	private Integer totalContribuciones;
	
	@JsonProperty("TotalConcepto")
	private Integer totalConcepto;
	
	@JsonProperty("Agrupador")
	private AgrupadorDTO agrupador;
	
	@JsonProperty("DatosIcep")
	private DatosIcepDTO datosIcep;
	
	@JsonProperty("DP")
	private TransaccionPDTO dP;
	
	/**
	 * 
	 */
	public DetalleConceptoDTO() {
		agrupador = new AgrupadorDTO();
		datosIcep = new DatosIcepDTO();
		dP = new TransaccionPDTO();
	}

	/**
	 * @return the numeroSecuencia
	 */
	public Integer getNumeroSecuencia() {
		return numeroSecuencia;
	}

	/**
	 * @param numeroSecuencia the numeroSecuencia to set
	 */
	public void setNumeroSecuencia(Integer numeroSecuencia) {
		this.numeroSecuencia = numeroSecuencia;
	}

	/**
	 * @return the claveConcepto
	 */
	public String getClaveConcepto() {
		return claveConcepto;
	}

	/**
	 * @param claveConcepto the claveConcepto to set
	 */
	public void setClaveConcepto(String claveConcepto) {
		this.claveConcepto = claveConcepto;
	}

	/**
	 * @return the totalContribuciones
	 */
	public Integer getTotalContribuciones() {
		return totalContribuciones;
	}

	/**
	 * @param totalContribuciones the totalContribuciones to set
	 */
	public void setTotalContribuciones(Integer totalContribuciones) {
		this.totalContribuciones = totalContribuciones;
	}

	/**
	 * @return the totalConcepto
	 */
	public Integer getTotalConcepto() {
		return totalConcepto;
	}

	/**
	 * @param totalConcepto the totalConcepto to set
	 */
	public void setTotalConcepto(Integer totalConcepto) {
		this.totalConcepto = totalConcepto;
	}

	/**
	 * @return the agrupador
	 */
	public AgrupadorDTO getAgrupador() {
		return agrupador;
	}

	/**
	 * @param agrupador the agrupador to set
	 */
	public void setAgrupador(AgrupadorDTO agrupador) {
		this.agrupador = agrupador;
	}

	/**
	 * @return the datosIcep
	 */
	public DatosIcepDTO getDatosIcep() {
		return datosIcep;
	}

	/**
	 * @param datosIcep the datosIcep to set
	 */
	public void setDatosIcep(DatosIcepDTO datosIcep) {
		this.datosIcep = datosIcep;
	}

	/**
	 * @return the dP
	 */
	public TransaccionPDTO getdP() {
		return dP;
	}

	/**
	 * @param dP the dP to set
	 */
	public void setdP(TransaccionPDTO dP) {
		this.dP = dP;
	}
}

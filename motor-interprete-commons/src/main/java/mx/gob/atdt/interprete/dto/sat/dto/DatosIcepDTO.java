package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosIcepDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5839210080964842495L;
	
	@JsonProperty("ClavePeriodicidad")
	private String clavePeriodicidad;
	
	@JsonProperty("ClavePeriodo")
	private String clavePeriodo;
	
	@JsonProperty("FechaCausacion")
	private String fechaCausacion;
	
	/**
	 * 
	 */
	public DatosIcepDTO() {
		// Constructo vacío para inicialización por default
	}

	/**
	 * @return the clavePeriodicidad
	 */
	public String getClavePeriodicidad() {
		return clavePeriodicidad;
	}

	/**
	 * @param clavePeriodicidad the clavePeriodicidad to set
	 */
	public void setClavePeriodicidad(String clavePeriodicidad) {
		this.clavePeriodicidad = clavePeriodicidad;
	}

	/**
	 * @return the clavePeriodo
	 */
	public String getClavePeriodo() {
		return clavePeriodo;
	}

	/**
	 * @param clavePeriodo the clavePeriodo to set
	 */
	public void setClavePeriodo(String clavePeriodo) {
		this.clavePeriodo = clavePeriodo;
	}

	/**
	 * @return the fechaCausacion
	 */
	public String getFechaCausacion() {
		return fechaCausacion;
	}

	/**
	 * @param fechaCausacion the fechaCausacion to set
	 */
	public void setFechaCausacion(String fechaCausacion) {
		this.fechaCausacion = fechaCausacion;
	}	
}

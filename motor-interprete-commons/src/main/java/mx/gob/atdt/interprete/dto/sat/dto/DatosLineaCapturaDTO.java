package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosLineaCapturaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2521286229126170967L;

	@JsonProperty("FechaSolicitud")
	private String fechaSolicitud;
	
	@JsonProperty("Importe")
	private Integer importe;
	
	@JsonProperty("FechaVigencia")
	private String fechaVigencia;
	
	/**
	 * 
	 */
	public DatosLineaCapturaDTO() {
		// Constructo vacío para inicialización por default
	}

	/**
	 * @return the fechaSolicitud
	 */
	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * @param fechaSolicitud the fechaSolicitud to set
	 */
	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	/**
	 * @return the importe
	 */
	public Integer getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Integer importe) {
		this.importe = importe;
	}

	/**
	 * @return the fechaVigencia
	 */
	public String getFechaVigencia() {
		return fechaVigencia;
	}

	/**
	 * @param fechaVigencia the fechaVigencia to set
	 */
	public void setFechaVigencia(String fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}	
	
}

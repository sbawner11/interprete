package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestGenerarLineaCapturaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9222392881048350595L;

	@JsonProperty("DatosGenerales")
	private DatosGeneralesDTO datosGenerales;
	@JsonProperty("Tramites")
	private TramiteDTO tramites;
	
	/**
	 * 
	 */
	public RequestGenerarLineaCapturaDTO() {
		datosGenerales = new DatosGeneralesDTO();
		tramites = new TramiteDTO();
	}

	/**
	 * @return the datosGenerales
	 */
	public DatosGeneralesDTO getDatosGenerales() {
		return datosGenerales;
	}

	/**
	 * @param datosGenerales the datosGenerales to set
	 */
	public void setDatosGenerales(DatosGeneralesDTO datosGenerales) {
		this.datosGenerales = datosGenerales;
	}

	/**
	 * @return the tramites
	 */
	public TramiteDTO getTramites() {
		return tramites;
	}

	/**
	 * @param tramite the tramites to set
	 */
	public void setTramites(TramiteDTO tramites) {
		this.tramites = tramites;
	}	
}

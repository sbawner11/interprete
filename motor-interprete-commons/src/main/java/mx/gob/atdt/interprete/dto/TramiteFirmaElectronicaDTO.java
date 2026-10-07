package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class TramiteFirmaElectronicaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2530311247845945166L;
		
	private Long idTramiteFirma;
	private String cadenaOriginal;
	private String cadenaFirmada;
	private String nombreFirmante;
	private Date fechaCreacion;
	private String respuestaServicio;
	private boolean firmaCiudadano;
	private TramiteDTO tramiteDTO;
	
	/**
	 * 
	 */
	public TramiteFirmaElectronicaDTO() {
		tramiteDTO = new TramiteDTO();
	}

	/**
	 * @return the idTramiteFirma
	 */
	public Long getIdTramiteFirma() {
		return idTramiteFirma;
	}

	/**
	 * @param idTramiteFirma the idTramiteFirma to set
	 */
	public void setIdTramiteFirma(Long idTramiteFirma) {
		this.idTramiteFirma = idTramiteFirma;
	}

	/**
	 * @return the cadenaOriginal
	 */
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	/**
	 * @param cadenaOriginal the cadenaOriginal to set
	 */
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	/**
	 * @return the cadenaFirmada
	 */
	public String getCadenaFirmada() {
		return cadenaFirmada;
	}

	/**
	 * @param cadenaFirmada the cadenaFirmada to set
	 */
	public void setCadenaFirmada(String cadenaFirmada) {
		this.cadenaFirmada = cadenaFirmada;
	}
	
	/**
	 * @return the nombreFirmante
	 */
	public String getNombreFirmante() {
		return nombreFirmante;
	}

	/**
	 * @param nombreFirmante the nombreFirmante to set
	 */
	public void setNombreFirmante(String nombreFirmante) {
		this.nombreFirmante = nombreFirmante;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the respuestaServicio
	 */
	public String getRespuestaServicio() {
		return respuestaServicio;
	}

	/**
	 * @param respuestaServicio the respuestaServicio to set
	 */
	public void setRespuestaServicio(String respuestaServicio) {
		this.respuestaServicio = respuestaServicio;
	}
	
	public boolean isFirmaCiudadano() {
		return firmaCiudadano;
	}

	public void setFirmaCiudadano(boolean firmaCiudadano) {
		this.firmaCiudadano = firmaCiudadano;
	}

	/**
	 * @return the tramiteDTO
	 */
	public TramiteDTO getTramiteDTO() {
		return tramiteDTO;
	}

	/**
	 * @param tramiteDTO the tramiteDTO to set
	 */
	public void setTramiteDTO(TramiteDTO tramiteDTO) {
		this.tramiteDTO = tramiteDTO;
	}	
}

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseServiceConsultaFirmaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1950226556670098919L;
	private String code;
	private String status;
	private String idSolicitud;
	private String numeroSerieCer;
	private String identificadorCer;
	private String nombreFirmante;
	private boolean firmaCiudadano;
	
	//Variable auxiliar para registrar la respuesta completa del servicio.
	private String respuestaServicio;
	private List<CadenasDigitalesDTO> lstCadenaDigitales;

	public ResponseServiceConsultaFirmaDTO() {
		lstCadenaDigitales = new ArrayList<CadenasDigitalesDTO>();
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the idSolicitud
	 */
	public String getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * @return the numeroSerieCer
	 */
	public String getNumeroSerieCer() {
		return numeroSerieCer;
	}

	/**
	 * @param numeroSerieCer the numeroSerieCer to set
	 */
	public void setNumeroSerieCer(String numeroSerieCer) {
		this.numeroSerieCer = numeroSerieCer;
	}

	/**
	 * @return the identificadorCer
	 */
	public String getIdentificadorCer() {
		return identificadorCer;
	}

	/**
	 * @param identificadorCer the identificadorCer to set
	 */
	public void setIdentificadorCer(String identificadorCer) {
		this.identificadorCer = identificadorCer;
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
	
	public boolean isFirmaCiudadano() {
		return firmaCiudadano;
	}

	public void setFirmaCiudadano(boolean firmaCiudadano) {
		this.firmaCiudadano = firmaCiudadano;
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

	/**
	 * @return the lstCadenaDigitales
	 */
	public List<CadenasDigitalesDTO> getLstCadenaDigitales() {
		return lstCadenaDigitales;
	}

	/**
	 * @param lstCadenaDigitales the lstCadenaDigitales to set
	 */
	public void setLstCadenaDigitales(List<CadenasDigitalesDTO> lstCadenaDigitales) {
		this.lstCadenaDigitales = lstCadenaDigitales;
	}

	@Override
	public String toString() {
		return "ResponseServiceConsultaFirmaDTO [code=" + code + ", status=" + status + ", idSolicitud=" + idSolicitud
				+ ", numeroSerieCer=" + numeroSerieCer + ", identificadorCer=" + identificadorCer
				+ ", lstCadenaDigitales=" + lstCadenaDigitales + ", firmaCiudadano=" + firmaCiudadano + "]";
	}
}

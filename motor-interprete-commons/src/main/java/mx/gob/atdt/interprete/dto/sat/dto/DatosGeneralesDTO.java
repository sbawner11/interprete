package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DatosGeneralesDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5560533942143596297L;
	
	@JsonProperty("Solicitud")
	private String solicitud;
	
	@JsonProperty("CveDependencia")
	private String cveDependencia;
	
	@JsonProperty("UnidadAdministrativa")
	private String unidadAdministrativa;
	
	@JsonProperty("TipoPersona")
	private String tipoPersona;
	
	@JsonProperty("RFC")
	private String rfc;
	
	@JsonProperty("Curp")
	private String curp;
	
	@JsonProperty("Nombre")
	private String nombre;
	
	@JsonProperty("ApellidoPaterno")
	private String apellidoPaterno;
	
	@JsonProperty("ApellidoMaterno")
	private String apellidoMaterno;
	
	@JsonProperty("DatosLineaCaptura")
	private DatosLineaCapturaDTO datosLineaCapturaDTO;
	
	/**
	 * 
	 */
	public DatosGeneralesDTO() {
		datosLineaCapturaDTO = new DatosLineaCapturaDTO();
	}

	/**
	 * @return the solicitud
	 */
	public String getSolicitud() {
		return solicitud;
	}

	/**
	 * @param solicitud the solicitud to set
	 */
	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}

	/**
	 * @return the cveDependencia
	 */
	public String getCveDependencia() {
		return cveDependencia;
	}

	/**
	 * @param cveDependencia the cveDependencia to set
	 */
	public void setCveDependencia(String cveDependencia) {
		this.cveDependencia = cveDependencia;
	}

	/**
	 * @return the unidadAdministrativa
	 */
	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}

	/**
	 * @param unidadAdministrativa the unidadAdministrativa to set
	 */
	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}

	/**
	 * @return the tipoPersona
	 */
	public String getTipoPersona() {
		return tipoPersona;
	}

	/**
	 * @param tipoPersona the tipoPersona to set
	 */
	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the apellidoPaterno
	 */
	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	/**
	 * @param apellidoPaterno the apellidoPaterno to set
	 */
	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	/**
	 * @return the apellidoMaterno
	 */
	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	/**
	 * @param apellidoMaterno the apellidoMaterno to set
	 */
	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	/**
	 * @return the datosLineaCapturaDTO
	 */
	public DatosLineaCapturaDTO getDatosLineaCapturaDTO() {
		return datosLineaCapturaDTO;
	}

	/**
	 * @param datosLineaCapturaDTO the datosLineaCapturaDTO to set
	 */
	public void setDatosLineaCapturaDTO(DatosLineaCapturaDTO datosLineaCapturaDTO) {
		this.datosLineaCapturaDTO = datosLineaCapturaDTO;
	}
}

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class ComponenteDatosPersonaMoralDTO extends ComponenteDTO implements Serializable{
		
	private static final long serialVersionUID = 1405503283626244872L;
	
	private Long idComponenteDatosPersonaMoral;
	private boolean habilitaRfc;
	private boolean habilitaPersonaMoral;
	private boolean habilitaFechaVigencia;
	
	/* Variables para colocar el valor ingresado como respuesta */
	private Long idPersonaMoral;
	private String rfc;
	private String razonSocial;
	private String vigenciaCertificado;
	private boolean certificadoVigente;
	
	/**
	 * @return the idPersonaMoral
	 */
	public final Long getIdPersonaMoral() {
		return idPersonaMoral;
	}

	/**
	 * @param idPersonaMoral the idPersonaMoral to set
	 */
	public final void setIdPersonaMoral(Long idPersonaMoral) {
		this.idPersonaMoral = idPersonaMoral;
	}

	/**
	 * @return the rfc
	 */
	public final String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc the rfc to set
	 */
	public final void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @return the razonSocial
	 */
	public final String getRazonSocial() {
		return razonSocial;
	}

	/**
	 * @param razonSocial the razonSocial to set
	 */
	public final void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	/**
	 * @return the vigenciaCertificado
	 */
	public final String getVigenciaCertificado() {
		return vigenciaCertificado;
	}

	/**
	 * @param vigenciaCertificado the vigenciaCertificado to set
	 */
	public final void setVigenciaCertificado(String vigenciaCertificado) {
		this.vigenciaCertificado = vigenciaCertificado;
	}

	/**
	 * @return the certificadoVigente
	 */
	public final boolean isCertificadoVigente() {
		return certificadoVigente;
	}

	/**
	 * @param certificadoVigente the certificadoVigente to set
	 */
	public final void setCertificadoVigente(boolean certificadoVigente) {
		this.certificadoVigente = certificadoVigente;
	}

	/**
	 * 
	 */
	public ComponenteDatosPersonaMoralDTO() {
		super();
	}
	
	/**
	 * Constructor utilizado por la NamedQuery ComponenteDatosPersonaMoral.findByIdComponente
	 * 
	 * @param idComponenteDatosPersonaMoral
	 * @param idComponente
	 * @param idTipoComponente
	 * @param idSubseccionFormulario
	 * @param orden
	 * @param requerido
	 * @param tooltip
	 * @param descripcionTooltip
	 * @param tituloCampo
	 * @param activo
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param seccionSincronizada
	 * @param habilitaRfc
	 * @param habilitaPersonaMoral
	 * @param habilitaFechaVigencia
	 */
	@SuppressWarnings({"java:S107"})
	public ComponenteDatosPersonaMoralDTO(Long idComponenteDatosPersonaMoral, Long idComponente, Integer idTipoComponente,
			Long idSubseccionFormulario, int orden, boolean requerido, boolean tooltip, String descripcionTooltip, 
			String tituloCampo, boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion, boolean seccionSincronizada,
			boolean habilitaRfc, boolean habilitaPersonaMoral, boolean habilitaFechaVigencia) {
		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip,
				descripcionTooltip, tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		this.idComponenteDatosPersonaMoral = idComponenteDatosPersonaMoral;
		this.habilitaRfc = habilitaRfc;
		this.habilitaPersonaMoral = habilitaPersonaMoral;
		this.habilitaFechaVigencia = habilitaFechaVigencia;
	}

	/**
	 * @return the idComponenteDatosPersonaMoral
	 */
	public Long getIdComponenteDatosPersonaMoral() {
		return idComponenteDatosPersonaMoral;
	}

	/**
	 * @param idComponenteDatosPersonaMoral the idComponenteDatosPersonaMoral to set
	 */
	public void setIdComponenteDatosPersonaMoral(Long idComponenteDatosPersonaMoral) {
		this.idComponenteDatosPersonaMoral = idComponenteDatosPersonaMoral;
	}

	/**
	 * @return the habilitaRfc
	 */
	public boolean isHabilitaRfc() {
		return habilitaRfc;
	}

	/**
	 * @param habilitaRfc the habilitaRfc to set
	 */
	public void setHabilitaRfc(boolean habilitaRfc) {
		this.habilitaRfc = habilitaRfc;
	}

	/**
	 * @return the habilitaPersonaMoral
	 */
	public boolean isHabilitaPersonaMoral() {
		return habilitaPersonaMoral;
	}

	/**
	 * @param habilitaPersonaMoral the habilitaPersonaMoral to set
	 */
	public void setHabilitaPersonaMoral(boolean habilitaPersonaMoral) {
		this.habilitaPersonaMoral = habilitaPersonaMoral;
	}

	/**
	 * @return the habilitaFechaVigencia
	 */
	public boolean isHabilitaFechaVigencia() {
		return habilitaFechaVigencia;
	}

	/**
	 * @param habilitaFechaVigencia the habilitaFechaVigencia to set
	 */
	public void setHabilitaFechaVigencia(boolean habilitaFechaVigencia) {
		this.habilitaFechaVigencia = habilitaFechaVigencia;
	}			
}

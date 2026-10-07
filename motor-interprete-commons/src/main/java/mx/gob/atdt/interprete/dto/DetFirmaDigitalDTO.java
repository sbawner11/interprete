package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetFirmaDigitalDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1792721502965289617L;

	private Long idDetalleFirma;
	private ProyectoDTO proyectoDTO;
	private String claveSistema;
	private String urlFirmado;
	private String urlRedirecciona;
	private String usuarioDominioSeg;
	private String contrasenaDominioSeg;	
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	private boolean firmaCiudadano;

	/**
	 * 
	 */
	public DetFirmaDigitalDTO() {
	}
	
	public DetFirmaDigitalDTO(boolean firmaCiudadano) {
		this.firmaCiudadano = firmaCiudadano;
	}

	/**
	 * @param idProyecto
	 * @param idDetalleFirma
	 */
	public DetFirmaDigitalDTO(Long idProyecto, Long idDetalleFirma) {
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.idDetalleFirma = idDetalleFirma;
	}
	
	/**
	 * Constructor utilizado por la NamedQuery DetFirmaDigital.findByIdProyecto y
	 * DetFirmaDigital.existeDetalleActivoIdProyecto
	 * 
	 * @param idDetalleFirma
	 * @param idProyecto
	 * @param claveSistema
	 * @param urlFirmado
	 * @param urlRedirecciona
	 * @param usuarioDominioSeg
	 * @param contrasenaDominioSeg
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 * @param firmaCiudadano
	 */
	public DetFirmaDigitalDTO(Long idDetalleFirma, Long idProyecto, String claveSistema, String urlFirmado, String urlRedirecciona, 
			String usuarioDominioSeg, String contrasenaDominioSeg, Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo, 
			boolean seccionSincronizada, boolean firmaCiudadano) {
		this.idDetalleFirma = idDetalleFirma;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.claveSistema = claveSistema;
		this.urlFirmado = urlFirmado;
		this.urlRedirecciona = urlRedirecciona;
		this.usuarioDominioSeg = usuarioDominioSeg;
		this.contrasenaDominioSeg = contrasenaDominioSeg;		
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		this.firmaCiudadano = firmaCiudadano;
	}

	/**
	 * @return the idDetalleFirma
	 */
	public Long getIdDetalleFirma() {
		return idDetalleFirma;
	}

	/**
	 * @param idDetalleFirma the idDetalleFirma to set
	 */
	public void setIdDetalleFirma(Long idDetalleFirma) {
		this.idDetalleFirma = idDetalleFirma;
	}

	/**
	 * @return the proyectoDTO
	 */
	public ProyectoDTO getProyectoDTO() {
		return proyectoDTO;
	}

	/**
	 * @param proyectoDTO the proyectoDTO to set
	 */
	public void setProyectoDTO(ProyectoDTO proyectoDTO) {
		this.proyectoDTO = proyectoDTO;
	}

	/**
	 * @return the claveSistema
	 */
	public String getClaveSistema() {
		return claveSistema;
	}

	/**
	 * @param claveSistema the claveSistema to set
	 */
	public void setClaveSistema(String claveSistema) {
		this.claveSistema = claveSistema;
	}

	/**
	 * @return the urlFirmado
	 */
	public String getUrlFirmado() {
		return urlFirmado;
	}

	/**
	 * @param urlFirmado the urlFirmado to set
	 */
	public void setUrlFirmado(String urlFirmado) {
		this.urlFirmado = urlFirmado;
	}

	/**
	 * @return the urlRedirecciona
	 */
	public String getUrlRedirecciona() {
		return urlRedirecciona;
	}

	/**
	 * @param urlRedirecciona the urlRedirecciona to set
	 */
	public void setUrlRedirecciona(String urlRedirecciona) {
		this.urlRedirecciona = urlRedirecciona;
	}

	/**
	 * @return the usuarioDominioSeg
	 */
	public String getUsuarioDominioSeg() {
		return usuarioDominioSeg;
	}

	/**
	 * @param usuarioDominioSeg the usuarioDominioSeg to set
	 */
	public void setUsuarioDominioSeg(String usuarioDominioSeg) {
		this.usuarioDominioSeg = usuarioDominioSeg;
	}

	/**
	 * @return the contrasenaDominioSeg
	 */
	public String getContrasenaDominioSeg() {
		return contrasenaDominioSeg;
	}

	/**
	 * @param contrasenaDominioSeg the contrasenaDominioSeg to set
	 */
	public void setContrasenaDominioSeg(String contrasenaDominioSeg) {
		this.contrasenaDominioSeg = contrasenaDominioSeg;
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
	 * @return the fechaUltimaActualizacion
	 */
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	/**
	 * @param fechaUltimaActualizacion the fechaUltimaActualizacion to set
	 */
	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	/**
	 * @return the seccionSincronizada
	 */
	public boolean isSeccionSincronizada() {
		return seccionSincronizada;
	}

	/**
	 * @param seccionSincronizada the seccionSincronizada to set
	 */
	public void setSeccionSincronizada(boolean seccionSincronizada) {
		this.seccionSincronizada = seccionSincronizada;
	}
	
	public boolean isFirmaCiudadano() {
		return firmaCiudadano;
	}

	public void setFirmaCiudadano(boolean firmaCiudadano) {
		this.firmaCiudadano = firmaCiudadano;
	}

	@Override
	public String toString() {
		return "DetFirmaDigitalDTO [idDetalleFirma=" + idDetalleFirma + ", proyectoDTO=" + proyectoDTO
				+ ", claveSistema=" + claveSistema + ", urlFirmado=" + urlFirmado + ", urlRedirecciona="
				+ urlRedirecciona + ", usuarioDominioSeg=" + usuarioDominioSeg + ", contrasenaDominioSeg="
				+ contrasenaDominioSeg + ", fechaCreacion=" + fechaCreacion + ", fechaUltimaActualizacion="
				+ fechaUltimaActualizacion + ", activo=" + activo + ", seccionSincronizada=" + seccionSincronizada
				+ ", firma_ciudadano=" + firmaCiudadano + "]";
	}

}

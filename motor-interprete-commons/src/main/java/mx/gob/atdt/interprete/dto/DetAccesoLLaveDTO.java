package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetAccesoLLaveDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4586767275043513845L;

	private Long idDetalleAcceso;
	private ProyectoDTO proyectoDTO;
	private String claveSistema;
	private String urlRedireccionar;
	private String usuarioDominoSeg;
	private String contrasenaDominioSeg;
	private String codigoSecreto;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean limitarUnicoTramite;
	private boolean seccionSincronizada;
	private boolean autenticacionCiudadano;
	private boolean validaRol;
	private String rolesPermitidos;

	/**
	 * 
	 */
	public DetAccesoLLaveDTO() {
	}

	/**
	 * Constructor utilizado por la NamedQuery
	 * DetalleAccesoLlave.existeDetalleIdProyecto
	 * 
	 * @param idProyecto
	 * @param idDetalleAcceso
	 */
	public DetAccesoLLaveDTO(Long idProyecto, Long idDetalleAcceso) {
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.idDetalleAcceso = idDetalleAcceso;
	}
	
	public DetAccesoLLaveDTO(boolean validaRol, String rolesPermitidos) {
		this.validaRol = validaRol;
		this.rolesPermitidos = rolesPermitidos;
	}

	/**
	 * @param idDetalleAcceso
	 * @param proyectoDTO
	 * @param sistema
	 * @param claveSistema
	 * @param urlRedireccionar
	 * @param usuarioDominoSeg
	 * @param contrasenaDominioSeg
	 * @param codigoSecreto
	 * @param urlHome
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 */
	public DetAccesoLLaveDTO(Long idDetalleAcceso, ProyectoDTO proyectoDTO, String claveSistema,
			String urlRedireccionar, String urlHome, String usuarioDominoSeg, String contrasenaDominioSeg,
			String codigoSecreto, Date fechaCreacion, Date fechaUltimaActualizacion, boolean validaRol, String rolesPermitidos) {
		this.idDetalleAcceso = idDetalleAcceso;
		this.proyectoDTO = proyectoDTO;
		this.claveSistema = claveSistema;
		this.urlRedireccionar = urlRedireccionar;
		this.usuarioDominoSeg = usuarioDominoSeg;
		this.contrasenaDominioSeg = contrasenaDominioSeg;
		this.codigoSecreto = codigoSecreto;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}
	
	public DetAccesoLLaveDTO(Long idProyecto, Long idDetalleAcceso, String claveSistema,
			String urlRedireccionar, String usuarioDominoSeg, String contrasenaDominioSeg, String codigoSecreto,
			Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo, boolean limitarUnicoTramite,
			boolean seccionSincronizada, boolean autenticacionCiudadano, boolean validaRol, String rolesPermitidos) {
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.idDetalleAcceso = idDetalleAcceso;
		this.claveSistema = claveSistema;
		this.urlRedireccionar = urlRedireccionar;
		this.usuarioDominoSeg = usuarioDominoSeg;
		this.contrasenaDominioSeg = contrasenaDominioSeg;
		this.codigoSecreto = codigoSecreto;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.limitarUnicoTramite = limitarUnicoTramite;
		this.seccionSincronizada = seccionSincronizada;
		this.autenticacionCiudadano = autenticacionCiudadano;
		this.validaRol = validaRol;
		this.rolesPermitidos = rolesPermitidos;
	}

	/**
	 * Constructor utilizado por la NamedQuery DetalleAccesoLlave.findByIdProyecto
	 * 
	 * @param idProyecto
	 * @param idDetalleAcceso
	 * @param sistema
	 * @param claveSistema
	 * @param urlRedireccionar
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	public DetAccesoLLaveDTO(Long idProyecto, Long idDetalleAcceso, String claveSistema, String urlRedireccionar,
			Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada,
			boolean autenticacionCiudadano, String usuarioDominoSeg, String contrasenaDominioSeg,
			String codigoSecreto) {
		this.idDetalleAcceso = idDetalleAcceso;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.claveSistema = claveSistema;
		this.urlRedireccionar = urlRedireccionar;
		this.usuarioDominoSeg = usuarioDominoSeg;
		this.contrasenaDominioSeg = contrasenaDominioSeg;
		this.codigoSecreto = codigoSecreto;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		this.autenticacionCiudadano = autenticacionCiudadano;
	}

	/**
	 * @return the idDetalleAcceso
	 */
	public Long getIdDetalleAcceso() {
		return idDetalleAcceso;
	}

	/**
	 * @param idDetalleAcceso the idDetalleAcceso to set
	 */
	public void setIdDetalleAcceso(Long idDetalleAcceso) {
		this.idDetalleAcceso = idDetalleAcceso;
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
	 * @return the urlRedireccionar
	 */
	public String getUrlRedireccionar() {
		return urlRedireccionar;
	}

	/**
	 * @param urlRedireccionar the urlRedireccionar to set
	 */
	public void setUrlRedireccionar(String urlRedireccionar) {
		this.urlRedireccionar = urlRedireccionar;
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
	 * @return the limitarUnicoTramite
	 */
	public boolean isLimitarUnicoTramite() {
		return limitarUnicoTramite;
	}

	/**
	 * @param limitarUnicoTramite the limitarUnicoTramite to set
	 */
	public void setLimitarUnicoTramite(boolean limitarUnicoTramite) {
		this.limitarUnicoTramite = limitarUnicoTramite;
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

	/**
	 * @return the autenticacionCiudadano
	 */
	public boolean isAutenticacionCiudadano() {
		return autenticacionCiudadano;
	}

	/**
	 * @param autenticacionCiudadano the autenticacionCiudadano to set
	 */
	public void setAutenticacionCiudadano(boolean autenticacionCiudadano) {
		this.autenticacionCiudadano = autenticacionCiudadano;
	}

	public String getUsuarioDominoSeg() {
		return usuarioDominoSeg;
	}

	public void setUsuarioDominoSeg(String usuarioDominoSeg) {
		this.usuarioDominoSeg = usuarioDominoSeg;
	}

	public String getContrasenaDominioSeg() {
		return contrasenaDominioSeg;
	}

	public void setContrasenaDominioSeg(String contrasenaDominioSeg) {
		this.contrasenaDominioSeg = contrasenaDominioSeg;
	}

	public String getCodigoSecreto() {
		return codigoSecreto;
	}

	public void setCodigoSecreto(String codigoSecreto) {
		this.codigoSecreto = codigoSecreto;
	}

	public boolean isValidaRol() {
		return validaRol;
	}

	public String getRolesPermitidos() {
		return rolesPermitidos;
	}

	public void setValidaRol(boolean validaRol) {
		this.validaRol = validaRol;
	}

	public void setRolesPermitidos(String rolesPermitidos) {
		this.rolesPermitidos = rolesPermitidos;
	}

	@Override
	public String toString() {
		return "DetAccesoLLaveDTO [idDetalleAcceso=" + idDetalleAcceso + ", proyectoDTO=" + proyectoDTO
				+ ", claveSistema=" + claveSistema + ", urlRedireccionar=" + urlRedireccionar + ", usuarioDominoSeg="
				+ usuarioDominoSeg + ", contrasenaDominioSeg=" + contrasenaDominioSeg + ", codigoSecreto="
				+ codigoSecreto + ", fechaCreacion=" + fechaCreacion + ", fechaUltimaActualizacion="
				+ fechaUltimaActualizacion + ", activo=" + activo + ", seccionSincronizada=" + seccionSincronizada
				+ ", autenticacionCiudadano=" + autenticacionCiudadano + "]";
	}
}
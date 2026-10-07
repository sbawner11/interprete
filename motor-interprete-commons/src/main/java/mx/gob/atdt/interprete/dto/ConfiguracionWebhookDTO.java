package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class ConfiguracionWebhookDTO implements Serializable {
	
	private static final long serialVersionUID = 8286194716523671098L;
	
	private long idConfiguracionWebhook;
	private ProyectoDTO proyectoDTO;
	private String usuario;
	private String contrasenia;
	private String urlAplicacionNotificaciones;
	private boolean habilitaEnvioNotificaciones;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	
	/**
	 * 
	 */
	public ConfiguracionWebhookDTO() {
	}

	/**
	 * @param idConfiguracionWebhook
	 * @param idProyecto
	 * @param usuario
	 * @param contrasenia
	 * @param urlAplicacionNotificaciones
	 * @param habilitaEnvioNotificaciones
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	@SuppressWarnings({"java:S107"})
	public ConfiguracionWebhookDTO(long idConfiguracionWebhook, Long idProyecto, String usuario,
			String contrasenia, String urlAplicacionNotificaciones, boolean habilitaEnvioNotificaciones,
			Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idConfiguracionWebhook = idConfiguracionWebhook;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
		this.urlAplicacionNotificaciones = urlAplicacionNotificaciones;
		this.habilitaEnvioNotificaciones = habilitaEnvioNotificaciones;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	/**
	 * @return the idConfiguracionWebhook
	 */
	public long getIdConfiguracionWebhook() {
		return idConfiguracionWebhook;
	}

	/**
	 * @param idConfiguracionWebhook the idConfiguracionWebhook to set
	 */
	public void setIdConfiguracionWebhook(long idConfiguracionWebhook) {
		this.idConfiguracionWebhook = idConfiguracionWebhook;
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
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * @return the urlAplicacionNotificaciones
	 */
	public String getUrlAplicacionNotificaciones() {
		return urlAplicacionNotificaciones;
	}

	/**
	 * @param urlAplicacionNotificaciones the urlAplicacionNotificaciones to set
	 */
	public void setUrlAplicacionNotificaciones(String urlAplicacionNotificaciones) {
		this.urlAplicacionNotificaciones = urlAplicacionNotificaciones;
	}

	/**
	 * @return the habilitaEnvioNotificaciones
	 */
	public boolean isHabilitaEnvioNotificaciones() {
		return habilitaEnvioNotificaciones;
	}

	/**
	 * @param habilitaEnvioNotificaciones the habilitaEnvioNotificaciones to set
	 */
	public void setHabilitaEnvioNotificaciones(boolean habilitaEnvioNotificaciones) {
		this.habilitaEnvioNotificaciones = habilitaEnvioNotificaciones;
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
}
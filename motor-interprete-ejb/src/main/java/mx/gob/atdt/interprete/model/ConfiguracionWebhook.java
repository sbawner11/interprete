package mx.gob.atdt.interprete.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "configuracion_webhook", schema = "motor_interprete")

@NamedQuery(name = "ConfiguracionWebhook.findByProyecto",
	query = "SELECT new mx.gob.atdt.interprete.dto.ConfiguracionWebhookDTO(" +
        "cwh.idConfiguracionWebhook, p.idProyecto, cwh.usuario, " +
        "cwh.contrasenia, cwh.urlAplicacionNotificaciones, cwh.habilitaEnvioNotificaciones, " +
        "cwh.fechaCreacion, cwh.fechaUltimaActualizacion, cwh.activo, cwh.seccionSincronizada) " +
        "FROM ConfiguracionWebhook cwh " +
        "JOIN cwh.proyecto p " +
        "WHERE p.idProyecto = :idProyecto ")
public class ConfiguracionWebhook implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4013391507087111292L;
	
	private long idConfiguracionWebhook;
	private Proyecto proyecto;
	private String usuario;
	private String contrasenia;
	private String urlAplicacionNotificaciones;
	private boolean habilitaEnvioNotificaciones;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;

	public ConfiguracionWebhook() {
	}

	@SuppressWarnings({"java:S107"})
	public ConfiguracionWebhook(long idConfiguracionWebhook, Proyecto proyecto, String usuario, String contrasenia,
			String urlAplicacionNotificaciones, boolean habilitaEnvioNotificaciones, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idConfiguracionWebhook = idConfiguracionWebhook;
		this.proyecto = proyecto;
		this.usuario = usuario;
		this.contrasenia = contrasenia;
		this.urlAplicacionNotificaciones = urlAplicacionNotificaciones;
		this.habilitaEnvioNotificaciones = habilitaEnvioNotificaciones;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	@Id
	@Column(name = "id_configuracion_webhook", unique = true, nullable = false)
	public long getIdConfiguracionWebhook() {
		return this.idConfiguracionWebhook;
	}

	public void setIdConfiguracionWebhook(long idConfiguracionWebhook) {
		this.idConfiguracionWebhook = idConfiguracionWebhook;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "usuario", nullable = false, length = 100)
	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@Column(name = "contrasenia", nullable = false, length = 100)
	public String getContrasenia() {
		return this.contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	@Column(name = "url_aplicacion_notificaciones", nullable = false, length = 400)
	public String getUrlAplicacionNotificaciones() {
		return this.urlAplicacionNotificaciones;
	}

	public void setUrlAplicacionNotificaciones(String urlAplicacionNotificaciones) {
		this.urlAplicacionNotificaciones = urlAplicacionNotificaciones;
	}

	@Column(name = "habilita_envio_notificaciones", nullable = false)
	public boolean isHabilitaEnvioNotificaciones() {
		return this.habilitaEnvioNotificaciones;
	}

	public void setHabilitaEnvioNotificaciones(boolean habilitaEnvioNotificaciones) {
		this.habilitaEnvioNotificaciones = habilitaEnvioNotificaciones;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false, length = 29)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_ultima_actualizacion", nullable = false, length = 29)
	public Date getFechaUltimaActualizacion() {
		return this.fechaUltimaActualizacion;
	}

	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Column(name = "seccion_sincronizada", nullable = false)
	public boolean isSeccionSincronizada() {
		return this.seccionSincronizada;
	}

	public void setSeccionSincronizada(boolean seccionSincronizada) {
		this.seccionSincronizada = seccionSincronizada;
	}

}

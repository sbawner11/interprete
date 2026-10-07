package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cat_tipo_notificacion", schema = "motor_interprete")
public class CatTipoNotificacion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5411725323568546510L;
	
	private int idTipoNotificacion;
	private String descripcion;
	private Set<NotificacionMovimientoTramite> notificacionMovimientoTramites = new HashSet<NotificacionMovimientoTramite>(
			0);

	public CatTipoNotificacion() {
	}

	public CatTipoNotificacion(int idTipoNotificacion, String descripcion) {
		this.idTipoNotificacion = idTipoNotificacion;
		this.descripcion = descripcion;
	}

	public CatTipoNotificacion(int idTipoNotificacion, String descripcion,
			Set<NotificacionMovimientoTramite> notificacionMovimientoTramites) {
		this.idTipoNotificacion = idTipoNotificacion;
		this.descripcion = descripcion;
		this.notificacionMovimientoTramites = notificacionMovimientoTramites;
	}

	@Id
	@Column(name = "id_tipo_notificacion", unique = true, nullable = false)
	public int getIdTipoNotificacion() {
		return this.idTipoNotificacion;
	}

	public void setIdTipoNotificacion(int idTipoNotificacion) {
		this.idTipoNotificacion = idTipoNotificacion;
	}

	@Column(name = "descripcion", nullable = false, length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catTipoNotificacion")
	public Set<NotificacionMovimientoTramite> getNotificacionMovimientoTramites() {
		return this.notificacionMovimientoTramites;
	}

	public void setNotificacionMovimientoTramites(Set<NotificacionMovimientoTramite> notificacionMovimientoTramites) {
		this.notificacionMovimientoTramites = notificacionMovimientoTramites;
	}

}

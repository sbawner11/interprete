package mx.gob.atdt.interprete.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "notificacion_movimiento_tramite", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "NotificacionMovimientoTramite.findAllNoConfirmadas", 	
			query = "SELECT new mx.gob.atdt.interprete.dto.NotificacionMovimientoTramiteDTO( " +
					" nmt.idNotificacionMovimiento, ctn.idTipoNotificacion, ctn.descripcion, " +
					" t.idTramite, t.folioSeguimiento, cet.idEstatusTramite, cet.descripcion, " +
					" t.fechaCreacion, t.fechaRevision, nmt.fechaNotificacion, nmt.envioConfirmado) " +
					" FROM NotificacionMovimientoTramite nmt " +
					" JOIN nmt.catTipoNotificacion ctn " +
					" JOIN nmt.tramites t " +
					" JOIN t.catEstatusTramite cet " +					
					" WHERE nmt.envioConfirmado = false " +
					" AND nmt.fechaNotificacion IS NULL " +
					" ORDER BY nmt.idNotificacionMovimiento ASC")
	,@NamedQuery(name = "NotificacionMovimientoTramite.findByIdTramite", 	
			query = "SELECT new mx.gob.atdt.interprete.dto.NotificacionMovimientoTramiteDTO(nt.idNotificacionMovimiento)"
					+ " FROM NotificacionMovimientoTramite nt"
					+ " JOIN nt.catTipoNotificacion ct"
					+ " WHERE nt.tramites.idTramite = :idTramite"
					+ " AND ct.idTipoNotificacion = :idTipoNotificacion"
					+ " AND nt.fechaNotificacion IS NULL"
					+ " AND nt.envioConfirmado = false"
					)
	,@NamedQuery(name = "NotificacionMovimientoTramite.findAllTramitesNotificacionFallida", 	
				query = "SELECT new mx.gob.atdt.interprete.dto.NotificacionMovimientoTramiteDTO(nmt.idNotificacionMovimiento, ctn.idTipoNotificacion,"
					+ " t.idTramite, t.folioSeguimiento, t.uuid,t.fechaCreacion, t.fechaRevision, t.rutaDocumentoPrevencion, t.rutaDocumentoRevocado,"
					+ " t.rutaResolucionPositiva, t.rutaResolucionNegativa, cet.idEstatusTramite, cet.descripcion,"
					+ " cet.descripcionAviso, cet.descripcionPersonalizada, u.idUsuarioLlaveCdmx, u.curp, pm.idPersonaMoral)"
					+ " FROM NotificacionMovimientoTramite nmt"
					+ " JOIN nmt.catTipoNotificacion ctn"
					+ " JOIN nmt.tramites t" 
					+ " JOIN t.catEstatusTramite cet" 
					+ " JOIN t.usuarioByIdUsuarioLlaveCdmx u"
					+ " LEFT JOIN t.personaMoral pm"
					+ " WHERE nmt.fechaNotificacion IS NULL AND nmt.envioConfirmado = false" 					
					+ " ORDER BY t.fechaCreacion"
					)
})
public class NotificacionMovimientoTramite implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2536704877191657748L;
	
	private long idNotificacionMovimiento;
	private CatTipoNotificacion catTipoNotificacion;
	private Tramites tramites;
	private Date fechaNotificacion;
	private boolean envioConfirmado;

	public NotificacionMovimientoTramite() {
	}

	public NotificacionMovimientoTramite(long idNotificacionMovimiento, CatTipoNotificacion catTipoNotificacion,
			Tramites tramites, Date fechaNotificacion, boolean envioConfirmado) {
		this.idNotificacionMovimiento = idNotificacionMovimiento;
		this.catTipoNotificacion = catTipoNotificacion;
		this.tramites = tramites;
		this.fechaNotificacion = fechaNotificacion;
		this.envioConfirmado = envioConfirmado;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_notificacion_movimiento", unique = true, nullable = false)
	public long getIdNotificacionMovimiento() {
		return this.idNotificacionMovimiento;
	}

	public void setIdNotificacionMovimiento(long idNotificacionMovimiento) {
		this.idNotificacionMovimiento = idNotificacionMovimiento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_notificacion", nullable = false)
	public CatTipoNotificacion getCatTipoNotificacion() {
		return this.catTipoNotificacion;
	}

	public void setCatTipoNotificacion(CatTipoNotificacion catTipoNotificacion) {
		this.catTipoNotificacion = catTipoNotificacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tramite", nullable = false)
	public Tramites getTramites() {
		return this.tramites;
	}

	public void setTramites(Tramites tramites) {
		this.tramites = tramites;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_notificacion", nullable = false, length = 29)
	public Date getFechaNotificacion() {
		return this.fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	@Column(name = "envio_confirmado", nullable = false)
	public boolean isEnvioConfirmado() {
		return this.envioConfirmado;
	}

	public void setEnvioConfirmado(boolean envioConfirmado) {
		this.envioConfirmado = envioConfirmado;
	}

}

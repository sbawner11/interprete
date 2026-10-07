package mx.gob.atdt.interprete.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "bit_movimientos_secciones", schema = "motor_interprete")
public class BitMovimientosSecciones implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6531711250257969785L;
	
	private long idMovimientoSeccion;
	private CatSeccionesProyecto catSeccionesProyecto;
	private Proyecto proyecto;
	private Usuario usuario;
	private Date fechaMovimiento;
	private String tablaMovimiento;
	private long idRegistroMovimiento;
	private boolean cambioSincronizado;
	private Date fechaSincronizacion;

	public BitMovimientosSecciones() {
	}

	public BitMovimientosSecciones(long idMovimientoSeccion, CatSeccionesProyecto catSeccionesProyecto,
			Proyecto proyecto, Usuario usuario, Date fechaMovimiento, String tablaMovimiento, long idRegistroMovimiento,
			boolean cambioSincronizado) {
		this.idMovimientoSeccion = idMovimientoSeccion;
		this.catSeccionesProyecto = catSeccionesProyecto;
		this.proyecto = proyecto;
		this.usuario = usuario;
		this.fechaMovimiento = fechaMovimiento;
		this.tablaMovimiento = tablaMovimiento;
		this.idRegistroMovimiento = idRegistroMovimiento;
		this.cambioSincronizado = cambioSincronizado;
	}

	public BitMovimientosSecciones(long idMovimientoSeccion, CatSeccionesProyecto catSeccionesProyecto,
			Proyecto proyecto, Usuario usuario, Date fechaMovimiento, String tablaMovimiento, long idRegistroMovimiento,
			boolean cambioSincronizado, Date fechaSincronizacion) {
		this.idMovimientoSeccion = idMovimientoSeccion;
		this.catSeccionesProyecto = catSeccionesProyecto;
		this.proyecto = proyecto;
		this.usuario = usuario;
		this.fechaMovimiento = fechaMovimiento;
		this.tablaMovimiento = tablaMovimiento;
		this.idRegistroMovimiento = idRegistroMovimiento;
		this.cambioSincronizado = cambioSincronizado;
		this.fechaSincronizacion = fechaSincronizacion;
	}

	@Id
	@Column(name = "id_movimiento_seccion", unique = true, nullable = false)
	public long getIdMovimientoSeccion() {
		return this.idMovimientoSeccion;
	}

	public void setIdMovimientoSeccion(long idMovimientoSeccion) {
		this.idMovimientoSeccion = idMovimientoSeccion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_seccion_proyecto", nullable = false)
	public CatSeccionesProyecto getCatSeccionesProyecto() {
		return this.catSeccionesProyecto;
	}

	public void setCatSeccionesProyecto(CatSeccionesProyecto catSeccionesProyecto) {
		this.catSeccionesProyecto = catSeccionesProyecto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_movimiento", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_movimiento", nullable = false, length = 29)
	public Date getFechaMovimiento() {
		return this.fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	
	@Column(name = "tabla_movimiento", nullable = false, length = 200)
	public String getTablaMovimiento() {
		return this.tablaMovimiento;
	}

	public void setTablaMovimiento(String tablaMovimiento) {
		this.tablaMovimiento = tablaMovimiento;
	}

	@Column(name = "id_registro_movimiento", nullable = false)
	public long getIdRegistroMovimiento() {
		return this.idRegistroMovimiento;
	}

	public void setIdRegistroMovimiento(long idRegistroMovimiento) {
		this.idRegistroMovimiento = idRegistroMovimiento;
	}

	@Column(name = "cambio_sincronizado", nullable = false)
	public boolean isCambioSincronizado() {
		return this.cambioSincronizado;
	}

	public void setCambioSincronizado(boolean cambioSincronizado) {
		this.cambioSincronizado = cambioSincronizado;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_sincronizacion", length = 29)
	public Date getFechaSincronizacion() {
		return this.fechaSincronizacion;
	}

	public void setFechaSincronizacion(Date fechaSincronizacion) {
		this.fechaSincronizacion = fechaSincronizacion;
	}
	
}

package mx.gob.atdt.interprete.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "det_distribucion", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "DetDistribucion.findByIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetDistribucionDTO("
			+ " d.idDistribucion, c.idComponente, p.idProyecto, d.fechaCreacion, "
			+ " d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada ) "
			+ " FROM DetDistribucion d "
			+ " JOIN d.componente c "
			+ " JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto"), 
	@NamedQuery(name = "DetDistribucion.existeDetalleActivoIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetDistribucionDTO("
			+ " p.idProyecto, d.idDistribucion) "
			+ " FROM DetDistribucion d "
			+ " JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto"
			+ " AND d.activo = :activo ")
})
public class DetDistribucion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -923333575685153150L;
	
	private Long idDistribucion;
	private Componente componente;
	private Proyecto proyecto;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;

	public DetDistribucion() {
	}

	public DetDistribucion(Long idDistribucion, Componente componente, Proyecto proyecto, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idDistribucion = idDistribucion;
		this.componente = componente;
		this.proyecto = proyecto;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	@Id
	@Column(name = "id_distribucion", unique = true, nullable = false)
	public Long getIdDistribucion() {
		return this.idDistribucion;
	}

	public void setIdDistribucion(Long idDistribucion) {
		this.idDistribucion = idDistribucion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
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

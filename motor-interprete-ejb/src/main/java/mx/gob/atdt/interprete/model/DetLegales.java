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
@Table(name = "det_legales", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "DetLegales.findByIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetLegalesDTO("
			+ " d.idDetalleLegal, p.idProyecto, d.contieneAvisoSimplificado, d.cuerpoAvisoSimplificado, d.contieneAvisoIntegral, d.cuerpoAvisoIntegral, d.contieneManifiesto, d.cuerpoManifiesto, "
			+ " d.fechaCreacion, d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada) "
			+ " FROM DetLegales d "
			+ " JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto"),
	@NamedQuery(name = "DetLegales.existeDetalleActivoIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetLegalesDTO("
			+ " p.idProyecto, d.idDetalleLegal ) "
			+ " FROM DetLegales d "
			+ " JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto"
			+ " AND d.activo = :activo ")
})
public class DetLegales implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2576612486821519268L;
	
	private Long idDetalleLegal;
	private Proyecto proyecto;
	private boolean contieneAvisoSimplificado;
	private String cuerpoAvisoSimplificado;
	private boolean contieneAvisoIntegral;
	private String cuerpoAvisoIntegral;
	private boolean contieneManifiesto;
	private String cuerpoManifiesto;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;

	public DetLegales() {
	}

	public DetLegales(Long idDetalleLegal, Proyecto proyecto, boolean contieneAvisoSimplificado,
			boolean contieneAvisoIntegral, boolean contieneManifiesto, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idDetalleLegal = idDetalleLegal;
		this.proyecto = proyecto;
		this.contieneAvisoSimplificado = contieneAvisoSimplificado;
		this.contieneAvisoIntegral = contieneAvisoIntegral;
		this.contieneManifiesto = contieneManifiesto;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	public DetLegales(Long idDetalleLegal, Proyecto proyecto, boolean contieneAvisoSimplificado,
			String cuerpoAvisoSimplificado, boolean contieneAvisoIntegral, String cuerpoAvisoIntegral,
			boolean contieneManifiesto, String cuerpoManifiesto, Date fechaCreacion, Date fechaUltimaActualizacion,
			boolean activo, boolean seccionSincronizada) {
		this.idDetalleLegal = idDetalleLegal;
		this.proyecto = proyecto;
		this.contieneAvisoSimplificado = contieneAvisoSimplificado;
		this.cuerpoAvisoSimplificado = cuerpoAvisoSimplificado;
		this.contieneAvisoIntegral = contieneAvisoIntegral;
		this.cuerpoAvisoIntegral = cuerpoAvisoIntegral;
		this.contieneManifiesto = contieneManifiesto;
		this.cuerpoManifiesto = cuerpoManifiesto;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	@Id
	@Column(name = "id_detalle_legal", unique = true, nullable = false)
	public Long getIdDetalleLegal() {
		return this.idDetalleLegal;
	}

	public void setIdDetalleLegal(Long idDetalleLegal) {
		this.idDetalleLegal = idDetalleLegal;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "contiene_aviso_simplificado", nullable = false)
	public boolean isContieneAvisoSimplificado() {
		return this.contieneAvisoSimplificado;
	}

	public void setContieneAvisoSimplificado(boolean contieneAvisoSimplificado) {
		this.contieneAvisoSimplificado = contieneAvisoSimplificado;
	}

	@Column(name = "cuerpo_aviso_simplificado")
	public String getCuerpoAvisoSimplificado() {
		return this.cuerpoAvisoSimplificado;
	}

	public void setCuerpoAvisoSimplificado(String cuerpoAvisoSimplificado) {
		this.cuerpoAvisoSimplificado = cuerpoAvisoSimplificado;
	}

	@Column(name = "contiene_aviso_integral", nullable = false)
	public boolean isContieneAvisoIntegral() {
		return this.contieneAvisoIntegral;
	}

	public void setContieneAvisoIntegral(boolean contieneAvisoIntegral) {
		this.contieneAvisoIntegral = contieneAvisoIntegral;
	}

	@Column(name = "cuerpo_aviso_integral")
	public String getCuerpoAvisoIntegral() {
		return this.cuerpoAvisoIntegral;
	}

	public void setCuerpoAvisoIntegral(String cuerpoAvisoIntegral) {
		this.cuerpoAvisoIntegral = cuerpoAvisoIntegral;
	}

	@Column(name = "contiene_manifiesto", nullable = false)
	public boolean isContieneManifiesto() {
		return this.contieneManifiesto;
	}

	public void setContieneManifiesto(boolean contieneManifiesto) {
		this.contieneManifiesto = contieneManifiesto;
	}

	@Column(name = "cuerpo_manifiesto")
	public String getCuerpoManifiesto() {
		return this.cuerpoManifiesto;
	}

	public void setCuerpoManifiesto(String cuerpoManifiesto) {
		this.cuerpoManifiesto = cuerpoManifiesto;
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

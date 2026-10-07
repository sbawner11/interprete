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
@Table(name = "det_analytics", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "DetAnalytics.findByIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetAnalyticsDTO("
			+ " d.idDetalleAnalytics, p.idProyecto, d.identificadorAnalytics, d.tituloBusqueda, d.descripcionBusqueda, "
			+ " d.palabraClaveBusqueda, d.tituloGrap, d.descripcionGrap, d.urlGrap, d.rutaImagenGrap, d.fechaCreacion, "
			+ " d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada ) "
			+ " FROM DetAnalytics d "
			+ " JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto"), 
	@NamedQuery(name = "DetAnalytics.existeDetalleActivoIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetAnalyticsDTO("
			+ " p.idProyecto, d.idDetalleAnalytics) "
			+ " FROM DetAnalytics d "
			+ " JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto"
			+ " AND d.activo = :activo ")
})
public class DetAnalytics implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -238059666447596405L;
	
	private Long idDetalleAnalytics;
	private Proyecto proyecto;
	private String identificadorAnalytics;
	private String tituloBusqueda;
	private String descripcionBusqueda;
	private String palabraClaveBusqueda;
	private String tituloGrap;
	private String descripcionGrap;
	private String urlGrap;
	private String rutaImagenGrap;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;

	public DetAnalytics() {
	}

	public DetAnalytics(Long idDetalleAnalytics, Proyecto proyecto, String identificadorAnalytics,
			String tituloBusqueda, String descripcionBusqueda, String palabraClaveBusqueda, String tituloGrap,
			String descripcionGrap, String urlGrap, String rutaImagenGrap, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idDetalleAnalytics = idDetalleAnalytics;
		this.proyecto = proyecto;
		this.identificadorAnalytics = identificadorAnalytics;
		this.tituloBusqueda = tituloBusqueda;
		this.descripcionBusqueda = descripcionBusqueda;
		this.palabraClaveBusqueda = palabraClaveBusqueda;
		this.tituloGrap = tituloGrap;
		this.descripcionGrap = descripcionGrap;
		this.urlGrap = urlGrap;
		this.rutaImagenGrap = rutaImagenGrap;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	@Id
	@Column(name = "id_detalle_analytics", unique = true, nullable = false)
	public Long getIdDetalleAnalytics() {
		return this.idDetalleAnalytics;
	}

	public void setIdDetalleAnalytics(Long idDetalleAnalytics) {
		this.idDetalleAnalytics = idDetalleAnalytics;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "identificador_analytics", nullable = false, length = 16)
	public String getIdentificadorAnalytics() {
		return this.identificadorAnalytics;
	}

	public void setIdentificadorAnalytics(String identificadorAnalytics) {
		this.identificadorAnalytics = identificadorAnalytics;
	}

	@Column(name = "titulo_busqueda", nullable = false, length = 100)
	public String getTituloBusqueda() {
		return this.tituloBusqueda;
	}

	public void setTituloBusqueda(String tituloBusqueda) {
		this.tituloBusqueda = tituloBusqueda;
	}

	@Column(name = "descripcion_busqueda", nullable = false, length = 200)
	public String getDescripcionBusqueda() {
		return this.descripcionBusqueda;
	}

	public void setDescripcionBusqueda(String descripcionBusqueda) {
		this.descripcionBusqueda = descripcionBusqueda;
	}

	@Column(name = "palabra_clave_busqueda", nullable = false, length = 100)
	public String getPalabraClaveBusqueda() {
		return this.palabraClaveBusqueda;
	}

	public void setPalabraClaveBusqueda(String palabraClaveBusqueda) {
		this.palabraClaveBusqueda = palabraClaveBusqueda;
	}

	@Column(name = "titulo_grap", nullable = false, length = 100)
	public String getTituloGrap() {
		return this.tituloGrap;
	}

	public void setTituloGrap(String tituloGrap) {
		this.tituloGrap = tituloGrap;
	}

	@Column(name = "descripcion_grap", nullable = false, length = 200)
	public String getDescripcionGrap() {
		return this.descripcionGrap;
	}

	public void setDescripcionGrap(String descripcionGrap) {
		this.descripcionGrap = descripcionGrap;
	}

	@Column(name = "url_grap", nullable = false, length = 100)
	public String getUrlGrap() {
		return this.urlGrap;
	}

	public void setUrlGrap(String urlGrap) {
		this.urlGrap = urlGrap;
	}

	@Column(name = "ruta_imagen_grap", nullable = false, length = 200)
	public String getRutaImagenGrap() {
		return this.rutaImagenGrap;
	}

	public void setRutaImagenGrap(String rutaImagenGrap) {
		this.rutaImagenGrap = rutaImagenGrap;
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
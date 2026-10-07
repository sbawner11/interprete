package mx.gob.atdt.interprete.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "det_pago", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetPago.findByIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetPagoDTO( "
			+ " d.idDetallePago ,d.catTipoCosto.idTipoCosto , p.idProyecto, d.montoCostoFijo, "		
			+ " d.urlServicio, d.identificadorProceso, d.fechaCreacion, "
			+ " d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada ) " 
			+ " FROM DetPago d "
			+ "	JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto"),	
	@NamedQuery(name = "DetPago.existeDetalleActivoIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetPagoDTO("
			+ " p.idProyecto, d.idDetallePago ) " 
			+ " FROM DetPago d "
			+ "	JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto "
			+ " AND d.activo = :activo "),	
	@NamedQuery(name = "DetPago.findById", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetPagoDTO("
			+ " p.idProyecto, d.idDetallePago ) " 
			+ " FROM DetPago d "
			+ "	JOIN d.proyecto p "
			+ " WHERE d.idDetallePago = :idDetallePago ")
})
public class DetPago implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6484079171287755783L;
	
	private Long idDetallePago;
	private CatTipoCosto catTipoCosto;
	private Proyecto proyecto;
	private Double montoCostoFijo;
	private String urlServicio;
	private String identificadorProceso;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	private Set<ParametrosDetallePago> parametrosDetallePagos = new HashSet<ParametrosDetallePago>(0);

	public DetPago() {
	}

	public DetPago(Long idDetallePago, CatTipoCosto catTipoCosto, Proyecto proyecto, String urlServicio,
			String identificadorProceso, Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo,
			boolean seccionSincronizada) {
		this.idDetallePago = idDetallePago;
		this.catTipoCosto = catTipoCosto;
		this.proyecto = proyecto;
		this.urlServicio = urlServicio;
		this.identificadorProceso = identificadorProceso;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	public DetPago(Long idDetallePago, CatTipoCosto catTipoCosto, Proyecto proyecto, Double montoCostoFijo,
			String urlServicio, String identificadorProceso, Date fechaCreacion, Date fechaUltimaActualizacion,
			boolean activo, boolean seccionSincronizada, Set<ParametrosDetallePago> parametrosDetallePagos) {
		this.idDetallePago = idDetallePago;
		this.catTipoCosto = catTipoCosto;
		this.proyecto = proyecto;
		this.montoCostoFijo = montoCostoFijo;
		this.urlServicio = urlServicio;
		this.identificadorProceso = identificadorProceso;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		this.parametrosDetallePagos = parametrosDetallePagos;		
	}

	@Id
	@Column(name = "id_detalle_pago", unique = true, nullable = false)
	public Long getIdDetallePago() {
		return this.idDetallePago;
	}

	public void setIdDetallePago(Long idDetallePago) {
		this.idDetallePago = idDetallePago;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_costo", nullable = false)
	public CatTipoCosto getCatTipoCosto() {
		return this.catTipoCosto;
	}

	public void setCatTipoCosto(CatTipoCosto catTipoCosto) {
		this.catTipoCosto = catTipoCosto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "monto_costo_fijo", precision = 5)
	public Double getMontoCostoFijo() {
		return this.montoCostoFijo;
	}

	public void setMontoCostoFijo(Double montoCostoFijo) {
		this.montoCostoFijo = montoCostoFijo;
	}

	@Column(name = "url_servicio", nullable = false, length = 200)
	public String getUrlServicio() {
		return this.urlServicio;
	}

	public void setUrlServicio(String urlServicio) {
		this.urlServicio = urlServicio;
	}

	@Column(name = "identificador_proceso", nullable = false, length = 20)
	public String getIdentificadorProceso() {
		return this.identificadorProceso;
	}

	public void setIdentificadorProceso(String identificadorProceso) {
		this.identificadorProceso = identificadorProceso;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detPago")
	public Set<ParametrosDetallePago> getParametrosDetallePagos() {
		return this.parametrosDetallePagos;
	}

	public void setParametrosDetallePagos(Set<ParametrosDetallePago> parametrosDetallePagos) {
		this.parametrosDetallePagos = parametrosDetallePagos;
	}
}

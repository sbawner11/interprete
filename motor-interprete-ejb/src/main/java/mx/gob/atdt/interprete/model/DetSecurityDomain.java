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
@Table(name = "det_security_domain", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetSecurityDomain.findByIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetSecurityDomainDTO("
			+ " p.idProyecto, d.idDetalleSecurity, d.usuario, d.contrasenia, "		
			+ " d.urlSistema, d.fechaCreacion, d.fechaUltimaActualizacion, d.activo,"
			+ " cts.idTipoSecurityDomain, cts.descripcion) " 
			+ " FROM DetSecurityDomain d "
			+ "	JOIN d.proyecto p "
			+ "	JOIN d.catTipoSecurityDomain cts "
			+ " WHERE p.idProyecto = :idProyecto "
			+ " AND cts.idTipoSecurityDomain = :idTipoSecurityDomain"),
	@NamedQuery(name = "DetSecurityDomain.existeDetalleActivoIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetSecurityDomainDTO("
			+ " p.idProyecto, d.idDetalleSecurity) " 
			+ " FROM DetSecurityDomain d "
			+ "	JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto"
			+ " AND d.activo = :activo ")
})
public class DetSecurityDomain implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1322684293578450797L;

	private Long idDetalleSecurity;
	private CatTipoSecurityDomain catTipoSecurityDomain;
	private Proyecto proyecto;
	private String usuario;
	private String contrasenia;
	private String urlSistema;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;

	public DetSecurityDomain() {
	}

	public DetSecurityDomain(long idDetalleSecurity, CatTipoSecurityDomain catTipoSecurityDomain, Proyecto proyecto,
			String usuario, String contrasenia, String urlSistema, Date fechaCreacion, Date fechaUltimaActualizacion,
			boolean activo) {
		this.idDetalleSecurity = idDetalleSecurity;
		this.catTipoSecurityDomain = catTipoSecurityDomain;
		this.proyecto = proyecto;
		this.usuario = usuario;
		this.contrasenia = contrasenia;
		this.urlSistema = urlSistema;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_detalle_security", unique = true, nullable = false)
	public Long getidDetalleSecurity() {
		return this.idDetalleSecurity;
	}

	public void setidDetalleSecurity(Long idDetalleSecurity) {
		this.idDetalleSecurity = idDetalleSecurity;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_security_domain", nullable = false)
	public CatTipoSecurityDomain getCatTipoSecurityDomain() {
		return this.catTipoSecurityDomain;
	}

	public void setCatTipoSecurityDomain(CatTipoSecurityDomain catTipoSecurityDomain) {
		this.catTipoSecurityDomain = catTipoSecurityDomain;
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

	@Column(name = "url_sistema", nullable = false, length = 400)
	public String getUrlSistema() {
		return this.urlSistema;
	}

	public void setUrlSistema(String urlSistema) {
		this.urlSistema = urlSistema;
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
}

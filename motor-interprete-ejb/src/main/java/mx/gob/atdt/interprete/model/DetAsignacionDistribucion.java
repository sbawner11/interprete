package mx.gob.atdt.interprete.model;

import java.io.Serializable;
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
@Table(name = "det_asignacion_distribucion", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetAsignacionDistribucion.findByIdUsuarioAsignado",
			query = "SELECT new mx.gob.atdt.interprete.dto.DetAsignacionDistribucionDTO( "
					+ "dad.idAsignacionDistribucion, dad.idElementoAsignado, dad.desElementoAsignado, "
					+ "dad.rol, dad.fechaAsignacion, dad.activo, "
					+ "c.idComponente, c.tituloCampo, c.catTipoComponente.idTipoComponente,"
					+ "ua.idUsuarioLlaveCdmx, ua.correo, ua.nombre, ua.primerApellido, ua.segundoApellido, "
					+ "aa.idUsuarioLlaveCdmx, aa.correo) "
					+ "FROM DetAsignacionDistribucion dad "
					+ "LEFT JOIN dad.componente c "
					+ "LEFT JOIN dad.usuarioAsignado ua "
					+ "LEFT JOIN dad.administradorAsigna aa "
					+ "WHERE ua.idUsuarioLlaveCdmx = :idUsuarioAsignado AND dad.activo = true"),
	@NamedQuery(name = "DetAsignacionDistribucion.findByIdUsuarioAsignadoAndComponente",
			query = "SELECT new mx.gob.atdt.interprete.dto.DetAsignacionDistribucionDTO( "
					+ "dad.idAsignacionDistribucion, dad.idElementoAsignado, dad.desElementoAsignado, "
					+ "dad.rol, dad.fechaAsignacion, dad.activo, "
					+ "c.idComponente, c.tituloCampo, c.catTipoComponente.idTipoComponente, "
					+ "ua.idUsuarioLlaveCdmx, ua.correo, ua.nombre, ua.primerApellido, ua.segundoApellido, "
					+ "aa.idUsuarioLlaveCdmx, aa.correo) "
					+ "FROM DetAsignacionDistribucion dad "
					+ "LEFT JOIN dad.componente c "
					+ "LEFT JOIN dad.usuarioAsignado ua "
					+ "LEFT JOIN dad.administradorAsigna aa "
					+ "WHERE ua.idUsuarioLlaveCdmx = :idUsuarioAsignado AND dad.activo = true "
					+ "AND c.idComponente = :idComponente "),
	@NamedQuery(name = "DetAsignacionDistribucion.findAllActivos",
			query = "SELECT new mx.gob.atdt.interprete.dto.DetAsignacionDistribucionDTO("
					+ "dad.idAsignacionDistribucion, dad.idElementoAsignado, dad.desElementoAsignado, "
					+ "dad.rol, dad.fechaAsignacion, dad.activo, "
					+ "c.idComponente, c.tituloCampo, c.catTipoComponente.idTipoComponente,"
					+ "ua.idUsuarioLlaveCdmx, ua.correo, ua.nombre, ua.primerApellido, ua.segundoApellido, "
					+ "aa.idUsuarioLlaveCdmx, aa.correo) "
					+ "FROM DetAsignacionDistribucion dad "
					+ "LEFT JOIN dad.componente c "
					+ "LEFT JOIN dad.usuarioAsignado ua "
					+ "LEFT JOIN dad.administradorAsigna aa "
					+ "WHERE dad.activo = true "
					+ "ORDER BY dad.idAsignacionDistribucion DESC "),
	@NamedQuery(name = "DetAsignacionDistribucion.findAllActivosByComponentes",
			query = "SELECT new mx.gob.atdt.interprete.dto.DetAsignacionDistribucionDTO( "
					+ "dad.idAsignacionDistribucion, dad.idElementoAsignado, dad.desElementoAsignado, "
					+ "dad.rol, dad.fechaAsignacion, dad.activo, "
					+ "c.idComponente, c.tituloCampo, c.catTipoComponente.idTipoComponente, "
					+ "ua.idUsuarioLlaveCdmx, ua.correo, ua.nombre, ua.primerApellido, ua.segundoApellido, "
					+ "aa.idUsuarioLlaveCdmx, aa.correo) "
					+ "FROM DetAsignacionDistribucion dad "
					+ "LEFT JOIN dad.componente c "
					+ "LEFT JOIN dad.usuarioAsignado ua "
					+ "LEFT JOIN dad.administradorAsigna aa "
					+ "WHERE dad.activo = true AND c.idComponente = :idComponente "),
	@NamedQuery(name = "DetAsignacionDistribucion.findAllActivosByComponentesCount",
			query = "SELECT count(dad) FROM DetAsignacionDistribucion dad "
					+ "LEFT JOIN dad.componente c "
					+ "LEFT JOIN dad.usuarioAsignado ua "
					+ "LEFT JOIN dad.administradorAsigna aa "
					+ "WHERE dad.activo = true AND c.idComponente = :idComponente "),
	@NamedQuery(name = "DetAsignacionDistribucion.findByIdUsuarioAsignadoAndIdElementoAsignado",
			query = "SELECT new mx.gob.atdt.interprete.dto.DetAsignacionDistribucionDTO( "
					+ "dad.idAsignacionDistribucion, dad.idElementoAsignado, dad.desElementoAsignado, "
					+ "dad.rol, dad.fechaAsignacion, dad.activo, "
					+ "c.idComponente, c.tituloCampo, c.catTipoComponente.idTipoComponente, "
					+ "ua.idUsuarioLlaveCdmx, ua.correo, ua.nombre, ua.primerApellido, ua.segundoApellido, "
					+ "aa.idUsuarioLlaveCdmx, aa.correo) "
					+ "FROM DetAsignacionDistribucion dad "
					+ "LEFT JOIN dad.componente c "
					+ "LEFT JOIN dad.usuarioAsignado ua "
					+ "LEFT JOIN dad.administradorAsigna aa "
					+ "WHERE ua.idUsuarioLlaveCdmx = :idUsuarioAsignado "
					+ "AND dad.idElementoAsignado = :idElementoAsignado AND dad.activo = true"),
	@NamedQuery(name = "DetAsignacionDistribucion.findByAllActiveIdElementoAndIdUsuarioAsignadoAndRol",
			query = "SELECT new mx.gob.atdt.interprete.dto.DetAsignacionDistribucionDTO( "
					+ "dad.idAsignacionDistribucion, dad.idElementoAsignado, dad.desElementoAsignado, "
					+ "dad.rol, dad.fechaAsignacion, dad.activo, "
					+ "c.idComponente, c.tituloCampo, c.catTipoComponente.idTipoComponente, "
					+ "ua.idUsuarioLlaveCdmx, ua.correo, ua.nombre, ua.primerApellido, ua.segundoApellido, "
					+ "aa.idUsuarioLlaveCdmx, aa.correo) "
					+ "FROM DetAsignacionDistribucion dad "
					+ "LEFT JOIN dad.componente c "
					+ "LEFT JOIN dad.usuarioAsignado ua "
					+ "LEFT JOIN dad.administradorAsigna aa "
					+ "WHERE dad.activo = true "
					+ "AND c.idComponente = CASE WHEN :idComponente IS NULL THEN c.idComponente ELSE :idComponente END "
					+ "AND ua.idUsuarioLlaveCdmx = CASE WHEN :idUsuarioAsignado IS NULL THEN ua.idUsuarioLlaveCdmx ELSE :idUsuarioAsignado END "
					+ "AND dad.idElementoAsignado = CASE WHEN :idElementoAsignado IS NULL THEN dad.idElementoAsignado ELSE :idElementoAsignado END "
					+ "AND dad.rol= CASE WHEN :rol IS NULL THEN dad.rol ELSE :rol END "),
	@NamedQuery(name = "DetAsignacionDistribucion.findByAllActiveIdElementoAndIdUsuarioAsignadoAndRolCount",
			query = "SELECT count(dad) FROM DetAsignacionDistribucion dad "
					+ "LEFT JOIN dad.componente c "
					+ "LEFT JOIN dad.usuarioAsignado ua "
					+ "LEFT JOIN dad.administradorAsigna aa "
					+ "WHERE dad.activo = true "
					+ "AND c.idComponente = CASE WHEN :idComponente IS NULL THEN c.idComponente ELSE :idComponente END "
					+ "AND ua.idUsuarioLlaveCdmx = CASE WHEN :idUsuarioAsignado IS NULL THEN ua.idUsuarioLlaveCdmx ELSE :idUsuarioAsignado END "
					+ "AND dad.idElementoAsignado = CASE WHEN :idElementoAsignado IS NULL THEN dad.idElementoAsignado ELSE :idElementoAsignado END "
					+ "AND dad.rol= CASE WHEN :rol IS NULL THEN dad.rol ELSE :rol END"),
	@NamedQuery(name = "DetAsignacionDistribucion.findByIdElementoAsignadoAndComponenteAndRol",
			query = "SELECT new mx.gob.atdt.interprete.dto.DetAsignacionDistribucionDTO( "
					+ "dad.idAsignacionDistribucion, dad.idElementoAsignado, dad.desElementoAsignado, "
					+ "dad.rol, dad.fechaAsignacion, dad.activo, "
					+ "c.idComponente, c.tituloCampo, c.catTipoComponente.idTipoComponente,"
					+ "ua.idUsuarioLlaveCdmx, ua.correo, ua.nombre, ua.primerApellido, ua.segundoApellido, "
					+ "aa.idUsuarioLlaveCdmx, aa.correo) "
					+ "FROM DetAsignacionDistribucion dad "
					+ "LEFT JOIN dad.componente c "
					+ "LEFT JOIN dad.usuarioAsignado ua "
					+ "LEFT JOIN dad.administradorAsigna aa "
					+ "WHERE dad.idElementoAsignado = :idElementoAsignado AND dad.activo = true "
					+ "AND c.idComponente = :idComponente AND dad.rol = :rol"),
})
public class DetAsignacionDistribucion implements Serializable{

	private static final long serialVersionUID = 2480582070846642583L;
	private Long idAsignacionDistribucion;
	private Componente componente;
	private Long idElementoAsignado;
	private String desElementoAsignado;
	private Usuario usuarioAsignado;
	private Usuario administradorAsigna;
	private String rol;
	private Date fechaAsignacion;
	private Date fechaDesvinculacion;
	private boolean activo;
	
	public DetAsignacionDistribucion() {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_asignacion_distribucion", unique = true, nullable = false)
	public Long getIdAsignacionDistribucion() {
		return idAsignacionDistribucion;
	}

	public void setIdAsignacionDistribucion(Long idAsignacionDistribucion) {
		this.idAsignacionDistribucion = idAsignacionDistribucion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@Column(name = "id_elemento_asignado", nullable = false)
	public Long getIdElementoAsignado() {
		return idElementoAsignado;
	}

	public void setIdElementoAsignado(Long idElementoAsignado) {
		this.idElementoAsignado = idElementoAsignado;
	}

	@Column(name = "desc_elemento_asignado", nullable = false, length = 200)
	public String getDesElementoAsignado() {
		return desElementoAsignado;
	}

	public void setDesElementoAsignado(String desElementoAsignado) {
		this.desElementoAsignado = desElementoAsignado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_asignado", nullable = false)
	public Usuario getUsuarioAsignado() {
		return usuarioAsignado;
	}

	public void setUsuarioAsignado(Usuario usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_administrador_asigna", nullable = false)
	public Usuario getAdministradorAsigna() {
		return administradorAsigna;
	}

	public void setAdministradorAsigna(Usuario administradorAsigna) {
		this.administradorAsigna = administradorAsigna;
	}

	@Column(name = "rol", nullable = false, length = 100)
	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_asignacion", nullable = false, length = 23)
	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_desvinculacion", nullable = true, length = 23)
	public Date getFechaDesvinculacion() {
		return fechaDesvinculacion;
	}

	public void setFechaDesvinculacion(Date fechaDesvinculacion) {
		this.fechaDesvinculacion = fechaDesvinculacion;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}

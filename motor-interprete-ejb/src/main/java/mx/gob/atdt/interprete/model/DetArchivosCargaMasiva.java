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
@Table(name = "det_archivos_carga_masiva", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetArchivosCargaMasiva.findByIdComponente",
			query = "SELECT NEW mx.gob.atdt.interprete.dto.DetArchivosCargaMasivaDTO("
					+ " a.idArchivoCargaMasiva, a.fechaCarga,"
					+ " e.idEstatusCarga, e.descripcion,"
					+ " uc.idUsuarioLlaveCdmx, uc.correo,"
					+ " c.idComponente,"
					+ " ua.idUsuarioLlaveCdmx, ua.correo,"
					+ " a.nombreArchivoOrigen, a.mensajeError)"
					+ " FROM DetArchivosCargaMasiva a"
					+ " LEFT JOIN a.estatusCarga e"
					+ " LEFT JOIN a.usuarioCarga uc"
					+ " LEFT JOIN a.usuarioAsignado ua"
					+ " LEFT JOIN a.componente c"
					+ " WHERE c.idComponente = :idComponente"
					+ " ORDER BY a.fechaCarga DESC"),
	@NamedQuery(name = "DetArchivosCargaMasiva.findById",
			query = "SELECT NEW mx.gob.atdt.interprete.dto.DetArchivosCargaMasivaDTO("
					+ " a.idArchivoCargaMasiva, a.fechaCarga,"
					+ " e.idEstatusCarga, e.descripcion,"
					+ " uc.idUsuarioLlaveCdmx, uc.correo,"
					+ " c.idComponente,"
					+ " ua.idUsuarioLlaveCdmx, ua.correo,"
					+ " a.nombreArchivoOrigen, a.mensajeError)"
					+ " FROM DetArchivosCargaMasiva a"
					+ " LEFT JOIN a.estatusCarga e"
					+ " LEFT JOIN a.usuarioCarga uc"
					+ " LEFT JOIN a.usuarioAsignado ua"
					+ " LEFT JOIN a.componente c"
					+ " WHERE a.idArchivoCargaMasiva = :idArchivoCargaMasiva"),
	@NamedQuery(name = "DetArchivosCargaMasiva.findAll",
		    query = "SELECT NEW mx.gob.atdt.interprete.dto.DetArchivosCargaMasivaDTO("
		            + "a.idArchivoCargaMasiva, a.fechaCarga, "
		            + "e.idEstatusCarga, e.descripcion, "
		            + "uc.idUsuarioLlaveCdmx, uc.correo, uc.nombre, uc.primerApellido, uc.segundoApellido,"
		            + "c.idComponente, "
		            + "ua.idUsuarioLlaveCdmx, ua.correo, ua.nombre, ua.primerApellido, ua.segundoApellido,"
		            + "a.nombreArchivoOrigen, a.mensajeError) "
		            + "FROM DetArchivosCargaMasiva a "
		            + "LEFT JOIN a.estatusCarga e "
		            + "LEFT JOIN a.usuarioCarga uc "
		            + "LEFT JOIN a.usuarioAsignado ua "
		            + "LEFT JOIN a.componente c "
		            + "ORDER BY a.fechaCarga DESC")
})
public class DetArchivosCargaMasiva implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_archivo_carga_masiva")
	private Long idArchivoCargaMasiva;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_carga", nullable = false)
	private Date fechaCarga;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_carga", nullable = false)
	private Usuario usuarioCarga;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus_carga", nullable = false)
	private CatEstatusCargaMasiva estatusCarga;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	private Componente componente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_asignado", nullable = false)
	private Usuario usuarioAsignado;

	@Column(name = "nombre_archivo_origen", nullable = false, length = 255)
	private String nombreArchivoOrigen;

	@Column(name = "mensaje_error", length = 500)
	private String mensajeError;

	public Long getIdArchivoCargaMasiva() {
		return idArchivoCargaMasiva;
	}

	public void setIdArchivoCargaMasiva(Long idArchivoCargaMasiva) {
		this.idArchivoCargaMasiva = idArchivoCargaMasiva;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public Usuario getUsuarioCarga() {
		return usuarioCarga;
	}

	public void setUsuarioCarga(Usuario usuarioCarga) {
		this.usuarioCarga = usuarioCarga;
	}

	public CatEstatusCargaMasiva getEstatusCarga() {
		return estatusCarga;
	}

	public void setEstatusCarga(CatEstatusCargaMasiva estatusCarga) {
		this.estatusCarga = estatusCarga;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public Usuario getUsuarioAsignado() {
		return usuarioAsignado;
	}

	public void setUsuarioAsignado(Usuario usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
	}

	public String getNombreArchivoOrigen() {
		return nombreArchivoOrigen;
	}

	public void setNombreArchivoOrigen(String nombreArchivoOrigen) {
		this.nombreArchivoOrigen = nombreArchivoOrigen;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}
}

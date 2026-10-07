package mx.gob.atdt.interprete.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "det_tramites_linea_captura", schema = "motor_interprete")

@NamedQueries({

    @NamedQuery(
        name = "DetTramitesLineaCaptura.findById",
        query = "SELECT new mx.gob.atdt.interprete.dto.DetTramitesLineaCapturaDTO( "
              + "l.idTramiteLineaCaptura, d.idDetalleLineaCaptura, "
              + "l.homoclave, l.variante, l.descripcion, "
              + "l.importe, l.numeroConceptos, "
              + "l.idUsuarioRegistro, l.fechaCreacion, l.fechaActualizacion, "
              + "l.activo, l.seccionSincronizada) "
              + "FROM DetTramitesLineaCaptura l "
              + "JOIN l.detLineaCaptura d "
              + "WHERE l.idTramiteLineaCaptura = :idTramiteLineaCaptura"
    ),

    @NamedQuery(
        name = "DetTramitesLineaCaptura.findByLineaCaptura",
        query = "SELECT new mx.gob.atdt.interprete.dto.DetTramitesLineaCapturaDTO( "
              + "l.idTramiteLineaCaptura, d.idDetalleLineaCaptura, "
              + "l.homoclave, l.variante, l.descripcion, "
              + "l.importe, l.numeroConceptos, "
              + "l.idUsuarioRegistro, l.fechaCreacion, l.fechaActualizacion, "
              + "l.activo, l.seccionSincronizada) "
              + "FROM DetTramitesLineaCaptura l "
              + "JOIN l.detLineaCaptura d "
              + "WHERE d.idDetalleLineaCaptura = :idDetalleLineaCaptura "
              + "AND l.activo = true"
    )
})
public class DetTramitesLineaCaptura implements java.io.Serializable {

	private static final long serialVersionUID = -4377873420161783392L;
	
	private long idTramiteLineaCaptura;
	private DetLineaCaptura detLineaCaptura;
	private String homoclave;
	private String variante;
	private String descripcion;
	private long importe;
	private int numeroConceptos;
	private long idUsuarioRegistro;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	private Set<DetConceptosTramite> detConceptosTramites = new HashSet<>();

	public DetTramitesLineaCaptura() {
	}

	@SuppressWarnings({"java:S107"})
	public DetTramitesLineaCaptura(long idTramiteLineaCaptura, DetLineaCaptura detLineaCaptura, String homoclave,
			String variante, String descripcion, long importe, int numeroConceptos, long idUsuarioRegistro,
			Date fechaCreacion, Date fechaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idTramiteLineaCaptura = idTramiteLineaCaptura;
		this.detLineaCaptura = detLineaCaptura;
		this.homoclave = homoclave;
		this.variante = variante;
		this.descripcion = descripcion;
		this.importe = importe;
		this.numeroConceptos = numeroConceptos;
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	@SuppressWarnings({"java:S107"})
	public DetTramitesLineaCaptura(long idTramiteLineaCaptura, DetLineaCaptura detLineaCaptura, String homoclave,
			String variante, String descripcion, long importe, int numeroConceptos, long idUsuarioRegistro,
			Date fechaCreacion, Date fechaActualizacion, boolean activo, boolean seccionSincronizada,
			Set<DetConceptosTramite> detConceptosTramites) {
		this.idTramiteLineaCaptura = idTramiteLineaCaptura;
		this.detLineaCaptura = detLineaCaptura;
		this.homoclave = homoclave;
		this.variante = variante;
		this.descripcion = descripcion;
		this.importe = importe;
		this.numeroConceptos = numeroConceptos;
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		this.detConceptosTramites = detConceptosTramites;
	}

	@Id
	@Column(name = "id_tramite_linea_captura", unique = true, nullable = false)
	public long getIdTramiteLineaCaptura() {
		return this.idTramiteLineaCaptura;
	}

	public void setIdTramiteLineaCaptura(long idTramiteLineaCaptura) {
		this.idTramiteLineaCaptura = idTramiteLineaCaptura;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_detalle_linea_captura", nullable = false)
	public DetLineaCaptura getDetLineaCaptura() {
		return this.detLineaCaptura;
	}

	public void setDetLineaCaptura(DetLineaCaptura detLineaCaptura) {
		this.detLineaCaptura = detLineaCaptura;
	}

	@Column(name = "homoclave", nullable = false, length = 30)
	public String getHomoclave() {
		return this.homoclave;
	}

	public void setHomoclave(String homoclave) {
		this.homoclave = homoclave;
	}

	@Column(name = "variante", nullable = false, length = 3)
	public String getVariante() {
		return this.variante;
	}

	public void setVariante(String variante) {
		this.variante = variante;
	}

	@Column(name = "descripcion", nullable = false, length = 350)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "importe", nullable = false, precision = 14, scale = 0)
	public long getImporte() {
		return this.importe;
	}

	public void setImporte(long importe) {
		this.importe = importe;
	}

	@Column(name = "numero_conceptos", nullable = false)
	public int getNumeroConceptos() {
		return this.numeroConceptos;
	}

	public void setNumeroConceptos(int numeroConceptos) {
		this.numeroConceptos = numeroConceptos;
	}

	@Column(name = "id_usuario_registro", nullable = false)
	public long getIdUsuarioRegistro() {
		return this.idUsuarioRegistro;
	}

	public void setIdUsuarioRegistro(long idUsuarioRegistro) {
		this.idUsuarioRegistro = idUsuarioRegistro;
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
	@Column(name = "fecha_actualizacion", nullable = false, length = 29)
	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detTramitesLineaCaptura")
	public Set<DetConceptosTramite> getDetConceptosTramites() {
		return this.detConceptosTramites;
	}

	public void setDetConceptosTramites(Set<DetConceptosTramite> detConceptosTramites) {
		this.detConceptosTramites = detConceptosTramites;
	}

}

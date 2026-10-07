package mx.gob.atdt.interprete.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "det_linea_captura", schema = "motor_interprete")

@NamedQueries({

    @NamedQuery(name = "DetLineaCaptura.findById",
        query = "SELECT new mx.gob.atdt.interprete.dto.DetLineaCapturaDTO( "
                + "d.idDetalleLineaCaptura, p.idProyecto, "
                + "cd.idDependenciaPago, cd.sigla, cd.descripcion, "
                + "cu.idUnidadAdministrativaPago, cu.descripcion, cu.clave,"
                + "d.vigencia, tv.idTipoVigencia, tv.clave, tv.descripcion, "
                + "tp.idTipoPersona, tp.clave, tp.descripcion, "
                + "d.idUsuarioRegistro, d.fechaCreacion, d.fechaActualizacion, "
                + "d.completo, d.activo, d.seccionSincronizada) "
                + "FROM DetLineaCaptura d "
                + "JOIN d.proyecto p "
                + "JOIN d.dependenciaPago cd "
                + "JOIN d.unidadAdministrativaPago cu "
                + "JOIN d.tipoVigencia tv "
                + "JOIN d.tipoPersona tp "
                + "WHERE d.idDetalleLineaCaptura = :idDetalleLineaCaptura"),

    @NamedQuery(name = "DetLineaCaptura.findByIdProyecto",
	    query = "SELECT new mx.gob.atdt.interprete.dto.DetLineaCapturaDTO( "
	            + "d.idDetalleLineaCaptura, p.idProyecto, "
	            + "cd.idDependenciaPago, cd.sigla, cd.descripcion, "
	            + "cu.idUnidadAdministrativaPago, cu.descripcion, cu.clave, "
	            + "d.vigencia, tv.idTipoVigencia, tv.clave, tv.descripcion, "
	            + "tp.idTipoPersona, tp.clave,tp.descripcion, "
	            + "d.idUsuarioRegistro, d.fechaCreacion, d.fechaActualizacion, "
	            + "d.completo, d.activo, d.seccionSincronizada) "
	            + "FROM DetLineaCaptura d "
	            + "JOIN d.proyecto p "
	            + "JOIN d.dependenciaPago cd "
	            + "JOIN d.unidadAdministrativaPago cu "
	            + "JOIN d.tipoVigencia tv "
	            + "JOIN d.tipoPersona tp "
	            + "WHERE p.idProyecto = :idProyecto "
	            + "AND d.activo = true "), 
    
    @NamedQuery(name = "DetLineaCaptura.findByIdDetalleLineaCaptura",
	    query = "SELECT new mx.gob.atdt.interprete.dto.DetLineaCapturaDTO( "
	            + "d.idDetalleLineaCaptura, p.idProyecto, "
	            + "cd.idDependenciaPago, cu.idUnidadAdministrativaPago, "
	            + "d.vigencia, tv.idTipoVigencia, tp.idTipoPersona, "
	            + "d.idUsuarioRegistro, d.fechaCreacion, d.fechaActualizacion, "
	            + "d.completo, d.activo, d.seccionSincronizada) "
	            + "FROM DetLineaCaptura d "
	            + "JOIN d.proyecto p "
	            + "JOIN d.dependenciaPago cd "
	            + "JOIN d.unidadAdministrativaPago cu "
	            + "JOIN d.tipoVigencia tv "
	            + "JOIN d.tipoPersona tp "
	            + "WHERE d.idDetalleLineaCaptura = :idDetalleLineaCaptura")
})

public class DetLineaCaptura implements java.io.Serializable {

	private static final long serialVersionUID = -8699779272122962809L;
	
	private long idDetalleLineaCaptura;
	private Proyecto proyecto;
	private CatDependenciaPago dependenciaPago;
	private CatUnidadAdministrativaPago unidadAdministrativaPago;
	private int vigencia;
	private CatTipoVigencia tipoVigencia;
	private CatTipoPersona tipoPersona;
	private long idUsuarioRegistro;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private boolean completo;
	private boolean activo;
	private boolean seccionSincronizada;
	private Set<DetTramitesLineaCaptura> detTramitesLineaCapturas = new HashSet<>();
	private Set<DetSecurityDomainLineasCaptura> detSecurityDomainLineasCapturas = new HashSet<>();

	@Id
	@Column(name = "id_detalle_linea_captura", unique = true, nullable = false)
	public long getIdDetalleLineaCaptura() {
		return this.idDetalleLineaCaptura;
	}

	public void setIdDetalleLineaCaptura(long idDetalleLineaCaptura) {
		this.idDetalleLineaCaptura = idDetalleLineaCaptura;
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
	@JoinColumn(name = "id_dependencia_pago", nullable = false)
	public CatDependenciaPago getDependenciaPago() {
		return this.dependenciaPago;
	}

	public void setDependenciaPago(CatDependenciaPago dependenciaPago) {
		this.dependenciaPago = dependenciaPago;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_unidad_administrativa_pago", nullable = false)
	public CatUnidadAdministrativaPago getUnidadAdministrativaPago() {
		return this.unidadAdministrativaPago;
	}

	public void setUnidadAdministrativaPago(CatUnidadAdministrativaPago unidadAdministrativaPago) {
		this.unidadAdministrativaPago = unidadAdministrativaPago;
	}

	@Column(name = "vigencia", nullable = false)
	public int getVigencia() {
		return this.vigencia;
	}

	public void setVigencia(int vigencia) {
		this.vigencia = vigencia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_vigencia", nullable = false)
	public CatTipoVigencia  getTipoVigencia() {
		return this.tipoVigencia;
	}

	public void setTipoVigencia(CatTipoVigencia tipoVigencia) {
		this.tipoVigencia = tipoVigencia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_persona", nullable = false)
	public CatTipoPersona getTipoPersona() {
		return this.tipoPersona;
	}

	public void setTipoPersona(CatTipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	@Column(name = "id_usuario", nullable = false)
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

	@Column(name = "completo", nullable = false)
	public boolean isCompleto() {
		return this.completo;
	}

	public void setCompleto(boolean completo) {
		this.completo = completo;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detLineaCaptura")
	public Set<DetTramitesLineaCaptura> getDetTramitesLineaCapturas() {
		return this.detTramitesLineaCapturas;
	}

	public void setDetTramitesLineaCapturas(Set<DetTramitesLineaCaptura> detTramitesLineaCapturas) {
		this.detTramitesLineaCapturas = detTramitesLineaCapturas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detLineaCaptura")
	public Set<DetSecurityDomainLineasCaptura> getDetSecurityDomainLineasCapturas() {
		return this.detSecurityDomainLineasCapturas;
	}

	public void setDetSecurityDomainLineasCapturas(
			Set<DetSecurityDomainLineasCaptura> detSecurityDomainLineasCapturas) {
		this.detSecurityDomainLineasCapturas = detSecurityDomainLineasCapturas;
	}

}

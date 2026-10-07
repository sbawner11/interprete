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
@Table(name = "det_acceso_llave", schema = "motor_interprete")
@NamedQueries({
    @NamedQuery(
        name = "DetAccesoLlave.findByIdProyecto",
        query = "SELECT new mx.gob.atdt.interprete.dto.DetAccesoLLaveDTO(" +
                "p.idProyecto, d.idDetalleAcceso, d.claveSistema, d.urlRedireccionar, d.usuarioDominoSeg, d.contrasenaDominioSeg,  " +
                "d.codigoSecreto, d.fechaCreacion, d.fechaUltimaActualizacion, d.activo, d.limitarUnicoTramite, " +
                "d.seccionSincronizada, d.autenticacionCiudadano," +
                "d.validaRol, d.rolesPermitidos) " +
                "FROM DetAccesoLlave d " +
                "JOIN d.proyecto p " +
                "WHERE p.idProyecto = :idProyecto"
    )
})   

public class DetAccesoLlave implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2661288536278808160L;
	
	private Long idDetalleAcceso;
	private Proyecto proyecto;
	private String claveSistema;
	private String urlRedireccionar;
	private String usuarioDominoSeg;
	private String contrasenaDominioSeg;
	private String codigoSecreto;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean limitarUnicoTramite;
	private boolean seccionSincronizada;
	private boolean autenticacionCiudadano;
	private boolean validaRol;
	private String rolesPermitidos;

	public DetAccesoLlave() {
	}

	public DetAccesoLlave(Long idDetalleAcceso, Proyecto proyecto, String claveSistema,
			String urlRedireccionar, String usuarioDominoSeg, String contrasenaDominioSeg, String codigoSecreto,
			Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idDetalleAcceso = idDetalleAcceso;
		this.proyecto = proyecto;
		this.claveSistema = claveSistema;
		this.urlRedireccionar = urlRedireccionar;
		this.usuarioDominoSeg = usuarioDominoSeg;
		this.contrasenaDominioSeg = contrasenaDominioSeg;
		this.codigoSecreto = codigoSecreto;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		this.validaRol = validaRol;
		this.rolesPermitidos = rolesPermitidos;
	}

	@Id
	@Column(name = "id_detalle_acceso", unique = true, nullable = false)
	public Long getIdDetalleAcceso() {
		return this.idDetalleAcceso;
	}

	public void setIdDetalleAcceso(Long idDetalleAcceso) {
		this.idDetalleAcceso = idDetalleAcceso;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "clave_sistema", nullable = false, length = 60)
	public String getClaveSistema() {
		return this.claveSistema;
	}

	public void setClaveSistema(String claveSistema) {
		this.claveSistema = claveSistema;
	}

	@Column(name = "url_redireccionar", nullable = false, length = 400)
	public String getUrlRedireccionar() {
		return this.urlRedireccionar;
	}

	public void setUrlRedireccionar(String urlRedireccionar) {
		this.urlRedireccionar = urlRedireccionar;
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
	
	@Column(name = "limitar_unico_tramite", nullable = false)
	public boolean isLimitarUnicoTramite() {
		return limitarUnicoTramite;
	}

	public void setLimitarUnicoTramite(boolean limitarUnicoTramite) {
		this.limitarUnicoTramite = limitarUnicoTramite;
	}	
	
	@Column(name = "seccion_sincronizada", nullable = false)
	public boolean isSeccionSincronizada() {
		return this.seccionSincronizada;
	}

	public void setSeccionSincronizada(boolean seccionSincronizada) {
		this.seccionSincronizada = seccionSincronizada;
	}

	@Column(name = "autenticacion_ciudadano", nullable = false)
	public boolean isAutenticacionCiudadano() {
		return autenticacionCiudadano;
	}

	public void setAutenticacionCiudadano(boolean autenticacionCiudadano) {
		this.autenticacionCiudadano = autenticacionCiudadano;
	}

	@Column(name = "usuario_dominio_seg", nullable = true, length = 100)
	public String getUsuarioDominoSeg() {
		return usuarioDominoSeg;
	}

	public void setUsuarioDominoSeg(String usuarioDominoSeg) {
		this.usuarioDominoSeg = usuarioDominoSeg;
	}

	@Column(name = "contrasena_dominio_seg", nullable = true, length = 100)
	public String getContrasenaDominioSeg() {
		return contrasenaDominioSeg;
	}

	public void setContrasenaDominioSeg(String contrasenaDominioSeg) {
		this.contrasenaDominioSeg = contrasenaDominioSeg;
	}

	@Column(name = "codigo_secreto", nullable = true, length = 100)
	public String getCodigoSecreto() {
		return codigoSecreto;
	}

	public void setCodigoSecreto(String codigoSecreto) {
		this.codigoSecreto = codigoSecreto;
	}
	
	@Column(name = "valida_rol", nullable = false)
	public boolean isValidaRol() {
		return validaRol;
	}
	
	public void setValidaRol(boolean validaRol) {
		this.validaRol = validaRol;
	}
	
	@Column(name = "roles_permitidos", nullable = true, length = 100)
	public String getRolesPermitidos() {
		return rolesPermitidos;
	}
	
	public void setRolesPermitidos(String rolesPermitidos) {
		this.rolesPermitidos = rolesPermitidos;
	}
}
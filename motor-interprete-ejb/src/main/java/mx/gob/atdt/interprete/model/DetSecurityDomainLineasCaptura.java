package mx.gob.atdt.interprete.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "det_security_domain_lineas_captura", schema = "motor_interprete")
@NamedQueries({
	
    @NamedQuery(name = "DetSecurityDomainLineasCaptura.findByIdLineaCaptura",
		    query = "SELECT new mx.gob.atdt.interprete.dto.DetSecurityDomainLineasCapturaDTO( "
		            + " d.idSecurityDomainLc, lc.idDetalleLineaCaptura, d.usuario, d.contrasenia, d.urlSistema, "
		            + " d.idUsuarioRegistro, d.fechaCreacion, d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada ) "
		            + "FROM DetSecurityDomainLineasCaptura d "
		            + "JOIN d.detLineaCaptura lc "
		            + "WHERE lc.idDetalleLineaCaptura = :idDetalleLineaCaptura "
		            + "AND d.activo = true "),
    @NamedQuery(name = "DetSecurityDomainLineasCaptura.findByIdProyecto",
            query = "SELECT new mx.gob.atdt.interprete.dto.DetSecurityDomainLineasCapturaDTO( "
                    + " d.idSecurityDomainLc, lc.idDetalleLineaCaptura, d.usuario, d.contrasenia, d.urlSistema, "
                    + " d.idUsuarioRegistro, d.fechaCreacion, d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada ) "
                    + "FROM DetSecurityDomainLineasCaptura d "
                    + "JOIN d.detLineaCaptura lc "
                    + "JOIN lc.proyecto p "
                    + "WHERE p.idProyecto = :idProyecto "
                    + "AND d.activo = true ")
    })
public class DetSecurityDomainLineasCaptura implements java.io.Serializable {

    private static final long serialVersionUID = -9182858825453018597L;
    
	private long idSecurityDomainLc;
    private DetLineaCaptura detLineaCaptura;
    private String usuario;
    private String contrasenia;
    private String urlSistema;
    private long idUsuarioRegistro;
    private Date fechaCreacion;
    private Date fechaUltimaActualizacion;
    private boolean activo;
    private boolean seccionSincronizada;

    /* CONSTRUCTORES */

    public DetSecurityDomainLineasCaptura() {
    }

    public DetSecurityDomainLineasCaptura(long idSecurityDomainLc) {
        this.idSecurityDomainLc = idSecurityDomainLc;
    }

    public DetSecurityDomainLineasCaptura(long idSecurityDomainLc, DetLineaCaptura detLineaCaptura) {
        this.idSecurityDomainLc = idSecurityDomainLc;
        this.detLineaCaptura = detLineaCaptura;
    }

    /* GETTERS / SETTERS */

    @Id
    @Column(name = "id_security_domain_lc", unique = true, nullable = false)
    public long getIdSecurityDomainLc() {
        return idSecurityDomainLc;
    }

    public void setIdSecurityDomainLc(long idSecurityDomainLc) {
        this.idSecurityDomainLc = idSecurityDomainLc;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_detalle_linea_captura", nullable = false)
    public DetLineaCaptura getDetLineaCaptura() {
        return detLineaCaptura;
    }

    public void setDetLineaCaptura(DetLineaCaptura detLineaCaptura) {
        this.detLineaCaptura = detLineaCaptura;
    }

    @Column(name = "usuario", nullable = false, length = 100)
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Column(name = "contrasenia", nullable = false, length = 100)
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    @Column(name = "url_sistema", nullable = false, length = 400)
    public String getUrlSistema() {
        return urlSistema;
    }

    public void setUrlSistema(String urlSistema) {
        this.urlSistema = urlSistema;
    }
    
    @Column(name = "id_usuario_registro", nullable = false)
    public long getIdUsuarioRegistro() {
        return idUsuarioRegistro;
    }

    public void setIdUsuarioRegistro(long idUsuarioRegistro) {
        this.idUsuarioRegistro = idUsuarioRegistro;
    }
    

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false)
    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ultima_actualizacion", nullable = false)
    public Date getFechaUltimaActualizacion() {
        return fechaUltimaActualizacion;
    }

    public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    @Column(name = "activo", nullable = false)
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Column(name = "seccion_sincronizada", nullable = false)
    public boolean isSeccionSincronizada() {
        return seccionSincronizada;
    }

    public void setSeccionSincronizada(boolean seccionSincronizada) {
        this.seccionSincronizada = seccionSincronizada;
    }
}
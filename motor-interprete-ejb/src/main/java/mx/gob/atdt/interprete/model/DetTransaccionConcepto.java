package mx.gob.atdt.interprete.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "det_transaccion_concepto", schema = "motor_interprete")

@NamedQueries({

    @NamedQuery(
        name = "DetTransaccionConcepto.findById",
        query = "SELECT new mx.gob.atdt.interprete.dto.DetTransaccionConceptoDTO( "
              + "d.idTransaccionConcepto, c.idConceptoTramite, "
              + "d.clave, d.valor, "
              + "d.actualizacion, d.recargo, d.multa, d.idUsuarioRegistro, "
              + "d.fechaCreacion, d.fechaActualizacion, "
              + "d.activo, d.seccionSincronizada) "
              + "FROM DetTransaccionConcepto d "
              + "JOIN d.detConceptosTramite c "
              + "WHERE d.idTransaccionConcepto = :idTransaccionConcepto"),

    @NamedQuery(
    	name = "DetTransaccionConcepto.findByIdConcepto",
        query = "SELECT new mx.gob.atdt.interprete.dto.DetTransaccionConceptoDTO( "
        	  + "d.idTransaccionConcepto, c.idConceptoTramite, "
              + "d.clave, d.valor, "
              + "d.actualizacion, d.recargo, d.multa, d.idUsuarioRegistro, "
              + "d.fechaCreacion, d.fechaActualizacion, "
              + "d.activo, d.seccionSincronizada) "
              + "FROM DetTransaccionConcepto d "
              + "JOIN d.detConceptosTramite c "
              + "WHERE c.idConceptoTramite = :idConceptoTramite "
              + "AND d.activo = true ")    
})

public class DetTransaccionConcepto implements java.io.Serializable {

    private static final long serialVersionUID = 1458529120439862666L;
    
	private long idTransaccionConcepto;
    private DetConceptosTramite detConceptosTramite;
    private int clave;
    private long valor;
    private boolean actualizacion;
    private boolean recargo;
    private boolean multa;
    private long idUsuarioRegistro;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private boolean activo;
    private boolean seccionSincronizada;

    /* ID */

    @Id
    @Column(name = "id_transaccion_concepto", unique = true, nullable = false)
    public long getIdTransaccionConcepto() {
        return idTransaccionConcepto;
    }

    public void setIdTransaccionConcepto(long idTransaccionConcepto) {
        this.idTransaccionConcepto = idTransaccionConcepto;
    }

    /* RELACIONES */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_concepto_tramite", nullable = false)
    public DetConceptosTramite getDetConceptosTramite() {
        return detConceptosTramite;
    }

    public void setDetConceptosTramite(DetConceptosTramite detConceptosTramite) {
        this.detConceptosTramite = detConceptosTramite;
    }

    /* CAMPOS */

    @Column(name = "clave", nullable = false)
    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    @Column(name = "valor", nullable = false, precision = 14, scale = 0)
    public long getValor() {
        return valor;
    }

    public void setValor(long valor) {
        this.valor = valor;
    }

    @Column(name = "actualizacion", nullable = false)
    public boolean isActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(boolean actualizacion) {
        this.actualizacion = actualizacion;
    }

    @Column(name = "recargo", nullable = false)
    public boolean isRecargo() {
        return recargo;
    }

    public void setRecargo(boolean recargo) {
        this.recargo = recargo;
    }

    @Column(name = "multa", nullable = false)
    public boolean isMulta() {
        return multa;
    }

    public void setMulta(boolean multa) {
        this.multa = multa;
    }    

	@Column(name = "id_usuario_registro", nullable = false)
	public long getIdUsuarioRegistro() {
		return this.idUsuarioRegistro;
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
    @Column(name = "fecha_actualizacion", nullable = false)
    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
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
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "bit_asignacion_revisor_tramite", schema = "motor_interprete")
public class BitAsignacionRevisorTramite implements Serializable {

	private static final long serialVersionUID = 7128919977414820097L;
	private Long idBitAsignacion;
	private Long idTramite;
	private Date fechaAsignacion;
	private Usuario usuarioRevisor;
	private Usuario usuarioAsigna;
	
	public BitAsignacionRevisorTramite() {}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_bit_asignacion", unique = true, nullable = false)
	public Long getIdBitAsignacion() {
		return idBitAsignacion;
	}

	public void setIdBitAsignacion(Long idBitAsignacion) {
		this.idBitAsignacion = idBitAsignacion;
	}

	@Column(name = "id_tramite", nullable = false)
	public Long getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_asignacion", nullable = false, length = 29)
	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_revisor", nullable = false)
	public Usuario getUsuarioRevisor() {
		return usuarioRevisor;
	}

	public void setUsuarioRevisor(Usuario usuarioRevisor) {
		this.usuarioRevisor = usuarioRevisor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_asigna", nullable = false)
	public Usuario getUsuarioAsigna() {
		return usuarioAsigna;
	}

	public void setUsuarioAsigna(Usuario usuarioAsigna) {
		this.usuarioAsigna = usuarioAsigna;
	}
	
}

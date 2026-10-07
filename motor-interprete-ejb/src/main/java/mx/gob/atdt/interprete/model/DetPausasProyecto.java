package mx.gob.atdt.interprete.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "det_pausas_proyecto", schema = "motor_interprete")
public class DetPausasProyecto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8346551951163098149L;	
	
	private Long idPausaProyecto;
	private CatMotivosPausa catMotivosPausa;
	private Proyecto proyecto;
	private Usuario usuario;
	private String observacionesPausa;
	private Date fechaCreacion;

	public DetPausasProyecto() {
	}

	public DetPausasProyecto(Long idPausaProyecto, CatMotivosPausa catMotivosPausa, Proyecto proyecto, Usuario usuario,
			Date fechaCreacion) {
		this.idPausaProyecto = idPausaProyecto;
		this.catMotivosPausa = catMotivosPausa;
		this.proyecto = proyecto;
		this.usuario = usuario;
		this.fechaCreacion = fechaCreacion;
	}

	public DetPausasProyecto(Long idPausaProyecto, CatMotivosPausa catMotivosPausa, Proyecto proyecto, Usuario usuario,
			String observacionesPausa, Date fechaCreacion) {
		this.idPausaProyecto = idPausaProyecto;
		this.catMotivosPausa = catMotivosPausa;
		this.proyecto = proyecto;
		this.usuario = usuario;
		this.observacionesPausa = observacionesPausa;
		this.fechaCreacion = fechaCreacion;
	}

	@Id
	@Column(name = "id_pausa_proyecto", unique = true, nullable = false)
	public Long getIdPausaProyecto() {
		return this.idPausaProyecto;
	}

	public void setIdPausaProyecto(Long idPausaProyecto) {
		this.idPausaProyecto = idPausaProyecto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_motivo", nullable = false)
	public CatMotivosPausa getCatMotivosPausa() {
		return this.catMotivosPausa;
	}

	public void setCatMotivosPausa(CatMotivosPausa catMotivosPausa) {
		this.catMotivosPausa = catMotivosPausa;
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
	@JoinColumn(name = "id_usuario_llave_cdmx", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "observaciones_pausa")
	public String getObservacionesPausa() {
		return this.observacionesPausa;
	}

	public void setObservacionesPausa(String observacionesPausa) {
		this.observacionesPausa = observacionesPausa;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false, length = 29)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}

package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "det_requisito", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "DetRequisito.findByIdDetalleHome", query = "SELECT new mx.gob.atdt.interprete.dto.DetRequisitoDTO(dr.idRequisito, dr.detHome.idDetalleHome, dr.descripcionRequisito, dr.orden, dr.activo) FROM DetRequisito dr JOIN dr.detHome dh WHERE dh.idDetalleHome = :idDetalleHome AND dr.activo = true ORDER BY dr.orden"), 
		@NamedQuery(name = "DetRequisito.findByIdRequisito", query = "SELECT new mx.gob.atdt.interprete.dto.DetRequisitoDTO(dr.idRequisito) FROM DetRequisito dr WHERE dr.idRequisito =:idRequisito")
})
public class DetRequisito implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2524488680601377089L;

	private Long idRequisito;
	private DetHome detHome;
	private String descripcionRequisito;
	private int orden;
	private boolean activo;
	private Set<DetEspecificacionRequisito> detEspecificacionRequisitos = new HashSet<DetEspecificacionRequisito>(
			0);

	public DetRequisito() {
	}

	public DetRequisito(Long idRequisito, DetHome detHome, String descripcionRequisito, int orden,
			boolean activo) {
		this.idRequisito = idRequisito;
		this.detHome = detHome;
		this.descripcionRequisito = descripcionRequisito;
		this.orden = orden;
		this.activo = activo;
	}

	public DetRequisito(Long idRequisito, DetHome detHome, String descripcionRequisito, int orden,
			boolean activo, Set<DetEspecificacionRequisito> detEspecificacionRequisitos) {
		this.idRequisito = idRequisito;
		this.detHome = detHome;
		this.descripcionRequisito = descripcionRequisito;
		this.orden = orden;
		this.activo = activo;
		this.detEspecificacionRequisitos = detEspecificacionRequisitos;
	}

	@Id
	@Column(name = "id_requisito", unique = true, nullable = false)
	public Long getIdRequisito() {
		return this.idRequisito;
	}

	public void setIdRequisito(Long idRequisito) {
		this.idRequisito = idRequisito;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_detalle_home", nullable = false)
	public DetHome getDetHome() {
		return this.detHome;
	}

	public void setDetHome(DetHome detHome) {
		this.detHome = detHome;
	}

	@Column(name = "descripcion_requisito", nullable = false, length = 200)
	public String getDescripcionRequisito() {
		return this.descripcionRequisito;
	}

	public void setDescripcionRequisito(String descripcionRequisito) {
		this.descripcionRequisito = descripcionRequisito;
	}

	@Column(name = "orden", nullable = false)
	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detRequisito")
	public Set<DetEspecificacionRequisito> getDetEspecificacionRequisitos() {
		return this.detEspecificacionRequisitos;
	}

	public void setDetEspecificacionRequisitos(
			Set<DetEspecificacionRequisito> detEspecificacionRequisitos) {
		this.detEspecificacionRequisitos = detEspecificacionRequisitos;
	}

}

package mx.gob.atdt.interprete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "det_poblacion_objetivo", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "DetPoblacionObjetivo.findByIdDetalleHome", query = "SELECT new mx.gob.atdt.interprete.dto.DetPoblacionObjetivoDTO( d.idPoblacionObjetivo, h.idDetalleHome, d.descripcionPoblacionObjetivo, d.orden, d.activo )  FROM DetPoblacionObjetivo d JOIN d.detHome h  WHERE h.idDetalleHome = :idDetalleHome AND d.activo = true ORDER BY d.orden") })
public class DetPoblacionObjetivo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1277461447362981334L;

	private Long idPoblacionObjetivo;
	private DetHome detHome;
	private String descripcionPoblacionObjetivo;
	private int orden;
	private boolean activo;

	public DetPoblacionObjetivo() {
	}

	public DetPoblacionObjetivo(Long idPoblacionObjetivo, DetHome detHome,
			String descripcionPoblacionObjetivo, int orden, boolean activo) {
		this.idPoblacionObjetivo = idPoblacionObjetivo;
		this.detHome = detHome;
		this.descripcionPoblacionObjetivo = descripcionPoblacionObjetivo;
		this.orden = orden;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_poblacion_objetivo", unique = true, nullable = false)
	public Long getIdPoblacionObjetivo() {
		return this.idPoblacionObjetivo;
	}

	public void setIdPoblacionObjetivo(Long idPoblacionObjetivo) {
		this.idPoblacionObjetivo = idPoblacionObjetivo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_detalle_home", nullable = false)
	public DetHome getDetHome() {
		return this.detHome;
	}

	public void setDetHome(DetHome detHome) {
		this.detHome = detHome;
	}

	@Column(name = "descripcion_poblacion_objetivo", nullable = false, length = 200)
	public String getDescripcionPoblacionObjetivo() {
		return this.descripcionPoblacionObjetivo;
	}

	public void setDescripcionPoblacionObjetivo(String descripcionPoblacionObjetivo) {
		this.descripcionPoblacionObjetivo = descripcionPoblacionObjetivo;
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

}

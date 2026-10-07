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
@Table(name = "det_excepcion_tramite", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetExcepcionTramite.findByIdDetalleHome", query = "SELECT new mx.gob.atdt.interprete.dto.DetExcepcionTramiteDTO(det.idExcepcion, dh.idDetalleHome, det.descripcionExcepcion, det.orden) FROM DetExcepcionTramite det JOIN det.detHome dh WHERE dh.idDetalleHome = :idDetalleHome AND det.activo = true ORDER BY det.orden") 
	})
public class DetExcepcionTramite implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3006838569851935290L;
	
	private Long idExcepcion;
	private DetHome detHome;
	private String descripcionExcepcion;
	private int orden;
	private boolean activo;

	public DetExcepcionTramite() {
	}

	public DetExcepcionTramite(Long idExcepcion, DetHome detHome, String descripcionExcepcion, int orden,
			boolean activo) {
		this.idExcepcion = idExcepcion;
		this.detHome = detHome;
		this.descripcionExcepcion = descripcionExcepcion;
		this.orden = orden;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_excepcion", unique = true, nullable = false)
	public Long getIdExcepcion() {
		return this.idExcepcion;
	}

	public void setIdExcepcion(Long idExcepcion) {
		this.idExcepcion = idExcepcion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_detalle_home", nullable = false)
	public DetHome getDetHome() {
		return this.detHome;
	}

	public void setDetHome(DetHome detHome) {
		this.detHome = detHome;
	}

	@Column(name = "descripcion_excepcion", nullable = false, length = 200)
	public String getDescripcionExcepcion() {
		return this.descripcionExcepcion;
	}

	public void setDescripcionExcepcion(String descripcionExcepcion) {
		this.descripcionExcepcion = descripcionExcepcion;
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

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
@Table(name = "det_apoyo_otorgado", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "DetApoyoOtorgado.findByIdDetalleHome", query = "SELECT new mx.gob.atdt.interprete.dto.DetApoyoOtorgadoDTO(d.idApoyo, d.detHome.idDetalleHome, d.descripcionApoyoOtorgado, d.orden, d.activo) FROM DetApoyoOtorgado d JOIN d.detHome dh WHERE dh.idDetalleHome = :idDetalleHome AND d.activo = true ORDER BY d.orden") })
public class DetApoyoOtorgado implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3558891921979742920L;

	private Long idApoyo;
	private DetHome detHome;
	private String descripcionApoyoOtorgado;
	private int orden;
	private boolean activo;

	public DetApoyoOtorgado() {
	}

	public DetApoyoOtorgado(Long idApoyo, DetHome detHome, String descripcionApoyoOtorgado, int orden,
			boolean activo) {
		this.idApoyo = idApoyo;
		this.detHome = detHome;
		this.descripcionApoyoOtorgado = descripcionApoyoOtorgado;
		this.orden = orden;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_apoyo", unique = true, nullable = false)
	public Long getIdApoyo() {
		return this.idApoyo;
	}

	public void setIdApoyo(Long idApoyo) {
		this.idApoyo = idApoyo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_detalle_home", nullable = false)
	public DetHome getDetHome() {
		return this.detHome;
	}

	public void setDetHome(DetHome detHome) {
		this.detHome = detHome;
	}

	@Column(name = "descripcion_apoyo_otorgado", nullable = false, length = 200)
	public String getDescripcionApoyoOtorgado() {
		return this.descripcionApoyoOtorgado;
	}

	public void setDescripcionApoyoOtorgado(String descripcionApoyoOtorgado) {
		this.descripcionApoyoOtorgado = descripcionApoyoOtorgado;
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

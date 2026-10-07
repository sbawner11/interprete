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
@Table(name = "det_objetivos_programa", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetObjetivosPrograma.findByIdDetalleHome", query = "SELECT new mx.gob.atdt.interprete.dto.DetObjetivosProgramaDTO(dop.idObjetivo, dop.detHome.idDetalleHome, dop.descripcionObjetivo, dop.orden, dop.activo) FROM DetObjetivosPrograma dop JOIN dop.detHome dh WHERE dh.idDetalleHome = :idDetalleHome AND dop.activo = true ORDER BY dop.orden") 
	})
public class DetObjetivosPrograma implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1486893166859759316L;

	private Long idObjetivo;
	private DetHome detHome;
	private String descripcionObjetivo;
	private int orden;
	private boolean activo;

	public DetObjetivosPrograma() {
	}

	public DetObjetivosPrograma(Long idObjetivo, DetHome detHome, String descripcionObjetivo, int orden,
			boolean activo) {
		this.idObjetivo = idObjetivo;
		this.detHome = detHome;
		this.descripcionObjetivo = descripcionObjetivo;
		this.orden = orden;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_objetivo", unique = true, nullable = false)
	public Long getIdObjetivo() {
		return this.idObjetivo;
	}

	public void setIdObjetivo(Long idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_detalle_home", nullable = false)
	public DetHome getDetHome() {
		return this.detHome;
	}

	public void setDetHome(DetHome detHome) {
		this.detHome = detHome;
	}

	@Column(name = "descripcion_objetivo", nullable = false, length = 200)
	public String getDescripcionObjetivo() {
		return this.descripcionObjetivo;
	}

	public void setDescripcionObjetivo(String descripcionObjetivo) {
		this.descripcionObjetivo = descripcionObjetivo;
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

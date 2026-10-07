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
@Table(name = "det_especificacion_requisito", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetEspecificacionRequisito.findByIdRequisito", query = "SELECT new mx.gob.atdt.interprete.dto.DetEspecificacionRequisitoDTO(der.idEspecificacion, dr.idRequisito, der.descripcionEspecificacion, der.orden, der.activo)  FROM DetEspecificacionRequisito der JOIN der.detRequisito dr WHERE dr.idRequisito =:idRequisito AND der.activo = true ORDER BY der.orden"),
	@NamedQuery(name = "DetEspecificacionRequisito.deleteByIdRequisito", query = "DELETE FROM DetEspecificacionRequisito der WHERE der.detRequisito.idRequisito = :idRequisito")
	})
public class DetEspecificacionRequisito implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1023999792712203231L;

	private Long idEspecificacion;
	private DetRequisito detRequisito;
	private String descripcionEspecificacion;
	private int orden;
	private boolean activo;

	public DetEspecificacionRequisito() {
	}

	public DetEspecificacionRequisito(Long idEspecificacion, DetRequisito detRequisito,
			String descripcionEspecificacion, int orden, boolean activo) {
		this.idEspecificacion = idEspecificacion;
		this.detRequisito = detRequisito;
		this.descripcionEspecificacion = descripcionEspecificacion;
		this.orden = orden;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_especificacion", unique = true, nullable = false)
	public Long getIdEspecificacion() {
		return this.idEspecificacion;
	}

	public void setIdEspecificacion(Long idEspecificacion) {
		this.idEspecificacion = idEspecificacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_requisito", nullable = false)
	public DetRequisito getDetRequisito() {
		return this.detRequisito;
	}

	public void setDetRequisito(DetRequisito detRequisito) {
		this.detRequisito = detRequisito;
	}

	@Column(name = "descripcion_especificacion", nullable = false, length = 200)
	public String getDescripcionEspecificacion() {
		return this.descripcionEspecificacion;
	}

	public void setDescripcionEspecificacion(String descripcionEspecificacion) {
		this.descripcionEspecificacion = descripcionEspecificacion;
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

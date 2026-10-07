package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Table(name = "cat_tipo_costo", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatTipoCosto.findAllActivos", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoCostoDTO("
					+ " ctc.idTipoCosto, ctc.descripcion ) "
					+ " FROM CatTipoCosto ctc "
					+ " WHERE ctc.activo = true "),
	@NamedQuery(name="CatTipoCosto.findById", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoCostoDTO("
					+ " ctc.idTipoCosto, ctc.descripcion ) "
					+ " FROM CatTipoCosto ctc "
					+ " WHERE ctc.idTipoCosto = :idTipoCosto ")
})

public class CatTipoCosto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5217409739797092839L;
	
	private Integer idTipoCosto;
	private String descripcion;
	private boolean activo;
	private Set<DetPago> detallePagos = new HashSet<DetPago>(0);

	public CatTipoCosto() {
	}

	public CatTipoCosto(Integer idTipoCosto, String descripcion, boolean activo) {
		this.idTipoCosto = idTipoCosto;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	public CatTipoCosto(Integer idTipoCosto, String descripcion, boolean activo, Set<DetPago> detallePagos) {
		this.idTipoCosto = idTipoCosto;
		this.descripcion = descripcion;
		this.activo = activo;
		this.detallePagos = detallePagos;
	}

	@Id
	@Column(name = "id_tipo_costo", unique = true, nullable = false)
	public Integer getIdTipoCosto() {
		return this.idTipoCosto;
	}

	public void setIdTipoCosto(Integer idTipoCosto) {
		this.idTipoCosto = idTipoCosto;
	}

	@Column(name = "descripcion", nullable = false, length = 20)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catTipoCosto")
	public Set<DetPago> getDetallePagos() {
		return this.detallePagos;
	}

	public void setDetallePagos(Set<DetPago> detallePagos) {
		this.detallePagos = detallePagos;
	}
}

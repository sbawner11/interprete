package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cat_tipos_movimiento", schema = "motor_interprete")
public class CatTiposMovimiento implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6908989833153509852L;
	
	private int idTipoMovimiento;
	private String descripcion;
	private Set<BitMovimientosTramite> bitMovimientosTramites = new HashSet<BitMovimientosTramite>(0);

	public CatTiposMovimiento() {
	}

	public CatTiposMovimiento(int idTipoMovimiento, String descripcion) {
		this.idTipoMovimiento = idTipoMovimiento;
		this.descripcion = descripcion;
	}

	public CatTiposMovimiento(int idTipoMovimiento, String descripcion,
			Set<BitMovimientosTramite> bitMovimientosTramites) {
		this.idTipoMovimiento = idTipoMovimiento;
		this.descripcion = descripcion;
		this.bitMovimientosTramites = bitMovimientosTramites;
	}

	@Id
	@Column(name = "id_tipo_movimiento", unique = true, nullable = false)
	public int getIdTipoMovimiento() {
		return this.idTipoMovimiento;
	}

	public void setIdTipoMovimiento(int idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}

	@Column(name = "descripcion", nullable = false, length = 60)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catTiposMovimiento")
	public Set<BitMovimientosTramite> getBitMovimientosTramites() {
		return this.bitMovimientosTramites;
	}

	public void setBitMovimientosTramites(Set<BitMovimientosTramite> bitMovimientosTramites) {
		this.bitMovimientosTramites = bitMovimientosTramites;
	}

}

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
@Table(name = "cat_secciones_proyecto", schema = "motor_interprete")
public class CatSeccionesProyecto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4256744723130793186L;
	
	private Integer idSeccionProyecto;
	private String descripcion;
	private Set<BitMovimientosSecciones> bitMovimientosSeccioneses = new HashSet<BitMovimientosSecciones>(0);

	public CatSeccionesProyecto() {
	}

	public CatSeccionesProyecto(Integer idSeccionProyecto, String descripcion) {
		this.idSeccionProyecto = idSeccionProyecto;
		this.descripcion = descripcion;
	}

	public CatSeccionesProyecto(Integer idSeccionProyecto, String descripcion,
			Set<BitMovimientosSecciones> bitMovimientosSeccioneses) {
		this.idSeccionProyecto = idSeccionProyecto;
		this.descripcion = descripcion;
		this.bitMovimientosSeccioneses = bitMovimientosSeccioneses;
	}

	@Id
	@Column(name = "id_seccion_proyecto", unique = true, nullable = false)
	public Integer getIdSeccionProyecto() {
		return this.idSeccionProyecto;
	}

	public void setIdSeccionProyecto(Integer idSeccionProyecto) {
		this.idSeccionProyecto = idSeccionProyecto;
	}

	@Column(name = "descripcion", nullable = false, length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catSeccionesProyecto")
	public Set<BitMovimientosSecciones> getBitMovimientosSeccioneses() {
		return this.bitMovimientosSeccioneses;
	}

	public void setBitMovimientosSeccioneses(Set<BitMovimientosSecciones> bitMovimientosSeccioneses) {
		this.bitMovimientosSeccioneses = bitMovimientosSeccioneses;
	}

}

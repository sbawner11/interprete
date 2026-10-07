package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cat_estatus_proyecto", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatEstatusProyecto.findAll", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatEstatusProyectoDTO(cep.idEstatusProyecto, cep.descripcion) FROM CatEstatusProyecto cep")
})
public class CatEstatusProyecto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1849890545462366884L;
	
	private Integer idEstatusProyecto;
	private String descripcion;
	private Set<Proyecto> proyectos = new HashSet<Proyecto>(0);

	public CatEstatusProyecto() {
	}

	public CatEstatusProyecto(Integer idEstatusProyecto, String descripcion) {
		this.idEstatusProyecto = idEstatusProyecto;
		this.descripcion = descripcion;
	}

	public CatEstatusProyecto(Integer idEstatusProyecto, String descripcion, Set<Proyecto> proyectos) {
		this.idEstatusProyecto = idEstatusProyecto;
		this.descripcion = descripcion;
		this.proyectos = proyectos;
	}

	@Id
	@Column(name = "id_estatus_proyecto", unique = true, nullable = false)
	public Integer getIdEstatusProyecto() {
		return this.idEstatusProyecto;
	}

	public void setIdEstatusProyecto(Integer idEstatusProyecto) {
		this.idEstatusProyecto = idEstatusProyecto;
	}

	@Column(name = "descripcion", nullable = false, length = 60)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catEstatusProyecto")
	public Set<Proyecto> getProyectos() {
		return this.proyectos;
	}

	public void setProyectos(Set<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

}

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
@Table(name = "cat_tipo_proyecto", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatTipoProyecto.findAll", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoProyectoDTO(ctp.idTipoProyecto, ctp.descripcion) FROM CatTipoProyecto ctp")
})
public class CatTipoProyecto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4416557502652891144L;
	
	private Integer idTipoProyecto;
	private String descripcion;
	private Set<Proyecto> proyectos = new HashSet<Proyecto>(0);

	public CatTipoProyecto() {
	}

	public CatTipoProyecto(Integer idTipoProyecto, String descripcion) {
		this.idTipoProyecto = idTipoProyecto;
		this.descripcion = descripcion;
	}

	public CatTipoProyecto(Integer idTipoProyecto, String descripcion, Set<Proyecto> proyectos) {
		this.idTipoProyecto = idTipoProyecto;
		this.descripcion = descripcion;
		this.proyectos = proyectos;
	}

	@Id
	@Column(name = "id_tipo_proyecto", unique = true, nullable = false)
	public Integer getIdTipoProyecto() {
		return this.idTipoProyecto;
	}

	public void setIdTipoProyecto(Integer idTipoProyecto) {
		this.idTipoProyecto = idTipoProyecto;
	}

	@Column(name = "descripcion", nullable = false, length = 20)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catTipoProyecto")
	public Set<Proyecto> getProyectos() {
		return this.proyectos;
	}

	public void setProyectos(Set<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

}

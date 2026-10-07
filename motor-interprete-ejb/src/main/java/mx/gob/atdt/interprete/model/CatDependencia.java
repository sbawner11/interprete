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
@Table(name = "cat_dependencia", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatDependencia.findAll", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatDependenciaDTO(cd.idDependencia, cd.descripcion) "
					+ "FROM CatDependencia cd "
					+ "WHERE cd.activo = true "
					+ " ORDER BY cd.descripcion " )
})
public class CatDependencia implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4122750229356234306L;
	
	private Integer idDependencia;
	private String descripcion;
	private boolean activo;
	private Set<Proyecto> proyectos = new HashSet<Proyecto>(0);

	public CatDependencia() {
	}
	
	public CatDependencia(Integer idDependencia, String descripcion) {
		this.idDependencia = idDependencia;
		this.descripcion = descripcion;
	}

	public CatDependencia(Integer idDependencia, String descripcion, boolean activo) {
		this.idDependencia = idDependencia;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	public CatDependencia(Integer idDependencia, String descripcion, boolean activo, Set<Proyecto> proyectos) {
		this.idDependencia = idDependencia;
		this.descripcion = descripcion;
		this.activo = activo;
		this.proyectos = proyectos;
	}

	@Id
	@Column(name = "id_dependencia", unique = true, nullable = false)
	public Integer getIdDependencia() {
		return this.idDependencia;
	}

	public void setIdDependencia(Integer idDependencia) {
		this.idDependencia = idDependencia;
	}

	@Column(name = "descripcion", nullable = false, length = 150)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catDependencia")
	public Set<Proyecto> getProyectos() {
		return this.proyectos;
	}

	public void setProyectos(Set<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

}

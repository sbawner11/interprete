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
@Table(name = "cat_validadores", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatValidadores.findAll", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatValidadoresDTO(cv.idValidador, cv.nombreValidador) "
					+ "FROM CatValidadores cv "
					+ "WHERE cv.activo = true ")
})
public class CatValidadores implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2143150124346320640L;
	
	private Integer idValidador;
	private String nombreValidador;
	private boolean activo;
	private Set<ComponenteCampoTexto> componenteCampoTextos = new HashSet<ComponenteCampoTexto>(0);

	public CatValidadores() {
	}

	public CatValidadores(Integer idValidador, String nombreValidador, boolean activo) {
		this.idValidador = idValidador;
		this.nombreValidador = nombreValidador;
		this.activo = activo;
	}

	public CatValidadores(Integer idValidador, String nombreValidador, boolean activo,
			Set<ComponenteCampoTexto> componenteCampoTextos) {
		this.idValidador = idValidador;
		this.nombreValidador = nombreValidador;
		this.activo = activo;
		this.componenteCampoTextos = componenteCampoTextos;
	}
	
	public CatValidadores(Integer idValidador) {
		this.idValidador = idValidador;

	}	

	@Id
	@Column(name = "id_validador", unique = true, nullable = false)
	public Integer getIdValidador() {
		return this.idValidador;
	}


	public void setIdValidador(Integer idValidador) {
		this.idValidador = idValidador;
	}

	@Column(name = "nombre_validador", nullable = false, length = 100)
	public String getNombreValidador() {
		return this.nombreValidador;
	}

	public void setNombreValidador(String nombreValidador) {
		this.nombreValidador = nombreValidador;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catValidadores")
	public Set<ComponenteCampoTexto> getComponenteCampoTextos() {
		return this.componenteCampoTextos;
	}

	public void setComponenteCampoTextos(Set<ComponenteCampoTexto> componenteCampoTextos) {
		this.componenteCampoTextos = componenteCampoTextos;
	}

}

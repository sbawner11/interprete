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
@Table(name = "cat_estados", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "CatEstados.buscarPorIdEstado", 
			query =" SELECT DISTINCT NEW  mx.gob.atdt.interprete.commons.dto.CatEstadosDTO" +
				" (ce.idEstado, UPPER(ce.estado))" +
				" FROM CatEstados ce " +
				" WHERE ce.idEstado = :idEstado"),
	@NamedQuery(name = "CatEstados.buscarTodos", 
			query =" SELECT NEW  mx.gob.atdt.interprete.commons.dto.CatEstadosDTO" +
				" (ce.idEstado, UPPER(ce.estado))" +
				" FROM CatEstados ce " +
				" ORDER BY ce.idEstado ASC ")
})
public class CatEstados implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6761468227357769624L;
	
	private int idEstado;
	private String estado;
	private String cveEstado;
	private Set<CatCodigosPostales> catCodigosPostales = new HashSet<CatCodigosPostales>(0);
	private Set<CatMunicipios> catMunicipios = new HashSet<CatMunicipios>(0);
	private Set<CatAsentamientos> catAsentamientos = new HashSet<CatAsentamientos>(0);

	public CatEstados() {
	}

	public CatEstados(int idEstado, String estado, String cveEstado) {
		this.idEstado = idEstado;
		this.estado = estado;
		this.cveEstado = cveEstado;
	}

	public CatEstados(int idEstado, String estado, String cveEstado, Set<CatCodigosPostales> catCodigosPostales,
			Set<CatMunicipios> catMunicipios, Set<CatAsentamientos> catAsentamientos) {
		this.idEstado = idEstado;
		this.estado = estado;
		this.cveEstado = cveEstado;
		this.catCodigosPostales = catCodigosPostales;
		this.catMunicipios = catMunicipios;
		this.catAsentamientos = catAsentamientos;
	}

	@Id

	@Column(name = "id_estado", unique = true, nullable = false)
	public int getIdEstado() {
		return this.idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	@Column(name = "estado", nullable = false, length = 40)
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	@Column(name = "cve_estado", nullable = false, length = 2)
	public String getCveEstado() {
		return this.cveEstado;
	}

	public void setCveEstado(String cveEstado) {
		this.cveEstado = cveEstado;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catEstados")
	public Set<CatCodigosPostales> getCatCodigosPostales() {
		return this.catCodigosPostales;
	}

	public void setCatCodigosPostales(Set<CatCodigosPostales> catCodigosPostales) {
		this.catCodigosPostales = catCodigosPostales;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catEstados")
	public Set<CatMunicipios> getCatMunicipios() {
		return this.catMunicipios;
	}

	public void setCatMunicipios(Set<CatMunicipios> catMunicipios) {
		this.catMunicipios = catMunicipios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catEstados")
	public Set<CatAsentamientos> getCatAsentamientos() {
		return this.catAsentamientos;
	}

	public void setCatAsentamientos(Set<CatAsentamientos> catAsentamientos) {
		this.catAsentamientos = catAsentamientos;
	}

}


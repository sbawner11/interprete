package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cat_municipios", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "CatMunicipios.buscarPorIdMunicipio", 
			query ="SELECT DISTINCT NEW  mx.gob.atdt.interprete.commons.dto.CatMunicipiosDTO"
					+ "(cm.idMunicipio, UPPER(cm.municipio))"
				+ "	FROM CatMunicipios cm "
				+ "	WHERE cm.idMunicipio = :idMunicipio"),
	@NamedQuery(name = "CatMunicipios.buscarTodosByEstado", 
			query ="SELECT NEW  mx.gob.atdt.interprete.commons.dto.CatMunicipiosDTO"
					+ "(cm.idMunicipio, UPPER(cm.municipio))"
				+ "	FROM CatMunicipios cm "
				+ "	WHERE cm.catEstados.idEstado = :idEstado"
				+ "	ORDER BY cm.municipio ASC "),
	@NamedQuery(name = "CatMunicipios.buscarTodos", 
			query ="SELECT NEW  mx.gob.atdt.interprete.commons.dto.CatMunicipiosDTO"
					+ "(cm.idMunicipio, UPPER(cm.municipio))"
				+ "	FROM CatMunicipios cm "
				+ "	ORDER BY cm.municipio ASC ")
})
public class CatMunicipios implements java.io.Serializable {
	
	private static final long serialVersionUID = 9033626088395496081L;
	private int idMunicipio;
	private CatEstados catEstados;
	private String municipio;
	private int idMunicipioPorEstado;
	private Integer idMunicipioAnterior;
	private Set<CatAsentamientos> catAsentamientoses = new HashSet<CatAsentamientos>(0);
	private Set<CatCodigosPostales> catCodigosPostales = new HashSet<CatCodigosPostales>(0);

	public CatMunicipios() {
	}

	public CatMunicipios(int idMunicipio, CatEstados catEstados, String municipio, int idMunicipioPorEstado) {
		this.idMunicipio = idMunicipio;
		this.catEstados = catEstados;
		this.municipio = municipio;
		this.idMunicipioPorEstado = idMunicipioPorEstado;
	}

	public CatMunicipios(int idMunicipio, CatEstados catEstados, String municipio, int idMunicipioPorEstado,
			Integer idMunicipioAnterior, Set<CatAsentamientos> catAsentamientoses,
			Set<CatCodigosPostales> catCodigosPostaleses) {
		this.idMunicipio = idMunicipio;
		this.catEstados = catEstados;
		this.municipio = municipio;
		this.idMunicipioPorEstado = idMunicipioPorEstado;
		this.idMunicipioAnterior = idMunicipioAnterior;
		this.catAsentamientoses = catAsentamientoses;
		this.catCodigosPostales = catCodigosPostaleses;
	}

	@Id

	@Column(name = "id_municipio", unique = true, nullable = false)
	public int getIdMunicipio() {
		return this.idMunicipio;
	}

	public void setIdMunicipio(int idMunicipio) {
		this.idMunicipio = idMunicipio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado", nullable = false)
	public CatEstados getCatEstados() {
		return this.catEstados;
	}

	public void setCatEstados(CatEstados catEstados) {
		this.catEstados = catEstados;
	}

	@Column(name = "municipio", nullable = false, length = 60)
	public String getMunicipio() {
		return this.municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	@Column(name = "id_municipio_por_estado", nullable = false)
	public int getIdMunicipioPorEstado() {
		return this.idMunicipioPorEstado;
	}

	public void setIdMunicipioPorEstado(int idMunicipioPorEstado) {
		this.idMunicipioPorEstado = idMunicipioPorEstado;
	}

	@Column(name = "id_municipio_anterior")
	public Integer getIdMunicipioAnterior() {
		return this.idMunicipioAnterior;
	}

	public void setIdMunicipioAnterior(Integer idMunicipioAnterior) {
		this.idMunicipioAnterior = idMunicipioAnterior;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catMunicipios")
	public Set<CatAsentamientos> getCatAsentamientoses() {
		return this.catAsentamientoses;
	}

	public void setCatAsentamientoses(Set<CatAsentamientos> catAsentamientoses) {
		this.catAsentamientoses = catAsentamientoses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catMunicipios")
	public Set<CatCodigosPostales> getCatCodigosPostaleses() {
		return this.catCodigosPostales;
	}

	public void setCatCodigosPostaleses(Set<CatCodigosPostales> catCodigosPostaleses) {
		this.catCodigosPostales = catCodigosPostaleses;
	}

}


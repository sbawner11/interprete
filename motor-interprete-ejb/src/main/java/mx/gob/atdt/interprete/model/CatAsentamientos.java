package mx.gob.atdt.interprete.model;

import java.io.Serializable;

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
@Table(name = "cat_asentamientos", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "CatAsentamientos.buscarPorIdMunicipio", 
			query ="SELECT DISTINCT NEW  mx.gob.atdt.interprete.commons.dto.CatAsentamientosDTO"
					+ "(ca.idAsentamiento , UPPER(ca.asentamiento))"
				+ "	FROM CatAsentamientos ca "
				+ "	WHERE ca.idAsentamiento = :idAsentamiento")
})
public class CatAsentamientos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5151642820370482585L;
	
	private int idAsentamiento;
	private CatEstados catEstados;
	private CatMunicipios catMunicipios;
	private String asentamiento;
	private Integer idAsentamientoAnterior;
	private Integer idMunicipioPorEstado;
	private Integer idAsentamientoPorEdoMun;
	private Set<CatCodigosPostales> catCodigosPostaleses = new HashSet<CatCodigosPostales>(0);

	public CatAsentamientos() {
	}

	public CatAsentamientos(int idAsentamiento, CatEstados catEstados, String asentamiento) {
		this.idAsentamiento = idAsentamiento;
		this.catEstados = catEstados;
		this.asentamiento = asentamiento;
	}

	public CatAsentamientos(int idAsentamiento, CatEstados catEstados, CatMunicipios catMunicipios, String asentamiento,
			Integer idAsentamientoAnterior, Integer idMunicipioPorEstado, Integer idAsentamientoPorEdoMun,
			Set<CatCodigosPostales> catCodigosPostaleses) {
		this.idAsentamiento = idAsentamiento;
		this.catEstados = catEstados;
		this.catMunicipios = catMunicipios;
		this.asentamiento = asentamiento;
		this.idAsentamientoAnterior = idAsentamientoAnterior;
		this.idMunicipioPorEstado = idMunicipioPorEstado;
		this.idAsentamientoPorEdoMun = idAsentamientoPorEdoMun;
		this.catCodigosPostaleses = catCodigosPostaleses;
	}

	@Id

	@Column(name = "id_asentamiento", unique = true, nullable = false)
	public int getIdAsentamiento() {
		return this.idAsentamiento;
	}

	public void setIdAsentamiento(int idAsentamiento) {
		this.idAsentamiento = idAsentamiento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado", nullable = false)
	public CatEstados getCatEstados() {
		return this.catEstados;
	}

	public void setCatEstados(CatEstados catEstados) {
		this.catEstados = catEstados;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_municipio")
	public CatMunicipios getCatMunicipios() {
		return this.catMunicipios;
	}

	public void setCatMunicipios(CatMunicipios catMunicipios) {
		this.catMunicipios = catMunicipios;
	}

	@Column(name = "asentamiento", nullable = false, length = 60)
	public String getAsentamiento() {
		return this.asentamiento;
	}

	public void setAsentamiento(String asentamiento) {
		this.asentamiento = asentamiento;
	}

	@Column(name = "id_asentamiento_anterior")
	public Integer getIdAsentamientoAnterior() {
		return this.idAsentamientoAnterior;
	}

	public void setIdAsentamientoAnterior(Integer idAsentamientoAnterior) {
		this.idAsentamientoAnterior = idAsentamientoAnterior;
	}

	@Column(name = "id_municipio_por_estado")
	public Integer getIdMunicipioPorEstado() {
		return this.idMunicipioPorEstado;
	}

	public void setIdMunicipioPorEstado(Integer idMunicipioPorEstado) {
		this.idMunicipioPorEstado = idMunicipioPorEstado;
	}

	@Column(name = "id_asentamiento_por_edo_mun")
	public Integer getIdAsentamientoPorEdoMun() {
		return this.idAsentamientoPorEdoMun;
	}

	public void setIdAsentamientoPorEdoMun(Integer idAsentamientoPorEdoMun) {
		this.idAsentamientoPorEdoMun = idAsentamientoPorEdoMun;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catAsentamientos")
	public Set<CatCodigosPostales> getCatCodigosPostaleses() {
		return this.catCodigosPostaleses;
	}

	public void setCatCodigosPostaleses(Set<CatCodigosPostales> catCodigosPostaleses) {
		this.catCodigosPostaleses = catCodigosPostaleses;
	}

}


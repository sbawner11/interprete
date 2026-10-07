package mx.gob.atdt.interprete.commons.dto;

import java.io.Serializable;

public class CatAsentamientosDTO implements Serializable{

	private static final long serialVersionUID = -1404037432876166635L;
	private int idAsentamiento;
	private CatEstadosDTO catEstadosDTO;
	private CatMunicipiosDTO catMunicipiosDTO;
	private String descripcion;
	
	public CatAsentamientosDTO() {}
	
	//Constructor para namedquery = findPorAlcaldia
	public CatAsentamientosDTO(int idAsentamiento , String descripcion) {
		this.idAsentamiento = idAsentamiento;
		this.descripcion = descripcion;
	}
	
	//Constructor para namedquery = findALL
	public CatAsentamientosDTO(int idAsentamiento , String descripcion, int idMunicipio , int idEstado) {
		this.idAsentamiento = idAsentamiento;
		this.catEstadosDTO = new CatEstadosDTO(idEstado, null, null);
		this.catMunicipiosDTO = new CatMunicipiosDTO(idMunicipio, null, null, idEstado);
		this.descripcion = descripcion;
	}

	/**
	 * @return the idAsentamiento
	 */
	public int getIdAsentamiento() {
		return idAsentamiento;
	}

	/**
	 * @param idAsentamiento the idAsentamiento to set
	 */
	public void setIdAsentamiento(int idAsentamiento) {
		this.idAsentamiento = idAsentamiento;
	}

	/**
	 * @return the catEstadosDTO
	 */
	public CatEstadosDTO getCatEstadosDTO() {
		return catEstadosDTO;
	}

	/**
	 * @param catEstadosDTO the catEstadosDTO to set
	 */
	public void setCatEstadosDTO(CatEstadosDTO catEstadosDTO) {
		this.catEstadosDTO = catEstadosDTO;
	}

	/**
	 * @return the catMunicipiosDTO
	 */
	public CatMunicipiosDTO getCatMunicipiosDTO() {
		return catMunicipiosDTO;
	}

	/**
	 * @param catMunicipiosDTO the catMunicipiosDTO to set
	 */
	public void setCatMunicipiosDTO(CatMunicipiosDTO catMunicipiosDTO) {
		this.catMunicipiosDTO = catMunicipiosDTO;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}

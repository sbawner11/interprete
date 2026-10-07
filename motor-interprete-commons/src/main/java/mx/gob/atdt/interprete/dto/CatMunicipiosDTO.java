package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatMunicipiosDTO implements Serializable{

	private static final long serialVersionUID = -2515632900281761299L;
	private int idMunicipio;
	private CatEstadosDTO catEstadosDTO;
	private String descripcion;
	private String cveAlcaldia;
	
	public CatMunicipiosDTO() {
		this.catEstadosDTO =  new CatEstadosDTO();
	}
	
	public CatMunicipiosDTO(String cveMunicipio) {
		this.cveAlcaldia = cveMunicipio;
	}
	
	/**
	 * @param idMunicipio
	 * @param descripcion
	 */
	public CatMunicipiosDTO(int idMunicipio, String descripcion) {	
		this.idMunicipio = idMunicipio;
		this.descripcion = descripcion;
	}

	//Constructor para namedquery = findALL
	public CatMunicipiosDTO(int idMunicipio, String descripcion, String cveAlcaldia, int idEstado) {
		this.idMunicipio = idMunicipio;
		this.catEstadosDTO = new CatEstadosDTO(idEstado, null, null);
		this.descripcion = descripcion;
		this.cveAlcaldia = cveAlcaldia;
	}

	/**
	 * @return the idMunicipio
	 */
	public int getIdMunicipio() {
		return idMunicipio;
	}

	/**
	 * @param idMunicipio the idMunicipio to set
	 */
	public void setIdMunicipio(int idMunicipio) {
		this.idMunicipio = idMunicipio;
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

	/**
	 * @return the cveAlcaldia
	 */
	public String getCveAlcaldia() {
		return cveAlcaldia;
	}

	/**
	 * @param cveAlcaldia the cveAlcaldia to set
	 */
	public void setCveAlcaldia(String cveAlcaldia) {
		this.cveAlcaldia = cveAlcaldia;
	}
	
	
}

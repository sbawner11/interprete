package mx.gob.atdt.interprete.commons.dto;

import java.io.Serializable;

public class CatCodigosPostalesDTO implements Serializable{

	private static final long serialVersionUID = 6949432993664273616L;
	private int idCodigoPostal;
	private CatAsentamientosDTO catAsentamientosDTO;
	private CatEstadosDTO catEstadosDTO;
	private CatMunicipiosDTO catMunicipiosDTO;
	private CatTiposAsentamientoDTO catTiposAsentamientoDTO;
	private String codigoPostal;
	
	public CatCodigosPostalesDTO() {}
	
	public CatCodigosPostalesDTO(int idCodigoPostal, String codigoPostal) {
		this.idCodigoPostal = idCodigoPostal;
		this.codigoPostal = codigoPostal;
	}

	public CatCodigosPostalesDTO(int idCodigoPostal, String codigoPostal, CatAsentamientosDTO catAsentamientosDTO,
			CatMunicipiosDTO catMunicipiosDTO, CatEstadosDTO catEstadosDTO,
			CatTiposAsentamientoDTO catTiposAsentamientoDTO) {
		this.idCodigoPostal = idCodigoPostal;
		this.codigoPostal = codigoPostal;
		this.catAsentamientosDTO = catAsentamientosDTO;
		this.catMunicipiosDTO = catMunicipiosDTO;
		this.catEstadosDTO = catEstadosDTO;
		this.catTiposAsentamientoDTO = catTiposAsentamientoDTO;
	}
	
	/**
	 * Constructor utilizado por la NamedQuery CodigosPostales.buscarCodigoPostal
	 * @param idCodigoPostal
	 * @param codigoPostal
	 * @param idAsentamiento
	 * @param idMunicipio
	 * @param idEstado
	 */
	public CatCodigosPostalesDTO(int idCodigoPostal, String codigoPostal, 
			int idAsentamiento,  int idMunicipio, int idEstado) {
		this.idCodigoPostal = idCodigoPostal;
		this.codigoPostal = codigoPostal;
		this.catAsentamientosDTO = new CatAsentamientosDTO(idAsentamiento, null);
		this.catMunicipiosDTO = new CatMunicipiosDTO(idMunicipio, null);
		this.catEstadosDTO = new CatEstadosDTO(idEstado, null);
	}

	/**
	 * @return the idCodigoPostal
	 */
	public int getIdCodigoPostal() {
		return idCodigoPostal;
	}

	/**
	 * @param idCodigoPostal the idCodigoPostal to set
	 */
	public void setIdCodigoPostal(int idCodigoPostal) {
		this.idCodigoPostal = idCodigoPostal;
	}

	/**
	 * @return the catAsentamientosDTO
	 */
	public CatAsentamientosDTO getCatAsentamientosDTO() {
		return catAsentamientosDTO;
	}

	/**
	 * @param catAsentamientosDTO the catAsentamientosDTO to set
	 */
	public void setCatAsentamientosDTO(CatAsentamientosDTO catAsentamientosDTO) {
		this.catAsentamientosDTO = catAsentamientosDTO;
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
	 * @return the catTiposAsentamientoDTO
	 */
	public CatTiposAsentamientoDTO getCatTiposAsentamientoDTO() {
		return catTiposAsentamientoDTO;
	}

	/**
	 * @param catTiposAsentamientoDTO the catTiposAsentamientoDTO to set
	 */
	public void setCatTiposAsentamientoDTO(CatTiposAsentamientoDTO catTiposAsentamientoDTO) {
		this.catTiposAsentamientoDTO = catTiposAsentamientoDTO;
	}

	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	
}

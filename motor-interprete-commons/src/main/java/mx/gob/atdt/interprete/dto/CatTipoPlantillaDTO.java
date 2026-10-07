package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoPlantillaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8489760221807964481L;
	
	private Integer idTipoPlantilla;
	private String descripcion;
	private boolean activo;
			
	/**
	 * 
	 */
	public CatTipoPlantillaDTO() {
	}	

	/**
	 * @param idTipoPlantilla
	 */
	public CatTipoPlantillaDTO(Integer idTipoPlantilla) {
		this.idTipoPlantilla = idTipoPlantilla;
	}

	/**
	 * @param idTipoPlantilla
	 * @param descripcion
	 * @param activo
	 */
	public CatTipoPlantillaDTO(Integer idTipoPlantilla, String descripcion, boolean activo) {
		this.idTipoPlantilla = idTipoPlantilla;
		this.descripcion = descripcion;
		this.activo = activo;
	}
	
	/**
	 * @return the idTipoPlantilla
	 */
	public Integer getIdTipoPlantilla() {
		return idTipoPlantilla;
	}
	/**
	 * @param idTipoPlantilla the idTipoPlantilla to set
	 */
	public void setIdTipoPlantilla(Integer idTipoPlantilla) {
		this.idTipoPlantilla = idTipoPlantilla;
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
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}

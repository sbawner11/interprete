package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoCostoDTO implements Serializable {
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 7280184925515440674L;
	
	private Integer idTipoCosto;
	private String descripcion;
	private boolean activo;
	
	/**
	 * 
	 */
	public CatTipoCostoDTO() {
	}
	
	/**
	 * @param idTipoCosto
	 */
	public CatTipoCostoDTO(Integer idTipoCosto) {
		this.idTipoCosto = idTipoCosto;
	}
	
	/**
	 * @param idTipoCosto
	 * @param descripcion
	 */
	public CatTipoCostoDTO(Integer idTipoCosto, String descripcion) {
		this.idTipoCosto = idTipoCosto;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idTipoCosto
	 */
	public Integer getIdTipoCosto() {
		return idTipoCosto;
	}

	/**
	 * @param idTipoCosto the idTipoCosto to set
	 */
	public void setIdTipoCosto(Integer idTipoCosto) {
		this.idTipoCosto = idTipoCosto;
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

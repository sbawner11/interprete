package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatOrigenTokenDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5184202528764714914L;

	private Integer idOrigenToken;
	private String descripcion;
	private boolean activo;

	/**
	 * 
	 */
	public CatOrigenTokenDTO() {
	}	

	/**
	 * @param idOrigenToken
	 */
	public CatOrigenTokenDTO(Integer idOrigenToken) {
		this.idOrigenToken = idOrigenToken;
	}

	/**
	 * @param idOrigenToken
	 * @param descripcion
	 */
	public CatOrigenTokenDTO(Integer idOrigenToken, String descripcion, boolean activo) {
		this.idOrigenToken = idOrigenToken;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	/**
	 * @return the idOrigenToken
	 */
	public Integer getIdOrigenToken() {
		return idOrigenToken;
	}

	/**
	 * @param idOrigenToken the idOrigenToken to set
	 */
	public void setIdOrigenToken(Integer idOrigenToken) {
		this.idOrigenToken = idOrigenToken;
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

	@Override
	public String toString() {
		return "CatDependenciaDTO [idOrigenToken=" + idOrigenToken + ", descripcion=" + descripcion + "]";
	}

}

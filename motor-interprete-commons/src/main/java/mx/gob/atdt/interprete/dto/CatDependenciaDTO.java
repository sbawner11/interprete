package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatDependenciaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5184202528764714914L;

	private Integer idDependencia;
	private String descripcion;
	private boolean activo;

	/**
	 * 
	 */
	public CatDependenciaDTO() {
	}

	/**
	 * @param idDependencia
	 * @param descripcion
	 */
	public CatDependenciaDTO(Integer idDependencia, String descripcion) {
		this.idDependencia = idDependencia;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idDependencia
	 */
	public Integer getIdDependencia() {
		return idDependencia;
	}

	/**
	 * @param idDependencia the idDependencia to set
	 */
	public void setIdDependencia(Integer idDependencia) {
		this.idDependencia = idDependencia;
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
		return "CatDependenciaDTO [idDependencia=" + idDependencia + ", descripcion=" + descripcion + "]";
	}

}

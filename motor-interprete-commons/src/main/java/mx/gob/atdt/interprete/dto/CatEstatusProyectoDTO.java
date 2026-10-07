package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatEstatusProyectoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4329719251581753700L;

	private Integer idEstatusProyecto;
	private String descripcion;
	private boolean activo;

	/**
	 * 
	 */
	public CatEstatusProyectoDTO() {
	}

	/**
	 * @param idEstatusProyecto
	 */
	public CatEstatusProyectoDTO(Integer idEstatusProyecto) {
		this.idEstatusProyecto = idEstatusProyecto;
	}

	/**
	 * @param idEstatusProyecto
	 * @param descripcion
	 */
	public CatEstatusProyectoDTO(Integer idEstatusProyecto, String descripcion) {
		this.idEstatusProyecto = idEstatusProyecto;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idEstatusProyecto
	 */
	public Integer getIdEstatusProyecto() {
		return idEstatusProyecto;
	}

	/**
	 * @param idEstatusProyecto the idEstatusProyecto to set
	 */
	public void setIdEstatusProyecto(Integer idEstatusProyecto) {
		this.idEstatusProyecto = idEstatusProyecto;
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
		return "CatEstatusProyectoDTO [idEstatusProyecto=" + idEstatusProyecto + ", descripcion=" + descripcion
				+ ", activo=" + activo + "]";
	}

}

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoProyectoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5781766929957068038L;

	private Integer idTipoProyecto;
	private String descripcion;

	/**
	 * 
	 */
	public CatTipoProyectoDTO() {
	}

	/**
	 * @param idTipoProyecto
	 */
	public CatTipoProyectoDTO(Integer idTipoProyecto) {
		this.idTipoProyecto = idTipoProyecto;
	}

	/**
	 * @param idTipoProyecto
	 * @param descripcion
	 */
	public CatTipoProyectoDTO(Integer idTipoProyecto, String descripcion) {
		this.idTipoProyecto = idTipoProyecto;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idTipoProyecto
	 */
	public Integer getIdTipoProyecto() {
		return idTipoProyecto;
	}

	/**
	 * @param idTipoProyecto the idTipoProyecto to set
	 */
	public void setIdTipoProyecto(Integer idTipoProyecto) {
		this.idTipoProyecto = idTipoProyecto;
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

	@Override
	public String toString() {
		return "CatTipoProyectoDTO [idTipoProyecto=" + idTipoProyecto + ", descripcion=" + descripcion + "]";
	}

}

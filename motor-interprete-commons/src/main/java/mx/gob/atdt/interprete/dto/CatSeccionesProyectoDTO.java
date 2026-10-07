package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatSeccionesProyectoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2281343331536682609L;
	
	private Integer idSeccionProyecto;
	private String descripcion;
	
	/**
	 * 
	 */
	public CatSeccionesProyectoDTO() {
	}	

	/**
	 * @param idSeccionProyecto
	 */
	public CatSeccionesProyectoDTO(Integer idSeccionProyecto) {
		this.idSeccionProyecto = idSeccionProyecto;
	}

	/**
	 * @param idSeccionProyecto
	 * @param descripcion
	 */
	public CatSeccionesProyectoDTO(Integer idSeccionProyecto, String descripcion) {
		this.idSeccionProyecto = idSeccionProyecto;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idSeccionProyecto
	 */
	public Integer getIdSeccionProyecto() {
		return idSeccionProyecto;
	}

	/**
	 * @param idSeccionProyecto the idSeccionProyecto to set
	 */
	public void setIdSeccionProyecto(Integer idSeccionProyecto) {
		this.idSeccionProyecto = idSeccionProyecto;
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

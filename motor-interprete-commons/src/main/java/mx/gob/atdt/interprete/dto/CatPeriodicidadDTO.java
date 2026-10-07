package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatPeriodicidadDTO implements Serializable {
	
	private static final long serialVersionUID = 6474754593432191619L;
	
	private int idPeriodicidad;
	private String descripcion;
	private String clave;
	
	/**
	 * 
	 */
	public CatPeriodicidadDTO() {
	}

	/**
	 * @param idPeriodicidad
	 */
	public CatPeriodicidadDTO(int idPeriodicidad) {
		this.idPeriodicidad = idPeriodicidad;
	}

	/**
	 * Constructor utilizado por la NamedQuery CatPeriodicidad.findAll, CatPeriodicidad.findById y CatPeriodicidad.findByClave 
	 * @param idPeriodicidad
	 * @param descripcion
	 * @param clave
	 */
	public CatPeriodicidadDTO(int idPeriodicidad, String descripcion, String clave) {
		this.idPeriodicidad = idPeriodicidad;
		this.descripcion = descripcion;
		this.clave = clave;
	}

	/**
	 * @return the idPeriodicidad
	 */
	public int getIdPeriodicidad() {
		return idPeriodicidad;
	}

	/**
	 * @param idPeriodicidad the idPeriodicidad to set
	 */
	public void setIdPeriodicidad(int idPeriodicidad) {
		this.idPeriodicidad = idPeriodicidad;
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
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
	}
	
}

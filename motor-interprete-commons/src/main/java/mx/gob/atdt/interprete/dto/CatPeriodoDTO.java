package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatPeriodoDTO implements Serializable {
	
	private static final long serialVersionUID = 9055341925153110196L;
	
	private int idPeriodo;
	private CatPeriodicidadDTO catPeriodicidadDTO;
	private String descripcion;
	private String clave;
	
	/**
	 * 
	 */
	public CatPeriodoDTO() {
	}

	/**
	 * @param idPeriodo
	 */
	public CatPeriodoDTO(Integer idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	/**
	 * Constructor utilizado por las NamedQuery CatPeriodo.findAll, CatPeriodo.findById y CatPeriodo.findByIdPeriodicidad
	 * @param idPeriodo
	 * @param idPeriodicidad
	 * @param descripcion
	 * @param clave
	 */
	public CatPeriodoDTO(int idPeriodo, int idPeriodicidad, String descripcion, String clave) {
		this.idPeriodo = idPeriodo;
		this.catPeriodicidadDTO = new CatPeriodicidadDTO(idPeriodicidad);
		this.descripcion = descripcion;
		this.clave = clave;
	}
	
	public CatPeriodoDTO(int idPeriodo, String descripcion, String clave) {
		this.idPeriodo = idPeriodo;
		this.descripcion = descripcion;
		this.clave = clave;
	}

	/**
	 * @param idPeriodo
	 * @param catPeriodicidadDTO
	 * @param descripcion
	 * @param clave
	 */
	public CatPeriodoDTO(Integer idPeriodo, CatPeriodicidadDTO catPeriodicidadDTO, String descripcion, String clave) {
		this.idPeriodo = idPeriodo;
		this.catPeriodicidadDTO = catPeriodicidadDTO;
		this.descripcion = descripcion;
		this.clave = clave;
	}

	/**
	 * @return the idPeriodo
	 */
	public int getIdPeriodo() {
		return idPeriodo;
	}

	/**
	 * @param idPeriodo the idPeriodo to set
	 */
	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	/**
	 * @return the catPeriodicidadDTO
	 */
	public CatPeriodicidadDTO getCatPeriodicidadDTO() {
		return catPeriodicidadDTO;
	}

	/**
	 * @param catPeriodicidadDTO the catPeriodicidadDTO to set
	 */
	public void setCatPeriodicidadDTO(CatPeriodicidadDTO catPeriodicidadDTO) {
		this.catPeriodicidadDTO = catPeriodicidadDTO;
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

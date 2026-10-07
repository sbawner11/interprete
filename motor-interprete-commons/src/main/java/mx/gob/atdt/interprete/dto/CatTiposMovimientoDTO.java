package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTiposMovimientoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5003916875283258416L;
	
	private int idTipoMovimiento;
	private String descripcion;
		
	/**
	 * 
	 */
	public CatTiposMovimientoDTO() {
	}
	
	/**
	 * @param idTipoMovimiento
	 */
	public CatTiposMovimientoDTO(int idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
	}

	/**
	 * @param idTipoMovimiento
	 * @param descripcion
	 */
	public CatTiposMovimientoDTO(int idTipoMovimiento, String descripcion) {
		this.idTipoMovimiento = idTipoMovimiento;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idTipoMovimiento
	 */
	public int getIdTipoMovimiento() {
		return idTipoMovimiento;
	}

	/**
	 * @param idTipoMovimiento the idTipoMovimiento to set
	 */
	public void setIdTipoMovimiento(int idTipoMovimiento) {
		this.idTipoMovimiento = idTipoMovimiento;
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

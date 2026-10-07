package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoComponenteDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2901759544620486715L;
	
	private Integer idTipoComponente;
	private String descripcion;
	private boolean avanzado;
	private boolean activo;
	private int orden;
	
	/**
	 * 
	 */
	public CatTipoComponenteDTO() {
	}	
	
	/**
	 * @param idTipoComponente
	 * @param descripcion
	 */
	public CatTipoComponenteDTO(Integer idTipoComponente, String descripcion) {
		this.idTipoComponente = idTipoComponente;
		this.descripcion = descripcion;
	}

	public CatTipoComponenteDTO(Integer idTipoComponente) {
		this.idTipoComponente = idTipoComponente;

	}
	/** 
	 * @param idTipoComponente
	 * @param descripcion
	 * @param avanzado
	 */
	public CatTipoComponenteDTO(Integer idTipoComponente, String descripcion, boolean avanzado) {
		this.idTipoComponente = idTipoComponente;
		this.descripcion = descripcion;
		this.avanzado = avanzado;
	}

	/**
	 * Constructor utilizado por la NamedQuery CatTipoComponente.findAll
	 * 
	 * @param idTipoComponente
	 * @param descripcion
	 * @param avanzado
	 * @param activo
	 */
	public CatTipoComponenteDTO(Integer idTipoComponente, String descripcion, boolean avanzado, boolean activo) {
		this.idTipoComponente = idTipoComponente;
		this.descripcion = descripcion;
		this.avanzado = avanzado;
		this.activo = activo;
	}

	/**
	 * @return the idTipoComponente
	 */
	public Integer getIdTipoComponente() {
		return idTipoComponente;
	}

	/**
	 * @param idTipoComponente the idTipoComponente to set
	 */
	public void setIdTipoComponente(Integer idTipoComponente) {
		this.idTipoComponente = idTipoComponente;
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
	 * @return the avanzado
	 */
	public boolean isAvanzado() {
		return avanzado;
	}

	/**
	 * @param avanzado the avanzado to set
	 */
	public void setAvanzado(boolean avanzado) {
		this.avanzado = avanzado;
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
	
	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}
	
}

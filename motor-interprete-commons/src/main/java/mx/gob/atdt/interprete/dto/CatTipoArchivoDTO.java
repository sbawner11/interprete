package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoArchivoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6859028886940900493L;
	
	private Integer idTipoArchivo;
	private String descripcion;
	private boolean activo;
	
	/**
	 * 
	 */
	public CatTipoArchivoDTO() {
	}

	/**
	 * @param idTipoArchivo
	 */
	public CatTipoArchivoDTO(Integer idTipoArchivo) {
		this.idTipoArchivo = idTipoArchivo;
	}

	/**
	 * @param idTipoArchivo
	 * @param descripcion
	 */
	public CatTipoArchivoDTO(Integer idTipoArchivo, String descripcion) {
		this.idTipoArchivo = idTipoArchivo;
		this.descripcion = descripcion;
	}
	
	/**
	 * @param idTipoArchivo
	 * @param descripcion
	 * @param activo
	 */
	public CatTipoArchivoDTO(Integer idTipoArchivo, String descripcion, boolean activo) {
		this.idTipoArchivo = idTipoArchivo;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	/**
	 * @return the idTipoArchivo
	 */
	public Integer getIdTipoArchivo() {
		return idTipoArchivo;
	}

	/**
	 * @param idTipoArchivo the idTipoArchivo to set
	 */
	public void setIdTipoArchivo(Integer idTipoArchivo) {
		this.idTipoArchivo = idTipoArchivo;
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

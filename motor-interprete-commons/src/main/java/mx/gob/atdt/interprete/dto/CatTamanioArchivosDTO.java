package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTamanioArchivosDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3862597542352513630L;

	private Integer idTamanioArchivo;
	private String descripcion;
	private boolean activo;
	private int orden;
	
	/**
	 * 
	 */
	public CatTamanioArchivosDTO() {
	}
	
	/**
	 * @param idTamanioArchivo
	 */
	public CatTamanioArchivosDTO(Integer idTamanioArchivo) {
		this.idTamanioArchivo = idTamanioArchivo;
	}
	
	/**
	 * @param idTamanioArchivo
	 * @param descripcion
	 */
	public CatTamanioArchivosDTO(Integer idTamanioArchivo, String descripcion) {
		this.idTamanioArchivo = idTamanioArchivo;
		this.descripcion = descripcion;
	}

	/**
	 * @param idTamanioArchivo
	 * @param descripcion
	 * @param activo
	 */
	public CatTamanioArchivosDTO(Integer idTamanioArchivo, String descripcion, boolean activo) {
		this.idTamanioArchivo = idTamanioArchivo;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	/**
	 * @return the idTamanioArchivo
	 */
	public Integer getIdTamanioArchivo() {
		return idTamanioArchivo;
	}

	/**
	 * @param idTamanioArchivo the idTamanioArchivo to set
	 */
	public void setIdTamanioArchivo(Integer idTamanioArchivo) {
		this.idTamanioArchivo = idTamanioArchivo;
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

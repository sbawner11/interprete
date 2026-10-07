package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatEstadosSistemaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6211410446156332007L;
	
	private int idEstadoSistema;
	private String descripcion;
	
	/**
	 * 
	 */
	public CatEstadosSistemaDTO() {
	}
	
	/**
	 * @param idEstadoSistema
	 */
	public CatEstadosSistemaDTO(int idEstadoSistema) {
		this.idEstadoSistema = idEstadoSistema;
	}

	/**
	 * @param idEstadoSistema
	 * @param descripcion
	 */
	public CatEstadosSistemaDTO(int idEstadoSistema, String descripcion) {
		this.idEstadoSistema = idEstadoSistema;
		this.descripcion = descripcion;
	}

	public int getIdEstadoSistema() {
		return idEstadoSistema;
	}

	public void setIdEstadoSistema(int idEstadoSistema) {
		this.idEstadoSistema = idEstadoSistema;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
		
}

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoNotificacionDTO implements Serializable {
	
	private static final long serialVersionUID = -499398087197287963L;
	
	private int idTipoNotificacion;
	private String descripcion;
	
	/**
	 * 
	 */
	public CatTipoNotificacionDTO() {
	}
	
	/**
	 * @param idTipoNotificacion
	 * @param descripcion
	 */
	public CatTipoNotificacionDTO(int idTipoNotificacion) {
		this.idTipoNotificacion = idTipoNotificacion;
	}

	/**
	 * @param idTipoNotificacion
	 * @param descripcion
	 */
	public CatTipoNotificacionDTO(int idTipoNotificacion, String descripcion) {
		this.idTipoNotificacion = idTipoNotificacion;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idTipoNotificacion
	 */
	public int getIdTipoNotificacion() {
		return idTipoNotificacion;
	}

	/**
	 * @param idTipoNotificacion the idTipoNotificacion to set
	 */
	public void setIdTipoNotificacion(int idTipoNotificacion) {
		this.idTipoNotificacion = idTipoNotificacion;
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

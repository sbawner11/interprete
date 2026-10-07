package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatEstatusSolicitudDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -20586539771571895L;
	
	private int idEstatusSolicitud;
	private String descripcion;
	
	public CatEstatusSolicitudDTO() {
		
	}
	
	public CatEstatusSolicitudDTO(int idEstatusSolicitud) {
		this.idEstatusSolicitud = idEstatusSolicitud;
	}
	
	
	public CatEstatusSolicitudDTO(int idEstatusSolicitud, String descripcion) {
		super();
		this.idEstatusSolicitud = idEstatusSolicitud;
		this.descripcion = descripcion;
	}
	
	public int getIdEstatusSolicitud() {
		return idEstatusSolicitud;
	}
	public void setIdEstatusSolicitud(int idEstatusSolicitud) {
		this.idEstatusSolicitud = idEstatusSolicitud;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}

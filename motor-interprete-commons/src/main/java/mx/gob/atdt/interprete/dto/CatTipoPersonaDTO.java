package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoPersonaDTO implements Serializable {
	
	private static final long serialVersionUID = 7367597655296831413L;
	
	private int idTipoPersona;
	private String clave;
	private String descripcion;
	private boolean activo;
	
	/**
	 * 
	 */
	public CatTipoPersonaDTO() {
	}
	
	/**
	 * @param idTipoPersona
	 */
	public CatTipoPersonaDTO(int idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
	}

	public CatTipoPersonaDTO(int idTipoPersona, String clave, String descripcion) {
		super();
		this.idTipoPersona = idTipoPersona;
		this.clave = clave;
		this.descripcion = descripcion;
	}

	/**
	 * Constructor utilizado por la NamedQuery CatTipoPersona.findAll, CatTipoPersona.findById y CatTipoPersona.findActivos
	 * @param idTipoPersona
	 * @param clave
	 * @param descripcion
	 * @param activo
	 */
	public CatTipoPersonaDTO(int idTipoPersona, String clave, String descripcion, boolean activo) {
		this.idTipoPersona = idTipoPersona;
		this.clave = clave;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	/**
	 * @return the idTipoPersona
	 */
	public int getIdTipoPersona() {
		return idTipoPersona;
	}

	/**
	 * @param idTipoPersona the idTipoPersona to set
	 */
	public void setIdTipoPersona(int idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
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

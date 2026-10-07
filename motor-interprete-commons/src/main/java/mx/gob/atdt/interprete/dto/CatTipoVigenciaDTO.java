package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoVigenciaDTO implements Serializable {

	private static final long serialVersionUID = -362300742519958438L;
	
	private int idTipoVigencia;
	private String clave;
	private String descripcion;
	
	/**
	 * 
	 */
	public CatTipoVigenciaDTO() {
	}

	/**
	 * @param idTipoVigencia
	 */
	public CatTipoVigenciaDTO(int idTipoVigencia) {
		this.idTipoVigencia = idTipoVigencia;
	}

	/**
	 * Constructor utilizado por las NamedQuery CatTipoVigencia.findAll y CatTipoVigencia.findById
	 * @param idTipoVigencia
	 * @param clave
	 * @param descripcion
	 */
	public CatTipoVigenciaDTO(int idTipoVigencia, String clave, String descripcion) {
		this.idTipoVigencia = idTipoVigencia;
		this.clave = clave;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idTipoVigencia
	 */
	public int getIdTipoVigencia() {
		return idTipoVigencia;
	}

	/**
	 * @param idTipoVigencia the idTipoVigencia to set
	 */
	public void setIdTipoVigencia(int idTipoVigencia) {
		this.idTipoVigencia = idTipoVigencia;
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
	
}

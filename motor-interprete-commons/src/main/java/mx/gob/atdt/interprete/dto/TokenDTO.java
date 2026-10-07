package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class TokenDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4816489596017572270L;

	private Long idToken;
	private String descripcion;
	private int orden;

	/**
	 * 
	 */
	public TokenDTO() {
	}

	/**
	 * @param idToken
	 * @param descripcion
	 */
	public TokenDTO(int orden, String descripcion) {
		this.orden = orden;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idToken
	 */
	public Long getIdToken() {
		return idToken;
	}

	/**
	 * @param idToken the idToken to set
	 */
	public void setIdToken(Long idToken) {
		this.idToken = idToken;
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
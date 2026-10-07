package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatEstadosDTO implements Serializable{

	private static final long serialVersionUID = 7266135951031150234L;
	private int idEstado;
	private String estado;	
	private String claveEstado;
	
	public CatEstadosDTO() {
		
	}
	
	/**
	 * Constructor utilizado por la NamedQuery CatEstados.buscarTodos
	 * 
	 * @param idEstado
	 * @param descripcion
	 */
	public CatEstadosDTO(int idEstado, String estado) {
		this.idEstado = idEstado;
		this.estado = estado;
	}



	public CatEstadosDTO(int idEstado, String estado, String claveEstado) {
		this.idEstado = idEstado;
		this.estado = estado;
		this.claveEstado = claveEstado;
	}

	/**
	 * @return the idEstado
	 */
	public Integer getIdEstado() {
		return idEstado;
	}

	/**
	 * @param idEstado the idEstado to set
	 */
	public void setIdEstado(Integer idEstado) {
		this.idEstado = idEstado;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the claveEstado
	 */
	public String getClaveEstado() {
		return claveEstado;
	}

	/**
	 * @param claveEstado the claveEstado to set
	 */
	public void setClaveEstado(String claveEstado) {
		this.claveEstado = claveEstado;
	}
	
}

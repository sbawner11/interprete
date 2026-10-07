package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class ParametrosSistemaDTO implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7302853529612766017L;
	
	private Integer idParametro;
	private Integer valor;
	private String descripcion;
	private Integer activo;
		
	/**
	 * 
	 */
	public ParametrosSistemaDTO() {
	}
		
	/**
	 * @param idParametro
	 * @param valor
	 * @param descripcion
	 * @param activo
	 */
	public ParametrosSistemaDTO(Integer idParametro, Integer valor, String descripcion, Integer activo) {
		this.idParametro = idParametro;
		this.valor = valor;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	/**
	 * @return the idParametro
	 */
	public Integer getIdParametro() {
		return idParametro;
	}
	/**
	 * @param idParametro the idParametro to set
	 */
	public void setIdParametro(Integer idParametro) {
		this.idParametro = idParametro;
	}
	/**
	 * @return the valor
	 */
	public Integer getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(Integer valor) {
		this.valor = valor;
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
	public Integer getActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Integer activo) {
		this.activo = activo;
	}		
}
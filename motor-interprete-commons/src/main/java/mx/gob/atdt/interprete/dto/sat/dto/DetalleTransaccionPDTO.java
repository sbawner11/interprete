package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DetalleTransaccionPDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -359419456026321925L;
	
	@JsonProperty("ClaveTransaccion")
	private String claveTransaccion;
	
	@JsonProperty("ValorTransaccion")
	private Integer valorTransaccion;
	
	/**
	 * 
	 */
	public DetalleTransaccionPDTO() {
		// Constructor vacío para inicialización por defecto
	}

	/**
	 * @return the claveTransaccion
	 */
	public String getClaveTransaccion() {
		return claveTransaccion;
	}

	/**
	 * @param claveTransaccion the claveTransaccion to set
	 */
	public void setClaveTransaccion(String claveTransaccion) {
		this.claveTransaccion = claveTransaccion;
	}

	/**
	 * @return the valorTransaccion
	 */
	public Integer getValorTransaccion() {
		return valorTransaccion;
	}

	/**
	 * @param valorTransaccion the valorTransaccion to set
	 */
	public void setValorTransaccion(Integer valorTransaccion) {
		this.valorTransaccion = valorTransaccion;
	}	
}

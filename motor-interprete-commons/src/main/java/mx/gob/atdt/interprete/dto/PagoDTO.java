package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class PagoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443862513399695801L;
	
	private Integer id;
	private String parametro;
	private String valor;
	
	public PagoDTO() {

	}
	
	public PagoDTO(Integer id, String parametro, String valor) {
		this.id = id;
		this.parametro = parametro;
		this.valor = valor;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParametro() {
		return parametro;
	}
	public void setParametro(String parametro) {
		this.parametro = parametro;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	} 
	
	

}

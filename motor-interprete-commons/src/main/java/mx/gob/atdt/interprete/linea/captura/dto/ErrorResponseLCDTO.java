package mx.gob.atdt.interprete.linea.captura.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponseLCDTO implements Serializable{
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9001641985089932981L;

	@JsonProperty("codigo_resultado")
	private int codigoResultado;
    
	@JsonProperty("Mensaje")
	private String mensaje;
    
    
	public int getCodigoResultado() {
		return codigoResultado;
	}
	public void setCodigoResultado(int codigoResultado) {
		this.codigoResultado = codigoResultado;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
    
    
    
}

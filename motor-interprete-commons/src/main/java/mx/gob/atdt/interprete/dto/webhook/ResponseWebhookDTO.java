package mx.gob.atdt.interprete.dto.webhook;

import java.io.Serializable;

/**
 * Clase response webhook service
 * 
 * @author Ramiro Luna Torres
 */
public class ResponseWebhookDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9170727930051651797L;
	
	private int codigo;
	private String mensaje;
	private String folio;
	
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	
}

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class RespuestaDTO implements Serializable {

	private static final long serialVersionUID = 8921942653330738012L;
	private String documento;
	private String tipo;
	private String tamanio;

	public RespuestaDTO() {

	}

	/**
	 * @param documento
	 * @param tipo
	 * @param tamanio
	 */
	public RespuestaDTO(String documento, String tipo, String tamanio) {
		super();
		this.documento = documento;
		this.tipo = tipo;
		this.tamanio = tamanio;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTamanio() {
		return tamanio;
	}

	public void setTamanio(String tamanio) {
		this.tamanio = tamanio;
	}

	@Override
	public String toString() {
		return "RespuestaDTO [documento=" + documento + ", tipo=" + tipo + ", tamanio=" + tamanio + "]";
	}

}

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class ResponseDTO implements Serializable {

	private static final long serialVersionUID = -4099613861126948866L;
	private int codigo;
	private String mensaje;

	public ResponseDTO() {

	}

	public ResponseDTO(int codigo, String mensaje) {
		super();
		this.codigo = codigo;
		this.mensaje = mensaje;
	}

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

	@Override
	public String toString() {
		return "ResponseDTO [codigo=" + codigo + ", mensaje=" + mensaje + "]";
	}

}

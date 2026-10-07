package mx.gob.atdt.interprete.dto.webhook;

import java.io.Serializable;

/**
 * DTO Response para actualización de trámite en VDNI
 * REQ_FUN-2: Actualizar Trámite en VDNI
 * 
 * @author Edson
 * @date 2026-01-14
 */
public class WebhookActualizacionResponseDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String mensaje;
	private String codigoError;

	public WebhookActualizacionResponseDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}
}

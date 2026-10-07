package mx.gob.atdt.interprete.dto.webhook;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO para eventos recibidos via webhook
 * REQ_INTEGRACION - Servidor Webhook
 * 
 * @author Edson
 * @date 2026-01-14
 */
public class WebhookEventDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String tipoEvento;
	private String payload;
	private Date fechaEvento;
	private String estatus;

	public WebhookEventDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Date getFechaEvento() {
		return fechaEvento;
	}

	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
}

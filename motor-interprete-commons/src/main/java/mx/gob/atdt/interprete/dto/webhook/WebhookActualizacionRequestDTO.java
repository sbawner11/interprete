package mx.gob.atdt.interprete.dto.webhook;

import java.io.Serializable;

/**
 * DTO Request para actualización de trámite en VDNI
 * REQ_FUN-2: Actualizar Trámite en VDNI
 * 
 * @author Edson
 * @date 2026-01-14
 */
public class WebhookActualizacionRequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String estatusCliente;
	private String estatus;
	private String[] documentos; // Array de identificadores de documentos
	// TODO: Agregar parametros_autenticacion cuando se defina el esquema

	public WebhookActualizacionRequestDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEstatusCliente() {
		return estatusCliente;
	}

	public void setEstatusCliente(String estatusCliente) {
		this.estatusCliente = estatusCliente;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String[] getDocumentos() {
		return documentos;
	}

	public void setDocumentos(String[] documentos) {
		this.documentos = documentos;
	}
}

package mx.gob.atdt.interprete.linea.captura.dto;

import java.io.Serializable;

public class RequestConsultaLCDTO  implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idDependencia;
	private String idSolicitud;
	private String lineaCaptura;
	
	public RequestConsultaLCDTO() {
		// inicializar vacio objecto
	}
	
	public Integer getIdDependencia() {
		return idDependencia;
	}
	public void setIdDependencia(Integer idDependencia) {
		this.idDependencia = idDependencia;
	}
	public String getIdSolicitud() {
		return idSolicitud;
	}
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	public String getLineaCaptura() {
		return lineaCaptura;
	}
	public void setLineaCaptura(String lineaCaptura) {
		this.lineaCaptura = lineaCaptura;
	}

	@Override
	public String toString() {
		return "RequestConsultaLCDTO [idDependencia=" + idDependencia + ", idSolicitud=" + idSolicitud
				+ ", lineaCaptura=" + lineaCaptura + "]";
	}
	
	

}

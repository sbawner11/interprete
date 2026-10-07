package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RespuestaDatosGeneralesDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5560533942143596297L;
	
	@JsonProperty("Solicitud")
	private String solicitud;
	
	@JsonProperty("Resultado")
	private Integer resultado;
	
	@JsonProperty("IdDocumento")
	private Integer idDocumento;


	public RespuestaDatosGeneralesDTO() {
		super();
	}

	public String getSolicitud() {
		return solicitud;
	}

	public void setSolicitud(String solicitud) {
		this.solicitud = solicitud;
	}

	public Integer getResultado() {
		return resultado;
	}

	public void setResultado(Integer resultado) {
		this.resultado = resultado;
	}

	public Integer getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Integer idDocumento) {
		this.idDocumento = idDocumento;
	}
	
	
}

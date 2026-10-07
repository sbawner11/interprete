package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AcuseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7987093732487851021L;

	@JsonProperty("DatosLineaCaptura")
	private DatosLineaCapturaRespuestaDTO datosLineaCaptura;
	
	@JsonProperty("HTML")
	private String html;
	
	/**
	 * 
	 */
	public AcuseDTO() {
		// Constructo vacío para inicialización por default
	}

	public DatosLineaCapturaRespuestaDTO getDatosLineaCaptura() {
		return datosLineaCaptura;
	}

	public void setDatosLineaCaptura(DatosLineaCapturaRespuestaDTO datosLineaCaptura) {
		this.datosLineaCaptura = datosLineaCaptura;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

}

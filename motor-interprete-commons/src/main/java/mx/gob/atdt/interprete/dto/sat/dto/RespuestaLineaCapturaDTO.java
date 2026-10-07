package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

public class RespuestaLineaCapturaDTO <D> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6104259204370115632L;
	private D respuestaDTO;
	private String respuestaString;
	
	public D getRespuestaDTO() {
		return respuestaDTO;
	}
	public void setRespuestaDTO(D respuestaDTO) {
		this.respuestaDTO = respuestaDTO;
	}
	public String getRespuestaString() {
		return respuestaString;
	}
	public void setRespuestaString(String respuestaString) {
		this.respuestaString = respuestaString;
	}

}

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class ResponseServiceFirmaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 153471040968993147L;
	private String code;
	private String token;
	private String idSolicitud;
	//Variable auxiliar para registro de state
	private String state;

	public ResponseServiceFirmaDTO() {

	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the idSolicitud
	 */
	public String getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "ResponseServiceFirmaDTO [code=" + code + ", token=" + token + ", idSolicitud=" + idSolicitud + "]";
	}
}

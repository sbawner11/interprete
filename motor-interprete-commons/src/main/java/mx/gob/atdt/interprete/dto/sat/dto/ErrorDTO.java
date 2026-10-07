package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7987093732487851021L;
	
	@JsonProperty("Campo")
	private String campo;
	
	@JsonProperty("Codigo")
    private int codigo;
	
	@JsonProperty("Descripcion")
    private String descripcion;
	
	@JsonProperty("Tramite")
    private TramiteRespuestaDTO tramite;

	
	/**
	 * 
	 */
	public ErrorDTO() {
		// Constructo vacío para inicialización por default
	}


	public String getCampo() {
		return campo;
	}


	public void setCampo(String campo) {
		this.campo = campo;
	}


	public int getCodigo() {
		return codigo;
	}


	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public TramiteRespuestaDTO getTramite() {
		return tramite;
	}


	public void setTramite(TramiteRespuestaDTO tramite) {
		this.tramite = tramite;
	}

	
}

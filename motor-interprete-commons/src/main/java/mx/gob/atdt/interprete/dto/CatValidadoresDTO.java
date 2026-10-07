package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatValidadoresDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7880343492681201190L;

	private Integer idValidador;
	private String nombreValidador;
	private boolean activo;
	
	/**
	 * 
	 */
	public CatValidadoresDTO() {
	}

	public CatValidadoresDTO(Integer idValidador) {
		this.idValidador = idValidador;

	}
	/**
	 * @param idValidador
	 * @param nombreValidador
	 */
	public CatValidadoresDTO(Integer idValidador, String nombreValidador) {
		this.idValidador = idValidador;
		this.nombreValidador = nombreValidador;
	}

	/**
	 * @param idValidador
	 * @param nombreValidador
	 * @param activo
	 */
	public CatValidadoresDTO(Integer idValidador, String nombreValidador, boolean activo) {
		this.idValidador = idValidador;
		this.nombreValidador = nombreValidador;
		this.activo = activo;
	}

	/**
	 * @return the idValidador
	 */
	public Integer getIdValidador() {
		return idValidador;
	}

	/**
	 * @param idValidador the idValidador to set
	 */
	public void setIdValidador(Integer idValidador) {
		this.idValidador = idValidador;
	}

	/**
	 * @return the nombreValidador
	 */
	public String getNombreValidador() {
		return nombreValidador;
	}

	/**
	 * @param nombreValidador the nombreValidador to set
	 */
	public void setNombreValidador(String nombreValidador) {
		this.nombreValidador = nombreValidador;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}

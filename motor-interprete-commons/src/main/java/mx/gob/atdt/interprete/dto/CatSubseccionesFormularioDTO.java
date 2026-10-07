package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatSubseccionesFormularioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5101620723478036340L;

	private Long idSubseccionFormulario;
	private CatSeccionesFormularioDTO catSeccionesFormularioDTO;
	private String nombreSubseccion;
	private int orden;
	
	private Boolean deshabilitado;
	
	/**
	 * 
	 */
	public CatSubseccionesFormularioDTO() {
	}
		
	/**
	 * @param orden
	 * @param deshabilitado
	 */
	public CatSubseccionesFormularioDTO(int orden, Boolean deshabilitado) {
		this.orden = orden;
		this.deshabilitado = deshabilitado;
	}

	/**
	 * @param idSubseccionFormulario
	 * @param nombreSubseccion
	 * @param orden
	 */
	public CatSubseccionesFormularioDTO(Long idSubseccionFormulario, String nombreSubseccion, int orden) {
		this.idSubseccionFormulario = idSubseccionFormulario;
		this.nombreSubseccion = nombreSubseccion;
		this.orden = orden;
	}
	
	/**
	 * @param idSubseccionFormulario
	 * @param nombreSubseccion
	 * @param orden
	 * @param deshabilitado
	 */
	public CatSubseccionesFormularioDTO(Long idSubseccionFormulario, String nombreSubseccion, int orden,
			Boolean deshabilitado) {
		this.idSubseccionFormulario = idSubseccionFormulario;
		this.nombreSubseccion = nombreSubseccion;
		this.orden = orden;
		this.deshabilitado = deshabilitado;
	}


	/**
	 * @param idSubseccionFormulario
	 */
	public CatSubseccionesFormularioDTO(Long idSubseccionFormulario) {
		this.idSubseccionFormulario = idSubseccionFormulario;
	}

	/**
	 * @return the idSubseccionFormulario
	 */
	public Long getIdSubseccionFormulario() {
		return idSubseccionFormulario;
	}

	/**
	 * @param idSubseccionFormulario the idSubseccionFormulario to set
	 */
	public void setIdSubseccionFormulario(Long idSubseccionFormulario) {
		this.idSubseccionFormulario = idSubseccionFormulario;
	}

	/**
	 * @return the catSeccionesFormularioDTO
	 */
	public CatSeccionesFormularioDTO getCatSeccionesFormularioDTO() {
		return catSeccionesFormularioDTO;
	}

	/**
	 * @param catSeccionesFormularioDTO the catSeccionesFormularioDTO to set
	 */
	public void setCatSeccionesFormularioDTO(CatSeccionesFormularioDTO catSeccionesFormularioDTO) {
		this.catSeccionesFormularioDTO = catSeccionesFormularioDTO;
	}

	/**
	 * @return the nombreSubseccion
	 */
	public String getNombreSubseccion() {
		return nombreSubseccion;
	}

	/**
	 * @param nombreSubseccion the nombreSubseccion to set
	 */
	public void setNombreSubseccion(String nombreSubseccion) {
		this.nombreSubseccion = nombreSubseccion;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}


	/**
	 * @return the deshabilitado
	 */
	public Boolean getDeshabilitado() {
		return deshabilitado;
	}


	/**
	 * @param deshabilitado the deshabilitado to set
	 */
	public void setDeshabilitado(Boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}	
	
}

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatSeccionesFormularioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1214303433032261339L;

	private Long idSeccionFormulario;
	private ProyectoDTO proyecto;
	private String nombreSeccion;
	private int orden;
	
	private boolean deshabilitado;
	
	/**
	 * 
	 */
	public CatSeccionesFormularioDTO() {
	}
		
	/**	 
	 * @param orden
	 * @param deshabilitado
	 */
	public CatSeccionesFormularioDTO(int orden, boolean deshabilitado) {
		this.orden = orden;
		this.deshabilitado = deshabilitado;
	}

	/**
	 * @param idSeccionFormulario
	 * @param nombreSeccion
	 * @param orden
	 */
	public CatSeccionesFormularioDTO(Long idSeccionFormulario, String nombreSeccion, int orden) {
		this.idSeccionFormulario = idSeccionFormulario;
		this.nombreSeccion = nombreSeccion;
		this.orden = orden;
	}
	
	/**
	 * @param idSeccionFormulario
	 * @param nombreSeccion
	 * @param orden
	 * @param deshabilitado
	 */
	public CatSeccionesFormularioDTO(Long idSeccionFormulario, String nombreSeccion, int orden, boolean deshabilitado) {
		this.idSeccionFormulario = idSeccionFormulario;
		this.nombreSeccion = nombreSeccion;
		this.orden = orden;
		this.deshabilitado = deshabilitado;
	}

	/**
	 * @param idSeccionFormulario
	 */
	public CatSeccionesFormularioDTO(Long idSeccionFormulario) {
		this.idSeccionFormulario = idSeccionFormulario;
	}

	/**
	 * @return the idSeccionFormulario
	 */
	public Long getIdSeccionFormulario() {
		return idSeccionFormulario;
	}

	/**
	 * @param idSeccionFormulario the idSeccionFormulario to set
	 */
	public void setIdSeccionFormulario(Long idSeccionFormulario) {
		this.idSeccionFormulario = idSeccionFormulario;
	}

	/**
	 * @return the proyecto
	 */
	public ProyectoDTO getProyecto() {
		return proyecto;
	}

	/**
	 * @param proyecto the proyecto to set
	 */
	public void setProyecto(ProyectoDTO proyecto) {
		this.proyecto = proyecto;
	}

	/**
	 * @return the nombreSeccion
	 */
	public String getNombreSeccion() {
		return nombreSeccion;
	}

	/**
	 * @param nombreSeccion the nombreSeccion to set
	 */
	public void setNombreSeccion(String nombreSeccion) {
		this.nombreSeccion = nombreSeccion;
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
	public boolean isDeshabilitado() {
		return deshabilitado;
	}

	/**
	 * @param deshabilitado the deshabilitado to set
	 */
	public void setDeshabilitado(boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}
	
}

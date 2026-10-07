package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeccionesFormularioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8101581492454028809L;

	private Long idSeccionFormulario;
	private ProyectoDTO proyectoDTO;
	private String nombreSeccion;
	private int orden;
	private boolean activo;
	private boolean deshabilitado;
	private boolean seccionSincronizada;
	
	//Variables para observaciones por sección
	private boolean contieneObservaciones;
	private String observaciones;
	
	//Listado de las subsecciones que contiene cada sección
	private List<SubSeccionesFormularioDTO> lstSubsecciones;

	/**
	 * 
	 */
	public SeccionesFormularioDTO() {
	}
	
	/**
	 * 
	 * @param idSeccionFormulario
	 * @param idProyecto
	 */
	public SeccionesFormularioDTO(Long idSeccionFormulario, Long idProyecto) {
		this.idSeccionFormulario = idSeccionFormulario;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
	}

	/**
	 * 
	 * @param idSeccionFormulario
	 */
	public SeccionesFormularioDTO(Long idSeccionFormulario) {
		this.idSeccionFormulario = idSeccionFormulario;
	}

	/**
	 * @param idSeccionFormulario
	 * @param nombreSeccion
	 */
	public SeccionesFormularioDTO(Long idSeccionFormulario, String nombreSeccion) {
		this.idSeccionFormulario = idSeccionFormulario;
		this.nombreSeccion = nombreSeccion;
	}

	/**
	 * @param idSeccionFormulario
	 * @param proyectoDTO
	 * @param nombreSeccion
	 * @param orden
	 */
	public SeccionesFormularioDTO(Long idSeccionFormulario, ProyectoDTO proyectoDTO, String nombreSeccion, int orden) {
		this.idSeccionFormulario = idSeccionFormulario;
		this.proyectoDTO = proyectoDTO;
		this.nombreSeccion = nombreSeccion;
		this.orden = orden;
		
		this.lstSubsecciones = new ArrayList<SubSeccionesFormularioDTO>();
	}

	/**
	 * Constructor utilizado por la NamedQUery SeccionesFormulario.findByIdProyecto
	 * @param idSeccionFormulario
	 * @param idProyecto
	 * @param nombreSeccion
	 * @param orden
	 * @param activo
	 * @param seccionSincronizada
	 */
	public SeccionesFormularioDTO(Long idSeccionFormulario, Long idProyecto, String nombreSeccion, int orden,
			boolean activo, boolean seccionSincronizada) {
		this.idSeccionFormulario = idSeccionFormulario;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.nombreSeccion = nombreSeccion;
		this.orden = orden;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	/**
	 * 
	 * @param orden
	 * @param deshabilitado
	 */

	public SeccionesFormularioDTO(int orden, boolean deshabilitado) {
		this.orden = orden;
		this.deshabilitado = deshabilitado;
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
	 * @return the proyectoDTO
	 */
	public ProyectoDTO getProyectoDTO() {
		return proyectoDTO;
	}

	/**
	 * @param proyectoDTO the proyectoDTO to set
	 */
	public void setProyectoDTO(ProyectoDTO proyectoDTO) {
		this.proyectoDTO = proyectoDTO;
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
	
	/**
	 * @return the seccionSincronizada
	 */
	public boolean isSeccionSincronizada() {
		return seccionSincronizada;
	}

	/**
	 * @param seccionSincronizada the seccionSincronizada to set
	 */
	public void setSeccionSincronizada(boolean seccionSincronizada) {
		this.seccionSincronizada = seccionSincronizada;
	}
	
	/**
	 * @return the lstSubsecciones
	 */
	public List<SubSeccionesFormularioDTO> getLstSubsecciones() {
		return lstSubsecciones;
	}

	/**
	 * @param lstSubsecciones the lstSubsecciones to set
	 */
	public void setLstSubsecciones(List<SubSeccionesFormularioDTO> lstSubsecciones) {
		this.lstSubsecciones = lstSubsecciones;
	}
	
	/**
	 * @return the contieneObservaciones
	 */
	public boolean isContieneObservaciones() {
		return contieneObservaciones;
	}

	/**
	 * @param contieneObservaciones the contieneObservaciones to set
	 */
	public void setContieneObservaciones(boolean contieneObservaciones) {
		this.contieneObservaciones = contieneObservaciones;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	@Override
	public String toString() {
		return "SeccionesFormularioDTO [idSeccionFormulario=" + idSeccionFormulario + ", proyectoDTO=" + proyectoDTO
				+ ", nombreSeccion=" + nombreSeccion + ", orden=" + orden + ", activo=" + activo + "]";
	}
}

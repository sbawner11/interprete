package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SubSeccionesFormularioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9203054859427415038L;

	private Long idSubseccionFormulario;
	private SeccionesFormularioDTO seccionesFormularioDTO;
	private String nombreSubseccion;
	private int orden;
	private boolean activo;
	private boolean seccionSincronizada;
	private boolean deshabilitado;
	
	//Se utiliza para el borrado de componentes a nivel borrado de Sección.
	private List<ComponenteDTO> lstComponentes;

	/**
	 * 
	 */
	public SubSeccionesFormularioDTO() {
	}
	
	/**
	 * Constructor utilizado por la NamedQuery SubseccionesFormulario.findSeccionByIdSubseccion
	 * @param idSubseccionFormulario
	 * @param idSeccionFormulario
	 */
	public SubSeccionesFormularioDTO(Long idSubseccionFormulario, Long idSeccionFormulario) {
		this.idSubseccionFormulario = idSubseccionFormulario;
		this.seccionesFormularioDTO = new SeccionesFormularioDTO(idSeccionFormulario);
	}
	
	public SubSeccionesFormularioDTO(Long idSubseccionFormulario) {
		this.idSubseccionFormulario = idSubseccionFormulario;
	}

	/**
	 * Constructor utilizado por la Namedquery SubseccionesFormulario.findByIdSeccion
	 * @param idSubseccionFormulario
	 * @param idSeccionesFormulario
	 * @param nombreSubseccion
	 * @param orden
	 * @param activo
	 */
	public SubSeccionesFormularioDTO(Long idSubseccionFormulario, Long idSeccionesFormulario, String nombreSubseccion,
			int orden, boolean activo, boolean seccionSincronizada) {
		this.idSubseccionFormulario = idSubseccionFormulario;
		this.seccionesFormularioDTO = new SeccionesFormularioDTO(idSeccionesFormulario);
		this.nombreSubseccion = nombreSubseccion;
		this.orden = orden;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		
		this.lstComponentes = new ArrayList<ComponenteDTO>();
	}

	/**
	 * 
	 * @param orden
	 */
	public SubSeccionesFormularioDTO(int orden, boolean deshabilitado) {
		this.orden = orden;
		this.deshabilitado = deshabilitado;
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
	 * @return the seccionesFormularioDTO
	 */
	public SeccionesFormularioDTO getSeccionesFormularioDTO() {
		return seccionesFormularioDTO;
	}

	/**
	 * @param seccionesFormularioDTO the seccionesFormularioDTO to set
	 */
	public void setSeccionesFormularioDTO(SeccionesFormularioDTO seccionesFormularioDTO) {
		this.seccionesFormularioDTO = seccionesFormularioDTO;
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
	 * @return the lstComponentes
	 */
	public List<ComponenteDTO> getLstComponentes() {
		return lstComponentes;
	}

	/**
	 * @param lstComponentes the lstComponentes to set
	 */
	public void setLstComponentes(List<ComponenteDTO> lstComponentes) {
		this.lstComponentes = lstComponentes;
	}
	
}
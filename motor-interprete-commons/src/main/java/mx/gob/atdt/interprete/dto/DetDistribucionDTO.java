package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetDistribucionDTO implements Serializable {

	private static final long serialVersionUID = 3878676240210083603L;
	
	private Long idDistribucion;
	private ComponenteDTO componenteDTO;
	private ProyectoDTO proyectoDTO;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private Boolean activo;
	private Boolean seccionSincronizada;
	
	/**
	 * 
	 */
	public DetDistribucionDTO() {
		this.componenteDTO = new ComponenteDTO();
		this.proyectoDTO = new ProyectoDTO();
	}

	/**
	 * Constructor utilizado por la NamedQuery DetDistribucion.findByIdProyecto
	 * 
	 * @param idDistribucion
	 * @param idComponente
	 * @param idProyecto
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	public DetDistribucionDTO(Long idDistribucion, Long idComponente, Long idProyecto, Date fechaCreacion, 
			Date fechaUltimaActualizacion, Boolean activo, Boolean seccionSincronizada) {
		this.idDistribucion = idDistribucion;
		this.componenteDTO = new ComponenteDTO(idComponente);
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}
	
	/**
	 * Constructor utilizado por la NamedQuery DetDistribucion.existeDetalleActivoIdProyecto
	 * 
	 * @param idProyecto
	 * @param idDistribucion
	 */
	public DetDistribucionDTO(Long idProyecto, Long idDistribucion) {
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.idDistribucion = idDistribucion;
	}
	
	/**
	 * @return the idDistribucion
	 */
	public Long getIdDistribucion() {
		return idDistribucion;
	}

	/**
	 * @param idDistribucion the idDistribucion to set
	 */
	public void setIdDistribucion(Long idDistribucion) {
		this.idDistribucion = idDistribucion;
	}

	/**
	 * @return the componenteDTO
	 */
	public ComponenteDTO getComponenteDTO() {
		return componenteDTO;
	}

	/**
	 * @param componenteDTO the componenteDTO to set
	 */
	public void setComponenteDTO(ComponenteDTO componenteDTO) {
		this.componenteDTO = componenteDTO;
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
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the fechaUltimaActualizacion
	 */
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	/**
	 * @param fechaUltimaActualizacion the fechaUltimaActualizacion to set
	 */
	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	/**
	 * @return the activo
	 */
	public Boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the seccionSincronizada
	 */
	public Boolean getSeccionSincronizada() {
		return seccionSincronizada;
	}

	/**
	 * @param seccionSincronizada the seccionSincronizada to set
	 */
	public void setSeccionSincronizada(Boolean seccionSincronizada) {
		this.seccionSincronizada = seccionSincronizada;
	}
	
}

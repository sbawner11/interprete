package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;


public class DetLegalesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4771380756370578952L;
	
	private Long idDetalleLegal;
	private ProyectoDTO proyectoDTO;
	private boolean contieneAvisoSimplificado;
	private String cuerpoAvisoSimplificado;
	private boolean contieneAvisoIntegral;
	private String cuerpoAvisoIntegral;
	private boolean contieneManifiesto;
	private String cuerpoManifiesto;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	
	/**
	 * 
	 */
	public DetLegalesDTO() {
	}
	
	/**
	 * Constructor utilizado por la NamedQuery DetLegales.existeDetalleIdProyecto
	 * @param idProyecto
	 * @param idDetalleLegal
	 */
	public DetLegalesDTO(Long idProyecto, Long idDetalleLegal) {
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.idDetalleLegal = idDetalleLegal;
	}

	/**
	 * 
	 * @param idDetalleLegal
	 * @param proyectoDTO
	 * @param contieneAvisoSimplificado
	 * @param cuerpoAvisoSimplificado
	 * @param contieneAvisoIntegral
	 * @param cuerpoAvisoIntegral
	 * @param contieneManifiesto
	 * @param cuerpoManifiesto
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 */
	public DetLegalesDTO(Long idDetalleLegal, ProyectoDTO proyectoDTO, boolean contieneAvisoSimplificado,
			String cuerpoAvisoSimplificado, boolean contieneAvisoIntegral, String cuerpoAvisoIntegral,
			boolean contieneManifiesto, String cuerpoManifiesto, Date fechaCreacion, Date fechaUltimaActualizacion, 
			boolean activo) {
		this.idDetalleLegal = idDetalleLegal;
		this.proyectoDTO = proyectoDTO;
		this.contieneAvisoSimplificado = contieneAvisoSimplificado;
		this.cuerpoAvisoSimplificado = cuerpoAvisoSimplificado;
		this.contieneAvisoIntegral = contieneAvisoIntegral;
		this.cuerpoAvisoIntegral = cuerpoAvisoIntegral;
		this.contieneManifiesto = contieneManifiesto;
		this.cuerpoManifiesto = cuerpoManifiesto;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
	}
	
	
	/**
	 * Constructor utilizado por la NamedQuery DetLegales.findByIdProyecto
	 * @param idDetalleLegal
	 * @param idProyecto
	 * @param contieneAvisoSimplificado
	 * @param cuerpoAvisoSimplificado
	 * @param contieneAvisoIntegral
	 * @param cuerpoAvisoIntegral
	 * @param contieneManifiesto
	 * @param cuerpoManifiesto
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	public DetLegalesDTO(Long idDetalleLegal, Long idProyecto, boolean contieneAvisoSimplificado,
			String cuerpoAvisoSimplificado, boolean contieneAvisoIntegral, String cuerpoAvisoIntegral,
			boolean contieneManifiesto, String cuerpoManifiesto, Date fechaCreacion, Date fechaUltimaActualizacion, 
			boolean activo, boolean seccionSincronizada) {
		this.idDetalleLegal = idDetalleLegal;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.contieneAvisoSimplificado = contieneAvisoSimplificado;
		this.cuerpoAvisoSimplificado = cuerpoAvisoSimplificado;
		this.contieneAvisoIntegral = contieneAvisoIntegral;
		this.cuerpoAvisoIntegral = cuerpoAvisoIntegral;
		this.contieneManifiesto = contieneManifiesto;
		this.cuerpoManifiesto = cuerpoManifiesto;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	/**
	 * @return the idDetalleLegal
	 */
	public Long getIdDetalleLegal() {
		return idDetalleLegal;
	}

	/**
	 * @param idDetalleLegal the idDetalleLegal to set
	 */
	public void setIdDetalleLegal(Long idDetalleLegal) {
		this.idDetalleLegal = idDetalleLegal;
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
	 * @return the contieneAvisoSimplificado
	 */
	public boolean isContieneAvisoSimplificado() {
		return contieneAvisoSimplificado;
	}

	/**
	 * @param contieneAvisoSimplificado the contieneAvisoSimplificado to set
	 */
	public void setContieneAvisoSimplificado(boolean contieneAvisoSimplificado) {
		this.contieneAvisoSimplificado = contieneAvisoSimplificado;
	}

	/**
	 * @return the cuerpoAvisoSimplificado
	 */
	public String getCuerpoAvisoSimplificado() {
		return cuerpoAvisoSimplificado;
	}

	/**
	 * @param cuerpoAvisoSimplificado the cuerpoAvisoSimplificado to set
	 */
	public void setCuerpoAvisoSimplificado(String cuerpoAvisoSimplificado) {
		this.cuerpoAvisoSimplificado = cuerpoAvisoSimplificado;
	}

	/**
	 * @return the contieneAvisoIntegral
	 */
	public boolean isContieneAvisoIntegral() {
		return contieneAvisoIntegral;
	}

	/**
	 * @param contieneAvisoIntegral the contieneAvisoIntegral to set
	 */
	public void setContieneAvisoIntegral(boolean contieneAvisoIntegral) {
		this.contieneAvisoIntegral = contieneAvisoIntegral;
	}

	/**
	 * @return the cuerpoAvisoIntegral
	 */
	public String getCuerpoAvisoIntegral() {
		return cuerpoAvisoIntegral;
	}

	/**
	 * @param cuerpoAvisoIntegral the cuerpoAvisoIntegral to set
	 */
	public void setCuerpoAvisoIntegral(String cuerpoAvisoIntegral) {
		this.cuerpoAvisoIntegral = cuerpoAvisoIntegral;
	}

	/**
	 * @return the contieneManifiesto
	 */
	public boolean isContieneManifiesto() {
		return contieneManifiesto;
	}

	/**
	 * @param contieneManifiesto the contieneManifiesto to set
	 */
	public void setContieneManifiesto(boolean contieneManifiesto) {
		this.contieneManifiesto = contieneManifiesto;
	}

	/**
	 * @return the cuerpoManifiesto
	 */
	public String getCuerpoManifiesto() {
		return cuerpoManifiesto;
	}

	/**
	 * @param cuerpoManifiesto the cuerpoManifiesto to set
	 */
	public void setCuerpoManifiesto(String cuerpoManifiesto) {
		this.cuerpoManifiesto = cuerpoManifiesto;
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
	
}

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetCaptchaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3477233263351021371L;
	
	private Long idDetalleCaptcha;
	private ProyectoDTO proyectoDTO;
	private String llavePublica;
	private String llavePrivada;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	
	/**
	 * 
	 */
	public DetCaptchaDTO() {
	}
	
	/**
	 * Constructor utilizado por la NamedQuery DetCaptcha.existeDetalleIdProyecto 
	 * @param idProyecto
	 * @param idDetalleCaptcha
	 */
	public DetCaptchaDTO(Long idProyecto, Long idDetalleCaptcha) {		
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.idDetalleCaptcha = idDetalleCaptcha;		
	}

	/**
	 * @param idDetalleCaptcha
	 * @param proyectoDTO
	 * @param llavePublica
	 * @param llavePrivada
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 */
	public DetCaptchaDTO(Long idDetalleCaptcha, ProyectoDTO proyectoDTO, String llavePublica, String llavePrivada,
			Date fechaCreacion, Date fechaUltimaActualizacion) {
		this.idDetalleCaptcha = idDetalleCaptcha;
		this.proyectoDTO = proyectoDTO;
		this.llavePublica = llavePublica;
		this.llavePrivada = llavePrivada;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}
	
	/**
	 * Constructor utilizado por la NamedQuery DetCaptcha.findByIdProyecto
	 * @param idDetalleCaptcha
	 * @param idProyecto
	 * @param llavePublica
	 * @param llavePrivada
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	public DetCaptchaDTO(Long idDetalleCaptcha, Long idProyecto, String llavePublica, String llavePrivada,
			Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idDetalleCaptcha = idDetalleCaptcha;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.llavePublica = llavePublica;
		this.llavePrivada = llavePrivada;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	/**
	 * @return the idDetalleCaptcha
	 */
	public Long getIdDetalleCaptcha() {
		return idDetalleCaptcha;
	}

	/**
	 * @param idDetalleCaptcha the idDetalleCaptcha to set
	 */
	public void setIdDetalleCaptcha(Long idDetalleCaptcha) {
		this.idDetalleCaptcha = idDetalleCaptcha;
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
	 * @return the llavePublica
	 */
	public String getLlavePublica() {
		return llavePublica;
	}

	/**
	 * @param llavePublica the llavePublica to set
	 */
	public void setLlavePublica(String llavePublica) {
		this.llavePublica = llavePublica;
	}

	/**
	 * @return the llavePrivada
	 */
	public String getLlavePrivada() {
		return llavePrivada;
	}

	/**
	 * @param llavePrivada the llavePrivada to set
	 */
	public void setLlavePrivada(String llavePrivada) {
		this.llavePrivada = llavePrivada;
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

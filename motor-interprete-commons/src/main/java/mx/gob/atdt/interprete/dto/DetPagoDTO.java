package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetPagoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6885203324269949966L;
	
	private Long idDetallePago;
	private CatTipoCostoDTO catTipoCostoDTO;
	private ProyectoDTO proyectoDTO;
	private Double montoCostoFijo;
	private String urlServicio;
	private String identificadorProceso;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	
	/**
	 * 
	 */
	public DetPagoDTO() {
		catTipoCostoDTO = new CatTipoCostoDTO();
		proyectoDTO = new ProyectoDTO();
	}

	public DetPagoDTO(Long idDetallePago) {
		this.idDetallePago= idDetallePago;
	}
	
	/**
	 * Constructor utilizado por la NamedQuery DetPago.existeDetalleIdProyecto
	 * @param idProyecto
	 * @param idDetallePago
	 */
	public DetPagoDTO(Long idProyecto, Long idDetallePago) {
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.idDetallePago = idDetallePago;		
	}

	/**
	 * Constructor utilizado por la NamedQuery DetPago.findByIdProyecto
	 * @param idDetallePago
	 * @param idTipoCosto
	 * @param idProyecto
	 * @param montoCostoFijo
	 * @param urlServicio
	 * @param identificadorProceso
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	public DetPagoDTO(Long idDetallePago, Integer idTipoCosto, Long idProyecto,
			Double montoCostoFijo, String urlServicio, String identificadorProceso, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idDetallePago = idDetallePago;
		this.catTipoCostoDTO = new CatTipoCostoDTO(idTipoCosto);
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.montoCostoFijo = montoCostoFijo;
		this.urlServicio = urlServicio;
		this.identificadorProceso = identificadorProceso;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}
	
	
	/**
	 * @return the idDetallePago
	 */
	public Long getIdDetallePago() {
		return idDetallePago;
	}

	/**
	 * @param idDetallePago the idDetallePago to set
	 */
	public void setIdDetallePago(Long idDetallePago) {
		this.idDetallePago = idDetallePago;
	}

	/**
	 * @return the catTipoCostoDTO
	 */
	public CatTipoCostoDTO getCatTipoCostoDTO() {
		return catTipoCostoDTO;
	}

	/**
	 * @param catTipoCostoDTO the catTipoCostoDTO to set
	 */
	public void setCatTipoCostoDTO(CatTipoCostoDTO catTipoCostoDTO) {
		this.catTipoCostoDTO = catTipoCostoDTO;
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
	 * @return the montoCostoFijo
	 */
	public Double getMontoCostoFijo() {
		return montoCostoFijo;
	}

	/**
	 * @param montoCostoFijo the montoCostoFijo to set
	 */
	public void setMontoCostoFijo(Double montoCostoFijo) {
		this.montoCostoFijo = montoCostoFijo;
	}

	/**
	 * @return the urlServicio
	 */
	public String getUrlServicio() {
		return urlServicio;
	}

	/**
	 * @param urlServicio the urlServicio to set
	 */
	public void setUrlServicio(String urlServicio) {
		this.urlServicio = urlServicio;
	}

	/**
	 * @return the identificadorProceso
	 */
	public String getIdentificadorProceso() {
		return identificadorProceso;
	}

	/**
	 * @param identificadorProceso the identificadorProceso to set
	 */
	public void setIdentificadorProceso(String identificadorProceso) {
		this.identificadorProceso = identificadorProceso;
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

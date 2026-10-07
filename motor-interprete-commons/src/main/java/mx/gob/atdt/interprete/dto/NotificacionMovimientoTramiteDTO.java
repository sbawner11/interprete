package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class NotificacionMovimientoTramiteDTO implements Serializable {

	private static final long serialVersionUID = -6615779416387401854L;
	
	private long idNotificacionMovimiento;
	private CatTipoNotificacionDTO catTipoNotificacionDTO;
	private TramiteDTO tramiteDTO;
	private Date fechaNotificacion;
	private boolean envioConfirmado;
	
	/**
	 * 
	 */
	public NotificacionMovimientoTramiteDTO() {
		
	}
	
	/**
	 * 
	 */
	public NotificacionMovimientoTramiteDTO(long idNotificacionMovimiento) {
		this.idNotificacionMovimiento = idNotificacionMovimiento;
	}

	/**
	 * @param idNotificacionMovimiento
	 * @param catTipoNotificacionDTO
	 * @param tramiteDTO
	 * @param fechaNotificacion
	 * @param envioConfirmado
	 */
	public NotificacionMovimientoTramiteDTO(long idNotificacionMovimiento, CatTipoNotificacionDTO catTipoNotificacionDTO, TramiteDTO tramiteDTO, Date fechaNotificacion, boolean envioConfirmado) {
		this.idNotificacionMovimiento = idNotificacionMovimiento;
		this.catTipoNotificacionDTO = catTipoNotificacionDTO;
		this.tramiteDTO = tramiteDTO;
		this.fechaNotificacion = fechaNotificacion;
		this.envioConfirmado = envioConfirmado;
	}
	
	/**
	 * Constructor utlizado por la NamedQuery NotificacionMovimientoTramite.findAllNoConfirmadas
	 * 
	 * @param idNotificacionMovimiento
	 * @param idTipoNotificacion
	 * @param descripcionNotificacion
	 * @param idTramite
	 * @param folioSeguimiento
	 * @param idEstatusTramite
	 * @param descripcionEstatus
	 * @param fechaCreacion
	 * @param fechaRevision
	 * @param fechaNotificacion
	 * @param envioConfirmado
	 */
	@SuppressWarnings({"java:S107"})
	public NotificacionMovimientoTramiteDTO(long idNotificacionMovimiento, int idTipoNotificacion, String descripcionNotificacion, 
			Long idTramite, String folioSeguimiento, int idEstatusTramite, String descripcionEstatus, Date fechaCreacion,
			Date fechaRevision, Date fechaNotificacion, boolean envioConfirmado) {
		this.idNotificacionMovimiento = idNotificacionMovimiento;
		this.catTipoNotificacionDTO = new CatTipoNotificacionDTO(idTipoNotificacion, descripcionNotificacion);
		this.tramiteDTO = new TramiteDTO(idTramite, folioSeguimiento, idEstatusTramite, descripcionEstatus, fechaCreacion, fechaRevision);
		this.fechaNotificacion = fechaNotificacion;
		this.envioConfirmado = envioConfirmado;
	}
	
	/**
	 * Constructor utlizado por la NamedQuery NotificacionMovimientoTramite.findAllTramitesNotificacionFallida
	 *   
	 * @param idNotificacionMovimiento
	 * @param idTipoNotificacion
	 * @param idTramite
	 * @param folioSeguimiento
	 * @param uuid
	 * @param fechaCreacion
	 * @param fechaRevision
	 * @param rutaDocumentoPrevencion
	 * @param rutaDocumentoRevocado
	 * @param rutaDocumentoResolucionPositiva
	 * @param rutaDocumentoResolucionNegativa
	 * @param idEstatusTramite
	 * @param descripcion
	 * @param descripcionAviso
	 * @param descripcionPersonalizada
	 * @param idUsuarioLlaveCdmx
	 * @param curp
	 * @param idPersonaMoral
	 */
	@SuppressWarnings({"java:S107"})
	public NotificacionMovimientoTramiteDTO(long idNotificacionMovimiento, int idTipoNotificacion, Long idTramite, String folioSeguimiento, String uuid, Date fechaCreacion, Date fechaRevision, 
			String rutaDocumentoPrevencion, String rutaDocumentoRevocado, String rutaDocumentoResolucionPositiva, 
			String rutaDocumentoResolucionNegativa, int idEstatusTramite, String descripcion, String descripcionAviso, 
			String descripcionPersonalizada, Long idUsuarioLlaveCdmx, String curp, Long idPersonaMoral) {
		this.idNotificacionMovimiento = idNotificacionMovimiento;
		this.catTipoNotificacionDTO = new CatTipoNotificacionDTO(idTipoNotificacion);
		this.tramiteDTO = new TramiteDTO(idTramite, folioSeguimiento, uuid, fechaCreacion, fechaRevision, rutaDocumentoPrevencion, rutaDocumentoRevocado, rutaDocumentoResolucionPositiva, rutaDocumentoResolucionNegativa, idEstatusTramite, descripcion, descripcionAviso, descripcionPersonalizada, idUsuarioLlaveCdmx, curp, idPersonaMoral);
	}

	/**
	 * @return the idNotificacionMovimiento
	 */
	public long getIdNotificacionMovimiento() {
		return idNotificacionMovimiento;
	}

	/**
	 * @param idNotificacionMovimiento the idNotificacionMovimiento to set
	 */
	public void setIdNotificacionMovimiento(long idNotificacionMovimiento) {
		this.idNotificacionMovimiento = idNotificacionMovimiento;
	}

	/**
	 * @return the catTipoNotificacionDTO
	 */
	public CatTipoNotificacionDTO getCatTipoNotificacionDTO() {
		return catTipoNotificacionDTO;
	}

	/**
	 * @param catTipoNotificacionDTO the catTipoNotificacionDTO to set
	 */
	public void setCatTipoNotificacionDTO(CatTipoNotificacionDTO catTipoNotificacionDTO) {
		this.catTipoNotificacionDTO = catTipoNotificacionDTO;
	}

	/**
	 * @return the tramiteDTO
	 */
	public TramiteDTO getTramiteDTO() {
		return tramiteDTO;
	}

	/**
	 * @param tramiteDTO the tramiteDTO to set
	 */
	public void setTramiteDTO(TramiteDTO tramiteDTO) {
		this.tramiteDTO = tramiteDTO;
	}

	/**
	 * @return the fechaNotificacion
	 */
	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	/**
	 * @param fechaNotificacion the fechaNotificacion to set
	 */
	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}

	/**
	 * @return the envioConfirmado
	 */
	public boolean isEnvioConfirmado() {
		return envioConfirmado;
	}

	/**
	 * @param envioConfirmado the envioConfirmado to set
	 */
	public void setEnvioConfirmado(boolean envioConfirmado) {
		this.envioConfirmado = envioConfirmado;
	}
	
}

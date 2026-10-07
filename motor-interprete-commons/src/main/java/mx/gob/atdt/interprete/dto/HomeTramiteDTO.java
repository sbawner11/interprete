package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HomeTramiteDTO implements Serializable {

	private static final long serialVersionUID = 759630984896562699L;

	private Long idHomeTramite;
	private DetHomeDTO detalleHomeDTO;
	private String rutaImagen;
	private ArchivoDTO imagenInicio;
	private String notificacionEmpezar;
	private List<AgregaDescripcionesDTO> lstRequisitos;
	private String costoTramite;
	private List<AgregaDescripcionesDTO> lstExepciones;
	private boolean isNotificacion;
	private boolean isRequisitos;
	private boolean isCosto;
	private boolean isExepcion;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean seccionSincronizada;
	private DetLegalesDTO legalesDTO;

	public HomeTramiteDTO() {
		imagenInicio = new ArchivoDTO();
	}

	/**
	 * 
	 * @param idHomeTramite
	 */
	public HomeTramiteDTO(Long idHomeTramite) {
		this.idHomeTramite = idHomeTramite;
	}

	/**
	 * @param idHomeTramite
	 * @param rutaImagen
	 * @param imagenInicio
	 * @param notificacionEmpezar
	 * @param lstRequisitos
	 * @param costoTramite
	 * @param lstExepciones
	 * @param isAccesoLlave
	 * @param isManifiesto
	 * @param isNotificacion
	 * @param isRequisitos
	 * @param isCostoTramite
	 * @param isExepcion
	 */
	public HomeTramiteDTO(Long idHomeTramite, String rutaImagen, String notificacionEmpezar, String costoTramite,
			boolean isNotificacion, boolean isRequisitos, boolean isCosto, boolean isExepcion) {
		super();
		this.idHomeTramite = idHomeTramite;
		this.rutaImagen = rutaImagen;
		this.notificacionEmpezar = notificacionEmpezar;
		this.costoTramite = costoTramite;
		this.isNotificacion = isNotificacion;
		this.isRequisitos = isRequisitos;
		this.isCosto = isCosto;
		this.isExepcion = isExepcion;
	}

	public Long getIdHomeTramite() {
		return idHomeTramite;
	}

	public void setIdHomeTramite(Long idHomeTramite) {
		this.idHomeTramite = idHomeTramite;
	}

	public DetHomeDTO getDetalleHomeDTO() {
		return detalleHomeDTO;
	}

	public void setDetalleHomeDTO(DetHomeDTO detalleHomeDTO) {
		this.detalleHomeDTO = detalleHomeDTO;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	
	public ArchivoDTO getImagenInicio() {
		return imagenInicio;
	}

	public void setImagenInicio(ArchivoDTO imagenInicio) {
		this.imagenInicio = imagenInicio;
	}

	public String getNotificacionEmpezar() {
		return notificacionEmpezar;
	}

	public void setNotificacionEmpezar(String notificacionEmpezar) {
		this.notificacionEmpezar = notificacionEmpezar;
	}

	public List<AgregaDescripcionesDTO> getLstRequisitos() {
		return lstRequisitos;
	}

	public void setLstRequisitos(List<AgregaDescripcionesDTO> lstRequisitos) {
		this.lstRequisitos = lstRequisitos;
	}

	public String getCostoTramite() {
		return costoTramite;
	}

	public void setCostoTramite(String costoTramite) {
		this.costoTramite = costoTramite;
	}

	public List<AgregaDescripcionesDTO> getLstExepciones() {
		return lstExepciones;
	}

	public void setLstExepciones(List<AgregaDescripcionesDTO> lstExepciones) {
		this.lstExepciones = lstExepciones;
	}

	public boolean isNotificacion() {
		return isNotificacion;
	}

	public void setNotificacion(boolean isNotificacion) {
		this.isNotificacion = isNotificacion;
	}

	public boolean isRequisitos() {
		return isRequisitos;
	}

	public void setRequisitos(boolean isRequisitos) {
		this.isRequisitos = isRequisitos;
	}

	public boolean isCosto() {
		return isCosto;
	}

	public void setCosto(boolean isCosto) {
		this.isCosto = isCosto;
	}

	public boolean isExepcion() {
		return isExepcion;
	}

	public void setExepcion(boolean isExepcion) {
		this.isExepcion = isExepcion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
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

	public DetLegalesDTO getLegalesDTO() {
		return legalesDTO;
	}

	public void setLegalesDTO(DetLegalesDTO legalesDTO) {
		this.legalesDTO = legalesDTO;
	}

	@Override
	public String toString() {
		return "HomeTramiteDTO [idHomeTramite=" + idHomeTramite + ", detalleHomeDTO=" + detalleHomeDTO + ", rutaImagen="
				+ rutaImagen + ", notificacionEmpezar=" + notificacionEmpezar + ", lstRequisitos=" + lstRequisitos
				+ ", costoTramite=" + costoTramite + ", lstExepciones=" + lstExepciones + ", isNotificacion="
				+ isNotificacion + ", isRequisitos=" + isRequisitos + ", isCosto=" + isCosto + ", isExepcion="
				+ isExepcion + ", fechaCreacion=" + fechaCreacion + ", fechaUltimaActualizacion="
				+ fechaUltimaActualizacion + ", seccionSincronizada=" + seccionSincronizada + "]";
	}

}

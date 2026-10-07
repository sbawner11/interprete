package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetAnalyticsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6545368180790901561L;

	private Long idDetalleAnalytics;
	private ProyectoDTO proyectoDTO;
	private String identificadorAnalytics;
	private String tituloBusqueda;
	private String descripcionBusqueda;
	private String palabraClaveBusqueda;
	private String tituloGrap;
	private String descripcionGrap;
	private String urlGrap;
	private String rutaImagenGrap;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private ArchivoDTO imagenDTO;
	private boolean activo;
	private boolean seccionSincronizada;

	/**
	 * 
	 */
	public DetAnalyticsDTO() {
		this.imagenDTO = new ArchivoDTO();
	}

	/**
	 * Constructor utilizado por la NamedQuery
	 * DetalleAnalytics.existeDetalleIdProyecto
	 * 
	 * @param idProyecto
	 * @param idDetalleAnalytics
	 */
	public DetAnalyticsDTO(Long idProyecto, Long idDetalleAnalytics) {
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.idDetalleAnalytics = idDetalleAnalytics;
	}

	/**
	 * @param idDetalleAnalytics
	 * @param proyectoDTO
	 * @param identificadorAnalytics
	 * @param tituloBusqueda
	 * @param descripcionBusqueda
	 * @param palabraClaveBusqueda
	 * @param tituloGrap
	 * @param descripcionGrap
	 * @param urlGrap
	 * @param rutaImagenGrap
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 */
	public DetAnalyticsDTO(Long idDetalleAnalytics, ProyectoDTO proyectoDTO, String identificadorAnalytics,
			String tituloBusqueda, String descripcionBusqueda, String palabraClaveBusqueda, String tituloGrap,
			String descripcionGrap, String urlGrap, String rutaImagenGrap, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean activo) {
		this.idDetalleAnalytics = idDetalleAnalytics;
		this.proyectoDTO = proyectoDTO;
		this.identificadorAnalytics = identificadorAnalytics;
		this.tituloBusqueda = tituloBusqueda;
		this.descripcionBusqueda = descripcionBusqueda;
		this.palabraClaveBusqueda = palabraClaveBusqueda;
		this.tituloGrap = tituloGrap;
		this.descripcionGrap = descripcionGrap;
		this.urlGrap = urlGrap;
		this.rutaImagenGrap = rutaImagenGrap;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
	}

	/**
	 * Constructor utilizado por la NamedQuery DetalleAnalytics.findByIdProyecto
	 * 
	 * @param idDetalleAnalytics
	 * @param idProyecto
	 * @param identificadorAnalytics
	 * @param tituloBusqueda
	 * @param descripcionBusqueda
	 * @param palabraClaveBusqueda
	 * @param tituloGrap
	 * @param descripcionGrap
	 * @param urlGrap
	 * @param rutaImagenGrap
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	public DetAnalyticsDTO(Long idDetalleAnalytics, Long idProyecto, String identificadorAnalytics,
			String tituloBusqueda, String descripcionBusqueda, String palabraClaveBusqueda, String tituloGrap,
			String descripcionGrap, String urlGrap, String rutaImagenGrap, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idDetalleAnalytics = idDetalleAnalytics;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.identificadorAnalytics = identificadorAnalytics;
		this.tituloBusqueda = tituloBusqueda;
		this.descripcionBusqueda = descripcionBusqueda;
		this.palabraClaveBusqueda = palabraClaveBusqueda;
		this.tituloGrap = tituloGrap;
		this.descripcionGrap = descripcionGrap;
		this.urlGrap = urlGrap;
		this.rutaImagenGrap = rutaImagenGrap;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	/**
	 * @return the idDetalleAnalytics
	 */
	public Long getIdDetalleAnalytics() {
		return idDetalleAnalytics;
	}

	/**
	 * @param idDetalleAnalytics the idDetalleAnalytics to set
	 */
	public void setIdDetalleAnalytics(Long idDetalleAnalytics) {
		this.idDetalleAnalytics = idDetalleAnalytics;
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
	 * @return the identificadorAnalytics
	 */
	public String getIdentificadorAnalytics() {
		return identificadorAnalytics;
	}

	/**
	 * @param identificadorAnalytics the identificadorAnalytics to set
	 */
	public void setIdentificadorAnalytics(String identificadorAnalytics) {
		this.identificadorAnalytics = identificadorAnalytics;
	}

	/**
	 * @return the tituloBusqueda
	 */
	public String getTituloBusqueda() {
		return tituloBusqueda;
	}

	/**
	 * @param tituloBusqueda the tituloBusqueda to set
	 */
	public void setTituloBusqueda(String tituloBusqueda) {
		this.tituloBusqueda = tituloBusqueda;
	}

	/**
	 * @return the descripcionBusqueda
	 */
	public String getDescripcionBusqueda() {
		return descripcionBusqueda;
	}

	/**
	 * @param descripcionBusqueda the descripcionBusqueda to set
	 */
	public void setDescripcionBusqueda(String descripcionBusqueda) {
		this.descripcionBusqueda = descripcionBusqueda;
	}

	/**
	 * @return the palabraClaveBusqueda
	 */
	public String getPalabraClaveBusqueda() {
		return palabraClaveBusqueda;
	}

	/**
	 * @param palabraClaveBusqueda the palabraClaveBusqueda to set
	 */
	public void setPalabraClaveBusqueda(String palabraClaveBusqueda) {
		this.palabraClaveBusqueda = palabraClaveBusqueda;
	}

	/**
	 * @return the tituloGrap
	 */
	public String getTituloGrap() {
		return tituloGrap;
	}

	/**
	 * @param tituloGrap the tituloGrap to set
	 */
	public void setTituloGrap(String tituloGrap) {
		this.tituloGrap = tituloGrap;
	}

	/**
	 * @return the descripcionGrap
	 */
	public String getDescripcionGrap() {
		return descripcionGrap;
	}

	/**
	 * @param descripcionGrap the descripcionGrap to set
	 */
	public void setDescripcionGrap(String descripcionGrap) {
		this.descripcionGrap = descripcionGrap;
	}

	/**
	 * @return the urlGrap
	 */
	public String getUrlGrap() {
		return urlGrap;
	}

	/**
	 * @param urlGrap the urlGrap to set
	 */
	public void setUrlGrap(String urlGrap) {
		this.urlGrap = urlGrap;
	}

	/**
	 * @return the rutaImagenGrap
	 */
	public String getRutaImagenGrap() {
		return rutaImagenGrap;
	}

	/**
	 * @param rutaImagenGrap the rutaImagenGrap to set
	 */
	public void setRutaImagenGrap(String rutaImagenGrap) {
		this.rutaImagenGrap = rutaImagenGrap;
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
	 * @return the imagenDTO
	 */
	public ArchivoDTO getImagenDTO() {
		return imagenDTO;
	}

	/**
	 * @param imagenDTO the imagenDTO to set
	 */
	public void setImagenDTO(ArchivoDTO imagenDTO) {
		this.imagenDTO = imagenDTO;
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

	@Override
	public String toString() {
		return "DetAnalyticsDTO [idDetalleAnalytics=" + idDetalleAnalytics + ", proyectoDTO=" + proyectoDTO
				+ ", identificadorAnalytics=" + identificadorAnalytics + ", tituloBusqueda=" + tituloBusqueda
				+ ", descripcionBusqueda=" + descripcionBusqueda + ", palabraClaveBusqueda=" + palabraClaveBusqueda
				+ ", tituloGrap=" + tituloGrap + ", descripcionGrap=" + descripcionGrap + ", urlGrap=" + urlGrap
				+ ", rutaImagenGrap=" + rutaImagenGrap + ", fechaCreacion=" + fechaCreacion
				+ ", fechaUltimaActualizacion=" + fechaUltimaActualizacion + ", imagenDTO=" + imagenDTO + ", activo="
				+ ", imagenDTO=" + activo + ", seccionSincronizada=" + seccionSincronizada + "]";
	}

}
package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetSecurityDomainDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4586767275043513845L;

	private Long idDetalleSecurity;
	private CatTipoSecurityDomainDTO catTipoSecurityDomainDTO;
	private ProyectoDTO proyectoDTO;
	private String usuario;
	private String contrasenia;
	private String urlSistema;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;

	/**
	 * 
	 */
	public DetSecurityDomainDTO() {
		catTipoSecurityDomainDTO = new CatTipoSecurityDomainDTO();
	}

	/**
	 * Constructor utilizado por la NamedQuery
	 * DetAccesoLlave.existeDetalleIdProyecto
	 * 
	 * @param idProyecto
	 * @param idDetalleSecurity
	 */
	public DetSecurityDomainDTO(Long idProyecto, Long idDetalleSecurity) {
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.idDetalleSecurity = idDetalleSecurity;
	}

	/**
	 * @param idDetalleSecurity
	 * @param proyectoDTO
	 * @param usuario
	 * @param contrasenia
	 * @param urlSistema
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 */
	public DetSecurityDomainDTO(Long idDetalleSecurity, ProyectoDTO proyectoDTO, String usuario, String contrasenia,
			String urlSistema, Date fechaCreacion, Date fechaUltimaActualizacion) {
		this.idDetalleSecurity = idDetalleSecurity;
		this.proyectoDTO = proyectoDTO;
		this.usuario = usuario;
		this.contrasenia = contrasenia;
		this.urlSistema = urlSistema;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	/**
	 * Constructor utilizado por la NamedQuery DetSecurityDomain.findByIdProyecto
	 * 
	 * @param idProyecto
	 * @param idDetalleSecurity
	 * @param usuario
	 * @param contrasenia
	 * @param urlSistema
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 * @param idTipoSecurityDomain
	 * @param descripcion
	 */
	public DetSecurityDomainDTO(Long idProyecto, Long idDetalleSecurity, String usuario, String contrasenia,
			String urlSistema, Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo,
			Integer idTipoSecurityDomain, String descripcion) {
		this.idDetalleSecurity = idDetalleSecurity;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.usuario = usuario;
		this.contrasenia = contrasenia;
		this.urlSistema = urlSistema;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.catTipoSecurityDomainDTO = new CatTipoSecurityDomainDTO(idTipoSecurityDomain, descripcion);
	}

	/**
	 * @return the idDetalleSecurity
	 */
	public Long getidDetalleSecurity() {
		return idDetalleSecurity;
	}

	/**
	 * @param idDetalleSecurity the idDetalleAcceso to set
	 */
	public void setidDetalleSecurity(Long idDetalleSecurity) {
		this.idDetalleSecurity = idDetalleSecurity;
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
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param sistema the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the contrasenia
	 */
	public String getContrasenia() {
		return contrasenia;
	}

	/**
	 * @param contrasenia the contrasenia to set
	 */
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	/**
	 * @return the urlSistema
	 */
	public String getUrlSistema() {
		return urlSistema;
	}

	/**
	 * @param urlSistema the urlSistema to set
	 */
	public void setUrlSistema(String urlSistema) {
		this.urlSistema = urlSistema;
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
	 * @return the catTipoSecurityDomainDTO
	 */
	public CatTipoSecurityDomainDTO getCatTipoSecurityDomainDTO() {
		return catTipoSecurityDomainDTO;
	}

	/**
	 * @param catTipoSecurityDomainDTO the catTipoSecurityDomainDTO to set
	 */
	public void setCatTipoSecurityDomainDTO(CatTipoSecurityDomainDTO catTipoSecurityDomainDTO) {
		this.catTipoSecurityDomainDTO = catTipoSecurityDomainDTO;
	}

	@Override
	public String toString() {
		return "DetSecurityDomainDTO [idDetalleSecurity=" + idDetalleSecurity + ", proyectoDTO=" + proyectoDTO
				+ ", usuario=" + usuario + ", contrasenia=" + contrasenia + ", urlSistema=" + urlSistema
				+ ", fechaCreacion=" + fechaCreacion + ", fechaUltimaActualizacion=" + fechaUltimaActualizacion
				+ ", activo=" + activo + "]";
	}

}

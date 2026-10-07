package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetSecurityDomainLineasCapturaDTO implements Serializable {

    private static final long serialVersionUID = 6900416782696044852L;
    
	private long idSecurityDomainLc;
    private DetLineaCapturaDTO detLineaCapturaDTO;
    private String usuario;
    private String contrasenia;
    private String urlSistema;
    private long idUsuarioRegistro;
    private Date fechaCreacion;
    private Date fechaUltimaActualizacion;
    private boolean activo;
    private boolean seccionSincronizada;

    /* CONSTRUCTORES */

    public DetSecurityDomainLineasCapturaDTO() {
        this.detLineaCapturaDTO = new DetLineaCapturaDTO();
    }

    public DetSecurityDomainLineasCapturaDTO(long idSecurityDomainLc) {
        this.idSecurityDomainLc = idSecurityDomainLc;
        this.detLineaCapturaDTO = new DetLineaCapturaDTO();
    }

    /**
     * Constructor utilizado por la NamedQuery DetSecurityDomainLineasCaptura.findByIdLineaCaptura
     * @param idSecurityDomainLc
     * @param idLineaCaptura
     * @param usuario
     * @param contrasenia
     * @param urlSistema
     * @param idUsuarioRegistro
     * @param fechaCreacion
     * @param fechaUltimaActualizacion
     * @param activo
     * @param seccionSincronizada
     */
    @SuppressWarnings({"java:S107"})
    public DetSecurityDomainLineasCapturaDTO(
            long idSecurityDomainLc, long idLineaCaptura,
            String usuario, String contrasenia, String urlSistema,
            long idUsuarioRegistro, Date fechaCreacion, Date fechaUltimaActualizacion,
            boolean activo, boolean seccionSincronizada) {

        this.idSecurityDomainLc = idSecurityDomainLc;
        this.detLineaCapturaDTO = new DetLineaCapturaDTO(idLineaCaptura);
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.urlSistema = urlSistema;
        this.idUsuarioRegistro = idUsuarioRegistro;
        this.fechaCreacion = fechaCreacion;
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
        this.activo = activo;
        this.seccionSincronizada = seccionSincronizada;
    }

    /* GETTERS / SETTERS */

	/**
	 * @return the idSecurityDomainLc
	 */
	public long getIdSecurityDomainLc() {
		return idSecurityDomainLc;
	}

	/**
	 * @param idSecurityDomainLc the idSecurityDomainLc to set
	 */
	public void setIdSecurityDomainLc(long idSecurityDomainLc) {
		this.idSecurityDomainLc = idSecurityDomainLc;
	}

	/**
	 * @return the detLineaCapturaDTO
	 */
	public DetLineaCapturaDTO getDetLineaCapturaDTO() {
		return detLineaCapturaDTO;
	}

	/**
	 * @param detLineaCapturaDTO the detLineaCapturaDTO to set
	 */
	public void setDetLineaCapturaDTO(DetLineaCapturaDTO detLineaCapturaDTO) {
		this.detLineaCapturaDTO = detLineaCapturaDTO;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
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


	public long getIdUsuarioRegistro() {
		return idUsuarioRegistro;
	}

	public void setIdUsuarioRegistro(long idUsuarioRegistro) {
		this.idUsuarioRegistro = idUsuarioRegistro;
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
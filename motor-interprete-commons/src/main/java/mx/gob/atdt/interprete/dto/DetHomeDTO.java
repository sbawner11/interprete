package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetHomeDTO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3279330295389673990L;

    private Long idDetalleHome;
    private ProyectoDTO proyectoDTO;
    private String rutaArchivoLogotipo;
    private boolean habilitaNotificacion;
    private String descripcionNotificacion;
	private boolean personalizaPausa;
	private String tituloPausa;
	private String descripcionPausa;
    private Date fechaCreacion;
    private Date fechaUltimaActualizacion;
    private boolean activo;
    private boolean seccionSincronizada;

    /**
     * 
     */
    public DetHomeDTO() {
    }

    /**
     * @param idDetalleHome
     */
    public DetHomeDTO(Long idDetalleHome) {
        this.idDetalleHome = idDetalleHome;
    }

    /**
     * Constructor utilizado por la NamedQuery
     * DetHome.existeDetalleActivoIdProyecto
     * 
     * @param idDetalleHome
     * @param idProyecto
     */
    public DetHomeDTO(Long idDetalleHome, Long idProyecto) {
        this.idDetalleHome = idDetalleHome;
        this.proyectoDTO = new ProyectoDTO(idProyecto);
    }

    /**
     * @param idDetalleHome
     * @param proyectoDTO
     * @param rutaArchivoLogotipo
     * @param habilitaAccesoTramiteLlave
     * @param habilitaManifiesto
     * @param habilitaNotificacion
     * @param descripcionNotificacion
     * @param fechaCreacion
     * @param fechaUltimaActualizacion
     */
    public DetHomeDTO(Long idDetalleHome, ProyectoDTO proyectoDTO, String rutaArchivoLogotipo,
            boolean habilitaNotificacion, String descripcionNotificacion, Date fechaCreacion,
            Date fechaUltimaActualizacion) {
        this.idDetalleHome = idDetalleHome;
        this.proyectoDTO = proyectoDTO;
        this.rutaArchivoLogotipo = rutaArchivoLogotipo;
        this.habilitaNotificacion = habilitaNotificacion;
        this.descripcionNotificacion = descripcionNotificacion;
        this.fechaCreacion = fechaCreacion;
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
    }

    /**
     * Constructor utilizado por la NamedQuery DetHome.findByIdProyecto
     * 
     * @param idDetalleHome
     * @param idProyecto
     * @param rutaArchivoLogotipo
     * @param habilitaNotificacion
     * @param descripcionNotificacion
     * @param fechaCreacion
     * @param fechaUltimaActualizacion
     * @param activo
     * @param seccionSincronizada
     */
    public DetHomeDTO(Long idDetalleHome, Long idProyecto, String rutaArchivoLogotipo, boolean habilitaNotificacion,
            String descripcionNotificacion, Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
        this.idDetalleHome = idDetalleHome;
        this.proyectoDTO = new ProyectoDTO(idProyecto);
        this.rutaArchivoLogotipo = rutaArchivoLogotipo;
        this.habilitaNotificacion = habilitaNotificacion;
        this.descripcionNotificacion = descripcionNotificacion;
        this.fechaCreacion = fechaCreacion;
        this.fechaUltimaActualizacion = fechaUltimaActualizacion;
        this.activo = activo;
        this.seccionSincronizada = seccionSincronizada;
    }

    /**
     * @return the idDetalleHome
     */
    public Long getIdDetalleHome() {
        return idDetalleHome;
    }

    /**
     * @param idDetalleHome the idDetalleHome to set
     */
    public void setIdDetalleHome(Long idDetalleHome) {
        this.idDetalleHome = idDetalleHome;
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
     * @return the rutaArchivoLogotipo
     */
    public String getRutaArchivoLogotipo() {
        return rutaArchivoLogotipo;
    }

    /**
     * @param rutaArchivoLogotipo the rutaArchivoLogotipo to set
     */
    public void setRutaArchivoLogotipo(String rutaArchivoLogotipo) {
        this.rutaArchivoLogotipo = rutaArchivoLogotipo;
    }

    /**
     * @return the habilitaNotificacion
     */
    public boolean isHabilitaNotificacion() {
        return habilitaNotificacion;
    }

    /**
     * @param habilitaNotificacion the habilitaNotificacion to set
     */
    public void setHabilitaNotificacion(boolean habilitaNotificacion) {
        this.habilitaNotificacion = habilitaNotificacion;
    }

    /**
     * @return the descripcionNotificacion
     */
    public String getDescripcionNotificacion() {
        return descripcionNotificacion;
    }

    /**
     * @param descripcionNotificacion the descripcionNotificacion to set
     */
    public void setDescripcionNotificacion(String descripcionNotificacion) {
        this.descripcionNotificacion = descripcionNotificacion;
    }
    
	/**
	 * @return the personalizaPausa
	 */
	public boolean isPersonalizaPausa() {
		return personalizaPausa;
	}

	/**
	 * @param personalizaPausa the personalizaPausa to set
	 */
	public void setPersonalizaPausa(boolean personalizaPausa) {
		this.personalizaPausa = personalizaPausa;
	}

	/**
	 * @return the tituloPausa
	 */
	public String getTituloPausa() {
		return tituloPausa;
	}

	/**
	 * @param tituloPausa the tituloPausa to set
	 */
	public void setTituloPausa(String tituloPausa) {
		this.tituloPausa = tituloPausa;
	}

	/**
	 * @return the descripcionPausa
	 */
	public String getDescripcionPausa() {
		return descripcionPausa;
	}

	/**
	 * @param descripcionPausa the descripcionPausa to set
	 */
	public void setDescripcionPausa(String descripcionPausa) {
		this.descripcionPausa = descripcionPausa;
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

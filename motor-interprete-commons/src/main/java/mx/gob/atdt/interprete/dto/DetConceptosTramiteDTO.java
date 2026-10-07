package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetConceptosTramiteDTO implements Serializable {

    private static final long serialVersionUID = -8354536318203201689L;
    
	private long idConceptoTramite;
    private DetTramitesLineaCapturaDTO tramiteLineaCapturaDTO;
    private int secuencia;
    private int clave;
    private int agrupador;
    private CatTipoAgrupadorDTO tipoAgrupadorDTO;
    private CatPeriodicidadDTO catPeriodicidadDTO;
    private CatPeriodoDTO periodoDTO;
    private CatEjercicioDTO ejercicioDTO;
    private int claveContable;
    private long importe;
    private long idUsuarioRegistro;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private boolean activo;
    private boolean seccionSincronizada;

    /* CONSTRUCTORES */

    public DetConceptosTramiteDTO() {
        this.tramiteLineaCapturaDTO = new DetTramitesLineaCapturaDTO();
        this.tipoAgrupadorDTO = new CatTipoAgrupadorDTO();
        this.catPeriodicidadDTO = new CatPeriodicidadDTO();
        this.periodoDTO = new CatPeriodoDTO();
        this.ejercicioDTO = new CatEjercicioDTO();
    }

    public DetConceptosTramiteDTO(long idConceptoTramite) {
        this.idConceptoTramite = idConceptoTramite;
        this.tramiteLineaCapturaDTO = new DetTramitesLineaCapturaDTO();
        this.tipoAgrupadorDTO = new CatTipoAgrupadorDTO();
        this.catPeriodicidadDTO = new CatPeriodicidadDTO();
        this.periodoDTO = new CatPeriodoDTO();
        this.ejercicioDTO = new CatEjercicioDTO();
    }

    public DetConceptosTramiteDTO(long idConceptoTramite, int clave) {
        this.idConceptoTramite = idConceptoTramite;
        this.clave = clave;
        this.tramiteLineaCapturaDTO = new DetTramitesLineaCapturaDTO();
        this.tipoAgrupadorDTO = new CatTipoAgrupadorDTO();
        this.catPeriodicidadDTO = new CatPeriodicidadDTO();
        this.periodoDTO = new CatPeriodoDTO();
        this.ejercicioDTO = new CatEjercicioDTO();
    }

    /**
     * Constructor utilizado por la NamedQuery DetConceptosTramite.findByIdConceptoTramite
     * @param idConceptoTramite
     * @param idLineacTramite
     * @param secuencia
     * @param clave
     * @param agrupador
     * @param idTipoAgrupador
     * @param idPeriodo
     * @param idEjercicio
     * @param claveContable
     * @param importe
     * @param idUsuarioRegistro
     * @param fechaCreacion
     * @param fechaActualizacion
     * @param activo
     * @param seccionSincronizada
     */
    @SuppressWarnings({"java:S107"})
    public DetConceptosTramiteDTO(long idConceptoTramite, long idLineacTramite,
            int secuencia, int clave,
            int agrupador, int idTipoAgrupador, int idPeriodicidad,
            int idPeriodo, int idEjercicio, int claveContable, long importe,
            long idUsuarioRegistro,
            Date fechaCreacion, Date fechaActualizacion,
            boolean activo, boolean seccionSincronizada) {
        this.idConceptoTramite = idConceptoTramite;
        this.tramiteLineaCapturaDTO = new DetTramitesLineaCapturaDTO(idLineacTramite);
        this.secuencia = secuencia;
        this.clave = clave;
        this.agrupador = agrupador;
        this.tipoAgrupadorDTO = new CatTipoAgrupadorDTO(idTipoAgrupador);
        this.catPeriodicidadDTO = new CatPeriodicidadDTO(idPeriodicidad);
        this.periodoDTO = new CatPeriodoDTO(idPeriodo);
        this.ejercicioDTO = new CatEjercicioDTO(idEjercicio);
        this.claveContable = claveContable;
        this.importe = importe;
        this.idUsuarioRegistro = idUsuarioRegistro;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.activo = activo;
        this.seccionSincronizada = seccionSincronizada;
    }
    
    @SuppressWarnings({"java:S107"})
    public DetConceptosTramiteDTO(long idConceptoTramite, long idLineacTramite,
            int secuencia, int clave,
            int agrupador, 
            int idTipoAgrupador, String tipoAgrupador, String taDescripcion, 
            int idPeriodicidad, String prClave, String prDescripcion,
            int idPeriodo, String perClave, String perDescripcion,
            int idEjercicio, int ejercicio, 
            int claveContable, long importe,
            long idUsuarioRegistro,
            Date fechaCreacion, Date fechaActualizacion,
            boolean activo, boolean seccionSincronizada) {
        this.idConceptoTramite = idConceptoTramite;
        this.tramiteLineaCapturaDTO = new DetTramitesLineaCapturaDTO(idLineacTramite);
        this.secuencia = secuencia;
        this.clave = clave;
        this.agrupador = agrupador;
        this.tipoAgrupadorDTO = new CatTipoAgrupadorDTO(idTipoAgrupador, tipoAgrupador, taDescripcion);
        this.catPeriodicidadDTO = new CatPeriodicidadDTO(idPeriodicidad, prDescripcion, prClave);
        this.periodoDTO = new CatPeriodoDTO(idPeriodo, idPeriodicidad, perDescripcion, perClave);
        this.ejercicioDTO = new CatEjercicioDTO(idEjercicio, ejercicio);
        this.claveContable = claveContable;
        this.importe = importe;
        this.idUsuarioRegistro = idUsuarioRegistro;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.activo = activo;
        this.seccionSincronizada = seccionSincronizada;
    }

    @SuppressWarnings({"java:S107"})
    public DetConceptosTramiteDTO(long idConceptoTramite, long idLineacTramite,
            int secuencia, int clave,
            int agrupador, 
            int idTipoAgrupador, String tipoAgrupadorTA, String descripcionTA,
            int idPeriodicidad, String descripcionCP, String claveCP,
            int idPeriodo, String descripcionP, String claveP,
            int idEjercicio, int claveContable, long importe,
            long idUsuarioRegistro,
            Date fechaCreacion, Date fechaActualizacion,
            boolean activo, boolean seccionSincronizada) {
        this.idConceptoTramite = idConceptoTramite;
        this.tramiteLineaCapturaDTO = new DetTramitesLineaCapturaDTO(idLineacTramite);
        this.secuencia = secuencia;
        this.clave = clave;
        this.agrupador = agrupador;
        this.tipoAgrupadorDTO = new CatTipoAgrupadorDTO(idTipoAgrupador, tipoAgrupadorTA, descripcionTA);
        this.catPeriodicidadDTO = new CatPeriodicidadDTO(idPeriodicidad, descripcionCP, claveCP);
        this.periodoDTO = new CatPeriodoDTO(idPeriodo, descripcionP, claveP);
        this.ejercicioDTO = new CatEjercicioDTO(idEjercicio);
        this.claveContable = claveContable;
        this.importe = importe;
        this.idUsuarioRegistro = idUsuarioRegistro;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.activo = activo;
        this.seccionSincronizada = seccionSincronizada;
    }

    @SuppressWarnings({"java:S107"})
    public DetConceptosTramiteDTO(long idConceptoTramite, DetTramitesLineaCapturaDTO detTramiteLineaCaptura,
            int secuencia, int clave, int agrupador, CatTipoAgrupadorDTO catTipoAgrupador,
            CatPeriodoDTO catPeriodo, CatEjercicioDTO catEjercicio, int claveContable, long importe,
            long idUsuarioRegistro, Date fechaCreacion, Date fechaActualizacion,
            boolean activo, boolean seccionSincronizada) {
        this.idConceptoTramite = idConceptoTramite;
        this.tramiteLineaCapturaDTO = detTramiteLineaCaptura;
        this.secuencia = secuencia;
        this.clave = clave;
        this.agrupador = agrupador;
        this.tipoAgrupadorDTO = catTipoAgrupador;
        this.periodoDTO = catPeriodo;
        this.ejercicioDTO = catEjercicio;
        this.claveContable = claveContable;
        this.importe = importe;
        this.idUsuarioRegistro = idUsuarioRegistro;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.activo = activo;
        this.seccionSincronizada = seccionSincronizada;
    }

    /* GETTERS / SETTERS */

    
	/**
	 * @return the idConceptoTramite
	 */
	public long getIdConceptoTramite() {
		return idConceptoTramite;
	}

	/**
	 * @param idConceptoTramite the idConceptoTramite to set
	 */
	public void setIdConceptoTramite(long idConceptoTramite) {
		this.idConceptoTramite = idConceptoTramite;
	}

	/**
	 * @return the tramiteLineaCapturaDTO
	 */
	public DetTramitesLineaCapturaDTO getTramiteLineaCapturaDTO() {
		return tramiteLineaCapturaDTO;
	}

	/**
	 * @param tramiteLineaCapturaDTO the tramiteLineaCapturaDTO to set
	 */
	public void setTramiteLineaCapturaDTO(DetTramitesLineaCapturaDTO tramiteLineaCapturaDTO) {
		this.tramiteLineaCapturaDTO = tramiteLineaCapturaDTO;
	}

	/**
	 * @return the secuencia
	 */
	public int getSecuencia() {
		return secuencia;
	}

	/**
	 * @param secuencia the secuencia to set
	 */
	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}

	/**
	 * @return the clave
	 */
	public int getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(int clave) {
		this.clave = clave;
	}

	/**
	 * @return the agrupador
	 */
	public int getAgrupador() {
		return agrupador;
	}

	/**
	 * @param agrupador the agrupador to set
	 */
	public void setAgrupador(int agrupador) {
		this.agrupador = agrupador;
	}

	/**
	 * @return the tipoAgrupadorDTO
	 */
	public CatTipoAgrupadorDTO getTipoAgrupadorDTO() {
		return tipoAgrupadorDTO;
	}

	/**
	 * @param tipoAgrupadorDTO the tipoAgrupadorDTO to set
	 */
	public void setTipoAgrupadorDTO(CatTipoAgrupadorDTO tipoAgrupadorDTO) {
		this.tipoAgrupadorDTO = tipoAgrupadorDTO;
	}
	
	/**
	 * @return the catPeriodicidadDTO
	 */
	public CatPeriodicidadDTO getCatPeriodicidadDTO() {
		return catPeriodicidadDTO;
	}

	/**
	 * @param catPeriodicidadDTO the catPeriodicidadDTO to set
	 */
	public void setCatPeriodicidadDTO(CatPeriodicidadDTO catPeriodicidadDTO) {
		this.catPeriodicidadDTO = catPeriodicidadDTO;
	}

	/**
	 * @return the periodoDTO
	 */
	public CatPeriodoDTO getPeriodoDTO() {
		return periodoDTO;
	}

	/**
	 * @param periodoDTO the periodoDTO to set
	 */
	public void setPeriodoDTO(CatPeriodoDTO periodoDTO) {
		this.periodoDTO = periodoDTO;
	}

	/**
	 * @return the ejercicioDTO
	 */
	public CatEjercicioDTO getEjercicioDTO() {
		return ejercicioDTO;
	}

	/**
	 * @param ejercicioDTO the ejercicioDTO to set
	 */
	public void setEjercicioDTO(CatEjercicioDTO ejercicioDTO) {
		this.ejercicioDTO = ejercicioDTO;
	}

	/**
	 * @return the claveContable
	 */
	public int getClaveContable() {
		return claveContable;
	}

	/**
	 * @param claveContable the claveContable to set
	 */
	public void setClaveContable(int claveContable) {
		this.claveContable = claveContable;
	}

	/**
	 * @return the importe
	 */
	public long getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(long importe) {
		this.importe = importe;
	}

	/**
	 * @return the idUsuarioRegistro
	 */
	public long getIdUsuarioRegistro() {
		return idUsuarioRegistro;
	}

	/**
	 * @param idUsuarioRegistro the idUsuarioRegistro to set
	 */
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
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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
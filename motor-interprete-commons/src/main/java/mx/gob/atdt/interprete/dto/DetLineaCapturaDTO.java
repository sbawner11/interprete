package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetLineaCapturaDTO implements Serializable {
			
	private static final long serialVersionUID = 2445065741631985643L;
	
	private long idDetalleLineaCaptura;
    private ProyectoDTO proyectoDTO;
    private CatDependenciaPagoDTO dependenciaPagoDTO;
    private CatUnidadAdministrativaPagoDTO unidadAdministrativaPagoDTO;
    private int  vigencia;
    private CatTipoVigenciaDTO tipoVigenciaDTO;
    private CatTipoPersonaDTO tipoPersonaDTO;
    private long idUsuarioRegistro;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private boolean completo;
    private boolean activo;
    private boolean seccionSincronizada;
    private String solicitudLineaCaptura;
    
    /*
	 * 
	 */
	public DetLineaCapturaDTO() {
		this.proyectoDTO = new ProyectoDTO();
		this.dependenciaPagoDTO = new CatDependenciaPagoDTO();
		this.unidadAdministrativaPagoDTO = new CatUnidadAdministrativaPagoDTO();
		this.tipoVigenciaDTO = new CatTipoVigenciaDTO();
		this.tipoPersonaDTO = new CatTipoPersonaDTO();
	}
	

	@SuppressWarnings({"java:S107"})
	public DetLineaCapturaDTO(long idDetalleLineaCaptura, Long idProyecto, 
			int idDependenciaPago, final String sigla, final String descripcion,
			int idUA, final String descripcionUA, final String claveUA,
			int vigencia, int idTipoV, final String claveTV, final String descripcionTV,
			int idTipoP, final String claveTP, final String descripcionTP,
			long idUsuarioRegistro, Date fechaCreacion, Date fechaActualizacion,
			boolean completo, boolean activo, boolean seccionSincronizada) {
		this.idDetalleLineaCaptura = idDetalleLineaCaptura;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.dependenciaPagoDTO = new CatDependenciaPagoDTO(idDependenciaPago, sigla, descripcion);
		this.unidadAdministrativaPagoDTO = new CatUnidadAdministrativaPagoDTO(idUA, descripcionUA, claveUA);
		this.vigencia = vigencia;
		this.tipoVigenciaDTO = new CatTipoVigenciaDTO(idTipoV, claveTP, descripcionTV);
		this.tipoPersonaDTO = new CatTipoPersonaDTO(idTipoP, claveTP, descripcionTP);
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.completo = completo;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}
	
	/**
	 * Constructor utilizado por la NamedQuery DetLineaCaptura.findByIdProyecto
	 * @param idDetalleLineaCaptura
	 * @param idProyecto
	 * @param idDependenciaPago
	 * @param idUA
	 * @param vigencia
	 * @param idTipoV
	 * @param idTipoP
	 * @param idUsuarioRegistro
	 * @param fechaCreacion
	 * @param fechaActualizacion
	 * @param completo
	 * @param activo
	 * @param seccionSincronizada
	 */
	@SuppressWarnings({"java:S107"})
	public DetLineaCapturaDTO(long idDetalleLineaCaptura, Long idProyecto, 
			int idDependenciaPago, int idUA,
			int vigencia, int idTipoV, int idTipoP, 
			long idUsuarioRegistro, Date fechaCreacion, Date fechaActualizacion,
			boolean completo, boolean activo, boolean seccionSincronizada) {
		this.idDetalleLineaCaptura = idDetalleLineaCaptura;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.dependenciaPagoDTO = new CatDependenciaPagoDTO(idDependenciaPago);
		this.unidadAdministrativaPagoDTO = new CatUnidadAdministrativaPagoDTO(idUA);
		this.vigencia = vigencia;
		this.tipoVigenciaDTO = new CatTipoVigenciaDTO(idTipoV);
		this.tipoPersonaDTO = new CatTipoPersonaDTO(idTipoP);
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.completo = completo;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	@SuppressWarnings({"java:S107"})
	public DetLineaCapturaDTO(long idDetalleLineaCaptura, ProyectoDTO proyectoDTO, 
			CatDependenciaPagoDTO dependenciaPagoDTO, CatUnidadAdministrativaPagoDTO unidadAdministrativaPagoDTO,
			int vigencia, CatTipoVigenciaDTO tipoVigenciaDTO, CatTipoPersonaDTO tipoPersonaDTO, 
			long idUsuarioRegistro, Date fechaCreacion, Date fechaActualizacion,
			boolean completo, boolean activo, boolean seccionSincronizada) {
		this.idDetalleLineaCaptura = idDetalleLineaCaptura;
		this.proyectoDTO = proyectoDTO;
		this.dependenciaPagoDTO = dependenciaPagoDTO;
		this.unidadAdministrativaPagoDTO = unidadAdministrativaPagoDTO;
		this.vigencia = vigencia;
		this.tipoVigenciaDTO = tipoVigenciaDTO;
		this.tipoPersonaDTO = tipoPersonaDTO;
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.completo = completo;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

    /*
	 * 
	 */
	public DetLineaCapturaDTO(long idDetalleLineaCaptura) {
		this.idDetalleLineaCaptura = idDetalleLineaCaptura;
		this.proyectoDTO = new ProyectoDTO();
		this.dependenciaPagoDTO = new CatDependenciaPagoDTO();
		this.unidadAdministrativaPagoDTO = new CatUnidadAdministrativaPagoDTO();
		this.tipoVigenciaDTO = new CatTipoVigenciaDTO();
		this.tipoPersonaDTO = new CatTipoPersonaDTO();
	}

	// Getters y setters	

	/**
	 * @return the idDetalleLineaCaptura
	 */
	public long getIdDetalleLineaCaptura() {
		return idDetalleLineaCaptura;
	}

	/**
	 * @param idDetalleLineaCaptura the idDetalleLineaCaptura to set
	 */
	public void setIdDetalleLineaCaptura(long idDetalleLineaCaptura) {
		this.idDetalleLineaCaptura = idDetalleLineaCaptura;
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
	 * @return the dependenciaPagoDTO
	 */
	public CatDependenciaPagoDTO getDependenciaPagoDTO() {
		return dependenciaPagoDTO;
	}

	/**
	 * @param dependenciaPagoDTO the dependenciaPagoDTO to set
	 */
	public void setDependenciaPagoDTO(CatDependenciaPagoDTO dependenciaPagoDTO) {
		this.dependenciaPagoDTO = dependenciaPagoDTO;
	}

	/**
	 * @return the unidadAdministrativaPagoDTO
	 */
	public CatUnidadAdministrativaPagoDTO getUnidadAdministrativaPagoDTO() {
		return unidadAdministrativaPagoDTO;
	}

	/**
	 * @param unidadAdministrativaPagoDTO the unidadAdministrativaPagoDTO to set
	 */
	public void setUnidadAdministrativaPagoDTO(CatUnidadAdministrativaPagoDTO unidadAdministrativaPagoDTO) {
		this.unidadAdministrativaPagoDTO = unidadAdministrativaPagoDTO;
	}

	/**
	 * @return the vigencia
	 */
	public int getVigencia() {
		return vigencia;
	}

	/**
	 * @param vigencia the vigencia to set
	 */
	public void setVigencia(int vigencia) {
		this.vigencia = vigencia;
	}

	/**
	 * @return the tipoVigenciaDTO
	 */
	public CatTipoVigenciaDTO getTipoVigenciaDTO() {
		return tipoVigenciaDTO;
	}

	/**
	 * @param tipoVigenciaDTO the tipoVigenciaDTO to set
	 */
	public void setTipoVigenciaDTO(CatTipoVigenciaDTO tipoVigenciaDTO) {
		this.tipoVigenciaDTO = tipoVigenciaDTO;
	}

	/**
	 * @return the tipoPersonaDTO
	 */
	public CatTipoPersonaDTO getTipoPersonaDTO() {
		return tipoPersonaDTO;
	}

	/**
	 * @param tipoPersonaDTO the tipoPersonaDTO to set
	 */
	public void setTipoPersonaDTO(CatTipoPersonaDTO tipoPersonaDTO) {
		this.tipoPersonaDTO = tipoPersonaDTO;
	}

	/**
	 * @return the idUsuario
	 */
	public long getIdUsuarioRegistro() {
		return idUsuarioRegistro;
	}

	/**
	 * @param idUsuario the idUsuario to set
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
	 * @return the completo
	 */
	public boolean isCompleto() {
		return completo;
	}

	/**
	 * @param completo the completo to set
	 */
	public void setCompleto(boolean completo) {
		this.completo = completo;
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


	public String getSolicitudLineaCaptura() {
		return solicitudLineaCaptura;
	}

	public void setSolicitudLineaCaptura(String solicitudLineaCaptura) {
		this.solicitudLineaCaptura = solicitudLineaCaptura;
	}

}
package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class TramiteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -863328555016699722L;

	private Long idTramite;
	private String folioSeguimiento;
	private UsuarioDTO usuario;
	private CatEstatusTramiteDTO catEstatusTramiteDTO;
	private UsuarioDTO usuarioRevisor;
	private String respuestaFolioPrevencion;
	private String respuestaFolioConclusion;
	private Date fechaCreacion;
	private ProyectoDTO proyectoDTO;
	private Date fechaRevision;
	private String uuid;
	private String rutaDocumentoPrevencion;
	private String rutaDocumentoRevocado;
	private String rutaDocumentoResolucionPositiva;
	private String rutaDocumentoResolucionNegativa;
	private String motivoRechazo;
	private String tipoPersona;

	// Variables para colocar estilos de la etiqueta de Estatus
	private String estiloEstatus;
	private String estiloTxtEstatus;

	// Variables para filtros en la bandeja de entrada
	private Date fechaDesde;
	private Date fechaHasta;

	// Variable para guardar el usaurio operador asignado a un trámite
	private UsuarioDTO usuarioOperador;
	
	// Variable para marcar si el trámite cuenta con firma electrónica si el proyecto permite
	// realizar el firmado del trámites
	private boolean tramiteFirmado;
	
	private LineaCapturaDTO lineaCapturaDTO;
	
	// Variables para colocar estilos de la etiqueta de Estatus
	private String estiloLCEstatus;
	private String estiloLCTxtEstatus;
	/**
	 * 
	 */
	public TramiteDTO() {
		catEstatusTramiteDTO = new CatEstatusTramiteDTO();
		usuario = new UsuarioDTO();
	}

	/** 
	 * @param idTramite
	 */
	public TramiteDTO(Long idTramite) {
		this.idTramite = idTramite;
	}
	
	/**
	 * Constructor utilizado por la NamedQuery Tramites.findAllNoNotificados, Tramites.findAllNoNotificadosWithFirmaActu, Tramites.findAllNoNotificadosWithFirmaCrea
	 * @param idTramite
	 * @param idEstatusTramite
	 * @param descripcionEstatus
	 */
	public TramiteDTO(Long idTramite, int idEstatusTramite, String descripcionEstatus) {
		this.idTramite = idTramite;
		this.catEstatusTramiteDTO = new CatEstatusTramiteDTO(idEstatusTramite, descripcionEstatus);		
	}

	/**
	 * Constructor utilizado por la NamedQuery NotificacionMovimientoTramite.findAllNoConfirmadas
	 * 
	 * @param idTramite
	 * @param folioSeguimiento
	 * @param idEstatusTramite
	 * @param descripcionEstatus
	 * @param fechaCreacion
	 * @param fechaRevision
	 */
	public TramiteDTO(Long idTramite, String folioSeguimiento, int idEstatusTramite, String descripcionEstatus,
			Date fechaCreacion, Date fechaRevision) {
		this.idTramite = idTramite;
		this.folioSeguimiento = folioSeguimiento;
		this.catEstatusTramiteDTO = new CatEstatusTramiteDTO(idEstatusTramite, descripcionEstatus);
		this.fechaCreacion = fechaCreacion;
		this.fechaRevision = fechaRevision;
	}

	/**
	 * @param idTramite
	 * @param folioSeguimiento
	 * @param usuario
	 * @param catEstatusTramiteDTO
	 * @param usuarioRevisor
	 * @param respuestaFolio
	 * @param fechaCreacion
	 */
	@SuppressWarnings({"java:S107"})
	public TramiteDTO(Long idTramite, String folioSeguimiento, UsuarioDTO usuario,
			CatEstatusTramiteDTO catEstatusTramiteDTO, UsuarioDTO usuarioRevisor, String respuestaFolioPrevencion,
			String respuestaFolioConclusion, Date fechaCreacion, UsuarioDTO usuarioOperador) {
		this.idTramite = idTramite;
		this.folioSeguimiento = folioSeguimiento;
		this.usuario = usuario;
		this.catEstatusTramiteDTO = catEstatusTramiteDTO;
		this.usuarioRevisor = usuarioRevisor;
		this.respuestaFolioPrevencion = respuestaFolioPrevencion;
		this.respuestaFolioConclusion = respuestaFolioConclusion;
		this.fechaCreacion = fechaCreacion;
		this.usuarioOperador = usuarioOperador;
	}
	
	
	/**
	 *  Constructor utilizado por la NamedQuery NotificacionMovimientoTramite.findAllTramitesNotificacionFallida
	 *  
	 * @param idTramite
	 * @param folioSeguimiento
	 * @param idEstatusTramite
	 * @param idUsuarioLlaveCdmx
	 * @param curp
	 * @param idPersonaMoral
	 * @param fechaCreacion
	 * @param fechaRevision
	 * @param rutaDocumentoPrevencion
	 * @param rutaDocumentoRevocado
	 * @param rutaDocumentoResolucionPositiva
	 * @param rutaDocumentoResolucionNegativa
	 */
	@SuppressWarnings({"java:S107"})
	public TramiteDTO(Long idTramite, String folioSeguimiento, String uuid, Date fechaCreacion, Date fechaRevision, 
			String rutaDocumentoPrevencion, String rutaDocumentoRevocado, String rutaDocumentoResolucionPositiva, 
			String rutaDocumentoResolucionNegativa, int idEstatusTramite, String descripcion, String descripcionAviso, 
			String descripcionPersonalizada, Long idUsuarioLlaveCdmx, String curp, Long idPersonaMoral) {
		
		super();
		this.idTramite = idTramite;
		this.folioSeguimiento = folioSeguimiento;
		this.uuid = uuid;
		if(idUsuarioLlaveCdmx == null) {
			this.usuario = null;
		}else {
			this.usuario = new UsuarioDTO(idUsuarioLlaveCdmx, curp, idPersonaMoral);
		}
		this.catEstatusTramiteDTO = new CatEstatusTramiteDTO(idEstatusTramite, descripcion, descripcionAviso, descripcionPersonalizada);	
		this.fechaCreacion = fechaCreacion;
		this.fechaRevision = fechaRevision;
		this.rutaDocumentoPrevencion = rutaDocumentoPrevencion;
		this.rutaDocumentoRevocado = rutaDocumentoRevocado;
		this.rutaDocumentoResolucionPositiva = rutaDocumentoResolucionPositiva;
		this.rutaDocumentoResolucionNegativa = rutaDocumentoResolucionNegativa;
	}

	/**
	 * @return the idTramite
	 */
	public Long getIdTramite() {
		return idTramite;
	}

	/**
	 * @param idTramite the idTramite to set
	 */
	public void setIdTramite(Long idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * @return the folioSeguimiento
	 */
	public String getFolioSeguimiento() {
		return folioSeguimiento;
	}

	/**
	 * @param folioSeguimiento the folioSeguimiento to set
	 */
	public void setFolioSeguimiento(String folioSeguimiento) {
		this.folioSeguimiento = folioSeguimiento;
	}

	/**
	 * @return the usuario
	 */
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the catEstatusTramiteDTO
	 */
	public CatEstatusTramiteDTO getCatEstatusTramiteDTO() {
		return catEstatusTramiteDTO;
	}

	/**
	 * @param catEstatusTramiteDTO the catEstatusTramiteDTO to set
	 */
	public void setCatEstatusTramiteDTO(CatEstatusTramiteDTO catEstatusTramiteDTO) {
		this.catEstatusTramiteDTO = catEstatusTramiteDTO;
	}

	/**
	 * @return the usuarioRevisor
	 */
	public UsuarioDTO getUsuarioRevisor() {
		return usuarioRevisor;
	}

	/**
	 * @param usuarioRevisor the usuarioRevisor to set
	 */
	public void setUsuarioRevisor(UsuarioDTO usuarioRevisor) {
		this.usuarioRevisor = usuarioRevisor;
	}

	public String getRespuestaFolioPrevencion() {
		return respuestaFolioPrevencion;
	}

	public void setRespuestaFolioPrevencion(String respuestaFolioPrevencion) {
		this.respuestaFolioPrevencion = respuestaFolioPrevencion;
	}

	public String getRespuestaFolioConclusion() {
		return respuestaFolioConclusion;
	}

	public void setRespuestaFolioConclusion(String respuestaFolioConclusion) {
		this.respuestaFolioConclusion = respuestaFolioConclusion;
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
	 * @return the estiloEstatus
	 */
	public String getEstiloEstatus() {
		return estiloEstatus;
	}

	/**
	 * @param estiloEstatus the estiloEstatus to set
	 */
	public void setEstiloEstatus(String estiloEstatus) {
		this.estiloEstatus = estiloEstatus;
	}

	/**
	 * @return the estiloTxtEstatus
	 */
	public String getEstiloTxtEstatus() {
		return estiloTxtEstatus;
	}

	/**
	 * @param estiloTxtEstatus the estiloTxtEstatus to set
	 */
	public void setEstiloTxtEstatus(String estiloTxtEstatus) {
		this.estiloTxtEstatus = estiloTxtEstatus;
	}

	/**
	 * @return the fechaDesde
	 */
	public Date getFechaDesde() {
		return fechaDesde;
	}

	/**
	 * @param fechaDesde the fechaDesde to set
	 */
	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	/**
	 * @return the fechaHasta
	 */
	public Date getFechaHasta() {
		return fechaHasta;
	}

	/**
	 * @param fechaHasta the fechaHasta to set
	 */
	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Date getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getRutaDocumentoPrevencion() {
		return rutaDocumentoPrevencion;
	}

	public void setRutaDocumentoPrevencion(String rutaDocumentoPrevencion) {
		this.rutaDocumentoPrevencion = rutaDocumentoPrevencion;
	}

	public UsuarioDTO getUsuarioOperador() {
		return usuarioOperador;
	}

	public void setUsuarioOperador(UsuarioDTO usuarioOperador) {
		this.usuarioOperador = usuarioOperador;
	}

	/**
	 * @return the tramiteFirmado
	 */
	public boolean isTramiteFirmado() {
		return tramiteFirmado;
	}

	/**
	 * @param tramiteFirmado the tramiteFirmado to set
	 */
	public void setTramiteFirmado(boolean tramiteFirmado) {
		this.tramiteFirmado = tramiteFirmado;
	}

	public String getRutaDocumentoRevocado() {
		return rutaDocumentoRevocado;
	}

	public void setRutaDocumentoRevocado(String rutaDocumentoRevocado) {
		this.rutaDocumentoRevocado = rutaDocumentoRevocado;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	
	public String getRutaDocumentoResolucionPositiva() {
		return rutaDocumentoResolucionPositiva;
	}

	public void setRutaDocumentoResolucionPositiva(String rutaDocumentoResolucionPositiva) {
		this.rutaDocumentoResolucionPositiva = rutaDocumentoResolucionPositiva;
	}

	public String getRutaDocumentoResolucionNegativa() {
		return rutaDocumentoResolucionNegativa;
	}

	public void setRutaDocumentoResolucionNegativa(String rutaDocumentoResolucionNegativa) {
		this.rutaDocumentoResolucionNegativa = rutaDocumentoResolucionNegativa;
	}

	public String getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(String tipoPersona) {
		this.tipoPersona = tipoPersona;
	}
	
	public LineaCapturaDTO getLineaCapturaDTO() {
		return lineaCapturaDTO;
	}

	public void setLineaCapturaDTO(LineaCapturaDTO lineaCapturaDTO) {
		this.lineaCapturaDTO = lineaCapturaDTO;
	}
	
	public String getEstiloLCEstatus() {
		return estiloLCEstatus;
	}

	public void setEstiloLCEstatus(String estiloLCEstatus) {
		this.estiloLCEstatus = estiloLCEstatus;
	}

	public String getEstiloLCTxtEstatus() {
		return estiloLCTxtEstatus;
	}

	public void setEstiloLCTxtEstatus(String estiloLCTxtEstatus) {
		this.estiloLCTxtEstatus = estiloLCTxtEstatus;
	}

	@Override
	public String toString() {
	    return "TramiteDTO{" +
	            "idTramite=" + idTramite +
	            ", folioSeguimiento='" + folioSeguimiento + '\'' +
	            ", usuario=" + usuario +
	            ", catEstatusTramiteDTO=" + catEstatusTramiteDTO +
	            ", usuarioRevisor=" + usuarioRevisor +
	            ", respuestaFolioPrevencion='" + respuestaFolioPrevencion + '\'' +
	            ", respuestaFolioConclusion='" + respuestaFolioConclusion + '\'' +
	            ", fechaCreacion=" + fechaCreacion +
	            ", proyectoDTO=" + proyectoDTO +
	            ", fechaRevision=" + fechaRevision +
	            ", uuid='" + uuid + '\'' +
	            ", rutaDocumentoPrevencion='" + rutaDocumentoPrevencion + '\'' +
	            ", rutaDocumentoRevocado='" + rutaDocumentoRevocado + '\'' +
	            ", rutaDocumentoResolucionPositiva='" + rutaDocumentoResolucionPositiva + '\'' +
	            ", rutaDocumentoResolucionNegativa='" + rutaDocumentoResolucionNegativa + '\'' +
	            ", motivoRechazo='" + motivoRechazo + '\'' +
	            ", tipoPersona='" + tipoPersona + '\'' +
	            ", estiloEstatus='" + estiloEstatus + '\'' +
	            ", estiloTxtEstatus='" + estiloTxtEstatus + '\'' +
	            ", fechaDesde=" + fechaDesde +
	            ", fechaHasta=" + fechaHasta +
	            ", usuarioOperador=" + usuarioOperador +
	            ", tramiteFirmado=" + tramiteFirmado +
	            ", lineaCapturaDTO=" + lineaCapturaDTO +
	            ", estiloLCEstatus=" + estiloLCEstatus +
	            ", estiloLCTxtEstatus=" + estiloLCTxtEstatus +
	            '}';
	}
}

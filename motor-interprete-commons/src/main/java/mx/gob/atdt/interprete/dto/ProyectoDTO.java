package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class ProyectoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4861915165489787017L;

	private Long idProyecto;
	private CatDependenciaDTO catDependenciaDTO;
	private CatEstatusProyectoDTO catEstatusProyectoDTO;
	private CatTipoProyectoDTO catTipoProyectoDTO;
	private UsuarioDTO usuarioDTO;
	private String nombreProyecto;
	private String acronimo;
	private boolean habilitaCaptcha;
	private boolean habilitaAccesoLlave;
	private boolean habilitaPagoLinea;
	private boolean habilitaGestionUsuarios;
	private boolean habilitaFirmaDigital;
	private boolean habilitaDetalleLegales;
	private boolean habilitaAnalytics;
	private boolean habilitaSecurityDomain;
	private boolean habilitaSecurityDomainCurp;
	private boolean habilitaDistribucion;
	private boolean aviso;
	private Date fechaCreacion;
	private boolean habilitaCapturaTramites;
	private boolean habilitaConfiguracionCatalogos;
	private boolean habilitaNotificacionesWebhook;
	private boolean habilitaRevocacionAviso;

	// Variables para identificar secciones capturadas
	private boolean seccionHomeCompleta;	
	private boolean seccionCaptchaCompleta;
	private boolean seccionLlaveCompleta;
	private boolean seccionFormulariosCompleta;
	private boolean seccionPagoCompleta;
	private boolean seccionUsuariosCompleta;
	private boolean seccionFirmaCompleta;
	private boolean seccionRespuestaCompleta;
	private boolean seccionLegalesCompleta;
	private boolean seccionAnalyticsCompleta;
	private boolean seccionSecurityDomainCompleta;
	private boolean seccionSecurityDomainCurpCompleta;
	private boolean seccionDistribucionCompleta;
	private boolean seccionConfiguracionCatalogosCompleta;
	private boolean seccionConfiguracionWeebhookCompleta;
	
	private String estiloEstatus;
	private String estiloTxtEstatus;
	
	/*atributo relacionado a la completez de un proyecto con linea de captura configurado */
	private boolean proyectoLineaCaptura;

	/**
	 * 
	 */
	public ProyectoDTO() {
		catDependenciaDTO = new CatDependenciaDTO();
		catEstatusProyectoDTO = new CatEstatusProyectoDTO();
		catTipoProyectoDTO = new CatTipoProyectoDTO();
		usuarioDTO = new UsuarioDTO();
	}
	
	/**
	 * @param idProyecto
	 * @param nombreProyecto
	 */
	public ProyectoDTO(Long idProyecto, String nombreProyecto) {
		this.idProyecto = idProyecto;
		this.nombreProyecto = nombreProyecto;
	}

	/**
	 * Constructor utilizado por la NamedQuery Proyecto.findAllProyectos
	 * 
	 * @param idProyecto
	 */
	public ProyectoDTO(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	/**
	 * @param idProyecto
	 * @param catDependenciaDTO
	 * @param catEstatusProyectoDTO
	 * @param catTipoProyectoDTO
	 * @param usuarioDTO
	 * @param nombreProyecto
	 * @param habilitaCaptcha
	 * @param habilitaAccesoLlave
	 * @param habilitaPagoLinea
	 * @param habilitaGestionUsuarios
	 * @param habilitaFirmaDigital
	 * @param habilitaDetalleLegales
	 * @param habilitaAnalytics
	 * @param habilitaSecurityDomain
	 * @param fechaCreacion
	 * @param habilitaCapturaTramites;
	 * @param habilitaConfiguracionCatalogos
	 * @param habilitaRevocacionAviso 
	 */
	public ProyectoDTO(Long idProyecto, CatDependenciaDTO catDependenciaDTO,
			CatEstatusProyectoDTO catEstatusProyectoDTO, CatTipoProyectoDTO catTipoProyectoDTO, UsuarioDTO usuarioDTO,
			String nombreProyecto, boolean habilitaCaptcha, boolean habilitaAccesoLlave, boolean habilitaPagoLinea, boolean habilitaGestionUsuarios,
			boolean habilitaFirmaDigital, boolean habilitaDetalleLegales, boolean habilitaAnalytics,
			boolean habilitaSecurityDomain, Date fechaCreacion, boolean habilitaCapturaTramites, 
			boolean habilitaConfiguracionCatalogos, boolean habilitaRevocacionAviso) {
		this.idProyecto = idProyecto;
		this.catDependenciaDTO = catDependenciaDTO;
		this.catEstatusProyectoDTO = catEstatusProyectoDTO;
		this.catTipoProyectoDTO = catTipoProyectoDTO;
		this.usuarioDTO = usuarioDTO;
		this.nombreProyecto = nombreProyecto;
		this.habilitaCaptcha = habilitaCaptcha;
		this.habilitaAccesoLlave = habilitaAccesoLlave;
		this.habilitaPagoLinea = habilitaPagoLinea;
		this.habilitaGestionUsuarios = habilitaGestionUsuarios;
		this.habilitaFirmaDigital = habilitaFirmaDigital;
		this.habilitaDetalleLegales = habilitaDetalleLegales;
		this.habilitaAnalytics = habilitaAnalytics;
		this.habilitaSecurityDomain = habilitaSecurityDomain;
		this.fechaCreacion = fechaCreacion;
		this.habilitaCapturaTramites = habilitaCapturaTramites;
		this.habilitaConfiguracionCatalogos = habilitaConfiguracionCatalogos;
		this.habilitaRevocacionAviso = habilitaRevocacionAviso;
	}

	/**
	 * Constructor utilizado por la NamedQuery Proyecto.findByIdProyecto
	 * 
	 * @param idProyecto
	 * @param idDependencia
	 * @param descripcionDependencia
	 * @param idEstatusProyecto
	 * @param descripcionEstatus
	 * @param idTipoProyecto
	 * @param descripcionTipo
	 * @param idUsuarioLlaveCdmx
	 * @param nombreProyecto
	 * @param acronimo
	 * @param habilitaCaptcha
	 * @param habilitaAccesoLlave
	 * @param habilitaPagoLinea
	 * @param habilitaGestionUsuarios
	 * @param habilitaFirmaDigital
	 * @param habilitaDetalleLegales
	 * @param habilitaAnalytics
	 * @param habilitaSecurityDomain
	 * @param habilitaSecurityDomainCurp
	 * @param habilitaDistribucion
	 * @param aviso
	 * @param fechaCreacion
	 * @param habilitaCapturaTramites
	 * @param habilitaConfiguracionCatalogos
	 * @param habilitaRevocacionAviso
	 */
	public ProyectoDTO(Long idProyecto, Integer idDependencia, String descripcionDependencia, Integer idEstatusProyecto,
			String descripcionEstatus, Integer idTipoProyecto, String descripcionTipo, long idUsuarioLlaveCdmx,
			String nombreProyecto, String acronimo, boolean habilitaCaptcha,
			boolean habilitaAccesoLlave, boolean habilitaPagoLinea, boolean habilitaGestionUsuarios,
			boolean habilitaFirmaDigital, boolean habilitaDetalleLegales, boolean habilitaAnalytics,
			boolean habilitaSecurityDomain, boolean habilitaSecurityDomainCurp, boolean habilitaDistribucion, boolean aviso, 
			Date fechaCreacion, boolean habilitaCapturaTramites, boolean habilitaConfiguracionCatalogos,
			boolean habilitaRevocacionAviso) {
		this.idProyecto = idProyecto;
		this.catDependenciaDTO = new CatDependenciaDTO(idDependencia, descripcionDependencia);
		this.catEstatusProyectoDTO = new CatEstatusProyectoDTO(idEstatusProyecto, descripcionEstatus);
		this.catTipoProyectoDTO = new CatTipoProyectoDTO(idTipoProyecto, descripcionTipo);
		this.usuarioDTO = new UsuarioDTO(idUsuarioLlaveCdmx);
		this.nombreProyecto = nombreProyecto;
		this.acronimo = acronimo;
		this.habilitaCaptcha = habilitaCaptcha;
		this.habilitaAccesoLlave = habilitaAccesoLlave;
		this.habilitaPagoLinea = habilitaPagoLinea;
		this.habilitaGestionUsuarios = habilitaGestionUsuarios;
		this.habilitaFirmaDigital = habilitaFirmaDigital;
		this.habilitaDetalleLegales = habilitaDetalleLegales;
		this.habilitaAnalytics = habilitaAnalytics;
		this.habilitaSecurityDomain = habilitaSecurityDomain;
		this.habilitaSecurityDomainCurp = habilitaSecurityDomainCurp;
		this.habilitaDistribucion = habilitaDistribucion; 
		this.aviso = aviso;
		this.fechaCreacion = fechaCreacion;
		this.habilitaCapturaTramites = habilitaCapturaTramites;
		this.habilitaConfiguracionCatalogos = habilitaConfiguracionCatalogos;
		this.habilitaRevocacionAviso = habilitaRevocacionAviso;
	}

	/**
	 * Constructor utilizado por la consulta buscarPorCriterios de ProyectoDAO, y
	 * buscarProyectosPorIdUsuario
	 * 
	 * @param idProyecto
	 * @param idDependencia
	 * @param descripcionDependencia
	 * @param idEstatusProyecto
	 * @param descripcionEstatus
	 * @param nombreProyecto
	 * @param fechaCreacion
	 */
	public ProyectoDTO(Long idProyecto, Integer idDependencia, String descripcionDependencia, Integer idEstatusProyecto,
			String descripcionEstatus, String nombreProyecto, Date fechaCreacion) {
		this.idProyecto = idProyecto;
		this.catDependenciaDTO = new CatDependenciaDTO(idDependencia, descripcionDependencia);
		this.catEstatusProyectoDTO = new CatEstatusProyectoDTO(idEstatusProyecto, descripcionEstatus);
		this.nombreProyecto = nombreProyecto;
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Constructor utilizado por la NamedQyery
	 * Proyecto.findProyectoByEstatusEditable
	 * 
	 * @param idProyecto
	 * @param idEstatusProyecto
	 * @param descripcionEstatus
	 */
	public ProyectoDTO(Long idProyecto, Integer idEstatusProyecto, String descripcionEstatus) {
		this.idProyecto = idProyecto;
		this.catEstatusProyectoDTO = new CatEstatusProyectoDTO(idEstatusProyecto, descripcionEstatus);
	}

	/**
	 * @return the idProyecto
	 */
	public Long getIdProyecto() {
		return idProyecto;
	}

	/**
	 * @param idProyecto the idProyecto to set
	 */
	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	/**
	 * @return the catDependenciaDTO
	 */
	public CatDependenciaDTO getCatDependenciaDTO() {
		return catDependenciaDTO;
	}

	/**
	 * @param catDependenciaDTO the catDependenciaDTO to set
	 */
	public void setCatDependenciaDTO(CatDependenciaDTO catDependenciaDTO) {
		this.catDependenciaDTO = catDependenciaDTO;
	}

	/**
	 * @return the catEstatusProyectoDTO
	 */
	public CatEstatusProyectoDTO getCatEstatusProyectoDTO() {
		return catEstatusProyectoDTO;
	}

	/**
	 * @param catEstatusProyectoDTO the catEstatusProyectoDTO to set
	 */
	public void setCatEstatusProyectoDTO(CatEstatusProyectoDTO catEstatusProyectoDTO) {
		this.catEstatusProyectoDTO = catEstatusProyectoDTO;
	}

	/**
	 * @return the catTipoProyectoDTO
	 */
	public CatTipoProyectoDTO getCatTipoProyectoDTO() {
		return catTipoProyectoDTO;
	}

	/**
	 * @param catTipoProyectoDTO the catTipoProyectoDTO to set
	 */
	public void setCatTipoProyectoDTO(CatTipoProyectoDTO catTipoProyectoDTO) {
		this.catTipoProyectoDTO = catTipoProyectoDTO;
	}

	/**
	 * @return the usuarioDTO
	 */
	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	/**
	 * @param usuarioDTO the usuarioDTO to set
	 */
	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	/**
	 * @return the nombreProyecto
	 */
	public String getNombreProyecto() {
		return nombreProyecto;
	}

	/**
	 * @param nombreProyecto the nombreProyecto to set
	 */
	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}
	
	/**
	 * @return the acronimo
	 */
	public String getAcronimo() {
		return acronimo;
	}

	/**
	 * @param acronimo the acronimo to set
	 */
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}		

	/**
	 * @return the habilitaCaptcha
	 */
	public boolean isHabilitaCaptcha() {
		return habilitaCaptcha;
	}

	/**
	 * @param habilitaCaptcha the habilitaCaptcha to set
	 */
	public void setHabilitaCaptcha(boolean habilitaCaptcha) {
		this.habilitaCaptcha = habilitaCaptcha;
	}
	
	/**
	 * @return the habilitaAccesoLlave
	 */
	public boolean isHabilitaAccesoLlave() {
		return habilitaAccesoLlave;
	}

	/**
	 * @param habilitaAccesoLlave the habilitaAccesoLlave to set
	 */
	public void setHabilitaAccesoLlave(boolean habilitaAccesoLlave) {
		this.habilitaAccesoLlave = habilitaAccesoLlave;
	}

	/**
	 * @return the habilitaPagoLinea
	 */
	public boolean isHabilitaPagoLinea() {
		return habilitaPagoLinea;
	}

	/**
	 * @param habilitaPagoLinea the habilitaPagoLinea to set
	 */
	public void setHabilitaPagoLinea(boolean habilitaPagoLinea) {
		this.habilitaPagoLinea = habilitaPagoLinea;
	}

	/**
	 * @return the habilitaGestionUsuarios
	 */
	public boolean isHabilitaGestionUsuarios() {
		return habilitaGestionUsuarios;
	}

	/**
	 * @param habilitaGestionUsuarios the habilitaGestionUsuarios to set
	 */
	public void setHabilitaGestionUsuarios(boolean habilitaGestionUsuarios) {
		this.habilitaGestionUsuarios = habilitaGestionUsuarios;
	}

	/**
	 * @return the habilitaFirmaDigital
	 */
	public boolean isHabilitaFirmaDigital() {
		return habilitaFirmaDigital;
	}

	/**
	 * @param habilitaFirmaDigital the habilitaFirmaDigital to set
	 */
	public void setHabilitaFirmaDigital(boolean habilitaFirmaDigital) {
		this.habilitaFirmaDigital = habilitaFirmaDigital;
	}

	/**
	 * @return the habilitaDetalleLegales
	 */
	public boolean isHabilitaDetalleLegales() {
		return habilitaDetalleLegales;
	}

	/**
	 * @param habilitaDetalleLegales the habilitaDetalleLegales to set
	 */
	public void setHabilitaDetalleLegales(boolean habilitaDetalleLegales) {
		this.habilitaDetalleLegales = habilitaDetalleLegales;
	}

	/**
	 * @return the habilitaAnalytics
	 */
	public boolean isHabilitaAnalytics() {
		return habilitaAnalytics;
	}

	/**
	 * @param habilitaAnalytics the habilitaAnalytics to set
	 */
	public void setHabilitaAnalytics(boolean habilitaAnalytics) {
		this.habilitaAnalytics = habilitaAnalytics;
	}

	/**
	 * @return the habilitaSecurityDomain
	 */
	public boolean isHabilitaSecurityDomain() {
		return habilitaSecurityDomain;
	}

	/**
	 * @param habilitaSecurityDomain the habilitaSecurityDomain to set
	 */
	public void setHabilitaSecurityDomain(boolean habilitaSecurityDomain) {
		this.habilitaSecurityDomain = habilitaSecurityDomain;
	}
	
	/**
	 * @return the habilitaSecurityDomainCurp
	 */
	public boolean isHabilitaSecurityDomainCurp() {
		return habilitaSecurityDomainCurp;
	}

	/**
	 * @param habilitaSecurityDomainCurp the habilitaSecurityDomainCurp to set
	 */
	public void setHabilitaSecurityDomainCurp(boolean habilitaSecurityDomainCurp) {
		this.habilitaSecurityDomainCurp = habilitaSecurityDomainCurp;
	}
	
	/**
	 * @return the habilitaDistribucion
	 */
	public boolean isHabilitaDistribucion() {
		return habilitaDistribucion;
	}

	/**
	 * @param habilitaDistribucion the habilitaDistribucion to set
	 */
	public void setHabilitaDistribucion(boolean habilitaDistribucion) {
		this.habilitaDistribucion = habilitaDistribucion;
	}	
	
	/**
	 * @return the aviso
	 */
	public boolean isAviso() {
		return aviso;
	}

	/**
	 * @param aviso the aviso to set
	 */
	public void setAviso(boolean aviso) {
		this.aviso = aviso;
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
	 * @return the habilitaCapturaTramites
	 */
	public boolean isHabilitaCapturaTramites() {
		return habilitaCapturaTramites;
	}

	/**
	 * @param habilitaCapturaTramites the habilitaCapturaTramites to set
	 */
	public void setHabilitaCapturaTramites(boolean habilitaCapturaTramites) {
		this.habilitaCapturaTramites = habilitaCapturaTramites;
	}
	
	public boolean isHabilitaConfiguracionCatalogos() {
	    return habilitaConfiguracionCatalogos;
	}

	public void setHabilitaConfiguracionCatalogos(boolean habilitaConfiguracionCatalogos) {
	    this.habilitaConfiguracionCatalogos = habilitaConfiguracionCatalogos;
	}
	
	/**
	 * @return the habilitaNotificacionesWebhook
	 */
	public boolean isHabilitaNotificacionesWebhook() {
		return habilitaNotificacionesWebhook;
	}

	/**
	 * @param habilitaNotificacionesWebhook the habilitaNotificacionesWebhook to set
	 */
	public void setHabilitaNotificacionesWebhook(boolean habilitaNotificacionesWebhook) {
		this.habilitaNotificacionesWebhook = habilitaNotificacionesWebhook;
	}

	public boolean isHabilitaRevocacionAviso() {
		return habilitaRevocacionAviso;
	}

	public void setHabilitaRevocacionAviso(boolean habilitaRevocacionAviso) {
		this.habilitaRevocacionAviso = habilitaRevocacionAviso;
	}

	/**
	 * @return the seccionHomeCompleta
	 */
	public boolean isSeccionHomeCompleta() {
		return seccionHomeCompleta;
	}

	/**
	 * @param seccionHomeCompleta the seccionHomeCompleta to set
	 */
	public void setSeccionHomeCompleta(boolean seccionHomeCompleta) {
		this.seccionHomeCompleta = seccionHomeCompleta;
	}
	
	/**
	 * @return the seccionCaptchaCompleta
	 */
	public boolean isSeccionCaptchaCompleta() {
		return seccionCaptchaCompleta;
	}

	/**
	 * @param seccionCaptchaCompleta the seccionCaptchaCompleta to set
	 */
	public void setSeccionCaptchaCompleta(boolean seccionCaptchaCompleta) {
		this.seccionCaptchaCompleta = seccionCaptchaCompleta;
	}

	/**
	 * @return the seccionLlaveCompleta
	 */
	public boolean isSeccionLlaveCompleta() {
		return seccionLlaveCompleta;
	}

	/**
	 * @param seccionLlaveCompleta the seccionLlaveCompleta to set
	 */
	public void setSeccionLlaveCompleta(boolean seccionLlaveCompleta) {
		this.seccionLlaveCompleta = seccionLlaveCompleta;
	}

	/**
	 * @return the seccionFormulariosCompleta
	 */
	public boolean isSeccionFormulariosCompleta() {
		return seccionFormulariosCompleta;
	}

	/**
	 * @param seccionFormulariosCompleta the seccionFormulariosCompleta to set
	 */
	public void setSeccionFormulariosCompleta(boolean seccionFormulariosCompleta) {
		this.seccionFormulariosCompleta = seccionFormulariosCompleta;
	}

	/**
	 * @return the seccionPagoCompleta
	 */
	public boolean isSeccionPagoCompleta() {
		return seccionPagoCompleta;
	}

	/**
	 * @param seccionPagoCompleta the seccionPagoCompleta to set
	 */
	public void setSeccionPagoCompleta(boolean seccionPagoCompleta) {
		this.seccionPagoCompleta = seccionPagoCompleta;
	}

	/**
	 * @return the seccionUsuariosCompleta
	 */
	public boolean isSeccionUsuariosCompleta() {
		return seccionUsuariosCompleta;
	}

	/**
	 * @param seccionUsuariosCompleta the seccionUsuariosCompleta to set
	 */
	public void setSeccionUsuariosCompleta(boolean seccionUsuariosCompleta) {
		this.seccionUsuariosCompleta = seccionUsuariosCompleta;
	}

	/**
	 * @return the seccionFirmaCompleta
	 */
	public boolean isSeccionFirmaCompleta() {
		return seccionFirmaCompleta;
	}

	/**
	 * @param seccionFirmaCompleta the seccionFirmaCompleta to set
	 */
	public void setSeccionFirmaCompleta(boolean seccionFirmaCompleta) {
		this.seccionFirmaCompleta = seccionFirmaCompleta;
	}

	/**
	 * @return the seccionRespuestaCompleta
	 */
	public boolean isSeccionRespuestaCompleta() {
		return seccionRespuestaCompleta;
	}

	/**
	 * @param seccionRespuestaCompleta the seccionRespuestaCompleta to set
	 */
	public void setSeccionRespuestaCompleta(boolean seccionRespuestaCompleta) {
		this.seccionRespuestaCompleta = seccionRespuestaCompleta;
	}

	/**
	 * @return the seccionLegalesCompleta
	 */
	public boolean isSeccionLegalesCompleta() {
		return seccionLegalesCompleta;
	}

	/**
	 * @param seccionLegalesCompleta the seccionLegalesCompleta to set
	 */
	public void setSeccionLegalesCompleta(boolean seccionLegalesCompleta) {
		this.seccionLegalesCompleta = seccionLegalesCompleta;
	}

	/**
	 * @return the seccionAnalyticsCompleta
	 */
	public boolean isSeccionAnalyticsCompleta() {
		return seccionAnalyticsCompleta;
	}

	/**
	 * @param seccionAnalyticsCompleta the seccionAnalyticsCompleta to set
	 */
	public void setSeccionAnalyticsCompleta(boolean seccionAnalyticsCompleta) {
		this.seccionAnalyticsCompleta = seccionAnalyticsCompleta;
	}

	/**
	 * @return the seccionSecurityDomain
	 */
	public boolean isSeccionSecurityDomainCompleta() {
		return seccionSecurityDomainCompleta;
	}

	/**
	 * @param seccionSecurityDomain the seccionSecurityDomain to set
	 */
	public void setSeccionSecurityDomainCompleta(boolean seccionSecurityDomainCompleta) {
		this.seccionSecurityDomainCompleta = seccionSecurityDomainCompleta;
	}
	
	/**
	 * @return the seccionSecurityDomainCurpCompleta
	 */
	public boolean isSeccionSecurityDomainCurpCompleta() {
		return seccionSecurityDomainCurpCompleta;
	}

	/**
	 * @param seccionSecurityDomainCurpCompleta the seccionSecurityDomainCurpCompleta to set
	 */
	public void setSeccionSecurityDomainCurpCompleta(boolean seccionSecurityDomainCurpCompleta) {
		this.seccionSecurityDomainCurpCompleta = seccionSecurityDomainCurpCompleta;
	}
	
	public boolean isSeccionConfiguracionCatalogosCompleta() {
	    return seccionConfiguracionCatalogosCompleta;
	}

	public void setSeccionConfiguracionCatalogosCompleta(boolean seccionConfiguracionCatalogosCompleta) {
	    this.seccionConfiguracionCatalogosCompleta = seccionConfiguracionCatalogosCompleta;
	}
	
	/**
	 * @return the seccionConfiguracionWeebhookCompleta
	 */
	public boolean isSeccionConfiguracionWeebhookCompleta() {
		return seccionConfiguracionWeebhookCompleta;
	}

	/**
	 * @param seccionConfiguracionWeebhookCompleta the seccionConfiguracionWeebhookCompleta to set
	 */
	public void setSeccionConfiguracionWeebhookCompleta(boolean seccionConfiguracionWeebhookCompleta) {
		this.seccionConfiguracionWeebhookCompleta = seccionConfiguracionWeebhookCompleta;
	}
	
	/**
	 * @return the seccionDistribucionCompleta
	 */
	public boolean isSeccionDistribucionCompleta() {
		return seccionDistribucionCompleta;
	}

	/**
	 * @param seccionDistribucionCompleta the seccionDistribucionCompleta to set
	 */
	public void setSeccionDistribucionCompleta(boolean seccionDistribucionCompleta) {
		this.seccionDistribucionCompleta = seccionDistribucionCompleta;
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

	public boolean isProyectoLineaCaptura() {
		return proyectoLineaCaptura;
	}

	public void setProyectoLineaCaptura(boolean proyectoLineaCaptura) {
		this.proyectoLineaCaptura = proyectoLineaCaptura;
	}

	@Override
	public String toString() {
		return "ProyectoDTO [idProyecto=" + idProyecto + ", catDependenciaDTO=" + catDependenciaDTO
				+ ", catEstatusProyectoDTO=" + catEstatusProyectoDTO + ", catTipoProyectoDTO=" + catTipoProyectoDTO
				+ ", usuarioDTO=" + usuarioDTO + ", nombreProyecto=" + nombreProyecto + ", habilitaCaptcha=" 
				+ habilitaCaptcha + ", habilitaAccesoLlave=" + habilitaAccesoLlave + ", habilitaPagoLinea="
				+ habilitaPagoLinea + ", habilitaGestionUsuarios=" + habilitaGestionUsuarios + ", habilitaFirmaDigital="
				+ habilitaFirmaDigital + ", habilitaDetalleLegales=" + habilitaDetalleLegales + ", habilitaAnalytics="
				+ habilitaAnalytics + ", habilitaSecurityDomain=" + habilitaSecurityDomain  
				+ ", fechaCreacion=" + fechaCreacion + ", seccionHomeCompleta=" + seccionHomeCompleta + ", seccionCaptchaCompleta="
				+ seccionCaptchaCompleta + ", seccionLlaveCompleta=" + seccionLlaveCompleta
				+ ", seccionFormulariosCompleta=" + seccionFormulariosCompleta + ", seccionPagoCompleta="
				+ seccionPagoCompleta + ", seccionUsuariosCompleta=" + seccionUsuariosCompleta
				+ ", seccionFirmaCompleta=" + seccionFirmaCompleta + ", seccionRespuestaCompleta="
				+ seccionRespuestaCompleta + ", seccionLegalesCompleta=" + seccionLegalesCompleta
				+ ", seccionAnalyticsCompleta=" + seccionAnalyticsCompleta + ", seccionSecurityDomainCompleta="
				+ seccionSecurityDomainCompleta + ", estiloEstatus=" + estiloEstatus + ", estiloTxtEstatus="
				+ estiloTxtEstatus + ", aviso=" + aviso + ", proyectoLineaCaptura=" + proyectoLineaCaptura +"]";
	}
}

package mx.gob.atdt.interprete.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "proyecto", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "Proyecto.findByIdProyecto", query = "SELECT new mx.gob.atdt.interprete.dto.ProyectoDTO("
				+ " p.idProyecto, d.idDependencia, d.descripcion, e.idEstatusProyecto, e.descripcion, "
				+ " t.idTipoProyecto, t.descripcion, p.idUsuarioLlaveCdmx, p.nombreProyecto, p.acronimo, "
				+ " p.habilitaCaptcha, p.habilitaAccesoLlave, p.habilitaPagoLinea, "
				+ " p.habilitaGestionUsuarios, p.habilitaFirmaDigital, p.habilitaDetalleLegales, p.habilitaAnalytics, p.habilitaSecurityDomain, "
				+ " p.habilitaSecurityDomainCurp, p.habilitaDistribucion, p.aviso, p.fechaCreacion, p.habilitaCapturaTramites, "
				+ " p.habilitaConfiguracionCatalogos, p.habilitaRevocacionAviso )" 
				+ " FROM Proyecto p " 
				+ "		JOIN p.catDependencia d "
				+ "		JOIN p.catEstatusProyecto e " 
				+ "		JOIN p.catTipoProyecto t "
				+ " WHERE p.idProyecto = :idProyecto"),
		@NamedQuery(name = "Proyecto.findProyectoByEstatusEditable", query = "SELECT new mx.gob.atdt.interprete.dto.ProyectoDTO( "
				+ " p.idProyecto, e.idEstatusProyecto, e.descripcion ) " + " FROM Proyecto p "
				+ "	JOIN p.catEstatusProyecto e " + " WHERE p.idProyecto = :idProyecto "
				+ " AND  e.idEstatusProyecto in (2,4) "),
		@NamedQuery(name = "Proyecto.findById", query = "SELECT new mx.gob.atdt.interprete.dto.ProyectoDTO(p.idProyecto) "
				+ " FROM Proyecto p " + " WHERE p.idProyecto = :idProyecto"),
		@NamedQuery(name = "Proyecto.findAllProyectos", query = "SELECT new mx.gob.atdt.interprete.dto.ProyectoDTO(p.idProyecto) "
				+ " FROM Proyecto p ") })
public class Proyecto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4949489942092866847L;

	private Long idProyecto;
	private CatDependencia catDependencia;
	private CatEstatusProyecto catEstatusProyecto;
	private CatTipoProyecto catTipoProyecto;
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
	private boolean habilitaCapturaTramites;
	private boolean habilitaConfiguracionCatalogos;
	private boolean habilitaNotificacionesWebhook;
	private boolean habilitaRevocacionAviso;
	private long idUsuarioLlaveCdmx;
	private Date fechaCreacion;
	private Set<DetCaptcha> detalleCaptchas = new HashSet<DetCaptcha>(0);
	private Set<DetAnalytics> detalleAnalyticses = new HashSet<DetAnalytics>(0);
	private Set<DetHome> detalleHomes = new HashSet<DetHome>(0);
	private Set<BitMovimientosSecciones> bitMovimientosSeccioneses = new HashSet<BitMovimientosSecciones>(0);
	private Set<DetAccesoLlave> detalleAccesoLlaves = new HashSet<DetAccesoLlave>(0);
	private Set<DetFirmaDigital> detalleFirmaDigitals = new HashSet<DetFirmaDigital>(0);
	private Set<DetPago> detallePagos = new HashSet<DetPago>(0);
	private Set<DetGestionUsuario> detalleGestionUsuarios = new HashSet<DetGestionUsuario>(0);
	private Set<DetLegales> detalleLegaleses = new HashSet<DetLegales>(0);
	private Set<DetPausasProyecto> detPausasProyectos = new HashSet<DetPausasProyecto>(0);
	private Set<DetSecurityDomain> detSecurityDomains = new HashSet<DetSecurityDomain>(0);
	private Set<ArchivosRespuestaToken> archivosRespuestaTokens = new HashSet<ArchivosRespuestaToken>(0);
	private Set<SeccionesFormulario> seccionesFormularios = new HashSet<SeccionesFormulario>(0);
	private Set<DetDistribucion> detDistribucions = new HashSet<DetDistribucion>(0);
	private Set<ConfiguracionCatalogo> configuracionCatalogos = new HashSet<ConfiguracionCatalogo>(0);

	public Proyecto() {
	}

	public Proyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public Proyecto(Long idProyecto, CatEstatusProyecto catEstatusProyecto, CatTipoProyecto catTipoProyecto,
			String nombreProyecto, boolean habilitaCaptcha, boolean habilitaAccesoLlave, boolean habilitaPagoLinea, 
			boolean habilitaGestionUsuarios, boolean habilitaFirmaDigital, boolean habilitaDetalleLegales, 
			boolean habilitaAnalytics, boolean habilitaSecurityDomain, long idUsuarioLlaveCdmx, Date fechaCreacion, boolean habilitaCapturaTramites,
			boolean habilitaConfiguracionCatalogos, boolean habilitaRevocacionAviso) {
		this.idProyecto = idProyecto;
		this.catEstatusProyecto = catEstatusProyecto;
		this.catTipoProyecto = catTipoProyecto;
		this.nombreProyecto = nombreProyecto;
		this.habilitaCaptcha = habilitaCaptcha;
		this.habilitaAccesoLlave = habilitaAccesoLlave;
		this.habilitaPagoLinea = habilitaPagoLinea;
		this.habilitaGestionUsuarios = habilitaGestionUsuarios;
		this.habilitaFirmaDigital = habilitaFirmaDigital;
		this.habilitaDetalleLegales = habilitaDetalleLegales;
		this.habilitaAnalytics = habilitaAnalytics;
		this.habilitaSecurityDomain = habilitaSecurityDomain;
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
		this.fechaCreacion = fechaCreacion;
		this.habilitaCapturaTramites = habilitaCapturaTramites;
		this.habilitaConfiguracionCatalogos = habilitaConfiguracionCatalogos;
		this.habilitaRevocacionAviso = habilitaRevocacionAviso;
	}

	public Proyecto(long idProyecto, CatDependencia catDependencia, CatEstatusProyecto catEstatusProyecto,
			CatTipoProyecto catTipoProyecto, String nombreProyecto, 
			boolean habilitaCaptcha, boolean habilitaAccesoLlave, boolean habilitaPagoLinea,
			boolean habilitaGestionUsuarios, boolean habilitaFirmaDigital, boolean habilitaDetalleLegales,
			boolean habilitaAnalytics, boolean habilitaSecurityDomain, long idUsuarioLlaveCdmx, Date fechaCreacion, boolean habilitaCapturaTramites,
			Set<DetCaptcha> detCaptchas, Set<DetAnalytics> detAnalyticses,
			Set<BitMovimientosSecciones> bitMovimientosSeccioneses, Set<DetAccesoLlave> detAccesoLlaves,
			Set<DetPausasProyecto> detPausasProyectos, Set<DetSecurityDomain> detSecurityDomains,
			Set<DetFirmaDigital> detFirmaDigitals, Set<ArchivosRespuestaToken> archivosRespuestaTokens,
			Set<DetHome> detHomes, Set<SeccionesFormulario> seccionesFormularios, Set<DetPago> detPagos,
			Set<DetGestionUsuario> detGestionUsuarios, Set<DetLegales> detLegaleses, 
			Set<ConfiguracionCatalogo> configuracionCatalogos, boolean habilitaRevocacionAviso) {
		this.idProyecto = idProyecto;
		this.catDependencia = catDependencia;
		this.catEstatusProyecto = catEstatusProyecto;
		this.catTipoProyecto = catTipoProyecto;
		this.nombreProyecto = nombreProyecto;
		this.habilitaCaptcha = habilitaCaptcha;
		this.habilitaAccesoLlave = habilitaAccesoLlave;
		this.habilitaPagoLinea = habilitaPagoLinea;
		this.habilitaGestionUsuarios = habilitaGestionUsuarios;
		this.habilitaFirmaDigital = habilitaFirmaDigital;
		this.habilitaDetalleLegales = habilitaDetalleLegales;
		this.habilitaAnalytics = habilitaAnalytics;
		this.habilitaSecurityDomain = habilitaSecurityDomain;
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
		this.fechaCreacion = fechaCreacion;
		this.habilitaCapturaTramites = habilitaCapturaTramites;
		this.detalleCaptchas = detCaptchas;
		this.detalleAnalyticses = detAnalyticses;
		this.bitMovimientosSeccioneses = bitMovimientosSeccioneses;
		this.detalleAccesoLlaves = detAccesoLlaves;
		this.detPausasProyectos = detPausasProyectos;
		this.detSecurityDomains = detSecurityDomains;
		this.detalleFirmaDigitals = detFirmaDigitals;
		this.archivosRespuestaTokens = archivosRespuestaTokens;
		this.detalleHomes = detHomes;
		this.seccionesFormularios = seccionesFormularios;
		this.detallePagos = detPagos;
		this.detalleGestionUsuarios = detGestionUsuarios;
		this.detalleLegaleses = detLegaleses;		
		this.configuracionCatalogos = configuracionCatalogos;
		this.habilitaRevocacionAviso = habilitaRevocacionAviso;
	}
	
	public Proyecto(Long idProyecto, Integer idDependencia, String descripcionDependencia, Integer idEstatusProyecto,
			String descripcionEstatus, Integer idTipoProyecto, String descripcionTipo, long idUsuarioLlaveCdmx, String nombreProyecto,
			boolean habilitaCaptcha, boolean habilitaAccesoLlave,
			boolean habilitaPagoLinea, boolean habilitaGestionUsuarios, boolean habilitaFirmaDigital,
			boolean habilitaDetalleLegales, boolean habilitaAnalytics, boolean habilitaSecurityDomain,
			Date fechaCreacion, boolean habilitaCapturaTramites) {
		this.idProyecto = idProyecto;
		this.catDependencia = new CatDependencia(idDependencia, descripcionDependencia);
		this.catEstatusProyecto = new CatEstatusProyecto(idEstatusProyecto, descripcionEstatus);
		this.catTipoProyecto = new CatTipoProyecto(idTipoProyecto, descripcionTipo);
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
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
	}

	@Id
	@Column(name = "id_proyecto", unique = true, nullable = false)
	public Long getIdProyecto() {
		return this.idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dependencia")
	public CatDependencia getCatDependencia() {
		return this.catDependencia;
	}

	public void setCatDependencia(CatDependencia catDependencia) {
		this.catDependencia = catDependencia;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus_proyecto", nullable = false)
	public CatEstatusProyecto getCatEstatusProyecto() {
		return this.catEstatusProyecto;
	}

	public void setCatEstatusProyecto(CatEstatusProyecto catEstatusProyecto) {
		this.catEstatusProyecto = catEstatusProyecto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_proyecto", nullable = false)
	public CatTipoProyecto getCatTipoProyecto() {
		return this.catTipoProyecto;
	}

	public void setCatTipoProyecto(CatTipoProyecto catTipoProyecto) {
		this.catTipoProyecto = catTipoProyecto;
	}

	@Column(name = "id_usuario_llave_cdmx", nullable = false)
	public long getIdUsuarioLlaveCdmx() {
		return this.idUsuarioLlaveCdmx;
	}

	public void setIdUsuarioLlaveCdmx(long idUsuarioLlaveCdmx) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
	}

	@Column(name = "nombre_proyecto", nullable = false, length = 200)
	public String getNombreProyecto() {
		return this.nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}
	
	@Column(name = "acronimo", length = 10)
	public String getAcronimo() {
		return acronimo;
	}
	
	public void setAcronimo(String acronimo) {
		this.acronimo = acronimo;
	}

	@Column(name = "habilita_captcha", nullable = false)
	public boolean isHabilitaCaptcha() {
		return this.habilitaCaptcha;
	}

	public void setHabilitaCaptcha(boolean habilitaCaptcha) {
		this.habilitaCaptcha = habilitaCaptcha;
	}

	@Column(name = "habilita_acceso_llave", nullable = false)
	public boolean isHabilitaAccesoLlave() {
		return this.habilitaAccesoLlave;
	}

	public void setHabilitaAccesoLlave(boolean habilitaAccesoLlave) {
		this.habilitaAccesoLlave = habilitaAccesoLlave;
	}

	@Column(name = "habilita_pago_linea", nullable = false)
	public boolean isHabilitaPagoLinea() {
		return this.habilitaPagoLinea;
	}

	public void setHabilitaPagoLinea(boolean habilitaPagoLinea) {
		this.habilitaPagoLinea = habilitaPagoLinea;
	}

	@Column(name = "habilita_gestion_usuarios", nullable = false)
	public boolean isHabilitaGestionUsuarios() {
		return this.habilitaGestionUsuarios;
	}

	public void setHabilitaGestionUsuarios(boolean habilitaGestionUsuarios) {
		this.habilitaGestionUsuarios = habilitaGestionUsuarios;
	}

	@Column(name = "habilita_firma_digital", nullable = false)
	public boolean isHabilitaFirmaDigital() {
		return this.habilitaFirmaDigital;
	}

	public void setHabilitaFirmaDigital(boolean habilitaFirmaDigital) {
		this.habilitaFirmaDigital = habilitaFirmaDigital;
	}

	@Column(name = "habilita_detalle_legales", nullable = false)
	public boolean isHabilitaDetalleLegales() {
		return this.habilitaDetalleLegales;
	}

	public void setHabilitaDetalleLegales(boolean habilitaDetalleLegales) {
		this.habilitaDetalleLegales = habilitaDetalleLegales;
	}

	@Column(name = "habilita_security_domain", nullable = false)
	public boolean isHabilitaSecurityDomain() {
		return this.habilitaSecurityDomain;
	}

	public void setHabilitaSecurityDomain(boolean habilitaSecurityDomain) {
		this.habilitaSecurityDomain = habilitaSecurityDomain;
	}
	
	@Column(name = "habilita_security_domain_curp", nullable = false)
	public boolean isHabilitaSecurityDomainCurp() {
		return habilitaSecurityDomainCurp;
	}

	public void setHabilitaSecurityDomainCurp(boolean habilitaSecurityDomainCurp) {
		this.habilitaSecurityDomainCurp = habilitaSecurityDomainCurp;
	}
	
	@Column(name = "habilita_analytics", nullable = false)
	public boolean isHabilitaAnalytics() {
		return this.habilitaAnalytics;
	}

	public void setHabilitaAnalytics(boolean habilitaAnalytics) {
		this.habilitaAnalytics = habilitaAnalytics;
	}
	
	@Column(name = "habilita_distribucion", nullable = false)
	public boolean isHabilitaDistribucion() {
		return this.habilitaDistribucion;
	}

	public void setHabilitaDistribucion(boolean habilitaDistribucion) {
		this.habilitaDistribucion = habilitaDistribucion;
	}
	
	@Column(name = "aviso", nullable = false)
	public boolean isAviso() {
		return this.aviso;
	}

	public void setAviso(boolean aviso) {
		this.aviso = aviso;
	}

	@Column(name = "habilita_captura_tramites", nullable = false)
	public boolean isHabilitaCapturaTramites() {
		return this.habilitaCapturaTramites;
	}

	public void setHabilitaCapturaTramites(boolean habilitaCapturaTramites) {
		this.habilitaCapturaTramites = habilitaCapturaTramites;
	}
	
	@Column(name = "habilita_configuracion_catalogos", nullable = false)
	public boolean isHabilitaConfiguracionCatalogos() {
	    return habilitaConfiguracionCatalogos;
	}

	public void setHabilitaConfiguracionCatalogos(boolean habilitaConfiguracionCatalogos) {
	    this.habilitaConfiguracionCatalogos = habilitaConfiguracionCatalogos;
	}
	
	@Column(name = "habilita_notificaciones_webhook", nullable = false)
	public boolean isHabilitaNotificacionesWebhook() {
		return this.habilitaNotificacionesWebhook;
	}

	public void setHabilitaNotificacionesWebhook(boolean habilitaNotificacionesWebhook) {
		this.habilitaNotificacionesWebhook = habilitaNotificacionesWebhook;
	}

	@Column(name = "habilita_revocacion_aviso", nullable = false)
	public boolean isHabilitaRevocacionAviso() {
		return this.habilitaRevocacionAviso;
	}

	public void setHabilitaRevocacionAviso(boolean habilitaRevocacionAviso) {
		this.habilitaRevocacionAviso = habilitaRevocacionAviso;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false, length = 29)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<DetCaptcha> getDetalleCaptchas() {
		return this.detalleCaptchas;
	}

	public void setDetalleCaptchas(Set<DetCaptcha> detalleCaptchas) {
		this.detalleCaptchas = detalleCaptchas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<DetAnalytics> getDetalleAnalyticses() {
		return this.detalleAnalyticses;
	}

	public void setDetalleAnalyticses(Set<DetAnalytics> detalleAnalyticses) {
		this.detalleAnalyticses = detalleAnalyticses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<DetHome> getDetalleHomes() {
		return this.detalleHomes;
	}

	public void setDetalleHomes(Set<DetHome> detalleHomes) {
		this.detalleHomes = detalleHomes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<ArchivosRespuestaToken> getArchivosRespuestaTokens() {
		return this.archivosRespuestaTokens;
	}

	public void setArchivosRespuestaTokens(Set<ArchivosRespuestaToken> archivosRespuestaTokens) {
		this.archivosRespuestaTokens = archivosRespuestaTokens;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<BitMovimientosSecciones> getBitMovimientosSeccioneses() {
		return this.bitMovimientosSeccioneses;
	}

	public void setBitMovimientosSeccioneses(Set<BitMovimientosSecciones> bitMovimientosSeccioneses) {
		this.bitMovimientosSeccioneses = bitMovimientosSeccioneses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<DetAccesoLlave> getDetAccesoLlaves() {
		return this.detalleAccesoLlaves;
	}

	public void setDetAccesoLlaves(Set<DetAccesoLlave> detAccesoLlaves) {
		this.detalleAccesoLlaves = detAccesoLlaves;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<DetPausasProyecto> getDetPausasProyectos() {
		return this.detPausasProyectos;
	}

	public void setDetPausasProyectos(Set<DetPausasProyecto> detPausasProyectos) {
		this.detPausasProyectos = detPausasProyectos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<DetSecurityDomain> getDetSecurityDomains() {
		return this.detSecurityDomains;
	}

	public void setDetSecurityDomains(Set<DetSecurityDomain> detSecurityDomains) {
		this.detSecurityDomains = detSecurityDomains;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<DetFirmaDigital> getDetalleFirmaDigitals() {
		return this.detalleFirmaDigitals;
	}

	public void setDetalleFirmaDigitals(Set<DetFirmaDigital> detalleFirmaDigitals) {
		this.detalleFirmaDigitals = detalleFirmaDigitals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<DetPago> getDetallePagos() {
		return this.detallePagos;
	}

	public void setDetallePagos(Set<DetPago> detallePagos) {
		this.detallePagos = detallePagos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<DetGestionUsuario> getDetalleGestionUsuarios() {
		return this.detalleGestionUsuarios;
	}

	public void setDetalleGestionUsuarios(Set<DetGestionUsuario> detalleGestionUsuarios) {
		this.detalleGestionUsuarios = detalleGestionUsuarios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<DetLegales> getDetalleLegaleses() {
		return this.detalleLegaleses;
	}

	public void setDetalleLegaleses(Set<DetLegales> detalleLegaleses) {
		this.detalleLegaleses = detalleLegaleses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<SeccionesFormulario> getSeccionesFormularios() {
		return this.seccionesFormularios;
	}

	public void setSeccionesFormularios(Set<SeccionesFormulario> seccionesFormularios) {
		this.seccionesFormularios = seccionesFormularios;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<DetDistribucion> getDetDistribucions() {
		return this.detDistribucions;
	}

	public void setDetDistribucions(Set<DetDistribucion> detDistribucions) {
		this.detDistribucions = detDistribucions;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "proyecto")
	public Set<ConfiguracionCatalogo> getConfiguracionCatalogos() {
	    return configuracionCatalogos;
	}

	public void setConfiguracionCatalogos(Set<ConfiguracionCatalogo> configuracionCatalogos) {
	    this.configuracionCatalogos = configuracionCatalogos;
	}
	
	@Override
	public String toString() {
		return "Proyecto [idProyecto=" + idProyecto + ", catDependencia=" + catDependencia + ", catEstatusProyecto="
				+ catEstatusProyecto + ", catTipoProyecto=" + catTipoProyecto + ", nombreProyecto=" + nombreProyecto
				+ ", habilitaCaptcha=" + habilitaCaptcha + ", habilitaAccesoLlave=" + habilitaAccesoLlave
				+ ", habilitaPagoLinea=" + habilitaPagoLinea + ", habilitaGestionUsuarios=" + habilitaGestionUsuarios
				+ ", habilitaFirmaDigital=" + habilitaFirmaDigital + ", habilitaDetalleLegales="
				+ habilitaDetalleLegales + ", habilitaAnalytics=" + habilitaAnalytics + ", habilitaSecurityDomain="
				+ habilitaSecurityDomain + ", idUsuarioLlaveCdmx=" + idUsuarioLlaveCdmx + ", fechaCreacion="
				+ fechaCreacion + ", detalleCaptchas=" + detalleCaptchas + ", detalleAnalyticses=" + detalleAnalyticses
				+ ", detalleHomes=" + detalleHomes + ", bitMovimientosSeccioneses=" + bitMovimientosSeccioneses
				+ ", detalleAccesoLlaves=" + detalleAccesoLlaves + ", detalleFirmaDigitals=" + detalleFirmaDigitals
				+ ", detallePagos=" + detallePagos + ", detalleGestionUsuarios=" + detalleGestionUsuarios
				+ ", detalleLegaleses=" + detalleLegaleses + ", detPausasProyectos=" + detPausasProyectos
				+ ", detSecurityDomains=" + detSecurityDomains + ", archivosRespuestaTokens=" + archivosRespuestaTokens
				+ ", seccionesFormularios=" + seccionesFormularios + "]";
	}

}

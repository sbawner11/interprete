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
@Table(name = "tramites", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "Tramites.findAllNoNotificados", 	
			query = "SELECT new mx.gob.atdt.interprete.dto.TramiteDTO( t.idTramite, ce.idEstatusTramite, ce.descripcion )" 
					+ " FROM Tramites t" 
					+ " JOIN t.catEstatusTramite ce" 									
					+ " WHERE ce.idEstatusTramite IN (3,6,7)"
					+ " AND NOT EXISTS"
					+ " (SELECT 1 FROM NotificacionMovimientoTramite nmt JOIN nmt.catTipoNotificacion ctm"
					+ " WHERE nmt.tramites.idTramite = t.idTramite AND ctm.idTipoNotificacion = CASE WHEN ce.idEstatusTramite IN (6,7) THEN"
					+ " 2 WHEN ce.idEstatusTramite = 3 THEN 1 ELSE 0 END)"
					+ " ORDER BY t.fechaCreacion")
	//Consulta de tramites no notificados a webhook en proyectos que tiene firma activa
	,@NamedQuery(name = "Tramites.findAllNoNotificadosWithFirmaActu", 	
			query = "SELECT new mx.gob.atdt.interprete.dto.TramiteDTO(t.idTramite, ce.idEstatusTramite, ce.descripcion)" 
					+ " FROM Tramites t" 
					+ " JOIN t.catEstatusTramite ce" 
					+ " WHERE ce.idEstatusTramite IN (6,7)"
					+ " AND EXISTS"
					+ " (SELECT 1 FROM TramiteFirmaElectronica tfe WHERE tfe.tramites.idTramite = t.idTramite AND tfe.firmaCiudadano = false)" 
					+ " AND NOT EXISTS" 
					+ " (SELECT 1 FROM NotificacionMovimientoTramite nmt JOIN nmt.catTipoNotificacion ctm"
					+ " WHERE nmt.tramites.idTramite = t.idTramite AND ctm.idTipoNotificacion = 2)" 					
					+ " ORDER BY t.fechaCreacion "
					)
	,@NamedQuery(name = "Tramites.findAllNoNotificadosWithFirmaCrea", 	
		query = "SELECT new mx.gob.atdt.interprete.dto.TramiteDTO(t.idTramite, ce.idEstatusTramite, ce.descripcion )" 
					+ " FROM Tramites t" 
					+ " JOIN t.catEstatusTramite ce" 
					+ " WHERE ce.idEstatusTramite = 3"
					+ " AND NOT EXISTS (SELECT 1 FROM NotificacionMovimientoTramite nmt JOIN nmt.catTipoNotificacion ctm"
					+ " WHERE nmt.tramites.idTramite = t.idTramite AND ctm.idTipoNotificacion = 1)" 					
					+ " ORDER BY t.fechaCreacion "
					)
		//Consulta tramites no notificados al webhook
		,@NamedQuery(name = "Tramites.findAllTramitesNotificacionFallida", 	
				query = "SELECT new mx.gob.atdt.interprete.dto.TramiteDTO(t.idTramite, t.folioSeguimiento,"
					+ " t.uuid,t.fechaCreacion, t.fechaRevision, t.rutaDocumentoPrevencion, t.rutaDocumentoRevocado,"
					+ " t.rutaResolucionPositiva, t.rutaResolucionNegativa, ce.idEstatusTramite, ce.descripcion,"
					+ " ce.descripcionAviso, ce.descripcionPersonalizada, u.idUsuarioLlaveCdmx, u.curp, pm.idPersonaMoral)"
					+ " FROM Tramites t" 
					+ " JOIN t.catEstatusTramite ce" 
					+ " JOIN t.usuarioByIdUsuarioLlaveCdmx u"
					+ " LEFT JOIN t.personaMoral pm"
					+ " WHERE EXISTS" 
					+ " (SELECT 1 FROM NotificacionMovimientoTramite nmt WHERE nmt.tramites.idTramite = t.idTramite"
					+ " AND nmt.fechaNotificacion IS NULL AND nmt.envioConfirmado = false )" 					
					+ " ORDER BY t.fechaCreacion"
					)
})
public class Tramites implements java.io.Serializable {

	private static final long serialVersionUID = -4083931686184616953L;
	
	private long idTramite;
	private CatEstatusTramite catEstatusTramite;
	private PersonaMoral personaMoral;
	private Usuario usuarioByIdUsuarioLlaveCdmx;
	private Usuario usuarioByIdUsuarioRevisor;
	private String folioSeguimiento;
	private Integer idUsuarioOperador;
	private String respuestaFolioPrevencion;
	private String respuestaFolioConclusion;
	private Date fechaCreacion;
	private Date fechaRevision;
	private String uuid;
	private String rutaDocumentoPrevencion;
	private String rutaDocumentoRevocado;
	private String motivoRechazo;
	private String rutaResolucionPositiva;
	private String rutaResolucionNegativa;

	private Set<NotificacionMovimientoTramite> notificacionMovimientoTramites = new HashSet<NotificacionMovimientoTramite>(
			0);
	private Set<SolicitudLineaCaptura> solicitudLineaCapturas = new HashSet<SolicitudLineaCaptura>(0);
	private Set<LineaCaptura> lineaCapturas = new HashSet<LineaCaptura>(0);
	
	private Set<TramiteFirmaElectronica> tramiteFirmaElectronica = new HashSet<TramiteFirmaElectronica>(0);
	

	public Tramites() {
	}

	public Tramites(long idTramite, CatEstatusTramite catEstatusTramite, String folioSeguimiento, Date fechaCreacion) {
		this.idTramite = idTramite;
		this.catEstatusTramite = catEstatusTramite;
		this.folioSeguimiento = folioSeguimiento;
		this.fechaCreacion = fechaCreacion;
	}

	@Id
	@Column(name = "id_tramite", unique = true, nullable = false)
	public long getIdTramite() {
		return this.idTramite;
	}

	public void setIdTramite(long idTramite) {
		this.idTramite = idTramite;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus_tramite", nullable = false)
	public CatEstatusTramite getCatEstatusTramite() {
		return this.catEstatusTramite;
	}

	public void setCatEstatusTramite(CatEstatusTramite catEstatusTramite) {
		this.catEstatusTramite = catEstatusTramite;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_persona_moral")
	public PersonaMoral getPersonaMoral() {
		return this.personaMoral;
	}

	public void setPersonaMoral(PersonaMoral personaMoral) {
		this.personaMoral = personaMoral;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_llave_cdmx")
	public Usuario getUsuarioByIdUsuarioLlaveCdmx() {
		return this.usuarioByIdUsuarioLlaveCdmx;
	}

	public void setUsuarioByIdUsuarioLlaveCdmx(Usuario usuarioByIdUsuarioLlaveCdmx) {
		this.usuarioByIdUsuarioLlaveCdmx = usuarioByIdUsuarioLlaveCdmx;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_revisor")
	public Usuario getUsuarioByIdUsuarioRevisor() {
		return this.usuarioByIdUsuarioRevisor;
	}

	public void setUsuarioByIdUsuarioRevisor(Usuario usuarioByIdUsuarioRevisor) {
		this.usuarioByIdUsuarioRevisor = usuarioByIdUsuarioRevisor;
	}

	@Column(name = "folio_seguimiento", nullable = false, length = 25)
	public String getFolioSeguimiento() {
		return this.folioSeguimiento;
	}

	public void setFolioSeguimiento(String folioSeguimiento) {
		this.folioSeguimiento = folioSeguimiento;
	}

	@Column(name = "id_usuario_operador")
	public Integer getIdUsuarioOperador() {
		return this.idUsuarioOperador;
	}

	public void setIdUsuarioOperador(Integer idUsuarioOperador) {
		this.idUsuarioOperador = idUsuarioOperador;
	}

	@Column(name = "respuesta_folio_prevencion")
	public String getRespuestaFolioPrevencion() {
		return this.respuestaFolioPrevencion;
	}

	public void setRespuestaFolioPrevencion(String respuestaFolioPrevencion) {
		this.respuestaFolioPrevencion = respuestaFolioPrevencion;
	}

	@Column(name = "respuesta_folio_conclusion")
	public String getRespuestaFolioConclusion() {
		return this.respuestaFolioConclusion;
	}

	public void setRespuestaFolioConclusion(String respuestaFolioConclusion) {
		this.respuestaFolioConclusion = respuestaFolioConclusion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false, length = 29)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_revision", length = 29)
	public Date getFechaRevision() {
		return this.fechaRevision;
	}

	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	@Column(name = "uuid", length = 36)
	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "ruta_documento_prevencion", length = 500)
	public String getRutaDocumentoPrevencion() {
		return this.rutaDocumentoPrevencion;
	}

	public void setRutaDocumentoPrevencion(String rutaDocumentoPrevencion) {
		this.rutaDocumentoPrevencion = rutaDocumentoPrevencion;
	}

	@Column(name = "ruta_documento_revocado", length = 500)
	public String getRutaDocumentoRevocado() {
		return this.rutaDocumentoRevocado;
	}

	public void setRutaDocumentoRevocado(String rutaDocumentoRevocado) {
		this.rutaDocumentoRevocado = rutaDocumentoRevocado;
	}

	@Column(name = "motivo_rechazo")
	public String getMotivoRechazo() {
		return this.motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	@Column(name = "ruta_resolucion_positiva", length = 500)
	public String getRutaResolucionPositiva() {
		return this.rutaResolucionPositiva;
	}

	public void setRutaResolucionPositiva(String rutaResolucionPositiva) {
		this.rutaResolucionPositiva = rutaResolucionPositiva;
	}

	@Column(name = "ruta_resolucion_negativa", length = 500)
	public String getRutaResolucionNegativa() {
		return this.rutaResolucionNegativa;
	}

	public void setRutaResolucionNegativa(String rutaResolucionNegativa) {
		this.rutaResolucionNegativa = rutaResolucionNegativa;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tramites")
	public Set<NotificacionMovimientoTramite> getNotificacionMovimientoTramites() {
		return this.notificacionMovimientoTramites;
	}

	public void setNotificacionMovimientoTramites(Set<NotificacionMovimientoTramite> notificacionMovimientoTramites) {
		this.notificacionMovimientoTramites = notificacionMovimientoTramites;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tramites")
	public Set<LineaCaptura> getLineaCapturas() {
		return this.lineaCapturas;
	}

	public void setLineaCapturas(Set<LineaCaptura> lineaCapturas) {
		this.lineaCapturas = lineaCapturas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tramites")
	public Set<SolicitudLineaCaptura> getSolicitudLineaCapturas() {
		return solicitudLineaCapturas;
	}

	public void setSolicitudLineaCapturas(Set<SolicitudLineaCaptura> solicitudLineaCapturas) {
		this.solicitudLineaCapturas = solicitudLineaCapturas;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tramites")
	public Set<TramiteFirmaElectronica> getTramiteFirmaElectronica() {
		return tramiteFirmaElectronica;
	}

	public void setTramiteFirmaElectronica(Set<TramiteFirmaElectronica> tramiteFirmaElectronica) {
		this.tramiteFirmaElectronica = tramiteFirmaElectronica;
	}
	
	
}

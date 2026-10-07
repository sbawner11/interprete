package mx.gob.atdt.interprete.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "det_firma_digital", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "DetFirmaDigital.findByIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetFirmaDigitalDTO("
			+ " d.idDetalleFirma, p.idProyecto, d.claveSistema, d.urlFirmado, d.urlRedirecciona, d.usuarioDominioSeg, d.contrasenaDominioSeg, "
			+ " d.fechaCreacion, d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada, d.firmaCiudadano) "
			+ " FROM DetFirmaDigital d "
			+ " JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto "),
	@NamedQuery(name = "DetFirmaDigital.existeDetalleActivoIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetFirmaDigitalDTO("
			+ " d.idDetalleFirma, p.idProyecto, d.claveSistema, d.urlFirmado, d.urlRedirecciona, d.usuarioDominioSeg, d.contrasenaDominioSeg, "
			+ " d.fechaCreacion, d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada, d.firmaCiudadano) "
			+ " FROM DetFirmaDigital d "
			+ " JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto "
			+ " AND d.activo = :activo"),
	@NamedQuery(
		    name = "DetFirmaDigital.findFirmaCiudadanoByIdProyecto",
		    query = "SELECT new mx.gob.atdt.interprete.dto.DetFirmaDigitalDTO(d.firmaCiudadano) " +
		            " FROM DetFirmaDigital d " +
		            " JOIN d.proyecto p " +
		            " WHERE p.idProyecto = :idProyecto" +
		            " AND d.activo = true"
		)
})
public class DetFirmaDigital implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1929798866501302498L;
	
	private Long idDetalleFirma;
	private Proyecto proyecto;
	private String claveSistema;
	private String urlFirmado;
	private String urlRedirecciona;
	private String usuarioDominioSeg;
	private String contrasenaDominioSeg;		
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	private boolean firmaCiudadano;

	public DetFirmaDigital() {
	}

	public DetFirmaDigital(long idDetalleFirma, Proyecto proyecto, boolean activo, boolean seccionSincronizada, 
			String claveSistema, String urlFirmado, String urlRedirecciona, String usuarioDominioSeg, 
			String contrasenaDominioSeg, Date fechaCreacion, Date fechaUltimaActualizacion) {
		this.idDetalleFirma = idDetalleFirma;
		this.proyecto = proyecto;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		this.claveSistema = claveSistema;
		this.urlFirmado = urlFirmado;
		this.urlRedirecciona = urlRedirecciona;
		this.usuarioDominioSeg = usuarioDominioSeg;
		this.contrasenaDominioSeg = contrasenaDominioSeg;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	@Id
	@Column(name = "id_detalle_firma", unique = true, nullable = false)
	public Long getIdDetalleFirma() {
		return this.idDetalleFirma;
	}

	public void setIdDetalleFirma(Long idDetalleFirma) {
		this.idDetalleFirma = idDetalleFirma;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}
	
	@Column(name = "clave_sistema", nullable = false, length = 60)
	public String getClaveSistema() {
		return this.claveSistema;
	}

	public void setClaveSistema(String claveSistema) {
		this.claveSistema = claveSistema;
	}

	@Column(name = "url_firmado", nullable = false, length = 500)
	public String getUrlFirmado() {
		return this.urlFirmado;
	}

	public void setUrlFirmado(String urlFirmado) {
		this.urlFirmado = urlFirmado;
	}

	@Column(name = "url_redirecciona", nullable = false, length = 500)
	public String getUrlRedirecciona() {
		return this.urlRedirecciona;
	}

	public void setUrlRedirecciona(String urlRedirecciona) {
		this.urlRedirecciona = urlRedirecciona;
	}

	@Column(name = "usuario_dominio_seg", nullable = false, length = 100)
	public String getUsuarioDominioSeg() {
		return this.usuarioDominioSeg;
	}

	public void setUsuarioDominioSeg(String usuarioDominioSeg) {
		this.usuarioDominioSeg = usuarioDominioSeg;
	}

	@Column(name = "contrasena_dominio_seg", nullable = false, length = 100)
	public String getContrasenaDominioSeg() {
		return this.contrasenaDominioSeg;
	}

	public void setContrasenaDominioSeg(String contrasenaDominioSeg) {
		this.contrasenaDominioSeg = contrasenaDominioSeg;
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
	@Column(name = "fecha_ultima_actualizacion", nullable = false, length = 29)
	public Date getFechaUltimaActualizacion() {
		return this.fechaUltimaActualizacion;
	}

	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	@Column(name = "seccion_sincronizada", nullable = false)
	public boolean isSeccionSincronizada() {
		return this.seccionSincronizada;
	}

	public void setSeccionSincronizada(boolean seccionSincronizada) {
		this.seccionSincronizada = seccionSincronizada;
	}

	@Column(name = "firma_ciudadano", nullable = false)
	public boolean isFirmaCiudadano() {
		return firmaCiudadano;
	}

	public void setFirmaCiudadano(boolean firmaCiudadano) {
		this.firmaCiudadano = firmaCiudadano;
	}
	
	
}
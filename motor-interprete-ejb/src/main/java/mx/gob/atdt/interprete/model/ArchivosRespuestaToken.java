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
@Table(name = "archivos_respuesta_token", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "ArchivosRespuestaToken.findByIdProyecto", 
				query = "SELECT new mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO("
						+ " art.idArchivoRespuesta, p.idProyecto, art.rutaArchivoRespuesta, art.nombreArchivo, "
						+ " art.habilitaFirma, art.firmaSupervisor, art.firmaOperador, art.coodenadaQrX, art.coodenadaQrY, "
						+ " ctp.idTipoPlantilla , art.fechaCreacion, art.fechaUltimaActualizacion, art.activo, art.seccionSincronizada) "
						+ " FROM ArchivosRespuestaToken art "
						+ " JOIN art.proyecto p "
						+ " LEFT JOIN art.catTipoPlantilla ctp "
						+ " WHERE p.idProyecto =:idProyecto"),
		@NamedQuery(name = "ArchivosRespuestaToken.findById", 
				query = "SELECT new mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO("
						+ " art.idArchivoRespuesta, art.proyecto.idProyecto, art.rutaArchivoRespuesta, art.nombreArchivo, "
						+ " art.habilitaFirma, art.firmaSupervisor, art.firmaOperador, art.coodenadaQrX, art.coodenadaQrY, "
						+ "	ctp.idTipoPlantilla, art.fechaCreacion, art.fechaUltimaActualizacion, art.activo, art.seccionSincronizada) "
						+ "	FROM ArchivosRespuestaToken art "
						+ " LEFT JOIN art.catTipoPlantilla ctp "
						+ "	WHERE art.idArchivoRespuesta =:idArchivo"),
		@NamedQuery(name = "ArchivosRespuestaToken.existeDetalleIdProyecto", 
				query = "SELECT new mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO("
						+ " p.idProyecto, art.idArchivoRespuesta) "
						+ " FROM ArchivosRespuestaToken art "
						+ " JOIN art.proyecto p "
						+ " LEFT JOIN art.catTipoPlantilla ctp "
						+ " WHERE p.idProyecto =:idProyecto "
						+ " AND art.activo = true")
		})
public class ArchivosRespuestaToken implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4083797433914859404L;
	private Long idArchivoRespuesta;
	private CatTipoPlantilla catTipoPlantilla;
	private Proyecto proyecto;
	private String rutaArchivoRespuesta;
	private String nombreArchivo;
	private boolean habilitaFirma;
	private boolean firmaSupervisor;
	private boolean firmaOperador;
	private Long coodenadaQrX;
	private Long coodenadaQrY;	
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	private Set<DetElementosToken> detElementosTokens = new HashSet<DetElementosToken>(0);
	
	public ArchivosRespuestaToken() {
	}
	
	public ArchivosRespuestaToken(Long idArchivoRespuesta) {
		this.idArchivoRespuesta = idArchivoRespuesta;
	}

	public ArchivosRespuestaToken(long idArchivoRespuesta, Proyecto proyecto, String rutaArchivoRespuesta,
			String nombreArchivo, Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo,
			boolean seccionSincronizada, boolean habilitaFirma, boolean firmaSupervisor, boolean firmaOperador) {
		this.idArchivoRespuesta = idArchivoRespuesta;
		this.proyecto = proyecto;
		this.rutaArchivoRespuesta = rutaArchivoRespuesta;
		this.nombreArchivo = nombreArchivo;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		this.habilitaFirma = habilitaFirma;
		this.firmaSupervisor = firmaSupervisor;
		this.firmaOperador = firmaOperador;
	}

	public ArchivosRespuestaToken(long idArchivoRespuesta, Proyecto proyecto, String rutaArchivoRespuesta,
			String nombreArchivo, Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo,
			boolean seccionSincronizada, boolean habilitaFirma, boolean firmaSupervisor, boolean firmaOperador,
			Long coodenadaQrX, Long coodenadaQrY, Set<DetElementosToken> detElementosTokens) {
		this.idArchivoRespuesta = idArchivoRespuesta;
		this.proyecto = proyecto;
		this.rutaArchivoRespuesta = rutaArchivoRespuesta;
		this.nombreArchivo = nombreArchivo;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		this.habilitaFirma = habilitaFirma;
		this.firmaSupervisor = firmaSupervisor;
		this.firmaOperador = firmaOperador;
		this.coodenadaQrX = coodenadaQrX;
		this.coodenadaQrY = coodenadaQrY;
		this.detElementosTokens = detElementosTokens;
	}

	@Id
	@Column(name = "id_archivo_respuesta", unique = true, nullable = false)
	public Long getIdArchivoRespuesta() {
		return idArchivoRespuesta;
	}

	public void setIdArchivoRespuesta(Long idArchivoRespuesta) {
		this.idArchivoRespuesta = idArchivoRespuesta;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "ruta_archivo_respuesta", nullable = false)
	public String getRutaArchivoRespuesta() {
		return rutaArchivoRespuesta;
	}

	public void setRutaArchivoRespuesta(String rutaArchivoRespuesta) {
		this.rutaArchivoRespuesta = rutaArchivoRespuesta;
	}

	@Column(name = "nombre_archivo", nullable = false)
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	@Column(name = "habilita_firma", nullable = false)
	public boolean isHabilitaFirma() {
		return this.habilitaFirma;
	}

	public void setHabilitaFirma(boolean habilitaFirma) {
		this.habilitaFirma = habilitaFirma;
	}

	@Column(name = "firma_supervisor", nullable = false)
	public boolean isFirmaSupervisor() {
		return this.firmaSupervisor;
	}

	public void setFirmaSupervisor(boolean firmaSupervisor) {
		this.firmaSupervisor = firmaSupervisor;
	}

	@Column(name = "firma_operador", nullable = false)
	public boolean isFirmaOperador() {
		return this.firmaOperador;
	}

	public void setFirmaOperador(boolean firmaOperador) {
		this.firmaOperador = firmaOperador;
	}

	@Column(name = "coodenada_qr_x")
	public Long getCoodenadaQrX() {
		return this.coodenadaQrX;
	}

	public void setCoodenadaQrX(Long coodenadaQrX) {
		this.coodenadaQrX = coodenadaQrX;
	}

	@Column(name = "coodenada_qr_y")
	public Long getCoodenadaQrY() {
		return this.coodenadaQrY;
	}

	public void setCoodenadaQrY(Long coodenadaQrY) {
		this.coodenadaQrY = coodenadaQrY;
	}

	@Column(name = "fecha_creacion", nullable = false)
	public Date getFechaCreacion() {
		return fechaCreacion;
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
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Column(name = "seccion_sincronizada", nullable = false)
	public boolean isSeccionSincronizada() {
		return seccionSincronizada;
	}

	public void setSeccionSincronizada(boolean seccionSincronizada) {
		this.seccionSincronizada = seccionSincronizada;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "archivosRespuestaToken")
	public Set<DetElementosToken> getDetElementosTokens() {
		return this.detElementosTokens;
	}

	public void setDetElementosTokens(Set<DetElementosToken> detElementosTokens) {
		this.detElementosTokens = detElementosTokens;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_plantilla")
	public CatTipoPlantilla getCatTipoPlantilla() {
		return this.catTipoPlantilla;
	}

	public void setCatTipoPlantilla(CatTipoPlantilla catTipoPlantilla) {
		this.catTipoPlantilla = catTipoPlantilla;
	}

}

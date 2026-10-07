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
@Table(name = "det_home", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "DetHome.findByIdProyecto", query = "SELECT new mx.gob.atdt.interprete.dto.DetHomeDTO("
				+ " d.idDetalleHome, p.idProyecto, d.rutaArchivoLogotipo, "
				+ " d.habilitaNotificacion, d.descripcionNotificacion, d.fechaCreacion, d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada) "
				+ " FROM DetHome d " + "	JOIN d.proyecto p " + " WHERE p.idProyecto = :idProyecto"),
		@NamedQuery(name = "DetHome.existeDetalleHomeTramiteActivoIdProyecto", query = "SELECT new mx.gob.atdt.interprete.dto.DetHomeDTO("
				+ "	d.idDetalleHome, p.idProyecto)"
				+ " FROM DetHome d, DetTramiteServicio dts, DetRequisito dr" 
				+ " JOIN d.proyecto p "
				+ " WHERE p.idProyecto = :idProyecto "
				+ " AND d.activo = :activo "
				+ " AND dr.activo = true"),
		@NamedQuery(name = "DetHome.existeDetalleHomeProgramaActivoIdProyecto", query = "SELECT new mx.gob.atdt.interprete.dto.DetHomeDTO("
				+ " d.idDetalleHome, p.idProyecto) " + " FROM DetHome d, DetProgramaSocial dps " + "	JOIN d.proyecto p "
				+ " WHERE p.idProyecto = :idProyecto"
				+ " AND d.activo = :activo "),
		@NamedQuery(name = "DetHome.findByIdDetHome", query = "SELECT new mx.gob.atdt.interprete.dto.DetHomeDTO(d.idDetalleHome) "
				+ " FROM DetHome d WHERE d.idDetalleHome =:idDetHome"),
		})
public class DetHome implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1474093268522423949L;

	private Long idDetalleHome;
	private Proyecto proyecto;
	private String rutaArchivoLogotipo;
	private boolean habilitaNotificacion;
	private String descripcionNotificacion;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	private boolean personalizaPausa;
	private String tituloPausa;
	private String descripcionPausa;
	private Set<DetProgramaSocial> detProgramaSocials = new HashSet<DetProgramaSocial>(0);
	private Set<DetExcepcionTramite> detExcepcionTramites = new HashSet<DetExcepcionTramite>(0);
	private Set<DetTramiteServicio> detTramiteServicios = new HashSet<DetTramiteServicio>(0);
	private Set<DetObjetivosPrograma> detObjetivosProgramas = new HashSet<DetObjetivosPrograma>(0);
	private Set<DetPoblacionObjetivo> detPoblacionObjetivos = new HashSet<DetPoblacionObjetivo>(0);
	private Set<DetRequisito> detRequisitos = new HashSet<DetRequisito>(0);
	private Set<DetApoyoOtorgado> detApoyoOtorgados = new HashSet<DetApoyoOtorgado>(0);

	public DetHome() {
	}

	public DetHome(Long idDetalleHome, Proyecto proyecto, String rutaArchivoLogotipo, boolean habilitaNotificacion,
			Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idDetalleHome = idDetalleHome;
		this.proyecto = proyecto;
		this.rutaArchivoLogotipo = rutaArchivoLogotipo;
		this.habilitaNotificacion = habilitaNotificacion;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	public DetHome(Long idDetalleHome, Proyecto proyecto, String rutaArchivoLogotipo, boolean habilitaNotificacion,
			String descripcionNotificacion, Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo,
			boolean seccionSincronizada, Set<DetProgramaSocial> detProgramaSocials, 
			Set<DetExcepcionTramite> detExcepcionTramites,
			Set<DetTramiteServicio> detTramiteServicios,
			Set<DetObjetivosPrograma> detObjetivosProgramas,
			Set<DetPoblacionObjetivo> detPoblacionObjetivos, Set<DetRequisito> detRequisitos,
			Set<DetApoyoOtorgado> detApoyoOtorgados) {
		this.idDetalleHome = idDetalleHome;
		this.proyecto = proyecto;
		this.rutaArchivoLogotipo = rutaArchivoLogotipo;
		this.habilitaNotificacion = habilitaNotificacion;
		this.descripcionNotificacion = descripcionNotificacion;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		this.detProgramaSocials = detProgramaSocials;
		this.detExcepcionTramites = detExcepcionTramites;
		this.detTramiteServicios = detTramiteServicios;
		this.detObjetivosProgramas = detObjetivosProgramas;
		this.detPoblacionObjetivos = detPoblacionObjetivos;
		this.detRequisitos = detRequisitos;
		this.detApoyoOtorgados = detApoyoOtorgados;
	}

	@Id
	@Column(name = "id_detalle_home", unique = true, nullable = false)
	public Long getIdDetalleHome() {
		return this.idDetalleHome;
	}

	public void setIdDetalleHome(Long idDetalleHome) {
		this.idDetalleHome = idDetalleHome;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "ruta_archivo_logotipo", nullable = false, length = 200)
	public String getRutaArchivoLogotipo() {
		return this.rutaArchivoLogotipo;
	}

	public void setRutaArchivoLogotipo(String rutaArchivoLogotipo) {
		this.rutaArchivoLogotipo = rutaArchivoLogotipo;
	}

	@Column(name = "habilita_notificacion", nullable = false)
	public boolean isHabilitaNotificacion() {
		return this.habilitaNotificacion;
	}

	public void setHabilitaNotificacion(boolean habilitaNotificacion) {
		this.habilitaNotificacion = habilitaNotificacion;
	}

	@Column(name = "descripcion_notificacion", length = 200)
	public String getDescripcionNotificacion() {
		return this.descripcionNotificacion;
	}

	public void setDescripcionNotificacion(String descripcionNotificacion) {
		this.descripcionNotificacion = descripcionNotificacion;
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
	
	@Column(name = "personaliza_pausa", nullable = false)
	public boolean isPersonalizaPausa() {
		return personalizaPausa;
	}
	
	public void setPersonalizaPausa(boolean personalizaPausa) {
		this.personalizaPausa = personalizaPausa;
	}

	@Column(name = "titulo_pausa", length = 100)
	public String getTituloPausa() {
		return tituloPausa;
	}

	public void setTituloPausa(String tituloPausa) {
		this.tituloPausa = tituloPausa;
	}

	@Column(name = "descripcion_pausa", length = 500)
	public String getDescripcionPausa() {
		return descripcionPausa;
	}

	public void setDescripcionPausa(String descripcionPausa) {
		this.descripcionPausa = descripcionPausa;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detHome")
	public Set<DetProgramaSocial> getDetProgramaSocials() {
		return this.detProgramaSocials;
	}

	public void setDetProgramaSocials(Set<DetProgramaSocial> detProgramaSocials) {
		this.detProgramaSocials = detProgramaSocials;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detHome")
	public Set<DetExcepcionTramite> getDetExcepcionTramites() {
		return this.detExcepcionTramites;
	}

	public void setDetExcepcionTramites(Set<DetExcepcionTramite> detExcepcionTramites) {
		this.detExcepcionTramites = detExcepcionTramites;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detHome")
	public Set<DetTramiteServicio> getDetTramiteServicios() {
		return this.detTramiteServicios;
	}

	public void setDetTramiteServicios(Set<DetTramiteServicio> detTramiteServicios) {
		this.detTramiteServicios = detTramiteServicios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detHome")
	public Set<DetObjetivosPrograma> getDetObjetivosProgramas() {
		return this.detObjetivosProgramas;
	}

	public void setDetObjetivosProgramas(Set<DetObjetivosPrograma> detObjetivosProgramas) {
		this.detObjetivosProgramas = detObjetivosProgramas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detHome")
	public Set<DetPoblacionObjetivo> getDetPoblacionObjetivos() {
		return this.detPoblacionObjetivos;
	}

	public void setDetPoblacionObjetivos(Set<DetPoblacionObjetivo> detPoblacionObjetivos) {
		this.detPoblacionObjetivos = detPoblacionObjetivos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detHome")
	public Set<DetRequisito> getDetRequisitos() {
		return this.detRequisitos;
	}

	public void setDetRequisitos(Set<DetRequisito> detRequisitos) {
		this.detRequisitos = detRequisitos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "detHome")
	public Set<DetApoyoOtorgado> getDetApoyoOtorgados() {
		return this.detApoyoOtorgados;
	}

	public void setDetApoyoOtorgados(Set<DetApoyoOtorgado> detApoyoOtorgados) {
		this.detApoyoOtorgados = detApoyoOtorgados;
	}

}

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
@Table(name = "det_gestion_usuario", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetGestionUsuario.findByIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetGestionUsuarioDTO("
			+ " d.idGestionUsuario ,p.idProyecto, d.perfilSupervisorPrevencion, d.perfilOperadorPrevencion, d.perfilSupervisorConclusion, d.perfilOperadorConclusion, "		
			+ " d.correoConclusion, d.correoPrevencion, d.correoSubsanarPrevencion,"
			+ " d.correoRegistrado, d.correoRechazado, "
			+ " d.habilitaPrevencion,d.adjuntaOficio,d.diasSubsanarPrevencion, "
			+ " d.fechaCreacion, d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada, d.apiKey, "
			+ " d.habilitaResolucion, d.perfilSupervisorResolucion, d.perfilOperadorResolucion, "
			+ " d.resolucionPositivaObligatoria, d.resolucionNegativaObligatoria, "
			+ " d.correoResolucionPositiva, d.correoResolucionNegativa ) "  
			+ " FROM DetGestionUsuario d "
			+ "	JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto"),
	@NamedQuery(name = "DetGestionUsuario.existeDetalleActivoIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetGestionUsuarioDTO("
			+ " p.idProyecto, d.idGestionUsuario) " 
			+ " FROM DetGestionUsuario d "
			+ "	JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto"
			+ " AND d.activo = :activo "),
	@NamedQuery(name = "DetGestionUsuario.buscarProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetGestionUsuarioDTO("
			+ " d.idGestionUsuario ,p.idProyecto, d.perfilSupervisorPrevencion, d.perfilOperadorPrevencion, d.perfilSupervisorConclusion, d.perfilOperadorConclusion, "		
			+ " d.correoConclusion, d.correoPrevencion, d.correoSubsanarPrevencion,"
			+ " d.correoRegistrado, d.correoRechazado, "
			+ " d.habilitaPrevencion,d.adjuntaOficio,d.diasSubsanarPrevencion, "
			+ " d.fechaCreacion, d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada, d.apiKey, "
			+ " d.habilitaResolucion, d.perfilSupervisorResolucion, d.perfilOperadorResolucion, "
			+ " d.resolucionPositivaObligatoria, d.resolucionNegativaObligatoria, "
			+ " d.correoResolucionPositiva, d.correoResolucionNegativa ) " 
			+ " FROM DetGestionUsuario d "
			+ "	JOIN d.proyecto p ")
	
})
public class DetGestionUsuario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4717162879319812904L;
	
	private Long idGestionUsuario;
	private Proyecto proyecto;
	private Boolean perfilSupervisorPrevencion;
	private Boolean perfilOperadorPrevencion;
	private Boolean perfilSupervisorConclusion;
	private Boolean perfilOperadorConclusion;
	private String correoConclusion;
	private String correoPrevencion;
	private String correoSubsanarPrevencion;
	private String correoRegistrado;
	private String correoRechazado;
	private boolean habilitaPrevencion;
	private boolean adjuntaOficio;
	private Integer diasSubsanarPrevencion;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	private String apiKey;
	private boolean habilitaResolucion;
	private boolean perfilSupervisorResolucion;
	private boolean perfilOperadorResolucion;
	private boolean resolucionPositivaObligatoria;
	private boolean resolucionNegativaObligatoria;
	private String correoResolucionPositiva;
	private String correoResolucionNegativa;

	public DetGestionUsuario() {
	}

	public DetGestionUsuario(Long idGestionUsuario, Proyecto proyecto, boolean habilitaPrevencion,
			boolean adjuntaOficio, Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo,
			boolean seccionSincronizada) {
		this.idGestionUsuario = idGestionUsuario;
		this.proyecto = proyecto;
		this.habilitaPrevencion = habilitaPrevencion;
		this.adjuntaOficio = adjuntaOficio;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	public DetGestionUsuario(Long idGestionUsuario, Proyecto proyecto, Boolean perfilSupervisorPrevencion,
			Boolean perfilOperadorPrevencion, Boolean perfilSupervisorConclusion, Boolean perfilOperadorConclusion,
			String correoConclusion, String correoPrevencion, String correoSubsanarPrevencion,
			String correoRegistrado, String correoRechazado,
			boolean habilitaPrevencion, boolean adjuntaOficio, Integer diasSubsanarPrevencion, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada,
			boolean habilitaResolucion, boolean perfilSupervisorResolucion, boolean perfilOperadorResolucion,
			boolean resolucionPositivaObligatoria, boolean resolucionNegativaObligatoria,
			String correoResolucionPositiva, String correoResolucionNegativa ) {
		this.idGestionUsuario = idGestionUsuario;
		this.proyecto = proyecto;
		this.perfilSupervisorPrevencion = perfilSupervisorPrevencion;
		this.perfilOperadorPrevencion = perfilOperadorPrevencion;
		this.perfilSupervisorConclusion = perfilSupervisorConclusion;
		this.perfilOperadorConclusion = perfilOperadorConclusion;
		this.correoConclusion = correoConclusion;
		this.correoPrevencion = correoPrevencion;
		this.correoSubsanarPrevencion = correoSubsanarPrevencion;
		this.correoRegistrado = correoRegistrado;
		this.correoRechazado = correoRechazado;
		this.habilitaPrevencion = habilitaPrevencion;
		this.adjuntaOficio = adjuntaOficio;
		this.diasSubsanarPrevencion = diasSubsanarPrevencion;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		this.habilitaResolucion = habilitaResolucion;
		this.perfilSupervisorResolucion = perfilSupervisorResolucion;
		this.perfilOperadorResolucion = perfilOperadorResolucion;
		this.resolucionPositivaObligatoria = resolucionPositivaObligatoria;
		this.resolucionNegativaObligatoria = resolucionNegativaObligatoria;
		this.correoResolucionPositiva = correoResolucionPositiva;
		this.correoResolucionNegativa = correoResolucionNegativa;
	}

	@Id
	@Column(name = "id_gestion_usuario", unique = true, nullable = false)
	public Long getIdGestionUsuario() {
		return this.idGestionUsuario;
	}

	public void setIdGestionUsuario(Long idGestionUsuario) {
		this.idGestionUsuario = idGestionUsuario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "perfil_supervisor_prevencion")
	public Boolean getPerfilSupervisorPrevencion() {
		return this.perfilSupervisorPrevencion;
	}

	public void setPerfilSupervisorPrevencion(Boolean perfilSupervisorPrevencion) {
		this.perfilSupervisorPrevencion = perfilSupervisorPrevencion;
	}

	@Column(name = "perfil_operador_prevencion")
	public Boolean getPerfilOperadorPrevencion() {
		return this.perfilOperadorPrevencion;
	}

	public void setPerfilOperadorPrevencion(Boolean perfilOperadorPrevencion) {
		this.perfilOperadorPrevencion = perfilOperadorPrevencion;
	}

	@Column(name = "perfil_supervisor_conclusion")
	public Boolean getPerfilSupervisorConclusion() {
		return this.perfilSupervisorConclusion;
	}

	public void setPerfilSupervisorConclusion(Boolean perfilSupervisorConclusion) {
		this.perfilSupervisorConclusion = perfilSupervisorConclusion;
	}

	@Column(name = "perfil_operador_conclusion")
	public Boolean getPerfilOperadorConclusion() {
		return this.perfilOperadorConclusion;
	}

	public void setPerfilOperadorConclusion(Boolean perfilOperadorConclusion) {
		this.perfilOperadorConclusion = perfilOperadorConclusion;
	}

	@Column(name = "correo_conclusion")
	public String getCorreoConclusion() {
		return this.correoConclusion;
	}

	public void setCorreoConclusion(String correoConclusion) {
		this.correoConclusion = correoConclusion;
	}

	@Column(name = "correo_prevencion")
	public String getCorreoPrevencion() {
		return this.correoPrevencion;
	}

	public void setCorreoPrevencion(String correoPrevencion) {
		this.correoPrevencion = correoPrevencion;
	}

	@Column(name = "correo_subsanar_prevencion")
	public String getCorreoSubsanarPrevencion() {
		return this.correoSubsanarPrevencion;
	}

	public void setCorreoSubsanarPrevencion(String correoSubsanarPrevencion) {
		this.correoSubsanarPrevencion = correoSubsanarPrevencion;
	}
	
	@Column(name = "correo_registro")
	public String getCorreoRegistrado() {
		return correoRegistrado;
	}

	public void setCorreoRegistrado(String correoRegistrado) {
		this.correoRegistrado = correoRegistrado;
	}

	@Column(name = "correo_rechazo")
	public String getCorreoRechazado() {
		return correoRechazado;
	}

	public void setCorreoRechazado(String correoRechazado) {
		this.correoRechazado = correoRechazado;
	}

	@Column(name = "habilita_prevencion", nullable = false)
	public boolean isHabilitaPrevencion() {
		return this.habilitaPrevencion;
	}

	public void setHabilitaPrevencion(boolean habilitaPrevencion) {
		this.habilitaPrevencion = habilitaPrevencion;
	}

	@Column(name = "adjunta_oficio", nullable = false)
	public boolean isAdjuntaOficio() {
		return this.adjuntaOficio;
	}

	public void setAdjuntaOficio(boolean adjuntaOficio) {
		this.adjuntaOficio = adjuntaOficio;
	}

	@Column(name = "dias_subsanar_prevencion")
	public Integer getDiasSubsanarPrevencion() {
		return this.diasSubsanarPrevencion;
	}

	public void setDiasSubsanarPrevencion(Integer diasSubsanarPrevencion) {
		this.diasSubsanarPrevencion = diasSubsanarPrevencion;
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

	@Column(name = "api_key", length = 60)
	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	@Column(name = "habilita_resolucion", nullable = false)
	public boolean isHabilitaResolucion() {
		return this.habilitaResolucion;
	}

	public void setHabilitaResolucion(boolean habilitaResolucion) {
		this.habilitaResolucion = habilitaResolucion;
	}

	@Column(name = "perfil_supervisor_resolucion", nullable = false)
	public boolean isPerfilSupervisorResolucion() {
		return this.perfilSupervisorResolucion;
	}

	public void setPerfilSupervisorResolucion(boolean perfilSupervisorResolucion) {
		this.perfilSupervisorResolucion = perfilSupervisorResolucion;
	}

	@Column(name = "perfil_operador_resolucion", nullable = false)
	public boolean isPerfilOperadorResolucion() {
		return this.perfilOperadorResolucion;
	}

	public void setPerfilOperadorResolucion(boolean perfilOperadorResolucion) {
		this.perfilOperadorResolucion = perfilOperadorResolucion;
	}

	@Column(name = "resolucion_positiva_obligatoria", nullable = false)
	public boolean isResolucionPositivaObligatoria() {
		return this.resolucionPositivaObligatoria;
	}

	public void setResolucionPositivaObligatoria(boolean resolucionPositivaObligatoria) {
		this.resolucionPositivaObligatoria = resolucionPositivaObligatoria;
	}

	@Column(name = "resolucion_negativa_obligatoria", nullable = false)
	public boolean isResolucionNegativaObligatoria() {
		return this.resolucionNegativaObligatoria;
	}

	public void setResolucionNegativaObligatoria(boolean resolucionNegativaObligatoria) {
		this.resolucionNegativaObligatoria = resolucionNegativaObligatoria;
	}

	@Column(name = "correo_resolucion_positiva")
	public String getCorreoResolucionPositiva() {
		return correoResolucionPositiva;
	}

	public void setCorreoResolucionPositiva(String correoResolucionPositiva) {
		this.correoResolucionPositiva = correoResolucionPositiva;
	}

	@Column(name = "correo_resolucion_negativa")
	public String getCorreoResolucionNegativa() {
		return correoResolucionNegativa;
	}

	public void setCorreoResolucionNegativa(String correoResolucionNegativa) {
		this.correoResolucionNegativa = correoResolucionNegativa;
	}

}

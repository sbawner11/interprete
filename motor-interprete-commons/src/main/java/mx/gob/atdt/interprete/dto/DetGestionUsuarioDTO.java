package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetGestionUsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1252759867509219025L;

	private Long idGestionUsuario;
	private ProyectoDTO proyectoDTO;
	private boolean perfilSupervisorPrevencion;
	private boolean perfilOperadorPrevencion;
	private boolean perfilSupervisorConclusion;
	private boolean perfilOperadorConclusion;
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

	/**
	 * 
	 */
	public DetGestionUsuarioDTO() {
	}

	/**
	 * 
	 * @param idGestionUsuario
	 * @param proyectoDTO
	 * @param perfilSupervisorPrevencion
	 * @param perfilOperadorPrevencion
	 * @param perfilSupervisorConclusion
	 * @param perfilOperadorConclusion
	 * @param correoConclusion
	 * @param correoPrevencion
	 * @param correoSubsanarPrevencion
	 * @param habilitaPrevencion
	 * @param adjuntaOficio
	 * @param diasSubsanarPrevencion
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 */
	public DetGestionUsuarioDTO(Long idGestionUsuario, ProyectoDTO proyectoDTO, boolean perfilSupervisorPrevencion,
			boolean perfilOperadorPrevencion, boolean perfilSupervisorConclusion, boolean perfilOperadorConclusion,
			String correoConclusion, String correoPrevencion, String correoSubsanarPrevencion,
			boolean habilitaPrevencion, boolean adjuntaOficio, Integer diasSubsanarPrevencion, Date fechaCreacion,
			Date fechaUltimaActualizacion) {
		this.idGestionUsuario = idGestionUsuario;
		this.proyectoDTO = proyectoDTO;
		this.perfilSupervisorPrevencion = perfilSupervisorPrevencion;
		this.perfilOperadorPrevencion = perfilOperadorPrevencion;
		this.perfilSupervisorConclusion = perfilSupervisorConclusion;
		this.perfilOperadorConclusion = perfilOperadorConclusion;
		this.correoConclusion = correoConclusion;
		this.correoPrevencion = correoPrevencion;
		this.correoSubsanarPrevencion = correoSubsanarPrevencion;
		this.habilitaPrevencion = habilitaPrevencion;
		this.adjuntaOficio = adjuntaOficio;
		this.diasSubsanarPrevencion = diasSubsanarPrevencion;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	/**
	 * Constructor utilizado por la NamedQuery DetGestionUsuario.findByIdProyecto y DetGestionUsuario.buscarProyecto
	 * 
	 * @param idGestionUsuario
	 * @param idProyecto
	 * @param perfilSupervisorPrevencion
	 * @param perfilOperadorPrevencion
	 * @param perfilSupervisorConclusion
	 * @param perfilOperadorConclusion
	 * @param correoConclusion
	 * @param correoPrevencion
	 * @param correoSubsanarPrevencion
	 * @param correoRegistrado
	 * @param correoRechazado
	 * @param habilitaPrevencion
	 * @param adjuntaOficio
	 * @param diasSubsanarPrevencion
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 * @param apiKey
	 * @param habilitaResolucion
	 * @param perfilSupervisorResolucion
	 * @param perfilOperadorResolucion
	 * @param resolucionPositivaObligatoria
	 * @param resolucionNegativaObligatoria
	 * @param correoResolucionPositiva
	 * @param correoResolucionNegativa
	 */
	public DetGestionUsuarioDTO(Long idGestionUsuario, Long idProyecto, boolean perfilSupervisorPrevencion,
			boolean perfilOperadorPrevencion, boolean perfilSupervisorConclusion, boolean perfilOperadorConclusion,
			String correoConclusion, String correoPrevencion, String correoSubsanarPrevencion, String correoRegistrado,
			String correoRechazado, boolean habilitaPrevencion, boolean adjuntaOficio, Integer diasSubsanarPrevencion,
			Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada, String apiKey,			
			boolean habilitaResolucion, boolean perfilSupervisorResolucion, boolean perfilOperadorResolucion, 
			boolean resolucionPositivaObligatoria, boolean resolucionNegativaObligatoria, 
			String correoResolucionPositiva, String correoResolucionNegativa ) {
		this.idGestionUsuario = idGestionUsuario;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
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
		this.apiKey = apiKey;		
		this.habilitaResolucion = habilitaResolucion;
		this.perfilSupervisorResolucion = perfilSupervisorResolucion;
		this.perfilOperadorResolucion = perfilOperadorResolucion;
		this.resolucionPositivaObligatoria = resolucionPositivaObligatoria;
		this.resolucionNegativaObligatoria = resolucionNegativaObligatoria;
		this.correoResolucionPositiva = correoResolucionPositiva;
		this.correoResolucionNegativa = correoResolucionNegativa;
	}

	public DetGestionUsuarioDTO(Long idProyecto, Long idGestionUsuario) {
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.idGestionUsuario = idGestionUsuario;
	}

	/**
	 * @return the idGestionUsuario
	 */
	public Long getIdGestionUsuario() {
		return idGestionUsuario;
	}

	/**
	 * @param idGestionUsuario the idGestionUsuario to set
	 */
	public void setIdGestionUsuario(Long idGestionUsuario) {
		this.idGestionUsuario = idGestionUsuario;
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
	 * @return the perfilSupervisorPrevencion
	 */
	public boolean getPerfilSupervisorPrevencion() {
		return perfilSupervisorPrevencion;
	}

	/**
	 * @param perfilSupervisorPrevencion the perfilSupervisorPrevencion to set
	 */
	public void setPerfilSupervisorPrevencion(boolean perfilSupervisorPrevencion) {
		this.perfilSupervisorPrevencion = perfilSupervisorPrevencion;
	}

	/**
	 * @return the perfilOperadorPrevencion
	 */
	public boolean getPerfilOperadorPrevencion() {
		return perfilOperadorPrevencion;
	}

	/**
	 * @param perfilOperadorPrevencion the perfilOperadorPrevencion to set
	 */
	public void setPerfilOperadorPrevencion(boolean perfilOperadorPrevencion) {
		this.perfilOperadorPrevencion = perfilOperadorPrevencion;
	}

	/**
	 * @return the perfilSupervisorConclusion
	 */
	public boolean getPerfilSupervisorConclusion() {
		return perfilSupervisorConclusion;
	}

	/**
	 * @param perfilSupervisorConclusion the perfilSupervisorConclusion to set
	 */
	public void setPerfilSupervisorConclusion(boolean perfilSupervisorConclusion) {
		this.perfilSupervisorConclusion = perfilSupervisorConclusion;
	}

	public boolean getPerfilOperadorConclusion() {
		return perfilOperadorConclusion;
	}

	public void setPerfilOperadorConclusion(boolean perfilOperadorConclusion) {
		this.perfilOperadorConclusion = perfilOperadorConclusion;
	}

	/**
	 * @return the correoConclusion
	 */
	public String getCorreoConclusion() {
		return correoConclusion;
	}

	/**
	 * @param correoConclusion the correoConclusion to set
	 */
	public void setCorreoConclusion(String correoConclusion) {
		this.correoConclusion = correoConclusion;
	}

	/**
	 * @return the correoPrevencion
	 */
	public String getCorreoPrevencion() {
		return correoPrevencion;
	}

	/**
	 * @param correoPrevencion the correoPrevencion to set
	 */
	public void setCorreoPrevencion(String correoPrevencion) {
		this.correoPrevencion = correoPrevencion;
	}

	/**
	 * @return the correoSubsanarPrevencion
	 */
	public String getCorreoSubsanarPrevencion() {
		return correoSubsanarPrevencion;
	}

	/**
	 * @param correoSubsanarPrevencion the correoSubsanarPrevencion to set
	 */
	public void setCorreoSubsanarPrevencion(String correoSubsanarPrevencion) {
		this.correoSubsanarPrevencion = correoSubsanarPrevencion;
	}

	/**
	 * @return the habilitaPrevencion
	 */
	public boolean isHabilitaPrevencion() {
		return habilitaPrevencion;
	}

	/**
	 * @param habilitaPrevencion the habilitaPrevencion to set
	 */
	public void setHabilitaPrevencion(boolean habilitaPrevencion) {
		this.habilitaPrevencion = habilitaPrevencion;
	}

	/**
	 * @return the adjuntaOficio
	 */
	public boolean isAdjuntaOficio() {
		return adjuntaOficio;
	}

	/**
	 * @param adjuntaOficio the adjuntaOficio to set
	 */
	public void setAdjuntaOficio(boolean adjuntaOficio) {
		this.adjuntaOficio = adjuntaOficio;
	}

	/**
	 * @return the diasSubsanarPrevencion
	 */
	public Integer getDiasSubsanarPrevencion() {
		return diasSubsanarPrevencion;
	}

	/**
	 * @param diasSubsanarPrevencion the diasSubsanarPrevencion to set
	 */
	public void setDiasSubsanarPrevencion(Integer diasSubsanarPrevencion) {
		this.diasSubsanarPrevencion = diasSubsanarPrevencion;
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
	 * @return the fechaUltimaActualizacion
	 */
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	/**
	 * @param fechaUltimaActualizacion the fechaUltimaActualizacion to set
	 */
	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
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

	public String getCorreoRegistrado() {
		return correoRegistrado;
	}

	public void setCorreoRegistrado(String correoRegistrado) {
		this.correoRegistrado = correoRegistrado;
	}

	public String getCorreoRechazado() {
		return correoRechazado;
	}

	public void setCorreoRechazado(String correoRechazado) {
		this.correoRechazado = correoRechazado;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
		
	@Override
	public String toString() {
		return "DetGestionUsuarioDTO [idGestionUsuario=" + idGestionUsuario + ", proyectoDTO=" + proyectoDTO
				+ ", perfilSupervisorPrevencion=" + perfilSupervisorPrevencion + ", perfilOperadorPrevencion="
				+ perfilOperadorPrevencion + ", perfilSupervisorConclusion=" + perfilSupervisorConclusion
				+ ", perfilOperadorConclusion=" + perfilOperadorConclusion + ", correoConclusion=" + correoConclusion
				+ ", correoPrevencion=" + correoPrevencion + ", correoSubsanarPrevencion=" + correoSubsanarPrevencion
				+ ", correoRegistrado=" + correoRegistrado + ", correoRechazado=" + correoRechazado
				+ ", habilitaPrevencion=" + habilitaPrevencion + ", adjuntaOficio=" + adjuntaOficio
				+ ", diasSubsanarPrevencion=" + diasSubsanarPrevencion + ", fechaCreacion=" + fechaCreacion
				+ ", fechaUltimaActualizacion=" + fechaUltimaActualizacion + ", activo=" + activo
				+ ", seccionSincronizada=" + seccionSincronizada + ", apiKey=" + apiKey + "]";
	}

	public boolean isHabilitaResolucion() {
		return this.habilitaResolucion;
	}

	public void setHabilitaResolucion(boolean habilitaResolucion) {
		this.habilitaResolucion = habilitaResolucion;
	}

	public boolean isPerfilSupervisorResolucion() {
		return this.perfilSupervisorResolucion;
	}

	public void setPerfilSupervisorResolucion(boolean perfilSupervisorResolucion) {
		this.perfilSupervisorResolucion = perfilSupervisorResolucion;
	}

	public boolean isPerfilOperadorResolucion() {
		return this.perfilOperadorResolucion;
	}

	public void setPerfilOperadorResolucion(boolean perfilOperadorResolucion) {
		this.perfilOperadorResolucion = perfilOperadorResolucion;
	}

	public boolean isResolucionPositivaObligatoria() {
		return this.resolucionPositivaObligatoria;
	}

	public void setResolucionPositivaObligatoria(boolean resolucionPositivaObligatoria) {
		this.resolucionPositivaObligatoria = resolucionPositivaObligatoria;
	}

	public boolean isResolucionNegativaObligatoria() {
		return this.resolucionNegativaObligatoria;
	}

	public void setResolucionNegativaObligatoria(boolean resolucionNegativaObligatoria) {
		this.resolucionNegativaObligatoria = resolucionNegativaObligatoria;
	}

	public String getCorreoResolucionPositiva() {
		return correoResolucionPositiva;
	}

	public void setCorreoResolucionPositiva(String correoResolucionPositiva) {
		this.correoResolucionPositiva = correoResolucionPositiva;
	}

	public String getCorreoResolucionNegativa() {
		return correoResolucionNegativa;
	}

	public void setCorreoResolucionNegativa(String correoResolucionNegativa) {
		this.correoResolucionNegativa = correoResolucionNegativa;
	}	
	
}

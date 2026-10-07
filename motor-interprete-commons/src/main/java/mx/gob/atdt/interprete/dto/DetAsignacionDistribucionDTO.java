package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetAsignacionDistribucionDTO implements Serializable{

	private static final long serialVersionUID = -5382067250779871523L;
	private Long idAsignacionDistribucion;
	private ComponenteDTO componenteDistribucionDTO;
	private Long idElementoAsignado;
	private String desElementoAsignado;
	private UsuarioDTO usuarioAsignadoDTO;
	private UsuarioDTO administradorAsignaDTO;
	private String rol;
	private Date fechaAsignacion;
	private Date fechaDesvinculacion;
	private boolean activo;
	
	/**
	 * Constructor vacio de la clase
	 */
	public DetAsignacionDistribucionDTO() {
		componenteDistribucionDTO = new ComponenteDTO();
		usuarioAsignadoDTO = new UsuarioDTO();
		administradorAsignaDTO = new UsuarioDTO();
	}
	
	/**
	 * Constructor utilizado por las @NamedQueries = {DetAsignacionDistribucion.findByIdUsuarioAsignado,
	 *  DetAsignacionDistribucion.findAll, DetAsignacionDistribucion.findByIdUsuarioAsignadoAndIdElementoAsignado}
	 * @param idAsignacionDistribucion
	 * @param idElementoAsignado
	 * @param desElementoAsignado
	 * @param rol
	 * @param fechaAsignacion
	 * @param activo
	 * @param idComponente
	 * @param tituloCampo
	 * @param idTipoComponente
	 * @param idUsuarioAsignado
	 * @param correoUsuarioAsignado
	 * @param idAdministradorAsigna
	 * @param correoAdministradorAsigna
	 */
	@SuppressWarnings({"java:S107"})
	public DetAsignacionDistribucionDTO(Long idAsignacionDistribucion, Long idElementoAsignado, String desElementoAsignado,
			String rol, Date fechaAsignacion, boolean activo, Long idComponente, String tituloCampo, Integer idTipoComponente,
			long idUsuarioAsignado, String correoUsuarioAsignado,  String nombreAsignado, String primerApellidoAsignado,
			String segundoApellidoAsignado,	long idAdministradorAsigna, String correoAdministradorAsigna) {
		this.idAsignacionDistribucion = idAsignacionDistribucion;
		this.componenteDistribucionDTO = new  ComponenteDTO();
		this.componenteDistribucionDTO.setIdComponente(idComponente);
		this.componenteDistribucionDTO.setTituloCampo(tituloCampo);
		this.componenteDistribucionDTO.setCatTipoComponenteDTO(new CatTipoComponenteDTO(idTipoComponente));
		this.idElementoAsignado = idElementoAsignado;
		this.desElementoAsignado = desElementoAsignado;
		this.usuarioAsignadoDTO = new UsuarioDTO();
		this.usuarioAsignadoDTO.setIdUsuarioLlaveCdmx(idUsuarioAsignado);
		this.usuarioAsignadoDTO.setCorreo(correoUsuarioAsignado);
		this.usuarioAsignadoDTO.setNombre(nombreAsignado);
		this.usuarioAsignadoDTO.setPrimerApellido(primerApellidoAsignado);
		this.usuarioAsignadoDTO.setSegundoApellido(segundoApellidoAsignado);
		this.administradorAsignaDTO = new UsuarioDTO();
		this.administradorAsignaDTO.setIdUsuarioLlaveCdmx(idAdministradorAsigna);
		this.administradorAsignaDTO.setCorreo(correoAdministradorAsigna);
		this.rol = rol;
		this.fechaAsignacion = fechaAsignacion;
		this.activo = activo;
	}

	public Long getIdAsignacionDistribucion() {
		return idAsignacionDistribucion;
	}

	public void setIdAsignacionDistribucion(Long idAsignacionDistribucion) {
		this.idAsignacionDistribucion = idAsignacionDistribucion;
	}

	public ComponenteDTO getComponenteDistribucionDTO() {
		return componenteDistribucionDTO;
	}

	public void setComponenteDistribucionDTO(ComponenteDTO componenteDistribucionDTO) {
		this.componenteDistribucionDTO = componenteDistribucionDTO;
	}

	public Long getIdElementoAsignado() {
		return idElementoAsignado;
	}

	public void setIdElementoAsignado(Long idElementoAsignado) {
		this.idElementoAsignado = idElementoAsignado;
	}

	public UsuarioDTO getUsuarioAsignadoDTO() {
		return usuarioAsignadoDTO;
	}

	public void setUsuarioAsignadoDTO(UsuarioDTO usuarioAsignadoDTO) {
		this.usuarioAsignadoDTO = usuarioAsignadoDTO;
	}

	public UsuarioDTO getAdministradorAsignaDTO() {
		return administradorAsignaDTO;
	}

	public void setAdministradorAsignaDTO(UsuarioDTO administradorAsignaDTO) {
		this.administradorAsignaDTO = administradorAsignaDTO;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getDesElementoAsignado() {
		return desElementoAsignado;
	}

	public void setDesElementoAsignado(String desElementoAsignado) {
		this.desElementoAsignado = desElementoAsignado;
	}
	
	public Date getFechaDesvinculacion() {
		return fechaDesvinculacion;
	}

	public void setFechaDesvinculacion(Date fechaDesvinculacion) {
		this.fechaDesvinculacion = fechaDesvinculacion;
	}

}

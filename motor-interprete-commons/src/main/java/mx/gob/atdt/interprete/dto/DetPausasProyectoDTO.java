package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetPausasProyectoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8568809029469125024L;
	
	private Long idPausaProyecto;
	private CatMotivosPausaDTO catMotivosPausaDTO;
	private ProyectoDTO proyectoDTO;
	private UsuarioDTO usuarioDTO;
	private String observacionesPausa;
	private Date fechaCreacion;
	
	/**
	 * 
	 */
	public DetPausasProyectoDTO() {
		catMotivosPausaDTO = new CatMotivosPausaDTO();
		proyectoDTO = new ProyectoDTO();
		usuarioDTO = new UsuarioDTO();
	}

	/**
	 * @param idPausaProyecto
	 */
	public DetPausasProyectoDTO(Long idPausaProyecto) {
		this.idPausaProyecto = idPausaProyecto;
	}

	/**
	 * @param idPausaProyecto
	 * @param catMotivosPausaDTO
	 * @param proyectoDTO
	 * @param usuarioDTO
	 * @param observacionesPausa
	 * @param fechaCreacion
	 */
	public DetPausasProyectoDTO(Long idPausaProyecto, CatMotivosPausaDTO catMotivosPausaDTO, ProyectoDTO proyectoDTO,
			UsuarioDTO usuarioDTO, String observacionesPausa, Date fechaCreacion) {
		this.idPausaProyecto = idPausaProyecto;
		this.catMotivosPausaDTO = catMotivosPausaDTO;
		this.proyectoDTO = proyectoDTO;
		this.usuarioDTO = usuarioDTO;
		this.observacionesPausa = observacionesPausa;
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the idPausaProyecto
	 */
	public Long getIdPausaProyecto() {
		return idPausaProyecto;
	}

	/**
	 * @param idPausaProyecto the idPausaProyecto to set
	 */
	public void setIdPausaProyecto(Long idPausaProyecto) {
		this.idPausaProyecto = idPausaProyecto;
	}

	/**
	 * @return the catMotivosPausaDTO
	 */
	public CatMotivosPausaDTO getCatMotivosPausaDTO() {
		return catMotivosPausaDTO;
	}

	/**
	 * @param catMotivosPausaDTO the catMotivosPausaDTO to set
	 */
	public void setCatMotivosPausaDTO(CatMotivosPausaDTO catMotivosPausaDTO) {
		this.catMotivosPausaDTO = catMotivosPausaDTO;
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
	 * @return the observacionesPausa
	 */
	public String getObservacionesPausa() {
		return observacionesPausa;
	}

	/**
	 * @param observacionesPausa the observacionesPausa to set
	 */
	public void setObservacionesPausa(String observacionesPausa) {
		this.observacionesPausa = observacionesPausa;
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
	
}

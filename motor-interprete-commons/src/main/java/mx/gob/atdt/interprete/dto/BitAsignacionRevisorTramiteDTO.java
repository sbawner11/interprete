package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class BitAsignacionRevisorTramiteDTO implements Serializable{
	
	private static final long serialVersionUID = -6902555817518471383L;
	private Long idBitAsignacion;
	private Date fechaAsignacion;
	private TramiteDTO tamiteDTO;
	private UsuarioDTO usuarioRevisorDTO;
	private UsuarioDTO usuarioAsignaDTO;
	
	/**
	 * Constructor vacio que inicializa los objetos 
	 * contenidos en esta clase.
	 */
	public BitAsignacionRevisorTramiteDTO() {
		usuarioAsignaDTO = new UsuarioDTO();
		usuarioRevisorDTO = new UsuarioDTO();
	}

	public Long getIdBitAsignacion() {
		return idBitAsignacion;
	}

	public void setIdBitAsignacion(Long idBitAsignacion) {
		this.idBitAsignacion = idBitAsignacion;
	}

	public Date getFechaAsignacion() {
		return fechaAsignacion;
	}

	public void setFechaAsignacion(Date fechaAsignacion) {
		this.fechaAsignacion = fechaAsignacion;
	}

	public UsuarioDTO getUsuarioRevisorDTO() {
		return usuarioRevisorDTO;
	}

	public void setUsuarioRevisorDTO(UsuarioDTO usuarioRevisorDTO) {
		this.usuarioRevisorDTO = usuarioRevisorDTO;
	}

	public UsuarioDTO getUsuarioAsignaDTO() {
		return usuarioAsignaDTO;
	}

	public void setUsuarioAsignaDTO(UsuarioDTO usuarioAsignaDTO) {
		this.usuarioAsignaDTO = usuarioAsignaDTO;
	}

	public TramiteDTO getTamiteDTO() {
		return tamiteDTO;
	}

	public void setTamiteDTO(TramiteDTO tamiteDTO) {
		this.tamiteDTO = tamiteDTO;
	}

}

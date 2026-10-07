package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;


public class BitSincronizacionDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4873857282875517378L;
	
	private long idSincronizacion;
	private Date fechaSincronizacion;
	private UsuarioDTO usuarioDTO;

	public BitSincronizacionDTO() {
	}	

	public BitSincronizacionDTO(Long idSincronizacion, Date fechaSincronizacion, UsuarioDTO usuarioDTO) {
        this.idSincronizacion = idSincronizacion;
        this.fechaSincronizacion = fechaSincronizacion;
        this.usuarioDTO = usuarioDTO;
    }
	    
	/**
	 * @return the idSincronizacion
	 */
	public Long getIdSincronizacion() {
		return idSincronizacion;
	}

	/**
	 * @param idSincronizacion the idSincronizacion to set
	 */
	public void setIdSincronizacion(Long idSincronizacion) {
		this.idSincronizacion = idSincronizacion;
	}

	/**
	 * @return the fechaSincronizacion
	 */
	public Date getFechaSincronizacion() {
		return fechaSincronizacion;
	}

	/**
	 * @param fechaSincronizacion the fechaSincronizacion to set
	 */
	public void setFechaSincronizacion(Date fechaSincronizacion) {
		this.fechaSincronizacion = fechaSincronizacion;
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
	
}

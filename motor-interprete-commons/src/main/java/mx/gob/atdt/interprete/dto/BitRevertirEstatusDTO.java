package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class BitRevertirEstatusDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6425861989168545261L;
	
	private long idReversion;
	private TramiteDTO tramite;	
	private UsuarioDTO usuarioDTO;
	private CatEstatusTramiteDTO estatusTramiteDTO;
	private String respuestaPrevencionConclusion;
	private Date fechaReversion;
	
	/**
	 * 
	 */
	public BitRevertirEstatusDTO() {
	}

	/**
	 * @return the idReversion
	 */
	public long getIdReversion() {
		return idReversion;
	}

	/**
	 * @param idReversion the idReversion to set
	 */
	public void setIdReversion(long idReversion) {
		this.idReversion = idReversion;
	}

	/**
	 * @return the tramite
	 */
	public TramiteDTO getTramite() {
		return tramite;
	}

	/**
	 * @param tramite the tramite to set
	 */
	public void setTramite(TramiteDTO tramite) {
		this.tramite = tramite;
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
	 * @return the estatusTramiteDTO
	 */
	public CatEstatusTramiteDTO getEstatusTramiteDTO() {
		return estatusTramiteDTO;
	}

	/**
	 * @param estatusTramiteDTO the estatusTramiteDTO to set
	 */
	public void setEstatusTramiteDTO(CatEstatusTramiteDTO estatusTramiteDTO) {
		this.estatusTramiteDTO = estatusTramiteDTO;
	}
	
	/**
	 * @return the respuestaPrevencionConclusion
	 */
	public String getRespuestaPrevencionConclusion() {
		return respuestaPrevencionConclusion;
	}

	/**
	 * @param respuestaPrevencionConclusion the respuestaPrevencionConclusion to set
	 */
	public void setRespuestaPrevencionConclusion(String respuestaPrevencionConclusion) {
		this.respuestaPrevencionConclusion = respuestaPrevencionConclusion;
	}

	/**
	 * @return the fechaReversion
	 */
	public Date getFechaReversion() {
		return fechaReversion;
	}

	/**
	 * @param fechaReversion the fechaReversion to set
	 */
	public void setFechaReversion(Date fechaReversion) {
		this.fechaReversion = fechaReversion;
	}	
}

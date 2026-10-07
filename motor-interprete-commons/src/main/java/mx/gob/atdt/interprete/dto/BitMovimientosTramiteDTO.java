package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BitMovimientosTramiteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1195503721786395858L;

	private long idMovimientoTramite;
	private CatTiposMovimientoDTO catTiposMovimientoDTO;
	private UsuarioDTO usuarioDTO;
	private long idTramite;
	private String comentarios;
	private Date fechaMovimiento;
	
	/**
	 * 
	 */
	public BitMovimientosTramiteDTO() {
	}

	/**
	 * @param idMovimientoTramite
	 * @param catTiposMovimientoDTO
	 * @param usuarioDTO
	 * @param idTramite
	 * @param comentarios
	 * @param fechaMovimiento
	 */
	public BitMovimientosTramiteDTO(long idMovimientoTramite, CatTiposMovimientoDTO catTiposMovimientoDTO,
			UsuarioDTO usuarioDTO, long idTramite, String comentarios, Date fechaMovimiento) {
		this.idMovimientoTramite = idMovimientoTramite;
		this.catTiposMovimientoDTO = catTiposMovimientoDTO;
		this.usuarioDTO = usuarioDTO;
		this.idTramite = idTramite;
		this.comentarios = comentarios;
		this.fechaMovimiento = fechaMovimiento;
	}

	public BitMovimientosTramiteDTO(long idMovimientoTramite, int idTipoMovimiento,
			long idUsuario, long idTramite, String comentarios, Date fechaMovimiento) {
		this.idMovimientoTramite = idMovimientoTramite;
		this.catTiposMovimientoDTO = new CatTiposMovimientoDTO(idTipoMovimiento);
		this.usuarioDTO = new UsuarioDTO(idUsuario);
		this.idTramite = idTramite;
		this.comentarios = comentarios;
		this.fechaMovimiento = fechaMovimiento;
	}
	
	/*
	 * Metodo para obtener únicamente el estatus al que se cambia
	 */
	public Integer getIdEstatus() {
	    if (comentarios == null || catTiposMovimientoDTO.getIdTipoMovimiento() != 3 ) {
	        return null;
	    }
	    Pattern pattern = Pattern.compile("Se actualiza estatus a\\s*:\\s*(\\d+)");
	    Matcher matcher = pattern.matcher(comentarios);
	    if (matcher.find()) {
	    	return Integer.parseInt(matcher.group(1));
	    	//return Long.parseLong(matcher.group(1));
	    }
	    return null;
	}
	
	/*
	 * Metodo para obtener las observaciones al cambio de estatus
	 */
	public String getObservaciones() {
	    if (comentarios == null || catTiposMovimientoDTO.getIdTipoMovimiento() != 3 ) {
	        return null;
	    }
	    Pattern pattern = Pattern.compile("Observaciones\\s*:\\s*(.*)");
	    Matcher matcher = pattern.matcher(comentarios);
	    if (matcher.find()) {
	        return matcher.group(1).trim();
	    }
	    return null;
	}

	/**
	 * @return the idMovimientoTramite
	 */
	public long getIdMovimientoTramite() {
		return idMovimientoTramite;
	}

	/**
	 * @param idMovimientoTramite the idMovimientoTramite to set
	 */
	public void setIdMovimientoTramite(long idMovimientoTramite) {
		this.idMovimientoTramite = idMovimientoTramite;
	}

	/**
	 * @return the catTiposMovimientoDTO
	 */
	public CatTiposMovimientoDTO getCatTiposMovimientoDTO() {
		return catTiposMovimientoDTO;
	}

	/**
	 * @param catTiposMovimientoDTO the catTiposMovimientoDTO to set
	 */
	public void setCatTiposMovimientoDTO(CatTiposMovimientoDTO catTiposMovimientoDTO) {
		this.catTiposMovimientoDTO = catTiposMovimientoDTO;
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
	 * @return the idTramite
	 */
	public long getIdTramite() {
		return idTramite;
	}

	/**
	 * @param idTramite the idTramite to set
	 */
	public void setIdTramite(long idTramite) {
		this.idTramite = idTramite;
	}

	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the fechaMovimiento
	 */
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	/**
	 * @param fechaMovimiento the fechaMovimiento to set
	 */
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}
	
}

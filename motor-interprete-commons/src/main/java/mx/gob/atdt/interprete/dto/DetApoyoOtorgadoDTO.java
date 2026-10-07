package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class DetApoyoOtorgadoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9144228587173210981L;

	private Long idApoyo;
	private DetHomeDTO detHomeDTO;
	private String descripcionApoyoOtorgado;
	private int orden;
	private boolean activo;

	/**
	 * 
	 */
	public DetApoyoOtorgadoDTO() {
	}

	/**
	 * @param idApoyo
	 * @param detHomeDTO
	 * @param descripcionApoyoOtorgado
	 * @param orden
	 * @param activo
	 */
	public DetApoyoOtorgadoDTO(Long idApoyo, Long idDetalleHome, String descripcionApoyoOtorgado, int orden, boolean activo) {
		this.idApoyo = idApoyo;
		this.detHomeDTO = new DetHomeDTO(idDetalleHome);
		this.descripcionApoyoOtorgado = descripcionApoyoOtorgado;
		this.orden = orden;
		this.activo = activo;
	}

	/**
	 * @return the idApoyo
	 */
	public Long getIdApoyo() {
		return idApoyo;
	}

	/**
	 * @param idApoyo the idApoyo to set
	 */
	public void setIdApoyo(Long idApoyo) {
		this.idApoyo = idApoyo;
	}

	/**
	 * @return the detHomeDTO
	 */
	public DetHomeDTO getDetHomeDTO() {
		return detHomeDTO;
	}

	/**
	 * @param detHomeDTO the detHomeDTO to set
	 */
	public void setDetHomeDTO(DetHomeDTO detHomeDTO) {
		this.detHomeDTO = detHomeDTO;
	}

	/**
	 * @return the descripcionApoyoOtorgado
	 */
	public String getDescripcionApoyoOtorgado() {
		return descripcionApoyoOtorgado;
	}

	/**
	 * @param descripcionApoyoOtorgado the descripcionApoyoOtorgado to set
	 */
	public void setDescripcionApoyoOtorgado(String descripcionApoyoOtorgado) {
		this.descripcionApoyoOtorgado = descripcionApoyoOtorgado;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(int orden) {
		this.orden = orden;
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

	@Override
	public String toString() {
		return "DetApoyoOtorgadoDTO [idApoyo=" + idApoyo + ", detalleHomeDTO=" + detHomeDTO
				+ ", descripcionApoyoOtorgado=" + descripcionApoyoOtorgado + ", orden=" + orden + "]";
	}
}

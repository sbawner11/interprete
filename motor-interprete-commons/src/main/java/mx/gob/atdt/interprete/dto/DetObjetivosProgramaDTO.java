package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class DetObjetivosProgramaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -263660971993212914L;

	private Long idObjetivo;
	private DetHomeDTO detHomeDTO;
	private String descripcionObjetivo;
	private int orden;
	private boolean activo;

	/**
	 * 
	 */
	public DetObjetivosProgramaDTO() {
	}

	/**
	 * @param idObjetivo
	 * @param detHomeDTO
	 * @param descripcionObjetivo
	 * @param orden
	 * @param activo
	 */
	public DetObjetivosProgramaDTO(Long idObjetivo, Long idDetalleHome, String descripcionObjetivo, int orden, boolean activo) {
		this.idObjetivo = idObjetivo;
		this.detHomeDTO = new DetHomeDTO(idDetalleHome);
		this.descripcionObjetivo = descripcionObjetivo;
		this.orden = orden;
		this.activo = activo;
	}

	/**
	 * @return the idObjetivo
	 */
	public Long getIdObjetivo() {
		return idObjetivo;
	}

	/**
	 * @param idObjetivo the idObjetivo to set
	 */
	public void setIdObjetivo(Long idObjetivo) {
		this.idObjetivo = idObjetivo;
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
	 * @return the descripcionObjetivo
	 */
	public String getDescripcionObjetivo() {
		return descripcionObjetivo;
	}

	/**
	 * @param descripcionObjetivo the descripcionObjetivo to set
	 */
	public void setDescripcionObjetivo(String descripcionObjetivo) {
		this.descripcionObjetivo = descripcionObjetivo;
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
		return "DetObjetivosProgramaDTO [idObjetivo=" + idObjetivo + ", detHomeDTO=" + detHomeDTO
				+ ", descripcionObjetivo=" + descripcionObjetivo + ", orden=" + orden + "]";
	}

}

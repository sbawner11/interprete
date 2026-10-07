package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class DetPoblacionObjetivoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 364319641049645566L;

	private Long idPoblacionObjetivo;
	private DetHomeDTO detHomeDTO;
	private String descripcionPoblacionObjetivo;
	private int orden;
	private boolean activo;

	/**
	 * 
	 */
	public DetPoblacionObjetivoDTO() {
	}

	/**
	 * @param idPoblacionObjetivo
	 * @param iddetalleHome
	 * @param descripcionPoblacionObjetivo
	 * @param orden
	 * @param activo
	 */
	public DetPoblacionObjetivoDTO(Long idPoblacionObjetivo, Long idDetalleHome,
			String descripcionPoblacionObjetivo, int orden, boolean activo) {
		this.idPoblacionObjetivo = idPoblacionObjetivo;
		this.detHomeDTO = new DetHomeDTO(idDetalleHome);
		this.descripcionPoblacionObjetivo = descripcionPoblacionObjetivo;
		this.orden = orden;
		this.activo = activo;
	}

	/**
	 * Constructor utilizado por la NamedQuery
	 * DetPoblacionObjetivo.findByIdDetalleHome
	 * 
	 * @param idPoblacionObjetivo
	 * @param idDetalleHome
	 * @param descripcionPoblacionObjetivo
	 */
	public DetPoblacionObjetivoDTO(Long idPoblacionObjetivo, Long idDetalleHome,
			String descripcionPoblacionObjetivo) {
		this.idPoblacionObjetivo = idPoblacionObjetivo;
		this.detHomeDTO = new DetHomeDTO(idDetalleHome);
		this.descripcionPoblacionObjetivo = descripcionPoblacionObjetivo;
	}

	/**
	 * @return the idPoblacionObjetivo
	 */
	public Long getIdPoblacionObjetivo() {
		return idPoblacionObjetivo;
	}

	/**
	 * @param idPoblacionObjetivo the idPoblacionObjetivo to set
	 */
	public void setIdPoblacionObjetivo(Long idPoblacionObjetivo) {
		this.idPoblacionObjetivo = idPoblacionObjetivo;
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
	 * @return the descripcionPoblacionObjetivo
	 */
	public String getDescripcionPoblacionObjetivo() {
		return descripcionPoblacionObjetivo;
	}

	/**
	 * @param descripcionPoblacionObjetivo the descripcionPoblacionObjetivo to set
	 */
	public void setDescripcionPoblacionObjetivo(String descripcionPoblacionObjetivo) {
		this.descripcionPoblacionObjetivo = descripcionPoblacionObjetivo;
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
		return "DetPoblacionObjetivoDTO [idPoblacionObjetivo=" + idPoblacionObjetivo + ", detHomeDTO="
				+ detHomeDTO + ", descripcionPoblacionObjetivo=" + descripcionPoblacionObjetivo + ", orden=" + orden
				+ "]";
	}	

}

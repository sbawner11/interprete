package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class DetRequisitoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3936451863200676904L;

	private Long idRequisito;
	private DetHomeDTO detHomeDTO;
	private String descripcionRequisito;
	private int orden;
	private boolean activo;

	/**
	 * 
	 */
	public DetRequisitoDTO() {
	}

	public DetRequisitoDTO(Long idRequisito) {
		this.idRequisito = idRequisito;
	}

	/**
	 * @param idRequisito
	 * @param detHomeDTO
	 * @param descripcionRequisito
	 * @param orden
	 */
	public DetRequisitoDTO(Long idRequisito, Long idDetalleHomeDTO, String descripcionRequisito, int orden, boolean activo) {
		this.idRequisito = idRequisito;
		this.detHomeDTO = new DetHomeDTO(idDetalleHomeDTO);
		this.descripcionRequisito = descripcionRequisito;
		this.orden = orden;
		this.activo = activo;
	}

	/**
	 * @return the idRequisito
	 */
	public Long getIdRequisito() {
		return idRequisito;
	}

	/**
	 * @param idRequisito the idRequisito to set
	 */
	public void setIdRequisito(Long idRequisito) {
		this.idRequisito = idRequisito;
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
	 * @return the descripcionRequisito
	 */
	public String getDescripcionRequisito() {
		return descripcionRequisito;
	}

	/**
	 * @param descripcionRequisito the descripcionRequisito to set
	 */
	public void setDescripcionRequisito(String descripcionRequisito) {
		this.descripcionRequisito = descripcionRequisito;
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
		return "DetRequisitoDTO [idRequisito=" + idRequisito + ", detHomeDTO=" + detHomeDTO
				+ ", descripcionRequisito=" + descripcionRequisito + ", orden=" + orden + ", activo=" + activo + "]";
	}

}

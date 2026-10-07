package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class DetEspecificacionRequisitoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3975242768331518661L;

	private Long idEspecificacion;
	private DetRequisitoDTO detRequisitoDTO;
	private String descripcionEspecificacion;
	private int orden;
	private boolean activo;

	/**
	 * 
	 */
	public DetEspecificacionRequisitoDTO() {
	}

	public DetEspecificacionRequisitoDTO(Long idEspecificacion) {
		this.idEspecificacion = idEspecificacion;
	}
	
	/**
	 * @param idEspecificacion
	 * @param detRequisitoDTO
	 * @param descripcionEspecificacion
	 * @param orden
	 * @param activo
	 */
	public DetEspecificacionRequisitoDTO(Long idEspecificacion, Long idRequisito,
			String descripcionEspecificacion, int orden, boolean activo) {
		this.idEspecificacion = idEspecificacion;
		this.detRequisitoDTO = new DetRequisitoDTO(idRequisito);
		this.descripcionEspecificacion = descripcionEspecificacion;
		this.orden = orden;
		this.activo = activo;
	}

	/**
	 * @return the idEspecificacion
	 */
	public Long getIdEspecificacion() {
		return idEspecificacion;
	}

	/**
	 * @param idEspecificacion the idEspecificacion to set
	 */
	public void setIdEspecificacion(Long idEspecificacion) {
		this.idEspecificacion = idEspecificacion;
	}

	/**
	 * @return the detRequisitoDTO
	 */
	public DetRequisitoDTO getDetRequisitoDTO() {
		return detRequisitoDTO;
	}

	/**
	 * @param detRequisitoDTO the detRequisitoDTO to set
	 */
	public void setDetRequisitoDTO(DetRequisitoDTO detRequisitoDTO) {
		this.detRequisitoDTO = detRequisitoDTO;
	}

	/**
	 * @return the descripcionEspecificacion
	 */
	public String getDescripcionEspecificacion() {
		return descripcionEspecificacion;
	}

	/**
	 * @param descripcionEspecificacion the descripcionEspecificacion to set
	 */
	public void setDescripcionEspecificacion(String descripcionEspecificacion) {
		this.descripcionEspecificacion = descripcionEspecificacion;
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
		return "DetEspecificacionRequisitoDTO [idEspecificacion=" + idEspecificacion + ", detRequisitoDTO="
				+ detRequisitoDTO + ", descripcionEspecificacion=" + descripcionEspecificacion + ", orden=" + orden
				+ "]";
	}
	
}

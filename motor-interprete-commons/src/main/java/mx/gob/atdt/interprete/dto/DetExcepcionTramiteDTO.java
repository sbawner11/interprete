package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class DetExcepcionTramiteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7686430700152568434L;

	private Long idExcepcion;
	private DetHomeDTO detHomeDTO;
	private String descripcionExcepcion;
	private int orden;
	private boolean activo;

	/**
	 * 
	 */
	public DetExcepcionTramiteDTO() {
	}

	/**
	 * @param idExcepcion
	 * @param detalleHomeDTO
	 * @param descripcionExcepcion
	 * @param orden
	 */
	public DetExcepcionTramiteDTO(Long idExcepcion, Long idDetalleHome, String descripcionExcepcion,
			int orden) {
		this.idExcepcion = idExcepcion;
		this.detHomeDTO = new DetHomeDTO(idDetalleHome);
		this.descripcionExcepcion = descripcionExcepcion;
		this.orden = orden;
	}

	/**
	 * @return the idExcepcion
	 */
	public Long getIdExcepcion() {
		return idExcepcion;
	}

	/**
	 * @param idExcepcion the idExcepcion to set
	 */
	public void setIdExcepcion(Long idExcepcion) {
		this.idExcepcion = idExcepcion;
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
	 * @return the descripcionExcepcion
	 */
	public String getDescripcionExcepcion() {
		return descripcionExcepcion;
	}

	/**
	 * @param descripcionExcepcion the descripcionExcepcion to set
	 */
	public void setDescripcionExcepcion(String descripcionExcepcion) {
		this.descripcionExcepcion = descripcionExcepcion;
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
		return "DetExcepcionTramiteDTO [idExcepcion=" + idExcepcion + ", detalleHomeDTO=" + detHomeDTO
				+ ", descripcionExcepcion=" + descripcionExcepcion + ", orden=" + orden + "]";
	}	

}

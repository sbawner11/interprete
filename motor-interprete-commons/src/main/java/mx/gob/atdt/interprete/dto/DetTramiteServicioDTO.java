package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class DetTramiteServicioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3618478002050784071L;

	private Long idDetalleTramite;
	private DetHomeDTO detHomeDTO;
	private boolean habilitaCostoTramite;
	private String descripcionCostoTramite;
	private boolean habilitaExcepcionTramite;

	/**
	 * 
	 */
	public DetTramiteServicioDTO() {
	}

	/**
	 * @param idDetalleTramite
	 * @param detHomeDTO
	 * @param habilitaRequisitosTramite
	 * @param habilitaCostoTramite
	 * @param descripcionCostoTramite
	 * @param habilitaExcepcionTramite
	 * @param descripcionExcepcionTramite
	 */
	public DetTramiteServicioDTO(Long idDetalleTramite, DetHomeDTO detHomeDTO,
			boolean habilitaCostoTramite, String descripcionCostoTramite, boolean habilitaExcepcionTramite) {
		this.idDetalleTramite = idDetalleTramite;
		this.detHomeDTO = detHomeDTO;
		this.habilitaCostoTramite = habilitaCostoTramite;
		this.descripcionCostoTramite = descripcionCostoTramite;
		this.habilitaExcepcionTramite = habilitaExcepcionTramite;
	}

	/**
	 * Constructor utilizado por la NamedQuery
	 * DetTramiteServicio.findByIdDetalleHome
	 * 
	 * @param idDetalleTramite
	 * @param idDetalleHome
	 * @param habilitaRequisitosTramite
	 * @param habilitaCostoTramite
	 * @param descripcionCostoTramite
	 * @param habilitaExcepcionTramite
	 * @param descripcionExcepcionTramite
	 */
	public DetTramiteServicioDTO(Long idDetalleTramite, Long idDetalleHome,
			boolean habilitaCostoTramite, String descripcionCostoTramite, boolean habilitaExcepcionTramite) {
		this.idDetalleTramite = idDetalleTramite;
		this.detHomeDTO = new DetHomeDTO(idDetalleHome);
		this.habilitaCostoTramite = habilitaCostoTramite;
		this.descripcionCostoTramite = descripcionCostoTramite;
		this.habilitaExcepcionTramite = habilitaExcepcionTramite;
	}

	/**
	 * @return the idDetalleTramite
	 */
	public Long getIdDetalleTramite() {
		return idDetalleTramite;
	}

	/**
	 * @param idDetalleTramite the idDetalleTramite to set
	 */
	public void setIdDetalleTramite(Long idDetalleTramite) {
		this.idDetalleTramite = idDetalleTramite;
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
	 * @return the habilitaCostoTramite
	 */
	public boolean isHabilitaCostoTramite() {
		return habilitaCostoTramite;
	}

	/**
	 * @param habilitaCostoTramite the habilitaCostoTramite to set
	 */
	public void setHabilitaCostoTramite(boolean habilitaCostoTramite) {
		this.habilitaCostoTramite = habilitaCostoTramite;
	}

	/**
	 * @return the descripcionCostoTramite
	 */
	public String getDescripcionCostoTramite() {
		return descripcionCostoTramite;
	}

	/**
	 * @param descripcionCostoTramite the descripcionCostoTramite to set
	 */
	public void setDescripcionCostoTramite(String descripcionCostoTramite) {
		this.descripcionCostoTramite = descripcionCostoTramite;
	}

	/**
	 * @return the habilitaExcepcionTramite
	 */
	public boolean isHabilitaExcepcionTramite() {
		return habilitaExcepcionTramite;
	}

	/**
	 * @param habilitaExcepcionTramite the habilitaExcepcionTramite to set
	 */
	public void setHabilitaExcepcionTramite(boolean habilitaExcepcionTramite) {
		this.habilitaExcepcionTramite = habilitaExcepcionTramite;
	}

	@Override
	public String toString() {
		return "DetTramiteServicioDTO [idDetalleTramite=" + idDetalleTramite + ", detHomeDTO=" + detHomeDTO
				+ ", habilitaCostoTramite="
				+ habilitaCostoTramite + ", descripcionCostoTramite=" + descripcionCostoTramite
				+ ", habilitaExcepcionTramite=" + habilitaExcepcionTramite + "]";
	}

}

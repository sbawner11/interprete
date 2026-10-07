package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class DetProgramaSocialDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5074180647187270392L;

	private Long idDetallePrograma;
	private DetHomeDTO detHomeDTO;
	private boolean habilitaCicloPrograma;
	private String descripcionCicloPrograma;
	private String descripcionTipoApoyo;
	private String descripcionDuracionApoyo;
	private boolean habilitaProgramaSimultaneo;

	/**
	 * 
	 */
	public DetProgramaSocialDTO() {
	}

	/**
	 * @param idDetallePrograma
	 * @param detHomeDTO
	 * @param habilitaObjetivosPrograma
	 * @param habilitaPoblacionObjetivo
	 * @param habilitaCicloPrograma
	 * @param descripcionCicloPrograma
	 * @param habilitaRequisitosAcceso
	 * @param habilitaApoyoOtorgado
	 * @param descripcionTipoApoyo
	 * @param habilitaDuracionApoyo
	 * @param descripcionDuracionApoyo
	 * @param habilitaProgramaSimultaneo
	 */
	public DetProgramaSocialDTO(Long idDetallePrograma, DetHomeDTO detHomeDTO, boolean habilitaCicloPrograma,
			String descripcionCicloPrograma, String descripcionTipoApoyo, String descripcionDuracionApoyo,
			boolean habilitaProgramaSimultaneo) {
		this.idDetallePrograma = idDetallePrograma;
		this.detHomeDTO = detHomeDTO;
		this.habilitaCicloPrograma = habilitaCicloPrograma;
		this.descripcionCicloPrograma = descripcionCicloPrograma;
		this.descripcionTipoApoyo = descripcionTipoApoyo;
		this.descripcionDuracionApoyo = descripcionDuracionApoyo;
		this.habilitaProgramaSimultaneo = habilitaProgramaSimultaneo;
	}

	/**
	 * Método utilizado por la NamedQuery DetProgramaSocialDTO.findByIdDetalleHome
	 * 
	 * @param idDetallePrograma
	 * @param idDetalleHome
	 * @param habilitaObjetivosPrograma
	 * @param habilitaPoblacionObjetivo
	 * @param habilitaCicloPrograma
	 * @param descripcionCicloPrograma
	 * @param habilitaRequisitosAcceso
	 * @param habilitaApoyoOtorgado
	 * @param descripcionTipoApoyo
	 * @param habilitaDuracionApoyo
	 * @param descripcionDuracionApoyo
	 * @param habilitaProgramaSimultaneo
	 */
	public DetProgramaSocialDTO(Long idDetallePrograma, Long idDetalleHome, boolean habilitaCicloPrograma,
			String descripcionCicloPrograma, String descripcionTipoApoyo, String descripcionDuracionApoyo,
			boolean habilitaProgramaSimultaneo) {
		this.idDetallePrograma = idDetallePrograma;
		this.detHomeDTO = new DetHomeDTO(idDetalleHome);
		this.habilitaCicloPrograma = habilitaCicloPrograma;
		this.descripcionCicloPrograma = descripcionCicloPrograma;
		this.descripcionTipoApoyo = descripcionTipoApoyo;
		this.descripcionDuracionApoyo = descripcionDuracionApoyo;
		this.habilitaProgramaSimultaneo = habilitaProgramaSimultaneo;
	}

	/**
	 * @return the idDetallePrograma
	 */
	public Long getIdDetallePrograma() {
		return idDetallePrograma;
	}

	/**
	 * @param idDetallePrograma the idDetallePrograma to set
	 */
	public void setIdDetallePrograma(Long idDetallePrograma) {
		this.idDetallePrograma = idDetallePrograma;
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
	 * @return the habilitaCicloPrograma
	 */
	public boolean isHabilitaCicloPrograma() {
		return habilitaCicloPrograma;
	}

	/**
	 * @param habilitaCicloPrograma the habilitaCicloPrograma to set
	 */
	public void setHabilitaCicloPrograma(boolean habilitaCicloPrograma) {
		this.habilitaCicloPrograma = habilitaCicloPrograma;
	}

	/**
	 * @return the descripcionCicloPrograma
	 */
	public String getDescripcionCicloPrograma() {
		return descripcionCicloPrograma;
	}

	/**
	 * @param descripcionCicloPrograma the descripcionCicloPrograma to set
	 */
	public void setDescripcionCicloPrograma(String descripcionCicloPrograma) {
		this.descripcionCicloPrograma = descripcionCicloPrograma;
	}

	/**
	 * @return the descripcionTipoApoyo
	 */
	public String getDescripcionTipoApoyo() {
		return descripcionTipoApoyo;
	}

	/**
	 * @param descripcionTipoApoyo the descripcionTipoApoyo to set
	 */
	public void setDescripcionTipoApoyo(String descripcionTipoApoyo) {
		this.descripcionTipoApoyo = descripcionTipoApoyo;
	}

	/**
	 * @return the descripcionDuracionApoyo
	 */
	public String getDescripcionDuracionApoyo() {
		return descripcionDuracionApoyo;
	}

	/**
	 * @param descripcionDuracionApoyo the descripcionDuracionApoyo to set
	 */
	public void setDescripcionDuracionApoyo(String descripcionDuracionApoyo) {
		this.descripcionDuracionApoyo = descripcionDuracionApoyo;
	}

	/**
	 * @return the habilitaProgramaSimultaneo
	 */
	public boolean isHabilitaProgramaSimultaneo() {
		return habilitaProgramaSimultaneo;
	}

	/**
	 * @param habilitaProgramaSimultaneo the habilitaProgramaSimultaneo to set
	 */
	public void setHabilitaProgramaSimultaneo(boolean habilitaProgramaSimultaneo) {
		this.habilitaProgramaSimultaneo = habilitaProgramaSimultaneo;
	}

	@Override
	public String toString() {
		return "DetProgramaSocialDTO [idDetallePrograma=" + idDetallePrograma + ", detHomeDTO=" + detHomeDTO
				+ ", habilitaCicloPrograma=" + habilitaCicloPrograma + ", descripcionCicloPrograma="
				+ descripcionCicloPrograma + ", descripcionTipoApoyo=" + descripcionTipoApoyo
				+ ", descripcionDuracionApoyo=" + descripcionDuracionApoyo + ", habilitaProgramaSimultaneo="
				+ habilitaProgramaSimultaneo + "]";
	}

}

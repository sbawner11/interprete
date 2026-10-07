package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComponenteRadiobotonDTO extends ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1725181725275251220L;
	
	private Long idComponenteRadioboton;
	private ComponenteDTO componenteDTO;
	private boolean habilitaOpcionOtro;
	private String textoInteriorOtro;
	private List<DetElementosRadiobotonDTO> detElementosRadiobotonsDTO = new ArrayList<>();
	
	//Variables para colocar el valor ingresado como respuesta
	private String opcionRespuesta;
	private String isOtro;
	private String especifiqueOtro;
	
	/**
	 * 
	 */
	public ComponenteRadiobotonDTO() {
		super();
		super.setCatTipoComponenteDTO(new CatTipoComponenteDTO());
	}
	
	/**
	 * @param idComponenteRadioboton
	 * @param habilitaOpcionOtro
	 * @param textoInteriorOtro
	 * @param idComponente
	 * @param idTipoComponente
	 * @param idSubseccionFormulario
	 * @param orden
	 * @param requerido
	 * @param tooltip
	 * @param descripcionTooltip
	 * @param tituloCampo
	 * @param activo
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param seccionSincronizada
	 */
	public ComponenteRadiobotonDTO(Long idComponenteRadioboton, boolean habilitaOpcionOtro, String textoInteriorOtro,
			Long idComponente, Integer idTipoComponente, Long idSubseccionFormulario, int orden, boolean requerido,
			boolean tooltip, String descripcionTooltip, String tituloCampo, boolean activo, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean seccionSincronizada) {

		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip, descripcionTooltip,
				tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);

		this.idComponenteRadioboton = idComponenteRadioboton;
		this.habilitaOpcionOtro = habilitaOpcionOtro;
		this.textoInteriorOtro = textoInteriorOtro;
	}
	
	/**
	 * @param idComponenteRadioboton
	 */
	public ComponenteRadiobotonDTO(Long idComponenteRadioboton) {
		this.idComponenteRadioboton = idComponenteRadioboton;
	}
	
	/**
	 * @param idComponenteRadioboton
	 * @param componenteDTO
	 * @param habilitaOpcionOtro
	 * @param textoInteriorOtro
	 */
	public ComponenteRadiobotonDTO(Long idComponenteRadioboton, ComponenteDTO componenteDTO, boolean habilitaOpcionOtro,
			String textoInteriorOtro) {
		this.idComponenteRadioboton = idComponenteRadioboton;
		this.componenteDTO = componenteDTO;
		this.habilitaOpcionOtro = habilitaOpcionOtro;
		this.textoInteriorOtro = textoInteriorOtro;
	}

	/**
	 * @return the idComponenteRadioboton
	 */
	public Long getIdComponenteRadioboton() {
		return idComponenteRadioboton;
	}

	/**
	 * @param idComponenteRadioboton the idComponenteRadioboton to set
	 */
	public void setIdComponenteRadioboton(Long idComponenteRadioboton) {
		this.idComponenteRadioboton = idComponenteRadioboton;
	}

	/**
	 * @return the componenteDTO
	 */
	public ComponenteDTO getComponenteDTO() {
		return componenteDTO;
	}

	/**
	 * @param componenteDTO the componenteDTO to set
	 */
	public void setComponenteDTO(ComponenteDTO componenteDTO) {
		this.componenteDTO = componenteDTO;
	}

	/**
	 * @return the habilitaOpcionOtro
	 */
	public boolean isHabilitaOpcionOtro() {
		return habilitaOpcionOtro;
	}

	/**
	 * @param habilitaOpcionOtro the habilitaOpcionOtro to set
	 */
	public void setHabilitaOpcionOtro(boolean habilitaOpcionOtro) {
		this.habilitaOpcionOtro = habilitaOpcionOtro;
	}

	/**
	 * @return the textoInteriorOtro
	 */
	public String getTextoInteriorOtro() {
		return textoInteriorOtro;
	}

	/**
	 * @param textoInteriorOtro the textoInteriorOtro to set
	 */
	public void setTextoInteriorOtro(String textoInteriorOtro) {
		this.textoInteriorOtro = textoInteriorOtro;
	}

	/**
	 * @return the detElementosRadiobotonsDTO
	 */
	public List<DetElementosRadiobotonDTO> getDetElementosRadiobotonsDTO() {
		return detElementosRadiobotonsDTO;
	}

	/**
	 * @param detElementosRadiobotonsDTO the detElementosRadiobotonsDTO to set
	 */
	public void setDetElementosRadiobotonsDTO(List<DetElementosRadiobotonDTO> detElementosRadiobotonsDTO) {
		this.detElementosRadiobotonsDTO = detElementosRadiobotonsDTO;
	}

	/**
	 * @return the opcionRespuesta
	 */
	public String getOpcionRespuesta() {
		return opcionRespuesta;
	}

	/**
	 * @param opcionRespuesta the opcionRespuesta to set
	 */
	public void setOpcionRespuesta(String opcionRespuesta) {
		this.opcionRespuesta = opcionRespuesta;
	}

	/**
	 * @return the isOtro
	 */
	public String getIsOtro() {
		return isOtro;
	}

	/**
	 * @param isOtro the isOtro to set
	 */
	public void setIsOtro(String isOtro) {
		this.isOtro = isOtro;
	}

	/**
	 * @return the especifiqueOtro
	 */
	public String getEspecifiqueOtro() {
		return especifiqueOtro;
	}

	/**
	 * @param especifiqueOtro the especifiqueOtro to set
	 */
	public void setEspecifiqueOtro(String especifiqueOtro) {
		this.especifiqueOtro = especifiqueOtro;
	}	
}
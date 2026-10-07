package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComponenteCheckboxDTO extends ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2144692562580517311L;
	
	private Long idComponenteCheckbox;
	private boolean habilitaTodosNinguno;
	private ComponenteDTO componenteDTO;
	private List<DetElementosCheckboxDTO> detElementosCheckboxDTO = new ArrayList<>();
	
	//Lista para guardado de respuestas del componente
	private List<DetElementosCheckboxDTO> lstOpcionesSeleccionadas = new ArrayList<DetElementosCheckboxDTO>();
		
	/**
	 * 
	 */
	public ComponenteCheckboxDTO() {
		super();
		super.setCatTipoComponenteDTO(new CatTipoComponenteDTO());
		detElementosCheckboxDTO = new ArrayList<>();
		lstOpcionesSeleccionadas = new ArrayList<DetElementosCheckboxDTO>();
	}
	
	public ComponenteCheckboxDTO(Long idComponenteCheckbox, boolean habilitaTodosNinguno, Long idComponente, Integer idTipoComponente,
			Long idSubseccionFormulario, int orden, boolean requerido, boolean tooltip, String descripcionTooltip,
			String tituloCampo, boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion,
			boolean seccionSincronizada) {
		
		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip, descripcionTooltip,
				tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);

		this.idComponenteCheckbox = idComponenteCheckbox;
		this.habilitaTodosNinguno = habilitaTodosNinguno;
	}
	
	/**
	 * @param idComponenteCheckbox
	 */
	public ComponenteCheckboxDTO(Long idComponenteCheckbox) {
		this.idComponenteCheckbox = idComponenteCheckbox;
	}

	/**
	 * @param idComponenteCheckbox
	 * @param habilitaTodosNinguno
	 */
	public ComponenteCheckboxDTO(Long idComponenteCheckbox, boolean habilitaTodosNinguno, ComponenteDTO componenteDTO) {
		this.idComponenteCheckbox = idComponenteCheckbox;
		this.habilitaTodosNinguno = habilitaTodosNinguno;
		this.componenteDTO = componenteDTO;
	}

	/**
	 * @return the idComponenteCheckbox
	 */
	public Long getIdComponenteCheckbox() {
		return idComponenteCheckbox;
	}

	/**
	 * @param idComponenteCheckbox the idComponenteCheckbox to set
	 */
	public void setIdComponenteCheckbox(Long idComponenteCheckbox) {
		this.idComponenteCheckbox = idComponenteCheckbox;
	}
	
	/**
	 * @return the habilitaTodosNinguno
	 */
	public boolean isHabilitaTodosNinguno() {
		return habilitaTodosNinguno;
	}

	/**
	 * @param habilitaTodosNinguno the habilitaTodosNinguno to set
	 */
	public void setHabilitaTodosNinguno(boolean habilitaTodosNinguno) {
		this.habilitaTodosNinguno = habilitaTodosNinguno;
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
	 * @return the detElementosCheckboxDTO
	 */
	public List<DetElementosCheckboxDTO> getDetElementosCheckboxDTO() {
		return detElementosCheckboxDTO;
	}

	/**
	 * @param detElementosCheckboxDTO the detElementosCheckboxDTO to set
	 */
	public void setDetElementosCheckboxDTO(List<DetElementosCheckboxDTO> detElementosCheckboxDTO) {
		this.detElementosCheckboxDTO = detElementosCheckboxDTO;
	}

	/**
	 * @return the lstOpcionesSeleccionadas
	 */
	public List<DetElementosCheckboxDTO> getLstOpcionesSeleccionadas() {
		return lstOpcionesSeleccionadas;
	}

	/**
	 * @param lstOpcionesSeleccionadas the lstOpcionesSeleccionadas to set
	 */
	public void setLstOpcionesSeleccionadas(List<DetElementosCheckboxDTO> lstOpcionesSeleccionadas) {
		this.lstOpcionesSeleccionadas = lstOpcionesSeleccionadas;
	}	
}

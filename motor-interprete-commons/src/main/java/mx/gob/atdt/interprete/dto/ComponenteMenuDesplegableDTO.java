package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ComponenteMenuDesplegableDTO extends ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1725181725275251220L;
	
	private Long idComponenteMenuDesplegable;
	private ComponenteDTO componenteDTO;
	private CatOrigenLlenadoDTO catOrigenLlenadoDTO;
	private boolean habilitaTextoInteriorOtro;
	private String textoInteriorOtro;
	private List<DetElementosMenuDTO> detElementosMenuDTO = new ArrayList<>();
	private CatTipoOrdenamientoDTO catTipoOrdenamientoDTO;
	
	/**
	 * 
	 */
	public ComponenteMenuDesplegableDTO() {
		super();
		super.setCatTipoComponenteDTO(new CatTipoComponenteDTO());
		this.catOrigenLlenadoDTO = new CatOrigenLlenadoDTO();
	}
	
	public ComponenteMenuDesplegableDTO(Long idComponenteMenuDesplegable, Integer idOrigenLlenado, boolean habilitaTextoInteriorOtro,
			String textoInteriorOtro, Long idComponente, Integer idTipoComponente, Long idSubseccionFormulario, int orden, boolean requerido,
			boolean tooltip, String descripcionTooltip, String tituloCampo, boolean activo, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean seccionSincronizada) {

		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip, descripcionTooltip,
				tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		
		this.catOrigenLlenadoDTO = new CatOrigenLlenadoDTO(idOrigenLlenado);
		this.idComponenteMenuDesplegable = idComponenteMenuDesplegable;
		this.habilitaTextoInteriorOtro = habilitaTextoInteriorOtro;
		this.textoInteriorOtro = textoInteriorOtro;
	}

	public ComponenteMenuDesplegableDTO(Long idComponenteMenuDesplegable, Integer idOrigenLlenado, boolean habilitaTextoInteriorOtro,
			String textoInteriorOtro, Long idComponente, Integer idTipoComponente, Long idSubseccionFormulario, int orden, boolean requerido,
			boolean tooltip, String descripcionTooltip, String tituloCampo, boolean activo, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean seccionSincronizada, Long idTipoOrdenamiento) {

		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip, descripcionTooltip,
				tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		
		this.catOrigenLlenadoDTO = new CatOrigenLlenadoDTO(idOrigenLlenado);
		this.idComponenteMenuDesplegable = idComponenteMenuDesplegable;
		this.habilitaTextoInteriorOtro = habilitaTextoInteriorOtro;
		this.textoInteriorOtro = textoInteriorOtro;
		this.catTipoOrdenamientoDTO = new CatTipoOrdenamientoDTO(idTipoOrdenamiento);
	}

	/**
	 * @param idComponenteMenuDesplegable
	 */
	public ComponenteMenuDesplegableDTO(Long idComponenteMenuDesplegable) {
		this.idComponenteMenuDesplegable = idComponenteMenuDesplegable;
	}
	
	/**
	 * @param idComponenteRadioboton
	 * @param componenteDTO
	 * @param habilitaOpcionOtro
	 * @param textoInteriorOtro
	 */
	public ComponenteMenuDesplegableDTO(Long idComponenteMenuDesplegable, ComponenteDTO componenteDTO, CatOrigenLlenadoDTO catOrigenLlenadoDTO, boolean habilitaTextoInteriorOtro,
			String textoInteriorOtro) {
		this.idComponenteMenuDesplegable = idComponenteMenuDesplegable;
		this.componenteDTO = componenteDTO;
		this.habilitaTextoInteriorOtro = habilitaTextoInteriorOtro;
		this.textoInteriorOtro = textoInteriorOtro;
		this.catOrigenLlenadoDTO = catOrigenLlenadoDTO;
	}


	/**
	 * @return the idComponenteMenuDesplegable
	 */
	public Long getIdComponenteMenuDesplegable() {
		return idComponenteMenuDesplegable;
	}

	/**
	 * @param idComponenteMenuDesplegable the idComponenteMenuDesplegable to set
	 */
	public void setIdComponenteMenuDesplegable(Long idComponenteMenuDesplegable) {
		this.idComponenteMenuDesplegable = idComponenteMenuDesplegable;
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
	 * @return the habilitaTextoInteriorOtro
	 */
	public boolean isHabilitaTextoInteriorOtro() {
		return habilitaTextoInteriorOtro;
	}

	/**
	 * @param habilitaTextoInteriorOtro the habilitaTextoInteriorOtro to set
	 */
	public void setHabilitaTextoInteriorOtro(boolean habilitaTextoInteriorOtro) {
		this.habilitaTextoInteriorOtro = habilitaTextoInteriorOtro;
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
	 * @return the catOrigenLlenadoDTO
	 */
	public CatOrigenLlenadoDTO getCatOrigenLlenadoDTO() {
		return catOrigenLlenadoDTO;
	}

	/**
	 * @param catOrigenLlenadoDTO the catOrigenLlenadoDTO to set
	 */
	public void setCatOrigenLlenadoDTO(CatOrigenLlenadoDTO catOrigenLlenadoDTO) {
		this.catOrigenLlenadoDTO = catOrigenLlenadoDTO;
	}

	/**
	 * @return the detElementosMenuDTO
	 */
	public List<DetElementosMenuDTO> getDetElementosMenuDTO() {
		return detElementosMenuDTO;
	}

	/**
	 * @param detElementosMenuDTO the detElementosMenuDTO to set
	 */
	public void setDetElementosMenuDTO(List<DetElementosMenuDTO> detElementosMenuDTO) {
		this.detElementosMenuDTO = detElementosMenuDTO;
	}
	
	public CatTipoOrdenamientoDTO getCatTipoOrdenamientoDTO() {
		return catTipoOrdenamientoDTO;
	}

	public void setCatTipoOrdenamientoDTO(CatTipoOrdenamientoDTO catTipoOrdenamientoDTO) {
		this.catTipoOrdenamientoDTO = catTipoOrdenamientoDTO;
	}

}
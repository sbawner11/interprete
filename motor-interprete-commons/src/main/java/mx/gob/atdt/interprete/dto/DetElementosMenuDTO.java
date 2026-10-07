package mx.gob.atdt.interprete.dto;

import java.io.Serializable;



public class DetElementosMenuDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3439550517557369676L;
	
	private Long idElementoMenu;
	private ComponenteMenuDesplegableDTO componenteMenuDesplegableDTO;
	private String descripcionElemento;
	private boolean activo;

	
	/**
	 * 
	 */
	public DetElementosMenuDTO() {
	}
	
	/**
	 * @param idElementoMenu
	 * @param componenteMenuDesplegableDTO
	 * @param activo
	 * @param descripcionElemento
	 */
	public DetElementosMenuDTO(Long idElementoMenu, Long idComponenteMenuDesplegable,
			boolean activo, String descripcionElemento) {
		this.idElementoMenu = idElementoMenu;
		this.componenteMenuDesplegableDTO = new ComponenteMenuDesplegableDTO(idComponenteMenuDesplegable);
		this.activo = activo;
		this.descripcionElemento = descripcionElemento;
	}


	/**
	 * @return the idElementoMenu
	 */
	public Long getIdElementoMenu() {
		return idElementoMenu;
	}


	/**
	 * @param idElementoMenu the idElementoMenu to set
	 */
	public void setIdElementoMenu(Long idElementoMenu) {
		this.idElementoMenu = idElementoMenu;
	}


	/**
	 * @return the componenteMenuDesplegableDTO
	 */
	public ComponenteMenuDesplegableDTO getComponenteMenuDesplegableDTO() {
		return componenteMenuDesplegableDTO;
	}


	/**
	 * @param componenteMenuDesplegableDTO the componenteMenuDesplegableDTO to set
	 */
	public void setComponenteMenuDesplegableDTO(ComponenteMenuDesplegableDTO componenteMenuDesplegableDTO) {
		this.componenteMenuDesplegableDTO = componenteMenuDesplegableDTO;
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


	/**
	 * @return the descripcionElemento
	 */
	public String getDescripcionElemento() {
		return descripcionElemento;
	}


	/**
	 * @param descripcionElemento the descripcionElemento to set
	 */
	public void setDescripcionElemento(String descripcionElemento) {
		this.descripcionElemento = descripcionElemento;
	}
	
}
package mx.gob.atdt.interprete.dto;

import java.io.Serializable;


public class DetElementosCheckboxDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2859555560167743167L;
	
	private Long idElementoCheckbox;
	private transient ComponenteCheckboxDTO componenteCheckboxDTO;
	private transient boolean activo;	
	private transient int orden;
	private String descripcionElemento;
	private transient boolean deshabilitado;	
	private transient boolean isDelete;
	
	/**
	 * 
	 */
	public DetElementosCheckboxDTO() {
	}

	/**
	 * @param idElementoCheckbox
	 * @param componenteCheckboxDTO
	 * @param activo
	 * @param orden
	 * @param descripcionElemento
	 */
	public DetElementosCheckboxDTO(Long idElementoCheckbox, ComponenteCheckboxDTO componenteCheckboxDTO,
			boolean activo,int orden, String descripcionElemento) {
		this.idElementoCheckbox = idElementoCheckbox;
		this.componenteCheckboxDTO = componenteCheckboxDTO;
		this.activo = activo;
		this.orden = orden;
		this.descripcionElemento = descripcionElemento;
	}
	
	/**
	 * @param idElementoCheckbox
	 * @param componenteCheckboxDTO
	 * @param activo
	 * @param orden
	 * @param descripcionElemento
	 */
	public DetElementosCheckboxDTO(Long idElementoCheckbox, Long idComponenteCheckbox,
			boolean activo,int orden, String descripcionElemento) {
		this.idElementoCheckbox = idElementoCheckbox;
		this.componenteCheckboxDTO = new ComponenteCheckboxDTO(idComponenteCheckbox);
		this.activo = activo;
		this.orden = orden;
		this.descripcionElemento = descripcionElemento;
	}
	
	/**
	 * 
	 * @param orden
	 * @param deshabilitado
	 */

	public DetElementosCheckboxDTO(int orden, boolean deshabilitado, boolean activo) {
		this.orden = orden;
		this.deshabilitado = deshabilitado;
		this.activo = activo;
	}

	/**
	 * @return the idElementoCheckbox
	 */
	public Long getIdElementoCheckbox() {
		return idElementoCheckbox;
	}

	/**
	 * @param idElementoCheckbox the idElementoCheckbox to set
	 */
	public void setIdElementoCheckbox(Long idElementoCheckbox) {
		this.idElementoCheckbox = idElementoCheckbox;
	}

	/**
	 * @return the componenteCheckboxDTO
	 */
	public ComponenteCheckboxDTO getComponenteCheckboxDTO() {
		return componenteCheckboxDTO;
	}

	/**
	 * @param componenteCheckboxDTO the componenteCheckboxDTO to set
	 */
	public void setComponenteCheckboxDTO(ComponenteCheckboxDTO componenteCheckboxDTO) {
		this.componenteCheckboxDTO = componenteCheckboxDTO;
	}

	/**
	 * @return the habilitaElemento
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param habilitaElemento the habilitaElemento to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
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

	/**
	 * @return the deshabilitado
	 */
	public boolean isDeshabilitado() {
		return deshabilitado;
	}

	/**
	 * @param deshabilitado the deshabilitado to set
	 */
	public void setDeshabilitado(boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}

	/**
	 * @return the isDelete
	 */
	public boolean isDelete() {
		return isDelete;
	}

	/**
	 * @param isDelete the isDelete to set
	 */
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	

}

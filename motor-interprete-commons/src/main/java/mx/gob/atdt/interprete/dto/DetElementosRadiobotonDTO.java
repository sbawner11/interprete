package mx.gob.atdt.interprete.dto;

import java.io.Serializable;



public class DetElementosRadiobotonDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4816489596017572270L;
	
	private Long idElementoRadioboton;
	private ComponenteRadiobotonDTO componenteRadiobotonDTO;
	private boolean activo;
	private int orden;
	private String descripcionElemento;
	private boolean deshabilitado;
	private boolean isDelete;
	
	/**
	 * 
	 */
	public DetElementosRadiobotonDTO() {
	}

	/**
	 * @param idElementoRadioboton
	 * @param componenteRadiobotonDTO
	 * @param habilitaElemento
	 * @param descripcionElemento
	 */
	public DetElementosRadiobotonDTO(Long idElementoRadioboton, ComponenteRadiobotonDTO componenteRadiobotonDTO,
			boolean activo,int orden, String descripcionElemento) {
		this.idElementoRadioboton = idElementoRadioboton;
		this.componenteRadiobotonDTO = componenteRadiobotonDTO;
		this.activo = activo;
		this.orden = orden;
		this.descripcionElemento = descripcionElemento;
	}
	
	/**
	 * @param idElementoRadioboton
	 * @param componenteCheckboxDTO
	 * @param activo
	 * @param orden
	 * @param descripcionElemento
	 */
	public DetElementosRadiobotonDTO(Long idElementoRadioboton, Long idComponenteRadioboton,
			boolean activo, int orden, String descripcionElemento) {
		this.idElementoRadioboton = idElementoRadioboton;
		this.componenteRadiobotonDTO = new ComponenteRadiobotonDTO(idComponenteRadioboton);
		this.activo = activo;
		this.orden = orden;
		this.descripcionElemento = descripcionElemento;
	}
	
	/**
	 * 
	 * @param orden
	 * @param deshabilitado
	 */

	public DetElementosRadiobotonDTO(int orden, boolean activo) {
		this.orden = orden;
		this.activo = activo;
	}

	/**
	 * @return the idElementoRadioboton
	 */
	public Long getIdElementoRadioboton() {
		return idElementoRadioboton;
	}

	/**
	 * @param idElementoRadioboton the idElementoRadioboton to set
	 */
	public void setIdElementoRadioboton(Long idElementoRadioboton) {
		this.idElementoRadioboton = idElementoRadioboton;
	}

	/**
	 * @return the componenteRadiobotonDTO
	 */
	public ComponenteRadiobotonDTO getComponenteRadiobotonDTO() {
		return componenteRadiobotonDTO;
	}

	/**
	 * @param componenteRadiobotonDTO the componenteRadiobotonDTO to set
	 */
	public void setComponenteRadiobotonDTO(ComponenteRadiobotonDTO componenteRadiobotonDTO) {
		this.componenteRadiobotonDTO = componenteRadiobotonDTO;
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
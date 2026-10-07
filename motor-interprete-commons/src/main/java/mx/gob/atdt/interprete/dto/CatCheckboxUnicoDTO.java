package mx.gob.atdt.interprete.dto;

public class CatCheckboxUnicoDTO {
	private int idCheckboxUnico;
	private String descripcion;
	
	public CatCheckboxUnicoDTO() {

	}
	
	public CatCheckboxUnicoDTO(int idCheckboxUnico, String descripcion) {
		this.idCheckboxUnico = idCheckboxUnico;
		this.descripcion = descripcion;
	}
	
	/**
	 * @return the idCheckboxUnico
	 */
	public int getIdCheckboxUnico() {
		return idCheckboxUnico;
	}

	/**
	 * @param idCheckboxUnico the idCheckboxUnico to set
	 */
	public void setIdCheckboxUnico(int idCheckboxUnico) {
		this.idCheckboxUnico = idCheckboxUnico;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}

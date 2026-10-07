package mx.gob.atdt.interprete.dto;

public class CatDocObligatorioDTO {
	private Integer idDocObligatorio;
	private String descripcion;
	
	public CatDocObligatorioDTO() {
	}
	
	public CatDocObligatorioDTO(Integer idDocObligatorio, String descripcion) {
		this.idDocObligatorio = idDocObligatorio;
		this.descripcion = descripcion;
	}
	
	/**
	 * @return the idDocObligatorio
	 */
	public Integer getIdDocObligatorio() {
		return idDocObligatorio;
	}
	/**
	 * @param idDocObligatorio the idDocObligatorio to set
	 */
	public void setIdDocObligatorio(Integer idDocObligatorio) {
		this.idDocObligatorio = idDocObligatorio;
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

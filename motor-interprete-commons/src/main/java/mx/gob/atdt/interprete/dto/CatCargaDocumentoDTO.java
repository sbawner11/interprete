package mx.gob.atdt.interprete.dto;

public class CatCargaDocumentoDTO {
	private int idCargaDocumento;
	private String descripcion;
	
	
	public CatCargaDocumentoDTO() {

	}
	
	public CatCargaDocumentoDTO(int idCargaDocumento, String descripcion) {
		this.idCargaDocumento = idCargaDocumento;
		this.descripcion = descripcion;
	}
	/**
	 * @return the idCargaDocumento
	 */
	public int getIdCargaDocumento() {
		return idCargaDocumento;
	}
	/**
	 * @param idCargaDocumento the idCargaDocumento to set
	 */
	public void setIdCargaDocumento(int idCargaDocumento) {
		this.idCargaDocumento = idCargaDocumento;
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

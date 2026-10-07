package mx.gob.atdt.interprete.dto;

public class CatTamanioPermitidoDTO {

	private Integer idCatTamanioPermitido;
	private String descripcion;

	public CatTamanioPermitidoDTO() {
	}

	public CatTamanioPermitidoDTO(Integer idCatTamanioPermitido, String descripcion) {
		this.idCatTamanioPermitido = idCatTamanioPermitido;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idCatTamanioPermitido
	 */
	public Integer getIdCatTamanioPermitido() {
		return idCatTamanioPermitido;
	}

	/**
	 * @param idCatTamanioPermitido the idCatTamanioPermitido to set
	 */
	public void setIdCatTamanioPermitido(Integer idCatTamanioPermitido) {
		this.idCatTamanioPermitido = idCatTamanioPermitido;
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

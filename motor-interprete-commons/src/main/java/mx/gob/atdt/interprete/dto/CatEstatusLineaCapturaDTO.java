package mx.gob.atdt.interprete.dto;


public class CatEstatusLineaCapturaDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idEstatusLineaCaptura;
	private String descripcion;

	public CatEstatusLineaCapturaDTO() {
		// Constructor por defecto
	}

	
	public CatEstatusLineaCapturaDTO(final Integer idEstatusLineaCaptura, final String descripcion) {
		super();
		this.idEstatusLineaCaptura = idEstatusLineaCaptura;
		this.descripcion = descripcion;
	}



	public CatEstatusLineaCapturaDTO(Integer idEstatusLineaCaptura) {
		this.idEstatusLineaCaptura = idEstatusLineaCaptura;
	}
	
	public Integer getIdEstatusLineaCaptura() {
		return idEstatusLineaCaptura;
	}

	public void setIdEstatusLineaCaptura(Integer idEstatusLineaCaptura) {
		this.idEstatusLineaCaptura = idEstatusLineaCaptura;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


}


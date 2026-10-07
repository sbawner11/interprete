package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatAtributosComponentesDTO implements Serializable {

private static final long serialVersionUID = 1L;
	
	private Long idAtributoComponente;
	private long idTipoComponente;
	private String nombreAtributo;
	private long orden;
	
	public CatAtributosComponentesDTO() {
		
    }
	
	public CatAtributosComponentesDTO(Long idAtributoComponente) {
		this.idAtributoComponente = idAtributoComponente;
    }

	public CatAtributosComponentesDTO(Long idAtributoComponente, long idTipoComponente, String nombreAtributo,
			long orden) {
		this.idAtributoComponente = idAtributoComponente;
		this.idTipoComponente = idTipoComponente;
		this.nombreAtributo = nombreAtributo;
		this.orden = orden;
	}

	public Long getIdAtributoComponente() {
		return idAtributoComponente;
	}

	public long getIdTipoComponente() {
		return idTipoComponente;
	}

	public String getNombreAtributo() {
		return nombreAtributo;
	}

	public long getOrden() {
		return orden;
	}

	public void setIdAtributoComponente(Long idAtributoComponente) {
		this.idAtributoComponente = idAtributoComponente;
	}

	public void setIdTipoComponente(long idTipoComponente) {
		this.idTipoComponente = idTipoComponente;
	}

	public void setNombreAtributo(String nombreAtributo) {
		this.nombreAtributo = nombreAtributo;
	}

	public void setOrden(long orden) {
		this.orden = orden;
	}
	
	
}

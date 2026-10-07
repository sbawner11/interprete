package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatEstatusCargaMasivaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idEstatusCarga;
	private String descripcion;

	public CatEstatusCargaMasivaDTO() {
	}

	public CatEstatusCargaMasivaDTO(Integer idEstatusCarga, String descripcion) {
		this.idEstatusCarga = idEstatusCarga;
		this.descripcion = descripcion;
	}

	public Integer getIdEstatusCarga() {
		return idEstatusCarga;
	}

	public void setIdEstatusCarga(Integer idEstatusCarga) {
		this.idEstatusCarga = idEstatusCarga;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}

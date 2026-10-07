package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatMotivosPausaDTO implements Serializable {

	private static final long serialVersionUID = -4772836124794544738L;
	private Integer idMotivo;
	private String descripcion;
	private String descripcionMotivo;

	public CatMotivosPausaDTO() {

	}

	public CatMotivosPausaDTO(Integer idMotivo, String descripcion, String descripcionMotivo) {
		this.idMotivo = idMotivo;
		this.descripcion = descripcion;
		this.descripcionMotivo = descripcionMotivo;
	}

	public Integer getIdMotivo() {
		return idMotivo;
	}

	public void setIdMotivo(Integer idMotivo) {
		this.idMotivo = idMotivo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionMotivo() {
		return descripcionMotivo;
	}

	public void setDescripcionMotivo(String descripcionMotivo) {
		this.descripcionMotivo = descripcionMotivo;
	}

	@Override
	public String toString() {
		return "CatMotivosPausaDTO [idMotivo=" + idMotivo + ", descripcion=" + descripcion + ", descripcionMotivo="
				+ descripcionMotivo + "]";
	}

}

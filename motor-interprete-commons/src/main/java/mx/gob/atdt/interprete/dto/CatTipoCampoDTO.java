package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoCampoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
	private Integer id;
    private String descripcion;
    private Boolean activo;
    
	public CatTipoCampoDTO() {
		super();
	}
	public CatTipoCampoDTO(Integer id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	@Override
	public String toString() {
		return "CatTipoCampoDTO [id=" + id + ", descripcion=" + descripcion + ", activo=" + activo + "]";
	}

}
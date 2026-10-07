package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.List;

public class RequisitoDTO implements Serializable {

	private static final long serialVersionUID = -5931421111008137335L;
	private int id;
	private String descripcion;
	private List<EspecificacionDTO> lstEspecificaciones;

	public RequisitoDTO() {
	}

	public RequisitoDTO(int id, String descripcion) {
		super();
		this.id = id;
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<EspecificacionDTO> getLstEspecificaciones() {
		return lstEspecificaciones;
	}

	public void setLstEspecificaciones(List<EspecificacionDTO> lstEspecificaciones) {
		this.lstEspecificaciones = lstEspecificaciones;
	}

	@Override
	public String toString() {
		return "RequisitoDTO [id=" + id + ", descripcion=" + descripcion + ", lstEspecificaciones="
				+ lstEspecificaciones + "]";
	}

}

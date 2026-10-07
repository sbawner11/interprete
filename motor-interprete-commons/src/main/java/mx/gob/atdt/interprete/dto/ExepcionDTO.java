package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class ExepcionDTO implements Serializable {

	private static final long serialVersionUID = -4109507335180659145L;
	private int id;
	private String descripcion;

	public ExepcionDTO() {

	}

	/**
	 * @param id
	 * @param descripcion
	 */
	public ExepcionDTO(int id, String descripcion) {
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

	@Override
	public String toString() {
		return "ExcepcionDTO [id=" + id + ", descripcion=" + descripcion + "]";
	}

}

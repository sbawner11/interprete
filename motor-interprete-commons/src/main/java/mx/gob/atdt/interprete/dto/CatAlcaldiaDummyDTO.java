package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatAlcaldiaDummyDTO implements Serializable {

	private static final long serialVersionUID = 4928596154864928426L;
	private int id;
	private String descripcion;

	public CatAlcaldiaDummyDTO() {

	}

	/**
	 * @param id
	 * @param descripcion
	 */
	public CatAlcaldiaDummyDTO(int id, String descripcion) {
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
		return "CatAlcaldiaDummyDTO [id=" + id + ", descripcion=" + descripcion + "]";
	}

}

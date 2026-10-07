package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class EspecificacionDTO implements Serializable {

	private static final long serialVersionUID = -5931421111008137335L;
	private int idRequisito;
	private int id;
	private String descripcion;

	public EspecificacionDTO() {
	}

	/**
	 * @param id
	 * @param descripcion
	 */
	public EspecificacionDTO(int idRequisito, int id, String descripcion) {
		super();
		this.idRequisito = idRequisito;
		this.id = id;
		this.descripcion = descripcion;
	}

	public int getIdRequisito() {
		return idRequisito;
	}

	public void setIdRequisito(int idRequisito) {
		this.idRequisito = idRequisito;
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
		return "EspecificacionDTO [idRequisito=" + idRequisito + ", id=" + id + ", descripcion=" + descripcion + "]";
	}

}

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.List;

public class AgregaDescripcionesDTO implements Serializable {

	private static final long serialVersionUID = 5613983783271127912L;
	private Long id;
	private int orden;
	private String descripcion;
	private boolean activo;
	private List<AgregaDescripcionesDTO> subLst;

	public AgregaDescripcionesDTO() {

	}

	/**
	 * 
	 * @param id
	 */
	public AgregaDescripcionesDTO(Long id) {
		this.id = id;
	}

	/**
	 * @param id
	 * @param orden
	 * @param activo
	 */
	public AgregaDescripcionesDTO(int orden, String descripcion, boolean activo) {
		super();
		this.orden = orden;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<AgregaDescripcionesDTO> getSubLst() {
		return subLst;
	}

	public void setSubLst(List<AgregaDescripcionesDTO> subLst) {
		this.subLst = subLst;
	}

	@Override
	public String toString() {
		return "AgregaDescripcionesDTO [id=" + id + ", orden=" + orden + ", descripcion=" + descripcion + ", activo="
				+ activo + ", subLst=" + subLst + "]";
	}

}

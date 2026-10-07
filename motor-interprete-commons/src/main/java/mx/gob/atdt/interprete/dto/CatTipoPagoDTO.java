package mx.gob.atdt.interprete.dto;

import java.util.HashSet;
import java.util.Set;


public class CatTipoPagoDTO {
	private Integer id;
	private String descripcion;
	private Set<DetPagoDTO> detallePagoDTOs = new HashSet<DetPagoDTO>(0);
	
	public CatTipoPagoDTO() {
		
	}
	
	public CatTipoPagoDTO(Integer id, String descripcion) {
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

	public Set<DetPagoDTO> getDetallePagoDTOs() {
		return detallePagoDTOs;
	}

	public void setDetallePagoDTOs(Set<DetPagoDTO> detallePagoDTOs) {
		this.detallePagoDTOs = detallePagoDTOs;
	}
	
}

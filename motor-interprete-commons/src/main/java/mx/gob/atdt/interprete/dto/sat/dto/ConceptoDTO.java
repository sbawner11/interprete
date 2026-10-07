package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConceptoDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3461655596492416320L;
	
	@JsonProperty("Concepto")
	private List<DetalleConceptoDTO> concepto;
	
	/**
	 * 
	 */
	public ConceptoDTO() {
		this.concepto = new ArrayList<>();
	}

	/**
	 * @return the concepto
	 */
	public List<DetalleConceptoDTO> getConcepto() {
		return concepto;
	}

	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(List<DetalleConceptoDTO> concepto) {
		this.concepto = concepto;
	}	
}

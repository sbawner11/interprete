package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransaccionPDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8443751565896326002L;
	
	@JsonProperty("TransaccionP")
	private List<DetalleTransaccionPDTO> transaccionP;

	/**
	 * 
	 */
	public TransaccionPDTO() {
		this.transaccionP = new ArrayList<>();
		
	}

	/**
	 * @return the transaccionP
	 */
	public List<DetalleTransaccionPDTO> getTransaccionP() {
		return transaccionP;
	}

	/**
	 * @param transaccionP the transaccionP to set
	 */
	public void setTransaccionP(List<DetalleTransaccionPDTO> transaccionP) {
		this.transaccionP = transaccionP;
	}
}

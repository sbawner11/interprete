package mx.gob.atdt.interprete.linea.captura.dto;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class DatosPagosDTO implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("CveBanco")
	private Integer cveBanco;
	@SerializedName("NoOperacion")
	private String noOperacion;
	@SerializedName("FechaRecepcionPago")
	private String fechaRecepcionPago;
	@SerializedName("ImportePagado")
	private Integer importePagado;
	
	/**
	 * 
	 */
	public DatosPagosDTO() {
		// Constructo vacío para inicialización por default
	}

	/**
	 * @return the cveBanco
	 */
	public Integer getCveBanco() {
		return cveBanco;
	}

	/**
	 * @param cveBanco the cveBanco to set
	 */
	public void setCveBanco(Integer cveBanco) {
		this.cveBanco = cveBanco;
	}

	/**
	 * @return the noOperacion
	 */
	public String getNoOperacion() {
		return noOperacion;
	}

	/**
	 * @param noOperacion the noOperacion to set
	 */
	public void setNoOperacion(String noOperacion) {
		this.noOperacion = noOperacion;
	}

	/**
	 * @return the fechaRecepcionPago
	 */
	public String getFechaRecepcionPago() {
		return fechaRecepcionPago;
	}

	/**
	 * @param fechaRecepcionPago the fechaRecepcionPago to set
	 */
	public void setFechaRecepcionPago(String fechaRecepcionPago) {
		this.fechaRecepcionPago = fechaRecepcionPago;
	}

	/**
	 * @return the importePagado
	 */
	public Integer getImportePagado() {
		return importePagado;
	}

	/**
	 * @param importePagado the importePagado to set
	 */
	public void setImportePagado(Integer importePagado) {
		this.importePagado = importePagado;
	}	
}

package mx.gob.atdt.interprete.linea.captura.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;


public class ResponseEstatusLCDTO implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@SerializedName("Codigo")
	private Integer codigo;
	
	@SerializedName("Estatus")
	private String estatus;
	
	@SerializedName("DescripcionEstatus")
	private String descripcionEstatus;
	
	@SerializedName("IdSolicitud")
	private String idSolicitud;
	
	@SerializedName("LineaCaptura")
	private String lineaCaptura;
	
	@SerializedName("MensajeError")
	private String mensajeError;
	
	@SerializedName("DatosPagos")
	private List<DatosPagosDTO> datosPagos;
	
	/**
	 * 
	 */
	public ResponseEstatusLCDTO() {
		datosPagos = new ArrayList<>();
	}

	/**
	 * @return the codigo
	 */
	public Integer getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}

	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	/**
	 * @return the descripcionEstatus
	 */
	public String getDescripcionEstatus() {
		return descripcionEstatus;
	}

	/**
	 * @param descripcionEstatus the descripcionEstatus to set
	 */
	public void setDescripcionEstatus(String descripcionEstatus) {
		this.descripcionEstatus = descripcionEstatus;
	}

	/**
	 * @return the idSolicitud
	 */
	public String getIdSolicitud() {
		return idSolicitud;
	}

	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}

	/**
	 * @return the lineaCaptura
	 */
	public String getLineaCaptura() {
		return lineaCaptura;
	}

	/**
	 * @param lineaCaptura the lineaCaptura to set
	 */
	public void setLineaCaptura(String lineaCaptura) {
		this.lineaCaptura = lineaCaptura;
	}

	/**
	 * @return the mensajeError
	 */
	public String getMensajeError() {
		return mensajeError;
	}

	/**
	 * @param mensajeError the mensajeError to set
	 */
	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	/**
	 * @return the datosPagos
	 */
	public List<DatosPagosDTO> getDatosPagos() {
		return datosPagos;
	}

	/**
	 * @param datosPagos the datosPagos to set
	 */
	public void setDatosPagos(List<DatosPagosDTO> datosPagos) {
		this.datosPagos = datosPagos;
	}

	@Override
	public String toString() {
		return "ResponseEstatusLCDTO [codigo=" + codigo + ", estatus=" + estatus + ", descripcionEstatus="
				+ descripcionEstatus + ", idSolicitud=" + idSolicitud + ", lineaCaptura=" + lineaCaptura
				+ ", mensajeError=" + mensajeError + ", datosPagos=" + datosPagos + "]";
	}
}

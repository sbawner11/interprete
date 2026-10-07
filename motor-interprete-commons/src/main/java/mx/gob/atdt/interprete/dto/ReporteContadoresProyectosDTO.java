package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class ReporteContadoresProyectosDTO implements Serializable {
		
	private static final long serialVersionUID = 8374901742688751221L;
	
	private ProyectoDTO proyectoDTO;
	private Date fechaUltimaActualizacion;
	private int subtotalCaptura;
	private int subtotalEnviado;
	private int subtotalCorreccion;
	private int subtotalCorregido;
	private int subtotalRevisado;
	private int subtotalRechazado;
	private int subtotalAceptado;
	private int subtotalFirmado;
	private int totalRegistros;
	private String versionBaseDatos;
	private String versionEar;
	
	/**
	 * 
	 */
	public ReporteContadoresProyectosDTO() {
		this.proyectoDTO = new ProyectoDTO();
	}	
	
	/**
	 * @return the proyectoDTO
	 */
	public ProyectoDTO getProyectoDTO() {
		return proyectoDTO;
	}

	/**
	 * @param proyectoDTO the proyectoDTO to set
	 */
	public void setProyectoDTO(ProyectoDTO proyectoDTO) {
		this.proyectoDTO = proyectoDTO;
	}

	/**
	 * @return the fechaUltimaActualizacion
	 */
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	/**
	 * @param fechaUltimaActualizacion the fechaUltimaActualizacion to set
	 */
	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	/**
	 * @return the subtotalCaptura
	 */
	public int getSubtotalCaptura() {
		return subtotalCaptura;
	}

	/**
	 * @param subtotalCaptura the subtotalCaptura to set
	 */
	public void setSubtotalCaptura(int subtotalCaptura) {
		this.subtotalCaptura = subtotalCaptura;
	}

	/**
	 * @return the subtotalEnviado
	 */
	public int getSubtotalEnviado() {
		return subtotalEnviado;
	}

	/**
	 * @param subtotalEnviado the subtotalEnviado to set
	 */
	public void setSubtotalEnviado(int subtotalEnviado) {
		this.subtotalEnviado = subtotalEnviado;
	}

	/**
	 * @return the subtotalCorreccion
	 */
	public int getSubtotalCorreccion() {
		return subtotalCorreccion;
	}

	/**
	 * @param subtotalCorreccion the subtotalCorreccion to set
	 */
	public void setSubtotalCorreccion(int subtotalCorreccion) {
		this.subtotalCorreccion = subtotalCorreccion;
	}

	/**
	 * @return the subtotalCorregido
	 */
	public int getSubtotalCorregido() {
		return subtotalCorregido;
	}

	/**
	 * @param subtotalCorregido the subtotalCorregido to set
	 */
	public void setSubtotalCorregido(int subtotalCorregido) {
		this.subtotalCorregido = subtotalCorregido;
	}

	/**
	 * @return the subtotalRevisado
	 */
	public int getSubtotalRevisado() {
		return subtotalRevisado;
	}

	/**
	 * @param subtotalRevisado the subtotalRevisado to set
	 */
	public void setSubtotalRevisado(int subtotalRevisado) {
		this.subtotalRevisado = subtotalRevisado;
	}

	/**
	 * @return the subtotalRechazado
	 */
	public int getSubtotalRechazado() {
		return subtotalRechazado;
	}

	/**
	 * @param subtotalRechazado the subtotalRechazado to set
	 */
	public void setSubtotalRechazado(int subtotalRechazado) {
		this.subtotalRechazado = subtotalRechazado;
	}

	/**
	 * @return the subtotalAceptado
	 */
	public int getSubtotalAceptado() {
		return subtotalAceptado;
	}

	/**
	 * @param subtotalAceptado the subtotalAceptado to set
	 */
	public void setSubtotalAceptado(int subtotalAceptado) {
		this.subtotalAceptado = subtotalAceptado;
	}

	/**
	 * @return the subtotalFirmado
	 */
	public int getSubtotalFirmado() {
		return subtotalFirmado;
	}

	/**
	 * @param subtotalFirmado the subtotalFirmado to set
	 */
	public void setSubtotalFirmado(int subtotalFirmado) {
		this.subtotalFirmado = subtotalFirmado;
	}

	/**
	 * @return the totalRegistros
	 */
	public int getTotalRegistros() {
		return totalRegistros;
	}

	/**
	 * @param totalRegistros the totalRegistros to set
	 */
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	
	/**
	 * @return the versionBaseDatos
	 */
	public String getVersionBaseDatos() {
		return versionBaseDatos;
	}

	/**
	 * @param versionBaseDatos the versionBaseDatos to set
	 */
	public void setVersionBaseDatos(String versionBaseDatos) {
		this.versionBaseDatos = versionBaseDatos;
	}
	
	/**
	 * @return the versionEar
	 */
	public String getVersionEar() {
		return versionEar;
	}

	/**
	 * @param versionEar the versionEar to set
	 */
	public void setVersionEar(String versionEar) {
		this.versionEar = versionEar;
	}
	
}

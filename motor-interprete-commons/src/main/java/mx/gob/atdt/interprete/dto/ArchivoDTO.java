package mx.gob.atdt.interprete.dto;

import java.io.InputStream;
import java.io.Serializable;

public class ArchivoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4540401583808808973L;

	private String nombreArchivo;
	private InputStream contenidoArchivo;
		
	/**
	 * 
	 */
	public ArchivoDTO() {
	}
		
	/**
	 * @param nombreArchivo
	 * @param contenidoArchivo
	 */
	public ArchivoDTO(String nombreArchivo, InputStream contenidoArchivo) {
		this.nombreArchivo = nombreArchivo;
		this.contenidoArchivo = contenidoArchivo;
	}

	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	
	/**
	 * @param nombreArchivo the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}	

	/**
	 * @return the contenidoArchivo
	 */
	public InputStream getContenidoArchivo() {
		return contenidoArchivo;
	}

	/**
	 * @param contenidoArchivo the contenidoArchivo to set
	 */
	public void setContenidoArchivo(InputStream contenidoArchivo) {
		this.contenidoArchivo = contenidoArchivo;
	}
	
}

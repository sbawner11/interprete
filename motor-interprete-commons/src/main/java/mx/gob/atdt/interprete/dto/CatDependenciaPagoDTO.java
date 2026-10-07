package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatDependenciaPagoDTO implements Serializable {
	
	private static final long serialVersionUID = 2337634069334746700L;
	
	private int idDependenciaPago;
	private String sigla;
	private String descripcion;
	private boolean activo;
	
	/**
	 * 
	 */
	public CatDependenciaPagoDTO() {
	}

	/**
	 * @param idDependenciaPago
	 */
	public CatDependenciaPagoDTO(int idDependenciaPago) {
		this.idDependenciaPago = idDependenciaPago;
	}

	/**
	 * Constructor utilizado por las NamedQUery CatDependenciaPago.findAll y CatDependenciaPago.findById
	 * @param idDependenciaPago
	 * @param sigla
	 * @param descripcion
	 * @param activo
	 */
	public CatDependenciaPagoDTO(int idDependenciaPago, String sigla, String descripcion) {
		this.idDependenciaPago = idDependenciaPago;
		this.sigla = sigla;
		this.descripcion = descripcion;
	}
	
	

	public CatDependenciaPagoDTO(int idDependenciaPago, String sigla, String descripcion, boolean activo) {
		super();
		this.idDependenciaPago = idDependenciaPago;
		this.sigla = sigla;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	/**
	 * @return the idDependenciaPago
	 */
	public int getIdDependenciaPago() {
		return idDependenciaPago;
	}

	/**
	 * @param idDependenciaPago the idDependenciaPago to set
	 */
	public void setIdDependenciaPago(int idDependenciaPago) {
		this.idDependenciaPago = idDependenciaPago;
	}

	/**
	 * @return the sigla
	 */
	public String getSigla() {
		return sigla;
	}

	/**
	 * @param sigla the sigla to set
	 */
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatUnidadAdministrativaPagoDTO implements Serializable {
		
	private static final long serialVersionUID = 6388963606231328362L;
	
	private int idUnidadAdministrativaPago;
	private CatDependenciaPagoDTO catDependenciaPagoDTO;
	private String descripcion;
	private String clave;
	private boolean activo;
	
	/**
	 * 
	 */
	public CatUnidadAdministrativaPagoDTO() {
	}
	
	/**
	 * @param idUnidadAdministrativaPago
	 */
	public CatUnidadAdministrativaPagoDTO(Integer idUnidadAdministrativaPago) {
		this.idUnidadAdministrativaPago = idUnidadAdministrativaPago;
	}
	
	

	public CatUnidadAdministrativaPagoDTO(int idUnidadAdministrativaPago, String descripcion, String clave) {
		super();
		this.idUnidadAdministrativaPago = idUnidadAdministrativaPago;
		this.descripcion = descripcion;
		this.clave = clave;
	}

	/**
	 * Constructor utilizado por la NamedQuery CatUnidadAdministrativaPago.findById
	 * 
	 * @param idUnidadAdministrativaPago
	 * @param idDependenciaPagoDTO
	 * @param descripcion
	 * @param clave
	 * @param activo
	 */
	public CatUnidadAdministrativaPagoDTO(int idUnidadAdministrativaPago, int idDependenciaPago,
			String descripcion, String clave, boolean activo) {
		this.idUnidadAdministrativaPago = idUnidadAdministrativaPago;
		this.catDependenciaPagoDTO = new CatDependenciaPagoDTO(idDependenciaPago);
		this.descripcion = descripcion;
		this.clave = clave;
		this.activo = activo;
	}

	/**
	 * @return the idUnidadAdministrativaPago
	 */
	public int getIdUnidadAdministrativaPago() {
		return idUnidadAdministrativaPago;
	}

	/**
	 * @param idUnidadAdministrativaPago the idUnidadAdministrativaPago to set
	 */
	public void setIdUnidadAdministrativaPago(int idUnidadAdministrativaPago) {
		this.idUnidadAdministrativaPago = idUnidadAdministrativaPago;
	}

	/**
	 * @return the catDependenciaPagoDTO
	 */
	public CatDependenciaPagoDTO getCatDependenciaPagoDTO() {
		return catDependenciaPagoDTO;
	}

	/**
	 * @param catDependenciaPagoDTO the catDependenciaPagoDTO to set
	 */
	public void setCatDependenciaPagoDTO(CatDependenciaPagoDTO catDependenciaPagoDTO) {
		this.catDependenciaPagoDTO = catDependenciaPagoDTO;
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
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
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

package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoSecurityDomainDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5351253641056645149L;
	
	private Integer idTipoSecurityDomain;
	private String descripcion;
	
	/**
	 * 
	 */
	public CatTipoSecurityDomainDTO() {
	}
		
	/**
	 * @param idTipoSecurityDomain
	 */
	public CatTipoSecurityDomainDTO(Integer idTipoSecurityDomain) {
		this.idTipoSecurityDomain = idTipoSecurityDomain;
	}
	
	/**
	 * @param idTipoSecurityDomain
	 * @param descripcion
	 */
	public CatTipoSecurityDomainDTO(Integer idTipoSecurityDomain, String descripcion) {
		this.idTipoSecurityDomain = idTipoSecurityDomain;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idTipoSecurityDomain
	 */
	public Integer getIdTipoSecurityDomain() {
		return idTipoSecurityDomain;
	}

	/**
	 * @param idTipoSecurityDomain the idTipoSecurityDomain to set
	 */
	public void setIdTipoSecurityDomain(Integer idTipoSecurityDomain) {
		this.idTipoSecurityDomain = idTipoSecurityDomain;
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
	
}

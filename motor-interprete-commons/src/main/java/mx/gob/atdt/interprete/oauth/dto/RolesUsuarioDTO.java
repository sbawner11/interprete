package mx.gob.atdt.interprete.oauth.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RolesUsuarioDTO implements Serializable{

	private static final long serialVersionUID = -4952453659090103317L;
	private int idRol;
	private String rol;
	private int idAlcaldia;
	private String alcaldia;
	
	public RolesUsuarioDTO() {
	}
	
	public RolesUsuarioDTO(String rol) {
		this.rol = rol;
	}

	public RolesUsuarioDTO(int idRol, String rol) {
		this.idRol = idRol;
		this.rol = rol;
	}	

	/**
	 * @return the idRol
	 */
	public int getIdRol() {
		return idRol;
	}

	/**
	 * @param idRol the idRol to set
	 */
	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
	
	/**
	 * @return the idAlcaldia
	 */
	public int getIdAlcaldia() {
		return idAlcaldia;
	}

	/**
	 * @param idAlcaldia the idAlcaldia to set
	 */
	public void setIdAlcaldia(int idAlcaldia) {
		this.idAlcaldia = idAlcaldia;
	}

	/**
	 * @return the alcaldia
	 */
	public String getAlcaldia() {
		return alcaldia;
	}

	/**
	 * @param alcaldia the alcaldia to set
	 */
	public void setAlcaldia(String alcaldia) {
		this.alcaldia = alcaldia;
	}
}
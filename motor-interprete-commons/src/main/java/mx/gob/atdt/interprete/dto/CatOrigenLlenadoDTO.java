package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatOrigenLlenadoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8137010460287480859L;
	
	private Integer idOrigenLlenado;
	private String descripcion;
	private boolean activo;
	private boolean aplicaCampoTexto;
	private boolean aplicaRadioBoton;
	private boolean aplicaMenuDesplegable;
	private int orden;
	
	/**
	 * 
	 */
	public CatOrigenLlenadoDTO() {
	}
	
	public CatOrigenLlenadoDTO(Integer idOrigenLlenado) {
		this.idOrigenLlenado = idOrigenLlenado;

	}
	/**
	 * @param idOrigenLlenado
	 * @param descripcion
	 */
	public CatOrigenLlenadoDTO(Integer idOrigenLlenado, String descripcion,boolean activo, int orden, boolean aplicaCampoTexto, boolean aplicaRadioBoton, boolean aplicaMenuDesplegable) {
		this.idOrigenLlenado = idOrigenLlenado;
		this.descripcion = descripcion;
		this.activo = activo;
		this.orden = orden;
		this.aplicaCampoTexto = aplicaCampoTexto;
		this.aplicaRadioBoton = aplicaRadioBoton;
		this.aplicaMenuDesplegable = aplicaMenuDesplegable; 
	}

	/**
	 * @param idOrigenLlenado
	 * @param descripcion
	 * @param activo
	 */
	public CatOrigenLlenadoDTO(Integer idOrigenLlenado, String descripcion, boolean activo, boolean aplicaCampoTexto,
			boolean aplicaRadioBoton, boolean aplicaMenuDesplegable) {
		this.idOrigenLlenado = idOrigenLlenado;
		this.descripcion = descripcion;
		this.activo = activo;
		this.aplicaCampoTexto = aplicaCampoTexto;
	}

	/**
	 * @return the idOrigenLlenado
	 */
	public Integer getIdOrigenLlenado() {
		return idOrigenLlenado;
	}

	/**
	 * @param idOrigenLlenado the idOrigenLlenado to set
	 */
	public void setIdOrigenLlenado(Integer idOrigenLlenado) {
		this.idOrigenLlenado = idOrigenLlenado;
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

	public boolean isAplicaCampoTexto() {
		return aplicaCampoTexto;
	}

	public void setAplicaCampoTexto(boolean aplicaCampoTexto) {
		this.aplicaCampoTexto = aplicaCampoTexto;
	}

	/**
	 * @return the aplicaRadioBoton
	 */
	public boolean isAplicaRadioBoton() {
		return aplicaRadioBoton;
	}

	/**
	 * @param aplicaRadioBoton the aplicaRadioBoton to set
	 */
	public void setAplicaRadioBoton(boolean aplicaRadioBoton) {
		this.aplicaRadioBoton = aplicaRadioBoton;
	}

	/**
	 * @return the aplicaMenuDesplegable
	 */
	public boolean isAplicaMenuDesplegable() {
		return aplicaMenuDesplegable;
	}

	/**
	 * @param aplicaMenuDesplegable the aplicaMenuDesplegable to set
	 */
	public void setAplicaMenuDesplegable(boolean aplicaMenuDesplegable) {
		this.aplicaMenuDesplegable = aplicaMenuDesplegable;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	
}
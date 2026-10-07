package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatEjercicioDTO implements Serializable {
		
	private static final long serialVersionUID = -3269803184816160071L;
	
	private int idEjercicio;
	private int ejercicio;
	private boolean activo;
	
	/**
	 * 
	 */
	public CatEjercicioDTO() {
	}

	/**
	 * @param idEjercicio
	 */
	public CatEjercicioDTO(int idEjercicio) {
		this.idEjercicio = idEjercicio;
	}
	
	/**
	 * @param idEjercicio
	 * @param ejercicio
	 */
	public CatEjercicioDTO(int idEjercicio, int ejercicio) {
		this.idEjercicio = idEjercicio;
		this.ejercicio = ejercicio;
	}

	/**
	 * Constructor utilizado por las NamedQuery CatEjercicio.findAll, CatEjercicio.findById y CatEjercicio.findActivos
	 * @param idEjercicio
	 * @param ejercicio
	 * @param activo
	 */
	public CatEjercicioDTO(int idEjercicio, int ejercicio, boolean activo) {
		this.idEjercicio = idEjercicio;
		this.ejercicio = ejercicio;
		this.activo = activo;
	}

	/**
	 * @return the idEjercicio
	 */
	public int getIdEjercicio() {
		return idEjercicio;
	}

	/**
	 * @param idEjercicio the idEjercicio to set
	 */
	public void setIdEjercicio(int idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	/**
	 * @return the ejercicio
	 */
	public int getEjercicio() {
		return ejercicio;
	}

	/**
	 * @param ejercicio the ejercicio to set
	 */
	public void setEjercicio(int ejercicio) {
		this.ejercicio = ejercicio;
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

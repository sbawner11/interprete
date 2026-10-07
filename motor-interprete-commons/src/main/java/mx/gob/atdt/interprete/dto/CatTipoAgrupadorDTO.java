package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoAgrupadorDTO implements Serializable {
		
	private static final long serialVersionUID = -6253181807023608176L;
	
	private int idTipoAgrupador;
	private String tipoAgrupador;
	private String descripcion;
	
	/**
	 * 
	 */
	public CatTipoAgrupadorDTO() {
	}

	/**
	 * @param idTipoAgrupador
	 */
	public CatTipoAgrupadorDTO(int idTipoAgrupador) {
		this.idTipoAgrupador = idTipoAgrupador;
	}

	/**
	 * Constructor utilizado por la NamedQuery CatTipoAgrupador.findAll, CatTipoAgrupador.findById y CatTipoAgrupador.findByTipo
	 * @param idTipoAgrupador
	 * @param tipoAgrupador
	 * @param descripcion
	 */
	public CatTipoAgrupadorDTO(int idTipoAgrupador, String tipoAgrupador, String descripcion) {
		this.idTipoAgrupador = idTipoAgrupador;
		this.tipoAgrupador = tipoAgrupador;
		this.descripcion = descripcion;
	}

	/**
	 * @return the idTipoAgrupador
	 */
	public int getIdTipoAgrupador() {
		return idTipoAgrupador;
	}

	/**
	 * @param idTipoAgrupador the idTipoAgrupador to set
	 */
	public void setIdTipoAgrupador(int idTipoAgrupador) {
		this.idTipoAgrupador = idTipoAgrupador;
	}

	/**
	 * @return the tipoAgrupador
	 */
	public String getTipoAgrupador() {
		return tipoAgrupador;
	}

	/**
	 * @param tipoAgrupador the tipoAgrupador to set
	 */
	public void setTipoAgrupador(String tipoAgrupador) {
		this.tipoAgrupador = tipoAgrupador;
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

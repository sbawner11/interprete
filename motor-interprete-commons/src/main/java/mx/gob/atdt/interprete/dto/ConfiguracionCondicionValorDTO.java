package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class ConfiguracionCondicionValorDTO implements Serializable {

	private static final long serialVersionUID = -5520675827558290668L;

	private long idCondicionValor;
	private ConfiguracionCondicionesDTO configuracionCondicionesDTO;
	private int valor;

	/**
	 * 
	 */
	public ConfiguracionCondicionValorDTO() {
		configuracionCondicionesDTO = new ConfiguracionCondicionesDTO();
	}

	/**
	 * Constructor utilizado por la NamedQuery ConfiguracionCondicionValor.findAll
	 * @param idCondicionValor
	 */
	public ConfiguracionCondicionValorDTO(long idCondicionValor) {
		this.idCondicionValor = idCondicionValor;
	}

	/**
	 * Constructor utilizado por la NamedQuery ConfiguracionCondicionValor.findByIdConfiguracion
	 * @param idCondicionValor
	 * @param idConfiguracion
	 * @param valor
	 */
	public ConfiguracionCondicionValorDTO(long idCondicionValor, long idConfiguracion, int valor) {
		this.idCondicionValor = idCondicionValor;
		this.configuracionCondicionesDTO = new ConfiguracionCondicionesDTO(idConfiguracion);
		this.valor = valor;
	}

	/**
	 * @return the idCondicionValor
	 */
	public long getIdCondicionValor() {
		return idCondicionValor;
	}

	/**
	 * @param idCondicionValor the idCondicionValor to set
	 */
	public void setIdCondicionValor(long idCondicionValor) {
		this.idCondicionValor = idCondicionValor;
	}

	/**
	 * @return the configuracionCondicionesDTO
	 */
	public ConfiguracionCondicionesDTO getConfiguracionCondicionesDTO() {
		return configuracionCondicionesDTO;
	}

	/**
	 * @param configuracionCondicionesDTO the configuracionCondicionesDTO to set
	 */
	public void setConfiguracionCondicionesDTO(ConfiguracionCondicionesDTO configuracionCondicionesDTO) {
		this.configuracionCondicionesDTO = configuracionCondicionesDTO;
	}

	/**
	 * @return the valor
	 */
public int getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(int valor) {
		this.valor = valor;
	}

}
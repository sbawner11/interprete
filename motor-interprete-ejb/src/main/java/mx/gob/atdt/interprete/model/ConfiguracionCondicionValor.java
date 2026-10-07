package mx.gob.atdt.interprete.model;

import javax.persistence.*;

@Entity
@Table(name = "configuracion_condicion_valor", schema = "motor_interprete")
@NamedQueries({
	  @NamedQuery(name = "ConfiguracionCondicionValor.findByIdConfiguracion", 
			query = "SELECT new mx.gob.atdt.interprete.dto.ConfiguracionCondicionValorDTO( " +
					  " ccv.idCondicionValor, cc.idConfiguracion, ccv.valor ) " +
					  " FROM ConfiguracionCondicionValor ccv " + 
					  " JOIN ccv.configuracionCondiciones cc " +
			  		  " WHERE cc.idConfiguracion = :idConfiguracion"),
	  @NamedQuery(name = "ConfiguracionCondicionValor.findAll", 
	  		query = "SELECT new mx.gob.atdt.interprete.dto.ConfiguracionCondicionValorDTO( " +
	  				" ccv.idCondicionValor ) " + 
	  				" FROM ConfiguracionCondicionValor ccv " +
	  				" ORDER BY ccv.idCondicionValor") 
})

public class ConfiguracionCondicionValor implements java.io.Serializable {

	private static final long serialVersionUID = 5689757039212189012L;

	private long idCondicionValor;
	private ConfiguracionCondiciones configuracionCondiciones;
	private int valor;

	public ConfiguracionCondicionValor() {
	}

	public ConfiguracionCondicionValor(long idCondicionValor) {
		this.idCondicionValor = idCondicionValor;
	}

	public ConfiguracionCondicionValor(long idCondicionValor, ConfiguracionCondiciones configuracionCondiciones,
			int valor) {
		this.idCondicionValor = idCondicionValor;
		this.configuracionCondiciones = configuracionCondiciones;
		this.valor = valor;
	}
	
	@Id
	@Column(name = "id_condicion_valor", unique = true, nullable = false)
	public long getIdCondicionValor() {
		return this.idCondicionValor;
	}

	public void setIdCondicionValor(long idCondicionValor) {
		this.idCondicionValor = idCondicionValor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_configuracion", nullable = false)
	public ConfiguracionCondiciones getConfiguracionCondiciones() {
		return this.configuracionCondiciones;
	}

	public void setConfiguracionCondiciones(ConfiguracionCondiciones configuracionCondiciones) {
		this.configuracionCondiciones = configuracionCondiciones;
	}

	@Column(name = "valor", nullable = false)
	public int getValor() {
		return this.valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}
}
package mx.gob.atdt.interprete.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "cat_operador", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatOperador.findAll", 
		query = "SELECT new mx.gob.atdt.interprete.dto.CatOperadorDTO(co.idOperador, co.valorOperador, co.descripcion, co.activo) "
		+ "FROM CatOperador co "
		+ "WHERE co.activo = true "),
	@NamedQuery(name = "CatOperador.findById", 
		query = "SELECT new mx.gob.atdt.interprete.dto.CatOperadorDTO(co.idOperador, co.valorOperador, co.descripcion, co.activo) "
				+ "FROM CatOperador co "
				+ "WHERE co.idOperador = :idOperador ")
})

public class CatOperador implements Serializable {

	private static final long serialVersionUID = 2191367239619287830L;
			
    private Integer idOperador;
    private String valorOperador;
    private String descripcion;
    private Boolean activo;	
	private Set<ConfiguracionCondiciones> configuracionCondicioneses = new HashSet<>(0);
    
	/**
	 * 
	 */
	public CatOperador() {
    }
	
	public CatOperador(Integer idOperador, String valorOperador, String descripcion, Boolean activo) {
		this.idOperador = idOperador;
		this.valorOperador = valorOperador;
		this.descripcion = descripcion;
		this.activo = activo;
    }
	
	@Id
	@Column(name = "id_operador", unique = true, nullable = false)
	public Integer getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Integer idOperador) {
        this.idOperador = idOperador;
    }

    @Column(name = "valor_operador", nullable = false, length = 10)
    public String getValorOperador() {
        return valorOperador;
    }

    public void setValorOperador(String valorOperador) {
        this.valorOperador = valorOperador;
    }

    @Column(name = "descripcion", nullable = false, length = 50)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "activo", nullable = false)
    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catOperador")
	public Set<ConfiguracionCondiciones> getConfiguracionCondicioneses() {
		return this.configuracionCondicioneses;
	}

	public void setConfiguracionCondicioneses(Set<ConfiguracionCondiciones> configuracionCondicioneses) {
		this.configuracionCondicioneses = configuracionCondicioneses;
	}


}

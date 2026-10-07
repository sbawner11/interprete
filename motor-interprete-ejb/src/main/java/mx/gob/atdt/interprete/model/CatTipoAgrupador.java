package mx.gob.atdt.interprete.model;

import javax.persistence.*;

@Entity
@Table(name = "cat_tipo_agrupador", schema = "motor_interprete")

@NamedQueries({
    @NamedQuery(name = "CatTipoAgrupador.findAll", 
    		query = ""
    				+ "SELECT new mx.gob.atdt.interprete.dto.CatTipoAgrupadorDTO( "
    				+ "c.idTipoAgrupador, c.tipoAgrupador, c.descripcion) "
    				+ "FROM CatTipoAgrupador c "
    				+ "ORDER BY c.idTipoAgrupador"),
    
    @NamedQuery(name = "CatTipoAgrupador.findById", 
    		query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoAgrupadorDTO( "
    				+ "c.idTipoAgrupador, c.tipoAgrupador, c.descripcion) "
    				+ "FROM CatTipoAgrupador c "
    				+ "WHERE c.idTipoAgrupador = :idTipoAgrupador"),

    @NamedQuery(name = "CatTipoAgrupador.findByTipo", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoAgrupadorDTO( "
					+ "c.idTipoAgrupador, c.tipoAgrupador, c.descripcion) "
					+ "FROM CatTipoAgrupador c "
					+ "WHERE c.tipoAgrupador = :tipoAgrupador")

})

public class CatTipoAgrupador implements java.io.Serializable {
	
	private static final long serialVersionUID = -1691774424869813881L;
	
	private int idTipoAgrupador;
	private String tipoAgrupador;
	private String descripcion;

	public CatTipoAgrupador() {
	}

	public CatTipoAgrupador(int idTipoAgrupador, String tipoAgrupador, String descripcion) {
		this.idTipoAgrupador = idTipoAgrupador;
		this.tipoAgrupador = tipoAgrupador;
		this.descripcion = descripcion;
	}

	@Id
	@Column(name = "id_tipo_agrupador", unique = true, nullable = false)
	public int getIdTipoAgrupador() {
		return this.idTipoAgrupador;
	}

	public void setIdTipoAgrupador(int idTipoAgrupador) {
		this.idTipoAgrupador = idTipoAgrupador;
	}

	@Column(name = "tipo_agrupador", nullable = false, length = 1)
	public String getTipoAgrupador() {
		return this.tipoAgrupador;
	}

	public void setTipoAgrupador(String tipoAgrupador) {
		this.tipoAgrupador = tipoAgrupador;
	}

	@Column(name = "descripcion", nullable = false, length = 15)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}

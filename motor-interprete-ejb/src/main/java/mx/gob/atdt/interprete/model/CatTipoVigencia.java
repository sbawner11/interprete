package mx.gob.atdt.interprete.model;

import javax.persistence.*;

@Entity
@Table(name = "cat_tipo_vigencia", schema = "motor_interprete")

@NamedQueries({
    @NamedQuery(name = "CatTipoVigencia.findAll", 
    		query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoVigenciaDTO( "
    				+ "c.idTipoVigencia, c.clave, c.descripcion) "
    				+ "FROM CatTipoVigencia c "
    				+ "ORDER BY c.idTipoVigencia"),
    
    @NamedQuery(name = "CatTipoVigencia.findById", 
    		query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoVigenciaDTO( "
    				+ "c.idTipoVigencia, c.clave, c.descripcion) "
    				+ "FROM CatTipoVigencia c "
    				+ "WHERE c.idTipoVigencia = :idTipoVigencia")

})

public class CatTipoVigencia implements java.io.Serializable {
	
	private static final long serialVersionUID = 6659999860540678101L;
	
	private int idTipoVigencia;
	private String clave;
	private String descripcion;

	public CatTipoVigencia() {
	}

	public CatTipoVigencia(int idTipoVigencia, String clave, String descripcion) {
		this.idTipoVigencia = idTipoVigencia;
		this.clave = clave;
		this.descripcion = descripcion;
	}

	@Id
	@Column(name = "id_tipo_vigencia", unique = true, nullable = false)
	public int getIdTipoVigencia() {
		return this.idTipoVigencia;
	}

	public void setIdTipoVigencia(int idTipoVigencia) {
		this.idTipoVigencia = idTipoVigencia;
	}

	@Column(name = "clave", nullable = false, length = 1)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Column(name = "descripcion", nullable = false, length = 10)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}

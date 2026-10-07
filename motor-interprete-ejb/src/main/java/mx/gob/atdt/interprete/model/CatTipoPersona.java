package mx.gob.atdt.interprete.model;

import javax.persistence.*;

@Entity
@Table(name = "cat_tipo_persona", schema = "motor_interprete")

@NamedQueries({
    @NamedQuery(name = "CatTipoPersona.findAll", 
    		query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoPersonaDTO( "
    				+ "c.idTipoPersona, c.clave, c.descripcion, c.activo) "
    				+ "FROM CatTipoPersona c "
    				+ "ORDER BY c.idTipoPersona"),
    
    @NamedQuery(name = "CatTipoPersona.findById", 
    		query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoPersonaDTO( "
    				+ "c.idTipoPersona, c.clave, c.descripcion, c.activo) "
    				+ "FROM CatTipoPersona c "
    				+ "WHERE c.idTipoPersona = :idTipoPersona"),

    @NamedQuery(name = "CatTipoPersona.findActivos", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoPersonaDTO( "
					+ "c.idTipoPersona, c.clave, c.descripcion, c.activo) "
					+ "FROM CatTipoPersona c "
					+ "WHERE c.activo = true")
    
})

public class CatTipoPersona implements java.io.Serializable {
	
	private static final long serialVersionUID = 4641485242930328677L;
	
	private int idTipoPersona;
	private String clave;
	private String descripcion;
	private boolean activo;

	public CatTipoPersona() {
	}

	public CatTipoPersona(int idTipoPersona, String clave, String descripcion, boolean activo) {
		this.idTipoPersona = idTipoPersona;
		this.clave = clave;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_tipo_persona", unique = true, nullable = false)
	public int getIdTipoPersona() {
		return this.idTipoPersona;
	}

	public void setIdTipoPersona(int idTipoPersona) {
		this.idTipoPersona = idTipoPersona;
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

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}

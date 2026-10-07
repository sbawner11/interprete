package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "cat_periodicidad", schema = "motor_interprete")

@NamedQueries({
    @NamedQuery(name = "CatPeriodicidad.findAll", 
    		query = "SELECT new mx.gob.atdt.interprete.dto.CatPeriodicidadDTO( "
    				+ "c.idPeriodicidad, c.descripcion, c.clave) "
    				+ "FROM CatPeriodicidad c "
    				+ "ORDER BY c.idPeriodicidad"),
    
    @NamedQuery(name = "CatPeriodicidad.findById", 
    		query = "SELECT new mx.gob.atdt.interprete.dto.CatPeriodicidadDTO( "
    				+ "c.idPeriodicidad, c.descripcion, c.clave) "
    				+ "FROM CatPeriodicidad c "
    				+ "WHERE c.idPeriodicidad = :idPeriodicidad"),

    @NamedQuery(name = "CatPeriodicidad.findByClave", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatPeriodicidadDTO( "
					+ "c.idPeriodicidad, c.descripcion, c.clave) "
					+ "FROM CatPeriodicidad c "
					+ "WHERE c.clave = :clave")

})

public class CatPeriodicidad implements java.io.Serializable {
		
	private static final long serialVersionUID = -7362445961283278154L;
	
	private int idPeriodicidad;
	private String descripcion;
	private String clave;
	private Set<CatPeriodo> catPeriodos = new HashSet<>();

	public CatPeriodicidad() {
	}

	public CatPeriodicidad(int idPeriodicidad, String descripcion, String clave) {
		this.idPeriodicidad = idPeriodicidad;
		this.descripcion = descripcion;
		this.clave = clave;
	}

	public CatPeriodicidad(int idPeriodicidad, String descripcion, String clave, Set<CatPeriodo> catPeriodos) {
		this.idPeriodicidad = idPeriodicidad;
		this.descripcion = descripcion;
		this.clave = clave;
		this.catPeriodos = catPeriodos;
	}

	@Id
	@Column(name = "id_periodicidad", unique = true, nullable = false)
	public int getIdPeriodicidad() {
		return this.idPeriodicidad;
	}

	public void setIdPeriodicidad(int idPeriodicidad) {
		this.idPeriodicidad = idPeriodicidad;
	}

	@Column(name = "descripcion", nullable = false, length = 60)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "clave", nullable = false, length = 1)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catPeriodicidad")
	public Set<CatPeriodo> getCatPeriodos() {
		return this.catPeriodos;
	}

	public void setCatPeriodos(Set<CatPeriodo> catPeriodos) {
		this.catPeriodos = catPeriodos;
	}

}

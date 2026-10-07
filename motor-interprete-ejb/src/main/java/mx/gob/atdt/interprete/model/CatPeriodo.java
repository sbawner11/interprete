package mx.gob.atdt.interprete.model;

import javax.persistence.*;

@Entity
@Table(name = "cat_periodo", schema = "motor_interprete")

@NamedQueries({
    @NamedQuery(name = "CatPeriodo.findAll",
            query = "SELECT new mx.gob.atdt.interprete.dto.CatPeriodoDTO( "
                    + "c.idPeriodo, p.idPeriodicidad, c.descripcion, c.clave) "
                    + "FROM CatPeriodo c "
                    + "JOIN c.catPeriodicidad p "
                    + "ORDER BY c.idPeriodo"),

    @NamedQuery(name = "CatPeriodo.findById",
            query = "SELECT new mx.gob.atdt.interprete.dto.CatPeriodoDTO( "
                    + "c.idPeriodo, p.idPeriodicidad, c.descripcion, c.clave) "
                    + "FROM CatPeriodo c "
                    + "JOIN c.catPeriodicidad p "
                    + "WHERE c.idPeriodo = :idPeriodo"),

	@NamedQuery(name = "CatPeriodo.findByIdPeriodicidad",
	    	query = "SELECT new mx.gob.atdt.interprete.dto.CatPeriodoDTO( "
	            + "c.idPeriodo, p.idPeriodicidad, c.descripcion, c.clave) "
	            + "FROM CatPeriodo c "
	            + "JOIN c.catPeriodicidad p "
	            + "WHERE p.idPeriodicidad = :idPeriodicidad")    
})
public class CatPeriodo implements java.io.Serializable {
	
	private static final long serialVersionUID = -7279194708915974844L;
	
	private int idPeriodo;
	private CatPeriodicidad catPeriodicidad;
	private String descripcion;
	private String clave;

	public CatPeriodo() {
	}

	public CatPeriodo(int idPeriodo, CatPeriodicidad catPeriodicidad, String descripcion, String clave) {
		this.idPeriodo = idPeriodo;
		this.catPeriodicidad = catPeriodicidad;
		this.descripcion = descripcion;
		this.clave = clave;
	}

	@Id
	@Column(name = "id_periodo", unique = true, nullable = false)
	public int getIdPeriodo() {
		return this.idPeriodo;
	}

	public void setIdPeriodo(int idPeriodo) {
		this.idPeriodo = idPeriodo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_periodicidad", nullable = false)
	public CatPeriodicidad getCatPeriodicidad() {
		return this.catPeriodicidad;
	}

	public void setCatPeriodicidad(CatPeriodicidad catPeriodicidad) {
		this.catPeriodicidad = catPeriodicidad;
	}

	@Column(name = "descripcion", nullable = false, length = 60)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "clave", nullable = false, length = 3)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}

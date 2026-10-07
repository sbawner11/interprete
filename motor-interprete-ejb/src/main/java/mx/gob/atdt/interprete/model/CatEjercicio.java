package mx.gob.atdt.interprete.model;

import javax.persistence.*;

@Entity
@Table(name = "cat_ejercicio", schema = "motor_interprete")

@NamedQueries({
    @NamedQuery(name = "CatEjercicio.findAll", 
    		query = "SELECT new mx.gob.atdt.interprete.dto.CatEjercicioDTO( "
    				+ "c.idEjercicio, c.ejercicio, c.activo) "
    				+ "FROM CatEjercicio c "
    				+ "ORDER BY c.idEjercicio"),
    
    @NamedQuery(name = "CatEjercicio.findById", 
    		query = "SELECT new mx.gob.atdt.interprete.dto.CatEjercicioDTO( "
    				+ "c.idEjercicio, c.ejercicio, c.activo) "
    				+ "FROM CatEjercicio c "
    				+ "WHERE c.idEjercicio = :idEjercicio"),

    @NamedQuery(name = "CatEjercicio.findActivos", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatEjercicioDTO( "
					+ "c.idEjercicio, c.ejercicio, c.activo) "
					+ "FROM CatEjercicio c "
					+ "WHERE c.activo = true "
					+ "ORDER BY c.idEjercicio")

})

public class CatEjercicio implements java.io.Serializable {
		
	private static final long serialVersionUID = 1631782935806941885L;
	
	private int idEjercicio;
	private int ejercicio;
	private boolean activo;

	public CatEjercicio() {
	}

	public CatEjercicio(int idEjercicio, int ejercicio, boolean activo) {
		this.idEjercicio = idEjercicio;
		this.ejercicio = ejercicio;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_ejercicio", unique = true, nullable = false)
	public int getIdEjercicio() {
		return this.idEjercicio;
	}

	public void setIdEjercicio(int idEjercicio) {
		this.idEjercicio = idEjercicio;
	}

	@Column(name = "ejercicio", nullable = false)
	public int getEjercicio() {
		return this.ejercicio;
	}

	public void setEjercicio(int ejercicio) {
		this.ejercicio = ejercicio;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}

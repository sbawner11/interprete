package mx.gob.atdt.interprete.model;

import javax.persistence.*;

@Entity
@Table(name = "cat_dias_semana", schema = "motor_interprete")

@NamedQueries({
    @NamedQuery(name = "CatDiasSemana.findAll", 
    		query = ""
    				+ "SELECT new mx.gob.atdt.interprete.dto.CatDiasSemanaDTO( "
    				+ "c.idDiaSemana, c.descripcion, c.activo) "
    				+ "FROM CatDiasSemana c "
    				+ "ORDER BY c.idDiaSemana"),
    
    @NamedQuery(name = "CatDiasSemana.findById", 
    		query = "SELECT new mx.gob.atdt.interprete.dto.CatDiasSemanaDTO( "
    				+ "c.idDiaSemana, c.descripcion, c.activo) "
    				+ "FROM CatDiasSemana c "
    				+ "WHERE c.idDiaSemana = :idDiaSemana"),

    @NamedQuery(name = "CatDiasSemana.findActivos", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatDiasSemanaDTO( "
					+ "c.idDiaSemana, c.descripcion, c.activo) "
					+ "FROM CatDiasSemana c "
					+ "WHERE c.activo=true "
					+ "ORDER BY c.idDiaSemana")

})

public class CatDiasSemana implements java.io.Serializable {

	private static final long serialVersionUID = 5689757039212189012L;

    private Integer idDiaSemana;
    private String descripcion;
    private boolean activo;

    public CatDiasSemana() {}

    public CatDiasSemana(Integer idDiaSemana, String descripcion, boolean activo) {
        this.idDiaSemana = idDiaSemana;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public CatDiasSemana(mx.gob.atdt.interprete.dto.CatDiasSemanaDTO dto) {
        this.idDiaSemana = dto.getIdDiaSemana();
        this.descripcion = dto.getDescripcion();
        this.activo = dto.isActivo();
    }

    @Id
    @Column(name = "id_dia_semana", nullable = false)
    public Integer getIdDiaSemana() {
        return idDiaSemana;
    }

    public void setIdDiaSemana(Integer idDiaSemana) {
        this.idDiaSemana = idDiaSemana;
    }

    @Column(name = "descripcion", length = 60, nullable = false)
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Column(name = "activo", nullable = false)
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
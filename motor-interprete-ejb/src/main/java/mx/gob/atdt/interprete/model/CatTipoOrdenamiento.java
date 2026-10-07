package mx.gob.atdt.interprete.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "cat_tipo_ordenamiento", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(
		    name = "CatTipoOrdenamiento.findActivos",
		    query = "SELECT NEW mx.gob.atdt.interprete.dto.CatTipoOrdenamientoDTO(" +
		            "c.idTipoOrdenamiento, c.descripcion, c.activo) " +
		            "FROM CatTipoOrdenamiento c " +
		            "WHERE c.activo = true " +
		            "ORDER BY c.idTipoOrdenamiento"
		)
})
public class CatTipoOrdenamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_tipo_ordenamiento")
    private Long idTipoOrdenamiento;

    @Column(name = "descripcion", nullable = false, length = 200)
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private boolean activo;

    public Long getIdTipoOrdenamiento() {
        return idTipoOrdenamiento;
    }

    public void setIdTipoOrdenamiento(Long idTipoOrdenamiento) {
        this.idTipoOrdenamiento = idTipoOrdenamiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
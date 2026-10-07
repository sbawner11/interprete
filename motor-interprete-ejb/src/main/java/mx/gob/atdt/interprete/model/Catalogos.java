package mx.gob.atdt.interprete.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity 
@Table(name = "catalogos", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "Catalogos.findAll", 
			query = "SELECT NEW mx.gob.atdt.interprete.dto.CatalogosDTO(c.id, c.nombre) FROM Catalogos c"),
    @NamedQuery(name = "Catalogos.findById", 
        query = "SELECT c FROM Catalogos c WHERE c.idCatalogo = :idCatalogo")
})
public class Catalogos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_catalogo")
    private Integer idCatalogo;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "comentarios", length = 60)
    private String comentarios;

    public Catalogos() {
    }

    public Catalogos(Integer idCatalogo) {
        this.idCatalogo = idCatalogo;
    }

    public Integer getIdCatalogo() { return idCatalogo; }
    public void setIdCatalogo(Integer idCatalogo) { this.idCatalogo = idCatalogo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
}
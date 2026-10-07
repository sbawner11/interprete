package mx.gob.atdt.interprete.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "opciones_catalogo", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(
		    name = "OpcionesCatalogo.findByCatalogo",
		    query = "SELECT NEW mx.gob.atdt.interprete.dto.OpcionesCatalogoDTO(" +
		            "o.idOpcionCatalogo, o.catalogos.idCatalogo, o.descripcionOpcion ) " +
		            "FROM OpcionesCatalogo o WHERE o.catalogos.idCatalogo = :idCatalogo " +
		            "ORDER BY o.idOpcionCatalogo"
		),
    @NamedQuery(name = "OpcionesCatalogo.findById", 
        query = "SELECT o FROM OpcionesCatalogo o WHERE o.idOpcionCatalogo = :idOpcionCatalogo")
})
public class OpcionesCatalogo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_opcion_catalogo")
    private Integer idOpcionCatalogo;

    @ManyToOne
    @JoinColumn(name = "id_catalogo", referencedColumnName = "id_catalogo", nullable = false)
    private Catalogos catalogos;

    @Column(name = "descripcion_opcion", length = 60)
    private String descripcionOpcion;

    public OpcionesCatalogo() {
    }

    public OpcionesCatalogo(Integer idOpcionCatalogo) {
        this.idOpcionCatalogo = idOpcionCatalogo;
    }

    public Integer getIdOpcionCatalogo() { return idOpcionCatalogo; }
    public void setIdOpcionCatalogo(Integer idOpcionCatalogo) { this.idOpcionCatalogo = idOpcionCatalogo; }

    public Catalogos getCatalogos() { return catalogos; }
    public void setCatalogos(Catalogos catalogos) { this.catalogos = catalogos; }

    public String getDescripcionOpcion() { return descripcionOpcion; }
    public void setDescripcionOpcion(String descripcionOpcion) { this.descripcionOpcion = descripcionOpcion; }
}
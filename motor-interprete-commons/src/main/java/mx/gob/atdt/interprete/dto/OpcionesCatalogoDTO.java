package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class OpcionesCatalogoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idOpcionCatalogo;
    private Integer idCatalogo;
    private String descripcionOpcion;

    public OpcionesCatalogoDTO() {}
    
    public OpcionesCatalogoDTO(Integer idOpcionCatalogo, Integer idCatalogo, String descripcionOpcion) {
        this.idOpcionCatalogo = idOpcionCatalogo;
        this.idCatalogo = idCatalogo;
        this.descripcionOpcion = descripcionOpcion;
    }
    
    public Integer getIdOpcionCatalogo() { return idOpcionCatalogo; }
    public void setIdOpcionCatalogo(Integer idOpcionCatalogo) { this.idOpcionCatalogo = idOpcionCatalogo; }

    public Integer getIdCatalogo() { return idCatalogo; }
    public void setIdCatalogo(Integer idCatalogo) { this.idCatalogo = idCatalogo; }

    public String getDescripcionOpcion() { return descripcionOpcion; }
    public void setDescripcionOpcion(String descripcionOpcion) { this.descripcionOpcion = descripcionOpcion; }
    
    @Override
    public String toString() {
        return "OpcionesCatalogoDTO{" +
                "idOpcionCatalogo=" + idOpcionCatalogo +
                ", idCatalogo=" + idCatalogo +
                ", descripcionOpcion='" + descripcionOpcion + '\'' +
                '}';
    }
}
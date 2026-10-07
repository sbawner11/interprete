package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatalogosDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idCatalogo;
    private String nombre;
    private String comentarios;

    public CatalogosDTO() {}
    
    public CatalogosDTO(Integer idCatalogo, String nombre) {
        this.idCatalogo = idCatalogo;
        this.nombre = nombre;
    }
    
    public CatalogosDTO(Integer idCatalogo, String nombre, String comentarios) {
        this.idCatalogo = idCatalogo;
        this.nombre = nombre;
        this.comentarios = comentarios;
    }
    
    public Integer getIdCatalogo() { return idCatalogo; }
    public void setIdCatalogo(Integer idCatalogo) { this.idCatalogo = idCatalogo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getComentarios() { return comentarios; }
    public void setComentarios(String comentarios) { this.comentarios = comentarios; }
    
    @Override
    public String toString() {
        return "CatalogosDTO{" +
                "idCatalogo=" + idCatalogo +
                ", nombre='" + nombre + '\'' +
                ", comentarios='" + comentarios + '\'' +
                '}';
    }
    
}
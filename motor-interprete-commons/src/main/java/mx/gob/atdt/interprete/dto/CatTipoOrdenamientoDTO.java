package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatTipoOrdenamientoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long idTipoOrdenamiento;
    private String descripcion;
    private boolean activo;

    public CatTipoOrdenamientoDTO() {    	
    }    		

    public CatTipoOrdenamientoDTO(Long idTipoOrdenamiento) {    	
        this.idTipoOrdenamiento = idTipoOrdenamiento;
    }    		

    public CatTipoOrdenamientoDTO(
            final Long idTipoOrdenamiento,
            final String descripcion,
            final boolean activo) {

        this.idTipoOrdenamiento = idTipoOrdenamiento;
        this.descripcion = descripcion;
        this.activo = activo;
    }

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
package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatDiasSemanaDTO implements Serializable {

	private static final long serialVersionUID = -5184202528764714914L;

    private Integer idDiaSemana;
    private String descripcion;
    private boolean activo;

    public CatDiasSemanaDTO() {}

    public CatDiasSemanaDTO(Integer idDiaSemana) {
        this.idDiaSemana = idDiaSemana;
    }

    public CatDiasSemanaDTO(Integer idDiaSemana, String descripcion, boolean activo) {
        this.idDiaSemana = idDiaSemana;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public Integer getIdDiaSemana() {
        return idDiaSemana;
    }

    public void setIdDiaSemana(Integer idDiaSemana) {
        this.idDiaSemana = idDiaSemana;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "CatDiasSemanaDTO ["
        		+ "idDiaSemana=" + idDiaSemana + ", "
        		+ "descripcion=" + descripcion + ", "
        		+ "activo=" + activo + "]";
    }
}
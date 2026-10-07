package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatOperadorDTO implements Serializable {

	private static final long serialVersionUID = -5520675827558290668L;
	
	private Integer idOperador;
    private String valorOperador;
    private String descripcion;
    private Boolean activo;

	public CatOperadorDTO() {
    }
	
	/**
	 * @param idOperador
	 */
	public CatOperadorDTO(Integer idOperador) {
		this.idOperador = idOperador;
	}
	
	/**
	 * @param idOperador
	 * @param valorOperador
	 */
	public CatOperadorDTO(Integer idOperador, String valorOperador) {
		this.idOperador = idOperador;
		this.valorOperador = valorOperador;
	}

	/**
	 * @param idOperador
	 * @param valorOperador
	 * @param descripcion
	 * @param activo
	 */
	public CatOperadorDTO(Integer idOperador, String valorOperador, String descripcion, Boolean activo) {
        this.idOperador = idOperador;
        this.valorOperador = valorOperador;
        this.descripcion = descripcion;
        this.activo = activo;
    }
	
	public Integer getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(Integer idOperador) {
        this.idOperador = idOperador;
    }
	
	public String getValorOperador() {
        return valorOperador;
    }

    public void setValorOperador(String valorOperador) {
        this.valorOperador = valorOperador;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
	
    @Override
    public String toString() {
        return "CatOperadorDTO{" +
                "idOperador=" + idOperador +
                ", valorOperador='" + valorOperador + 
                ", descripcion='" + descripcion + 
                ", activo=" + activo +
                '}';
    }

}

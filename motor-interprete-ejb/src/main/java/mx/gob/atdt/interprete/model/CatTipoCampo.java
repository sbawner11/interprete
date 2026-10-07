package mx.gob.atdt.interprete.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat_tipo_campo", schema = "motor_interprete")
public class CatTipoCampo implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "id_cat_tipo_campo")
    private Integer id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

}
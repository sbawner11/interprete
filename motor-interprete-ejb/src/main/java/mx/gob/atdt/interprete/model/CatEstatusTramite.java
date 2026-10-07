package mx.gob.atdt.interprete.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity 
@Table(name = "cat_estatus_tramite", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "CatEstatusTramite.findAll", 
			query = "SELECT NEW mx.gob.atdt.interprete.dto.CatEstatusTramiteDTO("
					+ " c.idEstatusTramite, c.descripcion, c.descripcionAviso, c.descripcionPersonalizada) "
					+ " FROM CatEstatusTramite c"),
    @NamedQuery(name = "CatEstatusTramite.findById", 
        query = "SELECT  NEW mx.gob.atdt.interprete.dto.CatEstatusTramiteDTO("
        		+ " c.idEstatusTramite, c.descripcion, c.descripcionAviso, c.descripcionPersonalizada) "
        		+ " FROM CatEstatusTramite c "
        		+ " WHERE c.idEstatusTramite = :idEstatusTramite")
})
public class CatEstatusTramite implements Serializable {

    private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_estatus_tramite")
	private Integer idEstatusTramite;
													
	@Column(name = "descripcion", nullable = false, length = 60)
	private String descripcion;
													
	@Column(name = "descripcion_aviso", nullable = true, length = 60)
	private String descripcionAviso;
													
	@Column(name = "descripcion_personalizada", nullable = true, length = 20)
	private String descripcionPersonalizada;
													    
	public CatEstatusTramite() {
	}
													
	public CatEstatusTramite(Integer idEstatusTramite) {
		this.idEstatusTramite = idEstatusTramite;
	}
													
	public CatEstatusTramite(Integer idEstatusTramite, String descripcion, String descripcionAviso, String descripcionPersonalizada) {
		this.idEstatusTramite = idEstatusTramite;
		this.descripcion = descripcion;
		this.descripcionAviso = descripcionAviso;
		this.descripcionPersonalizada = descripcionPersonalizada;
	}

	public Integer getIdEstatusTramite() {
		return idEstatusTramite;
	}

	public void setIdEstatusTramite(Integer idEstatusTramite) {
		this.idEstatusTramite = idEstatusTramite;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionAviso() {
		return descripcionAviso;
	}

	public void setDescripcionAviso(String descripcionAviso) {
		this.descripcionAviso = descripcionAviso;
	}

	public String getDescripcionPersonalizada() {
		return descripcionPersonalizada;
	}

	public void setDescripcionPersonalizada(String descripcionPersonalizada) {
		this.descripcionPersonalizada = descripcionPersonalizada;
	}
    
}
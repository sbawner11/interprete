package mx.gob.atdt.interprete.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cat_motivos_pausa", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatMotivosPausa.findAll", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatMotivosPausaDTO(cmp.idMotivo, cmp.descripcion, cmp.descripcionMotivo) FROM CatMotivosPausa cmp")
})
public class CatMotivosPausa implements Serializable {

	private static final long serialVersionUID = 2191367239619287830L;
	private Integer idMotivo;
	private String descripcion;
	private String descripcionMotivo;
	private Set<DetPausasProyecto> detPausasProyectos = new HashSet<DetPausasProyecto>(0);

	public CatMotivosPausa() {

	}

	public CatMotivosPausa(Integer idMotivo, String descripcion, String descripcionMotivo) {
		super();
		this.idMotivo = idMotivo;
		this.descripcion = descripcion;
		this.descripcionMotivo = descripcionMotivo;
	}
	
	public CatMotivosPausa(int idMotivo, String descripcion, String descripcionMotivo,
			Set<DetPausasProyecto> detPausasProyectos) {
		this.idMotivo = idMotivo;
		this.descripcion = descripcion;
		this.descripcionMotivo = descripcionMotivo;
		this.detPausasProyectos = detPausasProyectos;
	}

	@Id
	@Column(name = "id_motivo", unique = true, nullable = false)
	public Integer getIdMotivo() {
		return idMotivo;
	}

	public void setIdMotivo(Integer idMotivo) {
		this.idMotivo = idMotivo;
	}

	@Column(name = "descripcion", nullable = false, length = 100)
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "descripcion_motivo", nullable = false, length = 200)
	public String getDescripcionMotivo() {
		return descripcionMotivo;
	}

	public void setDescripcionMotivo(String descripcionMotivo) {
		this.descripcionMotivo = descripcionMotivo;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catMotivosPausa")
	public Set<DetPausasProyecto> getDetPausasProyectos() {
		return this.detPausasProyectos;
	}

	public void setDetPausasProyectos(Set<DetPausasProyecto> detPausasProyectos) {
		this.detPausasProyectos = detPausasProyectos;
	}

}

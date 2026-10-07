package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "cat_estados_sistema", schema = "motor_interprete")
public class CatEstadosSistema implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 979610330705131482L;
	
	private int idEstadoSistema;
	private String descripcion;
	private Set<DetEstadoSistema> detEstadoSistemas = new HashSet<DetEstadoSistema>(0);

	public CatEstadosSistema() {
	}

	public CatEstadosSistema(int idEstadoSistema, String descripcion) {
		this.idEstadoSistema = idEstadoSistema;
		this.descripcion = descripcion;
	}

	public CatEstadosSistema(int idEstadoSistema, String descripcion, Set<DetEstadoSistema> detEstadoSistemas) {
		this.idEstadoSistema = idEstadoSistema;
		this.descripcion = descripcion;
		this.detEstadoSistemas = detEstadoSistemas;
	}

	@Id
	@Column(name = "id_estado_sistema", unique = true, nullable = false)
	public int getIdEstadoSistema() {
		return this.idEstadoSistema;
	}

	public void setIdEstadoSistema(int idEstadoSistema) {
		this.idEstadoSistema = idEstadoSistema;
	}

	@Column(name = "descripcion", nullable = false, length = 60)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catEstadosSistema")
	public Set<DetEstadoSistema> getDetEstadoSistemas() {
		return this.detEstadoSistemas;
	}

	public void setDetEstadoSistemas(Set<DetEstadoSistema> detEstadoSistemas) {
		this.detEstadoSistemas = detEstadoSistemas;
	}

}

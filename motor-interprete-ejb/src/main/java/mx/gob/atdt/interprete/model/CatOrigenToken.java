package mx.gob.atdt.interprete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "cat_origen_token", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "CatOrigenToken.findAll", query = "SELECT new mx.gob.atdt.interprete.dto.CatOrigenTokenDTO(cot.idOrigenToken, cot.descripcion, cot.activo) FROM CatOrigenToken cot") })
public class CatOrigenToken implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1849890545462366884L;

	private Integer idOrigenToken;
	private String descripcion;
	private boolean activo;

	public CatOrigenToken() {
	}

	public CatOrigenToken(Integer idOrigenToken, String descripcion, boolean activo) {
		this.idOrigenToken = idOrigenToken;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_origen_token", unique = true, nullable = false)
	public Integer getIdOrigenToken() {
		return this.idOrigenToken;
	}

	public void setIdOrigenToken(Integer idOrigenToken) {
		this.idOrigenToken = idOrigenToken;
	}

	@Column(name = "descripcion", nullable = false, length = 100)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}

package mx.gob.atdt.interprete.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "cat_estatus_carga_masiva", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "CatEstatusCargaMasiva.findAll",
			query = "SELECT NEW mx.gob.atdt.interprete.dto.CatEstatusCargaMasivaDTO(c.idEstatusCarga, c.descripcion) "
					+ " FROM CatEstatusCargaMasiva c ORDER BY c.idEstatusCarga ASC"),
	@NamedQuery(name = "CatEstatusCargaMasiva.findById",
			query = "SELECT NEW mx.gob.atdt.interprete.dto.CatEstatusCargaMasivaDTO(c.idEstatusCarga, c.descripcion) "
					+ " FROM CatEstatusCargaMasiva c WHERE c.idEstatusCarga = :idEstatusCarga")
})
public class CatEstatusCargaMasiva implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id_estatus_carga")
	private Integer idEstatusCarga;

	@Column(name = "descripcion", nullable = false, length = 100)
	private String descripcion;

	public CatEstatusCargaMasiva() {
	}

	public CatEstatusCargaMasiva(Integer idEstatusCarga) {
		this.idEstatusCarga = idEstatusCarga;
	}

	public Integer getIdEstatusCarga() {
		return idEstatusCarga;
	}

	public void setIdEstatusCarga(Integer idEstatusCarga) {
		this.idEstatusCarga = idEstatusCarga;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}

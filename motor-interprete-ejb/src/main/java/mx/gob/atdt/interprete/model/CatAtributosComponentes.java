package mx.gob.atdt.interprete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "cat_atributos_componentes", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "CatAtributosComponentes.findByIdTipoComponente", query = "SELECT new mx.gob.atdt.interprete.dto.CatAtributosComponentesDTO("
			+ " cac.idAtributoComponente, " + " cac.idTipoComponente, " + " cac.nombreAtributo, " + " cac.orden ) "
			+ " FROM CatAtributosComponentes cac " + " WHERE cac.idTipoComponente = :idTipoComponente "
			+ " order by cac.orden asc"),

	@NamedQuery(name = "CatAtributosComponentes.findAll", query = "SELECT new mx.gob.atdt.interprete.dto.CatAtributosComponentesDTO("
			+ " cac.idAtributoComponente, " + " cac.idTipoComponente, " + " cac.nombreAtributo, " + " cac.orden ) "
			+ " FROM CatAtributosComponentes cac " + " ORDER BY cac.orden ASC")

})

public class CatAtributosComponentes implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Long idAtributoComponente;
	private long idTipoComponente;
	private String nombreAtributo;
	private long orden;
	
	public CatAtributosComponentes() {
	}
	
	public CatAtributosComponentes(Long idAtributoComponente) {
		this.idAtributoComponente = idAtributoComponente;
	}

	public CatAtributosComponentes(Long idAtributoComponente, long idTipoComponente, String nombreAtributo,
			long orden) {
		super();
		this.idAtributoComponente = idAtributoComponente;
		this.idTipoComponente = idTipoComponente;
		this.nombreAtributo = nombreAtributo;
		this.orden = orden;
	}
	

	@Id
	@Column(name = "id_atributo_componente", unique = true, nullable = false)
	public Long getIdAtributoComponente() {
		return this.idAtributoComponente;
	}
	
	public void setIdAtributoComponente(Long idAtributoComponente) {
		this.idAtributoComponente = idAtributoComponente;
	}

	@Column(name = "id_tipo_componente", nullable = false)
	public long getIdTipoComponente() {
		return this.idTipoComponente;
	}

	public void setIdTipoComponente(long idTipoComponente) {
		this.idTipoComponente = idTipoComponente;
	}

	@Column(name = "nombre_atributo", nullable = false)
	public String getNombreAtributo() {
		return this.nombreAtributo;
	}

	public void setNombreAtributo(String nombreAtributo) {
		this.nombreAtributo = nombreAtributo;
	}

	@Column(name = "orden", nullable = false)
	public long getOrden() {
		return this.orden;
	}

	public void setOrden(long orden) {
		this.orden = orden;
	}
}

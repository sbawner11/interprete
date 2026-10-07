package mx.gob.atdt.interprete.model;

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
@Table(name = "cat_tipo_componente", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatTipoComponente.findAll", 
			query = " SELECT new mx.gob.atdt.interprete.dto.CatTipoComponenteDTO(ctc.idTipoComponente, ctc.descripcion,"
					+ " ctc.avanzado, ctc.activo ) "
					+ " FROM CatTipoComponente ctc "
					+ " ORDER BY ctc.orden"),
	@NamedQuery(name = "CatTipoComponente.findById", 
			query = " SELECT new mx.gob.atdt.interprete.dto.CatTipoComponenteDTO(ctc.idTipoComponente, ctc.descripcion,"
					+ " ctc.avanzado, ctc.activo ) "
					+ " FROM CatTipoComponente ctc "
					+ " WHERE ctc.idTipoComponente = :idTipoComponente"
					+ " ORDER BY ctc.idTipoComponente " ),
	@NamedQuery(name = "CatTipoComponente.findByTipoComponente", 
			query = " SELECT new mx.gob.atdt.interprete.dto.CatTipoComponenteDTO(ctc.idTipoComponente, ctc.descripcion)"  
					+ " FROM CatTipoComponente ctc " 
					+ " WHERE ctc.idTipoComponente =:idTipoComponente")
})
public class CatTipoComponente implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7425251685675875205L;
	
	private Integer idTipoComponente;
	private String descripcion;
	private boolean avanzado;
	private boolean activo;
	private int orden;
	private Set<Componente> componentes = new HashSet<Componente>(0);

	public CatTipoComponente() {
	}

	public CatTipoComponente(Integer idTipoComponente, String descripcion, boolean avanzado, boolean activo, int orden) {
		this.idTipoComponente = idTipoComponente;
		this.descripcion = descripcion;
		this.avanzado = avanzado;
		this.activo = activo;
		this.orden = orden;
	}

	public CatTipoComponente(Integer idTipoComponente, String descripcion, boolean avanzado, boolean activo, int orden,
			Set<Componente> componentes) {
		this.idTipoComponente = idTipoComponente;
		this.descripcion = descripcion;
		this.avanzado = avanzado;
		this.activo = activo;
		this.orden = orden;
		this.componentes = componentes;
	}
	
	public CatTipoComponente(Integer idTipoComponente) {
		this.idTipoComponente = idTipoComponente;

	}	

	@Id
	@Column(name = "id_tipo_componente", unique = true, nullable = false)
	public Integer getIdTipoComponente() {
		return this.idTipoComponente;
	}

	public void setIdTipoComponente(Integer idTipoComponente) {
		this.idTipoComponente = idTipoComponente;
	}

	@Column(name = "descripcion", nullable = false, length = 30)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "avanzado", nullable = false)
	public boolean isAvanzado() {
		return this.avanzado;
	}

	public void setAvanzado(boolean avanzado) {
		this.avanzado = avanzado;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	@Column(name = "orden", nullable = false)
	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catTipoComponente")
	public Set<Componente> getComponentes() {
		return this.componentes;
	}

	public void setComponentes(Set<Componente> componentes) {
		this.componentes = componentes;
	}

}
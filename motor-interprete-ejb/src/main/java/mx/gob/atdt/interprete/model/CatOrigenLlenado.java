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
@Table(name = "cat_origen_llenado", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatOrigenLlenado.findAll", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatOrigenLlenadoDTO(coll.idOrigenLlenado, coll.descripcion, coll.activo, coll.orden, coll.aplicaCampoTexto, coll.aplicaRadioBoton, coll.aplicaMenuDesplegable)"
					+ "FROM CatOrigenLlenado coll "
					+ "WHERE coll.activo = true "
					+ "AND coll.aplicaCampoTexto = true "
					+ "ORDER BY coll.orden "),
	@NamedQuery(name="CatOrigenLlenado.findAllMenuDesplegable", 
	query = "SELECT new mx.gob.atdt.interprete.dto.CatOrigenLlenadoDTO(coll.idOrigenLlenado, coll.descripcion, coll.activo, coll.orden, coll.aplicaCampoTexto, coll.aplicaRadioBoton, coll.aplicaMenuDesplegable) "
			+ "FROM CatOrigenLlenado coll "
			+ "WHERE coll.activo = true "
			+ "AND coll.aplicaMenuDesplegable = true "
			+ "ORDER BY coll.orden ")
})
public class CatOrigenLlenado implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1479044672332560355L;
	
	
	private Integer idOrigenLlenado;
	private String descripcion;
	private boolean activo;
	private boolean aplicaCampoTexto;
	private boolean aplicaRadioBoton;
	private boolean aplicaMenuDesplegable;
	private int orden;
	private Set<ComponenteCampoTexto> componenteCampoTextos = new HashSet<ComponenteCampoTexto>(0);
	private Set<ComponenteMenuDesplegable> componenteMenuDesplegables = new HashSet<ComponenteMenuDesplegable>(0);

	public CatOrigenLlenado() {
	}

	public CatOrigenLlenado(int idOrigenLlenado, String descripcion, boolean activo, boolean aplicaCampoTexto,
			boolean aplicaRadioBoton, boolean aplicaMenuDesplegable) {
		this.idOrigenLlenado = idOrigenLlenado;
		this.descripcion = descripcion;
		this.activo = activo;
		this.aplicaCampoTexto = aplicaCampoTexto;
		this.aplicaRadioBoton = aplicaRadioBoton;
		this.aplicaMenuDesplegable = aplicaMenuDesplegable;
	}

	public CatOrigenLlenado(int idOrigenLlenado, String descripcion, boolean activo, boolean aplicaCampoTexto,
			boolean aplicaRadioBoton, boolean aplicaMenuDesplegable, Set<ComponenteCampoTexto> componenteCampoTextos,
			Set<ComponenteMenuDesplegable> componenteMenuDesplegables) {
		this.idOrigenLlenado = idOrigenLlenado;
		this.descripcion = descripcion;
		this.activo = activo;
		this.aplicaCampoTexto = aplicaCampoTexto;
		this.aplicaRadioBoton = aplicaRadioBoton;
		this.aplicaMenuDesplegable = aplicaMenuDesplegable;
		this.componenteCampoTextos = componenteCampoTextos;
		this.componenteMenuDesplegables = componenteMenuDesplegables;
	}

	public CatOrigenLlenado(Integer idOrigenLlenado) {
		this.idOrigenLlenado = idOrigenLlenado;
	}
		
	@Id
	@Column(name = "id_origen_llenado", unique = true, nullable = false)
	public Integer getIdOrigenLlenado() {
		return this.idOrigenLlenado;
	}

	public void setIdOrigenLlenado(Integer idOrigenLlenado) {
		this.idOrigenLlenado = idOrigenLlenado;
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
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	@Column(name = "orden", nullable = false)
	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	@Column(name = "aplica_campo_texto", nullable = false)
	public boolean isAplicaCampoTexto() {
		return this.aplicaCampoTexto;
	}

	public void setAplicaCampoTexto(boolean aplicaCampoTexto) {
		this.aplicaCampoTexto = aplicaCampoTexto;
	}

	@Column(name = "aplica_radio_boton", nullable = false)
	public boolean isAplicaRadioBoton() {
		return this.aplicaRadioBoton;
	}

	public void setAplicaRadioBoton(boolean aplicaRadioBoton) {
		this.aplicaRadioBoton = aplicaRadioBoton;
	}

	@Column(name = "aplica_menu_desplegable", nullable = false)
	public boolean isAplicaMenuDesplegable() {
		return this.aplicaMenuDesplegable;
	}

	public void setAplicaMenuDesplegable(boolean aplicaMenuDesplegable) {
		this.aplicaMenuDesplegable = aplicaMenuDesplegable;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catOrigenLlenado")
	public Set<ComponenteCampoTexto> getComponenteCampoTextos() {
		return this.componenteCampoTextos;
	}

	public void setComponenteCampoTextos(Set<ComponenteCampoTexto> componenteCampoTextos) {
		this.componenteCampoTextos = componenteCampoTextos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catOrigenLlenado")
	public Set<ComponenteMenuDesplegable> getComponenteMenuDesplegables() {
		return this.componenteMenuDesplegables;
	}

	public void setComponenteMenuDesplegables(Set<ComponenteMenuDesplegable> componenteMenuDesplegables) {
		this.componenteMenuDesplegables = componenteMenuDesplegables;
	}
}
package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "componente_menu_desplegable", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "ComponenteMenuDesplegable.findByIdComponente", 
			query = "SELECT cmd "
			+ " FROM ComponenteMenuDesplegable cmd "
			+ " JOIN cmd.componente c "
			+ " WHERE c.idComponente = :idComponente"),
	@NamedQuery(name = "ComponenteMenuDesplegable.findByIdComponenteMenu", 
			query = "SELECT cmd "
			+ " FROM ComponenteMenuDesplegable cmd "
			+ " WHERE cmd.idComponenteMenu = :idComponenteMenu")
})
public class ComponenteMenuDesplegable implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6599893871184859834L;
	
	private Long idComponenteMenu;
	private CatOrigenLlenado catOrigenLlenado;
	private Componente componente;
	private boolean habilitaTextoInteriorOtro;
	private String textoInteriorOtro;
	private Set<DetElementosMenu> detElementosMenus = new HashSet<DetElementosMenu>(0);
	private CatTipoOrdenamiento catTipoOrdenamiento;

	public ComponenteMenuDesplegable() {
	}

	public ComponenteMenuDesplegable(Long idComponenteMenu, CatOrigenLlenado catOrigenLlenado, Componente componente) {
		this.idComponenteMenu = idComponenteMenu;
		this.catOrigenLlenado = catOrigenLlenado;
		this.componente = componente;
	}

	public ComponenteMenuDesplegable(Long idComponenteMenu, CatOrigenLlenado catOrigenLlenado, Componente componente, CatTipoOrdenamiento catTipoOrdenamiento) {
		this.idComponenteMenu = idComponenteMenu;
		this.catOrigenLlenado = catOrigenLlenado;
		this.componente = componente;
		this.catTipoOrdenamiento = catTipoOrdenamiento;
	}

	public ComponenteMenuDesplegable(Long idComponenteMenu, CatOrigenLlenado catOrigenLlenado, Componente componente,
			Set<DetElementosMenu> detElementosMenus) {
		this.idComponenteMenu = idComponenteMenu;
		this.catOrigenLlenado = catOrigenLlenado;
		this.componente = componente;
		this.detElementosMenus = detElementosMenus;
	}

	public ComponenteMenuDesplegable(Long idComponenteMenu, CatOrigenLlenado catOrigenLlenado, Componente componente,
			Set<DetElementosMenu> detElementosMenus, CatTipoOrdenamiento catTipoOrdenamiento) {
		this.idComponenteMenu = idComponenteMenu;
		this.catOrigenLlenado = catOrigenLlenado;
		this.componente = componente;
		this.detElementosMenus = detElementosMenus;
		this.catTipoOrdenamiento = catTipoOrdenamiento;
	}

	@Id
	@Column(name = "id_componente_menu", unique = true, nullable = false)
	public Long getIdComponenteMenu() {
		return this.idComponenteMenu;
	}

	public void setIdComponenteMenu(Long idComponenteMenu) {
		this.idComponenteMenu = idComponenteMenu;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_origen_llenado", nullable = false)
	public CatOrigenLlenado getCatOrigenLlenado() {
		return this.catOrigenLlenado;
	}

	public void setCatOrigenLlenado(CatOrigenLlenado catOrigenLlenado) {
		this.catOrigenLlenado = catOrigenLlenado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componenteMenuDesplegable")
	public Set<DetElementosMenu> getDetElementosMenus() {
		return this.detElementosMenus;
	}

	public void setDetElementosMenus(Set<DetElementosMenu> detElementosMenus) {
		this.detElementosMenus = detElementosMenus;
	}

	@Column(name = "habilita_opcion_otro", nullable = false)
	public boolean isHabilitaTextoInteriorOtro() {
		return habilitaTextoInteriorOtro;
	}


	public void setHabilitaTextoInteriorOtro(boolean habilitaTextoInteriorOtro) {
		this.habilitaTextoInteriorOtro = habilitaTextoInteriorOtro;
	}

	@Column(name = "texto_interior_otro", length = 100)
	public String getTextoInteriorOtro() {
		return textoInteriorOtro;
	}


	public void setTextoInteriorOtro(String textoInteriorOtro) {
		this.textoInteriorOtro = textoInteriorOtro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_ordenamiento", nullable = true)
	public CatTipoOrdenamiento getCatTipoOrdenamiento() {
		return catTipoOrdenamiento;
	}

	public void setCatTipoOrdenamiento(CatTipoOrdenamiento catTipoOrdenamiento) {
		this.catTipoOrdenamiento = catTipoOrdenamiento;
	}


}
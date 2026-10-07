package mx.gob.atdt.interprete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "det_elementos_menu", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "DetElementosMenu.findByIdComponenteMenu", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetElementosMenuDTO("
			+ " dem.idElementoMenu, cm.idComponenteMenu, dem.activo, dem.descripcionElemento) "
			+ " FROM DetElementosMenu dem "
			+ "	JOIN dem.componenteMenuDesplegable cm "
			+ " WHERE dem.componenteMenuDesplegable.idComponenteMenu = :idComponenteMenu")
})
public class DetElementosMenu implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -375517927932292990L;
	
	private Long idElementoMenu;
	private ComponenteMenuDesplegable componenteMenuDesplegable;
	private String descripcionElemento;
	private boolean activo;
	
	public DetElementosMenu() {
	}

	public DetElementosMenu(Long idElementoMenu, ComponenteMenuDesplegable componenteMenuDesplegable,
			String descripcionElemento, boolean activo) {
		this.idElementoMenu = idElementoMenu;
		this.componenteMenuDesplegable = componenteMenuDesplegable;
		this.descripcionElemento = descripcionElemento;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_elemento_menu", unique = true, nullable = false)
	public Long getIdElementoMenu() {
		return this.idElementoMenu;
	}

	public void setIdElementoMenu(Long idElementoMenu) {
		this.idElementoMenu = idElementoMenu;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente_menu", nullable = false)
	public ComponenteMenuDesplegable getComponenteMenuDesplegable() {
		return this.componenteMenuDesplegable;
	}

	public void setComponenteMenuDesplegable(ComponenteMenuDesplegable componenteMenuDesplegable) {
		this.componenteMenuDesplegable = componenteMenuDesplegable;
	}

	@Column(name = "descripcion_elemento", nullable = false, length = 200)
	public String getDescripcionElemento() {
		return this.descripcionElemento;
	}

	public void setDescripcionElemento(String descripcionElemento) {
		this.descripcionElemento = descripcionElemento;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}	

}

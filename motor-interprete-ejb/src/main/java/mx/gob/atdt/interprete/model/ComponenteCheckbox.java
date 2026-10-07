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
@Table(name = "componente_checkbox", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "ComponenteCheckbox.findByIdComponente", 
			query = "SELECT crb "
			+ " FROM ComponenteCheckbox crb "
			+ " JOIN crb.componente c "
			+ " WHERE c.idComponente = :idComponente"),
	@NamedQuery(name = "ComponenteCheckbox.findByIdComponenteCheckbox", 
			query = "SELECT crb "
			+ " FROM ComponenteCheckbox crb "
			+ " WHERE crb.idComponenteCheckbox = :idComponenteCheckbox")
})
public class ComponenteCheckbox implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1098505166040493210L;
	
	private Long idComponenteCheckbox;
	private Componente componente;
	private boolean habilitaTodosNinguno;
	private Set<DetElementosCheckbox> detElementosCheckboxes = new HashSet<DetElementosCheckbox>(0);

	public ComponenteCheckbox() {
	}

	public ComponenteCheckbox(Long idComponenteCheckbox, Componente componente, boolean habilitaTodosNinguno) {
		this.idComponenteCheckbox = idComponenteCheckbox;
		this.componente = componente;
		this.habilitaTodosNinguno = habilitaTodosNinguno;
	}

	public ComponenteCheckbox(Long idComponenteCheckbox, Componente componente, boolean habilitaTodosNinguno,
			Set<DetElementosCheckbox> detElementosCheckboxes) {
		this.idComponenteCheckbox = idComponenteCheckbox;
		this.componente = componente;
		this.habilitaTodosNinguno = habilitaTodosNinguno;
		this.detElementosCheckboxes = detElementosCheckboxes;
	}

	@Id
	@Column(name = "id_componente_checkbox", unique = true, nullable = false)
	public Long getIdComponenteCheckbox() {
		return this.idComponenteCheckbox;
	}

	public void setIdComponenteCheckbox(Long idComponenteCheckbox) {
		this.idComponenteCheckbox = idComponenteCheckbox;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@Column(name = "habilita_todos_ninguno", nullable = false)
	public boolean isHabilitaTodosNinguno() {
		return this.habilitaTodosNinguno;
	}

	public void setHabilitaTodosNinguno(boolean habilitaTodosNinguno) {
		this.habilitaTodosNinguno = habilitaTodosNinguno;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componenteCheckbox")
	public Set<DetElementosCheckbox> getDetElementosCheckboxes() {
		return this.detElementosCheckboxes;
	}

	public void setDetElementosCheckboxes(Set<DetElementosCheckbox> detElementosCheckboxes) {
		this.detElementosCheckboxes = detElementosCheckboxes;
	}

}

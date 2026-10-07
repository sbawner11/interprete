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
@Table(name = "componente_radioboton", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "ComponenteRadioboton.findByIdComponente", 
			query = "SELECT crb "
			+ " FROM ComponenteRadioboton crb "
			+ " JOIN crb.componente c "
			+ " WHERE c.idComponente = :idComponente"),
	@NamedQuery(name = "ComponenteRadioboton.findByIdComponenteRadioboton", 
			query = "SELECT crb "
			+ " FROM ComponenteRadioboton crb "
			+ " WHERE crb.idComponenteRadioboton = :idComponenteRadioboton")
})
public class ComponenteRadioboton implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8438986747882772203L;
	
	private Long idComponenteRadioboton;
	private Componente componente;
	private boolean habilitaOpcionOtro;
	private String textoInteriorOtro;
	private Set<DetElementosRadioboton> detElementosRadiobotons = new HashSet<DetElementosRadioboton>(0);

	public ComponenteRadioboton() {
	}

	public ComponenteRadioboton(Long idComponenteRadioboton, Componente componente, boolean habilitaOpcionOtro) {
		this.idComponenteRadioboton = idComponenteRadioboton;
		this.componente = componente;
		this.habilitaOpcionOtro = habilitaOpcionOtro;
	}

	public ComponenteRadioboton(Long idComponenteRadioboton, Componente componente, boolean habilitaOpcionOtro,
			String textoInteriorOtro, Set<DetElementosRadioboton> detElementosRadiobotons) {
		this.idComponenteRadioboton = idComponenteRadioboton;
		this.componente = componente;
		this.habilitaOpcionOtro = habilitaOpcionOtro;
		this.textoInteriorOtro = textoInteriorOtro;
		this.detElementosRadiobotons = detElementosRadiobotons;
	}

	@Id
	@Column(name = "id_componente_radioboton", unique = true, nullable = false)
	public Long getIdComponenteRadioboton() {
		return this.idComponenteRadioboton;
	}

	public void setIdComponenteRadioboton(Long idComponenteRadioboton) {
		this.idComponenteRadioboton = idComponenteRadioboton;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@Column(name = "habilita_opcion_otro", nullable = false)
	public boolean isHabilitaOpcionOtro() {
		return this.habilitaOpcionOtro;
	}

	public void setHabilitaOpcionOtro(boolean habilitaOpcionOtro) {
		this.habilitaOpcionOtro = habilitaOpcionOtro;
	}

	@Column(name = "texto_interior_otro", length = 100)
	public String getTextoInteriorOtro() {
		return this.textoInteriorOtro;
	}

	public void setTextoInteriorOtro(String textoInteriorOtro) {
		this.textoInteriorOtro = textoInteriorOtro;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componenteRadioboton")
	public Set<DetElementosRadioboton> getDetElementosRadiobotons() {
		return this.detElementosRadiobotons;
	}

	public void setDetElementosRadiobotons(Set<DetElementosRadioboton> detElementosRadiobotons) {
		this.detElementosRadiobotons = detElementosRadiobotons;
	}

}
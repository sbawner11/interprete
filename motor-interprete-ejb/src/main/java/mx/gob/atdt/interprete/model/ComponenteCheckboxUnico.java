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
@Table(name = "componente_checkbox_unico", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "ComponenteCheckboxUnico.findByIdComponente", 
			query = "SELECT ccu "
			+ " FROM ComponenteCheckboxUnico ccu "
			+ " JOIN ccu.componente c "
			+ " WHERE c.idComponente = :idComponente")
})
public class ComponenteCheckboxUnico implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1098505166040493210L;
	
	private Long idComponenteCheckboxUnico;
	private Componente componente;
	private String texto;
	
	public ComponenteCheckboxUnico() {
	}

	public ComponenteCheckboxUnico(Long idComponenteCheckboxUnico, Componente componente, String texto) {
		this.idComponenteCheckboxUnico = idComponenteCheckboxUnico;
		this.componente = componente;
		this.texto = texto;
	}

	@Id
	@Column(name = "id_componente_checkbox_unico", unique = true, nullable = false)
	public Long getIdComponenteCheckboxUnico() {
		return this.idComponenteCheckboxUnico;
	}

	public void setIdComponenteCheckboxUnico(Long idComponenteCheckboxUnico) {
		this.idComponenteCheckboxUnico = idComponenteCheckboxUnico;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@Column(name = "texto", nullable = false)
	public String getTexto() {
		return this.texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}

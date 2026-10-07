package mx.gob.atdt.interprete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "componente_informativo", schema = "motor_interprete")
public class ComponenteInformativo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4546839292057792630L;
	
	private Long idComponenteInformativo;
	private Componente componente;
	private String textoInformativo;

	public ComponenteInformativo() {
	}

	public ComponenteInformativo(Long idComponenteInformativo, Componente componente, String textoInformativo) {
		this.idComponenteInformativo = idComponenteInformativo;
		this.componente = componente;
		this.textoInformativo = textoInformativo;
	}

	@Id
	@Column(name = "id_componente_informativo", unique = true, nullable = false)
	public Long getIdComponenteInformativo() {
		return this.idComponenteInformativo;
	}

	public void setIdComponenteInformativo(Long idComponenteInformativo) {
		this.idComponenteInformativo = idComponenteInformativo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@Column(name = "texto_informativo", nullable = false)
	public String getTextoInformativo() {
		return this.textoInformativo;
	}

	public void setTextoInformativo(String textoInformativo) {
		this.textoInformativo = textoInformativo;
	}

}

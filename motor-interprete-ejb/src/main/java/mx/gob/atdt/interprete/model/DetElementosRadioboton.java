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
@Table(name = "det_elementos_radioboton", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "DetElementosRadioboton.findByIdComponenteRadioboton", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetElementosRadiobotonDTO("
			+ " der.idElementoRadioboton, cr.idComponenteRadioboton, der.activo, der.orden, der.descripcionElemento) "
			+ " FROM DetElementosRadioboton der "
			+ "	JOIN der.componenteRadioboton cr "
			+ " WHERE der.componenteRadioboton.idComponenteRadioboton = :idComponenteRadioboton")
})
public class DetElementosRadioboton implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4859415814436634571L;
	
	private Long idElementoRadioboton;
	private ComponenteRadioboton componenteRadioboton;
	private boolean activo;
	private int orden;
	private String descripcionElemento;

	public DetElementosRadioboton() {
		
	}

	public DetElementosRadioboton(Long idElementoRadioboton, ComponenteRadioboton componenteRadioboton,
			boolean activo) {
		this.idElementoRadioboton = idElementoRadioboton;
		this.componenteRadioboton = componenteRadioboton;
		this.activo = activo;
	}

	public DetElementosRadioboton(Long idElementoRadioboton, ComponenteRadioboton componenteRadioboton,
			boolean activo,int orden, String descripcionElemento) {
		this.idElementoRadioboton = idElementoRadioboton;
		this.componenteRadioboton = componenteRadioboton;
		this.activo = activo;
		this.orden = orden;
		this.descripcionElemento = descripcionElemento;
	}

	@Id
	@Column(name = "id_elemento_radioboton", unique = true, nullable = false)
	public Long getIdElementoRadioboton() {
		return this.idElementoRadioboton;
	}

	public void setIdElementoRadioboton(Long idElementoRadioboton) {
		this.idElementoRadioboton = idElementoRadioboton;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente_radioboton", nullable = false)
	public ComponenteRadioboton getComponenteRadioboton() {
		return this.componenteRadioboton;
	}

	public void setComponenteRadioboton(ComponenteRadioboton componenteRadioboton) {
		this.componenteRadioboton = componenteRadioboton;
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

	@Column(name = "descripcion_elemento", length = 100)
	public String getDescripcionElemento() {
		return this.descripcionElemento;
	}

	public void setDescripcionElemento(String descripcionElemento) {
		this.descripcionElemento = descripcionElemento;
	}

}
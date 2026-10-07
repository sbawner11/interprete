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
@Table(name = "det_elementos_checkbox", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "DetElementosCheckbox.findByIdComponenteCheckbox", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetElementosCheckboxDTO("
			+ " dec.idElementoCheckbox, cc.idComponenteCheckbox, dec.activo, dec.orden, dec.descripcionElemento) "
			+ " FROM DetElementosCheckbox dec "
			+ "	JOIN dec.componenteCheckbox cc "
			+ " WHERE dec.componenteCheckbox.idComponenteCheckbox = :idComponenteCheckbox")
})
public class DetElementosCheckbox implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3017520642897301026L;
	
	private Long idElementoCheckbox;
	private ComponenteCheckbox componenteCheckbox;
	private boolean activo;
	private int orden;
	private String descripcionElemento;
	
	public DetElementosCheckbox() {
	}

	public DetElementosCheckbox(Long idElementoCheckbox, ComponenteCheckbox componenteCheckbox,
			boolean activo) {
		this.idElementoCheckbox = idElementoCheckbox;
		this.componenteCheckbox = componenteCheckbox;
		this.activo = activo;
	}

	public DetElementosCheckbox(Long idElementoCheckbox, ComponenteCheckbox componenteCheckbox,
			boolean activo,int orden, String descripcionElemento) {
		this.idElementoCheckbox = idElementoCheckbox;
		this.componenteCheckbox = componenteCheckbox;
		this.activo = activo;
		this.orden = orden;
		this.descripcionElemento = descripcionElemento;
	}

	@Id
	@Column(name = "id_elemento_checkbox", unique = true, nullable = false)
	public Long getIdElementoCheckbox() {
		return this.idElementoCheckbox;
	}

	public void setIdElementoCheckbox(Long idElementoCheckbox) {
		this.idElementoCheckbox = idElementoCheckbox;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente_checkbox", nullable = false)
	public ComponenteCheckbox getComponenteCheckbox() {
		return this.componenteCheckbox;
	}

	public void setComponenteCheckbox(ComponenteCheckbox componenteCheckbox) {
		this.componenteCheckbox = componenteCheckbox;
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

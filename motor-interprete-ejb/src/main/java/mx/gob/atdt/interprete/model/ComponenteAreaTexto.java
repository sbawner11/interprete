package mx.gob.atdt.interprete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name = "componente_area_texto", schema = "motor_interprete")

@NamedQueries({
	@NamedQuery(name = "ComponenteAreaTexto.findComponentesAreaTextoByIdComponente",
			query = "SELECT new mx.gob.atdt.interprete.dto.ComponenteAreaTextoDTO( "
			+ "cat.idComponenteAreaTexto,c.idComponente, c.catTipoComponente.idTipoComponente, c.subseccionesFormulario.idSubseccionFormulario,"
			+ " c.orden, c.requerido, c.tooltip, c.descripcionTooltip, c.tituloCampo, c.activo, c.fechaCreacion, c.fechaUltimaActualizacion, c.seccionSincronizada, "
			+ "cat.habilitaTextoInterior, cat.textoInterior, cat.catOrigenLlenado.idOrigenLlenado, cat.lineasAltura ) "
			+ " FROM ComponenteAreaTexto cat "
			+ " JOIN cat.componente c "
			+ " WHERE c.idComponente = :idComponente " )
})

public class ComponenteAreaTexto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4083797433914859404L;
	
	private long idComponenteAreaTexto;
	private CatOrigenLlenado catOrigenLlenado;
	private Componente componente;
	private boolean habilitaTextoInterior;
	private String textoInterior;
	private int lineasAltura;

	public ComponenteAreaTexto() {
	}

	public ComponenteAreaTexto(long idComponenteAreaTexto, CatOrigenLlenado catOrigenLlenado,
			Componente componente, boolean habilitaTextoInterior) {
		this.idComponenteAreaTexto = idComponenteAreaTexto;
		this.catOrigenLlenado = catOrigenLlenado;
		this.componente = componente;
		this.habilitaTextoInterior = habilitaTextoInterior;
	}

	public ComponenteAreaTexto(long idComponenteAreaTexto, CatOrigenLlenado catOrigenLlenado,
			Componente componente, boolean habilitaTextoInterior, String textoInterior ) {
		this.idComponenteAreaTexto = idComponenteAreaTexto;
		this.catOrigenLlenado = catOrigenLlenado;
		this.componente = componente;
		this.habilitaTextoInterior = habilitaTextoInterior;
		this.textoInterior = textoInterior;
	}

	@Id
	@Column(name = "id_componente_area_texto", unique = true, nullable = false)
	public long getIdComponenteAreaTexto() {
		return this.idComponenteAreaTexto;
	}

	public void setIdComponenteAreaTexto(long idComponenteAreaTexto) {
		this.idComponenteAreaTexto = idComponenteAreaTexto;
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

	@Column(name = "habilita_texto_interior", nullable = false)
	public boolean isHabilitaTextoInterior() {
		return this.habilitaTextoInterior;
	}

	public void setHabilitaTextoInterior(boolean habilitaTextoInterior) {
		this.habilitaTextoInterior = habilitaTextoInterior;
	}

	@Column(name = "texto_interior", length = 100)
	public String getTextoInterior() {
		return this.textoInterior;
	}

	public void setTextoInterior(String textoInterior) {
		this.textoInterior = textoInterior;
	}

	@Column(name = "lineas_altura", nullable = false)
	public int getLineasAltura() {
		return this.lineasAltura;
	}

	public void setLineasAltura(int lineasAltura) {
		this.lineasAltura = lineasAltura;
	}

}

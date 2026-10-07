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
@Table(name = "componente_campo_texto", schema = "motor_interprete")

@NamedQueries({
	@NamedQuery(name = "ComponenteCampoTexto.findComponentesCampoTextoByIdComponente",
			query = "SELECT new mx.gob.atdt.interprete.dto.ComponenteCampoTextoDTO("
			+ "cct.idComponenteCampoTexto,c.idComponente, c.catTipoComponente.idTipoComponente, c.subseccionesFormulario.idSubseccionFormulario,"
			+ "	c.orden, c.requerido, c.tooltip, c.descripcionTooltip, c.tituloCampo, c.activo, c.fechaCreacion, c.fechaUltimaActualizacion, c.seccionSincronizada, cct.alfanumerico, cct.numerico, "
			+ "cct.habilitaTextoInterior, cct.textoInterior, cct.catOrigenLlenado.idOrigenLlenado, cct.validadores, "
			+ "cct.catValidadores.idValidador, cct.valorMinimo, cct.valorMaximo, cct.permiteDecimales ) "
			+ " FROM ComponenteCampoTexto cct "
			+ " JOIN cct.componente c "
			+ " WHERE c.idComponente = :idComponente " ),
	@NamedQuery(name = "ComponenteCampoTexto.findById",
			query = "SELECT new mx.gob.atdt.interprete.dto.ComponenteCampoTextoDTO( "
			+ "		cct.idComponenteCampoTexto, cct.alfanumerico, cct.numerico, "
			+ "		cct.habilitaTextoInterior, cct.textoInterior, cct.catOrigenLlenado.idOrigenLlenado, cct.validadores, "
			+ "		cct.catValidadores.idValidador, cct.valorMinimo, cct.valorMaximo, cct.permiteDecimales ) "
			+ " FROM ComponenteCampoTexto cct "
			+ " WHERE cct.idComponenteCampoTexto = :idComponenteCampoTexto " )
})

public class ComponenteCampoTexto implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4083797433914859404L;
	
	private long idComponenteCampoTexto;
	private CatOrigenLlenado catOrigenLlenado;
	private CatValidadores catValidadores;
	private Componente componente;
	private boolean alfanumerico;
	private boolean numerico;
	private boolean habilitaTextoInterior;
	private String textoInterior;
	private boolean validadores;
	private Long valorMinimo;
	private Long valorMaximo;
	private boolean permiteDecimales;

	public ComponenteCampoTexto() {
	}

	public ComponenteCampoTexto(long idComponenteCampoTexto, CatOrigenLlenado catOrigenLlenado,
			CatValidadores catValidadores, Componente componente, boolean alfanumerico, boolean numerico,
			boolean habilitaTextoInterior, boolean validadores) {
		this.idComponenteCampoTexto = idComponenteCampoTexto;
		this.catOrigenLlenado = catOrigenLlenado;
		this.catValidadores = catValidadores;
		this.componente = componente;
		this.alfanumerico = alfanumerico;
		this.numerico = numerico;
		this.habilitaTextoInterior = habilitaTextoInterior;
		this.validadores = validadores;
	}

	public ComponenteCampoTexto(long idComponenteCampoTexto, CatOrigenLlenado catOrigenLlenado,
			CatValidadores catValidadores, Componente componente, boolean alfanumerico, boolean numerico,
			boolean habilitaTextoInterior, String textoInterior, boolean origenLlenado, boolean validadores, boolean limiteCaracteres,
			Long valorMinimo, Long valorMaximo, boolean permiteDecimales ) {
		this.idComponenteCampoTexto = idComponenteCampoTexto;
		this.catOrigenLlenado = catOrigenLlenado;
		this.catValidadores = catValidadores;
		this.componente = componente;
		this.alfanumerico = alfanumerico;
		this.numerico = numerico;
		this.habilitaTextoInterior = habilitaTextoInterior;
		this.textoInterior = textoInterior;
		this.validadores = validadores;
		this.valorMinimo = valorMinimo;
		this.valorMaximo = valorMaximo;
		this.permiteDecimales = permiteDecimales;
	}

	@Id
	@Column(name = "id_componente_campo_texto", unique = true, nullable = false)
	public long getIdComponenteCampoTexto() {
		return this.idComponenteCampoTexto;
	}

	public void setIdComponenteCampoTexto(long idComponenteCampoTexto) {
		this.idComponenteCampoTexto = idComponenteCampoTexto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_origen_llenado")
	public CatOrigenLlenado getCatOrigenLlenado() {
		return this.catOrigenLlenado;
	}

	public void setCatOrigenLlenado(CatOrigenLlenado catOrigenLlenado) {
		this.catOrigenLlenado = catOrigenLlenado;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_validador")
	public CatValidadores getCatValidadores() {
		return this.catValidadores;
	}

	public void setCatValidadores(CatValidadores catValidadores) {
		this.catValidadores = catValidadores;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@Column(name = "alfanumerico", nullable = false)
	public boolean isAlfanumerico() {
		return this.alfanumerico;
	}

	public void setAlfanumerico(boolean alfanumerico) {
		this.alfanumerico = alfanumerico;
	}

	@Column(name = "numerico", nullable = false)
	public boolean isNumerico() {
		return this.numerico;
	}

	public void setNumerico(boolean numerico) {
		this.numerico = numerico;
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

	@Column(name = "validadores", nullable = false)
	public boolean isValidadores() {
		return this.validadores;
	}

	public void setValidadores(boolean validadores) {
		this.validadores = validadores;
	}


	@Column(name = "valor_minimo", precision = 131089, scale = 0)
	public Long getValorMinimo() {
		return this.valorMinimo;
	}

	public void setValorMinimo(Long valorMinimo) {
		this.valorMinimo = valorMinimo;

	}

	@Column(name = "valor_maximo", precision = 131089, scale = 0)
	public Long getValorMaximo() {
		return this.valorMaximo;
	}

	public void setValorMaximo(Long valorMaximo) {
		this.valorMaximo = valorMaximo;		
	}
	
	@Column(name = "permite_decimales", nullable = false)
	public boolean isPermiteDecimales() {
		return permiteDecimales;
	}

	public void setPermiteDecimales(boolean permiteDecimales) {
		this.permiteDecimales = permiteDecimales;
	}
	
}

package mx.gob.atdt.interprete.model;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "componente", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "Componente.findComponentesActivosByIdSubseccion", query = " SELECT new mx.gob.atdt.interprete.dto.ComponenteDTO("
				+ " c.idComponente, c.catTipoComponente.idTipoComponente, c.orden, c.tituloCampo, c.seccionSincronizada) "
				+ " FROM Componente c " + " JOIN c.subseccionesFormulario s "
				+ " WHERE s.idSubseccionFormulario =:idSubseccion " + " AND c.activo =:activo " + " ORDER BY c.orden "),
		@NamedQuery(name = "Componente.findComponentesActivosByTipo", query = " SELECT new mx.gob.atdt.interprete.dto.ComponenteDTO("
				+ " c.idComponente, c.catTipoComponente.idTipoComponente, c.orden, c.tituloCampo, c.seccionSincronizada) "
				+ " FROM Componente c " 
				+ " WHERE c.catTipoComponente.idTipoComponente =:idTipoComponente " + " AND c.activo = true " + " ORDER BY c.orden "),		
		@NamedQuery(name = "Componente.findByIdComponente", query = " SELECT new mx.gob.atdt.interprete.dto.ComponenteDTO("
				+ " c.idComponente, c.catTipoComponente.idTipoComponente, "
				+ " c.subseccionesFormulario.idSubseccionFormulario, c.orden, "
				+ " c.requerido, c.tooltip, c.descripcionTooltip, c.tituloCampo, c.activo, "
				+ " c.fechaCreacion, c.fechaUltimaActualizacion, c.seccionSincronizada) " + " FROM Componente c "
				+ " JOIN c.subseccionesFormulario s " + " WHERE c.idComponente =:idComponente") })
				//+ " AND c.activo = true "
public class Componente implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3867480395560928526L;

	private Long idComponente;
	private CatTipoComponente catTipoComponente;
	private SubseccionesFormulario subseccionesFormulario;
	private int orden;
	private boolean requerido;
	private boolean tooltip;
	private String descripcionTooltip;
	private String tituloCampo;
	private boolean activo;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean seccionSincronizada;
	private Set<ComponenteCargaDocumentos> componenteCargaDocumentoses = new HashSet<ComponenteCargaDocumentos>(0);
	private Set<ComponenteCampoTexto> componenteCampoTextos = new HashSet<ComponenteCampoTexto>(0);
	private Set<DetElementosToken> detElementosTokens = new HashSet<DetElementosToken>(0);
	private Set<ComponenteDatosPersonales> componenteDatosPersonaleses = new HashSet<ComponenteDatosPersonales>(0);
	private Set<ComponenteDatosDomicilio> componenteDatosDomicilios = new HashSet<ComponenteDatosDomicilio>(0);
	private Set<ComponenteInformativo> componenteInformativos = new HashSet<ComponenteInformativo>(0);
	private Set<ComponenteMenuDesplegable> componenteMenuDesplegables = new HashSet<ComponenteMenuDesplegable>(0);
	private Set<ComponenteDatosPersonalesLlave> componenteDatosPersonalesLlaves = new HashSet<ComponenteDatosPersonalesLlave>(
			0);
	private Set<ComponenteRadioboton> componenteRadiobotons = new HashSet<ComponenteRadioboton>(0);
	private Set<ComponenteFecha> componenteFechas = new HashSet<ComponenteFecha>(0);
	private Set<ComponenteCheckbox> componenteCheckboxes = new HashSet<ComponenteCheckbox>(0);
	private Set<ComponenteCheckboxUnico> componenteCheckboxUnicos = new HashSet<ComponenteCheckboxUnico>(0);
	private Set<DetDistribucion> detDistribucions = new HashSet<DetDistribucion>(0);
	
	public Componente() {
	}

	public Componente(Long idComponente) {
	}

	public Componente(Long idComponente, CatTipoComponente catTipoComponente,
			SubseccionesFormulario subseccionesFormulario, int orden, boolean requerido, boolean tooltip,
			String tituloCampo, boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion,
			boolean seccionSincronizada) {
		this.idComponente = idComponente;
		this.catTipoComponente = catTipoComponente;
		this.subseccionesFormulario = subseccionesFormulario;
		this.orden = orden;
		this.requerido = requerido;
		this.tooltip = tooltip;
		this.tituloCampo = tituloCampo;
		this.activo = activo;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.seccionSincronizada = seccionSincronizada;
	}

	public Componente(Long idComponente, CatTipoComponente catTipoComponente,
			SubseccionesFormulario subseccionesFormulario, int orden, boolean requerido, boolean tooltip,
			String descripcionTooltip, String tituloCampo, boolean activo, Date fechaCreacion,
			Date fechaUltimaActualizacion, boolean seccionSincronizada,
			Set<ComponenteCargaDocumentos> componenteCargaDocumentoses, Set<ComponenteCampoTexto> componenteCampoTextos,
			Set<DetElementosToken> detElementosTokens, Set<ComponenteDatosPersonales> componenteDatosPersonaleses,
			Set<ComponenteDatosDomicilio> componenteDatosDomicilios, Set<ComponenteInformativo> componenteInformativos,
			Set<ComponenteMenuDesplegable> componenteMenuDesplegables,
			Set<ComponenteDatosPersonalesLlave> componenteDatosPersonalesLlaves,
			Set<ComponenteRadioboton> componenteRadiobotons, Set<ComponenteFecha> componenteFechas,
			Set<ComponenteCheckbox> componenteCheckboxes, Set<ComponenteCheckboxUnico> componenteCheckboxUnicos) {
		this.idComponente = idComponente;
		this.catTipoComponente = catTipoComponente;
		this.subseccionesFormulario = subseccionesFormulario;
		this.orden = orden;
		this.requerido = requerido;
		this.tooltip = tooltip;
		this.descripcionTooltip = descripcionTooltip;
		this.tituloCampo = tituloCampo;
		this.activo = activo;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.seccionSincronizada = seccionSincronizada;
		this.componenteCargaDocumentoses = componenteCargaDocumentoses;
		this.componenteCampoTextos = componenteCampoTextos;
		this.detElementosTokens = detElementosTokens;
		this.componenteDatosPersonaleses = componenteDatosPersonaleses;
		this.componenteDatosDomicilios = componenteDatosDomicilios;
		this.componenteInformativos = componenteInformativos;
		this.componenteMenuDesplegables = componenteMenuDesplegables;
		this.componenteDatosPersonalesLlaves = componenteDatosPersonalesLlaves;
		this.componenteRadiobotons = componenteRadiobotons;
		this.componenteFechas = componenteFechas;
		this.componenteCheckboxes = componenteCheckboxes;
		this.componenteCheckboxUnicos = componenteCheckboxUnicos;
	}

	@Id
	@Column(name = "id_componente", unique = true, nullable = false)
	public Long getIdComponente() {
		return this.idComponente;
	}

	public void setIdComponente(Long idComponente) {
		this.idComponente = idComponente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_componente", nullable = false)
	public CatTipoComponente getCatTipoComponente() {
		return this.catTipoComponente;
	}

	public void setCatTipoComponente(CatTipoComponente catTipoComponente) {
		this.catTipoComponente = catTipoComponente;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_subseccion_formulario", nullable = false)
	public SubseccionesFormulario getSubseccionesFormulario() {
		return this.subseccionesFormulario;
	}

	public void setSubseccionesFormulario(SubseccionesFormulario subseccionesFormulario) {
		this.subseccionesFormulario = subseccionesFormulario;
	}

	@Column(name = "orden", nullable = false)
	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	@Column(name = "requerido", nullable = false)
	public boolean isRequerido() {
		return this.requerido;
	}

	public void setRequerido(boolean requerido) {
		this.requerido = requerido;
	}

	@Column(name = "tooltip", nullable = false)
	public boolean isTooltip() {
		return this.tooltip;
	}

	public void setTooltip(boolean tooltip) {
		this.tooltip = tooltip;
	}

	@Column(name = "descripcion_tooltip", length = 200)
	public String getDescripcionTooltip() {
		return this.descripcionTooltip;
	}

	public void setDescripcionTooltip(String descripcionTooltip) {
		this.descripcionTooltip = descripcionTooltip;
	}

	@Column(name = "titulo_campo", nullable = false, length = 100)
	public String getTituloCampo() {
		return this.tituloCampo;
	}

	public void setTituloCampo(String tituloCampo) {
		this.tituloCampo = tituloCampo;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false, length = 29)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_ultima_actualizacion", nullable = false, length = 29)
	public Date getFechaUltimaActualizacion() {
		return this.fechaUltimaActualizacion;
	}

	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	@Column(name = "seccion_sincronizada", nullable = false)
	public boolean isSeccionSincronizada() {
		return this.seccionSincronizada;
	}

	public void setSeccionSincronizada(boolean seccionSincronizada) {
		this.seccionSincronizada = seccionSincronizada;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<ComponenteCargaDocumentos> getComponenteCargaDocumentoses() {
		return this.componenteCargaDocumentoses;
	}

	public void setComponenteCargaDocumentoses(Set<ComponenteCargaDocumentos> componenteCargaDocumentoses) {
		this.componenteCargaDocumentoses = componenteCargaDocumentoses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<ComponenteCampoTexto> getComponenteCampoTextos() {
		return this.componenteCampoTextos;
	}

	public void setComponenteCampoTextos(Set<ComponenteCampoTexto> componenteCampoTextos) {
		this.componenteCampoTextos = componenteCampoTextos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<DetElementosToken> getDetElementosTokens() {
		return this.detElementosTokens;
	}

	public void setDetElementosTokens(Set<DetElementosToken> detElementosTokens) {
		this.detElementosTokens = detElementosTokens;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<ComponenteDatosPersonales> getComponenteDatosPersonaleses() {
		return this.componenteDatosPersonaleses;
	}

	public void setComponenteDatosPersonaleses(Set<ComponenteDatosPersonales> componenteDatosPersonaleses) {
		this.componenteDatosPersonaleses = componenteDatosPersonaleses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<ComponenteDatosDomicilio> getComponenteDatosDomicilios() {
		return this.componenteDatosDomicilios;
	}

	public void setComponenteDatosDomicilios(Set<ComponenteDatosDomicilio> componenteDatosDomicilios) {
		this.componenteDatosDomicilios = componenteDatosDomicilios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<ComponenteInformativo> getComponenteInformativos() {
		return this.componenteInformativos;
	}

	public void setComponenteInformativos(Set<ComponenteInformativo> componenteInformativos) {
		this.componenteInformativos = componenteInformativos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<ComponenteMenuDesplegable> getComponenteMenuDesplegables() {
		return this.componenteMenuDesplegables;
	}

	public void setComponenteMenuDesplegables(Set<ComponenteMenuDesplegable> componenteMenuDesplegables) {
		this.componenteMenuDesplegables = componenteMenuDesplegables;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<ComponenteDatosPersonalesLlave> getComponenteDatosPersonalesLlaves() {
		return this.componenteDatosPersonalesLlaves;
	}

	public void setComponenteDatosPersonalesLlaves(
			Set<ComponenteDatosPersonalesLlave> componenteDatosPersonalesLlaves) {
		this.componenteDatosPersonalesLlaves = componenteDatosPersonalesLlaves;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<ComponenteRadioboton> getComponenteRadiobotons() {
		return this.componenteRadiobotons;
	}

	public void setComponenteRadiobotons(Set<ComponenteRadioboton> componenteRadiobotons) {
		this.componenteRadiobotons = componenteRadiobotons;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<ComponenteFecha> getComponenteFechas() {
		return this.componenteFechas;
	}

	public void setComponenteFechas(Set<ComponenteFecha> componenteFechas) {
		this.componenteFechas = componenteFechas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<ComponenteCheckbox> getComponenteCheckboxes() {
		return this.componenteCheckboxes;
	}

	public void setComponenteCheckboxes(Set<ComponenteCheckbox> componenteCheckboxes) {
		this.componenteCheckboxes = componenteCheckboxes;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<ComponenteCheckboxUnico> getComponenteCheckboxUnicos() {
		return this.componenteCheckboxUnicos;
	}

	public void setComponenteCheckboxUnicos(Set<ComponenteCheckboxUnico> componenteCheckboxUnicos) {
		this.componenteCheckboxUnicos = componenteCheckboxUnicos;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componente")
	public Set<DetDistribucion> getDetDistribucions() {
		return this.detDistribucions;
	}

	public void setDetDistribucions(Set<DetDistribucion> detDistribucions) {
		this.detDistribucions = detDistribucions;
	}
}

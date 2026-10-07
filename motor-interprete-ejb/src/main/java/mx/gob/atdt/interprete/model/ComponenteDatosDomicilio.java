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
@Table(name = "componente_datos_domicilio", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "ComponenteDatosDomicilio.findByIdComponente", 
				query = " SELECT new mx.gob.atdt.interprete.dto.ComponenteDatosDomicilioDTO(" +
						" cdd.idComponenteDatosDomicilio, c.idComponente, c.catTipoComponente.idTipoComponente, " +
						" c.subseccionesFormulario.idSubseccionFormulario, c.orden, c.requerido, c.tooltip, " +
						" c.descripcionTooltip, c.tituloCampo, c.activo, c.fechaCreacion, c.fechaUltimaActualizacion, c.seccionSincronizada," +
						" cdd.habilitaCalle, cdd.calleObligatorio, cdd.textoInteriorCalle, cdd.habilitaNumeroExterior, cdd.numeroExteriorObligatorio, " +
						" cdd.textoInteriorNumeroExterior, cdd.habilitaNumeroInterior, cdd.numeroInteriorObligatorio, cdd.textoInteriorNumeroInterior, " +
						" cdd.habilitaCodigoPostal, cdd.codigoPostalObligatorio, cdd.textoInteriorCodigoPostal, cdd.habilitaColonia, cdd.coloniaObligatorio, " +
						" cdd.textoInteriorColonia, cdd.habilitaAlcaldia, cdd.alcaldiaObligatorio, cdd.textoInteriorAlcaldia, " +
						" cdd.habilitaEstado, cdd.estadoObligatorio, cdd.textoInteriorEstado ) " +
						" FROM ComponenteDatosDomicilio cdd " +
						" JOIN cdd.componente c " +
						" WHERE c.idComponente =:idComponente") 
})
public class ComponenteDatosDomicilio implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 177363531139686362L;

	private Long idComponenteDatosDomicilio;
	private Componente componente;
	private boolean habilitaCalle;
	private boolean calleObligatorio;
	private String textoInteriorCalle;
	private boolean habilitaNumeroExterior;
	private boolean numeroExteriorObligatorio;
	private String textoInteriorNumeroExterior;
	private boolean habilitaNumeroInterior;
	private boolean numeroInteriorObligatorio;
	private String textoInteriorNumeroInterior;
	private boolean habilitaCodigoPostal;
	private boolean codigoPostalObligatorio;
	private String textoInteriorCodigoPostal;
	private boolean habilitaColonia;
	private boolean coloniaObligatorio;
	private String textoInteriorColonia;
	private boolean habilitaAlcaldia;
	private boolean alcaldiaObligatorio;
	private String textoInteriorAlcaldia;
	private boolean habilitaEstado;
	private boolean estadoObligatorio;
	private String textoInteriorEstado;

	public ComponenteDatosDomicilio() {
	}

	public ComponenteDatosDomicilio(Long idComponenteDatosDomicilio, Componente componente, boolean habilitaCalle,
			boolean calleObligatorio, boolean habilitaNumeroExterior, boolean numeroExteriorObligatorio,
			boolean habilitaNumeroInterior, boolean numeroInteriorObligatorio, boolean habilitaCodigoPostal,
			boolean codigoPostalObligatorio, boolean habilitaColonia, boolean coloniaObligatorio,
			boolean habilitaAlcaldia, boolean alcaldiaObligatorio) {
		this.idComponenteDatosDomicilio = idComponenteDatosDomicilio;
		this.componente = componente;
		this.habilitaCalle = habilitaCalle;
		this.calleObligatorio = calleObligatorio;
		this.habilitaNumeroExterior = habilitaNumeroExterior;
		this.numeroExteriorObligatorio = numeroExteriorObligatorio;
		this.habilitaNumeroInterior = habilitaNumeroInterior;
		this.numeroInteriorObligatorio = numeroInteriorObligatorio;
		this.habilitaCodigoPostal = habilitaCodigoPostal;
		this.codigoPostalObligatorio = codigoPostalObligatorio;
		this.habilitaColonia = habilitaColonia;
		this.coloniaObligatorio = coloniaObligatorio;
		this.habilitaAlcaldia = habilitaAlcaldia;
		this.alcaldiaObligatorio = alcaldiaObligatorio;
	}

	public ComponenteDatosDomicilio(Long idComponenteDatosDomicilio, Componente componente, boolean habilitaCalle,
			boolean calleObligatorio, String textoInteriorCalle, boolean habilitaNumeroExterior,
			boolean numeroExteriorObligatorio, String textoInteriorNumeroExterior, boolean habilitaNumeroInterior,
			boolean numeroInteriorObligatorio, String textoInteriorNumeroInterior, boolean habilitaCodigoPostal,
			boolean codigoPostalObligatorio, String textoInteriorCodigoPostal, boolean habilitaColonia,
			boolean coloniaObligatorio, String textoInteriorColonia, boolean habilitaAlcaldia,
			boolean alcaldiaObligatorio, String textoInteriorAlcaldia) {
		this.idComponenteDatosDomicilio = idComponenteDatosDomicilio;
		this.componente = componente;
		this.habilitaCalle = habilitaCalle;
		this.calleObligatorio = calleObligatorio;
		this.textoInteriorCalle = textoInteriorCalle;
		this.habilitaNumeroExterior = habilitaNumeroExterior;
		this.numeroExteriorObligatorio = numeroExteriorObligatorio;
		this.textoInteriorNumeroExterior = textoInteriorNumeroExterior;
		this.habilitaNumeroInterior = habilitaNumeroInterior;
		this.numeroInteriorObligatorio = numeroInteriorObligatorio;
		this.textoInteriorNumeroInterior = textoInteriorNumeroInterior;
		this.habilitaCodigoPostal = habilitaCodigoPostal;
		this.codigoPostalObligatorio = codigoPostalObligatorio;
		this.textoInteriorCodigoPostal = textoInteriorCodigoPostal;
		this.habilitaColonia = habilitaColonia;
		this.coloniaObligatorio = coloniaObligatorio;
		this.textoInteriorColonia = textoInteriorColonia;
		this.habilitaAlcaldia = habilitaAlcaldia;
		this.alcaldiaObligatorio = alcaldiaObligatorio;
		this.textoInteriorAlcaldia = textoInteriorAlcaldia;
	}

	@Id
	@Column(name = "id_componente_datos_domicilio", unique = true, nullable = false)
	public Long getIdComponenteDatosDomicilio() {
		return this.idComponenteDatosDomicilio;
	}

	public void setIdComponenteDatosDomicilio(Long idComponenteDatosDomicilio) {
		this.idComponenteDatosDomicilio = idComponenteDatosDomicilio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@Column(name = "habilita_calle", nullable = false)
	public boolean isHabilitaCalle() {
		return this.habilitaCalle;
	}

	public void setHabilitaCalle(boolean habilitaCalle) {
		this.habilitaCalle = habilitaCalle;
	}

	@Column(name = "calle_obligatorio", nullable = false)
	public boolean isCalleObligatorio() {
		return this.calleObligatorio;
	}

	public void setCalleObligatorio(boolean calleObligatorio) {
		this.calleObligatorio = calleObligatorio;
	}

	@Column(name = "texto_interior_calle", length = 60)
	public String getTextoInteriorCalle() {
		return this.textoInteriorCalle;
	}

	public void setTextoInteriorCalle(String textoInteriorCalle) {
		this.textoInteriorCalle = textoInteriorCalle;
	}

	@Column(name = "habilita_numero_exterior", nullable = false)
	public boolean isHabilitaNumeroExterior() {
		return this.habilitaNumeroExterior;
	}

	public void setHabilitaNumeroExterior(boolean habilitaNumeroExterior) {
		this.habilitaNumeroExterior = habilitaNumeroExterior;
	}

	@Column(name = "numero_exterior_obligatorio", nullable = false)
	public boolean isNumeroExteriorObligatorio() {
		return this.numeroExteriorObligatorio;
	}

	public void setNumeroExteriorObligatorio(boolean numeroExteriorObligatorio) {
		this.numeroExteriorObligatorio = numeroExteriorObligatorio;
	}

	@Column(name = "texto_interior_numero_exterior", length = 60)
	public String getTextoInteriorNumeroExterior() {
		return this.textoInteriorNumeroExterior;
	}

	public void setTextoInteriorNumeroExterior(String textoInteriorNumeroExterior) {
		this.textoInteriorNumeroExterior = textoInteriorNumeroExterior;
	}

	@Column(name = "habilita_numero_interior", nullable = false)
	public boolean isHabilitaNumeroInterior() {
		return this.habilitaNumeroInterior;
	}

	public void setHabilitaNumeroInterior(boolean habilitaNumeroInterior) {
		this.habilitaNumeroInterior = habilitaNumeroInterior;
	}

	@Column(name = "numero_interior_obligatorio", nullable = false)
	public boolean isNumeroInteriorObligatorio() {
		return this.numeroInteriorObligatorio;
	}

	public void setNumeroInteriorObligatorio(boolean numeroInteriorObligatorio) {
		this.numeroInteriorObligatorio = numeroInteriorObligatorio;
	}

	@Column(name = "texto_interior_numero_interior", length = 60)
	public String getTextoInteriorNumeroInterior() {
		return this.textoInteriorNumeroInterior;
	}

	public void setTextoInteriorNumeroInterior(String textoInteriorNumeroInterior) {
		this.textoInteriorNumeroInterior = textoInteriorNumeroInterior;
	}

	@Column(name = "habilita_codigo_postal", nullable = false)
	public boolean isHabilitaCodigoPostal() {
		return this.habilitaCodigoPostal;
	}

	public void setHabilitaCodigoPostal(boolean habilitaCodigoPostal) {
		this.habilitaCodigoPostal = habilitaCodigoPostal;
	}

	@Column(name = "codigo_postal_obligatorio", nullable = false)
	public boolean isCodigoPostalObligatorio() {
		return this.codigoPostalObligatorio;
	}

	public void setCodigoPostalObligatorio(boolean codigoPostalObligatorio) {
		this.codigoPostalObligatorio = codigoPostalObligatorio;
	}

	@Column(name = "texto_interior_codigo_postal", length = 20)
	public String getTextoInteriorCodigoPostal() {
		return this.textoInteriorCodigoPostal;
	}

	public void setTextoInteriorCodigoPostal(String textoInteriorCodigoPostal) {
		this.textoInteriorCodigoPostal = textoInteriorCodigoPostal;
	}

	@Column(name = "habilita_colonia", nullable = false)
	public boolean isHabilitaColonia() {
		return this.habilitaColonia;
	}

	public void setHabilitaColonia(boolean habilitaColonia) {
		this.habilitaColonia = habilitaColonia;
	}

	@Column(name = "colonia_obligatorio", nullable = false)
	public boolean isColoniaObligatorio() {
		return this.coloniaObligatorio;
	}

	public void setColoniaObligatorio(boolean coloniaObligatorio) {
		this.coloniaObligatorio = coloniaObligatorio;
	}

	@Column(name = "texto_interior_colonia", length = 60)
	public String getTextoInteriorColonia() {
		return this.textoInteriorColonia;
	}

	public void setTextoInteriorColonia(String textoInteriorColonia) {
		this.textoInteriorColonia = textoInteriorColonia;
	}

	@Column(name = "habilita_alcaldia", nullable = false)
	public boolean isHabilitaAlcaldia() {
		return this.habilitaAlcaldia;
	}

	public void setHabilitaAlcaldia(boolean habilitaAlcaldia) {
		this.habilitaAlcaldia = habilitaAlcaldia;
	}

	@Column(name = "alcaldia_obligatorio", nullable = false)
	public boolean isAlcaldiaObligatorio() {
		return this.alcaldiaObligatorio;
	}

	public void setAlcaldiaObligatorio(boolean alcaldiaObligatorio) {
		this.alcaldiaObligatorio = alcaldiaObligatorio;
	}

	@Column(name = "texto_interior_alcaldia", length = 60)
	public String getTextoInteriorAlcaldia() {
		return this.textoInteriorAlcaldia;
	}

	public void setTextoInteriorAlcaldia(String textoInteriorAlcaldia) {
		this.textoInteriorAlcaldia = textoInteriorAlcaldia;
	}
	
	@Column(name = "habilita_estado", nullable = false)
	public boolean isHabilitaEstado() {
		return habilitaEstado;
	}
	
	public void setHabilitaEstado(boolean habilitaEstado) {
		this.habilitaEstado = habilitaEstado;
	}

	@Column(name = "estado_obligatorio", nullable = false)
	public boolean isEstadoObligatorio() {
		return estadoObligatorio;
	}

	public void setEstadoObligatorio(boolean estadoObligatorio) {
		this.estadoObligatorio = estadoObligatorio;
	}

	@Column(name = "texto_interior_estado", length = 60)
	public String getTextoInteriorEstado() {
		return textoInteriorEstado;
	}

	public void setTextoInteriorEstado(String textoInteriorEstado) {
		this.textoInteriorEstado = textoInteriorEstado;
	}

}

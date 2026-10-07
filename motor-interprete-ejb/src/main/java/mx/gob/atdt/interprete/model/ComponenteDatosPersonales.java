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
@Table(name = "componente_datos_personales", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "ComponenteDatosPersonales.findByIdComponente", 
			query = "SELECT new mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesDTO ( "
					+ "	cdp.idComponenteDatosPersonales, c.idComponente, c.catTipoComponente.idTipoComponente, c.subseccionesFormulario.idSubseccionFormulario,  "
					+ " c.orden, c.requerido, c.tooltip, c.descripcionTooltip, c.tituloCampo, c.activo, c.fechaCreacion, c.fechaUltimaActualizacion, c.seccionSincronizada, "
					+ " cdp.habilitaCurp, cdp.curpObligatorio, cdp.textoInteriorCurp, cdp.habilitaNombre, cdp.nombreObligatorio, cdp.textoInteriorNombre, cdp.habilitaPrimerApellido, cdp.primerApellidoObligatorio, cdp.textoInteriorPrimerApellido, cdp.habilitaSegundoApellido, cdp.segundoApellidoObligatorio, cdp.textoInteriorSegundoApellido, "
					+ " cdp.habilitaTelefono, cdp.telefonoObligatorio, cdp.textoInteriorTelefono, cdp.habilitaCorreoElectronico, cdp.correoElectronicoObligatorio, cdp.textoInteriorCorreoElectronico, cdp.habilitaRenapo) "
					+ " FROM ComponenteDatosPersonales cdp "
					+ " JOIN cdp.componente c "
					+ " WHERE c.idComponente = :idComponente")
})
public class ComponenteDatosPersonales implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7567716918075263948L;
	
	private Long idComponenteDatosPersonales;
	private Componente componente;
	private boolean habilitaCurp;
	private boolean curpObligatorio;
	private String textoInteriorCurp;
	private boolean habilitaNombre;
	private boolean nombreObligatorio;
	private String textoInteriorNombre;
	private boolean habilitaPrimerApellido;
	private boolean primerApellidoObligatorio;
	private String textoInteriorPrimerApellido;
	private boolean habilitaSegundoApellido;
	private boolean segundoApellidoObligatorio;
	private String textoInteriorSegundoApellido;
	private boolean habilitaTelefono;
	private boolean telefonoObligatorio;
	private String textoInteriorTelefono;
	private boolean habilitaCorreoElectronico;
	private boolean correoElectronicoObligatorio;
	private String textoInteriorCorreoElectronico;
	private boolean habilitaRenapo;

	public ComponenteDatosPersonales() {
	}

	public ComponenteDatosPersonales(Long idComponenteDatosPersonales, Componente componente, boolean habilitaCurp,
			boolean curpObligatorio, boolean habilitaNombre, boolean nombreObligatorio, boolean habilitaPrimerApellido,
			boolean primerApellidoObligatorio, boolean habilitaSegundoApellido, boolean segundoApellidoObligatorio,
			boolean habilitaTelefono, boolean telefonoObligatorio, boolean habilitaCorreoElectronico,
			boolean correoElectronicoObligatorio, boolean habilitaRenapo) {
		this.idComponenteDatosPersonales = idComponenteDatosPersonales;
		this.componente = componente;
		this.habilitaCurp = habilitaCurp;
		this.curpObligatorio = curpObligatorio;
		this.habilitaNombre = habilitaNombre;
		this.nombreObligatorio = nombreObligatorio;
		this.habilitaPrimerApellido = habilitaPrimerApellido;
		this.primerApellidoObligatorio = primerApellidoObligatorio;
		this.habilitaSegundoApellido = habilitaSegundoApellido;
		this.segundoApellidoObligatorio = segundoApellidoObligatorio;
		this.habilitaTelefono = habilitaTelefono;
		this.telefonoObligatorio = telefonoObligatorio;
		this.habilitaCorreoElectronico = habilitaCorreoElectronico;
		this.correoElectronicoObligatorio = correoElectronicoObligatorio;
		this.habilitaRenapo = habilitaRenapo;
	}

	public ComponenteDatosPersonales(Long idComponenteDatosPersonales, Componente componente, boolean habilitaCurp,
			boolean curpObligatorio, String textoInteriorCurp, boolean habilitaNombre, boolean nombreObligatorio,
			String textoInteriorNombre, boolean habilitaPrimerApellido, boolean primerApellidoObligatorio,
			String textoInteriorPrimerApellido, boolean habilitaSegundoApellido, boolean segundoApellidoObligatorio,
			String textoInteriorSegundoApellido, boolean habilitaTelefono, boolean telefonoObligatorio,
			String textoInteriorTelefono, boolean habilitaCorreoElectronico, boolean correoElectronicoObligatorio,
			String textoInteriorCorreoElectronico, boolean habilitaRenapo) {
		this.idComponenteDatosPersonales = idComponenteDatosPersonales;
		this.componente = componente;
		this.habilitaCurp = habilitaCurp;
		this.curpObligatorio = curpObligatorio;
		this.textoInteriorCurp = textoInteriorCurp;
		this.habilitaNombre = habilitaNombre;
		this.nombreObligatorio = nombreObligatorio;
		this.textoInteriorNombre = textoInteriorNombre;
		this.habilitaPrimerApellido = habilitaPrimerApellido;
		this.primerApellidoObligatorio = primerApellidoObligatorio;
		this.textoInteriorPrimerApellido = textoInteriorPrimerApellido;
		this.habilitaSegundoApellido = habilitaSegundoApellido;
		this.segundoApellidoObligatorio = segundoApellidoObligatorio;
		this.textoInteriorSegundoApellido = textoInteriorSegundoApellido;
		this.habilitaTelefono = habilitaTelefono;
		this.telefonoObligatorio = telefonoObligatorio;
		this.textoInteriorTelefono = textoInteriorTelefono;
		this.habilitaCorreoElectronico = habilitaCorreoElectronico;
		this.correoElectronicoObligatorio = correoElectronicoObligatorio;
		this.textoInteriorCorreoElectronico = textoInteriorCorreoElectronico;
		this.habilitaRenapo = habilitaRenapo;
	}

	@Id
	@Column(name = "id_componente_datos_personales", unique = true, nullable = false)
	public Long getIdComponenteDatosPersonales() {
		return this.idComponenteDatosPersonales;
	}

	public void setIdComponenteDatosPersonales(Long idComponenteDatosPersonales) {
		this.idComponenteDatosPersonales = idComponenteDatosPersonales;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@Column(name = "habilita_curp", nullable = false)
	public boolean isHabilitaCurp() {
		return this.habilitaCurp;
	}

	public void setHabilitaCurp(boolean habilitaCurp) {
		this.habilitaCurp = habilitaCurp;
	}

	@Column(name = "curp_obligatorio", nullable = false)
	public boolean isCurpObligatorio() {
		return this.curpObligatorio;
	}

	public void setCurpObligatorio(boolean curpObligatorio) {
		this.curpObligatorio = curpObligatorio;
	}

	@Column(name = "texto_interior_curp", length = 60)
	public String getTextoInteriorCurp() {
		return this.textoInteriorCurp;
	}

	public void setTextoInteriorCurp(String textoInteriorCurp) {
		this.textoInteriorCurp = textoInteriorCurp;
	}

	@Column(name = "habilita_nombre", nullable = false)
	public boolean isHabilitaNombre() {
		return this.habilitaNombre;
	}

	public void setHabilitaNombre(boolean habilitaNombre) {
		this.habilitaNombre = habilitaNombre;
	}

	@Column(name = "nombre_obligatorio", nullable = false)
	public boolean isNombreObligatorio() {
		return this.nombreObligatorio;
	}

	public void setNombreObligatorio(boolean nombreObligatorio) {
		this.nombreObligatorio = nombreObligatorio;
	}

	@Column(name = "texto_interior_nombre", length = 60)
	public String getTextoInteriorNombre() {
		return this.textoInteriorNombre;
	}

	public void setTextoInteriorNombre(String textoInteriorNombre) {
		this.textoInteriorNombre = textoInteriorNombre;
	}

	@Column(name = "habilita_primer_apellido", nullable = false)
	public boolean isHabilitaPrimerApellido() {
		return this.habilitaPrimerApellido;
	}

	public void setHabilitaPrimerApellido(boolean habilitaPrimerApellido) {
		this.habilitaPrimerApellido = habilitaPrimerApellido;
	}

	@Column(name = "primer_apellido_obligatorio", nullable = false)
	public boolean isPrimerApellidoObligatorio() {
		return this.primerApellidoObligatorio;
	}

	public void setPrimerApellidoObligatorio(boolean primerApellidoObligatorio) {
		this.primerApellidoObligatorio = primerApellidoObligatorio;
	}

	@Column(name = "texto_interior_primer_apellido", length = 60)
	public String getTextoInteriorPrimerApellido() {
		return this.textoInteriorPrimerApellido;
	}

	public void setTextoInteriorPrimerApellido(String textoInteriorPrimerApellido) {
		this.textoInteriorPrimerApellido = textoInteriorPrimerApellido;
	}

	@Column(name = "habilita_segundo_apellido", nullable = false)
	public boolean isHabilitaSegundoApellido() {
		return this.habilitaSegundoApellido;
	}

	public void setHabilitaSegundoApellido(boolean habilitaSegundoApellido) {
		this.habilitaSegundoApellido = habilitaSegundoApellido;
	}

	@Column(name = "segundo_apellido_obligatorio", nullable = false)
	public boolean isSegundoApellidoObligatorio() {
		return this.segundoApellidoObligatorio;
	}

	public void setSegundoApellidoObligatorio(boolean segundoApellidoObligatorio) {
		this.segundoApellidoObligatorio = segundoApellidoObligatorio;
	}

	@Column(name = "texto_interior_segundo_apellido", length = 60)
	public String getTextoInteriorSegundoApellido() {
		return this.textoInteriorSegundoApellido;
	}

	public void setTextoInteriorSegundoApellido(String textoInteriorSegundoApellido) {
		this.textoInteriorSegundoApellido = textoInteriorSegundoApellido;
	}

	@Column(name = "habilita_telefono", nullable = false)
	public boolean isHabilitaTelefono() {
		return this.habilitaTelefono;
	}

	public void setHabilitaTelefono(boolean habilitaTelefono) {
		this.habilitaTelefono = habilitaTelefono;
	}

	@Column(name = "telefono_obligatorio", nullable = false)
	public boolean isTelefonoObligatorio() {
		return this.telefonoObligatorio;
	}

	public void setTelefonoObligatorio(boolean telefonoObligatorio) {
		this.telefonoObligatorio = telefonoObligatorio;
	}

	@Column(name = "texto_interior_telefono", length = 30)
	public String getTextoInteriorTelefono() {
		return this.textoInteriorTelefono;
	}

	public void setTextoInteriorTelefono(String textoInteriorTelefono) {
		this.textoInteriorTelefono = textoInteriorTelefono;
	}

	@Column(name = "habilita_correo_electronico", nullable = false)
	public boolean isHabilitaCorreoElectronico() {
		return this.habilitaCorreoElectronico;
	}

	public void setHabilitaCorreoElectronico(boolean habilitaCorreoElectronico) {
		this.habilitaCorreoElectronico = habilitaCorreoElectronico;
	}

	@Column(name = "correo_electronico_obligatorio", nullable = false)
	public boolean isCorreoElectronicoObligatorio() {
		return this.correoElectronicoObligatorio;
	}

	public void setCorreoElectronicoObligatorio(boolean correoElectronicoObligatorio) {
		this.correoElectronicoObligatorio = correoElectronicoObligatorio;
	}

	@Column(name = "texto_interior_correo_electronico", length = 30)
	public String getTextoInteriorCorreoElectronico() {
		return this.textoInteriorCorreoElectronico;
	}

	public void setTextoInteriorCorreoElectronico(String textoInteriorCorreoElectronico) {
		this.textoInteriorCorreoElectronico = textoInteriorCorreoElectronico;
	}

	@Column(name = "habilita_renapo", nullable = false)
	public boolean isHabilitaRenapo() {
		return habilitaRenapo;
	}

	public void setHabilitaRenapo(boolean habilitaRenapo) {
		this.habilitaRenapo = habilitaRenapo;
	}
	
	

}

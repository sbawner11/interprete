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
@Table(name = "componente_datos_personales_llave", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "ComponenteDatosPersonalesLlave.findByIdComponente", 
			query = "SELECT new mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesLlaveDTO ( "
					+ "	cdp.idComponenteDatosPersonales, c.idComponente, c.catTipoComponente.idTipoComponente, c.subseccionesFormulario.idSubseccionFormulario,  "
					+ " c.orden, c.requerido, c.tooltip, c.descripcionTooltip, c.tituloCampo, c.activo, c.fechaCreacion, c.fechaUltimaActualizacion, c.seccionSincronizada, "
					+ " cdp.habilitaCurp, cdp.habilitaNombre, cdp.habilitaPrimerApellido, cdp.habilitaSegundoApellido,"
					+ " cdp.habilitaTelefono, cdp.habilitaCorreoElectronico, cdp.habilitaFechaNacimiento, cdp.habilitaSexo) "
					+ " FROM ComponenteDatosPersonalesLlave cdp "
					+ " JOIN cdp.componente c "
					+ " WHERE c.idComponente = :idComponente")
})
public class ComponenteDatosPersonalesLlave implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8440773173159869056L;
	
	private Long idComponenteDatosPersonales;
	private Componente componente;
	private boolean habilitaCurp;
	private boolean habilitaNombre;
	private boolean habilitaPrimerApellido;
	private boolean habilitaSegundoApellido;
	private boolean habilitaTelefono;
	private boolean habilitaCorreoElectronico;
	private boolean habilitaFechaNacimiento;
	private boolean habilitaSexo;

	public ComponenteDatosPersonalesLlave() {
	}

	public ComponenteDatosPersonalesLlave(Long idComponenteDatosPersonales, Componente componente, boolean habilitaCurp,
			boolean habilitaNombre, boolean habilitaPrimerApellido, boolean habilitaSegundoApellido,
			boolean habilitaTelefono, boolean habilitaCorreoElectronico, boolean habilitaFechaNacimiento, boolean habilitaSexo) {
		this.idComponenteDatosPersonales = idComponenteDatosPersonales;
		this.componente = componente;
		this.habilitaCurp = habilitaCurp;
		this.habilitaNombre = habilitaNombre;
		this.habilitaPrimerApellido = habilitaPrimerApellido;
		this.habilitaSegundoApellido = habilitaSegundoApellido;
		this.habilitaTelefono = habilitaTelefono;
		this.habilitaCorreoElectronico = habilitaCorreoElectronico;
		this.habilitaFechaNacimiento = habilitaFechaNacimiento;
		this.habilitaSexo = habilitaSexo;
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

	@Column(name = "habilita_nombre", nullable = false)
	public boolean isHabilitaNombre() {
		return this.habilitaNombre;
	}

	public void setHabilitaNombre(boolean habilitaNombre) {
		this.habilitaNombre = habilitaNombre;
	}

	@Column(name = "habilita_primer_apellido", nullable = false)
	public boolean isHabilitaPrimerApellido() {
		return this.habilitaPrimerApellido;
	}

	public void setHabilitaPrimerApellido(boolean habilitaPrimerApellido) {
		this.habilitaPrimerApellido = habilitaPrimerApellido;
	}

	@Column(name = "habilita_segundo_apellido", nullable = false)
	public boolean isHabilitaSegundoApellido() {
		return this.habilitaSegundoApellido;
	}

	public void setHabilitaSegundoApellido(boolean habilitaSegundoApellido) {
		this.habilitaSegundoApellido = habilitaSegundoApellido;
	}

	@Column(name = "habilita_telefono", nullable = false)
	public boolean isHabilitaTelefono() {
		return this.habilitaTelefono;
	}

	public void setHabilitaTelefono(boolean habilitaTelefono) {
		this.habilitaTelefono = habilitaTelefono;
	}

	@Column(name = "habilita_correo_electronico", nullable = false)
	public boolean isHabilitaCorreoElectronico() {
		return this.habilitaCorreoElectronico;
	}

	public void setHabilitaCorreoElectronico(boolean habilitaCorreoElectronico) {
		this.habilitaCorreoElectronico = habilitaCorreoElectronico;
	}

	@Column(name = "habilita_fecha_nacimiento", nullable = false)
	public boolean isHabilitaFechaNacimiento() {
		return habilitaFechaNacimiento;
	}
	
	public void setHabilitaFechaNacimiento(boolean habilitaFechaNacimiento) {
		this.habilitaFechaNacimiento = habilitaFechaNacimiento;
	}

	@Column(name = "habilita_sexo", nullable = false)
	public boolean isHabilitaSexo() {
		return habilitaSexo;
	}

	public void setHabilitaSexo(boolean habilitaSexo) {
		this.habilitaSexo = habilitaSexo;
	}
		

}

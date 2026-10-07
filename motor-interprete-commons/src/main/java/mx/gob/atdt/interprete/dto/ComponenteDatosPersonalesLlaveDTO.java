package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;


public class ComponenteDatosPersonalesLlaveDTO extends ComponenteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -237508541235371763L;

	private Long idComponenteDatosPersonales;
	private boolean habilitaCurp;
	private boolean habilitaNombre;
	private boolean habilitaPrimerApellido;
	private boolean habilitaSegundoApellido;
	private boolean habilitaTelefono;
	private boolean habilitaCorreoElectronico;
	private boolean habilitaFechaNacimiento;
	private boolean habilitaSexo;
	
	//Variables para colocar el valor ingresado como respuesta
	private String curp;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String telefono;
	private String correoElectronico;
	private String fechaNacimiento;
	private String sexo;
	
	/**
	 * 
	 */
	public ComponenteDatosPersonalesLlaveDTO() {
		super();
	}
		
	/**
	 * Constructor utilizado por la NamedQuery ComponenteDatosPersonalesLlave.findByIdComponente
	 * 
	 * @param idComponenteDatosPersonales
	 * @param idComponente
	 * @param idTipoComponente
	 * @param idSubseccionFormulario
	 * @param orden
	 * @param requerido
	 * @param tooltip
	 * @param descripcionTooltip
	 * @param tituloCampo
	 * @param activo
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param seccionSincronizada
	 * @param habilitaCurp
	 * @param habilitaNombre
	 * @param habilitaPrimerApellido
	 * @param habilitaSegundoApellido
	 * @param habilitaTelefono
	 * @param habilitaCorreoElectronico
	 * @param habilitaFechaNacimiento
	 */
	public ComponenteDatosPersonalesLlaveDTO(Long idComponenteDatosPersonales, Long idComponente, Integer idTipoComponente,
			Long idSubseccionFormulario, int orden, boolean requerido, boolean tooltip, String descripcionTooltip, 
			String tituloCampo, boolean activo, Date fechaCreacion,Date fechaUltimaActualizacion, boolean seccionSincronizada,
			boolean habilitaCurp, boolean habilitaNombre, boolean habilitaPrimerApellido, boolean habilitaSegundoApellido,
			boolean habilitaTelefono, boolean habilitaCorreoElectronico, boolean habilitaFechaNacimiento, boolean habilitaSexo) {
		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip,
				descripcionTooltip, tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		this.idComponenteDatosPersonales = idComponenteDatosPersonales;
		this.habilitaCurp = habilitaCurp;
		this.habilitaNombre = habilitaNombre;
		this.habilitaPrimerApellido = habilitaPrimerApellido;
		this.habilitaSegundoApellido = habilitaSegundoApellido;
		this.habilitaTelefono = habilitaTelefono;
		this.habilitaCorreoElectronico = habilitaCorreoElectronico;		
		this.habilitaFechaNacimiento = habilitaFechaNacimiento;
		this.habilitaSexo = habilitaSexo;
	}	

	/**
	 * @return the idComponenteDatosPersonales
	 */
	public Long getIdComponenteDatosPersonales() {
		return idComponenteDatosPersonales;
	}

	/**
	 * @param idComponenteDatosPersonales the idComponenteDatosPersonales to set
	 */
	public void setIdComponenteDatosPersonales(Long idComponenteDatosPersonales) {
		this.idComponenteDatosPersonales = idComponenteDatosPersonales;
	}

	/**
	 * @return the habilitaCurp
	 */
	public boolean isHabilitaCurp() {
		return habilitaCurp;
	}

	/**
	 * @param habilitaCurp the habilitaCurp to set
	 */
	public void setHabilitaCurp(boolean habilitaCurp) {
		this.habilitaCurp = habilitaCurp;
	}

	/**
	 * @return the habilitaNombre
	 */
	public boolean isHabilitaNombre() {
		return habilitaNombre;
	}

	/**
	 * @param habilitaNombre the habilitaNombre to set
	 */
	public void setHabilitaNombre(boolean habilitaNombre) {
		this.habilitaNombre = habilitaNombre;
	}

	/**
	 * @return the habilitaPrimerApellido
	 */
	public boolean isHabilitaPrimerApellido() {
		return habilitaPrimerApellido;
	}

	/**
	 * @param habilitaPrimerApellido the habilitaPrimerApellido to set
	 */
	public void setHabilitaPrimerApellido(boolean habilitaPrimerApellido) {
		this.habilitaPrimerApellido = habilitaPrimerApellido;
	}

	/**
	 * @return the habilitaSegundoApellido
	 */
	public boolean isHabilitaSegundoApellido() {
		return habilitaSegundoApellido;
	}

	/**
	 * @param habilitaSegundoApellido the habilitaSegundoApellido to set
	 */
	public void setHabilitaSegundoApellido(boolean habilitaSegundoApellido) {
		this.habilitaSegundoApellido = habilitaSegundoApellido;
	}

	/**
	 * @return the habilitaTelefono
	 */
	public boolean isHabilitaTelefono() {
		return habilitaTelefono;
	}

	/**
	 * @param habilitaTelefono the habilitaTelefono to set
	 */
	public void setHabilitaTelefono(boolean habilitaTelefono) {
		this.habilitaTelefono = habilitaTelefono;
	}

	/**
	 * @return the habilitaCorreoElectronico
	 */
	public boolean isHabilitaCorreoElectronico() {
		return habilitaCorreoElectronico;
	}

	/**
	 * @param habilitaCorreoElectronico the habilitaCorreoElectronico to set
	 */
	public void setHabilitaCorreoElectronico(boolean habilitaCorreoElectronico) {
		this.habilitaCorreoElectronico = habilitaCorreoElectronico;
	}

	/**
	 * @return the habilitaFechaNacimiento
	 */
	public boolean isHabilitaFechaNacimiento() {
		return habilitaFechaNacimiento;
	}

	/**
	 * @param habilitaFechaNacimiento the habilitaFechaNacimiento to set
	 */
	public void setHabilitaFechaNacimiento(boolean habilitaFechaNacimiento) {
		this.habilitaFechaNacimiento = habilitaFechaNacimiento;
	}
	
	public boolean isHabilitaSexo() {
		return habilitaSexo;
	}

	public void setHabilitaSexo(boolean habilitaSexo) {
		this.habilitaSexo = habilitaSexo;
	}

	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}

	/**
	 * @param curp the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the primerApellido
	 */
	public String getPrimerApellido() {
		return primerApellido;
	}

	/**
	 * @param primerApellido the primerApellido to set
	 */
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	/**
	 * @return the segundoApellido
	 */
	public String getSegundoApellido() {
		return segundoApellido;
	}

	/**
	 * @param segundoApellido the segundoApellido to set
	 */
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * @return the correoElectronico
	 */
	public String getCorreoElectronico() {
		return correoElectronico;
	}

	/**
	 * @param correoElectronico the correoElectronico to set
	 */
	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}	
	
}

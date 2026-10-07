package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class ComponenteDatosPersonalesDTO extends ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6602123878994695413L;

	private Long idComponenteDatosPersonales;
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
	
	private String curp;
	private String nombre;
	private String pApellido;
	private String sApellido;
	private String telefono;
	private String email;
	

	/**
	 * 
	 */
	public ComponenteDatosPersonalesDTO() {
	}

	/**
	 * Constructor utilizado por la NamedQuery ComponenteDatosPersonales.findByIdComponente
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
	 * @param curpObligatorio
	 * @param textoInteriorCurp
	 * @param habilitaNombre
	 * @param nombreObligatorio
	 * @param textoInteriorNombre
	 * @param habilitaPrimerApellido
	 * @param primerApellidoObligatorio
	 * @param textoInteriorPrimerApellido
	 * @param habilitaSegundoApellido
	 * @param segundoApellidoObligatorio
	 * @param textoInteriorSegundoApellido
	 * @param habilitaTelefono
	 * @param telefonoObligatorio
	 * @param textoInteriorTelefono
	 * @param habilitaCorreoElectronico
	 * @param correoElectronicoObligatorio
	 * @param textoInteriorCorreoElectronico
	 * @param habilitaRenapo
	 */
	public ComponenteDatosPersonalesDTO(Long idComponenteDatosPersonales, Long idComponente, Integer idTipoComponente,
			Long idSubseccionFormulario, int orden, boolean requerido, boolean tooltip, String descripcionTooltip,
			String tituloCampo, boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion, boolean seccionSincronizada, 
			boolean habilitaCurp, boolean curpObligatorio, String textoInteriorCurp, boolean habilitaNombre, boolean nombreObligatorio,
			String textoInteriorNombre, boolean habilitaPrimerApellido, boolean primerApellidoObligatorio,
			String textoInteriorPrimerApellido, boolean habilitaSegundoApellido, boolean segundoApellidoObligatorio,
			String textoInteriorSegundoApellido, boolean habilitaTelefono, boolean telefonoObligatorio,
			String textoInteriorTelefono, boolean habilitaCorreoElectronico, boolean correoElectronicoObligatorio,
			String textoInteriorCorreoElectronico, boolean habilitaRenapo) {
		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip, descripcionTooltip,
				tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		this.idComponenteDatosPersonales = idComponenteDatosPersonales;
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
	 * @return the curpObligatorio
	 */
	public boolean isCurpObligatorio() {
		return curpObligatorio;
	}

	/**
	 * @param curpObligatorio the curpObligatorio to set
	 */
	public void setCurpObligatorio(boolean curpObligatorio) {
		this.curpObligatorio = curpObligatorio;
	}

	/**
	 * @return the textoInteriorCurp
	 */
	public String getTextoInteriorCurp() {
		return textoInteriorCurp;
	}

	/**
	 * @param textoInteriorCurp the textoInteriorCurp to set
	 */
	public void setTextoInteriorCurp(String textoInteriorCurp) {
		this.textoInteriorCurp = textoInteriorCurp;
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
	 * @return the nombreObligatorio
	 */
	public boolean isNombreObligatorio() {
		return nombreObligatorio;
	}

	/**
	 * @param nombreObligatorio the nombreObligatorio to set
	 */
	public void setNombreObligatorio(boolean nombreObligatorio) {
		this.nombreObligatorio = nombreObligatorio;
	}

	/**
	 * @return the textoInteriorNombre
	 */
	public String getTextoInteriorNombre() {
		return textoInteriorNombre;
	}

	/**
	 * @param textoInteriorNombre the textoInteriorNombre to set
	 */
	public void setTextoInteriorNombre(String textoInteriorNombre) {
		this.textoInteriorNombre = textoInteriorNombre;
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
	 * @return the primerApellidoObligatorio
	 */
	public boolean isPrimerApellidoObligatorio() {
		return primerApellidoObligatorio;
	}

	/**
	 * @param primerApellidoObligatorio the primerApellidoObligatorio to set
	 */
	public void setPrimerApellidoObligatorio(boolean primerApellidoObligatorio) {
		this.primerApellidoObligatorio = primerApellidoObligatorio;
	}

	/**
	 * @return the textoInteriorPrimerApellido
	 */
	public String getTextoInteriorPrimerApellido() {
		return textoInteriorPrimerApellido;
	}

	/**
	 * @param textoInteriorPrimerApellido the textoInteriorPrimerApellido to set
	 */
	public void setTextoInteriorPrimerApellido(String textoInteriorPrimerApellido) {
		this.textoInteriorPrimerApellido = textoInteriorPrimerApellido;
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
	 * @return the segundoApellidoObligatorio
	 */
	public boolean isSegundoApellidoObligatorio() {
		return segundoApellidoObligatorio;
	}

	/**
	 * @param segundoApellidoObligatorio the segundoApellidoObligatorio to set
	 */
	public void setSegundoApellidoObligatorio(boolean segundoApellidoObligatorio) {
		this.segundoApellidoObligatorio = segundoApellidoObligatorio;
	}

	/**
	 * @return the textoInteriorSegundoApellido
	 */
	public String getTextoInteriorSegundoApellido() {
		return textoInteriorSegundoApellido;
	}

	/**
	 * @param textoInteriorSegundoApellido the textoInteriorSegundoApellido to set
	 */
	public void setTextoInteriorSegundoApellido(String textoInteriorSegundoApellido) {
		this.textoInteriorSegundoApellido = textoInteriorSegundoApellido;
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
	 * @return the telefonoObligatorio
	 */
	public boolean isTelefonoObligatorio() {
		return telefonoObligatorio;
	}

	/**
	 * @param telefonoObligatorio the telefonoObligatorio to set
	 */
	public void setTelefonoObligatorio(boolean telefonoObligatorio) {
		this.telefonoObligatorio = telefonoObligatorio;
	}

	/**
	 * @return the textoInteriorTelefono
	 */
	public String getTextoInteriorTelefono() {
		return textoInteriorTelefono;
	}

	/**
	 * @param textoInteriorTelefono the textoInteriorTelefono to set
	 */
	public void setTextoInteriorTelefono(String textoInteriorTelefono) {
		this.textoInteriorTelefono = textoInteriorTelefono;
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
	 * @return the correoElectronicoObligatorio
	 */
	public boolean isCorreoElectronicoObligatorio() {
		return correoElectronicoObligatorio;
	}

	/**
	 * @param correoElectronicoObligatorio the correoElectronicoObligatorio to set
	 */
	public void setCorreoElectronicoObligatorio(boolean correoElectronicoObligatorio) {
		this.correoElectronicoObligatorio = correoElectronicoObligatorio;
	}

	/**
	 * @return the textoInteriorCorreoElectronico
	 */
	public String getTextoInteriorCorreoElectronico() {
		return textoInteriorCorreoElectronico;
	}

	/**
	 * @param textoInteriorCorreoElectronico the textoInteriorCorreoElectronico to
	 *                                       set
	 */
	public void setTextoInteriorCorreoElectronico(String textoInteriorCorreoElectronico) {
		this.textoInteriorCorreoElectronico = textoInteriorCorreoElectronico;
	}

	/**
	 * @return the habilitaRenapo
	 */
	public boolean isHabilitaRenapo() {
		return habilitaRenapo;
	}

	/**
	 * @param habilitaRenapo the habilitaRenapo to set
	 */
	public void setHabilitaRenapo(boolean habilitaRenapo) {
		this.habilitaRenapo = habilitaRenapo;
	}
	
	//

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getpApellido() {
		return pApellido;
	}

	public void setpApellido(String pApellido) {
		this.pApellido = pApellido;
	}

	public String getsApellido() {
		return sApellido;
	}

	public void setsApellido(String sApellido) {
		this.sApellido = sApellido;
	}
	
	//

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
/*
	@Override
	public String toString() {
		return "ComponenteDatosPersonalesDTO [idComponenteDatosPersonales=" + idComponenteDatosPersonales
				+ ", habilitaCurp=" + habilitaCurp + ", curpObligatorio=" + curpObligatorio + ", textoInteriorCurp="
				+ textoInteriorCurp + ", habilitaNombre=" + habilitaNombre + ", nombreObligatorio=" + nombreObligatorio
				+ ", textoInteriorNombre=" + textoInteriorNombre + ", habilitaPrimerApellido=" + habilitaPrimerApellido
				+ ", primerApellidoObligatorio=" + primerApellidoObligatorio + ", textoInteriorPrimerApellido="
				+ textoInteriorPrimerApellido + ", habilitaSegundoApellido=" + habilitaSegundoApellido
				+ ", segundoApellidoObligatorio=" + segundoApellidoObligatorio + ", textoInteriorSegundoApellido="
				+ textoInteriorSegundoApellido + ", habilitaTelefono=" + habilitaTelefono + ", telefonoObligatorio="
				+ telefonoObligatorio + ", textoInteriorTelefono=" + textoInteriorTelefono
				+ ", habilitaCorreoElectronico=" + habilitaCorreoElectronico + ", correoElectronicoObligatorio="
				+ correoElectronicoObligatorio + ", textoInteriorCorreoElectronico=" + textoInteriorCorreoElectronico
				+ ", habilitaRenapo=" + habilitaRenapo + "]";
	}
*/
}

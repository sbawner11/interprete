package mx.gob.atdt.interprete.oauth.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PersonaDTO implements Serializable{

	private static final long serialVersionUID = -2398326667346889147L;
	
	private long idPersona;
	private String login;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String telVigente;
	private String curp;
	private String fechaNacimiento;
	private String sexo;
	private String estadoNacimiento;
	private int idRolUsuario;
	private String descripcionRol;	
	private String correoElectronico;
	private String nombreCompleto;
	
	private List<RolesUsuarioDTO> lstRoles;

	public PersonaDTO() {
		lstRoles = new ArrayList<RolesUsuarioDTO>();
	}
	
	/**
	 * @param idPersona
	 */
	public PersonaDTO(long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @param idPersona
	 * @param login
	 * @param nombre
	 * @param primerApellido
	 * @param segundoApellido
	 * @param correoAlternativo
	 */
	public PersonaDTO(long idPersona, String nombre, String primerApellido, String segundoApellido) {
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
	}
	
	/**
	 * @param idPersona
	 * @param nombre
	 * @param primerApellido
	 * @param segundoApellido
	 * @param folioIdentificacion
	 */
	public PersonaDTO(long idPersona, String nombre, String primerApellido, String segundoApellido,
			String folioIdentificacion, String correoAlternativo, String correoElectronico) {		
		this.idPersona = idPersona;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.correoElectronico = correoElectronico;
	}

	
	/**
	 * @param nombre
	 * @param primerApellido
	 * @param segundoApellido
	 */
	public PersonaDTO(String nombre, String primerApellido, String segundoApellido) {		
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
	}

	/**
	 * 
	 * @param idPersona
	 * @param login
	 * @param nombre
	 * @param primerApellido
	 * @param segundoApellido
	 * @param telVigente
	 * @param curp
	 * @param fechaNacimiento
	 * @param sexo
	 * @param estadoNacimiento
	 * @param idRolUsuario
	 * @param descripcionRol
	 * @param correoElectronico
	 */
	public PersonaDTO(long idPersona, String login, String nombre, String primerApellido, String segundoApellido,
			String telVigente, String curp, String fechaNacimiento, String sexo, String estadoNacimiento,
			int idRolUsuario, String descripcionRol, String correoElectronico) {
		this.idPersona = idPersona;
		this.login = login;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.telVigente = telVigente;
		this.curp = curp;
		this.fechaNacimiento = fechaNacimiento;
		this.sexo = sexo;
		this.estadoNacimiento = estadoNacimiento;
		this.idRolUsuario = idRolUsuario;
		this.descripcionRol = descripcionRol;
		this.setCorreoElectronico(correoElectronico);
	}
	/**
	 * 
	 * @param idPersona
	 * @param curp
	 * @param rfc
	 * @param nombre
	 * @param primerApellido
	 * @param segundoApellido
	 * @param fechaNacimiento
	 * @param sexo
	 * @param correoElectronico
	 * @param correoAlternativo
	 * @param telefonoAlternativo
	 * @param folioIdentificacion
	 * @param rutaIdentificacion
	 * @param idIdentificacion
	 * @param perfilCompleto
	 */
	public PersonaDTO(long idPersona, String curp, String nombre,
			String primerApellido, String segundoApellido, String fechaNacimiento, String sexo, String correoElectronico) {
		this.idPersona = idPersona;
		this.curp = curp;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.fechaNacimiento = fechaNacimiento;
		this.sexo = sexo;
		this.correoElectronico = correoElectronico;
	}

	/**
	 * @return the idPersona
	 */
	public long getIdPersona() {
		return idPersona;
	}

	/**
	 * @param idPersona the idPersona to set
	 */
	public void setIdPersona(long idPersona) {
		this.idPersona = idPersona;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
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
	 * @return the telVigente
	 */
	public String getTelVigente() {
		return telVigente;
	}

	/**
	 * @param telVigente the telVigente to set
	 */
	public void setTelVigente(String telVigente) {
		this.telVigente = telVigente;
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

	/**
	 * @return the sexo
	 */
	public String getSexo() {
		return sexo;
	}

	/**
	 * @param sexo the sexo to set
	 */
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	/**
	 * @return the estadoNacimiento
	 */
	public String getEstadoNacimiento() {
		return estadoNacimiento;
	}

	/**
	 * @param estadoNacimiento the estadoNacimiento to set
	 */
	public void setEstadoNacimiento(String estadoNacimiento) {
		this.estadoNacimiento = estadoNacimiento;
	}

	/**
	 * @return the idRolUsuario
	 */
	public int getIdRolUsuario() {
		return idRolUsuario;
	}

	/**
	 * @param idRolUsuario the idRolUsuario to set
	 */
	public void setIdRolUsuario(int idRolUsuario) {
		this.idRolUsuario = idRolUsuario;
	}

	/**
	 * @return the descripcionRol
	 */
	public String getDescripcionRol() {
		return descripcionRol;
	}

	/**
	 * @param descripcionRol the descripcionRol to set
	 */
	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
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
	 * @return the lstRoles
	 */
	public List<RolesUsuarioDTO> getLstRoles() {
		return lstRoles;
	}

	/**
	 * @param lstRoles the lstRoles to set
	 */
	public void setLstRoles(List<RolesUsuarioDTO> lstRoles) {
		this.lstRoles = lstRoles;
	}

	/**
	 * @return the nombreCompleto
	 */
	public String getNombreCompleto() {
		return nombreCompleto;
	}

	/**
	 * @param nombreCompleto the nombreCompleto to set
	 */
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
}

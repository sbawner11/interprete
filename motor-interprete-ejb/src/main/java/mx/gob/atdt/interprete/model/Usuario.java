package mx.gob.atdt.interprete.model;


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "usuario", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "Usuario.findById", 
			query = "SELECT new mx.gob.atdt.interprete.dto.UsuarioDTO(u.idUsuarioLlaveCdmx, u.nombre, u.primerApellido, u.segundoApellido, u.curp, u.telefono, u.correo, u.sexo) "
					+ "	FROM Usuario u "
					+ "	WHERE u.idUsuarioLlaveCdmx = :idUsuario")
})
public class Usuario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3378370913684976188L;
	
	private long idUsuarioLlaveCdmx;
	private String nombre;
	private String primerApellido;
	private String segundoApellido;
	private String curp;
	private String telefono;
	private String correo;
	private String sexo;
	private Set<BitMovimientosSecciones> bitMovimientosSeccioneses = new HashSet<BitMovimientosSecciones>(0);
	private Set<BitAsignacionRevisorTramite> bitAsignacionRevisor = new HashSet<BitAsignacionRevisorTramite>(0);
	private Set<BitAsignacionRevisorTramite> bitAsignacionUsuarioAsigna = new HashSet<BitAsignacionRevisorTramite>(0); 

	public Usuario() {
	}
	
	public Usuario(long idUsuario) {
		this.idUsuarioLlaveCdmx = idUsuario;
	}

	public Usuario(long idUsuarioLlaveCdmx, String nombre, String primerApellido, String curp, String correo) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.curp = curp;
		this.correo = correo;
	}

	public Usuario(long idUsuarioLlaveCdmx, String nombre, String primerApellido, String segundoApellido, String curp,
			String telefono, String correo, String sexo, Set<BitMovimientosSecciones> bitMovimientosSeccioneses,
			Set<Proyecto> proyectos) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
		this.nombre = nombre;
		this.primerApellido = primerApellido;
		this.segundoApellido = segundoApellido;
		this.curp = curp;
		this.telefono = telefono;
		this.correo = correo;
		this.sexo = sexo;
		this.bitMovimientosSeccioneses = bitMovimientosSeccioneses;
	}

	@Id
	@Column(name = "id_usuario_llave_cdmx", unique = true, nullable = false)
	public long getIdUsuarioLlaveCdmx() {
		return this.idUsuarioLlaveCdmx;
	}

	public void setIdUsuarioLlaveCdmx(long idUsuarioLlaveCdmx) {
		this.idUsuarioLlaveCdmx = idUsuarioLlaveCdmx;
	}

	@Column(name = "nombre", nullable = false, length = 60)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "primer_apellido", nullable = false, length = 60)
	public String getPrimerApellido() {
		return this.primerApellido;
	}

	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}

	@Column(name = "segundo_apellido", length = 60)
	public String getSegundoApellido() {
		return this.segundoApellido;
	}

	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}

	@Column(name = "curp", nullable = false, length = 18)
	public String getCurp() {
		return this.curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	@Column(name = "telefono", length = 10)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "correo", length = 60)
	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	@Column(name = "sexo", length = 15)
	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuario")
	public Set<BitMovimientosSecciones> getBitMovimientosSeccioneses() {
		return this.bitMovimientosSeccioneses;
	}

	public void setBitMovimientosSeccioneses(Set<BitMovimientosSecciones> bitMovimientosSeccioneses) {
		this.bitMovimientosSeccioneses = bitMovimientosSeccioneses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioRevisor")
	public Set<BitAsignacionRevisorTramite> getBitAsignacionRevisor() {
		return bitAsignacionRevisor;
	}

	public void setBitAsignacionRevisor(Set<BitAsignacionRevisorTramite> bitAsignacionRevisor) {
		this.bitAsignacionRevisor = bitAsignacionRevisor;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioAsigna")
	public Set<BitAsignacionRevisorTramite> getBitAsignacionUsuarioAsigna() {
		return bitAsignacionUsuarioAsigna;
	}

	public void setBitAsignacionUsuarioAsigna(Set<BitAsignacionRevisorTramite> bitAsignacionUsuarioAsigna) {
		this.bitAsignacionUsuarioAsigna = bitAsignacionUsuarioAsigna;
	}

}

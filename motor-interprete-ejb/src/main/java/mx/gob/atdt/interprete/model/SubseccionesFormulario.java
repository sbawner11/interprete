package mx.gob.atdt.interprete.model;

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

@Entity
@Table(name = "subsecciones_formulario", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "SubseccionesFormulario.findByIdSeccion", query = " SELECT new mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO( "
				+ " ssf.idSubseccionFormulario, sf.idSeccionFormulario, ssf.nombreSubseccion, "
				+ " ssf.orden, ssf.activo, ssf.seccionSincronizada ) " + " FROM SubseccionesFormulario ssf "
				+ " JOIN ssf.seccionesFormulario sf " + " WHERE sf.idSeccionFormulario = :idSeccionFormulario "
				+ " ORDER BY ssf.orden "),
		@NamedQuery(name = "SubseccionesFormulario.existeSubseccionActiva", query = " SELECT new mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO( "
				+ " sf.idSubseccionFormulario, sf2.idSeccionFormulario, sf.nombreSubseccion, "
				+ " sf.orden, sf.activo, sf.seccionSincronizada ) " + " FROM SubseccionesFormulario sf "
				+ " JOIN sf.seccionesFormulario sf2 " + " WHERE sf2.idSeccionFormulario =:idSeccion "
				+ " ORDER BY sf.orden "),
		@NamedQuery(name = "SubseccionesFormulario.findByIdSubseccion", query = " SELECT new mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO( "
				+ " ssf.idSubseccionFormulario, sf.idSeccionFormulario, ssf.nombreSubseccion, "
				+ " ssf.orden, ssf.activo, ssf.seccionSincronizada ) " + " FROM SubseccionesFormulario ssf "
				+ " JOIN ssf.seccionesFormulario sf WHERE ssf.idSubseccionFormulario = :idSubSeccion "
				+ " ORDER BY ssf.orden "),
		@NamedQuery(name = "SubseccionesFormulario.findSeccionByIdSubseccion", query = " SELECT new mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO( "
				+ " ssf.idSubseccionFormulario, sf.idSeccionFormulario) " + " FROM SubseccionesFormulario ssf "
				+ " JOIN ssf.seccionesFormulario sf "
				+ " WHERE ssf.idSubseccionFormulario = :idSubseccionFormulario ")
		})
public class SubseccionesFormulario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5689757039212189012L;

	private Long idSubseccionFormulario;
	private SeccionesFormulario seccionesFormulario;
	private String nombreSubseccion;
	private int orden;
	private boolean activo;
	private boolean seccionSincronizada;
	private Set<Componente> componentes = new HashSet<Componente>(0);

	public SubseccionesFormulario() {
	}

	public SubseccionesFormulario(Long idSubseccionFormulario) {
		this.idSubseccionFormulario = idSubseccionFormulario;
	}

	public SubseccionesFormulario(Long idSubseccionFormulario, SeccionesFormulario seccionesFormulario,
			String nombreSubseccion, int orden, boolean activo, boolean seccionSincronizada) {
		this.idSubseccionFormulario = idSubseccionFormulario;
		this.seccionesFormulario = seccionesFormulario;
		this.nombreSubseccion = nombreSubseccion;
		this.orden = orden;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	public SubseccionesFormulario(Long idSubseccionFormulario, SeccionesFormulario seccionesFormulario,
			String nombreSubseccion, int orden, boolean activo, boolean seccionSincronizada,
			Set<Componente> componentes) {
		this.idSubseccionFormulario = idSubseccionFormulario;
		this.seccionesFormulario = seccionesFormulario;
		this.nombreSubseccion = nombreSubseccion;
		this.orden = orden;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
		this.componentes = componentes;
	}

	@Id
	@Column(name = "id_subseccion_formulario", unique = true, nullable = false)
	public Long getIdSubseccionFormulario() {
		return this.idSubseccionFormulario;
	}

	public void setIdSubseccionFormulario(Long idSubseccionFormulario) {
		this.idSubseccionFormulario = idSubseccionFormulario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_seccion_formulario", nullable = false)
	public SeccionesFormulario getSeccionesFormulario() {
		return this.seccionesFormulario;
	}

	public void setSeccionesFormulario(SeccionesFormulario seccionesFormulario) {
		this.seccionesFormulario = seccionesFormulario;
	}

	@Column(name = "nombre_subseccion", nullable = false, length = 200)
	public String getNombreSubseccion() {
		return this.nombreSubseccion;
	}

	public void setNombreSubseccion(String nombreSubseccion) {
		this.nombreSubseccion = nombreSubseccion;
	}

	@Column(name = "orden", nullable = false)
	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@Column(name = "seccion_sincronizada", nullable = false)
	public boolean isSeccionSincronizada() {
		return this.seccionSincronizada;
	}

	public void setSeccionSincronizada(boolean seccionSincronizada) {
		this.seccionSincronizada = seccionSincronizada;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "subseccionesFormulario")
	public Set<Componente> getComponentes() {
		return this.componentes;
	}

	public void setComponentes(Set<Componente> componentes) {
		this.componentes = componentes;
	}

}
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
@Table(name = "secciones_formulario", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "SeccionesFormulario.findByIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.SeccionesFormularioDTO"
					+ " (sf.idSeccionFormulario, p.idProyecto, sf.nombreSeccion, sf.orden, sf.activo, sf.seccionSincronizada ) "
					+ " FROM SeccionesFormulario sf "
					+ " JOIN sf.proyecto p "
					+ " WHERE p.idProyecto = :idProyecto "
					+ " ORDER BY sf.orden"),
		@NamedQuery(name = "SeccionesFormulario.existeSeccionActivaIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.SeccionesFormularioDTO("
					+ " sf.idSeccionFormulario, p.idProyecto) " 
					+ " FROM SeccionesFormulario sf "
					+ "	JOIN sf.proyecto p "
					+ " WHERE p.idProyecto = :idProyecto "),
		@NamedQuery(name = "SeccionesFormulario.findById", 
		query = "SELECT new mx.gob.atdt.interprete.dto.SeccionesFormularioDTO"
				+ " (sf.idSeccionFormulario, p.idProyecto, sf.nombreSeccion, sf.orden, sf.activo, sf.seccionSincronizada ) "
				+ " FROM SeccionesFormulario sf "
				+ " JOIN sf.proyecto p "
				+ " WHERE sf.idSeccionFormulario = :idSeccionFormulario "),
})
public class SeccionesFormulario implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3806332658606601306L;

	private Long idSeccionFormulario;
	private Proyecto proyecto;
	private String nombreSeccion;
	private int orden;
	private boolean activo;
	private boolean seccionSincronizada;
	
	private Set<SubseccionesFormulario> subseccionesFormularios = new HashSet<SubseccionesFormulario>(0);

	public SeccionesFormulario() {
	}

	public SeccionesFormulario(Long idSeccionFormulario, Proyecto proyecto, String nombreSeccion, int orden,
			boolean activo, boolean seccionSincronizada) {
		this.idSeccionFormulario = idSeccionFormulario;
		this.proyecto = proyecto;
		this.nombreSeccion = nombreSeccion;
		this.orden = orden;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	public SeccionesFormulario(Long idSeccionFormulario, Proyecto proyecto, String nombreSeccion, int orden,
			boolean activo, boolean seccionSincronizada, Set<SubseccionesFormulario> subseccionesFormularios) {
		this.idSeccionFormulario = idSeccionFormulario;
		this.proyecto = proyecto;
		this.nombreSeccion = nombreSeccion;
		this.orden = orden;
		this.activo = activo;
		this.subseccionesFormularios = subseccionesFormularios;
		this.seccionSincronizada = seccionSincronizada;
	}

	@Id
	@Column(name = "id_seccion_formulario", unique = true, nullable = false)
	public Long getIdSeccionFormulario() {
		return this.idSeccionFormulario;
	}

	public void setIdSeccionFormulario(Long idSeccionFormulario) {
		this.idSeccionFormulario = idSeccionFormulario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "nombre_seccion", nullable = false, length = 200)
	public String getNombreSeccion() {
		return this.nombreSeccion;
	}

	public void setNombreSeccion(String nombreSeccion) {
		this.nombreSeccion = nombreSeccion;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "seccionesFormulario")
	public Set<SubseccionesFormulario> getSubseccionesFormularios() {
		return this.subseccionesFormularios;
	}

	public void setSubseccionesFormularios(Set<SubseccionesFormulario> subseccionesFormularios) {
		this.subseccionesFormularios = subseccionesFormularios;
	}

}

package mx.gob.atdt.interprete.model;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "configuracion_condiciones", schema = "motor_interprete")
@NamedQueries({ 
	  @NamedQuery(name = "ConfiguracionCondiciones.findById",
			  query = "SELECT new mx.gob.atdt.interprete.dto.ConfiguracionCondicionesDTO(" +
						 " cc.idConfiguracion, sc.idSeccionFormulario, sd.idSeccionFormulario, " +  
			  			 " c.idComponente, oc.idOperador, cc.activo, cc.idUsuarioRegistro, " +
			  	 		 " cc.fechaCreacion, cc.fechaUltimaActualizacion, cc.seccionSincronizada ) " +
			  			 " FROM ConfiguracionCondiciones cc " + 
			  			 " JOIN cc.seccionesFormularioByIdSeccionCondicionada sc " +
			  			 " JOIN cc.seccionesFormularioByIdSeccionCondicion sd " + 
			  			 " JOIN cc.catOperador oc " +
			  			 " JOIN cc.componente c " +
					  	 " WHERE cc.idConfiguracion = :idConfiguracion"),				 
	@NamedQuery(name = "ConfiguracionCondiciones.findAll",
	  		query = "SELECT new mx.gob.atdt.interprete.dto.ConfiguracionCondicionesDTO( " +
				" cc.idConfiguracion ) " + 
				" FROM ConfiguracionCondiciones cc " +
		  		" ORDER BY cc.idConfiguracion"),
	  @NamedQuery(name = "ConfiguracionCondiciones.findByIdSeccion", 
		query = "SELECT new mx.gob.atdt.interprete.dto.ConfiguracionCondicionesDTO(" +
				" cc.idConfiguracion, sc.idSeccionFormulario, sc.nombreSeccion, sd.idSeccionFormulario, sd.nombreSeccion, " +  
				" c.idComponente, c.tituloCampo, oc.idOperador, oc.valorOperador, cc.fechaCreacion) " +
				" FROM ConfiguracionCondiciones cc " + 
				" JOIN cc.seccionesFormularioByIdSeccionCondicionada sc " +
				" JOIN cc.seccionesFormularioByIdSeccionCondicion sd " + 
				" JOIN cc.catOperador oc " +
				" JOIN cc.componente c " +
				" WHERE sc.idSeccionFormulario = :idSeccionFormulario" +
			  	" and cc.activo= true"),
	  @NamedQuery(name = "ConfiguracionCondiciones.findByIdProyecto", 
		query = "SELECT new mx.gob.atdt.interprete.dto.ConfiguracionCondicionesDTO( "
				+ " cc.idConfiguracion ) " 
				+ " FROM ConfiguracionCondiciones cc "
				+ " JOIN cc.seccionesFormularioByIdSeccionCondicionada sf "
				+ " JOIN sf.proyecto p "
				+ " WHERE p.idProyecto = :idProyecto " 
				+ " ORDER BY cc.idConfiguracion")
})
public class ConfiguracionCondiciones implements java.io.Serializable {

	private static final long serialVersionUID = 5689757039212189012L;

	private long idConfiguracion;
	private CatOperador catOperador;
	private Componente componente;
	private SeccionesFormulario seccionesFormularioByIdSeccionCondicion;
	private SeccionesFormulario seccionesFormularioByIdSeccionCondicionada;
	private boolean activo;
	private long idUsuarioRegistro;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean seccionSincronizada;
	private Set<ConfiguracionCondicionValor> configuracionCondicionValors = new HashSet<>(0);

	public ConfiguracionCondiciones() {
	}
	
	public ConfiguracionCondiciones(long idConfiguracion) {
		this.idConfiguracion = idConfiguracion;
	}
	
	@SuppressWarnings({"java:S107"})
	public ConfiguracionCondiciones(long idConfiguracion, CatOperador catOperador, Componente componente,
			SeccionesFormulario seccionesFormularioByIdSeccionCondicion,
			SeccionesFormulario seccionesFormularioByIdSeccionCondicionada, long idUsuarioRegistro, boolean activo,
			Date fechaCreacion, Date fechaUltimaActualizacion, boolean seccionSincronizada) {
		this.idConfiguracion = idConfiguracion;
		this.catOperador = catOperador;
		this.componente = componente;
		this.seccionesFormularioByIdSeccionCondicion = seccionesFormularioByIdSeccionCondicion;
		this.seccionesFormularioByIdSeccionCondicionada = seccionesFormularioByIdSeccionCondicionada;
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.activo = activo;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.seccionSincronizada = seccionSincronizada;
	}	

	@SuppressWarnings({"java:S107"})
	public ConfiguracionCondiciones(long idConfiguracion, CatOperador catOperador, Componente componente,
			SeccionesFormulario seccionesFormularioByIdSeccionCondicion,
			SeccionesFormulario seccionesFormularioByIdSeccionCondicionada, long idUsuarioRegistro, boolean activo,
			Date fechaCreacion, Date fechaUltimaActualizacion, boolean seccionSincronizada,
			Set<ConfiguracionCondicionValor> configuracionCondicionValors) {
		this.idConfiguracion = idConfiguracion;
		this.catOperador = catOperador;
		this.componente = componente;
		this.seccionesFormularioByIdSeccionCondicion = seccionesFormularioByIdSeccionCondicion;
		this.seccionesFormularioByIdSeccionCondicionada = seccionesFormularioByIdSeccionCondicionada;
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.activo = activo;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.seccionSincronizada = seccionSincronizada;
		this.configuracionCondicionValors = configuracionCondicionValors;
	}
	
	@Id
	@Column(name = "id_configuracion", unique = true, nullable = false)
	public long getIdConfiguracion() {
		return this.idConfiguracion;
	}

	public void setIdConfiguracion(long idConfiguracion) {
		this.idConfiguracion = idConfiguracion;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_operador_condicion", nullable = false)
	public CatOperador getCatOperador() {
		return this.catOperador;
	}

	public void setCatOperador(CatOperador catOperador) {
		this.catOperador = catOperador;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente_condicion", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_seccion_condicion", nullable = false)
	public SeccionesFormulario getSeccionesFormularioByIdSeccionCondicion() {
		return this.seccionesFormularioByIdSeccionCondicion;
	}

	public void setSeccionesFormularioByIdSeccionCondicion(
			SeccionesFormulario seccionesFormularioByIdSeccionCondicion) {
		this.seccionesFormularioByIdSeccionCondicion = seccionesFormularioByIdSeccionCondicion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_seccion_condicionada", nullable = false)
	public SeccionesFormulario getSeccionesFormularioByIdSeccionCondicionada() {
		return this.seccionesFormularioByIdSeccionCondicionada;
	}

	public void setSeccionesFormularioByIdSeccionCondicionada(
			SeccionesFormulario seccionesFormularioByIdSeccionCondicionada) {
		this.seccionesFormularioByIdSeccionCondicionada = seccionesFormularioByIdSeccionCondicionada;
	}


	@Column(name = "id_usuario_registro", nullable = false)
	public long getIdUsuarioRegistro() {
		return this.idUsuarioRegistro;
	}

	public void setIdUsuarioRegistro(long idUsuarioRegistro) {
		this.idUsuarioRegistro = idUsuarioRegistro;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "configuracionCondiciones")
	public Set<ConfiguracionCondicionValor> getConfiguracionCondicionValors() {
		return this.configuracionCondicionValors;
	}

	public void setConfiguracionCondicionValors(Set<ConfiguracionCondicionValor> configuracionCondicionValors) {
		this.configuracionCondicionValors = configuracionCondicionValors;
	}	

}
package mx.gob.atdt.interprete.model;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "notificaciones", schema = "motor_interprete")

@NamedQueries({
	  @NamedQuery(name = "Notificaciones.findById",
			  query = "SELECT new mx.gob.atdt.interprete.dto.NotificacionesDTO(" +
			  	 		 " n.idNotificacion, p.idProyecto, n.envioNotificaciones, " +  
			  			 " n.correosNotificacion, ds.idDiaSemana, n.idUsuario, " +
			  	 		 " n.fechaCreacion, n.fechaActualizacion, n.activo, n.seccionSincronizada ) " +
			  			 " FROM Notificaciones n " + 
			  			" JOIN n.proyecto p " +
			  			" JOIN n.catDiaSemana ds" + 
					  	 " WHERE n.idNotificacion = :idNotificacion"),	  
	  @NamedQuery(name = "Notificaciones.findAll",
	  		query = "SELECT new mx.gob.atdt.interprete.dto.NotificacionesDTO(n.idNotificacion) " + 
					" FROM Notificaciones n " +
			  		" ORDER BY n.idNotificacion"),
	  @NamedQuery(name = "Notificaciones.findAllActivos",
		query = "SELECT new mx.gob.atdt.interprete.dto.NotificacionesDTO(n.idNotificacion) " + 
				" FROM Notificaciones n " +
				" WHERE n.activo = true " +
		  		" ORDER BY n.idNotificacion"),
	  @NamedQuery(name = "Notificaciones.findByIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.NotificacionesDTO(n.idNotificacion) " 
				+ " FROM Notificaciones n "
				+ " JOIN n.proyecto p "
				+ " WHERE p.idProyecto = :idProyecto " 
				+ " ORDER BY n.idNotificacion"),
		@NamedQuery(name = "Notificaciones.findActivoByIdProyecto", query = "SELECT new mx.gob.atdt.interprete.dto.NotificacionesDTO( "
				+ " n.idNotificacion, p.idProyecto, n.envioNotificaciones, "
				+ " n.correosNotificacion, ds.idDiaSemana, n.idUsuario, "
				+ " n.fechaCreacion, n.fechaActualizacion, n.activo, n.seccionSincronizada ) "
				+ " FROM Notificaciones n " + " JOIN n.proyecto p " + " JOIN n.catDiaSemana ds"
				+ " WHERE p.idProyecto = :idProyecto AND n.activo = true " + " ORDER BY n.idNotificacion")
	  
})

public class Notificaciones implements java.io.Serializable {
	
	private static final long serialVersionUID = 5689757039212189012L;
	
    private long idNotificacion;
    private Proyecto proyecto;
    private boolean envioNotificaciones;
    private String correosNotificacion;
    private CatDiasSemana catDiaSemana;
    private long idUsuario;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private boolean activo;
    private boolean seccionSincronizada;
    
    @Id
    @Column(name = "id_notificacion", unique = true, nullable = false)
	public long getIdNotificacion() {
		return idNotificacion;
	}
	
	public void setIdNotificacion(long idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "envio_notificaciones", nullable = false)
	public boolean isEnvioNotificaciones() {
		return envioNotificaciones;
	}

	public void setEnvioNotificaciones(boolean envioNotificaciones) {
		this.envioNotificaciones = envioNotificaciones;
	}

	@Column(name = "correos_notificacion", nullable = true, length = 300)
	public String getCorreosNotificacion() {
		return correosNotificacion;
	}

	public void setCorreosNotificacion(String correosNotificacion) {
		this.correosNotificacion = correosNotificacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dia_semana", nullable = true)
	public CatDiasSemana getCatDiaSemana() {
		return catDiaSemana;
	}

	public void setCatDiaSemana(CatDiasSemana catDiaSemana) {
		this.catDiaSemana = catDiaSemana;
	}

	@Column(name = "id_usuario", nullable = false)
	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_creacion", nullable = false, length = 29)
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_actualizacion", nullable = false, length = 29)
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
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
    
}
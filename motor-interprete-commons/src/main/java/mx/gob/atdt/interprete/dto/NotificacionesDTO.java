package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class NotificacionesDTO implements Serializable {

	private static final long serialVersionUID = -5184202528764714914L;
		
    private long idNotificacion;
    private ProyectoDTO proyectoDTO;
    private boolean envioNotificaciones;
    private String correosNotificacion;
    private CatDiasSemanaDTO catDiaSemanaDTO;
    private Long idUsuario;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private boolean activo;
    private boolean seccionSincronizada;
    
    /*
	 * 
	 */
	public NotificacionesDTO() {
		this.proyectoDTO = new ProyectoDTO();
		this.catDiaSemanaDTO = new CatDiasSemanaDTO();
	}
	
	/**
	 * @param idNotificacion
	 * @param idProyecto
	 * @param envioNotificaciones
	 * @param correosNotificacion
	 * @param idDiaSemana
	 * @param idUsuario
	 * @param fechaCreacion
	 * @param fechaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	@SuppressWarnings({"java:S107"})
	public NotificacionesDTO(long idNotificacion,long idProyecto, boolean envioNotificaciones,
				String correosNotificacion, Integer idDiaSemana, Long idUsuario,
				Date fechaCreacion, Date fechaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idNotificacion = idNotificacion;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.envioNotificaciones = envioNotificaciones;
		this.correosNotificacion = correosNotificacion;
		this.catDiaSemanaDTO = new CatDiasSemanaDTO(idDiaSemana);
		this.idUsuario = idUsuario;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}
	
    /*
	 * 
	 */
	public NotificacionesDTO(long idNotificacion) {
		this.idNotificacion = idNotificacion;
		this.proyectoDTO = new ProyectoDTO();
		this.catDiaSemanaDTO = new CatDiasSemanaDTO();
	}
	
	// Getters y setters
	
    public long getIdNotificacion() {
    	 return idNotificacion; 
    }
    
    public void setIdNotificacion(long idNotificacion) {
    	this.idNotificacion = idNotificacion; 
    }

    public ProyectoDTO getProyectoDTO() { 
    	return proyectoDTO; 
    }
    
    public void setProyectoDTO(ProyectoDTO proyectoDTO) { 
    	this.proyectoDTO = proyectoDTO; 
    }

    public boolean isEnvioNotificaciones() { 
    	return envioNotificaciones; 
    }
    
    public void setEnvioNotificaciones(boolean envioNotificaciones) { 
    	this.envioNotificaciones = envioNotificaciones; 
    }

    public String getCorreosNotificacion() { 
    	return correosNotificacion; 
    }
    
    public void setCorreosNotificacion(String correosNotificacion) { 
    	this.correosNotificacion = correosNotificacion; 
    }

    public CatDiasSemanaDTO getCatDiaSemanaDTO() { 
    	return catDiaSemanaDTO; 
    }
    
    public void setCatDiaSemanaDTO(CatDiasSemanaDTO catDiaSemanaDTO) { 
    	this.catDiaSemanaDTO = catDiaSemanaDTO; 
    }

    public Long getIdUsuario() { 
    	return idUsuario; 
    }
    
    public void setIdUsuario(Long idUsuario) { 
    	this.idUsuario = idUsuario; 
    }

    public Date getFechaCreacion() { 
    	return fechaCreacion; 
    }
    
    public void setFechaCreacion(Date fechaCreacion) { 
    	this.fechaCreacion = fechaCreacion; 
    }

    public Date getFechaActualizacion() { 
    	return fechaActualizacion; 
    }
    
    public void setFechaActualizacion(Date fechaActualizacion) { 
    	this.fechaActualizacion = fechaActualizacion; 
    }

    public boolean isActivo() { 
    	return activo; 
    }
    
    public void setActivo(boolean activo) { 
    	this.activo = activo; 
    }

    public boolean isSeccionSincronizada() { 
    	return seccionSincronizada; 
    }
    
    public void setSeccionSincronizada(boolean seccionSincronizada) { 
    	this.seccionSincronizada = seccionSincronizada; 
    }
		
}
package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class ConfiguracionCatalogoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long idConfiguracionCatalogo;
    private Long idProyecto;
    private Integer idCatalogo;
    private Integer idOpcionCatalogo;
    private String descripcionUsuario;
    private Long idUsuarioCambio;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private Boolean activo;
    private Boolean seccionSincronizada;

    public ConfiguracionCatalogoDTO() {}
    
    public ConfiguracionCatalogoDTO(Long idConfiguracionCatalogo, Long idProyecto, Integer idCatalogo, 
            Integer idOpcionCatalogo, String descripcionUsuario, Long idUsuarioCambio,
            Date fechaCreacion, Date fechaActualizacion, Boolean activo, 
            Boolean seccionSincronizada) {
    	
		this.idConfiguracionCatalogo = idConfiguracionCatalogo;
		this.idProyecto = idProyecto;
		this.idCatalogo = idCatalogo;
		this.idOpcionCatalogo = idOpcionCatalogo;
		this.descripcionUsuario = descripcionUsuario;
		this.idUsuarioCambio = idUsuarioCambio;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
    }
    
    public ConfiguracionCatalogoDTO(Long idProyecto, Integer idCatalogo, Integer idOpcionCatalogo, 
                                   String descripcionUsuario, Long idUsuarioCambio) {
        this.idProyecto = idProyecto;
        this.idCatalogo = idCatalogo;
        this.idOpcionCatalogo = idOpcionCatalogo;
        this.descripcionUsuario = descripcionUsuario;
        this.idUsuarioCambio = idUsuarioCambio;
        this.fechaCreacion = new Date();
        this.fechaActualizacion = new Date();
        this.activo = true;
        this.seccionSincronizada = false;
    }
    
    public Long getIdConfiguracionCatalogo() { return idConfiguracionCatalogo; }
    public void setIdConfiguracionCatalogo(Long idConfiguracionCatalogo) { this.idConfiguracionCatalogo = idConfiguracionCatalogo; }

    public Long getIdProyecto() { return idProyecto; }
    public void setIdProyecto(Long idProyecto) { this.idProyecto = idProyecto; }

    public Integer getIdCatalogo() { return idCatalogo; }
    public void setIdCatalogo(Integer idCatalogo) { this.idCatalogo = idCatalogo; }
    
    public Integer getIdOpcionCatalogo() { return idOpcionCatalogo; }
    public void setIdOpcionCatalogo(Integer idOpcionCatalogo) { this.idOpcionCatalogo = idOpcionCatalogo; }

    public String getDescripcionUsuario() { return descripcionUsuario; }
    public void setDescripcionUsuario(String descripcionUsuario) { this.descripcionUsuario = descripcionUsuario; }

    public Long getIdUsuarioCambio() { return idUsuarioCambio; }
    public void setIdUsuarioCambio(Long idUsuarioCambio) { this.idUsuarioCambio = idUsuarioCambio; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Date getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(Date fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Boolean getSeccionSincronizada() { return seccionSincronizada; }
    public void setSeccionSincronizada(Boolean seccionSincronizada) { this.seccionSincronizada = seccionSincronizada; }
}
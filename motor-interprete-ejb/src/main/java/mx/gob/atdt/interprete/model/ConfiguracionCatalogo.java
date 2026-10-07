package mx.gob.atdt.interprete.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "configuracion_catalogo", schema = "motor_interprete")
@NamedQueries({
    @NamedQuery(name = "ConfiguracionCatalogo.findByProyectoAndCatalogo",
            query = "SELECT NEW mx.gob.atdt.interprete.dto.ConfiguracionCatalogoDTO(" +
                    "c.idConfiguracionCatalogo, c.proyecto.idProyecto, c.catalogos.idCatalogo, " +
                    "c.opcionesCatalogo.idOpcionCatalogo, c.descripcionUsuario, c.idUsuarioCambio, " +
                    "c.fechaCreacion, c.fechaActualizacion, c.activo, c.seccionSincronizada) " +
                    "FROM ConfiguracionCatalogo c " +
                    "WHERE c.proyecto.idProyecto = :idProyecto AND c.catalogos.idCatalogo = :idCatalogo AND c.activo = true"),
        
    @NamedQuery(name = "ConfiguracionCatalogo.findByProyectoCatalogoAndOpcionCatalogo",
            query = "SELECT NEW mx.gob.atdt.interprete.dto.ConfiguracionCatalogoDTO(" +
                    "c.idConfiguracionCatalogo, c.proyecto.idProyecto, c.catalogos.idCatalogo, " +
                    "c.opcionesCatalogo.idOpcionCatalogo, c.descripcionUsuario, c.idUsuarioCambio, " +
                    "c.fechaCreacion, c.fechaActualizacion, c.activo, c.seccionSincronizada) " +
                    "FROM ConfiguracionCatalogo c " +
                    "WHERE c.proyecto.idProyecto = :idProyecto AND c.catalogos.idCatalogo = :idCatalogo " +
                    "AND c.opcionesCatalogo.idOpcionCatalogo = :idOpcionCatalogo"),
    
    @NamedQuery(name = "ConfiguracionCatalogo.inactivarByProyectoCatalogoAndOpcionCatalogo",
        query = "UPDATE ConfiguracionCatalogo c SET c.activo = false, c.fechaActualizacion = :fechaActualizacion "
        		+ "WHERE c.proyecto.idProyecto = :idProyecto AND c.catalogos.idCatalogo = :idCatalogo "
        		+ "AND c.opcionesCatalogo.idOpcionCatalogo = :idOpcionCatalogo"),
       
    @NamedQuery(name = "ConfiguracionCatalogo.findByProyectoAndSincronizacion",
    query = "SELECT NEW mx.gob.atdt.interprete.dto.ConfiguracionCatalogoDTO(" +
            "c.idConfiguracionCatalogo, c.proyecto.idProyecto, c.catalogos.idCatalogo, " +
            "c.opcionesCatalogo.idOpcionCatalogo, c.descripcionUsuario, c.idUsuarioCambio, " +
            "c.fechaCreacion, c.fechaActualizacion, c.activo, c.seccionSincronizada) " +
            "FROM ConfiguracionCatalogo c " +
            "WHERE c.proyecto.idProyecto = :idProyecto AND c.seccionSincronizada = :seccionSincronizada AND c.activo = true"),

	@NamedQuery(name = "ConfiguracionCatalogo.findByProyecto",
	    query = "SELECT NEW mx.gob.atdt.interprete.dto.ConfiguracionCatalogoDTO(" +
	            "c.idConfiguracionCatalogo, c.proyecto.idProyecto, c.catalogos.idCatalogo, " +
	            "c.opcionesCatalogo.idOpcionCatalogo, c.descripcionUsuario, c.idUsuarioCambio, " +
	            "c.fechaCreacion, c.fechaActualizacion, c.activo, c.seccionSincronizada) " +
	            "FROM ConfiguracionCatalogo c " +
	            "WHERE c.proyecto.idProyecto = :idProyecto AND c.activo = true")
})
public class ConfiguracionCatalogo implements Serializable {

    private static final long serialVersionUID = 5689757039212189012L;

    @Id
    @Column(name = "id_configuracion_catalogo", unique = true, nullable = false)
    private Long idConfiguracionCatalogo;

    @ManyToOne
    @JoinColumn(name = "id_proyecto", referencedColumnName = "id_proyecto", nullable = false)
    private Proyecto proyecto;

    @ManyToOne
    @JoinColumn(name = "id_catalogo", referencedColumnName = "id_catalogo", nullable = false)
    private Catalogos catalogos;

    @ManyToOne
    @JoinColumn(name = "id_opcion_catalogo", referencedColumnName = "id_opcion_catalogo", nullable = false)
    private OpcionesCatalogo opcionesCatalogo;

    @Column(name = "descripcion_usuario", nullable = false, length = 60)
    private String descripcionUsuario;

	@Column(name = "id_usuario_cambio", nullable = false)
    private long idUsuarioCambio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false)
    private Date fechaCreacion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion", nullable = false)
    private Date fechaActualizacion;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "seccion_sincronizada", nullable = false)
    private Boolean seccionSincronizada;

    public ConfiguracionCatalogo() {
    }

    public ConfiguracionCatalogo(Proyecto proyecto, Catalogos catalogos, OpcionesCatalogo opcionesCatalogo, 
                                String descripcionUsuario, long idUsuarioCambio) {
        this.proyecto = proyecto;
        this.catalogos = catalogos;
        this.opcionesCatalogo = opcionesCatalogo;
        this.descripcionUsuario = descripcionUsuario;
        this.idUsuarioCambio = idUsuarioCambio;
        this.fechaCreacion = new Date();
        this.fechaActualizacion = new Date();
        this.activo = true;
        this.seccionSincronizada = false;
    }

   
    public Long getIdConfiguracionCatalogo() { return idConfiguracionCatalogo; }
    public void setIdConfiguracionCatalogo(Long idConfiguracionCatalogo) { this.idConfiguracionCatalogo = idConfiguracionCatalogo; }

    public Proyecto getProyecto() { return proyecto; }
    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }

    public Catalogos getCatalogos() { return catalogos; }
    public void setCatalogo(Catalogos catalogos) { this.catalogos = catalogos; }

    public OpcionesCatalogo getOpcionCatalogo() { return opcionesCatalogo; }
    public void setOpcionesCatalogo(OpcionesCatalogo opcionesCatalogo) { this.opcionesCatalogo = opcionesCatalogo; }

    public String getDescripcionUsuario() { return descripcionUsuario; }
    public void setDescripcionUsuario(String descripcionUsuario) { this.descripcionUsuario = descripcionUsuario; }

    public long getIdUsuarioCambio() { return idUsuarioCambio; }
    public void setIdUsuarioCambio(long idUsuarioCambio) { this.idUsuarioCambio = idUsuarioCambio; }

    public Date getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(Date fechaCreacion) { this.fechaCreacion = fechaCreacion; }

    public Date getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(Date fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    public Boolean getSeccionSincronizada() { return seccionSincronizada; }
    public void setSeccionSincronizada(Boolean seccionSincronizada) { this.seccionSincronizada = seccionSincronizada; }
}
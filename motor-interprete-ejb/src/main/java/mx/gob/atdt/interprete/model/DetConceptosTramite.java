package mx.gob.atdt.interprete.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "det_conceptos_tramite", schema = "motor_interprete")

@NamedQueries({

    @NamedQuery(
            name = "DetConceptosTramite.findById",
            query = "SELECT new mx.gob.atdt.interprete.dto.DetConceptosTramiteDTO( "
                  + "c.idConceptoTramite, t.idTramiteLineaCaptura, "
                  + "c.secuencia, c.clave, "
                  + "c.agrupador, ta.idTipoAgrupador, cp.idPeriodicidad, p.idPeriodo, "
                  + "COALESCE(e.idEjercicio, 0), "
                  + "c.claveContable, c.importe, "
                  + "c.idUsuarioRegistro, c.fechaCreacion, c.fechaActualizacion, "
                  + "c.activo, c.seccionSincronizada) "
                  + "FROM DetConceptosTramite c "
                  + "LEFT JOIN c.detTramitesLineaCaptura t "
                  + "LEFT JOIN c.tipoAgrupador ta "
                  + "LEFT JOIN c.catPeriodicidad cp "
                  + "LEFT JOIN c.periodo p "
                  + "LEFT JOIN c.ejercicio e "
                  + "WHERE c.idConceptoTramite = :idConceptoTramite"),

    @NamedQuery(
            name = "DetConceptosTramite.findByTramite",
            query = "SELECT new mx.gob.atdt.interprete.dto.DetConceptosTramiteDTO( "
                  + "c.idConceptoTramite, t.idTramiteLineaCaptura, "
                  + "c.secuencia, c.clave, "
                  + "c.agrupador, ta.idTipoAgrupador, cp.idPeriodicidad, p.idPeriodo, "
                  + "COALESCE(e.idEjercicio, 0), "
                  + "c.claveContable, c.importe, "
                  + "c.idUsuarioRegistro, c.fechaCreacion, c.fechaActualizacion, "
                  + "c.activo, c.seccionSincronizada) "
                  + "FROM DetConceptosTramite c "
                  + "LEFT JOIN c.detTramitesLineaCaptura t "
                  + "LEFT JOIN c.tipoAgrupador ta "
                  + "LEFT JOIN c.catPeriodicidad cp "              
                  + "LEFT JOIN c.periodo p "
                  + "LEFT JOIN c.ejercicio e "
                  + "WHERE t.idTramiteLineaCaptura = :idTramiteLineaCaptura "
                  + "AND c.activo = true "
                  + "ORDER BY c.secuencia" ),

    
    @NamedQuery(
        name = "DetConceptosTramite.findByIdConceptoTramite",
        query = "SELECT new mx.gob.atdt.interprete.dto.DetConceptosTramiteDTO( "
              + "c.idConceptoTramite, t.idTramiteLineaCaptura, "
              + "c.secuencia, c.clave, "
              + "c.agrupador, ta.idTipoAgrupador, cp.idPeriodicidad, "
              + "p.idPeriodo, COALESCE(e.idEjercicio, 0), c.claveContable, c.importe, "
              + "c.idUsuarioRegistro, c.fechaCreacion, c.fechaActualizacion, "
              + "c.activo, c.seccionSincronizada) "
              + "FROM DetConceptosTramite c "
              + "LEFT JOIN c.detTramitesLineaCaptura t "
              + "LEFT JOIN c.tipoAgrupador ta "
              + "LEFT JOIN c.catPeriodicidad cp "
              + "LEFT JOIN c.periodo p "
              + "LEFT JOIN c.ejercicio e "
              + "WHERE c.idConceptoTramite = :idConceptoTramite "
              + "AND c.activo = true "),
    
    @NamedQuery(
            name = "DetConceptosTramite.findByIdTramiteLineaDeCaptura",
            query = "SELECT new mx.gob.atdt.interprete.dto.DetConceptosTramiteDTO( "
                    + "c.idConceptoTramite, t.idTramiteLineaCaptura, "
                    + "c.secuencia, c.clave, "
                    + "c.agrupador, "
                    + "ta.idTipoAgrupador, ta.tipoAgrupador, ta.descripcion, "
                    + "cp.idPeriodicidad, cp.clave, cp.descripcion, "
                    + "p.idPeriodo, p.clave, p.descripcion, "
                    + "COALESCE(e.idEjercicio, 0), COALESCE(e.ejercicio, 0), "
                    + "c.claveContable, c.importe, "
                    + "c.idUsuarioRegistro, c.fechaCreacion, c.fechaActualizacion, "
                    + "c.activo, c.seccionSincronizada) "
                    + "FROM DetConceptosTramite c "
                    + "LEFT JOIN c.detTramitesLineaCaptura t "
                    + "LEFT JOIN c.tipoAgrupador ta "
                    + "LEFT JOIN c.catPeriodicidad cp "
                    + "LEFT JOIN c.periodo p "
                    + "LEFT JOIN c.ejercicio e "
                    + "WHERE t.idTramiteLineaCaptura = :idTramiteLineaCaptura "
                    + "AND c.activo = true "
                    + "ORDER BY c.secuencia")
 })
public class DetConceptosTramite implements java.io.Serializable {
	
	private static final long serialVersionUID = -8130925765907743935L;
	
	private long idConceptoTramite;
	private DetTramitesLineaCaptura detTramitesLineaCaptura;
	private int secuencia;
	private int clave;
	private int agrupador;
	private CatTipoAgrupador tipoAgrupador;
	private CatPeriodicidad catPeriodicidad;
	private CatPeriodo periodo;
	private CatEjercicio ejercicio;
	private int claveContable;
	private long importe;
	private long idUsuarioRegistro;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;

	public DetConceptosTramite() {
	}

	@SuppressWarnings({"java:S107"})
	public DetConceptosTramite(long idConceptoTramite, DetTramitesLineaCaptura detTramitesLineaCaptura, int secuencia,
			int clave, int agrupador, CatTipoAgrupador tipoAgrupador, CatPeriodo periodo, CatEjercicio ejercicio, int claveContable, long importe,
			long idUsuarioRegistro, Date fechaCreacion, Date fechaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idConceptoTramite = idConceptoTramite;
		this.detTramitesLineaCaptura = detTramitesLineaCaptura;
		this.secuencia = secuencia;
		this.clave = clave;
		this.agrupador = agrupador;
		this.tipoAgrupador = tipoAgrupador;
		this.periodo = periodo;
		this.ejercicio = ejercicio;
		this.claveContable = claveContable;
		this.importe = importe;
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	@Id
	@Column(name = "id_concepto_tramite", unique = true, nullable = false)
	public long getIdConceptoTramite() {
		return this.idConceptoTramite;
	}

	public void setIdConceptoTramite(long idConceptoTramite) {
		this.idConceptoTramite = idConceptoTramite;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tramite_linea_captura", nullable = false)
	public DetTramitesLineaCaptura getDetTramitesLineaCaptura() {
		return this.detTramitesLineaCaptura;
	}

	public void setDetTramitesLineaCaptura(DetTramitesLineaCaptura detTramitesLineaCaptura) {
		this.detTramitesLineaCaptura = detTramitesLineaCaptura;
	}

	@Column(name = "secuencia", nullable = false)
	public int getSecuencia() {
		return this.secuencia;
	}

	public void setSecuencia(int secuencia) {
		this.secuencia = secuencia;
	}

	@Column(name = "clave", nullable = false)
	public int getClave() {
		return this.clave;
	}

	public void setClave(int clave) {
		this.clave = clave;
	}

	@Column(name = "agrupador", nullable = false)
	public int getAgrupador() {
		return this.agrupador;
	}

	public void setAgrupador(int agrupador) {
		this.agrupador = agrupador;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_agrupador", nullable = false)
	public CatTipoAgrupador getTipoAgrupador() {
		return this.tipoAgrupador;
	}

	public void setTipoAgrupador(CatTipoAgrupador tipoAgrupador) {
		this.tipoAgrupador = tipoAgrupador;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_periodicidad", nullable = false)
	public CatPeriodicidad getCatPeriodicidad() {
		return this.catPeriodicidad;
	}

	public void setCatPeriodicidad(CatPeriodicidad catPeriodicidad) {
		this.catPeriodicidad = catPeriodicidad;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_periodo", nullable = false)
	public CatPeriodo getPeriodo() {
		return this.periodo;
	}

	public void setPeriodo(CatPeriodo periodo) {
		this.periodo = periodo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ejercicio")
	public CatEjercicio getEjercicio() {
		return this.ejercicio;
	}

	public void setEjercicio(CatEjercicio ejercicio) {
		this.ejercicio = ejercicio;
	}

	@Column(name = "clave_contable", nullable = false)
	public int getClaveContable() {
		return this.claveContable;
	}

	public void setClaveContable(int claveContable) {
		this.claveContable = claveContable;
	}

	@Column(name = "importe", nullable = false, precision = 14, scale = 0)
	public long getImporte() {
		return this.importe;
	}

	public void setImporte(long importe) {
		this.importe = importe;
	}

	@Column(name = "id_usuario_registro", nullable = false)
	public long getIdUsuarioRegistro() {
		return this.idUsuarioRegistro;
	}
 
	public void setIdUsuarioRegistro(long idUsuarioRegistro) {
		this.idUsuarioRegistro = idUsuarioRegistro;
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
	@Column(name = "fecha_actualizacion", nullable = false, length = 29)
	public Date getFechaActualizacion() {
		return this.fechaActualizacion;
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

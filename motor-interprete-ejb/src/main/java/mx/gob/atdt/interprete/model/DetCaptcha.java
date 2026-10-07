package mx.gob.atdt.interprete.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
 
@Entity
@Table(name = "det_captcha", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetCaptcha.findByIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetCaptchaDTO("
			+ " d.idDetalleCaptcha, p.idProyecto, d.llavePublica, d.llavePrivada, d.fechaCreacion, "		
			+ " d.fechaUltimaActualizacion, d.activo, d.seccionSincronizada) " 
			+ " FROM DetCaptcha d "
			+ "	JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto "),	
	@NamedQuery(name = "DetCaptcha.existeDetalleActivoIdProyecto", 
			query = "SELECT new mx.gob.atdt.interprete.dto.DetCaptchaDTO("
			+ " p.idProyecto, d.idDetalleCaptcha ) " 
			+ " FROM DetCaptcha d "
			+ "	JOIN d.proyecto p "
			+ " WHERE p.idProyecto = :idProyecto"
			+ " AND d.activo = :activo ")
	
})
public class DetCaptcha implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1865984434071958382L;
	
	private Long idDetalleCaptcha;
	private Proyecto proyecto;
	private String llavePublica;
	private String llavePrivada;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;

	public DetCaptcha() {
	}

	public DetCaptcha(Long idDetalleCaptcha, Proyecto proyecto, String llavePublica, String llavePrivada,
			Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idDetalleCaptcha = idDetalleCaptcha;
		this.proyecto = proyecto;
		this.llavePublica = llavePublica;
		this.llavePrivada = llavePrivada;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	@Id
	@Column(name = "id_detalle_captcha", unique = true, nullable = false)
	public Long getIdDetalleCaptcha() {
		return this.idDetalleCaptcha;
	}

	public void setIdDetalleCaptcha(Long idDetalleCaptcha) {
		this.idDetalleCaptcha = idDetalleCaptcha;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_proyecto", nullable = false)
	public Proyecto getProyecto() {
		return this.proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	@Column(name = "llave_publica", nullable = false, length = 60)
	public String getLlavePublica() {
		return this.llavePublica;
	}

	public void setLlavePublica(String llavePublica) {
		this.llavePublica = llavePublica;
	}

	@Column(name = "llave_privada", nullable = false, length = 60)
	public String getLlavePrivada() {
		return this.llavePrivada;
	}

	public void setLlavePrivada(String llavePrivada) {
		this.llavePrivada = llavePrivada;
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

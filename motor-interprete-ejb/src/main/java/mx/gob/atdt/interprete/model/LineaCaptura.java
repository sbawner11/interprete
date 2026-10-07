package mx.gob.atdt.interprete.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "linea_captura", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "LineaCaptura.findLineaCapturaVigenteByIdTramite", 
		query = "SELECT new mx.gob.atdt.interprete.dto.LineaCapturaDTO("
				+ "    lc.idLineaCaptura,"
				+ "    lc.tramites.idTramite,"
				+ "    lc.respuestaServicioLc,"
				+ "    lc.respuestaServicioEstatus,"
				+ "    lc.fechaVigencia,"
				+ "    lc.monto,"
				+ "    lc.fechaPagoLc,"
				+ "    lc.fechaCreacion,"
				+ "    lc.lineCaptura,"
				+ "    lc.rutaDocumentoLineaCaptura,"
				+ "    lc.solicitudLineaCaptura,"
				+ "    lc.catEstatusLineaCaptura.idEstatusLineaCaptura, "
				+ "    lc.catEstatusLineaCaptura.descripcion "
				+ ") "
				+ "FROM LineaCaptura lc "
				+ "WHERE (lc.fechaVigencia IS NULL or lc.fechaVigencia >= CURRENT_TIMESTAMP()) "
				+ "AND lc.tramites.idTramite = :idTramite "
				+ "ORDER BY lc.idLineaCaptura DESC  "),
	@NamedQuery(name = "LineaCaptura.findLineaCapturaByIdTramiteOrderByIdDesc",
	query = "SELECT new mx.gob.atdt.interprete.dto.LineaCapturaDTO("
			+ "    lc.idLineaCaptura,"
			+ "    lc.tramites.idTramite,"
			+ "    lc.respuestaServicioLc,"
			+ "    lc.respuestaServicioEstatus,"
			+ "    lc.fechaVigencia,"
			+ "    lc.monto,"
			+ "    lc.fechaPagoLc,"
			+ "    lc.fechaCreacion,"
			+ "    lc.lineCaptura,"
			+ "    lc.rutaDocumentoLineaCaptura,"
			+ "    lc.solicitudLineaCaptura,"
			+ "    lc.catEstatusLineaCaptura.idEstatusLineaCaptura, "
			+ "    lc.catEstatusLineaCaptura.descripcion "
			+ ") "
			+ "FROM LineaCaptura lc "
			+ "WHERE lc.tramites.idTramite = :idTramite "
			+ "ORDER BY lc.idLineaCaptura DESC "),
	@NamedQuery(name = "LineaCaptura.findLastLineaCaptura",
		query = "SELECT new mx.gob.atdt.interprete.dto.LineaCapturaDTO("
			+ "    lc.idLineaCaptura,"
			+ "    lc.tramites.idTramite,"
			+ "    lc.respuestaServicioLc,"
			+ "    lc.respuestaServicioEstatus,"
			+ "    lc.fechaVigencia,"
			+ "    lc.monto,"
			+ "    lc.fechaPagoLc,"
			+ "    lc.fechaCreacion,"
			+ "    lc.lineCaptura,"
			+ "    lc.rutaDocumentoLineaCaptura,"
			+ "    lc.solicitudLineaCaptura,"
			+ "    lc.catEstatusLineaCaptura.idEstatusLineaCaptura, "
			+ "    lc.catEstatusLineaCaptura.descripcion "
			+ ") "
			+ "FROM LineaCaptura lc "
			+ "ORDER BY lc.idLineaCaptura DESC "),
	@NamedQuery(name = "LineaCaptura.findLineaCapuraByIdTramiteMax",
		query = "SELECT new mx.gob.atdt.interprete.dto.LineaCapturaDTO("
			+ "    lc.idLineaCaptura,"
			+ "    lc.tramites.idTramite,"
			+ "    lc.respuestaServicioLc,"
			+ "    lc.respuestaServicioEstatus,"
			+ "    lc.fechaVigencia,"
			+ "    lc.monto,"
			+ "    lc.fechaPagoLc,"
			+ "    lc.fechaCreacion,"
			+ "    lc.lineCaptura,"
			+ "    lc.rutaDocumentoLineaCaptura,"
			+ "    lc.solicitudLineaCaptura,"
			+ "    lc.catEstatusLineaCaptura.idEstatusLineaCaptura, "
			+ "    lc.catEstatusLineaCaptura.descripcion "
			+ ") "
			+ "FROM LineaCaptura lc "
			+ "WHERE lc.tramites.idTramite = :idTramite "
			+ "and lc.idLineaCaptura = (select max(lc1.idLineaCaptura) from LineaCaptura lc1 "
			+ "where lc1.tramites.idTramite = lc.tramites.idTramite) ")
})
public class LineaCaptura implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_linea_captura", unique = true, nullable = false)
	private long idLineaCaptura;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tramite", nullable = false)
	private Tramites tramites;
	
	@Column(name = "respuesta_servicio_lc", nullable = false)
	private String respuestaServicioLc;
	
	@Column(name = "respuesta_servicio_estatus")
	private String respuestaServicioEstatus;
	
	@Column(name = "linea_captura")
	private String lineCaptura;
	
	@Column(name = "fecha_vigencia")
	private Date fechaVigencia;
	
	@Column(name = "monto")
	private double monto;
	
	@Column(name = "fecha_pago_lc")
	private Date fechaPagoLc;
	
	@Column(name = "fecha_creacion")
	private Date fechaCreacion;
	
	@Column(name = "ruta_documento_linea_captura")
	private String rutaDocumentoLineaCaptura;
	
	@Column(name = "solicitud_linea_captura")
	private String solicitudLineaCaptura;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estatus_linea_captura", referencedColumnName = "id_estatus_linea_captura")
	private CatEstatusLineaCaptura catEstatusLineaCaptura;
	
	

	public LineaCaptura() {
		// Constructor por defecto
 }

	public long getIdLineaCaptura() {
		return idLineaCaptura;
	}

	public void setIdLineaCaptura(long idLineaCaptura) {
		this.idLineaCaptura = idLineaCaptura;
	}

	public Tramites getTramites() {
		return tramites;
	}

	public void setTramites(Tramites tramites) {
		this.tramites = tramites;
	}

	public String getRespuestaServicioLc() {
		return respuestaServicioLc;
	}

	public void setRespuestaServicioLc(String respuestaServicioLc) {
		this.respuestaServicioLc = respuestaServicioLc;
	}

	public String getRespuestaServicioEstatus() {
		return respuestaServicioEstatus;
	}

	public void setRespuestaServicioEstatus(String respuestaServicioEstatus) {
		this.respuestaServicioEstatus = respuestaServicioEstatus;
	}

	public Date getFechaVigencia() {
		return fechaVigencia;
	}

	public void setFechaVigencia(Date fechaVigencia) {
		this.fechaVigencia = fechaVigencia;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Date getFechaPagoLc() {
		return fechaPagoLc;
	}

	public void setFechaPagoLc(Date fechaPagoLc) {
		this.fechaPagoLc = fechaPagoLc;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public CatEstatusLineaCaptura getCatEstatusLineaCaptura() {
		return catEstatusLineaCaptura;
	}

	public void setCatEstatusLineaCaptura(CatEstatusLineaCaptura catEstatusLineaCaptura) {
		this.catEstatusLineaCaptura = catEstatusLineaCaptura;
	}

	public String getLineCaptura() {
		return lineCaptura;
	}

	public void setLineCaptura(String lineCaptura) {
		this.lineCaptura = lineCaptura;
	}

	public String getRutaDocumentoLineaCaptura() {
		return rutaDocumentoLineaCaptura;
	}

	public void setRutaDocumentoLineaCaptura(String rutaDocumentoLineaCaptura) {
		this.rutaDocumentoLineaCaptura = rutaDocumentoLineaCaptura;
	}

	public String getSolicitudLineaCaptura() {
		return solicitudLineaCaptura;
	}

	public void setSolicitudLineaCaptura(String solicitudLineaCaptura) {
		this.solicitudLineaCaptura = solicitudLineaCaptura;
	}
}


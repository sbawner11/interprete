package mx.gob.atdt.interprete.dto;

import java.util.Date;

public class LineaCapturaDTO implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private long idLineaCaptura;
	private long idTramite;
	private String respuestaServicioLc;
	private String respuestaServicioEstatus;
	private Date fechaVigencia;	
	private double monto;
	private Date fechaPagoLc;
	private Date fechaCreacion;
	private CatEstatusLineaCapturaDTO catEstatusLineaCaptura;
	private String lineaCaptura;
	private String rutaDocumentoLineaCaptura;
	private String solicitudLineaCaptura;

	public LineaCapturaDTO(long idLineaCaptura, long idTramite, String respuestaServicioLc,
			String respuestaServicioEstatus, Date fechaVigencia, double monto, Date fechaPagoLc, Date fechaCreacion,
			CatEstatusLineaCapturaDTO catEstatusLineaCaptura, String lineaCaptura,
			String rutaDocumentoLineaCaptura, String solicitudLineaCaptura) {
		super();
		this.idLineaCaptura = idLineaCaptura;
		this.idTramite = idTramite;
		this.respuestaServicioLc = respuestaServicioLc;
		this.respuestaServicioEstatus = respuestaServicioEstatus;
		this.fechaVigencia = fechaVigencia;
		this.monto = monto;
		this.fechaPagoLc = fechaPagoLc;
		this.fechaCreacion = fechaCreacion;
		this.catEstatusLineaCaptura = catEstatusLineaCaptura;
		this.lineaCaptura = lineaCaptura;
		this.rutaDocumentoLineaCaptura = rutaDocumentoLineaCaptura;
		this.solicitudLineaCaptura = solicitudLineaCaptura;
	}

	public LineaCapturaDTO(long idLineaCaptura, long idTramite, String respuestaServicioLc,
			String respuestaServicioEstatus, Date fechaVigencia, double monto, Date fechaPagoLc, Date fechaCreacion,
			String lineaCaptura, String rutaDocumentoLineaCaptura, String solicitudLineaCaptura,
			final Integer idEstatusLineaCaptura, final String descripcionEstatusLineaCaptura) {
		super();
		this.idLineaCaptura = idLineaCaptura;
		this.idTramite = idTramite;
		this.respuestaServicioLc = respuestaServicioLc;
		this.respuestaServicioEstatus = respuestaServicioEstatus;
		this.fechaVigencia = fechaVigencia;
		this.monto = monto;
		this.fechaPagoLc = fechaPagoLc;
		this.fechaCreacion = fechaCreacion;
		this.lineaCaptura = lineaCaptura;
		this.rutaDocumentoLineaCaptura = rutaDocumentoLineaCaptura;
		this.solicitudLineaCaptura = solicitudLineaCaptura;
		this.catEstatusLineaCaptura = new CatEstatusLineaCapturaDTO(idEstatusLineaCaptura, descripcionEstatusLineaCaptura);
	}

	public LineaCapturaDTO() {
		// Constructor por defecto
	}
	
	public LineaCapturaDTO(long idLineaCaptura) {
		this.idLineaCaptura = idLineaCaptura;
	}

	public long getIdLineaCaptura() {
		return idLineaCaptura;
	}

	public void setIdLineaCaptura(long idLineaCaptura) {
		this.idLineaCaptura = idLineaCaptura;
	}

	public long getIdTramite() {
		return idTramite;
	}

	public void setIdTramite(long idTramite) {
		this.idTramite = idTramite;
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

	
	public CatEstatusLineaCapturaDTO getCatEstatusLineaCaptura() {
		return catEstatusLineaCaptura;
	}

	public void setCatEstatusLineaCaptura(CatEstatusLineaCapturaDTO catEstatusLineaCaptura) {
		this.catEstatusLineaCaptura = catEstatusLineaCaptura;
	}

	public String getLineaCaptura() {
		return lineaCaptura;
	}

	public void setLineaCaptura(String lineaCaptura) {
		this.lineaCaptura = lineaCaptura;
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

	@Override
	public String toString() {
		return "LineaCapturaDTO [idLineaCaptura=" + idLineaCaptura + ", idTramite=" + idTramite
				+ ", fechaVigencia=" + fechaVigencia + ", monto=" + monto + ", fechaPagoLc="
				+ fechaPagoLc + ", fechaCreacion=" + fechaCreacion + ", catEstatusLineaCaptura="
				+ catEstatusLineaCaptura + ", lineaCaptura=" + lineaCaptura
				+ ", solicitudLineaCaptura=" + solicitudLineaCaptura +"]";
	}

	
}


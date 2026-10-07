package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;
import java.util.Date;

import mx.gob.atdt.interprete.dto.CatEstatusSolicitudDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;


public class SolicitudLineaCapturaDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private long idSolicitudLineaCaptura;
	private CatEstatusSolicitudDTO catEstatusSolicitud;
	private TramiteDTO tramite;
	private String solicitudLineaCaptura;
	private String requestServicioLc;
	private String respuestaServicioLc;
	private Date fechaCreacion;
    
    public SolicitudLineaCapturaDTO() {
    }
    
    public SolicitudLineaCapturaDTO(Long idSolicitudLineaCaptura, Integer idEstatusSolicitud, 
                                    Long idTramite,
                                    String solicitudLineaCaptura, String requestServicioLc, 
                                    String respuestaServicioLc, Date fechaCreacion) {
        this.idSolicitudLineaCaptura = idSolicitudLineaCaptura;
        this.catEstatusSolicitud = new CatEstatusSolicitudDTO(idEstatusSolicitud, null);
        this.tramite = new TramiteDTO(idTramite);
        this.solicitudLineaCaptura = solicitudLineaCaptura;
        this.requestServicioLc = requestServicioLc;
        this.respuestaServicioLc = respuestaServicioLc;
        this.fechaCreacion = fechaCreacion;
    }

	public long getIdSolicitudLineaCaptura() {
		return idSolicitudLineaCaptura;
	}

	public void setIdSolicitudLineaCaptura(long idSolicitudLineaCaptura) {
		this.idSolicitudLineaCaptura = idSolicitudLineaCaptura;
	}

	public CatEstatusSolicitudDTO getCatEstatusSolicitud() {
		return catEstatusSolicitud;
	}

	public void setCatEstatusSolicitud(CatEstatusSolicitudDTO catEstatusSolicitud) {
		this.catEstatusSolicitud = catEstatusSolicitud;
	}

	public TramiteDTO getTramite() {
		return tramite;
	}

	public void setTramite(TramiteDTO tramite) {
		this.tramite = tramite;
	}

	public String getSolicitudLineaCaptura() {
		return solicitudLineaCaptura;
	}

	public void setSolicitudLineaCaptura(String solicitudLineaCaptura) {
		this.solicitudLineaCaptura = solicitudLineaCaptura;
	}

	public String getRequestServicioLc() {
		return requestServicioLc;
	}

	public void setRequestServicioLc(String requestServicioLc) {
		this.requestServicioLc = requestServicioLc;
	}

	public String getRespuestaServicioLc() {
		return respuestaServicioLc;
	}

	public void setRespuestaServicioLc(String respuestaServicioLc) {
		this.respuestaServicioLc = respuestaServicioLc;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
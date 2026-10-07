package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetTransaccionConceptoDTO implements Serializable {

    private static final long serialVersionUID = 2453966994167435426L;
    
	private long idTransaccionConcepto;
    private DetConceptosTramiteDTO detConceptosTramiteDTO;
    private int clave;
    private long valor;
    private boolean actualizacion;
    private boolean recargo;
    private boolean multa;
    private long idUsuarioRegistro;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    private boolean activo;
    private boolean seccionSincronizada;

    /* CONSTRUCTORES */

	/**
	 * 
	 */
	public DetTransaccionConceptoDTO() {
	}

	/**
	 * @param idTransaccionConcepto
	 */
	public DetTransaccionConceptoDTO(long idTransaccionConcepto) {
        this.idTransaccionConcepto = idTransaccionConcepto;
        this.detConceptosTramiteDTO = new DetConceptosTramiteDTO();
    }

	/**
	 * @param idTransaccionConcepto
	 * @param detConceptosTramiteDTO
	 * @param clave
	 * @param valor
	 * @param actualizacion
	 * @param recargo
	 * @param multa
	 * @param fechaCreacion
	 * @param fechaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	public DetTransaccionConceptoDTO(long idTransaccionConcepto, DetConceptosTramiteDTO detConceptosTramiteDTO,
			int clave, long valor, boolean actualizacion, boolean recargo, boolean multa, long idUsuarioRegistro, Date fechaCreacion,
			Date fechaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idTransaccionConcepto = idTransaccionConcepto;
		this.detConceptosTramiteDTO = detConceptosTramiteDTO;
		this.clave = clave;
		this.valor = valor;
		this.actualizacion = actualizacion;
		this.recargo = recargo;
		this.multa = multa;
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

    @SuppressWarnings({"java:S107"})
    public DetTransaccionConceptoDTO(
            long idTransaccionConcepto, long idLineacConcepto,
            int clave, long valor,
            boolean actualizacion, boolean recargo, boolean multa, long idUsuarioRegistro,
            Date fechaCreacion, Date fechaActualizacion,
            boolean activo, boolean seccionSincronizada) {
        this.idTransaccionConcepto = idTransaccionConcepto;
        this.detConceptosTramiteDTO = new DetConceptosTramiteDTO(idLineacConcepto);
        this.clave = clave;
        this.valor = valor;
        this.actualizacion = actualizacion;
        this.recargo = recargo;
        this.multa = multa;
        this.idUsuarioRegistro = idUsuarioRegistro;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.activo = activo;
        this.seccionSincronizada = seccionSincronizada;
    }

    /* GETTERS / SETTERS */

    /**
	 * @return the idTransaccionConcepto
	 */
	public long getIdTransaccionConcepto() {
		return idTransaccionConcepto;
	}

	/**
	 * @param idTransaccionConcepto the idTransaccionConcepto to set
	 */
	public void setIdTransaccionConcepto(long idTransaccionConcepto) {
		this.idTransaccionConcepto = idTransaccionConcepto;
	}

	/**
	 * @return the detConceptosTramiteDTO
	 */
	public DetConceptosTramiteDTO getDetConceptosTramiteDTO() {
		return detConceptosTramiteDTO;
	}

	/**
	 * @param detConceptosTramiteDTO the detConceptosTramiteDTO to set
	 */
	public void setDetConceptosTramiteDTO(DetConceptosTramiteDTO detConceptosTramiteDTO) {
		this.detConceptosTramiteDTO = detConceptosTramiteDTO;
	}

	/**
	 * @return the clave
	 */
	public int getClave() {
		return clave;
	}

	/**
	 * @param clave the clave to set
	 */
	public void setClave(int clave) {
		this.clave = clave;
	}

	/**
	 * @return the valor
	 */
	public long getValor() {
		return valor;
	}

	/**
	 * @param valor the valor to set
	 */
	public void setValor(long valor) {
		this.valor = valor;
	}

	/**
	 * @return the actualizacion
	 */
	public boolean isActualizacion() {
		return actualizacion;
	}

	/**
	 * @param actualizacion the actualizacion to set
	 */
	public void setActualizacion(boolean actualizacion) {
		this.actualizacion = actualizacion;
	}

	/**
	 * @return the recargo
	 */
	public boolean isRecargo() {
		return recargo;
	}

	/**
	 * @param recargo the recargo to set
	 */
	public void setRecargo(boolean recargo) {
		this.recargo = recargo;
	}

	/**
	 * @return the multa
	 */
	public boolean isMulta() {
		return multa;
	}

	/**
	 * @param multa the multa to set
	 */
	public void setMulta(boolean multa) {
		this.multa = multa;
	}
	
	/**
	 * @return the idUsuarioRegistro
	 */
	public long getIdUsuarioRegistro() {
		return idUsuarioRegistro;
	}

	/**
	 * @param idUsuarioRegistro the idUsuarioRegistro to set
	 */
	public void setIdUsuarioRegistro(long idUsuarioRegistro) {
		this.idUsuarioRegistro = idUsuarioRegistro;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}

	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the seccionSincronizada
	 */
	public boolean isSeccionSincronizada() {
		return seccionSincronizada;
	}

	/**
	 * @param seccionSincronizada the seccionSincronizada to set
	 */
	public void setSeccionSincronizada(boolean seccionSincronizada) {
		this.seccionSincronizada = seccionSincronizada;
	}

   
}
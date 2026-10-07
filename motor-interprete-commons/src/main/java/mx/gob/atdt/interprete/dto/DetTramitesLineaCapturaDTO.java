package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetTramitesLineaCapturaDTO implements Serializable {
		
	private static final long serialVersionUID = 6873155370811282142L;
	
	private long idTramiteLineaCaptura;
	private DetLineaCapturaDTO detLineaCapturaDTO;
	private String homoclave;
	private String variante;
	private String descripcion;
	private long importe;
	private int numeroConceptos;
	private long idUsuarioRegistro;
	private Date fechaCreacion;
	private Date fechaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	
	/**
	 * 
	 */
	public DetTramitesLineaCapturaDTO() {
	}
	
    /*
	 * @param idTramiteLineaCaptura
	 */
	public DetTramitesLineaCapturaDTO(long idTramiteLineaCaptura) {
		this.idTramiteLineaCaptura = idTramiteLineaCaptura;
		this.detLineaCapturaDTO = new DetLineaCapturaDTO();
	}

	/**
	 * @param idTramiteLineaCaptura
	 * @param detLineaCapturaDTO
	 * @param homoclave
	 * @param variante
	 * @param descripcion
	 * @param importe
	 * @param numeroConceptos
	 * @param idUsuarioRegistro
	 * @param fechaCreacion
	 * @param fechaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	@SuppressWarnings({"java:S107"})
	public DetTramitesLineaCapturaDTO(long idTramiteLineaCaptura, DetLineaCapturaDTO detLineaCapturaDTO,
			String homoclave, String variante, String descripcion, long importe, int numeroConceptos,
			int idUsuarioRegistro, Date fechaCreacion, Date fechaActualizacion, boolean activo, boolean seccionSincronizada) {
		this.idTramiteLineaCaptura = idTramiteLineaCaptura;
		this.detLineaCapturaDTO = detLineaCapturaDTO;
		this.homoclave = homoclave;
		this.variante = variante;
		this.descripcion = descripcion;
		this.importe = importe;
		this.numeroConceptos = numeroConceptos;
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}
	
	/**
	 * Constructor utilizado por la NamedQuery DetTramitesLineaCaptura.findByLineaCaptura
	 * 
	 * @param idTramiteLineaCaptura
	 * @param idLineaCaptura
	 * @param homoclave
	 * @param variante
	 * @param descripcion
	 * @param importe
	 * @param numeroConceptos
	 * @param idUsuarioRegistro
	 * @param fechaCreacion
	 * @param fechaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	@SuppressWarnings({"java:S107"})
	public DetTramitesLineaCapturaDTO(long idTramiteLineaCaptura, long idLineaCaptura, 
			String homoclave, String variante, String descripcion,
			long importe,  int numeroConceptos,
			long idUsuarioRegistro, Date fechaCreacion, Date fechaActualizacion,
			boolean activo, boolean seccionSincronizada) {
		this.idTramiteLineaCaptura = idTramiteLineaCaptura;
		this.detLineaCapturaDTO = new DetLineaCapturaDTO(idLineaCaptura);
		this.homoclave = homoclave;
		this.variante = variante;
		this.descripcion = descripcion;
		this.importe = importe;
		this.numeroConceptos = numeroConceptos;		
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.fechaCreacion = fechaCreacion;
		this.fechaActualizacion = fechaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;
	}

	// Getters y setters

	/**
	 * @return the idTramiteLineaCaptura
	 */
	public long getIdTramiteLineaCaptura() {
		return idTramiteLineaCaptura;
	}

	/**
	 * @param idTramiteLineaCaptura the idTramiteLineaCaptura to set
	 */
	public void setIdTramiteLineaCaptura(long idTramiteLineaCaptura) {
		this.idTramiteLineaCaptura = idTramiteLineaCaptura;
	}

	/**
	 * @return the detLineaCapturaDTO
	 */
	public DetLineaCapturaDTO getDetLineaCapturaDTO() {
		return detLineaCapturaDTO;
	}

	/**
	 * @param detLineaCapturaDTO the detLineaCapturaDTO to set
	 */
	public void setDetLineaCapturaDTO(DetLineaCapturaDTO detLineaCapturaDTO) {
		this.detLineaCapturaDTO = detLineaCapturaDTO;
	}

	/**
	 * @return the homoclave
	 */
	public String getHomoclave() {
		return homoclave;
	}

	/**
	 * @param homoclave the homoclave to set
	 */
	public void setHomoclave(String homoclave) {
		this.homoclave = homoclave;
	}

	/**
	 * @return the variante
	 */
	public String getVariante() {
		return variante;
	}

	/**
	 * @param variante the variante to set
	 */
	public void setVariante(String variante) {
		this.variante = variante;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the importe
	 */
	public long getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(long importe) {
		this.importe = importe;
	}

	/**
	 * @return the numeroConceptos
	 */
	public int getNumeroConceptos() {
		return numeroConceptos;
	}

	/**
	 * @param numeroConceptos the numeroConceptos to set
	 */
	public void setNumeroConceptos(int numeroConceptos) {
		this.numeroConceptos = numeroConceptos;
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
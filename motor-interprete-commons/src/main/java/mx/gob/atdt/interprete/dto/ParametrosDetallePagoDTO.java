package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;


public class ParametrosDetallePagoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4565051505574393221L;
	
	private Long idParametro;
	private DetPagoDTO detPagoDTO;
	private String nombreParametro;
	private String valorParametro;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	
	/**
	 * 
	 */
	public ParametrosDetallePagoDTO() {
		
	}

	public ParametrosDetallePagoDTO(Long idParametro) {
		this.idParametro=idParametro;
	}
	
	public ParametrosDetallePagoDTO(Long idParametro, DetPagoDTO detPagoDTO, String nombreParametro,
			String valorParametro, Date fechaCreacion, Date fechaUltimaActualizacion) {
		this.idParametro = idParametro;
		this.detPagoDTO = detPagoDTO;
		this.nombreParametro = nombreParametro;
		this.valorParametro = valorParametro;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	/**
	 * Constructor utilizado por la NamedQuery ParametrosDetallePago.findParametrosActivosByIdDetallePago
	 * @param idParametro
	 * @param idDetallePago
	 * @param nombreParametro
	 * @param valorParametro
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 */
	public ParametrosDetallePagoDTO(Long idParametro, Long idDetallePago, String nombreParametro,
			String valorParametro, Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo) {
		this.idParametro = idParametro;
		this.detPagoDTO = new DetPagoDTO(idDetallePago);
		this.nombreParametro = nombreParametro;
		this.valorParametro = valorParametro;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
	}	
	
	/**
	 * @return the idParametro
	 */
	public Long getIdParametro() {
		return idParametro;
	}

	/**
	 * @param idParametro the idParametro to set
	 */
	public void setIdParametro(Long idParametro) {
		this.idParametro = idParametro;
	}


	public DetPagoDTO getDetPagoDTO() {
		return detPagoDTO;
	}

	public void setDetPagoDTO(DetPagoDTO detPagoDTO) {
		this.detPagoDTO = detPagoDTO;
	}

	/**
	 * @return the nombreParametro
	 */
	public String getNombreParametro() {
		return nombreParametro;
	}

	/**
	 * @param nombreParametro the nombreParametro to set
	 */
	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}

	/**
	 * @return the valorParametro
	 */
	public String getValorParametro() {
		return valorParametro;
	}

	/**
	 * @param valorParametro the valorParametro to set
	 */
	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
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
	 * @return the fechaUltimaActualizacion
	 */
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	/**
	 * @param fechaUltimaActualizacion the fechaUltimaActualizacion to set
	 */
	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombreParametro == null) ? 0 : nombreParametro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ParametrosDetallePagoDTO other = (ParametrosDetallePagoDTO) obj;
		if (nombreParametro == null) {
			if (other.nombreParametro != null)
				return false;
		} else if (!nombreParametro.equals(other.nombreParametro))
			return false;
		return true;
	}
	
}

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
@Table(name = "parametros_detalle_pago", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "ParametrosDetallePago.findParametrosActivosByIdDetallePago", 
			query = "SELECT new mx.gob.atdt.interprete.dto.ParametrosDetallePagoDTO("
					+ " pdp.idParametro, dp.idDetallePago, pdp.nombreParametro, "
					+ " pdp.valorParametro, pdp.fechaCreacion, "
					+ " pdp.fechaUltimaActualizacion, pdp.activo ) "
					+ " FROM ParametrosDetallePago pdp "
					+ "	JOIN pdp.detPago dp "
					+ " WHERE dp.idDetallePago = :idDetallePago "
					+ " AND pdp.activo = true ")	
})
public class ParametrosDetallePago implements java.io.Serializable {


	private static final long serialVersionUID = 5503003907230482526L;
	
	private Long idParametro;
	private DetPago detPago;
	private String nombreParametro;
	private String valorParametro;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;

	public ParametrosDetallePago() {
	}

	public ParametrosDetallePago(Long idParametro, DetPago detPago, String nombreParametro,
			String valorParametro, Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo) {
		this.idParametro = idParametro;
		this.detPago = detPago;
		this.nombreParametro = nombreParametro;
		this.valorParametro = valorParametro;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_parametro", unique = true, nullable = false)
	public Long getIdParametro() {
		return this.idParametro;
	}

	public void setIdParametro(Long idParametro) {
		this.idParametro = idParametro;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_detalle_pago", nullable = false)
	public DetPago getDetPago() {
		return this.detPago;
	}

	public void setDetPago(DetPago detPago) {
		this.detPago = detPago;
	}

	@Column(name = "nombre_parametro", nullable = false, length = 60)
	public String getNombreParametro() {
		return this.nombreParametro;
	}

	public void setNombreParametro(String nombreParametro) {
		this.nombreParametro = nombreParametro;
	}

	@Column(name = "valor_parametro", nullable = false, length = 60)
	public String getValorParametro() {
		return this.valorParametro;
	}

	public void setValorParametro(String valorParametro) {
		this.valorParametro = valorParametro;
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
}

package mx.gob.atdt.interprete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "det_tramite_servicio", schema = "motor_interprete")
@NamedQueries({
		@NamedQuery(name = "DetTramiteServicio.findByIdDetalleHome", query = "SELECT new mx.gob.atdt.interprete.dto.DetTramiteServicioDTO("
				+ " dts.idDetalleTramite, dh.idDetalleHome, dts.habilitaCostoTramite, "
				+ " dts.descripcionCostoTramite, dts.habilitaExcepcionTramite) "
				+ " FROM DetTramiteServicio dts " + "	JOIN dts.detHome dh "
				+ " WHERE dh.idDetalleHome = :idDetalleHome") })
public class DetTramiteServicio implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3928896504268767009L;

	private Long idDetalleTramite;
	private DetHome detHome;
	private boolean habilitaCostoTramite;
	private String descripcionCostoTramite;
	private boolean habilitaExcepcionTramite;

	public DetTramiteServicio() {
	}

	public DetTramiteServicio(Long idDetalleTramite, DetHome detHome, 
			boolean habilitaCostoTramite, boolean habilitaExcepcionTramite) {
		this.idDetalleTramite = idDetalleTramite;
		this.detHome = detHome;
		this.habilitaCostoTramite = habilitaCostoTramite;
		this.habilitaExcepcionTramite = habilitaExcepcionTramite;
	}

	public DetTramiteServicio(Long idDetalleTramite, DetHome detHome,
			boolean habilitaCostoTramite, String descripcionCostoTramite, boolean habilitaExcepcionTramite) {
		this.idDetalleTramite = idDetalleTramite;
		this.detHome = detHome;
		this.habilitaCostoTramite = habilitaCostoTramite;
		this.descripcionCostoTramite = descripcionCostoTramite;
		this.habilitaExcepcionTramite = habilitaExcepcionTramite;
	}

	@Id
	@Column(name = "id_detalle_tramite", unique = true, nullable = false)
	public Long getIdDetalleTramite() {
		return this.idDetalleTramite;
	}

	public void setIdDetalleTramite(Long idDetalleTramite) {
		this.idDetalleTramite = idDetalleTramite;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_detalle_home", nullable = false)
	public DetHome getDetHome() {
		return this.detHome;
	}

	public void setDetHome(DetHome detHome) {
		this.detHome = detHome;
	}

	@Column(name = "habilita_costo_tramite", nullable = false)
	public boolean isHabilitaCostoTramite() {
		return this.habilitaCostoTramite;
	}

	public void setHabilitaCostoTramite(boolean habilitaCostoTramite) {
		this.habilitaCostoTramite = habilitaCostoTramite;
	}

	@Column(name = "descripcion_costo_tramite", length = 60)
	public String getDescripcionCostoTramite() {
		return this.descripcionCostoTramite;
	}

	public void setDescripcionCostoTramite(String descripcionCostoTramite) {
		this.descripcionCostoTramite = descripcionCostoTramite;
	}

	@Column(name = "habilita_excepcion_tramite", nullable = false)
	public boolean isHabilitaExcepcionTramite() {
		return this.habilitaExcepcionTramite;
	}

	public void setHabilitaExcepcionTramite(boolean habilitaExcepcionTramite) {
		this.habilitaExcepcionTramite = habilitaExcepcionTramite;
	}

}

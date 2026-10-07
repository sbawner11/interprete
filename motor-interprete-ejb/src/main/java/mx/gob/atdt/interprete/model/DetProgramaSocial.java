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
@Table(name = "det_programa_social", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetProgramaSocial.findByIdDetalleHome", 
		query = "SELECT new mx.gob.atdt.interprete.dto.DetProgramaSocialDTO("
			+ " dps.idDetallePrograma, dh.idDetalleHome, "
			+ " dps.habilitaCicloPrograma, dps.descripcionCicloPrograma, "
			+ " dps.descripcionTipoApoyo, dps.descripcionDuracionApoyo, dps.habilitaProgramaSimultaneo) "
			+ " FROM DetProgramaSocial dps " 
			+ "	JOIN dps.detHome dh "
			+ " WHERE dh.idDetalleHome = :idDetalleHome") })
public class DetProgramaSocial implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7052929543801508765L;
	
	private Long idDetallePrograma;
	private DetHome detHome;
	private boolean habilitaCicloPrograma;
	private String descripcionCicloPrograma;
	private String descripcionTipoApoyo;
	private String descripcionDuracionApoyo;
	private boolean habilitaProgramaSimultaneo;

	public DetProgramaSocial() {
	}

	public DetProgramaSocial(Long idDetallePrograma, DetHome detHome, boolean habilitaCicloPrograma,
			boolean habilitaProgramaSimultaneo) {
		this.idDetallePrograma = idDetallePrograma;
		this.detHome = detHome;
		this.habilitaCicloPrograma = habilitaCicloPrograma;
		this.habilitaProgramaSimultaneo = habilitaProgramaSimultaneo;
	}

	public DetProgramaSocial(Long idDetallePrograma, DetHome detHome,
			boolean habilitaCicloPrograma, String descripcionCicloPrograma,
			String descripcionTipoApoyo,
			String descripcionDuracionApoyo, boolean habilitaProgramaSimultaneo) {
		this.idDetallePrograma = idDetallePrograma;
		this.detHome = detHome;
		this.habilitaCicloPrograma = habilitaCicloPrograma;
		this.descripcionCicloPrograma = descripcionCicloPrograma;
		this.descripcionTipoApoyo = descripcionTipoApoyo;
		this.descripcionDuracionApoyo = descripcionDuracionApoyo;
		this.habilitaProgramaSimultaneo = habilitaProgramaSimultaneo;
	}

	@Id
	@Column(name = "id_detalle_programa", unique = true, nullable = false)
	public Long getIdDetallePrograma() {
		return this.idDetallePrograma;
	}

	public void setIdDetallePrograma(Long idDetallePrograma) {
		this.idDetallePrograma = idDetallePrograma;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_detalle_home", nullable = false)
	public DetHome getDetHome() {
		return this.detHome;
	}

	public void setDetHome(DetHome detHome) {
		this.detHome = detHome;
	}

	@Column(name = "habilita_ciclo_programa", nullable = false)
	public boolean isHabilitaCicloPrograma() {
		return this.habilitaCicloPrograma;
	}

	public void setHabilitaCicloPrograma(boolean habilitaCicloPrograma) {
		this.habilitaCicloPrograma = habilitaCicloPrograma;
	}

	@Column(name = "descripcion_ciclo_programa", length = 60)
	public String getDescripcionCicloPrograma() {
		return this.descripcionCicloPrograma;
	}

	public void setDescripcionCicloPrograma(String descripcionCicloPrograma) {
		this.descripcionCicloPrograma = descripcionCicloPrograma;
	}

	@Column(name = "descripcion_tipo_apoyo", length = 200)
	public String getDescripcionTipoApoyo() {
		return this.descripcionTipoApoyo;
	}

	public void setDescripcionTipoApoyo(String descripcionTipoApoyo) {
		this.descripcionTipoApoyo = descripcionTipoApoyo;
	}

	@Column(name = "descripcion_duracion_apoyo", length = 60)
	public String getDescripcionDuracionApoyo() {
		return this.descripcionDuracionApoyo;
	}

	public void setDescripcionDuracionApoyo(String descripcionDuracionApoyo) {
		this.descripcionDuracionApoyo = descripcionDuracionApoyo;
	}

	@Column(name = "habilita_programa_simultaneo", nullable = false)
	public boolean isHabilitaProgramaSimultaneo() {
		return this.habilitaProgramaSimultaneo;
	}

	public void setHabilitaProgramaSimultaneo(boolean habilitaProgramaSimultaneo) {
		this.habilitaProgramaSimultaneo = habilitaProgramaSimultaneo;
	}

}

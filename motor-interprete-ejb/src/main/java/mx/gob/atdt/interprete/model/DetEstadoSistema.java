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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "det_estado_sistema", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="DetEstadoSistema.findAll", 
			query = "   SELECT new mx.gob.atdt.interprete.dto.DetEstadoSistemaDTO "
					+ "        (des.id, ces.idEstadoSistema, ces.descripcion, des.fechaUltimaActualizacion) "
					+ "	FROM DetEstadoSistema des"
					+ "      JOIN des.catEstadosSistema ces"
					+ " ORDER BY des.id desc ")
})

public class DetEstadoSistema implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -124254936170177352L;
	
	private long id;
	private CatEstadosSistema catEstadosSistema;
	private Date fechaUltimaActualizacion;

	public DetEstadoSistema() {
	}

	public DetEstadoSistema(long id, CatEstadosSistema catEstadosSistema, Date fechaUltimaActualizacion) {
		this.id = id;
		this.catEstadosSistema = catEstadosSistema;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_estado_sistema", nullable = false)
	public CatEstadosSistema getCatEstadosSistema() {
		return this.catEstadosSistema;
	}

	public void setCatEstadosSistema(CatEstadosSistema catEstadosSistema) {
		this.catEstadosSistema = catEstadosSistema;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_ultima_actualizacion", nullable = false, length = 29)
	public Date getFechaUltimaActualizacion() {
		return this.fechaUltimaActualizacion;
	}

	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

}

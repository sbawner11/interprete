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
@Table(name = "componente_fecha", schema = "motor_interprete")
	@NamedQueries({ 
		@NamedQuery(name = "ComponenteFecha.findByIdComponente", 
				query = "SELECT new mx.gob.atdt.interprete.dto.ComponenteFechaDTO ( "
						+ "	cf.idComponenteFecha, c.idComponente, c.catTipoComponente.idTipoComponente, c.subseccionesFormulario.idSubseccionFormulario,  "
						+ " c.orden, c.requerido, c.tooltip, c.descripcionTooltip, c.tituloCampo, c.activo, c.fechaCreacion, c.fechaUltimaActualizacion, c.seccionSincronizada, "
						+ " cf.diasInhabiles, cf.fechaMenorHoy, cf.fechaMayorHoy, cf.fechaInicio, cf.fechaLimite) "
						+ " FROM ComponenteFecha cf "
						+ " JOIN cf.componente c "
						+ " WHERE c.idComponente = :idComponente")
	})
	public class ComponenteFecha implements java.io.Serializable {
	
	private static final long serialVersionUID = 3105966608363237963L;
	
	private Long idComponenteFecha;
	private Componente componente;
	private boolean diasInhabiles;
	private boolean fechaMenorHoy;
	private boolean fechaMayorHoy;
	private Date fechaInicio;
	private Date fechaLimite;

	public ComponenteFecha() {
	}

	public ComponenteFecha(Long idComponenteFecha, Componente componente) {
		this.idComponenteFecha = idComponenteFecha;
		this.componente = componente;
	}

	public ComponenteFecha(Long idComponenteFecha, Componente componente, boolean diasInhabiles, boolean fechaMenorHoy, 
			boolean fechaMayorHoy, Date fechaInicio, Date fechaLimite) {
		this.idComponenteFecha = idComponenteFecha;
		this.componente = componente;
		this.diasInhabiles = diasInhabiles;
		this.fechaMenorHoy = fechaMenorHoy;
		this.fechaMayorHoy = fechaMayorHoy;
		this.fechaInicio = fechaInicio;
		this.fechaLimite = fechaLimite;
	}

	@Id
	@Column(name = "id_componente_fecha", unique = true, nullable = false)
	public Long getIdComponenteFecha() {
		return this.idComponenteFecha;
	}

	public void setIdComponenteFecha(Long idComponenteFecha) {
		this.idComponenteFecha = idComponenteFecha;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_inicio", length = 13)
	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fecha_limite", length = 13)
	public Date getFechaLimite() {
		return this.fechaLimite;
	}

	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	@Column(name = "dias_inhabiles", nullable = false)
	public boolean isDiasInhabiles() {
		return diasInhabiles;
	}

	public void setDiasInhabiles(boolean diasInhabiles) {
		this.diasInhabiles = diasInhabiles;
	}

	@Column(name = "fecha_menor_hoy", nullable = false)
	public boolean isFechaMenorHoy() {
		return fechaMenorHoy;
	}

	public void setFechaMenorHoy(boolean fechaMenorHoy) {
		this.fechaMenorHoy = fechaMenorHoy;
	}

	@Column(name = "fecha_mayor_hoy", nullable = false)
	public boolean isFechaMayorHoy() {
		return fechaMayorHoy;
	}

	public void setFechaMayorHoy(boolean fechaMayorHoy) {
		this.fechaMayorHoy = fechaMayorHoy;
	}	
	
}

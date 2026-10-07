package mx.gob.atdt.interprete.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tramite_firma_electronica", schema = "motor_interprete")
public class TramiteFirmaElectronica implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8135035761337538910L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tramite_firma", unique = true, nullable = false)
	private long idTramiteFirma;
	
	@Column(name = "cadena_original", nullable = false)
	private String cadenaOriginal;
	
	@Column(name = "cadena_firmada")
	private String cadenaFirmada;
	
	@Column(name = "nombre_firmante")
	private String nombreFirmante;
	
	@Column(name = "fecha_creacion")
	private LocalDateTime fechaCreacion;
	
	@Column(name = "respuesta_servicio")
	private String respuestaServicio;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tramite", nullable = false)
	private Tramites tramites;
	
	@Column(name = "firma_ciudadano")
	private boolean firmaCiudadano;

	public long getIdTramiteFirma() {
		return idTramiteFirma;
	}

	public void setIdTramiteFirma(long idTramiteFirma) {
		this.idTramiteFirma = idTramiteFirma;
	}

	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	public String getCadenaFirmada() {
		return cadenaFirmada;
	}

	public void setCadenaFirmada(String cadenaFirmada) {
		this.cadenaFirmada = cadenaFirmada;
	}

	public String getNombreFirmante() {
		return nombreFirmante;
	}

	public void setNombreFirmante(String nombreFirmante) {
		this.nombreFirmante = nombreFirmante;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getRespuestaServicio() {
		return respuestaServicio;
	}

	public void setRespuestaServicio(String respuestaServicio) {
		this.respuestaServicio = respuestaServicio;
	}

	public Tramites getTramites() {
		return tramites;
	}

	public void setTramites(Tramites tramites) {
		this.tramites = tramites;
	}

	public boolean isFirmaCiudadano() {
		return firmaCiudadano;
	}

	public void setFirmaCiudadano(boolean firmaCiudadano) {
		this.firmaCiudadano = firmaCiudadano;
	}	
}

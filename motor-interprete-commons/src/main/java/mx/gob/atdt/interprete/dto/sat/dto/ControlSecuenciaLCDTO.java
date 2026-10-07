package mx.gob.atdt.interprete.dto.sat.dto;

import java.io.Serializable;
import java.util.Date;

public class ControlSecuenciaLCDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long idControl;
    private String cveDependencia;
    private String unidadAdministrativa;
    private String anio;
    private String nombreSecuencia;
    private Long valorInicial;
    private Long valorActual;
    private Boolean activo;
    private Date fechaCreacion;
    private Date fechaActualizacion;
    
	public Long getIdControl() {
		return idControl;
	}
	public void setIdControl(Long idControl) {
		this.idControl = idControl;
	}
	public String getCveDependencia() {
		return cveDependencia;
	}
	public void setCveDependencia(String cveDependencia) {
		this.cveDependencia = cveDependencia;
	}
	public String getUnidadAdministrativa() {
		return unidadAdministrativa;
	}
	public void setUnidadAdministrativa(String unidadAdministrativa) {
		this.unidadAdministrativa = unidadAdministrativa;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getNombreSecuencia() {
		return nombreSecuencia;
	}
	public void setNombreSecuencia(String nombreSecuencia) {
		this.nombreSecuencia = nombreSecuencia;
	}
	public Long getValorInicial() {
		return valorInicial;
	}
	public void setValorInicial(Long valorInicial) {
		this.valorInicial = valorInicial;
	}
	public Long getValorActual() {
		return valorActual;
	}
	public void setValorActual(Long valorActual) {
		this.valorActual = valorActual;
	}
	public Boolean getActivo() {
		return activo;
	}
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
    
}

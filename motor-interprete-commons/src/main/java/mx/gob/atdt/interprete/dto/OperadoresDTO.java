package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class OperadoresDTO implements Serializable {

	private static final long serialVersionUID = 6013081501551863624L;

	private String folio;
	private String nombreProyecto;
	private String fechaSolicitud;
	private String areaEncargada;
	private String fechaPrevencion;
	private String operador;
	private String estatus;
	private String styleEstatus;
	private String styleEstatusFont;

	/**
	 * 
	 */
	public OperadoresDTO() {
	}

	/**
	 * @param folio
	 * @param nombreProyecto
	 * @param fechaSolicitud
	 * @param areaEncargada
	 * @param fechaPrevencion
	 * @param operador
	 * @param estatus
	 */
	public OperadoresDTO(String folio, String nombreProyecto, String fechaSolicitud, String areaEncargada,
			String fechaPrevencion, String operador, String estatus, String styleEstatus, String styleEstatusFont) {
		super();
		this.folio = folio;
		this.nombreProyecto = nombreProyecto;
		this.fechaSolicitud = fechaSolicitud;
		this.areaEncargada = areaEncargada;
		this.fechaPrevencion = fechaPrevencion;
		this.operador = operador;
		this.estatus = estatus;
		this.styleEstatus = styleEstatus;
		this.styleEstatusFont = styleEstatusFont;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public String getFechaSolicitud() {
		return fechaSolicitud;
	}

	public void setFechaSolicitud(String fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}

	public String getAreaEncargada() {
		return areaEncargada;
	}

	public void setAreaEncargada(String areaEncargada) {
		this.areaEncargada = areaEncargada;
	}

	public String getFechaPrevencion() {
		return fechaPrevencion;
	}

	public void setFechaPrevencion(String fechaPrevencion) {
		this.fechaPrevencion = fechaPrevencion;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public String getStyleEstatus() {
		return styleEstatus;
	}

	public void setStyleEstatus(String styleEstatus) {
		this.styleEstatus = styleEstatus;
	}

	public String getStyleEstatusFont() {
		return styleEstatusFont;
	}

	public void setStyleEstatusFont(String styleEstatusFont) {
		this.styleEstatusFont = styleEstatusFont;
	}

	@Override
	public String toString() {
		return "OperadoresDTO [folio=" + folio + ", nombreProyecto=" + nombreProyecto + ", fechaSolicitud="
				+ fechaSolicitud + ", areaEncargada=" + areaEncargada + ", fechaPrevencion=" + fechaPrevencion
				+ ", operador=" + operador + ", estatus=" + estatus + ", styleEstatus=" + styleEstatus
				+ ", styleEstatusFont=" + styleEstatusFont + "]";
	}

}

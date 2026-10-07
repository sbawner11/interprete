package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.List;


public class ConsultaTramiteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4142375092178689224L;
	
	private boolean isOperador; 
	private boolean isSupervisor;
	private boolean isConsulta;
	private boolean habilitaDistribucion;
	private List<Long> lstElementosDistribucion;
	private Long idComponente;
	private Integer idTipoComponente;
	private String nombreTablaComponenteDistribucion;
	
	public ConsultaTramiteDTO() {
		super();
	}
	
	/**
	 * All constructor
	 *  
	 * @param isOperador
	 * @param isSupervisor
	 * @param isConsulta
	 * @param habilitaDistribucion
	 * @param lstElementosDistribucion
	 * @param idComponente
	 * @param idTipoComponente
	 * @param nombreTamblaComponenteDistribucion
	 */
	public ConsultaTramiteDTO(boolean isOperador, boolean isSupervisor, boolean isConsulta,
			boolean habilitaDistribucion, List<Long> lstElementosDistribucion, Long idComponente,
			Integer idTipoComponente, String nombreTablaComponenteDistribucion) {
		super();
		this.isOperador = isOperador;
		this.isSupervisor = isSupervisor;
		this.isConsulta = isConsulta;
		this.habilitaDistribucion = habilitaDistribucion;
		this.lstElementosDistribucion = lstElementosDistribucion;
		this.idComponente = idComponente;
		this.idTipoComponente = idTipoComponente;
		this.nombreTablaComponenteDistribucion = nombreTablaComponenteDistribucion;
	}
	
	public boolean isOperador() {
		return isOperador;
	}
	public void setOperador(boolean isOperador) {
		this.isOperador = isOperador;
	}
	public boolean isSupervisor() {
		return isSupervisor;
	}
	public void setSupervisor(boolean isSupervisor) {
		this.isSupervisor = isSupervisor;
	}
	public boolean isConsulta() {
		return isConsulta;
	}
	public void setConsulta(boolean isConsulta) {
		this.isConsulta = isConsulta;
	}
	public boolean isHabilitaDistribucion() {
		return habilitaDistribucion;
	}
	public void setHabilitaDistribucion(boolean habilitaDistribucion) {
		this.habilitaDistribucion = habilitaDistribucion;
	}
	public List<Long> getLstElementosDistribucion() {
		return lstElementosDistribucion;
	}
	public void setLstElementosDistribucion(List<Long> lstElementosDistribucion) {
		this.lstElementosDistribucion = lstElementosDistribucion;
	}
	public Long getIdComponente() {
		return idComponente;
	}
	public void setIdComponente(Long idComponente) {
		this.idComponente = idComponente;
	}
	public Integer getIdTipoComponente() {
		return idTipoComponente;
	}
	public void setIdTipoComponente(Integer idTipoComponente) {
		this.idTipoComponente = idTipoComponente;
	}
	public String getNombreTablaComponenteDistribucion() {
		return nombreTablaComponenteDistribucion;
	}
	public void setNombreTablaComponenteDistribucion(String nombreTablaComponenteDistribucion) {
		this.nombreTablaComponenteDistribucion = nombreTablaComponenteDistribucion;
	}

	@Override
	public String toString() {
		return "ConsultaTramiteDTO [isOperador=" + isOperador + ", isSupervisor=" + isSupervisor + ", isConsulta="
				+ isConsulta + ", habilitaDistribucion=" + habilitaDistribucion + ", lstElementosDistribucion="
				+ lstElementosDistribucion + ", idComponente=" + idComponente + ", idTipoComponente=" + idTipoComponente
				+ ", nombreTablaComponenteDistribucion=" + nombreTablaComponenteDistribucion + "]";
	}
}

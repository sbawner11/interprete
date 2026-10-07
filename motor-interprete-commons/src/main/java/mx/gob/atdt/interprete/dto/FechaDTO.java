package mx.gob.atdt.interprete.dto;

import java.util.Date;

public class FechaDTO {
	private Integer idFecha;
	private CatTipoComponenteDTO catTipoComponente;
	private String titulo;
	private Boolean chkObligatorio;
	private Boolean chkTooltip;
	private String txtTooltip;
	private Boolean chkTxtInteriorCampo;
	private String txtInteriorCampo;
	private Date fechaInicio;
	private Date fechaLimite;
	
	public FechaDTO() {
		catTipoComponente = new CatTipoComponenteDTO();
	}
	
	
	
	/**
	 * @return the idFecha
	 */
	public Integer getIdFecha() {
		return idFecha;
	}
	/**
	 * @param idFecha the idFecha to set
	 */
	public void setIdFecha(Integer idFecha) {
		this.idFecha = idFecha;
	}
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return the chkObligatorio
	 */
	public Boolean getChkObligatorio() {
		return chkObligatorio;
	}
	/**
	 * @param chkObligatorio the chkObligatorio to set
	 */
	public void setChkObligatorio(Boolean chkObligatorio) {
		this.chkObligatorio = chkObligatorio;
	}
	/**
	 * @return the chkTooltip
	 */
	public Boolean getChkTooltip() {
		return chkTooltip;
	}
	/**
	 * @param chkTooltip the chkTooltip to set
	 */
	public void setChkTooltip(Boolean chkTooltip) {
		this.chkTooltip = chkTooltip;
	}
	/**
	 * @return the txtTooltip
	 */
	public String getTxtTooltip() {
		return txtTooltip;
	}
	/**
	 * @param txtTooltip the txtTooltip to set
	 */
	public void setTxtTooltip(String txtTooltip) {
		this.txtTooltip = txtTooltip;
	}
	/**
	 * @return the chkTxtInteriorCampo
	 */
	public Boolean getChkTxtInteriorCampo() {
		return chkTxtInteriorCampo;
	}
	/**
	 * @param chkTxtInteriorCampo the chkTxtInteriorCampo to set
	 */
	public void setChkTxtInteriorCampo(Boolean chkTxtInteriorCampo) {
		this.chkTxtInteriorCampo = chkTxtInteriorCampo;
	}
	/**
	 * @return the txtInteriorCampo
	 */
	public String getTxtInteriorCampo() {
		return txtInteriorCampo;
	}
	/**
	 * @param txtInteriorCampo the txtInteriorCampo to set
	 */
	public void setTxtInteriorCampo(String txtInteriorCampo) {
		this.txtInteriorCampo = txtInteriorCampo;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaLimite
	 */
	public Date getFechaLimite() {
		return fechaLimite;
	}
	/**
	 * @param fechaLimite the fechaLimite to set
	 */
	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}

	/**
	 * @return the catTipoComponente
	 */
	public CatTipoComponenteDTO getCatTipoComponente() {
		return catTipoComponente;
	}

	/**
	 * @param catTipoComponente the catTipoComponente to set
	 */
	public void setCatTipoComponente(CatTipoComponenteDTO catTipoComponente) {
		this.catTipoComponente = catTipoComponente;
	}
	
	

	
}

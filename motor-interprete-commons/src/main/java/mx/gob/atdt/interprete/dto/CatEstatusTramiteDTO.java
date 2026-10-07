package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CatEstatusTramiteDTO implements Serializable{
	private static final long serialVersionUID = -7377786396124790238L;

	private int idEstatusTramite;
	private String descripcion;
	private String descripcionAviso;
	private String descripcionPersonalizada;

	/*
	 * variable utilizada para el conteo de trámites en el envío de correo
	 */
	private int totalTramites;
	/**
	 * 
	 */
	public CatEstatusTramiteDTO() {
	}

	/**
	 * @param idEstatusTramite
	 */
	public CatEstatusTramiteDTO(int idEstatusTramite) {
		this.idEstatusTramite = idEstatusTramite;
	}

	/**
	 * @param idEstatusTramite
	 * @param descripcion
	 */
	public CatEstatusTramiteDTO(int idEstatusTramite, String descripcion) {
		this.idEstatusTramite = idEstatusTramite;
		this.descripcion = descripcion;
	}

	/**	 * 
	 * @param idEstatusTramite
	 * @param descripcion
	 * @param descripcionAviso
	 */
	public CatEstatusTramiteDTO(int idEstatusTramite, String descripcion, String descripcionAviso) {
		this.idEstatusTramite = idEstatusTramite;
		this.descripcion = descripcion;
		this.descripcionAviso = descripcionAviso;
	}
	
	/**
	 * 26/06/2026
	 * Se ajusta el constructor para usar la descripcion personalida dentro de los atributo descripcion y descripcionAviso det catalogo
	 * 
	 * @param idEstatusTramite
	 * @param descripcion
	 * @param descripcionAviso
	 * @param descripcionPersonalizada
	 */
	public CatEstatusTramiteDTO(Integer idEstatusTramite, String descripcion, 
			String descripcionAviso, String descripcionPersonalizada) {
		this.idEstatusTramite = idEstatusTramite;
		this.descripcion = descripcion;
		this.descripcionAviso = descripcionAviso;
		if (descripcionPersonalizada != null && !descripcionPersonalizada.trim().isEmpty()) {
			this.descripcion = descripcionPersonalizada;
			this.descripcionAviso = descripcionPersonalizada;
		}
		this.descripcionPersonalizada = descripcionPersonalizada;
	}

	/**
	 * @return the idEstatusTramite
	 */
	public int getIdEstatusTramite() {
		return idEstatusTramite;
	}

	/**
	 * @param idEstatusTramite the idEstatusTramite to set
	 */
	public void setIdEstatusTramite(int idEstatusTramite) {
		this.idEstatusTramite = idEstatusTramite;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return  descripcionPersonalizada != null && !descripcionPersonalizada.isEmpty() ? descripcionPersonalizada : descripcion; 
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcionAviso() {
		return  descripcionPersonalizada != null && !descripcionPersonalizada.isEmpty() ? descripcionPersonalizada : descripcionAviso;
	}

	public void setDescripcionAviso(String descripcionAviso) {
		this.descripcionAviso = descripcionAviso;
	}	

	public String getDescripcionPersonalizada() { 
		return descripcionPersonalizada; 
	}

	public void setDescripcionPersonalizada(String descripcionPersonalizada) { 
		this.descripcionPersonalizada = descripcionPersonalizada; 
	}

	public int getTotalTramites() {
		return totalTramites;
	}

	public void setTotalTramites(int totalTramites) {
		this.totalTramites = totalTramites;
	}
	
	 
}
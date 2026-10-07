package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class ControlComponentesDTO implements Serializable, Comparable<ControlComponentesDTO>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7790716932238989858L;

	private Long id;
	private String nombreTabla;
	private String nombreColumna;
	private Long idComponente;
	private int idTipoComponente;
	private String nombreComponente;
	private Integer orden;
	private Date fechaCreacion;
	//Atributo para el guardado del campo en el formulario
	private String valorComponente;
	
	
	/**
	 * 
	 */
	public ControlComponentesDTO() {
	}

	/**
	 * @param id
	 * @param nombreTabla
	 * @param nombreColumna
	 * @param idComponente
	 * @param nombreComponente
	 * @param orden
	 * @param fechaCreacion
	 */
	public ControlComponentesDTO(Long id, String nombreTabla, String nombreColumna, Long idComponente,
			String nombreComponente, Integer orden, Date fechaCreacion) {
		this.id = id;
		this.nombreTabla = nombreTabla;
		this.nombreColumna = nombreColumna;
		this.idComponente = idComponente;
		this.nombreComponente = nombreComponente;
		this.orden = orden;
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the nombreTabla
	 */
	public String getNombreTabla() {
		return nombreTabla;
	}

	/**
	 * @param nombreTabla the nombreTabla to set
	 */
	public void setNombreTabla(String nombreTabla) {
		this.nombreTabla = nombreTabla;
	}

	/**
	 * @return the nombreColumna
	 */
	public String getNombreColumna() {
		return nombreColumna;
	}

	/**
	 * @param nombreColumna the nombreColumna to set
	 */
	public void setNombreColumna(String nombreColumna) {
		this.nombreColumna = nombreColumna;
	}

	/**
	 * @return the idComponente
	 */
	public Long getIdComponente() {
		return idComponente;
	}

	/**
	 * @param idComponente the idComponente to set
	 */
	public void setIdComponente(Long idComponente) {
		this.idComponente = idComponente;
	}
	
	/**
	 * @return the idTipoComponente
	 */
	public int getIdTipoComponente() {
		return idTipoComponente;
	}

	/**
	 * @param idTipoComponente the idTipoComponente to set
	 */
	public void setIdTipoComponente(int idTipoComponente) {
		this.idTipoComponente = idTipoComponente;
	}

	/**
	 * @return the nombreComponente
	 */
	public String getNombreComponente() {
		return nombreComponente;
	}

	/**
	 * @param nombreComponente the nombreComponente to set
	 */
	public void setNombreComponente(String nombreComponente) {
		this.nombreComponente = nombreComponente;
	}

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * @return the valorComponente
	 */
	public String getValorComponente() {
		return valorComponente;
	}

	/**
	 * @param valorComponente the valorComponente to set
	 */
	public void setValorComponente(String valorComponente) {
		this.valorComponente = valorComponente;
	}	

	@Override
	public int compareTo(ControlComponentesDTO controlComponenteDTO) {
		return (int) (this.id - controlComponenteDTO.getId());
	}
	
}

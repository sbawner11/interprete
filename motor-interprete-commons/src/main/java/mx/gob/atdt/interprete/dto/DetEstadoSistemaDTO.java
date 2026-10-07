package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;


public class DetEstadoSistemaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 583896002232892899L;

	private long id;
	private CatEstadosSistemaDTO catEstadosSistemaDTO;
	private Date fechaUltimaActualizacion;
	
	/**
	 * 
	 */
	public DetEstadoSistemaDTO() {
	}

	/**
	 * @param id
	 * @param catEstadosSistemaDTO
	 * @param fechaUltimaActualizacion
	 */
	public DetEstadoSistemaDTO(long id, CatEstadosSistemaDTO catEstadosSistemaDTO, Date fechaUltimaActualizacion) {
		this.id = id;
		this.catEstadosSistemaDTO = catEstadosSistemaDTO;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}
	
	/**
	 * Método utilizado por la NamedQuery DetEstadoSistema.findAll
	 * @param id
	 * @param idEstadoSistema
	 * @param descripcion
	 * @param fechaUltimaActualizacion
	 */
	public DetEstadoSistemaDTO(long id, int idEstadoSistema, String descripcion, Date fechaUltimaActualizacion) {
		this.id = id;
		this.catEstadosSistemaDTO = new CatEstadosSistemaDTO(idEstadoSistema, descripcion);
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the catEstadosSistemaDTO
	 */
	public CatEstadosSistemaDTO getCatEstadosSistemaDTO() {
		return catEstadosSistemaDTO;
	}

	/**
	 * @param catEstadosSistemaDTO the catEstadosSistemaDTO to set
	 */
	public void setCatEstadosSistemaDTO(CatEstadosSistemaDTO catEstadosSistemaDTO) {
		this.catEstadosSistemaDTO = catEstadosSistemaDTO;
	}

	/**
	 * @return the fechaUltimaActualizacion
	 */
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	/**
	 * @param fechaUltimaActualizacion the fechaUltimaActualizacion to set
	 */
	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}	

}

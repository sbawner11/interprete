package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;


public class BitMovimientosSeccionesDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4873857282875517378L;
	
	private Long idMovimientoSeccion;
	private CatSeccionesProyectoDTO catSeccionesProyectoDTO;
	private ProyectoDTO proyectoDTO;	
	private UsuarioDTO usuarioDTO;
	private Date fechaMovimiento;
	private String tablaMovimiento;
	private long idRegistroMovimiento;
	private boolean cambioSincronizado;
	private Date fechaSincronizacion;	

	/**
	 * 
	 */
	public BitMovimientosSeccionesDTO() {
	}	

	/**
	 * @param idMovimientoSeccion
	 */
	public BitMovimientosSeccionesDTO(Long idMovimientoSeccion) {
		this.idMovimientoSeccion = idMovimientoSeccion;
	}

	/**
	 * @return the idMovimientoSeccion
	 */
	public Long getIdMovimientoSeccion() {
		return idMovimientoSeccion;
	}

	/**
	 * @param idMovimientoSeccion the idMovimientoSeccion to set
	 */
	public void setIdMovimientoSeccion(Long idMovimientoSeccion) {
		this.idMovimientoSeccion = idMovimientoSeccion;
	}

	/**
	 * @return the proyectoDTO
	 */
	public ProyectoDTO getProyectoDTO() {
		return proyectoDTO;
	}

	/**
	 * @param proyectoDTO the proyectoDTO to set
	 */
	public void setProyectoDTO(ProyectoDTO proyectoDTO) {
		this.proyectoDTO = proyectoDTO;
	}

	/**
	 * @return the catSeccionesProyectoDTO
	 */
	public CatSeccionesProyectoDTO getCatSeccionesProyectoDTO() {
		return catSeccionesProyectoDTO;
	}

	/**
	 * @param catSeccionesProyectoDTO the catSeccionesProyectoDTO to set
	 */
	public void setCatSeccionesProyectoDTO(CatSeccionesProyectoDTO catSeccionesProyectoDTO) {
		this.catSeccionesProyectoDTO = catSeccionesProyectoDTO;
	}

	/**
	 * @return the usuarioDTO
	 */
	public UsuarioDTO getUsuarioDTO() {
		return usuarioDTO;
	}

	/**
	 * @param usuarioDTO the usuarioDTO to set
	 */
	public void setUsuarioDTO(UsuarioDTO usuarioDTO) {
		this.usuarioDTO = usuarioDTO;
	}

	/**
	 * @return the fechaMovimiento
	 */
	public Date getFechaMovimiento() {
		return fechaMovimiento;
	}

	/**
	 * @param fechaMovimiento the fechaMovimiento to set
	 */
	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}		

	/**
	 * @return the tablaMovimiento
	 */
	public String getTablaMovimiento() {
		return tablaMovimiento;
	}

	/**
	 * @param tablaMovimiento the tablaMovimiento to set
	 */
	public void setTablaMovimiento(String tablaMovimiento) {
		this.tablaMovimiento = tablaMovimiento;
	}	
	
	/**
	 * @return the idRegistroMovimiento
	 */
	public long getIdRegistroMovimiento() {
		return idRegistroMovimiento;
	}

	/**
	 * @param idRegistroMovimiento the idRegistroMovimiento to set
	 */
	public void setIdRegistroMovimiento(long idRegistroMovimiento) {
		this.idRegistroMovimiento = idRegistroMovimiento;
	}

	/**
	 * @return the cambioSincronizado
	 */
	public boolean isCambioSincronizado() {
		return cambioSincronizado;
	}

	/**
	 * @param cambioSincronizado the cambioSincronizado to set
	 */
	public void setCambioSincronizado(boolean cambioSincronizado) {
		this.cambioSincronizado = cambioSincronizado;
	}

	/**
	 * @return the fechaSincronizacion
	 */
	public Date getFechaSincronizacion() {
		return fechaSincronizacion;
	}

	/**
	 * @param fechaSincronizacion the fechaSincronizacion to set
	 */
	public void setFechaSincronizacion(Date fechaSincronizacion) {
		this.fechaSincronizacion = fechaSincronizacion;
	}
	
}

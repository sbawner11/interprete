package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import mx.gob.atdt.interprete.commons.utils.Constantes;

public class ComponenteFechaDTO extends ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5987778037834308896L;
	
	private Long idComponenteFecha;
	private boolean diasInhabiles;
	private boolean fechaMenorHoy;
	private boolean fechaMayorHoy;	
	private Date fechaInicio;
	private Date fechaLimite;
	private List<Date> lstFechasInvalidas;
	
	/**
	 * 
	 */
	public ComponenteFechaDTO() {
		super();
		super.setCatTipoComponenteDTO(new CatTipoComponenteDTO());
	}

	/**
	 * Constructor utilizado por la NamedQuery ComponenteFecha.findByIdComponente
	 * 
	 * @param idComponenteFecha
	 * @param idComponente
	 * @param idTipoComponente
	 * @param idSubseccionFormulario
	 * @param orden
	 * @param requerido
	 * @param tooltip
	 * @param descripcionTooltip
	 * @param tituloCampo
	 * @param activo
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param seccionSincronizada
	 * @param diasInhabiles
	 * @param fechaMenorHoy
	 * @param fechaMayorHoy
	 * @param fechaInicio
	 * @param fechaLimite
	 */
	public ComponenteFechaDTO(Long idComponenteFecha, Long idComponente, Integer idTipoComponente, Long idSubseccionFormulario, int orden, boolean requerido, 
			boolean tooltip, String descripcionTooltip, String tituloCampo, boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion, boolean seccionSincronizada,
			boolean diasInhabiles, boolean fechaMenorHoy, boolean fechaMayorHoy, Date fechaInicio, Date fechaLimite) {
		super (idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip, descripcionTooltip, tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		
		this.idComponenteFecha = idComponenteFecha;
		this.diasInhabiles = diasInhabiles;
		this.fechaMenorHoy = fechaMenorHoy;
		this.fechaMayorHoy = fechaMayorHoy;		
		this.fechaInicio = fechaInicio;
		this.fechaLimite = fechaLimite;
	}
	
	/**
	 * Método auxiliar que obtiene la fecha máxima que se puede seleccionar en el calendario
	 * @return
	 */
	public Date obtenerMaxDate() {
		Date fechaMaxima = null;
		/** Si se selecciona opción fechaMenorHoy el valor de maxDate será al día actual **/
		/** Si se selecciona opción fechaMayorHoy el valor de maxDate será "fechaLimite" **/
		if(fechaMenorHoy) {
			fechaMaxima = new Date();
		} else {
			fechaMaxima = fechaLimite;
		}		
		return fechaMaxima;
	}
	
	/**
	 * Método auxiliar que obtiene la fecha mínima que se puede seleccionar en el calendario
	 * @return
	 */
	public Date obtenerMinDate() {
		Date fechaMinima = null;
		/** - Si se selecciona opción fechaMenorHoy el valor de minDate será "fechaInicio" **/
		/** - Si se selecciona opción fechaMayorHoy y "fechaLimite" es menor al día actual, 
		 *    entonces el valor de  minDate será "fechaLimite"
		 *    Si se selecciona opción fechaMayorHoy y "fechaLimite" es mayor al día actual,
		 *    entonces el valor de minDate será el día actual.
		 *    **/
		if(fechaMenorHoy) {
			fechaMinima = fechaInicio;
		} else {
			/**Si configuró fecha Mayor a hoy y la fecha límite registrada es menor al día actual, 
			 * se setea como fecha mínima la fecha límite.
			 */
			Calendar cFechaActual = Calendar.getInstance();
			cFechaActual.setTime(new Date());
			cFechaActual.set(Calendar.HOUR_OF_DAY, 23);
			cFechaActual.set(Calendar.MINUTE, 59);
			cFechaActual.set(Calendar.SECOND, 59);
			Date dateActual = cFechaActual.getTime();			
			if(fechaLimite.before(dateActual)) {
				fechaMinima = fechaLimite;	
			} else {
				fechaMinima = new Date();
			}
		}
		return fechaMinima;
	}
	
	/**
	 * Método auxiliar que genera un listado de fechas que deben invalidarse para selección
	 * en calendario.
	 * @return
	 */
	public List<Date> obtenerFechaNoDisponible(){
		this.lstFechasInvalidas = new ArrayList<Date>();
		if(fechaMayorHoy) {
			/**
			 * Si configuró fecha mayor a hoy, y la fecha límite ingresada es menor al día actual,
			 * se coloca maxdate y mindate el valor de "fechaLimite" además se  agrega a lista de
			 * fechasInvalidas para que el componente la deshabilite con el parámetro disabledDates.
			 */
			Calendar cFechaLimite = Calendar.getInstance();
			cFechaLimite.setTime(fechaLimite);
			cFechaLimite.set(Calendar.HOUR_OF_DAY, 23);
			cFechaLimite.set(Calendar.MINUTE, 59);
			cFechaLimite.set(Calendar.SECOND, 59);		
			Date dateFechaLimite = cFechaLimite.getTime();	
			
			if(dateFechaLimite.before(new Date())) {
				lstFechasInvalidas.add(fechaLimite);	
			} 
		}		
		
		return lstFechasInvalidas;			
	}
	
	/**
	 * Método auxiliar que permite calcular el Rango de años para mostrar en el componente fecha
	 * @return
	 */
	public String calculaRangoCalendario() {		
		String rangoFecha = Constantes.EMPTY_STRING;
		
		Calendar cInicio = Calendar.getInstance();
		Calendar cFin = Calendar.getInstance();
		
		Date fechaMaxima = null;
		Date fechaMinima = null;
		if(fechaMenorHoy) {
			fechaMaxima = new Date();
			fechaMinima = fechaInicio;			
		} else {
			Calendar cFechaActual = Calendar.getInstance();
			cFechaActual.setTime(new Date());
			cFechaActual.set(Calendar.HOUR_OF_DAY, 23);
			cFechaActual.set(Calendar.MINUTE, 59);
			cFechaActual.set(Calendar.SECOND, 59);
			Date dateActual = cFechaActual.getTime();			
			if(fechaLimite.before(dateActual)) {
				fechaMaxima = fechaLimite;
				fechaMinima = fechaLimite;					
			} else {
				fechaMaxima = fechaLimite;
				fechaMinima = new Date();				
			}
		}
		cInicio.setTime(fechaMinima);
		cFin.setTime(fechaMaxima);
		
		rangoFecha = cInicio.get(Calendar.YEAR) +":"+ cFin.get(Calendar.YEAR);		

		return rangoFecha;
	}

	/**
	 * @return the idComponenteFecha
	 */
	public Long getIdComponenteFecha() {
		return idComponenteFecha;
	}

	/**
	 * @param idComponenteFecha the idComponenteFecha to set
	 */
	public void setIdComponenteFecha(Long idComponenteFecha) {
		this.idComponenteFecha = idComponenteFecha;
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

	public boolean isDiasInhabiles() {
		return diasInhabiles;
	}

	public void setDiasInhabiles(boolean diasInhabiles) {
		this.diasInhabiles = diasInhabiles;
	}

	public boolean isFechaMenorHoy() {
		return fechaMenorHoy;
	}

	public void setFechaMenorHoy(boolean fechaMenorHoy) {
		this.fechaMenorHoy = fechaMenorHoy;
	}

	public boolean isFechaMayorHoy() {
		return fechaMayorHoy;
	}

	public void setFechaMayorHoy(boolean fechaMayorHoy) {
		this.fechaMayorHoy = fechaMayorHoy;
	}

	public List<Date> getLstFechasInvalidas() {
		return lstFechasInvalidas;
	}

	public void setLstFechasInvalidas(List<Date> lstFechasInvalidas) {
		this.lstFechasInvalidas = lstFechasInvalidas;
	}
	
}

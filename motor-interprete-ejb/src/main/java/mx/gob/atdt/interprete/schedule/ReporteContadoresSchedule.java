package mx.gob.atdt.interprete.schedule;

import java.net.ConnectException;
import java.net.URISyntaxException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.ReporteContadoresProyectosDTO;
import mx.gob.atdt.interprete.exception.InterpreteException;
import mx.gob.atdt.interprete.facade.ReporteContadoresProyectoFacade;
import mx.gob.atdt.motor.client.ContadoresProyectoRESTClient;

/**
 * @author
 * 
 * Clase que realiza la consulta de contadores de los trámites para enviarlos al Motor, para su registro en el 
 * Dashboard
 */
@Singleton
@Startup
public class ReporteContadoresSchedule {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReporteContadoresSchedule.class);
	
	@Resource
	private TimerService timerService;
	
	@Inject
	private ProyectoDAO proyectoDAO;
	
	@Inject
	private ReporteContadoresProyectoFacade reporteContadoresProyectoFacade;
		
	@PostConstruct
	public void inicializar() {
		TimerConfig configInicioReporte	= new TimerConfig();
		configInicioReporte.setPersistent(false);
		ScheduleExpression scheduleInicioReporte = new ScheduleExpression();
		scheduleInicioReporte.dayOfWeek("*");
		
		if(Environment.getAppProfile().compareTo("prod") == 0) {
			scheduleInicioReporte.hour("00");
			scheduleInicioReporte.minute("01");

		} else {
			scheduleInicioReporte.hour("06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23");
			scheduleInicioReporte.minute("00");		
		}		
		/*Timer timer1 = */timerService.createCalendarTimer(scheduleInicioReporte, configInicioReporte);
	}
	
	@Timeout
    public void sincronizarContadoresProyecto(Timer timer) {		
		LOGGER.info("----->>> Schedule para envío de contadores del proyecto.");
		
		//Se revisa si existe información de proyectos en la BD.
		if(proyectoDAO.buscarTodos() != null) {
			ReporteContadoresProyectosDTO contadoresProyecto = reporteContadoresProyectoFacade.consultarContadoresProyecto();		
			
			ContadoresProyectoRESTClient clienteContadoresProyecto = new ContadoresProyectoRESTClient();
			try {
				if(clienteContadoresProyecto.registrarContadoresProyecto(contadoresProyecto)) {
					LOGGER.info("-------->>>> Se realizó el registro de contadores correctamente!");
				}
			} catch (ConnectException | URISyntaxException | JSONException | InterpreteException e) {
				LOGGER.error("No fue posible realizar el registro de contadores del proyecto cliente.", e);
			}			
		} else {
			LOGGER.info("----->>> Cliente nuevo, no tiene proyecto sincronizado.");	
		}		
   }
}
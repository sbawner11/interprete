package mx.gob.atdt.interprete.schedule;

import java.util.Date;
import java.util.Objects;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.dao.NotificacionesDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.NotificacionesDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.formulario.facade.FormularioFacade;

@Singleton
@Startup
public class NotificacionFuncionarioSchedule {

	  private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionFuncionarioSchedule.class);

	    @Resource
	    TimerService timerService;

	    @Inject
	    private ProyectoDAO proyectoDAO;

	    @Inject
	    private NotificacionesDAO notificacionesDAO;

	    @Inject
	    private FormularioFacade formularioFacade; 

	    @Schedules({
	        @Schedule(hour = "09", minute = "00", persistent = false)
	    })
	    public void enviarNotificaciones() {
	        LOGGER.info(">>> INICIA SCHEDULE PARA ENVÍO DE NOTIFICACIONES DE FUNCIONARIOS: {}", new Date());

	        try {
	        	ProyectoDTO proyectoDTO = proyectoDAO.consultaProyecto();
	            if (proyectoDTO == null || proyectoDTO.getIdProyecto() == null) {
	                LOGGER.warn("Proyecto no disponible. Abortando envío de notificaciones.");
	                return;
	            }

	            NotificacionesDTO notificaciones = notificacionesDAO.buscarActivoPorIdProyecto(proyectoDTO.getIdProyecto());
	            if (notificaciones == null) {
	                return;
	            }

	            int diaActual = obtenerDiaSemanaActual();

	            if (notificaciones.getCatDiaSemanaDTO() != null
	                    && notificaciones.getCatDiaSemanaDTO().getIdDiaSemana() != null
	                    && Objects.equals(notificaciones.getCatDiaSemanaDTO().getIdDiaSemana(), diaActual)
	                    && notificaciones.isEnvioNotificaciones()) {

	                enviarCorreoNotificacion(notificaciones, proyectoDTO.getNombreProyecto());
	            }

	        } catch (Exception e) {
	            LOGGER.error("Ocurrió un error durante la ejecución del schedule NotificacionFuncionarioSchedule: ", e);
	        }

	        LOGGER.info(">>> TERMINA SCHEDULE PARA ENVÍO DE NOTIFICACIONES DE FUNCIONARIOS: {}", new Date());
	    }
	    
	    private void enviarCorreoNotificacion(NotificacionesDTO notificacion, String nombreProyecto) {
	        try {
	            formularioFacade.enviarNotificacionFuncionario(notificacion, nombreProyecto);
	            LOGGER.info("Correo enviado correctamente para la notificación ID {} a: {}",
	                    notificacion.getIdNotificacion(), notificacion.getCorreosNotificacion());
	        } catch (Exception e) {
	            LOGGER.error("Error al enviar correo para notificación ID {}: {}",
	                    notificacion.getIdNotificacion(), e.getMessage(), e);
	        }
	    }

		private int obtenerDiaSemanaActual() {
			java.util.Calendar calendar = java.util.Calendar.getInstance();
			int dia = calendar.get(java.util.Calendar.DAY_OF_WEEK);
			return (dia == java.util.Calendar.SUNDAY) ? 7 : dia - 1;
		}
	}
	

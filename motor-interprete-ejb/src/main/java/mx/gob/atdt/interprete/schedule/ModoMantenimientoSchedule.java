package mx.gob.atdt.interprete.schedule;

import java.util.Date;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.DetEstadoSistemaDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.CatEstadosSistemaDTO;
import mx.gob.atdt.interprete.dto.DetEstadoSistemaDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.motor.client.SincronizacionProyectoRESTClient;


/**
 * @author raul
 * 
 * Clase que se encarga de colocar el Sistema en "Modo Mantenimiento" periódicamente para que posteriormente otro proceso sincronice los cambios
 * del motor transaccional a este cliente. Una vez concluida esa sincronización, esta misma clase periódicamente regresa el estatus del sistema
 * a "En Línea".
 */
@Singleton
@Startup
public class ModoMantenimientoSchedule {

    private static final Logger LOGGER = LoggerFactory.getLogger(ModoMantenimientoSchedule.class);

    private static final String TIMER_CONFIG_INICIO_NAME = "inicia-mantto";

    @Resource
    private TimerService timerService;

    @Inject
    private DetEstadoSistemaDAO detEstadoSistemaDAO;

    @Inject
    private ProyectoDAO proyectoDAO;

    @PostConstruct
    public void inicializar() {
    	
		TimerConfig configInicioMantto = new TimerConfig(TIMER_CONFIG_INICIO_NAME, false); 
		ScheduleExpression scheduleInicioMantto = new ScheduleExpression();
		scheduleInicioMantto.dayOfWeek("*");

		if(Environment.getAppProfile().compareTo("prod") == 0) {
			scheduleInicioMantto.hour("01");
			scheduleInicioMantto.minute("50");					
		} else if(Environment.getAppProfile().compareTo("staging") == 0) {		
			scheduleInicioMantto.hour("01,03,05,07,09,11,13,15,17,19,21,23");
			scheduleInicioMantto.minute("59");
		} else {			
			scheduleInicioMantto.hour("*");
			scheduleInicioMantto.minute("59");					
		}
		
		timerService.createCalendarTimer(scheduleInicioMantto, configInicioMantto);
    }

    @Timeout
    public void actualizaModoMantenimiento(Timer timer) {
    	
        try {
            DetEstadoSistemaDTO estadoSistema = detEstadoSistemaDAO.buscarEstadoSistema();
            ProyectoDTO proyecto = proyectoDAO.consultaProyecto();            
            Long idProyecto = BeanUtils.isNotNull(proyecto) ? proyecto.getIdProyecto() : null;

            if (BeanUtils.isNull(estadoSistema) || BeanUtils.isNull(idProyecto)) {
                LOGGER.info("Schedule de Modo Mantenimiento no se ejecutó: no hay proyecto sincronizado.");
                return;
            }

            SincronizacionProyectoRESTClient clienteRest = new SincronizacionProyectoRESTClient();
            String respuesta = clienteRest.obtenerEstatusProyecto(idProyecto);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode respJson = mapper.readTree(respuesta);

            int idEstatus = respJson.path("idEstatus").asInt();
            String nombreProyecto = respJson.path("nombre").asText();
            String estatusProyecto = respJson.path("estatus").asText();

            if (idEstatus != Constantes.ID_ESTATUS_PUBLICAR_CAMBIOS) {
                LOGGER.info("Schedule de Modo Mantenimiento no se ejecutó para el proyecto '{}': estatus actual es '{}'. Debe estar en estatus 'PUBLICAR CAMBIOS'.",
                        nombreProyecto, estatusProyecto);
                return;
            }

            switch (timer.getInfo().toString()) {
                case TIMER_CONFIG_INICIO_NAME:
                    LOGGER.info("Iniciando Modo Mantenimiento - Fecha: {}", new Date());
                    //Se valida que el sistema se encuentre en Línea para poder actualizarlo a "En Mantenimiento".
                    if (estadoSistema.getCatEstadosSistemaDTO().getIdEstadoSistema() == Constantes.ID_ESTADO_SISTEMA_LINEA) {
                        estadoSistema.setCatEstadosSistemaDTO(new CatEstadosSistemaDTO(Constantes.ID_ESTADO_SISTEMA_MANTENIMIENTO));
                        detEstadoSistemaDAO.actualizar(estadoSistema);
                        LOGGER.info("Estado del sistema actualizado a 'MANTENIMIENTO'.");
                    }
                    break;           

                default:
                    LOGGER.warn("¡Etapa de Modo Mantenimiento no definida!: {}, Fecha: {}", timer.getInfo(), new Date());
                    break;
            }

        } catch (Exception e) {
            LOGGER.error("Error al ejecutar el Schedule de Modo Mantenimiento: ", e);
        }
    }
}

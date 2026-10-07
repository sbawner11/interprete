package mx.gob.atdt.interprete.schedule;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.TransactionTimeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.application.ConfiguracionWebhookApplication;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.dao.NotificacionMovimientoTramiteDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.ConfiguracionWebhookDTO;
import mx.gob.atdt.interprete.dto.GeneraComprobanteDTO;
import mx.gob.atdt.interprete.dto.NotificacionMovimientoTramiteDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.facade.NotificacionWebHookFacade;

/**
 * Schedule para reenvío de notificaciones webhook pendientes.
 * Se ejecuta cada 4 horas con 10 minutos y procesa los registros de notificacion_movimiento_tramite
 * que tienen envio_confirmado = false.
 */
@Singleton
@Startup
public class ReenvioNotificacionesWebhookSchedule {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReenvioNotificacionesWebhookSchedule.class);

	@Inject
	private ConfiguracionWebhookApplication configuracionWebhookApplication;

	@Inject
	private NotificacionWebHookFacade notificacionWebHookFacade;
	
	@Inject
    private ProyectoDAO proyectoDAO;
	
	
	@Inject 
	private NotificacionMovimientoTramiteDAO notificacionMovimientoTramiteDAO;

	/**
	 * Se ejecuta cada 4 horas: 00:00, 04:00, 08:00, 12:00, 16:00, 20:00
	 * Timeout de transacción: 1 hora
	 */
	@TransactionTimeout(value = 1, unit = TimeUnit.HOURS)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Schedules({
		@Schedule(hour = "0,4,8,12,16,20", minute = "10", persistent = false)
	})
	public void ejecutarReenvioNotificaciones() {
		long inicio = System.nanoTime();
		LOGGER.info(">>> INICIA SCHEDULE ReenvioNotificacionesWebhookSchedule: {}", new Date());

		try {
			
			ProyectoDTO proyectoDTO = proyectoDAO.consultaProyecto();
            if (proyectoDTO == null || proyectoDTO.getIdProyecto() == null) {
                LOGGER.warn("Proyecto no disponible. Abortando proceso.");
                return;
            }
            ConfiguracionWebhookDTO configDTO = this.validarConfiguracionWebhook();
			if (BeanUtils.isNull(configDTO)) {
				LOGGER.warn("Configuración webhook no habilitada. Proceso terminado.");
				return;
			}
			procesarNotificacionesPendientes(configDTO, proyectoDTO);
		} catch (Exception e) {
			LOGGER.error("Error durante la ejecución del schedule ReenvioNotificacionesWebhookSchedule: ", e);
		}
		long fin = System.nanoTime();
		LOGGER.info(">>> FINALIZA SCHEDULE ReenvioNotificacionesWebhookSchedule - Duración: {{}} seg.", TimeUnit.NANOSECONDS.toSeconds((fin-inicio)));
	}

	/**
	 * Valida que la configuración webhook esté disponible y habilitada
	 * @return true si está habilitado el envío de notificaciones
	 */
	private ConfiguracionWebhookDTO validarConfiguracionWebhook() {
		ConfiguracionWebhookDTO configDTO = configuracionWebhookApplication.getConfiguracionWebhookDTO();

		if (BeanUtils.isNull(configDTO)) {
			LOGGER.debug("ConfiguracionWebhookDTO es null");
			return null;
		}

		if (BeanUtils.isFalse(configDTO.isHabilitaEnvioNotificaciones())) {
			LOGGER.debug("Envío de notificaciones deshabilitado en configuración");
			return null;
		}

		if (BeanUtils.isNull(configDTO.getUrlAplicacionNotificaciones())  || 
				BeanUtils.isEmpty(configDTO.getUrlAplicacionNotificaciones().trim())) {
			LOGGER.warn("URL de aplicación de notificaciones no configurada");
			return null;
		}

		return configDTO;
	}

	/**
	 * Consulta y procesa las notificaciones pendientes de envío
	 */
	private void procesarNotificacionesPendientes(ConfiguracionWebhookDTO configDTO, ProyectoDTO proyectoDTO) {
		List<NotificacionMovimientoTramiteDTO> lstTramites = notificacionMovimientoTramiteDAO.obtenerPendientesConDatosTramite();
		if (BeanUtils.isNull(lstTramites)) {
			LOGGER.debug("No hay notificaciones pendientes de envío");
			return;
		}

		LOGGER.info("Procesando {} notificaciones pendientes", lstTramites.size());
		notificacionWebHookFacade.notificarWebhookScheduler(lstTramites, proyectoDTO, configDTO, new GeneraComprobanteDTO());
	}
}

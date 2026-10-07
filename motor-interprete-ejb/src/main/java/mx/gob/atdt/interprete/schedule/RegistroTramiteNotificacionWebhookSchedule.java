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
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.ArchivoRespuestaDAO;
import mx.gob.atdt.interprete.dao.NotificacionMovimientoTramiteDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dao.TramitesDAO;
import mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO;
import mx.gob.atdt.interprete.dto.CatTipoNotificacionDTO;
import mx.gob.atdt.interprete.dto.ConfiguracionWebhookDTO;
import mx.gob.atdt.interprete.dto.NotificacionMovimientoTramiteDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;

/**
 * Schedule que valida si se ha realizado la configuración de Webhook
 * para hacer el registro inicial de trámites en la tabla control de notificaciones.
 */
@Singleton
@Startup
public class RegistroTramiteNotificacionWebhookSchedule {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistroTramiteNotificacionWebhookSchedule.class);
	
	@Inject
	private ConfiguracionWebhookApplication configuracionWebhookApplication;
	
	@Inject
	private TramitesDAO tramitesDAO;

	@Inject
	private NotificacionMovimientoTramiteDAO notificacionMovimientoTramiteDAO;
	
	@Inject
    private ProyectoDAO proyectoDAO;
	
	@Inject
	private ArchivoRespuestaDAO respuestaDAO;

	/**
	 * Se ejecuta en los horarios siguientes: 06:10, 11:10, 15:10, 19:10
	 * Timeout de transacción: 1 hora
	 */
	@TransactionTimeout(value = 1, unit = TimeUnit.HOURS)
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	@Schedules({
		//@Schedule(hour = "06,11,15,19", minute = "10", persistent = false)
		@Schedule(hour = "*", minute = "01", persistent = false)
	})
	public void registrarNotificaciones() {
		
		long inicio = System.nanoTime();
		LOGGER.debug(">>> INICIA SCHEDULE RegistroNotificacionesWebhookSchedule: {}", new Date());

		try {
			ProyectoDTO proyectoDTO = proyectoDAO.consultaProyecto();
	        if (proyectoDTO == null || proyectoDTO.getIdProyecto() == null) {
	            LOGGER.warn("Proyecto no disponible. Abortando proceso.");
	            return;
	        }
	        
			if (!validarConfiguracionWebhook()) {
				LOGGER.debug("Configuración webhook no habilitada. Proceso terminado.");
				return;
			}
			
			List<ArchivosRespuestaTokenDTO> lstRespuesta = respuestaDAO.consultaArchivoRespuesta(proyectoDTO.getIdProyecto());
			boolean habilitaFirmadoTramites = false;
			if (BeanUtils.isNotNull(lstRespuesta)) {
				habilitaFirmadoTramites = BeanUtils.habilitaFirmadoTramites(lstRespuesta);
			}			
			LOGGER.info("scheduler registro tramites Proyecto habilitado para firma electronica: {}",habilitaFirmadoTramites);
			
			
			registrarTramitesNotificaciones(habilitaFirmadoTramites);
		} catch (Exception e) {
			LOGGER.error("Error durante la ejecución del schedule RegistroNotificacionesWebhookSchedule: ", e);
		}
		long fin = System.nanoTime();
		LOGGER.debug(">>> FINALIZA SCHEDULE RegistroNotificacionesWebhookSchedule - Duración: {} ms", (fin - inicio));
	}

	/**
	 * Valida que la configuración webhook esté disponible y habilitada
	 * @return true si está habilitado el envío de notificaciones
	 */
	private boolean validarConfiguracionWebhook() {
		ConfiguracionWebhookDTO configDTO = configuracionWebhookApplication.getConfiguracionWebhookDTO();

		if (configDTO == null) {
			LOGGER.debug("ConfiguracionWebhookDTO es null");
			return false;
		}

		if (!configDTO.isHabilitaEnvioNotificaciones()) {
			LOGGER.debug("Envío de notificaciones deshabilitado en configuración");
			return false;
		}

		if (configDTO.getUrlAplicacionNotificaciones() == null || 
			configDTO.getUrlAplicacionNotificaciones().trim().isEmpty()) {
			LOGGER.warn("URL de aplicación de notificaciones no configurada");
			return false;
		}

		return true;
	}

	/**
	 * Método que inicializa el registro de Notificaciones que se realizarán mediante el Webhook
	 * @param habilitaFirmadoTramites true or false
	 */
	private void registrarTramitesNotificaciones(boolean habilitaFirmadoTramites) {
		List<TramiteDTO> lstTramites = tramitesDAO.obtenerTramitesPendientesNotificacion(habilitaFirmadoTramites);
		
		if(BeanUtils.isNotNull(lstTramites)) {
			LOGGER.info("Total de trámites para registro de notificaciones: {}, inicia {}, lst {}", lstTramites.size(), new Date(), lstTramites);
			
			for(TramiteDTO tramiteTemp: lstTramites) {
				NotificacionMovimientoTramiteDTO notificacionTramite = new NotificacionMovimientoTramiteDTO();
				
				notificacionTramite.setTramiteDTO(tramiteTemp);
				if(tramiteTemp.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO ) {
					notificacionTramite.setCatTipoNotificacionDTO(new CatTipoNotificacionDTO(Constantes.ID_TIPO_NOTIFICACION_REGISTRO_TRAMITE));
				} else {
					notificacionTramite.setCatTipoNotificacionDTO(new CatTipoNotificacionDTO(Constantes.ID_TIPO_NOTIFICACION_ACTUALIZACION_ESTATUS_TRAMITE));
				}
				notificacionTramite.setFechaNotificacion(null);
				notificacionTramite.setEnvioConfirmado(false);
				
				notificacionMovimientoTramiteDAO.guardar(notificacionTramite);
			}
			LOGGER.info("Registro completado - {} ", new Date());
		}				
	}
}

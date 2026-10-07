package mx.gob.atdt.interprete.schedule;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.TransactionTimeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dao.UsuarioGestionDAO;
import mx.gob.atdt.interprete.dto.DetGestionUsuarioDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.formulario.dao.FormularioDAO;
import mx.gob.atdt.interprete.formulario.facade.FormularioFacade;

@Singleton
@Startup
public class ValidaPrevencionTramiteSchedule {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidaPrevencionTramiteSchedule.class);
	
	@Resource
	TimerService timerService;
	
	@Inject
	private ProyectoDAO proyectoDAO;
	
	@Inject
	private UsuarioGestionDAO usuarioGestionDAO;
	
	@Inject
	private FormularioDAO formularioDAO;
	
	@Inject
	private FormularioFacade formularioFacade;

	@TransactionTimeout(value = 30, unit = TimeUnit.MINUTES)
	@Schedules({
		//Definición schedule que se ejecutará 10 minutos despues del cambio a modo "En línea" del sistema.
		@Schedule(hour = "04", minute = "10", persistent = false)				
	})
	public void validarTiempoLimitePrevencionTramite() {
		LOGGER.info(">>> INICIA SCHEDULE PARA VALIDAR EL TIEMPO LIMITE DE PREVENCIÓN PARA UN TRÁMITE " + new Date());
		ProyectoDTO proyectoDTO = null;
		DetGestionUsuarioDTO detGestionUsuarioDTO = null;
		
		try {
			proyectoDTO = proyectoDAO.consultaProyecto();
			
			if(proyectoDTO != null && proyectoDTO.getIdProyecto() != null) {
				detGestionUsuarioDTO = usuarioGestionDAO.consultaUsuarioGestion(proyectoDTO.getIdProyecto());
				
				if(detGestionUsuarioDTO != null && detGestionUsuarioDTO.isHabilitaPrevencion()) {
					List<TramiteDTO> lstTramitesEnPrevencion = formularioDAO.obtenerTamitesPorEstatusPrevencion(Constantes.ID_ESTATUS_CORRECIONES);
					
					if(lstTramitesEnPrevencion != null) {
						
						for (TramiteDTO tramiteDTO : lstTramitesEnPrevencion) {

							if((new Date()).after(BeanUtils.sumarDiasFecha(tramiteDTO.getFechaRevision(), detGestionUsuarioDTO.getDiasSubsanarPrevencion()))) {
								tramiteDTO.setFechaRevision(new Date());
								tramiteDTO.getCatEstatusTramiteDTO().setIdEstatusTramite(Constantes.ID_ESTATUS_RECHAZADO);
								tramiteDTO.setUsuarioRevisor(new UsuarioDTO(Constantes.ID_USUARIO_SCHEDULE_PREVENCION));
								tramiteDTO.setRespuestaFolioConclusion("Vencimiento del periodo para subsanar la prevención");
								tramiteDTO.setProyectoDTO(proyectoDTO);
								formularioFacade.notificarPrevencionVencidaTramite(tramiteDTO, proyectoDTO.getNombreProyecto());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al consultar la información del proyecto o realizar la validación del tiempo limite de prevención: ", e);
		}
		LOGGER.info(">>> TERMINA SCHEDULE PARA VALIDAR EL TIEMPO LIMITE DE PREVENCIÓN PARA UN TRÁMITE " + new Date());
	}
	
}

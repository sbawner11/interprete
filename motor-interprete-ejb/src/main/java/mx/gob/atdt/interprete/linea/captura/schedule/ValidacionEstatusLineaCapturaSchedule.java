package mx.gob.atdt.interprete.linea.captura.schedule;

import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

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

import com.google.gson.Gson;

import mx.gob.atdt.interprete.application.DetSecurityDomainLineasCapturaApplication;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.DetLineaCapturaDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dao.TramitesDAO;
import mx.gob.atdt.interprete.dto.CatEstatusLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.DetLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.LineaCapturaDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.exception.InterpreteException;
import mx.gob.atdt.interprete.facade.LineaCapturaFacade;
import mx.gob.atdt.interprete.linea.captura.client.EstatusLineaCapturaClient;
import mx.gob.atdt.interprete.linea.captura.dto.RequestConsultaLCDTO;
import mx.gob.atdt.interprete.linea.captura.dto.ResponseEstatusLCDTO;

@Singleton
@Startup
public class ValidacionEstatusLineaCapturaSchedule {
	 
	private static final Logger LOGGER = LoggerFactory.getLogger(ValidacionEstatusLineaCapturaSchedule.class);
	
	@Inject
    private DetSecurityDomainLineasCapturaApplication securityDomainLineasCapturaApplication;
	
	@Inject
    private ProyectoDAO proyectoDAO;
	
	@Inject
    private TramitesDAO tramitesDAO;
	
	@Inject 
	private LineaCapturaFacade lineaFacturaFacade;
	
	@Inject 
	private DetLineaCapturaDAO detLineaCapturaDAO;
	
	/**
	 * Se establece tiempo de transacción a 2 hrs
	 * El schedule se ejecutará de 6 am a 22 pm cada 4 hrs
	 */
    @TransactionTimeout(value = 2, unit = TimeUnit.HOURS)     
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Schedules({
        @Schedule(hour = "6,10,14,18,22", minute = "00", persistent = false)
    })
	public void validarEstatusLineasCaptura() {
    	Gson gson = new Gson();
    	long inicio = System.nanoTime();
    	LOGGER.info(">>>INICIA SCHEDULE ValidacionEstatusLineaCapturaSchedule");
    	try {
    		ProyectoDTO proyectoDTO = proyectoDAO.consultaProyecto();
	            if (proyectoDTO == null || proyectoDTO.getIdProyecto() == null) {
	                LOGGER.warn("Proyecto no disponible. Abortando proceso.");
	                return;
	            }

	            if(proyectoDTO.isProyectoLineaCaptura()) {
	            	DetLineaCapturaDTO detLineaCapturaDTO = detLineaCapturaDAO.buscarPorIdProyecto(proyectoDTO.getIdProyecto());
	            	RequestConsultaLCDTO requestConsulta = new RequestConsultaLCDTO();
	            	Predicate<LineaCapturaDTO> prTramiteDTO = BeanUtils::isNotNull;
	            	Predicate<CatEstatusLineaCapturaDTO> prLineaDTO = p -> p.getIdEstatusLineaCaptura().equals(Constantes.ID_LC_ESTATUS_PENDIENTE);
	            	requestConsulta.setIdDependencia(detLineaCapturaDTO.getDependenciaPagoDTO().getIdDependenciaPago());
	            	List<TramiteDTO> lstTramites = tramitesDAO.consultaTramitePorEstatus(Constantes.ID_ESTATUS_PENDIENTE_PAGO);
	            		lstTramites.stream()
	            					.filter( p-> prTramiteDTO.test(p.getLineaCapturaDTO()) && prLineaDTO.test(p.getLineaCapturaDTO().getCatEstatusLineaCaptura()))
	            					.forEach(item ->consultaEstatusLineaCaptura(item, gson, requestConsulta));
	            }		
        	
        } catch (Exception e) {
            LOGGER.error("Ocurrió un error durante la ejecución del schedule ValidacionEstatusLineaCapturaSchedule: ", e);
        }
    	long fin = System.nanoTime();
    	LOGGER.info(">>>fINALIZA SCHEDULE ValidacionEstatusLineaCapturaSchedule Duración: {{}} seg.", TimeUnit.NANOSECONDS.toSeconds((fin-inicio)));
	}
    
    /**
     * Metodo auxiliar para orquestar consulta estatus LC y 
     * validaciones aplicadas al tramite
     * @param tramite datos tramite
     * @param gson gson de google
     */
    public void consultaEstatusLineaCaptura(TramiteDTO tramite, final Gson gson, RequestConsultaLCDTO requestConsulta) {
		///queda pendiente esta validacion respecto al estado del tramite
		ResponseEstatusLCDTO estatusLCDTO = null;
		EstatusLineaCapturaClient consultaLCClient = new EstatusLineaCapturaClient();
		Predicate<ResponseEstatusLCDTO> prResponseEstatus = p -> p.getCodigo().equals(0);
		try {
			LineaCapturaDTO lineaCapturaDTO = tramite.getLineaCapturaDTO();
			requestConsulta.setIdSolicitud(lineaCapturaDTO.getSolicitudLineaCaptura());
			requestConsulta.setLineaCaptura(lineaCapturaDTO.getLineaCaptura());
			LOGGER.info("requestConsulta estatus LC tramite: {} ", requestConsulta);
			estatusLCDTO = consultaLCClient.consultaEstatusLC(securityDomainLineasCapturaApplication.getSecurityDomainLineasCapturaDTO().getUrlSistema(), requestConsulta);
			LOGGER.info("termina consulta estatus LC tramite: {} ", estatusLCDTO);
			if(prResponseEstatus.test(estatusLCDTO)) {
				lineaFacturaFacade.procesarRespuestaEstatusLC(estatusLCDTO, tramite, gson);	
			}else {
				LOGGER.info("error al consultar Linea de captura: {} , mensajeError: {}", tramite.getLineaCapturaDTO().getLineaCaptura(),estatusLCDTO.getMensajeError());
			}
		} catch (URISyntaxException | ConnectException | InterpreteException e) {
			LOGGER.error("Ocurrio un error al consultar el estatus de la linea de captura:: ", e);
		}
	}
    
}

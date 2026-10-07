package mx.gob.atdt.interprete.facade;

import java.util.Date;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dao.TramitesDAO;
import mx.gob.atdt.interprete.dto.ReporteContadoresProyectosDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;
import mx.gob.atdt.interprete.common.infra.Environment;

@Stateless
@LocalBean
public class ReporteContadoresProyectoFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReporteContadoresProyectoFacade.class);
	
	@Inject
	private TramitesDAO tramitesDAO;

	@Inject
	private ProyectoDAO proyectoDAO;
	
	@Inject
	private EstructuraFormularioDAO estructuraFormularioDAO;
	
	/**
	 * Método que realiza el conteo de trámites por cada estatus.
	 */
	public ReporteContadoresProyectosDTO consultarContadoresProyecto() {
		ReporteContadoresProyectosDTO contadores = new ReporteContadoresProyectosDTO();
		
		//Se obtiene el nombre del proyecto y se agrega a los contadores.
		contadores.setProyectoDTO(proyectoDAO.consultaIdNombreProyecto());
		contadores.setFechaUltimaActualizacion(new Date());
		
		//Se obtiene la versión de base de datos.
		contadores.setVersionBaseDatos(estructuraFormularioDAO.consultaVersionBD());
		
		//Se obtiene la versión del Ear.
		contadores.setVersionEar(Environment.getAppGitVersion());
		try {
			//Se valida si existe la tabla de trámites
			if(estructuraFormularioDAO.existeTablaTramites()) {		
				//Se obtienen contadores del proyecto.
				ReporteContadoresProyectosDTO contadoresTmp = tramitesDAO.consultaContadoresProyecto();				
				contadores.setSubtotalCaptura(contadoresTmp.getSubtotalCaptura());
				contadores.setSubtotalEnviado(contadoresTmp.getSubtotalEnviado());			
				contadores.setSubtotalCorreccion(contadoresTmp.getSubtotalCorreccion());
				contadores.setSubtotalCorregido(contadoresTmp.getSubtotalCorregido());
				contadores.setSubtotalRevisado(contadoresTmp.getSubtotalRevisado());			
				contadores.setSubtotalRechazado(contadoresTmp.getSubtotalRechazado());
				contadores.setSubtotalAceptado(contadoresTmp.getSubtotalAceptado());			
				contadores.setTotalRegistros(contadoresTmp.getTotalRegistros());				
				
				//Se obtiene el nombre del proyecto y se agrega a los contadores.
				contadores.setProyectoDTO(proyectoDAO.consultaIdNombreProyecto());
				if(estructuraFormularioDAO.existeTablaTramitesFirma()) {
					//Se obtiene contador de trámites firmados
					contadores.setSubtotalFirmado(tramitesDAO.consultaTramitesFirmados());				
				}
			} 
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al realizar la consulta de contadores:: ", e);
		}		
		return contadores;
	}
	
	/**
	 * Método que realiza el conteo de trámites por cada estatus en un rango de fechas
	 */
	public ReporteContadoresProyectosDTO consultarContadoresProyectoPorFechas(TramiteDTO tramiteBusqueda) {
		ReporteContadoresProyectosDTO contadores = new ReporteContadoresProyectosDTO();
		
		//Se obtiene el nombre del proyecto y se agrega a los contadores.
		contadores.setProyectoDTO(proyectoDAO.consultaIdNombreProyecto());
		contadores.setFechaUltimaActualizacion(new Date());
		
		try {
			//Se valida si existe la tabla de trámites
			if(estructuraFormularioDAO.existeTablaTramites()) {		
				//Se obtienen contadores del proyecto.
				ReporteContadoresProyectosDTO contadoresTmp = tramitesDAO.consultaContadoresProyectoPorFechas(tramiteBusqueda);				
				contadores.setSubtotalCaptura(contadoresTmp.getSubtotalCaptura());
				contadores.setSubtotalEnviado(contadoresTmp.getSubtotalEnviado());			
				contadores.setSubtotalCorreccion(contadoresTmp.getSubtotalCorreccion());
				contadores.setSubtotalCorregido(contadoresTmp.getSubtotalCorregido());
				contadores.setSubtotalRevisado(contadoresTmp.getSubtotalRevisado());			
				contadores.setSubtotalRechazado(contadoresTmp.getSubtotalRechazado());
				contadores.setSubtotalAceptado(contadoresTmp.getSubtotalAceptado());			
				contadores.setTotalRegistros(contadoresTmp.getTotalRegistros());
				
				//Se obtiene el nombre del proyecto y se agrega a los contadores.
				contadores.setProyectoDTO(proyectoDAO.consultaIdNombreProyecto());
				if(estructuraFormularioDAO.existeTablaTramitesFirma()) {
					//Se obtiene contador de trámites firmados
					contadores.setSubtotalFirmado(tramitesDAO.consultaTramitesFirmados());				
				}
			} 
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al realizar la consulta de contadores por fecha:: ", e);
		}		
		return contadores;
	}
}
package mx.gob.atdt.interprete.reportes.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.ReporteContadoresProyectosDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.facade.ReporteContadoresProyectoFacade;
import mx.gob.atdt.interprete.tramites.bean.BandejaValidacionTramitesBean;
import mx.gob.atdt.interprete.util.WebResources;


@Named("reporteContadoresProyectosBean")
@SessionScoped
public class ReporteContadoresProyectosBean implements Serializable {

	private static final long serialVersionUID = -3247092002353191738L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ReporteContadoresProyectosBean.class);
		
	@Inject
	private BandejaValidacionTramitesBean bandejaValidacionTramitesBean;
	
	@Inject
	private ReporteContadoresProyectoFacade reporteContadoresProyectoFacade;

	@Inject
	AuthenticatorBean authenticatorBean;
	
	private List<ReporteContadoresProyectosDTO> lstContadoresProyectos;
	
	private TramiteDTO tramiteBusqueda;
	private Date fechaMaxima;

	/**
	 * Método que inicializa la vista de Dashboard
	 * 
	 * @return
	 */
	public String inicializar() {
		fechaMaxima = new Date();
		tramiteBusqueda = new TramiteDTO();
		lstContadoresProyectos = new ArrayList<ReporteContadoresProyectosDTO>(); 
		lstContadoresProyectos.add(reporteContadoresProyectoFacade.consultarContadoresProyecto());		
		if (BeanUtils.isNull(lstContadoresProyectos) || lstContadoresProyectos.isEmpty()) {
			WebResources.validationMessage("msj_dashboard_sin_resultados", true);
		} else {
			WebResources.successMessage("msj_dashboard_resultados", true);			
		}
		return Constantes.RETURN_DASHBOARD_PAGE + Constantes.JSF_REDIRECT;
	}
	
	/**
	 * Método que realiza la búsqueda de trámites con los filtros ingresados.
	 */
	public void filtrarTramites() {				
		boolean isFiltrosCorrectos = true;
		if(BeanUtils.isNotNull(tramiteBusqueda.getFechaDesde()) && BeanUtils.isNotNull(tramiteBusqueda.getFechaHasta())) {
			if(tramiteBusqueda.getFechaDesde().after(tramiteBusqueda.getFechaHasta())) {
				isFiltrosCorrectos = false;
				WebResources.validationMessage("msj_fechas_incorrectas", false);
			}			
			if(tramiteBusqueda.getFechaDesde().compareTo(tramiteBusqueda.getFechaHasta()) == 0) {
				isFiltrosCorrectos = false;
				WebResources.validationMessage("msj_fechas_iguales", false);
			}
		} 
		if((BeanUtils.isNotNull(tramiteBusqueda.getFechaDesde()) && BeanUtils.isNull(tramiteBusqueda.getFechaHasta())) ||
				(BeanUtils.isNull(tramiteBusqueda.getFechaDesde()) && BeanUtils.isNotNull(tramiteBusqueda.getFechaHasta()))) {
			isFiltrosCorrectos = false;
			WebResources.validationMessage("msj_fechas_incompletas", false);
		}
		if(isFiltrosCorrectos) {
			try {				
				lstContadoresProyectos = new ArrayList<ReporteContadoresProyectosDTO>(); 
				lstContadoresProyectos.add(reporteContadoresProyectoFacade.consultarContadoresProyectoPorFechas(tramiteBusqueda));		
				if (BeanUtils.isNull(lstContadoresProyectos) || lstContadoresProyectos.isEmpty()) {
					WebResources.validationMessage("msj_dashboard_sin_resultados", true);
				} else {
					WebResources.successMessage("msj_dashboard_resultados", true);			
				}
			} catch (Exception e) {
				LOGGER.error("Ocurrió un error al filtrar los trámites por fechas:: ", e);
				WebResources.errorMessage("msj_error_busqueda", true);
			}
		} else {
			lstContadoresProyectos = new ArrayList<ReporteContadoresProyectosDTO>();
		}
	}
	
	/**
	 * Método que inicializa los valores de búsqueda
	 */
	public void limpiar() {	
		tramiteBusqueda = new TramiteDTO();
		lstContadoresProyectos = new ArrayList<ReporteContadoresProyectosDTO>();
	}
	
	/**
	 * Método que inicializa la vista Administrar proyectos
	 * 
	 * @return
	 */
	public String salir() {
		return bandejaValidacionTramitesBean.inicializar();
	}

	/** GETTER´s y SETTER´s **/
	
	/**
	 * @return the lstContadoresProyectos
	 */
	public List<ReporteContadoresProyectosDTO> getLstContadoresProyectos() {
		return lstContadoresProyectos;
	}

	/**
	 * @param lstContadoresProyectos the lstContadoresProyectos to set
	 */
	public void setLstContadoresProyectos(List<ReporteContadoresProyectosDTO> lstContadoresProyectos) {
		this.lstContadoresProyectos = lstContadoresProyectos;
	}

	/**
	 * @return the tramiteBusqueda
	 */
	public TramiteDTO getTramiteBusqueda() {
		return tramiteBusqueda;
	}

	/**
	 * @param tramiteBusqueda the tramiteBusqueda to set
	 */
	public void setTramiteBusqueda(TramiteDTO tramiteBusqueda) {
		this.tramiteBusqueda = tramiteBusqueda;
	}

	/**
	 * @return the fechaMaxima
	 */
	public Date getFechaMaxima() {
		return fechaMaxima;
	}

	/**
	 * @param fechaMaxima the fechaMaxima to set
	 */
	public void setFechaMaxima(Date fechaMaxima) {
		this.fechaMaxima = fechaMaxima;
	}	
}
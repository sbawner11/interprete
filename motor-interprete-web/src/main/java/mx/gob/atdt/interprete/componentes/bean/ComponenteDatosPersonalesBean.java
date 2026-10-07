package mx.gob.atdt.interprete.componentes.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.client.CurpRESTClient;
import mx.gob.atdt.interprete.application.SeccionSecurityCurpApplication;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesDTO;
import mx.gob.atdt.interprete.dto.DetSecurityDomainDTO;
import mx.gob.atdt.interprete.exception.InterpreteException;
import mx.gob.atdt.interprete.formularios.bean.RegistrarFormularioBean;
import mx.gob.atdt.interprete.util.WebResources;

@Named
@SessionScoped
public class ComponenteDatosPersonalesBean implements Serializable{
	
	private static final long serialVersionUID = 7189755918957049948L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ComponenteDatosPersonalesBean.class);
	
	@Inject
	private RegistrarFormularioBean registrarFormularioBean;
	
	@Inject
	private SeccionSecurityCurpApplication seccionSecurityCurpApplication;
	
	private CurpRESTClient curpRestClient;	
		
	@PostConstruct
	public void inicializarComponente() {
//		LOGGER.info("ComponenteDatosPersonalesBean ::::   " + this.toString());
	}	
	
	/**
	 * Método que realiza la consulta del curp ingresado para obtener los datos de Nombres y Apellidos medieante el servicio
	 * de Renapo.
	 * 
	 * @param curp
	 * @param datosPersonalesDTO
	 */
	public void consultarCurp(String curp, ComponenteDatosPersonalesDTO datosPersonalesDTO) {
		curpRestClient = new CurpRESTClient();
		DetSecurityDomainDTO  detSecurityDomainCurpDTO = seccionSecurityCurpApplication.getDetSecurityDomainCurpDTO();
		if(BeanUtils.isNotNull(curp) &&	BeanUtils.isNotEmpty(curp) && curp.trim().length() == Constantes.LONGITUD_CURP) {
			try {			
				ComponenteDatosPersonalesDTO datosPersonalesTmp = curpRestClient.obtenerDatosCurp(curp, detSecurityDomainCurpDTO);
				
				if (datosPersonalesTmp != null) {
					/**Se accede al map de respuestas para colocar las respuestas, en la key indicada por el componente actual**/
					registrarFormularioBean.getMapRespuestas().put(datosPersonalesDTO.getCurp(), datosPersonalesTmp.getCurp());
					registrarFormularioBean.getMapRespuestas().put(datosPersonalesDTO.getNombre(), datosPersonalesTmp.getNombre());
					registrarFormularioBean.getMapRespuestas().put(datosPersonalesDTO.getpApellido(), datosPersonalesTmp.getpApellido());
					registrarFormularioBean.getMapRespuestas().put(datosPersonalesDTO.getsApellido(), datosPersonalesTmp.getsApellido());
					
				} else {
					inicializarRespuestas(datosPersonalesDTO, "msj_error_curp_invalida");
				}
			} catch (InterpreteException e) {
				LOGGER.error("Error al consultar CURP: ", e);
				inicializarRespuestas(datosPersonalesDTO, e.getMessage());
			} 
		} else {
			inicializarRespuestas(datosPersonalesDTO, "msj_error_caracteres_curp");
		}
	}	
	
	/**
	 * Método auxiliar que inicializa las respuestas de búsqueda por CURP cuando el servicio retorna algún error o no se cumple alguna validación.
	 * @param datosPersonalesDTO
	 */
	private void inicializarRespuestas(ComponenteDatosPersonalesDTO datosPersonalesDTO, String mensaje) {
		registrarFormularioBean.getMapRespuestas().put(datosPersonalesDTO.getCurp(), null);
		registrarFormularioBean.getMapRespuestas().put(datosPersonalesDTO.getNombre(), null);
		registrarFormularioBean.getMapRespuestas().put(datosPersonalesDTO.getpApellido(), null);
		registrarFormularioBean.getMapRespuestas().put(datosPersonalesDTO.getsApellido(), null);
		
		PrimeFaces.current().scrollTo("frmFormulario:componente_"+datosPersonalesDTO.getIdComponente()+":txtCurp_"+datosPersonalesDTO.getIdComponente());
		FacesContext.getCurrentInstance().addMessage(FacesContext.getCurrentInstance().getViewRoot().findComponent("frmFormulario:componente_"+datosPersonalesDTO.getIdComponente()+":txtCurp_"+datosPersonalesDTO.getIdComponente()).getClientId(), 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, null, WebResources.getBundleMsg(mensaje)));
	}
	
}

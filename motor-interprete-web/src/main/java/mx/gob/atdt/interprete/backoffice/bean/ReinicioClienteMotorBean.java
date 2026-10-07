package mx.gob.atdt.interprete.backoffice.bean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.commons.db.FlywayIntegrator;
import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.exception.InterpreteException;
import mx.gob.atdt.interprete.util.WebResources;
import mx.gob.atdt.motor.client.BitacoraProyectoRESTClient;
import mx.gob.atdt.widget.application.IndexBean;

import java.io.Serializable;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author raul
 */
@Named
@SessionScoped
public class ReinicioClienteMotorBean implements Serializable {

	private static final long serialVersionUID = -6576631731244437967L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ReinicioClienteMotorBean.class);

	@Inject
	private ProyectoDAO proyectoDAO;
	
	@Inject
	private FlywayIntegrator flywayIntegrator;
	
	@Inject
	private AuthenticatorBean authenticatorBean;
	
	@Inject
	private IndexBean indexBean;

	public String inicializar() {
		return Constantes.RETURN_REINICIO_CLIENTE + Constantes.JSF_REDIRECT;
	}
	
	public String reiniciarCliente() {
		String urlRetorno = "";
		try {
			if(permiteReiniciarCliente()) {		
				
				try {
					//Se valida el proyecto que existe en la BD actual, si existe proyecto sincronizado
					//se invoca servicio del Motor para que la bitácora del proyecto sea reiniciada.
					List<ProyectoDTO> lstProyectos = proyectoDAO.buscarTodos();
					if(BeanUtils.isNotNull(lstProyectos)) {
						BitacoraProyectoRESTClient bitacoraProyeyectoClient = new BitacoraProyectoRESTClient();
						//Se envía al servicio el único proyecto que debe existir en la BD del cliente actual.					
						boolean bitacoraActualizada = bitacoraProyeyectoClient.actualizarBitacoraProyecto(lstProyectos.get(0));
						if(bitacoraActualizada == false) {
							WebResources.addSuccessMessage("msj_reinicio_bitacora_incorrecto", true);		
						}					
					}	
				} catch (InterpreteException | ConnectException | URISyntaxException | JSONException e) {
					LOGGER.error("Ocurrió un error al  realizar el reinicio de bitácora del proyecto en el motor: ", e);
					WebResources.addSuccessMessage("msj_reinicio_bitacora_incorrecto", true);
				} 
				
				flywayIntegrator.reiniciarBasedeDatosCliente();
				WebResources.addSuccessMessage("msj_reinicio_correcto", true);
				
				indexBean.setProyectoDTO(null);
				urlRetorno = authenticatorBean.cerrarSesionUsuarioFuncionario();
			}else{
				WebResources.addValidationMessage("msj_reinicio_no_posible", false);
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al  realizar el reinicio del cliente ", e);
			WebResources.addErrorMessage("msj_error", false);
		}
		return urlRetorno;
	}
	
	public boolean permiteReiniciarCliente() {
		return (
			(	Environment.getAppProfile().compareTo("dev") == 0 || 
				Environment.getAppProfile().compareTo("staging") == 0 || 
				Environment.getAppProfile().compareTo("local") == 0 ) 
			&& 
			(	authenticatorBean.isRolAdministrador() || 
				authenticatorBean.isRolAdministradorDatosTecnicos() )
		)
		||
		(	Environment.getAppProfile().compareTo("prod") == 0 && 
			authenticatorBean.isRolAdministradorDatosTecnicos()
		);
	}
	
}
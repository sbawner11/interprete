package mx.gob.atdt.interprete.backoffice.bean;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.BitSincronizacionDAO;
import mx.gob.atdt.interprete.dao.DetEstadoSistemaDAO;
import mx.gob.atdt.interprete.dto.BitSincronizacionDTO;
import mx.gob.atdt.interprete.dto.DetEstadoSistemaDTO;
import mx.gob.atdt.interprete.dto.CatEstadosSistemaDTO;
import mx.gob.atdt.interprete.util.WebResources;
import mx.gob.atdt.motor.client.SincronizacionProyectoRESTClient;
import mx.gob.atdt.widget.application.IndexBean;

import java.io.Serializable;
import java.util.Date;
import java.net.URL;

/**
 * Para realizar la Sincronización del Proyecto a petición del Cliente
 * Primero se verifica el estatus del proyecto,
 * dependiendo de la respuesta se realiza o no la Sincronización.
 */
@Named
@SessionScoped
public class SolicitarSincronizacionBean implements Serializable {

	private static final long serialVersionUID = -6576631731244437967L;

	private static final Logger LOGGER = LoggerFactory.getLogger(SolicitarSincronizacionBean.class);
	
	@Inject
	private AuthenticatorBean authenticatorBean;
	
	@Inject
	private BitSincronizacionDAO bitSincronizacionDAO;
	
	@Inject
	SeccionesProyectoBean seccionesProyectoBean;
	
	@Inject
	private DetEstadoSistemaDAO detEstadoSistemaDAO;
	
	@Inject
	private IndexBean indexBean;
	
	/**
	 * Método que inicializa la vista para confirmar el inicio de la sincronización a petición del intérprete o cliente
	 * @return
	 */
	public String inicializar() {
		return Constantes.RETURN_SOLICITAR_SINCRONIZACION + Constantes.JSF_REDIRECT;
	}
	
	/**
	 * Método que realiza la petición de sincronización del proyecto al motor transaccional
	 * @return
	 */
	public String solicitaSincronizacion() {
		String urlRetorno = "";
		
		try {
			BitSincronizacionDTO solSinc = new BitSincronizacionDTO();
	        solSinc.setFechaSincronizacion(new Date());
	        solSinc.setUsuarioDTO(authenticatorBean.getUsuarioLogueado());
	        bitSincronizacionDAO.guardar(solSinc);
	        
	        Long idProyecto = seccionesProyectoBean.getProyectoDTO().getIdProyecto();
	        
	        URL urlDomain = new URL(seccionesProyectoBean.getSecurityDomainDTO().getUrlSistema());
	        String domainCliente = urlDomain.getHost();
	        LOGGER.info("Sincronizar proyecto >>> Validando proyecto " +  idProyecto.toString() + " desde " + domainCliente);
			
			 SincronizacionProyectoRESTClient clienteRest = new SincronizacionProyectoRESTClient();
			 String respuesta = clienteRest.validarSincronizacion(idProyecto, domainCliente);
			 
			 ObjectMapper mapper = new ObjectMapper();
			 JsonNode respJson = mapper.readTree(respuesta);
			 int respCodigo = respJson.path("codigo").asInt();			 

			 switch(respCodigo) {
			 	case Constantes.SINC_PROYECTO_ESTATUS_INCORRECTO:				 
			 		WebResources.addSuccessMessage("msj_sincronizacion_estatus_incorrecto", false);
			 		break;
			 	case Constantes.SINC_PROYECTO_SIN_CAMBIOS:
			 		WebResources.addSuccessMessage("msj_sincronizacion_proy_sin_cambios", false);
			 		break;
			 	case Constantes.SINC_PROYECTO_NO_ENCONTRADO:
			 		WebResources.addSuccessMessage("msj_sincronizacion_proy_no_existe", false);
			 		break;
			 	case Constantes.SINC_IP_INVALIDA:
			 		WebResources.addSuccessMessage("msj_sincronizacion_ip_invalida", false);
			 		break;
			 	case Constantes.SINC_PROYECTO_SINCRONIZADO:
			 		WebResources.addSuccessMessage("msj_sincronizacion_proy_sinc", false);
			 		break;
			 	case Constantes.SINC_PROYECTO_VALIDO:
			 		DetEstadoSistemaDTO estadoSistema = detEstadoSistemaDAO.buscarEstadoSistema();
			 		if (estadoSistema != null) {
			 			urlRetorno = iniciaSincronizacion(estadoSistema, idProyecto);
			 		} else {
			 			LOGGER.warn("No se encontró estado del sistema para actualizar.");
			 		}
			 		break;
			 	default:
			 		LOGGER.warn("Sincronizar Proyecto >>> Código de respuesta no identificado :." + respCodigo);
			 		WebResources.addSuccessMessage("msj_sincronizacion_fallida", false);
			 		break;				
			 }
		} catch(Exception e) {
			LOGGER.error("Ocurrió un error al intentar sincronizar: ", e);
			WebResources.addErrorMessage("msj_error", false);			
		}
		return urlRetorno;
	}
	
	private String iniciaSincronizacion(DetEstadoSistemaDTO estadoSistema, Long idProyecto) {
		String urlRetorno = "";
		try {
			estadoSistema.setCatEstadosSistemaDTO(new CatEstadosSistemaDTO(Constantes.ID_ESTADO_SISTEMA_MANTENIMIENTO));
			estadoSistema.setFechaUltimaActualizacion(new Date());
			detEstadoSistemaDAO.actualizar(estadoSistema);
			
			SincronizacionProyectoRESTClient clienteRest = new SincronizacionProyectoRESTClient();
			clienteRest.iniciarSincronizacion(idProyecto);

	        /**Se inicializan objetos que son mostrados página index para poder visualizar el estatus En mantenimiento,
			  * Al finalizar el envío de información desde el motor, como paso final actualizará nuevamente el cliente a En linea**/
			indexBean.setProyectoDTO(null);
			indexBean.setHomeProgramaDTO(null);
			
			LOGGER.info("Sincronizar proyecto >>> Sincronización manual realizada correctamente");
			
			/**Se cierra la sesión del usuario para mostrar pantalla de Mantenimiento**/
			urlRetorno = authenticatorBean.cerrarSesionUsuario();
		} catch (Exception ex) {
			LOGGER.error("Error durante la actualización del estado del sistema o la sincronización:", ex);
			WebResources.addErrorMessage("msj_sincronizacion_error_actualizacion", false);
		}
		return urlRetorno;
	}
	
}
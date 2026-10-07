package mx.gob.atdt.interprete.backoffice.bean;

import java.sql.Connection;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.estructura.formulario.dao.AsignarPermisosBDDAO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;
import mx.gob.atdt.interprete.tramites.bean.BandejaValidacionTramitesBean;
import mx.gob.atdt.interprete.util.WebResources;

import java.io.Serializable;

@Named
@SessionScoped
public class AsignarPermisosBDBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3307965800926286526L;

	private static final Logger LOGGER = LoggerFactory.getLogger(AsignarPermisosBDBean.class);

	@Inject
	private AuthenticatorBean authenticatorBean;
	
	@Inject
	private BandejaValidacionTramitesBean bandejaValidacionTramitesBean;
	
	@Inject
	private EstructuraFormularioDAO estructuraFormularioDAO;
	
	@Inject
	private AsignarPermisosBDDAO asignarPermisosBDDAO;
	
	@Resource(lookup="java:jboss/datasources/interpreteMotorDS")
    private javax.sql.DataSource dataSource;
	
	private String nombreBD;
	private String ipHostBD;
	private String cuentaUsuario;
		
	/**
	 * Método que inicializa la vista para el registro de permisos de usuario en la BD.
	 * @return
	 */
	public String inicializar() {
		cuentaUsuario = null;
		nombreBD = "No disponible";
		ipHostBD = "No disponible";
		consultarInformacionBD();
		return Constantes.RETURN_PERMISOS_BD + Constantes.JSF_REDIRECT;
	}
	
	/**
	 * Método que realiza la asignación de permisos para la cuenta ingresada.
	 * @return
	 */
	public String asignarPermisos() {
		String urlRetorno = "";
		try {
			if(permiteAsignarPermisos()) {				
				/**Se revisa si ya existe la tabla de trámites, para poder asignar permisos**/
				if(estructuraFormularioDAO.existeTablaTramites()) {					
					if(asignarPermisosBDDAO.existeUsuario(cuentaUsuario.trim())) {
						asignarPermisosBDDAO.otorgarPermisosUsuario(cuentaUsuario);
						WebResources.addSuccessMessage("msj_permisos_bd_correctos", true);				
						urlRetorno = inicializar();	
					} else {
						WebResources.addValidationMessage("msj_permisos_bd_cuenta_inexistente", false);	
					}
				} else {
					WebResources.addValidationMessage("msj_permisos_bd_sin_tramites", false);					
				}				
			}else{
				WebResources.addValidationMessage("msj_permisos_bd_no_posible", false);
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al realizar la asignación de permisos ", e);
			WebResources.addErrorMessage("msj_permisos_bd_incorrectos", false);
		}
		return urlRetorno;
	}
	
	/**
	 * Método que valida si es posible realizar la asignación de permisos a BD a la cuenta ingresada.
	 * @return
	 */
	public boolean permiteAsignarPermisos() {
		return (BeanUtils.isNotNull(cuentaUsuario)) && (BeanUtils.isNotEmpty(cuentaUsuario)) && (authenticatorBean.isRolAdministrador() || authenticatorBean.isRolAdministradorDatosTecnicos());
	}
	
	/**
	 * Método que inicializa la vista de trámites para el usuario actual
	 * @return
	 */
	public String cancelar() {
		return bandejaValidacionTramitesBean.inicializar();
	}
	
	/**
	 * Método auxiliar que realiza la consulta de información de la BD.
	 */
	public void consultarInformacionBD() {
		if (dataSource == null) {
			LOGGER.error("No se encontró un datasource para para consultar la información de la Base de Datos.");			
	    } else {	    	
	    	try(Connection conn = dataSource.getConnection()) {			
				String[] valoresUrl = separarCadena(conn.getMetaData().getURL(), "://");
				String[] valoresHost = separarCadena(valoresUrl[1], ":");				
				nombreBD = conn.getCatalog();
				ipHostBD = valoresHost[0];				
			} catch (SQLException e) {
				LOGGER.error("No fue posible leer la metadata del datasource para obtener nombre de BD e Ip o Host.", e);				
			} 
	    }
	}
	
	/**
	 * Método auxiliar que divide de una cadena indicando el separador.
	 * @param cadena
	 * @param separator
	 * @return
	 */
	private String[] separarCadena(String cadena, String separator){        
	    String[] parts = null;   
	    if(separator.equals("|")|| separator.equals("\\")||separator.equals(".")||separator.equals("^")||separator.equals("$")
	            ||separator.equals("?")||separator.equals("*")||separator.equals("+")||separator.equals("(")||separator.equals(")")
	            ||separator.equals("{")||separator.equals("[")){
	        parts = cadena.split("\\"+separator);       
	    } else {
	        parts = cadena.split(separator);
	    }    
	    return parts;
	}
	
	
	/**GETTER´s y SETTER´s**/

	/**
	 * @return the cuentaUsuario
	 */
	public String getCuentaUsuario() {
		return cuentaUsuario;
	}

	/**
	 * @param cuentaUsuario the cuentaUsuario to set
	 */
	public void setCuentaUsuario(String cuentaUsuario) {
		this.cuentaUsuario = cuentaUsuario;
	}

	/**
	 * @return the nombreBD
	 */
	public String getNombreBD() {
		return nombreBD;
	}

	/**
	 * @param nombreBD the nombreBD to set
	 */
	public void setNombreBD(String nombreBD) {
		this.nombreBD = nombreBD;
	}

	/**
	 * @return the ipHostBD
	 */
	public String getIpHostBD() {
		return ipHostBD;
	}

	/**
	 * @param ipHostBD the ipHostBD to set
	 */
	public void setIpHostBD(String ipHostBD) {
		this.ipHostBD = ipHostBD;
	}	
}
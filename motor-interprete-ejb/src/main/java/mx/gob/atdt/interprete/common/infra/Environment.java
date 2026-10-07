package mx.gob.atdt.interprete.common.infra;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.utils.Constantes;

/**
 * Clase utilizada para poder tener valores del profile en codigo Java. Tiene
 * Dependencia del archivo META-INF/env.properties
 * 
 * @author raul.soto
 *
 */
public final class Environment {

	/**
     * Logger de la clase
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Environment.class);
    
    /**
     * Indica el profile utilizado para la construccion de la aplicacion
     */
    private static String appProfile = "";
    
    /**
     * Indica la versión de código con la que se contruye la aplicacion
     */
    private static String appGitVersion;
    
    /**
     * Indica si mostrara los mensajes de tiempo de respuesta de los EJB
     * Stateless
     */
    private static boolean ejbLog = false;   
    
    /**
     * Indica si esta habilitado la escritura de fases JSF
     */
    private static boolean jsfLifeCycle = false;

    /**
     * Indica si mostrara los mensajes de tiempo de respuesta de las peticiones HTTP
     */
    private static boolean webLog = false;
    
    /**
     * Indica la URL a la cual este sistema redireccionará para que la autenticacion se realice mediante Llave CDMX
     */
    private static String urlLoginCdmx = "";
    
	/**
     * Indica la URL a la cual el SDK de Autenticación de la CDMX debe redireccionar trás haber iniciado sesión el usuario.
     */    
    private static String urlServiceGetToken;
    
    private static String urlServiceGetDatosUsuario;
    
    private static String urlServiceGetRolesUsuario;
    
    private static String urlServiceLogout;
    
    private static String urlServiceSituacionRol;
    
    private static String urlServiceGetInformacionPersonaMoral;
   
	/**
	 * Ruta donde se almacenaran los documentos cargados en los trámites
	 */
	private static String pathClienteDocumentos;
	
	private static String pathArchivosTemporales;

	private static String urlConsultaTramite;
	
	private static String pathPlantillasClientePdf;
	
	/**
	 * URL del servidor de archivos que tiene el motor-admin
	 */
	private static String urlFileServerMotor;
	
	private static String pathFileServerMotor;
	
	private static String pathPropertiesCaptcha;
	
	/**
	 * Datos para consumo de servicio de reinicio de bitácora de proyectos en el  motor-admin
	 */
	
	private static String urlServiceBitacora;
	
	private static String userServiceBitacora;
	
	private static String passwordServiceBitacora;
	
	/**
	 * Datos para consumo de servicio para registro de contadores del proyecto en el motor-admin
	 */
	
	private static String urlServiceDashboard;
	
	private static String userServiceDashboard;
	
	private static String passwordServiceDashboard;
	
	
	/**
	 * Datos para consumo de servicio de firmado de trámites
	 */
	
	private static String urlServiceFirmaRegistro;
	
	private static String urlServiceFirmaConsulta;
	
	
	/**
	 * Datos para consumo de servicio de sincronización
	 */
	private static String urlServiceSincronizarProyecto;
	private static String urlServiceGetEstatusProyecto;
	private static String urlServiceValidaSincronizacion;
	
	/**
	 * Datos para consumo de servicio puente-dpa
	 */
	private static String urlServicePuenteDpa;
	private static String serviceConsultaEstatusLc;
	private static String serviceGeneraLc;
	/**
     * Inicializacion de variables utilizando como entrada el archivo
     * "META-INF/env.properties"
     */
    static {
        final Properties properties = new Properties();
        try {
            LOGGER.info(":: E n v i r o n m e n t  C o n f i g ::\n");
            properties.load(Environment.class.getClassLoader().getResourceAsStream("META-INF/env.properties"));
            
            appProfile = properties.getProperty("app.profile", "dev");
            LOGGER.info("ENV [appProfile:\t{}]", appProfile);
            
            appGitVersion = properties.getProperty("app.git.build.version");
            LOGGER.info("ENV [app.git.build.version:\t\t\t\t{}]", appGitVersion);
            
            ejbLog = Boolean.valueOf(properties.getProperty("ejb.log", Constantes.FALSE));
            LOGGER.info("ENV [ejbLog:\t\t{}]", ejbLog);
            
            jsfLifeCycle = Boolean.valueOf(properties.getProperty("jsf.lifeCycle", Constantes.FALSE));
            LOGGER.info("ENV [jsfLifeCycle:\t{}]", jsfLifeCycle);
            
            webLog = Boolean.valueOf(properties.getProperty("web.log", "true"));
            LOGGER.info("ENV [webLog:\t\t{}]", webLog);            
            
            urlLoginCdmx = properties.getProperty("login.urlLoginCdmx");
            LOGGER.info("ENV [login.urlLoginCdmx:\t\t{}]", urlLoginCdmx);
            
            urlServiceGetToken = properties.getProperty("services.cdmx.getToken");
            LOGGER.info("ENV [services.cdmx.getToken:\t\t{}]", urlServiceGetToken);
            
            urlServiceGetDatosUsuario = properties.getProperty("services.cdmx.getDatosUsuario");
            LOGGER.info("ENV [services.cdmx.getDatosUsuario:\t\t{}]", urlServiceGetDatosUsuario);
            
            urlServiceGetRolesUsuario = properties.getProperty("services.cdmx.getRolesUsuario");
            LOGGER.info("ENV [services.cdmx.getRolesUsuario:\t\t{}]", urlServiceGetRolesUsuario);
                        
            urlServiceLogout = properties.getProperty("services.cdmx.logout");
			LOGGER.info("ENV [services.cdmx.logout:\t\t{}]", urlServiceLogout);
			
			urlServiceSituacionRol = properties.getProperty("services.cdmx.getSituacionRol");
			LOGGER.info("ENV [services.cdmx.getSituacionRol:\t\t{}]", urlServiceSituacionRol);
			
			urlServiceGetInformacionPersonaMoral =  properties.getProperty("services.cdmx.getInformacionPersonaMoral");
			LOGGER.info("ENV [services.cdmx.getInformacionPersonaMoral:\t\t{}]", urlServiceGetInformacionPersonaMoral);
						
			pathClienteDocumentos = properties.getProperty("path.documentos.cliente.motor");
			LOGGER.info("ENV [path.documentos.cliente.motor:\t\t{}]", pathClienteDocumentos);

			pathArchivosTemporales = properties.getProperty("path.archivos.temporal");
			LOGGER.info("ENV [path.archivos.temporal:\t\t{}]", pathArchivosTemporales);
			
			urlConsultaTramite = properties.getProperty("url.consulta.tramite");
			LOGGER.info("ENV [url.consulta.tramite:\t\t{}]", urlConsultaTramite);
			
			urlFileServerMotor = properties.getProperty("url.fileserver.motoradmin");
			LOGGER.info("ENV [url.fileserver.motoradmin:\t\t{}]", urlFileServerMotor);
			
			pathFileServerMotor = properties.getProperty("path.fileserver.motor");
			LOGGER.info("ENV [path.fileserver.motor:\t\t{}]", pathFileServerMotor);
						
			pathPlantillasClientePdf = properties.getProperty("path.plantillas.cliente.pdf");
			LOGGER.info("ENV [path.plantillas.cliente.pdf:\t\t{}]", pathPlantillasClientePdf);
			
			pathPropertiesCaptcha = properties.getProperty("path.properties.captcha");
			LOGGER.info("ENV [path.properties.captcha:\t\t{}]", pathPropertiesCaptcha);
			
			/** SERVICIOS DEL MOTOR - ADMIN **/			
			
			urlServiceBitacora = properties.getProperty("services.cdmx.motor.bitacora");
			LOGGER.info("ENV [services.cdmx.motor.bitacora:\t\t{}]", urlServiceBitacora);
			
			userServiceBitacora = properties.getProperty("services.cdmx.motor.bitacora.user");
			LOGGER.info("ENV [services.cdmx.motor.bitacora.user:\t\t{}]", userServiceBitacora);
			
			passwordServiceBitacora = properties.getProperty("services.cdmx.motor.bitacora.password");
			LOGGER.info("ENV [services.cdmx.motor.bitacora.password:\t\t{}]", passwordServiceBitacora);
			
			
			/** SERVICIO PARA SINCRONIZACION EN MOTOR - ADMIN **/	
			urlServiceSincronizarProyecto = properties.getProperty("services.cdmx.motor.sincronizar");
			LOGGER.info("ENV [services.cdmx.motor.sincronizar:\t\t{}]", urlServiceSincronizarProyecto);
			
			urlServiceGetEstatusProyecto = properties.getProperty("services.cdmx.motor.estatus");
			LOGGER.info("ENV [services.cdmx.motor.estatus:\t\t{}]", urlServiceGetEstatusProyecto);
			
			urlServiceValidaSincronizacion = properties.getProperty("services.cdmx.motor.validaSincronizacion");
			LOGGER.info("ENV [services.cdmx.motor.validaSincronizacion:\t\t{}]", urlServiceValidaSincronizacion);
			
			
			/** SERVICIO PARA REGISTRO DE CONTADORES EN MOTOR - ADMIN **/			
			
			urlServiceDashboard = properties.getProperty("services.cdmx.motor.dashboard");
			LOGGER.info("ENV [services.cdmx.motor.dashboard:\t\t{}]", urlServiceDashboard);
			
			userServiceDashboard = properties.getProperty("services.cdmx.motor.dashboard.user");
			LOGGER.info("ENV [services.cdmx.motor.dashboard.user:\t\t{}]", userServiceDashboard);
			
			passwordServiceDashboard = properties.getProperty("services.cdmx.motor.dashboard.password");
			LOGGER.info("ENV [services.cdmx.motor.dashboard.password:\t\t{}]", passwordServiceDashboard);
			
			/** SERVICIOS FIRMA CDMX **/			
			
			urlServiceFirmaRegistro = properties.getProperty("services.cdmx.firma.registro");
			LOGGER.info("ENV [services.cdmx.firma.registro:\t\t{}]", urlServiceFirmaRegistro);
			
			urlServiceFirmaConsulta = properties.getProperty("services.cdmx.firma.consulta");
			LOGGER.info("ENV [services.cdmx.firma.consulta:\t\t{}]", urlServiceFirmaConsulta);
			
			urlServicePuenteDpa = properties.getProperty("url.puentedpa");
			LOGGER.info("ENV [url.puentedpa:\t\t{}]", urlServicePuenteDpa);
			
			serviceConsultaEstatusLc = properties.getProperty("services.path.consulta.status.lc");
			LOGGER.info("ENV [services.path.consulta.status.lc:\t\t{}]", serviceConsultaEstatusLc);
			
			serviceGeneraLc = properties.getProperty("services.path.genera.lc");
			LOGGER.info("ENV [services.path.genera.lc:\t\t{}]", serviceGeneraLc);
			
			
        } catch (IOException e) {
            LOGGER.error("No se pueden cargar los valores de entorno de ejecución:", e);
        }
    }

    /**
     * Constructor privado para que no se instancíe esta clase desde afuera
     */
    private Environment() {
    }
    
    public static String getAppProfile() {
        return appProfile;
    }

    public static String getAppGitVersion() {
		return appGitVersion;
	}

	public static boolean isEJBLog() {
        return ejbLog;
    }

    public static boolean isJSFLifeCycle() {
        return jsfLifeCycle;
    }

    public static boolean isWEBLog() {
        return webLog;
    }

	public static String getUrlLoginCdmx() {
		return urlLoginCdmx;
	}

	public static String getUrlServiceGetToken() {
		return urlServiceGetToken;
	}
	
	public static String getUrlServiceGetDatosUsuario() {
		return urlServiceGetDatosUsuario;
	}
	
	public static String getUrlServiceGetRolesUsuario() {
		return urlServiceGetRolesUsuario;
	}
	
	public static String getUrlServiceLogout() {
		return urlServiceLogout;
	}

	public static String getPathClienteDocumentos() {
		return pathClienteDocumentos;
	}
	
	public static String getPathArchivosTemporales() {
		return pathArchivosTemporales;
	}

	public static String getUrlConsultaTramite() {
		return urlConsultaTramite;
	}

	public static String getPathPlantillasClientePdf() {
		return pathPlantillasClientePdf;
	}

	public static String getUrlServiceSituacionRol() {
		return urlServiceSituacionRol;
	}
	
	public static String getUrlServiceGetInformacionPersonaMoral() {
		return urlServiceGetInformacionPersonaMoral;
	}

	public static String getUrlFileServerMotor() {
		return urlFileServerMotor;
	}

	public static String getPathFileServerMotor() {
		return pathFileServerMotor;
	}

	public static String getPathPropertiesCaptcha() {
		return pathPropertiesCaptcha;
	}
	
	public static String getUrlServiceBitacora() {
		return urlServiceBitacora;
	}

	public static String getUserServiceBitacora() {
		return userServiceBitacora;
	}

	public static String getPasswordServiceBitacora() {
		return passwordServiceBitacora;
	}
	
	public static String getUserServiceDashboard() {
		return userServiceDashboard;
	}
	
	public static String getPasswordServiceDashboard() {
		return passwordServiceDashboard;
	}
	
	public static String getUrlServiceFirmaRegistro() {
		return urlServiceFirmaRegistro;
	}

	public static String getUrlServiceFirmaConsulta() {
		return urlServiceFirmaConsulta;
	}
	
	public static String getUrlServiceDashboard() {
		return urlServiceDashboard;
	}

	public static String getUrlServiceSincronizarProyecto() {
		return urlServiceSincronizarProyecto;
	}

	public static String getUrlServiceGetEstatusProyecto() {
		return urlServiceGetEstatusProyecto;
	}

	public static String getUrlServiceValidaSincronizacion() {
		return urlServiceValidaSincronizacion;
	}

	public static String getUrlServicePuenteDpa() {
		return urlServicePuenteDpa;
	}

	public static String getServiceConsultaEstatusLc() {
		return serviceConsultaEstatusLc;
	}

	public static String getServiceGeneraLc() {
		return serviceGeneraLc;
	}

	public static void setServiceGeneraLc(String serviceGeneraLc) {
		Environment.serviceGeneraLc = serviceGeneraLc;
	}

	public static boolean isWebLog() {
		return webLog;
	}
	
	
}
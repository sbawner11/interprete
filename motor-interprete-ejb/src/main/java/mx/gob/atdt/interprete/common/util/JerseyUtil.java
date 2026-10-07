package mx.gob.atdt.interprete.common.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.api.json.JSONConfiguration;

import mx.gob.atdt.interprete.application.SeccionAccesoLlaveApplication;
import mx.gob.atdt.interprete.application.ConfiguracionWebhookApplication;
import mx.gob.atdt.interprete.application.DetSecurityDomainLineasCapturaApplication;
import mx.gob.atdt.interprete.application.SeccionFirmaDigitalApplication;
import mx.gob.atdt.interprete.application.SeccionSecurityCurpApplication;
import mx.gob.atdt.interprete.common.infra.Environment;

public class JerseyUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(JerseyUtil.class);
	
	private static JerseyUtil instance;
	private Client client;
	private Client clientSDKCdmxWithAuth;
	private Client clientWithAuth;
	private Client clientCURPWithAuth;
	private Client clientMotorWithAuth;
	private Client clientContadoresWithAuth;
	private Client clientFirmaWithAuth;	
	private Client clientLineaCapturaAuth;
	private Client clientWebhook;

	private JerseyUtil() throws NoSuchAlgorithmException {
		SeccionAccesoLlaveApplication seccionAccesoLLave = new SeccionAccesoLlaveApplication();
		SeccionSecurityCurpApplication seccionSecurityCurp = new SeccionSecurityCurpApplication();
		SeccionFirmaDigitalApplication seccionFirmaDigital = new SeccionFirmaDigitalApplication();
		DetSecurityDomainLineasCapturaApplication securityDomainLineasCaptura = new DetSecurityDomainLineasCapturaApplication();
		ConfiguracionWebhookApplication configuracionWebhook = new ConfiguracionWebhookApplication();
		
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getProperties().put(ClientConfig.PROPERTY_CONNECT_TIMEOUT, 30000); //3seg
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		
		confiarEnTodosLosCertificados();
		
		client = Client.create(clientConfig);
		client.setConnectTimeout(3000); // Establecer a 3 segundos
		
		clientSDKCdmxWithAuth = Client.create(clientConfig);
		clientSDKCdmxWithAuth.setConnectTimeout(3000); // Establecer a 3 segundos
		clientSDKCdmxWithAuth.addFilter(new HTTPBasicAuthFilter(seccionAccesoLLave.getUsuarioDominoSeg(), seccionAccesoLLave.getContrasenaDominioSeg()));
		
		clientWithAuth = Client.create(clientConfig);
		clientWithAuth.setConnectTimeout(5000); // Establecer 5 segundos
		clientWithAuth.setReadTimeout(5000); // Establecer 5 segundos
		
		clientCURPWithAuth = Client.create(clientConfig);
		clientCURPWithAuth.setConnectTimeout(2000); // Establecer a 2 segundos
		clientCURPWithAuth.setReadTimeout(2000); //Establece tiempo de lectura 2 segundos;
		clientCURPWithAuth.addFilter(new HTTPBasicAuthFilter(seccionSecurityCurp.getUsuarioDominoSeg(), seccionSecurityCurp.getContrasenaDominioSeg()));
				
		clientMotorWithAuth = Client.create(clientConfig);
		clientMotorWithAuth.setConnectTimeout(5000); // Establecer a 5 segundos
		clientMotorWithAuth.setReadTimeout(5000); //Establece tiempo de lectura 5 segundos;
		clientMotorWithAuth.addFilter(new HTTPBasicAuthFilter(Environment.getUserServiceBitacora(), Environment.getPasswordServiceBitacora()));
		
		clientContadoresWithAuth = Client.create(clientConfig);
		clientContadoresWithAuth.setConnectTimeout(2000); // Establecer a 2 segundos
		clientContadoresWithAuth.setReadTimeout(2000); //Establece tiempo de lectura 2 segundos;
		clientContadoresWithAuth.addFilter(new HTTPBasicAuthFilter(Environment.getUserServiceDashboard(), Environment.getPasswordServiceDashboard()));
	
		clientFirmaWithAuth = Client.create(clientConfig);
		clientFirmaWithAuth.setConnectTimeout(5000); // Establecer a 5 segundos
		clientFirmaWithAuth.setReadTimeout(5000); //Establece tiempo de lectura 5 segundos;
		clientFirmaWithAuth.addFilter(new HTTPBasicAuthFilter(seccionFirmaDigital.getUsuarioDominioFirma(), seccionFirmaDigital.getContraseniaDominioFirma()));
		
		/*linea de captura puente-dpa*/
		clientLineaCapturaAuth = Client.create(clientConfig);
		clientLineaCapturaAuth.setConnectTimeout(5000); // Establecer a 5 segundos
		clientLineaCapturaAuth.setReadTimeout(10000); //Establece tiempo de lectura 5 segundos
		clientLineaCapturaAuth.addFilter(new HTTPBasicAuthFilter(securityDomainLineasCaptura.getUsuario(), securityDomainLineasCaptura.getContrasenia())); 
				
		//Cliente para servicios de notificaciones de cambios de trámites a servicio webhook
		clientWebhook = Client.create(clientConfig);
		clientWebhook.setConnectTimeout(5000); // Establecer a 5 segundos
		clientWebhook.setReadTimeout(5000); //Establece tiempo de lectura 5 segundos;
		clientWebhook.addFilter(new HTTPBasicAuthFilter(configuracionWebhook.getUsuario(), configuracionWebhook.getContrasenia()));		
	}
		
	public static JerseyUtil getInstance() throws NoSuchAlgorithmException {
		if(instance == null) {
			instance = new JerseyUtil();
		}
		return instance;
	}
	
	public Client getClient() {
		return client;
	}
	
	public Client getClientCURPWithAuth() {
		return clientCURPWithAuth;
	}

	public Client getClientSDKCdmxWithAuth() {		
		return clientSDKCdmxWithAuth;
	}
	
	public Client getClientMotorWithAuth() {
		return clientMotorWithAuth;
	}	
	
	public Client getClientContadoresWithAuth() {
		return clientContadoresWithAuth;
	}
		
	public Client getClientFirmaWithAuth() {
		return clientFirmaWithAuth;
	}
	
	/**
	 * Cliente para linea de captura
	 * @return
	 */
	public Client getClientLineaCapturaAuth() {
		return clientLineaCapturaAuth;
	}

	/**
	 * Cliente que será utilizado para la notificación de trámites mediante el webhook
	 * @return the clientWebhook
	 */
	public Client getClientWebhook() {
		return clientWebhook;
	}
	/** 
	 * Este método lo que causa es que el cliente permita conectarse con servicios que están publicados en HTTPS 
	 * y que la JVM no tiene registrado en el cacerts el certificado de ese dominio donde se encuentra el servicio.
	 *  
	 * Por ejemplo, si no se invoca este método y en la cacerts no se agrega el certificado, marcará el error: 
	 * sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target
	 */
	private void confiarEnTodosLosCertificados() {
		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
		    public X509Certificate[] getAcceptedIssuers(){return null;}
		    public void checkClientTrusted(X509Certificate[] certs, String authType){}
		    public void checkServerTrusted(X509Certificate[] certs, String authType){}
		}};

		// Install the all-trusting trust manager
		try {
		    SSLContext sc = SSLContext.getInstance("TLS");
		    sc.init(null, trustAllCerts, new SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al hacer que se confie en todos los certificados en la JVM:", e);
		}
	}
}

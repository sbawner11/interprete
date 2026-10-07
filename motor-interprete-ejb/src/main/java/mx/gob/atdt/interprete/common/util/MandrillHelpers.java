package mx.gob.atdt.interprete.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import com.cribbstechnologies.clients.mandrill.model.MandrillAttachment;
import com.cribbstechnologies.clients.mandrill.model.MandrillHtmlMessage;
import com.cribbstechnologies.clients.mandrill.model.MandrillMessageRequest;
import com.cribbstechnologies.clients.mandrill.model.MandrillRecipient;
import com.cribbstechnologies.clients.mandrill.model.response.message.SendMessageResponse;
import com.cribbstechnologies.clients.mandrill.request.MandrillMessagesRequest;
import com.cribbstechnologies.clients.mandrill.request.MandrillRESTRequest;
import com.cribbstechnologies.clients.mandrill.util.MandrillConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.atdt.interprete.dao.DetGestionUsuariosDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;

public class MandrillHelpers {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MandrillHelpers.class);
	
	@Inject
	static
	DetGestionUsuariosDAO detGestionUsuariosDAO; 
	
	@Inject
	static
	ProyectoDAO proyectoDAO;
	
	private static final String API_VERSION = "1.0";
    private static final String BASE_URL = "https://mandrillapp.com/api";

    private MandrillRESTRequest request = new MandrillRESTRequest();
    private static MandrillConfiguration config = new MandrillConfiguration();
    private MandrillMessagesRequest messagesRequest = new MandrillMessagesRequest();
    private HttpClient client = new DefaultHttpClient();
    private static ObjectMapper mapper = new ObjectMapper();

    public MandrillHelpers(String apikey) {
    	config.setApiKey(apikey);
        config.setApiVersion(API_VERSION);
        config.setBaseURL(BASE_URL);
        request.setConfig(config);
        request.setObjectMapper(mapper);
        request.setHttpClient(client);
        messagesRequest.setRequest(request);
	}
    
    public SendMessageResponse sendMessage(
    		String subject, 
    		MandrillRecipient[] recipients,
            String senderName, 
            String content, 
            List<MandrillAttachment> attachments,
            String fromEmail
            ) throws RequestFailedException {
    	
        MandrillMessageRequest mmr = new MandrillMessageRequest();
        MandrillHtmlMessage message = new MandrillHtmlMessage();

        Map<String, String> headers = new HashMap<String, String>();
        message.setFrom_email(fromEmail);
        message.setFrom_name(senderName);
        message.setHeaders(headers);
        
        //TODO Agregar el Subaccount del proyecto
        message.setSubaccount(null);
        
        message.setHtml(content);
        message.setSubject(subject);
        message.setAttachments(attachments);
        message.setTo(recipients);
        message.setTrack_clicks(true);
        message.setTrack_opens(true);
        mmr.setMessage(message);

        return messagesRequest.sendMessage(mmr);
    }
    
    public void closeResources() {
  	  if(client != null) {
  		  client.getConnectionManager().shutdown();
  	  }
    }
    
    
}


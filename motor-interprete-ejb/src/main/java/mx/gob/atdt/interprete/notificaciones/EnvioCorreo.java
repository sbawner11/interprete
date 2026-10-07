package mx.gob.atdt.interprete.notificaciones;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cribbstechnologies.clients.mandrill.exception.RequestFailedException;
import com.cribbstechnologies.clients.mandrill.model.MandrillAttachment;
import com.cribbstechnologies.clients.mandrill.model.MandrillRecipient;

import mx.gob.atdt.interprete.common.util.MandrillHelpers;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.CatEstatusTramiteDTO;
import mx.gob.atdt.interprete.dto.DetGestionUsuarioDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;

@Named("envioCorreo")
@SessionScoped
public class EnvioCorreo implements Serializable{
	
	private static final long serialVersionUID = -7824505702717032061L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnvioCorreo.class);
	
	/**
	 * Método auxiliar que realiza el envío de correo
	 * @param correoDestino
	 * @param cuerpoTexto
	 * @param asunto
	 * @param remitente
	 * @param tramite
	 * @param url
	 * @param detGestionUsuarioDTO
	 * @param proyectoDTO
	 * @throws IOException
	 */
	public void enviarCorreoNotificacion(final String correoDestino, String cuerpoTexto, String asunto, String remitente, TramiteDTO tramite, String url,
			DetGestionUsuarioDTO detGestionUsuarioDTO, ProyectoDTO proyectoDTO) throws IOException {

	    if (detGestionUsuarioDTO == null || detGestionUsuarioDTO.getApiKey() == null || detGestionUsuarioDTO.getApiKey().trim().isEmpty()) {	       
	        return;
	    }
		
		MandrillHelpers mandrillHelpers = new MandrillHelpers(detGestionUsuarioDTO.getApiKey());
		List<MandrillAttachment> attachments = null;
		
		attachments = new ArrayList<>();
		
		MandrillRecipient[] recipients = { new MandrillRecipient(correoDestino, correoDestino) };
		String strContenido = construirCuerpoCorreo(cuerpoTexto, asunto, tramite, url, remitente);
				
		try {		
			//mandrillHelpers.sendMessage(asunto, recipients, remitente, strContenido, attachments, "tramites@cdmx.gob.mx");
			//mandrillHelpers.sendMessage(asunto, recipients, remitente, strContenido, attachments, "noresponder@adyt.gob.mx");
			mandrillHelpers.sendMessage(asunto, recipients, remitente, strContenido, attachments, "noresponder@transformaciondigital.gob.mx");
			
		} catch (RequestFailedException e) {
			LOGGER.error("Ocurrió un error al enviar un correo:", e);
			throw new IOException(" Ocurrio un error al intentar enviar el correo: " + e);
		} finally {
			mandrillHelpers.closeResources();
		}
	}
	
	/**
	 * Método que se utiliza para construit el cuerpo del correo
	 */
	private String construirCuerpoCorreo(String cuerpoTexto, String asunto, TramiteDTO tramite, String strUrl, String remitente) {	
		final StringBuilder cuerpoCorreo = new StringBuilder();
	
		cuerpoCorreo.append("<div>");
	    cuerpoCorreo.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\""); 
	    cuerpoCorreo.append("style=\"border-collapse:collapse;width:100%;background-color:#f6f6f6\">"); 
	    cuerpoCorreo.append("<tbody><tr>"); 
	    cuerpoCorreo.append("<td style=\"display:block;margin:0 auto;max-width:800px;padding:2px;width:800px\">");
	    cuerpoCorreo.append("<div style=\"box-sizing:border-box;display:block;margin:0 auto;max-width:800px;padding:2px\">"); 
	    cuerpoCorreo.append("<table style=\"border-collapse:collapse;width:100%;background:#ffffff;border-radius:3px\">");
	    cuerpoCorreo.append("<tbody><tr style=\"background: #611232;\">");
	    cuerpoCorreo.append("<td style=\"border-bottom:3px solid #611232;padding:10px 50px\">");
	    cuerpoCorreo.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"");
	    cuerpoCorreo.append("style=\"border-collapse:collapse;width:100%;box-sizing:border-box\">");
	    cuerpoCorreo.append("<tbody><tr><td align=\"center\"><table><tbody><tr>");
	    cuerpoCorreo.append("<td style=\"padding-right:5px\"><a>");
	    cuerpoCorreo.append("<img alt=\"Gobierno de México \"");
	    cuerpoCorreo.append("style=\"font-family:'HelveticaNeue-Light','Helvetica Neue Light','Helvetica Neue',Helvetica,Arial,'Lucida Grande',sans-serif;height:35px;max-width:100%;");
	    cuerpoCorreo.append("object-fit:contain;vertical-align:middle;width:auto\"");
	    	
	    cuerpoCorreo.append("src=\"").append(strUrl).append("/resources/img/mx/logo_gob_mx.png").append("\"></a>");
	    //cuerpoCorreo.append("src=\"").append("https://www.evaluariesgo.atdt.gob.mx").append("/resources/img/mx/logo_gob_mx.png").append("\"></a>");
	    //cuerpoCorreo.append("src=\"").append("https://www.llave.gob.mx").append("/resources/img/mx/logo_gob_mx.png").append("\"></a>");
	    //cuerpoCorreo.append("src=\"").append("https://www.motortransaccional.adyt.gob.mx").append("/resources/img/adip-footer.png").append("\"></a>");
    	    
	    cuerpoCorreo.append("</td><td style=\"width:1px;border-right:none\">"); 
	    cuerpoCorreo.append("</td><td style=\"padding-left:5px\">"); 
	    cuerpoCorreo.append("<h3 style=\"text-transform:uppercase;font-size:8.5px;font-weight:700;color:#888b8d;font-family:'Metropolis-Medium',Helvetica,sans-serif;line-height:normal\">");
	    cuerpoCorreo.append("</h3>");
	    cuerpoCorreo.append("</td></tr></tbody></table></td></tr></tbody></table></td></tr>"); 

	    
	    cuerpoCorreo.append("<tr><td style=\"box-sizing:border-box;padding:5px 0px\">"); 
	    cuerpoCorreo.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\""); 
	    cuerpoCorreo.append("style=\"border-collapse:collapse;width:100%\">");
	    cuerpoCorreo.append("<tbody><tr><td style=\"box-sizing:border-box;padding:5px 0px\">");
	    cuerpoCorreo.append("<p style=\"font-family:'Metropolis-Medium',Helvetica,sans-serif;font-size:23px;font-weight:bold;color:#611232;margin:1em 0;text-align:left;line-height:17px;padding:5px 100px;\">");
        cuerpoCorreo.append("<strong>").append(asunto).append("</strong></p>");
	    cuerpoCorreo.append("<hr style=\"border:1px solid; border-color: #DDDDDD; \">");
	    cuerpoCorreo.append("</td></tr></tbody></table></td></tr>");
	    
	    cuerpoCorreo.append("<tr><td style=\"box-sizing:border-box;padding:0px 0px\">"); 
	    cuerpoCorreo.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\""); 
	    cuerpoCorreo.append("style=\"border-collapse:collapse;width:100%\">");
	    cuerpoCorreo.append("<tbody><tr><td style=\"box-sizing:border-box;padding:0px 100px;\">");
	    
	    cuerpoCorreo.append("<p style=\"font-family:'Metropolis-Medium',Helvetica,sans-serif;font-size:16px;font-weight:normal;color:#000;text-align:left;line-height:1.56;\">");		
	    cuerpoCorreo.append("<b>");
	    cuerpoCorreo.append("<strong>Número de folio: </b> ");
	    cuerpoCorreo.append(tramite.getFolioSeguimiento());
	    cuerpoCorreo.append("</strong></p>");
			    
		cuerpoCorreo.append("<p style=\"font-family:'Metropolis-Medium',Helvetica,sans-serif;font-size:16px;font-weight:normal;color:#000;margin:1em 0;text-align:left;line-height:1.56;\">");
		
		cuerpoCorreo.append(cuerpoTexto).append("</p>");
		    
		if(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_CONCLUSION_POSITIVA ||
				tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_CONCLUSION_NEGATIVA) {
			cuerpoCorreo.append("<p style=\"font-family:'Metropolis-Medium',Helvetica,sans-serif;font-size:16px;font-weight:normal;color:#000;margin:1em 0;text-align:left;line-height:1.56;\">");				
			cuerpoCorreo.append(strUrl).append("</p>");
		}	    
		
	    cuerpoCorreo.append("</td></tr></tbody></table></td></tr>"); 
	    	    
	    cuerpoCorreo.append("<br><br>");	   
	    
	    cuerpoCorreo.append("<tr><td style=\"vertical-align:top\">"); 
	    cuerpoCorreo.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"");
	    cuerpoCorreo.append("style=\"background-color:#611232;border-collapse:collapse;width:100%;box-sizing:border-box\">"); 
	    cuerpoCorreo.append("<tbody><tr><td align=\"center\" style=\"padding:10px 0 10px 0\">"); 
	    
	    cuerpoCorreo.append("<p style=\"font-family: 'Metropolis-Medium', Helvetica, sans-serif; font-size: 18px; font-weight: normal; color:#FFF;  margin: 1em 2em;  text-align: center; line-height: 17px\">");
		cuerpoCorreo.append("<strong>");
		cuerpoCorreo.append(remitente);
		cuerpoCorreo.append("</strong>");
		cuerpoCorreo.append("</p>");	    	    
	    
	    cuerpoCorreo.append("</td></tr>");
	    cuerpoCorreo.append("</tbody></table></td></tr></tbody></table></div></td></tr></tbody></table>"); 
		
		return cuerpoCorreo.toString();
	}

	public static void main(String args[]) {
		EnvioCorreo ec = new EnvioCorreo();
		TramiteDTO t = new TramiteDTO();
		t.setUuid("testemail");
		t.setCatEstatusTramiteDTO(new CatEstatusTramiteDTO(Constantes.ID_ESTATUS_APROBADO));
		DetGestionUsuarioDTO detGestionUsuarioDTO = new DetGestionUsuarioDTO();
		//detGestionUsuarioDTO.setApiKey("md-pmCRCiCCZMCai7DYeRSA-w");
		
		detGestionUsuarioDTO.setApiKey("md-4Wu-uwGRhMAo-khIaWQlWQ");
		
		detGestionUsuarioDTO.setCorreoResolucionPositiva("Estimado(a) usuario(a):\n"
				+ "Le informamos que ya se cuenta con la resolución del trámite, la cual puede descargar al ingresar al detalle del trámite desde el siguiente enlace:\n"
				+ "");
		
		t.setFolioSeguimiento("CR-250212-2");
				
		//String cuerpo = "<p>Estimado/a usuario</p><p>Hemos recibido tus respuestas a nuestra encuesta. Queremos agradecerte por el tiempo y esfuerzo que has invertido en compartir la información con nosotros.</p><p><br></p><p>Tu participación es de gran valor y nos ayudará a mejorar nuestros procesos y servicios.</p>";
	
		try {
			ec.enviarCorreoNotificacion("adriana8119@gmail.com", detGestionUsuarioDTO.getCorreoResolucionPositiva(), "Trámite con resolución positiva", "Evaluación de riesgos", t, "localhost:8080", detGestionUsuarioDTO, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

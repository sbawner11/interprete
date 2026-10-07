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

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.common.util.MandrillHelpers;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.CatEstatusTramiteDTO;
import mx.gob.atdt.interprete.dto.DetGestionUsuarioDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;

@Named("envioCorreoEstatus")
@SessionScoped
public class EnvioCorreoEstatusTramite implements Serializable {

	private static final long serialVersionUID = -7824505702717032061L;

	private static final Logger LOGGER = LoggerFactory.getLogger(EnvioCorreoEstatusTramite.class);

	/*
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
	@SuppressWarnings("squid:S107")
	public void enviarCorreoEstatus(final String correoDestino,String asunto, String remitente,
	        List<TramiteDTO> tramites, String url, DetGestionUsuarioDTO detGestionUsuarioDTO, 
	        ProyectoDTO proyectoDTO, List<CatEstatusTramiteDTO> listEstatus) throws IOException {

	    if (detGestionUsuarioDTO == null || detGestionUsuarioDTO.getApiKey() == null) {
	        return;
	    }
	    
	    String strContenido = construirCuerpoCorreo(asunto, tramites, url, remitente,
	            proyectoDTO, listEstatus);

	    MandrillHelpers mandrillHelpers = new MandrillHelpers(detGestionUsuarioDTO.getApiKey());
	    List<MandrillAttachment> attachments = new ArrayList<>();
	    
	    String[] correos = correoDestino.split(",");
	    MandrillRecipient[] recipients = new MandrillRecipient[correos.length];
	    for (int i = 0; i < correos.length; i++) {
	        recipients[i] = new MandrillRecipient(correos[i], correos[i]); 
	    }

	    try {
	        mandrillHelpers.sendMessage(asunto, recipients, remitente, strContenido, attachments, "noresponder@transformaciondigital.gob.mx");
	    } catch (RequestFailedException e) {
	        LOGGER.error("Ocurrió un error al enviar el correo a {}: {}", correoDestino, e.getMessage(), e);
	        throw new IOException("Error al enviar correo: " + e);
	    } finally {
	        mandrillHelpers.closeResources();
	        LOGGER.info("Recursos de Mandrill liberados.");
	    }
	}
	
	/**
	 * Método que se utiliza para construi el cuerpo del correo
	 */
	private String construirCuerpoCorreo(String asunto, List<TramiteDTO> lstTramites,
	        String strUrl, String remitente, ProyectoDTO proyectoDTO, List<CatEstatusTramiteDTO> listEstatus) {

	    final StringBuilder cuerpoCorreo = new StringBuilder();

	    cuerpoCorreo.append("<div>");
	    cuerpoCorreo.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"")
	                .append(" style=\"border-collapse:collapse;width:100%;background-color:#f6f6f6\">");
	    cuerpoCorreo.append("<tbody><tr>");
	    cuerpoCorreo.append("<td style=\"display:block;margin:0 auto;max-width:800px;padding:2px;width:800px\">");
	    cuerpoCorreo.append("<div style=\"box-sizing:border-box;display:block;margin:0 auto;max-width:800px;padding:2px\">");
	    cuerpoCorreo.append("<table style=\"border-collapse:collapse;width:100%;background:#ffffff;border-radius:3px\">");

	    cuerpoCorreo.append("<tbody><tr style=\"background: #611232;\">");
	    cuerpoCorreo.append("<td style=\"border-bottom:3px solid #611232;padding:10px 50px\">");
	    cuerpoCorreo.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"")
	                .append(" style=\"border-collapse:collapse;width:100%;box-sizing:border-box\">");
	    cuerpoCorreo.append("<tbody><tr><td align=\"center\"><table><tbody><tr>");
	    cuerpoCorreo.append("<td style=\"padding-right:5px\"><a>");
	    cuerpoCorreo.append("<img alt=\"Gobierno de México\"")
	                .append(" style=\"height:35px;max-width:100%;object-fit:contain;vertical-align:middle;width:auto\"")
	                .append(" src=\"").append(strUrl).append("/resources/img/mx/logo_gob_mx.png\"></a>");
	    cuerpoCorreo.append("</td></tr></tbody></table></td></tr></tbody></table></td></tr>");

	    cuerpoCorreo.append("<tr><td style=\"padding:5px 0px\">");
	    cuerpoCorreo.append("<p style=\"font-family:'Metropolis-Medium',Helvetica,sans-serif;font-size:23px;font-weight:bold;color:#611232;margin:1em 0;text-align:left;padding:5px 100px;\">")
	            .append("<strong>").append(asunto).append("</strong></p>");
	    cuerpoCorreo.append("<hr style=\"border:1px solid #DDDDDD;\">");
	    cuerpoCorreo.append("</td></tr>");

	    cuerpoCorreo.append("<tr><td style=\"padding:0px 100px\">");
	    cuerpoCorreo.append("<p style=\"font-family:'Metropolis-Medium',Helvetica,sans-serif;font-size:16px;color:#000;line-height:1.56;\">");
	    cuerpoCorreo.append("<b>Estimado(a) usuario(a):</b></p>");
	    cuerpoCorreo.append("<p style=\"font-family:'Metropolis-Medium',Helvetica,sans-serif;font-size:16px;color:#000;line-height:1.56;\">");
	    cuerpoCorreo.append("Este es un mensaje automático enviado por el sistema: <b>")
	            .append(proyectoDTO.getNombreProyecto()).append("</b> publicado en: <b>").append(strUrl)
	            .append("</b></p>");
	    cuerpoCorreo.append("<p style=\"font-family:'Metropolis-Medium',Helvetica,sans-serif;font-size:16px;color:#000;line-height:1.56;\">");
	    cuerpoCorreo.append("Al respecto, se hace de su conocimiento que se tienen los siguientes trámites:</p>");

	    cuerpoCorreo.append("<table border=\"1\" width=\"300\" align=\"center\" style=\"border-collapse:collapse; text-align:center;\">");
	    cuerpoCorreo.append("<tr><th><strong>ESTATUS</strong></th><th><strong>CANTIDAD</strong></th></tr>");
	
	    if(!BeanUtils.isEmpty(listEstatus)) {
	    	for (CatEstatusTramiteDTO estatusTramite : listEstatus) {
	    		 long cantidad = lstTramites.stream()
	    		            .filter(t -> t.getCatEstatusTramiteDTO() != null
	    		                && t.getCatEstatusTramiteDTO().getIdEstatusTramite() == estatusTramite.getIdEstatusTramite())
	    		            .count();
	    		cuerpoCorreo.append("<tr>");
	    		cuerpoCorreo.append("<td>");
	    		cuerpoCorreo.append(
	    				proyectoDTO.isAviso() ? 
	    						estatusTramite.getDescripcionAviso().toUpperCase() :
	    						estatusTramite.getDescripcion().toUpperCase());
	    		cuerpoCorreo.append("</td>");
	    		cuerpoCorreo.append("<td>");
	    		cuerpoCorreo.append(cantidad);
	    		cuerpoCorreo.append("</td>");
	    	}
	    }
	    
	    cuerpoCorreo.append("</table>");
	    
	    cuerpoCorreo.append("<p style=\"font-family:'Metropolis-Medium',Helvetica,sans-serif;font-size:16px;color:#000;line-height:1.56;\">")
	                .append("Para mayor detalle, ingrese al sitio y consulte los estatus desde su bandeja de trámites.")
	                .append("</p>");
	    cuerpoCorreo.append("<br><br>");
	    cuerpoCorreo.append("<tr><td style=\"vertical-align:top\">");
	    cuerpoCorreo.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"")
	                .append("style=\"background-color:#611232;border-collapse:collapse;width:100%;box-sizing:border-box\">");
	    cuerpoCorreo.append("<tbody><tr><td align=\"center\" style=\"padding:10px 0 10px 0\">");
	    cuerpoCorreo.append("<p style=\"font-family: 'Metropolis-Medium', Helvetica, sans-serif; font-size: 18px; font-weight: normal; color:#FFF;  margin: 1em 2em;  text-align: center; line-height: 17px\">");
	    cuerpoCorreo.append("<strong>").append(remitente).append("</strong>");
	    cuerpoCorreo.append("</p></td></tr></tbody></table></td></tr>");
	    cuerpoCorreo.append("</tbody></table></div></td></tr></tbody></table></div>");

	    return cuerpoCorreo.toString();
	}

}

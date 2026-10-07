package mx.gob.atdt.interprete.tramites.bean;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.util.WebResources;

@Named
@SessionScoped
public class NuevoTramiteBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2728149906059396161L;

	private static final Logger LOGGER = LoggerFactory.getLogger(NuevoTramiteBean.class);

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private AuthenticatorBean authenticatorBean;
		
	/**
	 * Método auxiliar que se ejecuta al cargar el XHTML NuevoTramite.xhtml 
	 * Dicho XHTML el usuario lo abre cuando es redirido desde otro sitio.
	 * Se debe revisar si se encuentra el parámetro iniciaTramite en la URL.
	 */
	public void validaNuevoTramite() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if (!FacesContext.getCurrentInstance().isPostback() && params != null && !params.isEmpty()) {
			if (params.get("iniciaTramite") == null || params.get("iniciaTramite").trim().isEmpty()) {
				/*
				 * Aquí entra por ejemplo si capturan una URL así:
				 * http://localhost:8180/public/NuevoTramite.xhtml?algo=1
				 * http://localhost:8180/public/NuevoTramite.xhtml?iniciaTramite=
				 */
				WebResources.validationMessage("inicia_tramite_no_encontrado", false);
			} else {
				try {
					/**
					 * Se revisa parámetro enviado
					 */
					Boolean blnIniciaTramite = Boolean.valueOf(params.get("iniciaTramite"));
					if(blnIniciaTramite != null && blnIniciaTramite) {
						authenticatorBean.redirectUrlLoginCDMX(Constantes.ID_NUEVO_TRAMITE, false);
					} else {
						/*
						 * Aquí entra por ejemplo si capturan una URL así:
						 * http://localhost:8180/public/NuevoTramite.xhtml?iniciaTramite=ABCDEFGHI
						 */
						WebResources.validationMessage("valor_nuevo_tramite_incorrecto", true);
					}
				
				} catch (Exception e) {
					WebResources.validationMessage("msg_error_inicio_tramite", false);
					LOGGER.error("Error al obtener/convertir el valor iniciaTramite: ",e);
				} 
			}
		} else {
			/*
			 * Aquí entra por ejemplo si solo capturan una URL así:
			 * http://localhost:8180/public/NuevoTramite.xhtml
			 */
			WebResources.validationMessage("incia_tramite_vacio", false);
		}
	}	
}

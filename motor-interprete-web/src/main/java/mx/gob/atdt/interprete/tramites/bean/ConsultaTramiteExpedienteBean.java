package mx.gob.atdt.interprete.tramites.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.TramitesDAO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.util.WebResources;

@Named
@SessionScoped
public class ConsultaTramiteExpedienteBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4352723862193334887L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaTramiteExpedienteBean.class);

	@Inject
	private FacesContext facesContext;
	
	@Inject
	private AuthenticatorBean authenticatorBean;
	
	@Inject
	private TramitesDAO tramitesDAO;
		
	private TramiteDTO tramiteConsulta;

	
	/**
	 * Método auxiliar que se ejecuta al cargar el XHTML ConsultaTramiteExpediente.xhtml 
	 * Dicho XHTML el usuario lo abre cuando es redirido desde portal CDMX.
	 * Se debe revisar si se encuentra el parámetro idTramite en la URL.
	 */
	public void verificaIdTramite() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if (!FacesContext.getCurrentInstance().isPostback() && params != null && !params.isEmpty()) {
			if (params.get("idTramite") == null || params.get("idTramite").trim().isEmpty()) {
				/*
				 * Aquí entra por ejemplo si capturan una URL así:
				 * http://localhost:8180/public/ConsultaTramiteExpediente.xhtml?algo=1
				 * http://localhost:8180/public/ConsultaTramiteExpediente.xhtml?idTramite=
				 */
				WebResources.validationMessage("consulta_id_tramite_no_encontrado", false);
			} else {
				List<TramiteDTO> lstTramitesDTO = new ArrayList<TramiteDTO>();		
				try {
					/**
					 * Se realiza búsqueda del trámite enviado
					 */
					lstTramitesDTO = tramitesDAO.buscarTramitePorId(Long.valueOf(params.get("idTramite")));
					if(lstTramitesDTO != null && !lstTramitesDTO.isEmpty()) {
						try {
							if (BeanUtils.isNotNull(lstTramitesDTO.get(Constantes.FIRST_INDEX_LIST).getUsuario())) {								
								if (BeanUtils.isNotNull(lstTramitesDTO.get(Constantes.FIRST_INDEX_LIST).getUsuario().getIdUsuarioLlaveCdmx())) {
									this.tramiteConsulta = lstTramitesDTO.get(Constantes.FIRST_INDEX_LIST);
									authenticatorBean.redirectUrlLoginCDMX(Constantes.ID_ORIGEN_EXPEDIENTE, false);
								} else {
									WebResources.validationMessage("consulta_id_tramite_sin_usuario", true);
								}
							} else {
								WebResources.validationMessage("consulta_id_tramite_sin_usuario", true);
							}						
						} catch (Exception e) {
							LOGGER.error("Ocurrió un error al redireccionar al detalle del trámite en la consulta de trámites.");
						}
					} else {
						/*
						 * Aquí entra por ejemplo si capturan una URL así:
						 * http://localhost:8080/public/ConsultaTramiteExpediente.xhtml?idTramite=ABCDEFGHI
						 */
						WebResources.validationMessage("consulta_id_tramite_incorrecto", true);
					}
				} catch (Exception e) {
					WebResources.validationMessage("msg_error_consulta_tramite", false);
					LOGGER.error("Error al obtener/convertir el idTramite: ",e);
				} 
			}
		} else {
			/*
			 * Aquí entra por ejemplo si solo capturan una URL así:
			 * http://localhost:8180/public/ConsultaTramiteExpediente.xhtml
			 */
			WebResources.validationMessage("consulta_id_tramite_vacio", false);
		}
	}

	/**GETTER´s y SETTER's **/

	/**
	 * @return the tramiteConsulta
	 */
	public TramiteDTO getTramiteConsulta() {
		return tramiteConsulta;
	}

	/**
	 * @param tramiteConsulta the tramiteConsulta to set
	 */
	public void setTramiteConsulta(TramiteDTO tramiteConsulta) {
		this.tramiteConsulta = tramiteConsulta;
	}	
}

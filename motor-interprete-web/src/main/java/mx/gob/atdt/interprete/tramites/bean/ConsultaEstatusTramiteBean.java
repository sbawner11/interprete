package mx.gob.atdt.interprete.tramites.bean;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.UsuarioDAO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.TramiteFirmaElectronicaDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.formulario.dao.FormularioDAO;
import mx.gob.atdt.interprete.util.WebResources;

@Named
@SessionScoped
public class ConsultaEstatusTramiteBean implements Serializable {

	private static final long serialVersionUID = 2628607670730253945L;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsultaEstatusTramiteBean.class);

	@Inject
	private FacesContext facesContext;

	@Inject
	private FormularioDAO formularioDAO;

	@Inject
	private UsuarioDAO usuarioDAO;

	@Inject
	private SeccionesProyectoBean seccionesProyectoBean;

	private TramiteDTO tramiteConsulta;
	private String fecha;
	private UsuarioDTO usuarioTramite;
	private TramiteFirmaElectronicaDTO firmaDTO;
	private String fechaFirma;

	public void verificarUuid() {
		Map<String, String> params = facesContext.getExternalContext().getRequestParameterMap();
		if (!FacesContext.getCurrentInstance().isPostback() && params != null && !params.isEmpty()) {
			if (params.get("uuid") == null || params.get("uuid").trim().isEmpty()) {
				WebResources.validationMessage("consulta_uuid_incorrecto", false);
			} else {
				inicializar(params.get("uuid"));
			}
		} else {
			WebResources.validationMessage("consulta_uuid_incorrecto", false);
		}
	}

	private void inicializar(String uuid) {
		TramiteDTO tramite = new TramiteDTO();
		tramite.setUuid(uuid);
		List<TramiteDTO> lstTramites;
		List<TramiteFirmaElectronicaDTO> lstFirma;
		try {
			lstTramites = formularioDAO.consultarSeguimientoTramite(tramite);
			if (lstTramites != null && !lstTramites.isEmpty()) {
				tramiteConsulta = lstTramites.get(0);
				if (BeanUtils.isNotNull(tramiteConsulta.getUsuario())) {
					usuarioTramite = usuarioDAO.buscarPorId(tramiteConsulta.getUsuario().getIdUsuarioLlaveCdmx());
				}
				// Tener en cuenta que para mostrar fechas en español se debe indicar en el
				// Locale el lenguaje "ES" y en nuestro caso el pais "MX"
				DateFormat formateadorFechaLarga = DateFormat.getDateInstance(DateFormat.LONG, new Locale("ES", "MX"));
				fecha = formateadorFechaLarga.format(tramiteConsulta.getFechaCreacion());
				if (seccionesProyectoBean.isHabilitarFirmadoTramites()) {
					lstFirma = formularioDAO.consultarFirmaTramite(tramiteConsulta);
					if (BeanUtils.isNotNull(lstFirma) || BeanUtils.isNotEmpty(lstFirma)) {
						firmaDTO = lstFirma.get(0);
						fechaFirma = formateadorFechaLarga.format(firmaDTO.getFechaCreacion());
					}
				}
				String urlRedireccionar = Constantes.RETURN_ESTATUS_TRAMITE;
				facesContext.getExternalContext()
						.redirect(facesContext.getExternalContext().getRequestContextPath() + urlRedireccionar);
			} else {
				WebResources.validationMessage("consulta_uuid_incorrecto", true);
			}
		} catch (Exception e1) {
			LOGGER.error("Ocurrió un error al incializar la Consulta del Estatus del Trámite:", e1);
		}

	}

	public TramiteDTO getTramiteConsulta() {
		return tramiteConsulta;
	}

	public void setTramiteConsulta(TramiteDTO tramiteConsulta) {
		this.tramiteConsulta = tramiteConsulta;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public UsuarioDTO getUsuarioTramite() {
		return usuarioTramite;
	}

	public void setUsuarioTramite(UsuarioDTO usuarioTramite) {
		this.usuarioTramite = usuarioTramite;
	}

	public TramiteFirmaElectronicaDTO getFirmaDTO() {
		return firmaDTO;
	}

	public void setFirmaDTO(TramiteFirmaElectronicaDTO firmaDTO) {
		this.firmaDTO = firmaDTO;
	}

	public String getFechaFirma() {
		return fechaFirma;
	}

	public void setFechaFirma(String fechaFirma) {
		this.fechaFirma = fechaFirma;
	}

}

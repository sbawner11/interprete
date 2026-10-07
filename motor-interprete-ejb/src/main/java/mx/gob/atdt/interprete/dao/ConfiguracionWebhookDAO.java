package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ConfiguracionWebhookDTO;
import mx.gob.atdt.interprete.model.ConfiguracionWebhook;
import mx.gob.atdt.interprete.model.Proyecto;

@Stateless
@LocalBean
public class ConfiguracionWebhookDAO extends IBaseService<ConfiguracionWebhookDTO, Long> {

	@Override
	public ConfiguracionWebhookDTO buscarPorId(Long idProyecto) {		
		return null;
	}

	@Override
	public void actualizar(ConfiguracionWebhookDTO e) {
		ConfiguracionWebhook configuracionWebhook = new ConfiguracionWebhook();
		
		configuracionWebhook.setIdConfiguracionWebhook(e.getIdConfiguracionWebhook());
		configuracionWebhook.setProyecto(em.getReference(Proyecto.class, e.getProyectoDTO().getIdProyecto()));
		configuracionWebhook.setUsuario(e.getUsuario());
		configuracionWebhook.setContrasenia(e.getContrasenia());
		configuracionWebhook.setUrlAplicacionNotificaciones(e.getUrlAplicacionNotificaciones());
		configuracionWebhook.setHabilitaEnvioNotificaciones(e.isHabilitaEnvioNotificaciones());
		configuracionWebhook.setFechaCreacion(e.getFechaCreacion());
		configuracionWebhook.setFechaUltimaActualizacion(e.getFechaUltimaActualizacion());
		configuracionWebhook.setActivo(e.isActivo());
		configuracionWebhook.setSeccionSincronizada(e.isSeccionSincronizada());
		
		em.merge(configuracionWebhook);
	}

	/**
	 * Método auxiliar que realiza la búsqueda de la sección Configuración Webhook mediante el idProyecto
	 * @param idProyecto
	 * @return
	 */
	public ConfiguracionWebhookDTO buscarPorIdProyecto(Long idProyecto) {
		List<ConfiguracionWebhookDTO> lstConfiguraciones = em.createNamedQuery("ConfiguracionWebhook.findByProyecto", ConfiguracionWebhookDTO.class)
				.setParameter("idProyecto", idProyecto).getResultList();
		return lstConfiguraciones == null || lstConfiguraciones.isEmpty() ? null : lstConfiguraciones.get(0);
	}
}

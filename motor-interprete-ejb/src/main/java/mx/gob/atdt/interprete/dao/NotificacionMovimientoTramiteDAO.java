package mx.gob.atdt.interprete.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.NotificacionMovimientoTramiteDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.model.CatTipoNotificacion;
import mx.gob.atdt.interprete.model.NotificacionMovimientoTramite;
import mx.gob.atdt.interprete.model.Tramites;


@Stateless
@LocalBean
public class NotificacionMovimientoTramiteDAO extends IBaseService<NotificacionMovimientoTramiteDTO, Long> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionMovimientoTramiteDAO.class);
	
	@Override
	public NotificacionMovimientoTramiteDTO buscarPorId(Long id) {
		return null;
	}

	/**
	 * Método auxiliar que actualiza los datos de una notificación realizada de manera correcta mediante el Webhook.
	 */
	@Override
	public void actualizar(NotificacionMovimientoTramiteDTO e) {
		NotificacionMovimientoTramite entity = em.find(NotificacionMovimientoTramite.class, e.getIdNotificacionMovimiento());
		if (entity != null) {
			entity.setFechaNotificacion(e.getFechaNotificacion());
			entity.setEnvioConfirmado(e.isEnvioConfirmado());

			em.merge(entity);
		}
	}

	/**
	 * Método auxiliar que realiza el registro de notificaciones mediante el webhook.
	 * @param dto
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardar(NotificacionMovimientoTramiteDTO dto) {
		NotificacionMovimientoTramite entity = new NotificacionMovimientoTramite();
		entity.setCatTipoNotificacion(em.getReference(CatTipoNotificacion.class, dto.getCatTipoNotificacionDTO().getIdTipoNotificacion()));
		entity.setTramites(em.getReference(Tramites.class, dto.getTramiteDTO().getIdTramite()));
		entity.setFechaNotificacion(dto.getFechaNotificacion());
		entity.setEnvioConfirmado(dto.isEnvioConfirmado());
		
		em.persist(entity);
		em.flush();
	}

	/**
	 * Obtiene las notificaciones pendientes de envío.
	 * Criterios: envio_confirmado = false AND fecha_notificacion IS NULL
	 * Incluye datos del trámite asociado para el envío al sistema externo.
	 * 	
	 * @return Lista de NotificacionMovimientoTramiteDTO con datos del trámite
	 * 		el metodo queda en deshuso tras la actualizacion en el formato json 
	 * 		para la notificacion a webhook
	 */
	public List<NotificacionMovimientoTramiteDTO> obtenerPendientesConDatosTramite() {	
		Predicate<List<NotificacionMovimientoTramiteDTO>> prLstVacio =  p -> !p.isEmpty();
		List<NotificacionMovimientoTramiteDTO> lstNotificaciones = em.createNamedQuery("NotificacionMovimientoTramite.findAllTramitesNotificacionFallida", NotificacionMovimientoTramiteDTO.class)
				.getResultList();
		
		return Optional.ofNullable(lstNotificaciones).filter(prLstVacio).orElse(null);
	}

	/**
	 * Marca una notificación como enviada exitosamente.
	 * Actualiza fecha_notificacion y envio_confirmado = true.
	 * 
	 * @param idNotificacionMovimiento ID de la notificación
	 * @param fechaEnvio Fecha del envío exitoso
	 * @return true si se actualizó correctamente
	 */
	public boolean marcarComoEnviado(long idNotificacionMovimiento, Date fechaEnvio) {
		try {
			NotificacionMovimientoTramite entity = em.find(NotificacionMovimientoTramite.class, idNotificacionMovimiento);
			if (entity != null) {
				entity.setFechaNotificacion(fechaEnvio);
				entity.setEnvioConfirmado(true);
				em.merge(entity);
				LOGGER.debug("Notificación {} marcada como enviada", idNotificacionMovimiento);
				return true;
			}
			return false;
		} catch (Exception e) {
			LOGGER.error("Error al marcar notificación {} como enviada: ", idNotificacionMovimiento, e);
			return false;
		}
	}
	
	public NotificacionMovimientoTramiteDTO buscarPorIdTramite(long idTramite, Integer idTipoNotificacion) {
		try {
			return  em.createNamedQuery("NotificacionMovimientoTramite.findByIdTramite", NotificacionMovimientoTramiteDTO.class)
					.setParameter("idTramite", idTramite)
					.setParameter("idTipoNotificacion", idTipoNotificacion)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
}

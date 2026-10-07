package mx.gob.atdt.interprete.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.LocalBean;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatDiasSemanaDTO;
import mx.gob.atdt.interprete.dto.NotificacionesDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.model.CatDiasSemana;
import mx.gob.atdt.interprete.model.Notificaciones;
import mx.gob.atdt.interprete.model.Proyecto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.NoResultException;

@Stateless
@LocalBean
public class NotificacionesDAO extends IBaseService<NotificacionesDTO, Long> {
	
	@Override
	public NotificacionesDTO buscarPorId(Long id) {
		try {
			return em.createNamedQuery("Notificaciones.findById", NotificacionesDTO.class)
				.setParameter("idNotificacion", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<NotificacionesDTO> buscarTodos() {
		List<NotificacionesDTO> listado = em.createNamedQuery("Notificaciones.findAll", 
				NotificacionesDTO.class).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}

	public NotificacionesDTO buscarPorIdProyecto(Long id) {		
		try {
			return em.createNamedQuery("Notificaciones.findByIdProyecto", NotificacionesDTO.class)
				.setParameter("idProyecto", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public NotificacionesDTO buscarActivoPorIdProyecto(Long id) {
		try {
			return em.createNamedQuery("Notificaciones.findActivoByIdProyecto", NotificacionesDTO.class)
					.setParameter("idProyecto", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(NotificacionesDTO e) {
		Notificaciones notificaciones = new Notificaciones();
		notificaciones.setIdNotificacion(e.getIdNotificacion());
		notificaciones.setProyecto(em.getReference(Proyecto.class, e.getProyectoDTO().getIdProyecto()));
		notificaciones.setEnvioNotificaciones(e.isEnvioNotificaciones());
		notificaciones.setCorreosNotificacion(e.getCorreosNotificacion());
		if (e.getCatDiaSemanaDTO() != null && e.getCatDiaSemanaDTO().getIdDiaSemana() != null) {
		    notificaciones.setCatDiaSemana(
		        em.getReference(CatDiasSemana.class, e.getCatDiaSemanaDTO().getIdDiaSemana())
		    );
		} else {
		    notificaciones.setCatDiaSemana(null);
		}		notificaciones.setIdUsuario(e.getIdUsuario());
		notificaciones.setFechaCreacion(e.getFechaCreacion());
		notificaciones.setFechaActualizacion(e.getFechaActualizacion());
		notificaciones.setActivo(e.isActivo());
		notificaciones.setSeccionSincronizada(e.isSeccionSincronizada());	
		em.merge(notificaciones);
	}
	
}

package mx.gob.atdt.interprete.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.DetHomeDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.model.DetHome;
import mx.gob.atdt.interprete.model.Proyecto;

@Stateless
@LocalBean
public class DetHomeDAO extends IBaseService<DetHomeDTO, Long> {

	@Override
	public DetHomeDTO buscarPorId(Long id) {
		List<DetHomeDTO> listado = em.createNamedQuery("DetHome.findByIdDetHome", DetHomeDTO.class)
				.setParameter("idDetHome", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

	@Override
	public void actualizar(DetHomeDTO e) {
		DetHome detHome = new DetHome();
		detHome.setIdDetalleHome(e.getIdDetalleHome());
		detHome.setProyecto(em.getReference(Proyecto.class, e.getProyectoDTO().getIdProyecto()));
		detHome.setRutaArchivoLogotipo(e.getRutaArchivoLogotipo());
		detHome.setHabilitaNotificacion(e.isHabilitaNotificacion());
		detHome.setDescripcionNotificacion(e.getDescripcionNotificacion());
		detHome.setFechaCreacion(e.getFechaCreacion());
		detHome.setFechaUltimaActualizacion(e.getFechaUltimaActualizacion());
		detHome.setActivo(e.isActivo());
		detHome.setSeccionSincronizada(e.isSeccionSincronizada());
		detHome.setPersonalizaPausa(e.isPersonalizaPausa());
		detHome.setTituloPausa(e.getTituloPausa());
		detHome.setDescripcionPausa(e.getDescripcionPausa());
		em.merge(detHome);
	}

	public boolean buscarDetalleHomePorId(Long idProyecto) {
		List<DetHomeDTO> listado = em.createNamedQuery("DetHome.findByIdProyecto", DetHomeDTO.class)
				.setParameter("idProyecto", idProyecto).getResultList();
		return listado != null && !listado.isEmpty() ? true : false;
	}
	
	@SuppressWarnings("unchecked")
	public DetHomeDTO buscarPorIdProyecto(Long idProyecto) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("d.id_detalle_home , p.id_proyecto , d.ruta_archivo_logotipo , ");
		strQuery.append("d.habilita_notificacion , d.descripcion_notificacion , d.fecha_creacion , d.fecha_ultima_actualizacion , d.activo ");
		strQuery.append("FROM motor_interprete.det_home d ");
		strQuery.append("JOIN motor_interprete.proyecto p on p.id_proyecto = d.id_proyecto ");
		strQuery.append("WHERE p.id_proyecto  = :idProyecto");
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idProyecto", idProyecto);
		List<Object[]> rows = query.getResultList();
		List<DetHomeDTO> lstDetHome = new ArrayList<DetHomeDTO>();
		for (Object[] row : rows) {
			DetHomeDTO homeDTO = new DetHomeDTO();
			homeDTO.setIdDetalleHome(Long.parseLong(String.valueOf(row[0])));
			homeDTO.setProyectoDTO(new ProyectoDTO(idProyecto));
			homeDTO.setRutaArchivoLogotipo((String) row[2]);
			homeDTO.setHabilitaNotificacion((boolean) row[3]);
			homeDTO.setDescripcionNotificacion((String) row[4]);
			homeDTO.setFechaCreacion((Date) row[5]);
			homeDTO.setFechaUltimaActualizacion((Date) row[6]);
			homeDTO.setActivo((boolean) row[7]);
			lstDetHome.add(homeDTO);
		}
		return lstDetHome.get(0);
	}
}

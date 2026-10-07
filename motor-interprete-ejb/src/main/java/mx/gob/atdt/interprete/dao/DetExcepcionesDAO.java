package mx.gob.atdt.interprete.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.DetExcepcionTramiteDTO;
import mx.gob.atdt.interprete.dto.DetHomeDTO;
import mx.gob.atdt.interprete.model.DetExcepcionTramite;
import mx.gob.atdt.interprete.model.DetHome;

@Stateless
@LocalBean
public class DetExcepcionesDAO extends IBaseService<DetExcepcionTramiteDTO, Long> {
	
	@Override
	public DetExcepcionTramiteDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(DetExcepcionTramiteDTO e) {
		DetExcepcionTramite excepcion = new DetExcepcionTramite();
		excepcion.setIdExcepcion(e.getIdExcepcion());
		excepcion.setDetHome(em.getReference(DetHome.class, e.getDetHomeDTO().getIdDetalleHome()));
		excepcion.setDescripcionExcepcion(e.getDescripcionExcepcion());
		excepcion.setOrden(e.getOrden());
		excepcion.setActivo(e.isActivo());
		em.merge(excepcion);
	}
	
	public List<DetExcepcionTramiteDTO> buscarExcepcionesPorIdDetalleHome(Long idDetalleHome) {
		List<DetExcepcionTramiteDTO> listado = em.createNamedQuery("DetExcepcionTramite.findByIdDetalleHome", DetExcepcionTramiteDTO.class)
				.setParameter("idDetalleHome", idDetalleHome)
				.getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}
	
	@SuppressWarnings("unchecked")
	public List<DetExcepcionTramiteDTO> buscarExcepcionesPorIdDetHome(Long idDetHome) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("det.id_excepcion, dh.id_detalle_home, det.descripcion_excepcion, det.orden, det.activo ");
		strQuery.append("FROM motor_interprete.det_excepcion_tramite det ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = det.id_detalle_home ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome ");
		strQuery.append("AND det.activo = true  ");
		strQuery.append("ORDER BY det.orden");
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idDetalleHome", idDetHome);

		List<Object[]> rows = query.getResultList();
		List<DetExcepcionTramiteDTO> lstExcepciones = new ArrayList<DetExcepcionTramiteDTO>();
		for (Object[] row : rows) {
			DetExcepcionTramiteDTO excepcion = new DetExcepcionTramiteDTO();
			excepcion.setIdExcepcion(Long.parseLong(String.valueOf(row[0])));
			excepcion.setDetHomeDTO(new DetHomeDTO(idDetHome));
			excepcion.setDescripcionExcepcion((String) row[2]);
			excepcion.setOrden((int) row[3]);
			excepcion.setActivo((boolean) row[4]);
			lstExcepciones.add(excepcion);
		}

		return lstExcepciones != null && !lstExcepciones.isEmpty() ? lstExcepciones : null;
	}
}

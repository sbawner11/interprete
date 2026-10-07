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
import mx.gob.atdt.interprete.dto.DetHomeDTO;
import mx.gob.atdt.interprete.dto.DetRequisitoDTO;
import mx.gob.atdt.interprete.model.DetHome;
import mx.gob.atdt.interprete.model.DetRequisito;

@Stateless
@LocalBean
public class DetRequisitoDAO extends IBaseService<DetRequisitoDTO, Long> {

	@Override
	public DetRequisitoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(DetRequisitoDTO e) {
		DetRequisito requisito = new DetRequisito();
		requisito.setIdRequisito(e.getIdRequisito());
		requisito.setDetHome(em.getReference(DetHome.class, e.getDetHomeDTO().getIdDetalleHome()));
		requisito.setDescripcionRequisito(e.getDescripcionRequisito());
		requisito.setOrden(e.getOrden());
		requisito.setActivo(e.isActivo());
		em.merge(requisito);
	}

	public boolean buscarRequisitoPorID(Long idRequisito) {
		List<DetRequisitoDTO> listado = em.createNamedQuery("DetRequisito.findByIdRequisito", DetRequisitoDTO.class)
				.setParameter("idRequisito", idRequisito).getResultList();
		return listado != null && !listado.isEmpty() ? true : false;
	}

	@SuppressWarnings("unchecked")
	public List<DetRequisitoDTO> buscarRequisitosPorIdDetalleHome(Long idDetHome) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("dr.id_requisito , dr.id_detalle_home, dr.descripcion_requisito , dr.orden, dr.activo ");
		strQuery.append("FROM motor_interprete.det_requisito dr ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = dr.id_detalle_home  ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome ");
		strQuery.append("AND dr.activo = true ");
		strQuery.append("ORDER BY dr.orden");
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idDetalleHome", idDetHome);

		List<Object[]> rows = query.getResultList();
		List<DetRequisitoDTO> lstRequisitos = new ArrayList<DetRequisitoDTO>();
		for (Object[] row : rows) {
			DetRequisitoDTO req = new DetRequisitoDTO();
			req.setIdRequisito(Long.parseLong(String.valueOf(row[0])));
			req.setDetHomeDTO(new DetHomeDTO(idDetHome));
			req.setDescripcionRequisito((String) row[2]);
			req.setOrden((int) row[3]);
			req.setActivo((boolean) row[4]);
			lstRequisitos.add(req);
		}

		return lstRequisitos != null && !lstRequisitos.isEmpty() ? lstRequisitos : null;
	}
}

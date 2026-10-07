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
import mx.gob.atdt.interprete.dto.DetEspecificacionRequisitoDTO;
import mx.gob.atdt.interprete.dto.DetRequisitoDTO;
import mx.gob.atdt.interprete.model.DetEspecificacionRequisito;
import mx.gob.atdt.interprete.model.DetRequisito;

@Stateless
@LocalBean
public class DetEspecificacionRequisitoDAO extends IBaseService<DetEspecificacionRequisitoDTO, Long> {

	@Override
	public DetEspecificacionRequisitoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void actualizar(DetEspecificacionRequisitoDTO e) {
		DetEspecificacionRequisito especificacion = new DetEspecificacionRequisito();
		especificacion.setIdEspecificacion(e.getIdEspecificacion());
		especificacion.setDetRequisito(em.getReference(DetRequisito.class, e.getDetRequisitoDTO().getIdRequisito()));
		especificacion.setDescripcionEspecificacion(e.getDescripcionEspecificacion());
		especificacion.setOrden(e.getOrden());
		especificacion.setActivo(e.isActivo());
		em.merge(especificacion);
	}
	
	@SuppressWarnings("unchecked")
	public List<DetEspecificacionRequisitoDTO> buscarEspecificacionesPorIdRequisito(Long idRequisito) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("der.id_especificacion , dr.id_requisito , der.descripcion_especificacion , der.orden, der.activo  ");
		strQuery.append("FROM motor_interprete.det_especificacion_requisito der ");
		strQuery.append("JOIN motor_interprete.det_requisito  dr on dr.id_requisito = der.id_requisito  ");
		strQuery.append("WHERE dr.id_requisito = :idRequisito ");
		strQuery.append("AND der.activo = true ");
		strQuery.append("ORDER BY der.orden");
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idRequisito", idRequisito);

		List<Object[]> rows = query.getResultList();
		List<DetEspecificacionRequisitoDTO> lstEspecificaciones = new ArrayList<DetEspecificacionRequisitoDTO>();
		for (Object[] row : rows) {
			DetEspecificacionRequisitoDTO esp = new DetEspecificacionRequisitoDTO();
			esp.setIdEspecificacion(Long.parseLong(String.valueOf(row[0])));
			esp.setDetRequisitoDTO(new DetRequisitoDTO(idRequisito));
			esp.setDescripcionEspecificacion((String) row[2]);
			esp.setOrden((int) row[3]);
			esp.setActivo((boolean) row[4]);
			lstEspecificaciones.add(esp);
		}
		
		return lstEspecificaciones != null && !lstEspecificaciones.isEmpty() ? lstEspecificaciones : null;
	}

}

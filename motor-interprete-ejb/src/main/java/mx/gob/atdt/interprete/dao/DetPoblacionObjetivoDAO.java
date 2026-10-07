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
import mx.gob.atdt.interprete.dto.DetPoblacionObjetivoDTO;
import mx.gob.atdt.interprete.model.DetHome;
import mx.gob.atdt.interprete.model.DetPoblacionObjetivo;

@Stateless
@LocalBean
public class DetPoblacionObjetivoDAO extends IBaseService<DetPoblacionObjetivoDTO, Long> {

	@Override
	public DetPoblacionObjetivoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void actualizar(DetPoblacionObjetivoDTO e) {
		DetPoblacionObjetivo poblacion = new DetPoblacionObjetivo();
		poblacion.setIdPoblacionObjetivo(e.getIdPoblacionObjetivo());
		poblacion.setDetHome(em.getReference(DetHome.class, e.getDetHomeDTO().getIdDetalleHome()));
		poblacion.setDescripcionPoblacionObjetivo(e.getDescripcionPoblacionObjetivo());
		poblacion.setOrden(e.getOrden());
		poblacion.setActivo(e.isActivo());
		em.merge(poblacion);
	}
	
	@SuppressWarnings("unchecked")
	public List<DetPoblacionObjetivoDTO> buscarListaPoblacionPorIdDetalleHome(Long idDetalleHome) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("d.id_poblacion_objetivo , h.id_detalle_home , d.descripcion_poblacion_objetivo , d.orden, d.activo ");
		strQuery.append("FROM motor_interprete.det_poblacion_objetivo d ");
		strQuery.append("JOIN motor_interprete.det_home h on h.id_detalle_home = d.id_detalle_home ");
		strQuery.append("WHERE h.id_detalle_home = :idDetalleHome ");
		strQuery.append("AND d.activo = true ");
		strQuery.append("ORDER BY d.orden");
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idDetalleHome", idDetalleHome);

		List<Object[]> rows = query.getResultList();
		List<DetPoblacionObjetivoDTO> lstPobObjetivos = new ArrayList<DetPoblacionObjetivoDTO>();
		for (Object[] row : rows) {
			DetPoblacionObjetivoDTO pob = new DetPoblacionObjetivoDTO();
			pob.setIdPoblacionObjetivo(Long.parseLong(String.valueOf(row[0])));
			pob.setDetHomeDTO(new DetHomeDTO(idDetalleHome));
			pob.setDescripcionPoblacionObjetivo((String) row[2]);
			pob.setOrden((int) row[3]);
			pob.setActivo((boolean) row[4]);
			lstPobObjetivos.add(pob);
		}
		
		return lstPobObjetivos != null && !lstPobObjetivos.isEmpty() ? lstPobObjetivos : null;
	}

}

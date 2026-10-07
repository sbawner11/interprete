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
import mx.gob.atdt.interprete.dto.DetObjetivosProgramaDTO;
import mx.gob.atdt.interprete.model.DetHome;
import mx.gob.atdt.interprete.model.DetObjetivosPrograma;

@Stateless
@LocalBean
public class DetObjetivosProgramaDAO extends IBaseService<DetObjetivosProgramaDTO, Long> {

	@Override
	public DetObjetivosProgramaDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(DetObjetivosProgramaDTO e) {
		DetObjetivosPrograma objetivo = new DetObjetivosPrograma();
		objetivo.setIdObjetivo(e.getIdObjetivo());
		objetivo.setDetHome(em.getReference(DetHome.class, e.getDetHomeDTO().getIdDetalleHome()));
		objetivo.setDescripcionObjetivo(e.getDescripcionObjetivo());
		objetivo.setOrden(e.getOrden());
		objetivo.setActivo(e.isActivo());
		em.merge(objetivo);
	}

	@SuppressWarnings("unchecked")
	public List<DetObjetivosProgramaDTO> buscarObjetivosPorIdDetalleHome(Long idDetalleHome) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("dop.id_objetivo , dop.id_detalle_home, dop.descripcion_objetivo , dop.orden, dop.activo ");
		strQuery.append("FROM motor_interprete.det_objetivos_programa dop ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = dop.id_detalle_home ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome ");
		strQuery.append("AND dop.activo = true ");
		strQuery.append("ORDER BY dop.orden");
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idDetalleHome", idDetalleHome);

		List<Object[]> rows = query.getResultList();
		List<DetObjetivosProgramaDTO> lstObjetivos = new ArrayList<DetObjetivosProgramaDTO>();
		for (Object[] row : rows) {
			DetObjetivosProgramaDTO obj = new DetObjetivosProgramaDTO();
			obj.setIdObjetivo(Long.parseLong(String.valueOf(row[0])));
			obj.setDetHomeDTO(new DetHomeDTO(idDetalleHome));
			obj.setDescripcionObjetivo((String) row[2]);
			obj.setOrden((int) row[3]);
			obj.setActivo((boolean) row[4]);
			lstObjetivos.add(obj);
		}
		return lstObjetivos != null && !lstObjetivos.isEmpty() ? lstObjetivos : null;
	}
}

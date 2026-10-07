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
import mx.gob.atdt.interprete.dto.DetApoyoOtorgadoDTO;
import mx.gob.atdt.interprete.dto.DetHomeDTO;
import mx.gob.atdt.interprete.model.DetApoyoOtorgado;
import mx.gob.atdt.interprete.model.DetHome;

@Stateless
@LocalBean
public class DetApoyoOtorgadoDAO extends IBaseService<DetApoyoOtorgadoDTO, Long> {
	
	@Override
	public DetApoyoOtorgadoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(DetApoyoOtorgadoDTO e) {
		DetApoyoOtorgado apoyo = new DetApoyoOtorgado();
		apoyo.setIdApoyo(e.getIdApoyo());
		apoyo.setDetHome(em.getReference(DetHome.class, e.getDetHomeDTO().getIdDetalleHome()));
		apoyo.setDescripcionApoyoOtorgado(e.getDescripcionApoyoOtorgado());
		apoyo.setOrden(e.getOrden());
		apoyo.setActivo(e.isActivo());
		em.merge(apoyo);
	}
	
	@SuppressWarnings("unchecked")
	public List<DetApoyoOtorgadoDTO> buscarApoyosPorIdDetalleHome(Long idDetalleHome) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("d.id_apoyo , d.id_detalle_home , d.descripcion_apoyo_otorgado , d.orden, d.activo ");
		strQuery.append("FROM motor_interprete.det_apoyo_otorgado d ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = d.id_detalle_home ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome ");
		strQuery.append("AND d.activo = true  ");
		strQuery.append("ORDER BY d.orden");
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idDetalleHome", idDetalleHome);

		List<Object[]> rows = query.getResultList();
		List<DetApoyoOtorgadoDTO> lstApoyo = new ArrayList<DetApoyoOtorgadoDTO>();
		for (Object[] row : rows) {
			DetApoyoOtorgadoDTO apoyo = new DetApoyoOtorgadoDTO();
			apoyo.setIdApoyo(Long.parseLong(String.valueOf(row[0])));
			apoyo.setDetHomeDTO(new DetHomeDTO(idDetalleHome));
			apoyo.setDescripcionApoyoOtorgado((String) row[2]);
			apoyo.setOrden((int) row[3]);
			apoyo.setActivo((boolean) row[4]);
			lstApoyo.add(apoyo);
		}
		
		return lstApoyo != null && !lstApoyo.isEmpty() ? lstApoyo : null;
	}

}

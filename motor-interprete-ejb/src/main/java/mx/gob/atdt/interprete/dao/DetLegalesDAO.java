package mx.gob.atdt.interprete.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.DetLegalesDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.model.DetLegales;
import mx.gob.atdt.interprete.model.Proyecto;

@Stateless
@LocalBean
public class DetLegalesDAO extends IBaseService<DetLegalesDTO, Long> {

	@Override
	public DetLegalesDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(DetLegalesDTO dto) {		
		DetLegales detalleLegal =	new DetLegales(); 
		
		detalleLegal.setIdDetalleLegal(dto.getIdDetalleLegal());
		detalleLegal.setProyecto(em.getReference(Proyecto.class, dto.getProyectoDTO().getIdProyecto()));
		detalleLegal.setContieneAvisoSimplificado(dto.isContieneAvisoSimplificado());
		detalleLegal.setCuerpoAvisoSimplificado(dto.getCuerpoAvisoSimplificado());
		detalleLegal.setContieneAvisoIntegral(dto.isContieneAvisoIntegral());
		detalleLegal.setCuerpoAvisoIntegral(dto.getCuerpoAvisoIntegral());
		detalleLegal.setContieneManifiesto(dto.isContieneManifiesto());
		detalleLegal.setCuerpoManifiesto(dto.getCuerpoManifiesto());
		detalleLegal.setFechaCreacion(dto.getFechaCreacion());
		detalleLegal.setFechaUltimaActualizacion(dto.getFechaUltimaActualizacion());
		detalleLegal.setActivo(dto.isActivo());
		detalleLegal.setSeccionSincronizada(dto.isSeccionSincronizada());
	
		em.merge(detalleLegal);
		
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public DetLegalesDTO buscarPorIdProyecto(Long idProyecto) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" d.id_detalle_legal, p.id_proyecto , d.contiene_aviso_simplificado , d.cuerpo_aviso_simplificado, d.contiene_aviso_integral, d.cuerpo_aviso_integral, d.contiene_manifiesto, d.cuerpo_manifiesto, ");
		strQuery.append(" d.fecha_creacion, d.fecha_ultima_actualizacion, d.activo, d.seccion_sincronizada ");
		strQuery.append(" FROM motor_interprete.det_legales d  ");
		strQuery.append(" JOIN motor_interprete.proyecto p on p.id_proyecto = d.id_proyecto ");
		strQuery.append(" WHERE p.id_proyecto = :idProyecto ");
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idProyecto", idProyecto);
		List<Object[]> rows = query.getResultList();
		List<ProyectoDTO> lstLegales = new ArrayList<ProyectoDTO>();
		for (Object[] row : rows) {
			DetLegalesDTO legales = new DetLegalesDTO();
			legales.setIdDetalleLegal(Long.parseLong(String.valueOf(row[0])));
			legales.setProyectoDTO(new ProyectoDTO(idProyecto));
			legales.setContieneAvisoSimplificado((boolean) row[2]);
		}
		
		List<DetLegalesDTO> listado = em
				.createNamedQuery("DetLegales.findByIdProyecto", DetLegalesDTO.class)
				.setParameter("idProyecto", idProyecto).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;

	}

}

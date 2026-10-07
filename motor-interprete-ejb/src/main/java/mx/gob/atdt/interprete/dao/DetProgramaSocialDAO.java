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
import mx.gob.atdt.interprete.dto.DetProgramaSocialDTO;
import mx.gob.atdt.interprete.model.DetHome;
import mx.gob.atdt.interprete.model.DetProgramaSocial;

@Stateless
@LocalBean
public class DetProgramaSocialDAO extends IBaseService<DetProgramaSocialDTO, Long> {

	@Override
	public DetProgramaSocialDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void actualizar(DetProgramaSocialDTO e) {
		DetProgramaSocial detPrograma = new DetProgramaSocial();
		detPrograma.setIdDetallePrograma(e.getIdDetallePrograma());
		detPrograma.setDetHome(em.getReference(DetHome.class, e.getDetHomeDTO().getIdDetalleHome()));
		detPrograma.setHabilitaCicloPrograma(e.isHabilitaCicloPrograma());
		detPrograma.setDescripcionCicloPrograma(e.getDescripcionCicloPrograma());
		detPrograma.setDescripcionTipoApoyo(e.getDescripcionTipoApoyo());
		detPrograma.setDescripcionDuracionApoyo(e.getDescripcionDuracionApoyo());
		detPrograma.setHabilitaProgramaSimultaneo(e.isHabilitaProgramaSimultaneo());
		
		em.merge(detPrograma);	
	}
	
	@SuppressWarnings("unchecked")
	public DetProgramaSocialDTO buscarPorIdDetalleHome(Long idDetalleHome) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("dps.id_detalle_programa , dh.id_detalle_home , ");
		strQuery.append("dps.habilita_ciclo_programa , dps.descripcion_ciclo_programa , ");
		strQuery.append("dps.descripcion_tipo_apoyo , dps.descripcion_duracion_apoyo, dps.habilita_programa_simultaneo ");
		strQuery.append("FROM motor_interprete.det_programa_social dps ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = dps.id_detalle_home ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome");
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idDetalleHome", idDetalleHome);
		List<Object[]> rows = query.getResultList();
		List<DetProgramaSocialDTO> lstProgramaSocial = new ArrayList<DetProgramaSocialDTO>();
		for (Object[] row : rows) {
			DetProgramaSocialDTO programa = new DetProgramaSocialDTO();
			programa.setIdDetallePrograma(Long.parseLong(String.valueOf(row[0])));
			programa.setDetHomeDTO(new DetHomeDTO(idDetalleHome));
			programa.setHabilitaCicloPrograma((boolean) row[2]);
			programa.setDescripcionCicloPrograma((String) row[3]);
			programa.setDescripcionTipoApoyo((String) row[4]);
			programa.setDescripcionDuracionApoyo((String) row[5]);
			programa.setHabilitaProgramaSimultaneo((boolean) row[6]);
			lstProgramaSocial.add(programa);
		}
		return lstProgramaSocial.get(0);
	}

}

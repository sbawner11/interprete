package mx.gob.atdt.interprete.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.DetGestionUsuarioDTO;

@Stateless
@LocalBean
public class UsuarioGestionDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccesoLlaveDAO.class);

	/**
	 * Método que consulta la información para el acceso mediante llave.
	 * 
	 * @param idProyecto
	 * @return DetAccesoLLaveDTO
	 * @throws Exception
	 */
	public DetGestionUsuarioDTO consultaUsuarioGestion(Long idProyecto) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" dgu.perfil_supervisor_prevencion, dgu.perfil_operador_prevencion, dgu.perfil_supervisor_conclusion, ");
		strQuery.append(" dgu.perfil_operador_conclusion , dgu.correo_conclusion, dgu.correo_prevencion, dgu.correo_subsanar_prevencion, ");
		strQuery.append(" dgu.habilita_prevencion, dgu.adjunta_oficio, dgu.dias_subsanar_prevencion, dgu.activo, dgu.api_key, ");
		strQuery.append(" dgu.habilita_resolucion, dgu.perfil_supervisor_resolucion, dgu.perfil_operador_resolucion, dgu.resolucion_positiva_obligatoria, ");
		strQuery.append(" dgu.resolucion_negativa_obligatoria, dgu.correo_resolucion_positiva, dgu.correo_resolucion_negativa ");
		strQuery.append(" FROM motor_interprete.det_gestion_usuario dgu ");
		strQuery.append(" JOIN motor_interprete.proyecto p on p.id_proyecto = dgu.id_proyecto  ");
		strQuery.append(" WHERE p.id_proyecto = :idProyecto ");

		List<DetGestionUsuarioDTO> lstGestion = new ArrayList<DetGestionUsuarioDTO>();

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", idProyecto);

		for (Object[] row : rows) {
			DetGestionUsuarioDTO gestionUsuario = new DetGestionUsuarioDTO();
			gestionUsuario.setPerfilSupervisorPrevencion((boolean) row[0]);
			gestionUsuario.setPerfilOperadorPrevencion((boolean) row[1]);
			gestionUsuario.setPerfilSupervisorConclusion((boolean) row[2]);
			gestionUsuario.setPerfilOperadorConclusion((boolean) row[3]);
			gestionUsuario.setCorreoConclusion((String) row[4]);
			gestionUsuario.setCorreoPrevencion((String) row[5]);
			gestionUsuario.setCorreoSubsanarPrevencion((String) row[6]);
			gestionUsuario.setHabilitaPrevencion((boolean) row[7]);
			gestionUsuario.setAdjuntaOficio((boolean) row[8]);
			gestionUsuario.setDiasSubsanarPrevencion((Integer) row[9]);
			gestionUsuario.setActivo(row[10] != null ? (boolean) row[10] : null);
			gestionUsuario.setApiKey((String) row[11]);			
			gestionUsuario.setHabilitaResolucion((boolean) row[12]);
			gestionUsuario.setPerfilSupervisorResolucion((boolean) row[13]);
			gestionUsuario.setPerfilOperadorResolucion((boolean) row[14]);
			gestionUsuario.setResolucionPositivaObligatoria((boolean) row[15]);
			gestionUsuario.setResolucionNegativaObligatoria((boolean) row[16]);
			gestionUsuario.setCorreoResolucionPositiva((String) row[17]);
			gestionUsuario.setCorreoResolucionNegativa((String) row[18]);	

			lstGestion.add(gestionUsuario);
		}

		return lstGestion != null && !lstGestion.isEmpty() ? lstGestion.get(0) : null;
	}

	/**
	 * Método auxiliar que realiza la ejecución de la consulta enviada.
	 * 
	 * @param consulta
	 * @param nombreParametro
	 * @param valorParametro
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> ejecutarConsulta(String consulta, String nombreParametro, Long valorParametro)
			throws Exception {
		List<Object[]> rows = null;

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery(consulta);
			if (BeanUtils.isNotNull(nombreParametro)) {
				query.setParameter(nombreParametro, valorParametro);
			}
			rows = query.getResultList();

			entityManager.getTransaction().commit();

		} catch (Throwable e) {
			if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("Error:  ", e);
			throw new Exception("Error en consulta. " + e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}

		return rows;
	}

}

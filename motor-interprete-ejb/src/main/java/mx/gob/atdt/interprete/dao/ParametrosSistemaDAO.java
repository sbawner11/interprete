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

import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.ParametrosSistemaDTO;

@Stateless
@LocalBean
public class ParametrosSistemaDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParametrosSistemaDAO.class);

	/**
	 * Método que consulta la información de parametros de sistema.
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ParametrosSistemaDTO> consultaParametrosActivosSistema(Integer activo) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" ps.id_parametro, ps.valor, ps.descripcion, ps.activo ");
		strQuery.append(" FROM motor_interprete.parametros_sistema ps ");		
		strQuery.append(" WHERE ps.activo = :activo ");

		List<ParametrosSistemaDTO> lstParametros = new ArrayList<ParametrosSistemaDTO>();

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "activo", activo);

		for (Object[] row : rows) {
			ParametrosSistemaDTO parametroDTO = new ParametrosSistemaDTO();
			parametroDTO.setIdParametro(Integer.parseInt(String.valueOf(row[0])));
			parametroDTO.setValor(Integer.parseInt(String.valueOf(row[1])));
			parametroDTO.setDescripcion((String) row[2]);
			parametroDTO.setActivo(Integer.parseInt(String.valueOf(row[3])));
			
			lstParametros.add(parametroDTO);
		}

		return lstParametros != null && !lstParametros.isEmpty() ? lstParametros : null;
	}

	/**
	 * Método auxiliar que realiza la ejecución de la consulta enviada.
	 * 
	 * @param consulta
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> ejecutarConsulta(String consulta, String nombreParametro, Integer valorParametro)
			throws Exception {
		List<Object[]> rows = null;

		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery(consulta);
			query.setParameter(nombreParametro, valorParametro);
			
			rows = query.getResultList();

			entityManager.getTransaction().commit();

		} catch (Throwable e) {
			if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("Error consulta de parametros:  ", e);
			throw new Exception("Error en consulta de parametros de sistema. " + e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}

		return rows;
	}

}

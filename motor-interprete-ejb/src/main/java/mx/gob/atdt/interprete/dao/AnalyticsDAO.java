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
import mx.gob.atdt.interprete.dto.DetAnalyticsDTO;

@Stateless
@LocalBean
public class AnalyticsDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(AnalyticsDAO.class);

	/**
	 * Método que consulta la información para el acceso mediante llave.
	 * 
	 * @param idProyecto
	 * @return DetAccesoLLaveDTO
	 * @throws Exception
	 */
	public DetAnalyticsDTO consultaAnalytics(Long idProyecto) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(
				" da.identificador_analytics, da.titulo_busqueda, da.descripcion_busqueda, da.palabra_clave_busqueda, da.titulo_grap, da.descripcion_grap, da.url_grap, da.ruta_imagen_grap ");
		strQuery.append(" FROM motor_interprete.det_analytics da ");
		strQuery.append(" JOIN motor_interprete.proyecto p on p.id_proyecto = da.id_proyecto ");
		strQuery.append(" WHERE p.id_proyecto = :idProyecto ");

		List<DetAnalyticsDTO> lstAnalytics = new ArrayList<DetAnalyticsDTO>();

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", idProyecto);

		for (Object[] row : rows) {
			DetAnalyticsDTO dto = new DetAnalyticsDTO();
			dto.setIdentificadorAnalytics((String) row[0]);
			dto.setTituloBusqueda((String) row[1]);
			dto.setDescripcionBusqueda((String) row[2]);
			dto.setPalabraClaveBusqueda((String) row[3]);
			dto.setTituloGrap((String) row[4]);
			dto.setDescripcionGrap((String) row[5]);
			dto.setUrlGrap((String) row[6]);
			dto.setRutaImagenGrap((String) row[7]);
			lstAnalytics.add(dto);
		}

		return lstAnalytics != null && !lstAnalytics.isEmpty() ? lstAnalytics.get(0) : null;
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

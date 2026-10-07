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
import mx.gob.atdt.interprete.dto.DetCaptchaDTO;

@Stateless
@LocalBean
public class CaptchaDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccesoLlaveDAO.class);

	/**
	 * Método que consulta la información para el acceso mediante llave.
	 * 
	 * @param idProyecto
	 * @return DetCaptchaDTO
	 * @throws Exception
	 */
	public DetCaptchaDTO consultaCaptcha(Long idProyecto) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" dc.llave_publica, dc.llave_privada ");
		strQuery.append(" FROM motor_interprete.det_captcha dc ");
		strQuery.append(" JOIN motor_interprete.proyecto p on p.id_proyecto = dc.id_proyecto ");
		strQuery.append(" WHERE p.id_proyecto = :idProyecto ");
		strQuery.append(" AND dc.activo = true ");

		List<DetCaptchaDTO> lstDetCaptcha = new ArrayList<DetCaptchaDTO>();

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", idProyecto);

		for (Object[] row : rows) {
			DetCaptchaDTO captcha = new DetCaptchaDTO();
			captcha.setLlavePublica((String) row[0]);
			captcha.setLlavePrivada((String) row[1]);

			lstDetCaptcha.add(captcha);
		}

		return lstDetCaptcha != null && !lstDetCaptcha.isEmpty() ? lstDetCaptcha.get(0) : null;
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

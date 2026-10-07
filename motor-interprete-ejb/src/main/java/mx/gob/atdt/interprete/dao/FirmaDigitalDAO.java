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
import mx.gob.atdt.interprete.dto.DetFirmaDigitalDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;

@Stateless
@LocalBean
public class FirmaDigitalDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccesoLlaveDAO.class);

	/**
	 * Método que consulta la información para el apartado de Firma digital
	 * 
	 * @param idProyecto
	 * @return
	 * @throws Exception
	 */
	public DetFirmaDigitalDTO consultaFirmaDigital(Long idProyecto) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" dfd.id_detalle_firma, dfd.id_proyecto, dfd.clave_sistema, ");
		strQuery.append(" dfd.url_firmado, dfd.url_redirecciona, dfd.usuario_dominio_seg, ");
		strQuery.append(" dfd.contrasena_dominio_seg, dfd.activo, dfd.firma_ciudadano ");
		strQuery.append(" FROM motor_interprete.det_firma_digital dfd  ");
		strQuery.append(" JOIN motor_interprete.proyecto p on p.id_proyecto = dfd .id_proyecto ");
		strQuery.append(" WHERE p.id_proyecto = :idProyecto ");
		strQuery.append(" AND dfd.activo = true ");

		List<DetFirmaDigitalDTO> lstDetFirma = new ArrayList<DetFirmaDigitalDTO>();

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", idProyecto);

		for (Object[] row : rows) {
			DetFirmaDigitalDTO firma = new DetFirmaDigitalDTO();
			firma.setIdDetalleFirma(Long.parseLong(String.valueOf(row[0])));
			firma.setProyectoDTO(new ProyectoDTO(Long.parseLong(String.valueOf(row[1]))));
			firma.setClaveSistema((String) row[2]);
			firma.setUrlFirmado((String) row[3]);
			firma.setUrlRedirecciona((String) row[4]);
			firma.setUsuarioDominioSeg((String) row[5]);
			firma.setContrasenaDominioSeg((String) row[6]);
			firma.setActivo((boolean) row[7]);
			firma.setFirmaCiudadano((boolean) row[8]);
			lstDetFirma.add(firma);
		}

		return lstDetFirma != null && !lstDetFirma.isEmpty() ? lstDetFirma.get(0) : null;
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

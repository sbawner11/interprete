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
import mx.gob.atdt.interprete.dto.DetAccesoLLaveDTO;

@Stateless
@LocalBean
public class AccesoLlaveDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccesoLlaveDAO.class);

	/**
	 * Método que consulta la información para el acceso mediante llave.
	 * 
	 * @param idProyecto
	 * @return DetAccesoLLaveDTO
	 * @throws Exception
	 */
	public DetAccesoLLaveDTO consultaAccesoLlave(Long idProyecto) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" dal.id_detalle_acceso, dal.clave_sistema, dal.url_redireccionar, dal.usuario_dominio_seg, dal.contrasena_dominio_seg, dal.codigo_secreto, dal.autenticacion_ciudadano, dal.limitar_unico_tramite, dal.valida_rol, dal.roles_permitidos");
		strQuery.append(" FROM motor_interprete.det_acceso_llave dal ");
		strQuery.append(" JOIN motor_interprete.proyecto p on p.id_proyecto = dal.id_proyecto  ");
		strQuery.append(" WHERE p.id_proyecto = :idProyecto ");

		List<DetAccesoLLaveDTO> lstAccesoLlave = new ArrayList<DetAccesoLLaveDTO>();

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", idProyecto);

		for (Object[] row : rows) {
			DetAccesoLLaveDTO llave = new DetAccesoLLaveDTO();
			llave.setIdDetalleAcceso(Long.parseLong(String.valueOf(row[0])));
			llave.setClaveSistema((String) row[1]);
			llave.setUrlRedireccionar((String) row[2]);
			llave.setUsuarioDominoSeg((String) row[3]);
			llave.setContrasenaDominioSeg((String) row[4]);
			llave.setCodigoSecreto((String) row[5]);
			llave.setAutenticacionCiudadano((boolean) row[6]);
			llave.setLimitarUnicoTramite((boolean) row[7]);
			llave.setValidaRol((boolean) row[8]);
			llave.setRolesPermitidos((String) row[9]);
			lstAccesoLlave.add(llave);
		}

		return lstAccesoLlave != null && !lstAccesoLlave.isEmpty() ? lstAccesoLlave.get(0) : null;
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

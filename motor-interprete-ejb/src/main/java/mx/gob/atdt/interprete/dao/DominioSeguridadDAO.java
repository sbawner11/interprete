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
import mx.gob.atdt.interprete.dto.CatTipoSecurityDomainDTO;
import mx.gob.atdt.interprete.dto.DetSecurityDomainDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;

@Stateless
@LocalBean
public class DominioSeguridadDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(DominioSeguridadDAO.class);

	/**
	 * Método que consulta la información para el dominio de seguridad.
	 * 
	 * @param idProyecto
	 * @return DetSecurityDomainDTO
	 * @throws Exception
	 */
	public DetSecurityDomainDTO consultaDominioSeguridad(Long idProyecto) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" dsd.id_detalle_security, dsd.id_proyecto, dsd.usuario, dsd.contrasenia, dsd.url_sistema, ");
		strQuery.append(" dsd.activo, dsd.id_tipo_security_domain ");
		strQuery.append(" FROM motor_interprete.det_security_domain dsd  ");
		strQuery.append(" JOIN motor_interprete.proyecto p on p.id_proyecto = dsd.id_proyecto ");
		strQuery.append(" WHERE p.id_proyecto = :idProyecto ");
		strQuery.append(" AND dsd.id_tipo_security_domain = 1 ");

		List<DetSecurityDomainDTO> lstDominio = new ArrayList<DetSecurityDomainDTO>();

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", idProyecto);

		for (Object[] row : rows) {
			DetSecurityDomainDTO dominio = new DetSecurityDomainDTO();
			dominio.setidDetalleSecurity(Long.parseLong(String.valueOf(row[0])));
			dominio.setProyectoDTO(new ProyectoDTO(Long.parseLong(String.valueOf(row[1]))));
			dominio.setUsuario((String) row[2]);
			dominio.setContrasenia((String) row[3]);
			dominio.setUrlSistema((String) row[4]);
			dominio.setActivo((boolean) row[5]);
			dominio.setCatTipoSecurityDomainDTO(new CatTipoSecurityDomainDTO(Integer.parseInt(String.valueOf(row[6]))));
			lstDominio.add(dominio);
		}

		return lstDominio != null && !lstDominio.isEmpty() ? lstDominio.get(0) : null;
	}
	
	/**
	 * Método que consulta la información para el dominio de seguridad de CURP.
	 * 
	 * @param idProyecto
	 * @return DetSecurityDomainDTO
	 * @throws Exception
	 */
	public DetSecurityDomainDTO consultaDominioSeguridadCurp(Long idProyecto) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" dsd.id_detalle_security, dsd.id_proyecto, dsd.usuario, dsd.contrasenia, dsd.url_sistema, ");
		strQuery.append(" dsd.activo, dsd.id_tipo_security_domain ");
		strQuery.append(" FROM motor_interprete.det_security_domain dsd  ");
		strQuery.append(" JOIN motor_interprete.proyecto p on p.id_proyecto = dsd.id_proyecto ");
		strQuery.append(" WHERE p.id_proyecto = :idProyecto ");
		strQuery.append(" AND dsd.id_tipo_security_domain = 2 ");

		List<DetSecurityDomainDTO> lstDominio = new ArrayList<DetSecurityDomainDTO>();

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", idProyecto);

		for (Object[] row : rows) {
			DetSecurityDomainDTO dominio = new DetSecurityDomainDTO();
			dominio.setidDetalleSecurity(Long.parseLong(String.valueOf(row[0])));
			dominio.setProyectoDTO(new ProyectoDTO(Long.parseLong(String.valueOf(row[1]))));
			dominio.setUsuario((String) row[2]);
			dominio.setContrasenia((String) row[3]);
			dominio.setUrlSistema((String) row[4]);
			dominio.setActivo((boolean) row[5]);
			dominio.setCatTipoSecurityDomainDTO(new CatTipoSecurityDomainDTO(Integer.parseInt(String.valueOf(row[6]))));
			lstDominio.add(dominio);
		}

		return lstDominio != null && !lstDominio.isEmpty() ? lstDominio.get(0) : null;
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

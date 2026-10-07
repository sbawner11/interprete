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
import mx.gob.atdt.interprete.dto.DetPagoDTO;
import mx.gob.atdt.interprete.dto.ParametrosDetallePagoDTO;

@Stateless
@LocalBean
public class PagosDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(PagosDAO.class);

	/**
	 * Método que consulta la información para el acceso mediante llave.
	 * 
	 * @param idProyecto
	 * @return DetPagoDTO
	 * @throws Exception
	 */
	public DetPagoDTO consultaDetallePagos(Long idProyecto) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" dp.id_detalle_pago, dp.id_tipo_costo, dp.monto_costo_fijo, dp.url_servicio, dp.identificador_proceso, dp.activo ");
		strQuery.append(" FROM motor_interprete.det_pago dp ");
		strQuery.append(" JOIN motor_interprete.proyecto p on p.id_proyecto = dp.id_proyecto ");
		strQuery.append(" WHERE p.id_proyecto = :idProyecto ");

		List<DetPagoDTO> lstPago = new ArrayList<DetPagoDTO>();

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", idProyecto);

		for (Object[] row : rows) {
			DetPagoDTO dto = new DetPagoDTO();
			dto.setIdDetallePago(Long.parseLong(String.valueOf(row[0])));
			dto.getCatTipoCostoDTO().setIdTipoCosto((Integer) row[1]);
			dto.setMontoCostoFijo(row[2] != null ? Double.valueOf(String.valueOf(row[2])) : null);
			dto.setUrlServicio((String) row[3]);
			dto.setIdentificadorProceso((String) row[4]);
			dto.setActivo(row[5] != null ? (boolean) row[5] : null);
			lstPago.add(dto);
		}

		return lstPago != null && !lstPago.isEmpty() ? lstPago.get(0) : null;
	}

	public List<ParametrosDetallePagoDTO> consultaParametrosPago(Long idPago) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" pdp.id_parametro, pdp.nombre_parametro, pdp.valor_parametro ");
		strQuery.append(" FROM motor_interprete.parametros_detalle_pago pdp ");
		strQuery.append(" JOIN motor_interprete.det_pago dp on dp.id_detalle_pago = pdp.id_detalle_pago ");
		strQuery.append(" WHERE dp.id_detalle_pago = :idPago ");
		strQuery.append(" AND pdp.activo = true");
		
		List<ParametrosDetallePagoDTO> lstParametros = new ArrayList<ParametrosDetallePagoDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idPago", idPago);
		
		for (Object[] row : rows) {
			ParametrosDetallePagoDTO dto = new ParametrosDetallePagoDTO();
			dto.setIdParametro(Long.parseLong(String.valueOf(row[0])));
			dto.setDetPagoDTO(new DetPagoDTO(idPago));
			dto.setNombreParametro((String) row[1]);
			dto.setValorParametro((String) row[2]);
			lstParametros.add(dto);
		}
		
		return lstParametros != null && !lstParametros.isEmpty() ? lstParametros : null;
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

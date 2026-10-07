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
import mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO;
import mx.gob.atdt.interprete.dto.CatOrigenTokenDTO;
import mx.gob.atdt.interprete.dto.CatTipoPlantillaDTO;
import mx.gob.atdt.interprete.dto.CatTipoProyectoDTO;
import mx.gob.atdt.interprete.dto.CatDependenciaDTO;
import mx.gob.atdt.interprete.dto.DetElementosTokenDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;

@Stateless
@LocalBean
public class ArchivoRespuestaDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(AccesoLlaveDAO.class);

	/**
	 * Método que consulta la información para archivos de respuesta.
	 * 
	 * @param idProyecto
	 * @return List<ArchivosRespuestaTokenDTO>
	 * @throws Exception
	 */
	public List<ArchivosRespuestaTokenDTO> consultaArchivoRespuesta(Long idProyecto) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" art.id_archivo_respuesta, art.ruta_archivo_respuesta, art.nombre_archivo, ");
		strQuery.append(" art.habilita_firma, art.firma_supervisor, art.firma_operador, art.coodenada_qr_x, art.coodenada_qr_y, art.id_tipo_plantilla, art.activo, ");
		strQuery.append(" p.id_proyecto, p.nombre_proyecto, p.id_tipo_proyecto, p.id_dependencia, cd.descripcion, p.aviso ");
		strQuery.append(" FROM motor_interprete.archivos_respuesta_token art ");
		strQuery.append(" JOIN motor_interprete.proyecto p on p.id_proyecto = art.id_proyecto ");
		strQuery.append(" JOIN motor_interprete.cat_dependencia cd on cd.id_dependencia = p.id_dependencia ");
		strQuery.append(" LEFT JOIN motor_interprete.cat_tipo_plantilla ctp on art.id_tipo_plantilla = ctp.id_tipo_plantilla ");
		strQuery.append(" WHERE p.id_proyecto = :idProyecto ");
		strQuery.append(" AND art.activo = true ");

		List<ArchivosRespuestaTokenDTO> lstArchivos = new ArrayList<ArchivosRespuestaTokenDTO>();

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", idProyecto);

		for (Object[] row : rows) {
			ArchivosRespuestaTokenDTO respuesta = new ArchivosRespuestaTokenDTO();
			respuesta.setIdArchivoRespuesta(Long.parseLong(String.valueOf(row[0])));
			respuesta.setRutaArchivoRespuesta((String) row[1]);
			respuesta.setNombreArchivo((String) row[2]);
			respuesta.setHabilitaFirma((boolean) row[3]);
			respuesta.setFirmaSupervisor((boolean) row[4]);
			respuesta.setFirmaOperador((boolean) row[5]);
			respuesta.setCoodenadaQrX(row[6] != null ? Long.parseLong(String.valueOf(row[6])) : null);
			respuesta.setCoodenadaQrY(row[7] != null ? Long.parseLong(String.valueOf(row[7])) : null);
			respuesta.setCatTipoPlantillaDTO(row[8] != null ? new CatTipoPlantillaDTO(Integer.parseInt(String.valueOf(row[8])))	: null);
			respuesta.setActivo((boolean) row[9]);
			respuesta.setProyectoDTO(row[10] != null ? new ProyectoDTO(Long.parseLong(String.valueOf(row[10]))) : null);
			
			respuesta.getProyectoDTO().setNombreProyecto((String) row[11]);
			respuesta.getProyectoDTO().setCatTipoProyectoDTO(row[12] != null ? new CatTipoProyectoDTO(Integer.parseInt(String.valueOf(row[12]))) : null);
			respuesta.getProyectoDTO().setCatDependenciaDTO(row[13] != null ? new CatDependenciaDTO(Integer.parseInt(String.valueOf(row[13])), (String) row[14]) : null);
			respuesta.getProyectoDTO().setAviso((boolean) row[15]);
		
			lstArchivos.add(respuesta);
		}

		return lstArchivos != null && !lstArchivos.isEmpty() ? lstArchivos : null;
	}
	
	/**
	 * Método que consulta la información de archivos token configurados para el archivo de respuesta.
	 * 
	 * @param idArchivoRespuesta
	 * @return List<ArchivosRespuestaTokenDTO>
	 * @throws Exception
	 */
	public List<DetElementosTokenDTO> consultaElementosToken(Long idArchivoRespuesta) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(" det.nombre_token, det.id_origen_token, det.estructura_folio, det.id_formato_fecha, det.longitud_folio, det.campo_personalizado, det.id_componente ");
		strQuery.append(" FROM motor_interprete.det_elementos_token det ");
		strQuery.append(" JOIN motor_interprete.archivos_respuesta_token art on art.id_archivo_respuesta = det.id_archivo_respuesta ");
		strQuery.append(" WHERE art.id_archivo_respuesta = :idArchivoRespuesta ");
		strQuery.append(" AND det.activo = true ");
		strQuery.append(" ORDER BY det.orden ");

		List<DetElementosTokenDTO> lstTokens = new ArrayList<DetElementosTokenDTO>();

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idArchivoRespuesta", idArchivoRespuesta);

		for (Object[] row : rows) {
			DetElementosTokenDTO token = new DetElementosTokenDTO();
			token.setNombreToken((String) row[0]);
			token.setCatOrigenTokenDTO(new CatOrigenTokenDTO((int) row[1]));
			token.setEstructuraFolio((String) row[2]);
			token.setIdFormatoFecha((int) row[3]);
			token.setLongitudFolio((String) row[4]);
			token.setCampoPersonalizado((String) row[5]);
			token.setIdComponente(Long.getLong(String.valueOf(row[6])));
			lstTokens.add(token);
		}

		return lstTokens != null && !lstTokens.isEmpty() ? lstTokens : null;
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

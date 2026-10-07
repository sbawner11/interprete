package mx.gob.atdt.interprete.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.CatEstadosSistemaDTO;
import mx.gob.atdt.interprete.dto.DetApoyoOtorgadoDTO;
import mx.gob.atdt.interprete.dto.DetEspecificacionRequisitoDTO;
import mx.gob.atdt.interprete.dto.DetEstadoSistemaDTO;
import mx.gob.atdt.interprete.dto.DetExcepcionTramiteDTO;
import mx.gob.atdt.interprete.dto.DetHomeDTO;
import mx.gob.atdt.interprete.dto.DetLegalesDTO;
import mx.gob.atdt.interprete.dto.DetObjetivosProgramaDTO;
import mx.gob.atdt.interprete.dto.DetPoblacionObjetivoDTO;
import mx.gob.atdt.interprete.dto.DetProgramaSocialDTO;
import mx.gob.atdt.interprete.dto.DetRequisitoDTO;
import mx.gob.atdt.interprete.dto.DetTramiteServicioDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;

@Stateless
@LocalBean
public class HomeDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(HomeDAO.class);
	
	/**
	 * * Método que verificar si existe el esquema en la BD. 
	 *  (No existe cuando se ejecuta por primera vez el ear en el server)
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeEsquemaInterprete() throws Exception {
		
		List<Object[]> rows = null; 
		List<String> lstEsquema = new ArrayList<String>();
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT oid, nspname  ");
		strQuery.append(" FROM pg_namespace ");
		strQuery.append(" WHERE nspname = '");
		strQuery.append(Constantes.ESQUEMA_INTERPRETE).append("';");		
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery(strQuery.toString());	
			
			rows = query.getResultList();			
			for (Object[] row : rows) {
				lstEsquema.add(String.valueOf(row[1]));
			}			
			
			entityManager.getTransaction().commit();
		} catch (Throwable e) {
			if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("Error al realizar consulta a esquema:  ", e);
			throw new Exception("Error en consulta de esquema. ", e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
		return lstEsquema != null && !lstEsquema.isEmpty() ? true : false;
	}
	
	/**
	 * Metodo que verifica si existe el nombre de la tabla det_estado_sistema.
	 * 
	 * @return
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeTablaEstadoSistema() throws Exception {
			
		List<Object[]> rows = null; 
		List<String> lstTablas = new ArrayList<String>();
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT t.table_schema, t.table_name");
		strQuery.append(" FROM information_schema.tables t ");
		strQuery.append(" WHERE t.table_schema='");
		strQuery.append(Constantes.ESQUEMA_INTERPRETE).append("'");
		strQuery.append(" AND t.table_name='");
		strQuery.append(Constantes.NOMBRE_BASE_TABLA_ESTADO_SISTEMA).append("'");		
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery(strQuery.toString());	
			
			rows = query.getResultList();			
			for (Object[] row : rows) {
				lstTablas.add(String.valueOf(row[1]));
			}			
			
			entityManager.getTransaction().commit();
		} catch (Throwable e) {
			if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("Error al realizar consulta a esquema:  ", e);
			throw new Exception("Error en consulta de esquema. ", e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
		return lstTablas != null && !lstTablas.isEmpty() ? true : false;
	}

	
	/**
	 * Método que realiza la consulta del estado actual del Sistema.
	 * @return
	 * @throws Exception
	 */
	public DetEstadoSistemaDTO consultarEstadoSistema() throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT d.id, d.id_estado_sistema, d.fecha_ultima_actualizacion ");		
		strQuery.append("FROM motor_interprete.det_estado_sistema d ");
	
		List<DetEstadoSistemaDTO> lstDetEstadoSistema = new ArrayList<DetEstadoSistemaDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), null, null);
		
		for (Object[] row : rows) {
			DetEstadoSistemaDTO estadoSistemaDTO = new DetEstadoSistemaDTO();
			estadoSistemaDTO.setId(Long.parseLong(String.valueOf(row[0])));
			estadoSistemaDTO.setCatEstadosSistemaDTO(new CatEstadosSistemaDTO((Integer)row[1]));
			estadoSistemaDTO.setFechaUltimaActualizacion((Date) row[2]);
			lstDetEstadoSistema.add(estadoSistemaDTO);
		}
		
		return lstDetEstadoSistema != null && !lstDetEstadoSistema.isEmpty() ? lstDetEstadoSistema.get(0) : null;
	}

	/**
	 * Método que realiza la consulta del detalle de un Proyecto.
	 * @return
	 * @throws Exception
	 */
	public ProyectoDTO consultaProyecto() throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(
				" p.id_proyecto, d.id_dependencia, d.descripcion as descDependencia, e.id_estatus_proyecto, e.descripcion as descEstatus,  ");
		strQuery.append(
				" t.id_tipo_proyecto, t.descripcion, p.id_usuario_llave_cdmx, p.nombre_proyecto, ");
		strQuery.append(
				" p.habilita_captcha, p.habilita_acceso_llave, p.habilita_pago_linea,  ");
		strQuery.append(
				" p.habilita_gestion_usuarios, p.habilita_firma_digital, p.habilita_detalle_legales, p.habilita_analytics, p.habilita_security_domain,");
		strQuery.append(" p.fecha_creacion ");
		strQuery.append(" FROM motor_interprete.proyecto  p ");
		strQuery.append(" JOIN motor_interprete.cat_dependencia d on d.id_dependencia = p.id_dependencia ");
		strQuery.append(
				" JOIN motor_interprete.cat_estatus_proyecto e on e.id_estatus_proyecto = p.id_estatus_proyecto ");
		strQuery.append(" JOIN motor_interprete.cat_tipo_proyecto  t on t.id_tipo_proyecto = p.id_tipo_proyecto  ");
		strQuery.append(" WHERE 1 = 1 ");

		List<ProyectoDTO> lstProyectos = new ArrayList<ProyectoDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), null, null);
		
		for (Object[] row : rows) {
			ProyectoDTO proyecto = new ProyectoDTO();
			proyecto.setIdProyecto(Long.parseLong(String.valueOf(row[0])));
			proyecto.getCatDependenciaDTO().setIdDependencia((Integer) row[1]);
			proyecto.getCatDependenciaDTO().setDescripcion((String) row[2]);
			proyecto.getCatEstatusProyectoDTO().setIdEstatusProyecto((Integer) row[3]);
			proyecto.getCatEstatusProyectoDTO().setDescripcion((String) row[4]);
			proyecto.getCatTipoProyectoDTO().setIdTipoProyecto((Integer) row[5]);
			proyecto.getCatTipoProyectoDTO().setDescripcion((String) row[6]);
			proyecto.setNombreProyecto((String) row[8]);
			proyecto.setHabilitaCaptcha((boolean) row[9]);
			proyecto.setHabilitaAccesoLlave((boolean) row[10]);
			proyecto.setHabilitaPagoLinea((boolean) row[11]);
			proyecto.setHabilitaGestionUsuarios((boolean) row[12]);
			proyecto.setHabilitaFirmaDigital((boolean) row[13]);
			proyecto.setHabilitaDetalleLegales((boolean) row[14]);
			proyecto.setHabilitaAnalytics((boolean) row[15]);
			proyecto.setHabilitaSecurityDomain((boolean) row[16]);
			proyecto.setFechaCreacion((Date) row[17]);

			lstProyectos.add(proyecto);
		}
		
		return lstProyectos != null && !lstProyectos.isEmpty() ? lstProyectos.get(0) : null;
	}

	/**
	 * Método que obtiene el detalle de un Home por el Id de proyecto.
	 * @param idProyecto
	 * @return
	 * @throws Exception
	 */
	public DetHomeDTO buscarPorIdProyecto(Long idProyecto) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("d.id_detalle_home , p.id_proyecto , d.ruta_archivo_logotipo , ");
		strQuery.append(
			"d.habilita_notificacion , d.descripcion_notificacion , d.fecha_creacion , "
			+ "d.fecha_ultima_actualizacion , d.activo, d.seccion_sincronizada, d.personaliza_pausa, "
			+ "d.titulo_pausa, d.descripcion_pausa ");
		strQuery.append("FROM motor_interprete.det_home d ");
		strQuery.append("JOIN motor_interprete.proyecto p on p.id_proyecto = d.id_proyecto ");
		strQuery.append("WHERE p.id_proyecto  = :idProyecto");

		List<DetHomeDTO> lstDetHome = new ArrayList<DetHomeDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", idProyecto);
		
		for (Object[] row : rows) {
			DetHomeDTO homeDTO = new DetHomeDTO();
			homeDTO.setIdDetalleHome(Long.parseLong(String.valueOf(row[0])));
			homeDTO.setProyectoDTO(new ProyectoDTO(idProyecto));
			homeDTO.setRutaArchivoLogotipo((String) row[2]);
			homeDTO.setHabilitaNotificacion((boolean) row[3]);
			homeDTO.setDescripcionNotificacion((String) row[4]);
			homeDTO.setFechaCreacion((Date) row[5]);
			homeDTO.setFechaUltimaActualizacion((Date) row[6]);
			homeDTO.setActivo((boolean) row[7]);
			homeDTO.setSeccionSincronizada((boolean) row[8]);
			homeDTO.setPersonalizaPausa((boolean) row[9]);
			homeDTO.setTituloPausa((String) row[10]);
			homeDTO.setDescripcionPausa((String) row[11]);
			lstDetHome.add(homeDTO);
		}
		
		return lstDetHome.get(0);
	}

	/**
	 * Método que obtiene la información del detalle de Legales por el Id de proyecto.
	 * @param idProyecto
	 * @return
	 * @throws Exception
	 */
	public DetLegalesDTO buscarDetalleLegalesPorIdProyecto(Long idProyecto) throws Exception {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append(
				" d.id_detalle_legal, p.id_proyecto , d.contiene_aviso_simplificado , d.cuerpo_aviso_simplificado, d.contiene_aviso_integral, d.cuerpo_aviso_integral, d.contiene_manifiesto, d.cuerpo_manifiesto ");
		strQuery.append(" FROM motor_interprete.det_legales d  ");
		strQuery.append(" JOIN motor_interprete.proyecto p on p.id_proyecto = d.id_proyecto ");
		strQuery.append(" WHERE p.id_proyecto = :idProyecto ");

		List<DetLegalesDTO> lstLegales = new ArrayList<DetLegalesDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idProyecto", idProyecto);
		
		for (Object[] row : rows) {
			DetLegalesDTO legales = new DetLegalesDTO();
			legales.setIdDetalleLegal(Long.parseLong(String.valueOf(row[0])));
			legales.setProyectoDTO(new ProyectoDTO(Long.parseLong(String.valueOf(row[1]))));
			legales.setContieneAvisoSimplificado((boolean) row[2]);
			legales.setCuerpoAvisoSimplificado((String) row[3]);
			legales.setContieneAvisoIntegral((boolean) row[4]);
			legales.setCuerpoAvisoIntegral((String) row[5]);
			legales.setContieneManifiesto((boolean) row[6]);
			legales.setCuerpoManifiesto((String) row[7]);
			lstLegales.add(legales);
		}
		
		return lstLegales != null && !lstLegales.isEmpty() ? lstLegales.get(0) : null;
	}
	


	/**
	 * Método que realiza la consulta de un home de tipo Trámite servicio por el Id idDetalleHome.
	 * @param idDetalleHome
	 * @return
	 * @throws Exception
	 */
	public DetTramiteServicioDTO buscarPorIdDetalleHome(Long idDetalleHome) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("dts.id_detalle_tramite, dh.id_detalle_home, dts.habilita_costo_tramite,  ");
		strQuery.append("dts.descripcion_costo_tramite , dts.habilita_excepcion_tramite ");
		strQuery.append("FROM motor_interprete.det_tramite_servicio dts ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = dts.id_detalle_home  ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome");

		List<DetTramiteServicioDTO> lstTramite = new ArrayList<DetTramiteServicioDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idDetalleHome", idDetalleHome);
		
		for (Object[] row : rows) {
			DetTramiteServicioDTO tramite = new DetTramiteServicioDTO();
			tramite.setIdDetalleTramite(Long.parseLong(String.valueOf(row[0])));
			tramite.setDetHomeDTO(new DetHomeDTO(idDetalleHome));
			tramite.setHabilitaCostoTramite((boolean) row[2]);
			tramite.setDescripcionCostoTramite((String) row[3]);
			tramite.setHabilitaExcepcionTramite((boolean) row[4]);
			lstTramite.add(tramite);
		}
		
		return lstTramite != null && !lstTramite.isEmpty() ? lstTramite.get(0) : null;
	}

	/**
	 * Método que realiza la consulta de requisitos por el Id idDetalleHome.
	 * @param idDetalleHome
	 * @return
	 * @throws Exception
	 */
	public List<DetRequisitoDTO> buscarRequisitosPorIdDetalleHome(Long idDetalleHome) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("dr.id_requisito , dr.id_detalle_home, dr.descripcion_requisito , dr.orden, dr.activo ");
		strQuery.append("FROM motor_interprete.det_requisito dr ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = dr.id_detalle_home  ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome ");
		strQuery.append("AND dr.activo = true ");
		strQuery.append("ORDER BY dr.orden");

		List<DetRequisitoDTO> lstRequisitos = new ArrayList<DetRequisitoDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idDetalleHome", idDetalleHome);
		
		for (Object[] row : rows) {
			DetRequisitoDTO req = new DetRequisitoDTO();
			req.setIdRequisito(Long.parseLong(String.valueOf(row[0])));
			req.setDetHomeDTO(new DetHomeDTO(idDetalleHome));
			req.setDescripcionRequisito((String) row[2]);
			req.setOrden((int) row[3]);
			req.setActivo((boolean) row[4]);
			lstRequisitos.add(req);
		}
		
		return lstRequisitos != null && !lstRequisitos.isEmpty() ? lstRequisitos : null;
	}

	/**
	 * Método que realiza la consulta de Especificaciones por el Id idRequisito.
	 * @param idRequisito
	 * @return
	 * @throws Exception
	 */
	public List<DetEspecificacionRequisitoDTO> buscarEspecificacionesPorIdRequisito(Long idRequisito) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append(
				"der.id_especificacion , dr.id_requisito , der.descripcion_especificacion , der.orden, der.activo  ");
		strQuery.append("FROM motor_interprete.det_especificacion_requisito der ");
		strQuery.append("JOIN motor_interprete.det_requisito  dr on dr.id_requisito = der.id_requisito  ");
		strQuery.append("WHERE dr.id_requisito = :idRequisito ");
		strQuery.append("AND der.activo = true ");
		strQuery.append("ORDER BY der.orden");

		List<DetEspecificacionRequisitoDTO> lstEspecificaciones = new ArrayList<DetEspecificacionRequisitoDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idRequisito", idRequisito);
		
		for (Object[] row : rows) {
			DetEspecificacionRequisitoDTO esp = new DetEspecificacionRequisitoDTO();
			esp.setIdEspecificacion(Long.parseLong(String.valueOf(row[0])));
			esp.setDetRequisitoDTO(new DetRequisitoDTO(idRequisito));
			esp.setDescripcionEspecificacion((String) row[2]);
			esp.setOrden((int) row[3]);
			esp.setActivo((boolean) row[4]);
			lstEspecificaciones.add(esp);
		}
		
		return lstEspecificaciones != null && !lstEspecificaciones.isEmpty() ? lstEspecificaciones : null;
	}

	/**
	 * Método que realiza la consulta de Excepciones por el Id idDetalleHome.
	 * @param idDetalleHome
	 * @return
	 * @throws Exception
	 */
	public List<DetExcepcionTramiteDTO> buscarExcepcionesPorIdDetHome(Long idDetalleHome) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("det.id_excepcion, dh.id_detalle_home, det.descripcion_excepcion, det.orden, det.activo ");
		strQuery.append("FROM motor_interprete.det_excepcion_tramite det ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = det.id_detalle_home ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome ");
		strQuery.append("AND det.activo = true  ");
		strQuery.append("ORDER BY det.orden");

		List<DetExcepcionTramiteDTO> lstExcepciones = new ArrayList<DetExcepcionTramiteDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idDetalleHome", idDetalleHome);
		
		for (Object[] row : rows) {
			DetExcepcionTramiteDTO excepcion = new DetExcepcionTramiteDTO();
			excepcion.setIdExcepcion(Long.parseLong(String.valueOf(row[0])));
			excepcion.setDetHomeDTO(new DetHomeDTO(idDetalleHome));
			excepcion.setDescripcionExcepcion((String) row[2]);
			excepcion.setOrden((int) row[3]);
			excepcion.setActivo((boolean) row[4]);
			lstExcepciones.add(excepcion);
		}
		
		return lstExcepciones != null && !lstExcepciones.isEmpty() ? lstExcepciones : null;
	}

	/**
	 * Método que realiza la consulta del Detalle de Home de Programa social por el Id idDetalleHome.
	 * @param idDetalleHome
	 * @return
	 * @throws Exception
	 */
	public DetProgramaSocialDTO buscarProgramaSocialPorIdDetalleHome(Long idDetalleHome) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("dps.id_detalle_programa , dh.id_detalle_home , ");
		strQuery.append("dps.habilita_ciclo_programa , dps.descripcion_ciclo_programa , ");
		strQuery.append(
				"dps.descripcion_tipo_apoyo , dps.descripcion_duracion_apoyo, dps.habilita_programa_simultaneo ");
		strQuery.append("FROM motor_interprete.det_programa_social dps ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = dps.id_detalle_home ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome");

		List<DetProgramaSocialDTO> lstProgramaSocial = new ArrayList<DetProgramaSocialDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idDetalleHome", idDetalleHome);
		
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

	/**
	 * Método que realiza la consulta de Objetivos por Id idDetalleHome.
	 * @param idDetalleHome
	 * @return
	 * @throws Exception
	 */
	public List<DetObjetivosProgramaDTO> buscarObjetivosPorIdDetalleHome(Long idDetalleHome) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("dop.id_objetivo , dop.id_detalle_home, dop.descripcion_objetivo , dop.orden, dop.activo ");
		strQuery.append("FROM motor_interprete.det_objetivos_programa dop ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = dop.id_detalle_home ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome ");
		strQuery.append("AND dop.activo = true ");
		strQuery.append("ORDER BY dop.orden");

		List<DetObjetivosProgramaDTO> lstObjetivos = new ArrayList<DetObjetivosProgramaDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idDetalleHome", idDetalleHome);
		
		for (Object[] row : rows) {
			DetObjetivosProgramaDTO obj = new DetObjetivosProgramaDTO();
			obj.setIdObjetivo(Long.parseLong(String.valueOf(row[0])));
			obj.setDetHomeDTO(new DetHomeDTO(idDetalleHome));
			obj.setDescripcionObjetivo((String) row[2]);
			obj.setOrden((int) row[3]);
			obj.setActivo((boolean) row[4]);
			lstObjetivos.add(obj);
		}		

		return lstObjetivos != null && !lstObjetivos.isEmpty() ? lstObjetivos : null;
	}

	/**
	 * Método que realiza la consulta de Población Objetivo por Id idDetalleHome.
	 * @param idDetalleHome
	 * @return
	 * @throws Exception
	 */
	public List<DetPoblacionObjetivoDTO> buscarListaPoblacionPorIdDetalleHome(Long idDetalleHome) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append(
				"d.id_poblacion_objetivo , h.id_detalle_home , d.descripcion_poblacion_objetivo , d.orden, d.activo ");
		strQuery.append("FROM motor_interprete.det_poblacion_objetivo d ");
		strQuery.append("JOIN motor_interprete.det_home h on h.id_detalle_home = d.id_detalle_home ");
		strQuery.append("WHERE h.id_detalle_home = :idDetalleHome ");
		strQuery.append("AND d.activo = true ");
		strQuery.append("ORDER BY d.orden");

		List<DetPoblacionObjetivoDTO> lstPobObjetivos = new ArrayList<DetPoblacionObjetivoDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idDetalleHome", idDetalleHome);
		
		for (Object[] row : rows) {
			DetPoblacionObjetivoDTO pob = new DetPoblacionObjetivoDTO();
			pob.setIdPoblacionObjetivo(Long.parseLong(String.valueOf(row[0])));
			pob.setDetHomeDTO(new DetHomeDTO(idDetalleHome));
			pob.setDescripcionPoblacionObjetivo((String) row[2]);
			pob.setOrden((int) row[3]);
			pob.setActivo((boolean) row[4]);
			lstPobObjetivos.add(pob);
		}	

		return lstPobObjetivos != null && !lstPobObjetivos.isEmpty() ? lstPobObjetivos : null;
	}

	/**
	 * Método que realiza la consulta de apoyos por el Id idDetalleHome.
	 * @param idDetalleHome
	 * @return
	 * @throws Exception
	 */
	public List<DetApoyoOtorgadoDTO> buscarApoyosPorIdDetalleHome(Long idDetalleHome) throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("d.id_apoyo , d.id_detalle_home , d.descripcion_apoyo_otorgado , d.orden, d.activo ");
		strQuery.append("FROM motor_interprete.det_apoyo_otorgado d ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = d.id_detalle_home ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome ");
		strQuery.append("AND d.activo = true  ");
		strQuery.append("ORDER BY d.orden");

		List<DetApoyoOtorgadoDTO> lstApoyo = new ArrayList<DetApoyoOtorgadoDTO>();
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), "idDetalleHome", idDetalleHome);
		
		for (Object[] row : rows) {
			DetApoyoOtorgadoDTO apoyo = new DetApoyoOtorgadoDTO();
			apoyo.setIdApoyo(Long.parseLong(String.valueOf(row[0])));
			apoyo.setDetHomeDTO(new DetHomeDTO(idDetalleHome));
			apoyo.setDescripcionApoyoOtorgado((String) row[2]);
			apoyo.setOrden((int) row[3]);
			apoyo.setActivo((boolean) row[4]);
			lstApoyo.add(apoyo);
		}

		return lstApoyo != null && !lstApoyo.isEmpty() ? lstApoyo : null;
	}
		
	/**
	 * Método auxiliar que realiza la ejecución de la consulta enviada.
	 * @param consulta
	 * @param nombreParametro
	 * @param valorParametro
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> ejecutarConsulta(String consulta, String nombreParametro, Long valorParametro) throws Exception {
		List<Object[]> rows = null; 
				
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			Query query = entityManager.createNativeQuery(consulta);	
			if(BeanUtils.isNotNull(nombreParametro)) {
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

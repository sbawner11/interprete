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
import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.model.CatDependencia;
import mx.gob.atdt.interprete.model.CatEstatusProyecto;
import mx.gob.atdt.interprete.model.CatTipoProyecto;
import mx.gob.atdt.interprete.model.Proyecto;

@Stateless
@LocalBean
public class ProyectoDAO extends IBaseService<ProyectoDTO, Long> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProyectoDAO.class);

	@SuppressWarnings("unchecked")
	@Override
	public ProyectoDTO buscarPorId(Long id) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("p.id_proyecto, d.id_dependencia, d.descripcion as descDependencia, e.id_estatus_proyecto, e.descripcion as descEstatus,  ");
		strQuery.append("t.id_tipo_proyecto, t.descripcion, p.id_usuario_llave_cdmx, p.nombre_proyecto, ");
		strQuery.append("p.habilita_captcha, p.habilita_acceso_llave, p.habilita_pago_linea,  ");
		strQuery.append("p.habilita_gestion_usuarios, p.habilita_firma_digital, p.habilita_detalle_legales, p.habilita_analytics, p.habilita_security_domain, ");
		strQuery.append("p.habilita_security_domain_curp, p.aviso, p.fecha_creacion, p.habilita_captura_tramites, p.habilita_configuracion_catalogos, ");
		strQuery.append("case when p.habilita_pago_linea and l.completo then true else false end as proyectoLineaCaptura ");
		strQuery.append("FROM motor_interprete.proyecto p ");
		strQuery.append("JOIN motor_interprete.cat_dependencia d on d.id_dependencia = p.id_dependencia ");
		strQuery.append("JOIN motor_interprete.cat_estatus_proyecto e on e.id_estatus_proyecto = p.id_estatus_proyecto ");
		strQuery.append("JOIN motor_interprete.cat_tipo_proyecto  t on t.id_tipo_proyecto = p.id_tipo_proyecto  ");
		strQuery.append("LEFT JOIN motor_interprete.det_linea_captura l on l.id_proyecto = p.id_proyecto and l.completo = true ");
		strQuery.append("WHERE p.id_proyecto = :idProyec ");
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idProyec", id);

		List<Object[]> rows = query.getResultList();		
		List<ProyectoDTO> lstProyectos = new ArrayList<>();
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
			proyecto.setHabilitaSecurityDomainCurp((boolean) row[17]);
			proyecto.setAviso((boolean) row[18]);
			proyecto.setFechaCreacion((Date) row[19]);
			proyecto.setHabilitaCapturaTramites((boolean) row[20]);
			proyecto.setHabilitaConfiguracionCatalogos((boolean) row[21]);
			proyecto.setProyectoLineaCaptura((boolean) row[22]);
			lstProyectos.add(proyecto);
		}
		return !lstProyectos.isEmpty() ? lstProyectos.get(0) : null;
	}

	@Override
	public void actualizar(ProyectoDTO e) {
		Proyecto proyecto = new Proyecto();
		proyecto.setIdProyecto(e.getIdProyecto());
		proyecto.setNombreProyecto(e.getNombreProyecto());
		proyecto.setAcronimo(e.getAcronimo());
		proyecto.setCatTipoProyecto(
				em.getReference(CatTipoProyecto.class, e.getCatTipoProyectoDTO().getIdTipoProyecto()));
		proyecto.setCatDependencia(em.getReference(CatDependencia.class, e.getCatDependenciaDTO().getIdDependencia()));
		proyecto.setHabilitaCaptcha(e.isHabilitaCaptcha());
		proyecto.setHabilitaAccesoLlave(e.isHabilitaAccesoLlave());
		proyecto.setHabilitaPagoLinea(e.isHabilitaPagoLinea());
		proyecto.setHabilitaGestionUsuarios(e.isHabilitaGestionUsuarios());
		proyecto.setHabilitaFirmaDigital(e.isHabilitaFirmaDigital());
		proyecto.setHabilitaDetalleLegales(e.isHabilitaDetalleLegales());
		proyecto.setHabilitaAnalytics(e.isHabilitaAnalytics());
		proyecto.setHabilitaSecurityDomain(e.isHabilitaSecurityDomain());
		proyecto.setHabilitaSecurityDomainCurp(e.isHabilitaSecurityDomainCurp());
		proyecto.setHabilitaDistribucion(e.isHabilitaDistribucion());
		proyecto.setAviso(e.isAviso());
		proyecto.setHabilitaRevocacionAviso(e.isHabilitaRevocacionAviso());
		proyecto.setCatEstatusProyecto(
				em.getReference(CatEstatusProyecto.class, e.getCatEstatusProyectoDTO().getIdEstatusProyecto()));
		proyecto.setIdUsuarioLlaveCdmx(e.getUsuarioDTO().getIdUsuarioLlaveCdmx());
		proyecto.setFechaCreacion(e.getFechaCreacion());
		proyecto.setHabilitaCapturaTramites(e.isHabilitaCapturaTramites());
		proyecto.setHabilitaConfiguracionCatalogos(e.isHabilitaConfiguracionCatalogos());
		
		em.merge(proyecto);
	}

	public boolean buscarProyectoPorId(Long idProyecto) {
		List<ProyectoDTO> listado = em.createNamedQuery("Proyecto.findByIdProyecto", ProyectoDTO.class)
				.setParameter("idProyecto", idProyecto).getResultList();
		return listado != null && !listado.isEmpty();
	}

	/**
	 * Método que obtiene todos los proyectos registrados en BD.
	 * 
	 * @return
	 */
	public List<ProyectoDTO> buscarTodos() {
		List<ProyectoDTO> listado = em.createNamedQuery("Proyecto.findAllProyectos", ProyectoDTO.class).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}

	/**
	 * Método que realiza la consulta del detalle del proyecto del intérprete.
	 * 
	 * @return
	 * @throws Exception
	 */
	public ProyectoDTO consultaProyecto() throws Exception {

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT ");
		strQuery.append("p.id_proyecto, d.id_dependencia, d.descripcion as descDependencia, e.id_estatus_proyecto, e.descripcion as descEstatus,  ");
		strQuery.append("t.id_tipo_proyecto, t.descripcion, p.id_usuario_llave_cdmx, p.nombre_proyecto, p.acronimo, ");
		strQuery.append("p.habilita_captcha, p.habilita_acceso_llave, p.habilita_pago_linea,  ");
		strQuery.append("p.habilita_gestion_usuarios, p.habilita_firma_digital, p.habilita_detalle_legales, p.habilita_analytics, p.habilita_security_domain, ");
		strQuery.append("p.habilita_security_domain_curp, p.aviso, p.fecha_creacion, p.habilita_captura_tramites, p.habilita_configuracion_catalogos, ");
		strQuery.append("p.habilita_revocacion_aviso, ");
		strQuery.append("case when p.habilita_pago_linea and l.completo then true else false end as proyectoLineaCaptura ");
		strQuery.append("FROM motor_interprete.proyecto p ");
		strQuery.append("JOIN motor_interprete.cat_dependencia d on d.id_dependencia = p.id_dependencia ");
		strQuery.append("JOIN motor_interprete.cat_estatus_proyecto e on e.id_estatus_proyecto = p.id_estatus_proyecto ");
		strQuery.append("JOIN motor_interprete.cat_tipo_proyecto  t on t.id_tipo_proyecto = p.id_tipo_proyecto  ");
		strQuery.append("LEFT JOIN motor_interprete.det_linea_captura l on l.id_proyecto = p.id_proyecto and l.completo = true ");
		strQuery.append("WHERE 1 = 1 ");

		List<ProyectoDTO> lstProyectos = new ArrayList<>();
		
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
			proyecto.setAcronimo((String) row[9]);
			proyecto.setHabilitaCaptcha((boolean) row[10]);
			proyecto.setHabilitaAccesoLlave((boolean) row[11]);
			proyecto.setHabilitaPagoLinea((boolean) row[12]);
			proyecto.setHabilitaGestionUsuarios((boolean) row[13]);
			proyecto.setHabilitaFirmaDigital((boolean) row[14]);
			proyecto.setHabilitaDetalleLegales((boolean) row[15]);
			proyecto.setHabilitaAnalytics((boolean) row[16]);
			proyecto.setHabilitaSecurityDomain((boolean) row[17]);
			proyecto.setHabilitaSecurityDomainCurp((boolean) row[18]);
			proyecto.setAviso((boolean) row[19]);
			proyecto.setFechaCreacion((Date) row[20]);
			proyecto.setHabilitaCapturaTramites((boolean )row[21]);
			proyecto.setHabilitaConfiguracionCatalogos((boolean) row[22]);
			proyecto.setHabilitaRevocacionAviso((boolean) row[23]);
			proyecto.setProyectoLineaCaptura((boolean) row[24]);
			lstProyectos.add(proyecto);
		}

		return lstProyectos != null && !lstProyectos.isEmpty() ? lstProyectos.get(0) : null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizarEstatusProyecto(ProyectoDTO proyectoDTO) {
		final StringBuilder strQueryUpdate = new StringBuilder();

		strQueryUpdate.append("UPDATE ").append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_PROYECTO).append(" SET ")
			.append("id_estatus_proyecto = :idEstatus ")
			.append("WHERE id_proyecto = :idProyecto ");

		em.createNativeQuery(strQueryUpdate.toString())
			.setParameter("idEstatus", proyectoDTO.getCatEstatusProyectoDTO().getIdEstatusProyecto())
			.setParameter("idProyecto", proyectoDTO.getIdProyecto())
			.executeUpdate();
	}
	
	/**
	 * Método que obtiene el id y nombre del proyecto actual
	 * 
	 * @return
	 */
	public ProyectoDTO consultaIdNombreProyecto() {
		List<ProyectoDTO> lstProyecto = new ArrayList<>();
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("p.id_proyecto, p.nombre_proyecto ");
		strQuery.append("FROM ").append(Constantes.ESQUEMA_INTERPRETE).append(".").append(Constantes.NOMBRE_BASE_TABLA_PROYECTO).append(" p ");
		
		Query query = em.createNativeQuery(strQuery.toString());	
		List<Object[]> rows = query.getResultList();	
		for (Object[] row : rows) {
			ProyectoDTO proyectoTmp = new ProyectoDTO();
			proyectoTmp.setIdProyecto(Long.parseLong(String.valueOf(row[0])));
			proyectoTmp.setNombreProyecto((String) row[1]);			
			lstProyecto.add(proyectoTmp);
		}		
		return lstProyecto != null && !lstProyecto.isEmpty() ? lstProyecto.get(0) : null;
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

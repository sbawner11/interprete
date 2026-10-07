package mx.gob.atdt.interprete.estructura.formulario.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.utils.Constantes;

@Stateless
@LocalBean
public class AsignarPermisosBDDAO {
	
	@Inject
	protected EntityManager em;

	private static final Logger LOGGER = LoggerFactory.getLogger(AsignarPermisosBDDAO.class);
		
	/**
	 * Método que verificar si existe un nombre de usuario en BD.
	 * @param nombreUsuario
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeUsuario(String nombreUsuario) {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT pr.rolname ");
		strQuery.append(" FROM pg_catalog.pg_roles pr ");
		strQuery.append(" WHERE pr.rolname= :nombreUsuario");
		
		List<String> lstUsuarios = em.createNativeQuery(strQuery.toString())
				.setParameter("nombreUsuario", nombreUsuario)
				.getResultList();
		
		return lstUsuarios != null && !lstUsuarios.isEmpty() ? true : false;
	}
	
	/**
	 * Método que otorga los permisos de lectura a tablas a un usuario.
	 * @param cuentaUsuario
	 * @throws Exception
	 */
	public void otorgarPermisosUsuario(String cuentaUsuario) throws Exception {

		StringBuilder strPermisosEsquema = new StringBuilder();
		strPermisosEsquema.append("GRANT USAGE ON schema ");
		strPermisosEsquema.append(Constantes.ESQUEMA_INTERPRETE); 
		strPermisosEsquema.append(" TO ");
		strPermisosEsquema.append(cuentaUsuario);
		strPermisosEsquema.append(";");
		
		StringBuilder strPermisosAdicionales = new StringBuilder();
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".cat_asentamientos TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".cat_codigos_postales TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".cat_estados TO ").append(cuentaUsuario).append("; ");
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".cat_estatus_tramite TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".cat_municipios TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".cat_tipo_componente TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".componente TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".control_componentes TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".det_elementos_checkbox TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".det_elementos_menu TO ").append(cuentaUsuario).append("; ");
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".det_elementos_radioboton TO ").append(cuentaUsuario).append("; ");
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".secciones_formulario TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".subsecciones_formulario TO ").append(cuentaUsuario).append("; ");
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".tramites TO ").append(cuentaUsuario).append("; ");
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".usuario TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".componente_checkbox TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".componente_menu_desplegable TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".componente_radioboton TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".datos_tabla_dinamica TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".det_elementos_tabla TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".solicitud_linea_captura TO ").append(cuentaUsuario).append("; ");		
		strPermisosAdicionales.append("GRANT SELECT ON TABLE ").append(Constantes.ESQUEMA_INTERPRETE).append(".linea_captura TO ").append(cuentaUsuario).append("; ");		
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {		
			entityManager.getTransaction().begin();			
			entityManager.createNativeQuery(strPermisosEsquema.toString()).executeUpdate();
			LOGGER.info("Se otorgan permisos para utilizar esquema a: " + cuentaUsuario);
			
			/**Se ejecuta función que otorga permisos a tablas creadas de manera dinámica con el prefijo 'seccion_'**/			
			StoredProcedureQuery query = entityManager.createStoredProcedureQuery("motor_interprete.otorgar_permisos");
			query.registerStoredProcedureParameter("cuentausuario", String.class, ParameterMode.IN);
			query.setParameter("cuentausuario", cuentaUsuario);
			query.execute();
			LOGGER.info("Se otorgan permisos de consulta a tablas generadas de manera dinámica al usuario: " + cuentaUsuario);
			
			/**Se otorgan permisos a tablas adicionales que necesita el usuario**/
			entityManager.createNativeQuery(strPermisosAdicionales.toString()).executeUpdate();
			LOGGER.info("Se otorgan permisos de consulta a tablas adicionales al usuario: " + cuentaUsuario);
			entityManager.getTransaction().commit();				
		} catch (Throwable e) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No fue posible realizar la asignación de permisos a la cuenta de usuario:  " + cuentaUsuario, e);					
			throw new Exception("No fue posible realizar la asignación de permisos a la cuenta de usuario:  " + cuentaUsuario + e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}	
}

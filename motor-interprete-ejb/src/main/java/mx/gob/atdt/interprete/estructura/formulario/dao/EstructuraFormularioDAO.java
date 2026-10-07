package mx.gob.atdt.interprete.estructura.formulario.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.ComponenteAreaTextoDTO;
import mx.gob.atdt.interprete.dto.ComponenteCampoTextoDTO;
import mx.gob.atdt.interprete.dto.ComponenteCargaDocumentosDTO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxDTO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxUnicoDTO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosDomicilioDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonaMoralDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesLlaveDTO;
import mx.gob.atdt.interprete.dto.ComponenteFechaDTO;
import mx.gob.atdt.interprete.dto.ComponenteMenuDesplegableDTO;
import mx.gob.atdt.interprete.dto.ComponenteRadiobotonDTO;
import mx.gob.atdt.interprete.dto.ComponenteTablaDTO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;

@Stateless
@LocalBean
public class EstructuraFormularioDAO {

	@Inject
	protected EntityManager em;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EstructuraFormularioDAO.class);

	/**
	 * Método que verificar si existe un nombre de tabla en BD para la sección actual.
	 * @param seccion
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeTabla(SeccionesFormularioDTO seccion) {
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT t.table_name");
		strQuery.append(" FROM information_schema.tables t ");
		strQuery.append(" WHERE t.table_schema='");
		strQuery.append(Constantes.ESQUEMA_INTERPRETE).append("'");
		strQuery.append(" AND t.table_name='");
		strQuery.append(Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString())).append("'");
				
		List<String> lstTablas = em.createNativeQuery(strQuery.toString()).getResultList();
		
		return lstTablas != null && !lstTablas.isEmpty();
	}
	
	/**
	 * Metodo que verifica si existe el nombre de la tabla control_componentes en BD para la seccion actual.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeTablaControl() {
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT t.table_name");
		strQuery.append(" FROM information_schema.tables t ");
		strQuery.append(" WHERE t.table_schema='");
		strQuery.append(Constantes.ESQUEMA_INTERPRETE).append("'");
		strQuery.append(" AND t.table_name='");
		strQuery.append(Constantes.NOMBRE_BASE_TABLA_CONTROL).append("'");
				
		List<String> lstTablas = em.createNativeQuery(strQuery.toString()).getResultList();
		
		return lstTablas != null && !lstTablas.isEmpty();
	}
	
	/**
	 * Metodo que verifica si existe el nombre de la tabla de trámites.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeTablaTramites() {
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT t.table_name");
		strQuery.append(" FROM information_schema.tables t ");
		strQuery.append(" WHERE t.table_schema='");
		strQuery.append(Constantes.ESQUEMA_INTERPRETE).append("'");
		strQuery.append(" AND t.table_name='");
		strQuery.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append("'");
				
		List<String> lstTablas = em.createNativeQuery(strQuery.toString()).getResultList();
		
		return lstTablas != null && !lstTablas.isEmpty();
	}
	
	/**
	 * Método que valida si existe la tabla de bítacora para revertir estatus de trámites
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeTablaBitacoraCambiosEstatus() {
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT t.table_name");
		strQuery.append(" FROM information_schema.tables t ");
		strQuery.append(" WHERE t.table_schema='");
		strQuery.append(Constantes.ESQUEMA_INTERPRETE).append("'");
		strQuery.append(" AND t.table_name='");
		strQuery.append(Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS).append("'");
				
		List<String> lstTablas = em.createNativeQuery(strQuery.toString()).getResultList();
		
		return lstTablas != null && !lstTablas.isEmpty();
	}
	
	/**
	 * Metodo que verifica si existe el nombre de la tabla de trámites con firma electrónica.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeTablaTramitesFirma() {
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT t.table_name");
		strQuery.append(" FROM information_schema.tables t ");
		strQuery.append(" WHERE t.table_schema='");
		strQuery.append(Constantes.ESQUEMA_INTERPRETE).append("'");
		strQuery.append(" AND t.table_name='");
		strQuery.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA).append("'");
				
		List<String> lstTablas = em.createNativeQuery(strQuery.toString()).getResultList();
		
		return lstTablas != null && !lstTablas.isEmpty();
	}
	
	/**
	 * Método que se verifica si la tabla contiene el campo para el componente actual en BD.
	 * @param seccion
	 * @param componente
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeColumnaTabla(SeccionesFormularioDTO seccion, ComponenteDTO componente, boolean isComponenteComplejo) {
		String nombreColumnaBusqueda = Constantes.EMPTY_STRING;
		
		if(isComponenteComplejo) {
			nombreColumnaBusqueda = Constantes.NOMBRE_BASE_COLUMNAS.concat(componente.getIdComponente().toString()).concat(Constantes.NOMBRE_CAMPO_UNO_COMPONENTE_COMPLEJO); 
		} else {
			nombreColumnaBusqueda = Constantes.NOMBRE_BASE_COLUMNAS.concat(componente.getIdComponente().toString());
		}
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT c.column_name");
		strQuery.append(" FROM information_schema.columns c ");
		strQuery.append(" WHERE c.table_schema='");
		strQuery.append(Constantes.ESQUEMA_INTERPRETE).append("'");
		strQuery.append(" AND c.table_name='");
		strQuery.append(Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString())).append("'");
		strQuery.append(" AND c.column_name='");		
		strQuery.append(nombreColumnaBusqueda).append("'");
		
		List<String> lstColumnas = em.createNativeQuery(strQuery.toString()).getResultList();
		
		return lstColumnas != null && !lstColumnas.isEmpty();
	}
	
	/**
	 * Método que se verifica si el nombre de un campo existe en la tabla de la sección enviada.
	 * 
	 * @param seccion
	 * @param nombreColumna
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeNombreColumnaTabla(SeccionesFormularioDTO seccion, String nombreColumna) {
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT c.column_name");
		strQuery.append(" FROM information_schema.columns c ");
		strQuery.append(" WHERE c.table_schema='");
		strQuery.append(Constantes.ESQUEMA_INTERPRETE).append("'");
		strQuery.append(" AND c.table_name='");
		strQuery.append(Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString())).append("'");
		strQuery.append(" AND c.column_name='");		
		strQuery.append(nombreColumna).append("'");
		
		List<String> lstColumnas = em.createNativeQuery(strQuery.toString()).getResultList();
		
		return lstColumnas != null && !lstColumnas.isEmpty();
	}
		
	/**
	 * Método que genera en BD una nueva tabla para el registro de información de la sección actual.
	 * @param seccion
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaTabla(SeccionesFormularioDTO seccion) throws Exception {
		
		StringBuilder strTabla = new StringBuilder();
		strTabla.append(" CREATE TABLE ");
		strTabla.append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString())).append(" (");
		strTabla.append(" id bigserial NOT NULL,");
		strTabla.append(" id_seccion int8 NOT NULL,");
		strTabla.append(" id_tramite int8 NOT NULL,");
		strTabla.append(" fecha_creacion TIMESTAMP NOT NULL,");
		strTabla.append(" contiene_observaciones bool NOT NULL default false,");
		strTabla.append(" observaciones text NULL,");
		strTabla.append(" CONSTRAINT ");
		strTabla.append((Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString()).concat("_pk")));
		strTabla.append(" PRIMARY KEY (id));");
		
		strTabla.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString())));
		strTabla.append(" ADD CONSTRAINT ").append(Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString()).concat("_")).append(Constantes.NOMBRE_BASE_TABLA_TRAMITES.concat("_fk"));
		strTabla.append(" FOREIGN KEY (id_tramite) REFERENCES ").append(Constantes.ESQUEMA_INTERPRETE.concat(".")).append(Constantes.NOMBRE_BASE_TABLA_TRAMITES.concat("(id_tramite);"));
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {		
			entityManager.getTransaction().begin();			
			entityManager.createNativeQuery(strTabla.toString()).executeUpdate();			
			entityManager.getTransaction().commit();
				
		} catch (Throwable e) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo crear la tabla correspondiente a la Subsección:  "+Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString()), e);					
			throw new Exception("No se pudo crear la nueva tabla. " + Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString()) + e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
	
	/**
	 * Método que genera en BD una secuencia para las tablas creadas para el registro información de formularios.
	 * @param seccion
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaSecuenciaTabla(SeccionesFormularioDTO seccion) throws Exception {
		
		StringBuilder strSecuencia = new StringBuilder();
		strSecuencia.append(" CREATE SEQUENCE ");
		strSecuencia.append(Constantes.ESQUEMA_INTERPRETE.concat(".")); 
		strSecuencia.append(Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString()));
		strSecuencia.append(Constantes.NOMBRE_BASE_SECUENCIAS);
		strSecuencia.append(" INCREMENT BY 1 ");
		strSecuencia.append(" MINVALUE 1 ");
		strSecuencia.append(" MAXVALUE 99999999 ");
		strSecuencia.append(" START 1; ");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {		
			entityManager.getTransaction().begin();			
			entityManager.createNativeQuery(strSecuencia.toString()).executeUpdate();			
			entityManager.getTransaction().commit();
				
		} catch (Throwable e) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo crear la secuencia correspondiente a la Subsección:  "+Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString()), e);					
			throw new Exception("No se pudo crear la secuencia tabla. " + Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString()) + e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
	
	/**
	 * Metodo que genera en BD una nueva tabla control_componentes para el registro de informacion de la seccion a guardar.
	 * @param seccion
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaTablaControl(SeccionesFormularioDTO seccion) throws Exception {
		
		StringBuilder strTabla = new StringBuilder();
		strTabla.append(" CREATE TABLE ");
		strTabla.append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_CONTROL).append(" (");
		strTabla.append(" id bigserial NOT NULL,");
		strTabla.append(" nombre_tabla VARCHAR(50) NOT NULL,");
		strTabla.append(" nombre_columna VARCHAR(50) NOT NULL,");
		strTabla.append(" id_componente int8 NOT NULL,");
		strTabla.append(" id_tipo_componente int4 NOT NULL,");		
		strTabla.append(" nombre_componente VARCHAR(100) NOT NULL,");
		strTabla.append(" orden int4 NOT NULL,");
		strTabla.append(" fecha_creacion TIMESTAMP NOT NULL,");
		strTabla.append(" CONSTRAINT ");
		strTabla.append((Constantes.NOMBRE_BASE_TABLA_CONTROL.concat(seccion.getIdSeccionFormulario().toString()).concat("_pk")));
		strTabla.append(" PRIMARY KEY (id));");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {		
			entityManager.getTransaction().begin();			
			entityManager.createNativeQuery(strTabla.toString()).executeUpdate();			
			entityManager.getTransaction().commit();
				
		} catch (Throwable e) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo crear la tabla correspondiente:  "+Constantes.NOMBRE_BASE_TABLA_CONTROL, e);					
			throw new Exception("No se pudo crear la nueva tabla. " + Constantes.NOMBRE_BASE_TABLA_CONTROL + e);
		}  finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
	
	/**
	 * Metodo que genera en BD una nueva tabla para el registro de trámites.
	 * 
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaTablaTramites() throws Exception {
		
		StringBuilder strTabla = new StringBuilder();
		strTabla.append(" CREATE TABLE ");
		strTabla.append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(" (");
		strTabla.append(" id_tramite bigserial NOT NULL,");
		strTabla.append(" folio_seguimiento VARCHAR(25) NOT NULL,");		
		strTabla.append(" id_usuario_llave_cdmx int4 NULL,");
		strTabla.append(" id_estatus_tramite int4 NOT NULL,");
		strTabla.append(" id_usuario_operador int4 NULL,");
		strTabla.append(" id_usuario_revisor int4 NULL,");
		strTabla.append(" respuesta_folio_prevencion text NULL,");
		strTabla.append(" respuesta_folio_conclusion text NULL,");
		strTabla.append(" fecha_creacion TIMESTAMP NOT NULL,");			
		strTabla.append(" fecha_revision TIMESTAMP NULL,");	
		strTabla.append(" uuid varchar(36) NULL,");
		strTabla.append(" ruta_documento_prevencion varchar(500) NULL,");
		strTabla.append(" ruta_documento_revocado varchar(500) NULL,");
		strTabla.append(" motivo_rechazo text NULL,");
		
		strTabla.append(" CONSTRAINT ");
		strTabla.append((Constantes.NOMBRE_BASE_TABLA_TRAMITES.concat("_pk")));
		strTabla.append(" PRIMARY KEY (id_tramite)); ");
		
		strTabla.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES));
		strTabla.append(" ADD CONSTRAINT ").append(Constantes.NOMBRE_BASE_TABLA_TRAMITES.concat("_usuario_fk ")).append("FOREIGN KEY (id_usuario_llave_cdmx) REFERENCES ").append(Constantes.ESQUEMA_INTERPRETE).append(".usuario(id_usuario_llave_cdmx); ");
		strTabla.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES));
		strTabla.append(" ADD CONSTRAINT ").append(Constantes.NOMBRE_BASE_TABLA_TRAMITES.concat("_estatus_fk ")).append("FOREIGN KEY (id_estatus_tramite) REFERENCES ").append(Constantes.ESQUEMA_INTERPRETE).append(".cat_estatus_tramite(id_estatus_tramite); ");
		strTabla.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES));
		strTabla.append(" ADD CONSTRAINT ").append(Constantes.NOMBRE_BASE_TABLA_TRAMITES.concat("_revisor_fk ")).append("FOREIGN KEY (id_usuario_revisor) REFERENCES ").append(Constantes.ESQUEMA_INTERPRETE).append(".usuario(id_usuario_llave_cdmx); ");
		
		strTabla.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_BIT_ASIGNACION));
		strTabla.append(" ADD CONSTRAINT ").append(Constantes.NOMBRE_BASE_TABLA_BIT_ASIGNACION.concat("_id_tramite_fk ")).append("FOREIGN KEY (id_tramite) REFERENCES ").append(Constantes.ESQUEMA_INTERPRETE).append(".tramites(id_tramite); ");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {		
			entityManager.getTransaction().begin();			
			entityManager.createNativeQuery(strTabla.toString()).executeUpdate();			
			entityManager.getTransaction().commit();
				
		} catch (Throwable e) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo crear la tabla correspondiente:  "+Constantes.NOMBRE_BASE_TABLA_TRAMITES, e);					
			throw new Exception("No se pudo crear la nueva tabla. " + Constantes.NOMBRE_BASE_TABLA_TRAMITES + e);
		}  finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
	
	/**
	 * Método que genera en BD una secuencia para la tabla de trámites.
	 * 
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaSecuenciaTablaTramites() throws Exception {
		
		StringBuilder strSecuencia = new StringBuilder();
		strSecuencia.append(" CREATE SEQUENCE ");
		strSecuencia.append(Constantes.ESQUEMA_INTERPRETE.concat(".")); 
		strSecuencia.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES);
		strSecuencia.append(Constantes.NOMBRE_BASE_SECUENCIAS);
		strSecuencia.append(" INCREMENT BY 1 ");
		strSecuencia.append(" MINVALUE 1 ");
		strSecuencia.append(" MAXVALUE 99999999 ");
		strSecuencia.append(" START 1; ");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {		
			entityManager.getTransaction().begin();			
			entityManager.createNativeQuery(strSecuencia.toString()).executeUpdate();			
			entityManager.getTransaction().commit();
				
		} catch (Throwable e) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo crear la secuencia correspondiente a la tabla:  " + Constantes.NOMBRE_BASE_TABLA_TRAMITES, e);					
			throw new Exception("No se pudo crear la secuencia tabla. " + Constantes.NOMBRE_BASE_TABLA_TRAMITES + e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
	
	/**
	 * Metodo que genera en BD una nueva tabla para el registro de trámites con firma electronica.
	 * 
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaTablaTramitesFirma() throws Exception {
		
		StringBuilder strTabla = new StringBuilder();
		strTabla.append(" CREATE TABLE ");
		strTabla.append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA).append(" (");
		strTabla.append(" id_tramite_firma bigserial NOT NULL,");		
		strTabla.append(" cadena_original text NOT NULL,");		
		strTabla.append(" cadena_firmada text NULL,");
		strTabla.append(" nombre_firmante text NULL,");
		strTabla.append(" fecha_creacion timestamp(0) NOT NULL,");		
		strTabla.append(" respuesta_servicio text NOT NULL,");		
		strTabla.append(" id_tramite int8 NOT NULL,");
		strTabla.append(" CONSTRAINT ");
		strTabla.append((Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA.concat("_pk")));
		strTabla.append(" PRIMARY KEY (id_tramite_firma)); ");
		
		strTabla.append(" CREATE INDEX ").append("tramite_firma_cadena_firmada_idx");
		strTabla.append(" ON ").append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA);
		strTabla.append(" USING btree (cadena_firmada); ");

		strTabla.append(" CREATE INDEX ").append("tramite_firma_id_tramite_idx");
		strTabla.append(" ON ").append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA);
		strTabla.append(" USING btree (id_tramite); ");

		strTabla.append(" CREATE INDEX ").append("tramite_firma_id_tramite_firma_idx");
		strTabla.append(" ON ").append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA);
		strTabla.append(" USING btree (id_tramite_firma); ");
				
		strTabla.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA));
		strTabla.append(" ADD CONSTRAINT ").append("tramite_firma_tramites_fk ").append("FOREIGN KEY (id_tramite) REFERENCES ");
		strTabla.append(Constantes.ESQUEMA_INTERPRETE).append(".").append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append("(id_tramite); ");
				
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {		
			entityManager.getTransaction().begin();			
			entityManager.createNativeQuery(strTabla.toString()).executeUpdate();			
			entityManager.getTransaction().commit();
				
		} catch (Throwable e) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo crear la tabla correspondiente:  "+Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA, e);					
			throw new Exception("No se pudo crear la nueva tabla. " + Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA + e);
		}  finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
	
	/**
	 * Método que genera en BD una secuencia para la tabla de trámites con firma electrónica.
	 * 
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaSecuenciaTablaFirmaTramites() throws Exception {
		
		StringBuilder strSecuencia = new StringBuilder();
		strSecuencia.append(" CREATE SEQUENCE ");
		strSecuencia.append(Constantes.ESQUEMA_INTERPRETE.concat(".")); 
		strSecuencia.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA);
		strSecuencia.append(Constantes.NOMBRE_BASE_SECUENCIAS);
		strSecuencia.append(" INCREMENT BY 1 ");
		strSecuencia.append(" MINVALUE 1 ");
		strSecuencia.append(" MAXVALUE 99999999 ");
		strSecuencia.append(" START 1; ");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {		
			entityManager.getTransaction().begin();			
			entityManager.createNativeQuery(strSecuencia.toString()).executeUpdate();			
			entityManager.getTransaction().commit();
				
		} catch (Throwable e) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo crear la secuencia correspondiente a la tabla:  " + Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA, e);					
			throw new Exception("No se pudo crear la secuencia tabla. " + Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA + e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
	
	/**
	 * Método que genera en BD una nueva tabla de bítacora para los cambios de estatus de trámites.
	 * 
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaTablaBitacoraCambioEstatus() throws Exception {
		
		StringBuilder strTabla = new StringBuilder();
		strTabla.append(" CREATE TABLE ");
		strTabla.append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS).append(" (");
		strTabla.append(" id_reversion bigserial NOT NULL,");		
		strTabla.append(" id_tramite int8 NOT NULL,");		
		strTabla.append(" id_usuario_llave_cdmx_revierte int8 NOT NULL,");		
		strTabla.append(" id_estatus_revertido int4 NOT NULL,");
		strTabla.append(" respuesta_prevencion_conclusion text NULL,");				
		strTabla.append(" fecha_reversion timestamp NOT NULL,");		
		strTabla.append(" CONSTRAINT ");
		strTabla.append((Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS.concat("_pk")));
		strTabla.append(" PRIMARY KEY (id_reversion)); ");
				
		strTabla.append(" CREATE INDEX ").append("bit_revertir_estatus_tramites_idx");
		strTabla.append(" ON ").append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS);
		strTabla.append(" USING btree (id_tramite); ");
		
		strTabla.append(" CREATE INDEX ").append("bit_revertir_estatus_usuario_idx");
		strTabla.append(" ON ").append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS);
		strTabla.append(" USING btree (id_usuario_llave_cdmx_revierte); ");
		
		strTabla.append(" CREATE INDEX ").append("bit_revertir_estatus_anterior_estatus_idx");
		strTabla.append(" ON ").append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS);
		strTabla.append(" USING btree (id_estatus_revertido); ");
		
		strTabla.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS));
		strTabla.append(" ADD CONSTRAINT ").append("bit_revertir_estatus_tramite_fk ").append("FOREIGN KEY (id_tramite) REFERENCES ");
		strTabla.append(Constantes.ESQUEMA_INTERPRETE).append(".").append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append("(id_tramite); ");
		
		strTabla.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS));
		strTabla.append(" ADD CONSTRAINT ").append("bit_revertir_estatus_usuario_fk ").append("FOREIGN KEY (id_usuario_llave_cdmx_revierte) REFERENCES ");
		strTabla.append(Constantes.ESQUEMA_INTERPRETE).append(".").append(Constantes.NOMBRE_BASE_TABLA_USUARIO).append("(id_usuario_llave_cdmx); ");
		
		strTabla.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS));
		strTabla.append(" ADD CONSTRAINT ").append("bit_revertir_estatus_anterior_estatus_fk ").append("FOREIGN KEY (id_estatus_revertido) REFERENCES ");
		strTabla.append(Constantes.ESQUEMA_INTERPRETE).append(".").append(Constantes.NOMBRE_BASE_TABLA_ESTATUS_TRAMITE).append("(id_estatus_tramite); ");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {		
			entityManager.getTransaction().begin();			
			entityManager.createNativeQuery(strTabla.toString()).executeUpdate();			
			entityManager.getTransaction().commit();
				
		} catch (Throwable e) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo crear la tabla correspondiente:  "+Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS, e);					
			throw new Exception("No se pudo crear la nueva tabla. " + Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS + e);
		}  finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
	
	/**
	 * Método que genera en BD una secuencia para la tabla de Bitácora de cambios de estatus de trámites.
	 * 
	 * @throws Exception
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaSecuenciaTablaBitacoraCambioEstatus() throws Exception {
		
		StringBuilder strSecuencia = new StringBuilder();
		strSecuencia.append(" CREATE SEQUENCE ");
		strSecuencia.append(Constantes.ESQUEMA_INTERPRETE.concat(".")); 
		strSecuencia.append(Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS);
		strSecuencia.append(Constantes.NOMBRE_BASE_SECUENCIAS);
		strSecuencia.append(" INCREMENT BY 1 ");
		strSecuencia.append(" MINVALUE 1 ");
		strSecuencia.append(" MAXVALUE 99999999 ");
		strSecuencia.append(" START 1; ");
		
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		try {		
			entityManager.getTransaction().begin();			
			entityManager.createNativeQuery(strSecuencia.toString()).executeUpdate();			
			entityManager.getTransaction().commit();
				
		} catch (Throwable e) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo crear la secuencia correspondiente a la tabla:  " + Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS, e);					
			throw new Exception("No se pudo crear la secuencia tabla. " + Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS + e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
	
	/**Métodos utilizados para la generación de Campos en BD para componentes SIMPLES**/
	
	/**
	 * Método que contiene la lógica para la creación de campos en BD para el componente Campo de Texto.
	 * @param seccion
	 * @param componenteCampoTexto
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaCampoTexto(SeccionesFormularioDTO seccion, ComponenteCampoTextoDTO componenteCampoTexto) {
		String tipoDatoComponente = null;
		if(componenteCampoTexto.isAlfanumerico()) {
			tipoDatoComponente = Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, componenteCampoTexto.getValorMaximo().toString());
		} else if(componenteCampoTexto.isPermiteDecimales()) {
			tipoDatoComponente = Constantes.TIPO_DATO_NUMERIC;
		} else {
			tipoDatoComponente = Constantes.TIPO_DATO_INT8;
		}
		crearColumnaComponente(generarSentenciaNuevaColumna(seccion, componenteCampoTexto, tipoDatoComponente));		
	}
	
	/**
	 * Método que contiene la lógica para la actualización de la longitud de campos en BD para el componente Campo de Texto.
	 * @param seccion
	 * @param componenteCampoTexto
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizarColumnaCampoTextoLongitud(SeccionesFormularioDTO seccion, ComponenteCampoTextoDTO componenteCampoTexto) {
		String tipoDatoComponente = null;
		if(componenteCampoTexto.isAlfanumerico()) {
			tipoDatoComponente = Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, componenteCampoTexto.getValorMaximo().toString());
			crearColumnaComponente(generarSentenciaActualizacionLongitudColumnaVarchar(seccion, componenteCampoTexto, tipoDatoComponente));	
		}
	}
	
	/**
	 * Método que contiene la lógica para la creación de campos en BD para el componente Tabla.
	 * @param seccion
	 * @param componenteTabla
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaTabla(SeccionesFormularioDTO seccion, ComponenteTablaDTO componenteTabla) {
		String tipoDatoComponente = null;
			tipoDatoComponente = Constantes.TIPO_DATO_INT8;
		crearColumnaComponente(generarSentenciaNuevaColumna(seccion, componenteTabla, tipoDatoComponente));		
	}
	
	/**
	 * Método que contiene la lógica para la creación de campos en BD para el componente Fecha.
	 * @param seccion
	 * @param componenteFecha
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaFecha(SeccionesFormularioDTO seccion, ComponenteFechaDTO componenteFecha) {
		String tipoDatoComponente = Constantes.TIPO_DATO_FECHA_HORA;
		
		crearColumnaComponente(generarSentenciaNuevaColumna(seccion, componenteFecha, tipoDatoComponente));		
	}
	
	/**
	 * Método que contiene la lógica para la creación de de campos para el componente Checkbox único.
	 * @param seccion
	 * @param componenteCheckUnico
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaCheckUnico(SeccionesFormularioDTO seccion, ComponenteCheckboxUnicoDTO componenteCheckUnico) {
		String tipoDatoComponente = Constantes.TIPO_DATO_BOOLEANO;
		
		crearColumnaComponente(generarSentenciaNuevaColumna(seccion, componenteCheckUnico, tipoDatoComponente));		
	}	
	
	/**
	 * Método que contiene la lógica para la creación de de campos para el componente Checkbox grupo.
	 * @param seccion
	 * @param componenteCheckbox
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaCheckBoxGrupo(SeccionesFormularioDTO seccion, ComponenteCheckboxDTO componenteCheckbox) {
		String tipoDatoComponente = Constantes.TIPO_DATO_JSON;
		
		crearColumnaComponente(generarSentenciaNuevaColumna(seccion, componenteCheckbox, tipoDatoComponente));
	}	
	
	/**
	 * Método que contiene la lógica para la creación de de campos para el componente Menu desplegable.
	 * @param seccion
	 * @param componenteMenuDesplegable
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaMenuDesplegable(SeccionesFormularioDTO seccion, ComponenteMenuDesplegableDTO componenteMenuDesplegable) {
		String tipoDatoComponente = Constantes.TIPO_DATO_INT4;
		
		crearColumnaComponente(generarSentenciaNuevaColumna(seccion, componenteMenuDesplegable, tipoDatoComponente));		
	}	
	
	/**
	 * Método que contiene la lógica para la creación de de campos para el componente Carga Documentos.
	 * @param seccion
	 * @param componenteCargaDocumentos
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaCargaDocumentos(SeccionesFormularioDTO seccion, ComponenteCargaDocumentosDTO componenteCargaDocumentos) {
		String tipoDatoComponente = Constantes.TIPO_DATO_JSON;
		
		crearColumnaComponente(generarSentenciaNuevaColumna(seccion, componenteCargaDocumentos, tipoDatoComponente));		
	}
	
	/**
	 * Método que contiene la lógica para la creación de campos en BD para el componente Area de Texto.
	 * @param seccion
	 * @param componenteAreaTexto
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaAreaTexto(SeccionesFormularioDTO seccion, ComponenteAreaTextoDTO componenteAreaTexto) {
		String tipoDatoComponente = null;
		tipoDatoComponente = Constantes.TIPO_DATO_TEXT;
		crearColumnaComponente(generarSentenciaNuevaColumna(seccion, componenteAreaTexto, tipoDatoComponente));		
	}

	
	/**
	 * Metodo auxiliar que realiza la ejecucion de la sentencia para insertar el registro de un componente sobre la tabla control.
	 * @param seccion
	 * @param idTipoComponente
	 * @param tituloComponente
	 */
	public void guardarRegistroTablaControl(SeccionesFormularioDTO seccion, ComponenteDTO componente, String tituloComponente, int numeroRegistro) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		final StringBuilder strQuery = new StringBuilder();
	    strQuery.append("INSERT INTO ").append(Constantes.ESQUEMA_INTERPRETE).append(".")
	    	.append(Constantes.NOMBRE_BASE_TABLA_CONTROL)
	            .append("(nombre_tabla, nombre_columna, id_componente, id_tipo_componente, nombre_componente, orden, fecha_creacion) ")
	            .append("VALUES (:nombreTabla, :nombreColumna, :idComponente, :idTipoComponente, :nombreComponente, :orden, :fechaCreacion)");

	    try {
	    	entityManager.getTransaction().begin();
	    	Query query = entityManager.createNativeQuery(strQuery.toString());
	    	
	    	query.setParameter("nombreTabla", Constantes.NOMBRE_BASE_TABLAS + seccion.getIdSeccionFormulario());

	    	String nombreColumna;
	        int idTipo = componente.getCatTipoComponenteDTO().getIdTipoComponente();
	        if (idTipo == Constantes.ID_COMPONENTE_RADIO_BOTON || idTipo == Constantes.ID_COMPONENTE_DATOS_DOMICILIO
	                || idTipo == Constantes.ID_COMPONENTE_DATOS_PERSONALES_CON_LLAVE || idTipo == Constantes.ID_COMPONENTE_DATOS_PERSONALES_SIN_LLAVE
	                || idTipo == Constantes.ID_COMPONENTE_DATOS_PERSONA_MORAL) {
	            nombreColumna = Constantes.NOMBRE_BASE_COLUMNAS + componente.getIdComponente() + "_" + ++numeroRegistro;
	        } else {
	            nombreColumna = Constantes.NOMBRE_BASE_COLUMNAS + componente.getIdComponente();
	        }
	        query.setParameter("nombreColumna", nombreColumna);
	        
	        query.setParameter("idComponente", componente.getIdComponente());
	        query.setParameter("idTipoComponente", componente.getCatTipoComponenteDTO().getIdTipoComponente());
	        query.setParameter("nombreComponente", tituloComponente);
	        query.setParameter("orden", numeroRegistro);
	        query.setParameter("fechaCreacion", new Date());
	        
	        query.executeUpdate();
	        entityManager.getTransaction().commit();
	    } catch (Throwable ex) {
	    	if (entityManager.getTransaction() != null && entityManager.getTransaction().isActive()) {
	    		entityManager.getTransaction().rollback();
	    	}
	        LOGGER.error("No se pudo ejecutar la siguiente sentencia: " + strQuery, ex);
	        throw new IllegalStateException("No se pudo insertar el registro en la tabla control: " + strQuery, ex);
	    } finally {
	        entityManager.close();
	        entityManagerFactory.close();
	    }
		
	}
	
	/**
	 * Metodo auxiliar que realiza la consulta del registro de un componente sobre la tabla control para validar que existe.
	 * @param seccion
	 * @param idTipoComponente
	 * @param tituloComponente
	 */
	@SuppressWarnings({ "unchecked" })
	public boolean existeRegistroTablaControl(SeccionesFormularioDTO seccion, ComponenteDTO componente, String tituloComponente, int numeroRegistro) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT c.id, c.nombre_tabla, c.nombre_columna, c.id_componente, c.nombre_componente, c.orden, c.fecha_creacion");
		strQuery.append(" FROM " + Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_CONTROL + " c");
		strQuery.append(" WHERE c.nombre_tabla = '" + Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString()) + "'");
		if(componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_RADIO_BOTON 
				|| componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_DOMICILIO 
				|| componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONALES_CON_LLAVE 
				|| componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONALES_SIN_LLAVE
				|| componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONA_MORAL) {
			strQuery.append(" AND c.nombre_columna = '" + Constantes.NOMBRE_BASE_COLUMNAS.concat(componente.getIdComponente().toString()) + "_" + (++numeroRegistro) +"'");
		} else {
			strQuery.append(" AND c.nombre_columna = '" + Constantes.NOMBRE_BASE_COLUMNAS.concat(componente.getIdComponente().toString()) + "'");
		}
		strQuery.append(" AND c.id_componente = " + componente.getIdComponente().toString());
		List<Object> lst = null;
		try {		
			entityManager.getTransaction().begin();			
			lst = em.createNativeQuery(strQuery.toString()).getResultList();	
			entityManager.getTransaction().commit();
		} catch (Throwable ex) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo ejecutar la siguiente sentencia: " + strQuery, ex);			
			throw new IllegalStateException("No se pudo actualizar el registro en la tabla control: " + strQuery  + ex);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
		return lst != null && !lst.isEmpty();
	}
	
	/**
	 * Metodo auxiliar que realiza la actualizacion del registro de un componente sobre la tabla control.
	 * @param seccion
	 * @param idTipoComponente
	 * @param tituloComponente
	 */
	public void actualizarRegistroTablaControl(SeccionesFormularioDTO seccion, ComponenteDTO componente, String tituloComponente, int numeroRegistro) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("UPDATE ").append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_CONTROL).append(" SET ")
			.append("nombre_componente = :nombreComponente, ")
			.append("fecha_creacion = :fechaCreacion ")
			.append("WHERE nombre_tabla = :nombreTabla ")
			.append("AND nombre_columna = :nombreColumna ")
			.append("AND id_componente = :idComponente");

		try {		
			Set<Integer> tiposComponente = new HashSet<>(Arrays.asList(Constantes.ID_COMPONENTE_RADIO_BOTON,
				    Constantes.ID_COMPONENTE_DATOS_DOMICILIO, Constantes.ID_COMPONENTE_DATOS_PERSONALES_CON_LLAVE,
				    Constantes.ID_COMPONENTE_DATOS_PERSONALES_SIN_LLAVE, Constantes.ID_COMPONENTE_DATOS_PERSONA_MORAL
				));

			String nombreColumna;
			if (tiposComponente.contains(componente.getCatTipoComponenteDTO().getIdTipoComponente())) {
				nombreColumna = Constantes.NOMBRE_BASE_COLUMNAS.concat(componente.getIdComponente().toString()) + "_" + ++numeroRegistro;
			} else {
				nombreColumna = Constantes.NOMBRE_BASE_COLUMNAS.concat(componente.getIdComponente().toString());
			}
			
			entityManager.getTransaction().begin();
			entityManager.createNativeQuery(strQuery.toString())
				.setParameter("nombreComponente", tituloComponente)
				.setParameter("fechaCreacion", new Date())
				.setParameter("nombreTabla", Constantes.NOMBRE_BASE_TABLAS + seccion.getIdSeccionFormulario())
				.setParameter("idComponente", componente.getIdComponente())
				.setParameter("nombreColumna", nombreColumna)
				.executeUpdate();
			entityManager.getTransaction().commit();	

		} catch (Throwable ex) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo ejecutar la siguiente sentencia: " + strQuery, ex);			
			throw new IllegalStateException("No se pudo actualizar el registro en la tabla control: " + strQuery  + ex);
		}  finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
	
	/**
	 * Método auxiliar que se encarga de generar la sentencia SQL para la creación de nueva columna para el componente actual
	 * @param seccion
	 * @param componente
	 * @param tipoDatoComponente
	 * @return
	 */
	private String generarSentenciaNuevaColumna(SeccionesFormularioDTO seccion, ComponenteDTO componente, String tipoDatoComponente) {
		final StringBuilder strQueryColumna = new StringBuilder();
		strQueryColumna.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString())));
		strQueryColumna.append(" ADD ").append(Constantes.NOMBRE_BASE_COLUMNAS.concat(componente.getIdComponente().toString())).append(" ").append(tipoDatoComponente).append(" NULL;");
		strQueryColumna.append(" COMMENT ON COLUMN ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString()).concat("."+Constantes.NOMBRE_BASE_COLUMNAS.concat(componente.getIdComponente().toString()))));
		strQueryColumna.append(" IS '").append(componente.getTituloCampo()).append("';");
		
		return strQueryColumna.toString();
	}
	
	/**
	 * Método auxiliar que se encarga de generar la sentencia SQL para la actualizacion de la columna para el componente actual
	 * @param seccion
	 * @param componente
	 * @param tipoDatoComponente
	 * @return
	 */
	private String generarSentenciaActualizacionLongitudColumnaVarchar(SeccionesFormularioDTO seccion, ComponenteDTO componente, String tipoDatoComponente) {
		final StringBuilder strQueryColumna = new StringBuilder();
		strQueryColumna.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString())));
		strQueryColumna.append(" ALTER COLUMN ").append(Constantes.NOMBRE_BASE_COLUMNAS.concat(componente.getIdComponente().toString())).append(" TYPE ").append(tipoDatoComponente).append(" ;");
		
		return strQueryColumna.toString();
	}
	
	/**
	 * Método auxiliar que se encarga de generar la sentencia SQL para la creación de nueva columna para los componentes que requiren más de 1 campo en BD
	 * @param seccion
	 * @param nombreCampo
	 * @param tipoDatoComponente
	 * @param comentarios
	 * @return
	 */
	private String generarSentenciaNuevaColumnaMultiple(SeccionesFormularioDTO seccion, String nombreCampo, String tipoDatoComponente, String comentarios) {
		final StringBuilder strQueryColumna = new StringBuilder();
		strQueryColumna.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString())));
		strQueryColumna.append(" ADD ").append(Constantes.NOMBRE_BASE_COLUMNAS.concat(nombreCampo)).append(" ").append(tipoDatoComponente).append(" NULL;");
		strQueryColumna.append(" COMMENT ON COLUMN ").append((Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLAS.concat(seccion.getIdSeccionFormulario().toString()).concat("."+Constantes.NOMBRE_BASE_COLUMNAS.concat(nombreCampo))));
		strQueryColumna.append(" IS '").append(comentarios).append("';");
		
		return strQueryColumna.toString();
	}
	
	/**
	 * Método auxiliar que realiza la ejecución de la sentencia para crear nueva columna.
	 * @param queryNuevaColumna
	 */
	private void crearColumnaComponente(String queryNuevaColumna) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		try {		
			entityManager.getTransaction().begin();			
			entityManager.createNativeQuery(queryNuevaColumna).executeUpdate();			
			entityManager.getTransaction().commit();				
		} catch (Throwable e) {
			if ( entityManager.getTransaction() != null && entityManager.getTransaction().isActive() ) {
				entityManager.getTransaction().rollback();
			}
			LOGGER.error("No se pudo ejecutar la siguiente sentencia: " + queryNuevaColumna, e);			
			throw new IllegalStateException("No se pudo crear la nueva columna: " + queryNuevaColumna  + e);
		} finally {
			entityManager.close();
			entityManagerFactory.close();
		}
	}
	
	/**Métodos utilizados para la generación de Campos en BD para componentes ESPECIALES**/
	
	/**
	 * Método que contiene la lógica para la creación de de campos para el componente RadioBotón.
	 * @param seccion
	 * @param componenteRadioBoton
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaRadioBoton(SeccionesFormularioDTO seccion, ComponenteRadiobotonDTO componenteRadioBoton) {
		
		final StringBuilder strQueryColumnas = new StringBuilder();
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteRadioBoton.getIdComponente().toString().concat("_1"), Constantes.TIPO_DATO_INT4, Constantes.CAMPO_OPCION_COMPONENTE_RADIOBOTON));				
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteRadioBoton.getIdComponente().toString().concat("_2"), Constantes.TIPO_DATO_BOOLEANO, Constantes.CAMPO_OTRO_COMPONENTE_RADIOBOTON));								
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteRadioBoton.getIdComponente().toString().concat("_3"), Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "100"), Constantes.CAMPO_ESPECIFIQUE_COMPONENTE_RADIOBOTON));				
		
		crearColumnaComponente(strQueryColumnas.toString());

	}	
	
	
	/**Métodos utilizados para la generación de Campos en BD para componentes COMPLEJOS**/
	
	/**
	 * Método que contiene la lógica para la creación de de campos para el componente Datos de domicilio.
	 * @param seccion
	 * @param componenteDatosDomicilio
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaDatosDomicilio(SeccionesFormularioDTO seccion, ComponenteDatosDomicilioDTO componenteDatosDomicilio) {
		
		final StringBuilder strQueryColumnas = new StringBuilder();
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosDomicilio.getIdComponente().toString().concat("_1"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "100"), Constantes.CAMPO_CALLE_COMPONENTE_DOMICILIO));
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosDomicilio.getIdComponente().toString().concat("_2"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "60"), Constantes.CAMPO_NUMEXT_COMPONENTE_DOMICILIO));
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosDomicilio.getIdComponente().toString().concat("_3"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "60"), Constantes.CAMPO_NUMINT_COMPONENTE_DOMICILIO));
						
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosDomicilio.getIdComponente().toString().concat("_4"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "5"), Constantes.CAMPO_CP_COMPONENTE_DOMICILIO));

		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosDomicilio.getIdComponente().toString().concat("_5"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "60"), Constantes.CAMPO_COLONIA_COMPONENTE_DOMICILIO));
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosDomicilio.getIdComponente().toString().concat("_6"), 
				Constantes.TIPO_DATO_INT4, Constantes.CAMPO_ALCALDIA_COMPONENTE_DOMICILIO));
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosDomicilio.getIdComponente().toString().concat("_7"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "60"), Constantes.CAMPO_ESTADO_COMPONENTE_DOMICILIO));
		
		crearColumnaComponente(strQueryColumnas.toString());
	}	
		
	/**Métodos utilizados para la generación de Campos en BD para componentes COMPLEJOS**/
	
	/**
	 * Método que contiene la lógica para la creación de nuevo campo para el componente Datos de domicilio.
	 * @param seccion
	 * @param componenteDatosDomicilio
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaColumnaEstadoComponenteDomicilio(SeccionesFormularioDTO seccion, ComponenteDatosDomicilioDTO componenteDatosDomicilio) {
		
		final StringBuilder strQueryColumnas = new StringBuilder();
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosDomicilio.getIdComponente().toString().concat(Constantes.ORDER_CAMPO_ESTADO_COMPONENTE_DOMICILIO), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "60"), Constantes.CAMPO_ESTADO_COMPONENTE_DOMICILIO));
		
		crearColumnaComponente(strQueryColumnas.toString());
	}	
	
	/**
	 * Método que contiene la lógica para la creación de nuevo campo para el componente Datos personales con llave.
	 * @param seccion
	 * @param datosPersonalesLlave
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaColumnaSexoComponenteDatosLlave(SeccionesFormularioDTO seccion, ComponenteDatosPersonalesLlaveDTO datosPersonalesLlave) {
		
		final StringBuilder strQueryColumnas = new StringBuilder();
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, datosPersonalesLlave.getIdComponente().toString().concat(Constantes.ORDER_CAMPO_SEXO_COMPONENTE_DATOS_PERSONALES), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "15"), Constantes.CAMPO_SEXO_COMPONENTE_DATOS_PERSONALES));
		
		crearColumnaComponente(strQueryColumnas.toString());
	}	
	
	/**
	 * Método que contiene la lógica para la creación de de campos para el componente Datos personales con llave.
	 * @param seccion
	 * @param componenteDatosPersonalesLlave
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaDatosPersonalesLLave(SeccionesFormularioDTO seccion, ComponenteDatosPersonalesLlaveDTO componenteDatosPersonalesLlave) {
		
		final StringBuilder strQueryColumnas = new StringBuilder();
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonalesLlave.getIdComponente().toString().concat("_1"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "18"), Constantes.CAMPO_CURP_COMPONENTE_DATOS_PERSONALES));

		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonalesLlave.getIdComponente().toString().concat("_2"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "50"), Constantes.CAMPO_NOMBRES_COMPONENTE_DATOS_PERSONALES));
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonalesLlave.getIdComponente().toString().concat("_3"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "50"), Constantes.CAMPO_PRIMERAPELLIDO_COMPONENTE_DATOS_PERSONALES));

		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonalesLlave.getIdComponente().toString().concat("_4"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "50"), Constantes.CAMPO_SEGUNDOAPELLIDO_COMPONENTE_DATOS_PERSONALES));

		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonalesLlave.getIdComponente().toString().concat("_5"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "10"), Constantes.CAMPO_TELEFONO_COMPONENTE_DATOS_PERSONALES));		

		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonalesLlave.getIdComponente().toString().concat("_6"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "100"), Constantes.CAMPO_CORREO_COMPONENTE_DATOS_PERSONALES));

		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonalesLlave.getIdComponente().toString().concat("_7"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "10"), Constantes.CAMPO_FECHANAC_COMPONENTE_DATOS_PERSONALES));
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonalesLlave.getIdComponente().toString().concat("_8"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "15"), Constantes.CAMPO_SEXO_COMPONENTE_DATOS_PERSONALES));
		
		crearColumnaComponente(strQueryColumnas.toString());
	}	
	
	
	/**
	 * Método que contiene la lógica para la creación de de campos para el componente Datos personales sin llave.
	 * @param seccion
	 * @param componenteDatosPersonales
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaDatosPersonales(SeccionesFormularioDTO seccion, ComponenteDatosPersonalesDTO componenteDatosPersonales) {
		
		final StringBuilder strQueryColumnas = new StringBuilder();
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonales.getIdComponente().toString().concat("_1"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "18"), Constantes.CAMPO_CURP_COMPONENTE_DATOS_PERSONALES));
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonales.getIdComponente().toString().concat("_2"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "50"), Constantes.CAMPO_NOMBRES_COMPONENTE_DATOS_PERSONALES));
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonales.getIdComponente().toString().concat("_3"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "50"), Constantes.CAMPO_PRIMERAPELLIDO_COMPONENTE_DATOS_PERSONALES));
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonales.getIdComponente().toString().concat("_4"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "50"), Constantes.CAMPO_SEGUNDOAPELLIDO_COMPONENTE_DATOS_PERSONALES));		
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonales.getIdComponente().toString().concat("_5"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "10"), Constantes.CAMPO_TELEFONO_COMPONENTE_DATOS_PERSONALES));		
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonales.getIdComponente().toString().concat("_6"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "100"), Constantes.CAMPO_CORREO_COMPONENTE_DATOS_PERSONALES));
				
		crearColumnaComponente(strQueryColumnas.toString());
	}	

	/**
	 * Método que contiene la lógica para la creación de campos para el componente Datos Persona Moral.
	 * @param seccion
	 * @param componenteDatosPersonaMoral
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void creaNuevaColumnaDatosPersonaMoral(SeccionesFormularioDTO seccion, ComponenteDatosPersonaMoralDTO componenteDatosPersonaMoral) {
		
		final StringBuilder strQueryColumnas = new StringBuilder();
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonaMoral.getIdComponente().toString().concat("_1"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "13"), Constantes.CAMPO_RFC_COMPONENTE_DATOS_PERSONA_MORAL));
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonaMoral.getIdComponente().toString().concat("_2"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "300"), Constantes.CAMPO_RAZON_SOCIAL_COMPONENTE_DATOS_PERSONA_MORAL));
		
		strQueryColumnas.append(generarSentenciaNuevaColumnaMultiple(seccion, componenteDatosPersonaMoral.getIdComponente().toString().concat("_3"), 
				Constantes.TIPO_DATO_VARCHAR.replace(Constantes.COMODIN, "10"), Constantes.CAMPO_VIGENCIA_CERTIFICADO_COMPONENTE_DATOS_PERSONA_MORAL));
				
		crearColumnaComponente(strQueryColumnas.toString());
	}	
	
	/**
	 * Metodo que verifica si existe una columna en la tabla indicada.
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public boolean existeColumna(String tableName, String columnName) {
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" SELECT t.column_name");
		strQuery.append(" FROM information_schema.columns t ");
		strQuery.append(" WHERE t.table_schema='");
		strQuery.append(Constantes.ESQUEMA_INTERPRETE).append("'");
		strQuery.append(" AND t.table_name='");
		strQuery.append(tableName).append("'");
		strQuery.append(" AND t.column_name='");
		strQuery.append(columnName).append("'");
				
		List<String> lstTablas = em.createNativeQuery(strQuery.toString()).getResultList();
		
		return lstTablas != null && !lstTablas.isEmpty();
	}
	
	/**
	 * Método auxiliar que se encarga de ejecutar la query para crear una nueva columna en la tabla indicada
	 * @param tableName
	 * @param columnName
	 * @param tipoDatoComponente
	 */
	public void crearNuevaColumnaTabla(String tableName, String columnName, String tipoDatoComponente) {
		final StringBuilder strQueryColumna = new StringBuilder();
		strQueryColumna.append(" ALTER TABLE ").append((Constantes.ESQUEMA_INTERPRETE + "." + tableName));
		strQueryColumna.append(" ADD ").append(columnName).append(" ").append(tipoDatoComponente).append(" NULL;");
		
		crearColumnaComponente(strQueryColumna.toString());
	}	
	
	/**
	 * Método que consulta la versión de Base de Datos
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String consultaVersionBD() {
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(" select version(); ");
				
		List<String> lstVersion = em.createNativeQuery(strQuery.toString()).getResultList();
		
		return lstVersion != null && !lstVersion.isEmpty() ? lstVersion.get(0) : null;
	}
	
}

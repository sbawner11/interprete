package mx.gob.atdt.interprete.formulario.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.SQLQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.common.util.StringUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.CatEstatusLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.CatEstatusTramiteDTO;
import mx.gob.atdt.interprete.dto.ConsultaTramiteDTO;
import mx.gob.atdt.interprete.dto.ControlComponentesDTO;
import mx.gob.atdt.interprete.dto.LineaCapturaDTO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.TramiteFirmaElectronicaDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;

@Stateless
@LocalBean
public class FormularioDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(FormularioDAO.class);

	@Inject
	@PersistenceContext
	protected EntityManager em;
	
	private static final String SQL_UPDATE = "UPDATE ";
	private static final String SQL_INSERT = "INSERT INTO ";
	private static final String SQL_FROM = " FROM ";
	private static final String SQL_SET = " SET ";
	private static final String SQL_VALUES = " VALUES ";
	private static final String SQL_WHERE = " WHERE ";
	private static final String SQL_ID_TRAMITE = "id_tramite = :idTramite";
	private static final String SQL_ID_ESTATUS_TRAMITE = "id_estatus_tramite = :idEstatusTramite";
	private static final String SQL_RUTA_RESOLUCION_POSITIVA = "ruta_resolucion_positiva = :rutaResolucionPositiva";
	private static final String SQL_RUTA_RESOLUCION_NEGATIVA = "ruta_resolucion_negativa = :rutaResolucionNegativa";
	private static final String SQL_RUTA_DOCTO_PREV_NULL = "ruta_documento_prevencion = null"; 
	private static final String SQL_RUTA_DOCTO_PREV = "ruta_documento_prevencion = :rutaDocumentoPrevencion";
	private static final String SQL_ID_USUARIO_REVISOR = "id_usuario_revisor = :idUsuarioRevisor";
	private static final String SQL_ID_USUARIO_OPERADOR = "id_usuario_operador = :idUsuarioOperador";
	private static final String SQL_FECHA_REVISION = "fecha_revision = :fechaRevision";
	private static final String SQL_FECHA_CREACION = "fecha_creacion = :fechaCreacion";
	private static final String SQL_CONTIENE_OBSER = "contiene_observaciones = :contieneObservaciones";
	private static final String SQL_RESPUESTA_FOLIO_CONCLUSION = "respuesta_folio_conclusion = :respuestaFolioConclusion";
	private static final String SQL_RESPUESTA_FOLIO_PREVENCION = "respuesta_folio_prevencion = :respuestaFolioPrevencion";
	private static final String SQL_ID_ESTATUS_TRAMITE_ID = "id_estatus_tramite";
	
	private static final String PARAM_ID_TRAMITE = "idTramite";
	private static final String PARAM_ID_ESTATUS_TRAMITE = "idEstatusTramite";
	private static final String PARAM_ID_USUARIO_REVISOR = "idUsuarioRevisor";
	private static final String PARAM_FECHA_REVISION = "fechaRevision";
	private static final String PARAM_FECHA_CREACION = "fechaCreacion";
	private static final String PARAM_ID_SECCION = "idSeccion";
	
	/**
	 * Método que realiza el registro de un nuevo trámite al inciar la captura de un Formulario.
	 * 	
	 * @param tramiteDTO
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registrarTramiteUsuario(TramiteDTO tramiteDTO) {
		final StringBuilder strQueryInsert = new StringBuilder();
		
		strQueryInsert.append(SQL_INSERT).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES)
			.append(" (id_tramite, folio_seguimiento, id_usuario_llave_cdmx, id_estatus_tramite, id_usuario_revisor,  ")
			.append("respuesta_folio_prevencion, respuesta_folio_conclusion, fecha_creacion, fecha_revision, uuid, id_persona_moral) ")
			.append(SQL_VALUES)
			.append("(:idTramite, :folioSeguimiento, :idUsuarioLlaveCdmx, :idEstatusTramite, :idUsuarioRevisor, ")
			.append(":respuestaFolioPrevencion, :respuestaFolioConclusion, :fechaCreacion, :fechaRevision, :uuid, :idPersonaMoral)");
		
		SQLQuery query = em.createNativeQuery(strQueryInsert.toString())
                .unwrap(SQLQuery.class);

	    query.setParameter(PARAM_ID_TRAMITE, tramiteDTO.getIdTramite());
	    query.setParameter("folioSeguimiento", tramiteDTO.getFolioSeguimiento().concat(tramiteDTO.getIdTramite().toString()));
	    query.setParameter("idUsuarioLlaveCdmx", BeanUtils.isNotNull(tramiteDTO.getUsuario()) 
	    		? (int) tramiteDTO.getUsuario().getIdUsuarioLlaveCdmx() : null, org.hibernate.type.IntegerType.INSTANCE);
	    query.setParameter(PARAM_ID_ESTATUS_TRAMITE, tramiteDTO.getCatEstatusTramiteDTO().getIdEstatusTramite());	    
	    query.setParameter(PARAM_ID_USUARIO_REVISOR, BeanUtils.isNotNull(tramiteDTO.getUsuarioRevisor()) 
	    		? (int) tramiteDTO.getUsuarioRevisor().getIdUsuarioLlaveCdmx() : null, org.hibernate.type.IntegerType.INSTANCE);
	    query.setParameter("respuestaFolioPrevencion", BeanUtils.isNotNull(tramiteDTO.getRespuestaFolioPrevencion()) 
	    		? tramiteDTO.getRespuestaFolioPrevencion() : null);
	    query.setParameter("respuestaFolioConclusion", BeanUtils.isNotNull(tramiteDTO.getRespuestaFolioConclusion()) 
	    		? tramiteDTO.getRespuestaFolioConclusion() : null);
	    query.setParameter(PARAM_FECHA_CREACION, tramiteDTO.getFechaCreacion(), org.hibernate.type.TimestampType.INSTANCE);
	    query.setParameter(PARAM_FECHA_REVISION, tramiteDTO.getFechaRevision(), org.hibernate.type.TimestampType.INSTANCE);
	    query.setParameter("uuid", tramiteDTO.getUuid());
	    query.setParameter("idPersonaMoral", BeanUtils.isNotNull(tramiteDTO.getUsuario())
	    			? tramiteDTO.getUsuario().getIdPersonaMoral() : null, org.hibernate.type.LongType.INSTANCE);
	    
	    query.executeUpdate();
	}
	
	/**
	 * Método que realiza la actualización del estatus del trámite.
	 * 
	 * @param tramiteActual
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizarEstatusTramite(TramiteDTO tramiteActual) {
		final StringBuilder strQueryUpdate = new StringBuilder();

		strQueryUpdate.append(SQL_UPDATE).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(SQL_SET)
			.append("id_estatus_tramite = :idEstatusTramite ")
			.append(SQL_WHERE).append(SQL_ID_TRAMITE);

		Query query = em.createNativeQuery(strQueryUpdate.toString());
		query.setParameter(PARAM_ID_ESTATUS_TRAMITE, tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite());
		query.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite());

		query.executeUpdate();
	}
	
	/**
	 * Método que realiza la actualización de la revocación del aviso
	 * 
	 * @param tramiteActual
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void realizarActualizacionRevocacionAviso(TramiteDTO tramiteActual) {
		final StringBuilder strQueryUpdate = new StringBuilder();
	
		strQueryUpdate.append(SQL_UPDATE).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(SQL_SET)
			.append("motivo_rechazo = :motivoRechazo, ")
			.append("ruta_documento_revocado = :rutaDocumento, ")
			.append("fecha_revision = :fechaRevision, ")
			.append("id_usuario_revisor = :idUsuarioRevisor ")
			.append(SQL_WHERE).append(SQL_ID_TRAMITE);
	
		em.createNativeQuery(strQueryUpdate.toString())
			.setParameter("motivoRechazo", tramiteActual.getMotivoRechazo())
			.setParameter("rutaDocumento", tramiteActual.getRutaDocumentoRevocado())
			.setParameter(PARAM_FECHA_REVISION, tramiteActual.getFechaRevision())
			.setParameter(PARAM_ID_USUARIO_REVISOR, tramiteActual.getUsuarioRevisor().getIdUsuarioLlaveCdmx())
			.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite())
			.executeUpdate();
	}
	
	
	/**
	 * Método que realiza la eliminación del registro de firma de un trámite.
	 * 
	 * @param tramiteActual
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminaFirmadoTramite(TramiteDTO tramiteActual) {
		final StringBuilder strQueryDelete = new StringBuilder();
	
		strQueryDelete.append("DELETE FROM ")
			.append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA)
			.append(SQL_WHERE).append(SQL_ID_TRAMITE);
	
		em.createNativeQuery(strQueryDelete.toString())
			.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite())
			.executeUpdate();
	}
	
	/**
	 * Método que elimina la respuesta de prevención de un trámite.
	 * 
	 * @param tramiteActual
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminaRespuestaPrevencionTramite(TramiteDTO tramiteActual) {
		final StringBuilder strQueryUpdate = new StringBuilder();
	
		strQueryUpdate.append(SQL_UPDATE).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(SQL_SET)
			.append("respuesta_folio_prevencion = :respuesta ")
			.append(SQL_WHERE).append(SQL_ID_TRAMITE);
	
		em.createNativeQuery(strQueryUpdate.toString())
			.setParameter("respuesta", null)
			.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite())
			.executeUpdate();
	}
	
	/**
	 * Método que elimina la respuesta de conclusión de un trámite.
	 * 
	 * @param tramiteActual
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminaRespuestaConclusionTramite(TramiteDTO tramiteActual) {
		final StringBuilder strQueryUpdate = new StringBuilder();
	
		strQueryUpdate.append(SQL_UPDATE).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(SQL_SET)
			.append("respuesta_folio_conclusion = :respuesta")
			.append(SQL_WHERE).append(SQL_ID_TRAMITE);
	
		em.createNativeQuery(strQueryUpdate.toString())
			.setParameter("respuesta", null)
			.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite())
			.executeUpdate();	
	}
	
	/**
	 * Método que realiza la actualización de la fecha de creación y id del estatus del trámite.
	 * 
	 * @param tramiteActual
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void generarActualizacionTramite(TramiteDTO tramiteActual) {
		final StringBuilder strQueryUpdate = new StringBuilder();
	
		strQueryUpdate.append(SQL_UPDATE).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(SQL_SET)
			.append("fecha_creacion = :fechaCreacion, ")
			.append(SQL_ID_ESTATUS_TRAMITE)
			.append(SQL_WHERE).append(SQL_ID_TRAMITE);
	
		em.createNativeQuery(strQueryUpdate.toString())
			.setParameter(PARAM_FECHA_CREACION, tramiteActual.getFechaCreacion())
			.setParameter(PARAM_ID_ESTATUS_TRAMITE, tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite())
			.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite())
			.executeUpdate();
	}
	
	/**
	 * Método que realiza la actualización de la fecha de revision, id del estatus del trámite y id usuario revisor.
	 * 
	 * @param tramiteActual
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizarRevisionTramite(TramiteDTO tramiteActual) {
		Predicate<CatEstatusTramiteDTO> prEstatusApro   = p -> p.getIdEstatusTramite()==Constantes.ID_ESTATUS_APROBADO;
		Predicate<CatEstatusTramiteDTO> prEstatusRech   = p -> p.getIdEstatusTramite()==Constantes.ID_ESTATUS_RECHAZADO;
		Predicate<CatEstatusTramiteDTO> prEstatusDifApr = p -> BeanUtils.isDiferent(p.getIdEstatusTramite(), Constantes.ID_ESTATUS_APROBADO);
		final StringBuilder strQueryUpdate = new StringBuilder();
		
		strQueryUpdate.append(SQL_UPDATE).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(SQL_SET)
			.append(SQL_FECHA_REVISION).append(", ")
			.append(SQL_ID_ESTATUS_TRAMITE).append(", ");

		if (prEstatusApro.test(tramiteActual.getCatEstatusTramiteDTO())) {
			strQueryUpdate.append(SQL_RUTA_DOCTO_PREV_NULL).append(", ");
			strQueryUpdate.append(SQL_RESPUESTA_FOLIO_CONCLUSION).append(", ");
		} else if (prEstatusRech.test(tramiteActual.getCatEstatusTramiteDTO())) {
			strQueryUpdate.append(SQL_RESPUESTA_FOLIO_CONCLUSION).append(", ");
			strQueryUpdate.append(SQL_RUTA_DOCTO_PREV).append(", ");
		} else {
			strQueryUpdate.append(SQL_RESPUESTA_FOLIO_PREVENCION).append(", ");
			strQueryUpdate.append(SQL_RUTA_DOCTO_PREV).append(", ");
		}
		
		strQueryUpdate.append(SQL_ID_USUARIO_REVISOR)
			.append(SQL_WHERE)
			.append(SQL_ID_TRAMITE);		
		
		Query query = em.createNativeQuery(strQueryUpdate.toString());
		query.setParameter(PARAM_FECHA_REVISION, tramiteActual.getFechaRevision());
		query.setParameter(PARAM_ID_ESTATUS_TRAMITE, tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite());
		query.setParameter(PARAM_ID_USUARIO_REVISOR, tramiteActual.getUsuarioRevisor().getIdUsuarioLlaveCdmx());
		query.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite());
		
		if (prEstatusApro.test(tramiteActual.getCatEstatusTramiteDTO())) {
			query.setParameter("respuestaFolioConclusion", BeanUtils.isNull(tramiteActual.getRespuestaFolioConclusion()) ? 
					null : StringUtils.obtenerCadenaEscapada(tramiteActual.getRespuestaFolioConclusion()));
		} else if (prEstatusRech.test(tramiteActual.getCatEstatusTramiteDTO())) {
			query.setParameter("respuestaFolioConclusion", BeanUtils.isNull(tramiteActual.getRespuestaFolioConclusion()) ? 
					null : StringUtils.obtenerCadenaEscapada(tramiteActual.getRespuestaFolioConclusion()));			
			query.setParameter("rutaDocumentoPrevencion", BeanUtils.isNull(tramiteActual.getRutaDocumentoPrevencion()) ? 
					null : tramiteActual.getRutaDocumentoPrevencion());
		} else if (prEstatusDifApr.test(tramiteActual.getCatEstatusTramiteDTO())) {
			query.setParameter("respuestaFolioPrevencion", BeanUtils.isNull(tramiteActual.getRespuestaFolioPrevencion()) ? 
					null : tramiteActual.getRespuestaFolioPrevencion());
			query.setParameter("rutaDocumentoPrevencion", BeanUtils.isNull(tramiteActual.getRutaDocumentoPrevencion()) ? 
					null : tramiteActual.getRutaDocumentoPrevencion());
		}
		query.executeUpdate();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizaResolucionTramite(TramiteDTO tramiteActual) {
		final StringBuilder strQueryUpdate = new StringBuilder();
		
		strQueryUpdate.append(SQL_UPDATE).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(SQL_SET)
			.append(SQL_FECHA_REVISION).append (", ")
		    .append(SQL_ID_ESTATUS_TRAMITE).append (", ");
		
		if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_CONCLUSION_POSITIVA) {
			strQueryUpdate.append(SQL_RUTA_RESOLUCION_POSITIVA).append (", ");
	    } else if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_CONCLUSION_NEGATIVA) {
	    	strQueryUpdate.append(SQL_RUTA_RESOLUCION_NEGATIVA).append (", ");
	    }
		
		strQueryUpdate.append(SQL_ID_USUARIO_REVISOR);
	    strQueryUpdate.append(SQL_WHERE).append(SQL_ID_TRAMITE);
	    
	    Query query = em.createNativeQuery(strQueryUpdate.toString());
	    query.setParameter(PARAM_FECHA_REVISION, tramiteActual.getFechaRevision());
	    query.setParameter(PARAM_ID_ESTATUS_TRAMITE, tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite());
	    query.setParameter(PARAM_ID_USUARIO_REVISOR, tramiteActual.getUsuarioRevisor().getIdUsuarioLlaveCdmx());
	    query.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite());
	    
	    if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_CONCLUSION_POSITIVA) {
	    	query.setParameter("rutaResolucionPositiva",
	    			BeanUtils.isNull(tramiteActual.getRutaDocumentoResolucionPositiva()) ? null : tramiteActual.getRutaDocumentoResolucionPositiva());
	    } else if (tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_CONCLUSION_NEGATIVA) {
	    	query.setParameter("rutaResolucionNegativa",
	    			BeanUtils.isNull(tramiteActual.getRutaDocumentoResolucionNegativa()) ? null : tramiteActual.getRutaDocumentoResolucionNegativa());
	    }
	    query.executeUpdate();
	}
	
	/**
	 * Método que genera la sentencia que inserta un registro del formulario en Base de Datos.
	 * @param tramiteActual
	 * @param idSeccion
	 * @param nombreTabla
	 * @param lstColumnas
	 * @param mapRespuestas
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void generarSentenciaRegistro(TramiteDTO tramiteActual, Long idSeccion, String nombreTabla, List<String> lstColumnas, Map<String, Object> mapRespuestas) {
		final StringBuilder strQueryInsert = new StringBuilder();
	
		strQueryInsert.append(SQL_INSERT).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(nombreTabla)
			.append(" (id, id_seccion, id_tramite, fecha_creacion, ");
		
		String ultimaColumna = lstColumnas.get(lstColumnas.size() - 1);
		for (String columnaTmp : lstColumnas) {
			strQueryInsert.append(columnaTmp);
			if (!ultimaColumna.equals(columnaTmp)) {
				strQueryInsert.append(", ");
			}
		}
		
		strQueryInsert.append(") VALUES (nextval('")
        	.append(Constantes.ESQUEMA_INTERPRETE).append(".")
        	.append(nombreTabla).append(Constantes.NOMBRE_BASE_SECUENCIAS)
        	.append("'), :idSeccion, :idTramite, :fechaCreacion, ");
		
		for (String columnaTmp : lstColumnas) {
			String tipoColumna = obtenerTipoColumna(nombreTabla, columnaTmp);	        
	        if (Constantes.TIPO_DATO_JSON.equalsIgnoreCase(tipoColumna) 
	        	|| Constantes.TIPO_DATO_JSONB.equalsIgnoreCase(tipoColumna)) {
	            strQueryInsert.append("CAST (:").append(columnaTmp).append(" as ").append(tipoColumna).append(")"); 
	        } else {
	        	strQueryInsert.append(":").append(columnaTmp);
	        }
			if (!ultimaColumna.equals(columnaTmp)) {
				strQueryInsert.append(", ");
			}
		}
		strQueryInsert.append(")");		

		SQLQuery query = em.createNativeQuery(strQueryInsert.toString())
                .unwrap(SQLQuery.class);

	    query.setParameter(PARAM_ID_SECCION, idSeccion);
	    query.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite());
	    query.setParameter(PARAM_FECHA_CREACION, tramiteActual.getFechaCreacion());

	    for (String columnaTmp : lstColumnas) {
	    	String tipoColumna = obtenerTipoColumna(nombreTabla, columnaTmp);
	    	Object valor = mapRespuestas.get(columnaTmp);
	    	
	        if (BeanUtils.isNotNull(valor) && BeanUtils.isNotEmpty(valor.toString())) {
	        	Object valorConvertido = convertirValorPorTipo(valor, tipoColumna);
	    	    	query.setParameter(columnaTmp, valorConvertido);
	        } else {
	            query.setParameter(columnaTmp, null, getHibernateType(tipoColumna));
	        }	
	    }	
	    
	    query.executeUpdate();	    
	}
	
	/**
	 * Método que genera la sentencia para actualizar un registro del formulario en Base de Datos.
	 * @param tramiteActual
	 * @param nombreTabla
	 * @param lstColumnas
	 * @param mapRespuestas
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void generarSentenciaActualizacion(TramiteDTO tramiteActual, String nombreTabla, List<String> lstColumnas,
			Map<String, Object> mapRespuestas) {
		final StringBuilder strQueryUpdate = new StringBuilder();

		strQueryUpdate.append(SQL_UPDATE).append(Constantes.ESQUEMA_INTERPRETE).append(".").append(nombreTabla)
				.append(SQL_SET).append(SQL_FECHA_CREACION).append(", ");

		String ultimaColumna = lstColumnas.get(lstColumnas.size() - 1);
		for (String columnaTmp : lstColumnas) {
			String tipoColumna = obtenerTipoColumna(nombreTabla, columnaTmp);
			if (Constantes.TIPO_DATO_JSON.equalsIgnoreCase(tipoColumna)
					|| Constantes.TIPO_DATO_JSONB.equalsIgnoreCase(tipoColumna)) {
				strQueryUpdate.append(columnaTmp).append(" = ").append("CAST (:").append(columnaTmp).append(" as ")
						.append(tipoColumna).append(")");
			} else {
				strQueryUpdate.append(columnaTmp).append(" = :").append(columnaTmp);
			}
			if (!ultimaColumna.equals(columnaTmp)) {
				strQueryUpdate.append(", ");
			}
		}

		strQueryUpdate.append(SQL_WHERE).append(SQL_ID_TRAMITE);

		SQLQuery query = em.createNativeQuery(strQueryUpdate.toString()).unwrap(SQLQuery.class);
		query.setParameter(PARAM_FECHA_CREACION, tramiteActual.getFechaCreacion());
		query.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite());
		for (String columnaTmp : lstColumnas) {
			String tipoColumna = obtenerTipoColumna(nombreTabla, columnaTmp);
			Object valor = mapRespuestas.get(columnaTmp);

			if (BeanUtils.isNotNull(valor) && BeanUtils.isNotEmpty(valor.toString())) {
				Object valorConvertido = convertirValorPorTipo(valor, tipoColumna);
				query.setParameter(columnaTmp, valorConvertido);
			} else {
				query.setParameter(columnaTmp, null, getHibernateType(tipoColumna));
			}
		}
		query.executeUpdate();
	}
	
	/**
	 * Método que realiza la consulta del folio de un trámite
	 * 
	 * @param tramiteActual
	 * @return
	 * @throws Exception
	 */
	public TramiteDTO consultarFolioTramite(TramiteDTO tramiteActual) throws Exception {
		TramiteDTO folioTramite = new TramiteDTO();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("t.id_tramite, t.folio_seguimiento, t.uuid  ");
		strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES)
				.append(" t ");
		strQuery.append("WHERE t.id_tramite = ").append(tramiteActual.getIdTramite());

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), null, null);
		for (Object[] row : rows) {
			folioTramite.setIdTramite(Long.parseLong(String.valueOf(row[0])));
			folioTramite.setFolioSeguimiento((String) row[1]);
			folioTramite.setUuid(row[2] != null ? (String) row[2] : null);
		}
		return folioTramite;
	}
		
	/**
	 * Método que genera la sentencia para consultar los datos registrados de la tabla indicada (Sección actual).
	 * 
	 * @param tramiteActual
	 * @param nombreTabla
	 * @param mapControlComponentes
	 * @param lstColumnas
	 * @return
	 * @throws Exception
	 */
	public  Map<String, Object> consultarRespuestasSeccion(TramiteDTO tramiteActual, String nombreTabla, 
			Map<String, ControlComponentesDTO> mapControlComponentes, List<String> lstColumnas) throws Exception {
		Map<String, Object> mapRespuestas = new HashMap<>();
		final StringBuilder strQuerySelect = new StringBuilder();
			
		strQuerySelect.append("SELECT id, ");
		String ultimaColumna = lstColumnas.get(lstColumnas.size() - 1);
		for(String columnaTmp: lstColumnas) {
			if(ultimaColumna.equals(columnaTmp)) {
				if(isCampoDatoJson(mapControlComponentes, columnaTmp)) {
					strQuerySelect.append("CAST(").append(columnaTmp).append(" as varchar) ").append(columnaTmp).append(" ");
				} else {
					strQuerySelect.append(columnaTmp).append(" ");	
				}	
			} else {
				if(isCampoDatoJson(mapControlComponentes, columnaTmp)) {
					strQuerySelect.append("CAST(").append(columnaTmp).append(" as varchar) ").append(columnaTmp).append(", ");
				} else {
					strQuerySelect.append(columnaTmp).append(", ");	
				}
			}					
		}
		strQuerySelect.append(SQL_FROM).append(nombreTabla).append(" ");
		strQuerySelect.append("WHERE id_tramite = ").append(tramiteActual.getIdTramite()).append(";");
	
		List<Object[]> rows = ejecutarConsulta(strQuerySelect.toString(), null, null);
		
		/**Se inicia indice en 1, para omitir el campo de ID que no se requiere como respuesta, 
		 * se agrega a consulta para evitar error en casteo de List<Object[]>**/
		int indice = 1;
		for (Object[] row : rows) {		
			for(String columnaTmp: lstColumnas) {			
				if(row[indice] instanceof Date) {
					mapRespuestas.put(columnaTmp, (Date) row[indice]);	
				} else if(row[indice] instanceof Integer) {
					mapRespuestas.put(columnaTmp, (Integer) row[indice]);
				} else if(row[indice] instanceof Boolean) {
					mapRespuestas.put(columnaTmp, (Boolean) row[indice]);
				} else if(row[indice] instanceof Long) {
					mapRespuestas.put(columnaTmp, (Long) row[indice]);
				} else if(row[indice] instanceof String) {
					mapRespuestas.put(columnaTmp, (String) row[indice]);
				} else {
					mapRespuestas.put(columnaTmp, row[indice]);
				}
				indice++;
			}
		}
			
		return mapRespuestas;		
	}
	
	/**
	 * Método que obtiene las observaciones de una sección mediente su id trámite.
	 * 
	 * @param tramiteActual
	 * @param nombreTabla
	 * @return
	 * @throws Exception
	 */
	public SeccionesFormularioDTO consultarObservacionesSeccion(TramiteDTO tramiteActual, String nombreTabla) throws Exception {
		SeccionesFormularioDTO observacionesSeccion = null;
		
		final StringBuilder strQuerySelect = new StringBuilder();
			
		strQuerySelect.append("SELECT id, contiene_observaciones, observaciones ");		
		strQuerySelect.append(SQL_FROM).append(nombreTabla).append(" ");
		strQuerySelect.append("WHERE id_tramite = ").append(tramiteActual.getIdTramite()).append(";");
	
		List<Object[]> rows = ejecutarConsulta(strQuerySelect.toString(), null, null);
		
		for (Object[] row : rows) {		
			observacionesSeccion = new SeccionesFormularioDTO();
			observacionesSeccion.setIdSeccionFormulario(Long.parseLong(String.valueOf(row[0])));
			observacionesSeccion.setContieneObservaciones((boolean) row[1]);
			observacionesSeccion.setObservaciones(row[2] != null ? (String) row[2] : null);
		}		
		return observacionesSeccion;	
	}
	
	/**
	 * Método auxiliar que verifica si la columna pertenece a algún campo que registra la información en formato Json.
	 * 
	 * @param mapControlComponentes
	 * @param nombreColumna
	 * @return
	 */
	private boolean isCampoDatoJson(Map<String, ControlComponentesDTO> mapControlComponentes, String nombreColumna) {
	    boolean registraJson = false;
	    
	    ControlComponentesDTO control = mapControlComponentes.get(nombreColumna);
	    if (control != null) {
	        int idTipoComponente = control.getIdTipoComponente();
	        if (idTipoComponente == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO 
	                || idTipoComponente == Constantes.ID_COMPONENTE_CARGA_DOCUMENTOS
	                || idTipoComponente == Constantes.ID_COMPONENTE_TABLA) {
	            registraJson = true;
	        }
	    }
	    return registraJson;
	}
	
	/**
	 * Método que realiza la consulta de trámites por usuario
	 * @param tramite
	 * @return
	 * @throws Exception
	 */
	public List<TramiteDTO> consultarTamitesUsuario(TramiteDTO tramite) throws Exception {
	    
	    final StringBuilder strQuery = new StringBuilder();
	    strQuery.append("SELECT ");
	    strQuery.append("t.id_tramite, t.folio_seguimiento,  t.id_usuario_llave_cdmx, t.id_estatus_tramite, cet.descripcion, ");
	    strQuery.append("t.id_usuario_revisor, t.respuesta_folio_prevencion, t.respuesta_folio_conclusion, t.fecha_creacion, ");
	    strQuery.append("t.fecha_revision, t.ruta_documento_prevencion, t.uuid,  ");
	    strQuery.append("t.ruta_documento_revocado, cet.descripcion_aviso, t.motivo_rechazo, t.id_persona_moral, ");
	    strQuery.append("t.ruta_resolucion_positiva, t.ruta_resolucion_negativa, ");
	    strQuery.append("cet.descripcion_personalizada, lc.id_linea_captura, lc.linea_captura, lc.fecha_vigencia, ");
	    strQuery.append("lc.fecha_creacion as lc_fecha_creacion, lc.monto, lc.fecha_pago_lc, lc.ruta_documento_linea_captura, ");
	    strQuery.append("lc.solicitud_linea_captura, lc.id_estatus_linea_captura, cel.descripcion as lc_estatus_descripcion");
	    strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE).append(".").append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(" t ");
	    strQuery.append("JOIN motor_interprete.cat_estatus_tramite cet on t.id_estatus_tramite = cet.id_estatus_tramite ");
	    strQuery.append("LEFT JOIN motor_interprete.linea_captura lc on t.id_tramite = lc.id_tramite ");
	    strQuery.append("and lc.id_linea_captura = (select max(id_linea_captura) from motor_interprete.linea_captura ");
	    strQuery.append("where id_tramite = lc.id_tramite) ");
	    strQuery.append("LEFT JOIN motor_interprete.cat_estatus_linea_captura cel on lc.id_estatus_linea_captura = cel.id_estatus_linea_captura ");	    
	    strQuery.append("WHERE 1 = 1 ");
	    List<TramiteDTO> lstTramites = new ArrayList<>();
	    List<String> lstParametros = new ArrayList<>();
	    Map<String, Object> parametrosBusqueda = new HashMap<>();
	    
	    this.validaParametrosConsultaUsuario(tramite, lstParametros, parametrosBusqueda, strQuery);
	    
	    List<Object[]> rows = ejecutarConsulta(strQuery.toString(), lstParametros, parametrosBusqueda);		
	    for (Object[] row : rows) {		
	        TramiteDTO tramiteTemp = new TramiteDTO();
	        tramiteTemp.setIdTramite(Long.parseLong(String.valueOf(row[0])));
	        tramiteTemp.setFolioSeguimiento((String) row[1]);
	        tramiteTemp.setUsuario(row[2]!= null ? new UsuarioDTO(Long.parseLong(String.valueOf(row[2]))) : null);	        	        
	        CatEstatusTramiteDTO estatus = new CatEstatusTramiteDTO();
	        estatus.setIdEstatusTramite((int) row[3]);
	        estatus.setDescripcion((String) row[4]);
	        tramiteTemp.setCatEstatusTramiteDTO(estatus);	        
	        tramiteTemp.setUsuarioRevisor(row[5]!= null ? new UsuarioDTO(Long.parseLong(String.valueOf(row[5]))) : null);
	        tramiteTemp.setRespuestaFolioPrevencion(BeanUtils.getStringOrDefault(row[6], null ));
	        tramiteTemp.setRespuestaFolioConclusion(BeanUtils.getStringOrDefault(row[7], null ));
	        tramiteTemp.setFechaCreacion((Date) row[8]);
	        tramiteTemp.setFechaRevision(BeanUtils.getDate(row[9]));
	        tramiteTemp.setRutaDocumentoPrevencion(BeanUtils.getStringOrDefault(row[10], null ));
	        tramiteTemp.setUuid((String) row[11]);
	        tramiteTemp.setRutaDocumentoRevocado((String) row[12]);
	        tramiteTemp.getCatEstatusTramiteDTO().setDescripcionAviso((String) row[13]);
	        tramiteTemp.setMotivoRechazo((String) row[14]);
	        tramiteTemp.setTipoPersona(row[15] != null? "Sí":"No");			
	        tramiteTemp.setRutaDocumentoResolucionPositiva((String) row[16]);
	        tramiteTemp.setRutaDocumentoResolucionNegativa((String) row[17]);
	        tramiteTemp.getCatEstatusTramiteDTO().setDescripcionPersonalizada((String) row[18]);
	        if(row[19] != null) {
	        	LineaCapturaDTO lineaC = new LineaCapturaDTO(Long.parseLong(String.valueOf(row[19])));
		        	lineaC.setIdTramite(tramiteTemp.getIdTramite());
			        lineaC.setLineaCaptura((String) row[20]);
			        lineaC.setFechaVigencia((Date) row[21]);	        
			        lineaC.setFechaCreacion((Date) row[22]); 
			        lineaC.setMonto(BeanUtils.isNotNull(row[23])?Double.parseDouble(String.valueOf(row[23])):0.0);
			        lineaC.setFechaPagoLc(BeanUtils.getDate(row[24]));
			        lineaC.setRutaDocumentoLineaCaptura(BeanUtils.getStringOrDefault(row[25], null ));
			        lineaC.setSolicitudLineaCaptura(String.valueOf(row[26]));
		        CatEstatusLineaCapturaDTO catEstatusLC = new CatEstatusLineaCapturaDTO(Integer.parseInt(String.valueOf(row[27])));		       
			        catEstatusLC.setDescripcion((String) row[28]);
			        lineaC.setCatEstatusLineaCaptura(catEstatusLC);
			        
		        tramiteTemp.setLineaCapturaDTO(lineaC);
	        }
	        lstTramites.add(tramiteTemp);
	    }		
	    return lstTramites.isEmpty() ? null : lstTramites;
	}
	
	/**
	 * Metodo auxiliar en la generacion de la consulta 
	 * 
	 * @param parametrosBusqueda
	 * @param strQuery
	 */
	public void validaParametrosConsultaUsuario(TramiteDTO tramite, List<String> lstParametros, Map<String, Object> parametrosBusqueda, StringBuilder strQuery) {
		if(BeanUtils.isNotNull(tramite.getUsuario()) 
	            && tramite.getUsuario().getIdUsuarioLlaveCdmx() > 0L) {
	        lstParametros.add("id_usuario_llave_cdmx");
	        parametrosBusqueda.put("id_usuario_llave_cdmx", tramite.getUsuario().getIdUsuarioLlaveCdmx());
	        strQuery.append("AND t.id_usuario_llave_cdmx = :id_usuario_llave_cdmx ");
	    }
		strQuery.append(this.valParametrosComunConsultaTramites(tramite, lstParametros, parametrosBusqueda));
	    strQuery.append("ORDER BY t.id_tramite DESC ");
	}
	
	/**
	 * Método que realiza la consulta todos los trámites 
	 * @param tramite
	 * @return
	 * @throws Exception
	 */
	public List<TramiteDTO> consultarTamites(TramiteDTO tramite, ConsultaTramiteDTO consulta) throws Exception {
		Predicate<String> snMoral = p->p.equals("No");
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
	    strQuery.append("t.id_tramite, t.folio_seguimiento, t.id_usuario_llave_cdmx, u.nombre as nombre_ciudadano, ");
	    strQuery.append("u.primer_apellido as primer_apellido_ciudadano, u.segundo_apellido as segundo_apellido_ciudadano, ");
	    strQuery.append("t.id_estatus_tramite, cet.descripcion, t.respuesta_folio_prevencion, t.respuesta_folio_conclusion, ");
	    strQuery.append("t.fecha_creacion, t.uuid, u.correo, u.curp, t.fecha_revision, t.ruta_documento_prevencion, ");
	    strQuery.append("t.id_usuario_operador, ur.correo as correo_operador, ur.nombre, ur.primer_apellido, ur.segundo_apellido, ");
	    strQuery.append("t.ruta_documento_revocado, cet.descripcion_aviso, t.motivo_rechazo, t.id_persona_moral,  ");
	    strQuery.append("t.ruta_resolucion_positiva, t.ruta_resolucion_negativa, cet.descripcion_personalizada, ");
	    strQuery.append("lc.id_linea_captura, lc.linea_captura, lc.fecha_vigencia, lc.fecha_creacion as lc_fecha_creacion, ");
	    strQuery.append("lc.monto, lc.fecha_pago_lc, lc.ruta_documento_linea_captura, lc.solicitud_linea_captura, ");
	    strQuery.append("lc.id_estatus_linea_captura, cel.descripcion as lc_estatus_descripcion");
	    strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE).append(".").append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(" t ");
	    strQuery.append("JOIN motor_interprete.cat_estatus_tramite cet on t.id_estatus_tramite = cet.id_estatus_tramite ");
	    strQuery.append("LEFT JOIN motor_interprete.usuario u on t.id_usuario_llave_cdmx = u.id_usuario_llave_cdmx ");
	    strQuery.append("LEFT JOIN motor_interprete.usuario ur on t.id_usuario_operador = ur.id_usuario_llave_cdmx ");
	    strQuery.append("LEFT JOIN motor_interprete.linea_captura lc on t.id_tramite = lc.id_tramite ");
	    strQuery.append("and lc.id_linea_captura = (select max(id_linea_captura) from motor_interprete.linea_captura ");
	    strQuery.append("where id_tramite = lc.id_tramite) ");
	    strQuery.append("LEFT JOIN motor_interprete.cat_estatus_linea_captura cel on cel.id_estatus_linea_captura = lc.id_estatus_linea_captura ");
	    strQuery.append("WHERE 1 = 1 AND t.id_estatus_tramite <> 1 ");
		
		List<TramiteDTO> lstTramites = new ArrayList<>();
		List<String> lstParametros = new ArrayList<>();
		Map<String, Object> parametrosBusqueda = new HashMap<>();
		this.validaParametrosConsulta(tramite, lstParametros, parametrosBusqueda, strQuery, consulta);
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), lstParametros, parametrosBusqueda);		
		for (Object[] row : rows) {		
	        TramiteDTO tramiteTemp = new TramiteDTO();
	        tramiteTemp.setUsuario(null);
	        tramiteTemp.setUsuarioOperador(null);
	        tramiteTemp.setIdTramite(Long.parseLong(String.valueOf(row[0])));
	        tramiteTemp.setFolioSeguimiento((String) row[1]);
	        if(row[2] != null) {
	            UsuarioDTO usuario = new UsuarioDTO(Long.parseLong(String.valueOf(row[2])));
	            usuario.setNombre(BeanUtils.getStringOrDefault(row[3], Constantes.EMPTY_STRING ));
	            usuario.setPrimerApellido(BeanUtils.getStringOrDefault(row[4], Constantes.EMPTY_STRING ));
	            usuario.setSegundoApellido(BeanUtils.getStringOrDefault(row[5], Constantes.EMPTY_STRING ));
	            tramiteTemp.setUsuario(usuario);				
	        } 
	        // Configurar estatus con ambos campos
	        CatEstatusTramiteDTO estatus = new CatEstatusTramiteDTO();
	        estatus.setIdEstatusTramite((int) row[6]);
	        estatus.setDescripcion((String) row[7]);
	        tramiteTemp.setCatEstatusTramiteDTO(estatus);
	        
	        tramiteTemp.setRespuestaFolioPrevencion(BeanUtils.getStringOrDefault(row[8], null ));
	        tramiteTemp.setRespuestaFolioConclusion(BeanUtils.getStringOrDefault(row[9], null ));
	        tramiteTemp.setFechaCreacion((Date) row[10]);
	        tramiteTemp.setUuid(BeanUtils.getStringOrDefault(row[11], null));
	       this.asignaDatosCiudadano(row, tramiteTemp);;
	        tramiteTemp.setFechaRevision(BeanUtils.getDate(row[14]));
	        tramiteTemp.setRutaDocumentoPrevencion(BeanUtils.getStringOrDefault(row[15], null));
	        
	        this.asignaOperadorTramite(row, tramiteTemp);
	        
	        tramiteTemp.setRutaDocumentoRevocado((String) row[21]);
	        tramiteTemp.getCatEstatusTramiteDTO().setDescripcionAviso((String) row[22]);
	        tramiteTemp.setMotivoRechazo((String) row[23]);
	        tramiteTemp.setTipoPersona(row[24] != null ? "Sí":"No");
	        //Si existe el id persona moral lo llevamos en los datos para para poder usarlo para webhook
	        if(snMoral.negate().test(tramiteTemp.getTipoPersona())) {
	        	tramiteTemp.getUsuario().setIdPersonaMoral(Long.parseLong(String.valueOf(row[24])));
	        }
	        tramiteTemp.setRutaDocumentoResolucionPositiva((String) row[25]);
	        tramiteTemp.setRutaDocumentoResolucionNegativa((String) row[26]);        
	        tramiteTemp.getCatEstatusTramiteDTO().setDescripcionPersonalizada((String) row[27]);
	        if(row[28] != null) {
	        	LineaCapturaDTO lineaC = new LineaCapturaDTO(Long.parseLong(String.valueOf(row[28])));
	        		lineaC.setIdTramite(tramiteTemp.getIdTramite());
			        lineaC.setLineaCaptura((String) row[29]);
			        lineaC.setFechaVigencia((Date) row[30]);	        
			        lineaC.setFechaCreacion((Date) row[31]);			        
			        lineaC.setMonto(BeanUtils.isNotNull(row[32])?Double.parseDouble(String.valueOf(row[32])):0.0);
			        lineaC.setFechaPagoLc(BeanUtils.getDate(row[33]));			        
			        lineaC.setRutaDocumentoLineaCaptura(BeanUtils.getStringOrDefault(row[34], null ));
			        lineaC.setSolicitudLineaCaptura(String.valueOf(row[35]));
		        CatEstatusLineaCapturaDTO catEstatusLC = new CatEstatusLineaCapturaDTO(Integer.parseInt(String.valueOf(row[36])));		       
			        catEstatusLC.setDescripcion((String) row[37]);
		        lineaC.setCatEstatusLineaCaptura(catEstatusLC);
		        
		        tramiteTemp.setLineaCapturaDTO(lineaC);
	        }
	        lstTramites.add(tramiteTemp);
	    }		
	    return lstTramites.isEmpty() ? null : lstTramites;
	}
	
	/**
	 * metodo auxiliar para reducir complejidad consultarTamites Asignar Datos Ciudadano
	 * @param row
	 * @param tramiteTemp
	 */
	public void asignaDatosCiudadano(Object[] row, TramiteDTO tramiteTemp) {
		if(BeanUtils.isNotNull(tramiteTemp.getUsuario())) {
        	if(BeanUtils.isNotNull(row[12])) {
	        	tramiteTemp.getUsuario().setCorreo((String) row[12]);	
	        }
        	if(BeanUtils.isNotNull(row[13])) {
	        	tramiteTemp.getUsuario().setCurp((String) row[13]);	
	        } 
        }
	}
	
	/**
	 * Metodo auxiliar para reducir complejidad consultarTamites AsignarOperador
	 * @param row
	 * @param tramiteTemp
	 */
	public void asignaOperadorTramite(Object[] row, TramiteDTO tramiteTemp) {
		if(row[16] != null) {
            UsuarioDTO usuarioOperador = new UsuarioDTO(Long.parseLong(String.valueOf(row[16])));
            
            usuarioOperador.setCorreo(BeanUtils.getStringOrDefault(row[17], null));
            usuarioOperador.setNombre(BeanUtils.getStringOrDefault(row[18], null));
            usuarioOperador.setPrimerApellido(BeanUtils.getStringOrDefault(row[19], null));
            usuarioOperador.setSegundoApellido(BeanUtils.getStringOrDefault(row[20], null));
            usuarioOperador.setNombreCompleto(
            (BeanUtils.isNotNull(usuarioOperador.getNombre()) ? usuarioOperador.getNombre() : Constantes.EMPTY_STRING) + Constantes.ESPACIO + 
                    (BeanUtils.isNotNull(usuarioOperador.getPrimerApellido()) ? usuarioOperador.getPrimerApellido() : Constantes.EMPTY_STRING) + Constantes.ESPACIO + 
                            (BeanUtils.isNotNull(usuarioOperador.getSegundoApellido()) ? usuarioOperador.getSegundoApellido() : Constantes.EMPTY_STRING)); 
            tramiteTemp.setUsuarioOperador(usuarioOperador);
        }
	}
	
	/**
	 * Metodo auxiliar el generacion de consulta mediante parametros
	 * @param tramite objeto tramite
	 * @param lstParametros lista de parametros a producir
	 * @param parametrosBusqueda                                                 
	 * @param strQuery
	 * @param consulta
	 */
	public void validaParametrosConsulta(TramiteDTO tramite, List<String> lstParametros, 
										Map<String, Object> parametrosBusqueda, StringBuilder strQuery,
										ConsultaTramiteDTO consulta) {
		if(BeanUtils.isNotNull(tramite.getFolioSeguimiento()) && BeanUtils.isNotEmpty(tramite.getFolioSeguimiento())) {
			lstParametros.add("folio_seguimiento");
			parametrosBusqueda.put("folio_seguimiento", tramite.getFolioSeguimiento().toUpperCase());
			strQuery.append("AND t.folio_seguimiento = :folio_seguimiento ");
		}	
		if(BeanUtils.isNotNull(tramite.getUsuario()) && BeanUtils.isNotNull(tramite.getUsuario().getCurp()) 
				&& BeanUtils.isNotEmpty(tramite.getUsuario().getCurp())) {
			lstParametros.add("curp");
			parametrosBusqueda.put("curp", tramite.getUsuario().getCurp());
			strQuery.append("AND u.curp = :curp ");
		}
		strQuery.append(this.valParametrosComunConsultaTramites(tramite, lstParametros, parametrosBusqueda));
		if(consulta.isOperador()) {
			lstParametros.add("id_usuario_operador");
			parametrosBusqueda.put("id_usuario_operador", tramite.getUsuarioOperador().getIdUsuarioLlaveCdmx());
			strQuery.append("AND t.id_usuario_operador = :id_usuario_operador ");
		}
		
		strQuery.append(this.validaDistribucionTramites(lstParametros, parametrosBusqueda, consulta));		
		strQuery.append("ORDER BY t.id_tramite DESC ");
	}
	
	/**
	 * 
	 * Validacion de parametros comunes dentro de la metodos consultarTamites/consultarTamitesUsuario
	 * @param tramite
	 * @param lstParametros
	 * @param parametrosBusqueda
	 * @return
	 */
	public StringBuilder valParametrosComunConsultaTramites(TramiteDTO tramite, List<String> lstParametros, Map<String, Object> parametrosBusqueda) {		
		StringBuilder strQuery = new StringBuilder();
		if(BeanUtils.isNotNull(tramite.getCatEstatusTramiteDTO()) 
	            && tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() > Constantes.INT_VALOR_CERO) {
	        lstParametros.add(SQL_ID_ESTATUS_TRAMITE_ID);
	        parametrosBusqueda.put(SQL_ID_ESTATUS_TRAMITE_ID, tramite.getCatEstatusTramiteDTO().getIdEstatusTramite());
	        strQuery.append("AND t.id_estatus_tramite = :id_estatus_tramite ");
	    }
	    if(BeanUtils.isNotNull(tramite.getFechaDesde())) {
	        tramite.setFechaDesde(BeanUtils.asignarTiempoAFecha(
	                tramite.getFechaDesde(), 
	                Constantes.INT_VALOR_CERO, 
	                Constantes.INT_VALOR_CERO, 
	                Constantes.INT_VALOR_CERO));
	        
	        lstParametros.add("fecha_desde");
	        parametrosBusqueda.put("fecha_desde", tramite.getFechaDesde());
	        strQuery.append("AND t.fecha_creacion >=:fecha_desde ");
	    }
	    if(BeanUtils.isNotNull(tramite.getFechaHasta())) {
	        tramite.setFechaHasta(BeanUtils.asignarTiempoAFecha(
	                tramite.getFechaHasta(), 
	                Constantes.INT_MAX_HORA_DIA, 
	                Constantes.INT_MAX_MINUTO_SEGUNDO_HORA, 
	                Constantes.INT_MAX_MINUTO_SEGUNDO_HORA));
	        
	        lstParametros.add("fecha_hasta");
	        parametrosBusqueda.put("fecha_hasta", tramite.getFechaHasta());
	        strQuery.append("AND t.fecha_creacion <=:fecha_hasta ");
	    }
	    return strQuery;
	}
	
	
	
	/**
	 * Metodo auxiliar para validar la distribucion de tramites dentro de la consulta
	 * @param tramite
	 * @param lstParametros
	 * @param parametrosBusqueda
	 * @param consulta
	 */
	public StringBuilder validaDistribucionTramites(List<String> lstParametros, 
			Map<String, Object> parametrosBusqueda, ConsultaTramiteDTO consulta) {
		StringBuilder strQuery = new StringBuilder();
		if(consulta.isHabilitaDistribucion() && ((consulta.isConsulta() 
				&& BeanUtils.isNotEmpty(consulta.getNombreTablaComponenteDistribucion())) 
				|| (consulta.isOperador() && BeanUtils.isNotEmpty(consulta.getNombreTablaComponenteDistribucion())) 
				|| (consulta.isSupervisor() && BeanUtils.isNotEmpty(consulta.getNombreTablaComponenteDistribucion())))) {
			
			if(BeanUtils.isNotNull(consulta.getIdTipoComponente()) && consulta.getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_DOMICILIO) {
				String componenteBusqueda = "componente_" + consulta.getIdComponente() + "_6";
				
				lstParametros.add("elementos_distribucion");
				parametrosBusqueda.put("elementos_distribucion", consulta.getLstElementosDistribucion());
				
				strQuery.append("AND t.id_tramite in (SELECT sfd.id_tramite FROM motor_interprete.")
				.append(consulta.getNombreTablaComponenteDistribucion())
				.append(" sfd WHERE sfd.")
				.append(componenteBusqueda)
				.append(" in(").append(" :elementos_distribucion").append(")) ");
				
			} else if(BeanUtils.isNotNull(consulta.getIdTipoComponente()) && consulta.getIdTipoComponente().intValue() == Constantes.ID_COMPONENTE_MENU_DESPLEGABLE) {
				String componenteBusqueda = "componente_" + consulta.getIdComponente();
				
				lstParametros.add("elementos_distribucion");
				parametrosBusqueda.put("elementos_distribucion", consulta.getLstElementosDistribucion());
				
				strQuery.append("AND t.id_tramite in  (SELECT sfd.id_tramite FROM motor_interprete.")
				.append(consulta.getNombreTablaComponenteDistribucion())
				.append(" sfd WHERE sfd.")
				.append(componenteBusqueda)
				.append(" in(").append(" :elementos_distribucion").append(")) ");
			}
		}
		return strQuery;
	}
	
	/**
	 * Método que realiza la consulta de Estatus de trámites.
	 * @return
	 * @throws Exception
	 */
	public List<CatEstatusTramiteDTO> consultarEstatusTramite() throws Exception {

	    final StringBuilder strQuery = new StringBuilder();
	    strQuery.append("SELECT ");
	    strQuery.append("cet.id_estatus_tramite, cet.descripcion, cet.descripcion_personalizada, cet.descripcion_aviso ");
	    strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE).append(".cat_estatus_tramite cet ");		
	    strQuery.append("ORDER BY cet.id_estatus_tramite ASC");

	    List<CatEstatusTramiteDTO> lstEstatusTramite = new ArrayList<>();		
	    List<Object[]> rows = ejecutarConsulta(strQuery.toString(), null, null);		
	    for (Object[] row : rows) {		
	        CatEstatusTramiteDTO estatusTemp = new CatEstatusTramiteDTO();
	        estatusTemp.setIdEstatusTramite((int) row[0]);
	        estatusTemp.setDescripcion((String) row[1]);
	        estatusTemp.setDescripcionPersonalizada((String) row[2]);
	        estatusTemp.setDescripcionAviso((String) row[3]);
	        lstEstatusTramite.add(estatusTemp);
	    }		
	    return  !lstEstatusTramite.isEmpty() ? lstEstatusTramite : null;
	}		
	
	/**
	 * Método que consulta si existe información registrada de una sección, por el id de trámite.
	 * 26/06/2026
	 * Se modifica para solo usar count de existe o no datos
	 * @param tramite
	 * @param nombreTabla
	 * @return
	 * @throws Exception
	 */
	public boolean existeRegistroSeccionPorTramite(TramiteDTO tramite, String nombreTabla) throws Exception {
		StringBuilder strQuery2 = new StringBuilder();
		strQuery2.append("SELECT count(*)");
		strQuery2.append(SQL_FROM).append(nombreTabla).append(" sf ");		
		strQuery2.append("WHERE sf.id_tramite = :idTramite");
		BigInteger count = (BigInteger) em.createNativeQuery(strQuery2.toString())
					   .setParameter(PARAM_ID_TRAMITE, tramite.getIdTramite())
					   .getSingleResult();
		return count.intValue() > 0;
	}		

	/**
	 * Método auxiliar que consulta el identificador consecutivo para el guardado de trámites
	 * @param strQueryInsert
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long consultarConsecutivoTramite() {
		Long idTramite = null;
		Query query = em.createNativeQuery("select nextval('"+ Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES + Constantes.NOMBRE_BASE_SECUENCIAS+ "');");
		idTramite =	((BigInteger) query.getSingleResult()).longValue();
		
		return idTramite;
	}		
	
	/**
	 * Método que realiza la consulta de todos los trámites en prevención
	 * @param idEstatusTramite
	 * @return
	 * @throws Exception
	 */
	public List<TramiteDTO> obtenerTamitesPorEstatusPrevencion(int idEstatusTramite) throws Exception {
		
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("t.id_tramite, t.folio_seguimiento,  t.id_usuario_llave_cdmx, t.id_estatus_tramite, cet.descripcion, ");
		strQuery.append("t.id_usuario_revisor, t.respuesta_folio_prevencion, t.respuesta_folio_conclusion, t.fecha_creacion, t.uuid, t.fecha_revision, t.ruta_documento_prevencion, u.correo ");
		strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE).append(".").append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(" t ");
		strQuery.append("JOIN motor_interprete.cat_estatus_tramite cet on t.id_estatus_tramite = cet.id_estatus_tramite ");
		strQuery.append("LEFT JOIN motor_interprete.usuario u on t.id_usuario_llave_cdmx = u.id_usuario_llave_cdmx ");
		strQuery.append("WHERE t.id_estatus_tramite = ").append(idEstatusTramite);
		strQuery.append(" ORDER BY t.id_tramite DESC ");
		
		List<TramiteDTO> lstTramites = new ArrayList<>();
		
		
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), null, null);		
		for (Object[] row : rows) {		
			TramiteDTO tramiteTemp = new TramiteDTO();
			tramiteTemp.setIdTramite(Long.parseLong(String.valueOf(row[0])));
			tramiteTemp.setFolioSeguimiento((String) row[1]);
			tramiteTemp.setUsuario(row[2]!= null ? new UsuarioDTO(Long.parseLong(String.valueOf(row[2]))) : null);
			tramiteTemp.getCatEstatusTramiteDTO().setIdEstatusTramite((int) row[3]);
			tramiteTemp.getCatEstatusTramiteDTO().setDescripcion((String) row[4]);
			tramiteTemp.setUsuarioRevisor(row[5]!= null ? new UsuarioDTO(Long.parseLong(String.valueOf(row[5]))) : null);
			tramiteTemp.setRespuestaFolioPrevencion(row[6] != null ? (String) row[6] : null );
			tramiteTemp.setRespuestaFolioConclusion(row[7] != null ? (String) row[7] : null );
			tramiteTemp.setFechaCreacion((Date) row[8]);
			tramiteTemp.setUuid(row[9] != null ? (String) row[9] : null );
			tramiteTemp.setFechaRevision((Date) row[10]);
			tramiteTemp.setRutaDocumentoPrevencion(row[11] != null ? (String) row[11] : null );
			tramiteTemp.getUsuario().setCorreo(row[12] != null ? (String) row[12] : null );
			
			lstTramites.add(tramiteTemp);
		}		
		return  !lstTramites.isEmpty() ? lstTramites : null;
	}
		
	/**
	 * Método que registra las observaciones de las secciones 
	 *  
	 * @param tramiteActual
	 * @param seccionActual
	 * @param nombreTabla
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void generarSentenciaRevisionSecciones(TramiteDTO tramiteActual, SeccionesFormularioDTO seccionActual, String nombreTabla) {
		final StringBuilder strQueryUpdate = new StringBuilder();
		
		strQueryUpdate.append(SQL_UPDATE).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(nombreTabla).append(SQL_SET)
			.append(" contiene_observaciones = :contieneObservaciones, ")
			.append(" observaciones = :observaciones ");

		strQueryUpdate.append(SQL_WHERE)
			.append(" id_seccion = :idSeccion ").append(" AND ")
			.append(SQL_ID_TRAMITE);

		Query query = em.createNativeQuery(strQueryUpdate.toString());
		query.setParameter("contieneObservaciones", seccionActual.isContieneObservaciones());
		query.setParameter("observaciones", 
				seccionActual.isContieneObservaciones() ? 
						StringUtils.obtenerCadenaEscapada(seccionActual.getObservaciones()) : seccionActual.getObservaciones());
		query.setParameter(PARAM_ID_SECCION, seccionActual.getIdSeccionFormulario());
		query.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite());

	    query.executeUpdate();
	}
	
	
	/**
	 * Método que registra las observaciones de una sección 
	 *  
	 * @param tramiteActual
	 * @param seccionActual
	 * @param nombreTabla
	 */	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizaObservacionesSeccion(TramiteDTO tramiteActual, SeccionesFormularioDTO seccionActual, String nombreTabla) {
		final StringBuilder strQueryUpdate = new StringBuilder();		
		
		strQueryUpdate.append(SQL_UPDATE).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(nombreTabla).append(SQL_SET)
			.append(SQL_CONTIENE_OBSER).append(", ")
			.append(" observaciones = :observaciones ")
			.append(SQL_WHERE)
			.append(" id_seccion = :idSeccion ").append(" AND ")
			.append(SQL_ID_TRAMITE);
		
		Query query = em.createNativeQuery(strQueryUpdate.toString());
		query.setParameter("contieneObservaciones", seccionActual.isContieneObservaciones());
		query.setParameter("observaciones", seccionActual.isContieneObservaciones() ? 
				StringUtils.obtenerCadenaEscapada(seccionActual.getObservaciones()) : seccionActual.getObservaciones());
		query.setParameter(PARAM_ID_SECCION, seccionActual.getIdSeccionFormulario());
		query.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite());
		query.executeUpdate();
			
	}
	
	/**
	 * Método que realiza la actualización del id_usuario_revisor del trámite
	 * @param tramiteActual
	 */
	public void actualizarOperadorTramite(TramiteDTO tramiteActual) {
		final StringBuilder strQueryUpdate = new StringBuilder();
	
		strQueryUpdate.append(SQL_UPDATE).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(SQL_SET)
			.append(SQL_ID_USUARIO_OPERADOR).append(", ")
			.append(SQL_ID_USUARIO_REVISOR)
			.append(SQL_WHERE)
			.append(SQL_ID_TRAMITE);
	
		Query query = em.createNativeQuery(strQueryUpdate.toString());
		query.setParameter("idUsuarioOperador", tramiteActual.getUsuarioOperador().getIdUsuarioLlaveCdmx());
		query.setParameter(PARAM_ID_USUARIO_REVISOR, tramiteActual.getUsuarioRevisor().getIdUsuarioLlaveCdmx());
		query.setParameter(PARAM_ID_TRAMITE, tramiteActual.getIdTramite());
		query.executeUpdate();
	}
	
	/**
	 * Método auxiliar que realiza la ejecución de la consulta enviada.
	 * @param consulta
	 * @param lstParametros
	 * @param parametrosBusqueda
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> ejecutarConsulta(String consulta, List<String> lstParametros, Map<String, Object> parametrosBusqueda) throws Exception {
		List<Object[]> rows = null; 
		Query query = em.createNativeQuery(consulta);	
		if(BeanUtils.isNotNull(lstParametros) && BeanUtils.isNotEmpty(lstParametros)) {
			for(String parametro: lstParametros) {
				query.setParameter(parametro, parametrosBusqueda.get(parametro));
			}
		}
		rows = query.getResultList();
		
		return rows;
	}
	
	/**
	 * Método que realiza la consulta un trámite a través de su uuid
	 * 
	 * @param tramiteActual
	 * @return
	 * @throws Exception
	 */
	public List<TramiteDTO> consultarSeguimientoTramite(TramiteDTO tramiteActual) throws Exception {
		List<TramiteDTO> lstTramites = new ArrayList<>();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("t.id_tramite, t.folio_seguimiento,  t.id_usuario_llave_cdmx, t.id_estatus_tramite, cet.descripcion, ");
		strQuery.append("t.id_usuario_revisor, t.respuesta_folio_prevencion, t.respuesta_folio_conclusion, t.fecha_creacion ");
		strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(" t ");		
		strQuery.append("JOIN motor_interprete.cat_estatus_tramite cet on t.id_estatus_tramite = cet.id_estatus_tramite ");
		strQuery.append("WHERE t.uuid = '").append(tramiteActual.getUuid());
		strQuery.append("'");

		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), null, null);	
		for (Object[] row : rows) {		
			TramiteDTO tramiteTemp = new TramiteDTO();
			tramiteTemp.setIdTramite(Long.parseLong(String.valueOf(row[0])));
			tramiteTemp.setFolioSeguimiento((String) row[1]);
			tramiteTemp.setUsuario(row[2]!= null ? new UsuarioDTO(Long.parseLong(String.valueOf(row[2]))) : null);
			tramiteTemp.getCatEstatusTramiteDTO().setIdEstatusTramite((int) row[3]);
			tramiteTemp.getCatEstatusTramiteDTO().setDescripcion((String) row[4]);
			tramiteTemp.setUsuarioRevisor(row[5]!= null ? new UsuarioDTO(Long.parseLong(String.valueOf(row[5]))) : null);
			tramiteTemp.setRespuestaFolioPrevencion(row[6] != null ? (String) row[6] : null );
			tramiteTemp.setRespuestaFolioConclusion(row[7] != null ? (String) row[7] : null );
			tramiteTemp.setFechaCreacion((Date) row[8]);
			
			lstTramites.add(tramiteTemp);
		}	
		
		return  !lstTramites.isEmpty() ? lstTramites : null;
	}
		
	/**
	 * Método que realiza el registro de firma electronica de un trámite
	 * 
	 * @param tramiteFirmaElectronicaDTO
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registrarFirmaTramite(TramiteFirmaElectronicaDTO tramiteFirmaElectronicaDTO) {
		final StringBuilder strQueryInsert = new StringBuilder();
	
		strQueryInsert.append(SQL_INSERT).append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA)
			.append(" (id_tramite_firma, cadena_original, cadena_firmada, nombre_firmante, fecha_creacion, respuesta_servicio, id_tramite, firma_ciudadano)")
			.append(SQL_VALUES)
			.append("(:idTramiteFirma, :cadenaOriginal, :cadenaFirmada, :nombreFirmante, :fechaCreacion, :respuestaServicio, :idTramite, :firmaCiudadano)");

		SQLQuery query = em.createNativeQuery(strQueryInsert.toString())
                .unwrap(SQLQuery.class);
		
	    query.setParameter("idTramiteFirma", tramiteFirmaElectronicaDTO.getIdTramiteFirma());
	    query.setParameter("cadenaOriginal", tramiteFirmaElectronicaDTO.getCadenaOriginal());
	    query.setParameter("cadenaFirmada", tramiteFirmaElectronicaDTO.getCadenaFirmada());
	    query.setParameter("nombreFirmante", BeanUtils.isNull(tramiteFirmaElectronicaDTO.getNombreFirmante()) 
	            ? null : tramiteFirmaElectronicaDTO.getNombreFirmante());
	    query.setParameter(PARAM_FECHA_CREACION, tramiteFirmaElectronicaDTO.getFechaCreacion(), org.hibernate.type.TimestampType.INSTANCE);
	    query.setParameter("respuestaServicio", tramiteFirmaElectronicaDTO.getRespuestaServicio());
	    query.setParameter(PARAM_ID_TRAMITE, tramiteFirmaElectronicaDTO.getTramiteDTO().getIdTramite());
	    query.setParameter("firmaCiudadano", tramiteFirmaElectronicaDTO.isFirmaCiudadano());

	    query.executeUpdate();
		
	}
	
	/**
	 * Método auxiliar que consulta el identificador consecutivo para el guardado de trámites firmados
	 * 
	 * @return
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long consultarConsecutivoTramitesFirma() {
		Long idTramiteFirma = null;
		Query query = em.createNativeQuery("select nextval('"+ Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA + Constantes.NOMBRE_BASE_SECUENCIAS+ "');");
		idTramiteFirma =	((BigInteger) query.getSingleResult()).longValue();
		
		return idTramiteFirma;
	}	
	
	/**
	 * Método auxiliar que consulta el identificador consecutivo para el guardado de movimientos de bítacora
	 * para revertir estatus de trámites
	 * @param strQueryInsert
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long consultarConsecutivoBitacoraRevertir() {
		Long idTramite = null;
		Query query = em.createNativeQuery("select nextval('"+ Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS + Constantes.NOMBRE_BASE_SECUENCIAS+ "');");
		idTramite =	((BigInteger) query.getSingleResult()).longValue();
		
		return idTramite;
	}		
	
	/**
	 * Método que realiza la consulta de firma de trámite mediante el id de trámite.
	 * 
	 * @param tramite
	 * @return
	 * @throws Exception
	 */
	public List<TramiteFirmaElectronicaDTO> consultarFirmaTramite(TramiteDTO tramite) throws Exception {
		List<TramiteFirmaElectronicaDTO> lstFirmaTramites = new ArrayList<>();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("tf.id_tramite_firma, tf.cadena_original,  tf.cadena_firmada, tf.nombre_firmante, tf.fecha_creacion,  ");
		strQuery.append("tf.respuesta_servicio, tf.id_tramite  ");
		strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA).append(" tf ");		
		strQuery.append("WHERE tf.id_tramite = ").append(tramite.getIdTramite()).append(Constantes.ESPACIO);
		strQuery.append("AND tf.firma_ciudadano = false ");
 
		List<Object[]> rows = ejecutarConsulta(strQuery.toString(), null, null);	
		for (Object[] row : rows) {		
			TramiteFirmaElectronicaDTO tramiteFirmaTemp = new TramiteFirmaElectronicaDTO();
			tramiteFirmaTemp.setIdTramiteFirma(Long.parseLong(String.valueOf(row[0])));
			tramiteFirmaTemp.setCadenaOriginal((String) row[1]);
			tramiteFirmaTemp.setCadenaFirmada((String) row[2]);
			tramiteFirmaTemp.setNombreFirmante((String) row[3]);
			tramiteFirmaTemp.setFechaCreacion((Date) row[4]);
			tramiteFirmaTemp.setRespuestaServicio((String) row[5]);
			tramiteFirmaTemp.setTramiteDTO(new TramiteDTO(Long.parseLong(String.valueOf(row[6]))));
						
			lstFirmaTramites.add(tramiteFirmaTemp);
		}	
		
		return  !lstFirmaTramites.isEmpty() ? lstFirmaTramites : null;
	}	
	
	public TramiteFirmaElectronicaDTO consultarFirmaTramiteCiudadano(TramiteDTO tramite) throws Exception {
			
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("tf.cadena_original,  tf.cadena_firmada, tf.nombre_firmante, tf.fecha_creacion ");
		strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA).append(" tf ");		
		strQuery.append("WHERE tf.id_tramite = ").append(tramite.getIdTramite()).append(" AND tf.firma_ciudadano = true");
		

	    try {
	        List<Object[]> rows = ejecutarConsulta(strQuery.toString(), null , null);
	        
	        if (rows.isEmpty()) {
	            return null; 
	        }
	        
	        Object[] row = rows.get(0);
	        TramiteFirmaElectronicaDTO dto = new TramiteFirmaElectronicaDTO();
	        dto.setCadenaOriginal((String) row[0]);
	        dto.setCadenaFirmada((String) row[1]);
	        dto.setNombreFirmante((String) row[2]);
	        dto.setFechaCreacion((Date) row[3]);
	        
	        return dto;
	    } catch (Exception e) {
	        throw new Exception("Error al consultar firma electrónica", e);
	    }
	}
	
	/*
	 * Método para identificar el tipo de datos que almacena el campo indicado de
	 * una tabla determinada
	 */
	private String obtenerTipoColumna(String tabla, String columna) {
		String sql = "SELECT data_type FROM information_schema.columns " + "WHERE table_schema = 'motor_interprete' "
				+ "AND table_name = :tabla " + "AND column_name = :columna";
		Query q = em.createNativeQuery(sql).setParameter("tabla", tabla).setParameter("columna", columna);

		return (String) q.getSingleResult();
	}
	
	/*
	 * Método para identificar el HibernateType
	 */
	private org.hibernate.type.AbstractSingleColumnStandardBasicType<?> getHibernateType(String tipoColumna) {
		if ("integer".equalsIgnoreCase(tipoColumna)) {
			return org.hibernate.type.IntegerType.INSTANCE;
		} else if ("bigint".equalsIgnoreCase(tipoColumna)) {
			return org.hibernate.type.LongType.INSTANCE;
		} else if ("boolean".equalsIgnoreCase(tipoColumna)) {
			return org.hibernate.type.BooleanType.INSTANCE;
		} else if ("numeric".equalsIgnoreCase(tipoColumna) || tipoColumna.toLowerCase().startsWith("numeric")) {
	        return org.hibernate.type.BigDecimalType.INSTANCE;
	    } else if ("timestamp without time zone".equalsIgnoreCase(tipoColumna)
				|| "timestamp with time zone".equalsIgnoreCase(tipoColumna)) {
			return org.hibernate.type.TimestampType.INSTANCE;
		} else if ("text".equalsIgnoreCase(tipoColumna)) {
			return org.hibernate.type.TextType.INSTANCE;
		} else if ("json".equalsIgnoreCase(tipoColumna) || "jsonb".equalsIgnoreCase(tipoColumna)) {
			return org.hibernate.type.TextType.INSTANCE;
		} else {
			return org.hibernate.type.StringType.INSTANCE; // default
		}
	}

	private Object convertirValorPorTipo(Object valor, String tipoColumna) {
		if (valor == null)
			return null;

		switch (tipoColumna.toLowerCase().trim()) {
		case Constantes.TIPO_DATO_INTEGER:
			return Integer.valueOf(valor.toString());
		case Constantes.TIPO_DATO_BIGINT:
			return Long.valueOf(valor.toString());
		case Constantes.TIPO_DATO_BOOLEAN:
			return Boolean.valueOf(valor.toString());
		case "numeric":
		case "decimal":
			return new BigDecimal(valor.toString());
		case "timestamp without time zone":
		case "timestamp with time zone":
			if (valor instanceof java.sql.Timestamp) {
				return valor;
			} else if (valor instanceof java.util.Date) {
				return new java.sql.Timestamp(((java.util.Date) valor).getTime());
			} else if (valor instanceof java.time.LocalDateTime) {
				return java.sql.Timestamp.valueOf((java.time.LocalDateTime) valor);
			} else {
				LocalDate fecha = LocalDate.parse(valor.toString());
				return java.sql.Timestamp.valueOf(fecha.atStartOfDay());
			}
		case Constantes.TIPO_DATO_JSON:
		case Constantes.TIPO_DATO_JSONB:
		case Constantes.TIPO_DATO_TEXT:
		case Constantes.TIPO_DATO_CHARACTER_VARYING:
		default:
			return valor.toString();
		}
	}
	
	public List<TramiteDTO> consultarTramites() throws Exception {
	    final String sql = "SELECT "
	            + "t.id_tramite, t.folio_seguimiento, t.id_usuario_llave_cdmx, t.id_estatus_tramite, "
	            + "t.id_usuario_operador, t.id_usuario_revisor, t.respuesta_folio_prevencion, t.respuesta_folio_conclusion, "
	            + "t.fecha_creacion, t.fecha_revision, t.uuid "
	            + "FROM " + Constantes.ESQUEMA_INTERPRETE + "." + Constantes.NOMBRE_BASE_TABLA_TRAMITES + " t";

	    List<Object[]> rows = ejecutarConsulta(sql, Collections.emptyList(), Collections.emptyMap());

	    List<TramiteDTO> tramites = new ArrayList<>();
	    for (Object[] row : rows) {
	        TramiteDTO tramite = new TramiteDTO();
	        tramite.setIdTramite(row[0] != null ? ((Number) row[0]).longValue() : null);
	        tramite.setFolioSeguimiento((String) row[1]);
	        tramite.setUsuario(row[2] != null ? new UsuarioDTO(((Number) row[2]).longValue()) : null);
	        tramite.getCatEstatusTramiteDTO().setIdEstatusTramite(row[3] != null ? ((Number) row[3]).intValue() : 0);
	        tramite.setUsuarioOperador(row[4] != null ? new UsuarioDTO(((Number) row[4]).longValue()) : null);
	        tramite.setUsuarioRevisor(row[5] != null ? new UsuarioDTO(((Number) row[5]).longValue()) : null);
	        tramite.setRespuestaFolioPrevencion((String) row[6]);
	        tramite.setRespuestaFolioConclusion((String) row[7]);
	        tramite.setFechaCreacion((Date) row[8]);
	        tramite.setFechaRevision((Date) row[9]);
	        tramite.setUuid((String) row[10]);
	        tramites.add(tramite);
	    }

	    return tramites; 
	}

		
}

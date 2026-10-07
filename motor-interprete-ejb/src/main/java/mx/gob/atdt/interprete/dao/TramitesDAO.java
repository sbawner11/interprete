package mx.gob.atdt.interprete.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.CatEstatusLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.LineaCapturaDTO;
import mx.gob.atdt.interprete.dto.ReporteContadoresProyectosDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;

@Stateless
@LocalBean
public class TramitesDAO extends IBaseService<TramiteDTO, Long> {

	private static final String SQL_SELECT = "SELECT ";
	private static final String SQL_FROM = " FROM ";

	@Override
	public TramiteDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(TramiteDTO e) {
		// TODO Auto-generated method stub
	}

	/**
	 * Método que obtiene todos los contadores del proyecto
	 * 
	 * @return
	 */
	public ReporteContadoresProyectosDTO consultaContadoresProyecto() {
		List<ReporteContadoresProyectosDTO> lstContadores = new ArrayList<>();

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(SQL_SELECT);
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 1 THEN 1 ELSE 0 END) total_captura, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 3 THEN 1 ELSE 0 END) total_enviados, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 4 THEN 1 ELSE 0 END) total_correccion, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 5 THEN 1 ELSE 0 END) total_corregidos, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 8 THEN 1 ELSE 0 END) total_revisados, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 6 THEN 1 ELSE 0 END) total_rechazados, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 7 THEN 1 ELSE 0 END) total_aceptados, ");
		strQuery.append("SUM(1) total_recibidos ");
		strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE).append(".")
				.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(" t ");

		Query query = em.createNativeQuery(strQuery.toString());
		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			ReporteContadoresProyectosDTO contadoresTmp = new ReporteContadoresProyectosDTO();
			contadoresTmp.setSubtotalCaptura(row[0] != null ? ((BigInteger) row[0]).intValue() : 0);
			contadoresTmp.setSubtotalEnviado(row[1] != null ? ((BigInteger) row[1]).intValue() : 0);
			contadoresTmp.setSubtotalCorreccion(row[2] != null ? ((BigInteger) row[2]).intValue() : 0);
			contadoresTmp.setSubtotalCorregido(row[3] != null ? ((BigInteger) row[3]).intValue() : 0);
			contadoresTmp.setSubtotalRevisado(row[4] != null ? ((BigInteger) row[4]).intValue() : 0);
			contadoresTmp.setSubtotalRechazado(row[5] != null ? ((BigInteger) row[5]).intValue() : 0);
			contadoresTmp.setSubtotalAceptado(row[6] != null ? ((BigInteger) row[6]).intValue() : 0);
			contadoresTmp.setTotalRegistros(row[7] != null ? ((BigInteger) row[7]).intValue() : 0);
			lstContadores.add(contadoresTmp);
		}
		return lstContadores != null && !lstContadores.isEmpty() ? lstContadores.get(0) : null;
	}

	/**
	 * Método que realiza el conteo de estatus en base a un rango de fechas definido
	 * 
	 * @return
	 */
	public ReporteContadoresProyectosDTO consultaContadoresProyectoPorFechas(TramiteDTO tramiteBusqueda) {
		List<ReporteContadoresProyectosDTO> lstContadores = new ArrayList<>();

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(SQL_SELECT);
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 1 THEN 1 ELSE 0 END) total_captura, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 3 THEN 1 ELSE 0 END) total_enviados, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 4 THEN 1 ELSE 0 END) total_correccion, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 5 THEN 1 ELSE 0 END) total_corregidos, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 8 THEN 1 ELSE 0 END) total_revisados, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 6 THEN 1 ELSE 0 END) total_rechazados, ");
		strQuery.append("SUM(CASE WHEN t.id_estatus_tramite = 7 THEN 1 ELSE 0 END) total_aceptados, ");
		strQuery.append("SUM(1) total_recibidos ");
		strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE).append(".")
				.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(" t ");
		if (BeanUtils.isNotNull(tramiteBusqueda.getFechaDesde())
				&& BeanUtils.isNotNull(tramiteBusqueda.getFechaHasta())) {
			strQuery.append("WHERE CAST(t.fecha_creacion AS date) >= :fechaDesde ");
			strQuery.append("AND CAST(t.fecha_creacion AS date) <= :fechaHasta ");
		}

		Query query = em.createNativeQuery(strQuery.toString());
		if (BeanUtils.isNotNull(tramiteBusqueda.getFechaDesde())
				&& BeanUtils.isNotNull(tramiteBusqueda.getFechaHasta())) {
			query.setParameter("fechaDesde", BeanUtils.convertirDateStringAnioMesDia(tramiteBusqueda.getFechaDesde()));
			query.setParameter("fechaHasta", BeanUtils.convertirDateStringAnioMesDia(tramiteBusqueda.getFechaHasta()));
		}

		List<Object[]> rows = query.getResultList();
		for (Object[] row : rows) {
			ReporteContadoresProyectosDTO contadoresTmp = new ReporteContadoresProyectosDTO();
			contadoresTmp.setSubtotalCaptura(row[0] != null ? ((BigInteger) row[0]).intValue() : 0);
			contadoresTmp.setSubtotalEnviado(row[1] != null ? ((BigInteger) row[1]).intValue() : 0);
			contadoresTmp.setSubtotalCorreccion(row[2] != null ? ((BigInteger) row[2]).intValue() : 0);
			contadoresTmp.setSubtotalCorregido(row[3] != null ? ((BigInteger) row[3]).intValue() : 0);
			contadoresTmp.setSubtotalRevisado(row[4] != null ? ((BigInteger) row[4]).intValue() : 0);
			contadoresTmp.setSubtotalRechazado(row[5] != null ? ((BigInteger) row[5]).intValue() : 0);
			contadoresTmp.setSubtotalAceptado(row[6] != null ? ((BigInteger) row[6]).intValue() : 0);
			contadoresTmp.setTotalRegistros(row[7] != null ? ((BigInteger) row[7]).intValue() : 0);
			lstContadores.add(contadoresTmp);
		}
		return lstContadores != null && !lstContadores.isEmpty() ? lstContadores.get(0) : null;
	}

	/**
	 * Método que obtiene total de tramites firmados
	 * 
	 * @return
	 */
	public int consultaTramitesFirmados() {
		int totalTramitesFirmados;

		final StringBuilder strQuery = new StringBuilder();
		strQuery.append(SQL_SELECT);
		strQuery.append("COUNT(t.id_tramite) as total_firmados ");
		strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE).append(".")
				.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(" t ");
		strQuery.append("INNER JOIN ").append(Constantes.ESQUEMA_INTERPRETE).append(".")
				.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES_FIRMA).append(" tfe ");
		strQuery.append("ON t.id_tramite = tfe.id_tramite ");

		Query query = em.createNativeQuery(strQuery.toString());
		totalTramitesFirmados = ((BigInteger) query.getSingleResult()).intValue();

		return totalTramitesFirmados;
	}

	/**
	 * Método auxiliar que realiza la consulta la información del un trámite
	 * mediante su id.
	 * 
	 * @param idTramite
	 * @return
	 */
	public List<TramiteDTO> buscarTramitePorId(Long idTramite) {

		List<TramiteDTO> lstTramites = new ArrayList<>();
		final StringBuilder strQuery = new StringBuilder();

		strQuery.append(SQL_SELECT);
		strQuery.append(
				"t.id_tramite, t.folio_seguimiento, t.id_usuario_llave_cdmx, u.nombre as nombre_ciudadano, u.primer_apellido as primer_apellido_ciudadano, u.segundo_apellido as segundo_apellido_ciudadano, ");
		strQuery.append(
				"t.id_estatus_tramite, cet.descripcion, t.respuesta_folio_prevencion, t.respuesta_folio_conclusion, t.fecha_creacion, t.uuid, u.correo, u.curp, t.fecha_revision, t.ruta_documento_prevencion, ");
		strQuery.append(
				"t.id_usuario_operador, ur.correo as correo_operador, ur.nombre, ur.primer_apellido, ur.segundo_apellido,");
		strQuery.append("t.ruta_documento_revocado, cet.descripcion_aviso, t.motivo_rechazo, ");
		strQuery.append("t.ruta_resolucion_positiva, t.ruta_resolucion_negativa ");
		strQuery.append(SQL_FROM).append(Constantes.ESQUEMA_INTERPRETE).append(".")
				.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(" t ");
		strQuery.append(
				"JOIN motor_interprete.cat_estatus_tramite cet on t.id_estatus_tramite = cet.id_estatus_tramite ");
		strQuery.append("LEFT JOIN motor_interprete.usuario u on t.id_usuario_llave_cdmx = u.id_usuario_llave_cdmx ");
		strQuery.append("LEFT JOIN motor_interprete.usuario ur on t.id_usuario_operador = ur.id_usuario_llave_cdmx ");
		strQuery.append("WHERE 1 = 1 AND t.id_tramite = :idTramite ");
		strQuery.append("ORDER BY t.id_tramite DESC ");

		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idTramite", idTramite);

		List<Object[]> rows = query.getResultList();

		for (Object[] row : rows) {
			TramiteDTO tramiteTemp = new TramiteDTO();
			tramiteTemp.setIdTramite(Long.parseLong(String.valueOf(row[0])));
			tramiteTemp.setFolioSeguimiento((String) row[1]);
			if (row[2] != null) {
				UsuarioDTO usuario = new UsuarioDTO(Long.parseLong(String.valueOf(row[2])));
				usuario.setNombre(row[3] != null ? (String) row[3] : Constantes.EMPTY_STRING);
				usuario.setPrimerApellido(row[4] != null ? (String) row[4] : Constantes.EMPTY_STRING);
				usuario.setSegundoApellido(row[5] != null ? (String) row[5] : Constantes.EMPTY_STRING);
				usuario.setNombreCompleto((usuario.getNombre() + " " + usuario.getPrimerApellido() + " " + usuario.getSegundoApellido()).trim());
				tramiteTemp.setUsuario(usuario);
			} else {
				tramiteTemp.setUsuario(null);
			}
			tramiteTemp.getCatEstatusTramiteDTO().setIdEstatusTramite((int) row[6]);
			tramiteTemp.getCatEstatusTramiteDTO().setDescripcion((String) row[7]);
			tramiteTemp.setRespuestaFolioPrevencion(row[8] != null ? (String) row[8] : null);
			tramiteTemp.setRespuestaFolioConclusion(row[9] != null ? (String) row[9] : null);
			tramiteTemp.setFechaCreacion((Date) row[10]);
			tramiteTemp.setUuid(row[11] != null ? (String) row[11] : null);
			if (row[12] != null && tramiteTemp.getUsuario() != null) {
				tramiteTemp.getUsuario().setCorreo((String) row[12]);
			}
			if (row[13] != null && tramiteTemp.getUsuario() != null) {
				tramiteTemp.getUsuario().setCurp((String) row[13]);
			}
			tramiteTemp.setFechaRevision(row[14] != null ? (Date) row[14] : null);
			tramiteTemp.setRutaDocumentoPrevencion(row[15] != null ? (String) row[15] : null);

			if (row[16] != null) {
				UsuarioDTO usuarioOperador = new UsuarioDTO(Long.parseLong(String.valueOf(row[16])));

				usuarioOperador.setCorreo(row[17] != null ? (String) row[17] : null);
				usuarioOperador.setNombre(row[18] != null ? (String) row[18] : null);
				usuarioOperador.setPrimerApellido(row[19] != null ? (String) row[19] : null);
				usuarioOperador.setSegundoApellido(row[20] != null ? (String) row[20] : null);
				usuarioOperador.setNombreCompleto(
						(BeanUtils.isNotNull(usuarioOperador.getNombre()) ? usuarioOperador.getNombre()
								: Constantes.EMPTY_STRING)
								+ Constantes.ESPACIO
								+ (BeanUtils.isNotNull(usuarioOperador.getPrimerApellido())
										? usuarioOperador.getPrimerApellido()
										: Constantes.EMPTY_STRING)
								+ Constantes.ESPACIO
								+ (BeanUtils.isNotNull(usuarioOperador.getSegundoApellido())
										? usuarioOperador.getSegundoApellido()
										: Constantes.EMPTY_STRING));
				tramiteTemp.setUsuarioOperador(usuarioOperador);
			} else {
				tramiteTemp.setUsuarioOperador(null);
			}
			tramiteTemp.setRutaDocumentoRevocado((String) row[21]);
			tramiteTemp.getCatEstatusTramiteDTO().setDescripcionAviso((String) row[22]);
			tramiteTemp.setMotivoRechazo((String) row[23]);
			tramiteTemp.setRutaDocumentoResolucionPositiva((String) row[24]);
			tramiteTemp.setRutaDocumentoResolucionNegativa((String) row[25]);

			lstTramites.add(tramiteTemp);
		}

		return lstTramites != null && !lstTramites.isEmpty() ? lstTramites : null;
	}
	
	/**
	 * Metodo creado para consultar los tramites pendientes de pago
	 * Proyectos que tienen configurado la linea de captura
	 * 
	 * @author Ramiro Luna Torres
	 * 
	 * @param idEstatus
	 * @return
	 */
	public List<TramiteDTO> consultaTramitePorEstatus(int idEstatus) { 
		List<TramiteDTO> lstTramites = new ArrayList<>();
		final StringBuilder strQuery = new StringBuilder();

		strQuery.append(SQL_SELECT);
		strQuery.append("t.id_tramite, t.folio_seguimiento, t.id_usuario_llave_cdmx, u.nombre as nombre_ciudadano, ");
		strQuery.append("u.primer_apellido as pr_apellido_ciudadano, u.segundo_apellido as sg_apellido_ciudadano, ");
		strQuery.append("u.correo as correo_ciudadano, u.curp as curp_ciudadano, t.id_estatus_tramite, cet.descripcion, ");
		strQuery.append("cet.descripcion_aviso, cet.descripcion_personalizada, t.respuesta_folio_prevencion, ");
		strQuery.append("t.respuesta_folio_conclusion, t.fecha_creacion, t.uuid, t.fecha_revision, ");
		strQuery.append("t.ruta_documento_revocado, t.motivo_rechazo, t.ruta_resolucion_positiva, t.ruta_resolucion_negativa, ");
		strQuery.append("t.id_usuario_operador, ur.correo, ur.nombre, ur.primer_apellido, ur.segundo_apellido, ");
		strQuery.append("lc.id_linea_captura, lc.linea_captura, lc.fecha_vigencia, lc.fecha_creacion as lc_fecha_creacion, ");
		strQuery.append("lc.solicitud_linea_captura, lc.id_estatus_linea_captura, cel.descripcion as lc_estatus_descripcion");	   
		strQuery.append(SQL_FROM)
				.append(Constantes.ESQUEMA_INTERPRETE).append(".")
				.append(Constantes.NOMBRE_BASE_TABLA_TRAMITES).append(" t ");
		strQuery.append("JOIN motor_interprete.cat_estatus_tramite cet on t.id_estatus_tramite = cet.id_estatus_tramite ");
		strQuery.append("LEFT JOIN motor_interprete.usuario u on t.id_usuario_llave_cdmx = u.id_usuario_llave_cdmx ");
		strQuery.append("LEFT JOIN motor_interprete.usuario ur on t.id_usuario_operador = ur.id_usuario_llave_cdmx ");
		strQuery.append("LEFT JOIN motor_interprete.linea_captura lc on t.id_tramite = lc.id_tramite ");
	    strQuery.append("and lc.id_linea_captura = (select max(id_linea_captura) from motor_interprete.linea_captura ");
	    strQuery.append("where id_tramite = lc.id_tramite) ");
	    strQuery.append("LEFT JOIN motor_interprete.cat_estatus_linea_captura cel on cel.id_estatus_linea_captura = lc.id_estatus_linea_captura ");
	    strQuery.append("WHERE 1 = 1 AND t.id_estatus_tramite = :idEstatus ");
		strQuery.append("ORDER BY t.id_tramite DESC ");
		
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idEstatus", idEstatus);

		List<Object[]> rows = query.getResultList();

		for (Object[] row : rows) {
			TramiteDTO tramiteTemp = new TramiteDTO();
			tramiteTemp.setIdTramite(Long.parseLong(String.valueOf(row[0])));
			tramiteTemp.setFolioSeguimiento((String) row[1]);
			if (row[2] != null) {
				UsuarioDTO usuario = new UsuarioDTO(Long.parseLong(String.valueOf(row[2])));
				usuario.setNombre(BeanUtils.getStringOrDefault(row[3], Constantes.EMPTY_STRING ));
				usuario.setPrimerApellido(BeanUtils.getStringOrDefault(row[4], Constantes.EMPTY_STRING ));
				usuario.setSegundoApellido(BeanUtils.getStringOrDefault(row[5], Constantes.EMPTY_STRING ));
				usuario.setCorreo(BeanUtils.getStringOrDefault(row[6], null ));
				usuario.setCurp(BeanUtils.getStringOrDefault(row[7], null ));
				tramiteTemp.setUsuario(usuario);
			} else {
				tramiteTemp.setUsuario(null);
			}
			tramiteTemp.getCatEstatusTramiteDTO().setIdEstatusTramite((int) row[8]);
			tramiteTemp.getCatEstatusTramiteDTO().setDescripcion((String) row[9]);
			tramiteTemp.getCatEstatusTramiteDTO().setDescripcionAviso((String) row[10]);
			tramiteTemp.getCatEstatusTramiteDTO().setDescripcionPersonalizada(BeanUtils.getStringOrDefault(row[6], null ));
			tramiteTemp.setRespuestaFolioPrevencion(BeanUtils.getStringOrDefault(row[12], null ));
			tramiteTemp.setRespuestaFolioConclusion(BeanUtils.getStringOrDefault(row[3], null ));
			tramiteTemp.setFechaCreacion((Date) row[14]);
			tramiteTemp.setUuid(BeanUtils.getStringOrDefault(row[15], null ));
			
			tramiteTemp.setFechaRevision(BeanUtils.getDate(row[16]));
			tramiteTemp.setRutaDocumentoRevocado(BeanUtils.getStringOrDefault(row[17], null ));
			tramiteTemp.setMotivoRechazo(BeanUtils.getStringOrDefault(row[18], null ));
			tramiteTemp.setRutaDocumentoResolucionPositiva(BeanUtils.getStringOrDefault(row[19], null ));
			tramiteTemp.setRutaDocumentoResolucionNegativa(BeanUtils.getStringOrDefault(row[20], null ));
			if (row[21] != null) {
				UsuarioDTO usuarioOperador = new UsuarioDTO(Long.parseLong(String.valueOf(row[21])));

				usuarioOperador.setCorreo(BeanUtils.getStringOrDefault(row[22], null ));
				usuarioOperador.setNombre(BeanUtils.getStringOrDefault(row[23], null ));
				usuarioOperador.setPrimerApellido(BeanUtils.getStringOrDefault(row[24], null ));
				usuarioOperador.setSegundoApellido(BeanUtils.getStringOrDefault(row[25], null ));
				tramiteTemp.setUsuarioOperador(usuarioOperador);
			} else {
				tramiteTemp.setUsuarioOperador(null);
			}
			if(row[26] != null) {
	        	LineaCapturaDTO lineaC = new LineaCapturaDTO(Long.parseLong(String.valueOf(row[26])));
	        		lineaC.setIdTramite(tramiteTemp.getIdTramite());
			        lineaC.setLineaCaptura((String) row[27]);
			        lineaC.setFechaVigencia((Date) row[28]);	        
			        lineaC.setFechaCreacion((Date) row[29]);
			        lineaC.setSolicitudLineaCaptura(String.valueOf(row[30]));
		        CatEstatusLineaCapturaDTO catEstatusLC = new CatEstatusLineaCapturaDTO(Integer.parseInt(String.valueOf(row[31])));		       
			        catEstatusLC.setDescripcion((String) row[32]);
		        lineaC.setCatEstatusLineaCaptura(catEstatusLC); 
		        tramiteTemp.setLineaCapturaDTO(lineaC);
	        }else {
	        	tramiteTemp.setLineaCapturaDTO(null);
	        }
			lstTramites.add(tramiteTemp);
		}

		return !lstTramites.isEmpty() ? lstTramites : null;
	}
	
	/**
	 * 
	 * @return
	 */
	/**
	 * 15/06/2026 se modifica firma de metodo para nuevos datos enviar a webhook
	 * 
	 * Método que obtiene el listado de trámites que no han sido registrados para notificaciones mediante Webhook
	 * @param habilitarFirmadoTramites proyecto habilitado para Firma
	 * @return lista de tramites
	 */
	public List<TramiteDTO> obtenerTramitesPendientesNotificacion(boolean habilitarFirmadoTramites) {
		Predicate<List<TramiteDTO>> prLstVacio = p -> !p.isEmpty();
		List<TramiteDTO> lstTramites = null;
		if(habilitarFirmadoTramites) {
			List<TramiteDTO> lstTramitesAct = em.createNamedQuery("Tramites.findAllNoNotificadosWithFirmaActu", TramiteDTO.class).getResultList();
			List<TramiteDTO> lstTramitesCrea = em.createNamedQuery("Tramites.findAllNoNotificadosWithFirmaCrea", TramiteDTO.class).getResultList();
			lstTramites = Stream.concat(lstTramitesAct.stream(), lstTramitesCrea.stream()).collect(Collectors.toList());
		}else {
			lstTramites = em.createNamedQuery("Tramites.findAllNoNotificados", TramiteDTO.class).getResultList();
		}
		
		
		return  Optional.ofNullable(lstTramites).filter(prLstVacio).orElse(null);
	}
	
	/**
	 * Metodo destinado para consultar los tramites que han quedado pendientes de ser notificados a webhook
	 * 
	 * @return lista de tramites
	 */
	public List<TramiteDTO> consultaTramitesNotificacionFallidaWebHook() {
		Predicate<List<TramiteDTO>> prLstVacio =  p -> !p.isEmpty();
		List<TramiteDTO> lstTramites = em.createNamedQuery("Tramites.findAllTramitesNotificacionFallida", TramiteDTO.class).getResultList();		
		return  Optional.ofNullable(lstTramites).filter(prLstVacio).orElse(null);
	}
}
package mx.gob.atdt.interprete.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.dto.DynamicColumnConfigDTO;
import mx.gob.atdt.interprete.dto.DynamicTableRowDTO;


@Stateless
@LocalBean
public class DynamicTableDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynamicTableDAO.class);

    @Inject
    @PersistenceContext
    protected EntityManager em;

    private Gson gson = new Gson();

    /**
     * Obtiene la configuración general de la tabla dinámica
     */
    public Map<String, Object> obtenerConfiguracionTabla(Long idComponente) {
        Map<String, Object> config = new HashMap<>();
        try {
            String sql = "SELECT ct.permite_agregar_filas, ct.tamanio_pagina, ct.minimo_filas, ct.maximo_filas, " +
                         "c.titulo_campo, c.requerido " +
                         "FROM motor_interprete.componente_tabla ct " +
                         "INNER JOIN motor_interprete.componente c ON c.id_componente = ct.id_componente " +
                         "WHERE ct.id_componente = :idComponente";

            Query query = em.createNativeQuery(sql);
            query.setParameter("idComponente", idComponente);

            List<Object[]> results = query.getResultList();
            if (!BeanUtils.isEmpty(results)) {
                Object[] row = results.get(0);
                config.put("permite_agregar_filas", row[0] != null ? row[0] : true);
                config.put("tamanio_pagina", row[1] != null ? row[1] : 20);
                config.put("minimo_filas", row[2] != null ? row[2] : 1);
                config.put("maximo_filas", row[3] != null ? row[3] : 200);
                config.put("titulo", row[4] != null ? row[4] : "Tabla dinámica");
                config.put("tabla_requerida", row[5] != null ? row[5] : false);
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener configuración de tabla para componente: {}", idComponente, e);
        }
        return config;
    }

    /**
     * Obtiene las columnas configuradas para la tabla dinámica
     */
    public List<DynamicColumnConfigDTO> obtenerColumnasTabla(Long idComponente) {
        List<DynamicColumnConfigDTO> columnas = new ArrayList<>();
        try {
            String sql = "SELECT det.id_elemento_tabla, det.titulo_header, det.id_cat_tipo_campo, " +
                         "det.requerido, det.tooltip, det.longitu_celda, det.orden_columna, tcc.descripcion " +
                         "FROM motor_interprete.det_elementos_tabla det " +
                         "INNER JOIN motor_interprete.componente_tabla ct ON ct.id_componente_tabla = det.id_componente_tabla " +
                         "INNER JOIN motor_interprete.cat_tipo_campo tcc ON tcc.id_cat_tipo_campo = det.id_cat_tipo_campo " +
                         "WHERE ct.id_componente = :idComponente " +
                         "AND det.activo = true " + 
                         "ORDER BY det.orden_columna ASC";

            Query query = em.createNativeQuery(sql);
            query.setParameter("idComponente", idComponente);

            List<Object[]> results = query.getResultList();
            for (Object[] row : results) {
                DynamicColumnConfigDTO columna = new DynamicColumnConfigDTO();
                columna.setId(((Number) row[0]).longValue());
                columna.setNombre((String) row[1]);  // titulo_header
                
                int idTipoCampo = ((Number) row[2]).intValue();
                String tipo = idTipoCampo == 1 ? "NUMERICO" : (idTipoCampo == 3 ? "FECHA" : "ALFANUMERICO");
                columna.setTipo(tipo);
                columna.setRequerido((Boolean) row[3]);
                columna.setTooltip((String) row[4]);
                
                int longitud = row[5] != null ? ((Number) row[5]).intValue() : (idTipoCampo == 1 ? 25 : 200);
                columna.setLongitudMaxima(longitud);
                columna.setOrden(((Number) row[6]).intValue());                           
                columnas.add(columna);
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener columnas de tabla para componente: {}", idComponente, e);
        }
        return columnas;
    }

    /**
     * Obtiene los datos guardados de un trámite para una tabla dinámica
     */
    public List<DynamicTableRowDTO> obtenerDatosTabla(Long idTramite, Long idComponente) {
        List<DynamicTableRowDTO> filas = new ArrayList<>();
        try {
           
            String sql = "SELECT id_datos_tabla, numero_fila, tipo_fila, CAST(datos AS TEXT) as datos_text " +
                         "FROM motor_interprete.datos_tabla_dinamica " +
                         "WHERE id_tramite = :idTramite AND id_componente = :idComponente " +
                         "AND eliminado = false " +
                         "ORDER BY numero_fila ASC";

            Query query = em.createNativeQuery(sql);
            query.setParameter("idTramite", idTramite);
            query.setParameter("idComponente", idComponente);

            List<Object[]> results = query.getResultList();
            for (Object[] row : results) {
                DynamicTableRowDTO fila = new DynamicTableRowDTO();
                fila.setId(((Number) row[0]).longValue());
                fila.setNumeroFila((int) row[1]);
                fila.setTipoFila((String) row[2]);
               
                String datosJson = (String) row[3];
                if (BeanUtils.isNotNull(datosJson) && !datosJson.isEmpty()) {
                    TypeToken<Map<String, Object>> typeToken = new TypeToken<Map<String, Object>>() {};
                    Map<String, Object> datos = gson.fromJson(datosJson, typeToken.getType());
                    fila.setDatos(datos);
                } else {
                    fila.setDatos(new HashMap<>());
                }
                fila.setUuid(java.util.UUID.randomUUID().toString());
                fila.setEliminado(false);
                filas.add(fila);
            }
        } catch (Exception e) {
            LOGGER.error("Error al obtener datos de tabla para trámite: {} componente: {}", idTramite, idComponente, e);
        }
        return filas;
    }

    /**
     * Guarda una nueva fila en la tabla dinámica
     */
    public void guardarFila(Long idTramite, Long idComponente, DynamicTableRowDTO fila) {
    
        try {
            String datosJson = gson.toJson(fila.getDatos());          

            String sql = "INSERT INTO motor_interprete.datos_tabla_dinamica " +
                         "(id_tramite, id_componente, numero_fila, tipo_fila, datos, fecha_creacion, fecha_modificacion, eliminado) " +
                         "VALUES (:idTramite, :idComponente, :numeroFila, :tipoFila, CAST(:datos AS JSONB), NOW(), NOW(), false)";

            Query query = em.createNativeQuery(sql);
            query.setParameter("idTramite", idTramite);
            query.setParameter("idComponente", idComponente);
            query.setParameter("numeroFila", fila.getNumeroFila());
            query.setParameter("tipoFila", fila.getTipoFila());  // ← "DINAMICO" debe ir aquí
            query.setParameter("datos", datosJson);

            int rowsAffected = query.executeUpdate();
            
            if (rowsAffected > 0) {
                
                // Obtener el ID generado
                String selectIdSql = "SELECT id_datos_tabla FROM motor_interprete.datos_tabla_dinamica " +
                                     "WHERE id_tramite = :idTramite AND id_componente = :idComponente AND numero_fila = :numeroFila";
                Query idQuery = em.createNativeQuery(selectIdSql);
                idQuery.setParameter("idTramite", idTramite);
                idQuery.setParameter("idComponente", idComponente);
                idQuery.setParameter("numeroFila", fila.getNumeroFila());
                
                List<Object> results = idQuery.getResultList();
                if (!BeanUtils.isEmpty(results)) {
                    fila.setId(((Number) results.get(0)).longValue());                  
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error en guardarFila", e);
            throw new RuntimeException("Error al guardar fila en tabla dinámica", e);
        }
    }

    /**
     * Actualiza una fila existente
     */
    public void actualizarFila(Long idTramite, Long idComponente, DynamicTableRowDTO fila) {
        try {
            String datosJson = gson.toJson(fila.getDatos());

            String sql = "UPDATE motor_interprete.datos_tabla_dinamica " +
                         "SET datos = CAST(:datos AS JSONB), fecha_modificacion = NOW() " +
                         "WHERE id_tramite = :idTramite AND id_componente = :idComponente AND numero_fila = :numeroFila";

            Query query = em.createNativeQuery(sql);
            query.setParameter("idTramite", idTramite);
            query.setParameter("idComponente", idComponente);
            query.setParameter("numeroFila", fila.getNumeroFila());
            query.setParameter("datos", datosJson);

            query.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error al actualizar fila en tabla dinámica", e);
            throw new RuntimeException("Error al actualizar fila en tabla dinámica", e);
        }
    }

    /**
     * Actualiza una celda específica
     */
    public void actualizarCelda(Long idTramite, Long idComponente, int numeroFila,
            String columnaNombre, Object valor) {
		try {
			
			String selectSql = "SELECT CAST(datos AS TEXT) as datos_text FROM motor_interprete.datos_tabla_dinamica " +
			          "WHERE id_tramite = :idTramite AND id_componente = :idComponente AND numero_fila = :numeroFila";
			
			Query selectQuery = em.createNativeQuery(selectSql);
			selectQuery.setParameter("idTramite", idTramite);
			selectQuery.setParameter("idComponente", idComponente);
			selectQuery.setParameter("numeroFila", numeroFila);
			
			List<Object> results = selectQuery.getResultList();
			Map<String, Object> datosActuales = new HashMap<>();
			
			if (!BeanUtils.isEmpty(results) && results.get(0) != null) {
				String datosJson = (String) results.get(0);
				if (!datosJson.isEmpty()) {
					TypeToken<Map<String, Object>> typeToken = new TypeToken<Map<String, Object>>() {};
					datosActuales = gson.fromJson(datosJson, typeToken.getType());
				}
			}
			
			if (columnaNombre != null) {
				datosActuales.put(columnaNombre, valor);
			}
			String nuevosDatosJson = gson.toJson(datosActuales);
			
			String updateSql = "UPDATE motor_interprete.datos_tabla_dinamica " +
			          "SET datos = CAST(:datos AS JSONB), fecha_modificacion = NOW() " +
			          "WHERE id_tramite = :idTramite AND id_componente = :idComponente AND numero_fila = :numeroFila";
			
			Query updateQuery = em.createNativeQuery(updateSql);
			updateQuery.setParameter("idTramite", idTramite);
			updateQuery.setParameter("idComponente", idComponente);
			updateQuery.setParameter("numeroFila", numeroFila);
			updateQuery.setParameter("datos", nuevosDatosJson);
			
			updateQuery.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Error al actualizar celda en tabla dinámica", e);
			throw new RuntimeException("Error al actualizar celda en tabla dinámica", e);
		}
    }


    /**
     * Elimina una fila (borrado lógico)
     */
    public void eliminarFila(Long idTramite, Long idComponente, int numeroFila) {
        try {
            String sql = "UPDATE motor_interprete.datos_tabla_dinamica " +
                         "SET eliminado = true, fecha_modificacion = NOW() " +
                         "WHERE id_tramite = :idTramite AND id_componente = :idComponente AND numero_fila = :numeroFila";

            Query query = em.createNativeQuery(sql);
            query.setParameter("idTramite", idTramite);
            query.setParameter("idComponente", idComponente);
            query.setParameter("numeroFila", numeroFila);

            query.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error al eliminar fila en tabla dinámica", e);
        }
    }


    /**
     * Elimina físicamente todas las filas de un componente (útil para limpieza)
     */
    public void limpiarDatosTabla(Long idTramite, Long idComponente) {
        try {
            String sql = "DELETE FROM motor_interprete.datos_tabla_dinamica " +
                         "WHERE id_tramite = :idTramite AND id_componente = :idComponente";

            Query query = em.createNativeQuery(sql);
            query.setParameter("idTramite", idTramite);
            query.setParameter("idComponente", idComponente);

            query.executeUpdate();
        } catch (Exception e) {
            LOGGER.error("Error al limpiar datos de tabla dinámica", e);
        }
    }
}
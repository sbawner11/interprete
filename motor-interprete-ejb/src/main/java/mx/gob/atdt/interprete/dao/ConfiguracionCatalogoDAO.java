package mx.gob.atdt.interprete.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ConfiguracionCatalogoDTO;
import mx.gob.atdt.interprete.model.Catalogos;
import mx.gob.atdt.interprete.model.ConfiguracionCatalogo;
import mx.gob.atdt.interprete.model.OpcionesCatalogo;
import mx.gob.atdt.interprete.model.Proyecto;
//import mx.gob.atdt.interprete.model.Usuario;


@Stateless
@LocalBean
public class ConfiguracionCatalogoDAO extends IBaseService<ConfiguracionCatalogoDTO, Long> {

    public List<ConfiguracionCatalogoDTO> findByProyectoAndCatalogo(Long idProyecto, Integer idCatalogo) {
        return em.createNamedQuery("ConfiguracionCatalogo.findByProyectoAndCatalogo", ConfiguracionCatalogoDTO.class)
                .setParameter("idProyecto", idProyecto)
                .setParameter("idCatalogo", idCatalogo)
                .getResultList();
    }

    public ConfiguracionCatalogoDTO findByProyectoCatalogoAndOpcionCatalogo(Long idProyecto, Integer idCatalogo, Integer idOpcionCatalogo) {
        List<ConfiguracionCatalogoDTO> result = em.createNamedQuery("ConfiguracionCatalogo.findByProyectoCatalogoAndOpcionCatalogo", ConfiguracionCatalogoDTO.class)
                .setParameter("idProyecto", idProyecto)
                .setParameter("idCatalogo", idCatalogo)
                .setParameter("idOpcionCatalogo", idOpcionCatalogo)
                .getResultList();
        return result.isEmpty() ? null : result.get(0);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void inactivarByProyectoCatalogoAndOpcionCatalogo(Long idProyecto, Integer idCatalogo, Integer idOpcionCatalogo) {
        em.createNamedQuery("ConfiguracionCatalogo.inactivarByProyectoCatalogoAndOpcionCatalogo")
                .setParameter("idProyecto", idProyecto)
                .setParameter("idCatalogo", idCatalogo)
                .setParameter("idOpcionCatalogo", idOpcionCatalogo)
                .setParameter("fechaActualizacion", new Date())
                .executeUpdate();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void actualizar(ConfiguracionCatalogoDTO e) {
		ConfiguracionCatalogo configuracion = new ConfiguracionCatalogo();
		configuracion.setIdConfiguracionCatalogo(e.getIdConfiguracionCatalogo());
		configuracion.setProyecto(em.getReference(Proyecto.class, e.getIdProyecto()));
		configuracion.setCatalogo(em.getReference(Catalogos.class, e.getIdCatalogo()));
		configuracion.setOpcionesCatalogo(em.getReference(OpcionesCatalogo.class, e.getIdOpcionCatalogo()));
		configuracion.setDescripcionUsuario(e.getDescripcionUsuario());
		configuracion.setIdUsuarioCambio(e.getIdUsuarioCambio());
		configuracion.setFechaCreacion(e.getFechaCreacion());
		configuracion.setFechaActualizacion(e.getFechaActualizacion());
		configuracion.setActivo(e.getActivo());
		configuracion.setSeccionSincronizada(e.getSeccionSincronizada());
		em.merge(configuracion);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void guardar(ConfiguracionCatalogoDTO dto) {
        ConfiguracionCatalogo entity = new ConfiguracionCatalogo();
        entity.setIdConfiguracionCatalogo(dto.getIdConfiguracionCatalogo());
        entity.setProyecto(em.getReference(Proyecto.class, dto.getIdProyecto()));
        entity.setCatalogo(em.getReference(Catalogos.class, dto.getIdCatalogo()));
        entity.setOpcionesCatalogo(em.getReference(OpcionesCatalogo.class, dto.getIdOpcionCatalogo()));
        entity.setDescripcionUsuario(dto.getDescripcionUsuario());
        entity.setIdUsuarioCambio(dto.getIdUsuarioCambio());
        entity.setFechaCreacion(dto.getFechaCreacion());
        entity.setFechaActualizacion(dto.getFechaActualizacion());
        entity.setActivo(dto.getActivo());
        entity.setSeccionSincronizada(dto.getSeccionSincronizada());
        
        em.persist(entity);
        em.flush();
        
        //dto.setIdConfiguracionCatalogo(entity.getIdConfiguracionCatalogo());
    }
    
    
    public List<ConfiguracionCatalogoDTO> buscarPorProyectoYSincronizacion(Long idProyecto, Boolean seccionSincronizada) {
        try {
            return em.createNamedQuery("ConfiguracionCatalogo.findByProyectoAndSincronizacion", ConfiguracionCatalogoDTO.class)
                    .setParameter("idProyecto", idProyecto)
                    .setParameter("seccionSincronizada", seccionSincronizada)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    public List<ConfiguracionCatalogoDTO> buscarPorProyecto(Long idProyecto) {
        try {
            return em.createNamedQuery("ConfiguracionCatalogo.findByProyecto", ConfiguracionCatalogoDTO.class)
                    .setParameter("idProyecto", idProyecto)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void actualizarEstadoSincronizacion(Long idConfiguracion, Boolean seccionSincronizada) {
        try {
            ConfiguracionCatalogo entity = em.find(ConfiguracionCatalogo.class, idConfiguracion);
            if (entity != null) {
                entity.setSeccionSincronizada(seccionSincronizada);
                entity.setFechaActualizacion(new Date());
                em.merge(entity);
            } 
        } catch (Exception e) {
            //LOGGER.error("Error al actualizar estado de sincronización para configuración {}", idConfiguracion, e);
            throw new RuntimeException("Error al actualizar estado de sincronización", e);
        }
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void actualizarEstadoSincronizacionPorProyecto(Long idProyecto, Boolean seccionSincronizada) {
        try {
            List<ConfiguracionCatalogoDTO> configuraciones = buscarPorProyecto(idProyecto);
            
            for (ConfiguracionCatalogoDTO config : configuraciones) {
                if (config != null && config.getIdConfiguracionCatalogo() != null) {
                    ConfiguracionCatalogo entity = em.find(ConfiguracionCatalogo.class, config.getIdConfiguracionCatalogo());
                    if (entity != null) {
                        entity.setSeccionSincronizada(seccionSincronizada);
                        entity.setFechaActualizacion(new Date());
                        em.merge(entity);
                    }
                }
            }
            
            
        } catch (Exception e) {
           
            throw new RuntimeException("Error al actualizar estado de sincronización masivo", e);
        }
    }
    
    /**
     * Busca configuraciones no sincronizadas por proyecto
     */
    public List<ConfiguracionCatalogoDTO> buscarConfiguracionesNoSincronizadas(Long idProyecto) {
        return buscarPorProyectoYSincronizacion(idProyecto, false);
    }
    
    /**
     * Busca configuraciones sincronizadas por proyecto
     */
    public List<ConfiguracionCatalogoDTO> buscarConfiguracionesSincronizadas(Long idProyecto) {
        return buscarPorProyectoYSincronizacion(idProyecto, true);
    }
    
    /**
     * Verifica si existe al menos una configuración no sincronizada para un proyecto
     */
    public boolean existeConfiguracionNoSincronizada(Long idProyecto) {
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(c) FROM ConfiguracionCatalogo c " +
                    "WHERE c.proyecto.idProyecto = :idProyecto " +
                    "AND c.seccionSincronizada = false " +
                    "AND c.activo = true", Long.class)
                    .setParameter("idProyecto", idProyecto)
                    .getSingleResult();
            
            return count != null && count > 0;
        } catch (Exception e) {
           
            return false;
        }
    }

    @Override
    public ConfiguracionCatalogoDTO buscarPorId(Long id) {
        ConfiguracionCatalogo entity = em.find(ConfiguracionCatalogo.class, id);
        return convertirADTO(entity);
    }
    
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void actualizarDescripcionPersonalizadaSiExiste(Long idProyecto, Integer idCatalogo, Integer idOpcionCatalogo) {
        try {
            ConfiguracionCatalogoDTO config = findByProyectoCatalogoAndOpcionCatalogo(idProyecto, idCatalogo, idOpcionCatalogo);
            
            if (config != null) {
                if (Boolean.FALSE.equals(config.getActivo())) {
                    // Si está inactivo, establecer descripción_personalizada a NULL en cat_estatus_tramite
                    em.createNativeQuery(
                        "UPDATE motor_interprete.cat_estatus_tramite " +
                        "SET descripcion_personalizada = NULL " +
                        "WHERE id_estatus_tramite = :idEstatusTramite")
                        .setParameter("idEstatusTramite", idOpcionCatalogo)
                        .executeUpdate();
                    
                } else if (config.getDescripcionUsuario() != null && !config.getDescripcionUsuario().trim().isEmpty()) {
                    // Si está activo y tiene descripción, actualizar cat_estatus_tramite
                    em.createNativeQuery(
                        "UPDATE motor_interprete.cat_estatus_tramite " +
                        "SET descripcion_personalizada = :descripcion " +
                        "WHERE id_estatus_tramite = :idEstatusTramite")
                        .setParameter("descripcion", config.getDescripcionUsuario())
                        .setParameter("idEstatusTramite", idOpcionCatalogo)
                        .executeUpdate();                       
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar descripción personalizada", e);
        }
    }



    private ConfiguracionCatalogoDTO convertirADTO(ConfiguracionCatalogo entity) {
        if (entity == null) return null;
        
        ConfiguracionCatalogoDTO dto = new ConfiguracionCatalogoDTO();
        dto.setIdConfiguracionCatalogo(entity.getIdConfiguracionCatalogo());
        dto.setIdProyecto(entity.getProyecto().getIdProyecto());
        dto.setIdCatalogo(entity.getCatalogos().getIdCatalogo());
        dto.setIdOpcionCatalogo(entity.getOpcionCatalogo().getIdOpcionCatalogo());
        dto.setDescripcionUsuario(entity.getDescripcionUsuario());
        dto.setIdUsuarioCambio(entity.getIdUsuarioCambio());
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaActualizacion(entity.getFechaActualizacion());
        dto.setActivo(entity.getActivo());
        dto.setSeccionSincronizada(entity.getSeccionSincronizada());
        
        return dto;
    }
    

}
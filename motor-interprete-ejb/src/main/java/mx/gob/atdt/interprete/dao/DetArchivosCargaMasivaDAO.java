package mx.gob.atdt.interprete.dao;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetArchivosCargaMasivaDTO;
import mx.gob.atdt.interprete.model.CatEstatusCargaMasiva;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.DetArchivosCargaMasiva;
import mx.gob.atdt.interprete.model.Usuario;

@LocalBean
@Stateless
public class DetArchivosCargaMasivaDAO extends IBaseService<DetArchivosCargaMasivaDTO, Long> {

	@Override
	public DetArchivosCargaMasivaDTO buscarPorId(Long id) {
		List<DetArchivosCargaMasivaDTO> resultados = em
				.createNamedQuery("DetArchivosCargaMasiva.findById", DetArchivosCargaMasivaDTO.class)
				.setParameter("idArchivoCargaMasiva", id)
				.getResultList();
		return BeanUtils.isNotEmpty(resultados) ? resultados.get(0) : null;
	}

	@Override
	public void actualizar(DetArchivosCargaMasivaDTO e) {
		actualizarResultadoProceso(e);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Long guardar(DetArchivosCargaMasivaDTO e) {
		DetArchivosCargaMasiva entity = new DetArchivosCargaMasiva();
		entity.setFechaCarga(e.getFechaCarga());
		entity.setUsuarioCarga(em.getReference(Usuario.class, e.getUsuarioCargaDTO().getIdUsuarioLlaveCdmx()));
		entity.setEstatusCarga(em.getReference(CatEstatusCargaMasiva.class, e.getEstatusCargaDTO().getIdEstatusCarga()));
		entity.setComponente(em.getReference(Componente.class, e.getIdComponente()));
		entity.setUsuarioAsignado(em.getReference(Usuario.class, e.getUsuarioAsignadoDTO().getIdUsuarioLlaveCdmx()));
		entity.setNombreArchivoOrigen(e.getNombreArchivoOrigen());
		entity.setMensajeError(e.getMensajeError());
		em.persist(entity);
		em.flush();
		return entity.getIdArchivoCargaMasiva();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarResultadoProceso(DetArchivosCargaMasivaDTO e) {
		DetArchivosCargaMasiva entity = em.getReference(DetArchivosCargaMasiva.class, e.getIdArchivoCargaMasiva());
		entity.setEstatusCarga(em.getReference(CatEstatusCargaMasiva.class, e.getEstatusCargaDTO().getIdEstatusCarga()));
		entity.setMensajeError(e.getMensajeError());
		em.merge(entity);
	}

	/**
     * Obtiene todas las cargas de un componente, ordenadas por fecha descendente.
     * Utiliza la consulta nombrada definida en la entidad.
     *
     * @param idComponente ID del componente
     * @return Lista de DTOs con las cargas, o lista vacía si no hay.
     */
	public List<DetArchivosCargaMasivaDTO> buscarPorComponente(Long idComponente) {
		List<DetArchivosCargaMasivaDTO> resultados = em
				.createNamedQuery("DetArchivosCargaMasiva.findByIdComponente", DetArchivosCargaMasivaDTO.class)
				.setParameter("idComponente", idComponente)
				.getResultList();
		return BeanUtils.isNotEmpty(resultados) ? resultados : null;
	}
    
    public List<DetArchivosCargaMasivaDTO> buscarTodos() {
        return em.createNamedQuery("DetArchivosCargaMasiva.findAll", DetArchivosCargaMasivaDTO.class)
                .getResultList();
    }
    
    /**
     * Busca cargas aplicando filtros dinámicos.
     * 
     * @param fechaDesde Fecha de inicio (puede ser null)
     * @param fechaHasta Fecha de fin (puede ser null)
     * @param idEstatus  ID del estatus (puede ser null)
     * @return Lista de DTOs con las cargas que cumplen los filtros
     */
    public List<DetArchivosCargaMasivaDTO> buscarConFiltros(
            Date fechaDesde, Date fechaHasta, Integer idEstatus,
            String usuarioCargaFiltro, String usuarioAsignadoFiltro) {
        
        StringBuilder jpql = new StringBuilder(
            "SELECT NEW mx.gob.atdt.interprete.dto.DetArchivosCargaMasivaDTO("
            + "a.idArchivoCargaMasiva, a.fechaCarga, "
            + "e.idEstatusCarga, e.descripcion, "
            + "uc.idUsuarioLlaveCdmx, uc.correo, uc.nombre, uc.primerApellido, uc.segundoApellido,"
            + "c.idComponente, "
            + "ua.idUsuarioLlaveCdmx, ua.correo, ua.nombre, ua.primerApellido, ua.segundoApellido,"
            + "a.nombreArchivoOrigen, a.mensajeError) "
            + "FROM DetArchivosCargaMasiva a "
            + "LEFT JOIN a.estatusCarga e "
            + "LEFT JOIN a.usuarioCarga uc "
            + "LEFT JOIN a.usuarioAsignado ua "
            + "LEFT JOIN a.componente c "
            + "WHERE 1=1 "
        );
        
        Map<String, Object> params = new HashMap<>();
        
        if (fechaDesde != null) {
            jpql.append("AND a.fechaCarga >= :fechaDesde ");
            params.put("fechaDesde", fechaDesde);
        }
        
        if (fechaHasta != null) {
            jpql.append("AND a.fechaCarga <= :fechaHasta ");
            params.put("fechaHasta", fechaHasta);
        }
        
        if (idEstatus != null) {
            jpql.append("AND e.idEstatusCarga = :idEstatus ");
            params.put("idEstatus", idEstatus);
        }
        
        if (usuarioCargaFiltro != null && !usuarioCargaFiltro.trim().isEmpty()) {
            String pattern = "%" + usuarioCargaFiltro.trim().toLowerCase() + "%";
            jpql.append("AND LOWER(CONCAT(uc.nombre, ' ', uc.primerApellido, ' ', uc.segundoApellido)) LIKE :usuarioCargaPattern ");
            params.put("usuarioCargaPattern", pattern);
        }

        if (usuarioAsignadoFiltro != null && !usuarioAsignadoFiltro.trim().isEmpty()) {
            String pattern = "%" + usuarioAsignadoFiltro.trim().toLowerCase() + "%";
            jpql.append("AND LOWER(CONCAT(ua.nombre, ' ', ua.primerApellido, ' ', ua.segundoApellido)) LIKE :usuarioAsignadoPattern ");
            params.put("usuarioAsignadoPattern", pattern);
        }
        
        jpql.append("ORDER BY a.fechaCarga DESC");
        
        TypedQuery<DetArchivosCargaMasivaDTO> query = em.createQuery(jpql.toString(), DetArchivosCargaMasivaDTO.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        
        return query.getResultList();
    }
}
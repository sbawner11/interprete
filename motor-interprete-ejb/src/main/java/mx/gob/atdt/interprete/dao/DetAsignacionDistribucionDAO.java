package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.TypedQuery;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetAsignacionDistribucionDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.DetAsignacionDistribucion;
import mx.gob.atdt.interprete.model.Usuario;

@LocalBean
@Stateless
public class DetAsignacionDistribucionDAO extends IBaseService<DetAsignacionDistribucionDTO, Long>{
	
	private static final String ID_ELEMENTO_ASIGANDO = "idElementoAsignado";
	private static final String ID_USUARIO_ASIGNADO = "idUsuarioAsignado";
	private static final String ID_COMPONENTE = "idComponente";
	
	/**
	 * Generacion de jpql dinamico para consultas por lazyLoad
	 * 
	 * @param namedQuery
	 * @param campoOrden
	 * @param direccionOrden
	 * @return TypedQuery cadena con sql dinamico
	 * @author Ramiro Luna Torres
	 */
	private TypedQuery<DetAsignacionDistribucionDTO> jpqlDinamicoEntity(String namedQuery, String campoOrden, String direccionOrden){
		String jpqlBase = em.createNamedQuery(namedQuery)
				.unwrap(org.hibernate.Query.class)
				.getQueryString();
		
		StringBuilder jpqlDinamico = new StringBuilder(jpqlBase);
		if (campoOrden != null && !campoOrden.isEmpty()) {
			jpqlDinamico.append(" ORDER BY ").append(campoOrden).append(" ").append(direccionOrden);
		}

		return em.createQuery(jpqlDinamico.toString(), DetAsignacionDistribucionDTO.class);
	}

	@Override
	public DetAsignacionDistribucionDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(DetAsignacionDistribucionDTO e) {
		// TODO Auto-generated method stub
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardar(DetAsignacionDistribucionDTO e) {
		DetAsignacionDistribucion asignacion = new DetAsignacionDistribucion();
		asignacion.setIdElementoAsignado(e.getIdElementoAsignado());
		asignacion.setDesElementoAsignado(e.getDesElementoAsignado());
		asignacion.setRol(e.getRol());
		asignacion.setActivo(e.isActivo());
		asignacion.setFechaAsignacion(e.getFechaAsignacion());
		asignacion.setComponente(em.getReference(Componente.class, e.getComponenteDistribucionDTO().getIdComponente()));
		asignacion.setUsuarioAsignado(em.getReference(Usuario.class, e.getUsuarioAsignadoDTO().getIdUsuarioLlaveCdmx()));
		asignacion.setAdministradorAsigna(em.getReference(Usuario.class, e.getAdministradorAsignaDTO().getIdUsuarioLlaveCdmx()));
		
		em.persist(asignacion);
		em.flush();
	}
	
	public List<DetAsignacionDistribucionDTO> buscarPorIdUsuarioAsignado(Long idUsuarioAsignado) {
		List<DetAsignacionDistribucionDTO> lstResultados = 
				em.createNamedQuery("DetAsignacionDistribucion.findByIdUsuarioAsignado", DetAsignacionDistribucionDTO.class)
				.setParameter(ID_USUARIO_ASIGNADO, idUsuarioAsignado)
				.getResultList();
		return BeanUtils.isNotEmpty(lstResultados) ? lstResultados : null;
	}
	
	public List<DetAsignacionDistribucionDTO> buscarPorIdUsuarioAsignadoAndComponente(Long idUsuarioAsignado, Long idComponente) {
		List<DetAsignacionDistribucionDTO> lstResultados = 
				em.createNamedQuery("DetAsignacionDistribucion.findByIdUsuarioAsignadoAndComponente", DetAsignacionDistribucionDTO.class)
				.setParameter(ID_USUARIO_ASIGNADO, idUsuarioAsignado)
				.setParameter(ID_COMPONENTE, idComponente)
				.getResultList();
		return BeanUtils.isNotEmpty(lstResultados) ? lstResultados : null;
	}
	
	public List<DetAsignacionDistribucionDTO> buscarTodosActivos(){
		List<DetAsignacionDistribucionDTO> lstResultados = 
				em.createNamedQuery("DetAsignacionDistribucion.findAllActivos", DetAsignacionDistribucionDTO.class)
				.getResultList();
		return BeanUtils.isNotEmpty(lstResultados) ? lstResultados : null;
	}
	
	public List<DetAsignacionDistribucionDTO> buscarTodosActivosPorComponente(Long idComponente, int first, int pageSize,
			String campoOrden, String direccionOrden){
		TypedQuery<DetAsignacionDistribucionDTO> query = jpqlDinamicoEntity("DetAsignacionDistribucion.findAllActivosByComponentes", campoOrden, direccionOrden)
				.setParameter(ID_COMPONENTE, idComponente)
				.setFirstResult(first)
				.setMaxResults(pageSize);

		if (pageSize > 0) {
			query.setMaxResults(pageSize);
		}

		List<DetAsignacionDistribucionDTO> lstResultados = query.getResultList();
		return BeanUtils.isNotEmpty(lstResultados) ? lstResultados : null;
	}
	
	/**
	 * Metodo para contar el total de registros activos por componente para distribucion
	 * @param idComponente
	 * @return
	 */
	public int contarTodosActivosPorComponente(Long idComponente) {
		Object resultado = em.createNamedQuery("DetAsignacionDistribucion.findAllActivosByComponentesCount")
	            .setParameter(ID_COMPONENTE, idComponente)
	            .getSingleResult();
	    if (resultado instanceof Number) {
	    	return ((Number) resultado).intValue();
	    }
	    return 0;
	}
	
	public boolean existeUsuarioAsignadoAElemento(Long idUsuarioAsignado, Long idElementoAsignado) {
		List<DetAsignacionDistribucionDTO> lstResultados = 
				em.createNamedQuery("DetAsignacionDistribucion.findByIdUsuarioAsignadoAndIdElementoAsignado", DetAsignacionDistribucionDTO.class)
				.setParameter(ID_USUARIO_ASIGNADO, idUsuarioAsignado)
				.setParameter(ID_ELEMENTO_ASIGANDO, idElementoAsignado)
				.getResultList();
		return BeanUtils.isNotEmpty(lstResultados);
	}

	public boolean existeElementoAsignadoEnComponenteYRol(Long idElementoAsignado, Long idComponente, String rol) {
		List<DetAsignacionDistribucionDTO> lstResultados =
				em.createNamedQuery("DetAsignacionDistribucion.findByIdElementoAsignadoAndComponenteAndRol", DetAsignacionDistribucionDTO.class)
				.setParameter("idElementoAsignado", idElementoAsignado)
				.setParameter("idComponente", idComponente)
				.setParameter("rol", rol)
				.getResultList();
		return BeanUtils.isNotEmpty(lstResultados);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarActivoAsignacion(DetAsignacionDistribucionDTO asignacionDTO) {
		DetAsignacionDistribucion asignacion = em.getReference(DetAsignacionDistribucion.class, asignacionDTO.getIdAsignacionDistribucion());
		asignacion.setActivo(asignacionDTO.isActivo());
		asignacion.setFechaDesvinculacion(asignacionDTO.getFechaDesvinculacion());
		em.merge(asignacion);
		
	}
	
	/**
	 * Metodo para devolver la lista de distribucion de acuerdo con el filtro
	 * @param idComponente
	 * @param idElementoAsignado
	 * @param idUsuarioAsignado
	 * @param rol
	 * @param first
	 * @param pageSize
	 * @param campoOrden
	 * @param direccionOrden
	 * @return Lista de asignacionaciones
	 * @author Ramiro Luna Torres
	 */
	@SuppressWarnings({"java:S107"})
	public List<DetAsignacionDistribucionDTO> buscarTodosActivosPorIdElementoAndIdUsuarioAsignadoAndRol(Long idComponente,Long idElementoAsignado,
			Long idUsuarioAsignado, String rol, int first, int pageSize, String campoOrden, String direccionOrden){
		TypedQuery<DetAsignacionDistribucionDTO> query = jpqlDinamicoEntity("DetAsignacionDistribucion.findByAllActiveIdElementoAndIdUsuarioAsignadoAndRol", campoOrden, direccionOrden)
				.setParameter(ID_COMPONENTE, idComponente)
				.setParameter(ID_ELEMENTO_ASIGANDO, idElementoAsignado)
				.setParameter(ID_USUARIO_ASIGNADO, idUsuarioAsignado)
				.setParameter("rol", rol)
				.setFirstResult(first);
		if (pageSize > 0) {
			query.setMaxResults(pageSize);
		}
		List<DetAsignacionDistribucionDTO> lstResultados = query.getResultList();
		return BeanUtils.isNotEmpty(lstResultados) ? lstResultados : null;
	}
	
	/**
	 * Metodo para contar los registros activos por filtro
	 * @param idComponente
	 * @param idElementoAsignado
	 * @param idUsuarioAsignado
	 * @param rol
	 * @return total de registros
	 * @author Ramiro Luna Torres
	 */
	public int contarTodosActivosPorIdElementoAndIdUsuarioAsignadoAndRol(Long idComponente, Long idElementoAsignado, Long idUsuarioAsignado, String rol) {
		Object resultado = em.createNamedQuery("DetAsignacionDistribucion.findByAllActiveIdElementoAndIdUsuarioAsignadoAndRolCount")
				.setParameter(ID_COMPONENTE, idComponente)
				.setParameter(ID_ELEMENTO_ASIGANDO, idElementoAsignado)
				.setParameter(ID_USUARIO_ASIGNADO, idUsuarioAsignado)
				.setParameter("rol", rol)
	            .getSingleResult();
	    if (resultado instanceof Number) {
	    	return ((Number) resultado).intValue();
	    }
	    return 0;
	}

}

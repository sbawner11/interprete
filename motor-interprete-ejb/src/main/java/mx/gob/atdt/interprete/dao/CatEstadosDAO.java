package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.commons.dto.CatEstadosDTO;

@LocalBean
@Stateless
public class CatEstadosDAO extends IBaseService<CatEstadosDTO, Integer>{

	@Override
	public CatEstadosDTO buscarPorId(Integer id) {
		try {
			return em.createNamedQuery("CatEstados.buscarPorIdEstado", CatEstadosDTO.class)
				.setParameter("idEstado", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void actualizar(CatEstadosDTO e) {
		// Método no necesario para implementación		
	}	

	/**
	 * Método que realiza la búsqueda de estado por el IdEstado
	 * @param idEstado
	 * @return
	 */
	public List<CatEstadosDTO> buscarPorIdEstado(int idEstado){
		List<CatEstadosDTO> lstEstados = em.createNamedQuery("CatEstados.buscarPorIdEstado", CatEstadosDTO.class)
				.setParameter("idEstado", idEstado)				
				.getResultList();
		return !lstEstados.isEmpty() ? lstEstados : null;
	}
	
	/**
	 * Método que realiza la consulta de estados.
	 * @return
	 */	
	public List<CatEstadosDTO> buscarTodos(){
		List<CatEstadosDTO> lstEstados = em.createNamedQuery("CatEstados.buscarTodos", CatEstadosDTO.class)
				.getResultList();
		return !lstEstados.isEmpty() ? lstEstados : null;
	}	

}

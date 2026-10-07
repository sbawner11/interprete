package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.commons.dto.CatMunicipiosDTO;

@LocalBean
@Stateless
public class CatMunicipiosDAO extends IBaseService<CatMunicipiosDTO, Integer>{

	@Override
	public CatMunicipiosDTO buscarPorId(Integer id) {
		try {
			return em.createNamedQuery("CatMunicipios.buscarPorIdMunicipio", CatMunicipiosDTO.class)
				.setParameter("idMunicipio", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void actualizar(CatMunicipiosDTO e) {
		// TODO Auto-generated method stub
		
	}
	
	public List<CatMunicipiosDTO>buscarTodosPorEstado(int idEstado){
		List<CatMunicipiosDTO> lstMunicipios = em.createNamedQuery("CatMunicipios.buscarTodosByEstado", CatMunicipiosDTO.class)
				.setParameter("idEstado", idEstado)				
				.getResultList();
		return BeanUtils.isNotEmpty(lstMunicipios) ? lstMunicipios : null;
	}

	public List<CatMunicipiosDTO> buscarPorIdMunicipio(int idMunicipio){
		List<CatMunicipiosDTO> lstMunicipios = em.createNamedQuery("CatMunicipios.buscarPorIdMunicipio", CatMunicipiosDTO.class)
				.setParameter("idMunicipio", idMunicipio)				
				.getResultList();
		return lstMunicipios.size() > 0 ? lstMunicipios : null;
	}
	
	public List<CatMunicipiosDTO> buscarTodos(){
		List<CatMunicipiosDTO> lstMunicipios = em.createNamedQuery("CatMunicipios.buscarTodos", CatMunicipiosDTO.class)
				.getResultList();
		return lstMunicipios.size() > 0 ? lstMunicipios : null;
	}
	

}

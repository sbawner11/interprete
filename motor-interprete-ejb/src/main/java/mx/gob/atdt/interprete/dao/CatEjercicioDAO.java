package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatEjercicioDTO;

@Stateless
@LocalBean
public class CatEjercicioDAO extends IBaseService<CatEjercicioDTO, Integer> {

	@Override
    public CatEjercicioDTO buscarPorId(Integer id) {
		try {
			return em.createNamedQuery("CatEjercicio.findById", CatEjercicioDTO.class)
				.setParameter("idEjercicio", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
    }

	@Override
	public void actualizar(CatEjercicioDTO e) {
		// Implementación no necesaria		
	}

	public List<CatEjercicioDTO> buscarActivos() {
		return em.createNamedQuery("CatEjercicio.findActivos", 
				CatEjercicioDTO.class).getResultList();
	}

}

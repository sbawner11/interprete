package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatOperadorDTO;

@Stateless
@LocalBean
public class CatOperadorDAO extends IBaseService<CatOperadorDTO, Integer> {

	@Override
	public CatOperadorDTO buscarPorId(Integer id) {
		try {
			return em.createNamedQuery("CatOperador.findById", 
					CatOperadorDTO.class)
					.setParameter("idOperador", id)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;			
		}
	}

	public List<CatOperadorDTO> buscarTodos() {
		return em.createNamedQuery("CatOperador.findAll", CatOperadorDTO.class).getResultList();
	}

	@Override
	public void actualizar(CatOperadorDTO e) {
		//metodo vacio para posterior actualización
	}

}

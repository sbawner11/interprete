package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatPeriodoDTO;

@Stateless
@LocalBean
public class CatPeriodoDAO extends IBaseService<CatPeriodoDTO, Integer> {

	@Override
	public CatPeriodoDTO buscarPorId(Integer id) {
		List<CatPeriodoDTO> listado = em.createNamedQuery("CatPeriodo.findById", CatPeriodoDTO.class)
				.setParameter("idPeriodo", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

	@Override
	public void actualizar(CatPeriodoDTO e) {
		// Implementación no necesaria		
	}
}

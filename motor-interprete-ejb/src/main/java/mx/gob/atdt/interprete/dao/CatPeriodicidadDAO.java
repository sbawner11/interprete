package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatPeriodicidadDTO;

@Stateless
@LocalBean
public class CatPeriodicidadDAO extends IBaseService<CatPeriodicidadDTO, Integer> {

	@Override
	public CatPeriodicidadDTO buscarPorId(Integer id) {
		List<CatPeriodicidadDTO> listado = em.createNamedQuery("CatPeriodicidad.findById", CatPeriodicidadDTO.class)
				.setParameter("idPeriodicidad", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;		
	}

	@Override
	public void actualizar(CatPeriodicidadDTO e) {
		// Implementación no necesaria		
	}	
}

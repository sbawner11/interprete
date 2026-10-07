package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatTipoAgrupadorDTO;

@Stateless
@LocalBean
public class CatTipoAgrupadorDAO extends IBaseService<CatTipoAgrupadorDTO, Integer> {
	
	@Override
	public CatTipoAgrupadorDTO buscarPorId(Integer id) {
		List<CatTipoAgrupadorDTO> listado = em.createNamedQuery("CatTipoAgrupador.findById", CatTipoAgrupadorDTO.class)
				.setParameter("idTipoAgrupador", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

	@Override
	public void actualizar(CatTipoAgrupadorDTO e) {
		// Implementación no necesaria
	}
}

package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatTipoCostoDTO;

@Stateless
@LocalBean
public class CatTipoCostoDAO extends IBaseService<CatTipoCostoDTO, Long> {

	@Override
	public CatTipoCostoDTO buscarPorId(Long id) {
		return null;
	}

	@Override
	public void actualizar(CatTipoCostoDTO e) {
		// TODO Auto-generated method stub		
	}

	public CatTipoCostoDTO buscarPorIdTipoCosto(Integer idTipoCosto) {
		List<CatTipoCostoDTO> listado = em.createNamedQuery("CatTipoCosto.findById", CatTipoCostoDTO.class)
				.setParameter("idTipoCosto", idTipoCosto).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

}

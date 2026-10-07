package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatEstatusLineaCapturaDTO;

@Stateless
@LocalBean
public class CatEstatusLineaCapturaDAO extends IBaseService<CatEstatusLineaCapturaDTO, Integer>{

	@Override
	public CatEstatusLineaCapturaDTO buscarPorId(Integer id) {
		try {
			return em.createNamedQuery("CatEstatusLineaCaptura.findById", CatEstatusLineaCapturaDTO.class)
				.setParameter("idEstatusLineaCaptura", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<CatEstatusLineaCapturaDTO> buscarTodos() {
	    return em.createNamedQuery("CatEstatusLineaCaptura.findAll", CatEstatusLineaCapturaDTO.class)
	             .getResultList();
	}

	@Override
	public void actualizar(CatEstatusLineaCapturaDTO e) {
		//pendiente
		
	}

}

package mx.gob.atdt.interprete.dao;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatEstatusTramiteDTO;

import java.util.List;
import javax.persistence.NoResultException;

@Stateless
@LocalBean
public class CatEstatusTramiteDAO extends IBaseService<CatEstatusTramiteDTO, Long> {

	@Override
	public CatEstatusTramiteDTO buscarPorId(Long id) { 
		try {
			return em.createNamedQuery("CatEstatusTramite.findById", CatEstatusTramiteDTO.class)
				.setParameter("idEstatusTramite", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<CatEstatusTramiteDTO> buscarTodos() {
	    return em.createNamedQuery("CatEstatusTramite.findAll", CatEstatusTramiteDTO.class)
	             .getResultList();
	}

	public CatEstatusTramiteDTO buscarPorIdEstatus(Integer id) { 
		try {
			return em.createNamedQuery("CatEstatusTramite.findById", CatEstatusTramiteDTO.class)
				.setParameter("idEstatusTramite", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


	@Override
	public void actualizar(CatEstatusTramiteDTO e) {
		//pendiente
	}
	

}
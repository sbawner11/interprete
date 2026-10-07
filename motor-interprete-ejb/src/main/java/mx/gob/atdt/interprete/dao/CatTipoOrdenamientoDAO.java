package mx.gob.atdt.interprete.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatTipoOrdenamientoDTO;

@Stateless
@LocalBean
public class CatTipoOrdenamientoDAO extends IBaseService<CatTipoOrdenamientoDTO, Long>{

	@Override
	public CatTipoOrdenamientoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CatTipoOrdenamientoDTO> buscarTodos() {
		List<CatTipoOrdenamientoDTO> resultados =
				em.createNamedQuery("CatTipoOrdenamiento.findActivos", 
						CatTipoOrdenamientoDTO.class).getResultList();
        return resultados == null ? new ArrayList<>() : resultados;
	}

	@Override
	public void actualizar(CatTipoOrdenamientoDTO e) {
		// TODO Auto-generated method stub
		
	}

}

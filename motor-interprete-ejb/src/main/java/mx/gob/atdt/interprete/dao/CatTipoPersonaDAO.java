package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatTipoPersonaDTO;

@Stateless
@LocalBean
public class CatTipoPersonaDAO extends IBaseService<CatTipoPersonaDTO, Integer> {
	
	@Override
	public CatTipoPersonaDTO buscarPorId(Integer id) {
		return null;
	}	

	@Override
	public void actualizar(CatTipoPersonaDTO e) {
		// Implementación no necesaria		
	}
	
	/**
	 * Método auxiliar que realiza la búsqueda por idTipoPersona
	 * @param idTipoPersona
	 * @return
	 */
	public CatTipoPersonaDTO buscarPorIdTipoPersona(Integer idTipoPersona) {
		List<CatTipoPersonaDTO> listado = em.createNamedQuery("CatTipoPersona.findById", CatTipoPersonaDTO.class)
				.setParameter("idTipoPersona", idTipoPersona).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
}

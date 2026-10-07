package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatTipoVigenciaDTO;

@Stateless
@LocalBean
public class CatTipoVigenciaDAO extends IBaseService<CatTipoVigenciaDTO, Integer> {

	@Override
	public CatTipoVigenciaDTO buscarPorId(Integer id) {		
		return null;
	}

	@Override
	public void actualizar(CatTipoVigenciaDTO e) {
		// Implementación no necesaria		
	}
	
	/**
	 * Método auxiliar que realiza la búsqueda por idTipoVigencia
	 * @param idTipoVigencia
	 * @return
	 */
	public CatTipoVigenciaDTO buscarPorIdTipoVigencia(Integer idTipoVigencia) {
		List<CatTipoVigenciaDTO> listado = em.createNamedQuery("CatTipoVigencia.findById", CatTipoVigenciaDTO.class)
				.setParameter("idTipoVigencia", idTipoVigencia).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
}

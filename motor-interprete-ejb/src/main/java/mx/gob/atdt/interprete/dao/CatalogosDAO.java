package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatalogosDTO;
import mx.gob.atdt.interprete.model.Catalogos;


@Stateless
@LocalBean
public class CatalogosDAO extends IBaseService<CatalogosDTO, Integer> {

    public List<CatalogosDTO> buscarTodos() {
        return em.createNamedQuery("Catalogos.findAll", CatalogosDTO.class)
                .getResultList();
    }

    @Override
    public CatalogosDTO buscarPorId(Integer id) {
        Catalogos entity = em.find(Catalogos.class, id);
        return convertirADTO(entity);
    }

    private CatalogosDTO convertirADTO(Catalogos entity) {
        if (entity == null) return null;
        
        return new CatalogosDTO(
            entity.getIdCatalogo(),
            entity.getNombre(),
            entity.getComentarios()
        );
    }

	@Override
	public void actualizar(CatalogosDTO e) {	
	}


}
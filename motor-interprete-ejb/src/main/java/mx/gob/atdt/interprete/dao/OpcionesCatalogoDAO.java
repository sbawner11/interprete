package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.OpcionesCatalogoDTO;
import mx.gob.atdt.interprete.model.OpcionesCatalogo;

@Stateless
@LocalBean
public class OpcionesCatalogoDAO extends IBaseService<OpcionesCatalogoDTO, Integer> {

    public List<OpcionesCatalogoDTO> findByCatalogo(Integer idCatalogo) {
        return em.createNamedQuery("OpcionesCatalogo.findByCatalogo", OpcionesCatalogoDTO.class)
                .setParameter("idCatalogo", idCatalogo)
                .getResultList();
    }

    @Override
    public OpcionesCatalogoDTO buscarPorId(Integer id) {
        OpcionesCatalogo entity = em.find(OpcionesCatalogo.class, id);
        return convertirADTO(entity);
    }

    private OpcionesCatalogoDTO convertirADTO(OpcionesCatalogo entity) {
        if (entity == null) return null;
        
        return new OpcionesCatalogoDTO(
            entity.getIdOpcionCatalogo(),
            entity.getCatalogos().getIdCatalogo(),
            entity.getDescripcionOpcion()
        );
    }

	@Override
	public void actualizar(OpcionesCatalogoDTO e) {
		
	}
}
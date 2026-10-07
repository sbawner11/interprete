package mx.gob.atdt.interprete.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatTipoCampoDTO;
import mx.gob.atdt.interprete.model.CatTipoCampo;

@Stateless
@LocalBean
public class CatTipoCampoDAO extends IBaseService<CatTipoCampoDTO, Integer> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public CatTipoCampoDTO buscarPorId(Integer id) {

        CatTipoCampo entity = em.find(CatTipoCampo.class, id);

        if (entity == null) return null;

        CatTipoCampoDTO dto = new CatTipoCampoDTO();
        dto.setId(entity.getId());
        dto.setDescripcion(entity.getDescripcion());
        dto.setActivo(entity.getActivo());

        return dto;
    }

    @Override
    public void actualizar(CatTipoCampoDTO e) {
        throw new UnsupportedOperationException("Catálogo no editable");
    }

}
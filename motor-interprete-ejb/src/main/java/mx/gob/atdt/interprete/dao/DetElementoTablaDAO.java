package mx.gob.atdt.interprete.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatTipoCampoDTO;
import mx.gob.atdt.interprete.dto.DetElementoTablaDTO;
import mx.gob.atdt.interprete.model.CatTipoCampo;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.DetElementoTabla;
import mx.gob.atdt.interprete.model.ComponenteTabla;


@Stateless
@LocalBean
public class DetElementoTablaDAO extends IBaseService<DetElementoTablaDTO, Long> {

    @Override
    public DetElementoTablaDTO buscarPorId(Long id) {
        DetElementoTabla entity = em.find(DetElementoTabla.class, id);
        if (entity == null) return null;
        return toDTO(entity);
    }

	public List<DetElementoTablaDTO> buscarPorIdComponente(Long id) {
		List<DetElementoTablaDTO> listado = em.createNamedQuery("DetElementoTabla.findByIdComponenteTabla", DetElementoTablaDTO.class)
				.setParameter("idComponenteTabla", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}

    @Override
    public void actualizar(DetElementoTablaDTO dto) {
    	DetElementoTabla elemento = new DetElementoTabla();
    	
    	elemento.setId(dto.getId());
    	elemento.setTituloHeader(dto.getTituloHeader());
    	elemento.setComponenteTabla(em.getReference(ComponenteTabla.class, dto.getIdComponenteTabla()));
    	elemento.setTipoCampo(em.getReference(CatTipoCampo.class, dto.getCatTipoCampoDTO().getId()));
    	elemento.setRequerido(dto.isRequerido());
    	elemento.setTooltip(dto.getTooltip());
    	elemento.setLongitudCelda(dto.getLongitudCelda());
    	elemento.setOrdenColumna(dto.getOrdenColumna());
    	elemento.setActivo(dto.isActivo());

    	em.merge(elemento);
    }

    // =====================
    // MAPPERS
    // =====================
    
    private DetElementoTabla mapColumnaToEntity(
            DetElementoTablaDTO colDTO) {

        DetElementoTabla entity = new DetElementoTabla();

        entity.setId(colDTO.getId());
        entity.setTituloHeader(colDTO.getTituloHeader());
        entity.setRequerido(colDTO.isRequerido());
        entity.setTooltip(colDTO.getTooltip());
        entity.setLongitudCelda(colDTO.getLongitudCelda());
        entity.setOrdenColumna(colDTO.getOrdenColumna());
        entity.setActivo(colDTO.isActivo());

        

        // asignamos tipo campo si viene en DTO (solo referencia, no cargamos entidad completa)
        if (colDTO.getCatTipoCampoDTO() != null && colDTO.getCatTipoCampoDTO().getId() != null) {

            CatTipoCampo tipo = em.getReference(
                CatTipoCampo.class,
                colDTO.getCatTipoCampoDTO().getId()
            );

            entity.setTipoCampo(tipo);
        }
        
        //	asignamos tabla padre para relación bidireccional
        if (colDTO.getIdComponenteTabla() != 0) {
            entity.setComponenteTabla(
                em.getReference(ComponenteTabla.class, colDTO.getIdComponenteTabla())
            );
        }
        return entity;
    }

    private DetElementoTablaDTO toDTO(DetElementoTabla entity) {

        DetElementoTablaDTO dto = new DetElementoTablaDTO();

        dto.setId(entity.getId());
        dto.setTituloHeader(entity.getTituloHeader());
        dto.setRequerido(entity.getRequerido());
        dto.setTooltip(entity.getTooltip());
        dto.setLongitudCelda(entity.getLongitudCelda());

        if (entity.getTipoCampo() != null) {
            CatTipoCampoDTO tipo = new CatTipoCampoDTO();
            tipo.setId(entity.getTipoCampo().getId());
            tipo.setDescripcion(entity.getTipoCampo().getDescripcion());
            tipo.setActivo(entity.getTipoCampo().getActivo());
            dto.setCatTipoCampoDTO(tipo);
        }

        return dto;
    }
}
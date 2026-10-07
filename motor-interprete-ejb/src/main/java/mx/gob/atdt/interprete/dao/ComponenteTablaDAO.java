package mx.gob.atdt.interprete.dao;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatTipoCampoDTO;
import mx.gob.atdt.interprete.dto.CatTipoComponenteDTO;
import mx.gob.atdt.interprete.dto.DetElementoTablaDTO;
import mx.gob.atdt.interprete.dto.ComponenteTablaDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteRadioboton;
import mx.gob.atdt.interprete.model.DetElementoTabla;
import mx.gob.atdt.interprete.model.ComponenteTabla;

@Stateless
@LocalBean
public class ComponenteTablaDAO extends IBaseService<ComponenteTablaDTO, Long> {

	@Override
	public ComponenteTablaDTO buscarPorId(Long idComponente) {
		ComponenteTabla entity = em.createNamedQuery(
	            "ComponenteTabla.findComponenteTablaByIdComponente",
	            ComponenteTabla.class)
	        .setParameter("idComponente", idComponente)
	        .getSingleResult();

        if (entity == null) {
            return null;
        }

        ComponenteTablaDTO dto = new ComponenteTablaDTO();
        mapEntityTablaToDTO(entity, dto);

        return dto;
	}

	public ComponenteTablaDTO buscarPorIdComponente(Long id) {
		List<ComponenteTablaDTO> listado = em.createNamedQuery("componenteTabla.findByIdComponenteTabla", ComponenteTablaDTO.class)
				.setParameter("idComponenteTabla", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
	
	public boolean buscarPorIdComponenteTabla(long idComponente) {
		List<ComponenteTabla> listado = em
				.createNamedQuery("ComponenteTabla.findByIdComponenteTabla", ComponenteTabla.class)
				.setParameter("idComponenteTabla", idComponente).getResultList();

		return listado != null && !listado.isEmpty() ? true : false;
	}

	@Override
	public void actualizar(ComponenteTablaDTO dto) {
		ComponenteTabla componente = new ComponenteTabla();
		componente.setId(dto.getIdComponenteTabla()) ;
		componente.setComponente(em.getReference(Componente.class, dto.getIdComponente()));
		componente.setPermiteAgregarFilas(dto.isPermiteAgregarFilas());
		componente.setTamanioPagina(dto.getTamanioPaginaTabla());
		componente.setMinimoFilas(dto.getMinFilas());
		componente.setMaximoFilas(dto.getMaxFilas());
		em.merge(componente);	    
	}
	
	/** Mapeo de entidad columna a DTO.
	 * 
	 * @param col
	 * @return
	 */
    private DetElementoTablaDTO mapColumnaToDTO(ComponenteTablaDTO componenteTablaDTO,
    		DetElementoTabla entityColumna) {

        DetElementoTablaDTO dto = new DetElementoTablaDTO();

        dto.setIdComponenteTabla(componenteTablaDTO.getIdComponenteTabla());
        dto.setId(entityColumna.getId());
        dto.setTituloHeader(entityColumna.getTituloHeader());
        dto.setRequerido(entityColumna.getRequerido());
        dto.setTooltip(entityColumna.getTooltip());
        dto.setLongitudCelda(entityColumna.getLongitudCelda());
        dto.setOrdenColumna(entityColumna.getOrdenColumna());
        dto.setActivo(entityColumna.isActivo());
        dto.setEsColumnaNueva(false);

        if (entityColumna.getComponenteTabla() != null) {
            dto.setIdComponenteTabla(entityColumna.getComponenteTabla().getId());
        }

        if (entityColumna.getTipoCampo() != null) {
            CatTipoCampoDTO tipo = new CatTipoCampoDTO();
            tipo.setId(entityColumna.getTipoCampo().getId());
            tipo.setDescripcion(entityColumna.getTipoCampo().getDescripcion());
            tipo.setActivo(entityColumna.getTipoCampo().getActivo());
            dto.setCatTipoCampoDTO(tipo);
        }
        

        return dto;
    }
    
    private void mapEntityTablaToDTO(ComponenteTabla entity, ComponenteTablaDTO tablaDTO) {

    	tablaDTO.setIdComponenteTabla(entity.getId());
    	tablaDTO.setIdComponente(entity.getComponente().getIdComponente());
    	tablaDTO.setRequerido(entity.getComponente().isRequerido());
    	tablaDTO.setTituloCampo(entity.getComponente().getTituloCampo());
    	tablaDTO.setPermiteAgregarFilas(entity.getPermiteAgregarFilas());
    	tablaDTO.setTamanioPaginaTabla(entity.getTamanioPagina().intValue());
    	tablaDTO.setMinFilas(entity.getMinimoFilas().intValue());
    	tablaDTO.setMaxFilas(entity.getMaximoFilas().intValue());

    	tablaDTO.setFechaCreacion(entity.getComponente().getFechaCreacion());
    	tablaDTO.setFechaUltimaActualizacion(entity.getComponente().getFechaUltimaActualizacion()); 
    	tablaDTO.setActivo(entity.getComponente().isActivo());  
    	tablaDTO.setCatTipoComponenteDTO(new CatTipoComponenteDTO(entity.getComponente().getCatTipoComponente().getIdTipoComponente()));
    	tablaDTO.setSubSeccionesFormularioDTO(new SubSeccionesFormularioDTO(entity.getComponente().getSubseccionesFormulario().getIdSubseccionFormulario()));
    	tablaDTO.setOrden(entity.getComponente().getOrden());
    	tablaDTO.setSeccionSincronizada(entity.getComponente().isSeccionSincronizada());

		// Mapeo de columnas
		if (entity.getColumnas() != null) {
			tablaDTO.setColumnas(
				entity.getColumnas().stream()
					.filter(entityColumna -> entityColumna.isActivo())
					.map(entityColumna -> this.mapColumnaToDTO(tablaDTO, entityColumna))
					.collect(Collectors.toSet())
			);
		}
	}

    /** Mapeo de DTO a entidad tabla.
     * 
     * @param dto
     * @param entity
     */
    private void mapDTOToEntity(ComponenteTablaDTO tablaDTO, ComponenteTabla tablaEntity, Componente componente) {

    	tablaEntity.setComponente(componente);
    	tablaEntity.setPermiteAgregarFilas(tablaDTO.isPermiteAgregarFilas());
    	tablaEntity.setTamanioPagina(tablaDTO.getTamanioPaginaTabla());
    	tablaEntity.setMinimoFilas(tablaDTO.getMinFilas());
    	tablaEntity.setMaximoFilas(tablaDTO.getMaxFilas());

    	if(tablaEntity.getColumnas() == null) {
    		tablaEntity.setColumnas(new java.util.ArrayList<>());
    	}
    	tablaEntity.getColumnas().clear();
        
    }

}

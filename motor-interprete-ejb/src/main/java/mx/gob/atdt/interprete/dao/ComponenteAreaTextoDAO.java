package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.Query;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteAreaTextoDTO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.model.CatOrigenLlenado;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteAreaTexto;

@Stateless
@LocalBean
public class ComponenteAreaTextoDAO extends IBaseService<ComponenteAreaTextoDTO, Long> {

	@Override
	public ComponenteAreaTextoDTO buscarPorId(Long id) {
		List<ComponenteAreaTextoDTO> listado = em.createNamedQuery("ComponenteAreaTexto.findComponentesAreaTextoByIdComponente", ComponenteAreaTextoDTO.class)
				.setParameter("idComponente", id).getResultList();

		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(ComponenteAreaTextoDTO dto) {		
		ComponenteAreaTexto areaTexto = new ComponenteAreaTexto();
		areaTexto.setIdComponenteAreaTexto(dto.getIdComponenteAreaTexto());
		areaTexto.setComponente(em.getReference(Componente.class, dto.getIdComponente()));
		
		if(dto.getCatOrigenLlenadoDTO() == null) {
			areaTexto.setCatOrigenLlenado(null);
		}else {
			areaTexto.setCatOrigenLlenado(em.getReference(CatOrigenLlenado.class, dto.getCatOrigenLlenadoDTO().getIdOrigenLlenado()));		
		}

		areaTexto.setHabilitaTextoInterior(dto.isHabilitaTextoInterior());
		areaTexto.setTextoInterior(dto.getTextoInterior());
		areaTexto.setLineasAltura(dto.getLineasAltura());
		em.merge(areaTexto);	
	}
	
}
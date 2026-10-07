package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteMenuDesplegableDTO;
import mx.gob.atdt.interprete.model.CatOrigenLlenado;
import mx.gob.atdt.interprete.model.CatTipoOrdenamiento;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteMenuDesplegable;

@Stateless
@LocalBean
public class ComponenteMenuDesplegableDAO extends IBaseService<ComponenteMenuDesplegableDTO, Long> {

	@Override
	public ComponenteMenuDesplegableDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean buscarPorIdComponente(long idComponente) {
		List<ComponenteMenuDesplegable> listado = em
				.createNamedQuery("ComponenteMenuDesplegable.findByIdComponente", ComponenteMenuDesplegable.class)
				.setParameter("idComponente", idComponente).getResultList();

		return listado != null && !listado.isEmpty() ? true : false;
	}
	
	public boolean buscarPorIdComponenteMenu(long idComponente) {
		List<ComponenteMenuDesplegable> listado = em
				.createNamedQuery("ComponenteMenuDesplegable.findByIdComponenteMenu", ComponenteMenuDesplegable.class)
				.setParameter("idComponenteMenu", idComponente).getResultList();

		return listado != null && !listado.isEmpty() ? true : false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(ComponenteMenuDesplegableDTO dto) {
		ComponenteMenuDesplegable componente = new ComponenteMenuDesplegable();
		componente.setIdComponenteMenu(dto.getIdComponenteMenuDesplegable());
		componente.setComponente(em.getReference(Componente.class, dto.getIdComponente()));
		componente.setCatOrigenLlenado(em.getReference(CatOrigenLlenado.class, dto.getCatOrigenLlenadoDTO().getIdOrigenLlenado()));
		componente.setHabilitaTextoInteriorOtro(dto.isHabilitaTextoInteriorOtro());
		componente.setTextoInteriorOtro(dto.getTextoInteriorOtro());
		
		if (dto.getCatTipoOrdenamientoDTO() != null
		        && dto.getCatTipoOrdenamientoDTO().getIdTipoOrdenamiento() != null) {
			componente.setCatTipoOrdenamiento(em.getReference(CatTipoOrdenamiento.class, dto.getCatTipoOrdenamientoDTO().getIdTipoOrdenamiento()));
		} else {
		    componente.setCatTipoOrdenamiento(null);
		}

		em.merge(componente);
	}
	
}
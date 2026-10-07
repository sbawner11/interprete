package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetElementosMenuDTO;
import mx.gob.atdt.interprete.model.ComponenteMenuDesplegable;
import mx.gob.atdt.interprete.model.DetElementosMenu;

@Stateless
@LocalBean
public class DetElementosMenuDAO extends IBaseService<DetElementosMenuDTO, Long>{

	@Override
	public DetElementosMenuDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<DetElementosMenuDTO> buscarPorIdComponente(Long id) {
		List<DetElementosMenuDTO> listado = em.createNamedQuery("DetElementosMenu.findByIdComponenteMenu", DetElementosMenuDTO.class)
				.setParameter("idComponenteMenu", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}

	@Override
	public void actualizar(DetElementosMenuDTO dto) {
		DetElementosMenu elemento = new DetElementosMenu();
		
		elemento.setIdElementoMenu(dto.getIdElementoMenu());
		elemento.setComponenteMenuDesplegable(em.getReference(ComponenteMenuDesplegable.class, dto.getComponenteMenuDesplegableDTO().getIdComponenteMenuDesplegable()));
		elemento.setDescripcionElemento(dto.getDescripcionElemento());
		elemento.setActivo(dto.isActivo());
		
		em.merge(elemento);
	}
	
}

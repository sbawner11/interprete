package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxUnicoDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteCheckboxUnico;

@Stateless
@LocalBean
public class ComponenteCheckboxUnicoDAO extends IBaseService<ComponenteCheckboxUnicoDTO, Long> {

	@Override
	public ComponenteCheckboxUnicoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(ComponenteCheckboxUnicoDTO e) {
		ComponenteCheckboxUnico checkboxUnico = new ComponenteCheckboxUnico();
		checkboxUnico.setIdComponenteCheckboxUnico(e.getIdComponenteCheckboxUnico());
		checkboxUnico.setComponente(em.getReference(Componente.class, e.getIdComponente()));
		checkboxUnico.setTexto(e.getTexto());
		em.merge(checkboxUnico);
	}

}

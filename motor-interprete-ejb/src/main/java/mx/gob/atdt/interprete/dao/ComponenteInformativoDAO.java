package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteInformativoDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteInformativo;

@Stateless
@LocalBean
public class ComponenteInformativoDAO extends IBaseService<ComponenteInformativoDTO, Long> {

	@Override
	public ComponenteInformativoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(ComponenteInformativoDTO e) {
		ComponenteInformativo informativo = new ComponenteInformativo();
		informativo.setIdComponenteInformativo(e.getIdComponenteInformativo());
		informativo.setComponente(em.getReference(Componente.class, e.getIdComponente()));
		informativo.setTextoInformativo(e.getTextoInformativo());
		em.merge(informativo);
	}

}

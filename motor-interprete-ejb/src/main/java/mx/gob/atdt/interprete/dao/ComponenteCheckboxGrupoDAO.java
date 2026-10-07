package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteCheckbox;

@Stateless
@LocalBean
public class ComponenteCheckboxGrupoDAO extends IBaseService<ComponenteCheckboxDTO, Long> {

	@Override
	public ComponenteCheckboxDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean buscarPorIdComponente(long idComponente) {
		List<ComponenteCheckbox> listado = em
				.createNamedQuery("ComponenteCheckbox.findByIdComponente", ComponenteCheckbox.class)
				.setParameter("idComponente", idComponente).getResultList();
		return listado != null && !listado.isEmpty() ? true : false;
	}
	
	public boolean buscarPorIdComponenteCheckbox(long idComponente) {
		List<ComponenteCheckbox> listado = em
				.createNamedQuery("ComponenteCheckbox.findByIdComponenteCheckbox", ComponenteCheckbox.class)
				.setParameter("idComponenteCheckbox", idComponente).getResultList();
		return listado != null && !listado.isEmpty() ? true : false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(ComponenteCheckboxDTO dto) {
		ComponenteCheckbox componente = new ComponenteCheckbox();
		componente.setIdComponenteCheckbox(dto.getIdComponenteCheckbox());
		componente.setComponente(em.getReference(Componente.class, dto.getIdComponente()));
		componente.setHabilitaTodosNinguno(dto.isHabilitaTodosNinguno());
		em.merge(componente);
	}
	
}

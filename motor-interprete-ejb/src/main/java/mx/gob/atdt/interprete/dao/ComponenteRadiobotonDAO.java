package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteRadiobotonDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteRadioboton;

@Stateless
@LocalBean
public class ComponenteRadiobotonDAO extends IBaseService<ComponenteRadiobotonDTO, Long> {

	@Override
	public ComponenteRadiobotonDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean buscarPorIdComponente(long idComponente) {
		List<ComponenteRadioboton> listado = em
				.createNamedQuery("ComponenteRadioboton.findByIdComponente", ComponenteRadioboton.class)
				.setParameter("idComponente", idComponente).getResultList();

		return listado != null && !listado.isEmpty() ? true : false;
	}
	
	public boolean buscarPorIdComponenteRadioboton(long idComponente) {
		List<ComponenteRadioboton> listado = em
				.createNamedQuery("ComponenteRadioboton.findByIdComponenteRadioboton", ComponenteRadioboton.class)
				.setParameter("idComponenteRadioboton", idComponente).getResultList();

		return listado != null && !listado.isEmpty() ? true : false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(ComponenteRadiobotonDTO dto) {
		ComponenteRadioboton componente = new ComponenteRadioboton();
		componente.setIdComponenteRadioboton(dto.getIdComponenteRadioboton());
		componente.setComponente(em.getReference(Componente.class, dto.getIdComponente()));
		componente.setHabilitaOpcionOtro(dto.isHabilitaOpcionOtro());
		componente.setTextoInteriorOtro(dto.getTextoInteriorOtro());
		em.merge(componente);
	}
	
}

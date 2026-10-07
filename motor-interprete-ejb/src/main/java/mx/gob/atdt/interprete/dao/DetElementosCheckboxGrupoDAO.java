package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetElementosCheckboxDTO;
import mx.gob.atdt.interprete.model.ComponenteCheckbox;
import mx.gob.atdt.interprete.model.DetElementosCheckbox;;

@Stateless
@LocalBean
public class DetElementosCheckboxGrupoDAO extends IBaseService<DetElementosCheckboxDTO, Long>{

	@Override
	public DetElementosCheckboxDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<DetElementosCheckboxDTO> buscarPorIdComponente(Long id) {
		List<DetElementosCheckboxDTO> listado = em.createNamedQuery("DetElementosCheckbox.findByIdComponenteCheckbox", DetElementosCheckboxDTO.class)
				.setParameter("idComponenteCheckbox", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(DetElementosCheckboxDTO dto) {
		DetElementosCheckbox elemento = new DetElementosCheckbox();
		elemento.setIdElementoCheckbox(dto.getIdElementoCheckbox());
		elemento.setComponenteCheckbox(em.getReference(ComponenteCheckbox.class, dto.getComponenteCheckboxDTO().getIdComponenteCheckbox()));
		elemento.setDescripcionElemento(dto.getDescripcionElemento());
		elemento.setActivo(dto.isActivo());
		elemento.setOrden(dto.getOrden());
		
		em.merge(elemento);
	}
	
}

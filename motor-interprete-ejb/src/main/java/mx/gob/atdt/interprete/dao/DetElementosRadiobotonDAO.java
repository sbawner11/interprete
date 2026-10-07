package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetElementosRadiobotonDTO;
import mx.gob.atdt.interprete.model.ComponenteRadioboton;
import mx.gob.atdt.interprete.model.DetElementosRadioboton;

@Stateless
@LocalBean
public class DetElementosRadiobotonDAO extends IBaseService<DetElementosRadiobotonDTO, Long>{

	@Override
	public DetElementosRadiobotonDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<DetElementosRadiobotonDTO> buscarPorIdComponente(Long id) {
		List<DetElementosRadiobotonDTO> listado = em.createNamedQuery("DetElementosRadioboton.findByIdComponenteRadioboton", DetElementosRadiobotonDTO.class)
				.setParameter("idComponenteRadioboton", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}
	
	@Override
	public void actualizar(DetElementosRadiobotonDTO dto) {
		DetElementosRadioboton elemento = new DetElementosRadioboton();
		
		elemento.setIdElementoRadioboton(dto.getIdElementoRadioboton());
		elemento.setComponenteRadioboton(em.getReference(ComponenteRadioboton.class, dto.getComponenteRadiobotonDTO().getIdComponenteRadioboton()));
		elemento.setDescripcionElemento(dto.getDescripcionElemento());
		elemento.setActivo(dto.isActivo());
		elemento.setOrden(dto.getOrden());
		
		em.merge(elemento);
	}
}

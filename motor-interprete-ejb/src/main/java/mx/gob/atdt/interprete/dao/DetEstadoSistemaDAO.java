package mx.gob.atdt.interprete.dao;

import java.util.Date;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetEstadoSistemaDTO;
import mx.gob.atdt.interprete.model.CatEstadosSistema;
import mx.gob.atdt.interprete.model.DetEstadoSistema;


@Stateless
@LocalBean
public class DetEstadoSistemaDAO extends IBaseService<DetEstadoSistemaDTO, Long> {

	@Override
	public DetEstadoSistemaDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(DetEstadoSistemaDTO e) {
		DetEstadoSistema detEstadoSistema = em.getReference(DetEstadoSistema.class, e.getId());
		
		detEstadoSistema.setCatEstadosSistema(em.getReference(CatEstadosSistema.class, e.getCatEstadosSistemaDTO().getIdEstadoSistema()));
		detEstadoSistema.setFechaUltimaActualizacion(new Date());
		
		em.merge(detEstadoSistema);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrar(DetEstadoSistemaDTO e) {
		DetEstadoSistema detEstadoSistema = new DetEstadoSistema();
		
		detEstadoSistema.setCatEstadosSistema(em.getReference(CatEstadosSistema.class, e.getCatEstadosSistemaDTO().getIdEstadoSistema()));
		detEstadoSistema.setFechaUltimaActualizacion(e.getFechaUltimaActualizacion());
		
		em.persist(detEstadoSistema);
		em.flush();
		e.setId(detEstadoSistema.getId());
	}

	/**
	 * Método auxiliar que obtiene el Estado actual del sistema para los cambios de Estados mediante los Schedule
	 * @return
	 */
	public DetEstadoSistemaDTO buscarEstadoSistema() {
		List<DetEstadoSistemaDTO> listado = 
				em.createNamedQuery("DetEstadoSistema.findAll", DetEstadoSistemaDTO.class).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
	
}

package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetRegistrosCargaMasivaDTO;
import mx.gob.atdt.interprete.model.DetArchivosCargaMasiva;
import mx.gob.atdt.interprete.model.DetRegistrosCargaMasiva;

@LocalBean
@Stateless
public class DetRegistrosCargaMasivaDAO extends IBaseService<DetRegistrosCargaMasivaDTO, Long> {

	@Override
	public DetRegistrosCargaMasivaDTO buscarPorId(Long id) {
		return null;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardar(DetRegistrosCargaMasivaDTO e) {
		DetRegistrosCargaMasiva entity = new DetRegistrosCargaMasiva();
		entity.setArchivoCargaMasiva(em.getReference(DetArchivosCargaMasiva.class, e.getIdArchivoCargaMasiva()));
		entity.setIdElemento(e.getIdElemento());
		entity.setDescripcionElemento(e.getDescripcionElemento());
		entity.setResultado(e.getResultado());
		entity.setExitoso(e.isExitoso());
		em.persist(entity);
		em.flush();
		e.setIdRegistro(entity.getIdRegistro());
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(DetRegistrosCargaMasivaDTO e) {
		if (BeanUtils.isNull(e) || BeanUtils.isNull(e.getIdRegistro())) {
			return;
		}
		DetRegistrosCargaMasiva entity = em.find(DetRegistrosCargaMasiva.class, e.getIdRegistro());
		if (BeanUtils.isNull(entity)) {
			return;
		}
		entity.setExitoso(e.isExitoso());
		entity.setResultado(e.getResultado());
		em.merge(entity);
		em.flush();
	}

	public List<DetRegistrosCargaMasivaDTO> buscarPorIdArchivo(Long idArchivoCargaMasiva) {
		List<DetRegistrosCargaMasivaDTO> resultados = em
				.createNamedQuery("DetRegistrosCargaMasiva.findByIdArchivo", DetRegistrosCargaMasivaDTO.class)
				.setParameter("idArchivoCargaMasiva", idArchivoCargaMasiva)
				.getResultList();
		return BeanUtils.isNotEmpty(resultados) ? resultados : null;
	}
}

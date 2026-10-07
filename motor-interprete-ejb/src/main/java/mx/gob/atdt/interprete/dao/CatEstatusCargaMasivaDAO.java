package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatEstatusCargaMasivaDTO;

@LocalBean
@Stateless
public class CatEstatusCargaMasivaDAO extends IBaseService<CatEstatusCargaMasivaDTO, Integer> {

	@Override
	public CatEstatusCargaMasivaDTO buscarPorId(Integer id) {
		List<CatEstatusCargaMasivaDTO> resultados = em
				.createNamedQuery("CatEstatusCargaMasiva.findById", CatEstatusCargaMasivaDTO.class)
				.setParameter("idEstatusCarga", id)
				.getResultList();
		return BeanUtils.isNotEmpty(resultados) ? resultados.get(0) : null;
	}

	@Override
	public void actualizar(CatEstatusCargaMasivaDTO e) {
		// Catálogo fijo
	}

	public List<CatEstatusCargaMasivaDTO> buscarTodos() {
		List<CatEstatusCargaMasivaDTO> resultados = em
				.createNamedQuery("CatEstatusCargaMasiva.findAll", CatEstatusCargaMasivaDTO.class)
				.getResultList();
		return BeanUtils.isNotEmpty(resultados) ? resultados : null;
	}
}

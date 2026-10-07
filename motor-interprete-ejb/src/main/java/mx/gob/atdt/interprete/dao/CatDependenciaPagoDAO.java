package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatDependenciaPagoDTO;


@Stateless
@LocalBean
public class CatDependenciaPagoDAO extends IBaseService<CatDependenciaPagoDTO, Integer> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CatDependenciaPagoDAO.class);

	@Override
	public CatDependenciaPagoDTO buscarPorId(Integer id) {
		return null;
	}	
	
	@Override
	public void actualizar(CatDependenciaPagoDTO e) {
		// Método no necesario
	}
	
	/**
	 * Método auxiliar que realiza la búsqueda de una Dependencua mediante su Id
	 * @param idDependenciaPago
	 * @return
	 */
	public CatDependenciaPagoDTO buscarPorIdDependenciaPago(int idDependenciaPago) {
		List<CatDependenciaPagoDTO> lstDependencias = em.createNamedQuery("CatDependenciaPago.findById", CatDependenciaPagoDTO.class)
				.setParameter("idDependenciaPago", idDependenciaPago)				
				.getResultList();
		return !lstDependencias.isEmpty() ? lstDependencias.get(0) : null;
	}	
}


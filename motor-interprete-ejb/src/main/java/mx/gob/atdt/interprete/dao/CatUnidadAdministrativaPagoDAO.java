package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.CatUnidadAdministrativaPagoDTO;


@Stateless
@LocalBean
public class CatUnidadAdministrativaPagoDAO extends IBaseService<CatUnidadAdministrativaPagoDTO, Integer> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CatUnidadAdministrativaPagoDAO.class);

	@Override
	public CatUnidadAdministrativaPagoDTO buscarPorId(Integer id) {
		return null;
	}

	@Override
	public void actualizar(CatUnidadAdministrativaPagoDTO e) {
		// Método no necesario		
	}
	
	/**
	 * Método auxiliar que realiza la búsqueda de una Unidad Administrativa mediante su Id
	 * @param idUnidadAdministrativaPago
	 * @return
	 */
	public CatUnidadAdministrativaPagoDTO buscarPorIdUnidadAdministrativaPago(int idUnidadAdministrativaPago) {
		List<CatUnidadAdministrativaPagoDTO> lstUnidadAdministrativa = em.createNamedQuery("CatUnidadAdministrativaPago.findById", CatUnidadAdministrativaPagoDTO.class)
				.setParameter("idUnidadAdministrativaPago", idUnidadAdministrativaPago)				
				.getResultList();
		return !lstUnidadAdministrativa.isEmpty() ? lstUnidadAdministrativa.get(0) : null;
	}
}


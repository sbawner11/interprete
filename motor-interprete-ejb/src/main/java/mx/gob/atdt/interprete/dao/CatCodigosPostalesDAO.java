package mx.gob.atdt.interprete.dao;


import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.commons.dto.CatCodigosPostalesDTO;


@Stateless
@LocalBean
public class CatCodigosPostalesDAO extends IBaseService<CatCodigosPostalesDTO, Integer>{
	private static final Logger LOGGER = LoggerFactory.getLogger(CatCodigosPostalesDAO.class);

	@Override
	public CatCodigosPostalesDTO buscarPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void actualizar(CatCodigosPostalesDTO e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Método que realiza la consulta del Código Postal
	 * @param codigoPostal
	 * @return
	 */
	public List<CatCodigosPostalesDTO> buscarPorCodigoPostal(String codigoPostal) {
		List<CatCodigosPostalesDTO> lstCodigosPostales = new ArrayList<CatCodigosPostalesDTO>();
		lstCodigosPostales = em.createNamedQuery("CodigosPostales.buscarCodigoPostal", CatCodigosPostalesDTO.class)
								.setParameter("codigoPostal", codigoPostal)
								.getResultList();
		return lstCodigosPostales.size() > 0 ? lstCodigosPostales : null;
	}
	
}


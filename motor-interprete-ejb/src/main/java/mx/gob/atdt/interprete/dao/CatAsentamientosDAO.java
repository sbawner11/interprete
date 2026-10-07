package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.commons.dto.CatAsentamientosDTO;

@LocalBean
@Stateless
public class CatAsentamientosDAO extends IBaseService<CatAsentamientosDTO, Integer>{

	@Override
	public CatAsentamientosDTO buscarPorId(Integer id) {
		try {
			return em.createNamedQuery("CatAsentamientos.buscarPorIdMunicipio", CatAsentamientosDTO.class)
				.setParameter("idAsentamiento", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void actualizar(CatAsentamientosDTO e) {
		// TODO Auto-generated method stub		
	}
	
	/**
	 * Método que busca los asentamientos por el IdMunicipio
	 * @param idMunicipio
	 * @return
	 */
	public CatAsentamientosDTO buscarPorIdMunicipio(int idAsentamiento){
		List<CatAsentamientosDTO> lstAsentamientos = em.createNamedQuery("CatAsentamientos.buscarPorIdMunicipio", CatAsentamientosDTO.class)
				.setParameter("idAsentamiento", idAsentamiento)				
				.getResultList();
		return lstAsentamientos.size() > 0 ? lstAsentamientos.get(0) : null;
	}
	


}

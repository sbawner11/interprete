package mx.gob.atdt.interprete.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.LocalBean;

import mx.gob.atdt.interprete.commons.dao.IBaseService;

import mx.gob.atdt.interprete.dto.CatDiasSemanaDTO;
import mx.gob.atdt.interprete.dto.NotificacionesDTO;
import mx.gob.atdt.interprete.model.CatDiasSemana;

import java.util.List;
import javax.persistence.NoResultException;

@Stateless
@LocalBean
public class CatDiasSemanaDAO extends IBaseService<CatDiasSemanaDTO, Integer> {

	@Override
    public CatDiasSemanaDTO buscarPorId(Integer id) {
		try {
			return em.createNamedQuery("CatDiasSemana.findById", CatDiasSemanaDTO.class)
				.setParameter("idDiaSemana", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
    }

	public List<CatDiasSemanaDTO> buscarTodos() {
		List<CatDiasSemanaDTO> listado = em.createNamedQuery("CatDiasSemana.findAll", 
				CatDiasSemanaDTO.class).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}

	public List<CatDiasSemanaDTO> buscarActivos() {
		List<CatDiasSemanaDTO> listado = em.createNamedQuery("CatDiasSemana.findActivos", 
				CatDiasSemanaDTO.class).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}

	@Override
	public void actualizar(CatDiasSemanaDTO e) {
		//Pendiente
	}

}
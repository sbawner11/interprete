package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetDistribucionDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.DetDistribucion;
import mx.gob.atdt.interprete.model.Proyecto;

@Stateless
@LocalBean
public class DetDistribucionDAO extends IBaseService<DetDistribucionDTO, Long>{

	@Override
	public DetDistribucionDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(DetDistribucionDTO detDistribucionDTO) {
		DetDistribucion detDistribucion = new DetDistribucion();

		detDistribucion.setIdDistribucion(detDistribucionDTO.getIdDistribucion());
		detDistribucion.setProyecto(em.getReference(Proyecto.class, detDistribucionDTO.getProyectoDTO().getIdProyecto()));
		detDistribucion.setComponente(em.getReference(Componente.class, detDistribucionDTO.getComponenteDTO().getIdComponente()));
		detDistribucion.setFechaCreacion(detDistribucionDTO.getFechaCreacion());
		detDistribucion.setFechaUltimaActualizacion(detDistribucionDTO.getFechaUltimaActualizacion());
		detDistribucion.setActivo(detDistribucionDTO.isActivo());
		detDistribucion.setSeccionSincronizada(detDistribucionDTO.getSeccionSincronizada());
		
		em.merge(detDistribucion);
	}
	/**
	 * Método auxiliar para realizar la consulta del detalle de distribución por 
	 * id del proyecto en la BD.
	 * @param idProyecto
	 * @return
	 */
	public DetDistribucionDTO buscarPorIdProyecto(final Long idProyecto) {
		List<DetDistribucionDTO> lstResultados = em.createNamedQuery("DetDistribucion.findByIdProyecto", DetDistribucionDTO.class)
				.setParameter("idProyecto", idProyecto)
				.getResultList();
		return BeanUtils.isNotEmpty(lstResultados) ? lstResultados.get(0) : null;
	}
	
}

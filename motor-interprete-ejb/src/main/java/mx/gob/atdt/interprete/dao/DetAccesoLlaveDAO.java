package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetAccesoLLaveDTO;
import mx.gob.atdt.interprete.model.DetAccesoLlave;
import mx.gob.atdt.interprete.model.Proyecto;

 
@Stateless
@LocalBean
public class DetAccesoLlaveDAO extends IBaseService<DetAccesoLLaveDTO,Long> {

	@Override
	public DetAccesoLLaveDTO buscarPorId(Long id) {
		return null;
	}	
	
	@Override
	public void actualizar(DetAccesoLLaveDTO e) {
		DetAccesoLlave detAccesoLLave = new DetAccesoLlave();
		detAccesoLLave.setIdDetalleAcceso(e.getIdDetalleAcceso());
		detAccesoLLave.setProyecto(em.getReference(Proyecto.class, e.getProyectoDTO().getIdProyecto()));
		detAccesoLLave.setClaveSistema(e.getClaveSistema());
		detAccesoLLave.setUrlRedireccionar(e.getUrlRedireccionar());
		detAccesoLLave.setFechaCreacion(e.getFechaCreacion());
		detAccesoLLave.setFechaUltimaActualizacion(e.getFechaUltimaActualizacion());
		detAccesoLLave.setActivo(e.isActivo());
		detAccesoLLave.setLimitarUnicoTramite(e.isLimitarUnicoTramite());
		detAccesoLLave.setSeccionSincronizada(e.isSeccionSincronizada());
		detAccesoLLave.setAutenticacionCiudadano(e.isAutenticacionCiudadano());
		detAccesoLLave.setUsuarioDominoSeg(e.getUsuarioDominoSeg());
		detAccesoLLave.setContrasenaDominioSeg(e.getContrasenaDominioSeg());
		detAccesoLLave.setCodigoSecreto(e.getCodigoSecreto());
		detAccesoLLave.setValidaRol(e.isValidaRol());
		detAccesoLLave.setRolesPermitidos(e.getRolesPermitidos());
		
		em.merge(detAccesoLLave);		
	}
	
	public DetAccesoLLaveDTO buscarPorIdProyecto(Long idProyecto) {
		List<DetAccesoLLaveDTO> listado = em.createNamedQuery("DetAccesoLlave.findByIdProyecto", DetAccesoLLaveDTO.class)
				.setParameter("idProyecto", idProyecto)
				.getResultList();		
		return  listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
	
}
package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetGestionUsuarioDTO;
import mx.gob.atdt.interprete.model.DetGestionUsuario;
import mx.gob.atdt.interprete.model.Proyecto;

@Stateless
@LocalBean
public class DetGestionUsuariosDAO extends IBaseService<DetGestionUsuarioDTO, Long> {

	@Override
	public DetGestionUsuarioDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(DetGestionUsuarioDTO dto) {		
		DetGestionUsuario detalleGestion =	new DetGestionUsuario(); 
		
		detalleGestion.setIdGestionUsuario(dto.getIdGestionUsuario());
		detalleGestion.setProyecto(em.getReference(Proyecto.class, dto.getProyectoDTO().getIdProyecto()));
		detalleGestion.setPerfilSupervisorPrevencion(dto.getPerfilSupervisorPrevencion());
		detalleGestion.setPerfilOperadorPrevencion(dto.getPerfilOperadorPrevencion());
		detalleGestion.setPerfilSupervisorConclusion(dto.getPerfilSupervisorConclusion());
		detalleGestion.setPerfilOperadorConclusion(dto.getPerfilOperadorConclusion());
		detalleGestion.setCorreoConclusion(dto.getCorreoConclusion());
		detalleGestion.setCorreoPrevencion(dto.getCorreoPrevencion());
		detalleGestion.setCorreoSubsanarPrevencion(dto.getCorreoSubsanarPrevencion());
		detalleGestion.setCorreoRegistrado(dto.getCorreoRegistrado());
		detalleGestion.setCorreoRechazado(dto.getCorreoRechazado());
		detalleGestion.setHabilitaPrevencion(dto.isHabilitaPrevencion());
		detalleGestion.setAdjuntaOficio(dto.isAdjuntaOficio());				
		detalleGestion.setDiasSubsanarPrevencion(dto.getDiasSubsanarPrevencion());
		detalleGestion.setFechaCreacion(dto.getFechaCreacion());
		detalleGestion.setFechaUltimaActualizacion(dto.getFechaUltimaActualizacion());
		detalleGestion.setActivo(dto.isActivo());
		detalleGestion.setSeccionSincronizada(dto.isSeccionSincronizada());
		detalleGestion.setApiKey(dto.getApiKey());		
		detalleGestion.setHabilitaResolucion(dto.isHabilitaResolucion());
		detalleGestion.setPerfilSupervisorResolucion(dto.isPerfilSupervisorResolucion());
		detalleGestion.setPerfilOperadorResolucion(dto.isPerfilOperadorResolucion());
		detalleGestion.setResolucionPositivaObligatoria(dto.isResolucionPositivaObligatoria());
		detalleGestion.setResolucionNegativaObligatoria(dto.isResolucionNegativaObligatoria());
		detalleGestion.setCorreoResolucionPositiva(dto.getCorreoResolucionPositiva());
		detalleGestion.setCorreoResolucionNegativa(dto.getCorreoResolucionNegativa());				
		em.merge(detalleGestion);			
	}
	
	public DetGestionUsuarioDTO buscarPorIdProyecto(Long id) {
		List<DetGestionUsuarioDTO> listado = em.createNamedQuery("DetGestionUsuario.findByIdProyecto", DetGestionUsuarioDTO.class)
				.setParameter("idProyecto", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
	
	public DetGestionUsuarioDTO buscarTodos() {
		List<DetGestionUsuarioDTO> listado = em.createNamedQuery("DetGestionUsuario.buscarProyecto", DetGestionUsuarioDTO.class)
				.getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
	
}

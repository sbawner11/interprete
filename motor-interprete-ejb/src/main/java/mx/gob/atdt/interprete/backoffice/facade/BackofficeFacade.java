package mx.gob.atdt.interprete.backoffice.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.dao.BitAsignacionRevisorTramiteDAO;
import mx.gob.atdt.interprete.dao.UsuarioDAO;
import mx.gob.atdt.interprete.dto.BitAsignacionRevisorTramiteDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.formulario.dao.FormularioDAO;

@Stateless
@LocalBean
public class BackofficeFacade {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BackofficeFacade.class);
	
	@Inject
	BitAsignacionRevisorTramiteDAO bitAsignacionRevisorTramiteDAO;
	
	@Inject
	FormularioDAO formularioDAO;
	
	@Inject
	UsuarioDAO usuarioDAO;
	
	/**
	 * Método auxiliar para registrar en la BD la asignación de un usuario revisor asociado a un trámite.
	 * @param tramiteDTO
	 * @param bitAsignacionReviorTramiteDTO
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void registrarAsignacionUsuarioRevisorDeTramite(
			TramiteDTO tramiteDTO, 
			BitAsignacionRevisorTramiteDTO bitAsignacionReviorTramiteDTO) {
		if(BeanUtils.isNotNull(tramiteDTO) && BeanUtils.isNotNull(bitAsignacionReviorTramiteDTO)) {
			//1. Se actualiza en el trámite indicado el usuario revisor asignado
			formularioDAO.actualizarOperadorTramite(tramiteDTO);
			
			//2. Se guarda el movimiento de asignación del usuario revisor en la bitacora
			bitAsignacionRevisorTramiteDAO.guardar(bitAsignacionReviorTramiteDTO);
		}
		
	}
	
	/**
	 * Método que guarda o actualiza la información del usario operador
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarActualizarUsuarioOperador(UsuarioDTO usuarioOperadorDTO) {
		UsuarioDTO tmpUsuario = usuarioDAO.buscarPorId(usuarioOperadorDTO.getIdUsuarioLlaveCdmx());
		if (tmpUsuario != null) {
			if (!tmpUsuario.equals(usuarioOperadorDTO)) {
				usuarioDAO.actualizar(usuarioOperadorDTO);
			}
		} else {
			usuarioDAO.guardar(usuarioOperadorDTO);
		}
	}
	

}

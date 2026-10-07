package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.ComponenteMenuDesplegableDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteMenuDesplegableDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;

@Stateless
@LocalBean
public class ComponenteMenuDesplegableFacade {

	@Inject
	private ComponenteDAO componenteDAO;

	@Inject
	private ComponenteMenuDesplegableDAO componenteMenuDesplegableDAO;
		
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteMenuDesplegableDTO componenteMenuDesplegableDTO) {
		//Se actualiza componente
		componenteDAO.actualizar(componenteMenuDesplegableDTO);
		
		//Se actualiza información del componente Menú Desplegable
		componenteMenuDesplegableDAO.actualizar(componenteMenuDesplegableDTO);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(componenteMenuDesplegableDTO.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
										
		//Se genera la columa en BD donde se registrará inforación del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), componenteMenuDesplegableDTO, false)) {
			estructuraFormularioDAO.creaNuevaColumnaMenuDesplegable(subSeccionTmp.getSeccionesFormularioDTO(), componenteMenuDesplegableDTO);
		}
		
		//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
		if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteMenuDesplegableDTO, componenteMenuDesplegableDTO.getTituloCampo(), 1)) {
			estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteMenuDesplegableDTO, componenteMenuDesplegableDTO.getTituloCampo(), 1);
		} else {
			estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteMenuDesplegableDTO, componenteMenuDesplegableDTO.getTituloCampo(), 1);
		}
	}
}
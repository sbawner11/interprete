package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.ComponenteCheckboxGrupoDAO;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;

@Stateless
@LocalBean
public class ComponenteCheckboxGrupoFacade {

	@Inject
	private ComponenteDAO componenteDAO;

	@Inject
	private ComponenteCheckboxGrupoDAO componenteCheckboxGrupoDAO;
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;
		
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteCheckboxDTO componenteCheckboxDTO) {
		
		//Se actualiza el componente
		componenteDAO.actualizar(componenteCheckboxDTO);
		
		//Se actualiza información del CheckBox grupo
		componenteCheckboxGrupoDAO.actualizar(componenteCheckboxDTO);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(componenteCheckboxDTO.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
				
		//Se genera la columa en BD donde se registrará inforación del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), componenteCheckboxDTO, false)) {
			estructuraFormularioDAO.creaNuevaColumnaCheckBoxGrupo(subSeccionTmp.getSeccionesFormularioDTO(), componenteCheckboxDTO);
		}
		
		//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
		if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteCheckboxDTO, componenteCheckboxDTO.getTituloCampo(), 1)) {
			estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteCheckboxDTO, componenteCheckboxDTO.getTituloCampo(), 1);
		} else {
			estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteCheckboxDTO, componenteCheckboxDTO.getTituloCampo(), 1);
		}
	}
}
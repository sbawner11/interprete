package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.ComponenteCheckboxUnicoDAO;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxUnicoDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;

@Stateless
@LocalBean
public class ComponenteCheckboxUnicoFacade {

	@Inject
	private ComponenteDAO componenteDAO;

	@Inject
	private ComponenteCheckboxUnicoDAO componenteCheckboxUnicoDAO;
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteCheckboxUnicoDTO componenteCheckboxUnicoDTO) {	
		//Se actualiza componente
		componenteDAO.actualizar(componenteCheckboxUnicoDTO);
		//Se actualiza información del componente checkbox único
		componenteCheckboxUnicoDAO.actualizar(componenteCheckboxUnicoDTO);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(componenteCheckboxUnicoDTO.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
								
		//Se genera la columa en BD donde se registrará inforación del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), componenteCheckboxUnicoDTO, false)) {
			estructuraFormularioDAO.creaNuevaColumnaCheckUnico(subSeccionTmp.getSeccionesFormularioDTO(), componenteCheckboxUnicoDTO);
		}
		
		//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
		if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteCheckboxUnicoDTO, componenteCheckboxUnicoDTO.getTituloCampo(), 1)) {
			estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteCheckboxUnicoDTO, componenteCheckboxUnicoDTO.getTituloCampo(), 1);
		} else {
			estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteCheckboxUnicoDTO, componenteCheckboxUnicoDTO.getTituloCampo(), 1);
		}
	}
}
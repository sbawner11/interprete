package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.ComponenteTablaDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteTablaDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;

@Stateless
@LocalBean
public class ComponenteTablaFacade {

	@Inject
	private ComponenteDAO componenteDAO;

	@Inject
	private ComponenteTablaDAO componenteTablaDAO;
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteTablaDTO componenteTablaDTO) {
		
		//Se actualiza componente
		componenteDAO.actualizar(componenteTablaDTO);
		
		//Se actualiza información del componente radiobutton
		componenteTablaDAO.actualizar(componenteTablaDTO);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(componenteTablaDTO.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 

		//Se genera la columa en BD donde se registrará información del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), componenteTablaDTO, false)) {
			estructuraFormularioDAO.creaNuevaColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), componenteTablaDTO);
		}
		//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
		if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteTablaDTO, componenteTablaDTO.getTituloCampo(), 1)) {
			estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteTablaDTO, componenteTablaDTO.getTituloCampo(), 1);
		} else {
			estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteTablaDTO, componenteTablaDTO.getTituloCampo(), 1);
		}
	}
}
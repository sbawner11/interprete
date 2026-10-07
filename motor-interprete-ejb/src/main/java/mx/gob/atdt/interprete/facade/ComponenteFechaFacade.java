package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.ComponenteFechaDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteFechaDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;


@Stateless
@LocalBean
public class ComponenteFechaFacade {

	@Inject
	private ComponenteDAO componenteDAO;	
	
	@Inject
	private ComponenteFechaDAO componenteFechaDAO;	
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteFechaDTO compFecha) {
		//Se actualiza el componente
		componenteDAO.actualizar(compFecha);
		//Se actualiza la información de componente de fecha
		componenteFechaDAO.actualizar(compFecha);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(compFecha.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
				
		//Se genera la columa en BD donde se registrará inforación del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), compFecha, false)) {
			estructuraFormularioDAO.creaNuevaColumnaFecha(subSeccionTmp.getSeccionesFormularioDTO(), compFecha);
		}
		
		//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
		if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), compFecha, compFecha.getTituloCampo(), 1)) {
			estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), compFecha, compFecha.getTituloCampo(), 1);
		} else {
			estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), compFecha, compFecha.getTituloCampo(), 1);
		}
	}
}

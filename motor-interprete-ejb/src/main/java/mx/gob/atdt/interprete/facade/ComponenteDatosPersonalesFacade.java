package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.ComponenteDatosPersonalesDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;


@Stateless
@LocalBean
public class ComponenteDatosPersonalesFacade {

	@Inject
	private ComponenteDAO componenteDAO;
	
	@Inject
	private ComponenteDatosPersonalesDAO componenteDatosPersonalesDAO;
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteDatosPersonalesDTO datosPersonales) {
		
		//Se actualiza el componente
		componenteDAO.actualizar(datosPersonales);
		
		//Se actualiza la información de datos personales
		componenteDatosPersonalesDAO.actualizar(datosPersonales);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(datosPersonales.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
								
		//Se genera la columa en BD donde se registrará inforación del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonales, true)) {
			estructuraFormularioDAO.creaNuevaColumnaDatosPersonales(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonales);
		}
		
		String[] camposDatosPersonales = new String[]{"Campo curp.","Nombres.","Primer apellido.","Segundo apellido.","Teléfono.","Correo electrónico."};
		for (int contadorElemento = 0; contadorElemento<camposDatosPersonales.length; contadorElemento++) {
			//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
			if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonales, camposDatosPersonales[contadorElemento], contadorElemento)) {
				estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonales, camposDatosPersonales[contadorElemento], contadorElemento);
			} else {
				estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonales, camposDatosPersonales[contadorElemento], contadorElemento);
			}
		}
	}
}

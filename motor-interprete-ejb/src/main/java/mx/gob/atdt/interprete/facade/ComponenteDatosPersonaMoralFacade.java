package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.ComponenteDatosPersonaMoralDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonaMoralDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;

@Stateless
@LocalBean
public class ComponenteDatosPersonaMoralFacade {

	@Inject
	private ComponenteDAO componenteDAO;
	
	@Inject
	private ComponenteDatosPersonaMoralDAO componenteDatosPersonaMoralDAO;
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteDatosPersonaMoralDTO datosPersonaMoral) {
		
		//Se actualiza el componente
		componenteDAO.actualizar(datosPersonaMoral);
		
		//Se actualiza la información de datos de persona Moral
		componenteDatosPersonaMoralDAO.actualizar(datosPersonaMoral);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(datosPersonaMoral.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
								
		//Se genera la columa en BD donde se registrará inforación del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonaMoral, true)) {
			estructuraFormularioDAO.creaNuevaColumnaDatosPersonaMoral(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonaMoral);
		}
		
		String[] camposDatosPersonaMoral = new String[]{"Campo rfc","Razon social","Vigencia certificado"};
		for (int contadorElemento = 0; contadorElemento<camposDatosPersonaMoral.length; contadorElemento++) {
			//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
			if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonaMoral, camposDatosPersonaMoral[contadorElemento], contadorElemento)) {
				estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonaMoral, camposDatosPersonaMoral[contadorElemento], contadorElemento);
			} else {
				estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonaMoral, camposDatosPersonaMoral[contadorElemento], contadorElemento);
			}
		}
	}
}

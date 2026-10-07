package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.ComponenteRadiobotonDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteRadiobotonDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;

@Stateless
@LocalBean
public class ComponenteRadioFacade {

	@Inject
	private ComponenteDAO componenteDAO;

	@Inject
	private ComponenteRadiobotonDAO componenteRadiobotonDAO;
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteRadiobotonDTO componenteRadiobotonDTO) {
		
		//Se actualiza componente
		componenteDAO.actualizar(componenteRadiobotonDTO);
		
		//Se actualiza información del componente radiobutton
		componenteRadiobotonDAO.actualizar(componenteRadiobotonDTO);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(componenteRadiobotonDTO.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
						
		//Se genera la columa en BD donde se registrará inforación del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), componenteRadiobotonDTO, true)) {
			estructuraFormularioDAO.creaNuevaColumnaRadioBoton(subSeccionTmp.getSeccionesFormularioDTO(), componenteRadiobotonDTO);
		}
		
		String[] camposRadioboton = new String[]{"Registra opción del listado.","Otra opción.","Especifique otra opción."};
		for (int contadorElemento = 0; contadorElemento<camposRadioboton.length; contadorElemento++) {
			//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
			if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteRadiobotonDTO, camposRadioboton[contadorElemento], contadorElemento)) {
				estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteRadiobotonDTO, camposRadioboton[contadorElemento], contadorElemento);
			} else {
				estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteRadiobotonDTO, camposRadioboton[contadorElemento], contadorElemento);
			}
		}
	}
}
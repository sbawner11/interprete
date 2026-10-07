package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.ComponenteDatosPersonalesLlaveDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesLlaveDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;


@Stateless
@LocalBean
public class ComponenteDatosPersonalesLlaveFacade {

	@Inject
	private ComponenteDAO componenteDAO;	
	
	@Inject
	private ComponenteDatosPersonalesLlaveDAO componenteDatosPersonalesLlaveDAO;
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteDatosPersonalesLlaveDTO datosPersonalesLlave) {
		//Se actualiza el componente
		componenteDAO.actualizar(datosPersonalesLlave);
		//Se actualiza la información de datos personales
		componenteDatosPersonalesLlaveDAO.actualizar(datosPersonalesLlave);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(datosPersonalesLlave.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
						
		//Se genera la columa en BD donde se registrará inforación del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonalesLlave, true)) {
			estructuraFormularioDAO.creaNuevaColumnaDatosPersonalesLLave(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonalesLlave);
		}
		
		String[] camposDatosPersonalesLlave = new String[]{"Campo curp.","Nombres.","Primer apellido.","Segundo apellido.","Teléfono.","Correo electrónico.","Fecha de nacimiento.", "Campo sexo."};
		for (int contadorElemento = 0; contadorElemento<camposDatosPersonalesLlave.length; contadorElemento++) {
			//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
			if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonalesLlave, camposDatosPersonalesLlave[contadorElemento], contadorElemento)) {
				estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonalesLlave, camposDatosPersonalesLlave[contadorElemento], contadorElemento);
			} else {
				estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonalesLlave, camposDatosPersonalesLlave[contadorElemento], contadorElemento);
			}
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarColumnasComponente(ComponenteDatosPersonalesLlaveDTO datosPersonalesLlave) {
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(datosPersonalesLlave.getSubSeccionesFormularioDTO().getIdSubseccionFormulario());
		
		String columnaSexo = Constantes.NOMBRE_BASE_COLUMNAS.concat(datosPersonalesLlave.getIdComponente().toString()).concat(Constantes.ORDER_CAMPO_SEXO_COMPONENTE_DATOS_PERSONALES);
		
		//Se revisa si la columna Sexo ya se encuentra registrada em la sección que le corresponde para almacenar la respuesta de la captura de un trámite. 
		if(!estructuraFormularioDAO.existeNombreColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), columnaSexo)) {
			estructuraFormularioDAO.creaColumnaSexoComponenteDatosLlave(subSeccionTmp.getSeccionesFormularioDTO(), datosPersonalesLlave);
		}			
	}
}

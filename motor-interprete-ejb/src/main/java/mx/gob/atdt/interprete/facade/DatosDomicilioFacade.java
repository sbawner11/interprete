package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.ComponenteDatosDomicilioDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteDatosDomicilioDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;

@Stateless
@LocalBean
public class DatosDomicilioFacade {

	@Inject
	private ComponenteDAO componenteDAO;

	@Inject
	private ComponenteDatosDomicilioDAO datosDomicilioDAO;
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteDatosDomicilioDTO dto) {
		
		//Se actualiza el componente
		componenteDAO.actualizar(dto);
		
		//Se actualiza la información de Datos Domicilio
		datosDomicilioDAO.actualizar(dto);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(dto.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
				
		//Se genera la columa en BD donde se registrará inforación del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), dto, true)) {
			estructuraFormularioDAO.creaNuevaColumnaDatosDomicilio(subSeccionTmp.getSeccionesFormularioDTO(), dto);
		}
		
		String[] camposDatosDomicilio = new String[]{"Campo calle.","Número exterior.","Número interior.","Código postal.","Colonia.","Alcaldía.","Estado."};
		for (int contadorElemento = 0; contadorElemento<camposDatosDomicilio.length; contadorElemento++) {
			//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
			if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), dto, camposDatosDomicilio[contadorElemento], contadorElemento)) {
				estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), dto, camposDatosDomicilio[contadorElemento], contadorElemento);
			} else {
				estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), dto, camposDatosDomicilio[contadorElemento], contadorElemento);
			}
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizarColumnasComponente(ComponenteDatosDomicilioDTO dto) {
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(dto.getSubSeccionesFormularioDTO().getIdSubseccionFormulario());
		
		String columnaEstado = Constantes.NOMBRE_BASE_COLUMNAS.concat(dto.getIdComponente().toString()).concat(Constantes.ORDER_CAMPO_ESTADO_COMPONENTE_DOMICILIO);
		
		//Se revisa si la columna Estado ya se encuentra registrada em la sección que le corresponde para almacenar la respuesta de la captura de un trámite. 
		if(!estructuraFormularioDAO.existeNombreColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), columnaEstado)) {
			estructuraFormularioDAO.creaColumnaEstadoComponenteDomicilio(subSeccionTmp.getSeccionesFormularioDTO(), dto);
		}		
		
		String[] camposDatosDomicilio = new String[]{"Campo calle.","Número exterior.","Número interior.","Código postal.","Colonia.","Alcaldía.","Estado."};
		for (int contadorElemento = 0; contadorElemento<camposDatosDomicilio.length; contadorElemento++) {
			//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
			if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), dto, camposDatosDomicilio[contadorElemento], contadorElemento)) {
				estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), dto, camposDatosDomicilio[contadorElemento], contadorElemento);
			} else {
				estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), dto, camposDatosDomicilio[contadorElemento], contadorElemento);
			}
		}
	}	
}

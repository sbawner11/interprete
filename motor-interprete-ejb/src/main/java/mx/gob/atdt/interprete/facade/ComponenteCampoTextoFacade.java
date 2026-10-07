package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.ComponenteCampoTextoDAO;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteCampoTextoDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;


@Stateless
@LocalBean
public class ComponenteCampoTextoFacade {

	@Inject
	private ComponenteDAO componenteDAO;
	
	@Inject
	private ComponenteCampoTextoDAO campoTextoDAO;
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteCampoTextoDTO campoTextoDTO) {
		//Obtener información actual del componente de texto
		ComponenteCampoTextoDTO campoTextoActual = null;
		if(campoTextoDTO.getIdComponenteCampoTexto() != Constantes.INT_VALOR_CERO) {
			campoTextoActual = campoTextoDAO.buscarPorId(campoTextoDTO.getIdComponenteCampoTexto());
		}
		
		//Se actualiza el componente
		componenteDAO.actualizar(campoTextoDTO);
		
		//Se actualiza la información del componente campo de texto
		campoTextoDAO.actualizar(campoTextoDTO);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(campoTextoDTO.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
		
		//Se genera la columa en BD donde se registrará inforación del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), campoTextoDTO, false)) {
			estructuraFormularioDAO.creaNuevaColumnaCampoTexto(subSeccionTmp.getSeccionesFormularioDTO(), campoTextoDTO);
		} else {
			//Se realiza alter a la longitud del campo de texto donde se registrará la información capturada, SOLO si la longitud del campo es mayor a la que tenía.
			if(campoTextoActual != null && campoTextoDTO.getValorMaximo() > campoTextoActual.getValorMaximo()) {
				//Se actualiza la longitud de la columna donde se registrará información del componente actual (Solo campos de texto)
				estructuraFormularioDAO.actualizarColumnaCampoTextoLongitud(subSeccionTmp.getSeccionesFormularioDTO(), campoTextoDTO);
			}
		}
		//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
		if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), campoTextoDTO, campoTextoDTO.getTituloCampo(), 1)) {
			estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), campoTextoDTO, campoTextoDTO.getTituloCampo(), 1);
		} else {
			estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), campoTextoDTO, campoTextoDTO.getTituloCampo(), 1);
		}
	}
	
}

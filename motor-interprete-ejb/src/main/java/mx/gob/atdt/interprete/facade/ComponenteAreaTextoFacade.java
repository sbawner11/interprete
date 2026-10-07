package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.ComponenteAreaTextoDAO;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteAreaTextoDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;


@Stateless
@LocalBean
public class ComponenteAreaTextoFacade {

	@Inject
	private ComponenteDAO componenteDAO;
	
	@Inject
	private ComponenteAreaTextoDAO areaTextoDAO;
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteAreaTextoDTO areaTextoDTO) {
		//Obtener información actual del componente de area de texto
		ComponenteAreaTextoDTO areaTextoActual = null;
		if(areaTextoDTO.getIdComponenteAreaTexto() != Constantes.INT_VALOR_CERO) {
			areaTextoActual = areaTextoDAO.buscarPorId(areaTextoDTO.getIdComponenteAreaTexto());
		}
		
		//Se actualiza el componente
		componenteDAO.actualizar(areaTextoDTO);
		
		//Se actualiza la información del componente area de texto
		areaTextoDAO.actualizar(areaTextoDTO);
		
		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(areaTextoDTO.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
		
		//Se genera la columa en BD donde se registrará información del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), areaTextoDTO, false)) {
			estructuraFormularioDAO.creaNuevaColumnaAreaTexto(subSeccionTmp.getSeccionesFormularioDTO(), areaTextoDTO);
		}
		//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
		if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), areaTextoDTO, areaTextoDTO.getTituloCampo(), 1)) {
			estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), areaTextoDTO, areaTextoDTO.getTituloCampo(), 1);
		} else {
			estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), areaTextoDTO, areaTextoDTO.getTituloCampo(), 1);
		}
	}
	
}

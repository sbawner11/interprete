package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.ComponenteCargaDocumentosDAO;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteCargaDocumentosDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;

@Stateless
@LocalBean
public class ComponenteCargaDocumentosFacade {

	@Inject
	private ComponenteDAO componenteDAO;

	@Inject
	private ComponenteCargaDocumentosDAO componenteCargaDocumentoDAO;
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;
	
	@Inject
	private EstructuraFormularioDAO  estructuraFormularioDAO;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteCargaDocumentosDTO componenteCargaDocumentosDTO) {
		
		// Se actualiza el componente
		componenteDAO.actualizar(componenteCargaDocumentosDTO);
		
		// Se actualiza la información del componente de Carga de documentos
		componenteCargaDocumentoDAO.actualizar(componenteCargaDocumentosDTO);

		//Obtener la seccion a la que pertenece el componente actual
		SubSeccionesFormularioDTO subSeccionTmp = subSeccionesFormularioDAO.buscarSeccionPorIdSubseccion(componenteCargaDocumentosDTO.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()); 
								
		//Se genera la columa en BD donde se registrará inforación del componente actual
		if(!estructuraFormularioDAO.existeColumnaTabla(subSeccionTmp.getSeccionesFormularioDTO(), componenteCargaDocumentosDTO, false)) {
			estructuraFormularioDAO.creaNuevaColumnaCargaDocumentos(subSeccionTmp.getSeccionesFormularioDTO(), componenteCargaDocumentosDTO);
		}
		
		//Se genera el registro en BD en la tabla control para el mapeo de columnas que servira en el insert del formulario creado de la seccion
		if(!estructuraFormularioDAO.existeRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteCargaDocumentosDTO, componenteCargaDocumentosDTO.getTituloCampo(), 1)) {
			estructuraFormularioDAO.guardarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteCargaDocumentosDTO, componenteCargaDocumentosDTO.getTituloCampo(), 1);
		} else {
			estructuraFormularioDAO.actualizarRegistroTablaControl(subSeccionTmp.getSeccionesFormularioDTO(), componenteCargaDocumentosDTO, componenteCargaDocumentosDTO.getTituloCampo(), 1);
		}
	}
}
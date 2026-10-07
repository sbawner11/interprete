package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.DetFirmaDigitalDAO;
import mx.gob.atdt.interprete.dto.DetFirmaDigitalDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;

@Stateless
@LocalBean
public class SeccionFirmaDigitalFacade {

	@Inject
	private DetFirmaDigitalDAO detFirmaDigitalDAO;
	
	@Inject
	private EstructuraFormularioDAO estructuraFormularioDAO;


	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(DetFirmaDigitalDTO firmaDTO) throws Exception {
		
		//Se actualiza la información del apartado de firma digital
		detFirmaDigitalDAO.actualizar(firmaDTO);
		
		//Se agrega intento de creación de tabla de trámites para firmado, el sincronizar cambios del apartado 
		//de firma digital
		if(estructuraFormularioDAO.existeTablaTramites()) {
			if(!estructuraFormularioDAO.existeTablaTramitesFirma()) {
				estructuraFormularioDAO.creaTablaTramitesFirma();
				estructuraFormularioDAO.creaSecuenciaTablaFirmaTramites();
			}	
		}			
	}
}

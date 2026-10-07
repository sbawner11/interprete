package mx.gob.atdt.interprete.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.ComponenteInformativoDAO;
import mx.gob.atdt.interprete.dto.ComponenteInformativoDTO;


@Stateless
@LocalBean
public class ComponenteInformativoFacade {

	@Inject
	private ComponenteDAO componenteDAO;
	
	@Inject
	private ComponenteInformativoDAO campoInfoDAO;	
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void actualizar(ComponenteInformativoDTO campoInfoDTO) {
		//Se actualiza componente
		componenteDAO.actualizar(campoInfoDTO);
		//Se actualiza información del componente informativo
		campoInfoDAO.actualizar(campoInfoDTO);
	}
}

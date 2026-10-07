package mx.gob.atdt.interprete.tramites.facade;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.BitRevertirEstatusDAO;
import mx.gob.atdt.interprete.dto.BitRevertirEstatusDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.formulario.dao.FormularioDAO;

@Stateless
@LocalBean
public class TramitesFacade {
		
	@Inject
	FormularioDAO formularioDAO;
		
	@Inject
	BitRevertirEstatusDAO bitRevertirEstatusDAO;	
		
	/**
	 * Método auxiliar que realiza el registro en bítacora de los trámites a los que se les actualiza su estatus
	 * 
	 * @param tramite
	 * @param bitacora
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void revertirEstatusTramite(TramiteDTO tramite, BitRevertirEstatusDTO bitacora) {
		//1. Se consulta consecutivo de bitácora
		bitacora.setIdReversion(formularioDAO.consultarConsecutivoBitacoraRevertir());
		
		//2. Se elimina la respuesta de prevención o conclusión, dependiendo el estatus actual del trámite.
		if(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_CORRECIONES) {
			formularioDAO.eliminaRespuestaPrevencionTramite(tramite);	
		}
		if(tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO || 
				tramite.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO) {
			formularioDAO.eliminaRespuestaConclusionTramite(tramite);	
		}	
		
		//3. Se registra actualiza el estatus del trámite.
		formularioDAO.actualizarEstatusTramite(tramite);		
		
		//4. Se elimina el registro del firmado si es que el trámite ya se encontraba firmado.
		if(tramite.isTramiteFirmado()) {			
			formularioDAO.eliminaFirmadoTramite(tramite);	
		}			
		
		//5. Se registra movimiento en bitácora.
		bitRevertirEstatusDAO.registrarBitacora(bitacora);
	}	
	
	/**
	 * Método auxiliar que realiza el registro en bítacora de los trámites a los que se les actualiza su estatus
	 * de Expedido a Revocado 
	 * 
	 * @param tramite
	 * @param bitacora
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void revocarAviso(TramiteDTO tramite, BitRevertirEstatusDTO bitacora) {
		//1. Se consulta consecutivo de bitácora
		bitacora.setIdReversion(formularioDAO.consultarConsecutivoBitacoraRevertir());
		
		//2.1. Se actualiza el estatus del trámite.
		tramite.setCatEstatusTramiteDTO(tramite.getCatEstatusTramiteDTO());
		formularioDAO.actualizarEstatusTramite(tramite);	
		
		//2.2. Se realiza la actualización de los campos referentes a la revocación del aviso.
		formularioDAO.realizarActualizacionRevocacionAviso(tramite);
		
		//3. Se registra movimiento en bitácora.
		bitRevertirEstatusDAO.registrarBitacora(bitacora);
		
	}	
}

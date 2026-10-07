package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.BitRevertirEstatusDTO;

@Stateless
@LocalBean
public class BitRevertirEstatusDAO {

	@Inject
	@PersistenceContext
	protected EntityManager em;
	
	/**
	 * Método que realiza el registro de los movimientos en bítacora de las actualizaciones de trámites.
	 * @param bitacoraDTO
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void registrarBitacora(BitRevertirEstatusDTO bitacoraDTO) {
		final StringBuilder strQueryInsert = new StringBuilder();
	
		strQueryInsert.append("INSERT INTO ").append(Constantes.ESQUEMA_INTERPRETE).append(".")
			.append(Constantes.NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS)
			.append(" (id_reversion, id_tramite, id_usuario_llave_cdmx_revierte, id_estatus_revertido, respuesta_prevencion_conclusion, fecha_reversion) ")
			.append("VALUES (:idReversion, :idTramite, :idUsuarioRevierte, :idEstatusRevertido, :respuestaPrevencionConclusion, :fechaReversion)");

		Query query = em.createNativeQuery(strQueryInsert.toString());
		query.setParameter("idReversion", bitacoraDTO.getIdReversion());
		query.setParameter("idTramite", bitacoraDTO.getTramite().getIdTramite());
		query.setParameter("idUsuarioRevierte", bitacoraDTO.getUsuarioDTO().getIdUsuarioLlaveCdmx());
		query.setParameter("idEstatusRevertido", bitacoraDTO.getEstatusTramiteDTO().getIdEstatusTramite());

		if (BeanUtils.isNotNull(bitacoraDTO.getRespuestaPrevencionConclusion()) && BeanUtils.isNotEmpty(bitacoraDTO.getRespuestaPrevencionConclusion())) {
			query.setParameter("respuestaPrevencionConclusion", bitacoraDTO.getRespuestaPrevencionConclusion());
		} else {
			query.setParameter("respuestaPrevencionConclusion", null);
		}
		query.setParameter("fechaReversion", bitacoraDTO.getFechaReversion());
		
		query.executeUpdate();
	}
}

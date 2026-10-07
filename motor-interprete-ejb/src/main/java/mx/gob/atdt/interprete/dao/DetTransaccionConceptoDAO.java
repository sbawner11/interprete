package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetTransaccionConceptoDTO;
import mx.gob.atdt.interprete.model.DetConceptosTramite;
import mx.gob.atdt.interprete.model.DetTransaccionConcepto;

@Stateless
@LocalBean
public class DetTransaccionConceptoDAO extends IBaseService<DetTransaccionConceptoDTO, Long> {

	@Override
	public DetTransaccionConceptoDTO buscarPorId(Long id) {
		return null;
	}

	@Override
	public void actualizar(DetTransaccionConceptoDTO e) {
		DetTransaccionConcepto detTransaccionConcepto = new DetTransaccionConcepto();
		
		detTransaccionConcepto.setIdTransaccionConcepto(e.getIdTransaccionConcepto());
		detTransaccionConcepto.setDetConceptosTramite(em.getReference(DetConceptosTramite.class, e.getDetConceptosTramiteDTO().getIdConceptoTramite()));
		detTransaccionConcepto.setClave(e.getClave());
		detTransaccionConcepto.setValor(e.getValor());
		detTransaccionConcepto.setActualizacion(e.isActualizacion());
		detTransaccionConcepto.setRecargo(e.isRecargo());
		detTransaccionConcepto.setMulta(e.isMulta());
		detTransaccionConcepto.setIdUsuarioRegistro(e.getIdUsuarioRegistro());
		detTransaccionConcepto.setFechaCreacion(e.getFechaCreacion());
		detTransaccionConcepto.setFechaActualizacion(e.getFechaActualizacion());
		detTransaccionConcepto.setActivo(e.isActivo());
		detTransaccionConcepto.setSeccionSincronizada(e.isSeccionSincronizada());
		
		em.merge(detTransaccionConcepto);		
	}
	
	/**
	 * Método auxiliar que realiza la consulta del detalle de transacciones mediante el IdConceptoTramite
	 * @param idConceptoTramite
	 * @return
	 */
	public List<DetTransaccionConceptoDTO> buscarPorIdConceptoTramite(Long idConceptoTramite) {
		List<DetTransaccionConceptoDTO> listado = em.createNamedQuery("DetTransaccionConcepto.findByIdConcepto", DetTransaccionConceptoDTO.class)
				.setParameter("idConceptoTramite", idConceptoTramite)
				.getResultList();		
		return  listado != null && !listado.isEmpty() ? listado : null;
	}

}

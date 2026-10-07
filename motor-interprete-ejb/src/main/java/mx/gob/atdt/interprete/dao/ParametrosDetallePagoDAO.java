package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ParametrosDetallePagoDTO;
import mx.gob.atdt.interprete.model.DetPago;
import mx.gob.atdt.interprete.model.ParametrosDetallePago;


@Stateless
@LocalBean
public class ParametrosDetallePagoDAO extends IBaseService<ParametrosDetallePagoDTO, Long> {

	@Override
	public ParametrosDetallePagoDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(ParametrosDetallePagoDTO dto) {		
		ParametrosDetallePago parametrosDetallePago = new ParametrosDetallePago(); 		
		
		parametrosDetallePago.setIdParametro(dto.getIdParametro());
		parametrosDetallePago.setDetPago(em.getReference(DetPago.class, dto.getDetPagoDTO().getIdDetallePago()));
		parametrosDetallePago.setNombreParametro(dto.getNombreParametro());
		parametrosDetallePago.setValorParametro(dto.getValorParametro());
		parametrosDetallePago.setFechaCreacion(dto.getFechaCreacion());
		parametrosDetallePago.setFechaUltimaActualizacion(dto.getFechaUltimaActualizacion());
		parametrosDetallePago.setActivo(dto.isActivo());
				
		em.merge(parametrosDetallePago);		
	}

}

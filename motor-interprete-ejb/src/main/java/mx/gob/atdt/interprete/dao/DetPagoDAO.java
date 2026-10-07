package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetPagoDTO;
import mx.gob.atdt.interprete.model.CatTipoCosto;
import mx.gob.atdt.interprete.model.DetPago;
import mx.gob.atdt.interprete.model.Proyecto;


@Stateless
@LocalBean
public class DetPagoDAO extends IBaseService<DetPagoDTO, Long> {

	@Override
	public DetPagoDTO buscarPorId(Long id) {
		List<DetPagoDTO> listado = em.createNamedQuery("DetPago.findById", DetPagoDTO.class)
				.setParameter("idDetallePago", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(DetPagoDTO dto) {		
		DetPago detPago =	new DetPago(); 
		
		detPago.setIdDetallePago(dto.getIdDetallePago());
		detPago.setCatTipoCosto(em.getReference(CatTipoCosto.class, dto.getCatTipoCostoDTO().getIdTipoCosto()));
		detPago.setProyecto(em.getReference(Proyecto.class, dto.getProyectoDTO().getIdProyecto()));
		detPago.setMontoCostoFijo(dto.getMontoCostoFijo());
		detPago.setUrlServicio(dto.getUrlServicio());
		detPago.setIdentificadorProceso(dto.getIdentificadorProceso());
		detPago.setFechaCreacion(dto.getFechaCreacion());
		detPago.setFechaUltimaActualizacion(dto.getFechaUltimaActualizacion());
		detPago.setActivo(dto.isActivo());
		detPago.setSeccionSincronizada(dto.isSeccionSincronizada());
				
		em.merge(detPago);	
	}
	
}

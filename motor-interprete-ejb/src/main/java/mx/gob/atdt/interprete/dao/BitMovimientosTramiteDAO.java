package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.BitMovimientosTramiteDTO;
import mx.gob.atdt.interprete.model.BitMovimientosTramite;
import mx.gob.atdt.interprete.model.CatTiposMovimiento;
import mx.gob.atdt.interprete.model.Usuario;

@Stateless
@LocalBean
public class BitMovimientosTramiteDAO extends IBaseService<BitMovimientosTramiteDTO, Long> {

	@Override
	public BitMovimientosTramiteDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public BitMovimientosTramiteDTO buscarPorIdtramiteYEstatus(long idTramite, int idEstatus) {
		List<BitMovimientosTramiteDTO> listado = em.createNamedQuery("BitMovimientosTramite.findByIdTramiteAndIdEstatus", BitMovimientosTramiteDTO.class)
				.setParameter("idTramite", idTramite)				
				.setParameter("comentario", "Se actualiza estatus a : " + idEstatus + "%")
				.getResultList();
		return !listado.isEmpty() ? listado.get(0) : null;
	}
	
	public List<BitMovimientosTramiteDTO> buscarPorIdTramite(long idTramite){
		return em.createNamedQuery("BitMovimientosTramite.findByIdTramite", BitMovimientosTramiteDTO.class)
				.setParameter("idTramite", idTramite)
				.getResultList();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(BitMovimientosTramiteDTO e) {
		BitMovimientosTramite bitMovTramite = new BitMovimientosTramite();	
		bitMovTramite.setCatTiposMovimiento(em.getReference(CatTiposMovimiento.class, e.getCatTiposMovimientoDTO().getIdTipoMovimiento()));
		bitMovTramite.setUsuario(e.getUsuarioDTO() != null ? em.getReference(Usuario.class, e.getUsuarioDTO().getIdUsuarioLlaveCdmx()) : null);
		bitMovTramite.setIdTramite(e.getIdTramite());	
		bitMovTramite.setComentarios(e.getComentarios());
		bitMovTramite.setFechaMovimiento(e.getFechaMovimiento());
		
		em.merge(bitMovTramite);		
	}	
}

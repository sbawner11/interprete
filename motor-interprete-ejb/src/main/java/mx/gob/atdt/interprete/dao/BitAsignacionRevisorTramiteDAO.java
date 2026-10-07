package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.BitAsignacionRevisorTramiteDTO;
import mx.gob.atdt.interprete.model.BitAsignacionRevisorTramite;
import mx.gob.atdt.interprete.model.Usuario;

@Stateless
@LocalBean
public class BitAsignacionRevisorTramiteDAO extends IBaseService<BitAsignacionRevisorTramiteDTO, Long>{

	@Override
	public BitAsignacionRevisorTramiteDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(BitAsignacionRevisorTramiteDTO e) {
		// TODO Auto-generated method stub
		
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardar(BitAsignacionRevisorTramiteDTO e) {
		BitAsignacionRevisorTramite bitAsignacionRevisorTramite = new BitAsignacionRevisorTramite();
		bitAsignacionRevisorTramite.setFechaAsignacion(e.getFechaAsignacion());
		bitAsignacionRevisorTramite.setIdTramite(e.getTamiteDTO().getIdTramite());
		bitAsignacionRevisorTramite.setUsuarioAsigna(em.getReference(Usuario.class, e.getUsuarioAsignaDTO().getIdUsuarioLlaveCdmx()));
		bitAsignacionRevisorTramite.setUsuarioRevisor(em.getReference(Usuario.class, e.getUsuarioRevisorDTO().getIdUsuarioLlaveCdmx()));
		em.persist(bitAsignacionRevisorTramite);
	}

}

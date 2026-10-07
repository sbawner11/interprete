package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.BitSincronizacionDTO;
import mx.gob.atdt.interprete.model.BitSincronizacion;
import mx.gob.atdt.interprete.model.Usuario;

@Stateless
@LocalBean
public class BitSincronizacionDAO extends IBaseService<BitSincronizacionDTO, Long> {


	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardar(BitSincronizacionDTO e) {
		BitSincronizacion bitSincro = new BitSincronizacion();
		bitSincro.setFechaSincronizacion(e.getFechaSincronizacion());
		bitSincro.setUsuario(e.getUsuarioDTO() != null ? em.getReference(Usuario.class, e.getUsuarioDTO().getIdUsuarioLlaveCdmx()) : null);
		em.persist(bitSincro);
	}
	
    @Override
    public BitSincronizacionDTO buscarPorId(Long id) {
        return  null;
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(BitSincronizacionDTO e) {
		BitSincronizacion bitSincro = new BitSincronizacion();			
		bitSincro.setIdSincronizacion(e.getIdSincronizacion());
		bitSincro.setUsuario(e.getUsuarioDTO() != null ? em.getReference(Usuario.class, e.getUsuarioDTO().getIdUsuarioLlaveCdmx()) : null);
		bitSincro.setFechaSincronizacion(e.getFechaSincronizacion());
		em.merge(bitSincro);		
	}	

}
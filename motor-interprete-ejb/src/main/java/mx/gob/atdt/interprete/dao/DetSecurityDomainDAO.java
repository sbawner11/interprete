package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetSecurityDomainDTO;
import mx.gob.atdt.interprete.model.CatTipoSecurityDomain;
import mx.gob.atdt.interprete.model.DetSecurityDomain;
import mx.gob.atdt.interprete.model.Proyecto;

@Stateless
@LocalBean
public class DetSecurityDomainDAO extends IBaseService<DetSecurityDomainDTO, Long> {

	@Override
	public DetSecurityDomainDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(DetSecurityDomainDTO e) {
		DetSecurityDomain detSecurity = new DetSecurityDomain();
		detSecurity.setidDetalleSecurity(e.getidDetalleSecurity());
		detSecurity.setCatTipoSecurityDomain(em.getReference(CatTipoSecurityDomain.class, e.getCatTipoSecurityDomainDTO().getIdTipoSecurityDomain()));
		detSecurity.setProyecto(em.getReference(Proyecto.class, e.getProyectoDTO().getIdProyecto()));
		detSecurity.setUsuario(e.getUsuario());
		detSecurity.setContrasenia(e.getContrasenia());
		detSecurity.setUrlSistema(e.getUrlSistema());
		detSecurity.setFechaCreacion(e.getFechaCreacion());
		detSecurity.setFechaUltimaActualizacion(e.getFechaUltimaActualizacion());
		detSecurity.setActivo(e.isActivo());
				
		em.merge(detSecurity);
		
	}

	public DetSecurityDomainDTO buscarPorIdProyecto(Long id, Integer idTipoSecurityDomain) {
		List<DetSecurityDomainDTO> listado = em.createNamedQuery("DetSecurityDomain.findByIdProyecto", DetSecurityDomainDTO.class)
				.setParameter("idProyecto", id)
				.setParameter("idTipoSecurityDomain", idTipoSecurityDomain).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

}

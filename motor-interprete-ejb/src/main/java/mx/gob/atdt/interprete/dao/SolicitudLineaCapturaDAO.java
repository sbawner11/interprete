package mx.gob.atdt.interprete.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.sat.dto.SolicitudLineaCapturaDTO;
import mx.gob.atdt.interprete.model.CatEstatusSolicitud;
import mx.gob.atdt.interprete.model.SolicitudLineaCaptura;
import mx.gob.atdt.interprete.model.Tramites;

@Stateless
@LocalBean
public class SolicitudLineaCapturaDAO extends IBaseService<SolicitudLineaCapturaDTO, Long> {

    
    @Override
    public void actualizar(SolicitudLineaCapturaDTO dto) {
        SolicitudLineaCaptura solicitud = new SolicitudLineaCaptura();
        solicitud.setIdSolicitudLineaCaptura(dto.getIdSolicitudLineaCaptura());
        solicitud.setCatEstatusSolicitud(em.getReference(CatEstatusSolicitud.class, 
                                      dto.getCatEstatusSolicitud().getIdEstatusSolicitud()));
        solicitud.setTramites(em.getReference(Tramites.class, 
                           dto.getTramite().getIdTramite()));
        solicitud.setSolicitudLineaCaptura(dto.getSolicitudLineaCaptura());
        solicitud.setRequestServicioLc(dto.getRequestServicioLc());
        solicitud.setRespuestaServicioLc(dto.getRespuestaServicioLc());
        solicitud.setFechaCreacion(dto.getFechaCreacion() != null ? 
                               dto.getFechaCreacion() : new Date());
        
        em.merge(solicitud);
    }
    
    public void guardar(SolicitudLineaCapturaDTO dto) {
        SolicitudLineaCaptura entity = new SolicitudLineaCaptura();
        
        entity.setCatEstatusSolicitud(em.getReference(CatEstatusSolicitud.class, 
                                      dto.getCatEstatusSolicitud().getIdEstatusSolicitud()));
        entity.setTramites(em.getReference(Tramites.class, 
                           dto.getTramite().getIdTramite()));
        entity.setSolicitudLineaCaptura(dto.getSolicitudLineaCaptura());
        entity.setRequestServicioLc(dto.getRequestServicioLc());
        entity.setRespuestaServicioLc(dto.getRespuestaServicioLc());
        entity.setFechaCreacion(dto.getFechaCreacion() != null ? 
                               dto.getFechaCreacion() : new Date());
        
        em.persist(entity);
        dto.setIdSolicitudLineaCaptura(entity.getIdSolicitudLineaCaptura());
    }
    
    public List<SolicitudLineaCapturaDTO> buscarSolicitudMasRecientePorTramite(final Long idTramite,
    		final int tamanioRegistros) {
		try {
			return em.createNamedQuery("SolicitudLineaCaptura.findSolicitudLineaCapturaOrderByIdDesc", 
					SolicitudLineaCapturaDTO.class)
					.setParameter("idTramite", idTramite)
					.setMaxResults(tamanioRegistros).getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public SolicitudLineaCapturaDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
    
  
}
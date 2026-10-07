package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetAnalyticsDTO;
import mx.gob.atdt.interprete.model.DetAnalytics;
import mx.gob.atdt.interprete.model.Proyecto;

@Stateless
@LocalBean
public class DetAnalyticsDAO extends IBaseService<DetAnalyticsDTO, Long>{

	@Override
	public DetAnalyticsDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(DetAnalyticsDTO dto) {
		DetAnalytics detAnalytics = new DetAnalytics();
		
		detAnalytics.setIdDetalleAnalytics(dto.getIdDetalleAnalytics());
		detAnalytics.setProyecto(em.getReference(Proyecto.class, dto.getProyectoDTO().getIdProyecto()));
		detAnalytics.setIdentificadorAnalytics(dto.getIdentificadorAnalytics());
		detAnalytics.setTituloBusqueda(dto.getTituloBusqueda());
		detAnalytics.setDescripcionBusqueda(dto.getDescripcionBusqueda());
		detAnalytics.setPalabraClaveBusqueda(dto.getPalabraClaveBusqueda());
		detAnalytics.setTituloGrap(dto.getTituloGrap());
		detAnalytics.setDescripcionGrap(dto.getDescripcionGrap());
		detAnalytics.setUrlGrap(dto.getUrlGrap());
		detAnalytics.setRutaImagenGrap(dto.getRutaImagenGrap());
		detAnalytics.setFechaCreacion(dto.getFechaCreacion());
		detAnalytics.setFechaUltimaActualizacion(dto.getFechaUltimaActualizacion());
		detAnalytics.setActivo(dto.isActivo());
		detAnalytics.setSeccionSincronizada(dto.isSeccionSincronizada());
		
		em.merge(detAnalytics);
	}
	
}

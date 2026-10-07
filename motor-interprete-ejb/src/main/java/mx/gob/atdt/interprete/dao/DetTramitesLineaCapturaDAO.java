package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetTramitesLineaCapturaDTO;
import mx.gob.atdt.interprete.model.DetLineaCaptura;
import mx.gob.atdt.interprete.model.DetTramitesLineaCaptura;

@Stateless
@LocalBean
public class DetTramitesLineaCapturaDAO extends IBaseService<DetTramitesLineaCapturaDTO, Long> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DetTramitesLineaCapturaDAO.class);
	
	@Override
	public DetTramitesLineaCapturaDTO buscarPorId(Long id) {
		return null;
	}
	
	/**
	 * Método que realiza la búsqueda del detalle del trámite de línea de capura mediante el idDetalleLineaCaptura
	 * @param idDetalleLineaCaptura
	 * @return
	 */
	public DetTramitesLineaCapturaDTO buscarPorIdProyecto(Long idDetalleLineaCaptura) {
		List<DetTramitesLineaCapturaDTO> lstTramiteLineasCaptura = em.createNamedQuery("DetTramitesLineaCaptura.findByLineaCaptura", DetTramitesLineaCapturaDTO.class)
				.setParameter("idDetalleLineaCaptura", idDetalleLineaCaptura)				
				.getResultList();
		return BeanUtils.isNotEmpty(lstTramiteLineasCaptura) ? lstTramiteLineasCaptura.get(0) : null;
	}

	@Override
	public void actualizar(DetTramitesLineaCapturaDTO e) {
		DetTramitesLineaCaptura detTramitesLineaCaptura = new DetTramitesLineaCaptura();
		detTramitesLineaCaptura.setIdTramiteLineaCaptura(e.getIdTramiteLineaCaptura());
		detTramitesLineaCaptura.setDetLineaCaptura(em.getReference(DetLineaCaptura.class, e.getDetLineaCapturaDTO().getIdDetalleLineaCaptura()));
		detTramitesLineaCaptura.setHomoclave(e.getHomoclave());
		detTramitesLineaCaptura.setVariante(e.getVariante());
		detTramitesLineaCaptura.setDescripcion(e.getDescripcion());
		detTramitesLineaCaptura.setImporte(e.getImporte());
		detTramitesLineaCaptura.setNumeroConceptos(e.getNumeroConceptos());
		detTramitesLineaCaptura.setIdUsuarioRegistro(e.getIdUsuarioRegistro());
		detTramitesLineaCaptura.setFechaCreacion(e.getFechaCreacion());
		detTramitesLineaCaptura.setFechaActualizacion(e.getFechaActualizacion());
		detTramitesLineaCaptura.setActivo(e.isActivo());
		detTramitesLineaCaptura.setSeccionSincronizada(e.isSeccionSincronizada());
		
		em.merge(detTramitesLineaCaptura);
	}
	
	/**
	 * Método auxiliar que realiza la consulta del tramites mediante el idDetalleLineaCaptura
	 * @param idDetalleLineaCaptura
	 * @return
	 */
	public List<DetTramitesLineaCapturaDTO> buscarPorIdDetalleLineaCaptura(Long idDetalleLineaCaptura) {
		List<DetTramitesLineaCapturaDTO> listado = em.createNamedQuery("DetTramitesLineaCaptura.findByLineaCaptura", DetTramitesLineaCapturaDTO.class)
				.setParameter("idDetalleLineaCaptura", idDetalleLineaCaptura)
				.getResultList();		
		return  listado != null && !listado.isEmpty() ? listado : null;
	}
}

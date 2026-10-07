package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetSecurityDomainLineasCapturaDTO;
import mx.gob.atdt.interprete.model.DetLineaCaptura;
import mx.gob.atdt.interprete.model.DetSecurityDomainLineasCaptura;

@Stateless
@LocalBean
public class DetSecurityDomainLineasCapturaDAO extends IBaseService<DetSecurityDomainLineasCapturaDTO, Long> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DetSecurityDomainLineasCapturaDAO.class);
	
	@Override
	public DetSecurityDomainLineasCapturaDTO buscarPorId(Long id) {
		return null;
	}	
	
	@Override
	public void actualizar(DetSecurityDomainLineasCapturaDTO e) {
		DetSecurityDomainLineasCaptura detSecurityDomainLineasCaptura = new DetSecurityDomainLineasCaptura();
		detSecurityDomainLineasCaptura.setIdSecurityDomainLc(e.getIdSecurityDomainLc());
		detSecurityDomainLineasCaptura.setDetLineaCaptura(em.getReference(DetLineaCaptura.class, e.getDetLineaCapturaDTO().getIdDetalleLineaCaptura()));
		detSecurityDomainLineasCaptura.setUsuario(e.getUsuario());
		detSecurityDomainLineasCaptura.setContrasenia(e.getContrasenia());
		detSecurityDomainLineasCaptura.setUrlSistema(e.getUrlSistema());
		detSecurityDomainLineasCaptura.setIdUsuarioRegistro(e.getIdUsuarioRegistro());
		detSecurityDomainLineasCaptura.setFechaCreacion(e.getFechaCreacion());
		detSecurityDomainLineasCaptura.setFechaUltimaActualizacion(e.getFechaUltimaActualizacion());
		detSecurityDomainLineasCaptura.setActivo(e.isActivo());
		detSecurityDomainLineasCaptura.setSeccionSincronizada(e.isSeccionSincronizada());
		
		em.merge(detSecurityDomainLineasCaptura);
	}
	
	/**
	 * Método auxiliar que realiza la consulta del detalle de seguridad de dominio mediante el IdDetalleLineaCaptura
	 * @param idDetalleLineaCaptura
	 * @return
	 */
	public DetSecurityDomainLineasCapturaDTO buscarPorIdDetalleLineaCaptura(Long idDetalleLineaCaptura) {
		List<DetSecurityDomainLineasCapturaDTO> listado = em.createNamedQuery("DetSecurityDomainLineasCaptura.findByIdLineaCaptura", DetSecurityDomainLineasCapturaDTO.class)
				.setParameter("idDetalleLineaCaptura", idDetalleLineaCaptura)
				.getResultList();		
		return  listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
	
	public DetSecurityDomainLineasCapturaDTO buscarPorIdProyecto(Long idProyecto) {
		List<DetSecurityDomainLineasCapturaDTO> listado = em.createNamedQuery("DetSecurityDomainLineasCaptura.findByIdProyecto", DetSecurityDomainLineasCapturaDTO.class)
				.setParameter("idProyecto", idProyecto)
				.getResultList();		
		return  listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
}
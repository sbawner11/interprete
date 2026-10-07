package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetLineaCapturaDTO;
import mx.gob.atdt.interprete.model.CatDependenciaPago;
import mx.gob.atdt.interprete.model.CatTipoPersona;
import mx.gob.atdt.interprete.model.CatTipoVigencia;
import mx.gob.atdt.interprete.model.CatUnidadAdministrativaPago;
import mx.gob.atdt.interprete.model.DetLineaCaptura;
import mx.gob.atdt.interprete.model.Proyecto;

@Stateless
@LocalBean
public class DetLineaCapturaDAO extends IBaseService<DetLineaCapturaDTO, Long> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DetLineaCapturaDAO.class);

	@Override
	public DetLineaCapturaDTO buscarPorId(Long id) {		
		return null;
	}
	
	@Override
	public void actualizar(DetLineaCapturaDTO e) {
		DetLineaCaptura lineaCaptura = new DetLineaCaptura();
		lineaCaptura.setIdDetalleLineaCaptura(e.getIdDetalleLineaCaptura());
		lineaCaptura.setProyecto(em.getReference(Proyecto.class, e.getProyectoDTO().getIdProyecto()));
		lineaCaptura.setDependenciaPago(em.getReference(CatDependenciaPago.class, e.getDependenciaPagoDTO().getIdDependenciaPago()));
		lineaCaptura.setUnidadAdministrativaPago(em.getReference(CatUnidadAdministrativaPago.class, e.getUnidadAdministrativaPagoDTO().getIdUnidadAdministrativaPago()));
		lineaCaptura.setVigencia(e.getVigencia());
		lineaCaptura.setTipoVigencia(em.getReference(CatTipoVigencia.class, e.getTipoVigenciaDTO().getIdTipoVigencia()));
		lineaCaptura.setTipoPersona(em.getReference(CatTipoPersona.class, e.getTipoPersonaDTO().getIdTipoPersona()));
		lineaCaptura.setIdUsuarioRegistro(e.getIdUsuarioRegistro());
		lineaCaptura.setFechaCreacion(e.getFechaCreacion());
		lineaCaptura.setFechaActualizacion(e.getFechaActualizacion());
		lineaCaptura.setCompleto(e.isCompleto());
		lineaCaptura.setActivo(e.isActivo());
		lineaCaptura.setSeccionSincronizada(e.isSeccionSincronizada());
		
		em.merge(lineaCaptura);
	}
	
	/**
	 * Método que realiza la búsqueda de detalle de Línea de captura mediante el IdProyecto
	 * @param idProyecto
	 * @return
	 */
	public DetLineaCapturaDTO buscarPorIdProyecto(Long idProyecto) {
		List<DetLineaCapturaDTO> lstLineasCaptura = em.createNamedQuery("DetLineaCaptura.findByIdProyecto", DetLineaCapturaDTO.class)
				.setParameter("idProyecto", idProyecto)				
				.getResultList();
		return BeanUtils.isNotEmpty(lstLineasCaptura) ? lstLineasCaptura.get(0) : null;
	}
	
	/**
	 * Método que realiza la búsqueda por idDetalleLineaCaptura
	 * @param idDetalleLineaCaptura
	 * @return
	 */
	public DetLineaCapturaDTO buscarPorIdDetalleLineaCaptura(Long idDetalleLineaCaptura) {
		List<DetLineaCapturaDTO> lstLineasCaptura = em.createNamedQuery("DetLineaCaptura.findById", DetLineaCapturaDTO.class)
				.setParameter("idDetalleLineaCaptura", idDetalleLineaCaptura)				
				.getResultList();
		return BeanUtils.isNotEmpty(lstLineasCaptura) ? lstLineasCaptura.get(0) : null;
	}
}

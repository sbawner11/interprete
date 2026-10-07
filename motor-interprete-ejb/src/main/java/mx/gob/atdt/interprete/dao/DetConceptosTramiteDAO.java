package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetConceptosTramiteDTO;
import mx.gob.atdt.interprete.model.CatEjercicio;
import mx.gob.atdt.interprete.model.CatPeriodicidad;
import mx.gob.atdt.interprete.model.CatPeriodo;
import mx.gob.atdt.interprete.model.CatTipoAgrupador;
import mx.gob.atdt.interprete.model.DetConceptosTramite;
import mx.gob.atdt.interprete.model.DetTramitesLineaCaptura;

@Stateless
@LocalBean
public class DetConceptosTramiteDAO extends IBaseService<DetConceptosTramiteDTO, Long> {

	@Override
	public DetConceptosTramiteDTO buscarPorId(Long id) {
		try {
			return em.createNamedQuery("DetConceptosTramite.findById", DetConceptosTramiteDTO.class)
				.setParameter("idConceptoTramite", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public void actualizar(DetConceptosTramiteDTO e) {
		DetConceptosTramite detConceptosTramite = new DetConceptosTramite();
		
		detConceptosTramite.setIdConceptoTramite(e.getIdConceptoTramite());
		detConceptosTramite.setDetTramitesLineaCaptura(em.getReference(DetTramitesLineaCaptura.class, e.getTramiteLineaCapturaDTO().getIdTramiteLineaCaptura()));
		detConceptosTramite.setSecuencia(e.getSecuencia());
		detConceptosTramite.setClave(e.getClave());
		detConceptosTramite.setAgrupador(e.getAgrupador());
		detConceptosTramite.setTipoAgrupador(em.getReference(CatTipoAgrupador.class, e.getTipoAgrupadorDTO().getIdTipoAgrupador()));
		detConceptosTramite.setCatPeriodicidad(em.getReference(CatPeriodicidad.class, e.getCatPeriodicidadDTO().getIdPeriodicidad()));
		detConceptosTramite.setPeriodo(em.getReference(CatPeriodo.class, e.getPeriodoDTO().getIdPeriodo()));
		if (e.getEjercicioDTO() != null  && e.getEjercicioDTO().getIdEjercicio() > 0) {
			detConceptosTramite.setEjercicio(em.getReference(CatEjercicio.class, e.getEjercicioDTO().getIdEjercicio()));
		} else {
			detConceptosTramite.setEjercicio(null);
		}	
		detConceptosTramite.setClaveContable(e.getClaveContable());
		detConceptosTramite.setImporte(e.getImporte());
		detConceptosTramite.setIdUsuarioRegistro(e.getIdUsuarioRegistro());
		detConceptosTramite.setFechaCreacion(e.getFechaCreacion());
		detConceptosTramite.setFechaActualizacion(e.getFechaActualizacion());
		detConceptosTramite.setActivo(e.isActivo());
		detConceptosTramite.setSeccionSincronizada(e.isSeccionSincronizada());
		
		em.merge(detConceptosTramite);
	}	
	
	/**
	 * Método auxiliar que realiza la consulta de los conceptos de trámites mediante el idConceptoTramite
	 * @param idConceptoTramite
	 * @return
	 */
	public List<DetConceptosTramiteDTO> buscarPorIdConceptoTramite(Long idConceptoTramite) {
		List<DetConceptosTramiteDTO> listado = em.createNamedQuery("DetConceptosTramite.findByIdConceptoTramite", DetConceptosTramiteDTO.class)
				.setParameter("idConceptoTramite", idConceptoTramite)
				.getResultList();		
		return  listado != null && !listado.isEmpty() ? listado : null;
	}
	
	public List<DetConceptosTramiteDTO> buscarConceptosPorTramite(Long idTramiteLineaCaptura) {
		List<DetConceptosTramiteDTO> listado = em.createNamedQuery("DetConceptosTramite.findByIdTramiteLineaDeCaptura", DetConceptosTramiteDTO.class)
				.setParameter("idTramiteLineaCaptura", idTramiteLineaCaptura)
				.getResultList();		
		return  listado != null && !listado.isEmpty() ? listado : null;
	}

}

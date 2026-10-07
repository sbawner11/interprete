package mx.gob.atdt.interprete.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.NoResultException;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.LineaCapturaDTO;
import mx.gob.atdt.interprete.model.CatEstatusLineaCaptura;
import mx.gob.atdt.interprete.model.LineaCaptura;
import mx.gob.atdt.interprete.model.Tramites;

@Stateless
@LocalBean
public class LineaCapturaDAO extends IBaseService<LineaCapturaDTO, Long> {

	@Override
	public LineaCapturaDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void actualizar(LineaCapturaDTO e) {
		LineaCaptura lCaptura = em.find(LineaCaptura.class, e.getIdLineaCaptura());
			lCaptura.setCatEstatusLineaCaptura(em.getReference(CatEstatusLineaCaptura.class, e.getCatEstatusLineaCaptura().getIdEstatusLineaCaptura()));
			lCaptura.setFechaPagoLc(e.getFechaPagoLc());
			lCaptura.setRespuestaServicioEstatus(e.getRespuestaServicioEstatus());
		em.merge(lCaptura);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void guardar(LineaCapturaDTO e) {
		LineaCaptura lCaptura = new LineaCaptura();
		lCaptura.setTramites(em.getReference(Tramites.class, e.getIdTramite()));
		lCaptura.setFechaCreacion(e.getFechaCreacion());
		lCaptura.setFechaPagoLc(e.getFechaPagoLc());
		lCaptura.setFechaVigencia(e.getFechaVigencia());
		lCaptura.setLineCaptura(e.getLineaCaptura());
		lCaptura.setCatEstatusLineaCaptura(em.getReference(CatEstatusLineaCaptura.class, e.getCatEstatusLineaCaptura().getIdEstatusLineaCaptura()));
		lCaptura.setRespuestaServicioEstatus(e.getRespuestaServicioEstatus());
		lCaptura.setMonto(e.getMonto());
		lCaptura.setRespuestaServicioEstatus(e.getRespuestaServicioEstatus());
		lCaptura.setRespuestaServicioLc(e.getRespuestaServicioLc());
		lCaptura.setRutaDocumentoLineaCaptura(e.getRutaDocumentoLineaCaptura());
		lCaptura.setSolicitudLineaCaptura(e.getSolicitudLineaCaptura());
		em.persist(lCaptura);
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Optional<LineaCapturaDTO> buscarLineaCapturaVigentePorTramite(long idTramite) {
		try {
			LineaCapturaDTO lineaCaptura = em.createNamedQuery("LineaCaptura.findLineaCapturaVigenteByIdTramite", 
					LineaCapturaDTO.class)
					.setParameter("idTramite", idTramite).getSingleResult();
			return Optional.ofNullable(lineaCaptura);
		} catch(NoResultException rse) {
			return Optional.empty();
		}
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<LineaCapturaDTO> buscarLineasDeCapturaPorTramitePorIdLineaCapturaDescendente(long idTramite) {
		try {
			return em.createNamedQuery("LineaCaptura.findLineaCapturaByIdTramiteOrderByIdDesc", 
					LineaCapturaDTO.class)
					.setParameter("idTramite", idTramite).getResultList();
		} catch(NoResultException rse) {
			return new ArrayList<>();
		}
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public List<LineaCapturaDTO> buscarLineasDeCapturaMasReciente(int tamanioRegistros) {
		try {
			return em.createNamedQuery("LineaCaptura.findLastLineaCaptura", 
					LineaCapturaDTO.class)
					.setMaxResults(tamanioRegistros)
					.getResultList();
		} catch(NoResultException rse) {
			return new ArrayList<>();
		}
	}
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Long obtenerSiguienteConsecutivoLineaCaptura() {
        BigInteger result = (BigInteger) em
                .createNativeQuery("SELECT nextval('motor_interprete.consecutivo_solicitud_linea_de_captura_seq')")
                .getSingleResult();

        return result.longValue();
    }
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Optional<LineaCapturaDTO> buscarLineaCapturaPorTramiteMax(long idTramite) {
		try {
			LineaCapturaDTO lineaCaptura = em.createNamedQuery("LineaCaptura.findLineaCapuraByIdTramiteMax", 
					LineaCapturaDTO.class)
					.setParameter("idTramite", idTramite).getSingleResult();
			return Optional.ofNullable(lineaCaptura);
		} catch(NoResultException rse) {
			return Optional.empty();
		}
	}
}

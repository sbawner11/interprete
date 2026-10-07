package mx.gob.atdt.interprete.dao;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.sat.dto.ControlSecuenciaLCDTO;

@Stateless
@LocalBean
public class ControlSecuenciaLCDAO extends IBaseService<ControlSecuenciaLCDTO, Long> {

	@Override
	public ControlSecuenciaLCDTO buscarPorId(Long id) {
		try {
			return em.createNamedQuery("ControlSecuenciaLC.findById", ControlSecuenciaLCDTO.class)
					.setParameter("idControl", id).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<ControlSecuenciaLCDTO> buscarTodos() {
		return em.createNamedQuery("ControlSecuenciaLC.findAll", ControlSecuenciaLCDTO.class).getResultList();
	}

	@Override
	public List<ControlSecuenciaLCDTO> buscarPorCriterios(ControlSecuenciaLCDTO e) {
		return em.createNamedQuery("ControlSecuenciaLC.findByDepUaAnio", ControlSecuenciaLCDTO.class)
				.setParameter("cveDependencia", e.getCveDependencia())
				.setParameter("unidadAdministrativa", e.getUnidadAdministrativa()).setParameter("anio", e.getAnio())
				.getResultList();
	}

	public ControlSecuenciaLCDTO buscarPorDepUaAnio(String cveDep, String uad, String anio) {
		try {
			return em.createNamedQuery("ControlSecuenciaLC.findByDepUaAnioUnico", ControlSecuenciaLCDTO.class)
					.setParameter("cveDependencia", cveDep).setParameter("unidadAdministrativa", uad)
					.setParameter("anio", anio).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public boolean existeSecuencia(String nombreSecuencia) {
		Query query = em.createQuery("SELECT COUNT(c) FROM ControlSecuenciaLCDTO c WHERE c.nombreSecuencia = :nombre");
		query.setParameter("nombre", nombreSecuencia);
		return ((Long) query.getSingleResult()) > 0;
	}

	// ⬇️ AQUÍ SÍ HAY em
	public void crearSecuenciaSiNoExiste(String nombreSecuencia, Long valorInicial) {
		Query query = em.createNativeQuery("SELECT interprete.crear_secuencia_si_no_existe(:nombre, :valor)");
		query.setParameter("nombre", nombreSecuencia);
		query.setParameter("valor", valorInicial);
		query.getSingleResult();
	}

	public void avanzarSecuencia(String nombreSecuencia, Long nuevoValor) {
		Query query = em.createNativeQuery("SELECT interprete.avanzar_secuencia(:nombre, :valor)");
		query.setParameter("nombre", nombreSecuencia);
		query.setParameter("valor", nuevoValor);
		query.getSingleResult();
	}

	public Long obtenerSiguienteFolio(String nombreSecuencia) {
		Query query = em.createNativeQuery("SELECT interprete.siguiente_folio_lc(:nombre)");
		query.setParameter("nombre", nombreSecuencia);
		return ((Number) query.getSingleResult()).longValue();
	}

	@Override
	public void actualizar(ControlSecuenciaLCDTO e) {
		em.merge(e);
	}

	public void guardar(ControlSecuenciaLCDTO e) {
		em.persist(e);
	}

}
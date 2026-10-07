package mx.gob.atdt.interprete.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.DetHomeDTO;
import mx.gob.atdt.interprete.dto.DetTramiteServicioDTO;
import mx.gob.atdt.interprete.model.DetHome;
import mx.gob.atdt.interprete.model.DetTramiteServicio;

@Stateless
@LocalBean
public class DetHomeTramiteDAO extends IBaseService<DetTramiteServicioDTO, Long> {

	@Override
	public DetTramiteServicioDTO buscarPorId(Long id) {
		List<DetTramiteServicioDTO> listado = em.createNamedQuery("DetTramiteServicio.findByIdDetalleHome", DetTramiteServicioDTO.class)
				.setParameter("idDetalleHome", id)
				.getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
	
	@SuppressWarnings("unchecked")
	public DetTramiteServicioDTO buscarPorIdDetalleHome(Long idDetalleHome) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(Constantes.PERSISTENTE_NAME);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		final StringBuilder strQuery = new StringBuilder();
		strQuery.append("SELECT ");
		strQuery.append("dts.id_detalle_tramite, dh.id_detalle_home, dts.habilita_costo_tramite,  ");
		strQuery.append("dts.descripcion_costo_tramite , dts.habilita_excepcion_tramite ");
		strQuery.append("FROM motor_interprete.det_tramite_servicio dts ");
		strQuery.append("JOIN motor_interprete.det_home dh on dh.id_detalle_home = dts.id_detalle_home  ");
		strQuery.append("WHERE dh.id_detalle_home = :idDetalleHome");
		Query query = em.createNativeQuery(strQuery.toString());
		query.setParameter("idDetalleHome", idDetalleHome);
		List<Object[]> rows = query.getResultList();
		List<DetTramiteServicioDTO> lstTramite = new ArrayList<DetTramiteServicioDTO>();
		for (Object[] row : rows) {
			DetTramiteServicioDTO tramite = new DetTramiteServicioDTO();
			tramite.setIdDetalleTramite(Long.parseLong(String.valueOf(row[0])));
			tramite.setDetHomeDTO(new DetHomeDTO(idDetalleHome));
			tramite.setHabilitaCostoTramite((boolean) row[2]);
			tramite.setDescripcionCostoTramite((String) row[3]);
			tramite.setHabilitaExcepcionTramite((boolean) row[4]);
			lstTramite.add(tramite);
		}
		return lstTramite != null && !lstTramite.isEmpty() ? lstTramite.get(0) : null;
	}
	
	@Override
	public void actualizar(DetTramiteServicioDTO e) {
		DetTramiteServicio tramite = new DetTramiteServicio();
		tramite.setIdDetalleTramite(e.getIdDetalleTramite());
		tramite.setDetHome(em.getReference(DetHome.class, e.getDetHomeDTO().getIdDetalleHome()));
		tramite.setHabilitaCostoTramite(e.isHabilitaCostoTramite());
		tramite.setDescripcionCostoTramite(e.getDescripcionCostoTramite());
		tramite.setHabilitaExcepcionTramite(e.isHabilitaExcepcionTramite());
		em.merge(tramite);
	}		
}

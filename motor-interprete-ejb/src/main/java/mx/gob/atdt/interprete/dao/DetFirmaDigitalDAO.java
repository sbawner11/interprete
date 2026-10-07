package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NonUniqueResultException;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetFirmaDigitalDTO;
import mx.gob.atdt.interprete.model.DetFirmaDigital;
import mx.gob.atdt.interprete.model.Proyecto;

@Stateless
@LocalBean
public class DetFirmaDigitalDAO extends IBaseService<DetFirmaDigitalDTO, Long> {

	@Override
	public DetFirmaDigitalDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void actualizar(DetFirmaDigitalDTO e) {
		DetFirmaDigital firma = new DetFirmaDigital();
		firma.setIdDetalleFirma(e.getIdDetalleFirma());
		firma.setProyecto(em.getReference(Proyecto.class, e.getProyectoDTO().getIdProyecto()));
		firma.setClaveSistema(e.getClaveSistema());
		firma.setUrlFirmado(e.getUrlFirmado());
		firma.setUrlRedirecciona(e.getUrlRedirecciona());
		firma.setUsuarioDominioSeg(e.getUsuarioDominioSeg());
		firma.setContrasenaDominioSeg(e.getContrasenaDominioSeg());
		firma.setFechaCreacion(e.getFechaCreacion());
		firma.setFechaUltimaActualizacion(e.getFechaUltimaActualizacion());
		firma.setActivo(e.isActivo());
		firma.setSeccionSincronizada(e.isSeccionSincronizada());
		firma.setFirmaCiudadano(e.isFirmaCiudadano());
		em.merge(firma);
	}
	
	public Boolean getFirmaCiudadanoPorIdProyecto(Long idProyecto) {
	    
		List<DetFirmaDigitalDTO> resultados = 
		em.createNamedQuery("DetFirmaDigital.findFirmaCiudadanoByIdProyecto", DetFirmaDigitalDTO.class)
        .setParameter("idProyecto", idProyecto)
        .getResultList();

	    if (resultados.isEmpty()) {
	        return Boolean.FALSE; 
	    } 
	    else if (resultados.size() == 1) {
	        return resultados.get(0).isFirmaCiudadano();  
	    } 
	    else {
	        throw new NonUniqueResultException("Múltiples firmas encontradas para el proyecto: " + idProyecto);
	    }
	}

}

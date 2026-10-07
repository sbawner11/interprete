package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO;
import mx.gob.atdt.interprete.model.ArchivosRespuestaToken;
import mx.gob.atdt.interprete.model.CatTipoPlantilla;
import mx.gob.atdt.interprete.model.Proyecto;

@Stateless
@LocalBean
public class ArchivosRespuestaTokenDAO extends IBaseService<ArchivosRespuestaTokenDTO, Long> {

	@Override
	public ArchivosRespuestaTokenDTO buscarPorId(Long id) {
		List<ArchivosRespuestaTokenDTO> listado = em.createNamedQuery("ArchivosRespuestaToken.findById", ArchivosRespuestaTokenDTO.class)
				.setParameter("idArchivo", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

	@Override
	public void actualizar(ArchivosRespuestaTokenDTO e) {
		ArchivosRespuestaToken token = new ArchivosRespuestaToken(); 
		
		token.setIdArchivoRespuesta(e.getIdArchivoRespuesta());
		token.setProyecto(em.getReference(Proyecto.class, e.getProyectoDTO().getIdProyecto()));
		token.setRutaArchivoRespuesta(e.getRutaArchivoRespuesta());
		token.setNombreArchivo(e.getNombreArchivo());
		token.setHabilitaFirma(e.isHabilitaFirma());
		token.setFirmaSupervisor(e.isFirmaSupervisor());
		token.setFirmaOperador(e.isFirmaOperador());
		token.setCoodenadaQrX(e.getCoodenadaQrX());
		token.setCoodenadaQrY(e.getCoodenadaQrY());
		token.setCatTipoPlantilla(e.getCatTipoPlantillaDTO().getIdTipoPlantilla() != null ? em.getReference(CatTipoPlantilla.class, e.getCatTipoPlantillaDTO().getIdTipoPlantilla()) : null);
		token.setFechaCreacion(e.getFechaCreacion());
		token.setFechaUltimaActualizacion(e.getFechaUltimaActualizacion());
		token.setActivo(e.isActivo());
		token.setSeccionSincronizada(e.isSeccionSincronizada());
		em.merge(token);
	}
	
}

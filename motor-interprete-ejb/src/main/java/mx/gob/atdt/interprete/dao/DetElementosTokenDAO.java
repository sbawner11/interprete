package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.DetElementosTokenDTO;
import mx.gob.atdt.interprete.model.ArchivosRespuestaToken;
import mx.gob.atdt.interprete.model.CatAtributosComponentes;
import mx.gob.atdt.interprete.model.CatOrigenToken;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.DetElementosToken;

@Stateless
@LocalBean
public class DetElementosTokenDAO extends IBaseService<DetElementosTokenDTO, Long> {

	@Override
	public DetElementosTokenDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(DetElementosTokenDTO e) {
		DetElementosToken token = new DetElementosToken();
		
		token.setIdElementoToken(e.getIdElementoToken());
		token.setArchivosRespuestaToken(em.getReference(ArchivosRespuestaToken.class, e.getArchivosRespuestaTokenDTO().getIdArchivoRespuesta()));
		token.setNombreToken(e.getNombreToken());
		token.setCatOrigenToken(em.getReference(CatOrigenToken.class, e.getCatOrigenTokenDTO().getIdOrigenToken()));
		token.setEstructuraFolio(e.getEstructuraFolio());
		token.setIdFormatoFecha(e.getIdFormatoFecha());
		token.setLongitudFolio(e.getLongitudFolio());
		token.setCampoPersonalizado(e.getCampoPersonalizado());
		token.setComponente(e.getIdComponente() != null ? em.getReference(Componente.class, e.getIdComponente()) : null);
		token.setOrden(e.getOrden());
		token.setActivo(e.isActivo());
		token.setCatAtributosComponentes((e.getCatAtributosComponentesDTO() != null && e.getCatAtributosComponentesDTO().getIdAtributoComponente() != null) ? em.getReference(CatAtributosComponentes.class, e.getCatAtributosComponentesDTO().getIdAtributoComponente()) : null);
		em.merge(token);
	}
	
	public List<DetElementosTokenDTO> buscarPorIdArchivoRespuesta(Long id) {
		List<DetElementosTokenDTO> listado = em.createNamedQuery("DetElementosToken.findByIdArchivoRespuesta", DetElementosTokenDTO.class)
				.setParameter("idArchivoRespuesta", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}
	
	public DetElementosTokenDTO buscarPorIdComponente(Long id) {
		List<DetElementosTokenDTO> listado = em.createNamedQuery("DetElementosToken.findByIdComponente", DetElementosTokenDTO.class)
				.setParameter("idComponente", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}

}

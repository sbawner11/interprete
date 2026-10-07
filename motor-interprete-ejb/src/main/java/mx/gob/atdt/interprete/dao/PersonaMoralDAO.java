package mx.gob.atdt.interprete.dao;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.ejb.EJBException;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.NoResultException;


import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.PersonaMoralDTO;
import mx.gob.atdt.interprete.model.PersonaMoral;
import mx.gob.atdt.interprete.model.Usuario;

@Stateless
@LocalBean
public class PersonaMoralDAO extends IBaseService<List<PersonaMoralDTO>, Long> {

	@Override
	public List<PersonaMoralDTO> buscarPorId(Long id) {
		List<PersonaMoralDTO> lstEncontrados = em.createNamedQuery("PersonaMoral.findById", PersonaMoralDTO.class)
				.setParameter("idUsuario", id).getResultList();
		return lstEncontrados != null && !lstEncontrados.isEmpty() ? lstEncontrados : null;
	}

	public PersonaMoralDTO buscarPorUsuarioYPersonaMoral(Long idUsuario, Long idPersonaMoral) {
		try {
			return em.createNamedQuery("PersonaMoral.findByUsuarioIdAndPersonaMoralId", PersonaMoralDTO.class)
		            .setParameter("idUsuario", idUsuario)
		            .setParameter("idPersonaMoral", idPersonaMoral)
		            .getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public PersonaMoralDTO buscarPorPersonaMoral(Long idPersonaMoral) {
		try {
			return em.createNamedQuery("PersonaMoral.findByPersonaMoralId", PersonaMoralDTO.class)
		            .setParameter("idPersonaMoral", idPersonaMoral)
		            .getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public void actualizar(List<PersonaMoralDTO> personasMoralesDTO) {
	
		for (PersonaMoralDTO dto : personasMoralesDTO) {
			
			TypedQuery<PersonaMoral> query = em.createNamedQuery( "PersonaMoral.findByUsuarioIdAndRfc", PersonaMoral.class);
	        query.setParameter("idUsuario", dto.getIdUsuarioLlaveCdmx());
	        query.setParameter("rfc", dto.getRfc());     
	        List<PersonaMoral> result = query.getResultList();
	        PersonaMoral entidadPersona = result.isEmpty() ? null : result.get(0);
			
			if (entidadPersona == null) {
				
				Usuario usuario = em.find(Usuario.class, dto.getIdUsuarioLlaveCdmx());
	            if (usuario == null) {
	                throw new IllegalArgumentException("Usuario no encontrado con ID: " + dto.getIdUsuarioLlaveCdmx());
	            }
		            
	            PersonaMoral nuevaEntidad = new PersonaMoral();
	            nuevaEntidad.setUsuario(usuario);
	            nuevaEntidad.setRfc(dto.getRfc());
	            nuevaEntidad.setRazonSocial(dto.getRazonSocial());
	            nuevaEntidad.setVigenciaCertificado(dto.getVigenciaCertificado());
	            nuevaEntidad.setCertificadoVigente(dto.isCertificadoVigente());
	            
	            em.persist(nuevaEntidad); 
			} else  {
							
				boolean existeCambio = false;

				// Razón Social
				if (!Objects.equals(dto.getRazonSocial(), entidadPersona.getRazonSocial())) {
					entidadPersona.setRazonSocial(dto.getRazonSocial());
					existeCambio = true;
				}

				// Vigencia del Certificado
				if (!Objects.equals(dto.getVigenciaCertificado(), entidadPersona.getVigenciaCertificado())) {
					entidadPersona.setVigenciaCertificado(dto.getVigenciaCertificado());
					existeCambio = true;
				}

				// Certificado Vigente
				if (dto.isCertificadoVigente() != entidadPersona.isCertificadoVigente()) {
					entidadPersona.setCertificadoVigente(dto.isCertificadoVigente());
					existeCambio = true;
				}
				
				if (existeCambio) {
					em.merge(entidadPersona);
				}
				
			}
				
		}
	}

	public void guardar(List<PersonaMoralDTO> personasMoralesDTO) {
		try {
			List<PersonaMoral> entidades = personasMoralesDTO.stream().map(dto -> {
				if (dto.getIdUsuarioLlaveCdmx() == null) {
					throw new IllegalArgumentException("ID de usuario es requerido");
				}
				return new PersonaMoral(dto, em);
			}).collect(Collectors.toList());

			entidades.forEach(em::persist);
		} catch (IllegalArgumentException e) {
			throw new EJBException("Error al guardar personas morales: " + e.getMessage(), e);
		}
	}
	

	public boolean existeRegistro(Long idUsuario) {
		
	    Query query = em.createNamedQuery("PersonaMoral.existeRegistro");
	    query.setParameter("idUsuario", idUsuario);
	    Long count = (Long) query.getSingleResult();
	    
	    return count > 0;
	}


}

package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.model.SeccionesFormulario;
import mx.gob.atdt.interprete.model.SubseccionesFormulario;

@Stateless
@LocalBean
public class SubSeccionesFormularioDAO extends IBaseService<SubSeccionesFormularioDTO, Long> {

	@Override
	public SubSeccionesFormularioDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(SubSeccionesFormularioDTO e) {
		SubseccionesFormulario subseccion = new SubseccionesFormulario();
		
		subseccion.setIdSubseccionFormulario(e.getIdSubseccionFormulario());
		subseccion.setSeccionesFormulario(em.getReference(SeccionesFormulario.class, e.getSeccionesFormularioDTO().getIdSeccionFormulario()));
		subseccion.setNombreSubseccion(e.getNombreSubseccion());
		subseccion.setOrden(e.getOrden());
		subseccion.setActivo(e.isActivo());
		subseccion.setSeccionSincronizada(e.isSeccionSincronizada());
				
		em.merge(subseccion);
	}
	
	public List<SubSeccionesFormularioDTO> buscarSubseccionesActivasPorIdSeccion(Long idSeccionFormulario) {
		List<SubSeccionesFormularioDTO> listado = em
				.createNamedQuery("SubseccionesFormulario.findByIdSeccion", SubSeccionesFormularioDTO.class)
				.setParameter("idSeccionFormulario", idSeccionFormulario).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}

	public boolean buscarSubseccionesPorIdSubseccion(Long idSubSeccion) {
		List<SubSeccionesFormularioDTO> listado = em
				.createNamedQuery("SubseccionesFormulario.findByIdSubseccion", SubSeccionesFormularioDTO.class)
				.setParameter("idSubSeccion", idSubSeccion).getResultList();
		return listado != null && !listado.isEmpty() ? true : false;
	}
	
	/**
	 * Método que obtiene la información de la sección a la que pertenece por el Id de Subsección
	 * @param subSeccionesFormularioDTO
	 * @return
	 */
	public SubSeccionesFormularioDTO buscarSeccionPorIdSubseccion(Long idSubSeccionFormulario) {
		List<SubSeccionesFormularioDTO> listado = em
				.createNamedQuery("SubseccionesFormulario.findSeccionByIdSubseccion", SubSeccionesFormularioDTO.class)
				.setParameter("idSubseccionFormulario", idSubSeccionFormulario).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
}

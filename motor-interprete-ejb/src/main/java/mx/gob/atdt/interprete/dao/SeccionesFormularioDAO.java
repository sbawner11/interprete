package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.model.Proyecto;
import mx.gob.atdt.interprete.model.SeccionesFormulario;

@Stateless
@LocalBean
public class SeccionesFormularioDAO extends IBaseService<SeccionesFormularioDTO, Long> {

	@Override
	public SeccionesFormularioDTO buscarPorId(Long id) {
		List<SeccionesFormularioDTO> listado = em.createNamedQuery("SeccionesFormulario.findById", SeccionesFormularioDTO.class)
				.setParameter("idSeccionFormulario", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(SeccionesFormularioDTO e) {
		SeccionesFormulario seccion = new SeccionesFormulario();

		seccion.setIdSeccionFormulario(e.getIdSeccionFormulario());
		seccion.setProyecto(em.getReference(Proyecto.class, e.getProyectoDTO().getIdProyecto()));
		seccion.setNombreSeccion(e.getNombreSeccion());
		seccion.setOrden(e.getOrden());
		seccion.setActivo(e.isActivo());
		seccion.setSeccionSincronizada(e.isSeccionSincronizada());
		
		em.merge(seccion);
	}

}

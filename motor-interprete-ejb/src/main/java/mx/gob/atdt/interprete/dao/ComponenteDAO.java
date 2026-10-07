package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.model.CatTipoComponente;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.SubseccionesFormulario;

@Stateless
@LocalBean
public class ComponenteDAO extends IBaseService<ComponenteDTO, Long> {

	@Override
	public ComponenteDTO buscarPorId(Long id) {
		List<ComponenteDTO> listado = em.createNamedQuery("Componente.findByIdComponente", ComponenteDTO.class)
				.setParameter("idComponente", id).getResultList();
		return listado != null && !listado.isEmpty() ? listado.get(0) : null;

	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void actualizar(ComponenteDTO dto) {
		Componente componente = new Componente();
		componente.setIdComponente(dto.getIdComponente());
		componente.setSubseccionesFormulario(
				new SubseccionesFormulario(dto.getSubSeccionesFormularioDTO().getIdSubseccionFormulario()));
		componente.setCatTipoComponente(new CatTipoComponente(dto.getCatTipoComponenteDTO().getIdTipoComponente()));
		componente.setOrden(dto.getOrden());
		componente.setRequerido(dto.isRequerido());
		componente.setTooltip(dto.isTooltip());
		componente.setDescripcionTooltip(dto.getDescripcionTooltip());
		componente.setTituloCampo(dto.getTituloCampo());
		componente.setActivo(dto.isActivo());
		componente.setFechaCreacion(dto.getFechaCreacion());
		componente.setFechaUltimaActualizacion(dto.getFechaUltimaActualizacion());
		componente.setSeccionSincronizada(dto.isSeccionSincronizada());

		em.merge(componente);
	}

	public List<ComponenteDTO> buscarPorTipo(int idTipo) {
		return em.createNamedQuery("Componente.findComponentesActivosByTipo", ComponenteDTO.class)
			.setParameter("idTipoComponente", idTipo)
			.getResultList();
	}

}

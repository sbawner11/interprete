package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteFechaDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteFecha;

@Stateless
@LocalBean
public class ComponenteFechaDAO extends IBaseService<ComponenteFechaDTO, Long> {

	@Override
	public ComponenteFechaDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(ComponenteFechaDTO e) {
		ComponenteFecha fecha = new ComponenteFecha();
		fecha.setIdComponenteFecha(e.getIdComponenteFecha());
		fecha.setComponente(em.getReference(Componente.class, e.getIdComponente()));
		fecha.setDiasInhabiles(e.isDiasInhabiles());
		fecha.setFechaMenorHoy(e.isFechaMenorHoy());
		fecha.setFechaMayorHoy(e.isFechaMayorHoy());
		fecha.setFechaInicio(e.getFechaInicio());
		fecha.setFechaLimite(e.getFechaLimite());
		em.merge(fecha);
	}

}

package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonaMoralDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteDatosPersonaMoral;

@Stateless
@LocalBean
public class ComponenteDatosPersonaMoralDAO extends IBaseService<ComponenteDatosPersonaMoralDTO, Long> {

	@Override
	public ComponenteDatosPersonaMoralDTO buscarPorId(Long id) {
		return null;
	}

	@Override
	public void actualizar(ComponenteDatosPersonaMoralDTO e) {
		ComponenteDatosPersonaMoral datosPersonaMoral = new ComponenteDatosPersonaMoral();
		datosPersonaMoral.setIdComponenteDatosPersonaMoral(e.getIdComponenteDatosPersonaMoral());
		datosPersonaMoral.setComponente(em.getReference(Componente.class, e.getIdComponente()));
		datosPersonaMoral.setHabilitaRfc(e.isHabilitaRfc());
		datosPersonaMoral.setHabilitaPersonaMoral(e.isHabilitaPersonaMoral());
		datosPersonaMoral.setHabilitaFechaVigencia(e.isHabilitaFechaVigencia());
		
		em.merge(datosPersonaMoral);
	}
}

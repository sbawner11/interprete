package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesLlaveDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteDatosPersonalesLlave;

@Stateless
@LocalBean
public class ComponenteDatosPersonalesLlaveDAO extends IBaseService<ComponenteDatosPersonalesLlaveDTO, Long> {

	@Override
	public ComponenteDatosPersonalesLlaveDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(ComponenteDatosPersonalesLlaveDTO e) {
		ComponenteDatosPersonalesLlave datosPersonalesLlave = new ComponenteDatosPersonalesLlave();
		datosPersonalesLlave.setIdComponenteDatosPersonales(e.getIdComponenteDatosPersonales());
		datosPersonalesLlave.setComponente(em.getReference(Componente.class, e.getIdComponente()));
		datosPersonalesLlave.setHabilitaCurp(e.isHabilitaCurp());
		datosPersonalesLlave.setHabilitaNombre(e.isHabilitaNombre());
		datosPersonalesLlave.setHabilitaPrimerApellido(e.isHabilitaPrimerApellido());
		datosPersonalesLlave.setHabilitaSegundoApellido(e.isHabilitaSegundoApellido());
		datosPersonalesLlave.setHabilitaTelefono(e.isHabilitaTelefono());
		datosPersonalesLlave.setHabilitaCorreoElectronico(e.isHabilitaCorreoElectronico());
		datosPersonalesLlave.setHabilitaFechaNacimiento(e.isHabilitaFechaNacimiento());
		datosPersonalesLlave.setHabilitaSexo(e.isHabilitaSexo());
		em.merge(datosPersonalesLlave);
	}

}

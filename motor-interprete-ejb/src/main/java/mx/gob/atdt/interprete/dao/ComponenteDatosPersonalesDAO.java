package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteDatosPersonales;

@Stateless
@LocalBean
public class ComponenteDatosPersonalesDAO extends IBaseService<ComponenteDatosPersonalesDTO, Long> {

	@Override
	public ComponenteDatosPersonalesDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(ComponenteDatosPersonalesDTO e) {
		ComponenteDatosPersonales datosPersonales = new ComponenteDatosPersonales();
		datosPersonales.setIdComponenteDatosPersonales(e.getIdComponenteDatosPersonales());
		datosPersonales.setComponente(em.getReference(Componente.class, e.getIdComponente()));
		datosPersonales.setHabilitaRenapo(e.isHabilitaRenapo());
		datosPersonales.setHabilitaCurp(e.isHabilitaCurp());
		datosPersonales.setCurpObligatorio(e.isCurpObligatorio());
		datosPersonales.setTextoInteriorCurp(e.getTextoInteriorCurp());
		datosPersonales.setHabilitaNombre(e.isHabilitaNombre());
		datosPersonales.setNombreObligatorio(e.isNombreObligatorio());
		datosPersonales.setTextoInteriorNombre(e.getTextoInteriorNombre());
		datosPersonales.setHabilitaPrimerApellido(e.isHabilitaPrimerApellido());
		datosPersonales.setPrimerApellidoObligatorio(e.isPrimerApellidoObligatorio());
		datosPersonales.setTextoInteriorPrimerApellido(e.getTextoInteriorPrimerApellido());
		datosPersonales.setHabilitaSegundoApellido(e.isHabilitaSegundoApellido());
		datosPersonales.setSegundoApellidoObligatorio(e.isSegundoApellidoObligatorio());
		datosPersonales.setTextoInteriorSegundoApellido(e.getTextoInteriorSegundoApellido());
		datosPersonales.setHabilitaTelefono(e.isHabilitaTelefono());
		datosPersonales.setTelefonoObligatorio(e.isTelefonoObligatorio());
		datosPersonales.setTextoInteriorTelefono(e.getTextoInteriorTelefono());
		datosPersonales.setHabilitaCorreoElectronico(e.isHabilitaCorreoElectronico());
		datosPersonales.setCorreoElectronicoObligatorio(e.isCorreoElectronicoObligatorio());
		datosPersonales.setTextoInteriorCorreoElectronico(e.getTextoInteriorCorreoElectronico());
		em.merge(datosPersonales);
	}

}

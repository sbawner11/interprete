package mx.gob.atdt.interprete.dao;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteDatosDomicilioDTO;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteDatosDomicilio;

@Stateless
@LocalBean
public class ComponenteDatosDomicilioDAO extends IBaseService<ComponenteDatosDomicilioDTO, Long> {

	@Override
	public ComponenteDatosDomicilioDTO buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void actualizar(ComponenteDatosDomicilioDTO e) {
		ComponenteDatosDomicilio datosDomicilio = new ComponenteDatosDomicilio();
		datosDomicilio.setIdComponenteDatosDomicilio(e.getIdComponenteDatosDomicilio());
		datosDomicilio.setComponente(em.getReference(Componente.class, e.getIdComponente()));
		datosDomicilio.setHabilitaCalle(e.isHabilitaCalle());
		datosDomicilio.setCalleObligatorio(e.isCalleObligatorio());
		datosDomicilio.setTextoInteriorCalle(e.getTextoInteriorCalle());
		datosDomicilio.setHabilitaNumeroExterior(e.isHabilitaNumeroExterior());
		datosDomicilio.setNumeroExteriorObligatorio(e.isNumeroExteriorObligatorio());
		datosDomicilio.setTextoInteriorNumeroExterior(e.getTextoInteriorNumeroExterior());
		datosDomicilio.setHabilitaNumeroInterior(e.isHabilitaNumeroInterior());
		datosDomicilio.setNumeroInteriorObligatorio(e.isNumeroInteriorObligatorio());
		datosDomicilio.setTextoInteriorNumeroInterior(e.getTextoInteriorNumeroInterior());
		datosDomicilio.setHabilitaCodigoPostal(e.isHabilitaCodigoPostal());
		datosDomicilio.setCodigoPostalObligatorio(e.isCodigoPostalObligatorio());
		datosDomicilio.setTextoInteriorCodigoPostal(e.getTextoInteriorCodigoPostal());
		datosDomicilio.setHabilitaColonia(e.isHabilitaColonia());
		datosDomicilio.setColoniaObligatorio(e.isColoniaObligatorio());
		datosDomicilio.setTextoInteriorColonia(e.getTextoInteriorColonia());
		datosDomicilio.setHabilitaAlcaldia(e.isHabilitaAlcaldia());
		datosDomicilio.setAlcaldiaObligatorio(e.isAlcaldiaObligatorio());
		datosDomicilio.setTextoInteriorAlcaldia(e.getTextoInteriorAlcaldia());
		datosDomicilio.setHabilitaEstado(e.isHabilitaEstado());
		datosDomicilio.setEstadoObligatorio(e.isEstadoObligatorio());
		datosDomicilio.setTextoInteriorEstado(e.getTextoInteriorEstado());
		
		em.merge(datosDomicilio);
	}

}

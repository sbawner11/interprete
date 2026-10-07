package mx.gob.atdt.interprete.dao;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ComponenteCampoTextoDTO;
import mx.gob.atdt.interprete.model.CatOrigenLlenado;
import mx.gob.atdt.interprete.model.CatValidadores;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.ComponenteCampoTexto;

@Stateless
@LocalBean
public class ComponenteCampoTextoDAO extends IBaseService<ComponenteCampoTextoDTO, Long> {

	@Override
	public ComponenteCampoTextoDTO buscarPorId(Long id) {
		List<ComponenteCampoTextoDTO> lstResultados = em.createNamedQuery("ComponenteCampoTexto.findById", ComponenteCampoTextoDTO.class)
		.setParameter("idComponenteCampoTexto", id)
		.getResultList();
		return lstResultados != null && !lstResultados.isEmpty() ? lstResultados.get(0) : null;
	}

	@Override
	public void actualizar(ComponenteCampoTextoDTO e) {
		ComponenteCampoTexto campoTexto = new ComponenteCampoTexto();
		campoTexto.setIdComponenteCampoTexto(e.getIdComponenteCampoTexto());
		campoTexto.setComponente(em.getReference(Componente.class, e.getIdComponente()));
		campoTexto.setAlfanumerico(e.isAlfanumerico());
		campoTexto.setNumerico(e.isNumerico());
		campoTexto.setHabilitaTextoInterior(e.isHabilitaTextoInterior());
		campoTexto.setTextoInterior(e.getTextoInterior());
		campoTexto.setCatOrigenLlenado(
				(e.getCatOrigenLlenadoDTO() != null && e.getCatOrigenLlenadoDTO().getIdOrigenLlenado() != null)
						? em.getReference(CatOrigenLlenado.class, e.getCatOrigenLlenadoDTO().getIdOrigenLlenado())
						: null);
		campoTexto.setValidadores(e.isValidadores());
		campoTexto.setCatValidadores(
				(e.getCatValidadoresDTO() != null && e.getCatValidadoresDTO().getIdValidador() != null)
						? em.getReference(CatValidadores.class, e.getCatValidadoresDTO().getIdValidador())
						: null);
		campoTexto.setValorMinimo(e.getValorMinimo());
		campoTexto.setValorMaximo(e.getValorMaximo());
		campoTexto.setPermiteDecimales(e.isPermiteDecimales());
		em.merge(campoTexto);
	}

}

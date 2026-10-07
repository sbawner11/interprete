package mx.gob.atdt.interprete.dao;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import java.util.List;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ConfiguracionCondicionValorDTO;
import mx.gob.atdt.interprete.dto.ConfiguracionCondicionesDTO;
import mx.gob.atdt.interprete.model.ConfiguracionCondicionValor;
import mx.gob.atdt.interprete.model.ConfiguracionCondiciones;

@Stateless
@LocalBean
public class ConfiguracionCondicionValorDAO extends IBaseService<ConfiguracionCondicionValorDTO, Long> {

	@Override
	public ConfiguracionCondicionValorDTO buscarPorId(Long id) {
		return null;
	}

	public List<ConfiguracionCondicionValorDTO> buscarTodos() {
		return null;
	}

	@Override
	public void actualizar(ConfiguracionCondicionValorDTO e) {
		ConfiguracionCondicionValor valor = new ConfiguracionCondicionValor();
		valor.setIdCondicionValor(e.getIdCondicionValor());
		valor.setConfiguracionCondiciones(em.getReference(ConfiguracionCondiciones.class, e.getConfiguracionCondicionesDTO().getIdConfiguracion()));
		valor.setValor(e.getValor());
		em.merge(valor);
	}

	/**
	 * Método que consulta la configuración de valores por idConfiguración
	 * @param configuracionCondicionValorDTO
	 * @return
	 */
	public List<ConfiguracionCondicionValorDTO> buscarValoresPorIdCondicion(ConfiguracionCondicionesDTO configuracionCondicionDTO) {
		List<ConfiguracionCondicionValorDTO> listadoValores = em.createNamedQuery("ConfiguracionCondicionValor.findByIdConfiguracion",ConfiguracionCondicionValorDTO.class)
				.setParameter("idConfiguracion", configuracionCondicionDTO.getIdConfiguracion())
				.getResultList();
		return listadoValores != null && !listadoValores.isEmpty() ? listadoValores : null;
	}

}
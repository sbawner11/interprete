package mx.gob.atdt.interprete.dao;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

import mx.gob.atdt.interprete.commons.dao.IBaseService;
import mx.gob.atdt.interprete.dto.ConfiguracionCondicionValorDTO;
import mx.gob.atdt.interprete.dto.ConfiguracionCondicionesDTO;
import mx.gob.atdt.interprete.model.ConfiguracionCondiciones;
import mx.gob.atdt.interprete.model.SeccionesFormulario;
import mx.gob.atdt.interprete.model.Componente;
import mx.gob.atdt.interprete.model.CatOperador;
import mx.gob.atdt.interprete.model.Usuario;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
@LocalBean
public class ConfiguracionCondicionesDAO extends IBaseService<ConfiguracionCondicionesDTO, Long> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracionCondicionesDAO.class);
	
	@Override
	public ConfiguracionCondicionesDTO buscarPorId(Long id) {
		try {
			return em.createNamedQuery("ConfiguracionCondiciones.findById", ConfiguracionCondicionesDTO.class)
				.setParameter("idConfiguracion", id)
				.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public List<ConfiguracionCondicionesDTO> buscarTodos() {
		List<ConfiguracionCondicionesDTO> listado = em.createNamedQuery("ConfiguracionCondiciones.findAll", 
				ConfiguracionCondicionesDTO.class).getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}
	
	/**
	 * Método que realiza la consulta de condiciones mediante el id de sección
	 * @param idSeccion
	 * @return
	 */
	public List<ConfiguracionCondicionesDTO> buscarCondicionesPorIdSeccion(Long idSeccion) {
		List<ConfiguracionCondicionesDTO> listadoCondiciones = em.createNamedQuery("ConfiguracionCondiciones.findByIdSeccion", 
			ConfiguracionCondicionesDTO.class).setParameter("idSeccionFormulario", idSeccion).getResultList();
		return listadoCondiciones != null && !listadoCondiciones.isEmpty() ? listadoCondiciones : null;
	}

	@Override
	public void actualizar(ConfiguracionCondicionesDTO e) {
		ConfiguracionCondiciones condicion = new ConfiguracionCondiciones();
		condicion.setIdConfiguracion(e.getIdConfiguracion());
		condicion.setSeccionesFormularioByIdSeccionCondicionada(
				em.getReference(SeccionesFormulario.class, e.getSeccionesFormularioByIdSeccionCondicionadaDTO().getIdSeccionFormulario()));
		condicion.setSeccionesFormularioByIdSeccionCondicion(
				em.getReference(SeccionesFormulario.class, e.getSeccionesFormularioByIdSeccionCondicionDTO().getIdSeccionFormulario()));
		condicion.setComponente(em.getReference(Componente.class, e.getComponenteDTO().getIdComponente()));
		condicion.setCatOperador(em.getReference(CatOperador.class, e.getCatOperadorDTO().getIdOperador()));
		condicion.setActivo(e.isActivo());
		condicion.setIdUsuarioRegistro(e.getIdUsuarioRegistro());
		condicion.setFechaCreacion(e.getFechaCreacion());
		condicion.setFechaUltimaActualizacion(e.getFechaUltimaActualizacion());
		condicion.setSeccionSincronizada(e.isSeccionSincronizada());
		em.merge(condicion);
	}

	public List<ConfiguracionCondicionesDTO> buscarPorIdProyecto(Long id) {
		List<ConfiguracionCondicionesDTO> listado = 
			em.createNamedQuery("ConfiguracionCondiciones.findByIdProyecto", 	ConfiguracionCondicionesDTO.class)
			.setParameter("idProyecto", id)
			.getResultList();
		return listado != null && !listado.isEmpty() ? listado : null;
	}

	/**
	 * Método que realiza la consulta de condiciones mediante el idProyecto
	 * @param idProyecto
	 * @return
	 */
	public List<ConfiguracionCondicionesDTO> buscarCondicionesPorIdProyecto(Long idProyecto) {
		List<ConfiguracionCondicionesDTO> listadoCondiciones = em.createNamedQuery("ConfiguracionCondiciones.findByIdProyecto", 
			ConfiguracionCondicionesDTO.class).setParameter("idProyecto", idProyecto).getResultList();
		return listadoCondiciones != null && !listadoCondiciones.isEmpty() ? listadoCondiciones : null;
	}
	
	public List<ConfiguracionCondicionesDTO> consultarCondicionesPorSeccion(Long idSeccion) throws Exception {
	        
	    try {
	        // Consulta principal para obtener las condiciones
	    	 String jpql = "SELECT new mx.gob.atdt.interprete.dto.ConfiguracionCondicionesDTO(" +
                     "cc.idConfiguracion, " +
                     "sc.idSeccionFormulario, " + 
                     "sd.idSeccionFormulario, " +
                     "c.idComponente, " +
                     "oc.idOperador, " +
                     "cc.activo, " +
                     "ctc.idTipoComponente) " + 
                     "FROM ConfiguracionCondiciones cc " +
                     "JOIN cc.seccionesFormularioByIdSeccionCondicionada sc " +
                     "JOIN cc.seccionesFormularioByIdSeccionCondicion sd " +
                     "JOIN cc.componente c " +
                     "JOIN c.catTipoComponente ctc " + 
                     "JOIN cc.catOperador oc " +
                     "WHERE cc.activo = true " +
                     "AND sc.idSeccionFormulario = :idSeccion";
	        
	        TypedQuery<ConfiguracionCondicionesDTO> query = em.createQuery(jpql, ConfiguracionCondicionesDTO.class);
	        query.setParameter("idSeccion", idSeccion);
	        
	        List<ConfiguracionCondicionesDTO> condiciones = query.getResultList();
	        
	        if (!condiciones.isEmpty()) {
	            
	            String jpqlValores = "SELECT new mx.gob.atdt.interprete.dto.ConfiguracionCondicionValorDTO(" +
	                               "ccv.idCondicionValor, ccv.configuracionCondiciones.idConfiguracion, ccv.valor) " +
	                               "FROM ConfiguracionCondicionValor ccv " +
	                               "WHERE ccv.configuracionCondiciones.idConfiguracion = :idConfiguracion";
	            
	            for (ConfiguracionCondicionesDTO condicion : condiciones) {
	                TypedQuery<ConfiguracionCondicionValorDTO> queryValores = em.createQuery(jpqlValores, ConfiguracionCondicionValorDTO.class)
	                    .setParameter("idConfiguracion", condicion.getIdConfiguracion());
	                
	                List<ConfiguracionCondicionValorDTO> valores = queryValores.getResultList();
	                condicion.setValoresCondicion(valores);
	            }
	            
	        }
	        
	        
	        return condiciones;
	        
	    } catch (Exception e) {
	        LOGGER.error("Error en consultarCondicionesPorSeccion: " + e.getMessage(), e);
	        throw e;
	    }
	}

}
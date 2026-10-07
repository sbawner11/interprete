package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ConfiguracionCondicionesDTO implements Serializable {

	private static final long serialVersionUID = -4861915165489787017L;

	private long idConfiguracion;
	private SeccionesFormularioDTO seccionesFormularioByIdSeccionCondicionadaDTO;
	private SeccionesFormularioDTO seccionesFormularioByIdSeccionCondicionDTO;
	private ComponenteDTO componenteDTO;
	private CatOperadorDTO catOperadorDTO;
	private boolean activo;
	private Long idUsuarioRegistro;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean seccionSincronizada;
	private List<ConfiguracionCondicionValorDTO> valoresCondicion;

	//Variable auxiliar para poder mostrar los valores de una configuración
	private String valoresConfiguracion;

	/**
	 * 
	 */
	public ConfiguracionCondicionesDTO() {
		seccionesFormularioByIdSeccionCondicionadaDTO = new SeccionesFormularioDTO();
		seccionesFormularioByIdSeccionCondicionDTO = new SeccionesFormularioDTO();
		componenteDTO = new ComponenteDTO();
		catOperadorDTO = new CatOperadorDTO();
	}

	/**
	 * Constructor utilizado por la NamedQuery ConfiguracionCondiciones.findByIdProyecto
	 * @param idConfiguracion
	 */
	public ConfiguracionCondicionesDTO(long idConfiguracion) {
		this.idConfiguracion = idConfiguracion;
	}
	
	/**
	 * @param idSeccionCondicionada
	 * @param idSeccionCondicion
	 * @param idComponente
	 * @param idOperador
	 * @param valorOperador
	 * @param idUsuarioRegistro
	 */
	public ConfiguracionCondicionesDTO(long idConfiguracion, Long idSeccionCondicionada, Long idSeccionCondicion, 
			Long idComponente, Integer idOperador, String valorOperador, long idUsuarioRegistro) {

		this.idConfiguracion = idConfiguracion;
		this.seccionesFormularioByIdSeccionCondicionadaDTO = new SeccionesFormularioDTO(idSeccionCondicionada);
		this.seccionesFormularioByIdSeccionCondicionDTO = new SeccionesFormularioDTO(idSeccionCondicion);
		this.componenteDTO = new ComponenteDTO(idComponente);
		this.catOperadorDTO = new CatOperadorDTO(idOperador, valorOperador);
		this.idUsuarioRegistro = idUsuarioRegistro;
	}
	
	/**
	 * Constructor utilizado por la NamedQuery ConfiguracionCondiciones.findByIdSeccion
	 * @param idConfiguracion
	 * @param idSeccionCondicionada
	 * @param idSeccionCondicion
	 * @param nombreSeccionCondicionada
	 * @param nombreSeccionCondicion
	 * @param idComponente
	 * @param tituloCampo
	 * @param idOperador
	 * @param valorOperador
	 * @param fechaCreacion
	 */
	@SuppressWarnings({"java:S107"})
	public ConfiguracionCondicionesDTO(long idConfiguracion, Long idSeccionCondicionada, String nombreSeccionCondicionada, 
			Long idSeccionCondicion, String nombreSeccionCondicion, Long idComponente, String tituloCampo, Integer idOperador, 
			String valorOperador, Date fechaCreacion) {

		this.idConfiguracion = idConfiguracion;
		this.seccionesFormularioByIdSeccionCondicionadaDTO = new SeccionesFormularioDTO(idSeccionCondicionada, nombreSeccionCondicionada);
		this.seccionesFormularioByIdSeccionCondicionDTO = new SeccionesFormularioDTO(idSeccionCondicion, nombreSeccionCondicion);
		this.componenteDTO = new ComponenteDTO(idComponente, tituloCampo);
		this.catOperadorDTO = new CatOperadorDTO(idOperador, valorOperador);
		this.fechaCreacion = fechaCreacion;
	}

	/**
	 * Constructor utilizado por la NamedQuery ConfiguracionCondiciones.findByIdSeccion
	 * @param idConfiguracion
	 * @param idSeccionCondicionada
	 * @param idSeccionCondicion
	 * @param idComponente
	 * @param idOperador
	 * @param activo
	 * @param idUsuarioRegistro
	 * @param fechaCreacion
	 * @param fechaActualizacion
	 * @param seccionSincronizada
	 */
	@SuppressWarnings({"java:S107"})
	public ConfiguracionCondicionesDTO(long idConfiguracion, Long idSeccionCondicionada,  
			Long idSeccionCondicion, Long idComponente, Integer idOperador, boolean activo,
			Long idUsuarioRegistro, Date fechaCreacion, Date fechaActualizacion, boolean seccionSincronizada) {
		this.idConfiguracion = idConfiguracion;
		this.seccionesFormularioByIdSeccionCondicionadaDTO = new SeccionesFormularioDTO(idSeccionCondicionada);
		this.seccionesFormularioByIdSeccionCondicionDTO = new SeccionesFormularioDTO(idSeccionCondicion);
		this.componenteDTO = new ComponenteDTO(idComponente);
		this.catOperadorDTO = new CatOperadorDTO(idOperador);
		this.activo = activo;
		this.idUsuarioRegistro = idUsuarioRegistro;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaActualizacion;
		this.seccionSincronizada = seccionSincronizada;
	}	
	
	
	public ConfiguracionCondicionesDTO(
			
			long idConfiguracion, 
            long idSeccionCondicionada, 
            long idSeccionCondicion,
            long idComponente,
            int idOperador,
            boolean activo,
            Integer idTipoComponente) {
		
			this.idConfiguracion = idConfiguracion;
			
			this.seccionesFormularioByIdSeccionCondicionadaDTO = new SeccionesFormularioDTO();
			this.seccionesFormularioByIdSeccionCondicionadaDTO.setIdSeccionFormulario(idSeccionCondicionada);
			
			this.seccionesFormularioByIdSeccionCondicionDTO = new SeccionesFormularioDTO();
			this.seccionesFormularioByIdSeccionCondicionDTO.setIdSeccionFormulario(idSeccionCondicion);
			
			this.componenteDTO = new ComponenteDTO();
			this.componenteDTO.setIdComponente(idComponente);
			
			this.componenteDTO.setCatTipoComponenteDTO(new CatTipoComponenteDTO(idTipoComponente));
				
			this.catOperadorDTO = new CatOperadorDTO();
			this.catOperadorDTO.setIdOperador(idOperador);
		
			this.activo = activo;

	}
	
	/**
	 * @return the idConfiguracion
	 */
	public long getIdConfiguracion() {
		return idConfiguracion;
	}

	/**
	 * @param idConfiguracion the idConfiguracion to set
	 */
	public void setIdConfiguracion(long idConfiguracion) {
		this.idConfiguracion = idConfiguracion;
	}

	/**
	 * @return the seccionesFormularioByIdSeccionCondicionadaDTO
	 */
	public SeccionesFormularioDTO getSeccionesFormularioByIdSeccionCondicionadaDTO() {
		return seccionesFormularioByIdSeccionCondicionadaDTO;
	}

	/**
	 * @param seccionesFormularioByIdSeccionCondicionadaDTO the seccionesFormularioByIdSeccionCondicionadaDTO to set
	 */
	public void setSeccionesFormularioByIdSeccionCondicionadaDTO(
			SeccionesFormularioDTO seccionesFormularioByIdSeccionCondicionadaDTO) {
		this.seccionesFormularioByIdSeccionCondicionadaDTO = seccionesFormularioByIdSeccionCondicionadaDTO;
	}

	/**
	 * @return the seccionesFormularioByIdSeccionCondicionDTO
	 */
	public SeccionesFormularioDTO getSeccionesFormularioByIdSeccionCondicionDTO() {
		return seccionesFormularioByIdSeccionCondicionDTO;
	}

	/**
	 * @param seccionesFormularioByIdSeccionCondicionDTO the seccionesFormularioByIdSeccionCondicionDTO to set
	 */
	public void setSeccionesFormularioByIdSeccionCondicionDTO(
			SeccionesFormularioDTO seccionesFormularioByIdSeccionCondicionDTO) {
		this.seccionesFormularioByIdSeccionCondicionDTO = seccionesFormularioByIdSeccionCondicionDTO;
	}
	
	/**
	 * @return the componenteDTO
	 */
	public ComponenteDTO getComponenteDTO() {
		return componenteDTO;
	}

	/**
	 * @param componenteDTO the componenteDTO to set
	 */
	public void setComponenteDTO(ComponenteDTO componenteDTO) {
		this.componenteDTO = componenteDTO;
	}
	
	/**
	 * @return the catOperadorDTO
	 */
	public CatOperadorDTO getCatOperadorDTO() {
		return catOperadorDTO;
	}

	/**
	 * @param catOperadorDTO the catOperadorDTO to set
	 */
	public void setCatOperadorDTO(CatOperadorDTO catOperadorDTO) {
		this.catOperadorDTO = catOperadorDTO;
	}	

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the idUsuarioRegistro
	 */
	public Long getIdUsuarioRegistro() {
		return idUsuarioRegistro;
	}

	/**
	 * @param idUsuarioRegistro the idUsuarioRegistro to set
	 */
	public void setIdUsuarioRegistro(Long idUsuarioRegistro) {
		this.idUsuarioRegistro = idUsuarioRegistro;
	}

	/**
	 * @return the fechaCreacion
	 */
	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	/**
	 * @param fechaCreacion the fechaCreacion to set
	 */
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	/**
	 * @return the fechaUltimaActualizacion
	 */
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	/**
	 * @param fechaUltimaActualizacion the fechaUltimaActualizacion to set
	 */
	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}
	
	/**
	 * @return the seccionSincronizada
	 */
	public boolean isSeccionSincronizada() {
		return seccionSincronizada;
	}

	/**
	 * @param seccionSincronizada the seccionSincronizada to set
	 */
	public void setSeccionSincronizada(boolean seccionSincronizada) {
		this.seccionSincronizada = seccionSincronizada;
	}

	/**
	 * @return the valoresConfiguracion
	 */
	public String getValoresConfiguracion() {
		return valoresConfiguracion;
	}

	/**
	 * @param valoresConfiguracion the valoresConfiguracion to set
	 */
	public void setValoresConfiguracion(String valoresConfiguracion) {
		this.valoresConfiguracion = valoresConfiguracion;
	}
	
	public List<ConfiguracionCondicionValorDTO> getValoresCondicion() {
	        return valoresCondicion;
	}
	
    public void setValoresCondicion(List<ConfiguracionCondicionValorDTO> valoresCondicion) {
        this.valoresCondicion = valoresCondicion;
    }

}
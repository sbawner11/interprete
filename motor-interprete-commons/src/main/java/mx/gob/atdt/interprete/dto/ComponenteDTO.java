package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8234769188267820538L;

	protected Long idComponente;
	protected CatTipoComponenteDTO catTipoComponenteDTO;
	protected SubSeccionesFormularioDTO subSeccionesFormularioDTO;
	protected int orden;
	protected boolean requerido;
	protected boolean tooltip;
	protected String descripcionTooltip;
	protected String tituloCampo;
	protected boolean activo;
	protected Date fechaCreacion;
	protected Date fechaUltimaActualizacion;
	private boolean seccionSincronizada;
	
	private String respuestaComponente;

	/**
	 * 
	 */
	public ComponenteDTO() {
		this.catTipoComponenteDTO = new CatTipoComponenteDTO();
		this.subSeccionesFormularioDTO = new SubSeccionesFormularioDTO();
	}

	public ComponenteDTO(Long idComponente) {
		this.idComponente = idComponente;
	}

	/**
	 * @param idComponente
	 * @param tituloCampo
	 */
	public ComponenteDTO(Long idComponente, String tituloCampo) {
		this.idComponente = idComponente;
		this.tituloCampo = tituloCampo;
	}

	/**
	 * Constructor utilizado por la NamedQuery Componente.findComponentesActivosByIdSubseccion
	 * 
	 * @param idComponente
	 * @param idCatTipoComponente
	 * @param orden
	 * @param tituloCampo
	 * @param seccionSincronizada
	 */
	public ComponenteDTO(Long idComponente, Integer idCatTipoComponente, int orden, String tituloCampo, boolean seccionSincronizada) {
		this.idComponente = idComponente;
		this.catTipoComponenteDTO = new CatTipoComponenteDTO(idCatTipoComponente);
		this.orden = orden;
		this.tituloCampo = tituloCampo;
		this.seccionSincronizada = seccionSincronizada;
	}

	/**
	 * 
	 * @param idComponente
	 * @param catTipoComponenteDTO
	 * @param subSeccionesFormularioDTO
	 * @param orden
	 * @param nombre
	 * @param tipoDato
	 * @param requerido
	 * @param tooltip
	 * @param descripcionTooltip
	 * @param tituloCampo
	 * @param activo
	 * @param limiteCaracteres
	 * @param minimo
	 * @param maximo
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 */
	public ComponenteDTO(Long idComponente, CatTipoComponenteDTO catTipoComponenteDTO,
			SubSeccionesFormularioDTO subSeccionesFormularioDTO, int orden, 
			boolean requerido, boolean tooltip, String descripcionTooltip, String tituloCampo, boolean activo,
			Date fechaCreacion,
			Date fechaUltimaActualizacion) {
		super();
		this.idComponente = idComponente;
		this.catTipoComponenteDTO = catTipoComponenteDTO;
		this.subSeccionesFormularioDTO = subSeccionesFormularioDTO;
		this.orden = orden;
		this.requerido = requerido;
		this.tooltip = tooltip;
		this.descripcionTooltip = descripcionTooltip;
		this.tituloCampo = tituloCampo;
		this.activo = activo;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}
	
	/**
	 * Constructor utilizado por la NamedQuery ComponenteDatosPersonalesLlave.findByIdComponente
	 * 
	 * @param idComponente
	 * @param idTipoComponente
	 * @param idSubseccionFormulario
	 * @param orden
	 * @param requerido
	 * @param tooltip
	 * @param descripcionTooltip
	 * @param tituloCampo
	 * @param activo
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param seccionSincronizada
	 */
	public ComponenteDTO(Long idComponente, Integer idTipoComponente, Long idSubseccionFormulario,
			int orden, boolean requerido, boolean tooltip, String descripcionTooltip, String tituloCampo, 
			boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion, boolean seccionSincronizada) {		
		this.idComponente = idComponente;
		this.catTipoComponenteDTO = new CatTipoComponenteDTO(idTipoComponente);
		this.subSeccionesFormularioDTO = new SubSeccionesFormularioDTO(idSubseccionFormulario);
		this.orden = orden;		
		this.requerido = requerido;
		this.tooltip = tooltip;
		this.descripcionTooltip = descripcionTooltip;
		this.tituloCampo = tituloCampo;
		this.activo = activo;		
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.seccionSincronizada = seccionSincronizada;
	}
	
	/**
	 * Constructor utilizado por la consulta ComponenteDAO.buscarComponentesPorTipo
	 * 
	 * @param idComponente
	 * @param idTipoComponente
	 * @param activo
	 */
	public ComponenteDTO(Long idComponente, Integer idTipoComponente, boolean activo) {		
		this.idComponente = idComponente;
		this.catTipoComponenteDTO = new CatTipoComponenteDTO(idTipoComponente);
		this.activo = activo;		
	}
	
	/**
	 * @return the idComponente
	 */
	public Long getIdComponente() {
		return idComponente;
	}

	/**
	 * @param idComponente the idComponente to set
	 */
	public void setIdComponente(Long idComponente) {
		this.idComponente = idComponente;
	}

	/**
	 * @return the catTipoComponenteDTO
	 */
	public CatTipoComponenteDTO getCatTipoComponenteDTO() {
		return catTipoComponenteDTO;
	}

	/**
	 * @param catTipoComponenteDTO the catTipoComponenteDTO to set
	 */
	public void setCatTipoComponenteDTO(CatTipoComponenteDTO catTipoComponenteDTO) {
		this.catTipoComponenteDTO = catTipoComponenteDTO;
	}

	/**
	 * @return the subSeccionesFormularioDTO
	 */
	public SubSeccionesFormularioDTO getSubSeccionesFormularioDTO() {
		return subSeccionesFormularioDTO;
	}

	/**
	 * @param subSeccionesFormularioDTO the subSeccionesFormularioDTO to set
	 */
	public void setSubSeccionesFormularioDTO(SubSeccionesFormularioDTO subSeccionesFormularioDTO) {
		this.subSeccionesFormularioDTO = subSeccionesFormularioDTO;
	}

	/**
	 * @return the orden
	 */
	public int getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(int orden) {
		this.orden = orden;
	}

	/**
	 * @return the requerido
	 */
	public boolean isRequerido() {
		return requerido;
	}

	/**
	 * @param requerido the requerido to set
	 */
	public void setRequerido(boolean requerido) {
		this.requerido = requerido;
	}

	/**
	 * @return the tooltip
	 */
	public boolean isTooltip() {
		return tooltip;
	}

	/**
	 * @param tooltip the tooltip to set
	 */
	public void setTooltip(boolean tooltip) {
		this.tooltip = tooltip;
	}

	/**
	 * @return the descripcionTooltip
	 */
	public String getDescripcionTooltip() {
		return descripcionTooltip;
	}

	/**
	 * @param descripcionTooltip the descripcionTooltip to set
	 */
	public void setDescripcionTooltip(String descripcionTooltip) {
		this.descripcionTooltip = descripcionTooltip;
	}

	/**
	 * @return the tituloCampo
	 */
	public String getTituloCampo() {
		return tituloCampo;
	}

	/**
	 * @param tituloCampo the tituloCampo to set
	 */
	public void setTituloCampo(String tituloCampo) {
		this.tituloCampo = tituloCampo;
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
	 * @return the respuestaComponente
	 */
	public String getRespuestaComponente() {
		return respuestaComponente;
	}

	/**
	 * @param respuestaComponente the respuestaComponente to set
	 */
	public void setRespuestaComponente(String respuestaComponente) {
		this.respuestaComponente = respuestaComponente;
	}

}

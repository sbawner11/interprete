package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ArchivosRespuestaTokenDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -516319610069803772L;

	private Long idArchivoRespuesta;
	private ProyectoDTO proyectoDTO;
	private CatTipoPlantillaDTO catTipoPlantillaDTO;
	private String rutaArchivoRespuesta;
	private String nombreArchivo;
	private boolean habilitaFirma;
	private boolean firmaSupervisor;
	private boolean firmaOperador;
	private Long coodenadaQrX;
	private Long coodenadaQrY;	
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private boolean activo;
	private boolean seccionSincronizada;
	private ArchivoDTO archivoRespuesta;
	private List<DetElementosTokenDTO> lstToken;

	public ArchivosRespuestaTokenDTO() {
		archivoRespuesta = new ArchivoDTO();
		catTipoPlantillaDTO = new CatTipoPlantillaDTO();
	}

	public ArchivosRespuestaTokenDTO(Long idArchivoRespuesta) {
		this.idArchivoRespuesta = idArchivoRespuesta;
	}

	/**
	 * Constructor utilizado por la NamedQuery ArchivosRespuestaToken.findByIdProyecto y ArchivosRespuestaToken.findById.
	 * 
	 * @param idArchivoRespuesta
	 * @param idProyecto
	 * @param rutaArchivoRespuesta
	 * @param nombreArchivo
	 * @param habilitaFirma
	 * @param firmaSupervisor
	 * @param firmaOperador
	 * @param coodenadaQrX
	 * @param coodenadaQrY
	 * @param idTipoPlantilla
	 * @param fechaCreacion
	 * @param fechaUltimaActualizacion
	 * @param activo
	 * @param seccionSincronizada
	 */
	public ArchivosRespuestaTokenDTO(Long idArchivoRespuesta, Long idProyecto, String rutaArchivoRespuesta,
			String nombreArchivo, boolean habilitaFirma, boolean firmaSupervisor, boolean firmaOperador, 
			Long coodenadaQrX, Long coodenadaQrY, Integer idTipoPlantilla, Date fechaCreacion, Date fechaUltimaActualizacion, boolean activo,
			boolean seccionSincronizada) {
		this.idArchivoRespuesta = idArchivoRespuesta;
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.rutaArchivoRespuesta = rutaArchivoRespuesta;
		this.nombreArchivo = nombreArchivo;
		this.habilitaFirma = habilitaFirma;
		this.firmaSupervisor = firmaSupervisor;
		this.firmaOperador = firmaOperador;
		this.coodenadaQrX = coodenadaQrX;
		this.coodenadaQrY = coodenadaQrY;
		this.catTipoPlantillaDTO = new CatTipoPlantillaDTO(idTipoPlantilla);
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
		this.activo = activo;
		this.seccionSincronizada = seccionSincronizada;		
	}

	/**
	 * Constructor usado por la media query ArchivosRespuestaToken.existeDetalleIdProyecto
	 * 
	 * @param idProyecto
	 * @param idArchivoRespuesta
	 */
	public ArchivosRespuestaTokenDTO(Long idProyecto, Long idArchivoRespuesta) {
		this.proyectoDTO = new ProyectoDTO(idProyecto);
		this.idArchivoRespuesta = idArchivoRespuesta;
	}

	/**
	 * @return the idArchivoRespuesta
	 */
	public Long getIdArchivoRespuesta() {
		return idArchivoRespuesta;
	}

	/**
	 * @param idArchivoRespuesta the idArchivoRespuesta to set
	 */
	public void setIdArchivoRespuesta(Long idArchivoRespuesta) {
		this.idArchivoRespuesta = idArchivoRespuesta;
	}

	/**
	 * @return the proyectoDTO
	 */
	public ProyectoDTO getProyectoDTO() {
		return proyectoDTO;
	}

	/**
	 * @param proyectoDTO the proyectoDTO to set
	 */
	public void setProyectoDTO(ProyectoDTO proyectoDTO) {
		this.proyectoDTO = proyectoDTO;
	}

	/**
	 * @return the rutaArchivoRespuesta
	 */
	public String getRutaArchivoRespuesta() {
		return rutaArchivoRespuesta;
	}

	/**
	 * @param rutaArchivoRespuesta the rutaArchivoRespuesta to set
	 */
	public void setRutaArchivoRespuesta(String rutaArchivoRespuesta) {
		this.rutaArchivoRespuesta = rutaArchivoRespuesta;
	}

	/**
	 * @return the nombreArchivo
	 */
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	/**
	 * @param nombreArchivo the nombreArchivo to set
	 */
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	
	/**
	 * @return the habilitaFirma
	 */
	public boolean isHabilitaFirma() {
		return habilitaFirma;
	}

	/**
	 * @param habilitaFirma the habilitaFirma to set
	 */
	public void setHabilitaFirma(boolean habilitaFirma) {
		this.habilitaFirma = habilitaFirma;
	}

	/**
	 * @return the firmaSupervisor
	 */
	public boolean isFirmaSupervisor() {
		return firmaSupervisor;
	}

	/**
	 * @param firmaSupervisor the firmaSupervisor to set
	 */
	public void setFirmaSupervisor(boolean firmaSupervisor) {
		this.firmaSupervisor = firmaSupervisor;
	}

	/**
	 * @return the firmaOperador
	 */
	public boolean isFirmaOperador() {
		return firmaOperador;
	}

	/**
	 * @param firmaOperador the firmaOperador to set
	 */
	public void setFirmaOperador(boolean firmaOperador) {
		this.firmaOperador = firmaOperador;
	}

	/**
	 * @return the coodenadaQrX
	 */
	public Long getCoodenadaQrX() {
		return coodenadaQrX;
	}

	/**
	 * @param coodenadaQrX the coodenadaQrX to set
	 */
	public void setCoodenadaQrX(Long coodenadaQrX) {
		this.coodenadaQrX = coodenadaQrX;
	}

	/**
	 * @return the coodenadaQrY
	 */
	public Long getCoodenadaQrY() {
		return coodenadaQrY;
	}

	/**
	 * @param coodenadaQrY the coodenadaQrY to set
	 */
	public void setCoodenadaQrY(Long coodenadaQrY) {
		this.coodenadaQrY = coodenadaQrY;
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
	 * @return the archivoRespuesta
	 */
	public ArchivoDTO getArchivoRespuesta() {
		return archivoRespuesta;
	}

	/**
	 * @param archivoRespuesta the archivoRespuesta to set
	 */
	public void setArchivoRespuesta(ArchivoDTO archivoRespuesta) {
		this.archivoRespuesta = archivoRespuesta;
	}

	/**
	 * @return the lstToken
	 */
	public List<DetElementosTokenDTO> getLstToken() {
		return lstToken;
	}

	/**
	 * @param lstToken the lstToken to set
	 */
	public void setLstToken(List<DetElementosTokenDTO> lstToken) {
		this.lstToken = lstToken;
	}
	
	/**
	 * @return the catTipoPlantillaDTO
	 */
	public CatTipoPlantillaDTO getCatTipoPlantillaDTO() {
		return catTipoPlantillaDTO;
	}

	/**
	 * @param catTipoPlantillaDTO the catTipoPlantillaDTO to set
	 */
	public void setCatTipoPlantillaDTO(CatTipoPlantillaDTO catTipoPlantillaDTO) {
		this.catTipoPlantillaDTO = catTipoPlantillaDTO;
	}

	@Override
	public String toString() {
		return "ArchivosRespuestaTokenDTO [idArchivoRespuesta=" + idArchivoRespuesta 
				+ ", proyectoDTO=" + proyectoDTO
				+ ", catTipoPlantillaDTO=" + catTipoPlantillaDTO
				+ ", rutaArchivoRespuesta=" + rutaArchivoRespuesta 
				+ ", nombreArchivo=" + nombreArchivo
				+ ", fechaCreacion=" + fechaCreacion 
				+ ", fechaUltimaActualizacion=" + fechaUltimaActualizacion 
				+ ", activo=" + activo 
				+ ", seccionSincronizada=" + seccionSincronizada 
				+ ", archivoRespuesta=" + archivoRespuesta
				+ ", lstToken=" + lstToken + "]";
	}	

}

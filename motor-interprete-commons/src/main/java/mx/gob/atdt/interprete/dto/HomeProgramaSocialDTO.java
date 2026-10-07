package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class HomeProgramaSocialDTO implements Serializable {

	private static final long serialVersionUID = 759630984896562699L;

	private Long idHome;
	private DetHomeDTO detalleHomeDTO;
	private String rutaImagen;
	private ArchivoDTO imagenInicio;
	private List<AgregaDescripcionesDTO> lstObjetivos;
	private List<AgregaDescripcionesDTO> lstPoblacion;
	private String ciclo;
	private List<AgregaDescripcionesDTO> lstRequisitos;
	private String tipoApoyo;
	private List<AgregaDescripcionesDTO> lstApoyos;
	private String duracionApoyo;
	private String notificacionEmpezar;
	private boolean isNotificacion;
	private boolean isObjetivoPrograma;
	private boolean isPoblacionObjetivo;
	private boolean isCicloPrograma;
	private boolean isRequisitos;
	private boolean isApoyo;
	private boolean isDuracion;
	private boolean isAccesoPrograma;
	private Date fechaCreacion;
	private Date fechaUltimaActualizacion;
	private DetLegalesDTO legalesDTO;

	public HomeProgramaSocialDTO() {
		imagenInicio = new ArchivoDTO();
	}

	/**
	 * 
	 * @param idHome
	 */
	public HomeProgramaSocialDTO(Long idHome) {
		this.idHome = idHome;
	}

	/**
	 * @param idHome
	 * @param rutaImagen
	 * @param imagenInicio
	 * @param lstObjetivos
	 * @param lstPoblacion
	 * @param ciclo
	 * @param lstRequisitos
	 * @param tipoApoyo
	 * @param lstApoyos
	 * @param duracionApoyo
	 * @param notificacionEmpezar
	 * @param isNotificacion
	 * @param isObjetivoPrograma
	 * @param isPoblacionObjetivo
	 * @param isCicloPrograma
	 * @param isRequisitos
	 * @param isApoyo
	 * @param isDuracion
	 * @param isAccesoPrograma
	 */
	public HomeProgramaSocialDTO(Long idHome, String rutaImagen, List<AgregaDescripcionesDTO> lstObjetivos,
			List<AgregaDescripcionesDTO> lstPoblacion, String ciclo, List<AgregaDescripcionesDTO> lstRequisitos,
			String tipoApoyo, List<AgregaDescripcionesDTO> lstApoyos, String duracionApoyo, String notificacionEmpezar,
			boolean isNotificacion, boolean isObjetivoPrograma, boolean isPoblacionObjetivo, boolean isCicloPrograma,
			boolean isRequisitos, boolean isApoyo, boolean isDuracion, boolean isAccesoPrograma) {
		super();
		this.idHome = idHome;
		this.rutaImagen = rutaImagen;
		this.lstObjetivos = lstObjetivos;
		this.lstPoblacion = lstPoblacion;
		this.ciclo = ciclo;
		this.lstRequisitos = lstRequisitos;
		this.tipoApoyo = tipoApoyo;
		this.lstApoyos = lstApoyos;
		this.duracionApoyo = duracionApoyo;
		this.notificacionEmpezar = notificacionEmpezar;
		this.isNotificacion = isNotificacion;
		this.isObjetivoPrograma = isObjetivoPrograma;
		this.isPoblacionObjetivo = isPoblacionObjetivo;
		this.isCicloPrograma = isCicloPrograma;
		this.isRequisitos = isRequisitos;
		this.isApoyo = isApoyo;
		this.isDuracion = isDuracion;
		this.isAccesoPrograma = isAccesoPrograma;
	}

	public Long getIdHome() {
		return idHome;
	}

	public void setIdHome(Long idHome) {
		this.idHome = idHome;
	}

	public DetHomeDTO getDetalleHomeDTO() {
		return detalleHomeDTO;
	}

	public void setDetalleHomeDTO(DetHomeDTO detalleHomeDTO) {
		this.detalleHomeDTO = detalleHomeDTO;
	}

	public String getRutaImagen() {
		return rutaImagen;
	}

	public void setRutaImagen(String rutaImagen) {
		this.rutaImagen = rutaImagen;
	}
	
	public ArchivoDTO getImagenInicio() {
		return imagenInicio;
	}

	public void setImagenInicio(ArchivoDTO imagenInicio) {
		this.imagenInicio = imagenInicio;
	}

	public List<AgregaDescripcionesDTO> getLstObjetivos() {
		return lstObjetivos;
	}

	public void setLstObjetivos(List<AgregaDescripcionesDTO> lstObjetivos) {
		this.lstObjetivos = lstObjetivos;
	}

	public List<AgregaDescripcionesDTO> getLstPoblacion() {
		return lstPoblacion;
	}

	public void setLstPoblacion(List<AgregaDescripcionesDTO> lstPoblacion) {
		this.lstPoblacion = lstPoblacion;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public List<AgregaDescripcionesDTO> getLstRequisitos() {
		return lstRequisitos;
	}

	public void setLstRequisitos(List<AgregaDescripcionesDTO> lstRequisitos) {
		this.lstRequisitos = lstRequisitos;
	}

	public String getTipoApoyo() {
		return tipoApoyo;
	}

	public void setTipoApoyo(String tipoApoyo) {
		this.tipoApoyo = tipoApoyo;
	}

	public List<AgregaDescripcionesDTO> getLstApoyos() {
		return lstApoyos;
	}

	public void setLstApoyos(List<AgregaDescripcionesDTO> lstApoyos) {
		this.lstApoyos = lstApoyos;
	}

	public String getDuracionApoyo() {
		return duracionApoyo;
	}

	public void setDuracionApoyo(String duracionApoyo) {
		this.duracionApoyo = duracionApoyo;
	}

	public String getNotificacionEmpezar() {
		return notificacionEmpezar;
	}

	public void setNotificacionEmpezar(String notificacionEmpezar) {
		this.notificacionEmpezar = notificacionEmpezar;
	}

	public boolean isNotificacion() {
		return isNotificacion;
	}

	public void setNotificacion(boolean isNotificacion) {
		this.isNotificacion = isNotificacion;
	}

	public boolean isObjetivoPrograma() {
		return isObjetivoPrograma;
	}

	public void setObjetivoPrograma(boolean isObjetivoPrograma) {
		this.isObjetivoPrograma = isObjetivoPrograma;
	}

	public boolean isPoblacionObjetivo() {
		return isPoblacionObjetivo;
	}

	public void setPoblacionObjetivo(boolean isPoblacionObjetivo) {
		this.isPoblacionObjetivo = isPoblacionObjetivo;
	}

	public boolean isCicloPrograma() {
		return isCicloPrograma;
	}

	public void setCicloPrograma(boolean isCicloPrograma) {
		this.isCicloPrograma = isCicloPrograma;
	}

	public boolean isRequisitos() {
		return isRequisitos;
	}

	public void setRequisitos(boolean isRequisitos) {
		this.isRequisitos = isRequisitos;
	}

	public boolean isApoyo() {
		return isApoyo;
	}

	public void setApoyo(boolean isApoyo) {
		this.isApoyo = isApoyo;
	}

	public boolean isDuracion() {
		return isDuracion;
	}

	public void setDuracion(boolean isDuracion) {
		this.isDuracion = isDuracion;
	}

	public boolean isAccesoPrograma() {
		return isAccesoPrograma;
	}

	public void setAccesoPrograma(boolean isAccesoPrograma) {
		this.isAccesoPrograma = isAccesoPrograma;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	public DetLegalesDTO getLegalesDTO() {
		return legalesDTO;
	}

	public void setLegalesDTO(DetLegalesDTO legalesDTO) {
		this.legalesDTO = legalesDTO;
	}

	@Override
	public String toString() {
		return "HomeProgramaSocialDTO [idHome=" + idHome + ", detalleHomeDTO=" + detalleHomeDTO + ", rutaImagen="
				+ rutaImagen + ", imagenInicio=" + imagenInicio + ", lstObjetivos=" + lstObjetivos + ", lstPoblacion=" + lstPoblacion + ", ciclo=" + ciclo
				+ ", lstRequisitos=" + lstRequisitos + ", tipoApoyo=" + tipoApoyo + ", lstApoyos=" + lstApoyos
				+ ", duracionApoyo=" + duracionApoyo + ", notificacionEmpezar=" + notificacionEmpezar
				+ ", isNotificacion=" + isNotificacion + ", isObjetivoPrograma=" + isObjetivoPrograma
				+ ", isPoblacionObjetivo=" + isPoblacionObjetivo + ", isCicloPrograma=" + isCicloPrograma
				+ ", isRequisitos=" + isRequisitos + ", isApoyo=" + isApoyo + ", isDuracion=" + isDuracion
				+ ", isAccesoPrograma=" + isAccesoPrograma + ", fechaCreacion=" + fechaCreacion
				+ ", fechaUltimaActualizacion=" + fechaUltimaActualizacion + "]";
	}

}

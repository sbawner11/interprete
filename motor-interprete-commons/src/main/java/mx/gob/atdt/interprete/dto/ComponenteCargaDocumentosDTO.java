package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComponenteCargaDocumentosDTO extends ComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8656371239996291034L;
	
	private Long idComponenteCarga;
	private CatTamanioArchivosDTO catTamanioArchivosDTO;
	private List<CrcCargaDocumentosTipoArchivoDTO> crcCargaDocumentosTipoArchivoDTO;
	private List<CatTipoArchivoDTO> lstCatTipoArchivosDTO;
	private boolean documentoUnico;
	private ComponenteDTO componenteDTO;
	
	//Atributos provicionales para guardar el value del componente y datos necesarios
	private List<ComponenteCargaDocumentosDTO> archivos = new ArrayList<ComponenteCargaDocumentosDTO>();
	private String rutaDocumento;
	private String nombreDocumento;
	
	/**
	 * 
	 */
	public ComponenteCargaDocumentosDTO() {
		super();
		catTamanioArchivosDTO = new CatTamanioArchivosDTO();
		crcCargaDocumentosTipoArchivoDTO = new ArrayList<CrcCargaDocumentosTipoArchivoDTO>();
		lstCatTipoArchivosDTO = new ArrayList<CatTipoArchivoDTO>();
		archivos = new ArrayList<ComponenteCargaDocumentosDTO>();
	}
	
	public ComponenteCargaDocumentosDTO(Long idComponenteCarga, boolean documentoUnico, Integer idTamanioArchivo, Long idComponente, Integer idTipoComponente,
			Long idSubseccionFormulario, int orden, boolean requerido, boolean tooltip, String descripcionTooltip,
			String tituloCampo, boolean activo, Date fechaCreacion, Date fechaUltimaActualizacion,
			boolean seccionSincronizada) {
		
		super(idComponente, idTipoComponente, idSubseccionFormulario, orden, requerido, tooltip, descripcionTooltip,
				tituloCampo, activo, fechaCreacion, fechaUltimaActualizacion, seccionSincronizada);
		
		this.catTamanioArchivosDTO = new CatTamanioArchivosDTO(idTamanioArchivo);
		this.idComponenteCarga = idComponenteCarga;
		this.documentoUnico = documentoUnico;
	}

	/**
	 * @param idComponenteCarga
	 * @param idComponente
	 * @param ducumentoUnico
	 */
	public ComponenteCargaDocumentosDTO(Long idComponenteCarga, boolean documentoUnico, Long idComponente) {
		this.idComponenteCarga = idComponenteCarga;
		super.setIdComponente(idComponente);
		this.documentoUnico = documentoUnico;
	}
	
	/**
	 * @param idComponenteCarga
	 * @param ducumentoUnico
	 */
	public ComponenteCargaDocumentosDTO(Long idComponenteCarga, boolean documentoUnico) {
		this.idComponenteCarga = idComponenteCarga;
		this.documentoUnico = documentoUnico;
	}
	
	/**
	 * @param idComponenteCarga
	 */
	public ComponenteCargaDocumentosDTO(Long idComponenteCarga) {
		this.idComponenteCarga = idComponenteCarga;
	}

	/**
	 * @return the idComponenteCarga
	 */
	public Long getIdComponenteCarga() {
		return idComponenteCarga;
	}

	/**
	 * @param idComponenteCarga the idComponenteCarga to set
	 */
	public void setIdComponenteCarga(Long idComponenteCarga) {
		this.idComponenteCarga = idComponenteCarga;
	}

	/**
	 * @return the catTamanioArchivosDTO
	 */
	public CatTamanioArchivosDTO getCatTamanioArchivosDTO() {
		return catTamanioArchivosDTO;
	}

	/**
	 * @param catTamanioArchivosDTO the catTamanioArchivosDTO to set
	 */
	public void setCatTamanioArchivosDTO(CatTamanioArchivosDTO catTamanioArchivosDTO) {
		this.catTamanioArchivosDTO = catTamanioArchivosDTO;
	}
	
	/**
	 * @return the crcCargaDocumentosTipoArchivoDTO
	 */
	public List<CrcCargaDocumentosTipoArchivoDTO> getCrcCargaDocumentosTipoArchivoDTO() {
		return crcCargaDocumentosTipoArchivoDTO;
	}

	/**
	 * @param crcCargaDocumentosTipoArchivoDTO the crcCargaDocumentosTipoArchivoDTO to set
	 */
	public void setCrcCargaDocumentosTipoArchivoDTO(
			List<CrcCargaDocumentosTipoArchivoDTO> crcCargaDocumentosTipoArchivoDTO) {
		this.crcCargaDocumentosTipoArchivoDTO = crcCargaDocumentosTipoArchivoDTO;
	}

	/**
	 * @return the lstCatTipoArchivosDTO
	 */
	public List<CatTipoArchivoDTO> getLstCatTipoArchivosDTO() {
		return lstCatTipoArchivosDTO;
	}

	/**
	 * @param lstCatTipoArchivosDTO the lstCatTipoArchivosDTO to set
	 */
	public void setLstCatTipoArchivosDTO(List<CatTipoArchivoDTO> lstCatTipoArchivosDTO) {
		this.lstCatTipoArchivosDTO = lstCatTipoArchivosDTO;
	}

	/**
	 * @return the ducumentoUnico
	 */
	public boolean isDocumentoUnico() {
		return documentoUnico;
	}

	/**
	 * @param ducumentoUnico the ducumentoUnico to set
	 */
	public void setDocumentoUnico(boolean documentoUnico) {
		this.documentoUnico = documentoUnico;
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

	public String getRutaDocumento() {
		return rutaDocumento;
	}

	public void setRutaDocumento(String rutaDocumento) {
		this.rutaDocumento = rutaDocumento;
	}

	public List<ComponenteCargaDocumentosDTO> getArchivos() {
		return archivos;
	}

	public void setArchivos(List<ComponenteCargaDocumentosDTO> archivos) {
		this.archivos = archivos;
	}

	public String getNombreDocumento() {
		return nombreDocumento;
	}

	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}	
}

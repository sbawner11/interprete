package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class CrcCargaDocumentosTipoArchivoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2384695969622982640L;

	private Long idCargaDocumentoTipoArchivo;
	private CatTipoArchivoDTO catTipoArchivoDTO;
	private ComponenteCargaDocumentosDTO componenteCargaDocumentosDTO;
	private Boolean activo;

	/**
	 * 
	 */
	public CrcCargaDocumentosTipoArchivoDTO() {
	}

	/**
	 * @param idCargaDocumentoTipoArchivo
	 * @param catTipoArchivoDTO
	 * @param componenteCargaDocumentosDTO
	 * @param activo
	 */
	public CrcCargaDocumentosTipoArchivoDTO(Long idCargaDocumentoTipoArchivo, CatTipoArchivoDTO catTipoArchivoDTO,
			ComponenteCargaDocumentosDTO componenteCargaDocumentosDTO, Boolean activo) {
		this.idCargaDocumentoTipoArchivo = idCargaDocumentoTipoArchivo;
		this.catTipoArchivoDTO = catTipoArchivoDTO;
		this.componenteCargaDocumentosDTO = componenteCargaDocumentosDTO;
		this.activo = activo;
	}


	/**
	 * Constructor utilizado por la NamedQUery CrcCargaDocumentosTipoArchivo.findByIdComponente
	 * @param idCargaDocumentoTipoArchivo
	 * @param idTipoArchivo
	 * @param descripcion
	 * @param idComponenteCarga
	 * @param ducumentoUnico
	 * @param activo
	 * @param idComponente
	 */
	public CrcCargaDocumentosTipoArchivoDTO(Long idCargaDocumentoTipoArchivo, Integer idTipoArchivo, String descripcion,
			Long idComponenteCarga, Boolean ducumentoUnico, Boolean activo, Long idComponente) {
		this.idCargaDocumentoTipoArchivo = idCargaDocumentoTipoArchivo;
		this.catTipoArchivoDTO = new CatTipoArchivoDTO(idTipoArchivo,descripcion);
		this.componenteCargaDocumentosDTO = new ComponenteCargaDocumentosDTO(idComponenteCarga, ducumentoUnico, idComponente);
		this.activo = activo;
	}

	/**
	 * @return the idArchivoTipoArchivo
	 */
	public Long getIdCargaDocumentoTipoArchivo() {
		return idCargaDocumentoTipoArchivo;
	}

	/**
	 * @param idArchivoTipoArchivo the idCargaDocumentoTipoArchivo to set
	 */
	public void setIdCargaDocumentoTipoArchivo(Long idCargaDocumentoTipoArchivo) {
		this.idCargaDocumentoTipoArchivo = idCargaDocumentoTipoArchivo;
	}

	/**
	 * @return the catTipoArchivoDTO
	 */
	public CatTipoArchivoDTO getCatTipoArchivoDTO() {
		return catTipoArchivoDTO;
	}

	/**
	 * @param catTipoArchivoDTO the catTipoArchivoDTO to set
	 */
	public void setCatTipoArchivoDTO(CatTipoArchivoDTO catTipoArchivoDTO) {
		this.catTipoArchivoDTO = catTipoArchivoDTO;
	}

	/**
	 * @return the componenteCargaDocumentosDTO
	 */
	public ComponenteCargaDocumentosDTO getComponenteCargaDocumentosDTO() {
		return componenteCargaDocumentosDTO;
	}

	/**
	 * @param componenteCargaDocumentosDTO the componenteCargaDocumentosDTO to set
	 */
	public void setComponenteCargaDocumentosDTO(ComponenteCargaDocumentosDTO componenteCargaDocumentosDTO) {
		this.componenteCargaDocumentosDTO = componenteCargaDocumentosDTO;
	}

	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

}
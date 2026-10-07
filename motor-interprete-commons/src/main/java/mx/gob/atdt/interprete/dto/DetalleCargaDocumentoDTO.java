package mx.gob.atdt.interprete.dto;

import java.util.ArrayList;
import java.util.List;


public class DetalleCargaDocumentoDTO {
	private Long idSeccion;
	private String nombre;
	private Integer posicion;
	private Boolean deshabilitado;
	private CatCargaDocumentoDTO catCargaDocumento;
	private CatTamanioPermitidoDTO catTamanioPermitido;
	private List<CatDocObligatorioDTO> catDocsObligatorios;
	private List<CatTipoArchivoDTO> catTiposArchivos;
	private Boolean documentoUnico;

	public DetalleCargaDocumentoDTO() {
		catCargaDocumento = new CatCargaDocumentoDTO();
		catTamanioPermitido = new CatTamanioPermitidoDTO();
		catDocsObligatorios = new ArrayList<>();
		catTiposArchivos = new ArrayList<>();
	}

	public DetalleCargaDocumentoDTO(Long idSeccion, CatCargaDocumentoDTO catCargaDocumento, String nombre, Integer posicion,
			Boolean deshabilitado) {
		this.idSeccion = idSeccion;
		this.catCargaDocumento = catCargaDocumento;
		this.nombre = nombre;
		this.posicion = posicion;
		this.deshabilitado = deshabilitado;
	}

	public DetalleCargaDocumentoDTO(Integer posicion, Boolean deshabilitado) {
		catCargaDocumento = new CatCargaDocumentoDTO();
		this.posicion = posicion;
		this.deshabilitado = deshabilitado;
	}

	public DetalleCargaDocumentoDTO(Long idSeccion, String nombre) {
		this.idSeccion = idSeccion;
		this.nombre = nombre;
	}

	/**
	 * @return the idSeccion
	 */
	public Long getIdSeccion() {
		return idSeccion;
	}

	/**
	 * @param idSeccion the idSeccion to set
	 */
	public void setIdSeccion(Long idSeccion) {
		this.idSeccion = idSeccion;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the posicion
	 */
	public Integer getPosicion() {
		return posicion;
	}

	/**
	 * @param posicion the posicion to set
	 */
	public void setPosicion(Integer posicion) {
		this.posicion = posicion;
	}

	/**
	 * @return the deshabilitado
	 */
	public Boolean getDeshabilitado() {
		return deshabilitado;
	}

	/**
	 * @param deshabilitado the deshabilitado to set
	 */
	public void setDeshabilitado(Boolean deshabilitado) {
		this.deshabilitado = deshabilitado;
	}

	/**
	 * @return the catCargaDocumento
	 */
	public CatCargaDocumentoDTO getCatCargaDocumento() {
		return catCargaDocumento;
	}

	/**
	 * @param catCargaDocumento the catCargaDocumento to set
	 */
	public void setCatCargaDocumento(CatCargaDocumentoDTO catCargaDocumento) {
		this.catCargaDocumento = catCargaDocumento;
	}

	/**
	 * @return the catDocsObligatorios
	 */
	public List<CatDocObligatorioDTO> getCatDocsObligatorios() {
		return catDocsObligatorios;
	}

	/**
	 * @param catDocsObligatorios the catDocsObligatorios to set
	 */
	public void setCatDocsObligatorios(List<CatDocObligatorioDTO> catDocsObligatorios) {
		this.catDocsObligatorios = catDocsObligatorios;
	}

	/**
	 * @return the catTamanioPermitido
	 */
	public CatTamanioPermitidoDTO getCatTamanioPermitido() {
		return catTamanioPermitido;
	}

	/**
	 * @param catTamanioPermitido the catTamanioPermitido to set
	 */
	public void setCatTamanioPermitido(CatTamanioPermitidoDTO catTamanioPermitido) {
		this.catTamanioPermitido = catTamanioPermitido;
	}

	/**
	 * @return the catTiposArchivos
	 */
	public List<CatTipoArchivoDTO> getCatTiposArchivos() {
		return catTiposArchivos;
	}

	/**
	 * @param catTiposArchivos the catTiposArchivos to set
	 */
	public void setCatTiposArchivos(List<CatTipoArchivoDTO> catTiposArchivos) {
		this.catTiposArchivos = catTiposArchivos;
	}

	/**
	 * @return the documentoUnico
	 */
	public Boolean getDocumentoUnico() {
		return documentoUnico;
	}

	/**
	 * @param documentoUnico the documentoUnico to set
	 */
	public void setDocumentoUnico(Boolean documentoUnico) {
		this.documentoUnico = documentoUnico;
	}

}
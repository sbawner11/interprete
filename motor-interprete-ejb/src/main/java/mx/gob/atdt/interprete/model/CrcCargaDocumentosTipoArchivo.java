package mx.gob.atdt.interprete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "crc_carga_documentos_tipo_archivo", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "CrcCargaDocumentosTipoArchivo.findByIdComponente ", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CrcCargaDocumentosTipoArchivoDTO(crc.idCargaDocumentoTipoArchivo, "
					+ " crc.catTipoArchivo.idTipoArchivo, crc.catTipoArchivo.descripcion, crc.componenteCargaDocumentos.idComponenteCarga, "
					+ " crc.componenteCargaDocumentos.documentoUnico, crc.activo, crc.componenteCargaDocumentos.componente.idComponente ) "
			+ " FROM CrcCargaDocumentosTipoArchivo crc "
			+ " INNER JOIN crc.componenteCargaDocumentos ccd "
			+ " INNER JOIN crc.catTipoArchivo cta "
			+ " WHERE crc.componenteCargaDocumentos.idComponenteCarga = :idComponenteCarga")
})
public class CrcCargaDocumentosTipoArchivo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 427712863383497980L;
	 
	private Long idCargaDocumentoTipoArchivo;
	private ComponenteCargaDocumentos componenteCargaDocumentos;
	private CatTipoArchivo catTipoArchivo;
	private Boolean activo;

	public CrcCargaDocumentosTipoArchivo() {
	}

	public CrcCargaDocumentosTipoArchivo(Long idCargaDocumentoTipoArchivo, CatTipoArchivo catTipoArchivo,
			ComponenteCargaDocumentos componenteCargaDocumentos) {
		this.idCargaDocumentoTipoArchivo = idCargaDocumentoTipoArchivo;
		this.catTipoArchivo = catTipoArchivo;
		this.componenteCargaDocumentos = componenteCargaDocumentos;
	}

	public CrcCargaDocumentosTipoArchivo(Long idCargaDocumentoTipoArchivo, CatTipoArchivo catTipoArchivo,
			ComponenteCargaDocumentos componenteCargaDocumentos, Boolean activo) {
		this.idCargaDocumentoTipoArchivo = idCargaDocumentoTipoArchivo;
		this.catTipoArchivo = catTipoArchivo;
		this.componenteCargaDocumentos = componenteCargaDocumentos;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_carga_documento_tipo_archivo", unique = true, nullable = false)
	public Long getIdCargaDocumentoTipoArchivo() {
		return this.idCargaDocumentoTipoArchivo;
	}

	public void setIdCargaDocumentoTipoArchivo(Long idCargaDocumentoTipoArchivo) {
		this.idCargaDocumentoTipoArchivo = idCargaDocumentoTipoArchivo;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente_carga", nullable = false)
	public ComponenteCargaDocumentos getComponenteCargaDocumentos() {
		return this.componenteCargaDocumentos;
	}

	public void setComponenteCargaDocumentos(ComponenteCargaDocumentos componenteCargaDocumentos) {
		this.componenteCargaDocumentos = componenteCargaDocumentos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_archivo", nullable = false)
	public CatTipoArchivo getCatTipoArchivo() {
		return this.catTipoArchivo;
	}

	public void setCatTipoArchivo(CatTipoArchivo catTipoArchivo) {
		this.catTipoArchivo = catTipoArchivo;
	}

	@Column(name = "activo")
	public Boolean getActivo() {
		return this.activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

}
package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cat_tipo_archivo", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatTipoArchivo.findAll", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoArchivoDTO(cta.idTipoArchivo, cta.descripcion) "
					+ "FROM CatTipoArchivo cta "
					+ "WHERE cta.activo = true ")
})
public class CatTipoArchivo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1075710448758203068L;
	
	private Integer idTipoArchivo;
	private String descripcion;
	private boolean activo;
	private Set<CrcCargaDocumentosTipoArchivo> crcCargaDocumentosTipoArchivo = new HashSet<CrcCargaDocumentosTipoArchivo>(0);
	
	public CatTipoArchivo() {
	}

	public CatTipoArchivo(Integer idTipoArchivo, String descripcion, boolean activo) {
		this.idTipoArchivo = idTipoArchivo;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	public CatTipoArchivo(Integer idTipoArchivo, String descripcion, boolean activo,
			Set<CrcCargaDocumentosTipoArchivo> crcCargaDocumentosTipoArchivo) {
		this.idTipoArchivo = idTipoArchivo;
		this.descripcion = descripcion;
		this.activo = activo;
		this.crcCargaDocumentosTipoArchivo = crcCargaDocumentosTipoArchivo;		
	}

	@Id
	@Column(name = "id_tipo_archivo", unique = true, nullable = false)
	public Integer getIdTipoArchivo() {
		return this.idTipoArchivo;
	}

	public void setIdTipoArchivo(Integer idTipoArchivo) {
		this.idTipoArchivo = idTipoArchivo;
	}

	@Column(name = "descripcion", nullable = false, length = 10)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catTipoArchivo")
	public Set<CrcCargaDocumentosTipoArchivo> getCrcCargaDocumentosTipoArchivo() {
		return this.crcCargaDocumentosTipoArchivo;
	}

	public void setCrcCargaDocumentosTipoArchivo(Set<CrcCargaDocumentosTipoArchivo> crcCargaDocumentosTipoArchivo) {
		this.crcCargaDocumentosTipoArchivo = crcCargaDocumentosTipoArchivo;
	}	
	
}
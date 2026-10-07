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
@Table(name = "cat_tamanio_archivos", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatTamanioArchivos.findAll", 
			query = " SELECT new mx.gob.atdt.interprete.dto.CatTamanioArchivosDTO(cta.idTamanioArchivo, cta.descripcion, cta.activo ) "
					+ " FROM CatTamanioArchivos cta "
					+ " ORDER BY cta.orden"),
	@NamedQuery(name = "CatTamanioArchivos.findById", 
			query = " SELECT new mx.gob.atdt.interprete.dto.CatTamanioArchivosDTO(cta.idTamanioArchivo, cta.descripcion, cta.activo ) "
					+ " FROM CatTamanioArchivos cta "
					+ " WHERE cta.idTamanioArchivo = :idTamanioArchivo"
					+ " ORDER BY cta.idTamanioArchivo " ),
	@NamedQuery(name = "CatTamanioArchivos.findByTamanioArchivo", 
			query = " SELECT new mx.gob.atdt.interprete.dto.CatTamanioArchivosDTO(cta.idTamanioArchivo, cta.descripcion, cta.activo ) "
					+ " FROM CatTamanioArchivos cta "
					+ " WHERE cta.idTamanioArchivo =:idTamanioArchivo")
})
public class CatTamanioArchivos implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8916773018944488251L;
	
	private Integer idTamanioArchivo;
	private String descripcion;
	private boolean activo;
	private int orden;
	private Set<ComponenteCargaDocumentos> componenteCargaDocumentoses = new HashSet<ComponenteCargaDocumentos>(0);

	public CatTamanioArchivos() {
	}

	public CatTamanioArchivos(Integer idTamanioArchivo, String descripcion, boolean activo) {
		this.idTamanioArchivo = idTamanioArchivo;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	public CatTamanioArchivos(Integer idTamanioArchivo, String descripcion, boolean activo,
			Set<ComponenteCargaDocumentos> componenteCargaDocumentoses) {
		this.idTamanioArchivo = idTamanioArchivo;
		this.descripcion = descripcion;
		this.activo = activo;
		this.componenteCargaDocumentoses = componenteCargaDocumentoses;
	}

	@Id
	@Column(name = "id_tamanio_archivo", unique = true, nullable = false)
	public Integer getIdTamanioArchivo() {
		return this.idTamanioArchivo;
	}

	public void setIdTamanioArchivo(Integer idTamanioArchivo) {
		this.idTamanioArchivo = idTamanioArchivo;
	}

	@Column(name = "descripcion", nullable = false, length = 100)
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
	
	@Column(name = "orden", nullable = false)
	public int getOrden() {
		return this.orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catTamanioArchivos")
	public Set<ComponenteCargaDocumentos> getComponenteCargaDocumentoses() {
		return this.componenteCargaDocumentoses;
	}

	public void setComponenteCargaDocumentoses(Set<ComponenteCargaDocumentos> componenteCargaDocumentoses) {
		this.componenteCargaDocumentoses = componenteCargaDocumentoses;
	}

}

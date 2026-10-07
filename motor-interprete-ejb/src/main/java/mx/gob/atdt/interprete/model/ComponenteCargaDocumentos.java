package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "componente_carga_documentos", schema = "motor_interprete")
@NamedQueries({ 
	@NamedQuery(name = "ComponenteCargaDocumentos.findByIdComponente", 
			query = "SELECT ccd "
			+ " FROM ComponenteCargaDocumentos ccd "
			+ " JOIN ccd.componente c "
			+ " WHERE c.idComponente = :idComponente"),
	@NamedQuery(name = "ComponenteCargaDocumentos.findByIdComponenteCarga", 
			query = "SELECT ccd "
			+ " FROM ComponenteCargaDocumentos ccd "
			+ " WHERE ccd.idComponenteCarga = :idComponenteCarga")
})
public class ComponenteCargaDocumentos implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3956710337383199202L;
	
	private Long idComponenteCarga;
	private CatTamanioArchivos catTamanioArchivos;
	private Componente componente;
	private boolean documentoUnico;
	private Set<CrcCargaDocumentosTipoArchivo> crcCargaDocumentosTipoArchivo = new HashSet<CrcCargaDocumentosTipoArchivo>(0);

	public ComponenteCargaDocumentos() {
	}

	public ComponenteCargaDocumentos(Long idComponenteCarga, CatTamanioArchivos catTamanioArchivos,
			Componente componente, boolean documentoUnico) {
		this.idComponenteCarga = idComponenteCarga;
		this.catTamanioArchivos = catTamanioArchivos;
		this.componente = componente;
		this.documentoUnico = documentoUnico;
	}

	@Id
	@Column(name = "id_componente_carga", unique = true, nullable = false)
	public Long getIdComponenteCarga() {
		return this.idComponenteCarga;
	}

	public void setIdComponenteCarga(Long idComponenteCarga) {
		this.idComponenteCarga = idComponenteCarga;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tamanio_archivo", nullable = false)
	public CatTamanioArchivos getCatTamanioArchivos() {
		return this.catTamanioArchivos;
	}

	public void setCatTamanioArchivos(CatTamanioArchivos catTamanioArchivos) {
		this.catTamanioArchivos = catTamanioArchivos;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_componente", nullable = false)
	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	@Column(name = "documento_unico", nullable = false)
	public boolean isDocumentoUnico() {
		return this.documentoUnico;
	}

	public void setDocumentoUnico(boolean documentoUnico) {
		this.documentoUnico = documentoUnico;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "componenteCargaDocumentos")
	public Set<CrcCargaDocumentosTipoArchivo> getCrcCargaDocumentosTipoArchivos() {
		return this.crcCargaDocumentosTipoArchivo;
	}

	public void setCrcCargaDocumentosTipoArchivos(Set<CrcCargaDocumentosTipoArchivo> crcCargaDocumentosTipoArchivo) {
		this.crcCargaDocumentosTipoArchivo = crcCargaDocumentosTipoArchivo;
	}

}
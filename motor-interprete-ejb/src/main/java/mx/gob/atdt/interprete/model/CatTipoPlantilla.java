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
@Table(name = "cat_tipo_plantilla", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name="CatTipoPlantilla.findAll", 
			query = "SELECT new mx.gob.atdt.interprete.dto.CatTipoPlantillaDTO(ctp.idTipoPlantilla, ctp.descripcion, ctp.activo) "
					+ " FROM CatTipoPlantilla ctp"
					+ " WHERE ctp.activo = true ")
})
public class CatTipoPlantilla implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9053939810845021100L;
	
	private Integer idTipoPlantilla;
	private String descripcion;
	private boolean activo;
	private Set<ArchivosRespuestaToken> archivosRespuestaTokens = new HashSet<ArchivosRespuestaToken>(0);

	public CatTipoPlantilla() {
	}

	public CatTipoPlantilla(Integer idTipoPlantilla, String descripcion, boolean activo) {
		this.idTipoPlantilla = idTipoPlantilla;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	public CatTipoPlantilla(Integer idTipoPlantilla, String descripcion, boolean activo,
			Set<ArchivosRespuestaToken> archivosRespuestaTokens) {
		this.idTipoPlantilla = idTipoPlantilla;
		this.descripcion = descripcion;
		this.activo = activo;
		this.archivosRespuestaTokens = archivosRespuestaTokens;
	}

	@Id
	@Column(name = "id_tipo_plantilla", unique = true, nullable = false)
	public Integer getIdTipoPlantilla() {
		return this.idTipoPlantilla;
	}

	public void setIdTipoPlantilla(Integer idTipoPlantilla) {
		this.idTipoPlantilla = idTipoPlantilla;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catTipoPlantilla")
	public Set<ArchivosRespuestaToken> getArchivosRespuestaTokens() {
		return this.archivosRespuestaTokens;
	}

	public void setArchivosRespuestaTokens(Set<ArchivosRespuestaToken> archivosRespuestaTokens) {
		this.archivosRespuestaTokens = archivosRespuestaTokens;
	}

}

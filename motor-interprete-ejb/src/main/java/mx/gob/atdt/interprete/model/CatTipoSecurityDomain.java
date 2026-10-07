package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cat_tipo_security_domain", schema = "motor_interprete")
public class CatTipoSecurityDomain implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5444771113980532427L;
	
	private Integer idTipoSecurityDomain;
	private String descripcion;
	private Set<DetSecurityDomain> detSecurityDomains = new HashSet<DetSecurityDomain>(0);

	public CatTipoSecurityDomain() {
	}

	public CatTipoSecurityDomain(Integer idTipoSecurityDomain, String descripcion) {
		this.idTipoSecurityDomain = idTipoSecurityDomain;
		this.descripcion = descripcion;
	}

	public CatTipoSecurityDomain(Integer idTipoSecurityDomain, String descripcion,
			Set<DetSecurityDomain> detSecurityDomains) {
		this.idTipoSecurityDomain = idTipoSecurityDomain;
		this.descripcion = descripcion;
		this.detSecurityDomains = detSecurityDomains;
	}

	@Id
	@Column(name = "id_tipo_security_domain", unique = true, nullable = false)
	public Integer getIdTipoSecurityDomain() {
		return this.idTipoSecurityDomain;
	}

	public void setIdTipoSecurityDomain(Integer idTipoSecurityDomain) {
		this.idTipoSecurityDomain = idTipoSecurityDomain;
	}

	@Column(name = "descripcion", nullable = false, length = 20)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catTipoSecurityDomain")
	public Set<DetSecurityDomain> getDetSecurityDomains() {
		return this.detSecurityDomains;
	}

	public void setDetSecurityDomains(Set<DetSecurityDomain> detSecurityDomains) {
		this.detSecurityDomains = detSecurityDomains;
	}

}

package mx.gob.atdt.interprete.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "cat_dependencia_pago", schema = "motor_interprete")

@NamedQueries({
    @NamedQuery(name = "CatDependenciaPago.findAll", 
    		query = ""
    				+ "SELECT new mx.gob.atdt.interprete.dto.CatDependenciaPagoDTO( "
    				+ "c.idDependenciaPago, c.sigla, c.descripcion, c.activo) "
    				+ "FROM CatDependenciaPago c "
    				+ "ORDER BY c.idDependenciaPago"),
    
    @NamedQuery(name = "CatDependenciaPago.findById", 
    		query = "SELECT new mx.gob.atdt.interprete.dto.CatDependenciaPagoDTO( "
    				+ "c.idDependenciaPago, c.sigla, c.descripcion, c.activo) "
    				+ "FROM CatDependenciaPago c "
    				+ "WHERE c.idDependenciaPago = :idDependenciaPago "
    				+ "AND c.activo = true ")
})

public class CatDependenciaPago implements java.io.Serializable {
	
	private static final long serialVersionUID = -7476090910939678961L;
	
	private int idDependenciaPago;
	private String sigla;
	private String descripcion;
	private boolean activo;
	private Set<CatUnidadAdministrativaPago> catUnidadAdministrativaPagos = new HashSet<CatUnidadAdministrativaPago>(0);

	public CatDependenciaPago() {
	}

	public CatDependenciaPago(int idDependenciaPago, String sigla, String descripcion, boolean activo) {
		this.idDependenciaPago = idDependenciaPago;
		this.sigla = sigla;
		this.descripcion = descripcion;
		this.activo = activo;
	}

	public CatDependenciaPago(int idDependenciaPago, String sigla, String descripcion, boolean activo,
			Set<CatUnidadAdministrativaPago> catUnidadAdministrativaPagos) {
		this.idDependenciaPago = idDependenciaPago;
		this.sigla = sigla;
		this.descripcion = descripcion;
		this.activo = activo;
		this.catUnidadAdministrativaPagos = catUnidadAdministrativaPagos;
	}

	@Id
	@Column(name = "id_dependencia_pago", unique = true, nullable = false)
	public int getIdDependenciaPago() {
		return this.idDependenciaPago;
	}

	public void setIdDependenciaPago(int idDependenciaPago) {
		this.idDependenciaPago = idDependenciaPago;
	}

	@Column(name = "sigla", nullable = false, length = 15)
	public String getSigla() {
		return this.sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	@Column(name = "descripcion", nullable = false, length = 60)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "catDependenciaPago")
	public Set<CatUnidadAdministrativaPago> getCatUnidadAdministrativaPagos() {
		return this.catUnidadAdministrativaPagos;
	}

	public void setCatUnidadAdministrativaPagos(Set<CatUnidadAdministrativaPago> catUnidadAdministrativaPagos) {
		this.catUnidadAdministrativaPagos = catUnidadAdministrativaPagos;
	}

}
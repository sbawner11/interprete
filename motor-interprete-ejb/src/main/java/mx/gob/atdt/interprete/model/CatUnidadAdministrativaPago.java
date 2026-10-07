package mx.gob.atdt.interprete.model;

import javax.persistence.*;

@Entity
@Table(name = "cat_unidad_administrativa_pago", schema = "motor_interprete")

@NamedQueries({

    @NamedQuery(name = "CatUnidadAdministrativaPago.findAll",
            query = "SELECT new mx.gob.atdt.interprete.dto.CatUnidadAdministrativaPagoDTO( "
                    + "c.idUnidadAdministrativaPago, cd.idDependenciaPago, c.descripcion, c.clave, c.activo) "
                    + "FROM CatUnidadAdministrativaPago c "
                    + "JOIN c.catDependenciaPago cd "
                    + "ORDER BY c.idUnidadAdministrativaPago"),

    @NamedQuery(name = "CatUnidadAdministrativaPago.findById",
            query = "SELECT new mx.gob.atdt.interprete.dto.CatUnidadAdministrativaPagoDTO( "
                    + "c.idUnidadAdministrativaPago, cd.idDependenciaPago, c.descripcion, c.clave, c.activo) "
                    + "FROM CatUnidadAdministrativaPago c "
                    + "JOIN c.catDependenciaPago cd "
                    + "WHERE c.idUnidadAdministrativaPago = :idUnidadAdministrativaPago")    
})
public class CatUnidadAdministrativaPago implements java.io.Serializable {
	
	private static final long serialVersionUID = 3236783439401466404L;
	
	private int idUnidadAdministrativaPago;
	private CatDependenciaPago catDependenciaPago;
	private String descripcion;
	private String clave;
	private boolean activo;

	public CatUnidadAdministrativaPago() {
	}

	public CatUnidadAdministrativaPago(int idUnidadAdministrativaPago, CatDependenciaPago catDependenciaPago,
			String descripcion, String clave, boolean activo) {
		this.idUnidadAdministrativaPago = idUnidadAdministrativaPago;
		this.catDependenciaPago = catDependenciaPago;
		this.descripcion = descripcion;
		this.clave = clave;
		this.activo = activo;
	}

	@Id
	@Column(name = "id_unidad_administrativa_pago", unique = true, nullable = false)
	public int getIdUnidadAdministrativaPago() {
		return this.idUnidadAdministrativaPago;
	}

	public void setIdUnidadAdministrativaPago(int idUnidadAdministrativaPago) {
		this.idUnidadAdministrativaPago = idUnidadAdministrativaPago;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_dependencia_pago", nullable = false)
	public CatDependenciaPago getCatDependenciaPago() {
		return this.catDependenciaPago;
	}

	public void setCatDependenciaPago(CatDependenciaPago catDependenciaPago) {
		this.catDependenciaPago = catDependenciaPago;
	}

	@Column(name = "descripcion", nullable = false, length = 60)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Column(name = "clave", nullable = false, length = 3)
	public String getClave() {
		return this.clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	@Column(name = "activo", nullable = false)
	public boolean isActivo() {
		return this.activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}

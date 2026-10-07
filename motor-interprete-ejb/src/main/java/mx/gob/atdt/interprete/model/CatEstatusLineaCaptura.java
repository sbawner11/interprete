package mx.gob.atdt.interprete.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "cat_estatus_linea_captura", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "CatEstatusLineaCaptura.findAll", 
			query = "SELECT NEW mx.gob.atdt.interprete.dto.CatEstatusLineaCapturaDTO("
					+ "c.idEstatusLineaCaptura, c.descripcion) "
	        		+ "FROM CatEstatusLineaCaptura c "),
    @NamedQuery(name = "CatEstatusLineaCaptura.findById", 
        query = "SELECT NEW mx.gob.atdt.interprete.dto.CatEstatusLineaCapturaDTO("
        		+ "c.idEstatusLineaCaptura, c.descripcion) "
        		+ "FROM CatEstatusLineaCaptura c "
        		+ "WHERE c.idEstatusLineaCaptura = :idEstatusLineaCaptura")
})
public class CatEstatusLineaCaptura implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id_estatus_linea_captura", unique = true, nullable = false)
	private Integer idEstatusLineaCaptura;
	
	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	public CatEstatusLineaCaptura() {
		// Constructor por defecto
 }

	public Integer getIdEstatusLineaCaptura() {
		return idEstatusLineaCaptura;
	}

	public void setIdEstatusLineaCaptura(Integer idEstatusLineaCaptura) {
		this.idEstatusLineaCaptura = idEstatusLineaCaptura;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


}


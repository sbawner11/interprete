package mx.gob.atdt.interprete.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;


import mx.gob.atdt.interprete.dto.BitSincronizacionDTO;
import mx.gob.atdt.interprete.dto.PersonaMoralDTO;

@Entity
@Table(name = "bit_sincronizacion", schema = "motor_interprete")
public class BitSincronizacion implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6531711250257969785L;
	
	private long idSincronizacion;
	private Date fechaSincronizacion;
	private Usuario usuario;

	public BitSincronizacion() {
	}

	public BitSincronizacion(long idSincronizacion, Date fechaSincronizacion, Usuario usuario) {
		this.idSincronizacion = idSincronizacion;
		this.fechaSincronizacion = fechaSincronizacion;
		this.usuario = usuario;
	}

    public BitSincronizacion(BitSincronizacionDTO dto, EntityManager em) {    	
        this.idSincronizacion = dto.getIdSincronizacion();
        this.fechaSincronizacion = dto.getFechaSincronizacion();
        this.usuario = em.find(Usuario.class, dto.getUsuarioDTO().getIdUsuarioLlaveCdmx());
        if (this.usuario == null) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " +  dto.getUsuarioDTO().getIdUsuarioLlaveCdmx());
        }
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_sincronizacion", unique = true, nullable = false)
	public long getIdSincronizacion() {
		return this.idSincronizacion;
	}

	public void setIdSincronizacion(long idSincronizacion) {
		this.idSincronizacion = idSincronizacion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_sinc", nullable = false)
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_sincronizacion", nullable = false, length = 29)
	public Date getFechaSincronizacion() {
		return this.fechaSincronizacion;
	}

	public void setFechaSincronizacion(Date fechaSincronizacion) {
		this.fechaSincronizacion = fechaSincronizacion;
	}
		
}

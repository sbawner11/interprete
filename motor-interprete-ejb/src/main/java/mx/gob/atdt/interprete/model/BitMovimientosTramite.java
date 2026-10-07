package mx.gob.atdt.interprete.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "bit_movimientos_tramite", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "BitMovimientosTramite.findByIdTramiteAndIdEstatus", 	
			query = "SELECT new mx.gob.atdt.interprete.dto.BitMovimientosTramiteDTO( " +
					" b.idMovimientoTramite, tm.idTipoMovimiento, u.idUsuarioLlaveCdmx, b.idTramite, b.comentarios, b.fechaMovimiento ) " +
					" FROM BitMovimientosTramite b " +
					" JOIN b.catTiposMovimiento tm " +
					" JOIN b.usuario u " +
					" WHERE tm.idTipoMovimiento = 3 " +
					" and b.idTramite = :idTramite " +
					" and b.comentarios LIKE :comentario " +
					" ORDER BY b.fechaMovimiento desc "),

	@NamedQuery(name = "BitMovimientosTramite.findByIdTramite", 	
		query = "SELECT new mx.gob.atdt.interprete.dto.BitMovimientosTramiteDTO( " +
				" b.idMovimientoTramite, tm.idTipoMovimiento, u.idUsuarioLlaveCdmx, b.idTramite, b.comentarios, b.fechaMovimiento ) " +
				" FROM BitMovimientosTramite b " +
				" JOIN b.catTiposMovimiento tm " +
				" JOIN b.usuario u " +
				" WHERE tm.idTipoMovimiento = 3 " +
				" and b.idTramite = :idTramite " +
				" ORDER BY b.fechaMovimiento ")

})
public class BitMovimientosTramite implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7577955291017059590L;
	
	private long idMovimientoTramite;
	private CatTiposMovimiento catTiposMovimiento;
	private Usuario usuario;
	private long idTramite;
	private String comentarios;
	private Date fechaMovimiento;

	public BitMovimientosTramite() {
	}

	public BitMovimientosTramite(long idMovimientoTramite, CatTiposMovimiento catTiposMovimiento, long idTramite,
			Date fechaMovimiento) {
		this.idMovimientoTramite = idMovimientoTramite;
		this.catTiposMovimiento = catTiposMovimiento;
		this.idTramite = idTramite;
		this.fechaMovimiento = fechaMovimiento;
	}

	public BitMovimientosTramite(long idMovimientoTramite, CatTiposMovimiento catTiposMovimiento, Usuario usuario,
			long idTramite, String comentarios, Date fechaMovimiento) {
		this.idMovimientoTramite = idMovimientoTramite;
		this.catTiposMovimiento = catTiposMovimiento;
		this.usuario = usuario;
		this.idTramite = idTramite;
		this.comentarios = comentarios;
		this.fechaMovimiento = fechaMovimiento;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_movimiento_tramite", unique = true, nullable = false)
	public long getIdMovimientoTramite() {
		return this.idMovimientoTramite;
	}

	public void setIdMovimientoTramite(long idMovimientoTramite) {
		this.idMovimientoTramite = idMovimientoTramite;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_movimiento", nullable = false)
	public CatTiposMovimiento getCatTiposMovimiento() {
		return this.catTiposMovimiento;
	}

	public void setCatTiposMovimiento(CatTiposMovimiento catTiposMovimiento) {
		this.catTiposMovimiento = catTiposMovimiento;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_usuario_movimiento")
	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Column(name = "id_tramite", nullable = false)
	public long getIdTramite() {
		return this.idTramite;
	}

	public void setIdTramite(long idTramite) {
		this.idTramite = idTramite;
	}

	@Column(name = "comentarios")
	public String getComentarios() {
		return this.comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_movimiento", nullable = false, length = 29)
	public Date getFechaMovimiento() {
		return this.fechaMovimiento;
	}

	public void setFechaMovimiento(Date fechaMovimiento) {
		this.fechaMovimiento = fechaMovimiento;
	}

}

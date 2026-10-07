package mx.gob.atdt.interprete.model;

import java.io.Serializable;
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

@Entity
@Table(name = "det_registros_carga_masiva", schema = "motor_interprete")
@NamedQueries({
	@NamedQuery(name = "DetRegistrosCargaMasiva.findByIdArchivo",
			query = "SELECT NEW mx.gob.atdt.interprete.dto.DetRegistrosCargaMasivaDTO("
					+ " r.idRegistro, a.idArchivoCargaMasiva, r.idElemento, r.descripcionElemento, r.resultado, r.exitoso)"
					+ " FROM DetRegistrosCargaMasiva r"
					+ " LEFT JOIN r.archivoCargaMasiva a"
					+ " WHERE a.idArchivoCargaMasiva = :idArchivoCargaMasiva"
					+ " ORDER BY r.idRegistro ASC")
})
public class DetRegistrosCargaMasiva implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_registro")
	private Long idRegistro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_archivo_carga_masiva", nullable = false)
	private DetArchivosCargaMasiva archivoCargaMasiva;

	@Column(name = "id_elemento")
	private Long idElemento;

	@Column(name = "descripcion_elemento", nullable = false, length = 200)
	private String descripcionElemento;

	@Column(name = "resultado", nullable = false, length = 255)
	private String resultado;

	@Column(name = "exitoso", nullable = false)
	private boolean exitoso;

	public Long getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}

	public DetArchivosCargaMasiva getArchivoCargaMasiva() {
		return archivoCargaMasiva;
	}

	public void setArchivoCargaMasiva(DetArchivosCargaMasiva archivoCargaMasiva) {
		this.archivoCargaMasiva = archivoCargaMasiva;
	}

	public Long getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(Long idElemento) {
		this.idElemento = idElemento;
	}

	public String getDescripcionElemento() {
		return descripcionElemento;
	}

	public void setDescripcionElemento(String descripcionElemento) {
		this.descripcionElemento = descripcionElemento;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public boolean isExitoso() {
		return exitoso;
	}

	public void setExitoso(boolean exitoso) {
		this.exitoso = exitoso;
	}
}

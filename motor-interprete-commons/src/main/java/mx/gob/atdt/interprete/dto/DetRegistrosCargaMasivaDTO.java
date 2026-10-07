package mx.gob.atdt.interprete.dto;

import java.io.Serializable;

public class DetRegistrosCargaMasivaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idRegistro;
	private Long idArchivoCargaMasiva;
	private String descripcionElemento;
	private String resultado;
	private boolean exitoso;
	/** Campo de trabajo para resolver el elemento durante el proceso. */
	private Long idElemento;

	public DetRegistrosCargaMasivaDTO() {
	}

	public DetRegistrosCargaMasivaDTO(Long idRegistro, Long idArchivoCargaMasiva, Long idElemento,
			String descripcionElemento, String resultado, boolean exitoso) {
		this.idRegistro = idRegistro;
		this.idArchivoCargaMasiva = idArchivoCargaMasiva;
		this.idElemento = idElemento;
		this.descripcionElemento = descripcionElemento;
		this.resultado = resultado;
		this.exitoso = exitoso;
	}

	public Long getIdRegistro() {
		return idRegistro;
	}

	public void setIdRegistro(Long idRegistro) {
		this.idRegistro = idRegistro;
	}

	public Long getIdArchivoCargaMasiva() {
		return idArchivoCargaMasiva;
	}

	public void setIdArchivoCargaMasiva(Long idArchivoCargaMasiva) {
		this.idArchivoCargaMasiva = idArchivoCargaMasiva;
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

	public Long getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(Long idElemento) {
		this.idElemento = idElemento;
	}
}

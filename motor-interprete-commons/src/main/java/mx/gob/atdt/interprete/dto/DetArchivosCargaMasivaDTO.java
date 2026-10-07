package mx.gob.atdt.interprete.dto;

import java.io.Serializable;
import java.util.Date;

public class DetArchivosCargaMasivaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idArchivoCargaMasiva;
	private Date fechaCarga;
	private UsuarioDTO usuarioCargaDTO;
	private CatEstatusCargaMasivaDTO estatusCargaDTO;
	private Long idComponente;
	private UsuarioDTO usuarioAsignadoDTO;
	private String nombreArchivoOrigen;
	private String mensajeError;
	/** Campo de trabajo (no persistido): rol seleccionado para la asignación. */
	private String rol;
	/** Campo de trabajo (no persistido): ruta temporal del archivo origen. */
	private String rutaArchivoOrigen;
	/** Campo de trabajo (no persistido): ruta del archivo resultado. */
	private String rutaArchivoResultado;
	private Integer totalRegistros;
	private Integer registrosExitosos;
	private Integer registrosError;

	public DetArchivosCargaMasivaDTO() {
		usuarioCargaDTO = new UsuarioDTO();
		usuarioAsignadoDTO = new UsuarioDTO();
		estatusCargaDTO = new CatEstatusCargaMasivaDTO();
	}

	public DetArchivosCargaMasivaDTO(Long idArchivoCargaMasiva, Date fechaCarga,
			Integer idEstatusCarga, String descripcionEstatus, Long idUsuarioCarga, String correoUsuarioCarga,
			Long idComponente, Long idUsuarioAsignado, String correoUsuarioAsignado,
			String nombreArchivoOrigen, String mensajeError) {
		this();
		this.idArchivoCargaMasiva = idArchivoCargaMasiva;
		this.fechaCarga = fechaCarga;
		this.estatusCargaDTO.setIdEstatusCarga(idEstatusCarga);
		this.estatusCargaDTO.setDescripcion(descripcionEstatus);
		this.usuarioCargaDTO.setIdUsuarioLlaveCdmx(idUsuarioCarga);
		this.usuarioCargaDTO.setCorreo(correoUsuarioCarga);
		this.idComponente = idComponente;
		this.usuarioAsignadoDTO.setIdUsuarioLlaveCdmx(idUsuarioAsignado);
		this.usuarioAsignadoDTO.setCorreo(correoUsuarioAsignado);
		this.nombreArchivoOrigen = nombreArchivoOrigen;
		this.mensajeError = mensajeError;
	}
	
	public DetArchivosCargaMasivaDTO(Long idArchivoCargaMasiva, Date fechaCarga,
	        Integer idEstatusCarga, String descripcionEstatus,
	        Long idUsuarioCarga, String correoUsuarioCarga, 
	        String nombreUsuarioCarga, String primerApellidoCarga, String segundoApellidoCarga,
	        Long idComponente,
	        Long idUsuarioAsignado, String correoUsuarioAsignado,
	        String nombreUsuarioAsignado, String primerApellidoAsignado, String segundoApellidoAsignado,
	        String nombreArchivoOrigen, String mensajeError) {
	    this();
	    this.idArchivoCargaMasiva = idArchivoCargaMasiva;
	    this.fechaCarga = fechaCarga;
	    this.estatusCargaDTO.setIdEstatusCarga(idEstatusCarga);
	    this.estatusCargaDTO.setDescripcion(descripcionEstatus);
	    this.usuarioCargaDTO.setIdUsuarioLlaveCdmx(idUsuarioCarga);
	    this.usuarioCargaDTO.setCorreo(correoUsuarioCarga);
	    this.usuarioCargaDTO.setNombre(nombreUsuarioCarga);
	    this.usuarioCargaDTO.setPrimerApellido(primerApellidoCarga);
	    this.usuarioCargaDTO.setSegundoApellido(segundoApellidoCarga);
	    this.idComponente = idComponente;
	    this.usuarioAsignadoDTO.setIdUsuarioLlaveCdmx(idUsuarioAsignado);
	    this.usuarioAsignadoDTO.setCorreo(correoUsuarioAsignado);
	    this.usuarioAsignadoDTO.setNombre(nombreUsuarioAsignado);
	    this.usuarioAsignadoDTO.setPrimerApellido(primerApellidoAsignado);
	    this.usuarioAsignadoDTO.setSegundoApellido(segundoApellidoAsignado);
	    this.nombreArchivoOrigen = nombreArchivoOrigen;
	    this.mensajeError = mensajeError;
	}

	public Long getIdArchivoCargaMasiva() {
		return idArchivoCargaMasiva;
	}

	public void setIdArchivoCargaMasiva(Long idArchivoCargaMasiva) {
		this.idArchivoCargaMasiva = idArchivoCargaMasiva;
	}

	public Date getFechaCarga() {
		return fechaCarga;
	}

	public void setFechaCarga(Date fechaCarga) {
		this.fechaCarga = fechaCarga;
	}

	public UsuarioDTO getUsuarioCargaDTO() {
		return usuarioCargaDTO;
	}

	public void setUsuarioCargaDTO(UsuarioDTO usuarioCargaDTO) {
		this.usuarioCargaDTO = usuarioCargaDTO;
	}

	public CatEstatusCargaMasivaDTO getEstatusCargaDTO() {
		return estatusCargaDTO;
	}

	public void setEstatusCargaDTO(CatEstatusCargaMasivaDTO estatusCargaDTO) {
		this.estatusCargaDTO = estatusCargaDTO;
	}

	public Long getIdComponente() {
		return idComponente;
	}

	public void setIdComponente(Long idComponente) {
		this.idComponente = idComponente;
	}

	public UsuarioDTO getUsuarioAsignadoDTO() {
		return usuarioAsignadoDTO;
	}

	public void setUsuarioAsignadoDTO(UsuarioDTO usuarioAsignadoDTO) {
		this.usuarioAsignadoDTO = usuarioAsignadoDTO;
	}

	public String getNombreArchivoOrigen() {
		return nombreArchivoOrigen;
	}

	public void setNombreArchivoOrigen(String nombreArchivoOrigen) {
		this.nombreArchivoOrigen = nombreArchivoOrigen;
	}

	public String getMensajeError() {
		return mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getRutaArchivoOrigen() {
		return rutaArchivoOrigen;
	}

	public void setRutaArchivoOrigen(String rutaArchivoOrigen) {
		this.rutaArchivoOrigen = rutaArchivoOrigen;
	}

	public String getRutaArchivoResultado() {
		return rutaArchivoResultado;
	}

	public void setRutaArchivoResultado(String rutaArchivoResultado) {
		this.rutaArchivoResultado = rutaArchivoResultado;
	}

	public Integer getTotalRegistros() {
		return totalRegistros;
	}

	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	public Integer getRegistrosExitosos() {
		return registrosExitosos;
	}

	public void setRegistrosExitosos(Integer registrosExitosos) {
		this.registrosExitosos = registrosExitosos;
	}

	public Integer getRegistrosError() {
		return registrosError;
	}

	public void setRegistrosError(Integer registrosError) {
		this.registrosError = registrosError;
	}
}

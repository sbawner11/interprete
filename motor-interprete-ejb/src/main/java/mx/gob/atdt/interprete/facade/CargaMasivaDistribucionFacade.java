package mx.gob.atdt.interprete.facade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.DetArchivosCargaMasivaDAO;
import mx.gob.atdt.interprete.dao.DetAsignacionDistribucionDAO;
import mx.gob.atdt.interprete.dao.DetRegistrosCargaMasivaDAO;
import mx.gob.atdt.interprete.dto.CatEstatusCargaMasivaDTO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.DetArchivosCargaMasivaDTO;
import mx.gob.atdt.interprete.dto.DetAsignacionDistribucionDTO;
import mx.gob.atdt.interprete.dto.DetElementosMenuDTO;
import mx.gob.atdt.interprete.dto.DetRegistrosCargaMasivaDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;

@LocalBean
@Stateless
public class CargaMasivaDistribucionFacade implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(CargaMasivaDistribucionFacade.class);

	@Inject
	private DetArchivosCargaMasivaDAO detArchivosCargaMasivaDAO;
	@Inject
	private DetRegistrosCargaMasivaDAO detRegistrosCargaMasivaDAO;
	@Inject
	private DetAsignacionDistribucionDAO detAsignacionDistribucionDAO;

	@Asynchronous
	public void procesarCargaMasivaAsync(Long idArchivoCargaMasiva, String rutaArchivoOrigen, String rol,
			List<DetElementosMenuDTO> elementosComponente, UsuarioDTO administradorAsigna,
			ComponenteDTO componenteDTO) {
		procesarCargaMasiva(idArchivoCargaMasiva, rutaArchivoOrigen, rol, elementosComponente,
				administradorAsigna, componenteDTO);
	}

	public void procesarCargaMasiva(Long idArchivoCargaMasiva, String rutaArchivoOrigen, String rol,
			List<DetElementosMenuDTO> elementosComponente, UsuarioDTO administradorAsigna,
			ComponenteDTO componenteDTO) {
		DetArchivosCargaMasivaDTO archivoDTO = detArchivosCargaMasivaDAO.buscarPorId(idArchivoCargaMasiva);
		if (BeanUtils.isNull(archivoDTO) || BeanUtils.isEmpty(rutaArchivoOrigen)) {
			LOGGER.error("No se encontró el archivo de carga masiva con id {}", idArchivoCargaMasiva);
			eliminarArchivoProcesado(rutaArchivoOrigen);
			return;
		}
		try {
			convertirCsvAUtf8(rutaArchivoOrigen);
			ResultadoProceso resultado = procesarArchivoCsv(idArchivoCargaMasiva, rutaArchivoOrigen, rol,
					elementosComponente, administradorAsigna, componenteDTO, archivoDTO);
			if (BeanUtils.isNotNull(resultado)) {
				finalizarProceso(archivoDTO, resultado);
			}
		} catch (Exception e) {
			LOGGER.error("Error al procesar carga masiva de distribución id {}", idArchivoCargaMasiva, e);
			marcarErrorArchivo(archivoDTO, "Error inesperado durante el procesamiento de la carga masiva.");
		} finally {
			eliminarArchivoProcesado(rutaArchivoOrigen);
		}
	}

	private ResultadoProceso procesarArchivoCsv(Long idArchivoCargaMasiva, String rutaArchivoOrigen, String rol,
			List<DetElementosMenuDTO> elementosComponente, UsuarioDTO administradorAsigna,
			ComponenteDTO componenteDTO, DetArchivosCargaMasivaDTO archivoDTO) throws Exception {
		ResultadoProceso resultado = new ResultadoProceso();
		Map<Long, DetElementosMenuDTO> mapaPorId = construirMapaElementos(elementosComponente);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(rutaArchivoOrigen), StandardCharsets.UTF_8))) {
			resultado.header = reader.readLine();
			int codigoEncabezado = validarEncabezado(resultado.header);
			if (codigoEncabezado != Constantes.ENCABEZADO_CSV_VALIDO) {
				marcarErrorArchivo(archivoDTO, obtenerMensajeErrorEncabezado(codigoEncabezado));
				return null;
			}
			String linea;
			while ((linea = reader.readLine()) != null) {
				procesarLineaCsv(linea, idArchivoCargaMasiva, rol, administradorAsigna,
						componenteDTO, archivoDTO, mapaPorId, resultado);
			}
		}
		return resultado;
	}

	private void procesarLineaCsv(String linea, Long idArchivoCargaMasiva, String rol,
			UsuarioDTO administradorAsigna, ComponenteDTO componenteDTO, DetArchivosCargaMasivaDTO archivoDTO,
			Map<Long, DetElementosMenuDTO> mapaPorId, ResultadoProceso resultado) {
		if (BeanUtils.isEmpty(linea.trim())) {
			return;
		}
		String[] columnas = parsearColumnasCsv(linea);
		String idCsv = obtenerColumna(columnas, 0);
		String descripcionCsv = obtenerColumna(columnas, 1);
		if (BeanUtils.isEmpty(idCsv) && BeanUtils.isEmpty(descripcionCsv)) {
			return;
		}
		DetRegistrosCargaMasivaDTO registro = crearRegistro(idArchivoCargaMasiva, idCsv, descripcionCsv);
		DetElementosMenuDTO elemento = mapaPorId.get(registro.getIdElemento());
		evaluarLineaCsv(registro, elemento, idCsv, archivoDTO, rol, resultado);
		boolean guardado = persistirRegistroCsv(registro, resultado);
		if (guardado && registro.isExitoso()) {
			registrarAsignacionExitosa(registro, elemento, rol, administradorAsigna, componenteDTO,
					archivoDTO, resultado);
		}
		resultado.registros.add(registro);
	}

	private void evaluarLineaCsv(DetRegistrosCargaMasivaDTO registro, DetElementosMenuDTO elemento,
			String idCsv, DetArchivosCargaMasivaDTO archivoDTO, String rol, ResultadoProceso resultado) {
		if (esDescripcionMuyLarga(registro.getDescripcionElemento())) {
			recortarDescripcion(registro);
			marcarErrorRegistro(registro, resultado, Constantes.RESULTADO_DESCRIPCION_MUY_LARGA);
			resultado.errorDescripcionLarga = true;
			return;
		}
		if (BeanUtils.isEmpty(idCsv)) {
			marcarErrorRegistro(registro, resultado, Constantes.RESULTADO_ID_ELEMENTO_VACIO);
			resultado.errorIdVacio = true;
			return;
		}
		if (esIdFormatoInvalido(idCsv, registro)) {
			marcarErrorRegistro(registro, resultado, construirMensajeIdInvalido(idCsv));
			resultado.errorIdInvalido = true;
			return;
		}
		evaluarRegistro(registro, elemento, archivoDTO, rol, resultado);
	}

	private DetRegistrosCargaMasivaDTO crearRegistro(Long idArchivoCargaMasiva, String idCsv, String descripcionCsv) {
		DetRegistrosCargaMasivaDTO registro = new DetRegistrosCargaMasivaDTO();
		registro.setIdArchivoCargaMasiva(idArchivoCargaMasiva);
		registro.setDescripcionElemento(descripcionCsv);
		registro.setExitoso(false);
		Long idElemento = parsearIdElemento(idCsv);
		if (BeanUtils.isNotNull(idElemento)) {
			registro.setIdElemento(idElemento);
		}
		return registro;
	}

	private void evaluarRegistro(DetRegistrosCargaMasivaDTO registro, DetElementosMenuDTO elemento,
			DetArchivosCargaMasivaDTO archivoDTO, String rol, ResultadoProceso resultado) {
		if (BeanUtils.isNull(elemento)) {
			marcarErrorRegistro(registro, resultado, Constantes.RESULTADO_ELEMENTO_NO_REGISTRADO);
			resultado.errorNoRegistrado = true;
			return;
		}
		completarDescripcion(registro, elemento);
		if (resultado.procesados.contains(elemento.getIdElementoMenu())
				|| detAsignacionDistribucionDAO.existeElementoAsignadoEnComponenteYRol(
						elemento.getIdElementoMenu(), archivoDTO.getIdComponente(), rol)) {
			marcarErrorRegistro(registro, resultado, Constantes.RESULTADO_ELEMENTO_EN_OTRA_DISTRIBUCION);
			resultado.errorOtraDistribucion = true;
			return;
		}
		registro.setResultado(Constantes.RESULTADO_DISTRIBUCION_EXITOSA
				.concat(generarMensajeAdicional(registro, elemento)));
		registro.setExitoso(true);
	}

	private void marcarErrorRegistro(DetRegistrosCargaMasivaDTO registro, ResultadoProceso resultado,
			String resultadoRegistro) {
		registro.setResultado(resultadoRegistro);
		registro.setExitoso(false);
		resultado.errores++;
	}

	private boolean persistirRegistroCsv(DetRegistrosCargaMasivaDTO registro, ResultadoProceso resultado) {
		try {
			detRegistrosCargaMasivaDAO.guardar(registro);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error al guardar registro de carga masiva", e);
			aplicarErrorPersistencia(registro, resultado, e);
			return reintentarGuardadoSinDescripcion(registro);
		}
	}

	private void aplicarErrorPersistencia(DetRegistrosCargaMasivaDTO registro, ResultadoProceso resultado,
			Exception e) {
		if (registro.isExitoso()) {
			resultado.errores++;
		}
		registro.setExitoso(false);
		registro.setDescripcionElemento(Constantes.EMPTY_STRING);
		registro.setResultado(recortarTexto(resolverMensajeError(e),
				Constantes.MAX_LONGITUD_RESULTADO_CARGA_MASIVA));
		resultado.errorPersistencia = true;
	}

	private boolean reintentarGuardadoSinDescripcion(DetRegistrosCargaMasivaDTO registro) {
		try {
			detRegistrosCargaMasivaDAO.guardar(registro);
			return true;
		} catch (Exception e) {
			LOGGER.error("Error al guardar registro de carga masiva sin descripción", e);
			return false;
		}
	}

	private boolean esDescripcionMuyLarga(String descripcion) {
		return BeanUtils.isNotNull(descripcion)
				&& descripcion.length() > Constantes.MAX_LONGITUD_DESCRIPCION_CARGA_MASIVA;
	}

	private void recortarDescripcion(DetRegistrosCargaMasivaDTO registro) {
		registro.setDescripcionElemento(registro.getDescripcionElemento()
				.substring(0, Constantes.MAX_LONGITUD_DESCRIPCION_CARGA_MASIVA));
	}

	private String resolverMensajeError(Exception e) {
		if (BeanUtils.isNotNull(e.getMessage())) {
			return e.getMessage();
		}
		if (BeanUtils.isNotNull(e.getCause()) && BeanUtils.isNotNull(e.getCause().getMessage())) {
			return e.getCause().getMessage();
		}
		return "Error al guardar el registro de carga masiva.";
	}

	private String recortarTexto(String texto, int maximo) {
		if (BeanUtils.isEmpty(texto) || texto.length() <= maximo) {
			return BeanUtils.isNull(texto) ? Constantes.EMPTY_STRING : texto;
		}
		return texto.substring(0, maximo);
	}

	private void registrarAsignacionExitosa(DetRegistrosCargaMasivaDTO registro, DetElementosMenuDTO elemento,
			String rol, UsuarioDTO administradorAsigna, ComponenteDTO componenteDTO,
			DetArchivosCargaMasivaDTO archivoDTO, ResultadoProceso resultado) {
		try {
			DetAsignacionDistribucionDTO asignacion = construirAsignacion(elemento, rol,
					administradorAsigna, componenteDTO, archivoDTO);
			detAsignacionDistribucionDAO.guardar(asignacion);
			resultado.procesados.add(elemento.getIdElementoMenu());
			resultado.exitosos++;
		} catch (Exception e) {
			LOGGER.error("Error al guardar asignación de distribución para carga masiva", e);
			marcarFalloAsignacion(registro, resultado, e);
		}
	}

	private DetAsignacionDistribucionDTO construirAsignacion(DetElementosMenuDTO elemento, String rol,
			UsuarioDTO administradorAsigna, ComponenteDTO componenteDTO, DetArchivosCargaMasivaDTO archivoDTO) {
		DetAsignacionDistribucionDTO asignacion = new DetAsignacionDistribucionDTO();
		asignacion.setIdElementoAsignado(elemento.getIdElementoMenu());
		asignacion.setDesElementoAsignado(elemento.getDescripcionElemento());
		asignacion.setRol(rol);
		asignacion.setActivo(true);
		asignacion.setFechaAsignacion(new Date());
		asignacion.setComponenteDistribucionDTO(componenteDTO);
		asignacion.setUsuarioAsignadoDTO(archivoDTO.getUsuarioAsignadoDTO());
		asignacion.setAdministradorAsignaDTO(administradorAsigna);
		return asignacion;
	}

	private void marcarFalloAsignacion(DetRegistrosCargaMasivaDTO registro, ResultadoProceso resultado,
			Exception e) {
		if (registro.isExitoso()) {
			resultado.errores++;
		}
		registro.setExitoso(false);
		registro.setResultado(recortarTexto(resolverMensajeError(e),
				Constantes.MAX_LONGITUD_ERROR_ASIGNACION_CARGA_MASIVA));
		resultado.errorAsignacion = true;
		actualizarRegistroFallido(registro);
	}

	private void actualizarRegistroFallido(DetRegistrosCargaMasivaDTO registro) {
		try {
			detRegistrosCargaMasivaDAO.actualizar(registro);
		} catch (Exception e) {
			LOGGER.error("Error al actualizar registro de carga masiva como fallido", e);
		}
	}
	
	private String generarMensajeAdicional(final DetRegistrosCargaMasivaDTO registro,
			final DetElementosMenuDTO elemento) {
		if (BeanUtils.isNull(registro.getDescripcionElemento())
				|| registro.getDescripcionElemento().equals(elemento.getDescripcionElemento())) {
			return "";
		}
		return " - " + Constantes.RESULTADO_ELEMENTO_CON_DESCRIPCION_DIFERENTE;
	}

	private void completarDescripcion(DetRegistrosCargaMasivaDTO registro, DetElementosMenuDTO elemento) {
		if (BeanUtils.isEmpty(registro.getDescripcionElemento())) {
			registro.setDescripcionElemento(elemento.getDescripcionElemento());
		}
	}

	private void finalizarProceso(DetArchivosCargaMasivaDTO archivoDTO, ResultadoProceso resultado) throws Exception {
		CatEstatusCargaMasivaDTO estatus = new CatEstatusCargaMasivaDTO();
		estatus.setIdEstatusCarga(resolverIdEstatusFinal(resultado));
		if (estatus.getIdEstatusCarga() != Constantes.ID_ESTATUS_CARGA_MASIVA_CORRECTA) {
			archivoDTO.setMensajeError(obtenerMensajeErrorFinal(resultado));
		}
		archivoDTO.setEstatusCargaDTO(estatus);
		detArchivosCargaMasivaDAO.actualizarResultadoProceso(archivoDTO);
	}

	private int resolverIdEstatusFinal(ResultadoProceso resultado) {
		if (resultado.exitosos > 0 && resultado.errores == 0) {
			return Constantes.ID_ESTATUS_CARGA_MASIVA_CORRECTA;
		}
		if (resultado.exitosos > 0 && resultado.errores > 0) {
			return Constantes.ID_ESTATUS_CARGA_MASIVA_PARCIAL;
		}
		return Constantes.ID_ESTATUS_CARGA_MASIVA_ERROR_ARCHIVO;
	}

	private String obtenerMensajeErrorFinal(ResultadoProceso resultado) {
		String mensaje;
		if (resultado.exitosos == 0 && resultado.errores == 0) {
			mensaje = "El archivo no contenía registros válidos para procesar.";
		} else {
			mensaje = resolverMensajeConDetalle(resultado);
		}
		return recortarTexto(mensaje, Constantes.MAX_LONGITUD_MENSAJE_ERROR_CARGA_MASIVA);
	}

	private String resolverMensajeConDetalle(ResultadoProceso resultado) {
		String detalle = obtenerDetalleErroresElemento(resultado);
		if (resultado.exitosos > 0 && resultado.errores > 0) {
			return concatenarMensaje("Carga finalizada con registros exitosos y con error.", detalle);
		}
		if (BeanUtils.isNotEmpty(detalle)) {
			return detalle;
		}
		return "El archivo contiene registros inválidos para procesar.";
	}

	private String obtenerDetalleErroresElemento(ResultadoProceso resultado) {
		List<String> detalles = new ArrayList<>();
		if (resultado.errorIdVacio) {
			detalles.add("elementos con Id vacío");
		}
		if (resultado.errorIdInvalido) {
			detalles.add("elementos con Id de formato inválido");
		}
		if (resultado.errorDescripcionLarga) {
			detalles.add("descripciones muy largas");
		}
		if (resultado.errorNoRegistrado) {
			detalles.add("elementos no registrados en el componente de distribución");
		}
		if (resultado.errorOtraDistribucion) {
			detalles.add("elementos registrados en otra distribución");
		}
		if (resultado.errorPersistencia) {
			detalles.add("registros que no pudieron guardarse");
		}
		if (resultado.errorAsignacion) {
			detalles.add("asignaciones que no pudieron guardarse");
		}
		if (detalles.isEmpty()) {
			return null;
		}
		return "El archivo contiene " + String.join(" y ", detalles) + ".";
	}

	private String concatenarMensaje(String mensajeBase, String detalle) {
		if (BeanUtils.isEmpty(detalle)) {
			return mensajeBase;
		}
		return mensajeBase + " " + detalle;
	}

	private Map<Long, DetElementosMenuDTO> construirMapaElementos(List<DetElementosMenuDTO> elementosComponente) {
		Map<Long, DetElementosMenuDTO> mapaPorId = new HashMap<>();
		if (BeanUtils.isNull(elementosComponente)) {
			return mapaPorId;
		}
		for (DetElementosMenuDTO elemento : elementosComponente) {
			if (BeanUtils.isNotNull(elemento.getIdElementoMenu())) {
				mapaPorId.put(elemento.getIdElementoMenu(), elemento);
			}
		}
		return mapaPorId;
	}

	private void eliminarArchivoProcesado(String rutaArchivo) {
		if (BeanUtils.isEmpty(rutaArchivo)) {
			return;
		}
		File archivo = new File(rutaArchivo);
		if (archivo.exists() && !archivo.delete()) {
			LOGGER.warn("No se pudo eliminar el CSV procesado de carga masiva: {}", rutaArchivo);
		}
	}

	private void marcarErrorArchivo(DetArchivosCargaMasivaDTO archivoDTO, String mensaje) {
		CatEstatusCargaMasivaDTO estatus = new CatEstatusCargaMasivaDTO();
		estatus.setIdEstatusCarga(Constantes.ID_ESTATUS_CARGA_MASIVA_ERROR_ARCHIVO);
		archivoDTO.setEstatusCargaDTO(estatus);
		archivoDTO.setMensajeError(recortarTexto(mensaje, Constantes.MAX_LONGITUD_MENSAJE_ERROR_CARGA_MASIVA));
		detArchivosCargaMasivaDAO.actualizarResultadoProceso(archivoDTO);
	}

	public static File obtenerCarpetaCargas(Long idComponente) {
		File carpeta = new File(Environment.getPathClienteDocumentos()
				.concat(Constantes.CARPETA_CARGA_MASIVA_DISTRIBUCION)
				.concat(File.separator)
				.concat(String.valueOf(idComponente)));
		if (!carpeta.exists()) {
			carpeta.mkdirs();
		}
		return carpeta;
	}

	public static void convertirCsvAUtf8(String rutaArchivo) throws Exception {
		if (BeanUtils.isEmpty(rutaArchivo)) {
			return;
		}
		File archivo = new File(rutaArchivo);
		if (!archivo.exists() || archivo.length() == 0) {
			return;
		}
		byte[] bytes = Files.readAllBytes(archivo.toPath());
		String contenido = decodificarBytesCsv(bytes);
		Files.write(archivo.toPath(), contenido.getBytes(StandardCharsets.UTF_8));
	}

	private static String decodificarBytesCsv(byte[] bytes) {
		if (bytes.length >= 3 && bytes[0] == (byte) 0xEF && bytes[1] == (byte) 0xBB && bytes[2] == (byte) 0xBF) {
			return new String(bytes, 3, bytes.length - 3, StandardCharsets.UTF_8);
		}
		if (bytes.length >= 2 && bytes[0] == (byte) 0xFF && bytes[1] == (byte) 0xFE) {
			return new String(bytes, 2, bytes.length - 2, StandardCharsets.UTF_16LE);
		}
		if (bytes.length >= 2 && bytes[0] == (byte) 0xFE && bytes[1] == (byte) 0xFF) {
			return new String(bytes, 2, bytes.length - 2, StandardCharsets.UTF_16BE);
		}
		if (esUtf8Valido(bytes)) {
			return new String(bytes, StandardCharsets.UTF_8);
		}
		return new String(bytes, charsetWindows1252());
	}

	private static boolean esUtf8Valido(byte[] bytes) {
		CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
				.onMalformedInput(CodingErrorAction.REPORT)
				.onUnmappableCharacter(CodingErrorAction.REPORT);
		try {
			decoder.decode(ByteBuffer.wrap(bytes));
			return true;
		} catch (CharacterCodingException e) {
			return false;
		}
	}

	private static Charset charsetWindows1252() {
		try {
			return Charset.forName("windows-1252");
		} catch (Exception e) {
			return StandardCharsets.ISO_8859_1;
		}
	}

	public static boolean esEncabezadoValido(String header) {
		return validarEncabezado(header) == Constantes.ENCABEZADO_CSV_VALIDO;
	}

	public static int validarEncabezado(String header) {
		if (BeanUtils.isNull(header) || BeanUtils.isEmpty(header.trim())) {
			return Constantes.ENCABEZADO_CSV_SIN_COLUMNAS;
		}
		String[] columnas = parsearColumnasCsv(header);
		String colId = obtenerColumna(columnas, 0);
		String colDescripcion = obtenerColumna(columnas, 1);
		if (BeanUtils.isNull(columnas) || columnas.length < Constantes.COLUMNAS_CARGA_MASIVA_DISTRIBUCION
				|| BeanUtils.isEmpty(colId) || BeanUtils.isEmpty(colDescripcion)) {
			return Constantes.ENCABEZADO_CSV_SIN_COLUMNAS;
		}
		if (!columnaContieneId(colId)) {
			return Constantes.ENCABEZADO_CSV_ID_INVALIDO;
		}
		return Constantes.ENCABEZADO_CSV_VALIDO;
	}

	public static String obtenerMensajeErrorEncabezado(int codigoEncabezado) {
		if (codigoEncabezado == Constantes.ENCABEZADO_CSV_ID_INVALIDO) {
			return Constantes.MENSAJE_ENCABEZADO_CSV_ID_INVALIDO;
		}
		return Constantes.MENSAJE_ENCABEZADO_CSV_SIN_COLUMNAS;
	}

	private static boolean columnaContieneId(String columna) {
		return BeanUtils.isNotEmpty(columna)
				&& columna.toLowerCase(Locale.ROOT).contains(Constantes.TOKEN_ID_ENCABEZADO_CARGA_MASIVA);
	}

	public static String[] parsearColumnasCsv(String linea) {
		if (BeanUtils.isNull(linea)) {
			return new String[0];
		}
		CsvParser parser = new CsvParser();
		for (int i = 0; i < linea.length(); i++) {
			i = parser.procesarCaracter(linea, i);
		}
		parser.cerrarColumna();
		return parser.columnas.toArray(new String[0]);
	}

	public static String obtenerColumna(String[] columnas, int indice) {
		if (BeanUtils.isNull(columnas) || indice < 0 || indice >= columnas.length
				|| BeanUtils.isNull(columnas[indice])) {
			return Constantes.EMPTY_STRING;
		}
		return columnas[indice].trim();
	}

	private Long parsearIdElemento(String idElementoCsv) {
		if (BeanUtils.isEmpty(idElementoCsv)) {
			return null;
		}
		try {
			return Long.valueOf(idElementoCsv);
		} catch (NumberFormatException e) {
			LOGGER.warn("Id de elemento con formato inválido en carga masiva: {}", idElementoCsv, e);
			return null;
		}
	}

	private boolean esIdFormatoInvalido(String idCsv, DetRegistrosCargaMasivaDTO registro) {
		return BeanUtils.isNotEmpty(idCsv) && BeanUtils.isNull(registro.getIdElemento());
	}

	private String construirMensajeIdInvalido(String idCsv) {
		return recortarTexto(Constantes.RESULTADO_ID_ELEMENTO_INVALIDO + idCsv,
				Constantes.MAX_LONGITUD_RESULTADO_CARGA_MASIVA);
	}

	private static class ResultadoProceso {
		int exitosos;
		int errores;
		boolean errorNoRegistrado;
		boolean errorOtraDistribucion;
		boolean errorIdVacio;
		boolean errorIdInvalido;
		boolean errorDescripcionLarga;
		boolean errorPersistencia;
		boolean errorAsignacion;
		String header;
		List<DetRegistrosCargaMasivaDTO> registros = new ArrayList<>();
		Set<Long> procesados = new HashSet<>();
	}

	private static class CsvParser {
		private final List<String> columnas = new ArrayList<>();
		private final StringBuilder actual = new StringBuilder();
		private boolean entreComillas;

		private int procesarCaracter(String linea, int indice) {
			char caracter = linea.charAt(indice);
			if (caracter == '"') {
				return procesarComilla(linea, indice);
			}
			if (caracter == ',' && !entreComillas) {
				cerrarColumna();
				return indice;
			}
			actual.append(caracter);
			return indice;
		}

		private int procesarComilla(String linea, int indice) {
			if (entreComillas && indice + 1 < linea.length() && linea.charAt(indice + 1) == '"') {
				actual.append('"');
				return indice + 1;
			}
			entreComillas = !entreComillas;
			return indice;
		}

		private void cerrarColumna() {
			columnas.add(actual.toString().trim());
			actual.setLength(0);
		}
	}
}

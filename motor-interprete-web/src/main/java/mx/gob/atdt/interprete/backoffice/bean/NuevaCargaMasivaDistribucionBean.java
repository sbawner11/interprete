package mx.gob.atdt.interprete.backoffice.bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.codehaus.jettison.json.JSONException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.client.SituacionRolClient;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dto.CatMunicipiosDTO;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.CatMunicipiosDAO;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.DetArchivosCargaMasivaDAO;
import mx.gob.atdt.interprete.dao.UsuarioDAO;
import mx.gob.atdt.interprete.dto.CatEstatusCargaMasivaDTO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.ComponenteMenuDesplegableDTO;
import mx.gob.atdt.interprete.dto.DetArchivosCargaMasivaDTO;
import mx.gob.atdt.interprete.dto.DetElementosMenuDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.DetalleFormularioDAO;
import mx.gob.atdt.interprete.facade.CargaMasivaDistribucionFacade;
import mx.gob.atdt.interprete.oauth.dto.RolesUsuarioDTO;
import mx.gob.atdt.interprete.util.WebResources;

@Named
@SessionScoped
public class NuevaCargaMasivaDistribucionBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = LoggerFactory.getLogger(NuevaCargaMasivaDistribucionBean.class);

	@Inject
	private AuthenticatorBean authBean;
	@Inject
	private SeccionesProyectoBean seccionesProyectoBean;
	@Inject
	private DetArchivosCargaMasivaDAO detArchivosCargaMasivaDAO;
	@Inject
	private CargaMasivaDistribucionFacade cargaMasivaDistribucionFacade;
	@Inject
	private ComponenteDAO componenteDAO;
	@Inject
	private CatMunicipiosDAO catMunicipiosDAO;
	@Inject
	private DetalleFormularioDAO detalleFormularioDAO;
	@Inject
	private UsuarioDAO usuarioDAO;

	private List<DetElementosMenuDTO> lstElementosDistribucion;
	private List<RolesUsuarioDTO> listRoles;
	private List<UsuarioDTO> lstUsuariosActivos;
	private RolesUsuarioDTO rolesSeleccionado;
	private UsuarioDTO usuarioSeleccionado;
	private ComponenteDTO componenteDTO;
	private String nombreArchivoCarga;
	private String rutaArchivoOrigen;
	private StreamedContent archivoDescarga;
	private DetArchivosCargaMasivaDTO archivoSeleccionado;

	public String inicializar() {
		try {
			cargarComponenteYElementos();
			return Constantes.URL_ASIGNACION_MASIVA + Constantes.JSF_REDIRECT;
		} catch (Exception e) {
			LOGGER.error("Error al inicializar administrador de cargas masivas de distribución", e);
			WebResources.errorMessage("msj_error_carga_masiva_inicializar", true);
			return Constantes.URL_ASIGNACION_MASIVA + Constantes.JSF_REDIRECT;
		}
	}

	public String irNuevaCarga() {
		try {
			cargarComponenteYElementos();
			inicializarFormularioNuevaCarga();
			return Constantes.URL_NUEVA_CARGA_MASIVA_DISTRIBUCION + Constantes.JSF_REDIRECT;
		} catch (Exception e) {
			LOGGER.error("Error al preparar nueva carga masiva de distribución", e);
			WebResources.errorMessage("msj_error_carga_masiva_inicializar", true);
			return Constantes.URL_ASIGNACION_MASIVA + Constantes.JSF_REDIRECT;
		}
	}

	private void inicializarFormularioNuevaCarga() {
		listRoles = new ArrayList<>();
		listRoles.add(new RolesUsuarioDTO(Constantes.DESC_ROL_SUPERVISOR));
		listRoles.add(new RolesUsuarioDTO(Constantes.DESC_ROL_OPERADOR));
		rolesSeleccionado = new RolesUsuarioDTO();
		usuarioSeleccionado = new UsuarioDTO();
		lstUsuariosActivos = new ArrayList<>();
		descartarArchivoPendiente();
	}

	/**
	 * Accion para cuando se da clic en cancelar
	 * @return
	 */
	public String regresarListado() {
		descartarArchivoPendiente();
		return inicializar();
	}

	public String regresarAsignacion() {
		descartarArchivoPendiente();
		return Constantes.URL_ASIGNAR_DISTRIBUCION + Constantes.JSF_REDIRECT;
	}

	public void listenerRolSeleccionado() {
		usuarioSeleccionado = new UsuarioDTO();
		SituacionRolClient situacionRolClient = new SituacionRolClient();
		try {
			lstUsuariosActivos = situacionRolClient.obtenerUsuariosRol(
					Long.parseLong(seccionesProyectoBean.getAccesoLlaveDTO().getClaveSistema()),
					rolesSeleccionado.getRol(),
					true);
			if (BeanUtils.isEmpty(lstUsuariosActivos)) {
				lstUsuariosActivos = new ArrayList<>();
				WebResources.addValidationMessage("msj_sin_usuarios_rol_llave",
						new Object[] { rolesSeleccionado.getRol() }, false);
			}
		} catch (NumberFormatException | JSONException e) {
			LOGGER.error("Error al consultar usuarios por rol en Llave MX para carga masiva: ", e);
			lstUsuariosActivos = new ArrayList<>();
			WebResources.addValidationMessage("msj_sin_usuarios_rol_llave",
					new Object[] { rolesSeleccionado.getRol() }, false);
		}
	}

	public void listenerUsuarioSeleccionado() {
		if (BeanUtils.isNull(lstUsuariosActivos) || BeanUtils.isNull(usuarioSeleccionado)) {
			return;
		}
		completarDatosUsuarioSeleccionado();
		UsuarioDTO usuarioTmp = usuarioDAO.buscarPorId(usuarioSeleccionado.getIdUsuarioLlaveCdmx());
		if (BeanUtils.isNull(usuarioTmp)) {
			usuarioDAO.guardar(usuarioSeleccionado);
		}
	}

	private void completarDatosUsuarioSeleccionado() {
		for (UsuarioDTO usuario : lstUsuariosActivos) {
			if (usuario.getIdUsuarioLlaveCdmx() == usuarioSeleccionado.getIdUsuarioLlaveCdmx()) {
				usuarioSeleccionado.setCorreo(usuario.getCorreo());
				usuarioSeleccionado.setCurp(usuario.getCurp());
				usuarioSeleccionado.setNombre(usuario.getNombre());
				usuarioSeleccionado.setPrimerApellido(usuario.getPrimerApellido());
				usuarioSeleccionado.setSegundoApellido(usuario.getSegundoApellido());
				usuarioSeleccionado.setTelefono(usuario.getTelefono());
				break;
			}
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			UploadedFile file = event.getFile();
			reemplazarArchivoPendiente(persistirArchivoCarga(file), file.getFileName());
		} catch (Exception e) {
			LOGGER.error("Error al guardar archivo temporal de carga masiva", e);
			WebResources.errorMessage("msj_error_carga_masiva_procesar", true);
		}
	}

	public String iniciarCargaMasiva() {
		try {
			if (!validarDatosCarga() || !validarArchivoSeleccionado()) {
				return Constantes.RETURN_SAME_PAGE;
			}
			CargaMasivaDistribucionFacade.convertirCsvAUtf8(rutaArchivoOrigen);
			List<String> registros = leerRegistrosDesdeRuta(rutaArchivoOrigen);
			if (!validarRegistrosCarga(registros)) {
				descartarArchivoPendiente();
				return Constantes.RETURN_SAME_PAGE;
			}
			registrarYLanzarProceso(rutaArchivoOrigen, nombreArchivoCarga);
			liberarReferenciaArchivoProcesado();
			WebResources.addSuccessMessage("msj_carga_masiva_proceso_iniciado", true);
			return inicializar();
		} catch (Exception e) {
			LOGGER.error("Error al iniciar carga masiva de distribución", e);
			WebResources.errorMessage("msj_error_carga_masiva_procesar", true);
			return Constantes.RETURN_SAME_PAGE;
		}
	}

	private boolean validarDatosCarga() {
		if (BeanUtils.isNull(rolesSeleccionado) || BeanUtils.isEmpty(rolesSeleccionado.getRol())) {
			WebResources.addValidationMessage("msj_carga_masiva_rol_requerido", false);
			return false;
		}
		if (BeanUtils.isNull(usuarioSeleccionado) || usuarioSeleccionado.getIdUsuarioLlaveCdmx() <= 0) {
			WebResources.addValidationMessage("msj_carga_masiva_usuario_requerido", false);
			return false;
		}
		return true;
	}

	private boolean validarArchivoSeleccionado() {
		if (BeanUtils.isEmpty(rutaArchivoOrigen) || BeanUtils.isEmpty(nombreArchivoCarga)) {
			WebResources.addValidationMessage("msj_carga_masiva_archivo_requerido", false);
			return false;
		}
		if (!nombreArchivoCarga.toLowerCase(Locale.ROOT).endsWith(Constantes.EXTENSION_CSV)) {
			WebResources.addValidationMessage("msj_carga_masiva_extension_invalida", false);
			return false;
		}
		return true;
	}

	private boolean validarRegistrosCarga(List<String> registros) {
		if (registros == null) {
			return false;
		}
		if (registros.size() > Constantes.MAXIMO_REGISTROS_CARGA_MASIVA_DISTRIBUCION) {
			WebResources.addValidationMessage("msj_carga_masiva_maximo_registros", false);
			return false;
		}
		if (registros.isEmpty()) {
			WebResources.addValidationMessage("msj_carga_masiva_sin_registros", false);
			return false;
		}
		return true;
	}

	private void registrarYLanzarProceso(String rutaOrigen, String nombreArchivo) {
		DetArchivosCargaMasivaDTO archivoDTO = construirArchivoEnProceso(nombreArchivo);
		Long idArchivo = detArchivosCargaMasivaDAO.guardar(archivoDTO);
		cargaMasivaDistribucionFacade.procesarCargaMasivaAsync(idArchivo, rutaOrigen,
				rolesSeleccionado.getRol(), lstElementosDistribucion,
				authBean.getUsuarioLogueado(), componenteDTO);
	}

	private DetArchivosCargaMasivaDTO construirArchivoEnProceso(String nombreArchivo) {
		DetArchivosCargaMasivaDTO archivoDTO = new DetArchivosCargaMasivaDTO();
		archivoDTO.setFechaCarga(new Date());
		archivoDTO.setUsuarioCargaDTO(authBean.getUsuarioLogueado());
		CatEstatusCargaMasivaDTO estatus = new CatEstatusCargaMasivaDTO();
		estatus.setIdEstatusCarga(Constantes.ID_ESTATUS_CARGA_MASIVA_EN_PROCESO);
		archivoDTO.setEstatusCargaDTO(estatus);
		archivoDTO.setIdComponente(componenteDTO.getIdComponente());
		archivoDTO.setUsuarioAsignadoDTO(usuarioSeleccionado);
		archivoDTO.setNombreArchivoOrigen(nombreArchivo);
		return archivoDTO;
	}

	private void cargarComponenteYElementos() throws Exception {
		componenteDTO = componenteDAO.buscarPorId(
				seccionesProyectoBean.getDetDistribucionDTO().getComponenteDTO().getIdComponente());
		lstElementosDistribucion = new ArrayList<>();
		if (BeanUtils.isNull(componenteDTO)) {
			return;
		}
		int idTipo = componenteDTO.getCatTipoComponenteDTO().getIdTipoComponente().intValue();
		if (idTipo == Constantes.ID_COMPONENTE_DATOS_DOMICILIO) {
			cargarElementosDomicilio();
		} else if (idTipo == Constantes.ID_COMPONENTE_MENU_DESPLEGABLE) {
			cargarElementosMenu();
		}
	}

	private void cargarElementosDomicilio() {
		List<CatMunicipiosDTO> lstAlcaldias = catMunicipiosDAO.buscarTodos();
		if (BeanUtils.isNull(lstAlcaldias)) {
			return;
		}
		for (CatMunicipiosDTO municipio : lstAlcaldias) {
			DetElementosMenuDTO elemento = new DetElementosMenuDTO();
			elemento.setIdElementoMenu((long) municipio.getIdMunicipio());
			elemento.setDescripcionElemento(municipio.getDescripcion());
			lstElementosDistribucion.add(elemento);
		}
	}

	private void cargarElementosMenu() throws Exception {
		ComponenteMenuDesplegableDTO menu = detalleFormularioDAO.consultarDetalleMenuDesplegable(componenteDTO);
		lstElementosDistribucion = detalleFormularioDAO.consultarElementosElementosMenuDesplegable(menu);
		if (BeanUtils.isNull(lstElementosDistribucion)) {
			lstElementosDistribucion = new ArrayList<>();
		}
	}

	private List<String> leerRegistrosDesdeRuta(String rutaArchivo) throws Exception {
		List<String> registros = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(rutaArchivo), StandardCharsets.UTF_8))) {
			String header = reader.readLine();
			int codigoEncabezado = CargaMasivaDistribucionFacade.validarEncabezado(header);
			if (codigoEncabezado != Constantes.ENCABEZADO_CSV_VALIDO) {
				WebResources.addValidationMessage(obtenerKeyErrorEncabezado(codigoEncabezado), false);
				return null;
			}
			String linea;
			while ((linea = reader.readLine()) != null) {
				agregarRegistroSiAplica(registros, linea);
			}
		}
		return registros;
	}

	private void agregarRegistroSiAplica(List<String> registros, String linea) {
		if (BeanUtils.isEmpty(linea.trim())) {
			return;
		}
		String[] columnas = CargaMasivaDistribucionFacade.parsearColumnasCsv(linea);
		String idElemento = CargaMasivaDistribucionFacade.obtenerColumna(columnas, 0);
		String descripcion = CargaMasivaDistribucionFacade.obtenerColumna(columnas, 1);
		if (BeanUtils.isEmpty(idElemento) && BeanUtils.isEmpty(descripcion)) {
			return;
		}
		registros.add(idElemento);
	}

	private String obtenerKeyErrorEncabezado(int codigoEncabezado) {
		if (codigoEncabezado == Constantes.ENCABEZADO_CSV_ID_INVALIDO) {
			return "msj_carga_masiva_header_id_invalido";
		}
		return "msj_carga_masiva_sin_columnas";
	}

	private String persistirArchivoCarga(UploadedFile file) throws Exception {
		File carpeta = CargaMasivaDistribucionFacade.obtenerCarpetaCargas(componenteDTO.getIdComponente());
		String nombre = "origen_" + System.currentTimeMillis() + "_" + file.getFileName();
		File destino = new File(carpeta, nombre);
		copiarStreamAArchivo(file.getInputStream(), destino);
		CargaMasivaDistribucionFacade.convertirCsvAUtf8(destino.getAbsolutePath());
		return destino.getAbsolutePath();
	}

	private void reemplazarArchivoPendiente(String nuevaRuta, String nombreArchivo) {
		String rutaAnterior = rutaArchivoOrigen;
		rutaArchivoOrigen = nuevaRuta;
		nombreArchivoCarga = nombreArchivo;
		eliminarArchivoSiExiste(rutaAnterior);
	}

	private void descartarArchivoPendiente() {
		eliminarArchivoSiExiste(rutaArchivoOrigen);
		liberarReferenciaArchivoProcesado();
	}

	private void liberarReferenciaArchivoProcesado() {
		rutaArchivoOrigen = null;
		nombreArchivoCarga = null;
	}

	private void eliminarArchivoSiExiste(String ruta) {
		if (BeanUtils.isEmpty(ruta)) {
			return;
		}
		File archivo = new File(ruta);
		if (archivo.exists() && !archivo.delete()) {
			LOGGER.warn("No se pudo eliminar el archivo pendiente de carga masiva: {}", ruta);
		}
	}

	private void copiarStreamAArchivo(InputStream inputStream, File destino) throws Exception {
		try (InputStream in = inputStream; FileOutputStream fos = new FileOutputStream(destino)) {
			byte[] buffer = new byte[Constantes.TAMAÑO_BUFFER];
			int leidos;
			while ((leidos = in.read(buffer)) != -1) {
				fos.write(buffer, 0, leidos);
			}
		}
	}

	public List<DetElementosMenuDTO> getLstElementosDistribucion() {
		return lstElementosDistribucion;
	}

	public List<RolesUsuarioDTO> getListRoles() {
		return listRoles;
	}

	public List<UsuarioDTO> getLstUsuariosActivos() {
		return lstUsuariosActivos;
	}

	public RolesUsuarioDTO getRolesSeleccionado() {
		return rolesSeleccionado;
	}

	public void setRolesSeleccionado(RolesUsuarioDTO rolesSeleccionado) {
		this.rolesSeleccionado = rolesSeleccionado;
	}

	public UsuarioDTO getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioDTO usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public ComponenteDTO getComponenteDTO() {
		return componenteDTO;
	}

	public String getNombreArchivoCarga() {
		return nombreArchivoCarga;
	}

	public StreamedContent getArchivoDescarga() {
		return archivoDescarga;
	}

	public DetArchivosCargaMasivaDTO getArchivoSeleccionado() {
		return archivoSeleccionado;
	}

	public void setArchivoSeleccionado(DetArchivosCargaMasivaDTO archivoSeleccionado) {
		this.archivoSeleccionado = archivoSeleccionado;
	}
}

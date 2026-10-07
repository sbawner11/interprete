package mx.gob.atdt.interprete.backoffice.bean;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.primefaces.PrimeFaces;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.client.SituacionRolClient;
import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.dto.CatMunicipiosDTO;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.CatMunicipiosDAO;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.DetAsignacionDistribucionDAO;
import mx.gob.atdt.interprete.dao.UsuarioDAO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.ComponenteMenuDesplegableDTO;
import mx.gob.atdt.interprete.dto.DetAsignacionDistribucionDTO;
import mx.gob.atdt.interprete.dto.DetDistribucionDTO;
import mx.gob.atdt.interprete.dto.DetElementosMenuDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.DetalleFormularioDAO;
import mx.gob.atdt.interprete.oauth.dto.RolesUsuarioDTO;
import mx.gob.atdt.interprete.util.GenericLazyDataModel;
import mx.gob.atdt.interprete.util.WebResources;

@Named
@SessionScoped
public class AsignarSolicitudesBean implements Serializable{
	
	private static final long serialVersionUID = -5817272615685733929L;
	private static final Logger LOGGER = LoggerFactory.getLogger(AsignarSolicitudesBean.class);
	
	private static final String NOMBRE_ARCHIVO_PLANTILLA="plantilla_carga_masiva"; 

	@Inject 
	private AuthenticatorBean authBean;
	@Inject
	private SeccionesProyectoBean seccionesProyectoBean;	
	@Inject 
	private DetalleFormularioDAO detalleFormularioDAO;	
	@Inject
	private ComponenteDAO componenteDAO;	
	@Inject
	private CatMunicipiosDAO catMunicipiosDAO;	
	@Inject
	private DetAsignacionDistribucionDAO detAsignacionDistribucionDAO;	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	private LazyDataModel<DetAsignacionDistribucionDTO> lazyModelAsignaciones;
	
	private List<DetElementosMenuDTO> lstElementosDistribucion;
	private List<RolesUsuarioDTO> listRoles;
	private List<UsuarioDTO> lstUsuariosActivos;
	
	private ComponenteMenuDesplegableDTO menuDesplegableDistribucion;
	private DetAsignacionDistribucionDTO asignacionDistribucionDTO;
	private ComponenteDTO componenteDTO;
	private DetDistribucionDTO detDistribucion;
	private RolesUsuarioDTO rolesSeleccionado;
	private UsuarioDTO usuarioSeleccionado;
	private DetAsignacionDistribucionDTO asignacionSeleccionada;
	
	private String rolSeleccionado;
	
	private boolean habilitaExportar;
	
	/**
	 * Método que inicializa la asignación de solicitudes mediante un componente de Distribución
	 * @return
	 */
	public String inicializar() {
		lstUsuariosActivos = new ArrayList<>();
		usuarioSeleccionado = new UsuarioDTO();
		Long idComponente = seccionesProyectoBean.getDetDistribucionDTO().getComponenteDTO().getIdComponente();
		int totalRegistros = detAsignacionDistribucionDAO.contarTodosActivosPorComponente(idComponente);
		setHabilitaExportar(false);
		if (totalRegistros == 0) {
			setHabilitaExportar(true);
	        WebResources.addValidationMessage("msj_no_distribuciones", true);
	    }
		setLazyModelAsignaciones(idComponente);
		
		listRoles = new ArrayList<>();
		listRoles.add(new RolesUsuarioDTO(Constantes.DESC_ROL_SUPERVISOR));
		listRoles.add(new RolesUsuarioDTO(Constantes.DESC_ROL_OPERADOR));	
		
		rolesSeleccionado = new RolesUsuarioDTO();
		
		//Se precarga el rol supervisor y los usuarios asociados a ese rol.
		//rolesSeleccionado.setRol(Constantes.DESC_ROL_SUPERVISOR);
		//listenerRolSeleccionado();
		rolSeleccionado = Constantes.EMPTY_STRING;
		
		lstElementosDistribucion = new ArrayList<>();
		
		componenteDTO = componenteDAO.buscarPorId(seccionesProyectoBean.getDetDistribucionDTO().getComponenteDTO().getIdComponente());
		
		if(BeanUtils.isNotNull(componenteDTO)) {
			//Se inicializa el objeto de asignación de distribución y se precarga la información del componente
			asignacionDistribucionDTO = new DetAsignacionDistribucionDTO();
			asignacionDistribucionDTO.setComponenteDistribucionDTO(componenteDTO);
			
			if(seccionesProyectoBean.isHabilitaDistribucion()) {
				asignaLstELementoDistribucion(componenteDTO);				
			}
		} else {
			WebResources.addValidationMessage("msj_no_componente_distribucion", true);
		}
		
		return Constantes.URL_ASIGNAR_DISTRIBUCION + Constantes.JSF_REDIRECT;
	}
	
	/**
	 * Metodo auxiliar de inicializacion lazyModelAsignaciones
	 * @param idComponente
	 * @author Ramiro Luna Torres
	 */
	private void setLazyModelAsignaciones(Long idComponente) {
		setLazyModelAsignaciones(new GenericLazyDataModel<>(
				(first, pageSize, sortBy, filterBy) -> {
					String campoOrden = "idAsignacionDistribucion";
					String direccionOrden = "DESC";
		            if (sortBy != null && !sortBy.isEmpty()) {
		                SortMeta sortMeta = sortBy.values().iterator().next();
		                campoOrden = sortMeta.getField();
		                direccionOrden = sortMeta.getOrder().isAscending() ? "ASC" : "DESC";
		            }
		            if(campoOrden.contains("correo")) {
		            	campoOrden="ua.correo";
		            }else {
		            	campoOrden="dad.".concat(campoOrden);
		            }
		            List<DetAsignacionDistribucionDTO> listaSegmentada = detAsignacionDistribucionDAO.buscarTodosActivosPorComponente(idComponente, first, pageSize, campoOrden, direccionOrden);

		            if (BeanUtils.isNull(listaSegmentada)) {
		                return new ArrayList<>();
		            }
		            return listaSegmentada;
				},
				() -> detAsignacionDistribucionDAO.contarTodosActivosPorComponente(idComponente)
				));
	}
	
	/**
	 * Metodo auxiliar en la asignacion elementos distribucion
	 * @param componenteDTO
	 */
	private void asignaLstELementoDistribucion(ComponenteDTO componenteDTO) {
		try {
			if(componenteDTO.getCatTipoComponenteDTO().getIdTipoComponente().intValue() == Constantes.ID_COMPONENTE_DATOS_DOMICILIO) {
				
				List<CatMunicipiosDTO> lstAlcaldias = catMunicipiosDAO.buscarTodos();
				if(BeanUtils.isNotNull(lstAlcaldias)) {
					lstElementosDistribucion = lstAlcaldias.stream()
														   .map(map->{
																DetElementosMenuDTO elementoMenu = new DetElementosMenuDTO();
																elementoMenu.setIdElementoMenu((long)map.getIdMunicipio());
																elementoMenu.setDescripcionElemento(map.getDescripcion());
																return elementoMenu;
															}).collect(Collectors.toList());
				}
				
			} else if(componenteDTO.getCatTipoComponenteDTO().getIdTipoComponente().intValue() == Constantes.ID_COMPONENTE_MENU_DESPLEGABLE) {
				menuDesplegableDistribucion = detalleFormularioDAO.consultarDetalleMenuDesplegable(seccionesProyectoBean.getDetDistribucionDTO().getComponenteDTO());
				lstElementosDistribucion = detalleFormularioDAO.consultarElementosElementosMenuDesplegable(menuDesplegableDistribucion);
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al inicializar la asignación de distribución de trámites: ", e);
		}
		
		if(BeanUtils.isNull(lstElementosDistribucion)) {
			WebResources.addValidationMessage("msj_no_opciones_distribucion", true);		
		}
	}
	
	/**
	 * Método listener del listado de elementos de distribución
	 */
	public void listenerDistribucion() {
		lstUsuariosActivos = new ArrayList<>();
		rolesSeleccionado = new RolesUsuarioDTO();
		usuarioSeleccionado = new UsuarioDTO();
		Optional<String> descripcionElemento = lstElementosDistribucion.stream()
																	.filter(flt-> flt.getIdElementoMenu().longValue() == asignacionDistribucionDTO.getIdElementoAsignado().longValue())
																	.findFirst()
																	.map(DetElementosMenuDTO::getDescripcionElemento);
		if(descripcionElemento.isPresent()) {
			asignacionDistribucionDTO.setDesElementoAsignado(descripcionElemento.get());
		}
	}
	
	/**
	 * Método listener que obtiene un listado de usuarios que tienen asignado el rol seleccionado.
	 */
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
			LOGGER.error("Error al consultar los operadores activos en Llave MX: ", e);
			lstUsuariosActivos = new ArrayList<>();
			WebResources.addValidationMessage("msj_sin_usuarios_rol_llave",
					new Object[] { rolesSeleccionado.getRol() }, false);
		}
	}
	
	/**
	 * Método axiliar que se ejecuta al seleccionar un operado y setea la información completa del operador.
	 */
	public void listenerUsuarioSeleccionado() {
		usuarioSeleccionado = lstUsuariosActivos.stream()
											  .filter(ftl-> ftl.getIdUsuarioLlaveCdmx() == usuarioSeleccionado.getIdUsuarioLlaveCdmx())
											  .findFirst()
											  .orElse(new UsuarioDTO());
		/**Se valida si el usuario seleccionado del listado ya se encuentra registrado en BD, de lo contrario se registra **/	
		UsuarioDTO usuarioTmp = usuarioDAO.buscarPorId(usuarioSeleccionado.getIdUsuarioLlaveCdmx());
		if(BeanUtils.isNull(usuarioTmp)) {
			usuarioDAO.guardar(usuarioSeleccionado);
		}
    }
	
	/**
	 * Método que realiza la asignación del distribución del usuario seleccionado
	 * 
	 * @return
	 */
	public String asignarDistribucion() {
		String redirect = Constantes.RETURN_SAME_PAGE;
		if(detAsignacionDistribucionDAO.existeUsuarioAsignadoAElemento(usuarioSeleccionado.getIdUsuarioLlaveCdmx(), asignacionDistribucionDTO.getIdElementoAsignado())) {
			WebResources.addValidationMessage("msj_asignacion_usuario_elemento_existente", false);
		} else {
			try {
				//Se setea la información de la asignación
				asignacionDistribucionDTO.setUsuarioAsignadoDTO(usuarioSeleccionado);
				asignacionDistribucionDTO.setAdministradorAsignaDTO(authBean.getUsuarioLogueado());
				asignacionDistribucionDTO.setFechaAsignacion(new Date());
				asignacionDistribucionDTO.setActivo(true);
				asignacionDistribucionDTO.setRol(rolesSeleccionado.getRol());
				
				//Se guarda asignación en la BD
				detAsignacionDistribucionDAO.guardar(asignacionDistribucionDTO);
				inicializar();
				WebResources.addSuccessMessage("asignacion_exitosa", false);
			} catch (Exception e) {
				LOGGER.error("Ocurrió un error al intentar asignar la distribución de solicitudes: ", e);
			}
		}
		return redirect;
	}
	
	/**
	 * Método auxiliar que realiza el borrado lógico de configuración de distribución del usuario actual
	 * @return
	 */
	public String desvincularAsignacion() {
		String redirect = Constantes.RETURN_SAME_PAGE;
		try {
			detAsignacionDistribucionDAO.actualizarActivoAsignacion(asignacionSeleccionada);
			
			cerrarModalDesvincularDistribucion();
			
			inicializar();
			WebResources.addSuccessMessage("desvinculacion_exitosa", false);
		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al intentar desvicular al usuario de la distribución asignada: ", e);
		}
		return redirect;
	}
	
	/**
	 * Método que muestra la modal para desvincular la distribucion
	 */
	public void mostrarModalRevertirEstatus(Long idAsignacionDesvincular) {
		asignacionSeleccionada = new DetAsignacionDistribucionDTO();
		asignacionSeleccionada.setIdAsignacionDistribucion(idAsignacionDesvincular);
		asignacionSeleccionada.setActivo(false);
		asignacionSeleccionada.setFechaDesvinculacion(new Date());
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('mdlDesvincular').show();");
	}
	
	/**
	 * Método que cierra la modal para desvincular la distribucion
	 */
	public void cerrarModalDesvincularDistribucion() {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('mdlDesvincular').hide();");
	}
	
	/**
	 * Metodo para la descarga de la plantilla para carga masiva
	 * @author Ramiro Luna Torres
	 */
	public void descargaPlantilla() {
		String rutaArchivo = Environment.getPathArchivosTemporales().concat(seccionesProyectoBean.getProyectoDTO().getIdProyecto() + Constantes.SEPARADOR_RUTA);
		String nombreArchivo = NOMBRE_ARCHIVO_PLANTILLA+Constantes.EXTENSION_CSV;
		String pathDocumento = rutaArchivo.concat(nombreArchivo);
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();

			response.reset();
			response.setHeader("Content-Type", Constantes.CONTENTTYPE_CSV);
			response.setHeader("Content-Disposition", "attachment;filename="+nombreArchivo);

			String[] encabezados = {"Id elemento","Elementos ".concat(componenteDTO.getTituloCampo())};
			List<StringBuilder> lstDatos = lstElementosDistribucion.stream().map(map -> {
				StringBuilder sb = new StringBuilder();
				sb.append(map.getIdElementoMenu()).append(Constantes.SEPARACION_COLUMNA_CSV).append(map.getDescripcionElemento());
				return sb;
			}).collect(Collectors.toList());
			
			BeanUtils.generarCsvDinamico(rutaArchivo, nombreArchivo, encabezados, lstDatos);
			if(Files.exists(Paths.get(pathDocumento))) {
				try (OutputStream out = response.getOutputStream();
					     InputStream in = new FileInputStream(pathDocumento)) {
						byte[] bytesBuffer = new byte[2048];
					    int bytesRead;
					    while ((bytesRead = in.read(bytesBuffer)) != -1) {
					        out.write(bytesBuffer, 0, bytesRead);
					    }
					    out.flush();
					}
				facesContext.responseComplete();
				
				Files.delete(Paths.get(pathDocumento));
				WebResources.successMessage("msj_plantilla_descarga_completa", true);
			}else {
				LOGGER.warn("No existe plantilla en ruta {}", pathDocumento);
				WebResources.addErrorMessage("msj_plantilla_descarga_error", true);
			}
		}catch (Exception e) {
			LOGGER.error("No se logro descargar plantilla " + NOMBRE_ARCHIVO_PLANTILLA, e);
			WebResources.addErrorMessage("msj_plantilla_descarga_error", true);
		}
	}
	
	/**
	 * Metodo para limpiar filtros de busquedad
	 * @author Ramiro Luna Torres
	 */
	public void limpiarFiltros() {
		asignacionDistribucionDTO = new DetAsignacionDistribucionDTO();
		usuarioSeleccionado = new UsuarioDTO();
		rolesSeleccionado = new RolesUsuarioDTO();
		rolSeleccionado = Constantes.EMPTY_STRING;
		inicializar();
	}
	
	/**
	 * Metodo para aplicar filtro de consulta de distribucion de solicitudes
	 * @author Ramiro Luna Torres
	 */
	public void filtrarAsignaDistribucion() {
		Long idUsuarioLlaveCdmx=usuarioSeleccionado.getIdUsuarioLlaveCdmx()!=0?usuarioSeleccionado.getIdUsuarioLlaveCdmx():null;
		if(BeanUtils.isNull(asignacionDistribucionDTO.getIdElementoAsignado()) && BeanUtils.isNull(idUsuarioLlaveCdmx)
				&& BeanUtils.isNull(rolesSeleccionado.getRol())) {
			WebResources.addValidationMessage("msj_filtro_vacio", false);
		}else {
			Long idComponente = seccionesProyectoBean.getDetDistribucionDTO().getComponenteDTO().getIdComponente();
			Long idElementoAsignado = asignacionDistribucionDTO.getIdElementoAsignado();
			String rol = rolesSeleccionado.getRol();
			int totalRegistros = detAsignacionDistribucionDAO.contarTodosActivosPorIdElementoAndIdUsuarioAsignadoAndRol(idComponente, idElementoAsignado, idUsuarioLlaveCdmx, rol);
			setHabilitaExportar(false);
			if (totalRegistros == 0) {
				setHabilitaExportar(true);
		        WebResources.addValidationMessage("msj_no_distribuciones_filtro", true);
		    }
			setLazyModelAsignaciones(idComponente, idElementoAsignado, rol, idUsuarioLlaveCdmx);
		}
	}
	
	/**
	 * Metodo auxiliar para la carga LazyModelAsignaciones
	 * 
	 * @param idComponente
	 * @param idElementoAsignado
	 * @param rol
	 * @param idUsuarioLlaveCdmx
	 * @author Ramiro Luna Torres
	 */
	private void setLazyModelAsignaciones(Long idComponente, Long idElementoAsignado, String rol,Long idUsuarioLlaveCdmx) {	
		setLazyModelAsignaciones(new GenericLazyDataModel<>(
				(first, pageSize, sortBy, filterBy) -> {
					String campoOrden = "idAsignacionDistribucion";
					String direccionOrden = "DESC";
					if (sortBy != null && !sortBy.isEmpty()) {
						SortMeta sortMeta = sortBy.values().iterator().next();
						campoOrden = sortMeta.getField();
						direccionOrden = sortMeta.getOrder().isAscending() ? "ASC" : "DESC";
					}
					if(campoOrden.contains("correo")) {
						campoOrden="ua.correo";
					}else {
						campoOrden="dad.".concat(campoOrden);
					}
					List<DetAsignacionDistribucionDTO> listaSegmentada = detAsignacionDistribucionDAO.buscarTodosActivosPorIdElementoAndIdUsuarioAsignadoAndRol(idComponente, idElementoAsignado, idUsuarioLlaveCdmx, rol, first, pageSize, campoOrden, direccionOrden);
					
					if (BeanUtils.isNull(listaSegmentada)) {
						return new ArrayList<>();
					}
					return listaSegmentada;
				},
				() -> detAsignacionDistribucionDAO.contarTodosActivosPorIdElementoAndIdUsuarioAsignadoAndRol(idComponente, idElementoAsignado, idUsuarioLlaveCdmx, rol)
				));
	}
	
	public String irAsignacionMasiva() {
	  return Constantes.URL_ASIGNACION_MASIVA + Constantes.JSF_REDIRECT;
	}	
	
	/**GETTER´s y SETTER´s**/
	
	public List<DetElementosMenuDTO> getLstElementosDistribucion() {
		return lstElementosDistribucion;
	}

	public void setLstElementosDistribucion(List<DetElementosMenuDTO> lstElementosDistribucion) {
		this.lstElementosDistribucion = lstElementosDistribucion;
	}

	public List<RolesUsuarioDTO> getListRoles() {
		return listRoles;
	}

	public void setListRoles(List<RolesUsuarioDTO> listRoles) {
		this.listRoles = listRoles;
	}

	public List<UsuarioDTO> getLstUsuariosActivos() {
		return lstUsuariosActivos;
	}

	public void setLstUsuariosActivos(List<UsuarioDTO> lstUsuariosActivos) {
		this.lstUsuariosActivos = lstUsuariosActivos;
	}

	public DetDistribucionDTO getDetDistribucion() {
		return detDistribucion;
	}

	public void setDetDistribucion(DetDistribucionDTO detDistribucion) {
		this.detDistribucion = detDistribucion;
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

	public String getRolSeleccionado() {
		return rolSeleccionado;
	}

	public void setRolSeleccionado(String rolSeleccionado) {
		this.rolSeleccionado = rolSeleccionado;
	}

	public ComponenteMenuDesplegableDTO getMenuDesplegableDistribucion() {
		return menuDesplegableDistribucion;
	}

	public void setMenuDesplegableDistribucion(ComponenteMenuDesplegableDTO menuDesplegableDistribucion) {
		this.menuDesplegableDistribucion = menuDesplegableDistribucion;
	}

	public DetAsignacionDistribucionDTO getAsignacionDistribucionDTO() {
		return asignacionDistribucionDTO;
	}

	public void setAsignacionDistribucionDTO(DetAsignacionDistribucionDTO asignacionDistribucionDTO) {
		this.asignacionDistribucionDTO = asignacionDistribucionDTO;
	}

	public ComponenteDTO getComponenteDTO() {
		return componenteDTO;
	}

	public void setComponenteDTO(ComponenteDTO componenteDTO) {
		this.componenteDTO = componenteDTO;
	}

	public CatMunicipiosDAO getCatMunicipiosDAO() {
		return catMunicipiosDAO;
	}

	public void setCatMunicipiosDAO(CatMunicipiosDAO catMunicipiosDAO) {
		this.catMunicipiosDAO = catMunicipiosDAO;
	}

	public DetAsignacionDistribucionDTO getAsignacionSeleccionada() {
		return asignacionSeleccionada;
	}

	public void setAsignacionSeleccionada(DetAsignacionDistribucionDTO asignacionSeleccionada) {
		this.asignacionSeleccionada = asignacionSeleccionada;
	}

	public LazyDataModel<DetAsignacionDistribucionDTO> getLazyModelAsignaciones() {
		return lazyModelAsignaciones;
	}

	public void setLazyModelAsignaciones(LazyDataModel<DetAsignacionDistribucionDTO> lazyModelAsignaciones) {
		this.lazyModelAsignaciones = lazyModelAsignaciones;
	}

	public boolean isHabilitaExportar() {
		return habilitaExportar;
	}

	public void setHabilitaExportar(boolean habilitaExportar) {
		this.habilitaExportar = habilitaExportar;
	}
}

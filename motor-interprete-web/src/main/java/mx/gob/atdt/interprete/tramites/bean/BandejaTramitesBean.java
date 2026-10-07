package mx.gob.atdt.interprete.tramites.bean;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import mx.gob.atdt.interprete.acceso.bean.AuthenticatorBean;
import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.common.formatos.FormatoRespuestaPDF;
import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.ArchivoRespuestaDAO;
import mx.gob.atdt.interprete.dao.BitMovimientosTramiteDAO;
import mx.gob.atdt.interprete.dao.CatEstatusTramiteDAO;
import mx.gob.atdt.interprete.dao.DetElementosTokenDAO;
import mx.gob.atdt.interprete.dao.DetLineaCapturaDAO;
import mx.gob.atdt.interprete.dao.UsuarioDAO;
import mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO;
import mx.gob.atdt.interprete.dto.BitMovimientosTramiteDTO;
import mx.gob.atdt.interprete.dto.CatEstatusLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.CatEstatusTramiteDTO;
import mx.gob.atdt.interprete.dto.ControlComponentesDTO;
import mx.gob.atdt.interprete.dto.DetElementosTokenDTO;
import mx.gob.atdt.interprete.dto.DetLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.LineaCapturaDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.TramiteFirmaElectronicaDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.estructura.formulario.dao.EstructuraFormularioDAO;
import mx.gob.atdt.interprete.exception.InterpreteException;
import mx.gob.atdt.interprete.facade.LineaCapturaFacade;
import mx.gob.atdt.interprete.formulario.dao.FormularioDAO;
import mx.gob.atdt.interprete.formularios.application.GenerarFormularioApplication;
import mx.gob.atdt.interprete.linea.captura.client.EstatusLineaCapturaClient;
import mx.gob.atdt.interprete.linea.captura.dto.RequestConsultaLCDTO;
import mx.gob.atdt.interprete.linea.captura.dto.ResponseEstatusLCDTO;
import mx.gob.atdt.interprete.util.WebResources;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;

@Named
@SessionScoped
public class BandejaTramitesBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8215932795436782731L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BandejaTramitesBean.class);
	
	@Inject
	private GenerarFormularioApplication formularioApplication;
	
	@Inject
	private EstructuraFormularioDAO estructuraFormularioDAO;
	
	@Inject
	private FormularioDAO formularioDAO;
	
	@Inject
	private AuthenticatorBean authenticatorBean;
	
	@Inject
	private SeccionesProyectoBean seccionesProyectoBean;
	
	@Inject
	private DetElementosTokenDAO detElementosTokenDAO;
	
	@Inject
	private ArchivoRespuestaDAO archivoRespuestaDAO;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject 
	private DetLineaCapturaDAO detLineaCapturaDAO;
	@Inject
	private BitMovimientosTramiteDAO bitMovimientosTramiteDAO;
	@Inject
	private CatEstatusTramiteDAO catEstatusTramiteDAO;
	
	@Inject 
	private LineaCapturaFacade lineaFacturaFacade;
	
	private List<TramiteDTO> lstTramites;
	
	private List<CatEstatusTramiteDTO> lstEstatusTramite;
	
	private TramiteDTO tramiteBusqueda;
	
	private TramiteDTO tramiteSeleccionado;
	private List<BitMovimientosTramiteDTO> lstHistorico;
	
	private String msgErrorFirma;
	
	 public void cargaMsgErrorFirmante() {
	      if(msgErrorFirma != null ) {
	    	  WebResources.validationMessage(msgErrorFirma, true);
	      }
	      
	      msgErrorFirma = null;
	 }
	
	/**
	 * Método que inicializa la bandeja de trámites del ciudadano
	 * @return
	 */
	public String inicializar() {		
		tramiteBusqueda = new TramiteDTO();
		tramiteBusqueda.setUsuario(new UsuarioDTO(authenticatorBean.getUsuarioLogueado().getIdUsuarioLlaveCdmx()));
		lstTramites = new ArrayList<TramiteDTO>();
		lstEstatusTramite = new ArrayList<CatEstatusTramiteDTO>();
		
		try {
			lstEstatusTramite = formularioDAO.consultarEstatusTramite();
			//Se valida si existe tabla trámites
			if(estructuraFormularioDAO.existeTablaTramites()) {
				lstTramites = formularioDAO.consultarTamitesUsuario(tramiteBusqueda);
			
				if (seccionesProyectoBean.isHabilitarFirmadoTramites()) {
					validaTramiteFirmadoParaDescarga();
				}
			}
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error en la consulta de trámites:: ", e);
			WebResources.errorMessage("msj_error_busqueda", true);
		}
		if (BeanUtils.isNull(lstTramites) || lstTramites.isEmpty()) {
			WebResources.validationMessage("msj_busqueda_sin_resultados", true);
		} else {
			validarEstatusLCBandeja(this.tramiteBusqueda);
			asignarEstiloTramite();	
		}
		return Constantes.RETURN_BANDEJA_TRAMITES_PAGE + Constantes.JSF_REDIRECT;		
	}
	
	/**
	 * Método que realiza la búsqueda de trámites con los filtros ingresados.
	 */
	public void filtrarTramites() {				
		boolean isFiltrosCorrectos = true;
		if(BeanUtils.isNotNull(tramiteBusqueda.getFechaDesde()) && BeanUtils.isNotNull(tramiteBusqueda.getFechaHasta())) {
			if(tramiteBusqueda.getFechaDesde().after(tramiteBusqueda.getFechaHasta())) {
				isFiltrosCorrectos = false;
				WebResources.validationMessage("msj_fechas_incorrectas", false);
			}
		}
		if(isFiltrosCorrectos) {
			try {				
				lstTramites = formularioDAO.consultarTamitesUsuario(tramiteBusqueda);			
				if (BeanUtils.isNull(lstTramites) || lstTramites.isEmpty()) {
					WebResources.validationMessage("msj_busqueda_sin_resultados", true);
				} else {
					asignarEstiloTramite();	
				}
				if (seccionesProyectoBean.isHabilitarFirmadoTramites()) {
					validaTramiteFirmadoParaDescarga();
				}
			} catch (Exception e) {
				LOGGER.error("Ocurrió un error al filtrar los trámites:: ", e);
				WebResources.errorMessage("msj_error_busqueda", true);
			}
		}		
	}
	
	/**
	 * Método para activa el botón de descarga para el ciudadano si es que 
	 * cuenta ya con la firma para su trámite
	 */
	public void validaTramiteFirmadoParaDescarga() {
		try {
			if (BeanUtils.isNotEmpty(lstTramites)) {
				for (int i = 0; i < lstTramites.size(); i++) {
					List<TramiteFirmaElectronicaDTO> lstFirma = null;
					lstFirma = formularioDAO.consultarFirmaTramite(lstTramites.get(i));
					if (BeanUtils.isNotEmpty(lstFirma) || BeanUtils.isNotNull(lstFirma)) {
						if (lstFirma.get(0).getCadenaFirmada() != null) {
							lstTramites.get(i).setTramiteFirmado(true);
						} else {
							lstTramites.get(i).setTramiteFirmado(false);
						}
					}
				}		
			}
		} catch (Exception e) {
			LOGGER.error("Error al validar firma", e);
		}
	}
	
	/**
	 * Método auxiliar que inicializa los valores de los campos ingresados para búsqueda de trámites y el listado 
	 * de trámites actual.
	 */
	public void limpiarFiltros() {
		inicializarValoresBusqueda();
		filtrarTramites();
	}
	
	/**
	 * Método que inicializa los valores que son utilizados en la búsqueda de Trámites
	 */
	private void inicializarValoresBusqueda() {
		tramiteBusqueda.setFechaDesde(null);
		tramiteBusqueda.setFechaHasta(null);
		tramiteBusqueda.setCatEstatusTramiteDTO(new CatEstatusTramiteDTO());
	}
	
	/**
	 * Método auxiliar que asigna los estilos para cada estatus de trámite.
	 */
	private void asignarEstiloTramite() {
	    lstTramites.forEach(tramite -> {
	        CatEstatusTramiteDTO estatus = tramite.getCatEstatusTramiteDTO();
	        
	        // descripción personalizada
	        if (estatus.getDescripcionPersonalizada() != null && !estatus.getDescripcionPersonalizada().trim().isEmpty()) {
	        	if(seccionesProyectoBean.getProyectoDTO().isAviso()) {
	        		estatus.setDescripcionAviso(estatus.getDescripcionPersonalizada());
	        	} else {
	        		estatus.setDescripcion(estatus.getDescripcionPersonalizada());
	        	}
	        } 
	        // aplicar si no hay descripción personalizada
	        else {
	            if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO) {
	                estatus.setDescripcion(Constantes.DESC_EN_REVISION);	               
	            } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_CORRECIONES) {
	                estatus.setDescripcion(Constantes.DESC_PREVENIDO);	              
	            } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_CORREGIDO) {
	                estatus.setDescripcion(Constantes.DESC_EN_REVISION);	                
	            } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_REVISADO) {  
	            	estatus.setDescripcion(Constantes.DESC_EN_REVISION);	                                                               
	            }
	        }
	        
	        // asignar estilos
	        if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_EN_CAPTURA) {
	            tramite.setEstiloEstatus("estatus-en-captura");
	            tramite.setEstiloTxtEstatus("estatus-en-captura-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_PENDIENTE_PAGO) {
	            tramite.setEstiloEstatus("estatus-pendiente-pago");
	            tramite.setEstiloTxtEstatus("estatus-pendiente-pago-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_ENVIADO) {
	            tramite.setEstiloEstatus("estatus-enviado");
	            tramite.setEstiloTxtEstatus("estatus-enviado-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_CORRECIONES) {
	            tramite.setEstiloEstatus("estatus-en-correcciones");
	            tramite.setEstiloTxtEstatus("estatus-en-correcciones-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_CORREGIDO) {
	            tramite.setEstiloEstatus("estatus-corregido");
	            tramite.setEstiloTxtEstatus("estatus-corregido-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO) {
	            tramite.setEstiloEstatus("estatus-rechazado");
	            tramite.setEstiloTxtEstatus("estatus-rechazado-txt");
	        } else if (estatus.getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO) {
	            tramite.setEstiloEstatus("estatus-aprobado");
	            tramite.setEstiloTxtEstatus("estatus-aprobado-txt");
	        }
	        asignarEstiloEstatusLCTramite(tramite);
	        
	        tramite.setProyectoDTO(formularioApplication.getProyectoDTO());
	    });
	}
	
	
	
	
//	/**
//	 * Método que muestra el formulario para registro de nuevo trámite
//	 * @return
//	 */
//	public String nuevoTramite() {
////TODO pendiente revisar método para complementar un formulario en progreso de captura.		
//		String redirect = Constantes.RETURN_SAME_PAGE;		
//		redirect = Constantes.RETURN_FORMULARIO_PAGE + Constantes.JSF_REDIRECT;		
//		
//		return redirect;
//	}
	
	/**
	 * Método que realizará la generación de un archivo al concluir la validación de un trámite, este método genera el archivo
	 * final que el ciudadano descarga desde su bandeja de trámites.
	 * 
	 * Los archivos que pueden generarse son 3:
	 * 
	 * 1.- Si la configuración de archivos de respuesta se encuentra habilitada para utilizar el firmado, se tendrán 2 posibles 
	 * 		archivos de respuesta con su respectiva plantilla, uno será para el trámites "Aceptados" y la segunda para trámites 
	 * 		"Rechazados", desde el motor se valida que cuando es utilizado el firmado, por fuerza sean sincronizadas las 2 plantillas.
	 * 
	 * 2.- Si la configuración de archivos de respuesta no tiene habilitado el firmado, solo se tendrá 1 posible archivo de respuesta
	 * 		o plantilla para la generación del archivo final no importanto si el estatus del trámite es "Aprobado" o "Rechazado".
	 * 
	 * 26/06/2026 WEBHOOK
	 * Este metodo sirvio de base para generar el metodo NotificacionWebHookFacade.generarComprobanteFinalizado
	 * cualquier cambio que sufra respecto a la generacion de comprobantes replicar la logica en dicho metodo
	 * @return
	 * @throws IOException
	 */
	public void generarArchivo(TramiteDTO tramiteActual) {
		FormatoRespuestaPDF formatoPDF = new FormatoRespuestaPDF();
		try {
			/**Dependiendo del estatus del trámite, se tomará el archivo respuesta para la descarga **/			
			ArchivosRespuestaTokenDTO archivoRespuesta = null;
			if(seccionesProyectoBean.isHabilitarFirmadoTramites()) {				
				if(tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO) {
					archivoRespuesta = seccionesProyectoBean.getArchivosRespuestaTokenAceptacionDTO();					
				}			
				if(tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_RECHAZADO) {
					archivoRespuesta = seccionesProyectoBean.getArchivosRespuestaTokenRechazoDTO();
				}				
			} else {
				if(tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == Constantes.ID_ESTATUS_APROBADO) {
					archivoRespuesta = seccionesProyectoBean.getArchivosRespuestaTokenConclusionDTO();					
				}	else {					
					archivoRespuesta = seccionesProyectoBean.getArchivosRespuestaTokenRegistroDTO();
				}
			}			
			if (archivoRespuesta == null || archivoRespuesta.getIdArchivoRespuesta() == null) {
				WebResources.addValidationMessage("msj_no_existe_comprobante", false);
	            return;
	        }
			List<DetElementosTokenDTO> lstDetElementosTokenDTO = detElementosTokenDAO.buscarPorIdArchivoRespuesta(archivoRespuesta.getIdArchivoRespuesta());
			if(archivoRespuesta != null && lstDetElementosTokenDTO != null) {
				Map<String, ControlComponentesDTO> mapControlComponentes = formularioApplication.getMapControlComponentes();
				List<SeccionesFormularioDTO> lstSeccionesDTO = formularioApplication.generarListaSecciones();
				Map<String, Object> mapRespuestas = new HashMap<>();
				mapRespuestas = cargarDatosRespuesta(tramiteActual, mapControlComponentes, lstSeccionesDTO, lstDetElementosTokenDTO);	
				
//				if(!mapRespuestas.isEmpty()) {
					List<TramiteFirmaElectronicaDTO> lstFirma = null;
					tramiteActual.setUsuario(usuarioDAO.buscarPorId(tramiteActual.getUsuario().getIdUsuarioLlaveCdmx()));
					if (seccionesProyectoBean.isHabilitarFirmadoTramites()) {
						lstFirma = formularioDAO.consultarFirmaTramite(tramiteActual);
						formatoPDF.generarDocumento(seccionesProyectoBean.getProyectoDTO().getIdProyecto(), archivoRespuesta.getRutaArchivoRespuesta(), mapRespuestas, tramiteActual, lstDetElementosTokenDTO, seccionesProyectoBean.isHabilitarFirmadoTramites(), 
								archivoRespuesta, seccionesProyectoBean.getSecurityDomainDTO().getUrlSistema(), lstFirma.get(0), mapControlComponentes, lstSeccionesDTO);
					} else {
						formatoPDF.generarDocumento(seccionesProyectoBean.getProyectoDTO().getIdProyecto(), archivoRespuesta.getRutaArchivoRespuesta(), mapRespuestas, tramiteActual, lstDetElementosTokenDTO, seccionesProyectoBean.isHabilitarFirmadoTramites(), 
								archivoRespuesta, null, null, mapControlComponentes, lstSeccionesDTO);
					}
					String directorioPDF = Environment.getPathPlantillasClientePdf()+ seccionesProyectoBean.getProyectoDTO().getIdProyecto()+ "/" + "formatosRespuesta/";
					String extensionPDF = ".pdf";
					File pdf = new File(directorioPDF + tramiteActual.getFolioSeguimiento() + extensionPDF);
					if (pdf.exists()) {
						FacesContext facesContext = FacesContext.getCurrentInstance();
						HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
						response.reset();
						response.setHeader("Content-Type", "application/pdf");
						response.setHeader("Content-Disposition", "attachment;filename=" + pdf.getName());

						OutputStream responseOutputStream = response.getOutputStream();
						InputStream fileInputStream = new FileInputStream(pdf);

						byte[] bytesBuffer = new byte[2048];
						int bytesRead;
						try {
							while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
								responseOutputStream.write(bytesBuffer, 0, bytesRead);
							}
							responseOutputStream.flush();
							fileInputStream.close();
							responseOutputStream.close();
							facesContext.responseComplete();
							WebResources.successMessage("msj_comprobante_generado", false);
						} catch (Exception e) {
							WebResources.addValidationMessage("msj_error_comprobante", false);	
							LOGGER.error("Ocurrió un error al descargar el documento: ", e);
						} finally {
							if(fileInputStream != null) {
								try {
									fileInputStream.close();
								} catch (Exception e2) {
									LOGGER.error("Ocurrió un error al cerrar el recurso fileInputStream ", e2);
								}
							}
						}
					} else {
						WebResources.addValidationMessage("msj_no_comprobante", false);	
					}					
//				} else {					
//					WebResources.addValidationMessage("msj_sin_datos_comprobante", false);
//				}
			} else {
				WebResources.addValidationMessage("msj_error_conf_comprobante", false);
			}
		} catch (Exception e) {
			LOGGER.error("No se encontró el PDF para la descarga del tramite " + tramiteActual.getFolioSeguimiento(), e);
			WebResources.addErrorMessage("msj_error_comprobante", false);
		}		
	}

	public void generarResolucion(TramiteDTO tramiteActual) {
		File pdfR = new File(tramiteActual.getCatEstatusTramiteDTO().getIdEstatusTramite() == 9 ? 
				tramiteActual.getRutaDocumentoResolucionPositiva() : tramiteActual.getRutaDocumentoResolucionNegativa());
		try {
			
			if (pdfR.exists()) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				HttpServletResponse responseR = (HttpServletResponse) facesContext.getExternalContext().getResponse();
				responseR.reset();
				responseR.setHeader("Content-Type", "application/pdf");
				responseR.setHeader("Content-Disposition", "attachment;filename=" + pdfR.getName());
				
				OutputStream responseOutputStream = responseR.getOutputStream();
				InputStream fileInputStream = new FileInputStream(pdfR);
				byte[] bytesBuffer = new byte[2048];
				int bytesRead;
				
				try {
					while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
						responseOutputStream.write(bytesBuffer, 0, bytesRead);
					}
					responseOutputStream.flush();
					fileInputStream.close();
					responseOutputStream.close();
					facesContext.responseComplete();
					WebResources.successMessage("msj_resolucion_generada", false);
					
				} catch (Exception e) {
					WebResources.addValidationMessage("msj_error_resolucion", false);	
					LOGGER.error("Ocurrió un error al descargar la resolución: ", e);
				} finally {
					if(fileInputStream != null) {
						try {
							fileInputStream.close();
						} catch (Exception e2) {
							LOGGER.error("Ocurrió un error al cerrar el recurso fileInputStream ", e2);
						}
					}
				}
			} else {
				WebResources.addValidationMessage("msj_error_resolucion", false);	
			}
		} catch (Exception e) {
			LOGGER.error("No se encontró el PDF de la Resolución para la descarga desde bandeja del funcionario " + tramiteActual.getFolioSeguimiento(), e);
			WebResources.addErrorMessage("msj_error_resolucion", false);
		}		

	}
	
	public void generarArchivoMotivoRevocacion(TramiteDTO tramiteActual) {
		try {
			File documento = new File(tramiteActual.getRutaDocumentoRevocado());
			if (documento.exists()) {
				FacesContext facesContext = FacesContext.getCurrentInstance();
				HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
				response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Disposition", "attachment;filename=" + documento.getName());

				OutputStream responseOutputStream = response.getOutputStream();
				InputStream fileInputStream = new FileInputStream(documento);

				byte[] bytesBuffer = new byte[2048];
				int bytesRead;
				try {
					while ((bytesRead = fileInputStream.read(bytesBuffer)) > 0) {
						responseOutputStream.write(bytesBuffer, 0, bytesRead);
					}
					responseOutputStream.flush();
					fileInputStream.close();
					responseOutputStream.close();
					facesContext.responseComplete();
					WebResources.successMessage("msj_documento_revocado_generado", false);
				} catch (Exception e) {
					WebResources.addValidationMessage("msj_error_documento_revocado", false);	
					LOGGER.error("Ocurrió un error al descargar el documento: ", e);
				} finally {
					if(fileInputStream != null) {
						try {
							fileInputStream.close();
						} catch (Exception e2) {
							LOGGER.error("Ocurrió un error al cerrar el recurso fileInputStream ", e2);
						}
					}
				}
			} else {
				WebResources.addValidationMessage("msj_no_documento_revocado", false);	
			}					
		} catch (Exception e) {
			LOGGER.error("No se encontró el PDF que complementa el motivo de rechazo para la descarga " + tramiteActual.getFolioSeguimiento(), e);
			WebResources.addErrorMessage("msj_error_documento_revocado", false);
		}		
	}
	
	/**
	 * Metodo que se utiliza para recuperar las respuestas de un formulario
	 **/
	private Map<String, Object> cargarDatosRespuesta(TramiteDTO tramiteActual, Map<String, 
			ControlComponentesDTO> mapControlComponentes, List<SeccionesFormularioDTO> lstSeccionesDTO,
			List<DetElementosTokenDTO> lstDetElementosTokenDTO) throws Exception {
		
		Map<String, Object> mapRespuestas = new HashMap<>();
		
		/**
		 * Se consulta las respuestas del formulario sección por sección
		 **/
		for (SeccionesFormularioDTO seccionTemp : lstSeccionesDTO) {
			
			List<String> lstColumnas = new ArrayList<String>();
			for (Iterator<Map.Entry<String, ControlComponentesDTO>> elementos = mapControlComponentes.entrySet()
					.iterator(); elementos.hasNext();) {
				Map.Entry<String, ControlComponentesDTO> elementoTmp = elementos.next();
				if (elementoTmp.getValue().getNombreTabla().equals(
						Constantes.NOMBRE_BASE_TABLAS.concat(seccionTemp.getIdSeccionFormulario().toString()))) {
					lstColumnas.add(elementoTmp.getValue().getNombreColumna());
				}
			}
			
			/**
			 * Se verifica que la sección no contenga únicamente Componentes informativos,
			 * dichos componentes no insertan datos en la BD
			 **/
			if (lstColumnas.size() > 0) {
				
				//Se iteran las columnas y solo se consultan aquellas que coinciden con un token a localizar
				for(int c = 0; c < lstColumnas.size(); c ++) {
					for(int t = 0; t < lstDetElementosTokenDTO.size(); t ++) {
						if(BeanUtils.isNotNull(lstDetElementosTokenDTO.get(t).getIdComponente())) {
								/*&& ( lstColumnas.get(c).equals("componente_".concat(lstDetElementosTokenDTO.get(t).getIdComponente().toString()))
										|| lstColumnas.get(c).equals("componente_".concat(lstDetElementosTokenDTO.get(t).getIdComponente().toString()).concat("_1"))
												|| lstColumnas.get(c).equals("componente_".concat(lstDetElementosTokenDTO.get(t).getIdComponente().toString()).concat("_2"))
														|| lstColumnas.get(c).equals("componente_".concat(lstDetElementosTokenDTO.get(t).getIdComponente().toString()).concat("_3"))))*/ 	
							/**
							 * Se consulta si existe información de la sección para el trámite actual, esto
							 * se puede dar por trámites existentes a los que se les habilita una nueva
							 * sección que anteriormente no fue registrada
							 **/
							if (formularioDAO.existeRegistroSeccionPorTramite(tramiteActual, 
									Constantes.ESQUEMA_INTERPRETE.concat(".").concat(Constantes.NOMBRE_BASE_TABLAS
											.concat(seccionTemp.getIdSeccionFormulario().toString())))) {

								/**
								 * Se consultar la información registrada en Base de datos de la Sección actual
								 **/
								Map<String, Object> respuestasFormulario = formularioDAO.consultarRespuestasSeccion(tramiteActual,
										Constantes.ESQUEMA_INTERPRETE.concat(".")
												.concat(Constantes.NOMBRE_BASE_TABLAS
														.concat(seccionTemp.getIdSeccionFormulario().toString())),
										mapControlComponentes, lstColumnas);

								/**
								 * Se coloca la información recuperada en el map de respuestas
								 **/
								for (Entry<String, Object> respuestaTemp : respuestasFormulario.entrySet()) {
									mapRespuestas.put(respuestaTemp.getKey(), respuestaTemp.getValue());
									LOGGER.info("Map actualizado: key=" + respuestaTemp.getKey() + ", value=" + respuestaTemp.getValue());
								}
							}							
						}						
					}
				}				
			} 
			LOGGER.info("Map final de respuestas: " + mapRespuestas);
		}
		return mapRespuestas;
		
	}
	
	
	/**
	 * Método que muestra la modal para ver el motivo de la revocación del aviso 
	 */
	public void mostrarModalVerRevocacion(TramiteDTO tramite) {
		tramiteSeleccionado = new TramiteDTO();
		tramiteSeleccionado = tramite;
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalVerRevocacion').show();");
	}
	
	/**
	 * Método que cierra la modal para ver el motivo de la revocación del aviso 
	 */
	public void cerrarModalVerRevocacion() {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalVerRevocacion').hide();");
	}
	
	/**
	 * Método auxiliar para mostrar en la modal el nombre del documento cargado
	 * 
	 * @param rutaArchivo
	 * @return
	 */
	public String obtenerFileName(String rutaArchivo) {
		if (rutaArchivo != null && !rutaArchivo.isEmpty()) {
			return FilenameUtils.getName(rutaArchivo);
		} else {
			return Constantes.EMPTY_STRING;
		}
	}
	
	/**
	 * Metodo auxiliar para asignar estilos de estatus linea captura 
	 * @param tramite
	 */
	private void asignarEstiloEstatusLCTramite(TramiteDTO tramite) {
		if(seccionesProyectoBean.getProyectoDTO().isProyectoLineaCaptura()
				&& BeanUtils.isDiferent(Constantes.ID_ESTATUS_EN_CAPTURA, tramite.getCatEstatusTramiteDTO().getIdEstatusTramite())
				&& BeanUtils.isNotNull(tramite.getLineaCapturaDTO())) {
			CatEstatusLineaCapturaDTO estatus = tramite.getLineaCapturaDTO().getCatEstatusLineaCaptura();
	        // asignar estilos
	        if (estatus.getIdEstatusLineaCaptura() == Constantes.ID_LC_ESTATUS_PENDIENTE) {
	            tramite.setEstiloLCEstatus("estatus-pendiente-pago");
	            tramite.setEstiloLCTxtEstatus("estatus-pendiente-pago-txt");
	        } else if (estatus.getIdEstatusLineaCaptura() == Constantes.ID_LC_ESTATUS_PAGADO) {
	            tramite.setEstiloLCEstatus("estatus-aprobado");
	            tramite.setEstiloLCTxtEstatus("estatus-aprobado-txt");
	        } else if (estatus.getIdEstatusLineaCaptura() == Constantes.ID_LC_ESTATUS_VENCIDA) {
	            tramite.setEstiloLCEstatus("estatus-rechazado");
	            tramite.setEstiloLCTxtEstatus("estatus-rechazado-txt");
	        }
        }
	}
	
	/**
	 * 
	 * Metodo auxiliar para la validacion de estatus LC de la bandeja del ciudadado
	 * 
	 * @param tramite
	 */
	public void validarEstatusLCBandeja(final TramiteDTO tramiteBusqueda) {
		ProyectoDTO proyectoDTO = seccionesProyectoBean.getProyectoDTO();
		if(BeanUtils.isFalse(proyectoDTO.isProyectoLineaCaptura())) {
			return;
		}
		boolean actualizarLstTramites=false;
		DetLineaCapturaDTO detLineaCapturaDTO = detLineaCapturaDAO.buscarPorIdProyecto(proyectoDTO.getIdProyecto());
    	RequestConsultaLCDTO requestConsulta = new RequestConsultaLCDTO();
    	requestConsulta.setIdDependencia(detLineaCapturaDTO.getDependenciaPagoDTO().getIdDependenciaPago());
    	Gson gson = new Gson();
		for(TramiteDTO tramite:lstTramites){
			if(validaConsultaEstatusLC(tramite)) {
				boolean procesaConsulta = procesaConsultaEstatusLineaCaptura(tramite, requestConsulta, gson);
				if(procesaConsulta) {
					actualizarLstTramites = true;
				}else {
					break;
				}
			}
		}
		if(actualizarLstTramites) {
			try {
				lstTramites = formularioDAO.consultarTamitesUsuario(tramiteBusqueda);
				if (seccionesProyectoBean.isHabilitarFirmadoTramites()) {
					validaTramiteFirmadoParaDescarga();
				}
			} catch (Exception e) {
				LOGGER.error("Ocurrió un error en la consulta de trámites:: ", e);
				WebResources.validationMessage("msj_error_busqueda", true);
			}
			
		}
		
	}
	
	/**
	 * Metodo auxiliar para validar que se debe solicitar la
	 * la consulta de linea de captura del tramite
	 * @param tramiteSeleccionado
	 * @return
	 */
	public boolean validaConsultaEstatusLC(TramiteDTO tramiteSeleccionado) {
		boolean isConsultaEstatusLC=false;
		Predicate<CatEstatusTramiteDTO> prTramite = p -> p.getIdEstatusTramite() == Constantes.ID_ESTATUS_PENDIENTE_PAGO;
		Predicate<LineaCapturaDTO> prLineaCaptura = BeanUtils::isNotNull;
		Predicate<CatEstatusLineaCapturaDTO> prEstLineaCaptura = p -> p.getIdEstatusLineaCaptura().equals(Constantes.ID_LC_ESTATUS_PENDIENTE);
		try {
			if(prTramite.test(tramiteSeleccionado.getCatEstatusTramiteDTO()) 
					&& prLineaCaptura.test(tramiteSeleccionado.getLineaCapturaDTO())) {
				isConsultaEstatusLC = prEstLineaCaptura.test(tramiteSeleccionado.getLineaCapturaDTO().getCatEstatusLineaCaptura());
			}
		}catch(NullPointerException ex){
			 LOGGER.error("No hay informacion sobre la linea de captura:  ", ex);
			 WebResources.addValidationMessage("msj_lc_error_consulta", false);
		}
		return isConsultaEstatusLC;
	}
	
	/**
	 * 
	 * 
	 * @param tramiteSeleccionado tramite seleccionado
	 */
	/**
	 * Metodo auxiliar para el consumo del webservice estatus LC
	 * 
	 * @param tramiteSeleccionado tramite seleccionado
	 * @param requestConsulta request de consulta instanciado
	 * @param gson Gson
	 * @return true or false
	 */
	public boolean procesaConsultaEstatusLineaCaptura(TramiteDTO tramiteSeleccionado, RequestConsultaLCDTO requestConsulta, Gson gson) {
		///queda pendiente esta validacion respecto al estado del tramite
		boolean procesaConsulta=true;
		ResponseEstatusLCDTO estatusLCDTO = null;
		EstatusLineaCapturaClient consultaLCClient = new EstatusLineaCapturaClient();
		Predicate<ResponseEstatusLCDTO> prResponseEstatus = p -> p.getCodigo().equals(0);
		try {			
			requestConsulta.setIdSolicitud(tramiteSeleccionado.getLineaCapturaDTO().getSolicitudLineaCaptura());
			requestConsulta.setLineaCaptura(tramiteSeleccionado.getLineaCapturaDTO().getLineaCaptura());
			LOGGER.info("requestConsulta estatus LC tramite: {} ", requestConsulta);
			estatusLCDTO = consultaLCClient.consultaEstatusLC(seccionesProyectoBean.getSecurityDomainLineasCapturaDTO().getUrlSistema(), requestConsulta);
			LOGGER.info("termina consulta estatus LC tramite: {} ", estatusLCDTO);
			if(prResponseEstatus.test(estatusLCDTO)) {
				lineaFacturaFacade.procesarRespuestaEstatusLC(estatusLCDTO, tramiteSeleccionado, gson);
			} else {
				LOGGER.warn("error al consultar Linea de captura: {} , mensajeError: {}", tramiteSeleccionado.getLineaCapturaDTO().getLineaCaptura(), estatusLCDTO.getMensajeError());
			}
		} catch (URISyntaxException e) {
			WebResources.errorMessage("msj_ce_lc_error_general", true);
			procesaConsulta = false;
			LOGGER.error("Ocurrio un error sintaxis URL consulta Linea de Captura:: ", e);
		}catch (ConnectException e) {
			WebResources.errorMessage("msj_ce_lc_error_conexion", true);
			procesaConsulta = false;
			LOGGER.error("Ocurrio un error de conexion al consulta Linea de Captura:: ", e);
		}catch (InterpreteException e) {
			WebResources.errorMessage("msj_ce_lc_error_general", true);
			LOGGER.error("Ocurrio un error de controlado interprete al consulta Linea de Captura:: ", e);
		}
		return procesaConsulta;
	}
	
	/**
	 * Metodo para abrir el modal de Consulta Estatus Linea Captura
	 */
	public void mostrarModalEstatusLC(TramiteDTO tramite) {
		this.tramiteSeleccionado = new TramiteDTO();
		this.tramiteSeleccionado = tramite;
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalEstatusLC').show();");
	}

	/**
	 * Metodo para cerrar el modal de Consulta Estatus Linea Captura
	 */
	public void cerrarModalEstatusLC() {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('modalEstatusLC').hide();");
	}
	
	public String buscarFechaEnvio(long idTramite) {
		BitMovimientosTramiteDTO  movDTO = bitMovimientosTramiteDAO.buscarPorIdtramiteYEstatus(idTramite, Constantes.ID_ESTATUS_ENVIADO);
		if (BeanUtils.isNotNull(movDTO) && BeanUtils.isNotNull(movDTO.getFechaMovimiento())) {
			return BeanUtils.convertirDateStringDiaMesAnio(movDTO.getFechaMovimiento()); 
		}
		return Constantes.EMPTY_STRING;
	}

	public void cargarHistoricoEstatus(long idTramite) {
		lstHistorico = new ArrayList<>();
		lstHistorico = bitMovimientosTramiteDAO.buscarPorIdTramite(idTramite);
	}
	
	public void cerrarDlgHistoricoEstatus() {
		PrimeFaces current = PrimeFaces.current();
		current.executeScript("PF('dlgHistoricoTramite').hide();");
	}

	public String descripcionEstatus(Integer idEstatus) {
		CatEstatusTramiteDTO estatusTramite = catEstatusTramiteDAO.buscarPorIdEstatus(idEstatus);
		if (BeanUtils.isNotNull(estatusTramite)) {
			ProyectoDTO proyectoDTO = formularioApplication.getProyectoDTO();
			return proyectoDTO.isAviso() ? estatusTramite.getDescripcionAviso() : estatusTramite.getDescripcion() ;
		}
		return Constantes.EMPTY_STRING;
	}	  

	/**GETTER´s y SETTER´s**/
	
	/**
	 * @return the tramiteBusqueda
	 */
	public TramiteDTO getTramiteBusqueda() {
		return tramiteBusqueda;
	}

	/**
	 * @param tramiteBusqueda the tramiteBusqueda to set
	 */
	public void setTramiteBusqueda(TramiteDTO tramiteBusqueda) {
		this.tramiteBusqueda = tramiteBusqueda;
	}	

	/**
	 * @return the lstTramites
	 */
	public List<TramiteDTO> getLstTramites() {
		return lstTramites;
	}


	/**
	 * @param lstTramites the lstTramites to set
	 */
	public void setLstTramites(List<TramiteDTO> lstTramites) {
		this.lstTramites = lstTramites;
	}

	/**
	 * @return the lstEstatusTramite
	 */
	public List<CatEstatusTramiteDTO> getLstEstatusTramite() {
		return lstEstatusTramite;
	}

	/**
	 * @param lstEstatusTramite the lstEstatusTramite to set
	 */
	public void setLstEstatusTramite(List<CatEstatusTramiteDTO> lstEstatusTramite) {
		this.lstEstatusTramite = lstEstatusTramite;
	}

	public TramiteDTO getTramiteSeleccionado() {
		return tramiteSeleccionado;
	}

	public void setTramiteSeleccionado(TramiteDTO tramiteSeleccionado) {
		this.tramiteSeleccionado = tramiteSeleccionado;
	}

	public String getMsgErrorFirma() {
		return msgErrorFirma;
	}

	public void setMsgErrorFirma(String msgErrorFirma) {
		this.msgErrorFirma = msgErrorFirma;
	}

	public List<BitMovimientosTramiteDTO> getLstHistorico() {
		return lstHistorico;
	}

	public void setLstHistorico(List<BitMovimientosTramiteDTO> lstHistorico) {
		this.lstHistorico = lstHistorico;
	}	
	
}

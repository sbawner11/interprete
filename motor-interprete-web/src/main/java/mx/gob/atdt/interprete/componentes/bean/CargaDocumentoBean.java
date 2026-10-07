package mx.gob.atdt.interprete.componentes.bean;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipInputStream;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.ComponenteCargaDocumentosDTO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.CrcCargaDocumentosTipoArchivoDTO;
import mx.gob.atdt.interprete.dto.DatosDocumentoJsonDTO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.formularios.bean.RegistrarFormularioBean;
import mx.gob.atdt.interprete.util.WebResources;

@Named
@SessionScoped
public class CargaDocumentoBean implements Serializable {

	private static final long serialVersionUID = -2841319154910904934L;
	private static final Logger LOGGER = LoggerFactory.getLogger(CargaDocumentoBean.class);

	@Inject
	SeccionesProyectoBean seccionesProyectoBean;
	
	@Inject
	private RegistrarFormularioBean registrarFormularioBean;

	private ComponenteCargaDocumentosDTO cargaDocumentoDTO;

	public void inicializar(ComponenteCargaDocumentosDTO cargaDocumentosDTO) {
		cargaDocumentoDTO = cargaDocumentosDTO;
	}

	// TODO Pendiente revisar mejor opción para recuperar listado de rutas para el
	// guardado en BD.
	/**
	 * v1. Método de apoyo para obtener en formato Json las rutas de los archivos del
	 * Componente de Carga de Documentos.
	 * 
	 * v2. Se actualiza metodo para procesar formato DatosDocumentoJsonDTO
	 * 
	 * @param archivos
	 * @author Ramiro Luna Torres
	 * @return
	 */
	public List<SelectItem> obtenerRutasArchivos(List<ComponenteCargaDocumentosDTO> archivos) {
		List<SelectItem> lstOpcion = new ArrayList<SelectItem>();

		List<DatosDocumentoJsonDTO> lstRutas = new ArrayList<>();
		if (!archivos.isEmpty()) {
			for (ComponenteCargaDocumentosDTO archivoTemp : archivos) {
				
				lstRutas.add(new DatosDocumentoJsonDTO(
		                archivoTemp.getRutaDocumento(),
		                archivoTemp.getNombreDocumento()));
			}
			lstOpcion.add(new SelectItem(new Gson().toJson(lstRutas), new Gson().toJson(lstRutas)));
		}
		
		return lstOpcion;
	}

	/**
	 * Método auxiliar para la carga del o los documentos utilizado por el
	 * componente de carga de documento
	 * 
	 * @param event
	 */
	public void subirArchivo(FileUploadEvent event) {
		try {
			if (event.getFile() != null) {
				if (!BeanUtils.isEmpty(registrarFormularioBean.getLstSeccionesDTO())) {
					for (SeccionesFormularioDTO seccion : registrarFormularioBean.getLstSeccionesDTO()) {
						if (!BeanUtils.isEmpty(seccion.getLstSubsecciones())) {
							for (SubSeccionesFormularioDTO subSecciones : seccion.getLstSubsecciones()) {
								if (!BeanUtils.isEmpty(subSecciones.getLstComponentes())) {
									for (ComponenteDTO componente : subSecciones.getLstComponentes()) {
										int indexInicial = event.getComponent().getClientId().lastIndexOf("_") + 1;
										if (componente.getCatTipoComponenteDTO() != null && componente
												.getCatTipoComponenteDTO()
												.getIdTipoComponente() == Constantes.ID_COMPONENTE_CARGA_DOCUMENTOS
												&& componente.getIdComponente() == Long.parseLong(
														event.getComponent().getClientId().substring(indexInicial,
														event.getComponent().getClientId().length()))) {
											
											ComponenteCargaDocumentosDTO cargaDocumento = (ComponenteCargaDocumentosDTO) componente;
											
											//Verificamos si es un archivo zip
											String nombreArchivo = event.getFile().getFileName();
											String extension = FilenameUtils.getExtension(nombreArchivo).toLowerCase(Locale.ROOT);
											boolean esArchivoZip = "zip".equals(extension);
											
											if (esArchivoZip) {
												boolean firmaZipValida;
												try (InputStream inputStreamFirma =event.getFile().getInputStream()) {
											        firmaZipValida = tieneFirmaZip(inputStreamFirma);
											    }
												
												if (!firmaZipValida) {
													enviarMensajeVista("msj_zip_invalido", event.getComponent().getClientId(), FacesMessage.SEVERITY_ERROR);													
													return;
												}
												
												Set<String> extensionesPermitidas = obtenerExtensionesPermitidas(cargaDocumento);

											    boolean contenidoZipValido;
											    try (InputStream inputStreamZip = event.getFile().getInputStream()) {
											        contenidoZipValido = validarTiposArchivoZip(inputStreamZip, extensionesPermitidas, event.getComponent().getClientId());
											    }
											    
											    if (!contenidoZipValido) {
											        return;
											    }
											}
											
											if (!cargaDocumento.isDocumentoUnico()) {
												ComponenteCargaDocumentosDTO documentoDTO = new ComponenteCargaDocumentosDTO(
														cargaDocumento.getIdComponenteCarga());
												
												//guardamos el nombre del archivo
												documentoDTO.setNombreDocumento(event.getFile().getFileName());
												
												//Se copia el documento cargado en el filesystem
												documentoDTO.setRutaDocumento(copiarDocumento(
														FilenameUtils.getExtension(event.getFile().getFileName()),
														event.getFile().getInputStream(),
														(Environment.getPathArchivosTemporales().concat(seccionesProyectoBean.getProyectoDTO().getIdProyecto() + Constantes.SEPARADOR_RUTA))));

												cargaDocumento
														.setArchivos(new ArrayList<ComponenteCargaDocumentosDTO>());
												cargaDocumento.getArchivos().add(documentoDTO);
											} else {
												if (cargaDocumento.getArchivos() == null) {
													cargaDocumento
															.setArchivos(new ArrayList<ComponenteCargaDocumentosDTO>());
												}
												if (cargaDocumento.getArchivos() != null && cargaDocumento.getArchivos()
														.size() < Constantes.MAXIMO_NUMERO_ARCHIVOS) {
													ComponenteCargaDocumentosDTO documentoDTO = new ComponenteCargaDocumentosDTO(
															cargaDocumento.getIdComponenteCarga());
													
													//guardamos el nombre del archivo
													
													documentoDTO.setNombreDocumento(event.getFile().getFileName());
													// Se copia el documento cargado en el filesystem
													documentoDTO.setRutaDocumento(copiarDocumento(
															FilenameUtils.getExtension(event.getFile().getFileName()),
															event.getFile().getInputStream(),
															(Environment.getPathArchivosTemporales().concat(seccionesProyectoBean.getProyectoDTO().getIdProyecto() + Constantes.SEPARADOR_RUTA))));

													cargaDocumento.getArchivos().add(documentoDTO);
												} else {
													enviarMensajeVista("msj_validacion_numero_archivos",
															event.getComponent().getClientId(),
															FacesMessage.SEVERITY_ERROR);
												}
											}
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (IOException e) {
			LOGGER.error("Ocurrió un error al intentar copiar el docuento en el filesystem: ", e);
		}
	}
	
	private boolean validarTiposArchivoZip(InputStream inputStream, Set<String> extensionesPermitidas, String clientId) {
	    try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(inputStream), Charset.forName("CP437"))) {
	        ZipEntry entrada;

	        while ((entrada = zipInputStream.getNextEntry()) != null) {
	            if (entrada.isDirectory()) {
	                zipInputStream.closeEntry();
	                continue;
	            }

	            String nombreArchivoInterno = entrada.getName();
	            String extensionArchivoInterno = FilenameUtils.getExtension(nombreArchivoInterno).toLowerCase(Locale.ROOT);

	            if (extensionArchivoInterno.isEmpty() || !extensionesPermitidas.contains(extensionArchivoInterno)) {
	                //LOGGER.warn("===== Tipo de archivo no permitido: {}", nombreArchivoInterno);
	                enviarMensajeVista("msj_zip_contenido_invalido", clientId, FacesMessage.SEVERITY_ERROR);
	                return false;
	            }

	            zipInputStream.closeEntry();
	        }

	        return true;

	    } catch (ZipException e) {
	        LOGGER.error("El archivo cargado no tiene una estructura ZIP válida.", e);
	        enviarMensajeVista("msj_zip_estructura_invalida", clientId, FacesMessage.SEVERITY_ERROR);
	        return false;
	    } catch (IOException e) {
	        LOGGER.error("Ocurrió un error al revisar los archivos contenidos en el ZIP.", e);
	        enviarMensajeVista("msj_zip_incorrecto", clientId, FacesMessage.SEVERITY_ERROR);
	        return false;
	    }
	}

	private boolean tieneFirmaZip(InputStream inputStream) throws IOException {
	    byte[] firma = new byte[4];
	    int bytesLeidos = inputStream.read(firma);
	    if (bytesLeidos < 4) {
	        return false;
	    }

	    return firma[0] == 0x50
	            && firma[1] == 0x4B
	            && (
	                (firma[2] == 0x03 && firma[3] == 0x04)
	                || (firma[2] == 0x05 && firma[3] == 0x06)
	                || (firma[2] == 0x07 && firma[3] == 0x08)
	            );
	}
	
	public boolean tieneExtensionMayuscula(String extension) {
		// Comprobamos si algun caracter es mayúscula
		for (char c : extension.toCharArray()) {
			if (Character.isUpperCase(c)) {
				return true; // Se encontró al menos una mayúscula
			}
		}
		return false;
	}

	/**
	 * Método auxiliar que eliminá un archivo cargado de la lista de documentos
	 * cargados, utilizado por la opción elimianr del componente carga de documento
	 * 
	 * 19/03/2026
	 * Se agrega validacion no eliminar archivos en la ruta ClienteDocumentos configurado
	 * @author Ramiro Luna
	 * @param archivo
	 */
	public void eliminarArchivoCargado(ComponenteCargaDocumentosDTO archivo,
			ComponenteCargaDocumentosDTO cargaDocumentoDTO) {
		File documento = new File(archivo.getRutaDocumento());
		try {
			cargaDocumentoDTO.getArchivos().remove(archivo);
			if(archivo.getRutaDocumento().contains(Environment.getPathClienteDocumentos())){
				return;
			}
			Files.deleteIfExists(documento.toPath());
		} catch (Exception e) {
			LOGGER.error("Ocurrió un error al querer eliminar el documento: ", e);
		}
	}

	/**
	 * Método privado que se utiliza para dar formato de lectura al tamaño en Bytes
	 * de los archivos
	 * 
	 * @param bytes
	 * @return
	 */
	public String formatoBytes(ComponenteCargaDocumentosDTO documentoDTO) {
		File file = new File(documentoDTO.getRutaDocumento());
		long bytes = file.length();
		long megabytes = Constantes.KILOBYTE * Constantes.KILOBYTE;
		if (bytes >= Constantes.KILOBYTE && bytes < megabytes) {
			return String.format("%.1f", (double) bytes / Constantes.KILOBYTE) + " " + Constantes.STR_KB;
		} else if (bytes >= megabytes && bytes < (megabytes * Constantes.KILOBYTE)) {
			return String.format("%.1f", (double) bytes / megabytes) + " " + Constantes.STR_MB;
		} else {
			return bytes / Constantes.KILOBYTE + " " + Constantes.STR_BYTES;
		}
	}

	/**
	 * Método privado utilizado para enviar un mensaje a la vista
	 * 
	 * @param mensaje
	 * @param idComponente
	 * @param severity
	 */
	private void enviarMensajeVista(String mensaje, String idComponente, Severity severity) {
		PrimeFaces.current().scrollTo(idComponente);
		FacesContext.getCurrentInstance().addMessage(
				FacesContext.getCurrentInstance().getViewRoot().findComponent(idComponente).getClientId(),
				new FacesMessage(severity, null, WebResources.getBundleMsg(mensaje)));
	}

	/**
	 * Método private auxiliar para copiar un documento cargado en el filesystem
	 * 
	 * @param extension
	 * @param documento
	 * @param destinoDoc
	 * @return
	 */
	private String copiarDocumento(String extension, InputStream documento, String destinoDoc) {
		File folder = new File(destinoDoc);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		OutputStream out = null;
		try {
			destinoDoc += UUID.randomUUID().toString() + "." + extension;
			out = new FileOutputStream(new File(destinoDoc));
			int read = Constantes.INT_VALOR_CERO;
			byte[] bytes = new byte[Constantes.TAMAÑO_BUFFER];
			while ((read = documento.read(bytes)) != -1) {
				out.write(bytes, Constantes.INT_VALOR_CERO, read);
			}
			documento.close();
			out.flush();
			out.close();
		} catch (IOException e) {
			LOGGER.error("Problemas al copiar el archivo: ", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch(Exception e) {
					LOGGER.warn("No se pudo cerrar de manera correcta el outputstream de copiarDocumento", e);
				}
			}
		}
		return destinoDoc;
	}

	/**
	 * Método auxiliar que se utiliza por la opción Ver del componente de carga
	 * documento, este método lanza la descarga del documento.
	 * 
	 * @param archivo
	 */
	public void verDocumento(ComponenteCargaDocumentosDTO archivo) {
		File documento = new File(archivo.getRutaDocumento());

	    if (!documento.exists()) {
	        LOGGER.error("El archivo no existe: {}", documento.getAbsolutePath());
	        return;
	    }

	    if (!documento.isFile()) {
	        LOGGER.error("La ruta no corresponde a un archivo: {}", documento.getAbsolutePath());
	        return;
	    }

	    if (!documento.canRead()) {
	        LOGGER.error("No se tienen permisos de lectura sobre: {}", documento.getAbsolutePath());
	        return;
	    }

	    String nombreDocumento = eliminaAcentosTexto(obtenerNombreDocumento(archivo.getNombreDocumento(), archivo.getRutaDocumento()));
	    String contentType = asignarContentTypeDocumento(nombreDocumento);

	    FacesContext fctx = FacesContext.getCurrentInstance();
	    HttpServletResponse response = (HttpServletResponse) fctx.getExternalContext().getResponse();

	    long totalBytesEnviados = 0L;

	    try (InputStream is = new FileInputStream(documento);
	         ServletOutputStream soutput = response.getOutputStream()) {
	        response.reset();
	        response.setContentType(contentType);
	        response.setContentLengthLong(documento.length());

	        response.setHeader("Content-Disposition", "attachment; filename=\"" + nombreDocumento + "\"");

	        byte[] buffer = new byte[Constantes.TAMAÑO_BUFFER];
	        int read;

	        while ((read = is.read(buffer)) != -1) {
	            soutput.write(buffer, 0, read);
	            totalBytesEnviados += read;
	        }

	        soutput.flush();
	        fctx.responseComplete();

	    } catch (Exception e) {
	        LOGGER.error(
	                "Error al descargar documento. "
	                + "Archivo={}, Nombre={}, BytesEnviados={}",
	                documento.getAbsolutePath(),
	                nombreDocumento,
	                totalBytesEnviados, e);
	    }
	}
	
	/*
	 * Limpia el nombre del archivo a una versión sin acentos
	 */
	private static String eliminaAcentosTexto(String nombre) {
	    String normalizado = Normalizer.normalize(nombre, Normalizer.Form.NFD);
	    normalizado = normalizado.replaceAll("\\p{M}", "");
	    return normalizado;
	}

	/**
	 * Método privado auxiliar para poder asignar el content type del documento
	 * cargado
	 * 
	 * @param fileName
	 * @return
	 */
	private String asignarContentTypeDocumento(String fileName) {
		fileName = normalizarExtensionDeArchivo(fileName);
		String contentType = Constantes.EMPTY_STRING;
		if(BeanUtils.isNotEmpty(fileName)) {
			if (fileName.contains(Constantes.EXTENSION_JPG)) {
				contentType = Constantes.CONTENTTYPE_JPG;
			} else if (fileName.contains(Constantes.EXTENSION_JPEG)) {
				contentType = Constantes.CONTENTTYPE_JPEG;
			} else if (fileName.contains(Constantes.EXTENSION_PNG)) {
				contentType = Constantes.CONTENTTYPE_PNG;
			} else if (fileName.contains(Constantes.EXTENSION_PDF)) {
				contentType = Constantes.CONTENTTYPE_PDF;
			} else if (fileName.contains(Constantes.EXTENSION_XLSB) || fileName.contains(Constantes.EXTENSION_XLSX) 
					|| fileName.contains(Constantes.EXTENSION_XLSM)) {
				contentType = Constantes.CONTENTTYPE_XLSX_XLSM_XLSB;
			} else if (fileName.contains(Constantes.EXTENSION_XLS)) {
				contentType = Constantes.CONTENTTYPE_XLS;
			} else if (fileName.contains(Constantes.EXTENSION_MP3)) {
				contentType = Constantes.CONTENTTYPE_MP3;
			} else if (fileName.contains(Constantes.EXTENSION_WMA)) {
				contentType = Constantes.CONTENTTYPE_WMA;
			} else if (fileName.contains(Constantes.EXTENSION_MP4)) {
				contentType = Constantes.CONTENTTYPE_MP4;
			} else if (fileName.contains(Constantes.EXTENSION_AVI)) {
				contentType = Constantes.CONTENTTYPE_AVI;
			}else if (fileName.contains(Constantes.EXTENSION_CSV)) {
				contentType = Constantes.CONTENTTYPE_CSV;
			}else if (fileName.contains(Constantes.EXTENSION_ZIP)) {
				contentType = Constantes.CONTENTTYPE_ZIP;
			}
		}
		return contentType;
	}

	/**
	 * Método auxiliar que permite realizar el calculo del tamaño permitido en la
	 * carga de un documento, se expresa en long que representa el tamaño en bytes.
	 * 
	 * @param idTamanioArchivo
	 * @return
	 */
	public long calcularFileSizeLimit(Integer idTamanioArchivo) {
		long fileSizeLimit = 0l;
		long megabytes = Constantes.KILOBYTE * Constantes.KILOBYTE;
		switch (idTamanioArchivo) {
		case Constantes.ID_TAMANIO_ARCHIVO_1MB:
			fileSizeLimit = megabytes;
			break;
		case Constantes.ID_TAMANIO_ARCHIVO_2MB:
			fileSizeLimit = 2 * megabytes;
			break;
		case Constantes.ID_TAMANIO_ARCHIVO_5MB:
			fileSizeLimit = 5 * megabytes;
			break;
		case Constantes.ID_TAMANIO_ARCHIVO_10MB:
			fileSizeLimit = 10 * megabytes;
			break;
		case Constantes.ID_TAMANIO_ARCHIVO_15MB:
			fileSizeLimit = 15 * megabytes;
			break;
		case Constantes.ID_TAMANIO_ARCHIVO_2OMB:
			fileSizeLimit = 20 * megabytes;
			break;
		default:
			fileSizeLimit = 500 * Constantes.KILOBYTE;
			break;
		}
		return fileSizeLimit;
	}

	/**
	 * Método auxiliar que obtiene el accept para el componente de carga de
	 * documento
	 * 
	 * @param documento
	 * @return
	 */
	public String obtenerAcceptComponenteCargaDocmento(ComponenteCargaDocumentosDTO documento) {
		String accept = "";
		List<CrcCargaDocumentosTipoArchivoDTO> lisTipoArchivosActivos = documento.getCrcCargaDocumentosTipoArchivoDTO()
				  																 .stream()
																				 .filter(CrcCargaDocumentosTipoArchivoDTO::getActivo)
																				 .collect(Collectors.toList());	
		accept = lisTipoArchivosActivos.stream()
									   .map(map ->".".concat(obtenerDescripcionTipoArchivo(map.getCatTipoArchivoDTO().getIdTipoArchivo())))
									   .collect((Collectors.joining(",")));
		return accept;
	}

	/**
	 * Método auxiliar que obtiene el allow types para el componente de carga de
	 * documento
	 * 
	 * @param documento
	 * @return
	 */
	public String obtenerAllowTypesComponenteCargaDocmento(ComponenteCargaDocumentosDTO documento) {
		StringBuilder allowTypes = new StringBuilder("/(\\.|\\/)(");
		
		List<CrcCargaDocumentosTipoArchivoDTO> lisTipoArchivosActivos = documento.getCrcCargaDocumentosTipoArchivoDTO()
																				 .stream()
																				 .filter(CrcCargaDocumentosTipoArchivoDTO::getActivo)
																				 .collect(Collectors.toList());
		allowTypes.append(lisTipoArchivosActivos.stream()
										   .map(map ->obtenerDescripcionTipoArchivo(map.getCatTipoArchivoDTO().getIdTipoArchivo()))
										   .collect((Collectors.joining("|"))));
		return allowTypes.append(")$/i").toString();
	}
	

	private String obtenerDescripcionTipoArchivo(int idTipoarchivo) {
		String descripcionTipoArchivo = null;
		switch (idTipoarchivo) {
		case Constantes.ID_TIPO_ARCHIVO_JPG:
			descripcionTipoArchivo = Constantes.STR_JPG;
			break;
		case Constantes.ID_TIPO_ARCHIVO_PNG:
			descripcionTipoArchivo = Constantes.STR_PNG;
			break;
		case Constantes.ID_TIPO_ARCHIVO_PDF:
			descripcionTipoArchivo = Constantes.STR_PDF;
			break;
		case Constantes.ID_TIPO_ARCHIVO_XLSX:
			descripcionTipoArchivo = Constantes.STR_XLSX;
			break;
		case Constantes.ID_TIPO_ARCHIVO_XLSM:
			descripcionTipoArchivo = Constantes.STR_XLSM;
			break;
		case Constantes.ID_TIPO_ARCHIVO_XLSB:
			descripcionTipoArchivo = Constantes.STR_XLSB;
			break;
		case Constantes.ID_TIPO_ARCHIVO_XLS:
			descripcionTipoArchivo = Constantes.STR_XLS;
			break;
		case Constantes.ID_TIPO_ARCHIVO_MP3:
			descripcionTipoArchivo = Constantes.STR_MP3;
			break;
		case Constantes.ID_TIPO_ARCHIVO_WMA:
			descripcionTipoArchivo = Constantes.STR_WMA;
			break;
		case Constantes.ID_TIPO_ARCHIVO_MP4:
			descripcionTipoArchivo = Constantes.STR_MP4;
			break;
		case Constantes.ID_TIPO_ARCHIVO_AVI:
			descripcionTipoArchivo = Constantes.STR_AVI;
			break;
		case Constantes.ID_TIPO_ARCHIVO_CSV:
			descripcionTipoArchivo = Constantes.STR_CSV;
			break;
		case Constantes.ID_TIPO_ARCHIVO_ZIP:
			descripcionTipoArchivo = Constantes.STR_ZIP;
			break;
		default:
			descripcionTipoArchivo = Constantes.EMPTY_STRING;
			break;
		}
		return descripcionTipoArchivo;
	}	
	
	private Set<String> obtenerExtensionesPermitidas(ComponenteCargaDocumentosDTO documento) {
		if (documento == null || BeanUtils.isEmpty(documento.getCrcCargaDocumentosTipoArchivoDTO())) {
	        return Collections.emptySet();
	    }

	    return documento.getCrcCargaDocumentosTipoArchivoDTO()
	            .stream()
	            .map(tipo -> obtenerDescripcionTipoArchivo(
	                    tipo.getCatTipoArchivoDTO().getIdTipoArchivo()))
	            .filter(descripcion -> !"zip".equalsIgnoreCase(descripcion))
	            .map(String::toLowerCase)
	            .collect(Collectors.toSet());
	}	

	/**
	 * Método auxiliar que genera el texto informativo para el componente de carga
	 * de documento
	 * 
	 * @param documento
	 * @return
	 */
	public String informativoDocumentoCargar(ComponenteCargaDocumentosDTO documento) {
		StringBuilder informativo = new StringBuilder(WebResources.getBundleMsg("lbl_informativo_part1"));
		if (!documento.isDocumentoUnico())
			informativo.append(" ").append(WebResources.getBundleMsg("lbl_informativo_part2_1")).append(" ");
		else
			informativo.append(" ").append(WebResources.getBundleMsg("lbl_informativo_part2_2")).append(" ");
		
		
		//Bloque para obtener lista de tipos de archivos permitidos que se encuentran activos
		List<CrcCargaDocumentosTipoArchivoDTO> lisTipoArchivosActivos = documento.getCrcCargaDocumentosTipoArchivoDTO()
																				 .stream()
																				 .filter(CrcCargaDocumentosTipoArchivoDTO::getActivo)
																				 .collect(Collectors.toList());
		
		//Bloque que se utiliza para armar el copy de los tipos de archivos permitidos para la carga de documentos
		informativo.append(lisTipoArchivosActivos.stream()
											.map(map -> obtenerDescripcionTipoArchivo(map.getCatTipoArchivoDTO().getIdTipoArchivo()).toUpperCase())
											.collect(Collectors.joining(", ", "", ""))
											.replaceAll(", (?!.*, )", " o "));
		if (!documento.isDocumentoUnico())
			informativo.append(" ").append(WebResources.getBundleMsg("lbl_informativo_part3_1"));
		else
			informativo.append(" ").append(WebResources.getBundleMsg("lbl_informativo_part3_2"));
	
		
		switch (documento.getCatTamanioArchivosDTO().getIdTamanioArchivo()) {
		case Constantes.ID_TAMANIO_ARCHIVO_1MB:
			informativo.append(" 1 MB");
			break;
		case Constantes.ID_TAMANIO_ARCHIVO_2MB:
			informativo.append(" 2 MB");
			break;
		case Constantes.ID_TAMANIO_ARCHIVO_5MB:
			informativo.append(" 5 MB");
			break;
		case Constantes.ID_TAMANIO_ARCHIVO_10MB:
			informativo.append(" 10 MB");
			break;
		case Constantes.ID_TAMANIO_ARCHIVO_15MB:
			informativo.append(" 15 MB");
			break;
		case Constantes.ID_TAMANIO_ARCHIVO_2OMB:
			informativo.append(" 20 MB");
			break;
		default:
			informativo.append(" 500 KB");
			break;
		}
		return informativo.toString();
	}
	
	/**
	 * Metodo auxiliar para recuperar el nombre del documento
	 * en la variable nombreDocumento o rutaDocumento
	 * 
	 * @param nombreDocumento nombre documento
	 * @param rutaDocumento obtener nombre de la ruta del documento
	 * @author Ramiro Luna Torres
	 * @return nombre del documento 
	 */
	private String obtenerNombreDocumento(String nombreDocumento, String rutaDocumento) {
		return BeanUtils.isNotEmpty(nombreDocumento) ? nombreDocumento: FilenameUtils.getName(rutaDocumento);
	}

	public String obtenerFileName(ComponenteCargaDocumentosDTO cargaDocumentoDTO) {
		if (cargaDocumentoDTO != null && cargaDocumentoDTO.getRutaDocumento() != null) {
			return obtenerNombreDocumento(cargaDocumentoDTO.getNombreDocumento(), cargaDocumentoDTO.getRutaDocumento());
		} else {
			return Constantes.EMPTY_STRING;
		}
	}
	
	private String normalizarExtensionDeArchivo(final String nombreDocumento) {
		String rutaDocumento = nombreDocumento;
		String nuevaExtension = "." + FilenameUtils.getExtension(rutaDocumento).toLowerCase();
		if (rutaDocumento == null || rutaDocumento.isEmpty()) {
			return rutaDocumento;
		}

		if (!nuevaExtension.startsWith(".")) {
			nuevaExtension = "." + nuevaExtension;
		}
		int lastIndex = rutaDocumento.lastIndexOf('.');
		if (lastIndex > 0) {
			return rutaDocumento.substring(0, lastIndex) + nuevaExtension;
		}
		return rutaDocumento + nuevaExtension;
	}

	public String obtenerImagenArchivoAdjunto(ComponenteCargaDocumentosDTO cargaDocumentoDTO) {
		String fileName = normalizarExtensionDeArchivo(cargaDocumentoDTO.getRutaDocumento());
		String nombreImagen = Constantes.EMPTY_STRING;
		if(BeanUtils.isNotEmpty(fileName)) {
			if (fileName.contains(Constantes.EXTENSION_JPG)) {
				nombreImagen = Constantes.IMAGEN_JPG;
			} else if (fileName.contains(Constantes.EXTENSION_JPEG)) {
				nombreImagen = Constantes.IMAGEN_JPEG;
			} else if (fileName.contains(Constantes.EXTENSION_PNG)) {
				nombreImagen = Constantes.IMAGEN_PNG;
			} else if (fileName.contains(Constantes.EXTENSION_PDF)) {
				nombreImagen = Constantes.IMAGEN_PDF;
			} else if (fileName.contains(Constantes.EXTENSION_XLSB) || fileName.contains(Constantes.EXTENSION_XLSX) 
					|| fileName.contains(Constantes.EXTENSION_XLSM) || fileName.contains(Constantes.EXTENSION_XLS)) {
				nombreImagen = Constantes.IMAGEN_XLSM;
			} else if (fileName.contains(Constantes.EXTENSION_MP3)) {
				nombreImagen = Constantes.IMAGEN_MP3;
			} else if (fileName.contains(Constantes.EXTENSION_WMA)) {
				nombreImagen = Constantes.IMAGEN_WMA;
			} else if (fileName.contains(Constantes.EXTENSION_MP4)) {
				nombreImagen = Constantes.IMAGEN_MP4;
			} else if (fileName.contains(Constantes.EXTENSION_AVI)) {
				nombreImagen = Constantes.IMAGEN_AVI;
			} else if (fileName.contains(Constantes.EXTENSION_CSV)) {
				nombreImagen = Constantes.IMAGEN_CSV;
			} else if (fileName.contains(Constantes.EXTENSION_ZIP)) {
				nombreImagen = Constantes.IMAGEN_ZIP;
			}
		}
		
		return nombreImagen;
	}
	/**
	 * v1. Método de apoyo para obtener el valor convertido en Lista de Elementos a
	 * partir de una cadena Json
	 * 
	 * v2. Se ajusta metodo para procesar respuestas en dos formatos
	 * 		1.
	 * 		2. 
	 * @param strJsonOpciones
	 * @author Ramiro Luna Torres
	 * @return
	 */
	public List<ComponenteCargaDocumentosDTO> convertirPahtsDocumentos(String strJsonPathsDocumentos) {
		Gson gson = new Gson();
		java.lang.reflect.Type tipoLista;
		List<ComponenteCargaDocumentosDTO> lstDocumentos = null;
		//Validamos que la cadena json tenga la palabra para identificar el nuevo formateo de json
		Predicate<String> prNuevoFormato = p->p.contains("nombre");
		
		if(prNuevoFormato.test(strJsonPathsDocumentos)) {
			tipoLista = new TypeToken<ArrayList<DatosDocumentoJsonDTO>>() {}.getType();
			List<DatosDocumentoJsonDTO> lstPathsDocumentos = gson.fromJson(strJsonPathsDocumentos, tipoLista);
			if (!BeanUtils.isEmpty(lstPathsDocumentos)) {
				lstDocumentos = new ArrayList<>();
				
				for (DatosDocumentoJsonDTO datosDocumento : lstPathsDocumentos) {
					ComponenteCargaDocumentosDTO documentoTmp = new ComponenteCargaDocumentosDTO();
					documentoTmp.setRutaDocumento(datosDocumento.getRuta());
					documentoTmp.setNombreDocumento(datosDocumento.getNombre());
					lstDocumentos.add(documentoTmp);
				}
			}
		}else {
			tipoLista = new TypeToken<ArrayList<String>>() {}.getType();
			List<String> lstPathsDocumentos = gson.fromJson(strJsonPathsDocumentos, tipoLista);
			if (!BeanUtils.isEmpty(lstPathsDocumentos)) {
				lstDocumentos = new ArrayList<ComponenteCargaDocumentosDTO>();
				for (String string : lstPathsDocumentos) {
					ComponenteCargaDocumentosDTO documentoTmp = new ComponenteCargaDocumentosDTO();
					documentoTmp.setRutaDocumento(string);
					lstDocumentos.add(documentoTmp);
				}
			}
		}	
		return lstDocumentos;
	}

	public ComponenteCargaDocumentosDTO getCargaDocumentoDTO() {
		return cargaDocumentoDTO;
	}

	public void setCargaDocumentoDTO(ComponenteCargaDocumentosDTO cargaDocumentoDTO) {
		this.cargaDocumentoDTO = cargaDocumentoDTO;
	}
	
	

//	public List<ComponenteCargaDocumentosDTO> getArchivos() {
//		return archivos;
//	}
//
//	public void setArchivos(List<ComponenteCargaDocumentosDTO> archivos) {
//		this.archivos = archivos;
//	}

}

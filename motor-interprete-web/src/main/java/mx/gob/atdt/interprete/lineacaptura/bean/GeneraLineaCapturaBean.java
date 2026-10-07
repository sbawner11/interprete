package mx.gob.atdt.interprete.lineacaptura.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;
import org.codehaus.jettison.json.JSONException;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.CatEstatusLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.CatEstatusSolicitudDTO;
import mx.gob.atdt.interprete.dto.DetConceptosTramiteDTO;
import mx.gob.atdt.interprete.dto.DetLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.DetTramitesLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.DetTransaccionConceptoDTO;
import mx.gob.atdt.interprete.dto.LineaCapturaDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.dto.sat.dto.AgrupadorDTO;
import mx.gob.atdt.interprete.dto.sat.dto.ConceptoDTO;
import mx.gob.atdt.interprete.dto.sat.dto.DatosGeneralesDTO;
import mx.gob.atdt.interprete.dto.sat.dto.DatosIcepDTO;
import mx.gob.atdt.interprete.dto.sat.dto.DatosLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.sat.dto.DetalleConceptoDTO;
import mx.gob.atdt.interprete.dto.sat.dto.DetalleTramiteDTO;
import mx.gob.atdt.interprete.dto.sat.dto.DetalleTransaccionPDTO;
import mx.gob.atdt.interprete.dto.sat.dto.RequestGenerarLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.sat.dto.RespuestaGeneracionLCDTO;
import mx.gob.atdt.interprete.dto.sat.dto.RespuestaLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.sat.dto.SolicitudLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.sat.dto.TransaccionPDTO;
import mx.gob.atdt.interprete.exception.ServiciosException;
import mx.gob.atdt.interprete.facade.LineaCapturaFacade;
import mx.gob.atdt.interprete.lineacaptura.client.GeneraLineaCapturaClient;
import mx.gob.atdt.interprete.tramites.bean.BandejaTramitesBean;
import mx.gob.atdt.interprete.util.WebResources;

@Named("generaLineaCapturaBean")
@SessionScoped
public class GeneraLineaCapturaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 401909589136750271L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GeneraLineaCapturaBean.class);
	
	private static final SimpleDateFormat FORMATO_FECHA_LINEA_CAPTURA_DDMMYYYYHHMM = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static final SimpleDateFormat FORMATO_FECHA_DDMMYYYY= new SimpleDateFormat("dd/MM/yyyy");
	
	
	private ObjectMapper objectMapper;
	
	@Inject
	private LineaCapturaFacade lineaCapturaFacade;
	
	@Inject
	private SeccionesProyectoBean seccionesProyectoBean;
	
	@Inject
	private BandejaTramitesBean bandejaTramiteBean;
	
	private GeneraLineaCapturaClient generaLineaCapturaClient;
	
	private boolean deshabilitarBotonFinalizarTramite;
	private boolean mostrarBotonGeneraLineaCaptura;
	private StreamedContent filePdfLC;
	
	public GeneraLineaCapturaBean() {
		super();
		this.generaLineaCapturaClient = new GeneraLineaCapturaClient();
		this.deshabilitarBotonFinalizarTramite = false;
		this.mostrarBotonGeneraLineaCaptura = false;
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}
	
	public void inicializarDatosParaLineaDeCaptura(final TramiteDTO tramiteSeleccionado, 
			final ProyectoDTO proyecto, final boolean esUltimaSeccion) {
		boolean permitirGeneracionLineaDeCaptura = permitirGeneracionLineaDeCaptura(
				tramiteSeleccionado, proyecto, esUltimaSeccion);
		if(permitirGeneracionLineaDeCaptura) {
			setMostrarBotonGeneraLineaCaptura(true);
			setDeshabilitarBotonFinalizarTramite(true);
			if(Objects.isNull(tramiteSeleccionado.getIdTramite())) {
				setDeshabilitarBotonFinalizarTramite(true);
			} else {
				setDeshabilitarBotonFinalizarTramite(
						!this.buscarLineaCapturaVigente(tramiteSeleccionado.getIdTramite()).isPresent());
			}
			PrimeFaces.current().ajax().update(":frmFormulario");
			return;
		}
		setMostrarBotonGeneraLineaCaptura(false);
		setDeshabilitarBotonFinalizarTramite(false);
		
	}
	
	public boolean permitirGeneracionLineaDeCaptura(final TramiteDTO tramiteSeleccionado, final ProyectoDTO proyecto, 
			final  boolean esUltimaSeccion) {
		if(esUltimaSeccion) {
			boolean esSeccionPagoActiva = Optional.ofNullable(proyecto).map(ProyectoDTO::isHabilitaPagoLinea).orElse(false);
			boolean esDetLineaCapturaCompletaYActiva = this.esDetLineaCapturaCompletaYActiva(proyecto.getIdProyecto()); 
			boolean esTramiteConLineaCaptura = this.esTramiteConLineaCaptura(tramiteSeleccionado);
			return esDetLineaCapturaCompletaYActiva && esTramiteConLineaCaptura && esSeccionPagoActiva;
		} 
		return false;
	}
	
	public boolean esTramiteConLineaCaptura (final TramiteDTO tramiteSeleccionado) {
		return Optional.ofNullable(tramiteSeleccionado).map(linea -> {
			return (Constantes.ID_ESTATUS_PENDIENTE_PAGO == linea.getCatEstatusTramiteDTO().getIdEstatusTramite() 
					|| Constantes.ID_ESTATUS_EN_CAPTURA == linea.getCatEstatusTramiteDTO().getIdEstatusTramite());
		}).orElse(false);
	}
	
	public boolean esDetLineaCapturaCompletaYActiva(final long idProyecto) {
		return this.lineaCapturaFacade.buscarDetLineaCapturaPorIdProyecto(idProyecto)
				.map(detLineaCaptura -> detLineaCaptura.isCompleto() && detLineaCaptura.isActivo())
				.orElse(false);
	}
	
	public String generarLineaCapturaDesdeFormularioTramite(final TramiteDTO tramiteSeleccionado, 
			final ProyectoDTO proyecto) throws ServiciosException {
		try {
			this.generarLineaDeCaptura(tramiteSeleccionado, proyecto);
			if(!this.deshabilitarBotonFinalizarTramite) {
				WebResources.successMessage("msj_linea_captura_descargada", true);
			}
			
		}catch(ServiciosException se) {
			throw se;
		}
		return null;
	}

	public void generarLineaDeCaptura(final TramiteDTO tramiteSeleccionado, 
			final ProyectoDTO proyecto) throws ServiciosException {
		long idTramite = tramiteSeleccionado.getIdTramite();
		LOGGER.info("Generando linea de captura para el proyecto: {}", proyecto.getIdProyecto());
		try {
			this.filePdfLC = null;
			Optional<LineaCapturaDTO> lineaCapturaOpt = this.buscarLineaCapturaVigente(idTramite);
			
			if(lineaCapturaOpt.isPresent()) {
				setDeshabilitarBotonFinalizarTramite(false);
			} else {
				lineaCapturaOpt = this.generarLineaDeCapturaDesdeSatYGuardar(tramiteSeleccionado, proyecto);
				if(!lineaCapturaOpt.isPresent()) {
					return;
				}
				this.setDeshabilitarBotonFinalizarTramite(false);
			}
			tramiteSeleccionado.setLineaCapturaDTO(lineaCapturaOpt.get());
			this.prepararDescargaLC(tramiteSeleccionado);
		} catch (URISyntaxException e) {
			LOGGER.error("Error: {}", e);
			WebResources.errorMessage("msj_ce_lc_error_url", false);
			this.filePdfLC = null;
			throw new ServiciosException(e.getMessage());
		} catch (ServiciosException e) {
			LOGGER.error("Error: {}", e);
			WebResources.errorMessage("msj_ce_lc_error_conexion", false);
			this.filePdfLC = null;
			throw new ServiciosException(e.getMessage());
		} catch (Exception e) {
			LOGGER.error("", e);
			 WebResources.errorMessage("msj_ce_lc_error_generar_linea_captura", false);
			 this.filePdfLC = null;
			 throw new ServiciosException(e.getMessage());
		}
	}
	
	public Optional<LineaCapturaDTO> generarLineaDeCapturaDesdeSatYGuardar(final TramiteDTO tramiteSeleccionado, 
			final ProyectoDTO proyecto) throws ConnectException, URISyntaxException, JSONException, ServiciosException, ParseException{
		long idTramite = tramiteSeleccionado.getIdTramite();
		RequestGenerarLineaCapturaDTO solicitudLineaCapturaDTO = 
				this.crearRequestLineaDeCaptura(tramiteSeleccionado, proyecto);
		RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> respuesta = 
				enviarGeneracionDeLCaSAT(solicitudLineaCapturaDTO, tramiteSeleccionado);
		if(Objects.isNull(respuesta)) {
			return Optional.empty();
		}
		String ligaDocumentoLC = this.extraerLigaDocumentoLC(respuesta);
		this.guardarLineadeCaptura(respuesta, idTramite, ligaDocumentoLC);
		return this.buscarLineaCapturaVigente(idTramite);
	}

	private RequestGenerarLineaCapturaDTO crearRequestLineaDeCaptura(
			final TramiteDTO tramiteSeleccionado, final ProyectoDTO proyecto) {
		List<SolicitudLineaCapturaDTO> solicitudLC = this.lineaCapturaFacade
				.buscarSolicitudMasRecientePorTramite(tramiteSeleccionado.getIdTramite(), 1);
		if(!solicitudLC.isEmpty()) {
			try {
				if(solicitudLC.get(0).getCatEstatusSolicitud().getIdEstatusSolicitud() == Constantes.ID_ESTATUS_ERROR_CONEXION_A_SERVICIO) {
					LOGGER.info("Reintentando generacion LC con request con error de conexion: {}", solicitudLC.get(0).getRequestServicioLc());
					return this.objectMapper.readValue(solicitudLC.get(0).getRequestServicioLc(), RequestGenerarLineaCapturaDTO.class);
				}
				return this.construirSolicitudLineaDeCaptura(tramiteSeleccionado, proyecto, tramiteSeleccionado.getUsuario());
			} catch (JsonProcessingException e) {
				LOGGER.error("Error al convertir request de línea de captura: ", e);
				throw new RuntimeException("Error al convertir request de línea de captura: ", e);
			}
		} else {
			return construirSolicitudLineaDeCaptura(tramiteSeleccionado, proyecto, tramiteSeleccionado.getUsuario());
		}
	}
	
	public void descargarPDFLineaDeCaptura(final LineaCapturaDTO lineaCaptura) throws IOException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
        
        InputStream inputStream = obtenerInputStreamDelPDF();
        String nombreArchivo = "linea_captura.pdf";

        if (inputStream != null) {
            try {
                // 1. Configurar la respuesta
            	response.reset();
				response.setHeader("Content-Type", "application/pdf");
				response.setHeader("Content-Disposition", "attachment;filename=" + nombreArchivo);

                // 2. Escribir el InputStream al OutputStream de la respuesta
                OutputStream outputStream = response.getOutputStream();
                byte[] buffer = new byte[1024];
                int longitud;
                while ((longitud = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, longitud);
                }
                
                outputStream.flush();
                inputStream.close();
                
                // 3. Marcar la respuesta como completada
                facesContext.responseComplete();
                WebResources.successMessage("msj_linea_captura_descargada", false);
            } catch (IOException e) {
                LOGGER.error("Error generando PDF de linea de captura: {}", e);
                throw e;
            }
        }
    }

    // Método para cargar el PDF
    private InputStream obtenerInputStreamDelPDF() {
    	String pathDocument = "/resources/docs/ejemplo_linea_captura.pdf";
    	ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    	return servletContext.getResourceAsStream(pathDocument);
    }
	
	private RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> enviarGeneracionDeLCaSAT(
	        RequestGenerarLineaCapturaDTO solicitudLineaCapturaDTO, final TramiteDTO tramiteSeleccionado)
	        throws ConnectException, URISyntaxException, JSONException, ServiciosException {

	    final int maxIntentos = 5;
	    for (int intento = 1; intento <= maxIntentos; intento++) {
	    	RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> respuesta = null;
	    	try {
		        LOGGER.info("Intento {} de generación de línea de captura desde SAT", intento);
		        respuesta =
		                generaLineaCapturaClient.generaLineaCaptura(
		                        seccionesProyectoBean.getSecurityDomainLineasCapturaDTO().getUrlSistema(),
		                        solicitudLineaCapturaDTO);
		        
		        guardarPeticionEnSolicitud(respuesta, solicitudLineaCapturaDTO, tramiteSeleccionado, 
		        		obtenerIdStatusPeticion(respuesta));
	    	} catch (ConnectException ex) {
	    		LOGGER.error("Error al realizar la conexión con el servicio para"
	    			+ " generacion de linea de captura: ", ex);
	    		guardarPeticionConErrorDeTimeout(respuesta, solicitudLineaCapturaDTO, tramiteSeleccionado, ex);
	    		return null;
			}
	        if (sinErrores(respuesta)) {
	            LOGGER.info("Generación exitosa en intento {}", intento);
	            return respuesta;
	        }
	        if (!reintentarEnvio(respuesta)) {
	            throw new ServiciosException("Error funcional, no se permite reintento.");
	        }
	        // Generar nuevo consecutivo solo si se va a reintentar
	        String nuevoConsecutivo = obtenerSecuenciaSolicitud(
	                solicitudLineaCapturaDTO.getDatosGenerales().getSolicitud());
	        solicitudLineaCapturaDTO.getDatosGenerales().setSolicitud(nuevoConsecutivo);
	        LOGGER.warn("Reintentando con nuevo consecutivo: {}", nuevoConsecutivo);
	    }

	    throw new ServiciosException(
	            String.format("No se pudo generar la línea de captura después de %d intentos.", maxIntentos));
	}
	
	private String guardarPeticionConErrorDeTimeout(RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> respuesta, 
			RequestGenerarLineaCapturaDTO solicitudLineaCapturaDTO, final TramiteDTO tramiteSeleccionado, Exception ex) {
		LOGGER.warn("Guardando peticion: {}", solicitudLineaCapturaDTO.getDatosGenerales().getSolicitud());
		respuesta = new RespuestaLineaCapturaDTO<>();
		respuesta.setRespuestaString(ex.getMessage());
		guardarPeticionEnSolicitud(respuesta, solicitudLineaCapturaDTO, tramiteSeleccionado, 
        		Constantes.ID_ESTATUS_ERROR_CONEXION_A_SERVICIO);
		WebResources.errorMessage("msj_ce_lc_error_conexion", false);
		return null;
	}
	
	public void guardarPeticionEnSolicitud(final RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> respuesta,
			RequestGenerarLineaCapturaDTO request, final TramiteDTO tramiteSeleccionado, final int idSolicitud) {
		try {
			SolicitudLineaCapturaDTO solicitud = new SolicitudLineaCapturaDTO();
			solicitud.setCatEstatusSolicitud(new CatEstatusSolicitudDTO(idSolicitud));
			solicitud.setFechaCreacion(new Date());
			solicitud.setRequestServicioLc(objectMapper.writeValueAsString(request));
			solicitud.setRespuestaServicioLc(respuesta.getRespuestaString());
			solicitud.setSolicitudLineaCaptura(request.getDatosGenerales().getSolicitud());
			solicitud.setTramite(tramiteSeleccionado);
			this.lineaCapturaFacade.guardarSolicitudLineaCaptura(solicitud);
		} catch (Exception e) {
			LOGGER.error("Error guardando intento de solicitud de línea de captura: ", e);
		}
	}
	private boolean sinErrores(RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> respuesta) {
	    return Objects.isNull(respuesta.getRespuestaDTO().getErrores())
	            || respuesta.getRespuestaDTO().getErrores().isEmpty();
	}
	
	private boolean reintentarEnvio(final RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> respuesta) {

		return respuesta.getRespuestaDTO().getErrores().stream()
			.anyMatch(error -> {
				if( error.getDescripcion().contains(Constantes.DESCRIPCION_SOLICITUD_PROCESADA_ANTERIORMENTE)) {
					return true;
				}
				return false;
			});
	}
	
	private int obtenerIdStatusPeticion(final RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> respuesta) {
		
		if(sinErrores(respuesta)) {
			return Constantes.ID_ESTATUS_SOLICITUD_EXITOSA;
		} else if (reintentarEnvio(respuesta)) {
			return Constantes.ID_ESTATUS_SOLICITUD_PROCESADA_ANTERIORMENTE;
		} else {
			return Constantes.ID_ESTATUS_ERROR_SOLICITUD;
		}
	}

	public void guardarLineadeCaptura(final RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> respuesta, 
			final long idTramite, String ligaDocumentoLC) throws ParseException  {
		
		RespuestaGeneracionLCDTO respuestaDTO = respuesta.getRespuestaDTO();
		
		LineaCapturaDTO lineaCaptura = new LineaCapturaDTO();
		lineaCaptura.setCatEstatusLineaCaptura(new CatEstatusLineaCapturaDTO(1));
		lineaCaptura.setFechaCreacion(new Date());
		lineaCaptura.setFechaPagoLc(null);
		lineaCaptura.setFechaVigencia(FORMATO_FECHA_DDMMYYYY.parse(
				respuestaDTO.getAcuseDTO().getDatosLineaCaptura().getFechaVigencia()));
		lineaCaptura.setIdTramite(idTramite);
		lineaCaptura.setLineaCaptura(
				respuestaDTO.getAcuseDTO().getDatosLineaCaptura().getLineaCaptura());
		lineaCaptura.setMonto(
				respuestaDTO.getAcuseDTO().getDatosLineaCaptura().getImporte());
		lineaCaptura.setRespuestaServicioEstatus(HttpStatus.SC_OK + "");
		lineaCaptura.setRespuestaServicioLc(respuesta.getRespuestaString());
		lineaCaptura.setRutaDocumentoLineaCaptura(ligaDocumentoLC);
		lineaCaptura.setSolicitudLineaCaptura(respuesta.getRespuestaDTO().getRespuestaDatosGenerales().getSolicitud());
		this.lineaCapturaFacade.guardarLineaCaptura(lineaCaptura);
	}
	
	public RequestGenerarLineaCapturaDTO construirSolicitudLineaDeCaptura(final TramiteDTO tramiteSeleccionado, 
			final ProyectoDTO proyecto, final UsuarioDTO usuarioTramite) {
		RequestGenerarLineaCapturaDTO solicitudLineaCaptura = new RequestGenerarLineaCapturaDTO();
		solicitudLineaCaptura.setTramites(construirTramiteSolicitud(tramiteSeleccionado, proyecto, usuarioTramite));
		
		UsuarioDTO usuario = this.lineaCapturaFacade
				.buscarUsuarioPorId(tramiteSeleccionado.getUsuario().getIdUsuarioLlaveCdmx());
		solicitudLineaCaptura.setDatosGenerales(construirDatosGenerales(
				tramiteSeleccionado, proyecto, usuario));
		
		try {
			String jsonString = objectMapper.writeValueAsString(solicitudLineaCaptura);
			System.out.println("Solicitud linea captura: " + jsonString);
		} catch (JsonProcessingException e) {
			LOGGER.info("Error al convertir solicitud de línea de captura a JSON: ", e.getMessage());
		}
		return solicitudLineaCaptura;
	}
	
	public DatosGeneralesDTO construirDatosGenerales(final TramiteDTO tramiteSeleccionado, 
			final ProyectoDTO proyecto, final UsuarioDTO usuarioTramite) {
		Optional<DetLineaCapturaDTO>  detLineaCaptura = 
				this.lineaCapturaFacade.buscarDetLineaCapturaPorIdProyecto(proyecto.getIdProyecto());
		
		return detLineaCaptura.map(valueDetLineaCaptura -> {
			DatosGeneralesDTO datosGenerales = new DatosGeneralesDTO();
			datosGenerales.setCveDependencia(rellenarCadena(valueDetLineaCaptura
					.getDependenciaPagoDTO().getIdDependenciaPago() + "", 3));
			datosGenerales.setUnidadAdministrativa(rellenarCadena(valueDetLineaCaptura
					.getUnidadAdministrativaPagoDTO().getIdUnidadAdministrativaPago() + "", 3));
			datosGenerales.setTipoPersona(valueDetLineaCaptura.getTipoPersonaDTO().getClave());
			datosGenerales.setApellidoMaterno(usuarioTramite.getSegundoApellido());
			datosGenerales.setApellidoPaterno(usuarioTramite.getPrimerApellido());
			datosGenerales.setNombre(usuarioTramite.getNombre());
			datosGenerales.setCurp(usuarioTramite.getCurp());
			
			
			DatosLineaCapturaDTO datosLineaCaptura = 
					Optional.ofNullable(this.lineaCapturaFacade.buscarDetTramitesLineaCapturaPorIdDetLineaCaptura(
					valueDetLineaCaptura.getIdDetalleLineaCaptura()).get(0))
						.map(value ->{
							DatosLineaCapturaDTO datosLC = new DatosLineaCapturaDTO();
							datosLC.setFechaSolicitud(FORMATO_FECHA_LINEA_CAPTURA_DDMMYYYYHHMM.format(new Date()));
							datosLC.setImporte((int)value.getImporte());
							datosLC.setFechaVigencia(this.obtenerVigenciaDeLineaDeCaptura(valueDetLineaCaptura));
							return datosLC;
						})
						.orElse(new DatosLineaCapturaDTO());
			
			datosGenerales.setDatosLineaCapturaDTO(datosLineaCaptura);
			datosGenerales.setSolicitud(construirIdSolicitud(datosGenerales, tramiteSeleccionado.getIdTramite()));
			return datosGenerales;
		}).orElseThrow(() -> new RuntimeException("det_linea_captura no configurado"));
	}
	
	private String obtenerVigenciaDeLineaDeCaptura(final DetLineaCapturaDTO detLineaCapturaDTO) {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		switch(detLineaCapturaDTO.getTipoVigenciaDTO().getClave()) {
			case Constantes.CLAVE_VIGENCIA_POR_DIA:
				return formato.format(LocalDate.now().plusDays(detLineaCapturaDTO.getVigencia()));
			case Constantes.CLAVE_VIGENCIA_POR_SEMANA:
				return formato.format(LocalDate.now().plusWeeks(detLineaCapturaDTO.getVigencia()));
			case Constantes.CLAVE_VIGENCIA_POR_MES:
				return formato.format(LocalDate.now().plusMonths(detLineaCapturaDTO.getVigencia()));
			case Constantes.CLAVE_VIGENCIA_POR_ANIO:
				return formato.format(LocalDate.now().plusYears(detLineaCapturaDTO.getVigencia()));
			default:
				return formato.format(LocalDate.now().plusDays(detLineaCapturaDTO.getVigencia()));
		}
	}
	
	private String construirIdSolicitud(final DatosGeneralesDTO datosGenerales, final long idTramite) {
		StringBuilder complemento = new StringBuilder(datosGenerales.getCveDependencia());
		complemento.append(datosGenerales.getUnidadAdministrativa());
		
		List<LineaCapturaDTO> lineasCaptura = this.lineaCapturaFacade
				.buscarLineasDeCapturaMasReciente(1);//Recuperar solo la linea de captura mas reciente para obtener el consecutivo, en caso de existir
		if(lineasCaptura.isEmpty()) {
			String anioEnDosDigitos = Year.now().format(DateTimeFormatter.ofPattern("uu"));
			long nuevoConsecutivo = this.lineaCapturaFacade.obtenerSiguienteConsecutivoLineaCaptura();
			return complemento.toString() + anioEnDosDigitos + rellenarCadena(String.valueOf(nuevoConsecutivo), 
					Constantes.LONGITUD_CONSECUTIVO_LINEA_CAPTURA);
		}
		String consecutivoLineaCaptura = lineasCaptura.get(0).getSolicitudLineaCaptura();

		return obtenerSecuenciaSolicitud(consecutivoLineaCaptura);
	}
	
	private String obtenerSecuenciaSolicitud(final String consecutivoLineaCaptura) {
		String anioEnDosDigitos = Year.now().format(DateTimeFormatter.ofPattern("uu"));
		String complemento = consecutivoLineaCaptura.substring(0, Constantes.LONGITUD_COMPLEMENTO_SOLICITUD_LINEA_CAPTURA);
		long nuevoConsecutivo = this.lineaCapturaFacade.obtenerSiguienteConsecutivoLineaCaptura();
		return complemento + anioEnDosDigitos + rellenarCadena(String.valueOf(nuevoConsecutivo), Constantes.LONGITUD_CONSECUTIVO_LINEA_CAPTURA);
	}
	
	private String rellenarCadena(final String valor, final int longitug) {
		return String.format("%1$" + longitug + "s", valor).replace(' ', '0');
	}
	
	public mx.gob.atdt.interprete.dto.sat.dto.TramiteDTO construirTramiteSolicitud(
			final TramiteDTO tramiteSeleccionado, 
			final ProyectoDTO proyecto, final UsuarioDTO usuarioTramite) {
		mx.gob.atdt.interprete.dto.sat.dto.TramiteDTO tramite = new mx.gob.atdt.interprete.dto.sat.dto.TramiteDTO();
		
		DetLineaCapturaDTO  detLineaCaptura = 
				this.lineaCapturaFacade.buscarDetLineaCapturaPorIdProyecto(proyecto.getIdProyecto()).get();
		List<DetalleTramiteDTO> tramitesLC = new ArrayList<>();
		
		List<DetTramitesLineaCapturaDTO> detTramitesLineaCaptura = 
				this.lineaCapturaFacade.buscarDetTramitesLineaCapturaPorIdDetLineaCaptura(
				detLineaCaptura.getIdDetalleLineaCaptura());

		int idSecuencia = 1;
		for (DetTramitesLineaCapturaDTO tramiteLineaCaptura : detTramitesLineaCaptura) {
		
			DetalleTramiteDTO detalleTramite = new DetalleTramiteDTO();
			detalleTramite.setHomoClave(tramiteLineaCaptura.getHomoclave());
			detalleTramite.setNumeroTramite(idSecuencia++);
			detalleTramite.setVariante(tramiteLineaCaptura.getVariante());
			
			
			List<DetConceptosTramiteDTO> conceptos = 
					this.lineaCapturaFacade.buscarDetConceptosTramites(tramiteLineaCaptura.getIdTramiteLineaCaptura());

			detalleTramite.setNumeroConceptos(conceptos.size());
			
			List<DetalleConceptoDTO> conceptoTramites = this.construirConceptosDeTramite(conceptos);
			
			ConceptoDTO conceptosDTO = new ConceptoDTO();
			conceptosDTO.setConcepto(conceptoTramites);
			
			detalleTramite.setConceptos(conceptosDTO);
			detalleTramite.setTotalTramite((int)this.obtenerTotalTramite(conceptoTramites));
			tramitesLC.add(detalleTramite);
		}
		
		tramite.setTramite(tramitesLC);
		return tramite;
	}
	
	private double obtenerTotalTramite(final List<DetalleConceptoDTO> conceptosTramites) {
		// Dato obligatorio igual a la suma de todos los campos "TotalConcepto".
		return conceptosTramites.get(0).getTotalConcepto();
	}
	
	private List<DetalleConceptoDTO> construirConceptosDeTramite(final List<DetConceptosTramiteDTO> conceptos){
		return conceptos.stream()
		.map(concepto -> {
			DetalleConceptoDTO newConcepto = new DetalleConceptoDTO();
			newConcepto.setNumeroSecuencia(concepto.getSecuencia());
			newConcepto.setClaveConcepto(concepto.getClave() + "");
			
			
			AgrupadorDTO agrupador = new AgrupadorDTO();
			agrupador.setTipoAgrupador(concepto.getTipoAgrupadorDTO().getTipoAgrupador());
			agrupador.setIdAgrupador(concepto.getTipoAgrupadorDTO().getIdTipoAgrupador());
			newConcepto.setAgrupador(agrupador);
			
			DatosIcepDTO datosIcepDTO = new DatosIcepDTO();
			datosIcepDTO.setClavePeriodicidad(concepto.getCatPeriodicidadDTO().getClave());
			datosIcepDTO.setClavePeriodo(concepto.getPeriodoDTO().getClave());
			datosIcepDTO.setFechaCausacion(FORMATO_FECHA_DDMMYYYY.format(new Date()));//Campo pendiente
			newConcepto.setDatosIcep(datosIcepDTO);
			
			TransaccionPDTO dp = new TransaccionPDTO();
			List<DetalleTransaccionPDTO> transaccionesP =  new ArrayList<>();
			double totalContribuciones = 0;
			for(DetTransaccionConceptoDTO transaccionBD : 
					this.lineaCapturaFacade.buscarTransaccionesConcepto(concepto.getIdConceptoTramite())){
				DetalleTransaccionPDTO transaccion = new DetalleTransaccionPDTO();
				transaccion.setClaveTransaccion(transaccionBD.getClave() + "");
				transaccion.setValorTransaccion((int) transaccionBD.getValor());
				transaccionesP.add(transaccion);
				totalContribuciones += transaccion.getValorTransaccion();
				
			}
			
			//Temporalmente se asigna el valor de la primera transacción, pero el valor correcto debe ser la suma de todas las transacciones.
			totalContribuciones = transaccionesP.get(0).getValorTransaccion();
			dp.setTransaccionP(transaccionesP);
			newConcepto.setdP(dp);
			newConcepto.setTotalContribuciones((int) totalContribuciones);//Valor que debe ser igual a la suma de los montos registrados.
			newConcepto.setTotalConcepto((int) totalContribuciones);//El valor debe ser igual a "TotalContribuciones" menos "Compensación" (Si es utilizado).
			
			return newConcepto;
		})
		.collect(Collectors.toList());
	}

    public Optional<LineaCapturaDTO> buscarLineaCapturaVigente(final long idTramite) {
    		return this.lineaCapturaFacade.buscarLineaCapturaVigente(idTramite)
        			.filter(lineaCaptura -> Objects.nonNull(lineaCaptura.getLineaCaptura()));
    	
    }
    
    public void regenerarFormatoLineaCaptura(final TramiteDTO tramiteSeleccionado) {		
			try {
				this.generarLineaDeCaptura(tramiteSeleccionado, seccionesProyectoBean.getProyectoDTO());
			} catch (ServiciosException e) {
				LOGGER.error("ServiciosException Error: ", e);
			}
			bandejaTramiteBean.inicializar();
    }
    
    public void prepararDescargaLC(TramiteDTO tramiteSeleccionado) {
    	try {
	        LineaCapturaDTO lineaCapturaDTO = tramiteSeleccionado.getLineaCapturaDTO();
	        String pathDocumento = lineaCapturaDTO.getRutaDocumentoLineaCaptura();
	        String nombreArchivo = lineaCapturaDTO.getSolicitudLineaCaptura();
	
	        filePdfLC = DefaultStreamedContent.builder()
	                .name(nombreArchivo + ".pdf")
	                .contentType("application/pdf")
	                .stream(() -> {
	                    try {
	                        return new URL(pathDocumento).openStream();
	                    } catch (Exception e) {
	                        throw new RuntimeException(e);
	                    }
	                })
	                .build();
	        WebResources.successMessage("msj_lc_comprobante_descargado", false);
	    }catch (Exception e) {
			LOGGER.error("No se encontró el PDF para la descarga del tramite " + tramiteSeleccionado.getFolioSeguimiento(), e);
			WebResources.addErrorMessage("msj_lc_comprobante_no_encontrado", false);
		}
    }
    
    public static String decodeBase64Html(String base64Html) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64Html);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
    
	public String extraerLigaDocumentoLC(RespuestaLineaCapturaDTO<RespuestaGeneracionLCDTO> respuestaLC) {
		String archivoHtml = decodeBase64Html(respuestaLC.getRespuestaDTO().getAcuseDTO().getHtml());
		String targetText = "Obtener Formato de Pago"; 
	    int index = archivoHtml.indexOf(targetText);
	    if (index == -1) return null;
	    // Buscar href antes del texto
	    int hrefStart = archivoHtml.lastIndexOf("href=\"", index);
	    if (hrefStart == -1) return null;
	    hrefStart += 6; // saltar 'href="'

	    int hrefEnd = archivoHtml.indexOf("\"", hrefStart);
	    if (hrefEnd == -1) return null;

	    return archivoHtml.substring(hrefStart, hrefEnd);	
	}

	public void generarComprobanteLC(final TramiteDTO tramiteSeleccionado) {
		LineaCapturaDTO lineaCapturaDTO = tramiteSeleccionado.getLineaCapturaDTO();
        String pathDocumento = lineaCapturaDTO.getRutaDocumentoLineaCaptura();
        String nombreArchivo = lineaCapturaDTO.getSolicitudLineaCaptura();
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
			
			response.reset();
			response.setHeader("Content-Type", "application/pdf");
			response.setHeader("Content-Disposition", "attachment;filename="+nombreArchivo+"_Acuse.pdf");
			try (OutputStream out = response.getOutputStream();
			     InputStream in = new URL(pathDocumento).openStream()) {
				byte[] bytesBuffer = new byte[2048];
			    int bytesRead;
			    while ((bytesRead = in.read(bytesBuffer)) != -1) {
			        out.write(bytesBuffer, 0, bytesRead);
			    }
			    out.flush();
			}
			facesContext.responseComplete();
			WebResources.successMessage("msj_lc_comprobante_descargado", false);
		}catch (Exception e) {
			LOGGER.error("No se encontró el PDF para la descarga del tramite " + tramiteSeleccionado.getFolioSeguimiento(), e);
			WebResources.addErrorMessage("msj_lc_comprobante_no_encontrado", false);
		}
	}
    /**GETTER´s y SETTER´s**/
	public boolean isDeshabilitarBotonFinalizarTramite() {
		return deshabilitarBotonFinalizarTramite;
	}

	public boolean isMostrarBotonGeneraLineaCaptura() {
		LOGGER.info("isMostrarBotonGeneraLineaCaptura: {}", mostrarBotonGeneraLineaCaptura);
		return mostrarBotonGeneraLineaCaptura;
	}

	public void setDeshabilitarBotonFinalizarTramite(boolean deshabilitarBotonFinalizarTramite) {
		this.deshabilitarBotonFinalizarTramite = deshabilitarBotonFinalizarTramite;
	}

	public void setMostrarBotonGeneraLineaCaptura(boolean mostrarBotonGeneraLineaCaptura) {
		this.mostrarBotonGeneraLineaCaptura = mostrarBotonGeneraLineaCaptura;
	}

	public StreamedContent getFilePdfLC() {
		if(this.filePdfLC == null || this.deshabilitarBotonFinalizarTramite) {
			LOGGER.warn("Archivo PDF de línea de captura no disponible para descarga");
			 return null;
			
		}
		return filePdfLC;
	}
	
}

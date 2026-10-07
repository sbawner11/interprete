package mx.gob.atdt.interprete.facade;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.NoResultException;

import com.google.gson.Gson;

import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.DetConceptosTramiteDAO;
import mx.gob.atdt.interprete.dao.DetLineaCapturaDAO;
import mx.gob.atdt.interprete.dao.DetTramitesLineaCapturaDAO;
import mx.gob.atdt.interprete.dao.DetTransaccionConceptoDAO;
import mx.gob.atdt.interprete.dao.LineaCapturaDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dao.SolicitudLineaCapturaDAO;
import mx.gob.atdt.interprete.dao.UsuarioDAO;
import mx.gob.atdt.interprete.dto.CatEstatusLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.CatEstatusTramiteDTO;
import mx.gob.atdt.interprete.dto.DetConceptosTramiteDTO;
import mx.gob.atdt.interprete.dto.DetLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.DetTramitesLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.DetTransaccionConceptoDTO;
import mx.gob.atdt.interprete.dto.LineaCapturaDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.dto.sat.dto.SolicitudLineaCapturaDTO;
import mx.gob.atdt.interprete.formulario.facade.FormularioFacade;
import mx.gob.atdt.interprete.linea.captura.dto.DatosPagosDTO;
import mx.gob.atdt.interprete.linea.captura.dto.ResponseEstatusLCDTO;

@Stateless
@LocalBean
public class LineaCapturaFacade {

	@Inject
	private LineaCapturaDAO lineaCapturaDAO;
	
	@Inject
	private ProyectoDAO proyectoDAO;
	
	@Inject
	private DetLineaCapturaDAO detLineaCapturaDAO;
	
	@Inject
	private DetTramitesLineaCapturaDAO detTramitesLineaCapturaDAO;
	
	@Inject
	private DetConceptosTramiteDAO detConceptosTramiteDAO;
	
	@Inject
	private DetTransaccionConceptoDAO detTransaccionConceptoDAO;
	
	@Inject
	private UsuarioDAO usuarioDAO;
	
	@Inject
	private SolicitudLineaCapturaDAO solicitudLineaCapturaDAO;
	
	@Inject 
	private FormularioFacade formularioFacade;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void guardarLineaCaptura(LineaCapturaDTO lineaCaptura) {
		this.lineaCapturaDAO.guardar(lineaCaptura);
	}
	
	public ProyectoDTO buscarProyectoPorId(final long idProyecto) {
		return this.proyectoDAO.buscarPorId(idProyecto);
	}
	
	public Optional<LineaCapturaDTO> buscarLineaCapturaVigente(final long idTramite) {
		return this.lineaCapturaDAO.buscarLineaCapturaVigentePorTramite(idTramite);
	}
	
	public Optional<DetLineaCapturaDTO> buscarDetLineaCapturaPorIdProyecto(final Long idProyecto){
		try {
			return Optional.ofNullable(this.detLineaCapturaDAO.buscarPorIdProyecto(idProyecto));
		} catch(NoResultException rse) {
			return Optional.empty();
		}
	}
	
	public List<DetTramitesLineaCapturaDTO> buscarDetTramitesLineaCapturaPorIdDetLineaCaptura(
			final Long idDetLineaCaptura){
		try {
			return this.detTramitesLineaCapturaDAO
					.buscarPorIdDetalleLineaCaptura(idDetLineaCaptura);
		} catch(NoResultException rse) {
			return new ArrayList<>();
		}
	}
	
	public List<DetConceptosTramiteDTO> buscarDetConceptosTramites(
			final Long idTramiteLineaCaptura){
		try {
			return this.detConceptosTramiteDAO
					.buscarConceptosPorTramite(idTramiteLineaCaptura);
		} catch(NoResultException rse) {
			return new ArrayList<>();
		}
	}
	
	public List<DetTransaccionConceptoDTO> buscarTransaccionesConcepto(
			final Long idConceptoTramite){
		try {
			return this.detTransaccionConceptoDAO
					.buscarPorIdConceptoTramite(idConceptoTramite);
		} catch(NoResultException rse) {
			return new ArrayList<>();
		}
	}
	
	public List<LineaCapturaDTO> buscarLineasDeCapturaPorTramitePorIdLineaCapturaDescendente(
			final Long idTramite){
		return this.lineaCapturaDAO.buscarLineasDeCapturaPorTramitePorIdLineaCapturaDescendente(idTramite);
	}
	
	public List<LineaCapturaDTO> buscarLineasDeCapturaMasReciente(final int tamanioRegistros){
		return this.lineaCapturaDAO.buscarLineasDeCapturaMasReciente(tamanioRegistros);
	}
	
	public UsuarioDTO buscarUsuarioPorId(final Long getIdUsuarioLlaveCdmx) {
		return usuarioDAO.buscarPorId(getIdUsuarioLlaveCdmx);
	}
	
	/**
	 * Guarda una solicitud de línea de captura.
	 * Si el estatus de la solicitud es 3 (Solicitud exitosa) guarda la linea de captura.
	 * 
	 * @param solicitudDTO La solicitud de línea de captura a guardar
	 * @param lineaCapturaDTO La linea de captura a guardar
	 * @return El DTO de la solicitud guardada
	 * @throws Exception Si ocurre un error durante el guardado
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public SolicitudLineaCapturaDTO guardarSolicitudLineaCaptura(SolicitudLineaCapturaDTO solicitudDTO) throws Exception {
		
		solicitudLineaCapturaDAO.guardar(solicitudDTO);
	
		return solicitudDTO;
	}
	
	public Long obtenerSiguienteConsecutivoLineaCaptura() {
        return this.lineaCapturaDAO.obtenerSiguienteConsecutivoLineaCaptura();
    }
	
	public List<SolicitudLineaCapturaDTO> buscarSolicitudMasRecientePorTramite(final Long idTramite,
    		final int tamanioRegistros) {
		return this.solicitudLineaCapturaDAO
				.buscarSolicitudMasRecientePorTramite(idTramite, tamanioRegistros);
	}
	
	
	/**
     * Metodo auxiliar para procesar la respuesta del sat
     * al servicio de estatus LC
     * @param estatusLCDTO respuesta servicio
     * @param tramiteSeleccionado tramite seleccionado
     * @param gson Gson
     * @return tramite modificado
     */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public TramiteDTO procesarRespuestaEstatusLC(ResponseEstatusLCDTO estatusLCDTO, TramiteDTO tramiteSeleccionado, final Gson gson) {
		TramiteDTO tramite = tramiteSeleccionado;
		LineaCapturaDTO lineaCapturaDTO = tramite.getLineaCapturaDTO();
		LocalDate fechaActual = LocalDate.now();
        LocalDate localFechaVigencia = new java.sql.Date(tramite.getLineaCapturaDTO().getFechaVigencia().getTime()).toLocalDate();
		//Se completa la informacion relativa a la linea de captura para poder actualizar el registro
		for(DatosPagosDTO pagoDTO : estatusLCDTO.getDatosPagos()) {
			if(Constantes.ESTATUS_LC_PAGD.equals(estatusLCDTO.getEstatus())){
				lineaCapturaDTO.setFechaPagoLc(BeanUtils.convertirStringDate(pagoDTO.getFechaRecepcionPago()));
			}
			lineaCapturaDTO.setMonto(pagoDTO.getImportePagado());
		}
		if(Constantes.ESTATUS_LC_PAGD.equals(estatusLCDTO.getEstatus())){
			tramite.setCatEstatusTramiteDTO(new CatEstatusTramiteDTO(Constantes.ID_ESTATUS_ENVIADO));
			lineaCapturaDTO.setCatEstatusLineaCaptura(new CatEstatusLineaCapturaDTO(Constantes.ID_LC_ESTATUS_PAGADO));
			
			//Se procede a actualizar el estatus del tramite a enviado				
			formularioFacade.actualizarEstatusTramite(tramite);
		}else if(localFechaVigencia.isBefore(fechaActual)){
			lineaCapturaDTO.setCatEstatusLineaCaptura(new CatEstatusLineaCapturaDTO(Constantes.ID_LC_ESTATUS_VENCIDA));
		}
		lineaCapturaDTO.setRespuestaServicioEstatus(gson.toJson(estatusLCDTO));		
		//se invoca la actualizacion del registro de linea de captura
		lineaCapturaDAO.actualizar(lineaCapturaDTO);
		tramite.setLineaCapturaDTO(lineaCapturaDTO);
		return tramite;
	}
}

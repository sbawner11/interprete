package mx.gob.atdt.interprete.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import mx.gob.atdt.interprete.commons.enums.ResponseEnum;
import mx.gob.atdt.interprete.dao.CatEjercicioDAO;
import mx.gob.atdt.interprete.dao.CatPeriodicidadDAO;
import mx.gob.atdt.interprete.dao.CatPeriodoDAO;
import mx.gob.atdt.interprete.dao.CatTipoAgrupadorDAO;
import mx.gob.atdt.interprete.dao.DetConceptosTramiteDAO;
import mx.gob.atdt.interprete.dao.DetTramitesLineaCapturaDAO;
import mx.gob.atdt.interprete.dto.DetConceptosTramiteDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.model.CatPeriodicidad;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detConceptosTramite")
@RequestScoped
public class DetConceptosTramiteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetConceptosTramiteService.class);

	@Inject
	private DetConceptosTramiteDAO detConceptosTramiteDAO;
	
	@Inject
	private DetTramitesLineaCapturaDAO detTramitesLineaCapturaDAO;
		
	@Inject
	private CatTipoAgrupadorDAO catTipoAgrupadorDAO;
	
	@Inject
	private CatPeriodoDAO catPeriodoDAO;
	
	@Inject
	private CatPeriodicidadDAO catPeriodicidadDAO;
	
	@Inject
	private CatEjercicioDAO catEjercicioDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarConceptoTramite(DetConceptosTramiteDTO detConceptosTramiteDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();

		if (BeanUtils.isNull(detConceptosTramiteDTO.getIdConceptoTramite())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getTramiteLineaCapturaDTO().getIdTramiteLineaCaptura())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getSecuencia())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getClave())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getAgrupador())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getTipoAgrupadorDTO().getIdTipoAgrupador())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getCatPeriodicidadDTO().getIdPeriodicidad())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getPeriodoDTO().getIdPeriodo())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getClaveContable())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getImporte())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getIdUsuarioRegistro())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getFechaCreacion())
				|| BeanUtils.isNull(detConceptosTramiteDTO.getFechaActualizacion())
				|| BeanUtils.isNull(detConceptosTramiteDTO.isActivo())				
				|| BeanUtils.isNull(detConceptosTramiteDTO.isSeccionSincronizada())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		try {
			if(BeanUtils.isNull(detTramitesLineaCapturaDAO.buscarPorIdDetalleLineaCaptura(detConceptosTramiteDTO.getTramiteLineaCapturaDTO().getIdTramiteLineaCaptura()))) {
				respuesta.setCodigo(ResponseEnum.TRAMITE_LINEA_CAPTURA_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.TRAMITE_LINEA_CAPTURA_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			if(BeanUtils.isNull(catTipoAgrupadorDAO.buscarPorId(detConceptosTramiteDTO.getTipoAgrupadorDTO().getIdTipoAgrupador()))) {
				respuesta.setCodigo(ResponseEnum.TIPO_AGRUPADOR_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.TIPO_AGRUPADOR_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			if(BeanUtils.isNull(catPeriodicidadDAO.buscarPorId(detConceptosTramiteDTO.getCatPeriodicidadDTO().getIdPeriodicidad()))) {
				respuesta.setCodigo(ResponseEnum.PERIODICIDAD_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.PERIODICIDAD_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			if(BeanUtils.isNull(catPeriodoDAO.buscarPorId(detConceptosTramiteDTO.getPeriodoDTO().getIdPeriodo()))) {
				respuesta.setCodigo(ResponseEnum.PERIODO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.PERIODO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			detConceptosTramiteDAO.actualizar(detConceptosTramiteDTO);
			
			respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());
			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
		} catch (Exception e) {
			LOGGER.error("Error en guardado de Conceptos de trámites :: ", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}

	}
}

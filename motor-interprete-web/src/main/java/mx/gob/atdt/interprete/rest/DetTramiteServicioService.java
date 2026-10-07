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
import mx.gob.atdt.interprete.dao.DetHomeDAO;
import mx.gob.atdt.interprete.dao.DetHomeTramiteDAO;
import mx.gob.atdt.interprete.dto.DetTramiteServicioDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detHomeTramite")
@RequestScoped
public class DetTramiteServicioService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetTramiteServicioService.class);

	@Inject
	private DetHomeDAO detHomeDAO;

	@Inject
	private DetHomeTramiteDAO tramiteServicioDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarDetalleTramiteServicio(DetTramiteServicioDTO tramiteDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();

		if (BeanUtils.isNull(tramiteDTO.getIdDetalleTramite())
				|| BeanUtils.isNull(tramiteDTO.getDetHomeDTO().getIdDetalleHome())
				|| BeanUtils.isNull(tramiteDTO.isHabilitaCostoTramite())
				|| BeanUtils.isNull(tramiteDTO.isHabilitaExcepcionTramite())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		if (detHomeDAO.buscarPorId(tramiteDTO.getDetHomeDTO().getIdDetalleHome()) != null) {
			try {
				tramiteServicioDAO.actualizar(tramiteDTO);
				respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());

				return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
			} catch (Exception e) {
				LOGGER.error("*** ERROR AL GUARDAR HOME DE UN TRAMITE ***", e);
				respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
			}
		} else {
			respuesta.setCodigo(ResponseEnum.DETALLE_HOME_INEXISTENTE.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.DETALLE_HOME_INEXISTENTE.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
	}
}

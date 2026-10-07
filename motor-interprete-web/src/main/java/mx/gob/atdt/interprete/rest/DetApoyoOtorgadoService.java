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
import mx.gob.atdt.interprete.dao.DetApoyoOtorgadoDAO;
import mx.gob.atdt.interprete.dao.DetHomeDAO;
import mx.gob.atdt.interprete.dto.DetApoyoOtorgadoDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detApoyoOtorgado")
@RequestScoped
public class DetApoyoOtorgadoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetApoyoOtorgadoService.class);

	@Inject
	private DetHomeDAO detHomeDAO;

	@Inject
	private DetApoyoOtorgadoDAO apoyoDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarApoyo(DetApoyoOtorgadoDTO apoyoDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();

		if (BeanUtils.isNull(apoyoDTO.getIdApoyo()) || BeanUtils.isNull(apoyoDTO.getDetHomeDTO().getIdDetalleHome())
				|| BeanUtils.isNull(apoyoDTO.getDescripcionApoyoOtorgado()) || BeanUtils.isNull(apoyoDTO.getOrden())
				|| BeanUtils.isNull(apoyoDTO.isActivo())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		if (detHomeDAO.buscarPorId(apoyoDTO.getDetHomeDTO().getIdDetalleHome()) != null) {
			try {
				apoyoDAO.actualizar(apoyoDTO);
				respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());

				return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
			} catch (Exception e) {
				LOGGER.error("*** ERROR AL GUARDAR APOYO ***", e);
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

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
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.DetHomeDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detHome")
@RequestScoped
public class DetHomeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetHomeService.class);

	@Inject
	private ProyectoDAO proyectoDAO;

	@Inject
	private DetHomeDAO detHomeDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarDetalleHome(DetHomeDTO detHomeDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();

		if (BeanUtils.isNull(detHomeDTO.getIdDetalleHome())
				|| BeanUtils.isNull(detHomeDTO.getProyectoDTO().getIdProyecto())
				|| BeanUtils.isNull(detHomeDTO.getRutaArchivoLogotipo())
				|| BeanUtils.isNull(detHomeDTO.isHabilitaNotificacion())
				|| BeanUtils.isNull(detHomeDTO.getFechaCreacion())
				|| BeanUtils.isNull(detHomeDTO.getFechaUltimaActualizacion()) || BeanUtils.isNull(detHomeDTO.isActivo())
				|| BeanUtils.isNull(detHomeDTO.isSeccionSincronizada()) 
				|| BeanUtils.isNull(detHomeDTO.isPersonalizaPausa())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		if (proyectoDAO.buscarProyectoPorId(detHomeDTO.getProyectoDTO().getIdProyecto())) {
			try {
				detHomeDAO.actualizar(detHomeDTO);
				respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());

				return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
			} catch (Exception e) {
				LOGGER.error("*** ERROR AL GUARDAR DATOS HOME ***", e);
				respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
			}
		} else {
			respuesta.setCodigo(ResponseEnum.PROYECTO_INEXISTENTE.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.PROYECTO_INEXISTENTE.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
	}

}

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
import mx.gob.atdt.interprete.dao.DetDistribucionDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.DetDistribucionDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detDistribucion")
@RequestScoped
public class DetDistribucionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetDistribucionService.class);

	@Inject
	private DetDistribucionDAO detDistribucionDAO;
	
	@Inject
	private ProyectoDAO proyectoDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarDetalleDistribucion(DetDistribucionDTO detDistribucion) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		if (BeanUtils.isNull(detDistribucion.getIdDistribucion()) || BeanUtils.isNull(detDistribucion.getComponenteDTO().getIdComponente()) 
				|| BeanUtils.isNull(detDistribucion.getProyectoDTO().getIdProyecto()) || BeanUtils.isNull(detDistribucion.getFechaCreacion()) 
				|| BeanUtils.isNull(detDistribucion.getFechaUltimaActualizacion()) || BeanUtils.isNull(detDistribucion.isActivo()) 
				|| BeanUtils.isNull(detDistribucion.getSeccionSincronizada()) ) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		try {
			if(!proyectoDAO.buscarProyectoPorId(detDistribucion.getProyectoDTO().getIdProyecto())) {
				respuesta.setCodigo(ResponseEnum.PROYECTO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.PROYECTO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			detDistribucionDAO.actualizar(detDistribucion);
			respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());
			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
		} catch (Exception e) {
			LOGGER.error("Error en guardado :: ", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}
	}
}

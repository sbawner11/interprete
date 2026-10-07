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
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.facade.SeccionesFormularioFacade;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/seccion")
@RequestScoped
public class SeccionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SeccionService.class);
	
	@Inject
	private ProyectoDAO proyectoDAO;
	
	@Inject
	private SeccionesFormularioFacade seccionesFormularioFacade;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarSeccion(SeccionesFormularioDTO seccion) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		if (BeanUtils.isNull(seccion.getIdSeccionFormulario()) || BeanUtils.isNull(seccion.getProyectoDTO().getIdProyecto()) 
				|| BeanUtils.isNull(seccion.getNombreSeccion()) || BeanUtils.isNull(seccion.getOrden()) 
				|| BeanUtils.isNull(seccion.isActivo()) || BeanUtils.isNull(seccion.isSeccionSincronizada()) ) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		try {
			if(!proyectoDAO.buscarProyectoPorId(seccion.getProyectoDTO().getIdProyecto())) {
				respuesta.setCodigo(ResponseEnum.PROYECTO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.PROYECTO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			seccionesFormularioFacade.actualizar(seccion);
			respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());
			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
		} catch (Exception e) {
			LOGGER.error("Error en guardado :: ", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(e.toString());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}
	}
}

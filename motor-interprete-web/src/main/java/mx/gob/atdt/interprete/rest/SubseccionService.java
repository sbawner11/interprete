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
import mx.gob.atdt.interprete.dao.SeccionesFormularioDAO;
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/subseccion")
@RequestScoped
public class SubseccionService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SubseccionService.class);
	
	@Inject
	private SeccionesFormularioDAO seccionesFormulario;	
	
	@Inject
	private SubSeccionesFormularioDAO subSeccionesFormularioDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarSeccion(SubSeccionesFormularioDTO subseccion) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		if (BeanUtils.isNull(subseccion.getIdSubseccionFormulario()) || BeanUtils.isNull(subseccion.getSeccionesFormularioDTO().getIdSeccionFormulario()) 
				|| BeanUtils.isNull(subseccion.getNombreSubseccion()) || BeanUtils.isNull(subseccion.getOrden()) 
				|| BeanUtils.isNull(subseccion.isActivo()) || BeanUtils.isNull(subseccion.isSeccionSincronizada()) ) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		try {
			if(BeanUtils.isNull(seccionesFormulario.buscarPorId(subseccion.getSeccionesFormularioDTO().getIdSeccionFormulario()))) {
				respuesta.setCodigo(ResponseEnum.SECCION_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.SECCION_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}

			subSeccionesFormularioDAO.actualizar(subseccion);
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

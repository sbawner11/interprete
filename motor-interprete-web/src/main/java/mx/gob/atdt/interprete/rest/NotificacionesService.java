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
import mx.gob.atdt.interprete.dao.CatDiasSemanaDAO;
import mx.gob.atdt.interprete.dao.NotificacionesDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.NotificacionesDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/notificaciones")
@RequestScoped
public class NotificacionesService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NotificacionesService.class);

	@Inject
	private CatDiasSemanaDAO catDiasSemanaDAO;
	@Inject
	private NotificacionesDAO notificacionesDAO;
	@Inject
	private ProyectoDAO proyectoDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarNotificaciones(NotificacionesDTO notificaciones) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		
		if (BeanUtils.isNull(notificaciones.getIdNotificacion()) 
				|| BeanUtils.isNull(notificaciones.getProyectoDTO().getIdProyecto()) 
				|| BeanUtils.isNull(notificaciones.isEnvioNotificaciones())
				|| BeanUtils.isNull(notificaciones.getIdUsuario())
				|| BeanUtils.isNull(notificaciones.getFechaCreacion())
				|| BeanUtils.isNull(notificaciones.getFechaActualizacion())
				|| BeanUtils.isNull(notificaciones.isActivo())
				|| BeanUtils.isNull(notificaciones.isSeccionSincronizada()) ) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		try {
			if(BeanUtils.isNull(proyectoDAO.buscarPorId(notificaciones.getProyectoDTO().getIdProyecto()))) {
				respuesta.setCodigo(ResponseEnum.PROYECTO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.PROYECTO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			if (BeanUtils.isNotNull(notificaciones.getCatDiaSemanaDTO()) &&
				    BeanUtils.isNotNull(notificaciones.getCatDiaSemanaDTO().getIdDiaSemana()) &&
				    BeanUtils.isNull(catDiasSemanaDAO.buscarPorId(notificaciones.getCatDiaSemanaDTO().getIdDiaSemana()))) {
				respuesta.setCodigo(ResponseEnum.DIA_SEMANA_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.DIA_SEMANA_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			notificacionesDAO.actualizar(notificaciones);
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
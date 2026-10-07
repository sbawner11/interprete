package mx.gob.atdt.interprete.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import mx.gob.atdt.interprete.commons.enums.ResponseEnum;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/interprete")
@RequestScoped
public class ValidaDisponibilidadInterpreteService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidaDisponibilidadInterpreteService.class);
	
	@Inject
	private ProyectoDAO proyectoDAO;

	@GET
	@Path("/verificaEstatus")
	@Produces(MediaType.APPLICATION_JSON)
	public Response consultaDisponibilidad(@QueryParam("idProyecto") String idProyecto) {
		Gson gson = new GsonBuilder()
			    .disableHtmlEscaping()
			    .create();
		ResponseDTO respuesta = new ResponseDTO();
		if (BeanUtils.isNull(idProyecto)) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		} else {
			try {
				Long.parseLong(idProyecto);
			} catch (final NumberFormatException e) {
				LOGGER.warn("No se pudo consultar la disponibilidad del proyecto porque se recibió como parámetro un id String en lugar de un id numérico");
				respuesta.setCodigo(ResponseEnum.IDENTIFICADOR_PROYECTO_INCORRECTO.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.IDENTIFICADOR_PROYECTO_INCORRECTO.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
		}
		try {
			List<ProyectoDTO> listaProyectos = proyectoDAO.buscarTodos();
			if(BeanUtils.isNotNull(listaProyectos)) {
				for(ProyectoDTO proyectoTemp: listaProyectos) {
					if(proyectoTemp.getIdProyecto() != Long.parseLong(idProyecto)) {
						respuesta.setCodigo(ResponseEnum.PROYECTO_NO_COINCIDE.getCodigoRespuesta());
						respuesta.setMensaje(ResponseEnum.PROYECTO_NO_COINCIDE.getMensajeRespuesta().replace("&", proyectoTemp.getIdProyecto().toString()).replace("$", idProyecto));
						return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
					}
				}
			} else {
				/**09/07/2025 Se devuelve código de resultado para que el motor identifique cuando es una primer sincronización.**/
				respuesta.setCodigo(ResponseEnum.PRIMER_SINCRONIZACION.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.PRIMER_SINCRONIZACION.getMensajeRespuesta());
				return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
			}
			respuesta.setCodigo(ResponseEnum.SINCRONIZACION_DISPONIBLE.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.SINCRONIZACION_DISPONIBLE.getMensajeRespuesta());
			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
		} catch (Exception e) {			
			LOGGER.error("Error validación de disponibilidad :: ", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}
	}
}

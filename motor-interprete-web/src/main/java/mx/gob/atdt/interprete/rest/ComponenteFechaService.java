package mx.gob.atdt.interprete.rest;

import java.util.TimeZone;

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
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteFechaDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.facade.ComponenteFechaFacade;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/componenteFecha")
@RequestScoped
public class ComponenteFechaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComponenteFechaService.class);

	@Inject
	private SubSeccionesFormularioDAO subseccionDAO;

	@Inject
	private ComponenteFechaFacade componenteFechaFacade;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarComponenteFecha(ComponenteFechaDTO fechaDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		
		if (BeanUtils.isNull(fechaDTO.getIdComponente()) || BeanUtils.isNull(fechaDTO.getIdComponenteFecha())
				|| BeanUtils.isNull(fechaDTO.isDiasInhabiles()) || BeanUtils.isNull(fechaDTO.isFechaMenorHoy())
				|| BeanUtils.isNull(fechaDTO.isFechaMayorHoy())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		
		if (subseccionDAO.buscarSubseccionesPorIdSubseccion(fechaDTO.getSubSeccionesFormularioDTO().getIdSubseccionFormulario())) {
			try {				
				TimeZone.setDefault(TimeZone.getTimeZone("UTC"));				
				componenteFechaFacade.actualizar(fechaDTO);
				
				respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());

				return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
			} catch (Exception e) {
				LOGGER.error("*** ERROR AL GUARDAR FECHA ***", e);
				respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
				respuesta.setMensaje(e.toString());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
			} finally {
				TimeZone.setDefault(TimeZone.getTimeZone("America/Mexico_City"));
			}
		} else {
			respuesta.setCodigo(ResponseEnum.SUBSECCION_INEXISTENTE.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.SUBSECCION_INEXISTENTE.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
	}
}

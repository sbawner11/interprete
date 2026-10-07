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
import mx.gob.atdt.interprete.dao.ConfiguracionCondicionesDAO;
import mx.gob.atdt.interprete.dao.ConfiguracionCondicionValorDAO;
import mx.gob.atdt.interprete.dto.ConfiguracionCondicionValorDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/configuracionCondicionValor")
@RequestScoped
public class ConfiguracionCondicionValorService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracionCondicionValorService.class);

	@Inject
	private ConfiguracionCondicionValorDAO condicionValorDAO;
	
	@Inject
	private ConfiguracionCondicionesDAO configuracionCondicionDAO;
	
	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarDetElementosToken(ConfiguracionCondicionValorDTO condicionValor) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		
		if (BeanUtils.isNull(condicionValor.getIdCondicionValor())
				|| BeanUtils.isNull(condicionValor.getConfiguracionCondicionesDTO().getIdConfiguracion())
				|| BeanUtils.isNull(condicionValor.getValor()) ) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		try {
			if(BeanUtils.isNull(configuracionCondicionDAO.buscarPorId(condicionValor.getConfiguracionCondicionesDTO().getIdConfiguracion()))) {
				respuesta.setCodigo(ResponseEnum.CONDICION_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.CONDICION_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			condicionValorDAO.actualizar(condicionValor);
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

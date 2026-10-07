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
import mx.gob.atdt.interprete.dao.SeccionesFormularioDAO;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.CatOperadorDAO;
import mx.gob.atdt.interprete.dto.ConfiguracionCondicionesDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/configuracionCondiciones")
@RequestScoped
public class ConfiguracionCondicionesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracionCondicionesService.class);

	@Inject
	private ConfiguracionCondicionesDAO configuracionCondicionesDAO;
	
	@Inject
	private ComponenteDAO componente;

	@Inject
	private CatOperadorDAO catOperador;

	@Inject
	private SeccionesFormularioDAO seccionesFormulario;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarConfiguracionCondiciones(ConfiguracionCondicionesDTO configuracionCondiciones) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		
		if (BeanUtils.isNull(configuracionCondiciones.getIdConfiguracion()) 
				|| BeanUtils.isNull(configuracionCondiciones.getSeccionesFormularioByIdSeccionCondicionadaDTO().getIdSeccionFormulario()) 
				|| BeanUtils.isNull(configuracionCondiciones.getSeccionesFormularioByIdSeccionCondicionDTO().getIdSeccionFormulario())
				|| BeanUtils.isNull(configuracionCondiciones.getComponenteDTO().getIdComponente())
				|| BeanUtils.isNull(configuracionCondiciones.getCatOperadorDTO().getIdOperador())
				|| BeanUtils.isNull(configuracionCondiciones.isActivo())
				|| BeanUtils.isNull(configuracionCondiciones.getIdUsuarioRegistro())
				|| BeanUtils.isNull(configuracionCondiciones.getFechaCreacion())
				|| BeanUtils.isNull(configuracionCondiciones.getFechaUltimaActualizacion())
				|| BeanUtils.isNull(configuracionCondiciones.isSeccionSincronizada()) ) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		try {
			if(BeanUtils.isNull(seccionesFormulario.buscarPorId(configuracionCondiciones.getSeccionesFormularioByIdSeccionCondicionadaDTO().getIdSeccionFormulario()))) {
				respuesta.setCodigo(ResponseEnum.SECCION_CONDICIONADA_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.SECCION_CONDICIONADA_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			if(BeanUtils.isNull(seccionesFormulario.buscarPorId(configuracionCondiciones.getSeccionesFormularioByIdSeccionCondicionDTO().getIdSeccionFormulario()))) {
				respuesta.setCodigo(ResponseEnum.SECCION_CONDICION_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.SECCION_CONDICION_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			if(BeanUtils.isNull(componente.buscarPorId(configuracionCondiciones.getComponenteDTO().getIdComponente()))) {
				respuesta.setCodigo(ResponseEnum.COMPONENTE_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.COMPONENTE_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			if(BeanUtils.isNull(catOperador.buscarPorId(configuracionCondiciones.getCatOperadorDTO().getIdOperador()))) {
				respuesta.setCodigo(ResponseEnum.OPERADOR_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.OPERADOR_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			configuracionCondicionesDAO.actualizar(configuracionCondiciones);
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


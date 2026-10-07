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
import mx.gob.atdt.interprete.dao.CatalogosDAO;
import mx.gob.atdt.interprete.dao.OpcionesCatalogoDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.ConfiguracionCatalogoDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.facade.ConfiguracionCatalogoFacade;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/configuracionCatalogo")
@RequestScoped
public class ConfiguracionCatalogoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfiguracionCatalogoService.class);

	@Inject
    private ConfiguracionCatalogoFacade configuracionCatalogoFacade;

	@Inject
	private ProyectoDAO proyectoDAO;

	@Inject
	private CatalogosDAO catalogosDAO;

	@Inject
	private OpcionesCatalogoDAO opcionesCatalogoDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarConfiguracionCatalogo(ConfiguracionCatalogoDTO configuracionCatalogo) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		
		if (BeanUtils.isNull(configuracionCatalogo.getIdConfiguracionCatalogo()) 
				|| BeanUtils.isNull(configuracionCatalogo.getIdProyecto())
				|| BeanUtils.isNull(configuracionCatalogo.getIdCatalogo())
				|| BeanUtils.isNull(configuracionCatalogo.getIdOpcionCatalogo())
				|| BeanUtils.isNull(configuracionCatalogo.getDescripcionUsuario())
				|| BeanUtils.isNull(configuracionCatalogo.getIdUsuarioCambio())
				|| BeanUtils.isNull(configuracionCatalogo.getFechaCreacion())
				|| BeanUtils.isNull(configuracionCatalogo.getFechaActualizacion())
				|| BeanUtils.isNull(configuracionCatalogo.getActivo())
				|| BeanUtils.isNull(configuracionCatalogo.getSeccionSincronizada()) ) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		try {
			if(BeanUtils.isNull(proyectoDAO.buscarPorId(configuracionCatalogo.getIdProyecto()))) {
				respuesta.setCodigo(ResponseEnum.PROYECTO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.PROYECTO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			if(BeanUtils.isNull(catalogosDAO.buscarPorId(configuracionCatalogo.getIdCatalogo()))) {
				respuesta.setCodigo(ResponseEnum.CATALOGO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.CATALOGO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			if(BeanUtils.isNull(opcionesCatalogoDAO.buscarPorId(configuracionCatalogo.getIdOpcionCatalogo()))) {
				respuesta.setCodigo(ResponseEnum.OPCION_CATALOGO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.OPCION_CATALOGO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
								
			configuracionCatalogoFacade.actualizarConfiguracionCatalogo(configuracionCatalogo);
			
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


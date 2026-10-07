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
import mx.gob.atdt.interprete.dao.ComponenteMenuDesplegableDAO;
import mx.gob.atdt.interprete.dao.DetElementosMenuDAO;
import mx.gob.atdt.interprete.dto.DetElementosMenuDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detElementosMenuDesplegable")
@RequestScoped
public class DetElementosMenuDesplegableService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetElementosMenuDesplegableService.class);

	@Inject
	private DetElementosMenuDAO detElementosMenuDAO;
	
	@Inject
	private ComponenteMenuDesplegableDAO menuDesplegableDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarDetElementoMenuDesplegable(DetElementosMenuDTO detElementoMenu) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		if (BeanUtils.isNull(detElementoMenu.getIdElementoMenu()) || BeanUtils.isNull(detElementoMenu.getComponenteMenuDesplegableDTO().getIdComponenteMenuDesplegable()) 
				|| BeanUtils.isNull(detElementoMenu.isActivo()) || BeanUtils.isNull(detElementoMenu.getDescripcionElemento())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		try {
			if(!menuDesplegableDAO.buscarPorIdComponenteMenu(detElementoMenu.getComponenteMenuDesplegableDTO().getIdComponenteMenuDesplegable())) {
				respuesta.setCodigo(ResponseEnum.COMPONENTE_MENU_DESPLEGABLE_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.COMPONENTE_MENU_DESPLEGABLE_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			detElementosMenuDAO.actualizar(detElementoMenu);
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

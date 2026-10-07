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
import mx.gob.atdt.interprete.dao.CatTipoCostoDAO;
import mx.gob.atdt.interprete.dao.DetPagoDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.DetPagoDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detPago")
@RequestScoped
public class DetPagoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetPagoService.class);

	@Inject
	private DetPagoDAO detPagoDAO;
	
	@Inject
	private ProyectoDAO proyectoDAO;
	
	@Inject
	private CatTipoCostoDAO catTipoCostoDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarDetallePago(DetPagoDTO detPago) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		if (BeanUtils.isNull(detPago.getIdDetallePago()) || BeanUtils.isNull(detPago.getProyectoDTO().getIdProyecto()) 
				|| BeanUtils.isNull(detPago.getCatTipoCostoDTO().getIdTipoCosto()) 
				|| BeanUtils.isNull(detPago.getUrlServicio()) || BeanUtils.isNull(detPago.getIdentificadorProceso()) 
				|| BeanUtils.isNull(detPago.getFechaCreacion()) || BeanUtils.isNull(detPago.getFechaUltimaActualizacion()) 
				|| BeanUtils.isNull(detPago.isActivo()) || BeanUtils.isNull(detPago.isSeccionSincronizada()) ) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		try {
			if(!proyectoDAO.buscarProyectoPorId(detPago.getProyectoDTO().getIdProyecto())) {
				respuesta.setCodigo(ResponseEnum.PROYECTO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.PROYECTO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			if(BeanUtils.isNull(catTipoCostoDAO.buscarPorIdTipoCosto(detPago.getCatTipoCostoDTO().getIdTipoCosto()))) {
				respuesta.setCodigo(ResponseEnum.TIPO_COSTO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.TIPO_COSTO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			detPagoDAO.actualizar(detPago);
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

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
import mx.gob.atdt.interprete.dto.DetFirmaDigitalDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.facade.SeccionFirmaDigitalFacade;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detFirmaDigital")
@RequestScoped
public class DetFirmaDigitalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetFirmaDigitalService.class);

	@Inject
	private ProyectoDAO proyectoDAO;

	@Inject
	private SeccionFirmaDigitalFacade seccionFirmaDigitalFacade;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarDetalleFirmaDigital(DetFirmaDigitalDTO firmaDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();

		if (BeanUtils.isNull(firmaDTO.getIdDetalleFirma()) || BeanUtils.isNull(firmaDTO.getProyectoDTO().getIdProyecto())
				|| BeanUtils.isNull(firmaDTO.getClaveSistema()) || BeanUtils.isNull(firmaDTO.getUrlFirmado())
				|| BeanUtils.isNull(firmaDTO.getUrlRedirecciona()) || BeanUtils.isNull(firmaDTO.getUsuarioDominioSeg())
				|| BeanUtils.isNull(firmaDTO.getContrasenaDominioSeg()) || BeanUtils.isNull(firmaDTO.getFechaCreacion())
				|| BeanUtils.isNull(firmaDTO.getFechaUltimaActualizacion()) || BeanUtils.isNull(firmaDTO.isActivo()) 
				|| BeanUtils.isNull(firmaDTO.isSeccionSincronizada())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		if (proyectoDAO.buscarProyectoPorId(firmaDTO.getProyectoDTO().getIdProyecto())) {
			try {
				seccionFirmaDigitalFacade.actualizar(firmaDTO);
				respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());

				return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
			} catch (Exception e) {
				LOGGER.error("*** ERROR AL GUARDAR DATOS FIRMA ***", e);
				respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
			}
		} else {
			respuesta.setCodigo(ResponseEnum.PROYECTO_INEXISTENTE.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.PROYECTO_INEXISTENTE.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
	}
}

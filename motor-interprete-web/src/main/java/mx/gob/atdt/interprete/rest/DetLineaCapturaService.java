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
import mx.gob.atdt.interprete.dao.CatDependenciaPagoDAO;
import mx.gob.atdt.interprete.dao.CatTipoPersonaDAO;
import mx.gob.atdt.interprete.dao.CatTipoVigenciaDAO;
import mx.gob.atdt.interprete.dao.CatUnidadAdministrativaPagoDAO;
import mx.gob.atdt.interprete.dao.DetLineaCapturaDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.DetLineaCapturaDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detLineaCaptura")
@RequestScoped
public class DetLineaCapturaService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DetLineaCapturaService.class);

	@Inject
	private DetLineaCapturaDAO detLineaCapturaDAO;
	
	@Inject
	private CatDependenciaPagoDAO catDependenciaPagoDAO;
	
	@Inject
	private CatUnidadAdministrativaPagoDAO catUnidadAdministrativaPagoDAO;
	
	@Inject
	private CatTipoVigenciaDAO catTipoVigenciaDAO;
	
	@Inject
	private CatTipoPersonaDAO catTipoPersonaDAO;
	
	@Inject
	private ProyectoDAO proyectoDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarDetalleLineaCaptura(DetLineaCapturaDTO detLineaCapturaDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		
		if (BeanUtils.isNull(detLineaCapturaDTO.getIdDetalleLineaCaptura()) 
				|| BeanUtils.isNull(detLineaCapturaDTO.getProyectoDTO().getIdProyecto()) 
				|| BeanUtils.isNull(detLineaCapturaDTO.getDependenciaPagoDTO().getIdDependenciaPago())
				|| BeanUtils.isNull(detLineaCapturaDTO.getUnidadAdministrativaPagoDTO().getIdUnidadAdministrativaPago())
				|| BeanUtils.isNull(detLineaCapturaDTO.getVigencia())
				|| BeanUtils.isNull(detLineaCapturaDTO.getTipoVigenciaDTO().getIdTipoVigencia())
				|| BeanUtils.isNull(detLineaCapturaDTO.getTipoPersonaDTO().getIdTipoPersona())
				|| BeanUtils.isNull(detLineaCapturaDTO.getIdUsuarioRegistro())
				|| BeanUtils.isNull(detLineaCapturaDTO.getFechaCreacion())
				|| BeanUtils.isNull(detLineaCapturaDTO.getFechaActualizacion())
				|| BeanUtils.isNull(detLineaCapturaDTO.isCompleto())
				|| BeanUtils.isNull(detLineaCapturaDTO.isActivo())
				|| BeanUtils.isNull(detLineaCapturaDTO.isSeccionSincronizada()) ) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		try {
			if(BeanUtils.isNull(proyectoDAO.buscarPorId(detLineaCapturaDTO.getProyectoDTO().getIdProyecto()))) {
				respuesta.setCodigo(ResponseEnum.PROYECTO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.PROYECTO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			if(BeanUtils.isNull(catDependenciaPagoDAO.buscarPorIdDependenciaPago(detLineaCapturaDTO.getDependenciaPagoDTO().getIdDependenciaPago()))) {
				respuesta.setCodigo(ResponseEnum.DEPENDENCIA_PAGO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.DEPENDENCIA_PAGO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			if(BeanUtils.isNull(catUnidadAdministrativaPagoDAO.buscarPorIdUnidadAdministrativaPago(detLineaCapturaDTO.getUnidadAdministrativaPagoDTO().getIdUnidadAdministrativaPago()))) {
				respuesta.setCodigo(ResponseEnum.UNIDAD_ADMINISTRATIVA_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.UNIDAD_ADMINISTRATIVA_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			if(BeanUtils.isNull(catTipoVigenciaDAO.buscarPorIdTipoVigencia(detLineaCapturaDTO.getTipoVigenciaDTO().getIdTipoVigencia()))) {
				respuesta.setCodigo(ResponseEnum.TIPO_VIGENCIA_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.TIPO_VIGENCIA_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			if(BeanUtils.isNull(catTipoPersonaDAO.buscarPorIdTipoPersona(detLineaCapturaDTO.getTipoPersonaDTO().getIdTipoPersona()))) {
				respuesta.setCodigo(ResponseEnum.TIPO_PERSONA_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.TIPO_PERSONA_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			detLineaCapturaDAO.actualizar(detLineaCapturaDTO);
			
			respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());
			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
		} catch (Exception e) {
			LOGGER.error("Error en guardado del detalle de línea de captura :: ", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}

	}
}
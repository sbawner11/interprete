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
import mx.gob.atdt.interprete.dao.DetLineaCapturaDAO;
import mx.gob.atdt.interprete.dao.DetSecurityDomainLineasCapturaDAO;
import mx.gob.atdt.interprete.dto.DetSecurityDomainLineasCapturaDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detSecurityDomainLineasCaptura")
@RequestScoped
public class DetSecurityDomainLineasCapturaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetSecurityDomainLineasCapturaService.class);

	@Inject
	private DetSecurityDomainLineasCapturaDAO detSecurityDomainLineasCapturaDAO;
	
	@Inject
	private DetLineaCapturaDAO detLineaCapturaDAO;
		
	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarSecurityDomainLC(DetSecurityDomainLineasCapturaDTO detSecurityDomainLineasCapturaDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();

		if (BeanUtils.isNull(detSecurityDomainLineasCapturaDTO.getIdSecurityDomainLc())
				|| BeanUtils.isNull(detSecurityDomainLineasCapturaDTO.getDetLineaCapturaDTO().getIdDetalleLineaCaptura())
				|| BeanUtils.isNull(detSecurityDomainLineasCapturaDTO.getUsuario())
				|| BeanUtils.isNull(detSecurityDomainLineasCapturaDTO.getContrasenia())
				|| BeanUtils.isNull(detSecurityDomainLineasCapturaDTO.getUrlSistema())
				|| BeanUtils.isNull(detSecurityDomainLineasCapturaDTO.getIdUsuarioRegistro())
				|| BeanUtils.isNull(detSecurityDomainLineasCapturaDTO.getFechaCreacion())
				|| BeanUtils.isNull(detSecurityDomainLineasCapturaDTO.getFechaUltimaActualizacion())
				|| BeanUtils.isNull(detSecurityDomainLineasCapturaDTO.isActivo())					
				|| BeanUtils.isNull(detSecurityDomainLineasCapturaDTO.isSeccionSincronizada())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		try {
			if(BeanUtils.isNull(detLineaCapturaDAO.buscarPorIdDetalleLineaCaptura(detSecurityDomainLineasCapturaDTO.getDetLineaCapturaDTO().getIdDetalleLineaCaptura()))) {
				respuesta.setCodigo(ResponseEnum.DETALLE_LINEA_CAPTURA_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.DETALLE_LINEA_CAPTURA_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			detSecurityDomainLineasCapturaDAO.actualizar(detSecurityDomainLineasCapturaDTO);
			
			respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());
			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
		} catch (Exception e) {
			LOGGER.error("Error en guardado de detalle de seguridad de dominio de linea de captura :: ", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}

	}
}

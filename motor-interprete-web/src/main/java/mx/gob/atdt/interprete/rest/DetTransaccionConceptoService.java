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
import mx.gob.atdt.interprete.dao.DetConceptosTramiteDAO;
import mx.gob.atdt.interprete.dao.DetTransaccionConceptoDAO;
import mx.gob.atdt.interprete.dto.DetTransaccionConceptoDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detTransaccionConcepto")
@RequestScoped
public class DetTransaccionConceptoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetTransaccionConceptoService.class);

	@Inject
	private DetConceptosTramiteDAO detConceptosTramiteDAO;
	
	@Inject
	private DetTransaccionConceptoDAO detTransaccionConceptoDAO;
		
	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarTransaccionConcepto(DetTransaccionConceptoDTO detTransaccionConceptoDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();

		if (BeanUtils.isNull(detTransaccionConceptoDTO.getIdTransaccionConcepto())
				|| BeanUtils.isNull(detTransaccionConceptoDTO.getDetConceptosTramiteDTO().getIdConceptoTramite())
				|| BeanUtils.isNull(detTransaccionConceptoDTO.getClave())
				|| BeanUtils.isNull(detTransaccionConceptoDTO.getValor())
				|| BeanUtils.isNull(detTransaccionConceptoDTO.isActualizacion())
				|| BeanUtils.isNull(detTransaccionConceptoDTO.isRecargo())
				|| BeanUtils.isNull(detTransaccionConceptoDTO.isMulta())
				|| BeanUtils.isNull(detTransaccionConceptoDTO.getIdUsuarioRegistro())
				|| BeanUtils.isNull(detTransaccionConceptoDTO.getFechaCreacion())
				|| BeanUtils.isNull(detTransaccionConceptoDTO.getFechaActualizacion())
				|| BeanUtils.isNull(detTransaccionConceptoDTO.isActivo())					
				|| BeanUtils.isNull(detTransaccionConceptoDTO.isSeccionSincronizada())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		try {
			if(BeanUtils.isNull(detConceptosTramiteDAO.buscarPorIdConceptoTramite(detTransaccionConceptoDTO.getDetConceptosTramiteDTO().getIdConceptoTramite()))) {
				respuesta.setCodigo(ResponseEnum.CONCEPTO_TRAMITE_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.CONCEPTO_TRAMITE_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			detTransaccionConceptoDAO.actualizar(detTransaccionConceptoDTO);
			
			respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());
			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
		} catch (Exception e) {
			LOGGER.error("Error en guardado de transacción conceptos :: ", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}

	}
}

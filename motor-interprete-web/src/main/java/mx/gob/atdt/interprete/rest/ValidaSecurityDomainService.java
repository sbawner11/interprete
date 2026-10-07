package mx.gob.atdt.interprete.rest;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import mx.gob.atdt.interprete.commons.enums.ResponseEnum;
import mx.gob.atdt.interprete.dto.ResponseDTO;

@Path("/interprete")
@RequestScoped
public class ValidaSecurityDomainService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ValidaSecurityDomainService.class);
	

	@GET
	@Path("/validaSeguridadDominio")
	@Produces(MediaType.APPLICATION_JSON)
	public Response validaSeguridadDominio() {	
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		try {			
			respuesta.setCodigo(ResponseEnum.SINCRONIZACION_DISPONIBLE.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.SINCRONIZACION_DISPONIBLE.getMensajeRespuesta());
			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
		} catch (Exception e) {			
			LOGGER.error("Error validación de seguridad de dominio :: ", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}
	}
}

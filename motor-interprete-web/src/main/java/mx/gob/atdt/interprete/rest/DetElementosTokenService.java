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
import mx.gob.atdt.interprete.dao.ArchivosRespuestaTokenDAO;
import mx.gob.atdt.interprete.dao.ComponenteDAO;
import mx.gob.atdt.interprete.dao.DetElementosTokenDAO;
import mx.gob.atdt.interprete.dto.DetElementosTokenDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/detElementosToken")
@RequestScoped
public class DetElementosTokenService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DetElementosTokenService.class);

	@Inject
	private DetElementosTokenDAO detElementosTokenDAO;
	
	@Inject
	private ArchivosRespuestaTokenDAO archivosRespuestaTokenDAO;
	
	@Inject
	private ComponenteDAO componenteDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarDetElementosToken(DetElementosTokenDTO detElementoToken) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		if (BeanUtils.isNull(detElementoToken.getIdElementoToken()) || BeanUtils.isNull(detElementoToken.getArchivosRespuestaTokenDTO().getIdArchivoRespuesta()) 
				|| BeanUtils.isNull(detElementoToken.getNombreToken()) || BeanUtils.isNull(detElementoToken.getCatOrigenTokenDTO().getIdOrigenToken())
				|| BeanUtils.isNull(detElementoToken.getOrden()) || BeanUtils.isNull(detElementoToken.isActivo())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		try {
			if(BeanUtils.isNull(archivosRespuestaTokenDAO.buscarPorId(detElementoToken.getArchivosRespuestaTokenDTO().getIdArchivoRespuesta()))) {
				respuesta.setCodigo(ResponseEnum.ARCHIVO_RESPUESTA_TOKEN_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.ARCHIVO_RESPUESTA_TOKEN_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			if (detElementoToken.getCatOrigenTokenDTO().getIdOrigenToken() == 3) {
				if(componenteDAO.buscarPorId(detElementoToken.getIdComponente()) == null) {
					respuesta.setCodigo(ResponseEnum.COMPONENTE_INEXISTENTE.getCodigoRespuesta());
					respuesta.setMensaje(ResponseEnum.COMPONENTE_INEXISTENTE.getMensajeRespuesta());
					return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
				}	
			}			
			detElementosTokenDAO.actualizar(detElementoToken);
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

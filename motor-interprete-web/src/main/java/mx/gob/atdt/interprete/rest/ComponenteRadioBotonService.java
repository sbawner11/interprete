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
import mx.gob.atdt.interprete.dao.SubSeccionesFormularioDAO;
import mx.gob.atdt.interprete.dto.ComponenteRadiobotonDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.facade.ComponenteRadioFacade;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/componenteRadioBoton")
@RequestScoped
public class ComponenteRadioBotonService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComponenteRadioBotonService.class);

	@Inject
	private SubSeccionesFormularioDAO subseccionDAO;

	@Inject
	private ComponenteRadioFacade componenteRadioFacade;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarComponenteCheckboxGrupo(ComponenteRadiobotonDTO radiobotonDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();

		if (BeanUtils.isNull(radiobotonDTO.getIdComponente())
				|| BeanUtils.isNull(radiobotonDTO.getIdComponenteRadioboton())
				|| BeanUtils.isNull(radiobotonDTO.isHabilitaOpcionOtro())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		if (subseccionDAO.buscarSubseccionesPorIdSubseccion(radiobotonDTO.getSubSeccionesFormularioDTO().getIdSubseccionFormulario())) {
			try {
				componenteRadioFacade.actualizar(radiobotonDTO);
				respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());

				return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
			} catch (Exception e) {
				LOGGER.error("*** ERROR AL GUARDAR RADIO BOTON ***", e);
				respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
				respuesta.setMensaje(e.toString());
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
			}
		} else {
			respuesta.setCodigo(ResponseEnum.SUBSECCION_INEXISTENTE.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.SUBSECCION_INEXISTENTE.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
	}
}

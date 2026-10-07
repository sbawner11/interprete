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
import mx.gob.atdt.interprete.dto.ComponenteDatosDomicilioDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.facade.DatosDomicilioFacade;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/componenteDatosDomicilio")
@RequestScoped
public class ComponenteDatosDomicilioService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ComponenteDatosDomicilioService.class);

	@Inject
	private SubSeccionesFormularioDAO subseccionDAO;

	@Inject
	private DatosDomicilioFacade datosDomicilioFacade;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarComponenteDatosDomicilio(ComponenteDatosDomicilioDTO datosDomicilioDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();

		if (BeanUtils.isNull(datosDomicilioDTO.getIdComponente())
				|| BeanUtils.isNull(datosDomicilioDTO.getIdComponenteDatosDomicilio())
				|| BeanUtils.isNull(datosDomicilioDTO.isHabilitaCalle())
				|| BeanUtils.isNull(datosDomicilioDTO.isCalleObligatorio())
				|| BeanUtils.isNull(datosDomicilioDTO.isHabilitaNumeroExterior())
				|| BeanUtils.isNull(datosDomicilioDTO.isNumeroExteriorObligatorio())
				|| BeanUtils.isNull(datosDomicilioDTO.isHabilitaNumeroInterior())
				|| BeanUtils.isNull(datosDomicilioDTO.isNumeroInteriorObligatorio())
				|| BeanUtils.isNull(datosDomicilioDTO.isHabilitaCodigoPostal())
				|| BeanUtils.isNull(datosDomicilioDTO.isCodigoPostalObligatorio())
				|| BeanUtils.isNull(datosDomicilioDTO.isHabilitaColonia())
				|| BeanUtils.isNull(datosDomicilioDTO.isColoniaObligatorio())
				|| BeanUtils.isNull(datosDomicilioDTO.isHabilitaAlcaldia())
				|| BeanUtils.isNull(datosDomicilioDTO.isAlcaldiaObligatorio())
				|| BeanUtils.isNull(datosDomicilioDTO.isEstadoObligatorio())
				|| BeanUtils.isNull(datosDomicilioDTO.isHabilitaEstado())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		if (subseccionDAO.buscarSubseccionesPorIdSubseccion(datosDomicilioDTO.getSubSeccionesFormularioDTO().getIdSubseccionFormulario())) {
			try {
				datosDomicilioFacade.actualizar(datosDomicilioDTO);
				respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());

				return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
			} catch (Exception e) {
				LOGGER.error("*** ERROR AL GUARDAR DATOS DOMICILIO ***", e);
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

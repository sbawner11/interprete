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
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/proyecto")
@RequestScoped
public class ProyectoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProyectoService.class);

	@Inject
	private ProyectoDAO proyectoDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarProyecto(ProyectoDTO proyectoDTO) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();

		if (BeanUtils.isNull(proyectoDTO.getIdProyecto()) || BeanUtils.isNull(proyectoDTO.getNombreProyecto())
				|| BeanUtils.isNull(proyectoDTO.getCatTipoProyectoDTO().getIdTipoProyecto())
				|| BeanUtils.isNull(proyectoDTO.getCatDependenciaDTO().getIdDependencia())
				|| BeanUtils.isNull(proyectoDTO.isHabilitaCaptcha())
				|| BeanUtils.isNull(proyectoDTO.isHabilitaAccesoLlave())
				|| BeanUtils.isNull(proyectoDTO.isHabilitaPagoLinea())
				|| BeanUtils.isNull(proyectoDTO.isHabilitaGestionUsuarios())
				|| BeanUtils.isNull(proyectoDTO.isHabilitaFirmaDigital())
				|| BeanUtils.isNull(proyectoDTO.isHabilitaDetalleLegales())
				|| BeanUtils.isNull(proyectoDTO.isHabilitaAnalytics())
				|| BeanUtils.isNull(proyectoDTO.isHabilitaSecurityDomain())
				|| BeanUtils.isNull(proyectoDTO.isHabilitaDistribucion())
				|| BeanUtils.isNull(proyectoDTO.isHabilitaConfiguracionCatalogos())
				|| BeanUtils.isNull(proyectoDTO.isAviso())
				|| BeanUtils.isNull(proyectoDTO.getCatEstatusProyectoDTO().getIdEstatusProyecto())
				|| BeanUtils.isNull(proyectoDTO.getUsuarioDTO().getIdUsuarioLlaveCdmx())
				|| BeanUtils.isNull(proyectoDTO.getFechaCreacion())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		try {
			proyectoDAO.actualizar(proyectoDTO);
			respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());

			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
		} catch (Exception e) {
			LOGGER.error("*** ERROR AL GUARDAR DATOS PROYECTO ***", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}
	}
}

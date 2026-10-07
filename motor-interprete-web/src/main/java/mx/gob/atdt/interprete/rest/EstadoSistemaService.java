package mx.gob.atdt.interprete.rest;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

import mx.gob.atdt.interprete.application.SeccionesProyectoBean;
import mx.gob.atdt.interprete.commons.enums.ResponseEnum;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dao.DetEstadoSistemaDAO;
import mx.gob.atdt.interprete.dao.ProyectoDAO;
import mx.gob.atdt.interprete.dto.CatEstadosSistemaDTO;
import mx.gob.atdt.interprete.dto.DetEstadoSistemaDTO;
import mx.gob.atdt.interprete.dto.ProyectoDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.formularios.application.GenerarFormularioApplication;
import mx.gob.atdt.interprete.util.BeanUtils;
import mx.gob.atdt.widget.application.IndexBean;

@Path("/sistema")
@RequestScoped
public class EstadoSistemaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EstadoSistemaService.class);

	@Inject
	private DetEstadoSistemaDAO detEstadoSistemaDAO;
	
	@Inject
	private ProyectoDAO proyectoDAO;
	
	// Se inyectan bean que se cargan cuando levanta el server para pruebas
	@Inject
	private GenerarFormularioApplication generarFormularioApplication;
	
	@Inject
	private SeccionesProyectoBean seccionesProyectoBean;
	
	@Inject
	private IndexBean indexBean;

	@POST
	@Path("/actualizaSincronizacion")
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizaEstadoSistema(@QueryParam("idEstadoSistema") Integer idEstadoSistema) {
		
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();

		/**Se valida que contenga un estatus para continuar**/
		if (BeanUtils.isNull(idEstadoSistema)) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}

		try {
			/**Se valida identificador de estatus válidos**/
			if((idEstadoSistema.intValue() != Constantes.ID_ESTADO_SISTEMA_EN_SINCRONIZACION) 
					&& (idEstadoSistema.intValue() != Constantes.ID_ESTADO_SISTEMA_MANTENIMIENTO)
							&& (idEstadoSistema.intValue() != Constantes.ID_ESTADO_SISTEMA_LINEA)) {
				respuesta.setCodigo(ResponseEnum.ESTATUS_SISTEMA_NO_VALIDO.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.ESTATUS_SISTEMA_NO_VALIDO.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			/**Se consulta el registro para obtener estatus actual del Sistema y se actualiza a Modo mantenimiento**/
			DetEstadoSistemaDTO estadoSistemaActual = detEstadoSistemaDAO.buscarEstadoSistema();
			
			/** Si es la primera vez que se valida el estatus del Sistema, se tiene que generar el registro
			 * y marcarlo como en mantenimiento.
			 */
			if(BeanUtils.isNull(estadoSistemaActual)) {
				estadoSistemaActual = new DetEstadoSistemaDTO();				
				estadoSistemaActual.setCatEstadosSistemaDTO(new CatEstadosSistemaDTO(Constantes.ID_ESTADO_SISTEMA_MANTENIMIENTO));
				estadoSistemaActual.setFechaUltimaActualizacion(new Date());			
				detEstadoSistemaDAO.registrar(estadoSistemaActual);					
			} 

			if(idEstadoSistema == Constantes.ID_ESTADO_SISTEMA_EN_SINCRONIZACION 
					&& estadoSistemaActual.getCatEstadosSistemaDTO().getIdEstadoSistema() != Constantes.ID_ESTADO_SISTEMA_MANTENIMIENTO) {
				respuesta.setCodigo(ResponseEnum.ESTATUS_SISTEMA_NO_MANTENIMIENTO.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.ESTATUS_SISTEMA_NO_MANTENIMIENTO.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			if((idEstadoSistema == Constantes.ID_ESTADO_SISTEMA_MANTENIMIENTO || idEstadoSistema == Constantes.ID_ESTADO_SISTEMA_LINEA) 
					&& (estadoSistemaActual.getCatEstadosSistemaDTO().getIdEstadoSistema() != Constantes.ID_ESTADO_SISTEMA_EN_SINCRONIZACION)) {
				respuesta.setCodigo(ResponseEnum.ESTATUS_SISTEMA_NO_INICIO_SINCRONIZACION.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.ESTATUS_SISTEMA_NO_INICIO_SINCRONIZACION.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			estadoSistemaActual.setCatEstadosSistemaDTO(new CatEstadosSistemaDTO(idEstadoSistema));
			estadoSistemaActual.setFechaUltimaActualizacion(new Date());
			detEstadoSistemaDAO.actualizar(estadoSistemaActual);
			
			// Debe de entrar nuevamente a las clases application para que recargue la información siempre que sea estado de SINCRONIZADO
			//TODO las siguiente líneas se agregaron para pruebas de una sincronización sin tener que esperar
			//TODO los horarios de ejecución de las clases schedule.
//			if(BeanUtils.isNull(estadoSistemaActual)) {
//				estadoSistemaActual = new DetEstadoSistemaDTO();				
//				estadoSistemaActual.setCatEstadosSistemaDTO(new CatEstadosSistemaDTO(idEstadoSistema));
//				estadoSistemaActual.setFechaUltimaActualizacion(new Date());
//			
//				detEstadoSistemaDAO.registrar(estadoSistemaActual);	
//			} else {
//				estadoSistemaActual.setCatEstadosSistemaDTO(new CatEstadosSistemaDTO(idEstadoSistema));
//				estadoSistemaActual.setFechaUltimaActualizacion(new Date());
//				detEstadoSistemaDAO.actualizar(estadoSistemaActual);
//			}
//			LOGGER.info("***Va inicializar las clases application generarFormularioApplication y seccionesProyectoBean***");
			generarFormularioApplication.inicializarComponente();			
			seccionesProyectoBean.init();
//			LOGGER.info("***Fin BLOQUE DE PRUEBAS***");
						
			respuesta.setCodigo(ResponseEnum.ACTUALIZACION_CORRECTA.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ACTUALIZACION_CORRECTA.getMensajeRespuesta());
			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
			
		} catch (Exception e) {
			LOGGER.error("*** Error al registrar cambio de estado del sistema ***", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}
	}
	
	
	@POST
	@Path("/cambiarEstado")
	@Produces(MediaType.APPLICATION_JSON)
	public Response actualizaEstatusSistema(
			@QueryParam("idProyecto") Long idProyecto, 
			@QueryParam("idEstadoSistema") Integer idEstadoSistema) {
		
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		
		try {
			if(BeanUtils.isNull(idProyecto) && BeanUtils.isNull(idEstadoSistema)) {
				respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			ProyectoDTO proyectoActual = proyectoDAO.buscarPorId(idProyecto);
			if(BeanUtils.isNull(proyectoActual)) {
				respuesta.setCodigo(ResponseEnum.IDENTIFICADOR_PROYECTO_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.IDENTIFICADOR_PROYECTO_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			/*Se consulta el registro para obtener estatus actual del Sistema*/
			DetEstadoSistemaDTO estadoSistemaActual = detEstadoSistemaDAO.buscarEstadoSistema();
			
			if(idEstadoSistema.intValue() == Constantes.ID_ESTADO_SISTEMA_PAUSADO 
					&& estadoSistemaActual.getCatEstadosSistemaDTO().getIdEstadoSistema() == Constantes.ID_ESTADO_SISTEMA_PAUSADO){
				respuesta.setCodigo(ResponseEnum.PROYECTO_YA_EN_PAUSA.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.PROYECTO_YA_EN_PAUSA.getMensajeRespuesta());
				return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
			} 
			if(idEstadoSistema.intValue() == Constantes.ID_ESTADO_SISTEMA_PAUSADO
					&& (estadoSistemaActual.getCatEstadosSistemaDTO().getIdEstadoSistema() == Constantes.ID_ESTADO_SISTEMA_MANTENIMIENTO 
					|| estadoSistemaActual.getCatEstadosSistemaDTO().getIdEstadoSistema() == Constantes.ID_ESTADO_SISTEMA_EN_SINCRONIZACION)) {
				respuesta.setCodigo(ResponseEnum.ESTATUS_SISTEMA_NO_VALIDO_PAUSA.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.ESTATUS_SISTEMA_NO_VALIDO_PAUSA.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			
			estadoSistemaActual.setCatEstadosSistemaDTO(new CatEstadosSistemaDTO(idEstadoSistema));
			estadoSistemaActual.setFechaUltimaActualizacion(new Date());
			detEstadoSistemaDAO.actualizar(estadoSistemaActual);
			
			indexBean.inicializar();
			
			/**Al finalizar el proceso de sincronización desde el motor, 
			 * el último paso es cambiar el estado del sistema a estado 2 "Mantenimiento" 
			 * que significa que todo el envío fue correcto sin errores y por lo tanto el 
			 * motor marca al intérprete con este estado, para que el schedule que activa el 
			 * intérprete "CambioSistemaLineaSchedule" revisa que esté en este estatus para poder
			 * habilitar el proyecto al ciudadano**/
			if(estadoSistemaActual.getCatEstadosSistemaDTO().getIdEstadoSistema() == Constantes.ID_ESTADO_SISTEMA_MANTENIMIENTO
					|| estadoSistemaActual.getCatEstadosSistemaDTO().getIdEstadoSistema() == Constantes.ID_ESTADO_SISTEMA_LINEA){
				/**En este punto es necesario refrescar los datos de las clases @ApplicationScoped 
				 * porque después de una sincronización ya tuvieron cambios a nivel de BD**/
				generarFormularioApplication.inicializarComponente();			
				seccionesProyectoBean.init();
			}
			
			respuesta.setCodigo(ResponseEnum.ACTUALIZACION_CORRECTA.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ACTUALIZACION_CORRECTA.getMensajeRespuesta());
			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
		} catch (Exception e) {
			LOGGER.error("*** Error al actualizar el estatus del proyecto ***", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}
	}
}

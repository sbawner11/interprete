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
import mx.gob.atdt.interprete.dao.ComponenteCargaDocumentosDAO;
import mx.gob.atdt.interprete.dao.CrcCargaDocumentosTipoArchivoDAO;
import mx.gob.atdt.interprete.dto.CrcCargaDocumentosTipoArchivoDTO;
import mx.gob.atdt.interprete.dto.ResponseDTO;
import mx.gob.atdt.interprete.util.BeanUtils;

@Path("/crcCargaDocumentosTipoArchivo")
@RequestScoped
public class CrcCargaDocumentosTipoArchivoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CrcCargaDocumentosTipoArchivoService.class);

	@Inject
	private CrcCargaDocumentosTipoArchivoDAO crcCargaDocumentosTipoArchivoDAO;
	
	@Inject
	private ComponenteCargaDocumentosDAO cargaDocumentosDAO;

	@POST
	@Path("/crear")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response guardarCrcCargaDocumentosTipoArchivo(CrcCargaDocumentosTipoArchivoDTO crcCargaDocumentoTipoArchivo) {
		Gson gson = new Gson();
		ResponseDTO respuesta = new ResponseDTO();
		if (BeanUtils.isNull(crcCargaDocumentoTipoArchivo.getIdCargaDocumentoTipoArchivo()) || BeanUtils.isNull(crcCargaDocumentoTipoArchivo.getComponenteCargaDocumentosDTO().getIdComponenteCarga()) 
				|| BeanUtils.isNull(crcCargaDocumentoTipoArchivo.getCatTipoArchivoDTO().getIdTipoArchivo())) {
			respuesta.setCodigo(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.FALTAN_PARAMETROS_OBLIGATORIOS.getMensajeRespuesta());
			return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
		}
		try {
			if(!cargaDocumentosDAO.buscarPorIdComponenteCarga(crcCargaDocumentoTipoArchivo.getComponenteCargaDocumentosDTO().getIdComponenteCarga())) {
				respuesta.setCodigo(ResponseEnum.COMPONENTE_CARGA_DOCUMENTOS_INEXISTENTE.getCodigoRespuesta());
				respuesta.setMensaje(ResponseEnum.COMPONENTE_CARGA_DOCUMENTOS_INEXISTENTE.getMensajeRespuesta());
				return Response.status(Response.Status.BAD_REQUEST).entity(gson.toJson(respuesta)).build();
			}
			crcCargaDocumentosTipoArchivoDAO.actualizar(crcCargaDocumentoTipoArchivo);
			respuesta.setCodigo(ResponseEnum.CREACION_CORRECTA.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.CREACION_CORRECTA.getMensajeRespuesta());
			return Response.status(Response.Status.OK).entity(gson.toJson(respuesta)).build();
		} catch (Exception e) {
			LOGGER.error("Error en guardado:  ", e);
			respuesta.setCodigo(ResponseEnum.ERROR_INESPERADO.getCodigoRespuesta());
			respuesta.setMensaje(ResponseEnum.ERROR_INESPERADO.getMensajeRespuesta());
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(respuesta)).build();
		}
	}
}

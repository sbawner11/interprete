package mx.gob.atdt.firma.client;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.common.util.JerseyUtil;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.CadenasDigitalesDTO;
import mx.gob.atdt.interprete.dto.DetFirmaDigitalDTO;
import mx.gob.atdt.interprete.dto.FirmaTramiteDTO;
import mx.gob.atdt.interprete.dto.ResponseServiceConsultaFirmaDTO;
import mx.gob.atdt.interprete.dto.ResponseServiceFirmaDTO;
import mx.gob.atdt.interprete.exception.InterpreteException;

public class FirmaRESTClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(FirmaRESTClient.class);
	
	/**
	 * Método que se encarga de enviar la información del trámite para su firmado.
	 * 
	 * @param lstFirmaTramites
	 * @param firmaDigitalDTO
	 * @return
	 * @throws URISyntaxException
	 * @throws JSONException
	 * @throws InterpreteException
	 */
	public ResponseServiceFirmaDTO firmarRespuesta(List<FirmaTramiteDTO> lstFirmaTramites, DetFirmaDigitalDTO firmaDigitalDTO)
			throws URISyntaxException, JSONException, InterpreteException {
		ResponseServiceFirmaDTO responseFirma = null;		
		URI uri = null;
		String respuesta = null;
		try {
			uri = new URI(Environment.getUrlServiceFirmaRegistro().trim().trim());
			WebResource webResource = JerseyUtil.getInstance().getClientFirmaWithAuth().resource(uri.toString());
			
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", Constantes.CONTENT_TYPE)
					.post(ClientResponse.class, generaBodyFirmado(lstFirmaTramites, firmaDigitalDTO));		
			respuesta = response.getEntity(String.class);
			
			if (response.getStatus() == Response.Status.OK.getStatusCode()) {
				JSONObject jsonObj = new JSONObject(respuesta);
				if (BeanUtils.isNotNull(jsonObj)) {
					responseFirma = new ResponseServiceFirmaDTO();
					responseFirma.setCode(jsonObj.get("code").toString());
					responseFirma.setToken(jsonObj.get("token").toString());
					responseFirma.setIdSolicitud(jsonObj.get("idSolicitud").toString());					
				}				
			} else {
				LOGGER.error("No fue posible realizar el registro del trámite para firmado : " + respuesta);
				throw new InterpreteException("msj_error_firmar_tramite");
			}
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio de Firma: ", e);
			throw new InterpreteException("msj_error_consulta_firma");
		} catch (ClientHandlerException e) {
		    LOGGER.error("No se pudo conectar al servicio de Firma para registrar el tramite. URL: {}", uri, e);
		    throw new InterpreteException("msj_error_registro_firma_tramite");
		}
		return responseFirma;
	}
	
	/**
	 * Método auxiliar que genera el Body para firmado de trámite
	 * 
	 * @param firmaTramite
	 * @param firmaDigitalDTO
	 * @return
	 */
	private String generaBodyFirmado(List<FirmaTramiteDTO> lstFirmasTramite, DetFirmaDigitalDTO firmaDigitalDTO) {
		StringBuilder strBodyFirmado = new StringBuilder();
				
		strBodyFirmado.append("{");
		strBodyFirmado.append("\"").append(Constantes.CLIENT_ID).append("\"").append(":").append(firmaDigitalDTO.getClaveSistema()).append(",");
		strBodyFirmado.append("\"").append(Constantes.TIPO_FIRMA).append("\"").append(":\"").append(Constantes.FIRMA).append("\",");
		strBodyFirmado.append("\"").append(Constantes.CADENAS_DIGITALES).append("\"").append(":[");
		
		boolean iteracionInicial = true;
		
		for(FirmaTramiteDTO firmaTramite: lstFirmasTramite) {
			if(iteracionInicial == false) {
				strBodyFirmado.append(", ");
			}
			strBodyFirmado.append("{").append("\"").append(Constantes.CADENA).append("\"").append(":\"").append(firmaTramite.getCadenaFirmado()).append("\",");		
			strBodyFirmado.append("\"").append(Constantes.NOMBRE).append("\"").append(":\"").append(firmaTramite.getTramite().getProyectoDTO().getNombreProyecto()).append("\",");
			
			strBodyFirmado.append("\"").append(Constantes.DATA).append("\"").append(":[").append("{");
			
			strBodyFirmado.append("\"").append(Constantes.CLAVE).append("\"").append(":\"").append(Constantes.ID_PROYECTO).append("\",");
			strBodyFirmado.append("\"").append(Constantes.VALOR).append("\"").append(":\"").append(firmaTramite.getTramite().getProyectoDTO().getIdProyecto()).append("\",");
			
			strBodyFirmado.append("\"").append(Constantes.CLAVE).append("\"").append(":\"").append(Constantes.ID_TRAMITE).append("\",");
			strBodyFirmado.append("\"").append(Constantes.VALOR).append("\"").append(":\"").append(firmaTramite.getTramite().getIdTramite()).append("\",");
			
			strBodyFirmado.append("\"").append(Constantes.CLAVE).append("\"").append(":\"").append(Constantes.FOLIO_TRAMITE).append("\",");
			strBodyFirmado.append("\"").append(Constantes.VALOR).append("\"").append(":\"").append(firmaTramite.getTramite().getFolioSeguimiento()).append("\",");
			
			if(BeanUtils.isNotNull(firmaTramite.getUsuarioTramite())) {
				strBodyFirmado.append("\"").append(Constantes.CLAVE).append("\"").append(":\"").append(Constantes.ID_USUARIO_LLAVE_SOLICITANTE).append("\",");
				strBodyFirmado.append("\"").append(Constantes.VALOR).append("\"").append(":\"").append(firmaTramite.getUsuarioTramite().getIdUsuarioLlaveCdmx()).append("\",");
				
				strBodyFirmado.append("\"").append(Constantes.CLAVE).append("\"").append(":\"").append(Constantes.CURP_USUARIO_LLAVE_SOLICITANTE).append("\",");
				strBodyFirmado.append("\"").append(Constantes.VALOR).append("\"").append(":\"").append(firmaTramite.getUsuarioTramite().getCurp()).append("\",");			
			}
			
			strBodyFirmado.append("\"").append(Constantes.CLAVE).append("\"").append(":\"").append(Constantes.ID_USUARIO_LLAVE_FIRMANTE).append("\",");
			strBodyFirmado.append("\"").append(Constantes.VALOR).append("\"").append(":\"").append(firmaTramite.getUsuarioFirmante().getIdUsuarioLlaveCdmx()).append("\",");
	
			strBodyFirmado.append("\"").append(Constantes.CLAVE).append("\"").append(":\"").append(Constantes.CURP_USUARIO_LLAVE_FIRMANTE).append("\",");
			strBodyFirmado.append("\"").append(Constantes.VALOR).append("\"").append(":\"").append(firmaTramite.getUsuarioFirmante().getCurp()).append("\",");
			
			strBodyFirmado.append("\"").append(Constantes.CLAVE).append("\"").append(":\"").append(Constantes.ID_ESTATUS_TRAMITE).append("\",");
			strBodyFirmado.append("\"").append(Constantes.VALOR).append("\"").append(":\"").append(firmaTramite.getTramite().getCatEstatusTramiteDTO().getIdEstatusTramite()).append("\",");
	
			strBodyFirmado.append("\"").append(Constantes.CLAVE).append("\"").append(":\"").append(Constantes.FECHA_REGISTRO_TRAMITE).append("\",");
			strBodyFirmado.append("\"").append(Constantes.VALOR).append("\"").append(":\"").append(BeanUtils.convertirDateStringFirmado(firmaTramite.getTramite().getFechaCreacion())).append("\",");
			
			strBodyFirmado.append("\"").append(Constantes.CLAVE).append("\"").append(":\"").append(Constantes.FECHA_FIRMADO).append("\",");
			strBodyFirmado.append("\"").append(Constantes.VALOR).append("\"").append(":\"").append(BeanUtils.convertirDateStringFirmado(firmaTramite.getFechaFirmado())).append("\"");
	
			strBodyFirmado.append("}").append("]").append("}");		
			
			iteracionInicial = false;
		}
		
		strBodyFirmado.append("],");
		
		strBodyFirmado.append("\"").append(Constantes.ID_LLAVE).append("\"").append(":null,");
		strBodyFirmado.append("\"").append(Constantes.NOMBRE_FIRMANTE).append("\"").append(":null");
		strBodyFirmado.append("}");
		
		return strBodyFirmado.toString();
	}
	
	/**
	 * Método para consultar la respuesta del firmado
	 * 
	 * @param responseFirmaDTO
	 * @param firmaDigitalDTO
	 * @return
	 * @throws URISyntaxException
	 * @throws JSONException
	 * @throws InterpreteException
	 */
	public ResponseServiceConsultaFirmaDTO consultaFirmado(ResponseServiceFirmaDTO responseFirmaDTO, DetFirmaDigitalDTO firmaDigitalDTO) throws URISyntaxException, JSONException, InterpreteException {
		ResponseServiceConsultaFirmaDTO responseConsultaFirmadoDTO = null;
		URI uri = null;
		String respuesta = null;
		try {
			uri = new URI(Environment.getUrlServiceFirmaConsulta().trim());
			WebResource webResource = JerseyUtil.getInstance().getClientFirmaWithAuth().resource(uri.toString());
			ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
					.header("Content-Type", Constantes.CONTENT_TYPE)
					.header("token", responseFirmaDTO.getToken())
					.post(ClientResponse.class, generaBodyConsultaTramiteFirmado(responseFirmaDTO, firmaDigitalDTO));

			respuesta = response.getEntity(String.class);
			JSONObject jsonObj = new JSONObject(respuesta);
			
			if (BeanUtils.isNotNull(respuesta)) {
				if (jsonObj.get("status").toString().equalsIgnoreCase("Firmada")) {
					responseConsultaFirmadoDTO = new ResponseServiceConsultaFirmaDTO();
					responseConsultaFirmadoDTO.setCode(jsonObj.get("code").toString());
					responseConsultaFirmadoDTO.setStatus(jsonObj.get("status").toString());
					responseConsultaFirmadoDTO.setIdSolicitud(jsonObj.get("idSolicitud").toString());
					responseConsultaFirmadoDTO.setNumeroSerieCer(jsonObj.get("numeroSerieCer").toString());
					responseConsultaFirmadoDTO.setIdentificadorCer(jsonObj.get("identificadorCer").toString());
					responseConsultaFirmadoDTO.setNombreFirmante((jsonObj.get("nombreFirmante") != null && jsonObj.get("nombreFirmante").toString().isEmpty() == false) 
								? jsonObj.get("nombreFirmante").toString() : null);
					responseConsultaFirmadoDTO.setRespuestaServicio(respuesta);
					JSONArray jsonCadena = jsonObj.getJSONArray("cadenasDigitales");
					for (int i = 0; i < jsonCadena.length(); i++) {
						JSONObject json = jsonCadena.getJSONObject(i);
						CadenasDigitalesDTO cadenaDTO = new CadenasDigitalesDTO();
						cadenaDTO.setCadena(json.get("cadena").toString());
						cadenaDTO.setSelloDigitalFirma(json.get("selloDigitalFirma").toString());
						cadenaDTO.setSelloDigitalTimestamp(json.get("selloDigitalTimestamp").toString());						
						responseConsultaFirmadoDTO.getLstCadenaDigitales().add(cadenaDTO);
					}
				}	
			}			
		} catch (NoSuchAlgorithmException e) {
			LOGGER.error("No se pudo establecer la conexión al servicio de Firma: ", e);
			throw new InterpreteException("msj_error_consulta_firma");
		}
		return responseConsultaFirmadoDTO;
	}

	/**
	 * Método auxiliar que genera el Body para consultar un trámite firmado
	 * 
	 * @param responseFirmaDTO
	 * @param firmaDigitalDTO
	 * @return
	 */
	private String generaBodyConsultaTramiteFirmado(ResponseServiceFirmaDTO responseFirmaDTO, DetFirmaDigitalDTO firmaDigitalDTO) {
		StringBuilder strBodyFirmado = new StringBuilder();		
				
		strBodyFirmado.append("{");
		strBodyFirmado.append("\"").append(Constantes.CLIENT_ID).append("\"").append(":").append(firmaDigitalDTO.getClaveSistema()).append(",");
		strBodyFirmado.append("\"").append(Constantes.ID_SOLICITUD_FIRMA).append("\"").append(":\"").append(responseFirmaDTO.getIdSolicitud()).append("\"");
		strBodyFirmado.append("}");
		
		return strBodyFirmado.toString();
	}	
}

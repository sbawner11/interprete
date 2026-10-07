package mx.gob.atdt.interprete.formatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import mx.gob.atdt.interprete.common.infra.Environment;
import mx.gob.atdt.interprete.common.util.BeanUtils;
import mx.gob.atdt.interprete.commons.utils.Constantes;
import mx.gob.atdt.interprete.dto.ArchivosRespuestaTokenDTO;
import mx.gob.atdt.interprete.dto.ComponenteCheckboxUnicoDTO;
import mx.gob.atdt.interprete.dto.ComponenteDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosDomicilioDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonaMoralDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesDTO;
import mx.gob.atdt.interprete.dto.ComponenteDatosPersonalesLlaveDTO;
import mx.gob.atdt.interprete.dto.ComponenteMenuDesplegableDTO;
import mx.gob.atdt.interprete.dto.ComponenteRadiobotonDTO;
import mx.gob.atdt.interprete.dto.ControlComponentesDTO;
import mx.gob.atdt.interprete.dto.DetElementosMenuDTO;
import mx.gob.atdt.interprete.dto.DetElementosRadiobotonDTO;
import mx.gob.atdt.interprete.dto.DetElementosTokenDTO;
import mx.gob.atdt.interprete.dto.SeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.SubSeccionesFormularioDTO;
import mx.gob.atdt.interprete.dto.TramiteDTO;
import mx.gob.atdt.interprete.dto.TramiteFirmaElectronicaDTO;
import mx.gob.atdt.interprete.exception.InterpreteException;
import mx.gob.atdt.interprete.util.GeneradorQRUtil;

/**
 * La logica de la clase de movio al proyecto EJB
 * para futuras modificaciones hacerlas en el paquete mx.gob.atdt.interprete.common.formatos
 * 16/06/2026
 */
@Deprecated
public class FormatoRespuestaPDF {

	private static final Logger LOGGER = LoggerFactory.getLogger(FormatoRespuestaPDF.class);
	private static final String DIRECTORIO_DESTINO = "formatosRespuesta/";
	private static final String EXTENSION_PDF = ".pdf";
	private static final String COMPONENTE = "componente_";

	public String generarDocumento(Long idProyecto, String ruta, Map<String, Object> mapRespuestas, TramiteDTO tramiteActual,
			List<DetElementosTokenDTO> lstDetElementosTokenDTO, boolean isHabilitarFirmado,
			ArchivosRespuestaTokenDTO archivoRespuesta, String urlSistema, TramiteFirmaElectronicaDTO firmadoDTO,
			Map<String, ControlComponentesDTO> mapControlComponentesRespuestas, List<SeccionesFormularioDTO> lstSeccionesTramiteDTO)
			throws InterpreteException {
		
		String pathDocumento = null;
		PdfReader pdfReader = null;
		FileOutputStream fos = null;
		PdfStamper pdfStamper = null;
		URL urlFormatoRespuesta = null;
		try {
			urlFormatoRespuesta = new URL(
					ruta.replace(Environment.getPathFileServerMotor(), Environment.getUrlFileServerMotor()));
			File fileRutaDestino = new File(Environment.getPathPlantillasClientePdf() + idProyecto +"/" + DIRECTORIO_DESTINO);
			if (!fileRutaDestino.exists()) {
				fileRutaDestino.mkdirs();
			}
			pdfReader = new PdfReader(urlFormatoRespuesta);
			pathDocumento = Environment.getPathPlantillasClientePdf() + idProyecto +"/" + DIRECTORIO_DESTINO
					+ tramiteActual.getFolioSeguimiento() + EXTENSION_PDF;
			fos = new FileOutputStream(pathDocumento);
			pdfStamper = new PdfStamper(pdfReader, fos);
			AcroFields form = pdfStamper.getAcroFields();
			for (int i = 0; i < lstDetElementosTokenDTO.size(); i++) {
				switch (lstDetElementosTokenDTO.get(i).getCatOrigenTokenDTO().getIdOrigenToken()) {
				case Constantes.TOKEN_FOLIO_SISTEMA:
					// Origen folio
					form.setField(lstDetElementosTokenDTO.get(i).getNombreToken(), tramiteActual.getFolioSeguimiento());
					break;
				case Constantes.TOKEN_FECHA_ACTUAL:
					// Origen fecha
					String stringDate = "-Formato fecha no válido-";
					try {
						Date date = new Date();
						SimpleDateFormat DateFor = new SimpleDateFormat(
								lstDetElementosTokenDTO.get(i).getCampoPersonalizado().replace("DD", "dd"));
						stringDate = DateFor.format(date);
				    } catch (Exception e){
				    	LOGGER.error("El campo personalizado de fecha no es válido:: " + lstDetElementosTokenDTO.get(i).getCampoPersonalizado(), e);
				    }					
					form.setField(lstDetElementosTokenDTO.get(i).getNombreToken(), stringDate);
					break;
				case Constantes.TOKEN_COMPONENTE:
					String respuestaToken = Constantes.DATO_NO_CAPTURADO;
					String nombreComponente = COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString());
					ControlComponentesDTO controlComponente = new ControlComponentesDTO();
					
					controlComponente = mapControlComponentesRespuestas.get(nombreComponente);
					/**Si no encuentra el componente con el nombre base, quiere decir que es un componente con más de 1 posible respuesta
					 * Por lo tanto se busca por su primer elemento**/
					if(BeanUtils.isNull(controlComponente)) {
						controlComponente = mapControlComponentesRespuestas.get(nombreComponente.concat("_1"));	
					}
					/**Revisar el tipo de componente, para determinar si tiene 1 o varias posibles respuestas**/						 
					if(controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_CAMPO_TEXTO 
							|| controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_FECHA) {
						
						/**Si se cuenta con una respuesta para el componente se coloca en su respectivo token**/
						if(mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString())) != null) {
							if(controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_FECHA) {
								respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString())).toString().substring(0, 10);
							} else {
								respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString())).toString();	
							}	
						} 
						
					} else if(controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_MENU_DESPLEGABLE
							|| controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_RADIO_BOTON
							|| controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_CHECKBOX_UNICO
							|| controlComponente.getIdTipoComponente()== Constantes.ID_COMPONENTE_DATOS_DOMICILIO
							|| controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONA_MORAL
							|| controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONALES_CON_LLAVE
							|| controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONALES_SIN_LLAVE) {
						
						boolean componenteEncontrado = false;
						for(SeccionesFormularioDTO seccionTemp :lstSeccionesTramiteDTO) {
							for(SubSeccionesFormularioDTO subSeccionTemp : seccionTemp.getLstSubsecciones()) {
								for(ComponenteDTO componente : subSeccionTemp.getLstComponentes()) {
									if(componente.getIdComponente().longValue() == lstDetElementosTokenDTO.get(i).getIdComponente().longValue()) {
										
										if(BeanUtils.isNotNull(componente) && 
												componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_CHECKBOX_UNICO) {
											
											ComponenteCheckboxUnicoDTO checkUnico = (ComponenteCheckboxUnicoDTO) componente;												
											boolean checkSeleccionado = (boolean) mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()));
											
											if(checkSeleccionado) {
												respuestaToken = checkUnico.getTexto().replaceAll("<[^>]*>", "");
												componenteEncontrado = true;
												break;
											} 
										} 
										
										/** Considerar que es posible que no tenga respuesta, esto por no ser un componente obligatorio **/
										if (BeanUtils.isNotNull(componente) &&
												componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_MENU_DESPLEGABLE &&
												mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString())) != null) {
											
											ComponenteMenuDesplegableDTO menuDesplegable = (ComponenteMenuDesplegableDTO) componente;
											/**Se obtienen los elementos del componente menú**/
											List<DetElementosMenuDTO> detElementosMenuDTO = menuDesplegable.getDetElementosMenuDTO();
											for(DetElementosMenuDTO opcionMenu: detElementosMenuDTO) {
												
												/**Se revisa la respuesta seleccionada por el usuario, para colocar la respuesta en token**/
												int respuestaMenu = (int) mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()));

												if(BeanUtils.isNotNull(respuestaMenu) && respuestaMenu == opcionMenu.getIdElementoMenu()) {
													respuestaToken = opcionMenu.getDescripcionElemento();
													componenteEncontrado = true;
													break;
												}
											}
										}
										
										if (BeanUtils.isNotNull(componente) && 
												componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_RADIO_BOTON) {
											
											ComponenteRadiobotonDTO radioButon = (ComponenteRadiobotonDTO) componente;
											
											/**Se valida si el campo "Otra opción" tiene respuesta**/
											if(mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_3")) != null) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_3")).toString();
												componenteEncontrado = true;
												break;
											/**Se valida si el campo "Registra opción del listado" no es vacía**/
											} else if(mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_1")) != null) {
												String respuestaListado = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_1")).toString();													
												if(BeanUtils.isNotNull(respuestaListado)) {
													/**Se obtienen los elementos del componente radio botón**/
													List<DetElementosRadiobotonDTO> detElementosRadiobotonsDTO = radioButon.getDetElementosRadiobotonsDTO();
													
													for(DetElementosRadiobotonDTO opcionRadio: detElementosRadiobotonsDTO) {
														/**Se revisa la respuesta seleccionada por el usuario, para colocar la respuesta en token**/
														int idRespuestaRadio = Integer.parseInt(respuestaListado);
														if(idRespuestaRadio == opcionRadio.getIdElementoRadioboton()) {
															respuestaToken = opcionRadio.getDescripcionElemento();
															componenteEncontrado = true;
															break;
														}
													}														
												} 													
											}
										}
										if (BeanUtils.isNotNull(componente) && componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONALES_CON_LLAVE) {
											ComponenteDatosPersonalesLlaveDTO datosLlave = (ComponenteDatosPersonalesLlaveDTO) componente;
											Long idAtributo = lstDetElementosTokenDTO.get(i).getCatAtributosComponentesDTO().getIdAtributoComponente();
											if (datosLlave.isHabilitaCurp() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_1")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_CURP_CON_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_1")).toString();
												componenteEncontrado = true;
											}
											if (datosLlave.isHabilitaNombre() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_2")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_NOMBRE_CON_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_2")).toString();
												componenteEncontrado = true;
											} 
											if (datosLlave.isHabilitaPrimerApellido() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_3")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_PRIM_APELLIDO_CON_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_3")).toString();
												componenteEncontrado = true;
											} 
											if (datosLlave.isHabilitaSegundoApellido() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_4")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_SEG_APELLIDO_CON_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_4")).toString();
												componenteEncontrado = true;
											}
											if (datosLlave.isHabilitaTelefono() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_5")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_TEL_CON_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_5")).toString();
												componenteEncontrado = true;
											} 
											if (datosLlave.isHabilitaCorreoElectronico() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_6")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_CORREO_CON_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_6")).toString();
												componenteEncontrado = true;
											}
											if (datosLlave.isHabilitaFechaNacimiento() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_7")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_FECHA_CON_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_7")).toString();
												componenteEncontrado = true;
											} 
											if (datosLlave.isHabilitaSexo() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_8")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_SEXO_CON_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_8")).toString();
												componenteEncontrado = true;
											} 
										}
										
										if (BeanUtils.isNotNull(componente) && componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONALES_SIN_LLAVE) {
											ComponenteDatosPersonalesDTO datosSinLlave = (ComponenteDatosPersonalesDTO) componente;
											Long idAtributo = lstDetElementosTokenDTO.get(i).getCatAtributosComponentesDTO().getIdAtributoComponente();
											
											if (datosSinLlave.isHabilitaCurp() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_1")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_CURP_SIN_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_1")).toString();
												componenteEncontrado = true;
											} 
											if (datosSinLlave.isHabilitaNombre() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_2")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_NOMBRE_SIN_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_2")).toString();
												componenteEncontrado = true;
											}
											if (datosSinLlave.isHabilitaPrimerApellido() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_3")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_PRIM_APELLIDO_SIN_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_3")).toString();
												componenteEncontrado = true;
											} 
											if (datosSinLlave.isHabilitaSegundoApellido() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_4")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_SEG_APELLIDO_SIN_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_4")).toString();
												componenteEncontrado = true;
											} 
											if (datosSinLlave.isHabilitaTelefono() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_5")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_TEL_SIN_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_5")).toString();
												componenteEncontrado = true;
											}
											if (datosSinLlave.isHabilitaCorreoElectronico() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_6")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_CORREO_SIN_LLAVE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_6")).toString();
												componenteEncontrado = true;
											} 
										}
										
										if (BeanUtils.isNotNull(componente) && componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_PERSONA_MORAL) {
											ComponenteDatosPersonaMoralDTO datosMoral = (ComponenteDatosPersonaMoralDTO) componente;
											Long idAtributo = lstDetElementosTokenDTO.get(i).getCatAtributosComponentesDTO().getIdAtributoComponente();
											if (datosMoral.isHabilitaRfc() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_1")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_RFC) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_1")).toString();
												componenteEncontrado = true;
											}
											if (datosMoral.isHabilitaPersonaMoral() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_2")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_PERS_MORAL) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_2")).toString();
												componenteEncontrado = true;
											} 
											if (datosMoral.isHabilitaFechaVigencia() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_3")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_FECHA_VIGENCIA) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_3")).toString();
												componenteEncontrado = true;
											} 											
										}
										
										if (BeanUtils.isNotNull(componente) && componente.getCatTipoComponenteDTO().getIdTipoComponente() == Constantes.ID_COMPONENTE_DATOS_DOMICILIO) {
											ComponenteDatosDomicilioDTO datosDomicilio = (ComponenteDatosDomicilioDTO) componente;
											Long idAtributo = lstDetElementosTokenDTO.get(i).getCatAtributosComponentesDTO().getIdAtributoComponente();
											
											if (datosDomicilio.isHabilitaCalle() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_1")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_CALLE) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_1")).toString();
												componenteEncontrado = true;
											} 
											if (datosDomicilio.isHabilitaNumeroExterior() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_2")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_NUM_EXT) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_2")).toString();
												componenteEncontrado = true;
											} 
											if (datosDomicilio.isHabilitaNumeroInterior() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_3")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_NUM_INT) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_3")).toString();
												componenteEncontrado = true;
											} 
											if (datosDomicilio.isHabilitaCodigoPostal() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_4")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_COD_POST) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_4")).toString();
												componenteEncontrado = true;
											} 
											if (datosDomicilio.isHabilitaEstado() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_5")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_ESTADO) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_5")).toString();
												componenteEncontrado = true;
											} 
											if (datosDomicilio.isHabilitaAlcaldia() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_6")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_MUNICIPIO) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_6")).toString();
												componenteEncontrado = true;
											} 
											if (datosDomicilio.isHabilitaColonia() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_7")) != null && idAtributo != null 
											        && idAtributo.intValue() == Constantes.ID_COLONIA) {
												respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_7")).toString();
												componenteEncontrado = true;
											} 
										}
									}
									if(componenteEncontrado) {
										break;
									}
								}
								if(componenteEncontrado) {
									break;
								}
							}	
							if(componenteEncontrado) {
								break;
							}
						}
					} 						
					form.setField(lstDetElementosTokenDTO.get(i).getNombreToken().trim(), respuestaToken);
					break;
				case Constantes.TOKEN_CADENA_DIGITAL:
					if (BeanUtils.isNotNull(firmadoDTO)) {
						form.setField(lstDetElementosTokenDTO.get(i).getNombreToken(),
								firmadoDTO.getCadenaFirmada() != null ? firmadoDTO.getCadenaFirmada()
										: Constantes.DATO_NO_CAPTURADO);
					}
					break;
				case Constantes.TOKEN_FECHA_FIRMADO:					
					if (BeanUtils.isNotNull(firmadoDTO)) {
						form.setField(lstDetElementosTokenDTO.get(i).getNombreToken(),
								firmadoDTO.getFechaCreacion() != null ? BeanUtils.convertirDateStringDiaMesAnio(firmadoDTO.getFechaCreacion())	
										: Constantes.DATO_NO_CAPTURADO);
					}
					break;
				case Constantes.TOKEN_NOMBRE_FIRMANTE:					
					if (BeanUtils.isNotNull(firmadoDTO)) {
						form.setField(lstDetElementosTokenDTO.get(i).getNombreToken(),
								(firmadoDTO.getNombreFirmante() != null && BeanUtils.isNotEmpty(firmadoDTO.getNombreFirmante())) 
								? firmadoDTO.getNombreFirmante() : Constantes.DATO_NO_CAPTURADO);
					}
					break;
				case Constantes.TOKEN_NOMBRE_REGISTRA:
					form.setField(archivoRespuesta.getLstToken().get(i).getNombreToken(),
							tramiteActual.getUsuario() != null && tramiteActual.getUsuario().getNombreCompleto() !=null ?
							tramiteActual.getUsuario().getNombreCompleto() : Constantes.DATO_NO_CAPTURADO);
					break;
				case Constantes.TOKEN_NOMBRE_PROYECTO:
					form.setField(archivoRespuesta.getLstToken().get(i).getNombreToken(),
							archivoRespuesta.getProyectoDTO().getNombreProyecto());
					break;
				case Constantes.TOKEN_DEPENDENCIA:
					form.setField(archivoRespuesta.getLstToken().get(i).getNombreToken(), 
							archivoRespuesta.getProyectoDTO().getCatDependenciaDTO().getDescripcion() );					
					break;
				case Constantes.TOKEN_ESTATUS_TRAMITE:
					form.setField(archivoRespuesta.getLstToken().get(i).getNombreToken(), 
							archivoRespuesta.getProyectoDTO().isAviso()  ?
									tramiteActual.getCatEstatusTramiteDTO().getDescripcionAviso() :
										tramiteActual.getCatEstatusTramiteDTO().getDescripcion());
					break;
				default:
					LOGGER.info("Token no codificado : {} - {}", 
							archivoRespuesta.getLstToken().get(i).getCatOrigenTokenDTO().getIdOrigenToken(),
							archivoRespuesta.getLstToken().get(i).getCatOrigenTokenDTO().getDescripcion());
					break;
				}
			}
			if (isHabilitarFirmado) {
				Image qrImage = Image.getInstance(GeneradorQRUtil.generarCodigoQR(
						urlSistema + Environment.getUrlConsultaTramite() + "uuid=" + tramiteActual.getUuid()));
				qrImage.setAbsolutePosition(archivoRespuesta.getCoodenadaQrX(), archivoRespuesta.getCoodenadaQrY());
				qrImage.scaleAbsolute(Constantes.TAMANIO_QR_ANCHO, Constantes.TAMANIO_QR_ALTO);
				pdfStamper.getOverContent(1).addImage(qrImage);
			}
		} catch (IOException e) {
			pathDocumento = null;
			LOGGER.error("Ocurrió un error al querer abrir la plantilla: ", e);
			throw new InterpreteException("Ocurrio un error al querer abrir la plantilla");
		} catch (DocumentException e) {
			pathDocumento = null;
			LOGGER.error("Ocurrió un error al crear comprobante: ", e);
			throw new InterpreteException("Ocurrió un error al crear comprobante");
		} finally {
			if (pdfStamper != null) {
				try {
					pdfStamper.close();
				} catch (Exception e) {
					LOGGER.warn("No se pudo cerrar el pdfStamper al crear un documento desde una plantilla:", e);
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (Exception e) {
					LOGGER.warn("No se pudo cerrar el fileOutputStream al crear un documento desde una plantilla:", e);
				}
			}
			if (pdfReader != null) {
				try {
					pdfReader.close();
				} catch (Exception e) {
					LOGGER.warn("No se pudo cerrar el pdfReader al crear un documento desde una plantilla:", e);
				}
			}
		}

		return pathDocumento;
	}
}

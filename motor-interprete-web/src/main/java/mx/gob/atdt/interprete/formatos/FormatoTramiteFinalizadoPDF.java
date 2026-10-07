package mx.gob.atdt.interprete.formatos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.StringJoiner;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import mx.gob.atdt.interprete.common.infra.Environment;
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
import mx.gob.atdt.interprete.dto.UsuarioDTO;
import mx.gob.atdt.interprete.formularios.bean.RegistrarFormularioBean;
import mx.gob.atdt.interprete.util.BeanUtils;

/**
 * La logica de la clase de movio al proyecto EJB
 * para futuras modificaciones hacerlas en el paquete mx.gob.atdt.interprete.common.formatos
 * 16/06/2026
 */
@Deprecated
public class FormatoTramiteFinalizadoPDF {

	private static final Logger LOGGER = LoggerFactory.getLogger(FormatoTramiteFinalizadoPDF.class);
	private static final String PLANTILLA_LLAVE = "plantilla_llave.pdf";
	private static final String PLANTILLA_NO_LLAVE = "plantilla_no_llave.pdf";
	private static final String DIRECTORIO_DESTINO = "formatos/";
	private static final String EXTENSION_PDF = ".pdf";
	private static final String COMPONENTE = "componente_";	
	
	/**
	 *  * Método que revisa dependiendo de la información enviada la plantilla que será utilizada para la generación de la plantilla de 
	 * "Comprobante de registro".
	 * 
	 * @param tramite			DTO con los datos del trámite capturado y registrado.
	 * @param urlSistema		URL del xhtml que se registra en la generación de QR.
	 * @param usuarioTramite	DTO con la información del usuario que genera trámite, si el proyecto no tiene llave para ciudadano, este objeto es null.
	 * @param isAccesoLlave		Bandera que indica si la configuración de llave, tiene habilitado el acceso del ciudadano mediante llave.
	 * @param datosFirmaTramite	DTO con la información de trámite firmado.
	 * @param mapRespuestas		Mapa con las respuestas ingresadas en los componentes del formulario.
	 * @return
	 */
	public String generarDocumento(TramiteDTO tramite, String urlSistema, UsuarioDTO usuarioTramite, boolean isAccesoLlave,  
			ArchivosRespuestaTokenDTO archivosRespuestaTokenDTO, TramiteFirmaElectronicaDTO datosFirmaTramite, 
			Map<String, Object> mapRespuestas, List<DetElementosTokenDTO> lstDetElementosTokenDTO, 
			Map<String, ControlComponentesDTO> mapControlComponentesRespuestas, List<SeccionesFormularioDTO> lstSeccionesTramiteDTO) {
		
		boolean utilizaPlantillaPersonalizada = false;
		boolean plantillaConFirma = false;
		
		String pathDocumento = null;
		PdfReader pdfReader = null;
		FileOutputStream fos = null;
		PdfStamper pdfStamper = null;
		URL urlPlantillaRegistro = null;
		
		try {
			File filePlantilla = null;
						
			/**
			 * Se revisa si la configuración del detalle de llave, tiene habilitado el acceso para que el ciudadano ingrese mediante llave.
			 * 	1.- Si el ciudadano si ingresa con acceso llave, entonces se toma la plantilla de datos con llave, porque se puede recuperar su nombre de usuario.
			 * 	2.- Si el ciudadano no ingresa con acceso llave, entonces se debe tomar la plantilla que no tiene datos de llave, porque no se puede recuperar el 
			 * 		nombre de usuario.
			 * 	3.- Se agrega un nuevo escenario, que depende también de que el ciudadano ingrese con acceso llave, pero además contenga en el detalle firma
			 * 		la marca de que el ciudadano puede firmar su trámite en el proceso de captura, para este caso se tendrá una plantilla de tipo "4.- Plantilla 
			 * 		de registro" con la marca de "habilita_firma = true", para este caso, la plantilla se obtiene en el filesystem del motor de manera similar que 
			 * 		como se recupera la plantilla para Aceptación y Rechazo que son utilizadas en la validación de trámite. 
			 */
			if (isAccesoLlave) {
				
				/**Se cuenta con una plantilla de tipo Comprobante de registro personalizada en el motor**/
				if(BeanUtils.isNotNull(archivosRespuestaTokenDTO)) {
					utilizaPlantillaPersonalizada = true;
					
					if(BeanUtils.isNotNull(datosFirmaTramite) && archivosRespuestaTokenDTO.isHabilitaFirma()) {
						plantillaConFirma = true;	
					}					
					
				} else {
					/**Si no se configuró una plantilla de registro, se toma la plantilla default con datos de llave**/
					filePlantilla = new File(Environment.getPathPlantillasClientePdf() + PLANTILLA_LLAVE);
				}
				
			} else {
				//No cuenta con acceso mediante llave
				filePlantilla = new File(Environment.getPathPlantillasClientePdf() + PLANTILLA_NO_LLAVE);
			}						
			
			File fileRutaDestino = new File(Environment.getPathPlantillasClientePdf() + DIRECTORIO_DESTINO);

			if(utilizaPlantillaPersonalizada) {
				filePlantilla = new File(Environment.getPathPlantillasClientePdf() + tramite.getProyectoDTO().getIdProyecto() 
						+ Constantes.SEPARADOR_RUTA + DIRECTORIO_DESTINO);
			}
						
			if (!filePlantilla.exists()) {
				LOGGER.info("La plantilla o el directorio no existen o no se han creado: {}", filePlantilla);
			}
			if (!fileRutaDestino.exists()) {
				fileRutaDestino.mkdirs();
			}
						
			/**Se revisa si se debe tomar una plantilla de registro personalizada desde el motor, o alguna de las plantillas default que se tienen para registro**/
			if(utilizaPlantillaPersonalizada) {				
				/**Se contruye URL con la ruta física en la que se encuentra la plantilla de Registro configurada en el motor**/
				urlPlantillaRegistro = new URL(
						archivosRespuestaTokenDTO.getRutaArchivoRespuesta().replace
						(Environment.getPathFileServerMotor(), Environment.getUrlFileServerMotor()));
				
				pdfReader = new PdfReader(urlPlantillaRegistro);
				pathDocumento = Environment.getPathPlantillasClientePdf() + DIRECTORIO_DESTINO + (tramite.getUuid().substring(0, 14)) + tramite.getIdTramite() + EXTENSION_PDF;
				fos = new FileOutputStream(pathDocumento);
				pdfStamper = new PdfStamper(pdfReader, fos);
				AcroFields form = pdfStamper.getAcroFields();

				for (int i = 0; i < archivosRespuestaTokenDTO.getLstToken().size(); i++) {

					switch (archivosRespuestaTokenDTO.getLstToken().get(i).getCatOrigenTokenDTO().getIdOrigenToken()) {
					
					case Constantes.TOKEN_FOLIO_SISTEMA:
						// Origen folio
						form.setField(archivosRespuestaTokenDTO.getLstToken().get(i).getNombreToken(), tramite.getFolioSeguimiento());
						break;
					case Constantes.TOKEN_FECHA_ACTUAL:
						form.setField(archivosRespuestaTokenDTO.getLstToken().get(i).getNombreToken(), convertirFecha(archivosRespuestaTokenDTO.getLstToken().get(i).getCampoPersonalizado()));
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
								|| controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_FECHA
								|| controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_AREA_TEXTO
								|| controlComponente.getIdTipoComponente() == Constantes.ID_COMPONENTE_CHECKBOX_GRUPO) {
							
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
												
												int tipoCampoPdf = form.getFieldType(lstDetElementosTokenDTO.get(i).getNombreToken().trim());
												
												ComponenteCheckboxUnicoDTO checkUnico = (ComponenteCheckboxUnicoDTO) componente;												
												boolean checkSeleccionado = (boolean) mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()));

												if(tipoCampoPdf == AcroFields.FIELD_TYPE_CHECKBOX
												        || tipoCampoPdf == AcroFields.FIELD_TYPE_RADIOBUTTON ) {
													
													respuestaToken = checkSeleccionado ? "Yes" : "Off";
													
												}  else {
												    	respuestaToken = checkSeleccionado ?  checkUnico.getTexto().replaceAll("<[^>]*>", "") : "";
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
												if (datosLlave.isHabilitaCorreoElectronico() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_7")) != null && idAtributo != null 
												        && idAtributo.intValue() == Constantes.ID_FECHA_CON_LLAVE) {
													respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_7")).toString();
													componenteEncontrado = true;
												} 
												if (datosLlave.isHabilitaCorreoElectronico() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_8")) != null && idAtributo != null 
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
											
												if (idAtributo.intValue() == Constantes.ID_COMPLETO) {
													StringJoiner direccion = new StringJoiner(", ");
													
													for (int j = 1; j <= 7; j++) {
														String llave = COMPONENTE
													            .concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString())
													            .concat("_").concat(String.valueOf(j));

														Object valor = mapRespuestas.get(llave);
														
														if (valor != null && !valor.toString().trim().isEmpty()) {
															 direccion.add(valor.toString());
														 }
													}
													
													respuestaToken = direccion.toString();
													componenteEncontrado = true;
												}
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
												if (datosDomicilio.isHabilitaEstado() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_7")) != null && idAtributo != null 
												        && idAtributo.intValue() == Constantes.ID_ESTADO) {
													respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_7")).toString();
													componenteEncontrado = true;
												} 
												if (datosDomicilio.isHabilitaAlcaldia() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_6")) != null && idAtributo != null 
												        && idAtributo.intValue() == Constantes.ID_MUNICIPIO) {
													respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_6")).toString();
													componenteEncontrado = true;
												} 
												if (datosDomicilio.isHabilitaColonia() && mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_5")) != null && idAtributo != null 
												        && idAtributo.intValue() == Constantes.ID_COLONIA) {
													respuestaToken = mapRespuestas.get(COMPONENTE.concat(lstDetElementosTokenDTO.get(i).getIdComponente().toString()).concat("_5")).toString();
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
						if (plantillaConFirma) {
							form.setField(archivosRespuestaTokenDTO.getLstToken().get(i).getNombreToken(),
									datosFirmaTramite.getCadenaFirmada() != null ? datosFirmaTramite.getCadenaFirmada()
											: Constantes.DATO_NO_CAPTURADO);
						}
						break;
					case Constantes.TOKEN_FECHA_FIRMADO:					
						if (plantillaConFirma) {
							form.setField(archivosRespuestaTokenDTO.getLstToken().get(i).getNombreToken(),
									datosFirmaTramite.getFechaCreacion() != null ? BeanUtils.convertirDateStringDiaMesAnio(datosFirmaTramite.getFechaCreacion())	
											: Constantes.DATO_NO_CAPTURADO);
						}
						break;
					case Constantes.TOKEN_NOMBRE_FIRMANTE:
						if (plantillaConFirma) {
							form.setField(archivosRespuestaTokenDTO.getLstToken().get(i).getNombreToken(),
									(datosFirmaTramite.getNombreFirmante() != null && BeanUtils.isNotEmpty(datosFirmaTramite.getNombreFirmante())) 
									? datosFirmaTramite.getNombreFirmante() : Constantes.DATO_NO_CAPTURADO);
						}
						break;
					case Constantes.TOKEN_NOMBRE_REGISTRA:
						form.setField(archivosRespuestaTokenDTO.getLstToken().get(i).getNombreToken(),
								usuarioTramite != null && usuarioTramite.getNombreCompleto() !=null ?
								usuarioTramite.getNombreCompleto() : Constantes.DATO_NO_CAPTURADO);
						break;
					case Constantes.TOKEN_NOMBRE_PROYECTO:
						form.setField(archivosRespuestaTokenDTO.getLstToken().get(i).getNombreToken(),
								archivosRespuestaTokenDTO.getProyectoDTO().getNombreProyecto());
						break;
					case Constantes.TOKEN_DEPENDENCIA:
						form.setField(archivosRespuestaTokenDTO.getLstToken().get(i).getNombreToken(), 
								archivosRespuestaTokenDTO.getProyectoDTO().getCatDependenciaDTO().getDescripcion() );					
						break;
					case Constantes.TOKEN_ESTATUS_TRAMITE:
						form.setField(archivosRespuestaTokenDTO.getLstToken().get(i).getNombreToken(), 
								archivosRespuestaTokenDTO.getProyectoDTO().isAviso()  ?
										tramite.getCatEstatusTramiteDTO().getDescripcionAviso() :
										tramite.getCatEstatusTramiteDTO().getDescripcion());
						break;
					default:
							LOGGER.info("Token no codificado : {} - {}", 
									archivosRespuestaTokenDTO.getLstToken().get(i).getCatOrigenTokenDTO().getIdOrigenToken(),
									archivosRespuestaTokenDTO.getLstToken().get(i).getCatOrigenTokenDTO().getDescripcion());
							break;
					}
				}
				
			} else {
				/**Se toman plantillas default y se coloca la caída de token a los token fijos que se tienen en las plantillas predefinidas*/
				
				pdfReader = new PdfReader(filePlantilla.getAbsolutePath());
				pathDocumento = Environment.getPathPlantillasClientePdf() + DIRECTORIO_DESTINO + (tramite.getUuid().substring(0, 14)) + tramite.getIdTramite() + EXTENSION_PDF;
				fos = new FileOutputStream(pathDocumento);
				pdfStamper = new PdfStamper(pdfReader, fos);

				AcroFields form = pdfStamper.getAcroFields();

				String fecha = BeanUtils.convertirStringDateMx(tramite.getFechaCreacion());

				form.setField("PROYECTO", tramite.getProyectoDTO().getNombreProyecto());
				form.setField("FOLIO", tramite.getFolioSeguimiento());
				if (isAccesoLlave) {
					form.setField("NOMBRE", usuarioTramite.getNombre() + " " + usuarioTramite.getPrimerApellido() + " "
							+ usuarioTramite.getSegundoApellido());
				}
				form.setField("FECHA", fecha);
				form.setField("ESTATUS", tramite.getCatEstatusTramiteDTO().getDescripcion());
				form.setField("DEPENDENCIA", tramite.getProyectoDTO().getCatDependenciaDTO().getDescripcion());
			}			
			//SE COMENTA GENERACION DE QR EN PLANTILLA DE REGISTRO
//			Image qrImage = Image.getInstance(GeneradorQRUtil
//					.generarCodigoQR(urlSistema + Environment.getUrlConsultaTramite() + "uuid=" + tramite.getUuid()));
//			if (isAccesoLlave) {
//				qrImage.setAbsolutePosition(390, 380);	
//			} else {
//				qrImage.setAbsolutePosition(390, 340);
//			}			
//			qrImage.scaleAbsolute(215, 205);
//			pdfStamper.getOverContent(1).addImage(qrImage);

		} catch (IOException e) {
			pathDocumento = null;
			LOGGER.error("Ocurrio un error al querer abrir la plantilla: ", e);
		} catch (DocumentException e) {
			pathDocumento = null;
			LOGGER.error("Ocurrio un error al querer crear el documento del acta digital: ", e);
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
	
	/**
	 * Método auxiliar que realiza la conversión de una fecha al formato requerido
	 * @return
	 */
	private String convertirFecha(String strFecha) {
		String stringDate = "-Formato fecha no válido-";
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(strFecha.replace("DD", "dd"));
			stringDate = sdf.format(date);
	    } catch (Exception e){
	    	LOGGER.error("El campo personalizado de fecha no es válido:: {} ", strFecha, e);
	    }
		
		return stringDate;
	}
	
    /**
     * Función que convierte un Date a String con formato dd/MM/yyyy
     *
     */    
    public String convertirDateString(String strFecha) {
    	
    	String stringDate = "-Formato fecha no válido-";
    	try {
    		if(strFecha != null) {
    			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    			Date fecha = null;            
                fecha = sdf.parse(strFecha);
                stringDate = sdf.format(fecha);
    		} 
    	} catch (ParseException e) {
            LOGGER.error("currió un error al convertir fecha ", e);
            return stringDate;
        }
    	return stringDate;
    }    
}

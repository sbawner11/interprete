package mx.gob.atdt.interprete.commons.utils;

public final class Constantes {
	
	/**
	 * Constructor por defecto de la clase
	 */
	private Constantes() {
		/** Constructor vacío para que no se pueda instanciar la clase **/
	}
	
	//Constantes utilizadas en el acceso de Llave
	public static final String CONTENT_TYPE = "application/json;charset=utf-8";
	public static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
	public final static String STATE_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-";
	public static final String PARAM_CLIENT_ID = "client_id";
	public static final String PARAM_REDIRECT = "redirect_url";
	public static final String PARAM_CODE = "code";
	public static final String PARAM_STATE = "state";
	
    public static final String TOKEN_SESSION = "token";
    
    //Constantes WS rolesUsuario
    public static final int CODE_RESPUESTA_ROLES_USUARIO_SUCCESS = 201;
    public static final int CODE_RESPUESTA_ROLES_USUARIO_SIN_ROLES = 401;
    public static final int CODE_RESPUESTA_ROLES_USUARIO_ERROR_SERVER = 501;
    public static final String PARAM_ROLES_USUARIO_CODE = "code";
    public static final String PARAM_ROLES_USUARIO_ROLES = "roles";
    public static final String PARAM_ROLES_USUARIO_MENSAJE = "Mensaje";
    public static final String PARAM_ROLES_USUARIO_CODE_RESPONSE = "codeResponse";
	
	//Constantes de uso general
    public static final String SEPARADOR = System.getProperty("file.separator");
	public static final String SEPARADOR_RUTA = "/";
	public static final String SEPARADOR_FIRMADO = "|";
    public static final String RETURN_SAME_PAGE = "";    
    public static final String JSF_REDIRECT = "?faces-redirect=true";
    public static final String RETURN_HOME_PAGE = "/home.xhtml";
    public static final String RETURN_WELCOME_PAGE = "/welcome.xhtml";
    public static final String RETURN_INDEX_PAGE = "/index.xhtml";
    public static final String RETURN_INGRESAR_BACKOFFICE_PAGE = "/public/backoffice/ingresar.xhtml";
    
    //Ruta para consulta de trámites desde expediente
    public static final String RETURN_LOGIN_EXPEDIENTE_PAGE = "/public/LoginExpediente.xhtml";
    public static final String URL_BANDEJA_EXPEDIENTE ="public/bandejaTramites.xhtml?idSistema=";
    
    //Rutas avisos de privacidad
    public static final String RETURN_AVISO_SIMPLIFICADO_PAGE = "/public/AvisoPrivacidad.xhtml";
    public static final String RETURN_AVISO_INTEGRAL_PAGE = "/public/AvisoIntegral.xhtml";
    
    public static final String RETURN_BANDEJA_TRAMITES_PAGE = "/protected/bandejas/BandejaTramites.xhtml";
    public static final String RETURN_BANDEJA_VALIDACION_TRAMITES_PAGE = "/protected/bandejas/BandejaValidacionTramites.xhtml";
    public static final String RETURN_FORMULARIO_PAGE = "/protected/formularios/Formulario.xhtml";
    public static final String FORMULARIO_PAGE_GENERAR_FORMULARIO = "FORMULARIO_PAGE_GENERAR_FORMULARIO";
    public static final String FORMULARIO_PAGE_REGISTRAR_FORMULARIO = "FORMULARIO_PAGE_REGISTRAR_FORMULARIO";
    
    public static final String RETURN_FIN_FORMULARIO_PAGE = "/protected/formularios/SolicitudFinalizada.xhtml";
    
    public static final String RETURN_PERSONAS_MORALES_PAGE = "/protected/personasMorales/PersonasMorales.xhtml";
    
	//Rutas de la administración del perfil de usuario
    public static final String RETURN_ADMIN_PERFIL_PAGE = "/protected/PerfilUsuario.xhtml"; 
    
    //Ruta consulta estatus trámite
    public static final String RETURN_ESTATUS_TRAMITE = "/public/EstatusTramite.xhtml"; 
    
    public static final String RETURN_REINICIO_CLIENTE = "/protected/backoffice/ReinicioCliente.xhtml";
    
    public static final String RETURN_SOLICITAR_SINCRONIZACION = "/protected/backoffice/SolicitarSincronizacion.xhtml";
    
    public static final String RETURN_PERMISOS_BD = "/protected/backoffice/AsignarPermisosBD.xhtml";
    
    public static final String URL_ROL_INVALIDO = "/rolInvalido.xhtml";
    
    public static final String URL_USUARIO_NO_AUTORIZADO = "/noAutorizado.xhtml";
    
    public static final String URL_SIN_DISTRIBUCION = "/sinDistribucion.xhtml";
    
    public static final String URL_ASIGNAR_DISTRIBUCION = "/protected/backoffice/AsignarSolicitudes.xhtml";
    public static final String URL_NUEVA_CARGA_MASIVA_DISTRIBUCION = "/protected/backoffice/NuevaCargaMasivaDistribucion.xhtml";

    public static final int ID_ESTATUS_CARGA_MASIVA_CORRECTA = 1;
    public static final int ID_ESTATUS_CARGA_MASIVA_ERROR_ARCHIVO = 2;
    public static final int ID_ESTATUS_CARGA_MASIVA_EN_PROCESO = 3;
    public static final int ID_ESTATUS_CARGA_MASIVA_PARCIAL = 4;
    public static final int MAXIMO_REGISTROS_CARGA_MASIVA_DISTRIBUCION = 500;
    public static final int MAX_LONGITUD_DESCRIPCION_CARGA_MASIVA = 200;
    public static final int MAX_LONGITUD_RESULTADO_CARGA_MASIVA = 255;
    public static final int MAX_LONGITUD_MENSAJE_ERROR_CARGA_MASIVA = 500;
    public static final int MAX_LONGITUD_ERROR_ASIGNACION_CARGA_MASIVA = 200;
    public static final int COLUMNAS_CARGA_MASIVA_DISTRIBUCION = 2;
    public static final int ENCABEZADO_CSV_VALIDO = 0;
    public static final int ENCABEZADO_CSV_SIN_COLUMNAS = 1;
    public static final int ENCABEZADO_CSV_ID_INVALIDO = 2;
    public static final String TOKEN_ID_ENCABEZADO_CARGA_MASIVA = "id";
    public static final String COLUMNA_ID_ELEMENTO_CARGA_MASIVA = "Id elemento";
    public static final String COLUMNA_ELEMENTOS_CARGA_MASIVA_PREFIJO = "Elementos ";
    public static final String MENSAJE_ENCABEZADO_CSV_SIN_COLUMNAS =
    		"Deben existir encabezados de Id elemento y descripción del elemento.";
    public static final String MENSAJE_ENCABEZADO_CSV_ID_INVALIDO =
    		"El encabezado de la primera columna es inválido porque no hace referencia al Id del elemento.";
    public static final String RESULTADO_DISTRIBUCION_EXITOSA = "Distribución exitosa";
    public static final String RESULTADO_ELEMENTO_NO_REGISTRADO = "ELEMENTO no registrado en componente de DISTRIBUCIÓN";
    public static final String RESULTADO_ELEMENTO_EN_OTRA_DISTRIBUCION = "ELEMENTO registrado en otra DISTRIBUCIÓN";
    public static final String RESULTADO_ID_ELEMENTO_VACIO = "Id elemento vacío";
    public static final String RESULTADO_ID_ELEMENTO_INVALIDO = "Id elemento con formato inválido: ";
    public static final String RESULTADO_DESCRIPCION_MUY_LARGA = "Descripción del registro muy larga";
    public static final String RESULTADO_ELEMENTO_CON_DESCRIPCION_DIFERENTE = "Descripción del elemento cargado diferente a la descripción original";
    public static final String CARPETA_CARGA_MASIVA_DISTRIBUCION = "carga_masiva_distribucion";
    
    public static final String URL_ASIGNACION_MASIVA = "/protected/backoffice/AsignacionMasivaSolicitudes.xhtml?faces-redirect=true";
    
    
    //Ruta para ingresar al dashboard del proyecto
    public static final String RETURN_DASHBOARD_PAGE = "/protected/backoffice/Dashboard.xhtml";
    
//    public static final int INT_ID_ROL_ADMINISTRADOR = 374;
//    public static final int INT_ID_ROL_CAPTURISTA = 367;
    
    public static final String DESC_ROL_ADMINISTRADOR = "Administrador";
    public static final String DESC_ROL_ADMINISTRADOR_GENERAL = "Administrador general"; 
    public static final String DESC_ROL_ADMINISTRADOR_DATOS_TECNICOS = "Administrador datos tecnicos";
    public static final String DESC_ROL_SUPERVISOR = "Supervisor";
    public static final String DESC_ROL_OPERADOR = "Operador";
    public static final String DESC_ROL_CONSULTA = "Consulta";
    
    public static final int ESTADO_DEFAULT_DISTRIBUCION = 9;    
	
    public static final int FIRST_INDEX_LIST = 0;
    public static final int INT_VALOR_CERO = 0;
    public static final int INT_MAX_HORA_DIA = 23;
    public static final int INT_MAX_MINUTO_SEGUNDO_HORA = 59;
	public static final int SIZE_ARRAY_EMPTY = 0;
	public static final String EMPTY_STRING = "";
    public static final String ESPACIO = " ";
    public static final int COMBO_OPCION_SELECCIONAR = 0;
    
    public static final Object OBJETO_NULO = null;
    public static final Object NULL = null;
    public static final String STRING_NULO = null;
            
    public static final int MAX_RESULT = 50;
    public static final int TAMAÑO_BUFFER = 1024;
    
    public static final String FALSE= "false";
    
    //IDENTIFICADORES DE COMPONENTES
    public static final int ID_COMPONENTE_CAMPO_TEXTO = 1;
    public static final int ID_COMPONENTE_CHECKBOX_UNICO = 2;
    public static final int ID_COMPONENTE_CHECKBOX_GRUPO = 3;
    public static final int ID_COMPONENTE_FECHA = 4;
    public static final int ID_COMPONENTE_RADIO_BOTON = 5;
    public static final int ID_COMPONENTE_MENU_DESPLEGABLE = 6;
    public static final int ID_COMPONENTE_CARGA_DOCUMENTOS = 7;
    public static final int ID_COMPONENTE_IMAGEN = 8;
    public static final int ID_COMPONENTE_VIDEO = 9;
   
    public static final int ID_COMPONENTE_DATOS_DOMICILIO = 10;
    public static final int ID_COMPONENTE_DATOS_PERSONALES_SIN_LLAVE = 11;
    public static final int ID_COMPONENTE_DATOS_PERSONALES_CON_LLAVE = 12;
    public static final int ID_COMPONENTE_INFORMATIVO = 13;
    public static final int ID_COMPONENTE_DATOS_PERSONA_MORAL = 14;
    public static final int ID_COMPONENTE_AREA_TEXTO = 15;
    public static final int ID_COMPONENTE_TABLA = 16;
    
    //Identificadores del estado del Sistema en general
    public static final int ID_ESTADO_SISTEMA_LINEA = 1;
    public static final int ID_ESTADO_SISTEMA_MANTENIMIENTO = 2;
    public static final int ID_ESTADO_SISTEMA_EN_SINCRONIZACION = 3;
    public static final int ID_ESTADO_SISTEMA_SINCRONIZADO = 4; 
    public static final int ID_ESTADO_SISTEMA_PAUSADO = 5;
    public static final int ID_ESTADO_SISTEMA_FINALIZADO = 6;
    
    //Objetos de BD del intérprete
    public static final String PERSISTENTE_NAME = "interprete";
    public static final String ESQUEMA_INTERPRETE = "motor_interprete";
    public static final String NOMBRE_BASE_TABLAS = "seccion_formulario_";
    public static final String NOMBRE_BASE_TABLA_TRAMITES = "tramites";
    public static final String NOMBRE_BASE_TABLA_ESTATUS_TRAMITE = "cat_estatus_tramite";
    public static final String NOMBRE_BASE_TABLA_USUARIO = "usuario";
    public static final String NOMBRE_BASE_TABLA_BITACORA_CAMBIOS_ESTATUS = "bit_revertir_estatus";
    public static final String NOMBRE_BASE_TABLA_TRAMITES_FIRMA = "tramite_firma_electronica";
    public static final String NOMBRE_TABLA_COMPONENTES = "componente";
    public static final String NOMBRE_BASE_SECUENCIAS = "_seq";
    public static final String NOMBRE_BASE_COLUMNAS = "componente_";
    public static final String NOMBRE_CAMPO_UNO_COMPONENTE_COMPLEJO = "_1";
    public static final String NOMBRE_BASE_TABLA_CONTROL = "control_componentes";
    public static final String NOMBRE_BASE_TABLA_PROYECTO = "proyecto";
    public static final String NOMBRE_BASE_TABLA_ESTADO_SISTEMA = "det_estado_sistema";    
    public static final String NOMBRE_BASE_TABLA_BIT_ASIGNACION = "bit_asignacion_revisor_tramite";
    public static final String NOMBRE_FUNCION_PERMISOS_USUARIO = "otorgar_permisos";
    public static final String QUERY_INICIO_VALOR_SENTENCIA = "('";
    public static final String QUERY_FIN_VALOR_SENTENCIA = "');";
    
    public static final String NOMBRE_COLUMNA_RUTA_DOC_REVOCADO = "ruta_documento_revocado";
    public static final String NOMBRE_COLUMNA_MOTIVO_RECHAZO = "motivo_rechazo";
    
    public static final String COMODIN = "&";
    
    public static final String TIPO_DATO_CHARACTER_VARYING = "character varying";
    public static final String TIPO_DATO_BIGINT= "bigint";
    public static final String TIPO_DATO_BOOLEAN = "boolean";
    public static final String TIPO_DATO_BOOLEANO = "bool";
    public static final String TIPO_DATO_INT4 = "int4";
    public static final String TIPO_DATO_INT8 = "int8";
    public static final String TIPO_DATO_VARCHAR = "varchar(&)";
    public static final String TIPO_DATO_TEXT = "text";
    public static final String TIPO_DATO_FECHA_HORA = "timestamp";
    public static final String TIPO_DATO_INTEGER = "integer";
    public static final String TIPO_DATO_NUMERIC = "numeric(15,2)";
    public static final String TIPO_DATO_JSON = "json";
    public static final String TIPO_DATO_JSONB = "jsonb";
    
    public static final String CAMPO_OPCION_COMPONENTE_RADIOBOTON = "Registra opción del listado.";
    public static final String CAMPO_OTRO_COMPONENTE_RADIOBOTON = "Otra opción.";
    public static final String CAMPO_ESPECIFIQUE_COMPONENTE_RADIOBOTON = "Especifique otra opción.";
    
    public static final String CAMPO_CALLE_COMPONENTE_DOMICILIO = "Campo calle.";
    public static final String CAMPO_NUMEXT_COMPONENTE_DOMICILIO = "Número exterior.";
    public static final String CAMPO_NUMINT_COMPONENTE_DOMICILIO = "Número interior.";
    public static final String CAMPO_CP_COMPONENTE_DOMICILIO = "Código postal.";
    public static final String CAMPO_COLONIA_COMPONENTE_DOMICILIO = "Colonia.";
    public static final String CAMPO_ALCALDIA_COMPONENTE_DOMICILIO = "Alcaldía.";
    public static final String ORDER_CAMPO_ESTADO_COMPONENTE_DOMICILIO = "_7";
    public static final String CAMPO_ESTADO_COMPONENTE_DOMICILIO = "Estado.";
    
    public static final String CAMPO_CURP_COMPONENTE_DATOS_PERSONALES = "Campo curp.";
    public static final String CAMPO_NOMBRES_COMPONENTE_DATOS_PERSONALES = "Nombres.";
    public static final String CAMPO_PRIMERAPELLIDO_COMPONENTE_DATOS_PERSONALES = "Primer apellido.";
    public static final String CAMPO_SEGUNDOAPELLIDO_COMPONENTE_DATOS_PERSONALES = "Segundo apellido.";
    public static final String CAMPO_TELEFONO_COMPONENTE_DATOS_PERSONALES = "Teléfono.";
    public static final String CAMPO_CORREO_COMPONENTE_DATOS_PERSONALES = "Correo electrónico.";
    public static final String CAMPO_FECHANAC_COMPONENTE_DATOS_PERSONALES = "Fecha de nacimiento.";
    public static final String ORDER_CAMPO_SEXO_COMPONENTE_DATOS_PERSONALES = "_8";
    public static final String CAMPO_SEXO_COMPONENTE_DATOS_PERSONALES = "Campo sexo.";
    
    public static final String CAMPO_RFC_COMPONENTE_DATOS_PERSONA_MORAL = "Campo RFC";
    public static final String CAMPO_RAZON_SOCIAL_COMPONENTE_DATOS_PERSONA_MORAL = "Razon social";
    public static final String CAMPO_VIGENCIA_CERTIFICADO_COMPONENTE_DATOS_PERSONA_MORAL = "Vigencia certificado";

    public static final int PROYECTO_TIPO_TRAMITE = 1;
    public static final int PROYECTO_TIPO_PROGRAMA = 2;
    
    //Constantes para carga de documento
    public static final String CONTENTTYPE_PNG = "image/png";
    public static final String CONTENTTYPE_JPG = "image/jpg";
    public static final String CONTENTTYPE_JPEG = "image/jpeg";
    public static final String CONTENTTYPE_PDF = "application/pdf";
    public static final String CONTENTTYPE_XLSX_XLSM_XLSB = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final String CONTENTTYPE_XLS = "application/vnd.ms-excel";
    public static final String CONTENTTYPE_MP3 = "audio/mpeg";
    public static final String CONTENTTYPE_WMA = "audio/x-ms-wma";
    public static final String CONTENTTYPE_MP4 = "video/mp4";
    public static final String CONTENTTYPE_AVI = "video/x-msvideo";
    public static final String CONTENTTYPE_CSV = "text/csv";
    public static final String CONTENTTYPE_ZIP = "application/zip";
    
    public static final String EXTENSION_JPG = ".jpg";
    public static final String EXTENSION_JPEG = ".jpeg";
    public static final String EXTENSION_PNG = ".png";
    public static final String EXTENSION_PDF = ".pdf";
    public static final String EXTENSION_XLSX = ".xlsx";
    public static final String EXTENSION_XLSM = ".xlsm";
    public static final String EXTENSION_XLSB = ".xlsb";
    public static final String EXTENSION_MP3 = ".mp3";
    public static final String EXTENSION_WMA = ".wma";
    public static final String EXTENSION_MP4 = ".mp4";
    public static final String EXTENSION_AVI = ".avi";
    public static final String EXTENSION_CSV = ".csv";
    public static final String EXTENSION_ZIP = ".zip";
    
    public static final String IMAGEN_JPG = "/resources/img/JPG.svg";
    public static final String IMAGEN_JPEG = "/resources/img/JPG.svg";
    public static final String IMAGEN_PNG = "/resources/img/JPG.svg";
    public static final String IMAGEN_PDF = "/resources/img/PDF.svg";
    public static final String IMAGEN_XLSX = "/resources/img/excel.png";
    public static final String IMAGEN_XLSM = "/resources/img/excel.png";
    public static final String IMAGEN_XLSB = "/resources/img/excel.png";
    public static final String IMAGEN_XLS = "/resources/img/excel.png";
    public static final String IMAGEN_MP3 = "/resources/img/mp3.png";
    public static final String IMAGEN_WMA = "/resources/img/wma.png";
    public static final String IMAGEN_MP4 = "/resources/img/mp4.png";
    public static final String IMAGEN_AVI = "/resources/img/avi.png";
    public static final String IMAGEN_CSV = "/resources/img/csv.png";
    public static final String IMAGEN_ZIP = "/resources/img/zip.png";
    
    public static final String STR_JPG = "jpg";
    public static final String STR_JPEG = "jpeg";
    public static final String STR_PNG = "png";
    public static final String STR_PDF = "pdf";
    public static final String STR_XLSX = "xlsx";
    public static final String STR_XLSM = "xlsm";
    public static final String STR_XLSB = "xlsb";
    public static final String STR_XLS = "xls";
    public static final String STR_MP3 = "mp3";
    public static final String STR_WMA = "wma";
    public static final String STR_MP4 = "mp4";
    public static final String STR_AVI = "avi";
    public static final String STR_CSV = "csv";
    public static final String STR_ZIP = "zip";
    
    public static final String STR_KB = "KB";
    public static final String STR_MB = "MB";
    public static final String STR_BYTES = "Bytes";
    
    public static final long KILOBYTE = 1024L;
    
    public static final int ID_TAMANIO_ARCHIVO_500KB = 1;
    public static final int ID_TAMANIO_ARCHIVO_1MB = 2;
    public static final int ID_TAMANIO_ARCHIVO_2MB = 3;
    public static final int ID_TAMANIO_ARCHIVO_5MB = 4;
    public static final int ID_TAMANIO_ARCHIVO_10MB = 6;
    public static final int ID_TAMANIO_ARCHIVO_15MB = 5;
    public static final int ID_TAMANIO_ARCHIVO_2OMB = 7;
    
    public static final int ID_TIPO_ARCHIVO_PNG = 1;
    public static final int ID_TIPO_ARCHIVO_JPG = 2;
    public static final int ID_TIPO_ARCHIVO_PDF = 3;
    public static final int ID_TIPO_ARCHIVO_XLSX = 4;
    public static final int ID_TIPO_ARCHIVO_XLSM = 5;
    public static final int ID_TIPO_ARCHIVO_XLSB = 6;
    public static final int ID_TIPO_ARCHIVO_XLS = 7;
    public static final int ID_TIPO_ARCHIVO_MP3 = 8;
    public static final int ID_TIPO_ARCHIVO_WMA = 9;
    public static final int ID_TIPO_ARCHIVO_MP4 = 10;
    public static final int ID_TIPO_ARCHIVO_AVI = 11;
    public static final int ID_TIPO_ARCHIVO_CSV = 12;
    public static final int ID_TIPO_ARCHIVO_ZIP = 13;
    
    public static final int MAXIMO_NUMERO_ARCHIVOS = 3;
    
    public static final int LONGITUD_CODIGO_POSTAL = 5;
    public static final int LONGITUD_CURP = 18;
    public static final int LONGITUD_TELEFONO = 10;
    //VALIDACION DE URL
    public static final String EXPRESION_URL_PATTERN = "^((((https?|ftps?|gopher|telnet|nntp)://)|(mailto:|news:))(%{2}|[-()_.!~*';/?:@&=+$, A-Za-z0-9])+)" + "([).!';/?:, ][[:blank:]])?$";
    
    //Constantes para Estatus de Trámites
    //Estatus de trámite que no ha completado su registro (Todas las secciones del formulario, este estatus permite edición del trámite).
    public static final int ID_ESTATUS_EN_CAPTURA = 1;  
    //Estatus de trámite para aquellos que son marcados que requieren de un pago.
    public static final int ID_ESTATUS_PENDIENTE_PAGO = 2;
    //Estatus de trámite que ha sido completado en su registro (Todas las secciones del formulario).
    public static final int ID_ESTATUS_ENVIADO = 3;
    //Estatus de trámite que ya fue revisado por un funcionario y tiene alguna observación (Permite edición en formulario para corregir observaciones.
    public static final int ID_ESTATUS_CORRECIONES = 4;
    //Estatus de trámite que indica que el trámite ha sido corregido en su contenido por el ciudadano
    public static final int ID_ESTATUS_CORREGIDO = 5;
    //Estatus de trámite que indica que ya fue revisado y rechazado por un funcionario.
    public static final int ID_ESTATUS_RECHAZADO = 6;
    //Estatus de trámite que indica que ya fue revisado y aprobado por un funcionario.
    public static final int ID_ESTATUS_APROBADO = 7;
    //Estatus de trámite que indica que ya fue revisado por un Operador para cuando no hay prevención y el Operador no puede concluir el trámite
    public static final int ID_ESTATUS_REVISADO = 8;   
    //Estatus de trámite que indica que ya fue revisado por Supervisor u Operador y lo marcó como resolución positiva (Estos estatus solo se utilizan 
    //si el proyecto tiene la marca de "habilitar resolución" en la gestión de usuario) 
    public static final int ID_ESTATUS_CONCLUSION_POSITIVA = 9;
    //Estatus de trámite que indica que ya fue revisado por Supervisor u Operador y lo marcó como resolución negativa (Estos estatus solo se utilizan 
    //si el proyecto tiene la marca de "habilitar resolución" en la gestión de usuario) 
    public static final int ID_ESTATUS_CONCLUSION_NEGATIVA = 10;
    
    //Titulos provisionales
    public static final String TITULO_REGISTRO_TRAMITE = "Trámite registrado";
    public static final String TITULO_CONCLUSION_TRAMITE = "Trámite concluido";
    public static final String TITULO_PREVENCION_TRAMITE = "Trámite en prevención";
    public static final String TITULO_RECHAZO_TRAMITE = "Trámite rechazado";
    public static final String TITULO_NO_SUBSANAR_TRAMITE = "Trámite cancelado por caducidad (no subsanó la prevención)";
    public static final String TITULO_EXPEDIDO = "Aviso";
    public static final String TITULO_REVOCADO = "Aviso revocado";
    public static final String TITULO_CONCLUSION_POSITIVA = "Trámite con resolución positiva";
    public static final String TITULO_CONCLUSION_NEGATIVA = "Trámite con resolución negativa";
    public static final String TITULO_NOTIFICACION_ESTATUS = "Estatus del trámite";
    
    //Usuario relacionado al schedule que valida la prevención
    public static final long ID_USUARIO_SCHEDULE_PREVENCION = -9;
    
    //Constantes para Identificadores de movimientos en bitácora
    public static final int ID_REGISTRO_TRAMITE = 1;
    public static final int ID_REGISTRO_OBSERVACIONES = 2;
    public static final int ID_ACTUALIZACION_ESTATUS = 3;
    public static final int ID_REGISTRO_DATOS_SECCION = 4;
    public static final int ID_ACTUALIZACION_DATOS_SECCION = 5;
    
    public static final String DATO_NO_CAPTURADO = "----------";
    
    public static final int ID_ESTATUS_PUBLICADO = 1;
    public static final int ID_ESTATUS_BORRADOR = 2;
    public static final int ID_ESTATUS_PAUSA = 3;
    public static final int ID_ESTATUS_EDICION = 4;
    public static final int ID_ESTATUS_PUBLICAR_CAMBIOS = 5;
    public static final int ID_ESTATUS_ERROR_PUBLICAR = 6;
    public static final int ID_ESTATUS_FINALIZADO = 7;
    
    //Constante para años que se deben mostrar en componente fecha
    public static final int ANIOS_RANGO_FECHA = 120;
    
    //Descripciones para bandeja de ciudadano
    public static final String DESC_EN_REVISION = "En revisión";
    public static final String DESC_REVISADO = "Revisado";
    public static final String DESC_PREVENIDO = "Prevenido";
    
    //IDENTIFICADORES DE LOS TIPOS DE SEGURIDAD DE DOMINIO QUE SE REGISTRAN EN EL MOTOR
    public static final int ID_TIPO_SECURITY_DOMAIN_CLIENTE = 1;   
    public static final int ID_TIPO_SECURITY_DOMAIN_CURP = 2;
    
    //Constantes para funcionalidad de firmaCDMX
    public final static String STATE_CHARS_FIRMA = "abcdefghijklmnopqrstuvwxyz1234567890-";
	public static final String PARAM_FIRMA_CLIENT_ID = "clientId";
	public static final String PARAM_FIRMA_TRAMITE_ID = "id";
	public static final String PARAM_FIRMA_REDIRECT = "urlRedirect";	
	public static final String PARAM_FIRMA_STATE = "state";
	
    public static final String CLIENT_ID = "clientId";
    public static final String TIPO_FIRMA = "tipoFirma";
    public static final String FIRMA = "FIRMASAT";
    public static final String CADENAS_DIGITALES = "cadenasDigitales";
    public static final String CADENA = "cadena";
    public static final String NOMBRE = "nombre";
    public static final String DATA = "data";
    public static final String CLAVE = "clave";
    public static final String VALOR = "valor";
    
    public static final String ID_PROYECTO = "idProyecto";
    public static final String ID_TRAMITE = "idTramite";
    public static final String FOLIO_TRAMITE = "FolioTramite";
    public static final String ID_USUARIO_LLAVE_SOLICITANTE = "idUsuarioLlaveSolicitante";
    public static final String CURP_USUARIO_LLAVE_SOLICITANTE = "curpUsuarioLlaveSolicitante";    
    public static final String ID_USUARIO_LLAVE_FIRMANTE = "idUsuarioLlaveFirmante";
    public static final String CURP_USUARIO_LLAVE_FIRMANTE = "curpUsuarioLlaveFirmante";
    public static final String ID_ESTATUS_TRAMITE = "IdEstatusTramite";
    public static final String FECHA_REGISTRO_TRAMITE = "fechaRegistroTramite";
    public static final String FECHA_FIRMADO = "fechaFirmado";
      
    public static final String ID_LLAVE = "idLlaveMX";
    public static final String NOMBRE_FIRMANTE = "nombreFirmante";
    public static final String ID_SOLICITUD_FIRMA = "idSolicitud";  
    
    public static final int INT_MINIMO_TRAMITE_FIRMADO = 1;
    //Posición del iddentificador del trámite en la cadena generada para firmado.
    public static final int INT_POSICION_ID_TRAMITE_FIRMADO = 1;
    
    public static final int INT_MAX_TRAMITES_FIRMADO = 100;
    
    // MARGENES MÁXIMOS PARA QR PREDEFINIDO.
    public static final int TAMANIO_QR_ALTO = 100;
    public static final int TAMANIO_QR_ANCHO = 100;
    
    // ORIGEN TOKENS
    public static final int TOKEN_FOLIO_SISTEMA = 1;
    public static final int TOKEN_FECHA_ACTUAL = 2;
    public static final int TOKEN_COMPONENTE = 3;
    public static final int TOKEN_CADENA_DIGITAL = 4;
    public static final int TOKEN_FECHA_FIRMADO = 5;
    public static final int TOKEN_NOMBRE_FIRMANTE = 6;
    public static final int TOKEN_NOMBRE_REGISTRA = 7;
    public static final int TOKEN_NOMBRE_PROYECTO = 8;
    public static final int TOKEN_DEPENDENCIA = 9;
    public static final int TOKEN_ESTATUS_TRAMITE = 10;
    
    //CONSTANTES PARA TIPOS DE PLANTILLA PARA CONFIGURACION DE ARCHIVOS DE RESPUESTA CON FIRMA
    public static final int INT_PLANTILLA_FIRMADO_ACEPTADO = 1;
    public static final int INT_PLANTILLA_FIRMADO_RECHAZO = 2;
    public static final int INT_PLANTILLA_REGISTRO_CONCLUIDO = 3;
    public static final int INT_PLANTILLA_COMPROBANTE_REGISTRO = 4;
    
    //VALIDADORES DE CAMPO DE TEXTO
    public static final int ID_VALIDADOR_TELEFONO = 1;
    public static final int ID_VALIDADOR_CORREO = 2;
    public static final int ID_VALIDADOR_CURP = 3;
    public static final int ID_VALIDADOR_URL = 4;
    public static final int ID_VALIDADOR_NUMERO = 5;
    
    //LONGITUD DE ACRONIMO VALIDO DE PROYECTO
    public static final int LONGITUD_ACRONIMO = 10;
    
    //CONSTANTE QUE INDICA EL NUMERO MAXIMO DE TRAMITES QUE PUEDE GENERAR UN USUARIO CUANDO SE HABILITA BANDERA EN CONFIGURACION 
    //DE LLAVE PARA LIMITAR REGISTRO DE TRAMITES
    public static final int MAXIMO_TRAMITES_USUARIO = 1;
    
    //IDENTIFICADORES DE COLUMNAS DE REPORTE DE TRÁMITES
    public static final int ID_COLUMNA_FOLIO = 0;
    public static final String DESC_COLUMNA_FOLIO = "Folio del trámite";
    public static final int ID_COLUMNA_NOMBRE = 1;
    public static final String DESC_COLUMNA_NOMBRE = "Nombre del solicitante";
    public static final int ID_COLUMNA_APE_PATERNO = 2;
    public static final String DESC_COLUMNA_APE_PATERNO = "Primer apellido";
    public static final int ID_COLUMNA_APE_MATERNO = 3;
    public static final String DESC_COLUMNA_APE_MATERNO = "Segundo apellido";
    public static final int ID_COLUMNA_CURP = 4;
    public static final String DESC_COLUMNA_CURP = "CURP";
    public static final int ID_COLUMNA_FECHA_SOLICITUD = 5;
    public static final String DESC_COLUMNA_FECHA_SOLICITUD = "Fecha de solicitud";
    public static final int ID_COLUMNA_FECHA_CAMBIO = 6;
    public static final String DESC_COLUMNA_FECHA_CAMBIO = "Fecha de cambio de estatus";
    public static final int ID_COLUMNA_OPERADOR = 7;
    public static final String DESC_COLUMNA_OPERADOR = "Operador designado";
    public static final int ID_COLUMNA_ESTATUS = 8;        
    public static final String DESC_COLUMNA_ESTATUS = "Estatus actual";
    public static final int ID_COLUMNA_ESTATUS_ENVIADO = 9;
    public static final String DESC_COLUMNA_ESTATUS_ENVIADO = "Fecha enviado";
    public static final int ID_COLUMNA_ESTATUS_EN_CORRECION = 10;
    public static final String DESC_COLUMNA_ESTATUS_EN_CORRECCION = "Fecha en corrección";
    public static final int ID_COLUMNA_ESTATUS_CORREGIDO = 11;
    public static final String DESC_COLUMNA_ESTATUS_CORREGIDO = "Fecha corregido";
    public static final int ID_COLUMNA_ESTATUS_RECHAZADO = 12;
    public static final String DESC_COLUMNA_ESTATUS_RECHAZADO = "Fecha rechazado";
    public static final int ID_COLUMNA_ESTATUS_ACEPTADO = 13;
    public static final String DESC_COLUMNA_ESTATUS_ACEPTADO = "Fecha aceptado";
    public static final int ID_COLUMNA_ESTATUS_REVISADO = 14;
    public static final String DESC_COLUMNA_ESTATUS_REVISADO = "Fecha revisado";
    public static final int ID_COLUMNA_ESTATUS_RESOLUCION_POSITIVA = 15;
    public static final String DESC_COLUMNA_ESTATUS_RESOLUCION_POSITIVA = "Fecha resolución positiva";
    public static final int ID_COLUMNA_ESTATUS_RESOLUCION_NEGATIVA = 16;
    public static final String DESC_COLUMNA_ESTATUS_RESOLUCION_NEGATIVA = "Fecha resolución negativa";
    
    
    //NOMBRE DEL REPORTE DE TRÁMITES
    public static final String REPORTE_TRAMITES = "ReporteTramites";
    public static final String EXTENSION_XLS = ".xls";
    public static final String NOMBRE_REPORTE = "Reporte de trámites";
    
    //NUMERO DE RANGO MAXIMO PERMITIDO PARA BUSQUEDAS DE TRAMITES (PARA EVITAR DESCARGA DE EXCEL EN UN MISMO ARCHIVO)
    public static final int INT_MAX_DIAS_CONSULTA_TRAMITES_DEFAULT = 30;
    
    //IDENTIFICADORES PARA REGISTROS ACTIVOS E INACTIVOS
    public static final int ID_ACTIVO = 1;   
    public static final int ID_INACTIVO = 0;
    
    //IDENTIFICADORES DE PARAMETROS REGISTRADOS
    public static final int ID_PARAMETRO_MAX_DIAS_CONSULTA_TRAMITES = 1;
    
    //ORIGENES DE LOGIN A BANDEJA DE TRÁMITES
    public static final int ID_ORIGEN_HOME = 1;
    public static final int ID_ORIGEN_EXPEDIENTE = 2;    
    public static final int ID_NUEVO_TRAMITE = 3;
    
    //VALORES DE RESPUESTA DE SOLICITUDES DE SINCRONIZACION
    public static final int SINC_PROYECTO_ESTATUS_INCORRECTO = 223;
    public static final int SINC_PROYECTO_SIN_CAMBIOS = 224;
    public static final int SINC_PROYECTO_NO_ENCONTRADO = 225;
    public static final int SINC_PROYECTO_VALIDO = 226;
    public static final int SINC_IP_INVALIDA = 227;
    public static final int SINC_PROYECTO_SINCRONIZADO = 228;
    
    public static final int OPERADOR_IGUAL_QUE = 1;
    public static final int OPERADOR_DISTINTO_QUE = 2;
    public static final int OPERADOR_MAYOR_QUE = 3;
    public static final int OPERADOR_MENOR_QUE = 4;
    public static final int OPERADOR_MAYOR_O_IGUAL_QUE = 5;
    public static final int OPERADOR_MENOR_O_IGUAL_QUE = 6;
    public static final int OPERADOR_IN = 7;
    public static final int OPERADOR_NOT_IN = 8;
    
    //VALORES DE ID PARA ATRIBUTOS DE COMPONENTES
    public static final int ID_CALLE = 1;
    public static final int ID_NUM_EXT = 2;
    public static final int ID_NUM_INT = 3;
    public static final int ID_COD_POST = 4;
    public static final int ID_ESTADO = 5;
    public static final int ID_MUNICIPIO = 6;
    public static final int ID_COLONIA = 7;
    public static final int ID_CURP_SIN_LLAVE = 8;
    public static final int ID_NOMBRE_SIN_LLAVE = 9;
    public static final int ID_PRIM_APELLIDO_SIN_LLAVE = 10;
    public static final int ID_SEG_APELLIDO_SIN_LLAVE = 11;
    public static final int ID_TEL_SIN_LLAVE = 12;
    public static final int ID_CORREO_SIN_LLAVE = 13;
    public static final int ID_CURP_CON_LLAVE = 14;
    public static final int ID_NOMBRE_CON_LLAVE = 15;
    public static final int ID_PRIM_APELLIDO_CON_LLAVE = 16;
    public static final int ID_SEG_APELLIDO_CON_LLAVE = 17;
    public static final int ID_TEL_CON_LLAVE = 18;
    public static final int ID_CORREO_CON_LLAVE = 19;
    public static final int ID_FECHA_CON_LLAVE = 20;
    public static final int ID_SEXO_CON_LLAVE = 21;
    public static final int ID_RFC = 22;
    public static final int ID_PERS_MORAL = 23;
    public static final int ID_FECHA_VIGENCIA = 24;
    public static final int ID_COMPLETO = 25;
    
    //VALORES PARA LINEA DE CAPTURA SAT
    public static final String ESTATUS_LC_PAGD = "PAGD";
    public static final String ESTATUS_LC_NPAG = "NPAG";
    public static final String ID_FORM_MODAL_ESTATUS_LC = "frmModalEstatusLC";
    
  //VALORES PARA LINEA DE CAPTURA INTERPRETE
    public static final int ID_LC_ESTATUS_PENDIENTE = 1;
    public static final int ID_LC_ESTATUS_PAGADO = 2;
    public static final int ID_LC_ESTATUS_VENCIDA = 3;

    //CATALOGO DE CAT ESTATUS TRAMITE
    public static final int ID_TIPO_NOTIFICACION_REGISTRO_TRAMITE = 1;
    public static final int ID_TIPO_NOTIFICACION_ACTUALIZACION_ESTATUS_TRAMITE = 2;

    //CATALOGO DE ESTATUS DE PLATAFORMA *PENDIENTE DEFINICION
    public static final int ID_TIPO_NOTIFICACION_PLATAFORMA = 1;
    
    /**
     * Constantes para la generación de linea de captura
     */
    public static final Integer LONGITUD_CONSECUTIVO_LINEA_CAPTURA = 10;
    public static final Integer LONGITUD_COMPLEMENTO_SOLICITUD_LINEA_CAPTURA = 6;
    public static final Integer CODIGO_SOLICITUD_PROCESADA_ANTERIORMENTE = 2;
    public static final String DESCRIPCION_SOLICITUD_PROCESADA_ANTERIORMENTE = "Solicitud procesada anteriormente";
    
    /**
     * Constantes para status de solicitud de línea de captura
     */
    public static final Integer ID_ESTATUS_SOLICITUD_PROCESADA_ANTERIORMENTE = 1;
    public static final Integer ID_ESTATUS_ERROR_SOLICITUD = 2;
    public static final Integer ID_ESTATUS_SOLICITUD_EXITOSA = 3;
    public static final Integer ID_ESTATUS_ERROR_CONEXION_A_SERVICIO = 4;
    
    /**
     * Tipos de vigencia para linea de captura
     */
    public static final String CLAVE_VIGENCIA_POR_DIA = "D";
    public static final String CLAVE_VIGENCIA_POR_SEMANA = "S";
    public static final String CLAVE_VIGENCIA_POR_MES = "M";
    public static final String CLAVE_VIGENCIA_POR_ANIO = "A";
    
    /*
     * Tipos de ordenamiento para menú desplegable 
     */
    public static final Long ID_ORDEN_ASCENDENTE = 1L;
    public static final Long ID_ORDEN_DESCENDENTE = 2L;
    
    public static final String SEPARACION_COLUMNA_CSV = "#-#";
}
package mx.gob.atdt.interprete.commons.enums;

public enum ResponseEnum {
	//Códigos respuesta correcta
	CREACION_CORRECTA (101, "Creado correctamente."),
	ACTUALIZACION_CORRECTA (102, "Actualizado correctamente."),
	SINCRONIZACION_DISPONIBLE (103, "Ok, Intérprete disponible para sincronización."),	
	PROYECTO_YA_EN_PAUSA (104, "El Sistema ya se encuentra actualmente Pausado."),	
	PRIMER_SINCRONIZACION(105, "Primer sincronización del intérprete"),

	//Códigos para errores de validación
	FALTAN_PARAMETROS_OBLIGATORIOS (201, "Faltan parámetros obligatorios"),
	PROYECTO_INEXISTENTE (202, "No existe el proyecto para el registro de información."),
	DETALLE_HOME_INEXISTENTE (203, "No existe el detalle home para el registro de información."),
	REQUISITO_INEXISTENTE (204, "No existe el requisito para el registro de información."),
	TIPO_COSTO_INEXISTENTE (205, "No existe el id tipo costo para el registro de información."),
	SECCION_INEXISTENTE (206, "No existe el id sección para el registro de información."),
	DETALLE_PAGO_INEXISTENTE (207, "No existe el id detalle pago para el registro de información."),
	ARCHIVO_RESPUESTA_TOKEN_INEXISTENTE (208, "No existe el id archivo respuesta para el registro de información."),
	COMPONENTE_INEXISTENTE (209, "No existe el id componente para el registro de información."),
	SUBSECCION_INEXISTENTE(210, "No existe la subsección para el registro de la información"),
	COMPONENTE_RADIOBOTON_INEXISTENTE (211, "No existe el id componente radio para el registro de información."),
	COMPONENTE_CHECKBOX_GRUPO_INEXISTENTE (212, "No existe el id componente checkbox para el registro de información."),
	COMPONENTE_MENU_DESPLEGABLE_INEXISTENTE (213, "No existe el id componente menu para el registro de información."),
	COMPONENTE_CARGA_DOCUMENTOS_INEXISTENTE (214, "No existe el id componente carga para el registro de información."),
	PROYECTO_NO_COINCIDE (215, "Ya existe información sincronizada de otro proyecto con idProyecto=&, Por lo tanto no puede sincronizar la información del idProyecto=$."),
	IDENTIFICADOR_PROYECTO_INCORRECTO (216, "El valor de idProyecto no es un valor correcto."),
	ESTATUS_SISTEMA_NO_VALIDO_SINCRONIZACION (217, "No fue posible verificar el estado del sistema, por lo que no es posible realizar la sincronización"),
	ESTATUS_SISTEMA_NO_VALIDO(218, "El estatus enviado de sincronización no es un valor válido."),
	ESTATUS_SISTEMA_NO_MANTENIMIENTO (219, "El estatus actual del sistema no se encuentra en mantenimiento, por lo que no es posible realizar la sincronización"),
	ESTATUS_SISTEMA_NO_INICIO_SINCRONIZACION (220, "El estatus actual del sistema no se encuentra en proceso de sincronización, por lo que no es posible actualizar como sistema sincronizado o en línea."),
	IDENTIFICADOR_PROYECTO_INEXISTENTE (221, "El idProyecto enviado no existe."),
	ESTATUS_SISTEMA_NO_VALIDO_PAUSA(222, "Por el momento no puede colocar en Pausa el proyecto debido a que está En Mantenimiento o Sincronizando cambios"),
	SECCION_CONDICIONADA_INEXISTENTE (223, "No existe el id sección condicionada para el registro de información."),
	SECCION_CONDICION_INEXISTENTE (224, "No existe el id sección condición para el registro de información."),
	CONDICION_INEXISTENTE(225, "No existe la configuración de la condición para el registro de la información"),
	OPERADOR_INEXISTENTE(226, "No existe el operador para el registro de la información"),
	CATALOGO_INEXISTENTE(227, "No existe el catálogo para el registro de la información"),
	OPCION_CATALOGO_INEXISTENTE(228,"No existe la opción del catálogo para el registro de la información"),
	DIA_SEMANA_INEXISTENTE (227, "No existe el día de la semana para el registro de información."),
	DEPENDENCIA_PAGO_INEXISTENTE (229, "No existe id de dependencia de pago para el registro de información."),
	UNIDAD_ADMINISTRATIVA_INEXISTENTE (230, "No existe id de Unidad Administrativa para el registro de información."),
	TIPO_VIGENCIA_INEXISTENTE (231, "No existe id de Tipo Vigencia para el registro de información."),
	TIPO_PERSONA_INEXISTENTE (232, "No existe id de Tipo persona para el registro de información."),
	TRAMITE_LINEA_CAPTURA_INEXISTENTE (233, "No existe id de Trámite para linea de captura para el registro de información."),
	TIPO_AGRUPADOR_INEXISTENTE (234, "No existe id del tipo de agrupador para el registro de información."),
	PERIODO_INEXISTENTE (235, "No existe id del periodo para el registro de información."),
	EJERCICIO_INEXISTENTE (236, "No existe id del ejercicio para el registro de información."),
	CONCEPTO_TRAMITE_INEXISTENTE (237, "No existe id del concepto trámite para el registro de información."),
	DETALLE_LINEA_CAPTURA_INEXISTENTE (238, "No existe id del detalle de línea de captura para el registro de información."),
	PERIODICIDAD_INEXISTENTE (239, "No existe id de periodicidad para el registro de información."),
	COMPONENTE_TABLA_INEXISTENTE (240, "No existe el id componente tabla para el registro de información."),
	//Códigos de error
	ERROR_500 (500, "Server Error"),
	ERROR_INESPERADO(500, "Error inesperado"),
	
	//Códigos para sincronización iniciada desde el cliente
	PROYECTO_ESTATUS_INCORRECTO (223, "El estatus actual del proyecto es incorrecto, por favor verifique que se encuentre como Publicar cambios para iniciar la sincronización."),
	PROYECTO_SIN_CAMBIOS(224, "El proyecto que intenta sincronizar, no cuenta con cambios registrados, por favor verifique."),
	PROYECTO_NO_ENCONTRADO(225, "No es posible iniciar la sincronización, el proyecto enviado no es correcto."),
	PROYECTO_VALIDO_SINCRONIZACION(226, "Proyecto válido para inicio de sincronización."),
	PROYECTO_VALIDO_IP_SEGURIDAD_DOMINIO(227, "La IP del cliente no coincide con la IP del dominio de seguridad ");
	
	private Integer codigoRespuesta;
	private String mensajeRespuesta;
	
	ResponseEnum(final Integer codigoRespuesta, final String mensajeRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
		this.mensajeRespuesta = mensajeRespuesta;
	}

	public Integer getCodigoRespuesta() {
		return codigoRespuesta;
	}

	public String getMensajeRespuesta() {
		return mensajeRespuesta;
	}	
}

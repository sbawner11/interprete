# Changelog

Todos los cambios notables a este proyecto serán documentados es este archivo.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/en/1.0.0/), mantenemos las versiones de acuerdo
a [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

**Dada una versión número MAJOR.MINOR.PATCH:** (aplica a partir de 2.0.0)

* Se incrementará **MAJOR**, cuando se implemente un nuevo tipo de **Módulo** que genere incompatibilidad con versión
  anterior.
* Se incrementa **MINOR**, cuando se agregue una o más **nueva(s) funcionalidad(es) a la aplicación**.
* Se incrementa **PATCH**, cuando se implemente un **fix**.

## [0.44.15] - 2026-08-31
### [task]
* TR-MOTOR-MODULO-INTERPRETE-213159 - TR - Permitir distribuir el mismo elemento a diferentes roles en distribución de solicitudes

## [0.44.14] - 2026-08-28
### [task]
* TR-MOTOR-MODULO-INTERPRETE-212985 - TR - Error en la descarga de plantilla distribucion de solicitudes.

## [0.44.13] - 2026-08-27
### [task]
* TR-MOTOR-MODULO-INTERPRETE-212779 - TR - Permitir visualización de todos los trámites para usuarios con rol Consulta.

## [0.44.12] - 2026-08-27
### [task]
* TR-MOTOR-MODULO-INTERPRETE-212776 - TR - Eliminar rol Consulta de las opciones para distribucion de solicitudes.

## [0.44.11] - 2026-08-27
### [task]
* TR-MOTOR-MODULO-INTERPRETE-212675 - TR - Implementar actualización automática del listado de cargas masivas.

## [0.44.10] - 2026-08-26
### [task]
* TR-MOTOR-MODULO-INTERPRETE-212109 - TR - Agregar soporte para errores en procesamiento de registros de CSV

## [0.44.9] - 2026-08-25
### [task]
* TR-MOTOR-MODULO-INTERPRETE-212109 - TR - Agregar soporte para errores en procesamiento de registros de CSV

## [0.44.8] - 2026-08-24
### [task]
* TR-MOTOR-MODULO-INTERPRETE-212034 - TR - Agregar filtro del usuario asignado, quien realiza la carga y escape Csv.

## [0.44.7] - 2026-08-24
### [task]
* TR-MOTOR-MODULO-INTERPRETE-211965 - TR - Agregar msg complementario cuando existe detalle en carga de registros de CSV de carga masiva

## [0.44.6] - 2026-08-24
### [task]
* TR-MOTOR-MODULO-INTERPRETE-211965 - TR - Agregar msg complementario cuando existe detalle en carga de registros de CSV de carga masiva

## [0.44.5] - 2026-08-24
### [task]
* TR-MOTOR-MODULO-INTERPRETE-211864 - TR - Modificación de columna usuario que realiza la carga , usuario asignado y boton de descarga.

## [0.44.4] - 2026-08-21
### [task]
* TR-MOTOR-MODULO-INTERPRETE-211744 - TR - Error al Exportar Plantilla Pantalla de distribucion

## [0.44.3] - 2026-08-20
### [task]
* TR-MOTOR-MODULO-INTERPRETE-210502 - TR - Implementar lógica del bean y conexión con servicios para el administrador de archivos para carga masiva.

## [0.44.2] - 2026-08-20
### [task]
* TR-MOTOR-MODULO-INTERPRETE-211475 - Se agrega estatus carga parcial a procesamiento de CSV para carga masiva

## [0.44.1] - 2026-08-19
### [task]
* TR-MOTOR-MODULO-INTERPRETE-211180 - TR-Procesamiento de archivo CSV de forma asincrona

## [0.44.0] - 2026-08-19
### [hu]
* HU-MOTOR-MODULO-INTERPRETE-209448 - HU - Generar nueva pantalla para "Asignación masiva de usuarios para distribución".

## [0.43.0] - 2026-08-17
### [hu]
* HU-MOTOR-MODULO-INTERPRETE-209447 - HU - Incorporar nuevas opciones en la "Asignación de usuarios a distribución de solicitudes" en proyecto intérprete.

## [0.42.1] - 2026-08-12
### [task]
* TR-MOTOR-MODULO-INTERPRETE-210199 - TR - Implementar la exclusión del tipo de archivo zip dentro de archivos zip en la carga de archivos.

## [0.42.0] - 2026-08-11
### [hu]
* HU-MOTOR-MODULO-INTERPRETE-209449 - HU - Generar nueva pantalla para "Nueva carga masiva de distribución mediante archivos".

## [0.41.2] - 2026-08-05
### [task]
* TR-MOTOR-MODULO-INTERPRETE-209261 - TR - Agregar validaciones de seguridad para la carga de archivos zip.

## [0.41.1] - 2026-08-05
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-209106 - BUG - BUG-Nullpointer exception en validaciones de linea de captura al capturar informacion de secciones del tramite

## [0.41.0] - 2026-07-30
### [hu]
* HU-MOTOR-MODULO-INTERPRETE-205262 - HU - Implementar la ofuscación del código de motor-interprete.

## [0.40.14] - 2026-07-30
### [task]
* TR-MOTOR-MODULO-INTERPRETE-208334 - TR - Ajuste procesar respuesta del servicio webhook

## [0.40.13] - 2026-07-23
### [task]
* TR-MOTOR-MODULO-INTERPRETE-207377 - TR - Eliminar mensajes relacionados a CDMX en aplicativo interprete.

## [0.40.12] - 2026-07-22
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-206808 - BUG - Apagar bandera isFirmaTramiteCiudadano en la bandeja del funcionario.

## [0.40.11] - 2026-07-21
### [task]
* TR-MOTOR-MODULO-INTERPRETE-206676 - TR - Agregar validación para evitar error cuando la API key de Mandrill es nula.

## [0.40.10] - 2026-07-20
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-206320 - BUG - No permitir modificacion en componente tabla cuando tramite se encuentre en correccion y este componente no se haya marcado para correccion

## [0.40.9] - 2026-07-16
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-206188 - BUG - Genera alter table columna desc_elemento_asignado a 200 tabla det_asignacion_distribucion

## [0.40.8] - 2026-07-15
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-205793 - BUG - Ajustar edición de filas en componente tabla, restaurar información original al cancelar edición.

## [0.40.7] - 2026-07-15
### [task]
* TR-MOTOR-MODULO-INTERPRETE-205131 - TR - Ajustar Cambios para WebHook interprete

## [0.40.6] - 2026-07-10
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-205024 - BUG - Ajuste del layout en modo responsivo al deslizar horizontalmente la tabla dinámica.

## [0.40.5] - 2026-07-10
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-204953 - BUG - Ajuste a estilo responsivo de modal editar y agregar registro en componente tabla

## [0.40.4] - 2026-07-09
### [task]
* TR-MOTOR-MODULO-INTERPRETE-204248 - TR - Proyecto Aviso al a habilitar Sección Firma Digital Interprete

## [0.40.3] - 2026-07-03
### [task]
* TR-MOTOR-MODULO-INTERPRETE-200879 - TR - Ajustar ReenvioNotificacionesWebhookSchedule.

## [0.40.2] - 2026-07-01
### [task]
* TR-MOTOR-MODULO-INTERPRETE-200878 - TR - Ajustar RegistroNotificacionesWebhookSchedule para proyectos con firma.

## [0.40.1] - 2026-06-30
### [task]
* TR-MOTOR-MODULO-INTERPRETE-203242 - TR - Implementar visualización de revocación de avisos relacionados a la bandera correspondiente.

## [0.40.0] - 2026-06-26
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-200082 - HU - Realizar ajustes al servicio Webhook en el proyecto intérprete.

## [0.39.14] - 2026-06-25
### [task]
* TR-MOTOR-MODULO-INTERPRETE-202548 - TR - Amplicar el tamaño del campo para nombres de proyecto (intérprete).

## [0.39.13] - 2026-06-24
### [task]
* TR-MOTOR-MODULO-INTERPRETE-202322 - TR - Permitir la sincronización de proyecto, desde intérprete, solo al rol Administrador Datos Técnicos.

## [0.39.12] - 2026-06-19
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-201712 - BUG - Corregir la descarga de archivos en el componente carga de documentos.

## [0.39.11] - 2026-06-16
### [task]
* TR-MOTOR-MODULO-INTERPRETE-201020 - TR - Reestructuración clases Generación formatos PDF interprete.

## [0.39.10] - 2026-06-15
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-200950 - BUG - Ajustar el componente numérico en componente tabla para evitar que el usuario ingrese o seleccione un número menor a 0.

## [0.39.9] - 2026-06-10
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-200580 - BUG - Corregir asunto del correo de resolución negativa.

## [0.39.8] - 2026-06-10
### [task]
* TR-MOTOR-MODULO-INTERPRETE-200526 - TR - Preparar script sql, servicio para sincronización y vista de la propiedad ordenamiento del componente menú desplegable.

## [0.39.7] - 2026-06-09
### [task]
* TR-MOTOR-MODULO-INTERPRETE-200503 - TR - Implementar validación de tablas dinámicas en primera sección del formulario para no impactar el flujo natural al generar un nuevo trámite cuando no se tiene componente de tipo tabla en el formulario.

## [0.39.6] - 2026-06-03
### [task]
* TR-MOTOR-MODULO-INTERPRETE-199075 - TR - Mostrar modal Conexion fallida con Firma MX

## [0.39.5] - 2026-06-03
### [task]
* TR-MOTOR-MODULO-INTERPRETE-199697 - TR - Ajuste componente tabla elimina exportacion columna acciones y titulo componente.

## [0.39.4] - 2026-06-03
### [task]
* TR-MOTOR-MODULO-INTERPRETE-199678 -TR - Actualizacion script permisos BD interprete.

## [0.39.3] - 2026-06-02
### [task]
* TR-MOTOR-MODULO-INTERPRETE-199523 - TR - Ajustar flujo de firma para ciudadano y supervisor para no finalizar/cerrar tramite cuando firma falle

## [0.39.2] - 2026-06-01
### [task]
* TR-MOTOR-MODULO-INTERPRETE-199450 - TR - Ajustar comportamiento del componente tipo tabla para soportar mas de una tabla en la misma sección.

## [0.39.1] - 2026-05-29
### [hu]
* TR - Cargar fecha actual en calendario después de crear nueva fila y edición de filas en interprete

## [0.39.0] - 2026-05-29
### [hu]
* HU - Implementar la descarga de formularios PDF con las nuevas relaciones componente-token

## [0.38.35] - 2026-05-28
### [task]
* TR-MOTOR-MODULO-INTERPRETE-199075 - TR - Mostrar modal Conexion fallida con Firma MX

## [0.38.34] - 2026-05-28
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-199085 - BUG - Capturar error de conexión hacia servicio firma CDMX y no propagar error a guardado de información de tramite

## [0.38.33] - 2026-05-26
### [task]
* TR-MOTOR-MODULO-INTERPRETE-198780 - TR - Cargar fecha actual en calendario después de crear nueva fila y edición de filas en interprete

## [0.38.32] - 2026-05-25
### [task]
* TR-MOTOR-MODULO-INTERPRETE-198562 - TR - AL agregar una fila dentro de la tabla borra los cambios en otros componentes.

## [0.38.31] - 2026-05-20
### [task]
* TR-MOTOR-MODULO-INTERPRETE-197864 - TR - Implementar cambios en mapRespuestas de componentes datos de domicilio para los tokens de archivo repuesta.

## [0.38.30] - 2026-05-20
### [task]
* TR-MOTOR-MODULO-INTERPRETE-197862 - TR - Agregar el tipo de componente tabla en el encabezado interprete.

## [0.38.29] - 2026-05-20
### [task]
* TR-MOTOR-MODULO-INTERPRETE-197702 - TR - Ajuste a calendario y tooltip de tabla dinamica

## [0.38.28] - 2026-05-20
### [task]
* TR-MOTOR-MODULO-INTERPRETE-197748 - TR - Mostrar mensaje de obligatoriedad de filas requeridas componente tabla.

## [0.38.27] - 2026-05-20
### [task]
* TR-MOTOR-MODULO-INTERPRETE-197708 - TR - Ajustar tokens de componente datos domicilio para la configuración de formatos.

## [0.38.26] - 2026-05-19
### [task]
* TR-MOTOR-MODULO-INTERPRETE-197702 - TR - Ajuste a calendario y tooltip de tabla dinamica

## [0.38.25] - 2026-05-19
### [task]
* TR-MOTOR-MODULO-INTERPRETE-197583 - TR - Valida obligatoriedad componente tabla Estatus: En captura y En Corrreción.

## [0.38.24] - 2026-05-19
### [task]
* TR-MOTOR-MODULO-INTERPRETE-197649 - TR - Implementar el manejo de tipos de campos checkbox en formularios PDF de la configuración de formatos

## [0.38.23] - 2026-05-18
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196981 - TR - Implementar mensaje para solicitar completar la captura de la tabla, al avanzar de sección.

## [0.38.22] - 2026-05-18
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196959 - TR - Ajustar la visualización del tipo de campo numérico

## [0.38.21] - 2026-05-18
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196963 - TR - Agregar modal de verificación de actualización de datos de una fila

## [0.38.20] - 2026-05-15
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-197371 - BUG - Ajustar imágenes de stepper en proyecto interprete en la parte de secciones que conforma al formulario.

## [0.38.19] - 2026-05-15
### [task]
* TR-MOTOR-MODULO-INTERPRETE-197367 - TR - Implementar la utilización de campo de texto numérico con decimales

## [0.38.18] - 2026-05-15
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-197359 - BUG - Permitir la carga de archivos con extensión en Mayúsculas y Minúsculas.

## [0.38.17] - 2026-05-15
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-197319 - BUG - Al dar clic en componente carga de documentos aparece borde al componente.

## [0.38.16] - 2026-05-15
### [task]
* TR-MOTOR-MODULO-INTERPRETE-197312 - TR - Ajustar el estado del Bean para  cuando se realiza el cambio de sección al momento de agregar fila.

## [0.38.15] - 2026-05-14
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196968 - TR - Agregar mensaje de eliminación correcta de fila.

## [0.38.14] - 2026-05-14
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196972 - TR - Implementar validación de campos obligatorios al agregar/editar filas

## [0.38.13] - 2026-05-13
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196796 - TR - Scroll Width componente tabla.

## [0.38.12] - 2026-05-13
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196931 - TR - Implementar botón exportar para el componente tablas

## [0.38.11] - 2026-05-13
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196838 - TR - Ajustar la visualización de tipos de campo numérico.

## [0.38.10] - 2026-05-12
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196770 - TR - Implementar datePicker para los tipos de campo Fecha

## [0.38.9] - 2026-05-11
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196466 - TR - Configurar la propieda Scroll a componente tabla interprete.

## [0.38.8] - 2026-05-09
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196480 - TR - Ajustar visualización de filas precargadas del componente tabla.

## [0.38.7] - 2026-05-08
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196464 - TR - Implementar validación de filas registradas en tabla dnámica para avanzar de sección.

## [0.38.6] - 2026-05-08
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196245 - TR - Agregar paginador configurado en tabla dinamica interprete.

## [0.38.5] - 2026-05-07
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196340 - TR - Agregar el filtro (columna activo) sobre la consulta de la tabla dinámica.

## [0.38.4] - 2026-05-07
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196295 - TR - Implementación de inicialización del componente Tabla en primera sección del formulario.

## [0.38.3] - 2026-05-06
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196079 - TR - Implementar edición de fila y scroll horizontal para el componente Tabla.

## [0.38.2] - 2026-05-05
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196006 - TR - Implementar recarga de datos para el componente tipo tabla.

## [0.38.1] - 2026-05-05
### [task]
* TR-MOTOR-MODULO-INTERPRETE-196004 - TR - Ajustar proceso de sincronización del componente Tabla (intérprete)

## [0.38.0] - 2026-05-04
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-195768 - HU - Implementar nuevo componente tipo tabla para captura de datos repetibles en formularios (intérprete).

## [0.37.8] - 2026-04-30
### [task]
* TR-MOTOR-MODULO-INTERPRETE-195556 - TR - Implementar Service para la sincronización del componente Tabla

## [0.37.7] - 2026-04-28
### [task]
* TR-MOTOR-MODULO-INTERPRETE-195130 - TR - No permitir el redimensionamiento manual del componente inputtextarea.

## [0.37.6] - 2026-04-20
### [task]
* TR-MOTOR-MODULO-INTERPRETE-193953 - TR - Agregar validador Numero al catálogo de validadores (intérprete)

## [0.37.5] - 2026-04-20
### [task]
* TR-MOTOR-MODULO-INTERPRETE-193894 - TR - Ajuste ver documento en el componente de documentos.

## [0.37.4] - 2026-04-18
### [task]
* TR-MOTOR-MODULO-INTERPRETE-191657 - TR - visualización del nombre de los archivos cargados por el ciudadano

## [0.37.3] - 2026-04-17
### [task]
* TR-MOTOR-MODULO-INTERPRETE-193569 - TR - Generar las vista del componente área de texto para guardado y recuperación del componente.

## [0.37.2] - 2026-04-15
### [task]
* TR-MOTOR-MODULO-INTERPRETE-193295 - TR - Implementar Service para la sincronización del componente Área de Texto (intérprete)

## [0.37.1] - 2026-04-14
### [task]
* TR-MOTOR-MODULO-INTERPRETE-193002 - TR - Crear DTO, Entidad y DAO para el manejo de componente Área de Texto (interprete)

## [0.37.0] - 2026-04-10
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-191652 - TR - Agregar al catalogo la carga de archivos en formatos .csv -Interprete

## [0.36.13] - 2026-03-26
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-190390 - TR - Validación existen datos de usuario en la carga del Formulario.

## [0.36.12] - 2026-03-25
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-190248 - BUG - Intermitencia en Servicio de Curp provoca un error 500.

## [0.36.11] - 2026-03-24
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-190124 - BUG - Corregir botón sin texto en la última seccion de algunos trámites.

## [0.36.10] - 2026-03-24
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-190028 - BUG - Datos de firma del ciudadano se muestran en blanco

## [0.36.9] - 2026-03-23
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-189982 - BUG - Corregir visualización para cerrar modal de protesta de decir la verdad.

## [0.36.8] - 2026-03-20
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-189653 - TR - Ajustar título de columna a 2 líneas.

## [0.36.7] - 2026-03-19
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-189481 - BUG - Corregir eliminación de archivos de la carpeta /documentos del cliente

## [0.36.6] - 2026-03-19
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-189284 - TR - Ajuste validacion firmado de tramites interprete

## [0.36.5] - 2026-03-17
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-189042 - BUG Corregir el filtro de búsqueda sobre el componente de menú desplegable debido a un estilo que está afectando el comportamiento natural de los items.

## [0.36.4] - 2026-03-13
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-188714 - TR - Ajustar vista de modal del histórico de estatus de trámites.

## [0.36.3] - 2026-03-13
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-188657 - TR - Agregar filtro de busqueda para el componente menú desplegable, ajustar textos largos con salto de linea.

## [0.36.2] - 2026-03-12
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-188533 - TR - Ajuste a la revocación de estatus del tramite para guardar en bitacora de movimientos.

## [0.36.1] - 2026-03-12
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-188446 - TR - Ocultar la columna Fecha de envío para trámites de tipo aviso.

## [0.36.0] - 2026-03-10
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-187376 - HU04 - Cuadro de texto al concluir trámite

## [0.35.1] - 2026-03-09
### [Task]
* TR-MOTOR-MODULO-INTERPRETE-187924 - TR - Agregar filtro de reordenamiento a la tabla de trámites, en la bandeja del funcionario y del ciudadano.

## [0.35.00] - 2026-03-09
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-187496 - HU - Agregar campos de histórico de estatus de trámites a archivo ex y bandejas de trámites.portado.

## [0.34.38] - 2026-02-27
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-186558 - TR - Ajuste Validacion fecha de vigencia al Scheduler Consulta estatus Linea captura

## [0.34.37] - 2026-02-26
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-186481 - TR - Correcion de error de descarga en formato LC en bandejas Ciudadano/Funcionario

## [0.34.36] - 2026-02-26
### [BUG]
* BUG-MOTOR-MODULO-INTERPRETE-186470 - BUG - Ajuste descarga de archivos con extension mayuscula

## [0.34.35] - 2026-02-25
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-186366 - TR - Ajuste a captura error conexion LC

## [0.34.34] - 2026-02-24
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-186092 - TR - Habilitar Generar linea de captura bandeja de ciudadano

## [0.34.33] - 2026-02-23
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185876 - TR - Ajuste generación de formato LC solo para la bandeja ciudadano

## [0.34.32] - 2026-02-23
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-186028 - TR - Ajuste a descarga de LC para formulario de tramite y bandeja de ciudadano

## [0.34.31] - 2026-02-23
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185882 - TR - Ajuste a generación fecha de vigencia

## [0.34.30] - 2026-02-21
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185663 - TR - Ajuste a la consulta en la bandeja del ciudadano

## [0.34.29] - 2026-02-21
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185664 - TR - Ajuste a descarga de LC y consulta de tramites

## [0.34.28] - 2026-02-21
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185660 - TR - Agregar configuración para soportar errores de conexión hacia puente y reutilizar solicitud de LC

## [0.34.27] - 2026-02-21
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185660 - TR - Agregar configuración para soportar errores de conexión hacia puente y reutilizar solicitud de LC

## [0.34.26] - 2026-02-20
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185634 - TR - Ajuste Bandejas de tramites para Validar que existe información sobre línea de captura

## [0.34.25] - 2026-02-20
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185398 - TR - Generar registro de peticiones para generar linea de captura y guardar peticiones exitosas y fallidas.

## [0.34.24] - 2026-02-20
### [BUG]
* BUG-MOTOR-MODULO-INTERPRETE-185541 - BUG - No se concluye la revocación de un tramite tipo aviso modulo interprete

## [0.34.23] - 2026-02-19
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185504 - TR - Ajustar las clases, para recibir ejercicion nulo, para la sinconización de Lineas de Captura.

## [0.34.22] - 2026-02-19
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185412 - TR - Aumentar el tamaño a 10MB de subida de archivos del modal Revocacion del Aviso

## [0.34.21] - 2026-02-19
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185398 - TR - Generar registro de peticiones para generar linea de captura y guardar peticiones exitosas y fallidas.

## [0.34.20] - 2026-02-18
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-185278 - TR - Generación de DTO, DAO para guardado de la solicitud de linea de captura (Motor-interprete)).

## [0.34.19] - 2026-02-18
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-184931 - TR - Eliminar datos Dummy del llamado al cliente EstatusLineaCapturaCliente

## [0.34.18] - 2026-02-18
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-184983 - TR - Generar linea de captura con consecutivo

## [0.34.17] - 2026-02-17
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-184983 - TR - Generar linea de captura con consecutivo

## [0.34.16] - 2026-02-16
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-184942 - TR - Actualiza script V94__, no se construye correctamente aplicativo interprete

## [0.34.15] - 2026-02-16
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-184767 - TR - Agregar migración Crear la tabla cat_estatus_solicitud

## [0.34.14] - 2026-02-13
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-184755 - TR - Agregar columna solicitud_linea_captura a la tabla linea_captura motor interprete

## [0.34.13] - 2026-02-13
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-184241 - TR - Agregar columna ruta_documento_linea_captura a la tabla linea_captura

## [0.34.12] - 2026-02-12
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-182619 - TR - Ajustes para generación de la linea de captura con datos configurados

## [0.34.11] - 2026-02-12
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-184572 - TR - Ajustar DetConceptosTramiteDTO para su sincronización desde motor

## [0.34.10] - 2026-02-11
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-182619 - TR - Ajustes para descarga de la linea de captura

## [0.34.9] - 2026-02-11
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-184279 - TR - Generar nuevo Schedule que realice el registro movimientos en trámites para envío mediante webhook cuando se habilita la configuración en el proyecto en interprete.

## [0.34.8] - 2026-02-10
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-184265 - TR - Aumentar tamaño de carga de archivo en modal de prevención de trámites en motor-interprete.

## [0.34.7] - 2026-02-10
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-184252 - BUG - Corregir consulta en schedule para obtener trámites para envío de webhook en proyecto interprete.

## [0.34.6] - 2026-02-10
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-184217 - BUG - Corregir implementación de cliente para envío de registro y actualización de trámites mediante Webhook en motor-interprete.

## [0.34.5] - 2026-02-10
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-184151 - BUG - Corregir error en proceso de sincronización en motor-interprete sección webhook.

## [0.34.4] - 2026-02-09
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-183837 - TR - Implementar regla en motor interprete Proyectos con Línea Captura

## [0.34.3] - 2026-02-08
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-183922 - TR - Ajustes a generación de linea de captura

## [0.34.2] - 2026-02-06
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-183815 - TR - Normalizacion de nombres para Webhook.

## [0.34.1] - 2026-02-06
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-183493 - TR - Implementar carga Applicativo cargar datos seguridad DetSecurityDomainLineasCaptura

## [0.34.0] - 2026-02-05
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-182899 - HU - Generar clase schedule para reenvío de trámites mediante webhook.

## [0.33.0] - 2026-02-05
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-180024 - HU - Habilitar nuevo cliente para consumir servicio REST de notifiacion del terminar un trámite en VDNI en el motor-interprete.

## [0.32.3] - 2026-02-04
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-183538 - TR - Ajustar clases para proceso de sincronización de sección de configuración de líneas de captura en proyecto interprete.

## [0.32.2] - 2026-02-03
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-183287 - TR - Generar migración, servicios y objetos para sincronización de tablas detalle de configuración de líneas de captura en proyecto interprete.

## [0.32.1] - 2026-01-30
### [TASK]
* TR-MOTOR-MODULO-INTERPRETE-183112 - TR - Agregar lógica para carga los datos de la sección de ConfiguracionWehook en la clase que carga en memoria todas las configuraciones del proyecto interprete.

## [0.32.0] - 2026-01-30
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-180878 - HU - Genera clase que realice la generación del formato de LC en Motor-interprete.

## [0.31.2] - 2026-01-30
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-183025 - TR - Generar migración, entidad y DTO para nuevas tablas de control de envío de trámites mediante Webhook en proyecto interprete.

## [0.31.1] - 2026-01-29
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-182992 - TR - Generar migración, entidad, DTO, y nuevo servicio para nueva sección de configuración de Webhook en Motor-Interprete.

## [0.31.0] - 2026-01-28
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-180880 - HU - Generar funcionalidad para las columnas que mostrarán opciones de LC en Bandeja de funcionario y Bandeja de Ciudadano y columna que mostrará el estatus actual de LC.

## [0.30.1] - 2026-01-28
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-182619 - TR - Ajustes para descarga PDF de la linea de captura

## [0.30.0] - 2026-01-26
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-180879 - HU - Generar funcionalidad para agregar opción de descarga de LC al finalizar la captura de un trámite.

## [0.29.2] - 2026-01-23
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-182085 - TR - Modificar Consulta de tramites Bandeja Ciudadano y Supervisor

## [0.29.1] - 2026-01-22
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-181128 - TR - Genera la logica de negocio para orquestar tramites pendiente de pago

## [0.29.0] - 2026-01-22
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-180877 - HU - Generar clase Schedule que valide estatus de pago de las Líneas de captura generadas (Motor-interprete).

## [0.28.2] - 2026-01-21
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-181719 - TR - Ajuste nombre scripts de migración linea de captura

## [0.28.1] - 2026-01-21
### [HU]
* TR-MOTOR-MODULO-INTERPRETE-181525 - TR - Ajuste a entidades de linea de captura

## [0.28.0] - 2026-01-20
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-180875 - HU - Generar cliente REST para realizar petición de Línea de captura al proyecto "Puente-DPA".

## [0.27.0] - 2026-01-19
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-180874 - HU - Generar la migración en el proyecto motor-cliente para la creación de tablas para el registro de las Líneas de captura.

## [0.26.0] - 2026-01-19
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-180876 - HU - Generar cliente REST para realizar petición de estatus de pago de Línea de captura al proyecto "Puente-DPA".

## [0.25.15] - 2025-12-12
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-177049 - BUG - Corregir validación de permisos para eliminar archivos adjuntos en componente carga de archivos.

## [0.25.14] - 2025-12-11
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-176471 - BUG - Corregir opción "Eliminar" que no se habilita cuando se registra un trámite como "Ciudadano", actualmente está deshabilitando la opción de "Eliminar" en la captura de trámite.

## [0.25.13] - 2025-12-11
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-176478 - TR - Habilitar nuevos elementos en componente Carga de archivos en el motor-administrador.

## [0.25.12] - 2025-12-10
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-176824 - TR - Agregar elemento "xls" en catálogo de tipos de archivo en el proyecto intérprete.

## [0.25.11] - 2025-12-10
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-176478 - TR - Habilitar nuevos elementos en componente Carga de archivos en el motor-interprete.

## [0.25.10] - 2025-12-09
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-176474 - TR - Agregar nueva dependencia en el motor-interprete.

## [0.25.4] - 2025-11-28
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-175216 - TR - Al continuar-finalizar, desplazar la pantalla de un formulario al componente obligatorio vacío.

## [0.25.3] - 2025-11-25
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-174646 - TR - Implementación de validación de límite de registros en exportación Excel sobre la bandeja del funcionario.

## [0.25.2] - 2025-11-07
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-172198 - BUG - Cambio copy botón de inicio de captura de formulario en bandeja de ciudadano.

## [0.25.1] - 2025-11-07
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-171972 - BUG - Corregir copys de botón para avanzar, finalizar o firmar un trámite.

## [0.25.0] - 2025-11-05
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-171707 - HU - Realizar cambios en proyecto Intérprete para agregar un nuevo rol de usuario (Administrador General) con los mismos accesos que el rol Administrador. 

## [0.24.15] - 2025-11-04
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-171258 - BUG - Corregir datos de estatus en BandejaTramites y BandejaValidación de Avisos.

## [0.24.14] - 2025-11-04
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-171377 - BUG - Corregir codificación archivo messages - interprete.

## [0.24.13] - 2025-11-04
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-171120 - BUG - Estandarizar tamaño de botones al finalizar trámites. "Cliente".

## [0.24.12] - 2025-11-03
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-171260 - BUG - Corregir token Estatus de pdf generados en Avisos

## [0.24.11] - 2025-10-31
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-170971 - BUG - Corregir codificación archivo messages en "Cliente", parte_2

## [0.24.10] - 2025-10-31
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-170909 - BUG - Corregir codificación archivo messages en "Cliente", parte_1

## [0.24.9] - 2025-10-31
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-170884 - BUG - Corregir descarga de pdf de comprobante de registro cuando existen otros formatos

## [0.24.8] - 2025-10-30
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-170260 - TR - Agregar tokens faltantes para la configuración de la plantilla y al descargarla sean visualizados.

## [0.24.7] - 2025-10-27
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-169955 - TR - Modificar CatEstatusTramiteDTO para recolectar DescripcionPersonalizada. Agegar Script SQL para cambiar longitud del campo DescripcionPersonalizada.

## [0.24.6] - 2025-10-22
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-169290 - TR - Enviar las notificaciones considerando las descripciones personalizadas de los Estatus de Trámites.

## [0.24.5] - 2025-10-21
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-169051 - TR - Implementar la mejora en la parte del cliente en el guardado de la "Configuración de catálogos" utilizando el patrón Facade para manejar la transacción correctamente.

## [0.24.4] - 2025-10-20
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-168374 - BUG - Corregir scroll al enviar mensajes al usuario.

## [0.24.3] - 2025-10-17
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-167938 - BUG - Visualización de configuración de estatus en intérprete.

## [0.24.2] - 2025-10-16
### [TR]
* TR-MOTOR-MODULO-INTERPRETE-168144 - TR - Envío de correo Notificación de estatus de trámite.

## [0.24.1] - 2025-10-16
### [TR]
* TR - Depurar opciones en sección "Gestión del usuario" en proyecto intérprete.

## [0.24.0] - 2025-10-14
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-161789 - HU - Realizar cambios en proyecto intérprete para nueva sección "Configuración de notificaciones".

## [0.23.0] - 2025-10-09
### [HU]
*HU-MOTOR-MODULO-INTERPRETE-161775 - HU - Realizar cambios en proyecto intérprete para visualizar los estatus personalizados en la Configuración de estatus de trámites.

## [0.22.15] - 2025-09-26
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-164351 - BUG - No permite cargar archivo de más de 500 KB cuando el límite es de 2MB.

## [0.22.14] - 2025-09-25
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-163937 - BUG - Error en descarga de archivo de la bandeja de trámites.

## [0.22.13] - 2025-09-25
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-163812 - BUG - Error en captura de formulario para proyecto que no requiere llave.

## [0.22.12] - 2025-09-22
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-163358 - BUG-Mostrar botón de finalizar al estar corrigiendo un trámite en estado En Corrección

## [0.22.11] - 2025-09-18
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-162853 - BUG-Corrregir concatenación de esquema para llamado sw funxión en formularioDAO

## [0.22.10] - 2025-09-12
### [task]
* TR-MOTOR-MODULO-INTERPRETE-161759 - TR - Modificar cliente que envía la información de token con componente complejo para la configuración de formatos.

## [0.22.9] - 2025-09-12
### [task]
* TR-MOTOR-MODULO-INTERPRETE-161668 - TR - Parametrizar las querys utilizadas en Motor Interprete

## [0.22.8] - 2025-09-12
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-162087 - BUG - Corregir la excepción NullPointerException que se produce en el CheckBox Único cuando la sección en donde esta ubicado es saltada, correción de copy cuando Firma el Ciudadano. 

## [0.22.7] - 2025-09-09
### [task]
* TR-MOTOR-MODULO-INTERPRETE-161609 - TR - Agregar propiedad en el DTO de elementos para correcta sincronización.

## [0.22.6] - 2025-09-05
### [FIX]
* BUG-MOTOR-MODULO-INTERPRETE-160977 - BUG - Modificar migración para agregar atributo faltante, en Cliente.

## [0.22.5] - 2025-09-04
### [task]
* TR-MOTOR-MODULO-INTERPRETE-160602 - TR - Crear migración para manejo de atributos de componentes complejos.

## [0.22.4] - 2025-09-04
### [task
* TR-MOTOR-MODULO-INTERPRETE-160769 - TR - Agregar la funcionalidad para permitir ejecutar los operadores de IGUAL, DIFERENTE, MAYOR QUE, MENOR QUE ,MAYOR O IGUAL QUE,  MENOR IGUAL QUE para el componente CheckBoxGrupo.

## [0.22.3] - 2025-09-01
### [task
* TR-MOTOR-MODULO-INTERPRETE-160118 - TR-Cambiar querys sql para que se realicen con parámetros (intérprete)

## [0.22.2] - 2025-08-26
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-159183 - BUG - Corregir error en proyecto intérprete al ingresar apóstrofe en la captura de texto en el componente campo de texto.

## [0.22.1] - 2025-08-26
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-159137 - BUG - Corrección de error en el registro de observaciones en la validación de trámites, (corregir problema de guardado de observaciones por ingresar apóstrofe).

## [0.22.0] - 2025-08-25
### [add]
*HU-MOTOR-MODULO-INTERPRETE-156320 - HU - Actualizar grafica base V3.

## [0.21.2] - 2025-08-25
### [task]
*TR-MOTOR-MODULO-INTERPRETE-158877 - TR - Modificar la regla de negocio para que si se cumple la condición se permita visualizar la sección condicionada.

## [0.21.1] - 2025-08-22
### [task]
*TR-MOTOR-MODULO-INTERPRETE-158383 - TR - Añadir funcionalidad IN y NOT IN para los componentes RadioBoton y Menú Desplegable.

## [0.21.0] - 2025-08-21
### [add]
* HU-MOTOR-MODULO-INTERPRETE-158082 - HU - Cambio de copies bandeja solicitudes

## [0.20.1] - 2025-08-21
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-158328 - BUG-Corregir proceso de sincronización para condiciones eliminadas lógicamente (Intérprete)

## [0.20.0] - 2025-08-21
### [add]
* HU-MOTOR-MODULO-INTERPRETE-158081 - HU-Actualizar los copies en el Home.

## [0.19.4] - 2025-08-21
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-158200 - BUG - Validar valor actual de la respuesta cuando el campo de la opción otro del componente radioboton esta vacio.

## [0.19.3] - 2025-08-21
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-158165 - BUG-Corregir estrutura de condiciones para el proceso de sincronización (Intérprete)

## [0.19.2] - 2025-08-20
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-158144 - BUG - Corrección de las condiciones lógicas para el flujo de secciones condicionales.

## [0.19.1] - 2025-08-20
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-157933 - BUG-Error al descargar Aviso

## [0.19.0] - 2025-08-19
### [HU]
*HU-MOTOR-MODULO-INTERPRETE-157464 - HU - Generar funcionalidad del lado del Interprete para secciones condicionales.

## [0.18.3] - 2025-08-17
### [task]
*TR-MOTOR-MODULO-INTERPRETE-156844 - TR - Realizar ajuste a proceso de sincronización para que envíe la información del nuevo apartado de "Secciones condicionales" en Interprete

## [0.18.2] - 2025-08-16
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-157408 - BUG - Corregir estilo en componente Radiobotón, para que el texto no deje un espacio en blanco para textos extensos.

## [0.18.1] - 2025-08-15
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-157393 - BUG - En las opciones para descargar "Formato de registro", corregir que opción no sea mostrada cuando el trámite esté estatus Captura o Corrección.

## [0.18.0] - 2025-08-15
### [add]
*HU-MOTOR-MODULO-INTERPRETE-157160 - HU-Consultar el formato de registro después de concluir su captura.

## [0.17.9] - 2025-08-14
### [task]
*TR-MOTOR-MODULO-INTERPRETE-157003 - TR - Para el Formato Registro habilitar en el Token la opción de Componente en proyecto Interprete.

## [0.17.8] - 2025-08-14
### [task]
*TR-MOTOR-MODULO-INTERPRETE-156840 - TR - Generar nueva tabla para registro de configuración de condiciones en Interprete.

## [0.17.7] - 2025-08-13
### [task]
*TR-MOTOR-MODULO-INTERPRETE-156839 - TR - Generar nuevo catálogo de operadores para secciones condicionales en Interprete.

## [0.17.6] - 2025-08-12
### [task]
*TR-MOTOR-MODULO-INTERPRETE-156573 - TR - Ajustar comportamiento cliente con nueva bandera.

## [0.17.5] - 2025-08-12
### [task]
*TR-MOTOR-MODULO-INTERPRETE-156571 - TR-Realizar ajuste de sincronización en Motor-Interprete para bandera de captura de trámites

## [0.17.4] - 2025-08-11
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-156516 - BUG - Quitar botón de Finalizar en modales.

## [0.17.3] - 2025-08-08
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-155793 - BUG - Quitar botón de Finalizar en modales.

## [0.17.2] - 2025-08-08
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-156000 - BUG - Corregir error reportando cliente "https://www.bienestarmujeres.atdt.gob.mx/", a registrar nuevo trámite en ambiente productivo.

## [0.17.1] - 2025-08-07
### [task]
*TR-MOTOR-MODULO-INTERPRETE-155944 - TR - Preparar ear para Motor-interprete con el ajuste de cambio de dominio adyt por atdt.

## [0.17.0] - 2025-08-06
### [HU]
*HU-MOTOR-MODULO-INTERPRETE-155601 - HU - Identificar mejora en el flujo para Popup de firma.

## [0.16.8] - 2025-08-06
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-155654 - BUG - Mejora para desbordamiento de información en Menú desplegable .

## [0.16.7] - 2025-08-05
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-155532 - BUG - Corregir estilos de los botones de accion y descarga en la bandeja del Ciudadano y bandeja del Funcionario. 

## [0.16.6] - 2025-08-05
### [task]
*TR-MOTOR-MODULO-INTERPRETE-155458 - TR - Mensaje si no hay archivos cargados (Cliente).

## [0.16.5] - 2025-08-03
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-154112 - BUG - Realizar corrección en acción "Sincronizar proyecto" del lado del interprete, para que esta acción sea manejada como 1 solo movimiento.

## [0.16.4] - 2025-08-01
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-154311 - BUG-Formato erróneo de Registro, sin Formato de conclusión

## [0.16.3] - 2025-08-01
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-154588 - BUG-Motor no envía correos de registro.

## [0.16.2] - 2025-08-01
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-154969 - BUG - Corregir estilos en el botón de descarga de resolución.

## [0.16.1] - 2025-08-01
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-154534 - BUG - Se dibuja un cuadro azul, el cual sobre sale del icono del documento.

## [0.16.0] - 2025-07-31
### [HU]
*HU-MOTOR-MODULO-INTERPRETE-154312 - HU - Subir hasta 10MB en el archivo de resolución.

## [0.15.6] - 2025-07-30
### [fix]
*TR-MOTOR-MODULO-INTERPRETE-154584 - BUG - Corrección de error 500 al quitar persona moral.

## [0.15.5] - 2025-07-30
### [task]
*TR-MOTOR-MODULO-INTERPRETE-154532 - TR - Actualizar README de proyecto para nueva configuración en intérprete para carga de archivos de 20MB.

## [0.15.4] - 2025-07-28
### [task]
*TR-MOTOR-MODULO-INTERPRETE-153448 - TR-Implementar Rol de Administrador de Datos Técnicos para sincronización de proyectos.

## [0.15.3] - 2025-07-28
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-154048 - BUG - Corrección a método de la sincronización del lado del cliente que inicia la Sincronización.

## [0.15.2] - 2025-07-28
### [task]
*TR-MOTOR-MODULO-INTERPRETE-154074 - TR- Carga de documentos de 20mb - Cliente. 

## [0.15.1] - 2025-07-22
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-153163 - BUG - Corregir los mensajes de validación de resolución del trámite para que sean visualizados dentro de la ventana modal.

## [0.15.0] - 2025-07-18
### [HU]
*HU-MOTOR-MODULO-INTERPRETE-152298 - HU- Mejoras en la sincronización cron interprete.

## [0.14.0] - 2025-07-18
### [HU]
*HU-MOTOR-MODULO-INTERPRETE-152305 - HU- Actualizar proceso de sincronización de Cifras control en el intérprete para integrar el dato de versión de ear.

## [0.13.8] - 2025-07-17
### [Task]
*TR-MOTOR-MODULO-INTERPRETE-152468 - TR - Agregar dependencia y configuración de nuevo valor para obtener la versión de GIT y poderla utilizar en la sincronización de cifras control. 

## [0.13.7] - 2025-07-16
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-152138 - BUG - Corregir error en dato de Sexo del componenete Datos Personales con Llave y corregir valor null en la bandera HabilitaResolucion. 

## [0.13.6] - 2025-07-16
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-152136 - BUG - Corregir problema de sincronización en ambiente PROD.

## [0.13.5] - 2025-07-15
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-152022 - BUG - Corregir funcionalidad al momento de seleccionar un trámite rechazado, para permitir únicamente del radioboton seleccionar la resolución negativa.

## [0.13.4] - 2025-07-15
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-151934 - BUG-Corregir estilo del botón descarga Resolución

## [0.13.3] - 2025-07-15
### [fix]
*BUG-MOTOR-MODULO-INTERPRETE-151902 - BUG-Corregir la descarga del formato de resolución.


## [0.13.2] - 2025-07-14
### [task]
*TR-MOTOR-MODULO-INTERPRETE-151431 - TR - Desarrollar el flujo para la resolución de trámites de aceptación y rechazo. 

## [0.13.1] - 2025-07-12
### [task]
*TR-MOTOR-MODULO-INTERPRETE-151747 - TR - Eliminar Banner "Donar salva vidas" de la pantalla de registro de trámites.

## [0.13.0] - 2025-07-11
### [HU]
*HU-MOTOR-MODULO-INTERPRETE-150967 - HU - Adm Motor - Correo electrónico de resolución

## [0.12.0] - 2025-07-11
### [HU]
*HU-MOTOR-MODULO-INTERPRETE-151441 - HU - Descarga de la resolución

## [0.11.0] - 2025-07-11
### [HU]
*HU-MOTOR-MODULO-INTERPRETE-150886 - HU - Interprete-Contar con la opción de carga de Resolución.

## [0.10.2] - 2025-07-10
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-151246 - BUG - Error de descarga de Formato de Concluido - Expedición de Certificado de Menaje de Casa a Mexicanos.

## [0.10.1] - 2025-07-08
### [task]
* TR-MOTOR-MODULO-INTERPRETE-151266 - TR - Realizar ajuste a servicio que valida la disponibilidad en proyecto interprete para identificar si es la primera vez que se sincronizará un proyecto.

## [0.10.0] - 2025-07-08
### [HU]
*HU-MOTOR-MODULO-INTERPRETE-150079 - HU - eFirma no coincide al firmar documento de aceptación o rechazo.

## [0.9.17] - 2025-07-08
### [task]
* TR-MOTOR-MODULO-INTERPRETE-150907 - TR - Preparar Intérprete para la sincronización de nuevos campos para resolución

## [0.9.16] - 2025-07-04
### [task]
* TR-MOTOR-MODULO-INTERPRETE-150477 - TR - Actualizar readme para deshabilitar registro de toda sentencia sql generadas por hibernate.

## [0.9.15] - 2025-07-04
### [BUG]
* BUG-MOTOR-MODULO-INTERPRETE-150469 - BUG -Corregir error detectado en log, por registro de RFC de longitud 12.

## [0.9.14] - 2025-07-03
### [task]
* TR-MOTOR-MODULO-INTERPRETE-150359 - TR - Revertir avance de funcionalidad generada para el requerimiento de "Documentación personalizada" en el proyecto interprete.

## [0.9.13] - 2025-07-03
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-150080 - BUG - Eliminar imagen perteneciente a CDMX.

## [0.9.12] - 2025-07-02
### [task]
* TR-MOTOR-MODULO-INTERPRETE-150206 - TR - Agregar funcionalidad para identificar cuando en los intérpretes se debe utilizar el flujo de "Documentos de resolución personalizados".

## [0.9.11] - 2025-07-01
### [task]
* TR-MOTOR-MODULO-INTERPRETE-149971 - TR-Proceso de sincronización al interprete para enviar la variable del Formato personalizado de resolución

## [0.9.10] - 2025-06-30
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-149855 - BUG - Corregir configuración de archivo pom para funcionalidad de solicitud de sincronización desde el cliente.

## [0.9.9] - 2025-06-30
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-149811 - BUG - Mostrar sección de datos de firma en el detalle del trámite, únicamente si el proyecto está habilitado para que firme el ciudadano.

## [0.9.8] - 2025-06-19
### [task]
* TR-MOTOR-MODULO-INTERPRETE-148180 - TR - Generar cliente en proyecto intérprete para solicitar peticiones de sincronización al motor transaccional.

## [0.9.7] - 2025-06-27
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-149235 - BUG - Seccion de persona moral cuando no tiene configuración

## [0.9.6] - 2025-06-27
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-149478 - BUG - Corregir comportamiento en intérprete cuando la sección de Firma digital es eliminada en el motor y se sincroniza por segunda vez el proyecto.

## [0.9.5] - 2025-06-26
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-149227 - BUG - Error backoffice cuando no tienen Llave MX.

## [0.9.4] - 2025-06-26
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-149179 - BUG - Error 500 actualizar diseño.

## [0.9.3] - 2025-06-25
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-149108 - BUG - Se duplica Persona Moral y/o no se ven todas las PM.

## [0.9.2] - 2025-06-13
### [task]
* BUG-MOTOR-MODULO-INTERPRETE-147208 - BUG - Corregir acceso de rol "Consulta" para que pueda ingresar de manera normal al intérprete.

## [0.9.1] - 2025-06-10
### [task]
* TR-MOTOR-MODULO-INTERPRETE-146831 - TR - Agregar migración para permitir configuración de roles para poder acceder a trámites en el intérprete.

## [0.9.0] - 2025-06-10
### [HU]
*HU-MOTOR-MODULO-INTERPRETE-145684 - HU - El interprete verifica si el acceso esta restringido.

## [0.8.1] - 2025-06-06
### [fix]
* BUG - BUG - Modificar tipo de mensaje cuando no coincide firmante con datos del usuario que captura en proyecto intérprete, ajustar copy en validación.

## [0.8.0] - 2025-06-05
### [HU]
* HU - Contar con la validación de RFC o nombre completo vs Firma.

## [0.7.1] - 2025-06-05
### [fix]
* BUG - Corregir problema en guardado de formulario en proyecto intérprete, agregar nuevo campo "Sexo", en subsecciones que utilizan componente Datos Personales Llave.

## [0.7.0] - 2025-06-05
### [HU]
* HU- Contar con la opción de salir del aplicativo desde la pantalla de selección de tipo de persona.

## [0.6.1] - 2025-05-29
### [fix]
* BUG - Corregir funcionalidad para que en la última sección no sea mostrado el panel de firma cuando en el motor no se configura.

## [0.6.0] - 2025-05-29
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-144840 - HU- Realizar el ajuste para  que le interprete se visualice el dato de sexo desde el  componente de Datos Personales Llave MX. 

## [0.5.17] - 2025-05-29
### [fix]
* BUG - Solicitar token de cadena digital cuando se selecciona la plantilla Comprobante registros, y se tiene la configuración de firma.

## [0.5.16] - 2025-05-27
### [fix]
* BUG - Realizar corrección en proyecto intérprete para que los textos de Requisitos se ajusten al tamaño del panel que los contiene.

## [0.5.15] - 2025-05-27
### [task]
* TR-MOTOR-MODULO-INTERPRETE-144591 - TR - Generar funcionalidad para que el firmado del usuario Back (Supervisor, Operador) esté de acuerdo a como se le configuró cada tipo de plantilla.

## [0.5.14] - 2025-05-26
### [fix]
* BUG - Corregir visualización de atributo "Correo electrónico" en componente de Datos con Llave, actualmente se sobrepone con el campo siguiente.

## [0.5.13] - 2025-05-24
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-144385 - BUG - Ocultar sección de datos de firma, cuando un trámite aún está en captura.

## [0.5.12] - 2025-05-24
### [task]
* TR-MOTOR-MODULO-INTERPRETE-144384 - TR - Corregir opciones que se sobreponen en la bandeja del rol Administrador, realizar ajustes para mostrar los datos de firma de trámites capturados en última sección.

## [0.5.11] - 2025-05-24
### [task]
* BUG-MOTOR-MODULO-INTERPRETE-144368 - BUG - Corregir realizar ajuste para que los formularios que tengan un componente "Datos de persona moral", al consultarlo muestre Copy o Campos de persona moral de acuerdo a como está registrado el trámite.

## [0.5.10] - 2025-05-23
### [task]
* TR-MOTOR-MODULO-INTERPRETE-144367 - TR - Agregar funcionalidad en el intérprete para que en la última sección muestre la información de firma del ciudadano si el proyecto tiene la configuración para permitir firmado al registrar trámites.

## [0.5.9] - 2025-05-22
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-144166 - BUG - Corregir validación en el intérprete que determina si el proyecto sincronizado requiere de firma o no.

## [0.5.8] - 2025-05-22
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-144160 - BUG - Corregir error en proceso de sincronización del detalle de firma, para el registro de firma ciudadano.

## [0.5.7] - 2025-05-22
### [task]
* TR-MOTOR-MODULO-INTERPRETE-144066 - TR - Agregar elemento "Estado" en componente "Datos domicilio" en el intérprete, ajustar copy Municipio/Alcaldía.

## [0.5.6] - 2025-05-22
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-144098 - BUG - Realizar corrección en flujo de firmado, para que la firma sea solicitada a cualquier usuario no importando si tiene algún rol de usuario Back, siempre y cuando se encuentre configurado el proyecto para firmar por parte de un Ciudadano.

## [0.5.5] - 2025-05-22
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-143724 - BUG- No se obtiene/guarda la solicitud cuando es persona moral

## [0.5.4] - 2025-05-21
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-143984 - BUG - Se agrega secuencia para registro de trámites con firma electrónica.

## [0.5.3] - 2025-05-21
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-143982 - BUG - Realizar corrección de nombrado de atributo FirmaCiudadano en DTO, DAO y Clases que lo utilizan para homologar nombrado con el Motor Administrador.

## [0.5.2] - 2025-05-21
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-143937 - BUG - Deshabilitar opciones de Menú de usuario, cuando la cuenta llave tenga asociadas personas morales y no seleccione nada en la pantalla de elección de ciudadano o una persona moral de listado.

## [0.5.1] - 2025-05-21
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-143964 - BUG - Ajustar migración en proyecto intérprete para agregar nuevos campos a tabla "tramite_firma_electronica".

## [0.5.0] - 2025-05-20
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-143410 - HU - Realizar a ajuste en registro de trámites del proyecto intérprete para incorporar como parte del registro de trámites, pasar por el firmado, esto cuando así se tenga configurado en el Motor.

## [0.4.7] - 2025-05-20
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-143490 - BUG - Mejorar diseño de pausa en el Título.

## [0.4.6] - 2025-05-13
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-142967 - BUG - Cambiar componente datos de persona moral, por Copy cuando la captura de trámite no se esté realizando como persona moral y el formulario tenga configurados componentes de persona moral.

## [0.4.5] - 2025-05-13
### [task]
* TR-MOTOR-MODULO-INTERPRETE-142529 - TR - Realizar cambios en proyecto cliente para habilitar nuevamente el firmado de trámites.

## [0.4.4] - 2025-05-12
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-142558 - BUG - Corregir en proyecto intérprete componente "Datos persona moral" cuando en la captura de trámite se elige que sea registrado dicho trámite como "Ciudadano" y no persona moral.

## [0.4.3] - 2025-05-09
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-140935 - HU - Generar funcionalidad en proyecto intérprete para sincronización de nuevo componente "Datos persona moral"

## [0.4.2] - 2025-05-08
### [task]
* TR-MOTOR-MODULO-INTERPRETE-142372 - TR - Integrar lógica de clase "PersonasMoralesBean" dentro de clase AuthenticatorBean.

## [0.4.1] - 2025-05-06
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-141737 - BUG - Configurar perfiles en pom para uso de servicio de persona moral.

## [0.4.0] - 2025-05-01
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-140797 - HU - Implementar funcionalidad para registro de trámites como persona moral.

## [0.3.7] - 2025-04-29
### [task]
* TR-MOTOR-MODULO-INTERPRETE-140847 - TR - Generar nuevas tablas en Interprete para nuevo flujo de registro de trámites con persona moral.

## [0.3.6] - 2025-04-22
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-139879 - BUG - Realizar corrección en proyecto intérprete para el escenario en el que un componente de tipo listado no tiene elementos.

## [0.3.5] - 2025-04-17
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-139370 - BUG - Corregir problema de desbordamiento de textos en home, interpretes.

## [0.3.4] - 2025-04-16
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-137266 - HU-Funcionalidad motor e interprete para Respuestas archivo PDF

## [0.3.3] - 2025-04-16
### [task]
* TR-MOTOR-MODULO-INTERPRETE-139239 - TR - Actualizar imagen y corregir problema de mensajes que se muestran en apartado de "Reiniciar cliente" en proyecto interprete.

## [0.3.2] - 2025-04-16
### [task]
* TR-MOTOR-MODULO-INTERPRETE-139187 - TR - Corregir flujo de formato de captura de trámite.

## [0.3.1] - 2025-04-11
### [task]
* TR-MOTOR-MODULO-INTERPRETE-138581 - TR - Corregir estilos a nivel de componentes en proyecto intérprete.

## [0.3.0] - 2025-04-09
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-137179 - HU - Integrar en la sección del Home la sección para personalizar el mensaje de Pausa

## [0.2.11] - 2025-04-04
### [task]
* TR - Realizar migración para eliminar sección de detalle de pausa y realizar ajustes a tabla principal de detalle de home para incorporar datos de pausa. (Proyecto interprete)

## [0.2.10] - 2025-04-04
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-137329 - BUG - Corregir error en reinicio de instancia cliente. 

## [0.2.9] - 2025-04-02
### [task]
* TR - Configurar sincronización cada hora en proyecto intérprete.

## [0.2.8] - 2025-03-27
### [task]
* TR - Realizar cambio en perfil PROD para apuntar a www.api.llave.gob.mx

## [0.2.7] - 2025-03-26
### [HU]
* HU - Cambiar paquetes y pom's

## [0.2.6] - 2025-03-14
### [task]
* TR-MOTOR-MODULO-INTERPRETE-134692 - TR - Configurar perfiles LOCAL, DEV, VAL, STAGING para que apunten al ambiente VAL de llave.

## [0.2.5] - 2025-03-12
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-134356 - Error en generación de comprobante de registro para usuarios sin correo electrónico de cuenta llave.

## [0.2.4] - 2025-01-18
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-131392 - BUG - NO se muestra el mensaje de pausa por defecto

## [0.2.3] - 2025-01-17
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-131001 - BUG - No se visualiza completo el mensaje de pausa que esta por defecto

## [0.2.2] - 2025-01-13
### [fix]
* BUG-MOTOR-MODULO-INTERPRETE-130904 - BUG - No se visualiza el mensaje de pausa estandar

## [0.2.1] - 2025-01-12
### [fix]
* HU-MOTOR-MODULO-INTERPRETE-129852 - HU - Se respeta el estilo de motor 

## [0.2.0] - 2025-01-28
### [HU]
* HU-MOTOR-MODULO-INTERPRETE-128753 - HU - Se agrega nueva funcionalidad de la modal Pausa

## [0.1.3] - 2025-01-22
### [fix]
* BUG-MOTOR-TRANSACCIONAL-127746 - BUG - Integrar lo nuevo del Manual Llave MX, para Corregir inicio de sesión en Motor-Administrador y clientes.

## [0.1.2] - 2025-01-02
### [fix]
* BUG-MOTOR-TRANSACCIONAL-125818 - TR - Configurar nuevos ambientes Staging (Motor-Admin y Clientes).

## [0.1.1] - 2024-12-28
### [fix]
* BUG-MOTOR-TRANSACCIONAL-125441 - BUG - Corregir problema en funcionalidad para reiniciar clientes.

## [0.1.0] - 2024-12-12
### [add]
* HU-MOTOR-TRANSACCIONAL-124319 - HU - Generar un nuevo esquema en la base de datos de los clientes para almacenar los datos de seguridad de dominio.

## [0.0.9] - 2024-12-03
### [fix]
* BUG-MOTOR-TRANSACCIONAL-122553 - BUG - Corregir variables de ambiente DEV y VAL para carga de archivos dinámicos.

## [0.0.8] - 2024-11-21
### [fix]
* BUG-MOTOR-TRANSACCIONAL-121967 - BUG - Corregir problema en cliente de cierre de sesión en Motor-Admin  y Interprete.

## [0.0.7] - 2024-11-21
### [fix]
* BUG-MOTOR-TRANSACCIONAL-121944 - BUG - Corregir variables pom para ambiente DEV y VAL del motor-cliente.

## [0.0.6] - 2024-11-20
### [fix]
* TR-MOTOR-TRANSACCIONAL-121862 - BUG - Corregir estilos en home del motor - interprete.

## [0.0.5] - 2024-11-20
### [task]
* TR-MOTOR-TRANSACCIONAL-121621 - Ajuste en el SSO para que te lleve hasta el registro.

## [0.0.4] - 2024-11-19
### [task]
* BUG-MOTOR-TRANSACCIONAL-121601 - Inicio de sesión en Motor Adm desarrollo

## [0.0.3] - 2024-11-11
### [task]
* TR-MOTOR-TRANSACCIONAL-120506 - Actualizar información del catálogo de Dependencias en proyecto cliente

## [0.0.2] - 2024-11-05
### [task]
* TR-MOTOR-TRANSACCIONAL-119919 - Actualización gráfica base en Motor interpete

## [0.0.1] - 2024-11-01
### [task]
* TR-MOTOR-MODULO-INTERPRETE-MRTEMPLATE - Adición de archivo CHANGELOG y Template de Merge Request

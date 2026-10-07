-- No se necesita crear el schema porque flyway lo va a generar antes para guardar su metadata
--CREATE SCHEMA motor_interprete;

CREATE TABLE motor_interprete.cat_estatus_tramite (
	id_estatus_tramite int4 NOT NULL,
	descripcion varchar(60) NOT NULL,
	CONSTRAINT cat_estatus_tramite_pk PRIMARY KEY (id_estatus_tramite)
);

CREATE TABLE motor_interprete.cat_motivos_pausa (
	 id_motivo int4 NOT NULL,
	 descripcion varchar(100) NOT NULL,
	 descripcion_motivo varchar(200) NOT NULL,
	 CONSTRAINT cat_motivos_pausa_pk PRIMARY KEY (id_motivo)
);

CREATE TABLE motor_interprete.cat_secciones_proyecto (
	id_seccion_proyecto int4 NOT NULL,
	descripcion varchar(100) NOT NULL,
	CONSTRAINT cat_secciones_proyecto_pk PRIMARY KEY (id_seccion_proyecto)
);

CREATE TABLE motor_interprete.cat_estatus_proyecto (
	id_estatus_proyecto int4 NOT NULL,
	descripcion varchar(60) NOT NULL,
	CONSTRAINT cat_estatus_proyecto_pk PRIMARY KEY (id_estatus_proyecto)
);

CREATE TABLE motor_interprete.cat_tipo_proyecto (
	id_tipo_proyecto int4 NOT NULL,
	descripcion varchar(20) NOT NULL,
	CONSTRAINT cat_tipo_proyecto_pk PRIMARY KEY (id_tipo_proyecto)
);

CREATE TABLE motor_interprete.cat_tipo_costo (
	id_tipo_costo int4 NOT NULL,
	descripcion varchar(20) NOT NULL,
	activo bool NOT NULL,					
	CONSTRAINT cat_tipo_costo_pk PRIMARY KEY (id_tipo_costo)
);
COMMENT ON COLUMN motor_interprete.cat_tipo_costo.activo IS 'Bandera para eliminación lógica de tipos de costo';

CREATE TABLE motor_interprete.cat_tipo_archivo (
	id_tipo_archivo int4 NOT NULL,
	descripcion varchar(10) NOT NULL,
	activo bool NOT NULL,					
	CONSTRAINT cat_tipo_archivo_pk PRIMARY KEY (id_tipo_archivo)
);
COMMENT ON COLUMN motor_interprete.cat_tipo_archivo.activo IS 'Bandera para eliminación lógica de tipos de archivo';

CREATE TABLE motor_interprete.cat_dependencia (
	id_dependencia int4 NOT NULL,
	descripcion varchar(150) NOT NULL,
	activo bool NOT NULL,					
	CONSTRAINT cat_dependencia_pk PRIMARY KEY (id_dependencia)
);
COMMENT ON COLUMN motor_interprete.cat_dependencia.activo IS 'Bandera para eliminación lógica de Dependencias';

CREATE TABLE motor_interprete.cat_tamanio_archivos (
	id_tamanio_archivo int4 NOT NULL,
	descripcion varchar(100) NOT NULL,
	activo bool NOT NULL,					
	CONSTRAINT cat_tamanio_archivos_pk PRIMARY KEY (id_tamanio_archivo)
);
COMMENT ON COLUMN motor_interprete.cat_tamanio_archivos.activo IS 'Bandera para eliminación lógica de tamaños de archivos';

CREATE TABLE motor_interprete.cat_origen_llenado (
	id_origen_llenado int4 NOT NULL,
	descripcion varchar(100) NOT NULL,
	aplica_campo_texto bool NOT NULL,			
	aplica_radio_boton bool NOT NULL,			
	aplica_menu_desplegable bool NOT NULL,			
	activo bool NOT NULL,					
	orden int4 NOT NULL,
	CONSTRAINT cat_origen_llenado_pk PRIMARY KEY (id_origen_llenado)
);
COMMENT ON COLUMN motor_interprete.cat_origen_llenado.activo IS 'Bandera para eliminación lógica de Origenes de llenado';
COMMENT ON COLUMN motor_interprete.cat_origen_llenado.aplica_campo_texto IS 'Bandera que indica si el elemento debe mostrarse como opción para el componente de campo de texto';
COMMENT ON COLUMN motor_interprete.cat_origen_llenado.aplica_radio_boton IS 'Bandera que indica si el elemento debe mostrarse como opción para el componente radio botón';
COMMENT ON COLUMN motor_interprete.cat_origen_llenado.aplica_menu_desplegable IS 'Bandera que indica si el elemento debe mostrarse como opción para el componente menú desplegable';
COMMENT ON COLUMN motor_interprete.cat_origen_llenado.orden IS 'Campo que indica el orden de opciones.';

CREATE TABLE motor_interprete.cat_validadores (
	id_validador int4 NOT NULL,
	nombre_validador varchar(100) NOT NULL,
	activo bool NOT NULL,					
	CONSTRAINT cat_validadores_pk PRIMARY KEY (id_validador)
);
COMMENT ON COLUMN motor_interprete.cat_validadores.activo IS 'Bandera para eliminación lógica de Validadores';

CREATE TABLE motor_interprete.cat_tipo_componente (
	id_tipo_componente int4 NOT NULL,
	descripcion varchar(30) NOT NULL,
	avanzado bool NOT NULL,					
	activo bool NOT NULL,					
	CONSTRAINT cat_tipo_componente_pk PRIMARY KEY (id_tipo_componente)
);
COMMENT ON COLUMN motor_interprete.cat_tipo_componente.activo IS 'Bandera para eliminación lógica de Tipos de Componente';

CREATE TABLE motor_interprete.cat_origen_token (
  id_origen_token int4 NOT NULL,
  descripcion varchar(100) NOT NULL,
  activo bool NOT NULL,          
  CONSTRAINT cat_origen_token_pk PRIMARY KEY (id_origen_token)
);
COMMENT ON COLUMN motor_interprete.cat_origen_token.activo IS 'Bandera para eliminación lógica de origenes de token';

CREATE TABLE motor_interprete.usuario (
	id_usuario_llave_cdmx int4 NOT NULL,
	nombre varchar(60) NOT NULL,
	primer_apellido varchar(60) NOT NULL,
	segundo_apellido varchar(60) NULL,
	curp varchar(18) NOT NULL,
	telefono varchar(10) NULL,
	correo varchar(60) NOT NULL,
	CONSTRAINT usuario_pk PRIMARY KEY (id_usuario_llave_cdmx)
);

CREATE TABLE motor_interprete.proyecto (
	id_proyecto bigserial NOT NULL,
	nombre_proyecto varchar(150) NOT NULL,
	id_tipo_proyecto int4 NOT NULL,
	id_dependencia int4 NOT NULL,
	ruta_archivo_logotipo varchar(200) NOT NULL,
	ruta_archivo_favicon varchar(200) NOT NULL,
	habilita_captcha bool NOT NULL,
	habilita_acceso_llave bool NOT NULL,
	habilita_pago_linea bool NOT NULL,
	habilita_gestion_usuarios bool NOT NULL,
	habilita_firma_digital bool NOT NULL,
	habilita_detalle_legales bool NOT NULL,
	habilita_analytics bool NOT NULL,
	habilita_security_domain bool NOT NULL,
	id_estatus_proyecto int4 NOT NULL,
	id_usuario_llave_cdmx int8 NOT NULL,							
	fecha_creacion timestamp NOT NULL,
	CONSTRAINT proyecto_pk PRIMARY KEY (id_proyecto)
);
												
CREATE INDEX proyecto_nombre_proyecto_idx ON motor_interprete.proyecto USING btree (nombre_proyecto);
CREATE INDEX proyecto_id_estatus_proyecto_idx ON motor_interprete.proyecto USING btree (id_estatus_proyecto);
CREATE INDEX proyecto_id_dependencia_idx ON motor_interprete.proyecto USING btree (id_dependencia);
CREATE INDEX proyecto_id_tipo_proyecto_idx ON motor_interprete.proyecto USING btree (id_tipo_proyecto);
CREATE INDEX proyecto_id_usuario_llave_cdmx_idx ON motor_interprete.proyecto USING btree (id_usuario_llave_cdmx);

ALTER TABLE motor_interprete.proyecto ADD CONSTRAINT proyecto_cat_tipo_proyecto_fk FOREIGN KEY (id_tipo_proyecto) REFERENCES motor_interprete.cat_tipo_proyecto(id_tipo_proyecto);
ALTER TABLE motor_interprete.proyecto ADD CONSTRAINT proyecto_cat_dependencia_fk FOREIGN KEY (id_dependencia) REFERENCES motor_interprete.cat_dependencia(id_dependencia);
ALTER TABLE motor_interprete.proyecto ADD CONSTRAINT proyecto_cat_estatus_proyecto_fk FOREIGN KEY (id_estatus_proyecto) REFERENCES motor_interprete.cat_estatus_proyecto(id_estatus_proyecto);

CREATE TABLE motor_interprete.det_home (
	id_detalle_home bigserial NOT NULL,
	id_proyecto bigserial NOT NULL,
	ruta_archivo_logotipo varchar(200) NOT NULL,
	habilita_notificacion bool NOT NULL,
	descripcion_notificacion varchar(200) NULL,		
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL,					
	seccion_sincronizada bool NOT NULL,			
	CONSTRAINT detalle_home_pk PRIMARY KEY (id_detalle_home)
);
COMMENT ON COLUMN motor_interprete.det_home.activo IS 'Bandera para eliminación lógica de detalle de home';
COMMENT ON COLUMN motor_interprete.det_home.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.det_home ADD CONSTRAINT detalle_detalle_home_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

CREATE TABLE motor_interprete.det_programa_social (
	id_detalle_programa bigserial NOT NULL,
	id_detalle_home bigserial NOT NULL,
	habilita_ciclo_programa bool NOT NULL,
	descripcion_ciclo_programa varchar(60) NULL,	
	descripcion_tipo_apoyo varchar(200) NULL,			
	descripcion_duracion_apoyo varchar(60) NULL,	
	habilita_programa_simultaneo bool NOT NULL,	
	CONSTRAINT detalle_programa_social_pk PRIMARY KEY (id_detalle_programa)
);
	
ALTER TABLE motor_interprete.det_programa_social ADD CONSTRAINT detalle_programa_social_fk FOREIGN KEY (id_detalle_home) REFERENCES motor_interprete.det_home(id_detalle_home);

CREATE TABLE motor_interprete.det_objetivos_programa (
	id_objetivo bigserial NOT NULL,
	id_detalle_home bigserial NOT NULL,	
	descripcion_objetivo varchar(200) NOT NULL,	
	orden int4 NOT NULL,
	activo bool NOT NULL,						
	CONSTRAINT detalle_objetivos_programa_pk PRIMARY KEY (id_objetivo)
);
COMMENT ON COLUMN motor_interprete.det_objetivos_programa.activo IS 'Bandera para eliminación lógica de objetivos del programa';
	
ALTER TABLE motor_interprete.det_objetivos_programa ADD CONSTRAINT detalle_objetivos_programa_fk FOREIGN KEY (id_detalle_home) REFERENCES motor_interprete.det_home(id_detalle_home);

CREATE TABLE motor_interprete.det_poblacion_objetivo (
	id_poblacion_objetivo bigserial NOT NULL,
	id_detalle_home bigserial NOT NULL,	
	descripcion_poblacion_objetivo varchar(200) NOT NULL,
	orden int4 NOT NULL,
	activo bool NOT NULL,						
	CONSTRAINT detalle_poblacion_objetivo_pk PRIMARY KEY (id_poblacion_objetivo)
);
COMMENT ON COLUMN motor_interprete.det_poblacion_objetivo.activo IS 'Bandera para eliminación lógica de población objetivo';
	
ALTER TABLE motor_interprete.det_poblacion_objetivo ADD CONSTRAINT detalle_poblacion_objetivo_fk FOREIGN KEY (id_detalle_home) REFERENCES motor_interprete.det_home(id_detalle_home);

CREATE TABLE motor_interprete.det_requisito (
	id_requisito bigserial NOT NULL,
	id_detalle_home bigserial NOT NULL,	
	descripcion_requisito varchar(200) NOT NULL,		
	orden int4 NOT NULL,
	activo bool NOT NULL,						
	CONSTRAINT detalle_requisito_pk PRIMARY KEY (id_requisito)
);
COMMENT ON COLUMN motor_interprete.det_requisito.activo IS 'Bandera para eliminación lógica de requisitos';
	
ALTER TABLE motor_interprete.det_requisito ADD CONSTRAINT detalle_requisito_fk FOREIGN KEY (id_detalle_home) REFERENCES motor_interprete.det_home(id_detalle_home);

CREATE TABLE motor_interprete.det_especificacion_requisito (
	id_especificacion bigserial NOT NULL,
	id_requisito bigserial NOT NULL,
	descripcion_especificacion varchar(200) NOT NULL,
	orden int4 NOT NULL,
	activo bool NOT NULL,						
	CONSTRAINT detalle_especificacion_requisito_pk PRIMARY KEY (id_especificacion)
);
COMMENT ON COLUMN motor_interprete.det_especificacion_requisito.activo IS 'Bandera para eliminación lógica de especificaciones de requisitos';
	
ALTER TABLE motor_interprete.det_especificacion_requisito ADD CONSTRAINT detalle_especificacion_requisito_fk FOREIGN KEY (id_requisito) REFERENCES motor_interprete.det_requisito(id_requisito);

CREATE TABLE motor_interprete.det_apoyo_otorgado (
	id_apoyo bigserial NOT NULL,
	id_detalle_home bigserial NOT NULL,	
	descripcion_apoyo_otorgado varchar(200) NOT NULL,
	orden int4 NOT NULL,
	activo bool NOT NULL,						
	CONSTRAINT detalle_apoyo_otorgado_pk PRIMARY KEY (id_apoyo)
);
COMMENT ON COLUMN motor_interprete.det_apoyo_otorgado.activo IS 'Bandera para eliminación lógica de Apoyos Otorgados';
	
ALTER TABLE motor_interprete.det_apoyo_otorgado ADD CONSTRAINT detalle_apoyo_otorgado_fk FOREIGN KEY (id_detalle_home) REFERENCES motor_interprete.det_home(id_detalle_home);

CREATE TABLE motor_interprete.det_tramite_servicio (
	id_detalle_tramite bigserial NOT NULL,
	id_detalle_home bigserial NOT NULL,
	habilita_costo_tramite bool NOT NULL,
	descripcion_costo_tramite varchar(60) NULL,
	habilita_excepcion_tramite bool NOT NULL,			
	CONSTRAINT detalle_tramite_servicio_pk PRIMARY KEY (id_detalle_tramite)
);
COMMENT ON COLUMN motor_interprete.det_tramite_servicio.habilita_excepcion_tramite IS 'Bandera para habilitar el registro de excepciones del trámite';
	
ALTER TABLE motor_interprete.det_tramite_servicio ADD CONSTRAINT detalle_tramite_servicio_fk FOREIGN KEY (id_detalle_home) REFERENCES motor_interprete.det_home(id_detalle_home);

CREATE TABLE motor_interprete.det_excepcion_tramite (
	id_excepcion bigserial NOT NULL,
	id_detalle_home bigserial NOT NULL,	
	descripcion_excepcion varchar(200) NOT NULL,
	orden int4 NOT NULL,
	activo bool NOT NULL,						
	CONSTRAINT detalle_excepcion_tramite_pk PRIMARY KEY (id_excepcion)
);
COMMENT ON COLUMN motor_interprete.det_excepcion_tramite.activo IS 'Bandera para eliminación lógica de excepciones del trámite';
	
ALTER TABLE motor_interprete.det_excepcion_tramite ADD CONSTRAINT detalle_excepcion_tramite_fk FOREIGN KEY (id_detalle_home) REFERENCES motor_interprete.det_home(id_detalle_home);

CREATE TABLE motor_interprete.det_captcha (
	id_detalle_captcha bigserial NOT NULL,
	id_proyecto bigserial NOT NULL,
	llave_publica varchar(60)NOT NULL,
	llave_privada varchar(60) NOT NULL,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL,					
	seccion_sincronizada bool NOT NULL,			
	CONSTRAINT detalle_captcha_pk PRIMARY KEY (id_detalle_captcha)
);
COMMENT ON COLUMN motor_interprete.det_captcha.activo IS 'Bandera para eliminación lógica de detalle de captcha';
COMMENT ON COLUMN motor_interprete.det_captcha.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.det_captcha ADD CONSTRAINT detalle_captcha_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

CREATE TABLE motor_interprete.det_acceso_llave (
	id_detalle_acceso bigserial NOT NULL,
	id_proyecto bigserial NOT NULL,
	clave_sistema varchar(60) NOT NULL,
	url_redireccionar varchar(400) NOT NULL,
	usuario_dominio_seg VARCHAR(100) NOT NULL,
	contrasena_dominio_seg VARCHAR(100) NOT NULL,
	codigo_secreto VARCHAR(100) NOT NULL,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL,					
	seccion_sincronizada bool NOT NULL,			
	autenticacion_ciudadano bool NOT NULL,
	CONSTRAINT detalle_acceso_llave_pk PRIMARY KEY (id_detalle_acceso)
);
COMMENT ON COLUMN motor_interprete.det_acceso_llave.activo IS 'Bandera para eliminación lógica de detalle de datos de acceso de llave';
COMMENT ON COLUMN motor_interprete.det_acceso_llave.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.det_acceso_llave ADD CONSTRAINT detalle_acceso_llave_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

CREATE TABLE motor_interprete.det_pago (
	id_detalle_pago bigserial NOT NULL,
	id_proyecto bigserial NOT NULL,
	id_tipo_costo int4 NOT NULL,
	monto_costo_fijo numeric(7, 2) NULL,
	url_servicio varchar(200) NOT NULL,
	identificador_proceso varchar(20) NOT NULL,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL,					
	seccion_sincronizada bool NOT NULL,			
	CONSTRAINT detalle_pago_pk PRIMARY KEY (id_detalle_pago)
);
COMMENT ON COLUMN motor_interprete.det_pago.activo IS 'Bandera para eliminación lógica de detalle de Pago';
COMMENT ON COLUMN motor_interprete.det_pago.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.det_pago ADD CONSTRAINT detalle_pago_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);
ALTER TABLE motor_interprete.det_pago ADD CONSTRAINT detalle_pago_tipo_costo_fk FOREIGN KEY (id_tipo_costo) REFERENCES motor_interprete.cat_tipo_costo(id_tipo_costo);

CREATE TABLE motor_interprete.parametros_detalle_pago (	
	id_parametro bigserial NOT NULL,
	id_detalle_pago bigserial NOT NULL,
	nombre_parametro varchar(60) NOT NULL,
	valor_parametro varchar(60) NOT NULL,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL,					
	CONSTRAINT parametros_detalle_pago_pk PRIMARY KEY (id_parametro)
);
COMMENT ON COLUMN motor_interprete.parametros_detalle_pago.activo IS 'Bandera para eliminación lógica de parámetros de detalle de Pago';

ALTER TABLE motor_interprete.parametros_detalle_pago ADD CONSTRAINT parametros_detalle_pago_detalle_pago_fk FOREIGN KEY (id_detalle_pago) REFERENCES motor_interprete.det_pago(id_detalle_pago);

CREATE TABLE motor_interprete.det_gestion_usuario (
	id_gestion_usuario bigserial NOT NULL,
	id_proyecto bigserial NOT NULL,
	perfil_supervisor_prevencion bool NULL,
	perfil_operador_prevencion bool NULL,
	perfil_supervisor_conclusion bool NULL,
	perfil_operador_conclusion bool NULL,
	correo_registro text NULL,
	correo_conclusion text NULL,
	correo_prevencion text NULL,
	correo_subsanar_prevencion text NULL,
	correo_rechazo text NULL,
	habilita_prevencion bool NOT NULL,
	adjunta_oficio bool NOT NULL,  
	dias_subsanar_prevencion int4 NULL,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL,					
	seccion_sincronizada bool NOT NULL,			
	CONSTRAINT detalle_gestion_usuario_pk PRIMARY KEY (id_gestion_usuario)
);
COMMENT ON COLUMN motor_interprete.det_gestion_usuario.activo IS 'Bandera para eliminación lógica de parámetros de detalle de gestión de usuarios';
COMMENT ON COLUMN motor_interprete.det_gestion_usuario.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.det_gestion_usuario ADD CONSTRAINT detalle_gestion_usuario_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

CREATE TABLE motor_interprete.det_firma_digital (
	id_detalle_firma bigserial NOT NULL,
	id_proyecto bigserial NOT NULL,
	ruta_archivo_cer varchar(200) NULL,
	ruta_archivo_key varchar(200) NULL,
	contrasenia varchar(100) NOT NULL,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL,					
	seccion_sincronizada bool NOT NULL,			
	CONSTRAINT detalle_firma_digital_pk PRIMARY KEY (id_detalle_firma)
);
COMMENT ON COLUMN motor_interprete.det_firma_digital.activo IS 'Bandera para eliminación lógica de datos de Firma digital';
COMMENT ON COLUMN motor_interprete.det_firma_digital.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.det_firma_digital ADD CONSTRAINT detalle_firma_digital_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);


CREATE TABLE motor_interprete.det_legales (
	id_detalle_legal bigserial NOT NULL,
	id_proyecto bigserial NOT NULL,
	contiene_aviso_simplificado bool NOT NULL,
	cuerpo_aviso_simplificado text NULL,
	contiene_aviso_integral bool NOT NULL,
	cuerpo_aviso_integral text NULL,
	contiene_manifiesto bool NOT NULL,
	cuerpo_manifiesto text NULL,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL,					
	seccion_sincronizada bool NOT NULL,			
	CONSTRAINT detalle_legales_pk PRIMARY KEY (id_detalle_legal)
);
COMMENT ON COLUMN motor_interprete.det_legales.activo IS 'Bandera para eliminación lógica de detalle de legales';
COMMENT ON COLUMN motor_interprete.det_legales.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.det_legales ADD CONSTRAINT detalle_legales_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

CREATE TABLE motor_interprete.det_analytics (
	id_detalle_analytics bigserial NOT NULL,
	id_proyecto bigserial NOT NULL,
	identificador_analytics varchar(16) NOT NULL, 
	titulo_busqueda varchar(100) NOT NULL, 
	descripcion_busqueda varchar(200) NOT NULL, 
	palabra_clave_busqueda varchar(100) NOT NULL, 
	titulo_grap varchar(100) NOT NULL, 
	descripcion_grap varchar(200) NOT NULL, 
	url_grap varchar(100) NOT NULL, 
	ruta_imagen_grap varchar(200) NOT NULL,	
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,	
	activo bool NOT NULL,					
	seccion_sincronizada bool NOT NULL,			
	CONSTRAINT detalle_analytics_pk PRIMARY KEY (id_detalle_analytics)
);
COMMENT ON COLUMN motor_interprete.det_analytics.activo IS 'Bandera para eliminación lógica de detalle de Analytics';
COMMENT ON COLUMN motor_interprete.det_analytics.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.det_analytics ADD CONSTRAINT detalle_analytics_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

CREATE TABLE motor_interprete.det_security_domain (
	id_detalle_security bigserial NOT NULL,
	id_proyecto bigserial NOT NULL,
	usuario varchar(100) NOT NULL, 
	contrasenia varchar(100) NOT NULL,
	url_sistema varchar(400) NOT NULL,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL,          
	CONSTRAINT detalle_security_domain_pk PRIMARY KEY (id_detalle_security)
);
COMMENT ON COLUMN motor_interprete.det_security_domain.activo IS 'Bandera para eliminación lógica de detalle de security domain';

ALTER TABLE motor_interprete.det_security_domain ADD CONSTRAINT detalle_security_domain_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

CREATE TABLE motor_interprete.bit_movimientos_secciones (
	id_movimiento_seccion bigserial NOT NULL,
	fecha_movimiento timestamp NOT NULL,
	id_proyecto bigserial NOT NULL,
	id_seccion_proyecto int4 NOT NULL,
	tabla_movimiento varchar(200) NOT NULL,			
	id_registro_movimiento bigserial NOT NULL,		
	id_usuario_movimiento int4 NOT NULL,
	cambio_sincronizado bool NOT NULL,			
	fecha_sincronizacion timestamp NULL,			
	CONSTRAINT bit_movimientos_secciones_pk PRIMARY KEY (id_movimiento_seccion)
);
COMMENT ON COLUMN motor_interprete.bit_movimientos_secciones.id_registro_movimiento IS 'Identificador con la pk de la tabla de la sección que se modificó';
COMMENT ON COLUMN motor_interprete.bit_movimientos_secciones.cambio_sincronizado IS 'Bandera que indica si el cambio realizado en el Motor se ha sincronizado al interprete';
COMMENT ON COLUMN motor_interprete.bit_movimientos_secciones.fecha_sincronizacion IS 'Fecha en la que se realiza la sincronización con el interprete';

CREATE INDEX bit_movimientos_secciones_id_usuario_movimiento_idx ON motor_interprete.bit_movimientos_secciones USING btree (id_usuario_movimiento);
CREATE INDEX bit_movimientos_secciones_id_proyecto_idx ON motor_interprete.bit_movimientos_secciones USING btree (id_proyecto);
CREATE INDEX bit_movimientos_secciones_sincronizado_idx ON motor_interprete.bit_movimientos_secciones USING btree (cambio_sincronizado);

ALTER TABLE motor_interprete.bit_movimientos_secciones ADD CONSTRAINT bit_movimientos_secciones_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);
ALTER TABLE motor_interprete.bit_movimientos_secciones ADD CONSTRAINT bit_movimientos_secciones_seccion_fk FOREIGN KEY (id_seccion_proyecto) REFERENCES motor_interprete.cat_secciones_proyecto(id_seccion_proyecto);
ALTER TABLE motor_interprete.bit_movimientos_secciones ADD CONSTRAINT bit_movimientos_secciones_usuario_fk FOREIGN KEY (id_usuario_movimiento) REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx);


CREATE TABLE motor_interprete.secciones_formulario (
	id_seccion_formulario bigserial NOT NULL,
	id_proyecto bigserial NOT NULL,
	nombre_seccion varchar(200) NOT NULL,
	orden int4 NOT NULL,
	activo bool NOT NULL,					
	seccion_sincronizada bool NOT NULL,			
	CONSTRAINT secciones_formulario_pk PRIMARY KEY (id_seccion_formulario)
);
COMMENT ON COLUMN motor_interprete.secciones_formulario.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.secciones_formulario ADD CONSTRAINT secciones_formulario_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

CREATE TABLE motor_interprete.subsecciones_formulario (
	id_subseccion_formulario bigserial NOT NULL,
	id_seccion_formulario bigserial NOT NULL,
	nombre_subseccion varchar(200) NOT NULL,
	orden int4 NOT NULL,
	activo bool NOT NULL,					
	seccion_sincronizada bool NOT NULL,			
	CONSTRAINT subsecciones_formulario_pk PRIMARY KEY (id_subseccion_formulario)
);
COMMENT ON COLUMN motor_interprete.subsecciones_formulario.seccion_sincronizada IS 'Bandera que indica si la Subsección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.subsecciones_formulario ADD CONSTRAINT subsecciones_formulario_secciones_fk FOREIGN KEY (id_seccion_formulario) REFERENCES motor_interprete.secciones_formulario(id_seccion_formulario);

create table motor_interprete.componente (
	id_componente bigserial NOT NULL,	
	id_subseccion_formulario bigserial NOT NULL,
	id_tipo_componente int4 NOT NULL,
	orden int4 NOT NULL,	
	requerido bool NOT NULL,
	tooltip bool NOT NULL,
	descripcion_tooltip varchar(200) NULL,
	titulo_campo varchar(100) NOT NULL,		
	activo bool NOT NULL,					
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	seccion_sincronizada bool NOT NULL, 
	CONSTRAINT componente_pk PRIMARY KEY (id_componente)	
);

CREATE index componente_subseccion_idx ON motor_interprete.componente USING btree (id_subseccion_formulario);
CREATE INDEX componente_tipo_componente_idx ON motor_interprete.componente USING btree (id_tipo_componente);

COMMENT ON COLUMN motor_interprete.componente.activo IS 'Bandera para deshabilitar componentes para generación de formularios';
COMMENT ON COLUMN motor_interprete.componente.seccion_sincronizada IS 'Bandera que indica si el componente ha sido sincronizado al proyecto intérprete';

ALTER TABLE motor_interprete.componente ADD CONSTRAINT componente_subsecciones_fk FOREIGN KEY (id_subseccion_formulario) REFERENCES motor_interprete.subsecciones_formulario(id_subseccion_formulario);
ALTER TABLE motor_interprete.componente ADD CONSTRAINT componente_tipo_componente_fk FOREIGN KEY (id_tipo_componente) REFERENCES motor_interprete.cat_tipo_componente(id_tipo_componente);


CREATE TABLE motor_interprete.componente_campo_texto (
	id_componente_campo_texto bigserial NOT NULL,
	id_componente bigserial NOT NULL,
	alfanumerico bool NOT NULL,
	numerico bool NOT NULL,
	habilita_texto_interior bool NOT NULL,
	texto_interior varchar(100) NULL,
	id_origen_llenado int2 NULL,
	validadores bool NOT NULL,
	id_validador int4 NULL,	
	valor_minimo numeric null,
	valor_maximo numeric null,
	CONSTRAINT componente_campo_texto_pk PRIMARY KEY (id_componente_campo_texto)
);

ALTER TABLE motor_interprete.componente_campo_texto ADD CONSTRAINT componente_campo_texto_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);
ALTER TABLE motor_interprete.componente_campo_texto ADD CONSTRAINT componente_campo_texto_origen_fk FOREIGN KEY (id_origen_llenado) REFERENCES motor_interprete.cat_origen_llenado(id_origen_llenado);
ALTER TABLE motor_interprete.componente_campo_texto ADD CONSTRAINT componente_campo_texto_validador_fk FOREIGN KEY (id_validador) REFERENCES motor_interprete.cat_validadores(id_validador);


CREATE TABLE motor_interprete.componente_fecha (
	id_componente_fecha bigserial NOT NULL,
	id_componente bigserial NOT NULL,
	dias_inhabiles BOOL NOT NULL,		
	fecha_menor_hoy BOOL NOT NULL,		
	fecha_mayor_hoy BOOL NOT NULL,		
	fecha_inicio date NULL,
	fecha_limite date NULL,						
	CONSTRAINT componente_fecha_pk PRIMARY KEY (id_componente_fecha)
);

ALTER TABLE motor_interprete.componente_fecha ADD CONSTRAINT componente_fecha_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);

CREATE TABLE motor_interprete.componente_carga_documentos (
	id_componente_carga bigserial NOT NULL,
	id_componente bigserial NOT NULL,
	id_tamanio_archivo int4 NOT NULL,
	documento_unico bool NOT NULL,		
	CONSTRAINT componente_carga_documentos_pk PRIMARY KEY (id_componente_carga)
);

ALTER TABLE motor_interprete.componente_carga_documentos ADD CONSTRAINT componente_carga_documentos_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);
ALTER TABLE motor_interprete.componente_carga_documentos ADD CONSTRAINT componente_carga_documentos_tamanio_fk FOREIGN KEY (id_tamanio_archivo) REFERENCES motor_interprete.cat_tamanio_archivos(id_tamanio_archivo);

CREATE TABLE motor_interprete.crc_carga_documentos_tipo_archivo (
	id_carga_documento_tipo_archivo bigserial NOT NULL,
	id_componente_carga bigserial NOT NULL,
	id_tipo_archivo int4 NOT NULL,
	activo bool NULL DEFAULT true, 
	CONSTRAINT crc_carga_documentos_tipo_archivo_pk PRIMARY KEY (id_carga_documento_tipo_archivo)
);
COMMENT ON COLUMN motor_interprete.crc_carga_documentos_tipo_archivo.activo IS 'Bandera para eliminación lógica de los tipos de archivo para carga de documentos';

ALTER TABLE motor_interprete.crc_carga_documentos_tipo_archivo ADD CONSTRAINT crc_carga_documentos_componente_carga_fk FOREIGN KEY (id_componente_carga) REFERENCES motor_interprete.componente_carga_documentos(id_componente_carga);
ALTER TABLE motor_interprete.crc_carga_documentos_tipo_archivo ADD CONSTRAINT crc_carga_documentos_tipo_archivo_fk FOREIGN KEY (id_tipo_archivo) REFERENCES motor_interprete.cat_tipo_archivo(id_tipo_archivo);

CREATE TABLE motor_interprete.componente_checkbox (
	id_componente_checkbox bigserial NOT NULL,
	id_componente bigserial NOT NULL,	
	habilita_todos_ninguno bool NOT NULL,
	CONSTRAINT componente_checkbox_pk PRIMARY KEY (id_componente_checkbox)
);

ALTER TABLE motor_interprete.componente_checkbox ADD CONSTRAINT componente_checkbox_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);


CREATE TABLE motor_interprete.det_elementos_checkbox (
	id_elemento_checkbox bigserial NOT NULL,
	id_componente_checkbox bigserial NOT NULL,		
	descripcion_elemento varchar(100) null NULL,	
	activo bool NOT NULL,						
	orden int4 NOT NULL,
	CONSTRAINT det_elementos_checkbox_pk PRIMARY KEY (id_elemento_checkbox)
);
COMMENT ON COLUMN motor_interprete.det_elementos_checkbox.activo IS 'Bandera para eliminación lógica de elementos del componente checkbox';

ALTER TABLE motor_interprete.det_elementos_checkbox ADD CONSTRAINT det_elementos_checkbox_fk FOREIGN KEY (id_componente_checkbox) REFERENCES motor_interprete.componente_checkbox(id_componente_checkbox);

CREATE TABLE motor_interprete.componente_radioboton (
	id_componente_radioboton bigserial NOT NULL,
	id_componente bigserial NOT NULL,		
	habilita_opcion_otro bool NOT NULL,
	texto_interior_otro varchar(100) NULL,		
	CONSTRAINT componente_radioboton_pk PRIMARY KEY (id_componente_radioboton)
);

ALTER TABLE motor_interprete.componente_radioboton ADD CONSTRAINT componente_radioboton_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);

CREATE TABLE motor_interprete.det_elementos_radioboton (
	id_elemento_radioboton bigserial NOT NULL,
	id_componente_radioboton bigserial NOT NULL,		
	descripcion_elemento varchar(100) null NULL,	
	activo bool NOT NULL,						
	orden int4 NOT NULL,
	CONSTRAINT det_elementos_radioboton_pk PRIMARY KEY (id_elemento_radioboton)
);
COMMENT ON COLUMN motor_interprete.det_elementos_radioboton.activo IS 'Bandera para eliminación lógica de elementos del componente radioboton';

ALTER TABLE motor_interprete.det_elementos_radioboton ADD CONSTRAINT det_elementos_radioboton_fk FOREIGN KEY (id_componente_radioboton) REFERENCES motor_interprete.componente_radioboton(id_componente_radioboton);

CREATE TABLE motor_interprete.componente_datos_personales_llave (
	id_componente_datos_personales bigserial NOT NULL,
	id_componente bigserial NOT NULL,	
	habilita_curp bool NOT NULL,
	habilita_nombre bool NOT NULL,
	habilita_primer_apellido bool NOT NULL,
	habilita_segundo_apellido bool NOT NULL,
	habilita_telefono bool NOT NULL,
	habilita_correo_electronico bool NOT NULL,
	habilita_fecha_nacimiento bool NOT NULL,
	CONSTRAINT componente_datos_personales_llave_pk PRIMARY KEY (id_componente_datos_personales)
);

ALTER TABLE motor_interprete.componente_datos_personales_llave ADD CONSTRAINT componente_datos_personales_llave_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);

CREATE TABLE motor_interprete.componente_datos_personales (
	id_componente_datos_personales bigserial NOT NULL,
	id_componente bigserial NOT NULL,
	habilita_renapo bool NOT NULL,
	habilita_curp bool NOT NULL,
	curp_obligatorio bool NOT NULL,
	texto_interior_curp varchar(60) NULL,
	habilita_nombre bool NOT NULL,
	nombre_obligatorio bool NOT NULL,
	texto_interior_nombre varchar(60) NULL,
	habilita_primer_apellido bool NOT NULL,
	primer_apellido_obligatorio bool NOT NULL,
	texto_interior_primer_apellido varchar(60) NULL,
	habilita_segundo_apellido bool NOT NULL,
	segundo_apellido_obligatorio bool NOT NULL,
	texto_interior_segundo_apellido varchar(60) NULL,
	habilita_telefono bool NOT NULL,
	telefono_obligatorio bool NOT NULL,
	texto_interior_telefono varchar(30) NULL,
	habilita_correo_electronico bool NOT NULL,
	correo_electronico_obligatorio bool NOT NULL,
	texto_interior_correo_electronico varchar(30) NULL,		
	CONSTRAINT componente_datos_personales_pk PRIMARY KEY (id_componente_datos_personales)
);

ALTER TABLE motor_interprete.componente_datos_personales ADD CONSTRAINT componente_datos_personales_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);

CREATE TABLE motor_interprete.componente_datos_domicilio (
	id_componente_datos_domicilio bigserial NOT NULL,
	id_componente bigserial NOT NULL,
	habilita_calle bool NOT NULL,
	calle_obligatorio bool NOT NULL,
	texto_interior_calle varchar(60) NULL,
	habilita_numero_exterior bool NOT NULL,
	numero_exterior_obligatorio bool NOT NULL,
	texto_interior_numero_exterior varchar(60) NULL,
	habilita_numero_interior bool NOT NULL,
	numero_interior_obligatorio bool NOT NULL,
	texto_interior_numero_interior varchar(60) NULL,
	habilita_codigo_postal bool NOT NULL,
	codigo_postal_obligatorio bool NOT NULL,
	texto_interior_codigo_postal varchar(20) NULL,
	habilita_colonia bool NOT NULL,
	colonia_obligatorio bool NOT NULL,
	texto_interior_colonia varchar(60) NULL,
	habilita_alcaldia bool NOT NULL,
	alcaldia_obligatorio bool NOT NULL,
	texto_interior_alcaldia varchar(60) NULL,		
	CONSTRAINT componente_datos_domicilio_pk PRIMARY KEY (id_componente_datos_domicilio)
);

ALTER TABLE motor_interprete.componente_datos_domicilio ADD CONSTRAINT componente_datos_domicilio_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);

CREATE TABLE motor_interprete.componente_checkbox_unico ( 
 id_componente_checkbox_unico bigserial NOT NULL, 
 id_componente bigserial NOT NULL, 
 texto text NOT NULL, 
 CONSTRAINT componente_checkbox_unico_pk PRIMARY KEY (id_componente_checkbox_unico) 
);

ALTER TABLE motor_interprete.componente_checkbox_unico ADD CONSTRAINT componente_checkbox_unico_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);

CREATE TABLE motor_interprete.componente_menu_desplegable (
	id_componente_menu bigserial NOT NULL,
	id_componente bigserial NOT NULL,	
	id_origen_llenado int4 NOT NULL,
	habilita_opcion_otro bool not null,
	texto_interior_otro varchar(100) null,
	CONSTRAINT componente_menu_desplegable_pk PRIMARY KEY (id_componente_menu)
);

ALTER TABLE motor_interprete.componente_menu_desplegable ADD CONSTRAINT componente_menu_desplegable_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);
ALTER TABLE motor_interprete.componente_menu_desplegable ADD CONSTRAINT componente_menu_desplegable_origen_fk FOREIGN KEY (id_origen_llenado) REFERENCES motor_interprete.cat_origen_llenado(id_origen_llenado);

CREATE TABLE motor_interprete.det_elementos_menu (
	id_elemento_menu bigserial NOT NULL,
	id_componente_menu bigserial NOT NULL,		
	descripcion_elemento varchar(100) NOT NULL,	
	activo bool NOT NULL,						
	CONSTRAINT det_elementos_menu_pk PRIMARY KEY (id_elemento_menu)
);
COMMENT ON COLUMN motor_interprete.det_elementos_menu.activo IS 'Bandera para eliminación lógica de elementos del componente menu';

ALTER TABLE motor_interprete.det_elementos_menu ADD CONSTRAINT det_elementos_componente_menu_fk FOREIGN KEY (id_componente_menu) REFERENCES motor_interprete.componente_menu_desplegable(id_componente_menu);

CREATE TABLE motor_interprete.componente_informativo (
	id_componente_informativo bigserial NOT NULL,
	id_componente bigserial NOT NULL,	
	texto_informativo text NOT NULL,
	CONSTRAINT componente_informativo_pk PRIMARY KEY (id_componente_informativo)
);

ALTER TABLE motor_interprete.componente_informativo ADD CONSTRAINT componente_informativo_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);

CREATE TABLE motor_interprete.archivos_respuesta_token (
  id_archivo_respuesta bigserial NOT NULL,
  id_proyecto bigserial NOT NULL,
  ruta_archivo_respuesta varchar(200) NOT NULL,
  nombre_archivo varchar(200) NOT NULL, 
  fecha_creacion timestamp NOT NULL,
  fecha_ultima_actualizacion timestamp NOT NULL,
  activo bool NOT NULL,						
  seccion_sincronizada bool NOT NULL,				
  CONSTRAINT archivos_respuesta_token_pk PRIMARY KEY (id_archivo_respuesta)
);
COMMENT ON COLUMN motor_interprete.archivos_respuesta_token.activo IS 'Bandera para eliminación lógica de archivos de respuesta de token';
COMMENT ON COLUMN motor_interprete.archivos_respuesta_token.seccion_sincronizada IS 'Bandera que indica si los arhivos de respuesta han sido sincronizados al proyecto intérprete';
	
ALTER TABLE motor_interprete.archivos_respuesta_token ADD CONSTRAINT archivos_respuesta_token_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

CREATE TABLE motor_interprete.det_elementos_token (
  id_elemento_token bigserial NOT NULL,
  id_archivo_respuesta bigserial NOT NULL,  
  nombre_token varchar(200) NOT NULL,
  id_origen_token int4 NOT NULL,
  estructura_folio varchar(100) null,
  id_formato_fecha int4 null,
  longitud_folio varchar(100) null,
  campo_personalizado varchar(30) NULL,  
  id_componente bigserial,          
  orden int4 NOT NULL,
  activo bool NOT NULL,            
  CONSTRAINT det_elementos_token_pk PRIMARY KEY (id_elemento_token)
);  
COMMENT ON COLUMN motor_interprete.det_elementos_token.activo IS 'Bandera para eliminación lógica de elementos token de un archivo de respuesta';

ALTER TABLE motor_interprete.det_elementos_token ADD CONSTRAINT det_elementos_token_archivo_fk FOREIGN KEY (id_archivo_respuesta) REFERENCES motor_interprete.archivos_respuesta_token(id_archivo_respuesta);
ALTER TABLE motor_interprete.det_elementos_token ADD CONSTRAINT det_elementos_token_origen_fk FOREIGN KEY (id_origen_token) REFERENCES motor_interprete.cat_origen_token(id_origen_token);
ALTER TABLE motor_interprete.det_elementos_token ADD CONSTRAINT det_elementos_token_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);
ALTER TABLE motor_interprete.det_elementos_token ALTER COLUMN id_componente DROP NOT NULL;

CREATE TABLE motor_interprete.det_pausas_proyecto (
   id_pausa_proyecto bigserial NOT NULL,
   id_proyecto bigserial NOT NULL,
   id_motivo int4 NOT NULL,
   observaciones_pausa text NULL,
   id_usuario_llave_cdmx int8 NOT NULL,
   fecha_creacion timestamp NOT NULL,
   CONSTRAINT det_pausas_proyecto_pk PRIMARY KEY (id_pausa_proyecto)
);

CREATE INDEX det_pausas_proyecto_id_proyecto_idx ON motor_interprete.det_pausas_proyecto USING btree (id_proyecto);
CREATE INDEX det_pausas_proyecto_id_motivo_idx ON motor_interprete.det_pausas_proyecto USING btree (id_pausa_proyecto);
CREATE INDEX det_pausas_proyecto_id_usuario_idx ON motor_interprete.det_pausas_proyecto USING btree (id_usuario_llave_cdmx);

ALTER TABLE motor_interprete.det_pausas_proyecto ADD CONSTRAINT det_pausas_proyecto_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);
ALTER TABLE motor_interprete.det_pausas_proyecto ADD CONSTRAINT det_pausas_proyecto_motivo_pausa_fk FOREIGN KEY (id_motivo) REFERENCES motor_interprete.cat_motivos_pausa(id_motivo);
ALTER TABLE motor_interprete.det_pausas_proyecto ADD CONSTRAINT det_pausas_proyecto_usuario_fk FOREIGN KEY (id_usuario_llave_cdmx) REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx);

CREATE TABLE motor_interprete.cat_estados_sistema (
	id_estado_sistema int4 NOT NULL,
	descripcion varchar(60) NOT NULL,
	CONSTRAINT cat_estados_sistema_pk PRIMARY KEY (id_estado_sistema)
);

CREATE TABLE motor_interprete.det_estado_sistema (
	id bigserial NOT NULL,
	id_estado_sistema int4 NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	CONSTRAINT det_estados_sistema_pk PRIMARY KEY (id)
);

ALTER TABLE motor_interprete.det_estado_sistema ADD CONSTRAINT det_estado_sistema_estados_fk FOREIGN KEY (id_estado_sistema) REFERENCES motor_interprete.cat_estados_sistema(id_estado_sistema);

CREATE TABLE motor_interprete.cat_estados (
	id_estado int4 NOT NULL,
	estado varchar(40) NULL,
	cve_estado varchar(2) NULL
);

CREATE INDEX cat_estados_id_estado_idx ON motor_interprete.cat_estados USING btree (id_estado);

CREATE TABLE motor_interprete.cat_municipios (
	id_municipio int4 NOT NULL,
	municipio varchar(60) NULL,
	id_estado int4 NOT NULL,
	id_municipio_por_estado int4 NOT NULL,
	id_municipio_anterior int4 NULL
);

CREATE INDEX cat_municipios_id_municipio_idx ON motor_interprete.cat_municipios USING btree (id_municipio);
CREATE INDEX cat_municipios_id_estado_idx ON motor_interprete.cat_municipios USING btree (id_estado);


CREATE TABLE motor_interprete.cat_asentamientos (
	id_asentamiento int4 NOT NULL,
	asentamiento varchar(100) NULL,
	id_municipio int4 NOT NULL,
	id_estado int4 NOT NULL
);

CREATE INDEX cat_asentamientos_id_asentamiento_idx ON motor_interprete.cat_asentamientos USING btree (id_asentamiento);
CREATE INDEX cat_asentamientos_id_municipio_idx ON motor_interprete.cat_asentamientos USING btree (id_municipio);
CREATE INDEX cat_asentamientos_id_estado_idx ON motor_interprete.cat_asentamientos USING btree (id_estado);

CREATE TABLE motor_interprete.cat_codigos_postales (
	id_codigo_postal int4 NOT NULL,
	codigo_postal varchar(5) NULL,
	id_asentamiento int4 NOT NULL,
	id_tipo_asentamiento int4 NOT NULL,
	id_municipio int4 NOT NULL,
	id_estado int4 NOT NULL
);

CREATE INDEX cat_codigos_postales_id_codigo_postal_idx ON motor_interprete.cat_codigos_postales USING btree (id_codigo_postal);
CREATE INDEX cat_codigos_postales_id_asentamiento_idx ON motor_interprete.cat_codigos_postales USING btree (id_asentamiento);
CREATE INDEX cat_codigos_postales_id_municipio_idx ON motor_interprete.cat_codigos_postales USING btree (id_municipio);
CREATE INDEX cat_codigos_postales_id_estado_idx ON motor_interprete.cat_codigos_postales USING btree (id_estado);

INSERT INTO motor_interprete.cat_estatus_tramite (id_estatus_tramite,descripcion) VALUES (1,'En captura');
INSERT INTO motor_interprete.cat_estatus_tramite (id_estatus_tramite,descripcion) VALUES (2,'Pendiente de pago');
INSERT INTO motor_interprete.cat_estatus_tramite (id_estatus_tramite,descripcion) VALUES (3,'Enviado');
INSERT INTO motor_interprete.cat_estatus_tramite (id_estatus_tramite,descripcion) VALUES (4,'En corrección');
INSERT INTO motor_interprete.cat_estatus_tramite (id_estatus_tramite,descripcion) VALUES (5,'Corregido');
INSERT INTO motor_interprete.cat_estatus_tramite (id_estatus_tramite,descripcion) VALUES (6,'Rechazado');
INSERT INTO motor_interprete.cat_estatus_tramite (id_estatus_tramite,descripcion) VALUES (7,'Aceptado');

----INSERT DE PRUEBAS-----

INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (1,'Aguascalientes','AS');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (2,'Baja California','BC');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (3,'Baja California Sur','BS');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (4,'Campeche','CC');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (5,'Coahuila de Zaragoza','CL');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (6,'Colima','CM');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (7,'Chiapas','CS');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (8,'Chihuahua','CH');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (9,'Ciudad de México','DF');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (10,'Durango','DG');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (11,'Guanajuato','GT');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (12,'Guerrero','GR');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (13,'Hidalgo','HG');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (14,'Jalisco','JC');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (15,'México','MC');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (16,'Michoacán de Ocampo','MN');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (17,'Morelos','MS');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (18,'Nayarit','NT');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (19,'Nuevo León','NL');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (20,'Oaxaca','OC');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (21,'Puebla','PL');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (22,'Querétaro','QT');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (23,'Quintana Roo','QR');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (24,'San Luis Potosí','SP');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (25,'Sinaloa','SL');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (26,'Sonora','SR');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (27,'Tabasco','TC');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (28,'Tamaulipas','TS');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (29,'Tlaxcala','TL');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (30,'Veracruz de Ignacio de la Llave','VZ');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (31,'Yucatán','YN');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (32,'Zacatecas','ZS');
INSERT INTO motor_interprete.cat_estados (id_estado,estado,cve_estado) VALUES (33,'Nacido en el Extranjero','NE');


INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (2,'Azcapotzalco',9,2,2);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (3,'Coyoacán',9,3,3);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (4,'Cuajimalpa de Morelos',9,4,4);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (5,'Gustavo A. Madero',9,5,5);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (6,'Iztacalco',9,6,6);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (7,'Iztapalapa',9,7,7);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (8,'La Magdalena Contreras',9,8,8);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (9,'Milpa Alta',9,9,9);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (10,'Álvaro Obregón',9,10,10);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (11,'Tláhuac',9,11,11);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (12,'Tlalpan',9,12,12);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (13,'Xochimilco',9,13,13);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (14,'Benito Juárez',9,14,14);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (15,'Cuauhtémoc',9,15,15);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (16,'Miguel Hidalgo',9,16,16);
INSERT INTO motor_interprete.cat_municipios (id_municipio,municipio,id_estado,id_municipio_por_estado,id_municipio_anterior) VALUES (17,'Venustiano Carranza',9,17,17);


INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1,'San Ángel',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (5,'Los Alpes',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (6,'Guadalupe Inn',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (10,'Florida',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (9,'Axotla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (12,'Campestre',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (14,'Tlacopac',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (16,'Ex-Hacienda de Guadalupe Chimalistac',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (17,'Altavista',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (18,'San Ángel Inn',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (19,'Chimalistac',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (22,'Progreso Tizapan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (24,'Ermita Tizapan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (25,'La Otra Banda',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (28,'Tizapan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (26,'Loreto',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (31,'Pólvora',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (33,'La Conchita',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (37,'Ampliación El Capulín',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (39,'Liberales de 1857',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (34,'Belém de las Flores',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (36,'El Capulín',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (42,'Acueducto',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (46,'Hidalgo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (47,'Las Américas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (44,'Cove',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (43,'Ampliación Acueducto',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (56,'Real del Monte',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (53,'Molino de Santo Domingo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (52,'Paraíso',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (58,'Reacomodo Pino Suárez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (60,'José Maria Pino Suárez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (59,'Bellavista',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (64,'Tolteca',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (63,'Cristo Rey',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (66,'Bosque',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (68,'Maria G. de García Ruiz',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (69,'1a Victoria',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (67,'Isidro Fabela',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (74,'Abraham M. González',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (77,'8 de Agosto',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (76,'Carola',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (78,'San Pedro de los Pinos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (79,'Arturo Martínez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (82,'Santa Fe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (84,'Lomas de Santa Fe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (89,'Mártires de Tacubaya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (86,'Cuevitas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2681,'La Estrella',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (90,'Zenón Delgado',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (85,'Bonanza',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (87,'El Cuernito',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (92,'Campo de Tiro los Gamitos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (132,'El Piru Santa Fe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (97,'Los Gamitos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (98,'Tlapechico',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (224,'El Piru 2a Ampliación',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (102,'La Huerta',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (103,'Pueblo Nuevo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (108,'Tecolalco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (104,'El Árbol',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (106,'Lomas de Nuevo México',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (105,'Ladera',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (107,'Margarita Maza de Juárez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (109,'Ampliación La Cebada',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2845,'La Mexicana 2a Ampliación',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (112,'La Mexicana',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (114,'La Palmita',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (110,'Calzada Jalalpa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (115,'Liberación Proletaria',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (113,'Ampliación La Mexicana',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (118,'2a Sección Cañada',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (117,'1a Sección Cañada',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (121,'La Presa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (123,'Golondrinas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (120,'El Tejocote',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (124,'Golondrinas 1a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (127,'Lomas de Capula',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (125,'Golondrinas 2a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (131,'Villa Solidaridad',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (93,'El Pirul',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (133,'Desarrollo Urbano',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (136,'Lomas de Becerra',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (141,'La Joya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (138,'Arvide',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (139,'El Pocito',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (140,'Francisco Villa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (144,'El Rodeo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (146,'Reacomodo El Cuernito',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (147,'Piloto Adolfo López Mateos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (148,'Presidentes',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (153,'Ampliación Jalalpa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (152,'Jalalpa Tepito 2a Ampliación',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (154,'Jalalpa Tepito',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (156,'Ampliación Piloto Adolfo López Mateos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (160,'1a Ampliación Presidentes',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (161,'2a Ampliación Presidentes',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (163,'San Gabriel',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (164,'Carlos A. Madrazo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (168,'Paseo de las Lomas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (170,'Bejero del Pueblo Santa Fe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (171,'Santa Fe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2683,'Santa Fe La Loma',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2864,'Santa Fe Centro Ciudad',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2682,'Santa Fe Peña Blanca',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (172,'Jalalpa El Grande',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (176,'Santa Fe Tlayapaca',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (181,'Olivar del Conde 1a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (184,'Preconcreto',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (187,'Galeana',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (188,'Olivar del Conde 2a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (194,'Palmas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (193,'Barrio Norte',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (195,'Minas Cristo Rey',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (196,'Sacramento',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (197,'Santa María Nonoalco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (198,'Colina del Sur',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (202,'Hogar y Redención',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (204,'Alfonso XIII',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (207,'Molino de Rosas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (206,'Alfalfar',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (209,'Lomas de Plateros',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (211,'La Cascada',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (213,'Santa Lucía',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2704,'Miguel Gaona Armenta',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (221,'Santa Lucía Chantepec',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (222,'Garcimarrero',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2636,'Los Cedros',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (223,'La Araña',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (225,'Estado de Hidalgo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (227,'Piru Santa Lucía',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (94,'Ampliación Los Pirules',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (226,'Ampliación Estado de Hidalgo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2841,'El Politoco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (229,'Corpus Christy',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (232,'Tepopotla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (233,'Acuilotla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2643,'Cooperativa Unión Olivos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (238,'Balcones de Cehuayo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (239,'Cehuaya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (240,'Llano Redondo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (241,'Punta de Cehuaya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2638,'Villa Progresista',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (243,'Dos Ríos del Pueblo Santa Lucía',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (245,'Tepeaca',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2849,'Rinconada Las Cuevitas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (246,'Ampliación Tepeaca',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (249,'Canutillo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (250,'Canutillo 3a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (248,'Ave Real',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (251,'Canutillo 2a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2747,'Hueytlale',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (255,'Reacomodo Valentín Gómez Farías',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (259,'Tarango',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (261,'El Rincón',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (263,'Merced Gómez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2639,'Profesor J. Arturo López Martínez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (265,'Colinas de Tarango',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (267,'Arcos Centenario',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2745,'Ex-Hacienda de Tarango',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (268,'La Martinica',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (269,'Rinconada de Tarango',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (270,'Lomas de Tarango',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (271,'Lomas de Puerta Grande',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (273,'Puerta Grande',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2640,'Los Juristas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (275,'Herón Proal',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (276,'Ponciano Arriaga',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (280,'Ampliación Tlacuitlapa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (281,'2o Reacomodo Tlacuitlapa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2848,'Santa Lucía Chantepec',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2844,'El Ruedo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (277,'La Milagrosa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (278,'Palmas Axotitla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (279,'Tlacuitlapa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (284,'San Agustín del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (282,'La Joyita del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (283,'Ocotillos del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (286,'Tecalcapa del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (285,'2a Del Moral del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (287,'Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (292,'El Mirador del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (291,'El Encino del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (294,'Las Águilas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (295,'Ampliación Alpes',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (297,'Lomas de Guadalupe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (298,'Alcantarilla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (299,'Lomas de las Águilas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (300,'Puente Colorado',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (303,'La Peñita del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (304,'San Clemente Norte',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2842,'San Clemente Sur',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (307,'Las Águilas 3er Parque',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (305,'Las Águilas 1a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (306,'Las Águilas 2o Parque',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (308,'Ampliación Las Águilas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (309,'Atlamaya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (311,'La Herradura del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (310,'Flor de María',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (316,'San José del Olivar',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (315,'La Angostura',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (319,'Tizampampano del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (318,'Olivar de los Padres',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (323,'Miguel Hidalgo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (324,'Lomas de los Ángeles del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (325,'Lomas de San Ángel Inn',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (327,'San Bartolo Ameyalco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (328,'Rancho San Francisco Pueblo San Bartolo Ameyalco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (330,'Villa Verdún',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2843,'Ejido San Mateo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (332,'Lomas Axomiatla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (333,'Santa Rosa Xochiac',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (334,'Torres de Potrero',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (337,'Rincón de la Bolsa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2847,'Rancho del Carmen del Pueblo San Bartolo Ameyalco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (341,'Lomas de Chamontoya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (343,'Tlacoyaque',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (344,'Lomas de La Era',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (3,'Lomas del Capulín',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (345,'Lomas de los Cedros',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (347,'Jardines del Pedregal',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2846,'San Jerónimo Aculco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (353,'Centro de Azcapotzalco',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (356,'Los Reyes',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (357,'San Rafael',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (358,'Nuevo Barrio San Rafael',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (361,'Santo Tomás',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (360,'San Marcos',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (362,'Del Maestro',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (363,'San Sebastián',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (364,'Libertad',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (365,'Santa María Malinalco',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (369,'Un Hogar Para Cada Trabajador',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (368,'Sindicato Mexicano de Electricistas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (372,'Nextengo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (371,'Del Recreo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (374,'Clavería',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (376,'Sector Naval',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (377,'San Álvaro',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (378,'Ángel Zimbrón',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (379,'El Rosario',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (385,'San Martín Xochinahuac',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (389,'Nueva El Rosario',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (390,'Nueva España',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (391,'Tierra Nueva',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (392,'Santa Inés',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (393,'Pasteros',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (394,'Santo Domingo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (396,'Reynosa Tamaulipas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (397,'Santa Bárbara',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (398,'San Andrés',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (399,'San Andrés',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (400,'Santa Catarina',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (402,'Industrial Vallejo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (404,'Ferrería',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (405,'San Andrés de las Salinas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (406,'Huautla de las Salinas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (407,'Santa Cruz de las Salinas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (409,'Las Salinas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (410,'San Juan Tlihuaca',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (412,'Prados del Rosario',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (415,'Ex-Hacienda el Rosario',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (418,'Providencia',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (421,'Tezozomoc',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (422,'La Preciosa',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (423,'Ampliación Petrolera',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (425,'Petrolera',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (427,'San Mateo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (428,'Unidad Cuitlahuac',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (430,'El Jagüey',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (431,'Estación Pantaco',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (439,'Jardín Azpeitia',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (441,'Pro-Hogar',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (443,'Coltongo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2798,'Coltongo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (444,'Monte Alto',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (445,'Trabajadores de Hierro',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (446,'Euzkadi',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (447,'Cosmopolita',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (448,'Potrero del Llano',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (449,'San Miguel Amantla',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (451,'San Pedro Xalpa',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (454,'Ampliación San Pedro Xalpa',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (456,'San Antonio',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (457,'San Bartolo Cahualtongo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (459,'San Francisco Tetecala',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (461,'Santiago Ahuizotla',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (463,'Santa Lucía',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (462,'Industrial San Antonio',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (464,'Santa Cruz Acayucan',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (465,'Plenitud',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (466,'Santa Apolonia',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (467,'Nueva Santa María',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (469,'Ignacio Allende',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (470,'Victoria de las Democracias',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (473,'San Bernabé',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (474,'Obrero Popular',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (476,'Tlatilco',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (477,'San Salvador Xochimanca',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (478,'Aguilera',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (479,'Aldana',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (480,'Ampliación Cosmopolita',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (481,'Liberación',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (482,'Porvenir',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (483,'Del Gas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (484,'San Francisco Xocotitla',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (485,'Ampliación Del Gas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (486,'Arenal',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (487,'Patrimonio Familiar',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (488,'La Raza',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (489,'Piedad Narvarte',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (491,'Atenor Salas',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (493,'Narvarte Poniente',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2623,'Narvarte Oriente',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (496,'Del Valle Centro',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2624,'Insurgentes San Borja',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2621,'Del Valle Norte',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2622,'Del Valle Sur',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (501,'Tlacoquemécatl',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (505,'Actipan',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (506,'Acacias',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (507,'Portales Sur',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2625,'Portales Norte',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (509,'Santa Cruz Atoyac',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (512,'Residencial Emperadores',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (513,'Xoco',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (515,'General Pedro María Anaya',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (516,'Álamos',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (518,'Postal',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (519,'Miguel Alemán',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (521,'Josefa Ortiz de Domínguez',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (522,'Niños Héroes',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (523,'Nativitas',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (525,'Moderna',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (526,'Iztaccihuatl',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (527,'Villa de Cortes',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (528,'Del Carmen',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (529,'Zacahuitzco',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (530,'Albert',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (531,'Portales Oriente',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (532,'Miravalle',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (533,'Ermita',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (534,'Vértiz Narvarte',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (535,'Américas Unidas',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (536,'Periodista',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (537,'Independencia',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (538,'Del Lago',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (539,'Letrán Valle',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (540,'San Simón Ticumac',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (541,'Santa María Nonoalco',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (542,'Ciudad de los Deportes',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (543,'Nochebuena',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (544,'San Juan',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (545,'Extremadura Insurgentes',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (546,'San Pedro de los Pinos',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (548,'Nápoles',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (552,'8 de Agosto',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (554,'Ampliación Nápoles',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (556,'San José Insurgentes',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (558,'Mixcoac',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (559,'Insurgentes Mixcoac',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (561,'Merced Gómez',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (562,'Crédito Constructor',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (563,'Villa Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (566,'Santa Catarina',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (567,'La Concepción',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (569,'San Lucas',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (570,'Parque San Andrés',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (571,'Del Carmen',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (577,'San Diego Churubusco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (578,'San Mateo',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (580,'Campestre Churubusco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (582,'Churubusco Country Club',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (584,'Prado Churubusco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (586,'Hermosillo',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (587,'Paseos de Taxqueña',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2807,'San Francisco Culhuacán Barrio de La Magdalena',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2808,'San Francisco Culhuacán Barrio de Santa Ana',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2831,'San Francisco Culhuacán Barrio de San Juan',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (590,'San Francisco Culhuacán Barrio de San Francisco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (596,'Ajusco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (601,'Romero de Terreros',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (603,'Oxtopulco Universidad',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (606,'Cuadrante de San Francisco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (608,'Pedregal de San Francisco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (612,'El Rosedal',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (613,'Los Reyes',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (614,'Del Niño Jesús',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (622,'Copilco El Bajo',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (633,'Copilco Universidad',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (632,'Copilco El Alto',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (636,'Pedregal de Santo Domingo',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (638,'Ciudad Jardín',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (637,'Atlántida',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (641,'La Candelaria',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (640,'El Rosario',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (644,'Nueva Díaz Ordaz',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (643,'Huayamilpas',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (645,'Educación',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (646,'Petrolera Taxqueña',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (649,'Ex-Ejido de San Francisco Culhuacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (651,'Culhuacán CTM Sección V',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2811,'Culhuacán CTM Sección I',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2810,'Culhuacán CTM Sección II',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (652,'El Centinela',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (653,'Avante',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (655,'Presidentes Ejidales 1a Sección',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2805,'Presidentes Ejidales 2a Sección',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2815,'Culhuacán CTM Sección X-A',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2809,'Culhuacán CTM Sección III',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (657,'Culhuacán CTM Sección VI',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2812,'Culhuacán CTM CROC',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (658,'Culhuacán CTM Sección VII',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2813,'Culhuacán CTM Canal Nacional',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (659,'Culhuacán CTM Sección Piloto',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (660,'Jardines del Pedregal de San Ángel',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2793,'Universidad Nacional Autónoma de México',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (664,'La Otra Banda',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (665,'Insurgentes Cuicuilco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (669,'Pedregal de Santa Úrsula',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (670,'Xotepingo',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (671,'San Pablo Tepetlapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (672,'Adolfo Ruiz Cortínes',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (673,'El Reloj',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (676,'Santa Úrsula Coapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (677,'Joyas del Pedregal',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (678,'Pedregal de Carrasco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (680,'Olímpica',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (686,'Cantil del Pedregal',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (687,'Bosques de Tetlameya',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (690,'El Caracol',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (692,'Los Cedros',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (691,'Alianza Popular Revolucionaria',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (695,'Prados de Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (696,'Emiliano Zapata',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (697,'Los Cipreses',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2806,'Ex-Ejido de San Pablo Tepetlapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (706,'Espartaco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (708,'Jardines de Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (709,'Los Olivos',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (711,'El Parque de Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (714,'Culhuacán CTM Sección VIII',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (715,'Culhuacán CTM Sección IX-A',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2814,'Culhuacán CTM Sección IX-B',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (716,'Carmen Serdán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (717,'Cafetales',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (718,'Emiliano Zapata Fraccionamiento Popular',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (719,'Los Girasoles',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (723,'Las Campanas',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (724,'Santa Cecilia',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (725,'Campestre Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (726,'Culhuacán CTM Sección X',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (727,'Los Sauces',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (728,'El Mirador',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (729,'Villa Quietud',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (730,'Haciendas de Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2792,'Viejo Ejido de Santa Úrsula Coapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (731,'Ex-Hacienda Coapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (674,'Ex-Ejido de Santa Úrsula Coapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (732,'Cuajimalpa',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (735,'Zentlapatl',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (736,'Loma del Padre',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (737,'San Pedro',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2867,'La Manzanita',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (738,'Ahuatenco',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (740,'San Pablo Chimalpa',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (746,'Lomas de Vista Hermosa',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (748,'Cooperativa Palo Alto',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (751,'Granjas Palo Alto',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (752,'Campestre Palo Alto',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (753,'Bosques de las Lomas',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (758,'Lomas del Chamizal',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (766,'San José de los Cedros',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (768,'Granjas Navidad',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (769,'Tepetongo',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (770,'El Ébano',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (774,'El Molino',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (777,'Jesús del Monte',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (780,'Amado Nervo',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (782,'Manzanastitla',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (783,'Adolfo López Mateos',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (785,'El Molinito',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (786,'El Yaqui',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (787,'Lomas de Memetla',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (789,'Ampliación Memetla',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (788,'Memetla',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2869,'Ampliación el Yaqui',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (792,'Santa Fe Cuajimalpa',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (794,'Locaxco',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (797,'Las Tinajas',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (799,'Lomas de San Pedro',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (800,'El Tianguillo',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2866,'1° de Mayo',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (801,'San Lorenzo Acopilco',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (803,'Contadero',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (808,'La Venta',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (809,'Abdías García Soto',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (810,'San Mateo Tlaltenango',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2868,'Santa Rosa Xochiac',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (819,'Cruz Blanca',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (820,'Las Maromas',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (825,'Xalpa',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (827,'La Pila',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (829,'Las Lajas',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (833,'Agua Bendita',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (838,'(Área 1)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (847,'(Área 2)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (850,'(Área 3)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (853,'Tabacalera',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (860,'(Área 4)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (863,'(Área 5)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (867,'(Área 6)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (873,'(Área 7)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (875,'(Área 8)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (879,'(Área 9)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (882,'Hipódromo',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (884,'Condesa',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (886,'Hipódromo Condesa',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (891,'Morelos',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (894,'Peralvillo',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (895,'Valle Gómez',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (896,'Ex-Hipódromo de Peralvillo',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (898,'Maza',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (899,'Felipe Pescador',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (900,'Guerrero',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (906,'Buenavista',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (911,'Santa María la Ribera',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (913,'Santa María Insurgentes',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (914,'Atlampa',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (915,'San Rafael',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (919,'Cuauhtémoc',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (930,'Juárez',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (947,'Roma Norte',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (955,'Doctores',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (966,'Roma Sur',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (970,'Buenos Aires',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (974,'Obrera',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (978,'Tránsito',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (980,'Esperanza',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (981,'Asturias',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (982,'Vista Alegre',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (983,'Paulino Navarro',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (984,'Algarin',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (986,'Ampliación Asturias',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (988,'Nonoalco Tlatelolco',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (989,'San Simón Tolnáhuac',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (991,'Aragón la Villa',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (994,'Rosas del Tepeyac',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (995,'Santa Isabel Tola',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (996,'Tepetates',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (997,'Tepeyac Insurgentes',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (998,'Santiago Atzacoalco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1001,'Villa Gustavo A. Madero',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1003,'15 de Agosto',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1005,'Estanzuela',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1006,'Triunfo de La República',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1007,'La Cruz',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1008,'Dinamita',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1010,'Martín Carrera',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1011,'Gabriel Hernández',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1012,'Ampliación Gabriel Hernández',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1014,'C.T.M. El Risco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1013,'C.T.M. Atzacoalco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1016,'Cuautepec Barrio Alto',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1017,'San Miguel',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1019,'San Antonio',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1021,'Lomas de Cuautepec',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1022,'Malacates',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1227,'Ampliación Malacates',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1023,'Compositores Mexicanos',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1024,'El Tepetatal',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1028,'Forestal',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1029,'Forestal I',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1026,'Arboledas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1027,'Ampliación Arboledas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2887,'La Lengüeta',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2885,'Forestal II',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1031,'Parque Metropolitano',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1033,'La Casilda',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1032,'Juventino Rosas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1034,'Loma La Palma',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2549,'Tlacaélel',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2548,'Luis Donaldo Colosio',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2718,'Prados de Cuautepec',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2717,'Graciano Sánchez',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1035,'Palmatitla',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1036,'Cocoyotes',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1037,'General Felipe Berriozabal',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2687,'Ampliación Cocoyotes',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2688,'6 de Junio',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1038,'Vista Hermosa',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1039,'Tlalpexco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1040,'Ahuehuetes',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1041,'Valle de Madero',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1042,'Del Carmen',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1043,'Cuautepec de Madero',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1046,'Del Bosque',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1048,'Guadalupe Victoria Cuautepec',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1049,'Chalma de Guadalupe',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2884,'Ampliación Chalma de Guadalupe',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1051,'Castillo Chico',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1052,'Castillo Grande',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2883,'Ampliación Castillo Grande',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1053,'Zona Escolar',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1054,'Zona Escolar Oriente',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1056,'El Arbolillo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1061,'Benito Juárez',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1063,'Ampliación Benito Juárez',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1065,'Solidaridad Nacional',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1067,'Residencial Acueducto de Guadalupe',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1069,'Acueducto de Guadalupe',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1070,'Jorge Negrete',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1071,'La Pastora',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2888,'Lindavista Sur',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1072,'Lindavista Norte',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1075,'Candelaria Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1080,'Residencial la Escalera',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1079,'La Purísima Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1085,'Santa María Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1087,'La Laguna Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1088,'San José Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1091,'Guadalupe Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1092,'San Juan y Guadalupe Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1093,'San Rafael Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1095,'San Pedro Zacatenco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2744,'Lomas de San Juan Ixhuatepec 2a Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1097,'Residencial Zacatenco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1098,'Capultitlan',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1100,'Tlacamaca',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1099,'Maximino Ávila Camacho',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1101,'Salvador Díaz Mirón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1102,'Juan González Romero',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1103,'Villa Hermosa',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1105,'El Coyol',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1106,'Nueva Atzacoalco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1108,'Del Obrero',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1109,'Vasco de Quiroga',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1110,'DM Nacional',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1111,'Ferrocarrilera',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1112,'LI Legislatura',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1117,'Granjas Modernas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1118,'Constitución de La República',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1119,'Ampliación San Juan de Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1120,'San Pedro El Chico',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1122,'La Pradera',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1125,'Pradera II Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1126,'San Felipe de Jesús',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1128,'25 de Julio',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1129,'Campestre Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1131,'La Esmeralda',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1135,'Providencia',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1136,'Ampliación Providencia',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1137,'Villa de Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1138,'Ampliación Casas Alemán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1139,'Progreso Nacional',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1141,'Santa Rosa',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1142,'San José de la Escalera',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1143,'Santiago Atepetlac',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1144,'Ampliación Progreso Nacional',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1146,'Guadalupe Proletaria',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1147,'Ampliación Guadalupe Proletaria',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1148,'Nueva Industrial Vallejo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1149,'Siete Maravillas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1150,'Torres Lindavista',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1153,'Lindavista Vallejo I Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1157,'San Bartolo Atepehuacan',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1155,'Churubusco Tepeyac',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1156,'Montevideo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1159,'Planetario Lindavista',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1160,'Valle del Tepeyac',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1161,'Nueva Vallejo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2890,'Lindavista Vallejo III Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2889,'Lindavista Vallejo II Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1162,'Magdalena de las Salinas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1163,'Panamericana',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1164,'Ampliación Panamericana',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1166,'Defensores de La República',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1167,'Héroe de Nacozari',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1169,'Vallejo Poniente',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1168,'Guadalupe Victoria',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1170,'Industrial',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1172,'Estrella',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1174,'Aragón Inguarán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1175,'Tres Estrellas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1176,'Gertrudis Sánchez 1a Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2886,'Gertrudis Sánchez 3a Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1178,'Gertrudis Sánchez 2a Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1180,'7 de Noviembre',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1179,'Guadalupe Tepeyac',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1182,'Faja de Oro',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1181,'Bondojito',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1183,'Ampliación Emiliano Zapata',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1184,'Ampliación Mártires de Río Blanco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1185,'La Joyita',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1187,'Tablas de San Agustín',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1188,'Belisario Domínguez',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1190,'Vallejo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1189,'Guadalupe Insurgentes',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1191,'Mártires de Río Blanco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1192,'Emiliano Zapata',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1194,'La Joya',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1195,'Nueva Tenochtitlán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1193,'Cuchilla La Joya',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1196,'La Malinche',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1197,'Cuchilla del Tesoro',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1199,'San Juan de Aragón VII Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1200,'San Juan de Aragón VI Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1201,'Ex Ejido San Juan de Aragón Sector 32',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1203,'San Juan de Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1202,'El Olivo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1204,'Indeco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1205,'Héroes de Chapultepec',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1206,'Ex Ejido San Juan de Aragón Sector 33',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1207,'San Juan de Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1211,'Héroes de Cerro Prieto',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1212,'Ex Escuela de Tiro',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1213,'Fernando Casas Alemán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1216,'San Juan de Aragón II Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1215,'San Juan de Aragón I Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1217,'San Juan de Aragón III Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1218,'San Juan de Aragón IV Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1219,'San Juan de Aragón V Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1220,'Narciso Bassols',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1222,'C.T.M. Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1233,'Gabriel Ramos Millán Sección Bramadero',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1237,'Ex-Ejido de La Magdalena Mixiuhca',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1238,'Ampliación Gabriel Ramos Millán',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1241,'Gabriel Ramos Millán Sección Cuchilla',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1242,'Carlos Zapata Vela',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1244,'Agrícola Pantitlán',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1250,'Viaducto Piedad',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1251,'Nueva Santa Anita',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1252,'San Pedro',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1253,'San Francisco Xicaltongo',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1254,'Santiago Norte',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1255,'Santa Anita',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1256,'La Cruz',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1257,'Fraccionamiento Coyuya',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1258,'Granjas México',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1262,'Cuchilla Agrícola Oriental',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1263,'Agrícola Oriental',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1265,'El Rodeo',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1269,'La Asunción',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1270,'Zapotla',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1272,'Los Reyes',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1277,'San Miguel',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1278,'Juventino Rosas',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1279,'Tlazintla',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1280,'Gabriel Ramos Millán Sección Tlacotal',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1281,'Gabriel Ramos Millán',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2795,'Los Picos de Iztacalco Sección 2A',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2796,'Los Picos de Iztacalco Sección 1B',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1284,'INPI Picos',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1285,'Los Picos de Iztacalco Sección 1A',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1286,'Santiago Sur',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1288,'Reforma Iztaccíhuatl Norte',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1291,'Militar Marte',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1292,'Reforma Iztaccíhuatl Sur',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1293,'INFONAVIT Iztacalco',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1294,'Santa Cruz',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1295,'Jardines Tecma',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1296,'Campamento 2 de Octubre',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1304,'San Pedro',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1300,'San Ignacio',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1299,'La Asunción',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1301,'San José',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1302,'San Lucas',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1305,'Santa Bárbara',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1303,'San Pablo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1308,'Real del Moral',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1310,'Dr. Alfonso Ortiz Tirado',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1314,'Paseos de Churubusco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1316,'Central de Abasto',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1321,'Sector Popular',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1320,'Escuadrón 201',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1322,'Granjas de San Antonio',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1325,'Cacama',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1328,'Unidad Modelo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1329,'Héroes de Churubusco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1330,'Mexicaltzingo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1331,'Juan Escutia',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1334,'San Lorenzo Xicotencatl',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1335,'Santa Martha Acatitla Norte',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1339,'Ermita Zaragoza',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1341,'Unidad Vicente Guerrero',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1345,'Chinampac de Juárez',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1352,'Renovación',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1355,'Tepalcates',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1357,'Unidad Ejército Constitucionalista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1368,'El Paraíso',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1365,'Ejército de Oriente',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1369,'José María Morelos y Pavón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1364,'Álvaro Obregón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1370,'Ejército de Oriente Zona Peñón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1371,'Progresista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1373,'La Regadera',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1375,'Constitución de 1917',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1376,'Colonial Iztapalapa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1377,'Jacarandas',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1378,'Santa Cruz Meyehualco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1380,'Guadalupe del Moral',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1382,'Leyes de Reforma 1a Sección',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1383,'Leyes de Reforma 2a Sección',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2839,'Leyes de Reforma 3a Sección',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1384,'Cuchilla del Moral',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1388,'Sideral',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1392,'Albarrada',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1393,'Eva Sámano de López Mateos',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1398,'San Miguel',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1399,'Ampliación San Miguel',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1407,'San Juanico Nextipac',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1408,'El Sifón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1414,'San José Aculco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1410,'Aculco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1412,'Jardines de Churubusco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1413,'Magdalena Atlazolpa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1415,'Los Picos VI-B',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1416,'Nueva Rosita',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1417,'Purísima Atlazolpa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1419,'El Triunfo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1418,'Apatlaco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1422,'Ampliación El Triunfo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1426,'San Andrés Tetepilco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2840,'Zacahuitzco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1425,'El Retoño',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1429,'Banjidal',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1430,'Justo Sierra',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1431,'Sinatel',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1432,'Ampliación Sinatel',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1433,'El Prado',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1435,'Santa María Aztahuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2565,'Santa María Aztahuacán Ampliación',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1436,'Santa Martha Acatitla',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1439,'El Edén',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1440,'San Sebastián Tecoloxtitla',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1441,'Santa Martha Acatitla Sur',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1443,'Monte Alban',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1444,'Paraje Zacatepec',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1445,'Santa María Aztahuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1449,'Ejército de Agua Prieta',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1453,'Santiago Acahualtepec',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1454,'Santiago Acahualtepec 1ra. Ampliación',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1455,'Santiago Acahualtepec 2a. Ampliación',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1456,'Lomas de Zaragoza',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2853,'San Miguel Teotongo Sección Guadalupe',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2854,'San Miguel Teotongo Sección Iztlahuaca',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2859,'San Miguel Teotongo Sección Palmitas',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2850,'San Miguel Teotongo Sección Acorralado',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2855,'San Miguel Teotongo Sección Jardines',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2860,'San Miguel Teotongo Sección Puente',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2852,'San Miguel Teotongo Sección Capilla',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2858,'San Miguel Teotongo Sección Mercedes',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2862,'San Miguel Teotongo Sección Rancho Bajo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2861,'San Miguel Teotongo Sección Ranchito',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1481,'San Miguel Teotongo Sección Corrales',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2851,'San Miguel Teotongo Sección Avisadero',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2856,'San Miguel Teotongo Sección La Cruz',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2857,'San Miguel Teotongo Sección Loma Alta',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2863,'San Miguel Teotongo Sección Torres',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1461,'Campestre Potrero',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1462,'Ampliación Emiliano Zapata',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1464,'Lomas de la Estancia',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1465,'Xalpa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1467,'San Pablo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1470,'Citlalli',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1471,'Palmitas',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1473,'Tenorios',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1477,'Barranca de Guadalupe',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1478,'Iztlahuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1480,'Miravalles',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1482,'Miguel de La Madrid Hurtado',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1485,'Buenavista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1491,'Santa Cruz Meyehualco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1488,'Desarrollo Urbano Quetzalcoatl',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1486,'Carlos Hank Gonzalez',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1492,'Degollado',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2726,'Degollado - Mexicatlalli',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1497,'San José Buenavista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1500,'Mixcoatl',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1501,'Lomas de Santa Cruz',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1502,'Los Ángeles Apanoaya',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1505,'Francisco Villa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1506,'La Era',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1508,'Reforma Política',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1511,'Presidentes de México',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1514,'La Polvorilla',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1515,'Las Peñas',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1512,'Insurgentes',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1518,'Consejo Agrarista Mexicano',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1523,'El Triángulo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1524,'Puente Blanco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1527,'Año de Juárez',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1528,'Lomas de San Lorenzo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1531,'San Lorenzo Tezonco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1535,'Estrella Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1536,'Fuego Nuevo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1538,'San Antonio Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1540,'San Simón Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1539,'San Antonio Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1541,'Tula',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1534,'El Mirador',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1532,'Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1542,'Valle de Luces',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1549,'Minerva',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1547,'Granjas Esmeralda',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1548,'Los Cipreses',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1550,'Progreso del Sur',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1551,'Valle del Sur',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1553,'Estrella del Sur',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1557,'Santa Isabel Industrial',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1552,'El Santuario',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1556,'Ricardo Flores Magón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1559,'Ampliación Ricardo Flores Magón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1560,'Ampliación El Santuario',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1565,'Lomas El Manto',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1566,'Los Ángeles',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1562,'El Manto',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1563,'El Molino',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1568,'Paraje San Juan',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1570,'San Miguel 8va. Ampliación',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1571,'Plan de Iguala',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1576,'San Juan Joya',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1573,'Ampliación Paraje San Juan',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1578,'Los Reyes Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1580,'Ampliación Los Reyes',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1586,'San Juan Xalpa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1588,'San Nicolás Tolentino',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1589,'Santa María del Monte',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1591,'Ampliación Veracruzana',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1590,'Estado de Veracruz',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1594,'Paraje San Juan Cerro',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1595,'Benito Juárez',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1596,'Bellavista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1600,'Cerro de La Estrella',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1597,'Ampliación Bellavista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1601,'Parque Nacional Cerro  de la Estrella',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1598,'Casa Blanca',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1602,'El Rodeo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1607,'San Juan Estrella',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1613,'San Andrés Tomatlán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1609,'12 de Diciembre',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1611,'San Andrés Tomatlán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1616,'Santa María Tomatlán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1621,'Granjas Estrella',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1620,'El Vergel',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1629,'Lomas Estrella',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1633,'Carlos Jonguitud Barrios',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1639,'San Lorenzo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1638,'San Antonio',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1637,'Guadalupe',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1640,'La Esperanza',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1643,'José López Portillo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1644,'El Rosario',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1645,'Jardines de San Lorenzo Tezonco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1647,'USCOVI',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1646,'Celoalliotli',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1648,'El Molino Tezonco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1961,'Allapetlalli',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2736,'La Planta',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1651,'Cananea',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1652,'Valle de San Lorenzo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1672,'Lomas Quebradas',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1674,'San Bartolo Ameyalco',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1673,'La Malinche',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1675,'Cuauhtémoc',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1681,'El Maestro',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1682,'San Jerónimo Lídice',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1686,'San Bernabé Ocotepec',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1690,'El Tanque',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1691,'Las Cruces',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1692,'Los Padres',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1693,'Lomas de San Bernabé',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1694,'Huayatla',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1695,'Ampliación Potrerillo',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1696,'Ampliación Lomas de San Bernabé',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2669,'Tierra Unida',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1697,'Palmas',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1698,'Atacaxco',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1700,'Vista Hermosa',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1701,'Barros Sierra',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1702,'San Jerónimo Aculco',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1703,'Barrio San Francisco',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1707,'Barranca Seca',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1710,'El Rosal',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1713,'El Toro',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1714,'Potrerillo',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2706,'El Ocotal',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1716,'La Carbonera',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1717,'Pueblo Nuevo Alto',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1718,'Pueblo Nuevo Bajo',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1720,'El Ermitaño',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1721,'Héroes de Padierna',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1723,'Santa Teresa',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1728,'La Cruz',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1730,'San Francisco',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1731,'La Guadalupe',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1732,'La Concepción',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1734,'Plazuela del Pedregal',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1733,'Las Calles',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1735,'San Nicolás Totolapan',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1737,'La Magdalena',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1738,'Las Huertas',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1739,'Tierra Colorada',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2775,'Lomas de Chapultepec II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2776,'Lomas de Chapultepec III Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1745,'Lomas de Chapultepec I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2774,'Lomas de Chapultepec VIII Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2777,'Lomas de Chapultepec IV Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2778,'Lomas de Chapultepec V Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2779,'Lomas de Chapultepec VI Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2780,'Lomas de Chapultepec VII Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1752,'Molino del Rey',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2772,'Bosque de Chapultepec III Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1754,'Bosque de Chapultepec II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1757,'Lomas Hermosa',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1758,'Lomas de Sotelo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1761,'San Lorenzo Tlaltenango',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1764,'Periodista',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1765,'Argentina Poniente',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1767,'Ignacio Manuel Altamirano',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1768,'10 de Abril',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1770,'México Nuevo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1771,'San Joaquín',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1772,'Argentina Antigua',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1773,'Torre Blanca',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1774,'Ampliación Torre Blanca',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1775,'Huíchapan',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1776,'San Diego Ocoyoacac',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1777,'Verónica Anzures',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1780,'Mariano Escobedo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1782,'Anáhuac I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2771,'Anáhuac II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1784,'Un Hogar Para Nosotros',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1785,'Santo Tomas',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1786,'Plutarco Elías Calles',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1787,'Agricultura',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1789,'Tlaxpana',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1791,'Popotla',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1794,'Tacuba',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1793,'Legaria',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1796,'Nextitla',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1798,'Ventura Pérez de Alva',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1797,'Pensil Norte',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1800,'San Juanico',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1799,'Reforma Pensil',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1802,'Modelo Pensil',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1803,'Peralitos',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1801,'Ahuehuetes Anáhuac',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1804,'Dos Lagos',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1807,'Los Manzanos',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1806,'Lago Sur',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1805,'Lago Norte',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1809,'Deportivo Pensil',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1808,'5 de Mayo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1811,'Francisco I Madero',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1812,'Popo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1813,'Ampliación Popo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1814,'Cuauhtémoc Pensil',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1815,'Pensil Sur',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1816,'Irrigación',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2781,'Polanco I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1822,'Granada',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1823,'Ampliación Granada',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2782,'Polanco II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2783,'Polanco III Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2784,'Polanco IV Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2785,'Polanco V Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1833,'Bosque de Chapultepec I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1838,'Anzures',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1843,'Residencial Militar',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1844,'Manuel Avila Camacho',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1846,'Campo Militar 1',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1855,'Reforma Social',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1856,'Bosque de las Lomas',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1858,'Escandón I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2773,'Escandón II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1860,'16 de Septiembre',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1861,'América',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1862,'Daniel Garza',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1863,'Ampliación Daniel Garza',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1864,'San Miguel Chapultepec I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2786,'San Miguel Chapultepec II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1865,'Observatorio',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1867,'Tacubaya',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1869,'Lomas de Bezares',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1870,'Real de las Lomas',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2789,'Lomas de Reforma',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1874,'Lomas Altas',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1883,'Santa Martha',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1877,'La Concepción',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1879,'Los Ángeles',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1884,'Villa Milpa Alta Centro',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1878,'La Luz',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1881,'San Mateo',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1882,'Santa Cruz',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1889,'San Agustin',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1890,'San Agustin Ohtenco',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1895,'Xaltipac',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1891,'Cruztitla',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1894,'Tenantitla',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1896,'Xochitepec',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1893,'Tecaxtitla',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2010,'Emiliano Zapata',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2011,'La Conchita',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1897,'Nochtla',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1899,'Panchimalco',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1901,'Tula',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1898,'Ocotitla',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1903,'San Bartolomé Xicomulco',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1904,'San Salvador Cuauhtenco',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1905,'Centro',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1906,'San Juan',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1907,'San Miguel',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2870,'Chalmita',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1910,'San Lorenzo Tlacoyucan',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1911,'San Jerónimo Miacatlán',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1912,'San Francisco Tecoxpa',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1913,'San Juan Tepenahuac',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2871,'La Lupita Teticpac',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1880,'San Marcos',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2873,'San Miguel',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2874,'San José',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2872,'La Lupita Xolco',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1919,'La Asunción',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1923,'Santa Cecilia',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1924,'San José',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1925,'San Juan',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1926,'San Mateo',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1928,'La Habana',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1930,'Santa Ana',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1929,'La Guadalupe',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1931,'La Magdalena',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1932,'San Miguel',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1933,'Los Reyes',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1934,'Quiahuatla',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2497,'San Sebastián',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2898,'San Isidro',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1935,'San Andrés',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1937,'La Guadalupe',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1939,'Cañada',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1940,'La Poblanita',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1943,'Santiago',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1942,'Ampliación Santa Catarina',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2576,'La Mesa',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2577,'Chichilaula',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2575,'Teozoma',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1944,'San Francisco Apolocalco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1945,'La Concepción',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1946,'San Miguel',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1947,'Miguel Hidalgo',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1949,'Los Olivos',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1950,'Las Arboledas',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1951,'Ampliación Los Olivos',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1952,'La Nopalera',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1955,'Granjas Cabrera',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1957,'La Turba',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1958,'Del Mar',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2580,'La Draga',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1959,'Villa Centroamericana',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1962,'Agrícola Metropolitana',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1963,'Santa Ana Centro',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2894,'Santiago Norte',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1964,'Santiago Centro',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2891,'Santa Ana Poniente',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2893,'Santa Ana Norte',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1966,'Zapotitla',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2897,'Ampliación Zapotitla',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1970,'La Estación',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1971,'La Conchita Zapotitlán',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2895,'Santa Ana Sur',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2896,'Santiago Sur',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2586,'Tempiluli',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1972,'San Francisco Tlaltenco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1973,'López Portillo',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1975,'Ampliación José López Portillo',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1976,'Selene',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1978,'Ampliación Selene',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1980,'Zacatenco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1982,'Ojo de Agua',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1981,'Guadalupe Tlaltenco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1983,'El Triángulo',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1985,'San Agustín',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2593,'La Soledad',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1986,'La Concepción',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1987,'La Lupita',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1988,'Francisco Villa',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1989,'La Loma',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1991,'La Asunción',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1990,'Jaime Torres Bodet',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1994,'Tierra Blanca',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1992,'El Rosario',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2587,'Ampliación La Conchita',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2916,'Olivar Santa María',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1995,'Peña Alta',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1997,'Jardines del Llano',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1998,'Potrero del Llano',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2002,'San Bartolomé',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2004,'Los Reyes',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2005,'Santa Cruz',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2007,'San Agustín',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2008,'San Miguel',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2892,'Tepantitlamilco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2009,'San Nicolás Tetelco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2027,'Tlalpan Centro',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2876,'Tlalpan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2029,'Parque del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2035,'Villa Olímpica',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2036,'Isidro Fabela',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2041,'Ampliación Isidro Fabela',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2042,'Cantera Puente de Piedra',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2043,'Pueblo Quieto',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2044,'Comuneros de Santa Úrsula',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2045,'Toriello Guerra',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2046,'Peña Pobre',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2048,'Rómulo Sánchez Mireles',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2049,'San Fernando',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2050,'San Pedro Apóstol',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2052,'Del Niño Jesús',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2051,'Belisario Domínguez Sección XVI',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2054,'La Joya',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2062,'Pedregal de San Nicolás 3A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2063,'Pedregal de San Nicolás 4A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2061,'Pedregal de San Nicolás 2A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2064,'Pedregal de San Nicolás 5A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2060,'Pedregal de San Nicolás 1A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2067,'Chichicaspatl',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2881,'Ampliación Fuentes del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2071,'Rincón del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2074,'Fuentes del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2078,'Lomas del Pedregal Framboyanes',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2079,'Popular Santa Teresa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2081,'Jardines del Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2080,'Héroes de Padierna',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2083,'Colinas del Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2084,'Torres de Padierna',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2085,'Jardines en la Montaña',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2087,'Six Flags (Reino Aventura)',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2187,'Parque Nacional Bosque del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2088,'Cuchilla de Padierna',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2090,'Lomas del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2091,'Cultura Maya',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2093,'Los Encinos',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2094,'Lomas de Padierna',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2095,'Lomas Hidalgo',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2096,'Cruz del Farol',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2098,'Miguel Hidalgo 2A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2099,'Miguel Hidalgo 3A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2100,'Miguel Hidalgo 4A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2101,'Miguel Hidalgo',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2102,'El Capulín',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2103,'Miguel Hidalgo 1A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2106,'Zacayucan Peña Pobre',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2107,'De Caramagüey',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2108,'La Lonja',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2109,'La Fama',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2110,'Primavera',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2694,'Solidaridad',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2111,'Paraje 38',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2112,'Verano',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2113,'Nueva Oriental Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2114,'Residencial Acoxpa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2115,'Residencial Miramontes',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2879,'Ex Hacienda Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2120,'Belisario Domínguez',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2121,'Floresta Coyoacán',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2123,'Vergel Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2125,'Rinconada Coapa 2A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2126,'Tenorios',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2132,'Granjas Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2134,'Rinconada Coapa 1A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2146,'Vergel del Sur',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2145,'Vergel de Coyoacán',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2147,'Prado Coapa 1A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2149,'Prado Coapa 2A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2150,'Prado Coapa 3A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2877,'Magisterial Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2153,'Magisterial',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2155,'Residencial Chimali',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2158,'Villa Lázaro Cárdenas',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2157,'San Lorenzo Huipulco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2159,'Arboledas del Sur',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2161,'Hacienda San Juan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2167,'San Bartolo El Chico',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2163,'A.M.S.A',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2168,'Rancho los Colorines',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2169,'Ex Hacienda San Juan de Dios',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2170,'Guadalupe',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2172,'Arenal de Guadalupe',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2176,'Residencial Villa Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2878,'Villa Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2175,'Narciso Mendoza',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2174,'Rinconada Las Hadas',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2179,'San Andrés Totoltepec',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2182,'Divisadero',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2183,'Nuevo Renacimiento de Axalco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2184,'Tecorral',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2186,'Fuentes Brotantes',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2190,'Mesa de los Hornos',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2188,'Cumbres de Tepetongo',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2192,'Santa Úrsula Xitla',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2195,'Tlaxcaltenco la Mesa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2194,'Texcaltenco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2196,'San Juan Tepeximilpa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2197,'Tepeximilpa la Paz',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2200,'Santísima Trinidad',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2201,'El Truenito',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2203,'Tlalcoligia',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2205,'Pedregal de Santa Úrsula Xitla',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2206,'Pedregal de las Águilas',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2207,'Los Volcanes',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2208,'El Mirador 1A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2210,'El Mirador 3A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2209,'El Mirador 2A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2212,'Atocpa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2213,'Tlalpuente',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2214,'Plan de Ayala',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2216,'La Palma',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2219,'Viveros Coatectlán',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2220,'La Magdalena Petlacalco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2221,'San Miguel Xicalco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2222,'San Miguel Topilejo',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2223,'La Quinta',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2224,'Estación Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2227,'Estrella Mora',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2229,'Valle Escondido',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2231,'Colinas del Bosque',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2232,'Las Tórtolas',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2233,'Arenal Tepepan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2741,'Club de Golf México',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2236,'San Buenaventura',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2237,'Chimalcoyoc',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2238,'Villa Tlalpan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2240,'Ejidos de San Pedro Mártir',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2241,'Fuentes de Tepepan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2245,'Valle de Tepepan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2246,'Juventud Unida',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2247,'Movimiento Organizado de Tlalpan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2880,'Rinconada El Mirador',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2252,'San Pedro Mártir',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2882,'Heróico Colegio Militar',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2453,'Dolores Tlali',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2255,'La Magueyera',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2256,'Valle Verde o Lomas Verdes',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2258,'Tlalmille',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2259,'Mirador del Valle',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2260,'María Esther Zuno de Echeverría',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2261,'San Miguel Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2262,'Santo Tomas Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2263,'Belvedere Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2265,'Lomas de Cuilotepec',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2266,'Zacatón',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2695,'Lomas de Tepemecatl',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2267,'San Nicolás 2',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2268,'Vistas del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2269,'Bosques del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2270,'2 de Octubre',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2272,'Lomas de Padierna Sur',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2273,'Mirador I',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2274,'Mirador II',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2275,'Chimilli',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2277,'Rancho Viejo',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2278,'Héroes de 1910',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2279,'El Charco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2280,'San Jorge',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2286,'Parres El Guarda',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2287,'General Ignacio Zaragoza',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2289,'Valentín Gómez Farias',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2290,'Puebla',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2291,'Zona Centro',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2293,'Janitzio',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2294,'Valle Gómez',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2296,'Popular Rastro',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2295,'Nicolás Bravo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2297,'Emilio Carranza',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2298,'Michoacana',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2299,'Ampliación Michoacana',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2300,'Ampliación 20 de Noviembre',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2301,'Morelos',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2302,'Penitenciaria',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2303,'10 de Mayo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2305,'20 de Noviembre',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2307,'5o Tramo 20 de Noviembre',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2308,'Felipe Ángeles',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2309,'Azteca',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2310,'Tres Mosqueteros',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2311,'Ampliación Venustiano Carranza',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2312,'Venustiano Carranza',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2313,'Ampliación Penitenciaria',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2315,'Progresista',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2316,'Escuela de Tiro',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2319,'7 de Julio',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2320,'Romero Rubio',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2321,'Simón Bolívar',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2322,'Ampliación Simón Bolívar',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2323,'Aquiles Serdán',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2324,'1° de Mayo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2325,'Damián Carmona',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2326,'Revolución',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2327,'Miguel Hidalgo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2328,'Moctezuma 1a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2330,'Pensador Mexicano',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2331,'Peñón de los Baños',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2332,'Moctezuma 2a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2333,'Santa Cruz Aviación',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2334,'Arenal 1a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2335,'Cuchilla Pantitlán',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2336,'México (Lic. Benito Juárez)',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2340,'Caracol',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2342,'Arenal 4a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2351,'Arenal Puerto Aéreo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2343,'Ampliación Caracol',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2344,'Arenal 3a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2346,'Adolfo López Mateos',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2347,'Arenal 2a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2349,'Federal',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2350,'Industrial Puerto Aéreo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2353,'4 Árboles',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2354,'Aviación Civil',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2355,'Ampliación Aviación Civil',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2356,'Jamaica',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2358,'Merced Balbuena',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2359,'Lorenzo Boturini',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2360,'Artes Gráficas',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2361,'Sevilla',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2362,'Magdalena Mixiuhca',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2363,'La Magdalena Mixiuhca',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2364,'Aarón Sáenz',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2365,'Jardín Balbuena',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2372,'Del Parque',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2376,'Aeronáutica Militar',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2377,'24 de Abril',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2378,'Álvaro Obregón',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2381,'San Juan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2379,'La Concepción Tlacoapa',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2380,'San Antonio',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2832,'San Bartolo El Chico',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2388,'Paseos del Sur',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2386,'Las Peritas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2385,'Bosque Residencial del Sur',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2396,'Santa María Tepepan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2395,'San Juan Tepepan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2399,'Ampliación Tepepan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2402,'La Noria',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2919,'Ampliación La Noria',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2401,'Huichapan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2403,'Potrero de San Bernardino',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2405,'18',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2407,'San Lorenzo La Cebada',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2833,'Rinconada Coapa',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2408,'Mercado de Flores Plantas y Hortalizas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2409,'Ampliación San Marcos Norte',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2412,'San Lorenzo',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2411,'La Asunción',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2413,'Jardines del Sur',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2414,'San Marcos',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2415,'Tierra Nueva',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2420,'Pblo. Stgo.Tepalcatlalpan, U. H. Rinconada del Sur',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2421,'El Mirador',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2425,'La Guadalupita',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2426,'Santa Crucita',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2424,'El Rosario',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2423,'Belén',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2430,'San Esteban',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2429,'San Diego',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2428,'San Cristóbal',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2427,'La Santísima',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2434,'Xaltocan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2432,'San Pedro',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2433,'Tablas de San Lorenzo',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2439,'Santa Cruz Xochitepec',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2443,'Santiago Tepalcatlalpan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2444,'La Concha',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2458,'San Lucas Xochimanca',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2459,'La Cañada',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2460,'San Lucas Oriente',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2462,'Texmic',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2464,'San Lorenzo Atemoaya',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2465,'Lomas de Tonalco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2466,'San Jerónimo',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2467,'El Jazmín',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2468,'Rancho Tejomulco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2470,'Xochipilli',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2471,'Año de Juárez',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2899,'Pocitos',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2472,'Santa María Nativitas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2480,'Lomas de Nativitas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2473,'Ampliación Nativitas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2475,'Santa Cruz Acalpixca',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2900,'Apatlaco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2901,'Del Puente',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2902,'La Gallera',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2904,'Calpulco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2903,'Tetitla',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2478,'La Planta',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2834,'Las Flores',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2479,'Las Cruces',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2905,'Ahualapa',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2481,'Valle de Santa María',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2482,'San Gregorio Atlapulco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2906,'San Andrés',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2907,'Los Reyes',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2908,'3 de Mayo',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2909,'San Antonio',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2483,'La Candelaria',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2484,'San Luis Tlaxialtemalco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2910,'Niños Héroes',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2911,'La Asunción',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2913,'Santa Cecilia',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2912,'San Sebastián',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2485,'San José',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2489,'San Juan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2487,'La Guadalupana',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2490,'San Juan Moyotepec',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2491,'San Juan Minas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2494,'Quirino Mendoza',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2495,'Del Carmen',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2498,'San Isidro',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2499,'Guadalupita',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2500,'Las Animas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2501,'Calyequita',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2502,'San Felipe',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2503,'Santiaguito',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2600,'El Mirador',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2604,'Cristo Rey',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2835,'Cerrillos Segunda Sección',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2504,'Cerrillos Primera Sección',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2602,'El Sacrificio',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2836,'Cerrillos Tercera Sección',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2508,'Nativitas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2509,'Las Mesitas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2510,'San Mateo Xalpa',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2607,'Rosario Tlali',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2515,'San Andrés Ahuayucan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2516,'Santa Inés',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2914,'El Calvario',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2519,'Santa Cruz Chavarrieta',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2520,'Chapultepec',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2521,'Santa Cruz de Guadalupe',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2527,'Santa Cecilia Tepetlapa',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2530,'San Francisco Tlalnepantla',13,9);


INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1,'San Ángel',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (5,'Los Alpes',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (6,'Guadalupe Inn',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (10,'Florida',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (9,'Axotla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (12,'Campestre',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (14,'Tlacopac',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (16,'Ex-Hacienda de Guadalupe Chimalistac',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (17,'Altavista',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (18,'San Ángel Inn',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (19,'Chimalistac',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (22,'Progreso Tizapan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (24,'Ermita Tizapan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (25,'La Otra Banda',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (28,'Tizapan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (26,'Loreto',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (31,'Pólvora',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (33,'La Conchita',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (37,'Ampliación El Capulín',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (39,'Liberales de 1857',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (34,'Belém de las Flores',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (36,'El Capulín',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (42,'Acueducto',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (46,'Hidalgo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (47,'Las Américas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (44,'Cove',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (43,'Ampliación Acueducto',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (56,'Real del Monte',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (53,'Molino de Santo Domingo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (52,'Paraíso',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (58,'Reacomodo Pino Suárez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (60,'José Maria Pino Suárez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (59,'Bellavista',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (64,'Tolteca',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (63,'Cristo Rey',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (66,'Bosque',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (68,'Maria G. de García Ruiz',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (69,'1a Victoria',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (67,'Isidro Fabela',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (74,'Abraham M. González',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (77,'8 de Agosto',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (76,'Carola',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (78,'San Pedro de los Pinos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (79,'Arturo Martínez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (82,'Santa Fe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (84,'Lomas de Santa Fe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (89,'Mártires de Tacubaya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (86,'Cuevitas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2681,'La Estrella',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (90,'Zenón Delgado',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (85,'Bonanza',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (87,'El Cuernito',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (92,'Campo de Tiro los Gamitos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (132,'El Piru Santa Fe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (97,'Los Gamitos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (98,'Tlapechico',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (224,'El Piru 2a Ampliación',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (102,'La Huerta',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (103,'Pueblo Nuevo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (108,'Tecolalco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (104,'El Árbol',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (106,'Lomas de Nuevo México',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (105,'Ladera',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (107,'Margarita Maza de Juárez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (109,'Ampliación La Cebada',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2845,'La Mexicana 2a Ampliación',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (112,'La Mexicana',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (114,'La Palmita',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (110,'Calzada Jalalpa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (115,'Liberación Proletaria',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (113,'Ampliación La Mexicana',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (118,'2a Sección Cañada',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (117,'1a Sección Cañada',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (121,'La Presa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (123,'Golondrinas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (120,'El Tejocote',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (124,'Golondrinas 1a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (127,'Lomas de Capula',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (125,'Golondrinas 2a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (131,'Villa Solidaridad',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (93,'El Pirul',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (133,'Desarrollo Urbano',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (136,'Lomas de Becerra',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (141,'La Joya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (138,'Arvide',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (139,'El Pocito',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (140,'Francisco Villa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (144,'El Rodeo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (146,'Reacomodo El Cuernito',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (147,'Piloto Adolfo López Mateos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (148,'Presidentes',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (153,'Ampliación Jalalpa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (152,'Jalalpa Tepito 2a Ampliación',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (154,'Jalalpa Tepito',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (156,'Ampliación Piloto Adolfo López Mateos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (160,'1a Ampliación Presidentes',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (161,'2a Ampliación Presidentes',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (163,'San Gabriel',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (164,'Carlos A. Madrazo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (168,'Paseo de las Lomas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (170,'Bejero del Pueblo Santa Fe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (171,'Santa Fe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2683,'Santa Fe La Loma',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2864,'Santa Fe Centro Ciudad',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2682,'Santa Fe Peña Blanca',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (172,'Jalalpa El Grande',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (176,'Santa Fe Tlayapaca',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (181,'Olivar del Conde 1a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (184,'Preconcreto',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (187,'Galeana',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (188,'Olivar del Conde 2a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (194,'Palmas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (193,'Barrio Norte',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (195,'Minas Cristo Rey',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (196,'Sacramento',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (197,'Santa María Nonoalco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (198,'Colina del Sur',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (202,'Hogar y Redención',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (204,'Alfonso XIII',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (207,'Molino de Rosas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (206,'Alfalfar',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (209,'Lomas de Plateros',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (211,'La Cascada',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (213,'Santa Lucía',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2704,'Miguel Gaona Armenta',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (221,'Santa Lucía Chantepec',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (222,'Garcimarrero',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2636,'Los Cedros',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (223,'La Araña',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (225,'Estado de Hidalgo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (227,'Piru Santa Lucía',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (94,'Ampliación Los Pirules',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (226,'Ampliación Estado de Hidalgo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2841,'El Politoco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (229,'Corpus Christy',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (232,'Tepopotla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (233,'Acuilotla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2643,'Cooperativa Unión Olivos',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (238,'Balcones de Cehuayo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (239,'Cehuaya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (240,'Llano Redondo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (241,'Punta de Cehuaya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2638,'Villa Progresista',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (243,'Dos Ríos del Pueblo Santa Lucía',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (245,'Tepeaca',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2849,'Rinconada Las Cuevitas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (246,'Ampliación Tepeaca',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (249,'Canutillo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (250,'Canutillo 3a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (248,'Ave Real',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (251,'Canutillo 2a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2747,'Hueytlale',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (255,'Reacomodo Valentín Gómez Farías',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (259,'Tarango',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (261,'El Rincón',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (263,'Merced Gómez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2639,'Profesor J. Arturo López Martínez',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (265,'Colinas de Tarango',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (267,'Arcos Centenario',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2745,'Ex-Hacienda de Tarango',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (268,'La Martinica',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (269,'Rinconada de Tarango',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (270,'Lomas de Tarango',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (271,'Lomas de Puerta Grande',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (273,'Puerta Grande',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2640,'Los Juristas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (275,'Herón Proal',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (276,'Ponciano Arriaga',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (280,'Ampliación Tlacuitlapa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (281,'2o Reacomodo Tlacuitlapa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2848,'Santa Lucía Chantepec',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2844,'El Ruedo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (277,'La Milagrosa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (278,'Palmas Axotitla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (279,'Tlacuitlapa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (284,'San Agustín del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (282,'La Joyita del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (283,'Ocotillos del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (286,'Tecalcapa del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (285,'2a Del Moral del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (287,'Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (292,'El Mirador del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (291,'El Encino del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (294,'Las Águilas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (295,'Ampliación Alpes',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (297,'Lomas de Guadalupe',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (298,'Alcantarilla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (299,'Lomas de las Águilas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (300,'Puente Colorado',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (303,'La Peñita del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (304,'San Clemente Norte',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2842,'San Clemente Sur',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (307,'Las Águilas 3er Parque',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (305,'Las Águilas 1a Sección',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (306,'Las Águilas 2o Parque',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (308,'Ampliación Las Águilas',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (309,'Atlamaya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (311,'La Herradura del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (310,'Flor de María',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (316,'San José del Olivar',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (315,'La Angostura',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (319,'Tizampampano del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (318,'Olivar de los Padres',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (323,'Miguel Hidalgo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (324,'Lomas de los Ángeles del Pueblo Tetelpan',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (325,'Lomas de San Ángel Inn',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (327,'San Bartolo Ameyalco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (328,'Rancho San Francisco Pueblo San Bartolo Ameyalco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (330,'Villa Verdún',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2843,'Ejido San Mateo',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (332,'Lomas Axomiatla',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (333,'Santa Rosa Xochiac',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (334,'Torres de Potrero',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (337,'Rincón de la Bolsa',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2847,'Rancho del Carmen del Pueblo San Bartolo Ameyalco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (341,'Lomas de Chamontoya',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (343,'Tlacoyaque',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (344,'Lomas de La Era',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (3,'Lomas del Capulín',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (345,'Lomas de los Cedros',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (347,'Jardines del Pedregal',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2846,'San Jerónimo Aculco',10,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (353,'Centro de Azcapotzalco',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (356,'Los Reyes',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (357,'San Rafael',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (358,'Nuevo Barrio San Rafael',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (361,'Santo Tomás',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (360,'San Marcos',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (362,'Del Maestro',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (363,'San Sebastián',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (364,'Libertad',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (365,'Santa María Malinalco',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (369,'Un Hogar Para Cada Trabajador',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (368,'Sindicato Mexicano de Electricistas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (372,'Nextengo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (371,'Del Recreo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (374,'Clavería',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (376,'Sector Naval',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (377,'San Álvaro',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (378,'Ángel Zimbrón',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (379,'El Rosario',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (385,'San Martín Xochinahuac',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (389,'Nueva El Rosario',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (390,'Nueva España',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (391,'Tierra Nueva',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (392,'Santa Inés',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (393,'Pasteros',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (394,'Santo Domingo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (396,'Reynosa Tamaulipas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (397,'Santa Bárbara',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (398,'San Andrés',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (399,'San Andrés',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (400,'Santa Catarina',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (402,'Industrial Vallejo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (404,'Ferrería',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (405,'San Andrés de las Salinas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (406,'Huautla de las Salinas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (407,'Santa Cruz de las Salinas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (409,'Las Salinas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (410,'San Juan Tlihuaca',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (412,'Prados del Rosario',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (415,'Ex-Hacienda el Rosario',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (418,'Providencia',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (421,'Tezozomoc',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (422,'La Preciosa',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (423,'Ampliación Petrolera',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (425,'Petrolera',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (427,'San Mateo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (428,'Unidad Cuitlahuac',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (430,'El Jagüey',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (431,'Estación Pantaco',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (439,'Jardín Azpeitia',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (441,'Pro-Hogar',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (443,'Coltongo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2798,'Coltongo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (444,'Monte Alto',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (445,'Trabajadores de Hierro',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (446,'Euzkadi',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (447,'Cosmopolita',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (448,'Potrero del Llano',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (449,'San Miguel Amantla',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (451,'San Pedro Xalpa',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (454,'Ampliación San Pedro Xalpa',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (456,'San Antonio',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (457,'San Bartolo Cahualtongo',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (459,'San Francisco Tetecala',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (461,'Santiago Ahuizotla',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (463,'Santa Lucía',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (462,'Industrial San Antonio',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (464,'Santa Cruz Acayucan',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (465,'Plenitud',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (466,'Santa Apolonia',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (467,'Nueva Santa María',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (469,'Ignacio Allende',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (470,'Victoria de las Democracias',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (473,'San Bernabé',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (474,'Obrero Popular',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (476,'Tlatilco',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (477,'San Salvador Xochimanca',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (478,'Aguilera',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (479,'Aldana',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (480,'Ampliación Cosmopolita',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (481,'Liberación',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (482,'Porvenir',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (483,'Del Gas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (484,'San Francisco Xocotitla',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (485,'Ampliación Del Gas',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (486,'Arenal',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (487,'Patrimonio Familiar',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (488,'La Raza',2,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (489,'Piedad Narvarte',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (491,'Atenor Salas',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (493,'Narvarte Poniente',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2623,'Narvarte Oriente',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (496,'Del Valle Centro',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2624,'Insurgentes San Borja',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2621,'Del Valle Norte',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2622,'Del Valle Sur',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (501,'Tlacoquemécatl',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (505,'Actipan',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (506,'Acacias',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (507,'Portales Sur',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2625,'Portales Norte',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (509,'Santa Cruz Atoyac',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (512,'Residencial Emperadores',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (513,'Xoco',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (515,'General Pedro María Anaya',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (516,'Álamos',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (518,'Postal',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (519,'Miguel Alemán',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (521,'Josefa Ortiz de Domínguez',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (522,'Niños Héroes',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (523,'Nativitas',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (525,'Moderna',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (526,'Iztaccihuatl',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (527,'Villa de Cortes',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (528,'Del Carmen',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (529,'Zacahuitzco',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (530,'Albert',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (531,'Portales Oriente',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (532,'Miravalle',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (533,'Ermita',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (534,'Vértiz Narvarte',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (535,'Américas Unidas',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (536,'Periodista',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (537,'Independencia',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (538,'Del Lago',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (539,'Letrán Valle',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (540,'San Simón Ticumac',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (541,'Santa María Nonoalco',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (542,'Ciudad de los Deportes',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (543,'Nochebuena',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (544,'San Juan',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (545,'Extremadura Insurgentes',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (546,'San Pedro de los Pinos',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (548,'Nápoles',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (552,'8 de Agosto',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (554,'Ampliación Nápoles',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (556,'San José Insurgentes',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (558,'Mixcoac',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (559,'Insurgentes Mixcoac',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (561,'Merced Gómez',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (562,'Crédito Constructor',14,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (563,'Villa Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (566,'Santa Catarina',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (567,'La Concepción',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (569,'San Lucas',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (570,'Parque San Andrés',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (571,'Del Carmen',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (577,'San Diego Churubusco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (578,'San Mateo',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (580,'Campestre Churubusco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (582,'Churubusco Country Club',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (584,'Prado Churubusco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (586,'Hermosillo',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (587,'Paseos de Taxqueña',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2807,'San Francisco Culhuacán Barrio de La Magdalena',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2808,'San Francisco Culhuacán Barrio de Santa Ana',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2831,'San Francisco Culhuacán Barrio de San Juan',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (590,'San Francisco Culhuacán Barrio de San Francisco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (596,'Ajusco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (601,'Romero de Terreros',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (603,'Oxtopulco Universidad',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (606,'Cuadrante de San Francisco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (608,'Pedregal de San Francisco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (612,'El Rosedal',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (613,'Los Reyes',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (614,'Del Niño Jesús',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (622,'Copilco El Bajo',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (633,'Copilco Universidad',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (632,'Copilco El Alto',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (636,'Pedregal de Santo Domingo',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (638,'Ciudad Jardín',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (637,'Atlántida',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (641,'La Candelaria',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (640,'El Rosario',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (644,'Nueva Díaz Ordaz',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (643,'Huayamilpas',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (645,'Educación',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (646,'Petrolera Taxqueña',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (649,'Ex-Ejido de San Francisco Culhuacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (651,'Culhuacán CTM Sección V',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2811,'Culhuacán CTM Sección I',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2810,'Culhuacán CTM Sección II',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (652,'El Centinela',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (653,'Avante',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (655,'Presidentes Ejidales 1a Sección',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2805,'Presidentes Ejidales 2a Sección',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2815,'Culhuacán CTM Sección X-A',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2809,'Culhuacán CTM Sección III',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (657,'Culhuacán CTM Sección VI',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2812,'Culhuacán CTM CROC',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (658,'Culhuacán CTM Sección VII',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2813,'Culhuacán CTM Canal Nacional',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (659,'Culhuacán CTM Sección Piloto',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (660,'Jardines del Pedregal de San Ángel',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2793,'Universidad Nacional Autónoma de México',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (664,'La Otra Banda',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (665,'Insurgentes Cuicuilco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (669,'Pedregal de Santa Úrsula',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (670,'Xotepingo',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (671,'San Pablo Tepetlapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (672,'Adolfo Ruiz Cortínes',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (673,'El Reloj',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (676,'Santa Úrsula Coapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (677,'Joyas del Pedregal',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (678,'Pedregal de Carrasco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (680,'Olímpica',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (686,'Cantil del Pedregal',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (687,'Bosques de Tetlameya',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (690,'El Caracol',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (692,'Los Cedros',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (691,'Alianza Popular Revolucionaria',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (695,'Prados de Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (696,'Emiliano Zapata',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (697,'Los Cipreses',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2806,'Ex-Ejido de San Pablo Tepetlapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (706,'Espartaco',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (708,'Jardines de Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (709,'Los Olivos',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (711,'El Parque de Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (714,'Culhuacán CTM Sección VIII',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (715,'Culhuacán CTM Sección IX-A',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2814,'Culhuacán CTM Sección IX-B',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (716,'Carmen Serdán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (717,'Cafetales',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (718,'Emiliano Zapata Fraccionamiento Popular',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (719,'Los Girasoles',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (723,'Las Campanas',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (724,'Santa Cecilia',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (725,'Campestre Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (726,'Culhuacán CTM Sección X',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (727,'Los Sauces',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (728,'El Mirador',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (729,'Villa Quietud',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (730,'Haciendas de Coyoacán',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2792,'Viejo Ejido de Santa Úrsula Coapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (731,'Ex-Hacienda Coapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (674,'Ex-Ejido de Santa Úrsula Coapa',3,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (732,'Cuajimalpa',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (735,'Zentlapatl',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (736,'Loma del Padre',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (737,'San Pedro',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2867,'La Manzanita',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (738,'Ahuatenco',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (740,'San Pablo Chimalpa',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (746,'Lomas de Vista Hermosa',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (748,'Cooperativa Palo Alto',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (751,'Granjas Palo Alto',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (752,'Campestre Palo Alto',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (753,'Bosques de las Lomas',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (758,'Lomas del Chamizal',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (766,'San José de los Cedros',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (768,'Granjas Navidad',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (769,'Tepetongo',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (770,'El Ébano',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (774,'El Molino',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (777,'Jesús del Monte',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (780,'Amado Nervo',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (782,'Manzanastitla',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (783,'Adolfo López Mateos',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (785,'El Molinito',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (786,'El Yaqui',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (787,'Lomas de Memetla',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (789,'Ampliación Memetla',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (788,'Memetla',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2869,'Ampliación el Yaqui',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (792,'Santa Fe Cuajimalpa',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (794,'Locaxco',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (797,'Las Tinajas',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (799,'Lomas de San Pedro',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (800,'El Tianguillo',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2866,'1° de Mayo',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (801,'San Lorenzo Acopilco',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (803,'Contadero',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (808,'La Venta',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (809,'Abdías García Soto',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (810,'San Mateo Tlaltenango',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2868,'Santa Rosa Xochiac',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (819,'Cruz Blanca',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (820,'Las Maromas',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (825,'Xalpa',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (827,'La Pila',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (829,'Las Lajas',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (833,'Agua Bendita',4,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (838,'(Área 1)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (847,'(Área 2)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (850,'(Área 3)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (853,'Tabacalera',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (860,'(Área 4)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (863,'(Área 5)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (867,'(Área 6)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (873,'(Área 7)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (875,'(Área 8)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (879,'(Área 9)',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (882,'Hipódromo',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (884,'Condesa',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (886,'Hipódromo Condesa',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (891,'Morelos',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (894,'Peralvillo',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (895,'Valle Gómez',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (896,'Ex-Hipódromo de Peralvillo',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (898,'Maza',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (899,'Felipe Pescador',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (900,'Guerrero',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (906,'Buenavista',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (911,'Santa María la Ribera',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (913,'Santa María Insurgentes',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (914,'Atlampa',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (915,'San Rafael',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (919,'Cuauhtémoc',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (930,'Juárez',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (947,'Roma Norte',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (955,'Doctores',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (966,'Roma Sur',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (970,'Buenos Aires',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (974,'Obrera',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (978,'Tránsito',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (980,'Esperanza',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (981,'Asturias',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (982,'Vista Alegre',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (983,'Paulino Navarro',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (984,'Algarin',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (986,'Ampliación Asturias',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (988,'Nonoalco Tlatelolco',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (989,'San Simón Tolnáhuac',15,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (991,'Aragón la Villa',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (994,'Rosas del Tepeyac',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (995,'Santa Isabel Tola',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (996,'Tepetates',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (997,'Tepeyac Insurgentes',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (998,'Santiago Atzacoalco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1001,'Villa Gustavo A. Madero',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1003,'15 de Agosto',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1005,'Estanzuela',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1006,'Triunfo de La República',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1007,'La Cruz',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1008,'Dinamita',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1010,'Martín Carrera',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1011,'Gabriel Hernández',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1012,'Ampliación Gabriel Hernández',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1014,'C.T.M. El Risco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1013,'C.T.M. Atzacoalco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1016,'Cuautepec Barrio Alto',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1017,'San Miguel',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1019,'San Antonio',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1021,'Lomas de Cuautepec',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1022,'Malacates',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1227,'Ampliación Malacates',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1023,'Compositores Mexicanos',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1024,'El Tepetatal',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1028,'Forestal',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1029,'Forestal I',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1026,'Arboledas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1027,'Ampliación Arboledas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2887,'La Lengüeta',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2885,'Forestal II',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1031,'Parque Metropolitano',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1033,'La Casilda',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1032,'Juventino Rosas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1034,'Loma La Palma',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2549,'Tlacaélel',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2548,'Luis Donaldo Colosio',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2718,'Prados de Cuautepec',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2717,'Graciano Sánchez',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1035,'Palmatitla',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1036,'Cocoyotes',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1037,'General Felipe Berriozabal',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2687,'Ampliación Cocoyotes',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2688,'6 de Junio',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1038,'Vista Hermosa',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1039,'Tlalpexco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1040,'Ahuehuetes',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1041,'Valle de Madero',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1042,'Del Carmen',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1043,'Cuautepec de Madero',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1046,'Del Bosque',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1048,'Guadalupe Victoria Cuautepec',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1049,'Chalma de Guadalupe',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2884,'Ampliación Chalma de Guadalupe',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1051,'Castillo Chico',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1052,'Castillo Grande',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2883,'Ampliación Castillo Grande',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1053,'Zona Escolar',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1054,'Zona Escolar Oriente',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1056,'El Arbolillo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1061,'Benito Juárez',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1063,'Ampliación Benito Juárez',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1065,'Solidaridad Nacional',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1067,'Residencial Acueducto de Guadalupe',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1069,'Acueducto de Guadalupe',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1070,'Jorge Negrete',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1071,'La Pastora',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2888,'Lindavista Sur',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1072,'Lindavista Norte',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1075,'Candelaria Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1080,'Residencial la Escalera',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1079,'La Purísima Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1085,'Santa María Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1087,'La Laguna Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1088,'San José Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1091,'Guadalupe Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1092,'San Juan y Guadalupe Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1093,'San Rafael Ticomán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1095,'San Pedro Zacatenco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2744,'Lomas de San Juan Ixhuatepec 2a Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1097,'Residencial Zacatenco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1098,'Capultitlan',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1100,'Tlacamaca',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1099,'Maximino Ávila Camacho',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1101,'Salvador Díaz Mirón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1102,'Juan González Romero',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1103,'Villa Hermosa',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1105,'El Coyol',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1106,'Nueva Atzacoalco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1108,'Del Obrero',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1109,'Vasco de Quiroga',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1110,'DM Nacional',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1111,'Ferrocarrilera',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1112,'LI Legislatura',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1117,'Granjas Modernas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1118,'Constitución de La República',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1119,'Ampliación San Juan de Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1120,'San Pedro El Chico',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1122,'La Pradera',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1125,'Pradera II Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1126,'San Felipe de Jesús',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1128,'25 de Julio',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1129,'Campestre Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1131,'La Esmeralda',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1135,'Providencia',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1136,'Ampliación Providencia',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1137,'Villa de Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1138,'Ampliación Casas Alemán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1139,'Progreso Nacional',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1141,'Santa Rosa',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1142,'San José de la Escalera',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1143,'Santiago Atepetlac',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1144,'Ampliación Progreso Nacional',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1146,'Guadalupe Proletaria',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1147,'Ampliación Guadalupe Proletaria',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1148,'Nueva Industrial Vallejo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1149,'Siete Maravillas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1150,'Torres Lindavista',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1153,'Lindavista Vallejo I Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1157,'San Bartolo Atepehuacan',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1155,'Churubusco Tepeyac',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1156,'Montevideo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1159,'Planetario Lindavista',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1160,'Valle del Tepeyac',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1161,'Nueva Vallejo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2890,'Lindavista Vallejo III Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2889,'Lindavista Vallejo II Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1162,'Magdalena de las Salinas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1163,'Panamericana',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1164,'Ampliación Panamericana',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1166,'Defensores de La República',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1167,'Héroe de Nacozari',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1169,'Vallejo Poniente',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1168,'Guadalupe Victoria',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1170,'Industrial',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1172,'Estrella',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1174,'Aragón Inguarán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1175,'Tres Estrellas',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1176,'Gertrudis Sánchez 1a Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2886,'Gertrudis Sánchez 3a Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1178,'Gertrudis Sánchez 2a Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1180,'7 de Noviembre',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1179,'Guadalupe Tepeyac',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1182,'Faja de Oro',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1181,'Bondojito',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1183,'Ampliación Emiliano Zapata',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1184,'Ampliación Mártires de Río Blanco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1185,'La Joyita',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1187,'Tablas de San Agustín',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1188,'Belisario Domínguez',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1190,'Vallejo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1189,'Guadalupe Insurgentes',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1191,'Mártires de Río Blanco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1192,'Emiliano Zapata',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1194,'La Joya',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1195,'Nueva Tenochtitlán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1193,'Cuchilla La Joya',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1196,'La Malinche',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1197,'Cuchilla del Tesoro',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1199,'San Juan de Aragón VII Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1200,'San Juan de Aragón VI Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1201,'Ex Ejido San Juan de Aragón Sector 32',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1203,'San Juan de Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1202,'El Olivo',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1204,'Indeco',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1205,'Héroes de Chapultepec',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1206,'Ex Ejido San Juan de Aragón Sector 33',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1207,'San Juan de Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1211,'Héroes de Cerro Prieto',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1212,'Ex Escuela de Tiro',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1213,'Fernando Casas Alemán',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1216,'San Juan de Aragón II Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1215,'San Juan de Aragón I Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1217,'San Juan de Aragón III Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1218,'San Juan de Aragón IV Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1219,'San Juan de Aragón V Sección',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1220,'Narciso Bassols',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1222,'C.T.M. Aragón',5,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1233,'Gabriel Ramos Millán Sección Bramadero',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1237,'Ex-Ejido de La Magdalena Mixiuhca',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1238,'Ampliación Gabriel Ramos Millán',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1241,'Gabriel Ramos Millán Sección Cuchilla',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1242,'Carlos Zapata Vela',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1244,'Agrícola Pantitlán',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1250,'Viaducto Piedad',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1251,'Nueva Santa Anita',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1252,'San Pedro',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1253,'San Francisco Xicaltongo',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1254,'Santiago Norte',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1255,'Santa Anita',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1256,'La Cruz',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1257,'Fraccionamiento Coyuya',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1258,'Granjas México',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1262,'Cuchilla Agrícola Oriental',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1263,'Agrícola Oriental',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1265,'El Rodeo',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1269,'La Asunción',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1270,'Zapotla',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1272,'Los Reyes',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1277,'San Miguel',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1278,'Juventino Rosas',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1279,'Tlazintla',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1280,'Gabriel Ramos Millán Sección Tlacotal',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1281,'Gabriel Ramos Millán',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2795,'Los Picos de Iztacalco Sección 2A',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2796,'Los Picos de Iztacalco Sección 1B',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1284,'INPI Picos',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1285,'Los Picos de Iztacalco Sección 1A',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1286,'Santiago Sur',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1288,'Reforma Iztaccíhuatl Norte',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1291,'Militar Marte',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1292,'Reforma Iztaccíhuatl Sur',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1293,'INFONAVIT Iztacalco',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1294,'Santa Cruz',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1295,'Jardines Tecma',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1296,'Campamento 2 de Octubre',6,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1304,'San Pedro',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1300,'San Ignacio',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1299,'La Asunción',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1301,'San José',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1302,'San Lucas',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1305,'Santa Bárbara',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1303,'San Pablo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1308,'Real del Moral',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1310,'Dr. Alfonso Ortiz Tirado',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1314,'Paseos de Churubusco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1316,'Central de Abasto',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1321,'Sector Popular',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1320,'Escuadrón 201',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1322,'Granjas de San Antonio',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1325,'Cacama',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1328,'Unidad Modelo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1329,'Héroes de Churubusco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1330,'Mexicaltzingo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1331,'Juan Escutia',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1334,'San Lorenzo Xicotencatl',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1335,'Santa Martha Acatitla Norte',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1339,'Ermita Zaragoza',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1341,'Unidad Vicente Guerrero',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1345,'Chinampac de Juárez',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1352,'Renovación',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1355,'Tepalcates',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1357,'Unidad Ejército Constitucionalista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1368,'El Paraíso',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1365,'Ejército de Oriente',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1369,'José María Morelos y Pavón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1364,'Álvaro Obregón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1370,'Ejército de Oriente Zona Peñón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1371,'Progresista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1373,'La Regadera',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1375,'Constitución de 1917',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1376,'Colonial Iztapalapa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1377,'Jacarandas',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1378,'Santa Cruz Meyehualco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1380,'Guadalupe del Moral',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1382,'Leyes de Reforma 1a Sección',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1383,'Leyes de Reforma 2a Sección',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2839,'Leyes de Reforma 3a Sección',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1384,'Cuchilla del Moral',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1388,'Sideral',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1392,'Albarrada',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1393,'Eva Sámano de López Mateos',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1398,'San Miguel',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1399,'Ampliación San Miguel',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1407,'San Juanico Nextipac',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1408,'El Sifón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1414,'San José Aculco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1410,'Aculco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1412,'Jardines de Churubusco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1413,'Magdalena Atlazolpa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1415,'Los Picos VI-B',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1416,'Nueva Rosita',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1417,'Purísima Atlazolpa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1419,'El Triunfo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1418,'Apatlaco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1422,'Ampliación El Triunfo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1426,'San Andrés Tetepilco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2840,'Zacahuitzco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1425,'El Retoño',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1429,'Banjidal',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1430,'Justo Sierra',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1431,'Sinatel',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1432,'Ampliación Sinatel',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1433,'El Prado',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1435,'Santa María Aztahuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2565,'Santa María Aztahuacán Ampliación',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1436,'Santa Martha Acatitla',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1439,'El Edén',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1440,'San Sebastián Tecoloxtitla',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1441,'Santa Martha Acatitla Sur',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1443,'Monte Alban',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1444,'Paraje Zacatepec',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1445,'Santa María Aztahuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1449,'Ejército de Agua Prieta',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1453,'Santiago Acahualtepec',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1454,'Santiago Acahualtepec 1ra. Ampliación',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1455,'Santiago Acahualtepec 2a. Ampliación',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1456,'Lomas de Zaragoza',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2853,'San Miguel Teotongo Sección Guadalupe',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2854,'San Miguel Teotongo Sección Iztlahuaca',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2859,'San Miguel Teotongo Sección Palmitas',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2850,'San Miguel Teotongo Sección Acorralado',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2855,'San Miguel Teotongo Sección Jardines',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2860,'San Miguel Teotongo Sección Puente',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2852,'San Miguel Teotongo Sección Capilla',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2858,'San Miguel Teotongo Sección Mercedes',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2862,'San Miguel Teotongo Sección Rancho Bajo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2861,'San Miguel Teotongo Sección Ranchito',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1481,'San Miguel Teotongo Sección Corrales',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2851,'San Miguel Teotongo Sección Avisadero',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2856,'San Miguel Teotongo Sección La Cruz',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2857,'San Miguel Teotongo Sección Loma Alta',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2863,'San Miguel Teotongo Sección Torres',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1461,'Campestre Potrero',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1462,'Ampliación Emiliano Zapata',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1464,'Lomas de la Estancia',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1465,'Xalpa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1467,'San Pablo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1470,'Citlalli',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1471,'Palmitas',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1473,'Tenorios',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1477,'Barranca de Guadalupe',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1478,'Iztlahuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1480,'Miravalles',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1482,'Miguel de La Madrid Hurtado',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1485,'Buenavista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1491,'Santa Cruz Meyehualco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1488,'Desarrollo Urbano Quetzalcoatl',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1486,'Carlos Hank Gonzalez',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1492,'Degollado',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2726,'Degollado - Mexicatlalli',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1497,'San José Buenavista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1500,'Mixcoatl',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1501,'Lomas de Santa Cruz',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1502,'Los Ángeles Apanoaya',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1505,'Francisco Villa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1506,'La Era',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1508,'Reforma Política',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1511,'Presidentes de México',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1514,'La Polvorilla',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1515,'Las Peñas',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1512,'Insurgentes',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1518,'Consejo Agrarista Mexicano',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1523,'El Triángulo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1524,'Puente Blanco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1527,'Año de Juárez',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1528,'Lomas de San Lorenzo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1531,'San Lorenzo Tezonco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1535,'Estrella Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1536,'Fuego Nuevo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1538,'San Antonio Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1540,'San Simón Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1539,'San Antonio Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1541,'Tula',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1534,'El Mirador',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1532,'Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1542,'Valle de Luces',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1549,'Minerva',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1547,'Granjas Esmeralda',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1548,'Los Cipreses',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1550,'Progreso del Sur',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1551,'Valle del Sur',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1553,'Estrella del Sur',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1557,'Santa Isabel Industrial',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1552,'El Santuario',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1556,'Ricardo Flores Magón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1559,'Ampliación Ricardo Flores Magón',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1560,'Ampliación El Santuario',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1565,'Lomas El Manto',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1566,'Los Ángeles',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1562,'El Manto',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1563,'El Molino',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1568,'Paraje San Juan',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1570,'San Miguel 8va. Ampliación',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1571,'Plan de Iguala',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1576,'San Juan Joya',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1573,'Ampliación Paraje San Juan',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1578,'Los Reyes Culhuacán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1580,'Ampliación Los Reyes',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1586,'San Juan Xalpa',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1588,'San Nicolás Tolentino',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1589,'Santa María del Monte',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1591,'Ampliación Veracruzana',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1590,'Estado de Veracruz',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1594,'Paraje San Juan Cerro',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1595,'Benito Juárez',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1596,'Bellavista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1600,'Cerro de La Estrella',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1597,'Ampliación Bellavista',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1601,'Parque Nacional Cerro  de la Estrella',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1598,'Casa Blanca',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1602,'El Rodeo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1607,'San Juan Estrella',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1613,'San Andrés Tomatlán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1609,'12 de Diciembre',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1611,'San Andrés Tomatlán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1616,'Santa María Tomatlán',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1621,'Granjas Estrella',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1620,'El Vergel',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1629,'Lomas Estrella',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1633,'Carlos Jonguitud Barrios',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1639,'San Lorenzo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1638,'San Antonio',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1637,'Guadalupe',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1640,'La Esperanza',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1643,'José López Portillo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1644,'El Rosario',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1645,'Jardines de San Lorenzo Tezonco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1647,'USCOVI',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1646,'Celoalliotli',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1648,'El Molino Tezonco',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1961,'Allapetlalli',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2736,'La Planta',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1651,'Cananea',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1652,'Valle de San Lorenzo',7,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1672,'Lomas Quebradas',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1674,'San Bartolo Ameyalco',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1673,'La Malinche',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1675,'Cuauhtémoc',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1681,'El Maestro',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1682,'San Jerónimo Lídice',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1686,'San Bernabé Ocotepec',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1690,'El Tanque',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1691,'Las Cruces',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1692,'Los Padres',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1693,'Lomas de San Bernabé',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1694,'Huayatla',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1695,'Ampliación Potrerillo',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1696,'Ampliación Lomas de San Bernabé',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2669,'Tierra Unida',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1697,'Palmas',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1698,'Atacaxco',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1700,'Vista Hermosa',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1701,'Barros Sierra',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1702,'San Jerónimo Aculco',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1703,'Barrio San Francisco',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1707,'Barranca Seca',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1710,'El Rosal',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1713,'El Toro',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1714,'Potrerillo',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2706,'El Ocotal',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1716,'La Carbonera',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1717,'Pueblo Nuevo Alto',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1718,'Pueblo Nuevo Bajo',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1720,'El Ermitaño',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1721,'Héroes de Padierna',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1723,'Santa Teresa',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1728,'La Cruz',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1730,'San Francisco',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1731,'La Guadalupe',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1732,'La Concepción',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1734,'Plazuela del Pedregal',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1733,'Las Calles',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1735,'San Nicolás Totolapan',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1737,'La Magdalena',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1738,'Las Huertas',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1739,'Tierra Colorada',8,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2775,'Lomas de Chapultepec II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2776,'Lomas de Chapultepec III Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1745,'Lomas de Chapultepec I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2774,'Lomas de Chapultepec VIII Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2777,'Lomas de Chapultepec IV Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2778,'Lomas de Chapultepec V Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2779,'Lomas de Chapultepec VI Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2780,'Lomas de Chapultepec VII Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1752,'Molino del Rey',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2772,'Bosque de Chapultepec III Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1754,'Bosque de Chapultepec II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1757,'Lomas Hermosa',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1758,'Lomas de Sotelo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1761,'San Lorenzo Tlaltenango',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1764,'Periodista',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1765,'Argentina Poniente',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1767,'Ignacio Manuel Altamirano',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1768,'10 de Abril',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1770,'México Nuevo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1771,'San Joaquín',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1772,'Argentina Antigua',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1773,'Torre Blanca',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1774,'Ampliación Torre Blanca',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1775,'Huíchapan',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1776,'San Diego Ocoyoacac',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1777,'Verónica Anzures',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1780,'Mariano Escobedo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1782,'Anáhuac I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2771,'Anáhuac II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1784,'Un Hogar Para Nosotros',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1785,'Santo Tomas',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1786,'Plutarco Elías Calles',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1787,'Agricultura',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1789,'Tlaxpana',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1791,'Popotla',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1794,'Tacuba',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1793,'Legaria',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1796,'Nextitla',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1798,'Ventura Pérez de Alva',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1797,'Pensil Norte',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1800,'San Juanico',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1799,'Reforma Pensil',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1802,'Modelo Pensil',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1803,'Peralitos',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1801,'Ahuehuetes Anáhuac',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1804,'Dos Lagos',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1807,'Los Manzanos',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1806,'Lago Sur',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1805,'Lago Norte',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1809,'Deportivo Pensil',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1808,'5 de Mayo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1811,'Francisco I Madero',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1812,'Popo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1813,'Ampliación Popo',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1814,'Cuauhtémoc Pensil',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1815,'Pensil Sur',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1816,'Irrigación',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2781,'Polanco I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1822,'Granada',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1823,'Ampliación Granada',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2782,'Polanco II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2783,'Polanco III Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2784,'Polanco IV Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2785,'Polanco V Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1833,'Bosque de Chapultepec I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1838,'Anzures',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1843,'Residencial Militar',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1844,'Manuel Avila Camacho',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1846,'Campo Militar 1',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1855,'Reforma Social',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1856,'Bosque de las Lomas',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1858,'Escandón I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2773,'Escandón II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1860,'16 de Septiembre',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1861,'América',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1862,'Daniel Garza',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1863,'Ampliación Daniel Garza',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1864,'San Miguel Chapultepec I Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2786,'San Miguel Chapultepec II Sección',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1865,'Observatorio',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1867,'Tacubaya',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1869,'Lomas de Bezares',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1870,'Real de las Lomas',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2789,'Lomas de Reforma',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1874,'Lomas Altas',16,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1883,'Santa Martha',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1877,'La Concepción',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1879,'Los Ángeles',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1884,'Villa Milpa Alta Centro',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1878,'La Luz',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1881,'San Mateo',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1882,'Santa Cruz',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1889,'San Agustin',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1890,'San Agustin Ohtenco',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1895,'Xaltipac',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1891,'Cruztitla',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1894,'Tenantitla',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1896,'Xochitepec',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1893,'Tecaxtitla',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2010,'Emiliano Zapata',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2011,'La Conchita',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1897,'Nochtla',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1899,'Panchimalco',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1901,'Tula',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1898,'Ocotitla',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1903,'San Bartolomé Xicomulco',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1904,'San Salvador Cuauhtenco',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1905,'Centro',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1906,'San Juan',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1907,'San Miguel',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2870,'Chalmita',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1910,'San Lorenzo Tlacoyucan',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1911,'San Jerónimo Miacatlán',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1912,'San Francisco Tecoxpa',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1913,'San Juan Tepenahuac',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2871,'La Lupita Teticpac',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1880,'San Marcos',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2873,'San Miguel',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2874,'San José',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2872,'La Lupita Xolco',9,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1919,'La Asunción',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1923,'Santa Cecilia',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1924,'San José',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1925,'San Juan',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1926,'San Mateo',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1928,'La Habana',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1930,'Santa Ana',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1929,'La Guadalupe',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1931,'La Magdalena',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1932,'San Miguel',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1933,'Los Reyes',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1934,'Quiahuatla',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2497,'San Sebastián',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2898,'San Isidro',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1935,'San Andrés',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1937,'La Guadalupe',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1939,'Cañada',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1940,'La Poblanita',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1943,'Santiago',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1942,'Ampliación Santa Catarina',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2576,'La Mesa',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2577,'Chichilaula',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2575,'Teozoma',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1944,'San Francisco Apolocalco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1945,'La Concepción',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1946,'San Miguel',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1947,'Miguel Hidalgo',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1949,'Los Olivos',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1950,'Las Arboledas',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1951,'Ampliación Los Olivos',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1952,'La Nopalera',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1955,'Granjas Cabrera',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1957,'La Turba',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1958,'Del Mar',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2580,'La Draga',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1959,'Villa Centroamericana',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1962,'Agrícola Metropolitana',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1963,'Santa Ana Centro',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2894,'Santiago Norte',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1964,'Santiago Centro',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2891,'Santa Ana Poniente',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2893,'Santa Ana Norte',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1966,'Zapotitla',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2897,'Ampliación Zapotitla',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1970,'La Estación',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1971,'La Conchita Zapotitlán',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2895,'Santa Ana Sur',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2896,'Santiago Sur',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2586,'Tempiluli',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1972,'San Francisco Tlaltenco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1973,'López Portillo',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1975,'Ampliación José López Portillo',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1976,'Selene',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1978,'Ampliación Selene',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1980,'Zacatenco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1982,'Ojo de Agua',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1981,'Guadalupe Tlaltenco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1983,'El Triángulo',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1985,'San Agustín',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2593,'La Soledad',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1986,'La Concepción',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1987,'La Lupita',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1988,'Francisco Villa',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1989,'La Loma',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1991,'La Asunción',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1990,'Jaime Torres Bodet',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1994,'Tierra Blanca',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1992,'El Rosario',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2587,'Ampliación La Conchita',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2916,'Olivar Santa María',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1995,'Peña Alta',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1997,'Jardines del Llano',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (1998,'Potrero del Llano',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2002,'San Bartolomé',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2004,'Los Reyes',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2005,'Santa Cruz',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2007,'San Agustín',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2008,'San Miguel',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2892,'Tepantitlamilco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2009,'San Nicolás Tetelco',11,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2027,'Tlalpan Centro',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2876,'Tlalpan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2029,'Parque del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2035,'Villa Olímpica',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2036,'Isidro Fabela',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2041,'Ampliación Isidro Fabela',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2042,'Cantera Puente de Piedra',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2043,'Pueblo Quieto',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2044,'Comuneros de Santa Úrsula',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2045,'Toriello Guerra',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2046,'Peña Pobre',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2048,'Rómulo Sánchez Mireles',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2049,'San Fernando',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2050,'San Pedro Apóstol',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2052,'Del Niño Jesús',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2051,'Belisario Domínguez Sección XVI',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2054,'La Joya',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2062,'Pedregal de San Nicolás 3A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2063,'Pedregal de San Nicolás 4A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2061,'Pedregal de San Nicolás 2A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2064,'Pedregal de San Nicolás 5A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2060,'Pedregal de San Nicolás 1A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2067,'Chichicaspatl',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2881,'Ampliación Fuentes del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2071,'Rincón del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2074,'Fuentes del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2078,'Lomas del Pedregal Framboyanes',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2079,'Popular Santa Teresa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2081,'Jardines del Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2080,'Héroes de Padierna',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2083,'Colinas del Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2084,'Torres de Padierna',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2085,'Jardines en la Montaña',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2087,'Six Flags (Reino Aventura)',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2187,'Parque Nacional Bosque del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2088,'Cuchilla de Padierna',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2090,'Lomas del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2091,'Cultura Maya',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2093,'Los Encinos',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2094,'Lomas de Padierna',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2095,'Lomas Hidalgo',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2096,'Cruz del Farol',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2098,'Miguel Hidalgo 2A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2099,'Miguel Hidalgo 3A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2100,'Miguel Hidalgo 4A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2101,'Miguel Hidalgo',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2102,'El Capulín',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2103,'Miguel Hidalgo 1A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2106,'Zacayucan Peña Pobre',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2107,'De Caramagüey',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2108,'La Lonja',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2109,'La Fama',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2110,'Primavera',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2694,'Solidaridad',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2111,'Paraje 38',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2112,'Verano',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2113,'Nueva Oriental Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2114,'Residencial Acoxpa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2115,'Residencial Miramontes',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2879,'Ex Hacienda Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2120,'Belisario Domínguez',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2121,'Floresta Coyoacán',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2123,'Vergel Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2125,'Rinconada Coapa 2A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2126,'Tenorios',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2132,'Granjas Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2134,'Rinconada Coapa 1A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2146,'Vergel del Sur',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2145,'Vergel de Coyoacán',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2147,'Prado Coapa 1A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2149,'Prado Coapa 2A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2150,'Prado Coapa 3A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2877,'Magisterial Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2153,'Magisterial',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2155,'Residencial Chimali',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2158,'Villa Lázaro Cárdenas',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2157,'San Lorenzo Huipulco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2159,'Arboledas del Sur',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2161,'Hacienda San Juan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2167,'San Bartolo El Chico',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2163,'A.M.S.A',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2168,'Rancho los Colorines',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2169,'Ex Hacienda San Juan de Dios',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2170,'Guadalupe',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2172,'Arenal de Guadalupe',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2176,'Residencial Villa Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2878,'Villa Coapa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2175,'Narciso Mendoza',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2174,'Rinconada Las Hadas',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2179,'San Andrés Totoltepec',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2182,'Divisadero',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2183,'Nuevo Renacimiento de Axalco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2184,'Tecorral',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2186,'Fuentes Brotantes',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2190,'Mesa de los Hornos',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2188,'Cumbres de Tepetongo',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2192,'Santa Úrsula Xitla',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2195,'Tlaxcaltenco la Mesa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2194,'Texcaltenco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2196,'San Juan Tepeximilpa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2197,'Tepeximilpa la Paz',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2200,'Santísima Trinidad',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2201,'El Truenito',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2203,'Tlalcoligia',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2205,'Pedregal de Santa Úrsula Xitla',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2206,'Pedregal de las Águilas',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2207,'Los Volcanes',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2208,'El Mirador 1A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2210,'El Mirador 3A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2209,'El Mirador 2A Sección',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2212,'Atocpa',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2213,'Tlalpuente',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2214,'Plan de Ayala',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2216,'La Palma',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2219,'Viveros Coatectlán',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2220,'La Magdalena Petlacalco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2221,'San Miguel Xicalco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2222,'San Miguel Topilejo',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2223,'La Quinta',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2224,'Estación Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2227,'Estrella Mora',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2229,'Valle Escondido',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2231,'Colinas del Bosque',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2232,'Las Tórtolas',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2233,'Arenal Tepepan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2741,'Club de Golf México',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2236,'San Buenaventura',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2237,'Chimalcoyoc',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2238,'Villa Tlalpan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2240,'Ejidos de San Pedro Mártir',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2241,'Fuentes de Tepepan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2245,'Valle de Tepepan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2246,'Juventud Unida',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2247,'Movimiento Organizado de Tlalpan',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2880,'Rinconada El Mirador',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2252,'San Pedro Mártir',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2882,'Heróico Colegio Militar',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2453,'Dolores Tlali',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2255,'La Magueyera',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2256,'Valle Verde o Lomas Verdes',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2258,'Tlalmille',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2259,'Mirador del Valle',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2260,'María Esther Zuno de Echeverría',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2261,'San Miguel Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2262,'Santo Tomas Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2263,'Belvedere Ajusco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2265,'Lomas de Cuilotepec',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2266,'Zacatón',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2695,'Lomas de Tepemecatl',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2267,'San Nicolás 2',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2268,'Vistas del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2269,'Bosques del Pedregal',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2270,'2 de Octubre',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2272,'Lomas de Padierna Sur',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2273,'Mirador I',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2274,'Mirador II',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2275,'Chimilli',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2277,'Rancho Viejo',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2278,'Héroes de 1910',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2279,'El Charco',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2280,'San Jorge',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2286,'Parres El Guarda',12,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2287,'General Ignacio Zaragoza',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2289,'Valentín Gómez Farias',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2290,'Puebla',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2291,'Zona Centro',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2293,'Janitzio',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2294,'Valle Gómez',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2296,'Popular Rastro',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2295,'Nicolás Bravo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2297,'Emilio Carranza',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2298,'Michoacana',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2299,'Ampliación Michoacana',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2300,'Ampliación 20 de Noviembre',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2301,'Morelos',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2302,'Penitenciaria',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2303,'10 de Mayo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2305,'20 de Noviembre',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2307,'5o Tramo 20 de Noviembre',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2308,'Felipe Ángeles',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2309,'Azteca',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2310,'Tres Mosqueteros',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2311,'Ampliación Venustiano Carranza',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2312,'Venustiano Carranza',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2313,'Ampliación Penitenciaria',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2315,'Progresista',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2316,'Escuela de Tiro',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2319,'7 de Julio',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2320,'Romero Rubio',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2321,'Simón Bolívar',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2322,'Ampliación Simón Bolívar',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2323,'Aquiles Serdán',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2324,'1° de Mayo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2325,'Damián Carmona',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2326,'Revolución',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2327,'Miguel Hidalgo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2328,'Moctezuma 1a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2330,'Pensador Mexicano',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2331,'Peñón de los Baños',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2332,'Moctezuma 2a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2333,'Santa Cruz Aviación',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2334,'Arenal 1a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2335,'Cuchilla Pantitlán',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2336,'México (Lic. Benito Juárez)',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2340,'Caracol',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2342,'Arenal 4a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2351,'Arenal Puerto Aéreo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2343,'Ampliación Caracol',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2344,'Arenal 3a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2346,'Adolfo López Mateos',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2347,'Arenal 2a Sección',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2349,'Federal',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2350,'Industrial Puerto Aéreo',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2353,'4 Árboles',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2354,'Aviación Civil',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2355,'Ampliación Aviación Civil',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2356,'Jamaica',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2358,'Merced Balbuena',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2359,'Lorenzo Boturini',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2360,'Artes Gráficas',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2361,'Sevilla',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2362,'Magdalena Mixiuhca',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2363,'La Magdalena Mixiuhca',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2364,'Aarón Sáenz',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2365,'Jardín Balbuena',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2372,'Del Parque',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2376,'Aeronáutica Militar',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2377,'24 de Abril',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2378,'Álvaro Obregón',17,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2381,'San Juan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2379,'La Concepción Tlacoapa',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2380,'San Antonio',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2832,'San Bartolo El Chico',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2388,'Paseos del Sur',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2386,'Las Peritas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2385,'Bosque Residencial del Sur',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2396,'Santa María Tepepan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2395,'San Juan Tepepan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2399,'Ampliación Tepepan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2402,'La Noria',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2919,'Ampliación La Noria',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2401,'Huichapan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2403,'Potrero de San Bernardino',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2405,'18',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2407,'San Lorenzo La Cebada',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2833,'Rinconada Coapa',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2408,'Mercado de Flores Plantas y Hortalizas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2409,'Ampliación San Marcos Norte',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2412,'San Lorenzo',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2411,'La Asunción',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2413,'Jardines del Sur',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2414,'San Marcos',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2415,'Tierra Nueva',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2420,'Pblo. Stgo.Tepalcatlalpan, U. H. Rinconada del Sur',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2421,'El Mirador',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2425,'La Guadalupita',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2426,'Santa Crucita',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2424,'El Rosario',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2423,'Belén',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2430,'San Esteban',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2429,'San Diego',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2428,'San Cristóbal',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2427,'La Santísima',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2434,'Xaltocan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2432,'San Pedro',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2433,'Tablas de San Lorenzo',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2439,'Santa Cruz Xochitepec',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2443,'Santiago Tepalcatlalpan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2444,'La Concha',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2458,'San Lucas Xochimanca',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2459,'La Cañada',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2460,'San Lucas Oriente',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2462,'Texmic',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2464,'San Lorenzo Atemoaya',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2465,'Lomas de Tonalco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2466,'San Jerónimo',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2467,'El Jazmín',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2468,'Rancho Tejomulco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2470,'Xochipilli',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2471,'Año de Juárez',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2899,'Pocitos',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2472,'Santa María Nativitas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2480,'Lomas de Nativitas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2473,'Ampliación Nativitas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2475,'Santa Cruz Acalpixca',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2900,'Apatlaco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2901,'Del Puente',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2902,'La Gallera',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2904,'Calpulco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2903,'Tetitla',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2478,'La Planta',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2834,'Las Flores',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2479,'Las Cruces',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2905,'Ahualapa',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2481,'Valle de Santa María',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2482,'San Gregorio Atlapulco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2906,'San Andrés',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2907,'Los Reyes',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2908,'3 de Mayo',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2909,'San Antonio',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2483,'La Candelaria',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2484,'San Luis Tlaxialtemalco',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2910,'Niños Héroes',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2911,'La Asunción',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2913,'Santa Cecilia',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2912,'San Sebastián',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2485,'San José',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2489,'San Juan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2487,'La Guadalupana',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2490,'San Juan Moyotepec',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2491,'San Juan Minas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2494,'Quirino Mendoza',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2495,'Del Carmen',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2498,'San Isidro',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2499,'Guadalupita',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2500,'Las Animas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2501,'Calyequita',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2502,'San Felipe',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2503,'Santiaguito',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2600,'El Mirador',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2604,'Cristo Rey',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2835,'Cerrillos Segunda Sección',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2504,'Cerrillos Primera Sección',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2602,'El Sacrificio',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2836,'Cerrillos Tercera Sección',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2508,'Nativitas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2509,'Las Mesitas',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2510,'San Mateo Xalpa',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2607,'Rosario Tlali',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2515,'San Andrés Ahuayucan',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2516,'Santa Inés',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2914,'El Calvario',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2519,'Santa Cruz Chavarrieta',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2520,'Chapultepec',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2521,'Santa Cruz de Guadalupe',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2527,'Santa Cecilia Tepetlapa',13,9);
INSERT INTO motor_interprete.cat_asentamientos (id_asentamiento,asentamiento,id_municipio,id_estado) VALUES (2530,'San Francisco Tlalnepantla',13,9);



INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1,'01000',1,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (2,'01010',5,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (3,'01020',6,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (4,'01030',10,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (5,'01030',9,28,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (6,'01040',12,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (7,'01049',14,28,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (8,'01050',16,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (9,'01060',17,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (10,'01060',18,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (11,'01070',19,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (12,'01080',22,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (13,'01089',24,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (14,'01090',25,2,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (15,'01090',28,28,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (16,'01090',26,2,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (17,'01100',31,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (18,'01109',33,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (19,'01110',37,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (20,'01110',39,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (21,'01110',34,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (22,'01110',36,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (23,'01120',42,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (24,'01120',46,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (25,'01120',47,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (26,'01120',44,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (27,'01125',43,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (28,'01130',56,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (29,'01130',53,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (30,'01130',52,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (31,'01139',58,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (32,'01140',60,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (33,'01140',59,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (34,'01150',64,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (35,'01150',63,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (36,'01160',66,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (37,'01160',68,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (38,'01160',69,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (39,'01160',67,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (40,'01170',74,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (41,'01180',77,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (42,'01180',76,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (43,'01180',78,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (44,'01200',79,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (45,'01210',82,28,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (46,'01219',84,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (47,'01220',89,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (48,'01220',86,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (49,'01220',2681,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (50,'01220',90,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (51,'01220',85,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (52,'01220',87,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (53,'01230',92,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (54,'01230',132,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (55,'01230',97,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (56,'01230',98,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (57,'01230',224,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (58,'01239',102,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (59,'01240',103,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (60,'01250',108,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (61,'01250',104,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (62,'01250',106,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (63,'01250',105,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (64,'01250',107,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (65,'01259',109,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (66,'01259',2845,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (67,'01260',112,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (68,'01260',114,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (69,'01260',110,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (70,'01260',115,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (71,'01260',113,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (72,'01269',118,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (73,'01269',117,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (74,'01270',121,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (75,'01270',123,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (76,'01270',120,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (77,'01270',124,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (78,'01270',127,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (79,'01270',125,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (80,'01275',131,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (81,'01276',93,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (82,'01278',133,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (83,'01279',136,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (84,'01280',141,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (85,'01280',138,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (86,'01280',139,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (87,'01280',140,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (88,'01285',144,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (89,'01289',146,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (90,'01290',147,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (91,'01290',148,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (92,'01296',153,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (93,'01296',152,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (94,'01296',154,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (95,'01298',156,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (96,'01299',160,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (97,'01299',161,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (98,'01310',163,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (99,'01320',164,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (100,'01330',168,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (101,'01340',170,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (102,'01376',171,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (103,'01376',2683,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (104,'01376',2864,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (105,'01376',2682,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (106,'01377',172,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (107,'01389',176,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (108,'01400',181,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (109,'01400',184,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (110,'01407',187,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (111,'01408',188,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (112,'01410',194,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (113,'01410',193,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (114,'01419',195,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (115,'01420',196,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (116,'01420',197,2,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (117,'01430',198,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (118,'01450',202,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (119,'01460',204,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (120,'01470',207,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (121,'01470',206,2,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (122,'01480',209,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (123,'01490',211,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (124,'01500',213,28,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (125,'01500',2704,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (126,'01509',221,28,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (127,'01510',222,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (128,'01510',2636,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (129,'01510',223,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (130,'01520',225,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (131,'01520',227,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (132,'01520',94,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (133,'01520',226,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (134,'01520',2841,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (135,'01530',229,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (136,'01538',232,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (137,'01539',233,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (138,'01539',2643,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (139,'01540',238,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (140,'01540',239,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (141,'01540',240,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (142,'01540',241,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (143,'01548',2638,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (144,'01549',243,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (145,'01550',245,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (146,'01550',2849,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (147,'01550',246,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (148,'01560',249,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (149,'01560',250,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (150,'01560',248,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (151,'01560',251,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (152,'01566',2747,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (153,'01569',255,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (154,'01588',259,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (155,'01590',261,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (156,'01600',263,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (157,'01610',2639,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (158,'01610',265,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (159,'01618',267,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (160,'01618',2745,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (161,'01619',268,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (162,'01619',269,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (163,'01620',270,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (164,'01630',271,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (165,'01630',273,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (166,'01630',2640,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (167,'01640',275,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (168,'01645',276,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (169,'01650',280,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (170,'01650',281,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (171,'01650',2848,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (172,'01650',2844,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (173,'01650',277,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (174,'01650',278,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (175,'01650',279,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (176,'01700',284,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (177,'01700',282,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (178,'01700',283,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (179,'01700',286,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (180,'01700',285,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (181,'01700',287,28,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (182,'01708',292,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (183,'01708',291,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (184,'01710',294,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (185,'01710',295,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (186,'01720',297,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (187,'01729',298,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (188,'01730',299,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (189,'01730',300,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (190,'01740',303,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (191,'01740',304,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (192,'01740',2842,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (193,'01750',307,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (194,'01750',305,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (195,'01750',306,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (196,'01759',308,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (197,'01760',309,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (198,'01760',311,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (199,'01760',310,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (200,'01770',316,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (201,'01770',315,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (202,'01780',319,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (203,'01780',318,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (204,'01789',323,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (205,'01790',324,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (206,'01790',325,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (207,'01800',327,28,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (208,'01807',328,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (209,'01810',330,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (210,'01820',2843,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (211,'01820',332,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (212,'01830',333,28,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (213,'01840',334,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (214,'01849',337,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (215,'01849',2847,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (216,'01857',341,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (217,'01859',343,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (218,'01860',344,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (219,'01863',3,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (220,'01870',345,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (221,'01900',347,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (222,'01904',2846,9,10,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (223,'02000',353,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (224,'02010',356,2,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (225,'02010',357,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (226,'02010',358,2,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (227,'02020',361,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (228,'02020',360,2,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (229,'02040',362,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (230,'02040',363,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (231,'02050',364,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (232,'02050',365,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (233,'02060',369,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (234,'02060',368,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (235,'02070',372,2,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (236,'02070',371,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (237,'02080',374,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (238,'02080',376,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (239,'02090',377,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (240,'02099',378,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (241,'02100',379,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (242,'02120',385,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (243,'02128',389,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (244,'02129',390,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (245,'02130',391,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (246,'02140',392,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (247,'02150',393,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (248,'02160',394,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (249,'02200',396,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (250,'02230',397,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (251,'02240',398,2,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (252,'02240',399,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (253,'02250',400,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (254,'02300',402,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (255,'02310',404,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (256,'02320',405,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (257,'02330',406,2,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (258,'02340',407,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (259,'02360',409,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (260,'02400',410,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (261,'02410',412,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (262,'02420',415,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (263,'02440',418,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (264,'02459',421,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (265,'02460',422,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (266,'02470',423,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (267,'02480',425,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (268,'02490',427,2,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (269,'02500',428,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (270,'02519',430,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (271,'02520',431,17,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (272,'02530',439,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (273,'02600',441,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (274,'02630',443,2,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (275,'02630',2798,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (276,'02640',444,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (277,'02650',445,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (278,'02660',446,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (279,'02670',447,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (280,'02680',448,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (281,'02700',449,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (282,'02710',451,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (283,'02719',454,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (284,'02720',456,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (285,'02720',457,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (286,'02730',459,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (287,'02750',461,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (288,'02760',463,2,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (289,'02760',462,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (290,'02770',464,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (291,'02780',465,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (292,'02790',466,2,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (293,'02800',467,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (294,'02810',469,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (295,'02810',470,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (296,'02830',473,2,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (297,'02840',474,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (298,'02860',476,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (299,'02870',477,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (300,'02900',478,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (301,'02910',479,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (302,'02920',480,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (303,'02930',481,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (304,'02940',482,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (305,'02950',483,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (306,'02960',484,28,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (307,'02970',485,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (308,'02980',486,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (309,'02980',487,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (310,'02990',488,9,2,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (311,'03000',489,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (312,'03010',491,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (313,'03020',493,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (314,'03023',2623,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (315,'03100',496,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (316,'03100',2624,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (317,'03103',2621,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (318,'03104',2622,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (319,'03200',501,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (320,'03230',505,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (321,'03240',506,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (322,'03300',507,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (323,'03303',2625,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (324,'03310',509,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (325,'03320',512,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (326,'03330',513,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (327,'03340',515,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (328,'03400',516,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (329,'03410',518,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (330,'03420',519,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (331,'03430',521,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (332,'03440',522,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (333,'03500',523,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (334,'03510',525,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (335,'03520',526,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (336,'03530',527,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (337,'03540',528,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (338,'03550',529,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (339,'03560',530,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (340,'03570',531,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (341,'03580',532,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (342,'03590',533,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (343,'03600',534,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (344,'03610',535,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (345,'03620',536,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (346,'03630',537,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (347,'03640',538,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (348,'03650',539,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (349,'03660',540,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (350,'03700',541,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (351,'03710',542,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (352,'03720',543,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (353,'03730',544,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (354,'03740',545,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (355,'03800',546,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (356,'03810',548,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (357,'03820',552,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (358,'03840',554,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (359,'03900',556,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (360,'03910',558,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (361,'03920',559,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (362,'03930',561,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (363,'03940',562,9,14,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (364,'04000',563,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (365,'04010',566,2,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (366,'04020',567,2,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (367,'04030',569,2,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (368,'04040',570,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (369,'04100',571,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (370,'04120',577,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (371,'04120',578,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (372,'04200',580,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (373,'04210',582,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (374,'04230',584,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (375,'04240',586,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (376,'04250',587,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (377,'04260',2807,2,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (378,'04260',2808,2,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (379,'04260',2831,2,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (380,'04260',590,2,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (381,'04300',596,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (382,'04310',601,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (383,'04318',603,2,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (384,'04320',606,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (385,'04320',608,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (386,'04330',612,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (387,'04330',613,28,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (388,'04330',614,2,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (389,'04340',622,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (390,'04360',633,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (391,'04360',632,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (392,'04369',636,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (393,'04370',638,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (394,'04370',637,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (395,'04380',641,28,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (396,'04380',640,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (397,'04390',644,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (398,'04390',643,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (399,'04400',645,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (400,'04410',646,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (401,'04420',649,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (402,'04440',651,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (403,'04440',2811,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (404,'04440',2810,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (405,'04450',652,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (406,'04460',653,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (407,'04470',655,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (408,'04470',2805,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (409,'04480',2815,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (410,'04480',2809,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (411,'04480',657,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (412,'04480',2812,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (413,'04489',658,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (414,'04490',2813,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (415,'04490',659,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (416,'04500',660,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (417,'04510',2793,17,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (418,'04519',664,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (419,'04530',665,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (420,'04600',669,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (421,'04610',670,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (422,'04620',671,28,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (423,'04630',672,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (424,'04640',673,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (425,'04650',676,28,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (426,'04660',677,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (427,'04700',678,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (428,'04710',680,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (429,'04730',686,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (430,'04730',687,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (431,'04739',690,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (432,'04800',692,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (433,'04800',691,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (434,'04810',695,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (435,'04815',696,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (436,'04830',697,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (437,'04840',2806,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (438,'04870',706,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (439,'04890',708,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (440,'04890',709,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (441,'04899',711,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (442,'04909',714,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (443,'04909',715,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (444,'04909',2814,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (445,'04910',716,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (446,'04918',717,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (447,'04919',718,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (448,'04920',719,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (449,'04929',723,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (450,'04930',724,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (451,'04938',725,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (452,'04939',726,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (453,'04940',727,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (454,'04950',728,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (455,'04960',729,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (456,'04970',730,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (457,'04980',2792,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (458,'04980',731,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (459,'04980',674,9,3,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (460,'05000',732,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (461,'05010',735,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (462,'05020',736,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (463,'05030',737,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (464,'05030',2867,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (465,'05039',738,4,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (466,'05050',740,28,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (467,'05100',746,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (468,'05110',748,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (469,'05118',751,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (470,'05119',752,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (471,'05120',753,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (472,'05129',758,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (473,'05200',766,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (474,'05219',768,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (475,'05220',769,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (476,'05230',770,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (477,'05240',774,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (478,'05260',777,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (479,'05269',780,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (480,'05270',782,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (481,'05280',783,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (482,'05310',785,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (483,'05320',786,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (484,'05330',787,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (485,'05330',789,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (486,'05330',788,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (487,'05330',2869,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (488,'05348',792,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (489,'05360',794,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (490,'05370',797,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (491,'05379',799,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (492,'05400',800,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (493,'05410',2866,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (494,'05410',801,28,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (495,'05500',803,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (496,'05520',808,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (497,'05530',809,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (498,'05600',810,28,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (499,'05610',2868,28,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (500,'05700',819,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (501,'05710',820,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (502,'05730',825,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (503,'05750',827,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (504,'05760',829,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (505,'05780',833,9,4,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (506,'06000',838,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (507,'06010',847,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (508,'06020',850,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (509,'06030',853,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (510,'06040',860,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (511,'06050',863,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (512,'06060',867,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (513,'06070',873,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (514,'06080',875,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (515,'06090',879,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (516,'06100',882,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (517,'06140',884,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (518,'06170',886,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (519,'06200',891,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (520,'06220',894,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (521,'06240',895,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (522,'06250',896,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (523,'06270',898,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (524,'06280',899,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (525,'06300',900,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (526,'06350',906,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (527,'06400',911,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (528,'06430',913,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (529,'06450',914,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (530,'06470',915,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (531,'06500',919,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (532,'06600',930,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (533,'06700',947,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (534,'06720',955,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (535,'06760',966,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (536,'06780',970,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (537,'06800',974,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (538,'06820',978,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (539,'06840',980,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (540,'06850',981,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (541,'06860',982,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (542,'06870',983,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (543,'06880',984,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (544,'06890',986,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (545,'06900',988,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (546,'06920',989,9,15,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (547,'07000',991,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (548,'07010',994,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (549,'07010',995,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (550,'07010',996,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (551,'07020',997,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (552,'07040',998,28,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (553,'07050',1001,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (554,'07058',1003,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (555,'07060',1005,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (556,'07069',1006,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (557,'07070',1007,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (558,'07070',1008,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (559,'07070',1010,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (560,'07080',1011,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (561,'07089',1012,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (562,'07090',1014,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (563,'07090',1013,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (564,'07100',1016,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (565,'07100',1017,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (566,'07109',1019,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (567,'07110',1021,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (568,'07119',1022,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (569,'07119',1227,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (570,'07130',1023,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (571,'07130',1024,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (572,'07140',1028,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (573,'07140',1029,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (574,'07140',1026,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (575,'07140',1027,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (576,'07144',2887,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (577,'07144',2885,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (578,'07149',1031,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (579,'07150',1033,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (580,'07150',1032,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (581,'07160',1034,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (582,'07164',2549,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (583,'07164',2548,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (584,'07164',2718,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (585,'07164',2717,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (586,'07170',1035,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (587,'07180',1036,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (588,'07180',1037,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (589,'07180',2687,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (590,'07183',2688,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (591,'07187',1038,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (592,'07188',1039,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (593,'07189',1040,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (594,'07190',1041,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (595,'07199',1042,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (596,'07200',1043,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (597,'07207',1046,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (598,'07209',1048,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (599,'07210',1049,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (600,'07214',2884,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (601,'07220',1051,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (602,'07220',1052,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (603,'07224',2883,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (604,'07230',1053,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (605,'07239',1054,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (606,'07240',1056,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (607,'07250',1061,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (608,'07259',1063,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (609,'07268',1065,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (610,'07270',1067,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (611,'07279',1069,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (612,'07280',1070,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (613,'07290',1071,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (614,'07300',2888,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (615,'07300',1072,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (616,'07310',1075,2,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (617,'07320',1080,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (618,'07320',1079,2,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (619,'07330',1085,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (620,'07340',1087,2,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (621,'07340',1088,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (622,'07350',1091,2,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (623,'07350',1092,2,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (624,'07359',1093,2,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (625,'07360',1095,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (626,'07363',2744,4,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (627,'07369',1097,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (628,'07370',1098,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (629,'07380',1100,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (630,'07380',1099,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (631,'07400',1101,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (632,'07410',1102,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (633,'07410',1103,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (634,'07420',1105,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (635,'07420',1106,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (636,'07430',1108,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (637,'07440',1109,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (638,'07450',1110,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (639,'07455',1111,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (640,'07456',1112,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (641,'07460',1117,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (642,'07469',1118,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (643,'07470',1119,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (644,'07480',1120,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (645,'07500',1122,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (646,'07509',1125,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (647,'07510',1126,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (648,'07520',1128,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (649,'07530',1129,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (650,'07540',1131,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (651,'07550',1135,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (652,'07560',1136,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (653,'07570',1137,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (654,'07580',1138,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (655,'07600',1139,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (656,'07620',1141,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (657,'07630',1142,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (658,'07640',1143,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (659,'07650',1144,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (660,'07670',1146,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (661,'07680',1147,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (662,'07700',1148,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (663,'07707',1149,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (664,'07708',1150,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (665,'07720',1153,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (666,'07730',1157,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (667,'07730',1155,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (668,'07730',1156,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (669,'07739',1159,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (670,'07740',1160,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (671,'07750',1161,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (672,'07754',2890,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (673,'07755',2889,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (674,'07760',1162,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (675,'07770',1163,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (676,'07770',1164,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (677,'07780',1166,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (678,'07780',1167,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (679,'07790',1169,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (680,'07790',1168,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (681,'07800',1170,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (682,'07810',1172,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (683,'07820',1174,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (684,'07820',1175,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (685,'07830',1176,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (686,'07838',2886,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (687,'07839',1178,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (688,'07840',1180,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (689,'07840',1179,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (690,'07850',1182,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (691,'07850',1181,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (692,'07858',1183,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (693,'07859',1184,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (694,'07860',1185,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (695,'07860',1187,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (696,'07869',1188,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (697,'07870',1190,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (698,'07870',1189,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (699,'07880',1191,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (700,'07889',1192,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (701,'07890',1194,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (702,'07890',1195,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (703,'07890',1193,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (704,'07899',1196,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (705,'07900',1197,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (706,'07910',1199,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (707,'07918',1200,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (708,'07919',1201,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (709,'07920',1203,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (710,'07920',1202,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (711,'07930',1204,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (712,'07939',1205,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (713,'07940',1206,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (714,'07950',1207,28,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (715,'07960',1211,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (716,'07960',1212,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (717,'07960',1213,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (718,'07969',1216,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (719,'07969',1215,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (720,'07970',1217,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (721,'07979',1218,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (722,'07979',1219,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (723,'07980',1220,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (724,'07990',1222,9,5,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (725,'08000',1233,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (726,'08010',1237,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (727,'08020',1238,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (728,'08030',1241,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (729,'08040',1242,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (730,'08100',1244,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (731,'08200',1250,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (732,'08210',1251,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (733,'08220',1252,2,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (734,'08230',1253,2,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (735,'08240',1254,2,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (736,'08300',1255,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (737,'08310',1256,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (738,'08320',1257,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (739,'08400',1258,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (740,'08420',1262,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (741,'08500',1263,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (742,'08510',1265,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (743,'08600',1269,2,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (744,'08610',1270,2,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (745,'08620',1272,2,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (746,'08650',1277,2,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (747,'08700',1278,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (748,'08710',1279,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (749,'08720',1280,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (750,'08730',1281,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (751,'08760',2795,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (752,'08760',2796,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (753,'08760',1284,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (754,'08770',1285,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (755,'08800',1286,2,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (756,'08810',1288,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (757,'08830',1291,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (758,'08840',1292,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (759,'08900',1293,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (760,'08910',1294,2,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (761,'08920',1295,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (762,'08930',1296,9,6,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (763,'09000',1304,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (764,'09000',1300,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (765,'09000',1299,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (766,'09000',1301,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (767,'09000',1302,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (768,'09000',1305,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (769,'09000',1303,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (770,'09010',1308,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (771,'09020',1310,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (772,'09030',1314,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (773,'09040',1316,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (774,'09060',1321,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (775,'09060',1320,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (776,'09070',1322,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (777,'09080',1325,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (778,'09089',1328,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (779,'09090',1329,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (780,'09099',1330,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (781,'09100',1331,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (782,'09130',1334,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (783,'09140',1335,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (784,'09180',1339,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (785,'09200',1341,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (786,'09208',1345,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (787,'09209',1352,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (788,'09210',1355,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (789,'09220',1357,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (790,'09230',1368,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (791,'09230',1365,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (792,'09230',1369,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (793,'09230',1364,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (794,'09239',1370,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (795,'09240',1371,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (796,'09250',1373,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (797,'09260',1375,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (798,'09270',1376,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (799,'09280',1377,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (800,'09290',1378,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (801,'09300',1380,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (802,'09310',1382,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (803,'09310',1383,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (804,'09310',2839,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (805,'09319',1384,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (806,'09320',1388,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (807,'09350',1392,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (808,'09359',1393,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (809,'09360',1398,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (810,'09360',1399,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (811,'09400',1407,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (812,'09400',1408,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (813,'09410',1414,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (814,'09410',1410,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (815,'09410',1412,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (816,'09410',1413,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (817,'09420',1415,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (818,'09420',1416,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (819,'09429',1417,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (820,'09430',1419,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (821,'09430',1418,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (822,'09438',1422,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (823,'09440',1426,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (824,'09440',2840,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (825,'09440',1425,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (826,'09450',1429,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (827,'09460',1430,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (828,'09470',1431,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (829,'09479',1432,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (830,'09480',1433,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (831,'09500',1435,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (832,'09500',2565,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (833,'09510',1436,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (834,'09520',1439,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (835,'09520',1440,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (836,'09530',1441,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (837,'09550',1443,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (838,'09560',1444,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (839,'09570',1445,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (840,'09578',1449,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (841,'09600',1453,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (842,'09608',1454,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (843,'09609',1455,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (844,'09620',1456,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (845,'09630',2853,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (846,'09630',2854,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (847,'09630',2859,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (848,'09630',2850,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (849,'09630',2855,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (850,'09630',2860,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (851,'09630',2852,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (852,'09630',2858,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (853,'09630',2862,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (854,'09630',2861,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (855,'09630',1481,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (856,'09630',2851,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (857,'09630',2856,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (858,'09630',2857,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (859,'09630',2863,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (860,'09637',1461,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (861,'09638',1462,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (862,'09640',1464,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (863,'09640',1465,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (864,'09648',1467,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (865,'09660',1470,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (866,'09670',1471,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (867,'09680',1473,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (868,'09689',1477,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (869,'09690',1478,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (870,'09696',1480,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (871,'09698',1482,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (872,'09700',1485,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (873,'09700',1491,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (874,'09700',1488,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (875,'09700',1486,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (876,'09704',1492,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (877,'09705',2726,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (878,'09706',1497,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (879,'09708',1500,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (880,'09709',1501,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (881,'09710',1502,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (882,'09720',1505,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (883,'09720',1506,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (884,'09730',1508,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (885,'09740',1511,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (886,'09750',1514,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (887,'09750',1515,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (888,'09750',1512,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (889,'09760',1518,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (890,'09769',1523,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (891,'09770',1524,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (892,'09780',1527,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (893,'09780',1528,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (894,'09790',1531,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (895,'09800',1535,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (896,'09800',1536,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (897,'09800',1538,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (898,'09800',1540,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (899,'09800',1539,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (900,'09800',1541,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (901,'09800',1534,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (902,'09800',1532,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (903,'09800',1542,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (904,'09810',1549,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (905,'09810',1547,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (906,'09810',1548,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (907,'09810',1550,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (908,'09819',1551,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (909,'09820',1553,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (910,'09820',1557,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (911,'09820',1552,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (912,'09820',1556,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (913,'09828',1559,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (914,'09829',1560,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (915,'09830',1565,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (916,'09830',1566,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (917,'09830',1562,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (918,'09830',1563,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (919,'09830',1568,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (920,'09837',1570,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (921,'09838',1571,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (922,'09839',1576,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (923,'09839',1573,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (924,'09840',1578,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (925,'09849',1580,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (926,'09850',1586,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (927,'09850',1588,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (928,'09850',1589,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (929,'09856',1591,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (930,'09856',1590,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (931,'09858',1594,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (932,'09859',1595,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (933,'09860',1596,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (934,'09860',1600,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (935,'09860',1597,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (936,'09860',1601,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (937,'09860',1598,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (938,'09860',1602,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (939,'09868',1607,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (940,'09870',1613,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (941,'09870',1609,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (942,'09870',1611,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (943,'09870',1616,28,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (944,'09880',1621,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (945,'09880',1620,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (946,'09890',1629,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (947,'09897',1633,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (948,'09900',1639,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (949,'09900',1638,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (950,'09900',1637,2,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (951,'09910',1640,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (952,'09920',1643,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (953,'09930',1644,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (954,'09940',1645,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (955,'09960',1647,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (956,'09960',1646,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (957,'09960',1648,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (958,'09960',1961,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (959,'09960',2736,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (960,'09969',1651,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (961,'09970',1652,9,7,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (962,'10000',1672,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (963,'10010',1674,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (964,'10010',1673,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (965,'10020',1675,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (966,'10130',1681,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (967,'10200',1682,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (968,'10300',1686,28,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (969,'10320',1690,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (970,'10330',1691,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (971,'10340',1692,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (972,'10350',1693,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (973,'10360',1694,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (974,'10368',1695,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (975,'10369',1696,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (976,'10369',2669,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (977,'10370',1697,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (978,'10378',1698,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (979,'10379',1700,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (980,'10380',1701,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (981,'10400',1702,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (982,'10500',1703,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (983,'10580',1707,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (984,'10600',1710,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (985,'10610',1713,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (986,'10620',1714,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (987,'10630',2706,4,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (988,'10640',1716,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (989,'10640',1717,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (990,'10640',1718,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (991,'10660',1720,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (992,'10700',1721,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (993,'10710',1723,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (994,'10800',1728,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (995,'10810',1730,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (996,'10820',1731,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (997,'10830',1732,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (998,'10840',1734,2,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (999,'10840',1733,2,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1000,'10900',1735,28,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1001,'10910',1737,28,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1002,'10920',1738,9,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1003,'10926',1739,4,8,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1004,'11000',2775,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1005,'11000',2776,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1006,'11000',1745,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1007,'11000',2774,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1008,'11000',2777,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1009,'11000',2778,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1010,'11000',2779,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1011,'11000',2780,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1012,'11040',1752,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1013,'11100',2772,17,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1014,'11100',1754,17,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1015,'11200',1757,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1016,'11200',1758,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1017,'11210',1761,28,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1018,'11220',1764,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1019,'11230',1765,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1020,'11240',1767,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1021,'11250',1768,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1022,'11260',1770,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1023,'11260',1771,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1024,'11270',1772,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1025,'11280',1773,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1026,'11289',1774,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1027,'11290',1775,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1028,'11290',1776,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1029,'11300',1777,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1030,'11310',1780,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1031,'11320',1782,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1032,'11320',2771,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1033,'11330',1784,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1034,'11340',1785,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1035,'11350',1786,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1036,'11360',1787,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1037,'11370',1789,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1038,'11400',1791,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1039,'11410',1794,28,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1040,'11410',1793,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1041,'11420',1796,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1042,'11430',1798,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1043,'11430',1797,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1044,'11440',1800,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1045,'11440',1799,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1046,'11450',1802,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1047,'11450',1803,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1048,'11450',1801,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1049,'11460',1804,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1050,'11460',1807,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1051,'11460',1806,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1052,'11460',1805,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1053,'11470',1809,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1054,'11470',1808,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1055,'11480',1811,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1056,'11480',1812,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1057,'11489',1813,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1058,'11490',1814,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1059,'11490',1815,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1060,'11500',1816,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1061,'11510',2781,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1062,'11520',1822,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1063,'11529',1823,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1064,'11530',2782,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1065,'11540',2783,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1066,'11550',2784,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1067,'11560',2785,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1068,'11580',1833,17,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1069,'11590',1838,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1070,'11600',1843,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1071,'11610',1844,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1072,'11619',1846,17,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1073,'11650',1855,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1074,'11700',1856,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1075,'11800',1858,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1076,'11800',2773,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1077,'11810',1860,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1078,'11820',1861,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1079,'11830',1862,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1080,'11840',1863,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1081,'11850',1864,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1082,'11850',2786,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1083,'11860',1865,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1084,'11870',1867,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1085,'11910',1869,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1086,'11920',1870,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1087,'11930',2789,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1088,'11950',1874,9,16,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1089,'12000',1883,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1090,'12000',1877,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1091,'12000',1879,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1092,'12000',1884,9,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1093,'12000',1878,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1094,'12000',1881,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1095,'12000',1882,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1096,'12070',1889,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1097,'12080',1890,28,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1098,'12100',1895,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1099,'12100',1891,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1100,'12100',1894,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1101,'12100',1896,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1102,'12100',1893,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1103,'12110',2010,9,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1104,'12110',2011,9,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1105,'12200',1897,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1106,'12200',1899,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1107,'12200',1901,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1108,'12200',1898,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1109,'12250',1903,28,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1110,'12300',1904,28,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1111,'12400',1905,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1112,'12400',1906,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1113,'12400',1907,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1114,'12410',2870,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1115,'12500',1910,28,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1116,'12600',1911,28,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1117,'12700',1912,28,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1118,'12800',1913,28,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1119,'12910',2871,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1120,'12920',1880,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1121,'12930',2873,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1122,'12940',2874,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1123,'12950',2872,2,9,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1124,'13000',1919,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1125,'13010',1923,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1126,'13020',1924,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1127,'13030',1925,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1128,'13040',1926,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1129,'13050',1928,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1130,'13060',1930,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1131,'13060',1929,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1132,'13070',1931,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1133,'13070',1932,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1134,'13080',1933,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1135,'13090',1934,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1136,'13093',2497,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1137,'13094',2898,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1138,'13099',1935,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1139,'13100',1937,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1140,'13110',1939,4,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1141,'13119',1940,4,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1142,'13120',1943,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1143,'13120',1942,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1144,'13123',2576,4,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1145,'13123',2577,4,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1146,'13123',2575,4,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1147,'13129',1944,4,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1148,'13150',1945,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1149,'13180',1946,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1150,'13200',1947,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1151,'13210',1949,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1152,'13219',1950,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1153,'13219',1951,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1154,'13220',1952,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1155,'13230',1955,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1156,'13250',1957,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1157,'13270',1958,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1158,'13273',2580,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1159,'13278',1959,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1160,'13280',1962,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1161,'13300',1963,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1162,'13300',2894,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1163,'13300',1964,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1164,'13300',2891,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1165,'13300',2893,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1166,'13310',1966,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1167,'13315',2897,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1168,'13319',1970,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1169,'13360',1971,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1170,'13360',2895,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1171,'13360',2896,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1172,'13363',2586,4,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1173,'13400',1972,28,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1174,'13410',1973,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1175,'13419',1975,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1176,'13420',1976,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1177,'13430',1978,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1178,'13440',1980,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1179,'13450',1982,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1180,'13450',1981,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1181,'13460',1983,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1182,'13508',1985,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1183,'13508',2593,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1184,'13509',1986,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1185,'13510',1987,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1186,'13520',1988,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1187,'13529',1989,4,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1188,'13530',1991,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1189,'13530',1990,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1190,'13540',1994,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1191,'13540',1992,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1192,'13545',2587,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1193,'13546',2916,4,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1194,'13549',1995,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1195,'13550',1997,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1196,'13550',1998,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1197,'13600',2002,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1198,'13610',2004,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1199,'13625',2005,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1200,'13630',2007,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1201,'13640',2008,2,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1202,'13700',2892,9,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1203,'13700',2009,28,11,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1204,'14000',2027,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1205,'14000',2876,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1206,'14010',2029,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1207,'14020',2035,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1208,'14030',2036,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1209,'14039',2041,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1210,'14040',2042,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1211,'14040',2043,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1212,'14049',2044,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1213,'14050',2045,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1214,'14060',2046,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1215,'14070',2048,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1216,'14070',2049,2,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1217,'14070',2050,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1218,'14080',2052,2,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1219,'14080',2051,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1220,'14090',2054,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1221,'14100',2062,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1222,'14100',2063,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1223,'14100',2061,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1224,'14100',2064,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1225,'14100',2060,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1226,'14108',2067,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1227,'14110',2881,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1228,'14120',2071,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1229,'14140',2074,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1230,'14150',2078,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1231,'14160',2079,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1232,'14200',2081,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1233,'14200',2080,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1234,'14208',2083,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1235,'14209',2084,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1236,'14210',2085,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1237,'14219',2087,17,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1238,'14219',2187,17,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1239,'14220',2088,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1240,'14220',2090,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1241,'14230',2091,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1242,'14239',2093,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1243,'14240',2094,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1244,'14240',2095,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1245,'14248',2096,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1246,'14250',2098,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1247,'14250',2099,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1248,'14250',2100,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1249,'14250',2101,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1250,'14260',2102,2,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1251,'14260',2103,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1252,'14266',2106,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1253,'14267',2107,2,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1254,'14268',2108,2,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1255,'14269',2109,2,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1256,'14270',2110,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1257,'14273',2694,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1258,'14275',2111,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1259,'14276',2112,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1260,'14300',2113,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1261,'14300',2114,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1262,'14300',2115,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1263,'14308',2879,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1264,'14310',2120,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1265,'14310',2121,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1266,'14320',2123,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1267,'14325',2125,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1268,'14326',2126,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1269,'14330',2132,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1270,'14330',2134,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1271,'14340',2146,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1272,'14340',2145,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1273,'14350',2147,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1274,'14357',2149,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1275,'14357',2150,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1276,'14360',2877,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1277,'14360',2153,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1278,'14370',2155,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1279,'14370',2158,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1280,'14370',2157,28,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1281,'14376',2159,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1282,'14377',2161,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1283,'14380',2167,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1284,'14380',2163,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1285,'14386',2168,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1286,'14387',2169,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1287,'14388',2170,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1288,'14389',2172,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1289,'14390',2176,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1290,'14390',2878,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1291,'14390',2175,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1292,'14390',2174,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1293,'14400',2179,28,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1294,'14406',2182,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1295,'14408',2183,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1296,'14409',2184,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1297,'14410',2186,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1298,'14420',2190,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1299,'14420',2188,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1300,'14420',2192,28,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1301,'14426',2195,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1302,'14426',2194,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1303,'14427',2196,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1304,'14427',2197,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1305,'14429',2200,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1306,'14430',2201,2,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1307,'14430',2203,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1308,'14438',2205,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1309,'14439',2206,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1310,'14440',2207,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1311,'14449',2208,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1312,'14449',2210,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1313,'14449',2209,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1314,'14456',2212,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1315,'14460',2213,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1316,'14470',2214,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1317,'14476',2216,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1318,'14479',2219,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1319,'14480',2220,28,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1320,'14490',2221,28,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1321,'14500',2222,28,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1322,'14520',2223,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1323,'14529',2224,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1324,'14550',2227,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1325,'14600',2229,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1326,'14608',2231,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1327,'14609',2232,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1328,'14610',2233,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1329,'14620',2741,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1330,'14629',2236,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1331,'14630',2237,28,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1332,'14630',2238,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1333,'14640',2240,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1334,'14643',2241,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1335,'14646',2245,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1336,'14647',2246,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1337,'14647',2247,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1338,'14647',2880,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1339,'14650',2252,28,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1340,'14653',2882,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1341,'14654',2453,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1342,'14654',2255,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1343,'14655',2256,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1344,'14657',2258,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1345,'14658',2259,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1346,'14659',2260,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1347,'14700',2261,28,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1348,'14710',2262,28,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1349,'14720',2263,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1350,'14730',2265,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1351,'14734',2266,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1352,'14735',2695,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1353,'14735',2267,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1354,'14737',2268,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1355,'14738',2269,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1356,'14739',2270,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1357,'14740',2272,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1358,'14748',2273,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1359,'14748',2274,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1360,'14749',2275,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1361,'14750',2277,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1362,'14760',2278,9,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1363,'14780',2279,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1364,'14790',2280,4,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1365,'14900',2286,28,12,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1366,'15000',2287,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1367,'15010',2289,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1368,'15020',2290,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1369,'15100',2291,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1370,'15200',2293,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1371,'15210',2294,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1372,'15220',2296,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1373,'15220',2295,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1374,'15230',2297,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1375,'15240',2298,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1376,'15250',2299,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1377,'15260',2300,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1378,'15270',2301,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1379,'15280',2302,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1380,'15290',2303,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1381,'15300',2305,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1382,'15309',2307,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1383,'15310',2308,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1384,'15320',2309,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1385,'15330',2310,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1386,'15339',2311,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1387,'15340',2312,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1388,'15350',2313,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1389,'15370',2315,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1390,'15380',2316,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1391,'15390',2319,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1392,'15400',2320,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1393,'15410',2321,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1394,'15420',2322,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1395,'15430',2323,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1396,'15440',2324,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1397,'15450',2325,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1398,'15460',2326,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1399,'15470',2327,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1400,'15500',2328,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1401,'15510',2330,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1402,'15520',2331,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1403,'15530',2332,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1404,'15540',2333,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1405,'15600',2334,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1406,'15610',2335,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1407,'15620',2336,1,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1408,'15630',2340,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1409,'15640',2342,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1410,'15640',2351,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1411,'15650',2343,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1412,'15660',2344,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1413,'15670',2346,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1414,'15680',2347,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1415,'15700',2349,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1416,'15710',2350,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1417,'15730',2353,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1418,'15740',2354,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1419,'15750',2355,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1420,'15800',2356,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1421,'15810',2358,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1422,'15820',2359,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1423,'15830',2360,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1424,'15840',2361,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1425,'15850',2362,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1426,'15860',2363,28,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1427,'15870',2364,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1428,'15900',2365,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1429,'15960',2372,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1430,'15970',2376,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1431,'15980',2377,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1432,'15990',2378,9,17,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1433,'16000',2381,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1434,'16000',2379,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1435,'16000',2380,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1436,'16010',2832,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1437,'16010',2388,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1438,'16010',2386,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1439,'16010',2385,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1440,'16020',2396,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1441,'16020',2395,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1442,'16029',2399,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1443,'16030',2402,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1444,'16030',2919,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1445,'16030',2401,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1446,'16030',2403,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1447,'16034',2405,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1448,'16035',2407,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1449,'16035',2833,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1450,'16036',2408,17,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1451,'16038',2409,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1452,'16040',2412,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1453,'16040',2411,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1454,'16050',2413,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1455,'16050',2414,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1456,'16050',2415,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1457,'16059',2420,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1458,'16060',2421,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1459,'16070',2425,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1460,'16070',2426,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1461,'16070',2424,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1462,'16070',2423,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1463,'16080',2430,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1464,'16080',2429,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1465,'16080',2428,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1466,'16080',2427,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1467,'16090',2434,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1468,'16090',2432,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1469,'16090',2433,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1470,'16100',2439,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1471,'16200',2443,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1472,'16210',2444,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1473,'16300',2458,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1474,'16310',2459,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1475,'16320',2460,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1476,'16340',2462,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1477,'16400',2464,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1478,'16410',2465,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1479,'16420',2466,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1480,'16428',2467,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1481,'16429',2468,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1482,'16430',2470,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1483,'16440',2471,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1484,'16443',2899,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1485,'16450',2472,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1486,'16457',2480,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1487,'16459',2473,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1488,'16500',2475,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1489,'16513',2900,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1490,'16513',2901,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1491,'16514',2902,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1492,'16514',2904,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1493,'16514',2903,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1494,'16520',2478,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1495,'16530',2834,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1496,'16530',2479,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1497,'16533',2905,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1498,'16550',2481,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1499,'16600',2482,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1500,'16604',2906,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1501,'16605',2907,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1502,'16606',2908,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1503,'16607',2909,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1504,'16609',2483,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1505,'16610',2484,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1506,'16614',2910,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1507,'16615',2911,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1508,'16616',2913,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1509,'16617',2912,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1510,'16620',2485,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1511,'16629',2489,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1512,'16629',2487,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1513,'16630',2490,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1514,'16640',2491,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1515,'16710',2494,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1516,'16720',2495,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1517,'16739',2498,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1518,'16740',2499,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1519,'16749',2500,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1520,'16750',2501,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1521,'16770',2502,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1522,'16776',2503,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1523,'16776',2600,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1524,'16780',2604,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1525,'16780',2835,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1526,'16780',2504,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1527,'16780',2602,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1528,'16780',2836,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1529,'16797',2508,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1530,'16799',2509,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1531,'16800',2510,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1532,'16810',2607,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1533,'16810',2515,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1534,'16810',2516,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1535,'16813',2914,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1536,'16840',2519,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1537,'16850',2520,2,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1538,'16860',2521,9,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1539,'16880',2527,28,13,9);
INSERT INTO motor_interprete.cat_codigos_postales (id_codigo_postal,codigo_postal,id_asentamiento,id_tipo_asentamiento,id_municipio,id_estado) VALUES (1540,'16900',2530,28,13,9);


INSERT INTO motor_interprete.cat_estados_sistema VALUES (1, 'En línea');
INSERT INTO motor_interprete.cat_estados_sistema VALUES (2, 'En mantenimiento');
INSERT INTO motor_interprete.cat_estados_sistema VALUES (3, 'En sincronización');
INSERT INTO motor_interprete.cat_estados_sistema VALUES (4, 'Sincronizado');


INSERT INTO motor_interprete.cat_tipo_proyecto VALUES (1, 'Trámite o servicio');
INSERT INTO motor_interprete.cat_tipo_proyecto VALUES (2, 'Programa social');

INSERT INTO motor_interprete.cat_estatus_proyecto VALUES (1, 'Publicado');
INSERT INTO motor_interprete.cat_estatus_proyecto VALUES (2, 'Borrador');
INSERT INTO motor_interprete.cat_estatus_proyecto VALUES (3, 'En pausa');
INSERT INTO motor_interprete.cat_estatus_proyecto VALUES (4, 'Edición');
INSERT INTO motor_interprete.cat_estatus_proyecto VALUES (5, 'Publicar cambios');
INSERT INTO motor_interprete.cat_estatus_proyecto VALUES (6, 'Error al publicar');

INSERT INTO motor_interprete.cat_tipo_costo VALUES (1, 'Costo fijo', true);
INSERT INTO motor_interprete.cat_tipo_costo VALUES (2, 'Costo variable', true);

INSERT INTO motor_interprete.cat_tipo_archivo VALUES (1, 'png', true);
INSERT INTO motor_interprete.cat_tipo_archivo VALUES (2, 'jpg', true);
INSERT INTO motor_interprete.cat_tipo_archivo VALUES (3, 'pdf', true);

/**Orden definido en constantes de aplicativo**/
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (1, 'Detalle proyecto');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (2, 'Detalle home tramite');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (3, 'Detalle home programa social');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (4, 'Detalle captcha');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (5, 'Detalle acceso llave');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (6, 'Detalle Formulario y carga de documentos');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (7, 'Detalle pagos en línea y línea de captura');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (8, 'Detalle gestión usuarios');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (9, 'Detalle firma digital');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (10, 'Detalle archivos respuesta');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (11, 'Detalle legales');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (12, 'Detalle analytics');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (13, 'Secciones formulario');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (14, 'Subecciones formulario');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (15, 'Componentes');
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (16, 'Detalle Security Domain');

INSERT INTO motor_interprete.cat_motivos_pausa
(id_motivo, descripcion, descripcion_motivo)
VALUES(1, 'Actualización del trámite', 'El área usuaria solicita que se agreguen o quiten campos del trámite o se requiera hacer alguna actualización de base de datos.');

INSERT INTO motor_interprete.cat_tipo_componente VALUES (1, 'Campo de texto', false, true );
INSERT INTO motor_interprete.cat_tipo_componente VALUES (2, 'Checkbox único', false, true );
INSERT INTO motor_interprete.cat_tipo_componente VALUES (3, 'Checkbox grupo', false, true );
INSERT INTO motor_interprete.cat_tipo_componente VALUES (4, 'Fecha', false, true );
INSERT INTO motor_interprete.cat_tipo_componente VALUES (5, 'Radio botón', false, true );
INSERT INTO motor_interprete.cat_tipo_componente VALUES (6, 'Menú desplegable', false, false );
INSERT INTO motor_interprete.cat_tipo_componente VALUES (7, 'Carga de documentos', false, true );
INSERT INTO motor_interprete.cat_tipo_componente VALUES (8, 'Imágen', false, false );
INSERT INTO motor_interprete.cat_tipo_componente VALUES (9, 'Video', false, false );
INSERT INTO motor_interprete.cat_tipo_componente VALUES (10, 'Datos de domicilio', true, true);
INSERT INTO motor_interprete.cat_tipo_componente VALUES (11, 'Datos personales sin Llave', true, true);
INSERT INTO motor_interprete.cat_tipo_componente VALUES (12, 'Datos personales Llave', true, true);
INSERT INTO motor_interprete.cat_tipo_componente VALUES (13, 'Informativo', false, true);

insert into motor_interprete.cat_dependencia VALUES (1,'JEFATURA DE GOBIERNO',true);
insert into motor_interprete.cat_dependencia VALUES (2,'CENTRO DE COMANDO, CONTROL, CÓMPUTO, COMUNICACIONES Y CONTACTO CIUDADANO',true);
insert into motor_interprete.cat_dependencia VALUES (3,'AGENCIA DIGITAL DE INNOVACIÓN PÚBLICA',true);
insert into motor_interprete.cat_dependencia VALUES (4,'SECRETARÍA DE GOBIERNO',true);
insert into motor_interprete.cat_dependencia VALUES (5,'ALCALDÍA ÁLVARO OBREGÓN',true);
insert into motor_interprete.cat_dependencia VALUES (6,'ALCALDÍA AZCAPOTZALCO',true);
insert into motor_interprete.cat_dependencia VALUES (7,'ALCALDÍA BENITO JUÁREZ',true);
insert into motor_interprete.cat_dependencia VALUES (8,'ALCALDÍA COYOACÁN',true);
insert into motor_interprete.cat_dependencia VALUES (9,'ALCALDÍA CUAJIMALPA DE MORELOS',true);
insert into motor_interprete.cat_dependencia VALUES (10,'ALCALDÍA CUAUHTÉMOC',true);
insert into motor_interprete.cat_dependencia VALUES (11,'ALCALDÍA GUSTAVO A. MADERO',true);
insert into motor_interprete.cat_dependencia VALUES (12,'ALCALDÍA IZTACALCO',true);
insert into motor_interprete.cat_dependencia VALUES (13,'ALCALDÍA IZTAPALAPA',true);
insert into motor_interprete.cat_dependencia VALUES (14,'ALCALDÍA MAGDALENA CONTRERAS',true);
insert into motor_interprete.cat_dependencia VALUES (15,'ALCALDÍA MIGUEL HIDALGO',true);
insert into motor_interprete.cat_dependencia VALUES (16,'ALCALDÍA MILPA ALTA',true);
insert into motor_interprete.cat_dependencia VALUES (17,'ALCALDÍA TLAHUAC',true);
insert into motor_interprete.cat_dependencia VALUES (18,'ALCALDÍA TLALPAN',true);
insert into motor_interprete.cat_dependencia VALUES (19,'ALCALDÍA VENUSTIANO CARRANZA',true);
insert into motor_interprete.cat_dependencia VALUES (20,'ALCALDÍA XOCHIMILCO',true);
insert into motor_interprete.cat_dependencia VALUES (21,'COMISIÓN DE BUSQUEDA DE PERSONAS DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (22,'AUTORIDAD DEL CENTRO HISTÓRICO',true);
insert into motor_interprete.cat_dependencia VALUES (23,'INSTANCIA EJECUTORA DEL SISTEMA INTEGRAL DE DERECHOS HUMANOS',true);
insert into motor_interprete.cat_dependencia VALUES (24,'SECRETARÍA DE DESARROLLO URBANO Y VIVIENDA',true);
insert into motor_interprete.cat_dependencia VALUES (25,'SECRETARÍA DE DESARROLLO ECONÓMICO',true);
insert into motor_interprete.cat_dependencia VALUES (26,'SECRETARÍA DE TURISMO',true);
insert into motor_interprete.cat_dependencia VALUES (27,'SECRETARÍA DEL MEDIO AMBIENTE',true);
insert into motor_interprete.cat_dependencia VALUES (28,'SISTEMA DE AGUAS',true);
insert into motor_interprete.cat_dependencia VALUES (29,'AGENCIA DE ATENCIÓN ANIMAL',true);
insert into motor_interprete.cat_dependencia VALUES (30,'SECRETARÍA DE OBRAS Y SERVICIOS',true);
insert into motor_interprete.cat_dependencia VALUES (31,'PLANTA PRODUCTORA DE MEZCLAS ASFÁLTICAS',true);
insert into motor_interprete.cat_dependencia VALUES (32,'SECRETARÍA DE INCLUSIÓN Y BIENESTAR SOCIAL',true);
insert into motor_interprete.cat_dependencia VALUES (33,'SECRETARÍA DE ADMINISTRACIÓN Y FINANZAS',true);
insert into motor_interprete.cat_dependencia VALUES (34,'SECRETARÍA DE MOVILIDAD',true);
insert into motor_interprete.cat_dependencia VALUES (35,'SECRETARÍA DE SEGURIDAD CIUDADANA',true);
insert into motor_interprete.cat_dependencia VALUES (36,'UNIVERSIDAD DE LA POLICÍA',true);
insert into motor_interprete.cat_dependencia VALUES (37,'POLICÍA AUXILIAR',true);
insert into motor_interprete.cat_dependencia VALUES (38,'POLICÍA BANCARIA E INDUSTRIAL',true);
insert into motor_interprete.cat_dependencia VALUES (39,'SECRETARÍA DE LA CONTRALORÍA GENERAL',true);
insert into motor_interprete.cat_dependencia VALUES (40,'TESORERÍA',true);
insert into motor_interprete.cat_dependencia VALUES (41,'DEUDA PÚBLICA',true);
insert into motor_interprete.cat_dependencia VALUES (42,'CONSEJERÍA JURÍDICA Y DE SERVICIOS LEGALES',true);
insert into motor_interprete.cat_dependencia VALUES (43,'SECRETARÍA DE SALUD',true);
insert into motor_interprete.cat_dependencia VALUES (44,'AGENCIA DE PROTECCIÓN SANITARIA',true);
insert into motor_interprete.cat_dependencia VALUES (45,'SECRETARÍA DE CULTURA',true);
insert into motor_interprete.cat_dependencia VALUES (46,'SECRETARÍA DE TRABAJO Y FOMENTO AL EMPLEO',true);
insert into motor_interprete.cat_dependencia VALUES (47,'SECRETARÍA DE GESTIÓN INTEGRAL DE RIESGOS Y PROTECCIÓN CIVIL',true);
insert into motor_interprete.cat_dependencia VALUES (48,'SECRETARÍA DE PUEBLOS Y BARRIOS ORIGINARIOS Y COMUNIDADES INDÍGENAS RESIDENTES',true);
insert into motor_interprete.cat_dependencia VALUES (49,'SECRETARÍA DE EDUCACIÓN, CIENCIA, TECNOLOGÍA E INNOVACIÓN',true);
insert into motor_interprete.cat_dependencia VALUES (50,'UNIVERSIDAD DE LA SALUD',true);
insert into motor_interprete.cat_dependencia VALUES (51,'INSTITUTO DE ESTUDIOS SUPERIORES DE LA CIUDAD DE MÉXICO "ROSARIO CASTELLANOS"',true);
insert into motor_interprete.cat_dependencia VALUES (52,'SECRETARÍA DE LAS MUJERES',true);
insert into motor_interprete.cat_dependencia VALUES (53,'SISTEMA PÚBLICO DE RADIO DIFUSIÓN',true);
insert into motor_interprete.cat_dependencia VALUES (54,'CONGRESO DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (55,'AUDITORÍA SUPERIOR DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (56,'TRIBUNAL SUPERIOR DE JUSTICIA',true);
insert into motor_interprete.cat_dependencia VALUES (57,'CONSEJO DE LA JUDICATURA',true);
insert into motor_interprete.cat_dependencia VALUES (58,'TRIBUNAL DE JUSTICIA ADMINISTRATIVA DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (59,'JUNTA LOCAL DE CONCILIACIÓN Y ARBITRAJE DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (60,'COMISIÓN DE DERECHOS HUMANOS DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (61,'INSTITUTO ELECTORAL DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (62,'TRIBUNAL ELECTORAL DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (63,'UNIVERSIDAD AUTÓNOMA DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (64,'INSTITUTO DE TRANSPARENCIA, ACCESO A LA INFORMACIÓN PÚBLICA, PROTECCIÓN DE DATOS PERSONALES Y RENDICIÓN DE CUENTAS',true);
insert into motor_interprete.cat_dependencia VALUES (65,'FISCALÍA GENERAL DE JUSTICIA DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (66,'CONSEJO DE EVALUACIÓN DEL DESARROLLO SOCIAL DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (67,'FONDO PARA EL DESARROLLO ECONÓMICO Y SOCIAL',true);
insert into motor_interprete.cat_dependencia VALUES (68,'COMISIÓN DE ATENCIÓN A VÍCTIMAS DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (69,'MECANISMO PARA LA PROTECCIÓN INTEGRAL DE PERSONAS DEFENSORAS DE DERECHOS HUMANOS Y PERIODISTAS',true);
insert into motor_interprete.cat_dependencia VALUES (70,'INSTITUTO DE VIVIENDA',true);
insert into motor_interprete.cat_dependencia VALUES (71,'FONDO DE DESARROLLO ECONÓMICO',true);
insert into motor_interprete.cat_dependencia VALUES (72,'FONDO PARA EL DESARROLLO SOCIAL',true);
insert into motor_interprete.cat_dependencia VALUES (73,'FONDO MIXTO DE PROMOCIÓN TURÍSTICA',true);
insert into motor_interprete.cat_dependencia VALUES (74,'FONDO AMBIENTAL PÚBLICO',true);
insert into motor_interprete.cat_dependencia VALUES (75,'PROCURADURÍA AMBIENTAL Y DEL ORDENAMIENTO TERRITORIAL',true);
insert into motor_interprete.cat_dependencia VALUES (76,'INSTITUTO LOCAL DE LA INFRAESTRUCTURA FÍSICA EDUCATIVA',true);
insert into motor_interprete.cat_dependencia VALUES (77,'INSTITUTO PARA LA SEGURIDAD DE LAS CONSTRUCCIONES',true);
insert into motor_interprete.cat_dependencia VALUES (78,'CONSEJO PARA PREVENIR Y ELIMINAR LA DISCRIMINACIÓN',true);
insert into motor_interprete.cat_dependencia VALUES (79,'SISTEMA PARA EL DESARROLLO INTEGRAL DE LA FAMILIA',true);
insert into motor_interprete.cat_dependencia VALUES (80,'INSTITUTO DE LAS PERSONAS CON DISCAPACIDAD',true);
insert into motor_interprete.cat_dependencia VALUES (81,'INSTITUTO DE LA JUVENTUD',true);
insert into motor_interprete.cat_dependencia VALUES (82,'PROCURADURÍA SOCIAL',true);
insert into motor_interprete.cat_dependencia VALUES (83,'FIDEICOMISO DEL CENTRO HISTÓRICO',true);
insert into motor_interprete.cat_dependencia VALUES (84,'FIDEICOMISO DE RECUPERACIÓN CREDITICIA DE LA VIVIENDA',true);
insert into motor_interprete.cat_dependencia VALUES (85,'FONDO PÚBLICO DE ATENCIÓN AL CICLISTA Y AL PEATÓN',true);
insert into motor_interprete.cat_dependencia VALUES (86,'FIDEICOMISO PARA EL FONDO DE PROMOCIÓN PARA EL FINANCIAMIENTO DEL TRASPORTE PÚBLICO',true);
insert into motor_interprete.cat_dependencia VALUES (87,'METROBÚS',true);
insert into motor_interprete.cat_dependencia VALUES (88,'SISTEMA DE TRANSPORTE COLECTIVO METRO',true);
insert into motor_interprete.cat_dependencia VALUES (89,'RED DE TRANSPORTE DE PASAJEROS (RTP)',true);
insert into motor_interprete.cat_dependencia VALUES (90,'SERVICIO DE TRANSPORTES ELÉCTRICOS',true);
insert into motor_interprete.cat_dependencia VALUES (91,'ORGANISMO REGULADOR DE TRANSPORTE',true);
insert into motor_interprete.cat_dependencia VALUES (92,'ESCUELA DE ADMINISTRACIÓN PÚBLICA',true);
insert into motor_interprete.cat_dependencia VALUES (93,'INSTITUTO DE VERIFICACIÓN ADMINISTRATIVA DE LA CIUDAD DE MÉXICO',true);
insert into motor_interprete.cat_dependencia VALUES (94,'FIDEICOMISO FONDO DE APOYO A LA PROCURACIÓN DE JUSTICIA',true);
insert into motor_interprete.cat_dependencia VALUES (95,'INSTITUTO PARA LA ATENCIÓN Y PREVENCIÓN DE LAS ADICCIONES',true);
insert into motor_interprete.cat_dependencia VALUES (96,'SERVICIOS DE SALUD PÚBLICA',true);
insert into motor_interprete.cat_dependencia VALUES (97,'FIDEICOMISO MUSEO DE ARTE POPULAR MEXICANO',true);
insert into motor_interprete.cat_dependencia VALUES (98,'FIDEICOMISO MUSEO DEL ESTANQUILLO',true);
insert into motor_interprete.cat_dependencia VALUES (99,'FIDEICOMISO DE PROMOCION Y DESARROLLO DEL CINE MEXICANO',true);
insert into motor_interprete.cat_dependencia VALUES (100,'INSTITUTO DE CAPACITACIÓN PARA EL TRABAJO',true);
insert into motor_interprete.cat_dependencia VALUES (101,'HEROICO CUERPO DE BOMBEROS',true);
insert into motor_interprete.cat_dependencia VALUES (102,'INSTITUTO DEL DEPORTE',true);
insert into motor_interprete.cat_dependencia VALUES (103,'INSTITUTO DE EDUCACIÓN MEDIA SUPERIOR',true);
insert into motor_interprete.cat_dependencia VALUES (104,'FIDEICOMISO EDUCACIÓN GARANTIZADA',true);
insert into motor_interprete.cat_dependencia VALUES (105,'INSTITUTO DE PLANEACIÓN DEMOCRÁTICA Y PROSPECTIVA',true);
insert into motor_interprete.cat_dependencia VALUES (106,'CAJA DE PREVISIÓN PARA TRABAJADORES A LISTA DE RAYA',true);
insert into motor_interprete.cat_dependencia VALUES (107,'CAJA DE PREVISIÓN DE LA POLICÍA AUXILIAR',true);
insert into motor_interprete.cat_dependencia VALUES (108,'CAJA DE PREVISIÓN DE LA POLICÍA PREVENTIVA',true);
insert into motor_interprete.cat_dependencia VALUES (109,'CORPORACIÓN MEXICANA DE IMPRESIÓN, S.A. DE C.V.',true);
insert into motor_interprete.cat_dependencia VALUES (110,'SERVICIOS METROPOLITANOS, S.A. DE C.V.',true);

INSERT INTO motor_interprete.cat_validadores VALUES (1, 'TELEFONO', true);
INSERT INTO motor_interprete.cat_validadores VALUES (2, 'CORREO', true);
INSERT INTO motor_interprete.cat_validadores VALUES (3, 'CURP', true);
INSERT INTO motor_interprete.cat_validadores VALUES (4, 'URL', true);

INSERT INTO motor_interprete.cat_origen_llenado VALUES (1, 'Llave', true, true, false, true, 1);
INSERT INTO motor_interprete.cat_origen_llenado VALUES (2, 'Excel', true, true, true, true, 4);
INSERT INTO motor_interprete.cat_origen_llenado VALUES (3, 'Manual', true, false, true, true, 2);
INSERT INTO motor_interprete.cat_origen_llenado VALUES (4, 'Web service', true, false, true, true, 3);

INSERT INTO motor_interprete.cat_origen_token VALUES (1, 'Folio del sistema', true);
INSERT INTO motor_interprete.cat_origen_token VALUES (2, 'Fecha actual', true);
INSERT INTO motor_interprete.cat_origen_token VALUES (3, 'Componente', true);

INSERT INTO motor_interprete.cat_tamanio_archivos VALUES(1, '500 KB', true); 
INSERT INTO motor_interprete.cat_tamanio_archivos VALUES(2, '1 MB', true); 
INSERT INTO motor_interprete.cat_tamanio_archivos VALUES(3, '2 MB', true); 
INSERT INTO motor_interprete.cat_tamanio_archivos VALUES(4, '5 MB', true);


--Insert del usuario que hace referencia al schedule que se encargará de validar las vigencias de los tramites en prevención
INSERT INTO motor_interprete.usuario(id_usuario_llave_cdmx, nombre, primer_apellido, segundo_apellido, curp, telefono, correo)VALUES(-9, 'INTERPRETE MOTOR', 'VALIDACION', 'PREVENCION', 'SEIP220823XXXXXXXX', NULL, 'schedule.interprete.motor@cdmx.gob.mx');
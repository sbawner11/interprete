--Se agrega nueva tabla para registro de configuración de webhook
CREATE TABLE motor_interprete.configuracion_webhook (
  id_configuracion_webhook bigserial NOT NULL,
  id_proyecto int8 NOT NULL,
  usuario varchar(100) NOT NULL,
  contrasenia varchar(100) NOT NULL,
  url_aplicacion_notificaciones varchar(400) NOT NULL,  
  habilita_envio_notificaciones boolean NOT NULL DEFAULT false,  --Identifica si el proyecto estará integrado con VDNI
  fecha_creacion timestamp NOT NULL,
  fecha_ultima_actualizacion timestamp NOT NULL,
  activo boolean NOT NULL,
  seccion_sincronizada boolean NOT NULL,
  CONSTRAINT configuracion_webhook_pk PRIMARY KEY (id_configuracion_webhook),
  CONSTRAINT configuracion_webhook_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto)
);
CREATE INDEX configuracion_webhook_id_proyecto_idx ON motor_interprete.configuracion_webhook (id_proyecto);


--Se agrega nueva sección para configuración de webhook
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (24, 'Habilitar envío de notificaciones WEBHOOK');


--Se agrega nueva propiedad de configuración a la tabla proyecto
ALTER TABLE motor_interprete.proyecto ADD habilita_notificaciones_webhook bool DEFAULT false NOT NULL;

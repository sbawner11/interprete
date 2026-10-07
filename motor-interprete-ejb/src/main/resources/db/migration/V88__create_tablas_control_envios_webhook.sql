--CATALOGO PARA IDENTIFICAR EL TIPO DE MOVIMIENTO QUE TIENE UN TRAMITE
CREATE TABLE motor_interprete.cat_tipo_notificacion (
	id_tipo_notificacion int4 NOT NULL,
	descripcion varchar(100) NOT NULL,
	CONSTRAINT cat_tipo_notificacion_pk PRIMARY KEY (id_tipo_notificacion)
);

COMMENT ON TABLE motor_interprete.cat_tipo_notificacion IS 'Catálogo que define si es alta o modificación de estatus del trámite';

INSERT INTO motor_interprete.cat_tipo_notificacion (id_tipo_notificacion, descripcion) VALUES (1,'Registro de trámite');
INSERT INTO motor_interprete.cat_tipo_notificacion (id_tipo_notificacion, descripcion) VALUES (2,'Actualización de estatus de trámite');


--TABLA CONTROL PARA REGISTRO DE TRAMITES NOTIFICADOS CUANDO SE CUENTA CON LA CONFIGURACION WEBHOOK EN EL PROYECTO
CREATE TABLE motor_interprete.notificacion_movimiento_tramite (
	id_notificacion_movimiento bigserial NOT NULL,
	id_tramite int8 NOT NULL,
	id_tipo_notificacion int4 NOT NULL,
	fecha_notificacion timestamp NOT NULL,
	envio_confirmado bool DEFAULT false NOT NULL,
	CONSTRAINT notificacion_movimiento_tramite_pk PRIMARY KEY (id_notificacion_movimiento)
);
CREATE INDEX notificacion_mov_tramite_tramites_idx ON motor_interprete.notificacion_movimiento_tramite USING btree (id_tramite);
CREATE INDEX notificacion_mov_tramite_tipo_idx ON motor_interprete.notificacion_movimiento_tramite USING btree (id_tipo_notificacion);

COMMENT ON TABLE motor_interprete.notificacion_movimiento_tramite IS 'Tabla para control de envío de notificaciones de trámites cuando se encuentra habilitada la configuración WEBHOOK';
COMMENT ON COLUMN motor_interprete.notificacion_movimiento_tramite.fecha_notificacion IS 'Campo que registra la fecha cuando el movimiento del trámite es enviado y aceptado por la otra aplicación de manera correcta';
COMMENT ON COLUMN motor_interprete.notificacion_movimiento_tramite.envio_confirmado IS 'Campo que indica si el trámite ya se aceptó por la otra aplicación de manera correcta';

ALTER TABLE motor_interprete.notificacion_movimiento_tramite ADD CONSTRAINT notificacion_movimiento_tramites_fk FOREIGN KEY (id_tramite) REFERENCES motor_interprete.tramites(id_tramite);
ALTER TABLE motor_interprete.notificacion_movimiento_tramite ADD CONSTRAINT notificacion_movimiento_tramite_tipos_fk FOREIGN KEY (id_tipo_notificacion) REFERENCES motor_interprete.cat_tipo_notificacion(id_tipo_notificacion);

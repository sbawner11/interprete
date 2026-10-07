--INICIO Se agrega tabla para bítacora de asignación de usuarios revisores 12/09/2022

CREATE TABLE motor_interprete.bit_asignacion_revisor_tramite (
	id_bit_asignacion bigserial NOT NULL,
	id_tramite int8 NOT NULL,
	id_usuario_revisor int8 NOT NULL,
	id_usuario_asigna int8 NOT NULL,
	fecha_asignacion timestamp NOT NULL,
	CONSTRAINT bit_asignacion_revisor_tramite_pk PRIMARY KEY (id_bit_asignacion),
	CONSTRAINT bit_asignacion_revisor_tramite_fk FOREIGN KEY (id_usuario_revisor) REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx),
	CONSTRAINT bit_asignacion_revisor_tramite_fk_1 FOREIGN KEY (id_usuario_asigna) REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx)
);
CREATE INDEX bit_asignacion_revisor_tramite_id_tramite_idx ON motor_interprete.bit_asignacion_revisor_tramite (id_tramite);
CREATE INDEX bit_asignacion_revisor_tramite_id_usuario_asigna_idx ON motor_interprete.bit_asignacion_revisor_tramite (id_usuario_asigna);
CREATE INDEX bit_asignacion_revisor_tramite_id_usuario_revisor_idx ON motor_interprete.bit_asignacion_revisor_tramite (id_usuario_revisor);

--FIN Se agrega tabla para bítacora de asignación de usuarios revisores 12/09/2022


--INICIO Se agrega tabla de bitácora para movimientos de trámite 12/09/2022

CREATE TABLE motor_interprete.cat_tipos_movimiento (
	id_tipo_movimiento int4 NOT NULL,
	descripcion varchar(60) NOT NULL,
	CONSTRAINT cat_tipos_movimiento_pk PRIMARY KEY (id_tipo_movimiento)
);

INSERT INTO motor_interprete.cat_tipos_movimiento (id_tipo_movimiento,descripcion) VALUES (1,'Registro de trámite');
INSERT INTO motor_interprete.cat_tipos_movimiento (id_tipo_movimiento,descripcion) VALUES (2,'Registro de observaciones a sección.');
INSERT INTO motor_interprete.cat_tipos_movimiento (id_tipo_movimiento,descripcion) VALUES (3,'Actualización de estatus al trámite.');
INSERT INTO motor_interprete.cat_tipos_movimiento (id_tipo_movimiento,descripcion) VALUES (4,'Registro de información de componentes en sección.');
INSERT INTO motor_interprete.cat_tipos_movimiento (id_tipo_movimiento,descripcion) VALUES (5,'Actualización de información de componentes en sección.');

CREATE TABLE motor_interprete.bit_movimientos_tramite (
	id_movimiento_tramite bigserial NOT NULL,
	id_tramite bigserial NOT NULL,
	id_tipo_movimiento int4 NOT NULL,
	id_usuario_movimiento int4 NULL,
	comentarios text NULL,
	fecha_movimiento timestamp NOT NULL,
	CONSTRAINT bit_movimientos_tramite_pk PRIMARY KEY (id_movimiento_tramite),
	CONSTRAINT bit_movimientos_tramite_tipo_fk FOREIGN KEY (id_tipo_movimiento) REFERENCES motor_interprete.cat_tipos_movimiento(id_tipo_movimiento),
	CONSTRAINT bit_movimientos_tramite_usuario_fk FOREIGN KEY (id_usuario_movimiento) REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx)
);

CREATE INDEX bit_movimientos_id_tramite_idx ON motor_interprete.bit_movimientos_tramite USING btree (id_tramite);
CREATE INDEX bit_movimientos_id_tipo_movimiento_idx ON motor_interprete.bit_movimientos_tramite USING btree (id_tipo_movimiento);
CREATE INDEX bit_movimientos_id_usuario_idx ON motor_interprete.bit_movimientos_tramite USING btree (id_usuario_movimiento);

--FIN Se agrega tabla de bitácora para movimientos de trámite 12/09/2022

--Se agrega campo para registro de APIKEY en apartado de gestión de usuarios
ALTER TABLE motor_interprete.det_gestion_usuario ADD api_key varchar(60) NOT NULL DEFAULT 'api_key_test';
COMMENT ON COLUMN motor_interprete.det_gestion_usuario.api_key IS 'Columna para la configuración del API Key';
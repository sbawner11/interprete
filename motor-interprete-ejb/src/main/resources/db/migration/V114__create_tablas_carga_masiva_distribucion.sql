-- Catálogo de estatus de cargas masivas de distribución
CREATE TABLE motor_interprete.cat_estatus_carga_masiva (
	id_estatus_carga int4 NOT NULL,
	descripcion varchar(100) NOT NULL,
	CONSTRAINT cat_estatus_carga_masiva_pk PRIMARY KEY (id_estatus_carga)
);

INSERT INTO motor_interprete.cat_estatus_carga_masiva (id_estatus_carga, descripcion) VALUES (1, 'Carga correcta');
INSERT INTO motor_interprete.cat_estatus_carga_masiva (id_estatus_carga, descripcion) VALUES (2, 'Error en archivo');
INSERT INTO motor_interprete.cat_estatus_carga_masiva (id_estatus_carga, descripcion) VALUES (3, 'En proceso');
INSERT INTO motor_interprete.cat_estatus_carga_masiva (id_estatus_carga, descripcion) VALUES (4, 'Carga parcial');

-- Control de archivos cargados para distribución masiva
CREATE TABLE motor_interprete.det_archivos_carga_masiva (
	id_archivo_carga_masiva bigserial NOT NULL,
	fecha_carga timestamp NOT NULL,
	id_usuario_carga int8 NOT NULL,
	id_estatus_carga int4 NOT NULL,
	id_componente int8 NOT NULL,
	id_usuario_asignado int8 NOT NULL,
	nombre_archivo_origen varchar(255) NOT NULL,
	mensaje_error varchar(500) NULL, -- Para agregar detalle de error si falla la carga
	CONSTRAINT det_archivos_carga_masiva_pk PRIMARY KEY (id_archivo_carga_masiva),
	CONSTRAINT det_archivos_carga_masiva_fk_estatus FOREIGN KEY (id_estatus_carga)
		REFERENCES motor_interprete.cat_estatus_carga_masiva(id_estatus_carga),
	CONSTRAINT det_archivos_carga_masiva_fk_usuario_carga FOREIGN KEY (id_usuario_carga)
		REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx),
	CONSTRAINT det_archivos_carga_masiva_fk_usuario_asignado FOREIGN KEY (id_usuario_asignado)
		REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx),
	CONSTRAINT det_archivos_carga_masiva_fk_componente FOREIGN KEY (id_componente)
		REFERENCES motor_interprete.componente(id_componente)
);

CREATE INDEX det_archivos_carga_masiva_fecha_idx ON motor_interprete.det_archivos_carga_masiva (fecha_carga DESC);
CREATE INDEX det_archivos_carga_masiva_estatus_idx ON motor_interprete.det_archivos_carga_masiva (id_estatus_carga);
CREATE INDEX det_archivos_carga_masiva_componente_idx ON motor_interprete.det_archivos_carga_masiva (id_componente);

-- Detalle de cada registro procesado por archivo
CREATE TABLE motor_interprete.det_registros_carga_masiva (
	id_registro bigserial NOT NULL,
	id_archivo_carga_masiva int8 NOT NULL,
	id_elemento int8,
	descripcion_elemento varchar(200) NOT NULL,
	resultado varchar(255) NOT NULL,
	exitoso boolean NOT NULL DEFAULT false,
	CONSTRAINT det_registros_carga_masiva_pk PRIMARY KEY (id_registro),
	CONSTRAINT det_registros_carga_masiva_fk_archivo FOREIGN KEY (id_archivo_carga_masiva)
		REFERENCES motor_interprete.det_archivos_carga_masiva(id_archivo_carga_masiva)
);

CREATE INDEX det_registros_carga_masiva_archivo_idx ON motor_interprete.det_registros_carga_masiva (id_archivo_carga_masiva);
CREATE INDEX det_registros_carga_masiva_id_elemento_idx ON motor_interprete.det_registros_carga_masiva (id_elemento);

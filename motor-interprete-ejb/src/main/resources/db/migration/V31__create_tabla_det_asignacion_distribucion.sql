CREATE TABLE motor_interprete.det_asignacion_distribucion (
	id_asignacion_distribucion bigserial NOT NULL,
	id_elemento_asignado int8 NOT NULL,
	desc_elemento_asignado varchar(100) NOT NULL,
	rol varchar(100) NOT NULL,
	fecha_asignacion timestamp NOT NULL,
	fecha_desvinculacion timestamp NULL,
	activo boolean NOT NULL,
	id_componente int8 NOT NULL,
	id_usuario_asignado int8 NOT NULL,
	id_administrador_asigna int8 NOT NULL,
	CONSTRAINT det_asignacion_distribucion_pk PRIMARY KEY (id_asignacion_distribucion),
	CONSTRAINT det_asignacion_distribucion_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente),
	CONSTRAINT det_asignacion_distribucion_fk_1 FOREIGN KEY (id_usuario_asignado) REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx),
	CONSTRAINT det_asignacion_distribucion_fk_2 FOREIGN KEY (id_administrador_asigna) REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx)
);
CREATE INDEX det_asignacion_distribucion_activo_idx ON motor_interprete.det_asignacion_distribucion (activo);
CREATE INDEX det_asignacion_distribucion_id_elemento_componente_usuario_idx ON motor_interprete.det_asignacion_distribucion (id_elemento_asignado,id_componente,id_usuario_asignado);
CREATE INDEX det_asignacion_distribucion_id_componente_idx ON motor_interprete.det_asignacion_distribucion (id_componente);
CREATE INDEX det_asignacion_distribucion_id_usuario_asignado_idx ON motor_interprete.det_asignacion_distribucion (id_usuario_asignado);
CREATE INDEX det_asignacion_distribucion_id_administrador_asigna_idx ON motor_interprete.det_asignacion_distribucion (id_administrador_asigna);
CREATE INDEX det_asignacion_distribucion_id_elemento_asignado_idx ON motor_interprete.det_asignacion_distribucion (id_elemento_asignado);

-- Se crea la tabla detalle para almacenar la información necesaria para poder realizar la integración de los proyectos del motor con el expediente digital.
CREATE TABLE motor_interprete.det_integracion_expediente_digital (
	id_integracion_expediente bigserial NOT NULL,
	id_proyecto int8 NOT NULL,
	usuario varchar(100) NOT NULL,
	contrasenia varchar(100) NOT NULL,
	id_sistema_expediente int8 NOT NULL,
	id_funcionalidad_expediente int8 NOT NULL,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo boolean NOT NULL,
	seccion_sincronizada boolean NOT NULL,
	CONSTRAINT det_integracion_expediente_digital_pk PRIMARY KEY (id_integracion_expediente),
	CONSTRAINT det_integracion_expediente_digital_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto)
);
CREATE INDEX det_integracion_expediente_digital_id_proyecto_idx ON motor_interprete.det_integracion_expediente_digital (id_proyecto);

--Se agrega campo que servira como bandera para identificar si el proyecto estará integrado con expediente digital
ALTER TABLE motor_interprete.proyecto ADD habilita_integracion_expediente_digital boolean NOT NULL DEFAULT false;

--SE AGREGA NUEVA SECCION PARA DISTRIBUCION DE TRAMITES
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (20, 'Integración con Expediente Digital');



--SE AGREGA NUEVA TABLA PARA CONFIGURACION DE DISTRIBUCION DE TRAMITES

CREATE TABLE motor_interprete.det_distribucion (
	id_distribucion bigserial NOT NULL,
	id_proyecto int8 NOT NULL,
	id_componente int8 NOT NULL,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL, -- Bandera para eliminación lógica de valores
	seccion_sincronizada bool NOT NULL, -- Bandera que indica si la sección ha sido sincronizada al proyecto intérprete
		
	CONSTRAINT det_distribucion_pk PRIMARY KEY (id_distribucion)
);

COMMENT ON COLUMN motor_interprete.det_distribucion.id_componente IS 'Identificador del componente seleccionado para distribución de trámites';
COMMENT ON COLUMN motor_interprete.det_distribucion.activo IS 'Bandera para eliminación lógica de configuración de distribución de trámites';
COMMENT ON COLUMN motor_interprete.det_distribucion.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.det_distribucion ADD CONSTRAINT det_distribucion_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente);
ALTER TABLE motor_interprete.det_distribucion ADD CONSTRAINT det_distribucion_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

--SE AGREGA NUEVA SECCION PARA DISTRIBUCION DE TRAMITES
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (19, 'Distribución de trámites');

--SE AGREGA NUEVA BANDERA EN LA TABLA DE PROYECTO PARA EL SEMAFORO QUE INDICA SI EL APARTADO SE ENCUENTRA REGISTRADO
ALTER TABLE motor_interprete.proyecto ADD habilita_distribucion bool NOT NULL DEFAULT false;

--SE AGREGA NUEVA TABLA PARA PERSONALIZAR ESTATUS PAUSA

CREATE TABLE motor_interprete.det_pausa (
	id_detalle_pausa bigserial NOT NULL,
	id_proyecto int8 NOT NULL,	
	titulo_estatus varchar(100) NOT NULL,
	descripcion_estatus varchar(500) NOT NULL,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL, -- Bandera para eliminación lógica de detalle de pausa
	seccion_sincronizada bool NOT NULL, -- Bandera que indica si la sección ha sido sincronizada al proyecto intérprete
	CONSTRAINT detalle_pausa_pk PRIMARY KEY (id_detalle_pausa)
);

-- Column comments
COMMENT ON COLUMN motor_interprete.det_pausa.activo IS 'Bandera para eliminación lógica de detalle pausa';
COMMENT ON COLUMN motor_interprete.det_pausa.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';

ALTER TABLE motor_interprete.det_pausa ADD CONSTRAINT det_pausa_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

--SE AGREGA CAMPO HABILITA PAUSA COMO NUEVA BANDERA.
ALTER TABLE motor_interprete.proyecto ADD habilita_pausa bool NOT NULL DEFAULT false;

--SE AGREGA NUEVA SECCION 
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (21, 'Detalle pausa');

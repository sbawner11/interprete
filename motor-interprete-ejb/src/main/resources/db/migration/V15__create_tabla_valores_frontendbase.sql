--SE AGREGA NUEVA TABLA PARA REGISTRO DE VALORES PARA INTEGRACION DE FRONTENDBASE.

CREATE TABLE motor_interprete.det_valores_frontendbase (
	id_valor bigserial NOT NULL,
	id_proyecto int8 NOT NULL,

	url_favicon varchar(150) NOT NULL,
	url_header varchar(150) NOT NULL,
	url_footer varchar(150) NOT NULL,
	url_resources_header varchar(150) NOT NULL,
	url_resources_footer varchar(150) NOT NULL,

	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL, -- Bandera para eliminación lógica de valores
	seccion_sincronizada bool NOT NULL, -- Bandera que indica si la sección ha sido sincronizada al proyecto intérprete
	
	
	CONSTRAINT det_valores_frontendbase_pk PRIMARY KEY (id_valor)
);


COMMENT ON COLUMN motor_interprete.det_valores_frontendbase.activo IS 'Bandera para eliminación lógica de valores frontendbase';
COMMENT ON COLUMN motor_interprete.det_valores_frontendbase.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';


ALTER TABLE motor_interprete.det_valores_frontendbase ADD CONSTRAINT det_valores_frontendbase_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto);

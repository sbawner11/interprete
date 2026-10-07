--SE GREGA NUEVO CATALOGO PARA TIPO DE PLANTILLA UTILIZADA PARA ACEPTAR O RECHAZAR TRAMITE EN EL PROCESO DE FIRMADO

CREATE TABLE motor_interprete.cat_tipo_plantilla (
	 id_tipo_plantilla int4 NOT NULL,
	 descripcion varchar(100) NOT NULL,
	 activo bool NOT NULL,
	 CONSTRAINT cat_tipo_plantilla_pk PRIMARY KEY (id_tipo_plantilla)
);

--TIPOS DE PLANTILLAS PARA FIRMADO
INSERT INTO motor_interprete.cat_tipo_plantilla VALUES (1, 'Plantilla para registro aceptado', true );
INSERT INTO motor_interprete.cat_tipo_plantilla VALUES (2, 'Plantilla para registro rechazado', true );



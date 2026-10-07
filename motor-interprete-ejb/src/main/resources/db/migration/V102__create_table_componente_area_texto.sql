--Registro para componente área de texto
INSERT INTO motor_interprete.cat_tipo_componente (id_tipo_componente, descripcion, avanzado, activo)
VALUES (15, 'Área de texto', false, true);

--Tabla para manejar datos del componente Área de texto
CREATE TABLE motor_interprete.componente_area_texto (
	id_componente_area_texto bigserial NOT NULL,
	id_componente int8 NOT NULL,
	habilita_texto_interior bool NOT NULL,
	texto_interior varchar(100) NULL,
	id_origen_llenado int2 NULL,
	lineas_altura int4 NOT NULL,
	CONSTRAINT componente_area_texto_pk PRIMARY KEY (id_componente_area_texto),
	CONSTRAINT componente_area_texto_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente)
);


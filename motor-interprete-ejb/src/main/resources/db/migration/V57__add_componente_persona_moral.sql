--SE AGREGA NUEVO COMPONENTE PARA PERSONA MORAL
INSERT INTO motor_interprete.cat_tipo_componente (id_tipo_componente,descripcion,avanzado,activo) VALUES (14,'Datos persona Moral',true,true);


--SE GENERA ESTRUCTURA PARA NUEVO COMPONENTE DE PERSONA MORAL
CREATE TABLE motor_interprete.componente_datos_persona_moral (
	id_componente_datos_persona_moral bigserial NOT NULL,
	id_componente int8 NOT NULL,
	habilita_rfc bool NOT NULL,
	habilita_persona_moral bool NOT NULL,
	habilita_fecha_vigencia bool NOT NULL,
	CONSTRAINT componente_datos_persona_moral_pk PRIMARY KEY (id_componente_datos_persona_moral)
);

--SE AGREGA RELACION CON TABLA DE COMPONENTES
ALTER TABLE motor_interprete.componente_datos_persona_moral ADD CONSTRAINT componente_datos_persona_moral_fk FOREIGN KEY (id_componente)  REFERENCES motor_interprete.componente(id_componente);

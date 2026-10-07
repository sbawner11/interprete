--SE AGREGA NUEVA TABLA PARA REGISTRO DE PERSONA MORAL
CREATE TABLE motor_interprete.persona_moral (
  id_persona_moral bigserial NOT NULL,
  id_usuario_llave_cdmx int4 NOT NULL,
  rfc varchar(12) NOT NULL,
  razon_social varchar(300) NOT NULL,
  vigencia_certificado date NOT NULL,
  certificado_vigente bool DEFAULT true NOT NULL,
  CONSTRAINT persona_moral_pk PRIMARY KEY (id_persona_moral)
);
CREATE INDEX personamoral_idusuariollavecdmx_idx ON motor_interprete.persona_moral USING btree (id_usuario_llave_cdmx);
CREATE INDEX personamoral_rfc_idx ON motor_interprete.persona_moral USING btree (rfc);

--SE AGREGA RELACION CON TABLA USUARIO
ALTER TABLE motor_interprete.persona_moral ADD CONSTRAINT personamoral_idusuario_fk FOREIGN KEY (id_usuario_llave_cdmx) REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx);

--SE AGREGA COLUMNA DE PERSONA MORAL EN TABLA TRAMITES
ALTER TABLE motor_interprete.tramites ADD id_persona_moral int8 NULL;

--SE AGREGA REALACION CON LA TABLA DE PERSONA MORAL
ALTER TABLE motor_interprete.tramites ADD CONSTRAINT tramites_persona_moral_fk FOREIGN KEY (id_persona_moral) REFERENCES motor_interprete.persona_moral(id_persona_moral);
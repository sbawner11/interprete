--SE VERIFICA SI EXISTE TABLA DE TRAMITES, SI NO EXISTE SE CREA
DO
$do$
BEGIN
   IF NOT EXISTS (select from information_schema.tables t where t.table_schema='motor_interprete' and t.table_name='tramites') THEN
    --SE agregan los dos campos necesarios para el flujo de aviso.
	CREATE TABLE motor_interprete.tramites (
		id_tramite bigserial NOT NULL,
		folio_seguimiento varchar(25) NOT NULL,
		id_usuario_llave_cdmx int4 NULL,
		id_estatus_tramite int4 NOT NULL,
		id_usuario_operador int4 NULL,
		id_usuario_revisor int4 NULL,
		respuesta_folio_prevencion text NULL,
		respuesta_folio_conclusion text NULL,
		fecha_creacion timestamp NOT NULL,
		fecha_revision timestamp NULL,
		uuid varchar(36) NULL,
		ruta_documento_prevencion varchar(500) NULL,
		ruta_documento_revocado varchar(500) NULL,
		motivo_rechazo text NULL,
		CONSTRAINT tramites_pk PRIMARY KEY (id_tramite)
	);
	ALTER TABLE motor_interprete.tramites ADD CONSTRAINT tramites_estatus_fk FOREIGN KEY (id_estatus_tramite) REFERENCES motor_interprete.cat_estatus_tramite(id_estatus_tramite);
	ALTER TABLE motor_interprete.tramites ADD CONSTRAINT tramites_revisor_fk FOREIGN KEY (id_usuario_revisor) REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx);
	ALTER TABLE motor_interprete.tramites ADD CONSTRAINT tramites_usuario_fk FOREIGN KEY (id_usuario_llave_cdmx) REFERENCES motor_interprete.usuario(id_usuario_llave_cdmx);
	
	--AL CREAR LA TABLA TRAMITES SE DEBE CREAR LA LLAVE FORANEA EN LA TABLA BIT_ASIGNACION_REVISOR_TRAMITE
	ALTER TABLE motor_interprete.bit_asignacion_revisor_tramite ADD CONSTRAINT bit_asignacion_revisor_tramite_id_tramite_fk FOREIGN KEY (id_tramite) REFERENCES motor_interprete.tramites(id_tramite);

   END IF;
END
$do$
--SE VERIFICA, SI EXISTE TABLA DE TRAMITES, SE REALIZA ALTER ADD
DO
$do$
BEGIN
   IF EXISTS (select from information_schema.tables t where t.table_schema='motor_interprete' and t.table_name='tramites') THEN
    --SE agregan los dos campos necesarios para el flujo de aviso.
	ALTER TABLE motor_interprete.tramites ADD ruta_documento_revocado varchar(500) NULL;
	ALTER TABLE motor_interprete.tramites ADD motivo_rechazo text NULL;
   END IF;
END
$do$
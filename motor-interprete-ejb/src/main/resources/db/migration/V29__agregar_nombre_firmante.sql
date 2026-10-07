--SE VERIFICA, SI EXISTE TABLA DE TRAMITES CON FIRMA ELECTRONICA, SE AGREGA CAMPO DE NOMBRE DEL FIRMANTE
DO
$do$
BEGIN
   IF EXISTS (select from information_schema.tables t where t.table_schema='motor_interprete' and t.table_name='tramite_firma_electronica') THEN
      --SE AGREGA NUEVO CAMPO DE NOMBRE DEL FIRMANTE
      ALTER TABLE motor_interprete.tramite_firma_electronica ADD nombre_firmante text NULL;
   END IF;
END
$do$
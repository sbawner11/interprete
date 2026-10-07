--SE VERIFICA, SI EXISTE TABLA DE TRAMITES, SE REALIZA ALTER AL CAMPO DE FOLIO SEGUIMIENTO
DO
$do$
BEGIN
   IF EXISTS (select from information_schema.tables t where t.table_schema='motor_interprete' and t.table_name='tramites') THEN
      --SE INCREMENTA A LONGITUD 25 EL FOLIO SEGUIMIENTO DE LA TABLA TRAMITES 
	  ALTER TABLE motor_interprete.tramites ALTER COLUMN folio_seguimiento TYPE varchar(25) USING folio_seguimiento::varchar;
   END IF;
END
$do$
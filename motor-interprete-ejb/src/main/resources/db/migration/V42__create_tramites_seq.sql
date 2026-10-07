DO
$do$
BEGIN

	IF NOT EXISTS (select from information_schema."sequences" t where t.sequence_schema ='motor_interprete' and t.sequence_name ='tramites_seq') then 
	
		CREATE SEQUENCE 
		motor_interprete.tramites_seq
		INCREMENT BY 1
		MINVALUE 1
		MAXVALUE 99999999
		START 1;

   END IF;
  
  
  IF NOT EXISTS (select from pg_catalog.pg_indexes t where t.schemaname = 'motor_interprete' and t.tablename = 'tramites' and t.indexname = 'tramites_folio_seguimiento_idx')  then 
   
	   	CREATE INDEX tramites_folio_seguimiento_idx ON motor_interprete.tramites (folio_seguimiento);
		CREATE INDEX tramites_id_estatus_tramite_idx ON motor_interprete.tramites (id_estatus_tramite);
		CREATE INDEX tramites_id_usuario_llave_cdmx_idx ON motor_interprete.tramites (id_usuario_llave_cdmx);
		CREATE INDEX tramites_id_usuario_operador_idx ON motor_interprete.tramites (id_usuario_operador);
		CREATE INDEX tramites_id_usuario_revisor_idx ON motor_interprete.tramites (id_usuario_revisor);

   END IF;
END
$do$
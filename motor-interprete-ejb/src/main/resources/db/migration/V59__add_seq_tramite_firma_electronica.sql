--SE VALIDA SI EXISTE TABLA DE FIRMA DE ELECTRONICA DE TRAMITES PARA GENERAR SU SECUENCA
DO
$do$
BEGIN
	IF NOT EXISTS (
		SELECT FROM information_schema.sequences 
       	WHERE sequence_schema = 'motor_interprete' AND sequence_name = 'tramite_firma_electronica_seq'
	) THEN
		--SE CREA SECUENCIA PARA LA TABLA DE TRAMITE_FIRMA_ELECTRONICA
		CREATE SEQUENCE motor_interprete.tramite_firma_electronica_seq 
		INCREMENT BY 1
		MINVALUE 1
		MAXVALUE 99999999 
		START 1;
	
	END IF;   
	
END
$do$;
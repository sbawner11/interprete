--SE AGREGA COLUMNA PARA PERMITIR HABILITAR LA FIRMA DEL TRAMITE DEL CIUDADANO
ALTER TABLE motor_interprete.det_firma_digital ADD firma_ciudadano bool NOT NULL DEFAULT false;

--SE VALIDA SI EXISTE TABLA DE FIRMA DE ELECTRONICA DE TRAMITES PARA AGREGAR NUEVO CAMPO, EN CASO DE NO EXISTIR SE GENERA LA TABLA COMPLETA
DO
$do$
BEGIN
	IF EXISTS (
		SELECT FROM information_schema.tables 
       	WHERE table_schema = 'motor_interprete' AND table_name = 'tramite_firma_electronica'
	) THEN
		--SE AGREGA COLUMNA PARA PERMITIR DIFERENCIAR LA FIRMA DEL CIUDADANO DE LA FIRMA DEL FUNCIONARIO
		ALTER TABLE motor_interprete.tramite_firma_electronica ADD firma_ciudadano bool NOT NULL DEFAULT false;
	ELSE
	
		CREATE TABLE motor_interprete.tramite_firma_electronica (
        	id_tramite_firma bigserial NOT NULL,
         	cadena_original text NOT NULL,    
         	cadena_firmada text,
         	nombre_firmante text,
         	fecha_creacion timestamp(0) NOT NULL,
         	respuesta_servicio text NOT NULL,        
         	id_tramite int8 NOT NULL,
         	firma_ciudadano bool NOT NULL DEFAULT false,
         	CONSTRAINT tramite_firma_electronica_pk PRIMARY KEY (id_tramite_firma)
      	);
      	      	
      	CREATE INDEX tramite_firma_cadena_firmada_idx ON motor_interprete.tramite_firma_electronica USING btree (cadena_firmada);

		CREATE INDEX tramite_firma_id_tramite_idx ON motor_interprete.tramite_firma_electronica USING btree (id_tramite);

		CREATE INDEX tramite_firma_id_tramite_firma_idx ON motor_interprete.tramite_firma_electronica USING btree (id_tramite_firma);
				
		ALTER TABLE motor_interprete.tramite_firma_electronica ADD CONSTRAINT tramite_firma_tramites_fk FOREIGN KEY (id_tramite) REFERENCES motor_interprete.tramites(id_tramite); 
      	
	END IF;   
	
END
$do$;
DO
$do$
BEGIN

  IF EXISTS (SELECT 1 FROM information_schema.columns 
           WHERE table_schema = 'motor_interprete' 
           AND table_name = 'det_gestion_usuario' 
           AND column_name = 'habilita_descripcion_estatus') THEN
           
	    -- Eliminar las columnas relacionadas con habilitar la descripción de estatus
	    ALTER TABLE motor_interprete.det_gestion_usuario 
	    DROP COLUMN habilita_descripcion_estatus,
	    DROP COLUMN descripcion_estatus_revisado,
	    DROP COLUMN descripcion_estatus_aprobado;
    
	END IF;
	
END 
$do$
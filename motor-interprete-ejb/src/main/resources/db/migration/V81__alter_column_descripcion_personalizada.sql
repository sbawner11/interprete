 ALTER TABLE motor_interprete.cat_estatus_tramite 
   ALTER COLUMN descripcion_personalizada TYPE VARCHAR(20);

ALTER TABLE motor_interprete.configuracion_catalogo 
   ALTER COLUMN descripcion_usuario TYPE VARCHAR(20),
   ALTER COLUMN descripcion_usuario SET NOT NULL;
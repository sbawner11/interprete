--SE AGREGA NUEVO CAMPO PARA IDENTIFICAR SI EL TIPO DE PLANTILLA DE FIRMADO ES PARA UTILIZAR 
--EN EL RECHAZO O ACEPTACION DE TRAMITE

ALTER TABLE motor_interprete.archivos_respuesta_token ADD id_tipo_plantilla int4 NULL;

--SE CREA LA RELACION ENTRE LA TABLA ARCHIVOS RESPUETA TOKEN Y EL CATALOGO DE TIPO DE PLANTILLAS

ALTER TABLE motor_interprete.archivos_respuesta_token ADD CONSTRAINT archivos_respuesta_token_tipo_plantilla_fk FOREIGN KEY (id_tipo_plantilla) REFERENCES motor_interprete.cat_tipo_plantilla(id_tipo_plantilla);





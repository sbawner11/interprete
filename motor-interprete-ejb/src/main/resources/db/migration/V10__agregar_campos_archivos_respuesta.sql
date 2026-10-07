--SE AGREGAN NUEVOS CAMPOS PARA CONFIGURACION DE FIRMADO DIGITAL EN ARCHIVOS DE RESPUESTA.

ALTER TABLE motor_interprete.archivos_respuesta_token ADD habilita_firma bool NOT NULL DEFAULT false;
ALTER TABLE motor_interprete.archivos_respuesta_token ADD firma_supervisor bool NOT NULL DEFAULT false;
ALTER TABLE motor_interprete.archivos_respuesta_token ADD firma_operador bool NOT NULL DEFAULT false;
ALTER TABLE motor_interprete.archivos_respuesta_token ADD coodenada_qr_x int8 NULL;
ALTER TABLE motor_interprete.archivos_respuesta_token ADD coodenada_qr_y int8 NULL;


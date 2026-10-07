--Se eliminan campos que no seran utilizados para configuracion de firmado

ALTER TABLE motor_interprete.det_firma_digital DROP COLUMN ruta_archivo_cer;
ALTER TABLE motor_interprete.det_firma_digital DROP COLUMN ruta_archivo_key;
ALTER TABLE motor_interprete.det_firma_digital DROP COLUMN contrasenia;

--Se eliminan datos en caso de que existan registro capturados

DELETE from motor_interprete.det_firma_digital;
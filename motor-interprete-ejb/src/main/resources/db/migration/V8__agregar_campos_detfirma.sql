--Se agregan nuevos campos para realizar la configuracion del apartado de firmado

ALTER TABLE motor_interprete.det_firma_digital ADD clave_sistema varchar(60) NOT NULL;
ALTER TABLE motor_interprete.det_firma_digital ADD url_firmado varchar(500) NOT NULL;
ALTER TABLE motor_interprete.det_firma_digital ADD url_redirecciona varchar(500) NOT NULL;
ALTER TABLE motor_interprete.det_firma_digital ADD usuario_dominio_seg varchar(100) NOT NULL;
ALTER TABLE motor_interprete.det_firma_digital ADD contrasena_dominio_seg varchar(100) NOT NULL;
--SE ELIMINAN CAMPOS QUE YA SON CARGADOS DESDE FRONTEND BASE

ALTER TABLE motor_interprete.proyecto DROP COLUMN ruta_archivo_logotipo;
ALTER TABLE motor_interprete.proyecto DROP COLUMN ruta_archivo_favicon;

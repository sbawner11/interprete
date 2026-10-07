--ALTER TABLE LINEA CAPTURA
ALTER TABLE motor_interprete.linea_captura ADD solicitud_linea_captura varchar(18);

---ADD COMENTARIOS COLUMNAS
COMMENT ON COLUMN motor_interprete.linea_captura.ruta_documento_linea_captura IS 'ruta donde se guarda el documento de linea de captura';
COMMENT ON COLUMN motor_interprete.linea_captura.fecha_creacion IS 'Fecha de creacion del registro de linea de captura';
COMMENT ON COLUMN motor_interprete.linea_captura.id_estatus_linea_captura IS 'Id de estatus de la línea de captura';
COMMENT ON COLUMN motor_interprete.linea_captura.solicitud_linea_captura IS 'Numero de solicitud de línea de captura';
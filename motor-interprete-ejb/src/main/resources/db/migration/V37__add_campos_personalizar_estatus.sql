--SE AGREGAN CAMPOS ADICIONALES PARA PODER PERSONALIZAR DESCRIPCION INFORMATIVA DE ESTATUS REVISADO Y APROBADO PARA MOSTRAR
--EN BANDEJA DE TRAMITES DEL CIUDADANO

ALTER TABLE motor_interprete.det_gestion_usuario ADD habilita_descripcion_estatus bool NOT NULL DEFAULT false;
COMMENT ON COLUMN motor_interprete.det_gestion_usuario.habilita_descripcion_estatus IS 'Bandera que indica si se mostrara descripcion informativa de estatus en bandeja del ciudadano';
ALTER TABLE motor_interprete.det_gestion_usuario ADD descripcion_estatus_revisado varchar(200) NULL;
COMMENT ON COLUMN motor_interprete.det_gestion_usuario.descripcion_estatus_revisado IS 'Descripcion personalizada para estatus Revisado';
ALTER TABLE motor_interprete.det_gestion_usuario ADD descripcion_estatus_aprobado varchar(200) NULL;
COMMENT ON COLUMN motor_interprete.det_gestion_usuario.descripcion_estatus_aprobado IS 'Descripcion personalizada para estatus Aprobado';

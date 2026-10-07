--SE AGREGAN CAMPOS PARA LA VALIDACION DE ROLES PERMITIDOS PARA INGRESO A LOS TRÁMITES
ALTER TABLE motor_interprete.det_acceso_llave ADD valida_rol bool NOT NULL DEFAULT false;
COMMENT ON COLUMN motor_interprete.det_acceso_llave.valida_rol IS 'Valida si el trámite solo es para roles permitidos';
ALTER TABLE motor_interprete.det_acceso_llave ADD roles_permitidos varchar(250) NULL DEFAULT null;
COMMENT ON COLUMN motor_interprete.det_acceso_llave.roles_permitidos IS 'Lista de los roles permitidos';
--SE AGREGA CAMPO PARA LA VERIFICAR SI YA FUE SICRONIZADO
ALTER TABLE motor_interprete.configuracion_condiciones ADD seccion_sincronizada bool default false NOT NULL;
COMMENT ON COLUMN motor_interprete.configuracion_condiciones.seccion_sincronizada IS 'Bandera que indica si la sección ha sido sincronizada al proyecto intérprete';
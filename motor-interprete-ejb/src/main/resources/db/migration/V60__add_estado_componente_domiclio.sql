--SE AGREGAN CAMPOS PARA CONFIGURACION DE ESTADO EN COMPONENTE DATOS DE DOMICILIO
ALTER TABLE motor_interprete.componente_datos_domicilio ADD habilita_estado bool DEFAULT false NOT NULL;
ALTER TABLE motor_interprete.componente_datos_domicilio ADD estado_obligatorio bool DEFAULT false NOT NULL;
ALTER TABLE motor_interprete.componente_datos_domicilio ADD texto_interior_estado varchar(60) NULL;
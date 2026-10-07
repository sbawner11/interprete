--CAMPO SEXO PARA COMPONENTE DE DATOS PERSONALES CON LLAVE
ALTER TABLE motor_interprete.componente_datos_personales_llave ADD habilita_sexo bool DEFAULT false NOT NULL;
ALTER TABLE motor_interprete.usuario ADD sexo varchar(15);

--SE CREA NUEVA TABLA PARA PARAMETROS DE SISTEMA
CREATE TABLE motor_interprete.parametros_sistema (
	id_parametro int4 NOT NULL,
	valor int4 NOT NULL,
	descripcion varchar(100) NOT NULL,
	activo int2 NOT NULL,
	CONSTRAINT parametros_sistema_idparametro_pk PRIMARY KEY (id_parametro)
);

COMMENT ON COLUMN  motor_interprete.parametros_sistema.activo IS 'Bandera para eliminación lógica del parámetro. 1 Activo, 0 Inactivo.';

--SE AGREGA NUEVO PARAMETRO DE SISTEMA PARA RANGO PERMIDO DE DIAS PARA BUSQUEDA DE TRAMITES
INSERT INTO motor_interprete.parametros_sistema VALUES (1, 90, 'NUMERO DE DIAS PERMITODOS PARA BUSQUEDA DE TRAMITES EN BANDEJA DE VALIDACION DE TRAMITES.', 1);

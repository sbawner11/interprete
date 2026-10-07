--CATALOGO DE DEPENDENCIAS PARA LINEAS DE CAPTURA
CREATE TABLE motor_interprete.cat_dependencia_pago (
	id_dependencia_pago int4 NOT NULL,
	sigla varchar(15) NOT NULL,
	descripcion varchar(60) NOT NULL,
	activo bool NOT NULL,
	CONSTRAINT cat_dependencia_pago_pk PRIMARY KEY (id_dependencia_pago)
);
COMMENT ON TABLE motor_interprete.cat_dependencia_pago IS 'Catálogo de dependencia para líneas de captura';

--CATALOGO DE UNIDADES ADMINISTRATIVAS PARA LINEAS DE CAPTURA
CREATE TABLE motor_interprete.cat_unidad_administrativa_pago (
	id_unidad_administrativa_pago int4 GENERATED ALWAYS AS IDENTITY,
	id_dependencia_pago int4 not null, 
	descripcion varchar(60) NOT NULL,
	clave varchar(3) not null,
	activo bool NOT NULL,
	CONSTRAINT cat_unidad_administrativa_pago_pk PRIMARY KEY (id_unidad_administrativa_pago),
	CONSTRAINT cat_ua_dependencia_fk FOREIGN KEY (id_dependencia_pago) REFERENCES motor_interprete.cat_dependencia_pago(id_dependencia_pago)
);
COMMENT ON TABLE motor_interprete.cat_unidad_administrativa_pago IS 'Catálogo de unidades administrativas para líneas de captura';

--CATALOGO DE TIPOS DE VIGENCIA
CREATE TABLE motor_interprete.cat_tipo_vigencia (
	id_tipo_vigencia int4 GENERATED ALWAYS AS IDENTITY,
	clave varchar(1) NOT NULL,
	descripcion varchar(10) NOT NULL,
	CONSTRAINT cat_tipo_vigencia_pk PRIMARY KEY (id_tipo_vigencia)
);
COMMENT ON TABLE motor_interprete.cat_tipo_vigencia IS 'Catálogo de Tipos de vigencia';

INSERT INTO motor_interprete.cat_tipo_vigencia
	(clave,descripcion)
VALUES
	('D','Diario');

--CATALOGO DE PERIODICIDAD
CREATE TABLE motor_interprete.cat_periodicidad (
	id_periodicidad int4 GENERATED ALWAYS AS IDENTITY,
	descripcion varchar(60) NOT NULL,
	clave varchar(1) not null,
	CONSTRAINT cat_periodicidad_pk PRIMARY KEY (id_periodicidad)
);
COMMENT ON TABLE motor_interprete.cat_periodicidad IS 'Catálogo de Periodicidad';

INSERT INTO motor_interprete.cat_periodicidad (descripcion,clave)
VALUES
	('Mensual','M'),
	('Bimestral','B'),
	('Trimestral Definitiva','T'),
	('Cuatrimestral','C'),
	('Semestral','S'),
	('Anual','Y'),
	('Sin periodo','N');

--CATALOGO DE PERIODOS
CREATE TABLE motor_interprete.cat_periodo (
	id_periodo int4 GENERATED ALWAYS AS IDENTITY,
	id_periodicidad int4 not null,
	descripcion varchar(60) NOT NULL,
	clave varchar(3) not null,
	CONSTRAINT cat_periodo_pk PRIMARY KEY (id_periodo),
	CONSTRAINT cat_periodo_periodicidad_fk FOREIGN KEY (id_periodicidad) REFERENCES motor_interprete.cat_periodicidad(id_periodicidad)
);
COMMENT ON TABLE motor_interprete.cat_periodo IS 'Catálogo de Periodos';

INSERT INTO motor_interprete.cat_periodo (id_periodicidad,descripcion,clave)
VALUES 
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Enero','001'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Febrero','002'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Marzo','003'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Abril','004'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Mayo','005'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Junio','006'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Julio','007'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Agosto','008'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Septiembre','009'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Octubre','010'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Noviembre','011'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='M'),'Diciembre','012'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='B'),'1° Enero-Febrero','036'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='B'),'2° Marzo-Abril','037'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='B'),'3° Mayo-Junio','038'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='B'),'4° Julio-Agosto','039'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='B'),'5° Septiembre-Octubre','040'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='B'),'6° Noviembre-Diciembre','041'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='T'),'1° Enero-Marzo','013'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='T'),'2° Abril-Junio','014'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='T'),'3° Julio-Septiembre','015'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='T'),'4° Octubre-Diciembre','016'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='C'),'1° Enero-Abril','017'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='C'),'2° Mayo-Agosto','018'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='C'),'3° Septiembre-Diciembre','019'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='S'),'1° Enero-Junio','020'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='S'),'2° Julio-Diciembre','021'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='Y'),'Del Ejercicio','035'),
	((Select id_periodicidad from motor_interprete.cat_periodicidad where clave='N'),'Sin Periodo','099');

--CATALOGO DE TIPOS DE AGRUPADOR
CREATE TABLE motor_interprete.cat_tipo_agrupador (
	id_tipo_agrupador int4 GENERATED ALWAYS AS IDENTITY,
	tipo_agrupador varchar(1) NOT NULL,
	descripcion varchar(15) NOT NULL,
	CONSTRAINT cat_tipo_agrupador_pk PRIMARY KEY (id_tipo_agrupador)
);
COMMENT ON TABLE motor_interprete.cat_tipo_agrupador IS 'Catálogo de Tipo de agrupador';

INSERT INTO motor_interprete.cat_tipo_agrupador (tipo_agrupador,descripcion)
VALUES 
	('P','Primario'),
	('S','Secundario');

--CATALOGO DE EJERCICIOS CONTABLES
CREATE TABLE motor_interprete.cat_ejercicio (
	id_ejercicio int4 GENERATED ALWAYS AS IDENTITY,
	ejercicio int4 NOT NULL,
	activo bool NOT NULL,
	CONSTRAINT cat_ejercicio_pk PRIMARY KEY (id_ejercicio)
);
COMMENT ON TABLE motor_interprete.cat_ejercicio IS 'Catálogo de Ejercicios contables';

--CATALOGO DE TIPOS DE PERSONA
CREATE TABLE motor_interprete.cat_tipo_persona (
	id_tipo_persona int4 GENERATED ALWAYS AS IDENTITY,
	clave varchar(1) NOT NULL,
	descripcion varchar(10) NOT NULL,
	activo bool NOT NULL,
	CONSTRAINT cat_tipo_persona_pk PRIMARY KEY (id_tipo_persona)
); 
COMMENT ON TABLE motor_interprete.cat_tipo_persona IS 'Catálogo de Tipos de persona';

INSERT INTO motor_interprete.cat_tipo_persona (clave,descripcion, activo)
VALUES 
	('F','Física',true),
	('M','Moral',false);

--PARA ALMACENAR LA CABECERA DE LINEAS DE CAPTURA
CREATE TABLE motor_interprete.det_linea_captura (
	id_detalle_linea_captura BIGSERIAL NOT NULL,
	id_proyecto int8 not null,
	id_dependencia_pago int4 not null,
	id_unidad_administrativa_pago int4 not null,
	vigencia int not null,
	id_tipo_vigencia int4 not null,
	id_tipo_persona int4 not null,
	id_usuario int4 not null,
	fecha_creacion timestamp NOT NULL,
	fecha_actualizacion timestamp NOT NULL,
	completo bool NOT NULL DEFAULT false,
	activo bool NOT NULL DEFAULT true,
	seccion_sincronizada bool NOT NULL default false,
	CONSTRAINT det_linea_captura_pk PRIMARY KEY (id_detalle_linea_captura),
	CONSTRAINT det_linea_captura_proyecto_fk 
		FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto),
	CONSTRAINT det_linea_captura_dependencia_pago_fk 
		FOREIGN KEY (id_dependencia_pago) REFERENCES motor_interprete.cat_dependencia_pago(id_dependencia_pago),
	CONSTRAINT det_linea_captura_unidad_administrativa_pago_fk 
		FOREIGN KEY (id_unidad_administrativa_pago) REFERENCES motor_interprete.cat_unidad_administrativa_pago(id_unidad_administrativa_pago),
	CONSTRAINT det_linea_captura_tipo_vigencia_fk 
		FOREIGN KEY (id_tipo_vigencia) REFERENCES motor_interprete.cat_tipo_vigencia(id_tipo_vigencia),
	CONSTRAINT det_linea_captura_tipo_persona_fk 
		FOREIGN KEY (id_tipo_persona) REFERENCES motor_interprete.cat_tipo_persona(id_tipo_persona)
);

-- PARA REGISTRAR LOS TRAMITES DE UNA LINEA DE CAPTURA
CREATE TABLE motor_interprete.det_tramites_linea_captura (
	id_tramite_linea_captura BIGSERIAL not null,
	id_detalle_linea_captura INT8 not null,
	homoclave varchar(30) not null,
	variante varchar(3) not null,
	descripcion varchar(350) not null,
	importe NUMERIC(14,0) not null,
	numero_conceptos int4 not null,
	id_usuario_registro int4 not null,
	fecha_creacion timestamp NOT NULL,
	fecha_actualizacion timestamp NOT NULL,
	activo bool NOT NULL DEFAULT true,
	seccion_sincronizada bool NOT NULL default false,
	CONSTRAINT det_tramites_linea_captura_pk PRIMARY KEY (id_tramite_linea_captura),
	CONSTRAINT det_tramites_linea_captura_fk FOREIGN KEY (id_detalle_linea_captura) REFERENCES motor_interprete.det_linea_captura(id_detalle_linea_captura)
);

-- PARA ALMACENAR LOS CONCEPTOS DE UN TRAMITE DE LINEA DE CAPTURA
CREATE TABLE motor_interprete.det_conceptos_tramite (
	id_concepto_tramite BIGSERIAL not null,
	id_tramite_linea_captura int8 not null,
	secuencia int4 not null,
	clave int4 not null,
	agrupador int4 not null,
	id_tipo_agrupador int4 NOT NULL,
	id_periodo int4 not null,
	id_periodicidad int4 not null,
	id_ejercicio int4 null,
	clave_contable int4 not null,
	importe NUMERIC(14,0) not null,
	id_usuario_registro int4 not null,
	fecha_creacion timestamp NOT NULL,
	fecha_actualizacion timestamp NOT NULL,
	activo bool NOT NULL DEFAULT true,
	seccion_sincronizada bool NOT NULL default false,
	CONSTRAINT det_conceptos_tramite_pk PRIMARY KEY (id_concepto_tramite),
	CONSTRAINT det_conceptos_tramite_lc_fk 
		FOREIGN KEY (id_tramite_linea_captura) REFERENCES motor_interprete.det_tramites_linea_captura(id_tramite_linea_captura),
	CONSTRAINT det_conceptos_tipo_agrupador_fk 
		FOREIGN KEY (id_tipo_agrupador) REFERENCES motor_interprete.cat_tipo_agrupador(id_tipo_agrupador),
	CONSTRAINT det_conceptos_periodo_fk 
		FOREIGN KEY (id_periodo) REFERENCES motor_interprete.cat_periodo(id_periodo),
	CONSTRAINT det_conceptos_periodicidad_fk 
		FOREIGN KEY (id_periodicidad) REFERENCES motor_interprete.cat_periodicidad(id_periodicidad),
	CONSTRAINT det_conceptos_ejercicio_fk 
		FOREIGN KEY (id_ejercicio) REFERENCES motor_interprete.cat_ejercicio(id_ejercicio)
);

-- PARA ALMACENAR LAS TRANSACCIONES DE UN CONCEPTO DE LINEA DE CAPTURA
CREATE TABLE motor_interprete.det_transaccion_concepto (
	id_transaccion_concepto BIGSERIAL NOT NULL, 
	id_concepto_tramite INT8 not null, 
	clave int4 not null,
	valor NUMERIC(14,0) not null,
	actualizacion bool not null default false,
	recargo bool not null default false,
	multa bool not null default false,
	id_usuario_registro int4 not null,
	fecha_creacion timestamp NOT NULL,
	fecha_actualizacion timestamp NOT NULL,
	activo bool NOT NULL default true,
	seccion_sincronizada bool NOT NULL default false,
	CONSTRAINT det_transaccion_concepto_pk PRIMARY KEY (id_transaccion_concepto),
	CONSTRAINT det_transaccion_concepto_tramite_fk FOREIGN KEY (id_concepto_tramite) REFERENCES motor_interprete.det_conceptos_tramite(id_concepto_tramite)
);

-- PARA ALMACENAR LAS CREDENCIALES PARA PUENTE
CREATE TABLE motor_interprete.det_security_domain_lineas_captura(
	id_security_domain_lc bigserial NOT NULL,
	id_detalle_linea_captura int8 NOT NULL,
	usuario varchar(100) NOT NULL,
	contrasenia varchar(100) NOT NULL,
	url_sistema varchar(400) NOT NULL,
	id_usuario_registro int4 not null,
	fecha_creacion timestamp NOT NULL,
	fecha_ultima_actualizacion timestamp NOT NULL,
	activo bool NOT NULL default true,
	seccion_sincronizada bool not null default false,
	CONSTRAINT det_security_domain_lineas_captura_pk PRIMARY KEY (id_security_domain_lc),
	CONSTRAINT det_security_domain_lineas_captura_fk FOREIGN KEY (id_detalle_linea_captura) REFERENCES motor_interprete.det_linea_captura(id_detalle_linea_captura)
);

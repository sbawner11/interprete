--CREAR NUEVO CATALOGO DE TIPOS DE SEGURIDAD DE DOMINIO--

CREATE TABLE motor_interprete.cat_tipo_security_domain (
	id_tipo_security_domain int2 NOT NULL,
	descripcion varchar(20) NOT NULL,
	CONSTRAINT cat_tipo_security_domain_pk PRIMARY KEY (id_tipo_security_domain)
);

--AGREGAR LOS DOS TIPOS DE SEGURIDAD DE DOMINIO--
INSERT INTO motor_interprete.cat_tipo_security_domain (id_tipo_security_domain,descripcion) VALUES (1,'Servicios cliente');
INSERT INTO motor_interprete.cat_tipo_security_domain (id_tipo_security_domain,descripcion) VALUES (2,'Servicio CURP');

--AGREGAR AL DETALLE DE SEGURIDAD DE DOMINIO EL NUEVO CAMPO DEL TIPO DE SEGURIDAD--
ALTER TABLE motor_interprete.det_security_domain ADD id_tipo_security_domain int2 NOT NULL DEFAULT 1;

--AGREGAR RELACION DEL DETALLE AL NUEVO CATALOGO--
ALTER TABLE motor_interprete.det_security_domain ADD CONSTRAINT detalle_security_domain_tipo_sd_fk FOREIGN KEY (id_tipo_security_domain) REFERENCES motor_interprete.cat_tipo_security_domain(id_tipo_security_domain);


--AGREGAR BANDERA DE NUEVA SECCION EN DETALLE DEL MOTOR--
ALTER TABLE motor_interprete.proyecto ADD habilita_security_domain_curp bool NOT NULL DEFAULT false;

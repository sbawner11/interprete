--SE AGREGA NUEVO COMPONENTE PARA TABLA

INSERT INTO motor_interprete.cat_tipo_componente (id_tipo_componente,descripcion,avanzado,activo) VALUES (16,'Tabla',true,true);

--GENERA COMPONENTE TABLA
CREATE TABLE motor_interprete.componente_tabla (
  id_componente_tabla bigint not null,
  id_componente bigint NOT NULL,
  permite_agregar_filas bool NOT NULL,
  tamanio_pagina int4 NOT NULL,
  minimo_filas int4 NOT NULL,
  maximo_filas int4 NOT NULL,
  CONSTRAINT componente_tabla_pk PRIMARY KEY (id_componente_tabla),
  CONSTRAINT componente_tabla_componente_fk FOREIGN KEY (id_componente) REFERENCES motor_interprete.componente(id_componente)
);

CREATE TABLE motor_interprete.cat_tipo_campo (
  id_cat_tipo_campo int4 NOT NULL,
  descripcion varchar(300) DEFAULT NULL,
  activo bool NOT NULL,
  CONSTRAINT cat_tipo_campo_pk PRIMARY KEY (id_cat_tipo_campo)
);

INSERT INTO motor_interprete.cat_tipo_campo (id_cat_tipo_campo, descripcion, activo) VALUES(1, 'Numérico', true);
INSERT INTO motor_interprete.cat_tipo_campo (id_cat_tipo_campo, descripcion, activo) VALUES(2, 'Alfanumérico', true);
INSERT INTO motor_interprete.cat_tipo_campo (id_cat_tipo_campo, descripcion, activo) VALUES(3, 'Fecha', true);


CREATE TABLE motor_interprete.det_elementos_tabla (
  id_elemento_tabla bigint not null,
  titulo_header varchar(300) DEFAULT NULL,
  id_componente_tabla bigint NOT NULL,
  id_cat_tipo_campo int4 NOT NULL,
  requerido bool NOT NULL,
  tooltip varchar(200) DEFAULT NULL	,
  longitu_celda int4 DEFAULT NULL,
  orden_columna int4 NOT NULL,
  activo bool NOT NULL,
  CONSTRAINT columna_tabla_pk PRIMARY KEY (id_elemento_tabla),
  CONSTRAINT componente_tabla_celda_fk FOREIGN KEY (id_componente_tabla) REFERENCES motor_interprete.componente_tabla(id_componente_tabla),
  CONSTRAINT columna_tabla_tipo_celda_fk FOREIGN KEY (id_cat_tipo_campo) REFERENCES motor_interprete.cat_tipo_campo(id_cat_tipo_campo)
);

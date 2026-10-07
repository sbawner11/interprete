--SE CREA TABLA PARA CATALOGO DE ESTATUS DE LINEAS DE CAPTURA
CREATE TABLE motor_interprete.cat_estatus_linea_captura (
    id_estatus_linea_captura int4 NOT NULL,
    descripcion varchar(60) NOT NULL,
    CONSTRAINT cat_estatus_linea_captura_pk PRIMARY KEY (id_estatus_linea_captura)
);

--INSERT DE ESTATUS DE LINEAS DE CAPTURA
INSERT INTO motor_interprete.cat_estatus_linea_captura (id_estatus_linea_captura, descripcion) VALUES (1,'Pendiente');
INSERT INTO motor_interprete.cat_estatus_linea_captura (id_estatus_linea_captura, descripcion) VALUES (2,'Pagada');
INSERT INTO motor_interprete.cat_estatus_linea_captura (id_estatus_linea_captura, descripcion) VALUES (3,'Vencida');
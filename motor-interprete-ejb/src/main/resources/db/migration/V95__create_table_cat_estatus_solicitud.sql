--SE CREA TABLA PARA CATALOGO DE ESTATUS DE LA SOLICITUD DE LINEAS DE CAPTURA
CREATE TABLE motor_interprete.cat_estatus_solicitud (
    id_estatus_solicitud int4 NOT NULL,
    descripcion varchar(60) NOT NULL,
    CONSTRAINT cat_estatus_solicitud_pk PRIMARY KEY (id_estatus_solicitud)
);

--INSERT DE ESTATUS DE SOLICITUD DE LINEAS DE CAPTURA
INSERT INTO motor_interprete.cat_estatus_solicitud (id_estatus_solicitud, descripcion) VALUES (1,'Solicitud procesada anteriormente');
INSERT INTO motor_interprete.cat_estatus_solicitud (id_estatus_solicitud, descripcion) VALUES (2,'Error Solicitud');
INSERT INTO motor_interprete.cat_estatus_solicitud (id_estatus_solicitud, descripcion) VALUES (3,'Solicitud Exitosa');
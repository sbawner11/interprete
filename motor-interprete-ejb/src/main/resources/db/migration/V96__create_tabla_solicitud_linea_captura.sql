--SE CREA TABLA PARA EL REGISTRO DE SOLICITUDES DE LINEA DE CAPTURA
CREATE TABLE motor_interprete.solicitud_linea_captura (
    id_solicitud_linea_captura bigserial NOT NULL,
    id_tramite int8 NOT NULL,
    solicitud_linea_captura varchar(18),
    request_servicio_lc text NOT NULL,
    respuesta_servicio_lc text NOT NULL,
    fecha_creacion timestamp NOT NULL,
    id_estatus_solicitud int2 NOT NULL,
    CONSTRAINT solicitud_linea_captura_un UNIQUE (id_solicitud_linea_captura),
    CONSTRAINT solicitud_linea_captura_pk PRIMARY KEY (id_solicitud_linea_captura)
);

--CREACION DE INDICES
CREATE INDEX solicitud_linea_captura_id_estatus_solicitud_idx ON motor_interprete.solicitud_linea_captura (id_estatus_solicitud,fecha_creacion);
CREATE INDEX solicitud_linea_captura_id_tramite_idx ON motor_interprete.solicitud_linea_captura (id_tramite);


--SE AGREGA RELACION CON TABLA TRAMITES
ALTER TABLE motor_interprete.solicitud_linea_captura ADD CONSTRAINT solicitud_linea_captura_tramite_fk FOREIGN KEY (id_tramite) REFERENCES motor_interprete.tramites(id_tramite);
ALTER TABLE motor_interprete.solicitud_linea_captura ADD CONSTRAINT solicitud_linea_captura_estatus_linea_captura_fk FOREIGN KEY (id_estatus_solicitud) REFERENCES motor_interprete.cat_estatus_solicitud(id_estatus_solicitud);


--COMENTARIOS DE LAS COLUMNAS
COMMENT ON COLUMN motor_interprete.solicitud_linea_captura.id_tramite IS 'Id del trámite que solicita una Linea de captura';
COMMENT ON COLUMN motor_interprete.solicitud_linea_captura.solicitud_linea_captura IS 'Numero de solicitud de linea de captura';
COMMENT ON COLUMN motor_interprete.solicitud_linea_captura.request_servicio_lc IS 'Request de la solicitud Json del servicio del SAT del estatus de LC';
COMMENT ON COLUMN motor_interprete.solicitud_linea_captura.respuesta_servicio_lc IS 'Respuesta Json del servicio del SAT del estatus de LC';
COMMENT ON COLUMN motor_interprete.solicitud_linea_captura.fecha_creacion IS 'Fecha en la que se realiza la solicitud';
COMMENT ON COLUMN motor_interprete.solicitud_linea_captura.id_estatus_solicitud IS 'Id de estatus de la solicitud';

--SE CREA TABLA PARA REGISTRO DE LINEAS DE CAPTURA
CREATE TABLE motor_interprete.linea_captura (
    id_linea_captura bigserial NOT NULL,
    id_tramite int8 NOT NULL,
    respuesta_servicio_lc text NOT NULL,
    respuesta_servicio_estatus text NULL,
    linea_captura varchar(100) NULL,
    fecha_vigencia date NULL,
    monto numeric(10,0) NULL,
    fecha_pago_lc timestamp NULL,
    fecha_creacion timestamp NOT NULL,
    id_estatus_linea_captura int2 NOT NULL, 
    CONSTRAINT linea_captura_un UNIQUE (linea_captura),
    CONSTRAINT linea_captura_pk PRIMARY KEY (id_linea_captura)
);

--CREACION DE INDICES
CREATE INDEX linea_captura_linea_captura_idx ON motor_interprete.linea_captura (linea_captura);
CREATE INDEX linea_captura_id_estatus_linea_captura_idx ON motor_interprete.linea_captura (id_estatus_linea_captura,fecha_vigencia);
CREATE INDEX linea_captura_id_tramite_idx ON motor_interprete.linea_captura (id_tramite);

--COMENTARIOS DE LAS COLUMNAS
COMMENT ON COLUMN motor_interprete.linea_captura.id_tramite IS 'Id del trámite que solicita una Linea de captura';
COMMENT ON COLUMN motor_interprete.linea_captura.respuesta_servicio_lc IS 'Respuesta Json del servicio del SAT para la generación de LC';
COMMENT ON COLUMN motor_interprete.linea_captura.respuesta_servicio_estatus IS 'Respuesta Json del servicio del SAT del estatus de LC';
COMMENT ON COLUMN motor_interprete.linea_captura.linea_captura IS 'Linea de captura que se obtiene de la respueta del servicio cuando la petición es exitosa';
COMMENT ON COLUMN motor_interprete.linea_captura.fecha_pago_lc IS 'Fecha que registra SAT del momento de pago de la LC';
COMMENT ON COLUMN motor_interprete.linea_captura.fecha_vigencia IS 'Fecha de vigencia que se obtiene de la respueta del servicio cuando la petición es exitosa';
COMMENT ON COLUMN motor_interprete.linea_captura.monto IS 'Monto que se obtiene de la respueta del servicio cuando la petición es exitosa';


--SE AGREGA RELACION CON TABLA TRAMITES
ALTER TABLE motor_interprete.linea_captura ADD CONSTRAINT linea_captura_tramite_fk FOREIGN KEY (id_tramite) REFERENCES motor_interprete.tramites(id_tramite);
ALTER TABLE motor_interprete.linea_captura ADD CONSTRAINT linea_captura_estatus_linea_captura_fk FOREIGN KEY (id_estatus_linea_captura) REFERENCES motor_interprete.cat_estatus_linea_captura(id_estatus_linea_captura);

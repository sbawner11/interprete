CREATE TABLE IF NOT EXISTS motor_interprete.datos_tabla_dinamica (
    id_datos_tabla BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_tramite BIGINT NOT NULL,
    id_componente BIGINT NOT NULL,
    numero_fila INTEGER NOT NULL,
    tipo_fila VARCHAR(20) NOT NULL DEFAULT 'BASE',
    datos JSONB NOT NULL DEFAULT '{}',
    fecha_creacion TIMESTAMP DEFAULT NOW(),
    fecha_modificacion TIMESTAMP DEFAULT NOW(),
    eliminado BOOLEAN DEFAULT FALSE
);

ALTER TABLE motor_interprete.datos_tabla_dinamica
ADD CONSTRAINT fk_datos_tabla_tramite
FOREIGN KEY (id_tramite)
REFERENCES motor_interprete.tramites(id_tramite)
ON DELETE CASCADE; 


ALTER TABLE motor_interprete.datos_tabla_dinamica
ADD CONSTRAINT fk_datos_tabla_componente
FOREIGN KEY (id_componente)
REFERENCES motor_interprete.componente(id_componente)
ON DELETE RESTRICT;


CREATE INDEX IF NOT EXISTS idx_datos_tabla_tramite ON motor_interprete.datos_tabla_dinamica(id_tramite);
CREATE INDEX IF NOT EXISTS idx_datos_tabla_componente ON motor_interprete.datos_tabla_dinamica(id_componente);
CREATE INDEX IF NOT EXISTS idx_datos_tabla_no_eliminados ON motor_interprete.datos_tabla_dinamica(id_tramite, id_componente) WHERE eliminado = false;
CREATE INDEX IF NOT EXISTS idx_datos_tabla_jsonb ON motor_interprete.datos_tabla_dinamica USING GIN (datos);
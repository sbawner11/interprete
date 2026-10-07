--SE AGREGA NUEVA SECCION PARA EL REGISTRO DE CONFIGURACION DE CONDICIONES
INSERT INTO motor_interprete.cat_secciones_proyecto VALUES (23, 'Notificaciones');

--CATALOGO DE LOS DIAS DE LA SEMANA
CREATE TABLE motor_interprete.cat_dias_semana (
	id_dia_semana int4 NOT NULL,
	descripcion varchar(60) NOT NULL,
	activo bool NOT NULL,
	CONSTRAINT cat_dias_semana_pk PRIMARY KEY (id_dia_semana)
);

COMMENT ON TABLE motor_interprete.cat_dias_semana IS 'Catálogo de días de la semana';

--Elementos para nuevo catálogo.
INSERT INTO motor_interprete.cat_dias_semana (id_dia_semana, descripcion, activo) VALUES (1, 'Lunes', true);
INSERT INTO motor_interprete.cat_dias_semana (id_dia_semana, descripcion, activo) VALUES (2, 'Martes', true);
INSERT INTO motor_interprete.cat_dias_semana (id_dia_semana, descripcion, activo) VALUES (3, 'Miércoles', true);
INSERT INTO motor_interprete.cat_dias_semana (id_dia_semana, descripcion, activo) VALUES (4, 'Jueves', true);
INSERT INTO motor_interprete.cat_dias_semana (id_dia_semana, descripcion, activo) VALUES (5, 'Viernes', true);
INSERT INTO motor_interprete.cat_dias_semana (id_dia_semana, descripcion, activo) VALUES (6, 'Sábado', true);
INSERT INTO motor_interprete.cat_dias_semana (id_dia_semana, descripcion, activo) VALUES (7, 'Domingo', true);


--TABLA PARA LA CONFIGURACION DE LAS NOTIFICACIONES
CREATE TABLE motor_interprete.notificaciones (
    id_notificacion bigserial NOT NULL,
    id_proyecto int4 NOT NULL,
    envio_notificaciones bool NOT NULL,
    correos_notificacion VARCHAR(300) NULL,
    id_dia_semana int4 NULL,
    id_usuario int4 NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_actualizacion TIMESTAMP NOT NULL,
    activo bool  NOT NULL,
    seccion_sincronizada bool NOT NULL,
    CONSTRAINT notificaciones_pk PRIMARY KEY (id_notificacion),
    CONSTRAINT notificaciones_proyecto_fk FOREIGN KEY (id_proyecto) REFERENCES motor_interprete.proyecto(id_proyecto),
    CONSTRAINT notificaciones_catalogo_fk FOREIGN KEY (id_dia_semana) REFERENCES motor_interprete.cat_dias_semana(id_dia_semana)
);

COMMENT ON TABLE motor_interprete.notificaciones IS 'Configuración de proyectos que enviarán notificaciones';
COMMENT ON COLUMN motor_interprete.notificaciones.envio_notificaciones IS 'Bandera que indica si el proyecto envía notificaciones';
COMMENT ON COLUMN motor_interprete.notificaciones.correos_notificacion IS 'Cuentas de correos para notificaciones separadas por comas';
COMMENT ON COLUMN motor_interprete.notificaciones.id_dia_semana IS 'Id del día de la semana para enviar las notificaciones';
COMMENT ON COLUMN motor_interprete.notificaciones.id_usuario IS 'Id del usuario que realiza la configuración';

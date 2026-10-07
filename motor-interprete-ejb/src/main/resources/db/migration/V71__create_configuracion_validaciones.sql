--TABLA PARA MANEJO DE LAS CONDICIONES PARA OCULTAR UNA SECCION DETERMINADA DEL FORMULARIO
CREATE TABLE motor_interprete.configuracion_condiciones (
    id_configuracion bigserial NOT NULL,
    id_seccion_condicionada int8 NOT NULL,
    id_seccion_condicion int8 NOT NULL,
    id_componente_condicion int8 NOT NULL,
    id_operador_condicion int4 NOT NULL,
    activo BOOLEAN NOT NULL, 
    id_usuario_registro INT NOT NULL,
    fecha_creacion TIMESTAMP NOT NULL,
    fecha_ultima_actualizacion TIMESTAMP NOT NULL,    
    CONSTRAINT configuracion_condiciones_pk PRIMARY KEY (id_configuracion),
    CONSTRAINT fk_operador_condicion FOREIGN KEY (id_operador_condicion) REFERENCES motor_interprete.cat_operador (id_operador),
    CONSTRAINT fk_seccion_condicionada FOREIGN KEY (id_seccion_condicionada) REFERENCES motor_interprete.secciones_formulario (id_seccion_formulario),
    CONSTRAINT fk_seccion_condicion FOREIGN KEY (id_seccion_condicion) REFERENCES motor_interprete.secciones_formulario (id_seccion_formulario),
    CONSTRAINT fk_componente_condicion FOREIGN KEY (id_componente_condicion) REFERENCES motor_interprete.componente (id_componente),
	CONSTRAINT fk_usuario_registro FOREIGN KEY (id_usuario_registro) REFERENCES motor_interprete.usuario (id_usuario_llave_cdmx)
);

COMMENT ON COLUMN motor_interprete.configuracion_condiciones.id_seccion_condicionada IS 'Sección del formulario a la que se le aplicará la condición';
COMMENT ON COLUMN motor_interprete.configuracion_condiciones.id_seccion_condicion IS 'Sección del formulario necesaria para la condición';
COMMENT ON COLUMN motor_interprete.configuracion_condiciones.id_componente_condicion IS 'Componente a validar';

CREATE TABLE motor_interprete.configuracion_condicion_valor (
	id_condicion_valor BIGSERIAL NOT NULL,
    id_configuracion int8 NOT NULL,
    valor int4 NOT NULL,
	CONSTRAINT configuracion_valor_pk PRIMARY KEY (id_condicion_valor),
    CONSTRAINT fk_conf_condicion FOREIGN KEY (id_configuracion) REFERENCES motor_interprete.configuracion_condiciones (id_configuracion)
);

--TABLA PARA MANEJO DE LOS VALORES A UTILIZAR EN configuracion_condiciones
COMMENT ON COLUMN motor_interprete.configuracion_condicion_valor.id_configuracion IS 'Id de la configuración padre';
COMMENT ON COLUMN motor_interprete.configuracion_condicion_valor.valor IS 'Valor numérico a validar';
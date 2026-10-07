--SE CREA TABLA PARA MANEJO DE CATALOGO DE OPERADORES PARA CONDICIONALES
CREATE TABLE motor_interprete.cat_operador (
    id_operador int4 NOT NULL,
    valor_operador varchar(10) NOT NULL,
    descripcion varchar(50) NOT NULL,
    activo bool NOT NULL,
    CONSTRAINT cat_operador_pk PRIMARY KEY (id_operador)
);

COMMENT ON TABLE motor_interprete.cat_operador IS 'Catálogo de operadores lógicos/comparativos para condiciones';
COMMENT ON COLUMN motor_interprete.cat_operador.valor_operador IS 'Símbolo/código del operador';
COMMENT ON COLUMN motor_interprete.cat_operador.descripcion IS 'Uso condicional del operador';

--SE AGREGAN LOS OPERADORES A UTLIZAR EN LAS CONDICIONALES
INSERT INTO motor_interprete.cat_operador (id_operador, valor_operador, descripcion, activo) 
VALUES 
(1, '=',     'IGUAL', true),
(2, '!=',    'DIFERENTE', true),
(3, '>',     'MAYOR QUE', true),
(4, '<',     'MENOR QUE', true),
(5, '>=',    'MAYOR O IGUAL QUE', true),
(6, '<=',    'MENOR O IGUAL QUE', true),
(7, 'IN',    'ES PARTE DE', true),
(8, 'NOT IN','NO ES PARTE DE', true);

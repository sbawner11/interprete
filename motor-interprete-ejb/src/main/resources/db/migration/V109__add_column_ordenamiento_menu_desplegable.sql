-- Catalogo: cat_tipo_ordenamiento
CREATE TABLE motor_interprete.cat_tipo_ordenamiento (
    id_tipo_ordenamiento int4 NOT NULL,
    descripcion varchar(200) NOT NULL,
    activo bool NOT NULL DEFAULT TRUE,
    CONSTRAINT cat_tipo_ordenamiento_pk PRIMARY KEY (id_tipo_ordenamiento)
);

-- Datos ordenamiento
INSERT INTO motor_interprete.cat_tipo_ordenamiento (
    id_tipo_ordenamiento,
    descripcion
)
VALUES
    (1, 'Ascendente'),
    (2, 'Descendente');

-- Agregamos columna a componente menu desplegable
ALTER TABLE motor_interprete.componente_menu_desplegable
ADD COLUMN id_tipo_ordenamiento int4;

-- Referencia a catalogo de tipo de ordenamiento
ALTER TABLE motor_interprete.componente_menu_desplegable
ADD CONSTRAINT fk_cat_tipo_ordenamiento
FOREIGN KEY (id_tipo_ordenamiento)
REFERENCES motor_interprete.cat_tipo_ordenamiento(id_tipo_ordenamiento)
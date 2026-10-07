--Se actualiza nombre de atributo de Localidad a Colonia
UPDATE motor_interprete.cat_atributos_componentes 
SET nombre_atributo = 'Colonia' 
WHERE id_atributo_componente  = 7;

--Se agrega nuevo atributo para componente Datos D Domicilio, para la dirección completa
INSERT INTO motor_interprete.cat_atributos_componentes
(id_atributo_componente, id_tipo_componente, nombre_atributo, orden)
VALUES(25, 10, 'Completo', 8);

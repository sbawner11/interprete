--SE AGREGA COLUMNA PARA ORDENAMIENTO DE TIPOS DE COMPONENTES

ALTER TABLE motor_interprete.cat_tipo_componente ADD orden int4 DEFAULT 0 NOT NULL;

--SE ACTUALIZA ORDEN
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 1 WHERE id_tipo_componente = 15;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 2 WHERE id_tipo_componente = 1;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 3 WHERE id_tipo_componente = 7;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 4 WHERE id_tipo_componente = 2;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 5 WHERE id_tipo_componente = 3;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 6 WHERE id_tipo_componente = 10;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 7 WHERE id_tipo_componente = 14;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 8 WHERE id_tipo_componente = 12;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 9 WHERE id_tipo_componente = 11;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 10 WHERE id_tipo_componente = 4;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 11 WHERE id_tipo_componente = 8;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 12 WHERE id_tipo_componente = 13;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 13 WHERE id_tipo_componente = 6;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 14 WHERE id_tipo_componente = 5;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 15 WHERE id_tipo_componente = 16;
UPDATE motor_interprete.cat_tipo_componente SET ORDEN = 16 WHERE id_tipo_componente = 9;
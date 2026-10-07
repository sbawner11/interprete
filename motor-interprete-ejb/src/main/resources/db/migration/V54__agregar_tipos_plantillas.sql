--INSERT PARA CATALOGO DE ORIGEN TOKEN--
INSERT INTO motor_interprete.cat_origen_token
(id_origen_token, descripcion, activo)
VALUES(7, 'Nombre quien registra trámite', true);

INSERT INTO motor_interprete.cat_origen_token
(id_origen_token, descripcion, activo)
VALUES(8, 'Nombre del proyecto', true);

INSERT INTO motor_interprete.cat_origen_token
(id_origen_token, descripcion, activo)
VALUES(9, 'Dependencia', true);

INSERT INTO motor_interprete.cat_origen_token
(id_origen_token, descripcion, activo)
VALUES(10, 'Estatus trámite', true);

--INSERT PARA CATALOGO DE TIPO PLANTILLA--
INSERT INTO motor_interprete.cat_tipo_plantilla
(id_tipo_plantilla, descripcion, activo)
VALUES(3, 'Plantilla para registro concluido', true);

INSERT INTO motor_interprete.cat_tipo_plantilla
(id_tipo_plantilla, descripcion, activo)
VALUES(4, 'Plantilla comprobante registro', true);
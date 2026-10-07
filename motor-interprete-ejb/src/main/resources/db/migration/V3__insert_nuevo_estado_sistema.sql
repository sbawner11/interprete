-- Se agregan los nuevos estados del sistema "Pausado" y "Finalizado" 26-09-2022
INSERT INTO motor_interprete.cat_estados_sistema (id_estado_sistema, descripcion) VALUES(5, 'Pausado');
INSERT INTO motor_interprete.cat_estados_sistema (id_estado_sistema, descripcion) VALUES(6, 'Finalizado');
-- Se agrega el nuevo estatus de proyecto "Finalizado" 26-09-2022
INSERT INTO motor_interprete.cat_estatus_proyecto (id_estatus_proyecto, descripcion) VALUES(7, 'Finalizado');
-- Se agrega el nuevo estatus para el tramite 25/10/2022
INSERT INTO motor_interprete.cat_estatus_tramite (id_estatus_tramite, descripcion) VALUES(8, 'Revisado');
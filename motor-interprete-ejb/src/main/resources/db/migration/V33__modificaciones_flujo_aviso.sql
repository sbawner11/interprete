--Se agrega campo descripcion_aviso para mostrar en sistema el nombre de los estatus de trámite referentes a un aviso
ALTER TABLE motor_interprete.cat_estatus_tramite ADD descripcion_aviso varchar(60) NULL;
--Se agregan las nuevas descripciones de los estatus para un aviso
UPDATE motor_interprete.cat_estatus_tramite SET descripcion_aviso = 'Expedido' WHERE id_estatus_tramite = 7;
UPDATE motor_interprete.cat_estatus_tramite SET descripcion_aviso = 'Revocado' WHERE id_estatus_tramite = 6;
UPDATE motor_interprete.cat_estatus_tramite SET descripcion_aviso = 'En captura' WHERE id_estatus_tramite = 1;
UPDATE motor_interprete.cat_estatus_tramite SET descripcion_aviso = 'Pendiente de pago' WHERE id_estatus_tramite = 2;
UPDATE motor_interprete.cat_estatus_tramite SET descripcion_aviso = 'Enviado' WHERE id_estatus_tramite = 3;
UPDATE motor_interprete.cat_estatus_tramite SET descripcion_aviso = 'En corrección' WHERE id_estatus_tramite = 4;
UPDATE motor_interprete.cat_estatus_tramite SET descripcion_aviso = 'Corregido' WHERE id_estatus_tramite = 5;
UPDATE motor_interprete.cat_estatus_tramite SET descripcion_aviso = 'Revisado' WHERE id_estatus_tramite = 8;

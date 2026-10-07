-- Corrige constraint en campo fecha_notificacion para permitir valores NULL
-- Los registros pendientes de envío se crean con fecha_notificacion = NULL
-- y se actualiza con la fecha cuando el envío es confirmado exitosamente

ALTER TABLE motor_interprete.notificacion_movimiento_tramite 
ALTER COLUMN fecha_notificacion DROP NOT NULL;

COMMENT ON COLUMN motor_interprete.notificacion_movimiento_tramite.fecha_notificacion 
IS 'Fecha cuando el movimiento del trámite es enviado y aceptado por la otra aplicación. NULL indica pendiente de envío';

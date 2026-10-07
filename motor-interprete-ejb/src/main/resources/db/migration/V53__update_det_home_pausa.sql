--CAMBIOS PARA DEPURACION DE TABLA DET_PAUSA

--SE ELIMINA REGISTRO DE SECCIÓN 21 DETALLE DE PAUSA DEL CATALOGO DE SECCIONES
DELETE FROM motor_interprete.cat_secciones_proyecto WHERE id_seccion_proyecto = 21;

--SE ELIMINA RELACION DE DETALLE_PAUSA CON PROYECTO
ALTER TABLE motor_interprete.det_pausa drop constraint det_pausa_proyecto_fk;

--SE ELIMINA TABLA DE DETALLE DE PAUSA.
DROP TABLE motor_interprete.det_pausa;

--SE ELIMINA BANDERA QUE SE TENIA A NIVEL DE DETALLE DEL PROYECTO.
ALTER TABLE motor_interprete.proyecto DROP COLUMN habilita_pausa;

--CAMBIOS PARA HABILITAR NUEVOS CAMPOS EN LA TABLA DE DET_HOME
ALTER TABLE motor_interprete.det_home ADD personaliza_pausa bool NOT NULL DEFAULT false;
ALTER TABLE motor_interprete.det_home ADD titulo_pausa varchar(100) NULL;
ALTER TABLE motor_interprete.det_home ADD descripcion_pausa varchar(500) NULL;

--COMENTARIOS SOBRE NUEVAS COLUMNAS QUE SE AGREGAN AL DETALLE DE HOME
COMMENT ON COLUMN motor_interprete.det_home.personaliza_pausa IS 'Bandera para que indica si se personaliza la modal de estatus pausa en el proyecto.';
COMMENT ON COLUMN motor_interprete.det_home.titulo_pausa IS 'Campo que registra el titulo que se muestra en la modal de pausa.';
COMMENT ON COLUMN motor_interprete.det_home.descripcion_pausa IS 'Campo que registra la descripcion que se muestra en la modal de pausa.';
	
